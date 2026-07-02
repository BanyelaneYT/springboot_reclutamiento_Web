package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BitacoraRepositoryDAO implements BitacoraRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> listarBitacora() {
        String sql = "SELECT b.id, " +
                "       b.id_usuario AS idUsuario, " +
                "       b.id_recluta AS idRecluta, " +
                "       u.correo AS usuarioCorreo, " +
                "       r.nombre AS reclutaNombre, " +
                "       r.estado AS reclutaEstado, " +
                "       b.accion, " +
                "       b.fecha_registro AS fechaRegistro " +
                "FROM bitacora b " +
                "LEFT JOIN administradores u ON b.id_usuario = u.id " +
                "LEFT JOIN user_inf r ON b.id_recluta = r.id " +
                "ORDER BY b.fecha_registro DESC";

        return jdbcTemplate.queryForList(sql);
    }
}
