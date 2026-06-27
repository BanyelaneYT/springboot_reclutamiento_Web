package com.example.demo.Controller;

import com.example.demo.model.Usuarios;
import com.example.demo.model.CategoriaPuestos; // Cambiado de Evento a CategoriaPuestos
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
    private JdbcTemplate jdbcTemplate; // Permite consultar la BD

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
            // Si está vacía, las credenciales no existen o están mal escritas
            model.addAttribute("error", "Correo electrónico o contraseña incorrectos.");
            return "login"; // Recarga la misma página mostrando el error
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

    @GetMapping("/publicidad")
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
    public String mostrarFormularioPostular(Model model) {
        // Consulta adaptada a la nueva tabla con alias camelCase para el row mapper
        String sql = "SELECT id, nombre, tipo, descripcion, pres_rem AS presRem, horario, estado, pago FROM categoria_puestos WHERE estado = 1";

        // Mapea automáticamente a la clase CategoriaPuestos
        List<CategoriaPuestos> puestosActivos = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(CategoriaPuestos.class)
        );

        // Enviamos la lista de puestos disponibles para que el postulante seleccione uno en el formulario
        model.addAttribute("listaCatalogo", puestosActivos);
        return "postular";
    }

    @GetMapping("/evento")
    public String mostrarEvento(Model model) {
        // Traemos todas las categorías de puestos vigentes para la vista pública de convocatorias
        String sql = "SELECT id, nombre, tipo, descripcion, pres_rem AS presRem, horario, estado, pago FROM categoria_puestos";

        List<CategoriaPuestos> listaPuestos = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(CategoriaPuestos.class)
        );

        // Pasamos la lista a la vista pública usando el atributo esperado en tus JSPs actualizados
        model.addAttribute("listaCatalogo", listaPuestos);

        return "puestos-vist"; // Retorna evento-vist.jsp (puestos-vist)
    }

    @GetMapping("/metricas")
    public String mostrarMetricas() {
        return "metricas"; // Retorna el archivo metricas.jsp
    }
}