package com.example.demo.Controller;

import com.example.demo.Service.BitacoraService;
import com.example.demo.Service.PostulanteEvaService;
import com.example.demo.Service.CitasEntrevistaService;
import com.example.demo.model.PostulanteEva;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EvaluacionesController {

    private final PostulanteEvaService postulanteEvaService;
    private final CitasEntrevistaService citasEntrevistaService;
    private final BitacoraService bitacoraService;

    public EvaluacionesController(PostulanteEvaService postulanteEvaService, CitasEntrevistaService citasEntrevistaService, BitacoraService bitacoraService) {
        this.postulanteEvaService = postulanteEvaService;
        this.citasEntrevistaService = citasEntrevistaService;
        this.bitacoraService = bitacoraService;
    }

    // LISTAR EVALUACIONES
    @GetMapping("/evaluaciones")
    public String listarEvaluaciones(
            @RequestParam(value = "agendarCita", required = false) Integer agendarCitaId,
            @RequestParam(value = "editarEvaluacion", required = false) Integer editarEvaluacionId,
            Model model) {
        List<PostulanteEva> lista = postulanteEvaService.listarPostulantes();
        model.addAttribute("listaEvaluaciones", lista);

        if (agendarCitaId != null) {
            model.addAttribute("mostrarAgendarCita", true);
            model.addAttribute("idUserCita", agendarCitaId);
            List<PostulanteEva> postulantes = postulanteEvaService.obtenerPostulantesPorUsuario(agendarCitaId);
            if (!postulantes.isEmpty()) {
                model.addAttribute("nombreUsuarioCita", postulantes.get(0).getNombreUsuario());
            }
        }

        if (editarEvaluacionId != null) {
            PostulanteEva eva = postulanteEvaService.obtenerPostulantePorId(editarEvaluacionId);
            if (eva != null) {
                model.addAttribute("mostrarEditarEvaluacion", true);
                model.addAttribute("evaluacion", eva);
            }
        }

        return "evaluaciones-list";
    }

    // GUARDAR EVALUACION RÁPIDAMENTE SOLO PUNTAJE
    @PostMapping("/evaluaciones/actualizar-puntaje")
    public String actualizarPuntaje(@RequestParam int id,
                                    @RequestParam int puntaje) {
        PostulanteEva eva = postulanteEvaService.obtenerPostulantePorId(id);
        if (eva != null) {
            postulanteEvaService.actualizarPostulante(id, eva.getIdPuesto(), puntaje, eva.getDescripcion(), eva.getEstado(), eva.getIdCita());
            bitacoraService.registrarBitacora(1, eva.getIdUser(), "Actualizado puntaje a " + puntaje + " para evaluación ID " + id);
        }
        return "redirect:/evaluaciones";
    }

    // AGENDAR CITA
    @PostMapping("/evaluaciones/agendar-cita")
    public String agendarCita(@RequestParam(required = false) String idUser,
                              @RequestParam(required = false) String linkMeet,
                              @RequestParam(required = false) String fechaHora) {
        int idUserInt = -1;
        try {
            if (idUser == null || idUser.trim().isEmpty()) {
                throw new IllegalArgumentException("idUser vacío");
            }
            idUserInt = Integer.parseInt(idUser);
        } catch (Exception exParse) {
            exParse.printStackTrace();
            try {
                bitacoraService.registrarBitacora(1, -1, "Error parseando idUser al agendar cita: " + exParse.getMessage());
            } catch (Exception ignore) {}
            return "redirect:/evaluaciones?error=true";
        }

        try {
            int citaId = citasEntrevistaService.guardarCita(idUserInt, linkMeet, fechaHora);
            PostulanteEva eva = postulanteEvaService.obtenerPostulantesPorUsuario(idUserInt).isEmpty()
                ? null
                : postulanteEvaService.obtenerPostulantesPorUsuario(idUserInt).get(0);
            if (eva != null && citaId > 0) {
                postulanteEvaService.actualizarPostulante(eva.getId(), eva.getIdPuesto(), eva.getPuntaje(), eva.getDescripcion(), "ENTREVISTA", citaId);
            }
            bitacoraService.registrarBitacora(1, idUserInt, "Cita de entrevista agendada para postulante ID " + idUserInt);
            return "redirect:/evaluaciones";
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                bitacoraService.registrarBitacora(1, idUserInt, "Error agendando cita: " + ex.getMessage());
            } catch (Exception e) {}
            return "redirect:/evaluaciones?error=true";
        }
    }

    // APROBAR POSTULANTE
    @GetMapping("/evaluaciones/aprobar/{id}")
    public String aprobarPostulante(@PathVariable int id) {
        PostulanteEva eva = postulanteEvaService.obtenerPostulantePorId(id);

        if (eva != null) {
            postulanteEvaService.actualizarPostulante(eva.getId(), eva.getIdPuesto(), eva.getPuntaje(),
                eva.getDescripcion(), "APROBADO", eva.getIdCita());
            bitacoraService.registrarBitacora(1, eva.getIdUser(), "Aprobado postulante ID " + eva.getIdUser());
        }
        return "redirect:/evaluaciones";
    }

    // RECHAZAR POSTULANTE
    @GetMapping("/evaluaciones/rechazar/{id}")
    public String rechazarPostulante(@PathVariable int id) {
        PostulanteEva eva = postulanteEvaService.obtenerPostulantePorId(id);

        if (eva != null) {
            postulanteEvaService.actualizarPostulante(eva.getId(), eva.getIdPuesto(), eva.getPuntaje(),
                eva.getDescripcion(), "RECHAZADO", eva.getIdCita());
            bitacoraService.registrarBitacora(1, eva.getIdUser(), "Rechazado postulante ID " + eva.getIdUser());
        }
        return "redirect:/evaluaciones";
    }
}
