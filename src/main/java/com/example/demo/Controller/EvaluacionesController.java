package com.example.demo.Controller;

import com.example.demo.Service.BitacoraService;
import com.example.demo.Service.CitasEntrevistaService;
import com.example.demo.Service.PostulanteEvaService;
import com.example.demo.model.PostulanteEva;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EvaluacionesController {

    private final PostulanteEvaService postulanteEvaService;
    private final CitasEntrevistaService citasEntrevistaService;
    private final BitacoraService bitacoraService;

    public EvaluacionesController(PostulanteEvaService postulanteEvaService,
                                  CitasEntrevistaService citasEntrevistaService,
                                  BitacoraService bitacoraService) {
        this.postulanteEvaService = postulanteEvaService;
        this.citasEntrevistaService = citasEntrevistaService;
        this.bitacoraService = bitacoraService;
    }

    @GetMapping("/evaluaciones")
    public String listar(@RequestParam(required = false) Integer agendarCita,
                         @RequestParam(required = false) Integer editarEvaluacion,
                         Model model) {
        model.addAttribute("listaEvaluaciones", postulanteEvaService.listarPostulantes());

        if (agendarCita != null) {
            model.addAttribute("mostrarAgendarCita", true);
            model.addAttribute("idUserCita", agendarCita);
            PostulanteEva eva = postulanteEvaService.obtenerUltimaPorUsuario(agendarCita);
            if (eva != null) {
                model.addAttribute("nombreUsuarioCita", eva.getNombreUsuario());
            }
        }

        if (editarEvaluacion != null) {
            PostulanteEva eva = postulanteEvaService.obtenerPostulantePorId(editarEvaluacion);
            if (eva != null) {
                model.addAttribute("mostrarEditarEvaluacion", true);
                model.addAttribute("evaluacion", eva);
            }
        }

        return "evaluaciones-list";
    }

    @GetMapping({"/evaluaciones/puesto", "/evaluaciones/usuario"})
    public String listarFiltrado(@RequestParam(required = false) Integer idPuesto,
                                 @RequestParam(required = false) Integer idUser,
                                 Model model) {
        if (idPuesto != null) {
            model.addAttribute("listaEvaluaciones", postulanteEvaService.obtenerPostulantesPorPuesto(idPuesto));
        } else if (idUser != null) {
            model.addAttribute("listaEvaluaciones", postulanteEvaService.obtenerPostulantesPorUsuario(idUser));
        } else {
            model.addAttribute("listaEvaluaciones", postulanteEvaService.listarPostulantes());
        }
        return "evaluaciones-list";
    }

    @PostMapping("/evaluaciones/guardar")
    public String guardar(@RequestParam int idUser, @RequestParam int idPuesto,
                          @RequestParam int puntaje, @RequestParam String descripcion,
                          @RequestParam String estado,
                          @RequestParam(defaultValue = "0") int idCita) {
        postulanteEvaService.guardarPostulante(idUser, idPuesto, puntaje, descripcion, estado, idCita);
        bitacoraService.registrarAccion(idUser, "Registro de evaluación para postulante ID " + idUser);
        return "redirect:/evaluaciones";
    }

    @PostMapping({"/evaluaciones/actualizar", "/evaluaciones/actualizar-puntaje"})
    public String actualizar(@RequestParam int id, @RequestParam int puntaje,
                             @RequestParam(required = false) String descripcion) {
        int idUser = descripcion != null
                ? postulanteEvaService.actualizarEvaluacion(id, puntaje, descripcion)
                : actualizarSoloPuntaje(id, puntaje);
        if (idUser > 0) {
            String msg = descripcion != null
                    ? "Actualización de evaluación ID " + id + " (puntaje/descripcion)"
                    : "Actualizado puntaje a " + puntaje + " para evaluación ID " + id;
            bitacoraService.registrarAccion(idUser, msg);
        }
        return "redirect:/evaluaciones";
    }

    @PostMapping("/evaluaciones/agendar-cita")
    public String agendarCita(@RequestParam int idUser,
                              @RequestParam String linkMeet,
                              @RequestParam String fechaHora) {
        try {
            int citaId = citasEntrevistaService.guardarCita(idUser, linkMeet, fechaHora);
            postulanteEvaService.vincularEntrevista(idUser, citaId);
            bitacoraService.registrarAccion(idUser, "Cita de entrevista agendada para postulante ID " + idUser);
            return "redirect:/evaluaciones";
        } catch (Exception ex) {
            bitacoraService.registrarAccion(idUser, "Error agendando cita: " + ex.getMessage());
            return "redirect:/evaluaciones?error=true";
        }
    }

    @GetMapping("/evaluaciones/aprobar/{id}")
    public String aprobar(@PathVariable int id) {
        return cambiarEstadoEvaluacion(id, "APROBADO", "Aprobado");
    }

    @GetMapping("/evaluaciones/rechazar/{id}")
    public String rechazar(@PathVariable int id) {
        return cambiarEstadoEvaluacion(id, "RECHAZADO", "Rechazado");
    }

    @GetMapping("/evaluaciones/eliminar")
    public String eliminar(@RequestParam int id) {
        PostulanteEva eva = postulanteEvaService.obtenerPostulantePorId(id);
        postulanteEvaService.eliminarPostulante(id);
        if (eva != null) {
            bitacoraService.registrarAccion(eva.getIdUser(),
                    "Eliminación de evaluación ID " + id + " para postulante ID " + eva.getIdUser());
        }
        return "redirect:/evaluaciones";
    }

    private int actualizarSoloPuntaje(int id, int puntaje) {
        PostulanteEva eva = postulanteEvaService.obtenerPostulantePorId(id);
        if (eva == null) {
            return -1;
        }
        return postulanteEvaService.actualizarEvaluacion(id, puntaje, eva.getDescripcion());
    }

    private String cambiarEstadoEvaluacion(int id, String estado, String accion) {
        int idUser = postulanteEvaService.cambiarEstado(id, estado);
        if (idUser > 0) {
            bitacoraService.registrarAccion(idUser, accion + " postulante ID " + idUser);
        }
        return "redirect:/evaluaciones";
    }
}
