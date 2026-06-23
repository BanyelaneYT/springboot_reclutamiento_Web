package com.example.demo.Controller;

import com.example.demo.model.CategoriaPuestos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoriaPuestosController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // LISTAR TODOS LOS PUESTOS (Para el panel de administración)
    @GetMapping("/catalogo")
    public String listar(Model model) {
        // Mapeo explícito de pres_rem snake_case hacia presRem camelCase usando un alias en SQL
        String sql = "SELECT id, nombre, tipo, descripcion, pres_rem AS presRem, horario, estado, pago FROM categoria_puestos";
        List<CategoriaPuestos> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CategoriaPuestos.class));

        model.addAttribute("listaCatalogo", lista);
        return "catalogo-crud"; // Corregido: Retorna directamente el nombre del archivo sin "/"
    }

    // ACTUALIZAR PUESTO EXISTENTE
    @PostMapping("/catalogo/actualizar")
    public String actualizar(@RequestParam int id,
                             @RequestParam String nombre,
                             @RequestParam String tipo,
                             @RequestParam String descripcion,
                             @RequestParam String presRem,
                             @RequestParam String horario,
                             @RequestParam int estado,
                             @RequestParam int pago) {
        String sql = "UPDATE categoria_puestos SET nombre=?, tipo=?, descripcion=?, pres_rem=?, horario=?, estado=?, pago=? WHERE id=?";
        jdbcTemplate.update(sql, nombre, tipo, descripcion, presRem, horario, estado, pago, id);
        return "redirect:/catalogo";
    }

    // ELIMINAR PUESTO POR ID
    @GetMapping("/catalogo/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        String sql = "DELETE FROM categoria_puestos WHERE id=?";
        jdbcTemplate.update(sql, id);
        return "redirect:/catalogo";
    }

    // REGISTRAR NUEVO PUESTO DE TRABAJO
    @PostMapping("/catalogo/guardar")
    public String guardar(@RequestParam String nombre,
                          @RequestParam String tipo,
                          @RequestParam String descripcion,
                          @RequestParam String presRem,
                          @RequestParam String horario,
                          @RequestParam int estado,
                          @RequestParam int pago) {
        String sql = "INSERT INTO categoria_puestos (nombre, tipo, descripcion, pres_rem, horario, estado, pago) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, nombre, tipo, descripcion, presRem, horario, estado, pago);
        return "redirect:/catalogo";
    }
}