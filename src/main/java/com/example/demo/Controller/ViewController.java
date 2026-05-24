//@CODEX login

package com.example.demo.Controller;

import com.example.demo.model.Usuarios;
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
public class ViewController {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Permite consultar la BD H2

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String contrasena,
                                Model model) {
        // Consulta SQL apuntando a la tabla usuarios
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";

        List<Usuarios> lista = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Usuarios.class),
                correo,
                contrasena
        );

        if (!lista.isEmpty()) {
            return "redirect:/gestion";
        } else {
            //Si está vacía, las credenciales no existen o están mal escritas
            model.addAttribute("error", "Correo electrónico o contraseña incorrectos.");
            return "login"; //Recarga la misma página mostrando el error
        }
    }
    @GetMapping("/")
    public String Main() {
        return "main";
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

    @GetMapping("/postular")
    public String mostrarPostulacion() {
        return "postular"; // Retorna postular.jsp
    }
    @GetMapping("/evento")
    public String mostrarEvento(Model model) {
        // Consulta para obtener todos los eventos
        String sql = "SELECT * FROM evento";
        List<com.example.demo.model.Evento> listaEventos = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(com.example.demo.model.Evento.class)
        );

        // Pasamos la lista a la vista
        model.addAttribute("listaEventos", listaEventos);

        return"evento-vist"; // Retorna evento.jsp
    }
    @GetMapping("/metricas")
    public String mostrarMetricas() {
        return "metricas"; // Retorna el archivo metricas.jsp
    }
}
