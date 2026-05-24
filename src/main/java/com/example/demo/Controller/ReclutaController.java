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
public class ReclutaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. LISTAR POSTULANTES EN EL PANEL RR.HH. (Original intacto, tal cual lo quieres)
    @GetMapping("/crudpostulante")
    public String listarPostulantes(Model model) {
        // Usamos LEFT JOIN para traer el nombre del evento, si es que tiene uno asignado
        String sql = "SELECT p.*, e.nombre AS nombreEvento " +
                "FROM preg_recluta p " +
                "LEFT JOIN evento e ON p.id_evento = e.id";

        List<Preg_Recluta> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Preg_Recluta.class));
        model.addAttribute("listaPostulantes", lista);
        return "crudpostulantes";
    }

    // 2. GUARDAR POSTULANTE (CON FILTRO DE DNI Y EVALUACIÓN AUTOMÁTICA)
    @PostMapping("/reclutar/guardar")
    public String guardar(@ModelAttribute Preg_Recluta recluta) {

        // Sumamos las respuestas del test (0 a 4 puntos por pregunta)
        int puntajeTotal = recluta.getRes1() + recluta.getRes2() + recluta.getRes3() +
                recluta.getRes4() + recluta.getRes5() + recluta.getRes6() +
                recluta.getRes7() + recluta.getRes8();

        // El perfil define su ESTADO directamente según el puntaje (24 puntos o más de 32 posibles)
        String estadoFinal = (puntajeTotal >= 24) ? "PENDIENTE EN EVALUACION" : "RECHAZADO";

        // SQL usando WHERE NOT EXISTS guiado por el DNI único
        String sql = "INSERT INTO preg_recluta (dni, nombre, edad, res1, res2, res3, res4, res5, res6, res7, res8, ubicacion, estado, id_evento) " +
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
                estadoFinal,
                recluta.getId_evento(), // Captura el id del select de postular.jsp
                recluta.getDni()
        );

        // COMPROBACIÓN CRÍTICA: Solo operar bitácora si la inserción fue real
        if (filasAfectadas > 0) {
            try {
                // Obtener el ID generado de este nuevo recluta para enlazarlo en la bitácora
                String sqlId = "SELECT id FROM preg_recluta WHERE dni = ?";
                Integer nuevoIdRecluta = jdbcTemplate.queryForObject(sqlId, Integer.class, recluta.getDni());

                // Insertar en la bitácora de auditoría
                String sqlBitacora = "INSERT INTO bitacora (id_usuario, id_recluta, accion, fecha_registro) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
                jdbcTemplate.update(sqlBitacora, null, nuevoIdRecluta, "El postulante completó el formulario de reclutamiento de manera exitosa.");
            } catch (Exception e) {
                // Log opcional del error en consola para que no tumbe la app si la bitácora falla
                System.out.println("Error al registrar en bitácora: " + e.getMessage());
            }

            return "redirect:/postular?exito=true";
        } else {
            // Si no hubo filas afectadas, significa con seguridad que el DNI ya existía
            return "redirect:/postular?error=duplicado";
        }
    }

    // 4. ACTUALIZAR ESTADO DEL POSTULANTE DESDE EL PROCESAMIENTO
    @GetMapping("/crudpostulantes/estado/{id}/{nuevoEstado}")
    public String actualizarEstado(@PathVariable int id, @PathVariable String nuevoEstado) {
        String estadoDb = "";
        String accionBitacora = "";
        String sqlBuscar = "SELECT nombre FROM preg_recluta WHERE id = ?";
        String nombreRecluta = jdbcTemplate.queryForObject(sqlBuscar, String.class, id);

        switch (nuevoEstado) {
            case "entrevista":
                estadoDb = "ENTREVISTA";
                accionBitacora = "Se cambió el estado del postulante '" + nombreRecluta + "' a ENTREVISTA.";
                break;
            case "rechazar":
                estadoDb = "RECHAZADO";
                accionBitacora = "Se cambió el estado del postulante '" + nombreRecluta + "' a RECHAZADO.";
                break;
            case "aprobar":
                estadoDb = "APROBADO";
                accionBitacora = "Se cambió el estado del postulante '" + nombreRecluta + "' a APROBADO.";
                break;
            default:
                estadoDb = "PENDIENTE";
                accionBitacora = "Se cambió el estado del postulante '" + nombreRecluta + "' a PENDIENTE.";
                break;
        }

        String sql = "UPDATE preg_recluta SET estado = ? WHERE id = ?";
        jdbcTemplate.update(sql, estadoDb, id);

        // CORREGIDO: Se quitó el 'null' intermedio redundante que duplicaba los parámetros
        String sqlBitacora = "INSERT INTO bitacora (id_usuario, id_recluta, accion, fecha_registro) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sqlBitacora, null, id, accionBitacora);

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
            model.addAttribute("postulante", lista.get(0));
            return "resultado-estado";
        } else {
            model.addAttribute("error", "No se encontró ninguna postulación con este DNI.");
            return "consultar-estado";
        }
    }
}