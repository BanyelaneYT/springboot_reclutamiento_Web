package com.example.demo.Controller;

import com.example.demo.model.Preg_Recluta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ReclutaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. LISTAR RECLUTAS (Para el panel de gestión)
    @GetMapping("/reclutas")
    public String listarReclutas(Model model) {
        String sql = "SELECT * FROM preg_recluta";
        List<Preg_Recluta> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Preg_Recluta.class));
        model.addAttribute("listaReclutas", lista);
        return "gestion"; // Puedes usar gestion o crear una vista específica
    }

    // 2. GUARDAR CON VALIDACIÓN DE DNI Y EVALUACIÓN AUTOMÁTICA
    @PostMapping("/reclutar/guardar")
    public String guardar(@ModelAttribute Preg_Recluta recluta, Model model) {

        // Lógica de Evaluación: Sumamos las respuestas (0 a 4)
        int puntajeTotal = recluta.getRes1() + recluta.getRes2() + recluta.getRes3() +
                recluta.getRes4() + recluta.getRes5() + recluta.getRes6() +
                recluta.getRes7() + recluta.getRes8();

        // Umbral de aceptación (ejemplo: 20 puntos de 32 posibles)
        String resultadoEvaluacion = (puntajeTotal >= 20) ? "APROBADO" : "DESAPROBADO";
        String estadoInicial = "Pendiente";

        // SQL con WHERE NOT EXISTS para evitar duplicados por DNI
        String sql = "INSERT INTO preg_recluta (dni, nombre, edad, res1, res2, res3, res4, res5, res6, res7, res8, ubicacion, estado, res_eva) " +
                "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? " +
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
                estadoInicial,
                resultadoEvaluacion,
                recluta.getDni() // Para el parámetro del WHERE NOT EXISTS
        );

        if (filasAfectadas > 0) {
            return "redirect:/contacto?exito=true";
        } else {
            // Si el DNI ya existía, mandamos un error
            return "redirect:/contacto?error=duplicado";
        }
    }

    // 3. ELIMINAR RECLUTA
    @GetMapping("/reclutas/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        String sql = "DELETE FROM preg_recluta WHERE id=?";
        jdbcTemplate.update(sql, id);
        return "redirect:/reclutas";
    }
}