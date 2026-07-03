package com.example.demo.Repository;

import com.example.demo.model.PostulanteEva;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostulanteEvaRepositoryDAO implements PostulanteEvaRepository {

    private static final String ESTADO_INICIAL = "PENDIENTE EN EVALUACION";

    private static final String SELECT_BASE =
            "SELECT pe.id, pe.id_user as idUser, pe.id_puesto as idPuesto, pe.puntaje, pe.descripcion, " +
            "CASE WHEN pe.estado = 'PENDIENTE' THEN '" + ESTADO_INICIAL + "' ELSE pe.estado END as estado, " +
            "COALESCE(pe.id_cita, 0) as idCita, " +
            "u.nombre as nombreUsuario, cp.nombre as nombrePuesto FROM postulante_eva pe " +
            "LEFT JOIN user_inf u ON pe.id_user = u.id " +
            "LEFT JOIN categoria_puestos cp ON pe.id_puesto = cp.id";

    private final JdbcTemplate jdbcTemplate;

    public PostulanteEvaRepositoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PostulanteEva> listarPostulantes() {
        return jdbcTemplate.query(SELECT_BASE, new BeanPropertyRowMapper<>(PostulanteEva.class));
    }

    @Override
    public PostulanteEva obtenerPostulantePorId(int id) {
        String sql = SELECT_BASE + " WHERE pe.id = ?";
        List<PostulanteEva> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PostulanteEva.class), id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void guardarPostulante(int idUser, int idPuesto, int puntaje, String descripcion, String estado, int idCita) {
        String sql = "INSERT INTO postulante_eva (id_user, id_puesto, puntaje, descripcion, estado, id_cita) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, idUser, idPuesto, puntaje, descripcion, normalizarEstado(estado), normalizarIdCita(idCita));
    }

    @Override
    public void actualizarPostulante(int id, int idPuesto, int puntaje, String descripcion, String estado, int idCita) {
        String sql = "UPDATE postulante_eva SET id_puesto = ?, puntaje = ?, descripcion = ?, estado = ?, id_cita = ? WHERE id = ?";
        jdbcTemplate.update(sql, idPuesto, puntaje, descripcion, normalizarEstado(estado), normalizarIdCita(idCita), id);
    }

    @Override
    public void eliminarPostulante(int id) {
        String sql = "DELETE FROM postulante_eva WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<PostulanteEva> obtenerPostulantesPorPuesto(int idPuesto) {
        String sql = SELECT_BASE + " WHERE pe.id_puesto = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PostulanteEva.class), idPuesto);
    }

    @Override
    public List<PostulanteEva> obtenerPostulantesPorUsuario(int idUser) {
        String sql = SELECT_BASE + " WHERE pe.id_user = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PostulanteEva.class), idUser);
    }

    private int normalizarIdCita(int idCita) {
        return Math.max(idCita, 0);
    }

    private String normalizarEstado(String estado) {
        if (estado == null || estado.isBlank() || "PENDIENTE".equals(estado)) {
            return ESTADO_INICIAL;
        }
        return estado;
    }
}
