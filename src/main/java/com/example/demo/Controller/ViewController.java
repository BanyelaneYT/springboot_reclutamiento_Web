package com.example.demo.Controller;

import com.example.demo.model.Questions;
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
    public String mostrarPostulacion(
            @RequestParam(value = "puestoId", required = false) Integer puestoId,
            Model model) {

        // Listar todos los puestos para el combobox superior
        List<CategoriaPuestos> puestos = jdbcTemplate.query(
                "SELECT * FROM categoria_puestos WHERE estado = 1",
                new BeanPropertyRowMapper<>(CategoriaPuestos.class)
        );
        model.addAttribute("puestos", puestos);

        if (puestoId != null) {
            model.addAttribute("puestoSeleccionadoId", puestoId);

            // Buscar las preguntas asociadas al puesto
            String sqlQuest = "SELECT * FROM questions WHERE id_puesto = ? LIMIT 1";
            List<Questions> listaPreguntas = jdbcTemplate.query(sqlQuest, new BeanPropertyRowMapper<>(Questions.class), puestoId);

            if (!listaPreguntas.isEmpty()) {
                model.addAttribute("preguntas", listaPreguntas.get(0));
            } else {
                // SI NO HAY PREGUNTAS, enviamos un objeto vacío con un nombre para evitar el NullPointerException en el JSP
                Questions vacia = new Questions();
                vacia.setNombre("General (Sin preguntas específicas)");
                model.addAttribute("preguntas", vacia);
            }
        }
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