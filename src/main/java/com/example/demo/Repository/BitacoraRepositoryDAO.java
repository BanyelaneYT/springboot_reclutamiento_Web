package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BitacoraRepositoryDAO implements BitacoraRepository {

    private final JdbcTemplate jdbcTemplate;

    public BitacoraRepositoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> listarBitacora() {
        String sql = "SELECT b.id, " +
                "       b.id_usuario AS idUsuario, " +
                "       b.id_recluta AS idRecluta, " +
                "       u.correo AS usuarioCorreo, " +
                "       r.nombre AS reclutaNombre, " +
                "       COALESCE((SELECT 'ENTREVISTA' FROM citas_entrevista ce WHERE ce.id_user = r.id LIMIT 1), " +
                "                (SELECT pe.estado FROM postulante_eva pe WHERE pe.id_user = r.id ORDER BY pe.id DESC LIMIT 1), " +
                "                'PENDIENTE EN EVALUACION') AS reclutaEstado, " +
                "       b.accion, " +
                "       b.fecha_registro AS fechaRegistro " +
                "FROM bitacora b " +
                "LEFT JOIN administradores u ON b.id_usuario = u.id " +
                "LEFT JOIN user_inf r ON b.id_recluta = r.id " +
                "ORDER BY b.fecha_registro DESC";

        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public void registrarBitacora(int idUsuario, int idRecluta, String accion) {
        String sql = "INSERT INTO bitacora (id_usuario, id_recluta, accion) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, idUsuario, idRecluta, accion);
    }
}
