package com.example.demo.Controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BitacoraController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/bitacora")
    public String listarBitacora(Model model) {
        // Cambiado de preg_recluta a user_inf en los cruces
        String sql = "SELECT b.id, " +
                "       b.id_usuario AS idUsuario, " +
                "       b.id_recluta AS idRecluta, " +
                "       u.correo AS usuarioCorreo, " +
                "       r.nombre AS reclutaNombre, " +
                "       r.estado AS reclutaEstado, " +
                "       b.accion, " +
                "       b.fecha_registro AS fechaRegistro " +
                "FROM bitacora b " +
                "LEFT JOIN usuarios u ON b.id_usuario = u.id " +
                "LEFT JOIN user_inf r ON b.id_recluta = r.id " +
                "ORDER BY b.fecha_registro DESC";

        List<Map<String, Object>> listaHistorial = jdbcTemplate.queryForList(sql);
        model.addAttribute("listaBitacora", listaHistorial);

        return "bitacora-list";
    }
}