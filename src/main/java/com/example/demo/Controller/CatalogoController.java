package com.example.demo.Controller;

import com.example.demo.model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CatalogoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/cat")
    public String ircat() {
        return "catalogo-crud";
    }
    // LISTAR
    @GetMapping("/catalogo")
    public String listar(Model model) {
        String sql = "SELECT * FROM evento";
        List<Evento> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Evento>(Evento.class));

        model.addAttribute("listaCatalogo", lista);
        return "/catalogo-crud";
    }
    // ACTUALIZAR (UPDATE por ID)
    @PostMapping("/catalogo/actualizar")
    public String actualizar(@RequestParam int id, @RequestParam String nombre,
                             @RequestParam String tipo, @RequestParam String fecha,
                             @RequestParam String descripcion) {
        String sql = "UPDATE evento SET nombre=?, tipo=?, fecha=?, descripcion=? WHERE id=?";
        jdbcTemplate.update(sql, nombre, tipo, fecha, descripcion, id);
        return "redirect:/catalogo";
    }

    // ELIMINAR (DELETE por ID)
    @GetMapping("/catalogo/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        String sql = "DELETE FROM evento WHERE id=?";
        jdbcTemplate.update(sql, id);
        return "redirect:/catalogo";
    }
    @GetMapping("/contacto")
    public String mostrarFormulario() {
        return "contacto";
    }
}
