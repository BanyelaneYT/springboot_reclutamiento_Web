package com.example.demo.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            WHEN ce.id IS NOT NULL THEN 'ENTREVISTA'
            ELSE pe.estado
        END""";

    private static final String JOIN_CITA = " LEFT JOIN citas_entrevista ce ON ce.id_user = u.id";
    private static final String JOIN_ULTIMA_EVA = """
             LEFT JOIN postulante_eva pe ON pe.id = (
                 SELECT MAX(id) FROM postulante_eva WHERE id_user = u.id)""";

    private final JdbcTemplate jdbc;

    public ReclutaRepositoryDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<UserInf> listarPostulantes(Integer idPuesto, String filtroResultado) {
        StringBuilder sql = new StringBuilder("""
                SELECT u.id, u.dni, u.nombre, c.nombre nombrePuesto, %s estado
                FROM user_inf u
                LEFT JOIN categoria_puestos c ON u.id_puesto = c.id
                %s %s
                WHERE 1=1
                """.formatted(ESTADO, JOIN_CITA, JOIN_ULTIMA_EVA));

        List<Object> params = new ArrayList<>();

        if (idPuesto != null && idPuesto > 0) {
            sql.append(" AND u.id_puesto = ?");
            params.add(idPuesto);
        }
        if ("aprobados".equalsIgnoreCase(filtroResultado)) {
            sql.append(" AND pe.estado = 'APROBADO'");
        } else if ("desaprobados".equalsIgnoreCase(filtroResultado)) {
            sql.append(" AND pe.estado = 'RECHAZADO'");
        }

        sql.append(" ORDER BY u.id DESC");
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
                SELECT u.id, u.dni, u.nombre, %s estado,
                       ce.link_meet, ce.fecha_hora_entrevista,
                       CASE WHEN ce.fecha_hora_entrevista <= NOW() THEN 1 ELSE 0 END linkhabilitado,
                       pe.puntaje, pe.descripcion
                FROM user_inf u
                %s %s
                WHERE u.dni = ?
                """.formatted(ESTADO, JOIN_CITA, JOIN_ULTIMA_EVA);
        return jdbc.queryForList(sql, dni);
    }

    @Override
    public Integer registrarPostulante(int dni, String nombre, int edad, int idPuesto) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO user_inf (dni, nombre, edad, id_puesto) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dni);
            ps.setString(2, nombre);
            ps.setInt(3, edad);
            ps.setInt(4, idPuesto);
            return ps;
        }, key);

        Number idUser = key.getKey();
        if (idUser == null) {
            return null;
        }

        jdbc.update(
                "INSERT INTO postulante_eva (id_user, id_puesto, puntaje, descripcion, estado, id_cita) VALUES (?, ?, 0, '', ?, 0)",
                idUser.intValue(), idPuesto, ESTADO_INICIAL);
        return idUser.intValue();
    }
}
