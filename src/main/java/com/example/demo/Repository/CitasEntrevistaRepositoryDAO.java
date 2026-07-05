package com.example.demo.Repository;

import com.example.demo.model.CitasEntrevista;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Repository
public class CitasEntrevistaRepositoryDAO implements CitasEntrevistaRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CitasEntrevistaRepositoryDAO.class);

    public CitasEntrevistaRepositoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Parseo robusto reutilizable de la fecha/hora recibida desde el cliente
    private Timestamp parseFechaHora(String fechaHoraEntrevista) {
        if (fechaHoraEntrevista == null || fechaHoraEntrevista.trim().isEmpty()) {
            return new Timestamp(System.currentTimeMillis());
        }
        try {
            LocalDateTime ldt = LocalDateTime.parse(fechaHoraEntrevista);
            return Timestamp.valueOf(ldt);
        } catch (DateTimeParseException ex1) {
            try {
                String candidate = fechaHoraEntrevista.replace('T', ' ');
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime ldt2 = LocalDateTime.parse(candidate, fmt);
                return Timestamp.valueOf(ldt2);
            } catch (Exception ex2) {
                try {
                    LocalDateTime ldt3 = LocalDateTime.parse(fechaHoraEntrevista, DateTimeFormatter.ISO_DATE_TIME);
                    return Timestamp.valueOf(ldt3);
                } catch (Exception ex3) {
                    logger.error("No se pudo parsear fechaHoraEntrevista='{}'. Usando timestamp actual.", fechaHoraEntrevista, ex3);
                    return new Timestamp(System.currentTimeMillis());
                }
            }
        }
    }

    @Override
    public List<CitasEntrevista> listarCitas() {
        String sql = "SELECT ce.id, ce.id_user as idUser, ce.link_meet as linkMeet, " +
                "ce.fecha_hora_entrevista as fechaHoraEntrevista, u.nombre as nombreUsuario " +
                "FROM citas_entrevista ce LEFT JOIN user_inf u ON ce.id_user = u.id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CitasEntrevista.class));
    }

    @Override
    public CitasEntrevista obtenerCitaPorId(int id) {
        String sql = "SELECT ce.id, ce.id_user as idUser, ce.link_meet as linkMeet, " +
                "ce.fecha_hora_entrevista as fechaHoraEntrevista, u.nombre as nombreUsuario " +
                "FROM citas_entrevista ce LEFT JOIN user_inf u ON ce.id_user = u.id WHERE ce.id = ?";
        List<CitasEntrevista> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CitasEntrevista.class), id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public int guardarCita(int idUser, String linkMeet, String fechaHoraEntrevista) {
        // Eliminar citas previas para este usuario (mantener una sola cita activa)
        jdbcTemplate.update("DELETE FROM citas_entrevista WHERE id_user = ?", idUser);

        // Validaciones básicas
        if (fechaHoraEntrevista == null || fechaHoraEntrevista.trim().isEmpty()) {
            logger.warn("fechaHoraEntrevista vacío para idUser={}", idUser);
        }

        // Parsear fecha/hora mediante helper reutilizable
        Timestamp ts = parseFechaHora(fechaHoraEntrevista);

        String sql = "INSERT INTO citas_entrevista (id_user, link_meet, fecha_hora_entrevista) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws java.sql.SQLException {
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, idUser);
                    ps.setString(2, linkMeet == null ? "" : linkMeet);
                    ps.setTimestamp(3, ts);
                    return ps;
                }
            }, keyHolder);
        } catch (Exception ex) {
            logger.error("Error insertando cita para idUser={}", idUser, ex);
            throw new RuntimeException("Error al guardar cita de entrevista", ex);
        }

        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : 0;
    }

    @Override
    public void actualizarCita(int id, String linkMeet, String fechaHoraEntrevista) {
        String sql = "UPDATE citas_entrevista SET link_meet = ?, fecha_hora_entrevista = ? WHERE id = ?";
        Timestamp ts = parseFechaHora(fechaHoraEntrevista);
        jdbcTemplate.update(sql, linkMeet == null ? "" : linkMeet, ts, id);
    }

    @Override
    public void eliminarCita(int id) {
        String sql = "DELETE FROM citas_entrevista WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<CitasEntrevista> obtenerCitasPorUsuario(int idUser) {
        String sql = "SELECT ce.id, ce.id_user as idUser, ce.link_meet as linkMeet, " +
                "ce.fecha_hora_entrevista as fechaHoraEntrevista, u.nombre as nombreUsuario " +
                "FROM citas_entrevista ce LEFT JOIN user_inf u ON ce.id_user = u.id WHERE ce.id_user = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CitasEntrevista.class), idUser);
    }
}
