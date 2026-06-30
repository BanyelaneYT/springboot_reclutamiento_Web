package com.example.demo.Controller;

import com.example.demo.model.Preg_Recluta;
import com.example.demo.model.UserInf;
import com.example.demo.model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ReclutaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/crudpostulantes")
    public String listarPostulantes(
            @RequestParam(value = "verRespuestasId", required = false) Integer verRespuestasId,
            @RequestParam(value = "citarId", required = false) Integer citarId,
            Model model) {

        // Consulta SQL limpia y directa para listar postulantes con Alias CamelCase
        String sql = """
        SELECT
            u.id,
            u.dni,
            u.nombre,
            u.edad,
            u.id_quest AS idQuest,
            u.puesto,
            u.estado,
            c.nombre AS nombrePuesto
        FROM user_inf u
        LEFT JOIN categoria_puestos c ON u.puesto = c.id
        ORDER BY u.id DESC
        """;

        try {
            List<UserInf> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserInf.class));
            model.addAttribute("listaPostulantes", lista);
        } catch (Exception e) {
            System.out.println("=== ERROR AL LISTAR POSTULANTES ===");
            e.printStackTrace();
        }

        // CORRECCIÓN CRÍTICA: Bloque para cargar el mapa estructurado en MAYÚSCULAS que espera tu JSP
        if (verRespuestasId != null) {
            String sqlRespuestasCruzadas = "SELECT " +
                    "q.preg1 AS PREG1, r.res1 AS RES1, " +
                    "q.preg2 AS PREG2, r.res2 AS RES2, " +
                    "q.preg3 AS PREG3, r.res3 AS RES3, " +
                    "q.preg4 AS PREG4, r.res4 AS RES4, " +
                    "q.preg5 AS PREG5, r.res5 AS RES5, " +
                    "q.preg6 AS PREG6, r.res6 AS RES6, " +
                    "q.preg7 AS PREG7, r.res7 AS RES7, " +
                    "q.preg8 AS PREG8, r.res8 AS RES8 " +
                    "FROM user_inf u " +
                    "JOIN preg_recluta r ON u.id = r.id_user " +
                    "JOIN questions q ON u.puesto = q.id_puesto " +
                    "WHERE u.id = ? LIMIT 1";

            try {
                List<Map<String, Object>> respuestasMap = jdbcTemplate.queryForList(sqlRespuestasCruzadas, verRespuestasId);

                if (!respuestasMap.isEmpty()) {
                    model.addAttribute("respuestasSeleccionadas", respuestasMap.get(0));
                } else {
                    System.out.println("=== ADVERTENCIA: No se encontraron respuestas cruzadas para el ID: " + verRespuestasId);
                }
                model.addAttribute("idPostulanteVer", verRespuestasId);
            } catch (Exception e) {
                System.out.println("=== ERROR AL EXECUTER SQL DE RESPUESTAS ===");
                e.printStackTrace();
            }
        }

        // Bloque para agendar cita
        if (citarId != null) {
            model.addAttribute("idPostulanteCitar", citarId);
        }

        return "crudpostulantes";
    }

    // API para retornar las preguntas asignadas a un puesto mediante AJAX
    @GetMapping("/api/preguntas-por-puesto/{idPuesto}")
    @ResponseBody
    public List<Questions> obtenerPreguntasPorPuesto(@PathVariable int idPuesto) {
        String sql = "SELECT * FROM questions WHERE id_puesto = ? AND estado = 'Activo' LIMIT 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Questions.class), idPuesto);
    }

    // Endpoint para ver las respuestas en ventana modal o JSON
    @GetMapping("/crudpostulantes/respuestas/{idUser}")
    @ResponseBody
    public Map<String, Object> verRespuestas(@PathVariable int idUser) {
        String sql = "SELECT q.preg1, r.res1, q.preg2, r.res2, q.preg3, r.res3, q.preg4, r.res4, " +
                "q.preg5, r.res5, q.preg6, r.res6, q.preg7, r.res7, q.preg8, r.res8 " +
                "FROM user_inf u " +
                "JOIN preg_recluta r ON u.id = r.id_user " +
                "JOIN questions q ON u.puesto = q.id_puesto " +
                "WHERE u.id = ?";
        List<Map<String, Object>> res = jdbcTemplate.queryForList(sql, idUser);
        return res.isEmpty() ? null : res.get(0);
    }

    // Agendar entrevista
    @PostMapping("/crudpostulantes/agendar-cita")
    public String agendarCita(
            @RequestParam("idUser") Integer idUser,
            @RequestParam("linkMeet") String linkMeet,
            @RequestParam("fechaHora") String fechaHora) {

        if (idUser == null || idUser <= 0) {
            return "redirect:/crudpostulantes?error=idInvalido";
        }
        if (linkMeet == null || linkMeet.trim().isEmpty()) {
            return "redirect:/crudpostulantes?error=linkInvalido";
        }
        if (fechaHora == null || fechaHora.trim().isEmpty()) {
            return "redirect:/crudpostulantes?error=fechaInvalida";
        }

        try {
            String sqlUpdateUser = "UPDATE user_inf SET estado = 'ENTREVISTA' WHERE id = ?";
            jdbcTemplate.update(sqlUpdateUser, idUser);

            jdbcTemplate.update("UPDATE preg_recluta SET estado = 'ENTREVISTA' WHERE id_user = ?", idUser);

            jdbcTemplate.update("DELETE FROM citas_entrevista WHERE id_user = ?", idUser);

            String sqlCita = "INSERT INTO citas_entrevista (id_user, link_meet, fecha_hora_entrevista) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlCita, idUser, linkMeet, fechaHora);

            return "redirect:/crudpostulantes?success=citaAgendada";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/crudpostulantes?error=errorGeneral";
        }
    }

    @GetMapping("/crudpostulantes/estado/{id}/{accion}")
    public String cambiarEstado(@PathVariable int id, @PathVariable String accion) {
        String estadoDb = accion.equals("aprobar") ? "APROBADO" : "RECHAZADO";
        jdbcTemplate.update("UPDATE user_inf SET estado = ? WHERE id = ?", estadoDb, id);
        jdbcTemplate.update("UPDATE preg_recluta SET estado = ? WHERE id_user = ?", estadoDb, id);
        return "redirect:/crudpostulantes";
    }

    @GetMapping("/crudpostulantes/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        jdbcTemplate.update("DELETE FROM preg_recluta WHERE id_user = ?", id);
        jdbcTemplate.update("DELETE FROM user_inf WHERE id = ?", id);
        return "redirect:/crudpostulantes";
    }

    @GetMapping("/consultar-estado")
    public String mostrarConsultaEstado() { return "consultar-estado"; }

    @PostMapping("/consultar-estado")
    public String procesarConsultaEstado(@RequestParam("dni") int dni, Model model) {
        String sql = "SELECT u.*, ce.link_meet, ce.fecha_hora_entrevista, " +
                "CASE WHEN ce.fecha_hora_entrevista <= NOW() THEN 1 ELSE 0 END as LINKHABILITADO " +
                "FROM user_inf u " +
                "LEFT JOIN citas_entrevista ce ON u.id = ce.id_user " +
                "WHERE u.dni = ?";
        List<Map<String, Object>> lista = jdbcTemplate.queryForList(sql, dni);

        if (!lista.isEmpty()) {
            model.addAttribute("postulante", lista.get(0));
            return "resultado-estado";
        } else {
            model.addAttribute("error", "No se encontró ninguna postulación con el DNI ingresado.");
            return "consultar-estado";
        }
    }
}