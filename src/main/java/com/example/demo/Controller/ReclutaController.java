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

        // 1. Carga normal de la lista de postulantes
        String sql = "SELECT u.*, c.nombre AS nombrePuesto " +
                "FROM user_inf u " +
                "LEFT JOIN categoria_puestos c ON u.puesto = c.id";
        List<UserInf> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserInf.class));
        model.addAttribute("listaPostulantes", lista);

        // 2. PURO JAVA: Si solicitó ver respuestas, cargarlas directamente al modelo
        if (verRespuestasId != null) {
            String sqlRespuestas = "SELECT q.preg1, r.res1, q.preg2, r.res2, q.preg3, r.res3, q.preg4, r.res4, " +
                    "q.preg5, r.res5, q.preg6, r.res6, q.preg7, r.res7, q.preg8, r.res8 " +
                    "FROM user_inf u " +
                    "JOIN preg_recluta r ON u.id = r.id_user " +
                    "JOIN questions q ON u.puesto = q.id_puesto " +
                    "WHERE u.id = ?";
            List<Map<String, Object>> res = jdbcTemplate.queryForList(sqlRespuestas, verRespuestasId);
            if (!res.isEmpty()) {
                model.addAttribute("respuestasSeleccionadas", res.get(0));
                model.addAttribute("idPostulanteVer", verRespuestasId);
            }
        }

        // 3. PURO JAVA: Si solicitó citar, pasar el ID para habilitar el formulario
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

    // Guardado de postulante modificado para respuestas abiertas en String
    @PostMapping("/reclutar/guardar")
    public String guardarPostulante(@RequestParam int dni, @RequestParam String nombre,
                                    @RequestParam int edad, @RequestParam int puesto,
                                    @RequestParam(defaultValue="") String res1, @RequestParam(defaultValue="") String res2,
                                    @RequestParam(defaultValue="") String res3, @RequestParam(defaultValue="") String res4,
                                    @RequestParam(defaultValue="") String res5, @RequestParam(defaultValue="") String res6,
                                    @RequestParam(defaultValue="") String res7, @RequestParam(defaultValue="") String res8) {

        String sqlCheck = "SELECT COUNT(*) FROM user_inf WHERE dni = ?";
        Integer count = jdbcTemplate.queryForObject(sqlCheck, Integer.class, dni);
        if (count != null && count > 0) {
            return "redirect:/postular?error=duplicado";
        }

        // Las respuestas abiertas quedan en estado "PENDIENTE EN EVALUACION" para revisión manual de RRHH
        String estadoInicial = "PENDIENTE EN EVALUACION";

        String sqlUser = "INSERT INTO user_inf (dni, nombre, edad, puesto, estado) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlUser, dni, nombre, edad, puesto, estadoInicial);

        String sqlGetId = "SELECT id FROM user_inf WHERE dni = ? ORDER BY id DESC LIMIT 1";
        Integer idUsuarioGen = jdbcTemplate.queryForObject(sqlGetId, Integer.class, dni);

        if (idUsuarioGen != null) {
            String sqlPreg = "INSERT INTO preg_recluta (id_user, res1, res2, res3, res4, res5, res6, res7, res8, estado) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlPreg, idUsuarioGen, res1, res2, res3, res4, res5, res6, res7, res8, estadoInicial);
        }

        return "redirect:/consultar-estado";
    }

    // Agendar entrevista guardando el link y la fecha en la nueva tabla
    @PostMapping("/crudpostulantes/agendar-cita")
    public String agendarCita(@RequestParam int idUser,
                              @RequestParam String linkMeet,
                              @RequestParam String fechaHora) {

        String sqlUpdateUser = "UPDATE user_inf SET estado = 'ENTREVISTA' WHERE id = ?";
        jdbcTemplate.update(sqlUpdateUser, idUser);
        jdbcTemplate.update("UPDATE preg_recluta SET estado = 'ENTREVISTA' WHERE id_user = ?", idUser);

        // Limpiar citas previas del usuario si existieran y registrar la nueva
        jdbcTemplate.update("DELETE FROM citas_entrevista WHERE id_user = ?", idUser);
        String sqlCita = "INSERT INTO citas_entrevista (id_user, link_meet, fecha_hora_entrevista) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlCita, idUser, linkMeet, fechaHora);

        return "redirect:/crudpostulantes";
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
        // Obtenemos los datos cruzados con la cita programada si existe
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