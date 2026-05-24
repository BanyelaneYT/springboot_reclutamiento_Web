package com.example.demo.Controller;

import com.example.demo.model.Preg_Recluta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class
ReclutaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // 1. LISTAR POSTULANTES EN EL PANEL RR.HH.
    @GetMapping("/crudpostulante")
    public String listarPostulantes(Model model) {
        String sql = "SELECT * FROM preg_recluta ORDER BY id DESC";
        List<Preg_Recluta> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Preg_Recluta.class));
        model.addAttribute("listaPostulantes", lista);
        return "crudpostulantes"; // Redirige a crudpostulantes.jsp
    }

    // 2. GUARDAR POSTULANTE (CON FILTRO DE DNI Y EVALUACIÓN AUTOMÁTICA)
    @PostMapping("/reclutar/guardar")
    public String guardar(@ModelAttribute Preg_Recluta recluta) {

        // Sumamos las respuestas del test (0 a 4 puntos por pregunta)
        int puntajeTotal = recluta.getRes1() + recluta.getRes2() + recluta.getRes3() +
                recluta.getRes4() + recluta.getRes5() + recluta.getRes6() +
                recluta.getRes7() + recluta.getRes8();

        // El perfil define su ESTADO directamente según el puntaje (20 puntos o más de 32 posibles)
        String estadoFinal = (puntajeTotal >= 20) ? "PENDIENTE EN EVALUACION" : "RECHAZADO";

        // SQL usando WHERE NOT EXISTS guiado por el DNI único (sin la columna res_eva)
        String sql = "INSERT INTO preg_recluta (dni, nombre, edad, res1, res2, res3, res4, res5, res6, res7, res8, ubicacion, estado) " +
                "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? " +
                "WHERE NOT EXISTS (SELECT 1 FROM preg_recluta WHERE dni = ?)";

        int filasAfectadas = jdbcTemplate.update(sql,
                recluta.getDni(),
                recluta.getNombre(),
                recluta.getEdad(),
                recluta.getRes1(),
                recluta.getRes2(),
                recluta.getRes3(),
                recluta.getRes4(),
                recluta.getRes5(),
                recluta.getRes6(),
                recluta.getRes7(),
                recluta.getRes8(),
                recluta.getUbicacion(),
                estadoFinal, // Se guarda directamente como el estado del postulante
                recluta.getDni()
        );

        if (filasAfectadas > 0) {
            return "redirect:/postular?exito=true";
        } else {
            return "redirect:/postular?error=duplicado";
        }
    }
    // 4. ACTUALIZAR ESTADO DEL POSTULANTE DESDE EL PROCESAMIENTO
    @GetMapping("/crudpostulantes/estado/{id}/{nuevoEstado}")
    public String actualizarEstado(@PathVariable int id, @PathVariable String nuevoEstado) {
        // Traducimos el parámetro de la URL a los estados reales del negocio
        String estadoDb = "";
        switch (nuevoEstado) {
            case "entrevista":
                estadoDb = "ENTREVISTA";
                break;
            case "rechazar":
                estadoDb = "RECHAZADO";
                break;
            case "aprobar":
                estadoDb = "APROBADO";
                break;
            default:
                estadoDb = "PENDIENTE";
                break;
        }

        String sql = "UPDATE preg_recluta SET estado = ? WHERE id = ?";
        jdbcTemplate.update(sql, estadoDb, id);

        return "redirect:/crudpostulante";
    }
    // 3. ELIMINAR POSTULANTE
    @GetMapping("/crudpostulantes/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        String sql = "DELETE FROM preg_recluta WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return "redirect:/crudpostulante";
    }

    // Mostrar página para consultar DNI
    @GetMapping("/consultar-estado")
    public String mostrarConsultaEstado() {
        return "consultar-estado";
    }

    // Procesar el DNI y buscar al postulante
    @PostMapping("/consultar-estado")
    public String procesarConsultaEstado(@RequestParam("dni") int dni, Model model) {
        String sql = "SELECT * FROM preg_recluta WHERE dni = ?";
        List<Preg_Recluta> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Preg_Recluta.class), dni);

        if (!lista.isEmpty()) {
            // Si encuentra el DNI, manda los datos a la vista de resultados
            model.addAttribute("postulante", lista.get(0));
            return "resultado-estado";
        } else {
            // Si no existe, recarga la página mostrando un error
            model.addAttribute("error", "No se encontró ninguna postulación con este DNI.");
            return "consultar-estado";
        }
    }

}