package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BitacoraRepositoryDAO implements BitacoraRepository {

    private static final String ESTADO_INICIAL = "PENDIENTE EN EVALUACION";

    private static final String RECLUTA_ESTADO = """
            COALESCE(CASE WHEN ce.id IS NOT NULL THEN 'ENTREVISTA' END, pe.estado, '%s')"""
            .formatted(ESTADO_INICIAL);

    private static final String JOIN_CITA = " LEFT JOIN citas_entrevista ce ON ce.id_user = r.id";
    private static final String JOIN_ULTIMA_EVA = """
             LEFT JOIN postulante_eva pe ON pe.id = (
                 SELECT MAX(id) FROM postulante_eva WHERE id_user = r.id)""";

    private final JdbcTemplate jdbc;

    public BitacoraRepositoryDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Map<String, Object>> listarBitacora() {
        String sql = """
                SELECT b.id, u.correo usuarioCorreo, r.nombre reclutaNombre,
                       %s reclutaEstado, b.accion, b.fecha_registro fechaRegistro
                FROM bitacora b
                LEFT JOIN administradores u ON b.id_usuario = u.id
                LEFT JOIN user_inf r ON b.id_recluta = r.id
                %s %s
                ORDER BY b.fecha_registro DESC
                """.formatted(RECLUTA_ESTADO, JOIN_CITA, JOIN_ULTIMA_EVA);
        return jdbc.queryForList(sql);
    }

    @Override
    public void registrarBitacora(int idUsuario, int idRecluta, String accion) {
        jdbc.update("INSERT INTO bitacora (id_usuario, id_recluta, accion) VALUES (?, ?, ?)", idUsuario, idRecluta, accion);
    }
}
