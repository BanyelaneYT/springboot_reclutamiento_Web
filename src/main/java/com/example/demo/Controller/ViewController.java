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
    public String mostrarFormularioPostular(@RequestParam(required = false) Integer puestoId, Model model) {
        // 1. Cargar la lista completa de puestos activos para el select box
        String sqlPuestos = "SELECT id, nombre, tipo, descripcion, pres_rem AS presRem, horario, estado, pago FROM categoria_puestos WHERE estado = 1";
        List<CategoriaPuestos> puestosActivos = jdbcTemplate.query(sqlPuestos, new BeanPropertyRowMapper<>(CategoriaPuestos.class));
        model.addAttribute("listaCatalogo", puestosActivos);

        // 2. Enviar el ID del puesto seleccionado
        model.addAttribute("puestoSeleccionadoId", puestoId);

        // 3. PURE JAVA: Si hay un puesto seleccionado, traer sus preguntas directamente desde la base de datos
        if (puestoId != null) {
            String sqlPreguntas = "SELECT * FROM questions WHERE id_puesto = ? AND estado = 'Activo' LIMIT 1";
            List<Questions> listaPreguntas = jdbcTemplate.query(sqlPreguntas, new BeanPropertyRowMapper<>(Questions.class), puestoId);

            if (!listaPreguntas.isEmpty()) {
                model.addAttribute("preguntasPuesto", listaPreguntas.get(0));
            }
        }

        return "postular";
    }
    @PostMapping("/postular/guardar")
    public String guardarPostulacion(
            @RequestParam("dni") int dni,
            @RequestParam("nombre") String nombre,
            @RequestParam("edad") int edad,
            @RequestParam("puesto") int puesto,
            @RequestParam(value = "res1", required = false, defaultValue = "") String res1,
            @RequestParam(value = "res2", required = false, defaultValue = "") String res2,
            @RequestParam(value = "res3", required = false, defaultValue = "") String res3,
            @RequestParam(value = "res4", required = false, defaultValue = "") String res4,
            @RequestParam(value = "res5", required = false, defaultValue = "") String res5,
            @RequestParam(value = "res6", required = false, defaultValue = "") String res6,
            @RequestParam(value = "res7", required = false, defaultValue = "") String res7,
            @RequestParam(value = "res8", required = false, defaultValue = "") String res8) {

        try {
            // 1. Insertamos al postulante en la tabla user_inf
            String sqlUser = "INSERT INTO user_inf (dni, nombre, edad, puesto, estado) VALUES (?, ?, ?, ?, 'PENDIENTE EN EVALUACION')";
            jdbcTemplate.update(sqlUser, dni, nombre, edad, puesto);

            // 2. Buscamos el ID autogenerado que le dio la base de datos por su DNI
            Integer userId = jdbcTemplate.queryForObject("SELECT id FROM user_inf WHERE dni = ?", Integer.class, dni);

            // 3. Insertamos sus respuestas en preg_recluta usando ese ID
            String sqlRespuestas = "INSERT INTO preg_recluta (id_user, res1, res2, res3, res4, res5, res6, res7, res8, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Pendiente')";
            jdbcTemplate.update(sqlRespuestas, userId, res1, res2, res3, res4, res5, res6, res7, res8);

            // CORRECCIÓN: Redirige a una vista existente, por ejemplo al login o main indicando el éxito.
            return "redirect:/login?exitoPostulacion=true";

        } catch (Exception e) {
            System.out.println("=== ERROR AL GUARDAR POSTULACIÓN ===");
            e.printStackTrace(); // Esto te mostrará en la consola exactamente qué falló (ej: DNI duplicado)

            // Si falla, regresa al formulario pasando el ID del puesto para que vuelva a cargar las preguntas
            return "redirect:/postular?puestoId=" + puesto + "&error=true";
        }
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