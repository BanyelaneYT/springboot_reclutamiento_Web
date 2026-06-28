package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/preguntas")
public class QuestionsController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String listarPreguntas(@RequestParam(value = "accion", required = false) String accion,
                                  @RequestParam(value = "editarId", required = false) Integer editarId,
                                  Model model) {

        // Forzamos los alias en MAYÚSCULAS para asegurar la lectura en el JSP
        String sql = "SELECT q.id AS ID, q.id_puesto AS ID_PUESTO, q.preg1 AS PREG1, q.preg2 AS PREG2, " +
                "q.preg3 AS PREG3, q.preg4 AS PREG4, q.preg5 AS PREG5, q.preg6 AS PREG6, " +
                "q.preg7 AS PREG7, q.preg8 AS PREG8, q.estado AS ESTADO, p.nombre AS NOMBREPUESTO " +
                "FROM questions q " +
                "JOIN categoria_puestos p ON q.id_puesto = p.id";
        List<Map<String, Object>> lista = jdbcTemplate.queryForList(sql);

        // Forzamos también el ID y NOMBRE de los puestos a Mayúsculas
        String sqlPuestos = "SELECT id AS ID, nombre AS NOMBRE FROM categoria_puestos WHERE estado = 1";
        List<Map<String, Object>> puestos = jdbcTemplate.queryForList(sqlPuestos);

        model.addAttribute("listaPreguntas", lista);
        model.addAttribute("listaPuestos", puestos);

        if ("nuevo".equals(accion)) {
            model.addAttribute("mostrarFormulario", true);
        } else if (editarId != null) {
            // Especificamos las columnas exactas para evitar conflictos con SELECT *
            String sqlBuscar = "SELECT id AS ID, id_puesto AS ID_PUESTO, preg1 AS PREG1, preg2 AS PREG2, " +
                    "preg3 AS PREG3, preg4 AS PREG4, preg5 AS PREG5, preg6 AS PREG6, " +
                    "preg7 AS PREG7, preg8 AS PREG8, estado AS ESTADO FROM questions WHERE id = ?";
            List<Map<String, Object>> encontradas = jdbcTemplate.queryForList(sqlBuscar, editarId);
            if (!encontradas.isEmpty()) {
                model.addAttribute("mostrarFormulario", true);
                model.addAttribute("preguntaSeleccionada", encontradas.get(0));
            }
        }

        return "preguntas-crud";
    }

    // ACCIÓN EXCLUSIVA PARA REGISTRAR NUEVO
    @PostMapping("/guardar")
    public String guardar(@RequestParam int idPuesto,
                          @RequestParam String preg1,
                          @RequestParam(required = false, defaultValue = "") String preg2,
                          @RequestParam(required = false, defaultValue = "") String preg3,
                          @RequestParam(required = false, defaultValue = "") String preg4,
                          @RequestParam(required = false, defaultValue = "") String preg5,
                          @RequestParam(required = false, defaultValue = "") String preg6,
                          @RequestParam(required = false, defaultValue = "") String preg7,
                          @RequestParam(required = false, defaultValue = "") String preg8) {

        String sqlInsert = "INSERT INTO questions (id_puesto, preg1, preg2, preg3, preg4, preg5, preg6, preg7, preg8, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Activo')";
        jdbcTemplate.update(sqlInsert, idPuesto, preg1, preg2, preg3, preg4, preg5, preg6, preg7, preg8);

        return "redirect:/preguntas";
    }

    // ACCIÓN EXCLUSIVA PARA ACTUALIZAR EXISTENTE
    @PostMapping("/editar")
    public String editar(@RequestParam int id,
                         @RequestParam int idPuesto,
                         @RequestParam String preg1,
                         @RequestParam(required = false, defaultValue = "") String preg2,
                         @RequestParam(required = false, defaultValue = "") String preg3,
                         @RequestParam(required = false, defaultValue = "") String preg4,
                         @RequestParam(required = false, defaultValue = "") String preg5,
                         @RequestParam(required = false, defaultValue = "") String preg6,
                         @RequestParam(required = false, defaultValue = "") String preg7,
                         @RequestParam(required = false, defaultValue = "") String preg8) {

        String sqlUpdate = "UPDATE questions SET id_puesto = ?, preg1 = ?, preg2 = ?, preg3 = ?, preg4 = ?, " +
                "preg5 = ?, preg6 = ?, preg7 = ?, preg8 = ? WHERE id = ?";
        jdbcTemplate.update(sqlUpdate, idPuesto, preg1, preg2, preg3, preg4, preg5, preg6, preg7, preg8, id);

        return "redirect:/preguntas";
    }
}