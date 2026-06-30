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
            @RequestParam Map<String, String> params,
            Model model) {

        Integer verRespuestasId = null;
        Integer citarId = null;

        try {
            if (params.containsKey("verRespuestasId") && !params.get("verRespuestasId").isEmpty()) {
                verRespuestasId = Integer.parseInt(params.get("verRespuestasId"));
            }
            if (params.containsKey("citarId") && !params.get("citarId").isEmpty()) {
                citarId = Integer.parseInt(params.get("citarId"));
            }
        } catch (NumberFormatException e) {
            // Ignorar errores de conversión
        }

        String sql = "SELECT u.*, c.nombre AS nombrePuesto " +
                "FROM user_inf u " +
                "LEFT JOIN categoria_puestos c ON u.puesto = c.id";
        List<UserInf> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserInf.class));
        model.addAttribute("listaPostulantes", lista);

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
            } else {
                model.addAttribute("respuestasSeleccionadas", null);
            }
        } else {
            model.addAttribute("respuestasSeleccionadas", null);
        }

        if (citarId != null) {
            model.addAttribute("idPostulanteCitar", citarId);
        }

        return "crudpostulantes";
    }

    @GetMapping("/api/preguntas-por-puesto/{idPuesto}")
    @ResponseBody
    public List<Questions> obtenerPreguntasPorPuesto(@PathVariable int idPuesto) {
        String sql = "SELECT * FROM questions WHERE id_puesto = ? AND estado = 'Activo' LIMIT 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Questions.class), idPuesto);
    }

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

    // CORREGIDO: Solución al error de id_quest NULL de la base de datos
    @PostMapping("/postular/guardar")
    public String guardarPostulacion(@RequestParam Map<String, String> todasLasParams) {
        try {
            // 1. Validar y recuperar el DNI como int
            String dniStr = todasLasParams.get("dni");
            if (dniStr == null || dniStr.trim().isEmpty()) {
                return "redirect:/postular?error=dni_vacio";
            }
            int dni = Integer.parseInt(dniStr.trim());

            // 2. Leer nombre, edad y puesto
            String nombre = todasLasParams.get("nombre");
            int idPuesto = Integer.parseInt(todasLasParams.get("puesto"));

            int edad = 18;
            if (todasLasParams.containsKey("edad") && !todasLasParams.get("edad").isEmpty()) {
                edad = Integer.parseInt(todasLasParams.get("edad"));
            }

            // 3. Obtener el id_quest asociado al puesto
            Integer idQuestReal = null;
            try {
                String sqlBuscarQuest = "SELECT id FROM questions WHERE id_puesto = ? LIMIT 1";
                idQuestReal = jdbcTemplate.queryForObject(sqlBuscarQuest, Integer.class, idPuesto);
            } catch (Exception e) {
                idQuestReal = null; // Permite guardar nulo si el puesto no tiene cuestionario
            }

            // 4. Registrar al postulante en user_inf
            String sqlUser = "INSERT INTO user_inf (dni, nombre, edad, id_quest, puesto, estado) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlUser, dni, nombre, edad, idQuestReal, idPuesto, "PENDIENTE EN EVALUACION");

            // 5. Recuperar el último ID autonumérico insertado
            Integer idUser = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

            // 6. Registrar respuestas convirtiendo de forma segura cualquier "" en "No aplica"
            String sqlPreg = "INSERT INTO preg_recluta (id_user, res1, res2, res3, res4, res5, res6, res7, res8, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlPreg,
                    idUser,
                    obtenerValorSeguro(todasLasParams.get("res1")),
                    obtenerValorSeguro(todasLasParams.get("res2")),
                    obtenerValorSeguro(todasLasParams.get("res3")),
                    obtenerValorSeguro(todasLasParams.get("res4")),
                    obtenerValorSeguro(todasLasParams.get("res5")),
                    obtenerValorSeguro(todasLasParams.get("res6")),
                    obtenerValorSeguro(todasLasParams.get("res7")),
                    obtenerValorSeguro(todasLasParams.get("res8")),
                    "PENDIENTE EN EVALUACION"
            );

        } catch (Exception e) {
            System.err.println("Error al procesar la postulación: ");
            e.printStackTrace();
            return "redirect:/postular?error=true";
        }

        return "redirect:/crudpostulantes";
    }

    // Función auxiliar interna para evitar NumberFormatException en las respuestas vacías
    private String obtenerValorSeguro(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "No aplica";
        }
        return valor.trim();
    }
    @PostMapping("/crudpostulantes/agendar-cita")
    public String agendarCita(@RequestParam Map<String, String> params) {
        try {
            String idUserStr = params.get("idUser");
            String linkMeet = params.get("linkMeet");
            String fechaHora = params.get("fechaHora");

            if (idUserStr == null || linkMeet == null || fechaHora == null || idUserStr.isEmpty()) {
                return "redirect:/crudpostulantes?error=Incompletos";
            }

            int idUser = Integer.parseInt(idUserStr);

            String sqlUpdateUser = "UPDATE user_inf SET estado = 'ENTREVISTA' WHERE id = ?";
            jdbcTemplate.update(sqlUpdateUser, idUser);

            jdbcTemplate.update("UPDATE preg_recluta SET estado = 'ENTREVISTA' WHERE id_user = ?", idUser);
            jdbcTemplate.update("DELETE FROM citas_entrevista WHERE id_user = ?", idUser);

            String sqlCita = "INSERT INTO citas_entrevista (id_user, link_meet, fecha_hora_entrevista) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlCita, idUser, linkMeet, fechaHora);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/crudpostulantes?error=Exception";
        }

        return "redirect:/crudpostulantes";
    }

    @GetMapping("/crudpostulantes/estado/{id}/{accion}")
    public String cambiarEstado(@PathVariable("id") int id, @PathVariable("accion") String accion) {
        String estadoDb = "RECHAZADO";
        if ("aprobar".equalsIgnoreCase(accion)) {
            estadoDb = "APROBADO";
        } else if ("rechazar".equalsIgnoreCase(accion)) {
            estadoDb = "RECHAZADO";
        }

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