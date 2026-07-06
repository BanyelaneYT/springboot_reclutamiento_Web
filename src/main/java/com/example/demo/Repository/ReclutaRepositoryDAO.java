package com.example.demo.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserInf;

@Repository
public class ReclutaRepositoryDAO implements ReclutaRepository {

    private static final String ESTADO_INICIAL = "PENDIENTE EN EVALUACION";
    private static final BeanPropertyRowMapper<UserInf> MAPPER =
            new BeanPropertyRowMapper<>(UserInf.class);

    private static final String ESTADO = """
        CASE
            WHEN pe.estado IN ('APROBADO', 'RECHAZADO') THEN pe.estado
            WHEN ce.id IS NOT NULL AND pe.id_cita > 0 THEN 'ENTREVISTA'
            ELSE pe.estado
        END""";

    private static final String FROM_POSTULACION = """
            FROM postulante_eva pe
            JOIN user_inf u ON pe.id_user = u.id
            LEFT JOIN categoria_puestos c ON pe.id_puesto = c.id
            LEFT JOIN citas_entrevista ce ON ce.id = pe.id_cita AND pe.id_cita > 0""";

    private final JdbcTemplate jdbc;

    public ReclutaRepositoryDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<UserInf> listarPostulantes(Integer idPuesto, String filtroResultado) {
        StringBuilder sql = new StringBuilder("""
                SELECT pe.id, u.dni, u.nombre, c.nombre nombrePuesto, %s estado
                %s
                WHERE 1=1
                """.formatted(ESTADO, FROM_POSTULACION));

        List<Object> params = new ArrayList<>();

        if (idPuesto != null && idPuesto > 0) {
            sql.append(" AND pe.id_puesto = ?");
            params.add(idPuesto);
        }
        if ("aprobados".equalsIgnoreCase(filtroResultado)) {
            sql.append(" AND pe.estado = 'APROBADO'");
        } else if ("desaprobados".equalsIgnoreCase(filtroResultado)) {
            sql.append(" AND pe.estado = 'RECHAZADO'");
        }

        sql.append(" ORDER BY pe.id DESC");
        return params.isEmpty()
                ? jdbc.query(sql.toString(), MAPPER)
                : jdbc.query(sql.toString(), MAPPER, params.toArray());
    }

    @Override
    public void agendarCita(Integer idUser, String linkMeet, String fechaHora) {
        jdbc.update("DELETE FROM citas_entrevista WHERE id_user = ?", idUser);
        jdbc.update(
                "INSERT INTO citas_entrevista (id_user, link_meet, fecha_hora_entrevista) VALUES (?, ?, ?)",
                idUser, linkMeet, Timestamp.valueOf(LocalDateTime.parse(fechaHora)));
    }

    @Override
    public void cambiarEstado(int id, String estado) {
        jdbc.update(
                "UPDATE postulante_eva SET estado = ? WHERE id = (SELECT MAX(id) FROM postulante_eva WHERE id_user = ?)",
                estado, id);
    }

    @Override
    public void eliminar(int id) {
        jdbc.update("DELETE FROM user_inf WHERE id = ?", id);
    }

    @Override
    public List<Map<String, Object>> consultarEstadoPorDni(int dni) {
        String sql = """
                SELECT u.id, u.dni, u.nombre, pe.id idPostulacion, c.nombre nombrePuesto, %s estado,
                       ce.link_meet, ce.fecha_hora_entrevista,
                       CASE WHEN ce.fecha_hora_entrevista <= NOW() THEN 1 ELSE 0 END linkhabilitado,
                       pe.puntaje, pe.descripcion
                %s
                WHERE u.dni = ?
                ORDER BY pe.id DESC
                """.formatted(ESTADO, FROM_POSTULACION);
        return jdbc.queryForList(sql, dni);
    }

    @Override
    public boolean existePostulacionAlPuesto(int dni, int idPuesto) {
        Integer count = jdbc.queryForObject("""
                SELECT COUNT(*)
                FROM postulante_eva pe
                JOIN user_inf u ON pe.id_user = u.id
                WHERE u.dni = ? AND pe.id_puesto = ?
                """, Integer.class, dni, idPuesto);
        return count != null && count > 0;
    }

    @Override
    public Integer registrarPostulante(int dni, String nombre, int edad, int idPuesto) {
        Integer idUser = buscarIdPorDni(dni);
        if (idUser == null) {
            idUser = crearUsuario(dni, nombre, edad);
        } else {
            actualizarDatosPersonales(idUser, nombre, edad);
        }

        if (!insertarPostulacion(idUser, idPuesto)) {
            return null;
        }
        return idUser;
    }

    @Override
    public boolean tienePostulacionesActivas(int dni) {
        Integer count = jdbc.queryForObject("""
                SELECT COUNT(*)
                FROM postulante_eva pe
                JOIN user_inf u ON pe.id_user = u.id
                WHERE u.dni = ?
                  AND pe.estado NOT IN ('APROBADO', 'RECHAZADO')
                """, Integer.class, dni);
        return count != null && count > 0;
    }

    private Integer buscarIdPorDni(int dni) {
        List<Integer> ids = jdbc.query(
                "SELECT id FROM user_inf WHERE dni = ?",
                (rs, rowNum) -> rs.getInt("id"),
                dni);
        return ids.isEmpty() ? null : ids.get(0);
    }

    private Integer crearUsuario(int dni, String nombre, int edad) {
        KeyHolder key = new GeneratedKeyHolder();
        try {
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO user_inf (dni, nombre, edad) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, dni);
                ps.setString(2, nombre);
                ps.setInt(3, edad);
                return ps;
            }, key);
        } catch (DataIntegrityViolationException ex) {
            Integer idExistente = buscarIdPorDni(dni);
            if (idExistente == null) {
                throw ex;
            }
            return idExistente;
        }

        Number idUser = key.getKey();
        return idUser != null ? idUser.intValue() : null;
    }

    private void actualizarDatosPersonales(int idUser, String nombre, int edad) {
        jdbc.update("UPDATE user_inf SET nombre = ?, edad = ? WHERE id = ?", nombre, edad, idUser);
    }

    private boolean insertarPostulacion(int idUser, int idPuesto) {
        try {
            jdbc.update(
                    "INSERT INTO postulante_eva (id_user, id_puesto, puntaje, descripcion, estado, id_cita) VALUES (?, ?, 0, '', ?, 0)",
                    idUser, idPuesto, ESTADO_INICIAL);
            return true;
        } catch (DataIntegrityViolationException ex) {
            return false;
        }
    }
}
