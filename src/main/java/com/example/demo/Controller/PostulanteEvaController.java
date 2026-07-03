package com.example.demo.Controller;

import com.example.demo.Service.BitacoraService;
import com.example.demo.Service.PostulanteEvaService;
import com.example.demo.model.PostulanteEva;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostulanteEvaController {

    private final PostulanteEvaService postulanteEvaService;
    private final BitacoraService bitacoraService;

    public PostulanteEvaController(PostulanteEvaService postulanteEvaService, BitacoraService bitacoraService) {
        this.postulanteEvaService = postulanteEvaService;
        this.bitacoraService = bitacoraService;
    }

    // GUARDAR NUEVA EVALUACION
    @PostMapping("/evaluaciones/guardar")
    public String guardarEvaluacion(@RequestParam int idUser,
                                    @RequestParam int idPuesto,
                                    @RequestParam int puntaje,
                                    @RequestParam String descripcion,
                                    @RequestParam String estado,
                                    @RequestParam(required = false, defaultValue = "0") int idCita) {
        postulanteEvaService.guardarPostulante(idUser, idPuesto, puntaje, descripcion, estado, idCita);
        bitacoraService.registrarBitacora(1, idUser, "Registro de evaluación para postulante ID " + idUser);
        return "redirect:/evaluaciones";
    }

    // ACTUALIZAR EVALUACION (solo puntaje y descripción; estado permanece)
    @PostMapping("/evaluaciones/actualizar")
    public String actualizarEvaluacion(@RequestParam int id,
                                       @RequestParam int puntaje,
                                       @RequestParam String descripcion) {
        PostulanteEva eva = postulanteEvaService.obtenerPostulantePorId(id);
        if (eva != null) {
            // preservar idPuesto, estado e idCita
            postulanteEvaService.actualizarPostulante(id, eva.getIdPuesto(), puntaje, descripcion, eva.getEstado(), eva.getIdCita());
            bitacoraService.registrarBitacora(1, eva.getIdUser(), "Actualización de evaluación ID " + id + " (puntaje/descripcion)");
        }
        return "redirect:/evaluaciones";
    }

    // ELIMINAR EVALUACION
    @GetMapping("/evaluaciones/eliminar")
    public String eliminarEvaluacion(@RequestParam int id) {
        PostulanteEva eva = postulanteEvaService.obtenerPostulantePorId(id);
        postulanteEvaService.eliminarPostulante(id);
        if (eva != null) {
            bitacoraService.registrarBitacora(1, eva.getIdUser(), "Eliminación de evaluación ID " + id + " para postulante ID " + eva.getIdUser());
        }
        return "redirect:/evaluaciones";
    }

    // OBTENER EVALUACIONES POR PUESTO
    @GetMapping("/evaluaciones/puesto")
    public String obtenerEvaluacionesPorPuesto(@RequestParam int idPuesto, Model model) {
        List<PostulanteEva> lista = postulanteEvaService.obtenerPostulantesPorPuesto(idPuesto);
        model.addAttribute("listaEvaluaciones", lista);
        return "evaluaciones-list";
    }

    // OBTENER EVALUACIONES POR USUARIO
    @GetMapping("/evaluaciones/usuario")
    public String obtenerEvaluacionesPorUsuario(@RequestParam int idUser, Model model) {
        List<PostulanteEva> lista = postulanteEvaService.obtenerPostulantesPorUsuario(idUser);
        model.addAttribute("listaEvaluaciones", lista);
        return "evaluaciones-list";
    }
}
