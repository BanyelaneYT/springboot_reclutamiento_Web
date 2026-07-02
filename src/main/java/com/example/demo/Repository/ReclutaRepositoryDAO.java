package com.example.demo.Repository;

import com.example.demo.model.UserInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class ReclutaRepositoryDAO implements ReclutaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UserInf> listarPostulantes() {
        String sql = """
        SELECT
            u.id,
            u.dni,
            u.nombre,
            u.edad,
            u.puesto,
            u.estado,
            c.nombre AS nombrePuesto
        FROM user_inf u
        LEFT JOIN categoria_puestos c ON u.puesto = c.id
        ORDER BY u.id DESC
        """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserInf.class));
    }

    @Override
    public void agendarCita(Integer idUser, String linkMeet, String fechaHora) {
        jdbcTemplate.update("UPDATE user_inf SET estado = 'ENTREVISTA' WHERE id = ?", idUser);
        jdbcTemplate.update("DELETE FROM citas_entrevista WHERE id_user = ?", idUser);

        LocalDateTime fechaHoraParsed = LocalDateTime.parse(fechaHora);
        Timestamp fechaHoraTimestamp = Timestamp.valueOf(fechaHoraParsed);

        String sqlCita = "INSERT INTO citas_entrevista (id_user, link_meet, fecha_hora_entrevista) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlCita, idUser, linkMeet, fechaHoraTimestamp);
    }

    @Override
    public void cambiarEstado(int id, String estado) {
        jdbcTemplate.update("UPDATE user_inf SET estado = ? WHERE id = ?", estado, id);
    }

    @Override
    public void eliminar(int id) {
        jdbcTemplate.update("DELETE FROM user_inf WHERE id = ?", id);
    }

    @Override
    public List<Map<String, Object>> consultarEstadoPorDni(int dni) {
        String sql = "SELECT u.*, ce.link_meet, ce.fecha_hora_entrevista, " +
                "CASE WHEN ce.fecha_hora_entrevista <= NOW() THEN 1 ELSE 0 END as LINKHABILITADO " +
                "FROM user_inf u " +
                "LEFT JOIN citas_entrevista ce ON u.id = ce.id_user " +
                "WHERE u.dni = ?";
        return jdbcTemplate.queryForList(sql, dni);
    }
}
