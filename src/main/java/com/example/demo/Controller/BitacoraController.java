package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BitacoraController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. LISTAR HISTORIAL DE BITÁCORA
    // Usamos List<Map<String, Object>> para hacer JOINs y mostrar nombres reales en la vista JSP
    @GetMapping("/bitacora")
    public String listarBitacora(Model model) {
        String sql = "SELECT b.id, " +
                     "       u.correo AS usuarioCorreo, " +
                     "       r.nombre AS reclutaNombre, " +
                     "       e.nombre AS eventoNombre, " +
                     "       b.accion, " +
                     "       b.fecha_registro AS fechaRegistro " +
                     "FROM bitacora b " +
                     "LEFT JOIN usuarios u ON b.id_usuario = u.id " +
                     "LEFT JOIN preg_recluta r ON b.id_recluta = r.id " +
                     "LEFT JOIN evento e ON b.id_evento = e.id " +
                     "ORDER BY b.fecha_registro DESC";

        List<Map<String, Object>> listaHistorial = jdbcTemplate.queryForList(sql);
        model.addAttribute("listaBitacora", listaHistorial);
        
        return "bitacora-list"; // Redirige a bitacora-list.jsp
    }

    // 2. ELIMINAR UN REGISTRO DE LA BITÁCORA (Opcional por auditoría)
    @GetMapping("/bitacora/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        String sql = "DELETE FROM bitacora WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return "redirect:/bitacora";
    }

    public void registrarAccion(Integer idUsuario, Integer idRecluta, Integer idEvento, String accion) {
        String sql = "INSERT INTO bitacora (id_usuario, id_recluta, id_evento, accion) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, idUsuario, idRecluta, idEvento, accion);
    }
}