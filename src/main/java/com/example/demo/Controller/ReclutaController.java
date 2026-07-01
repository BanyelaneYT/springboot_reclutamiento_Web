package com.example.demo.Controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.UserInf;

@Controller
public class ReclutaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/crudpostulantes")
    public String listarPostulantes(
            @RequestParam(value = "citarId", required = false) Integer citarId,
            Model model) {

        // Consulta SQL limpia y directa para listar postulantes con Alias CamelCase
        String sql = """
        SELECT
            u.id,
            u.dni,
            u.nombre,
            u.edad,
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

        // Bloque para agendar cita
        if (citarId != null) {
            model.addAttribute("idPostulanteCitar", citarId);
        }

        return "crudpostulantes";
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

            jdbcTemplate.update("DELETE FROM citas_entrevista WHERE id_user = ?", idUser);

            LocalDateTime fechaHoraParsed = LocalDateTime.parse(fechaHora);
            Timestamp fechaHoraTimestamp = Timestamp.valueOf(fechaHoraParsed);

            String sqlCita = "INSERT INTO citas_entrevista (id_user, link_meet, fecha_hora_entrevista) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlCita, idUser, linkMeet, fechaHoraTimestamp);

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
        return "redirect:/crudpostulantes";
    }

    @GetMapping("/crudpostulantes/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
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