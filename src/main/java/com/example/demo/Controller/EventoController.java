package com.example.demo.Controller;

import com.example.demo.model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/Crudcreacion")
    public String listar(Model model) {
        // Consulta SQL pura para traer los datos
        String sql = "SELECT * FROM evento";
        List<Evento> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Evento.class));

        model.addAttribute("listaEventos", lista);
        return "evento-crud";
    }

    @PostMapping("/eventos/guardar")
    public String guardar(@RequestParam String nombre,
                          @RequestParam String tipo,
                          @RequestParam String fecha,
                          @RequestParam String descripcion) {

        // SQL para insertar. El ID no se pone porque es AUTO_INCREMENT en la BD
        String sql = "INSERT INTO evento (nombre, tipo, fecha, descripcion) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, nombre, tipo, fecha, descripcion);

        return "redirect:/Crudcreacion";
    }
}