package com.example.demo.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserInf;

@Repository
public class ReclutaRepositoryDAO implements ReclutaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReclutaRepositoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserInf> listarPostulantes() {
        String sql = """
        SELECT
            u.id,
            u.dni,
            u.nombre,
            u.edad,
            u.id_puesto AS puesto,
            c.nombre AS nombrePuesto,
            COALESCE(
                (SELECT CASE WHEN COUNT(*) > 0 THEN 'ENTREVISTA' END FROM citas_entrevista ce WHERE ce.id_user = u.id),
                (SELECT pe.estado FROM postulante_eva pe WHERE pe.id_user = u.id AND pe.id = (
                    SELECT MAX(id) FROM postulante_eva pe2 WHERE pe2.id_user = u.id
                )),
                'PENDIENTE EN EVALUACION'
            ) AS estado
        FROM user_inf u
        LEFT JOIN categoria_puestos c ON u.id_puesto = c.id
        ORDER BY u.id DESC
        """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserInf.class));
    }

    @Override
    public void agendarCita(Integer idUser, String linkMeet, String fechaHora) {
        jdbcTemplate.update("DELETE FROM citas_entrevista WHERE id_user = ?", idUser);

        LocalDateTime fechaHoraParsed = LocalDateTime.parse(fechaHora);
        Timestamp fechaHoraTimestamp = Timestamp.valueOf(fechaHoraParsed);

        String sqlCita = "INSERT INTO citas_entrevista (id_user, link_meet, fecha_hora_entrevista) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlCita, idUser, linkMeet, fechaHoraTimestamp);
    }

    @Override
    public void cambiarEstado(int id, String estado) {
        String sql = "UPDATE postulante_eva SET estado = ? WHERE id = " +
                "(SELECT MAX(id) FROM postulante_eva WHERE id_user = ?)";
        jdbcTemplate.update(sql, estado, id);
    }

    @Override
    public void eliminar(int id) {
        jdbcTemplate.update("DELETE FROM user_inf WHERE id = ?", id);
    }

    @Override
    public List<Map<String, Object>> consultarEstadoPorDni(int dni) {
        String sql = "SELECT u.id, u.dni, u.nombre, u.edad, u.id_puesto AS puesto, " +
                "COALESCE((SELECT CASE WHEN COUNT(*) > 0 THEN 'ENTREVISTA' END FROM citas_entrevista ce WHERE ce.id_user = u.id), " +
                "         (SELECT pe.estado FROM postulante_eva pe WHERE pe.id_user = u.id AND pe.id = (SELECT MAX(id) FROM postulante_eva pe2 WHERE pe2.id_user = u.id)), " +
                "         'PENDIENTE EN EVALUACION') AS estado, " +
                "ce.link_meet, ce.fecha_hora_entrevista, " +
                "CASE WHEN ce.fecha_hora_entrevista <= NOW() THEN 1 ELSE 0 END AS linkhabilitado, " +
                "(SELECT pe.puntaje FROM postulante_eva pe WHERE pe.id_user = u.id AND pe.id = (SELECT MAX(id) FROM postulante_eva pe2 WHERE pe2.id_user = u.id)) AS puntaje, " +
                "(SELECT pe.descripcion FROM postulante_eva pe WHERE pe.id_user = u.id AND pe.id = (SELECT MAX(id) FROM postulante_eva pe2 WHERE pe2.id_user = u.id)) AS descripcion " +
                "FROM user_inf u " +
                "LEFT JOIN citas_entrevista ce ON u.id = ce.id_user " +
                "WHERE u.dni = ?";
        return jdbcTemplate.queryForList(sql, dni);
    }

    @Override
    public Integer registrarPostulante(int dni, String nombre, int edad, int idPuesto) {
        // 1. Insertar en user_inf
        String sqlUser = "INSERT INTO user_inf (dni, nombre, edad, id_puesto) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sqlUser, dni, nombre, edad, idPuesto);
        
        // 2. Obtener el ID del usuario recién insertado
        Integer idUser = jdbcTemplate.queryForObject("SELECT id FROM user_inf WHERE dni = ? ORDER BY id DESC LIMIT 1", Integer.class, dni);
        
        // 3. Registrar automáticamente en postulante_eva
        if (idUser != null) {
            String sqlPostulante = "INSERT INTO postulante_eva (id_user, id_puesto, puntaje, descripcion, estado, id_cita) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlPostulante, idUser, idPuesto, 0, "", "PENDIENTE EN EVALUACION", 0);
        }
        
        return idUser;
    }
}
