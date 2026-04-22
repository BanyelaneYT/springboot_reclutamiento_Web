package com.example.demo.Controller;

import com.example.demo.model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EventoController {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Cambiamos Repository por JdbcTemplate

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String irMain() {
        return "main";
    }
    @GetMapping("/publicidad") //@CODEX
    public String mostrarPublicidad() {
        return "publicidad";
    }

    @GetMapping("/gestion")
    public String irGestion() {
        // Retorna el nombre del archivo JSP: gestion.jsp
        return "gestion";
    }
    @GetMapping("/contacto")
    public String mostrarFormulario() {
        return "contacto";
    }
    @GetMapping("/catalogo")
    public String listar(Model model) {
        String sql = "SELECT * FROM evento";
        List<Evento> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Evento>(Evento.class));

        model.addAttribute("listaCatalogo", lista);
        return "/catalogo-crud";
    }
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
    @PostMapping("/eventos/guardar")
    public String guardar(@RequestParam String nombre,
                          @RequestParam String tipo,
                          @RequestParam String fecha,
                          @RequestParam String descripcion) {

        // SQL para insertar. El ID no se pone porque es AUTO_INCREMENT en la BD
        String sql = "INSERT INTO evento (nombre, tipo, fecha, descripcion) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, nombre, tipo, fecha, descripcion);

        return "redirect:/catalogo";
    }
}