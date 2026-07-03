package com.example.demo.Controller;

import com.example.demo.Service.CitasEntrevistaService;
import com.example.demo.model.CitasEntrevista;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CitasEntrevistaController {

    private final CitasEntrevistaService citasEntrevistaService;

    public CitasEntrevistaController(CitasEntrevistaService citasEntrevistaService) {
        this.citasEntrevistaService = citasEntrevistaService;
    }

    // LISTAR TODAS LAS CITAS
    @GetMapping("/citas")
    public String listarCitas(Model model) {
        List<CitasEntrevista> lista = citasEntrevistaService.listarCitas();
        model.addAttribute("listaCitas", lista);
        return "citas-list";
    }

    // GUARDAR NUEVA CITA
    @PostMapping("/citas/guardar")
    public String guardarCita(@RequestParam int idUser,
                              @RequestParam String linkMeet,
                              @RequestParam String fechaHoraEntrevista) {
        citasEntrevistaService.guardarCita(idUser, linkMeet, fechaHoraEntrevista);
        return "redirect:/citas";
    }

    // ACTUALIZAR CITA
    @PostMapping("/citas/actualizar")
    public String actualizarCita(@RequestParam int id,
                                 @RequestParam String linkMeet,
                                 @RequestParam String fechaHoraEntrevista) {
        citasEntrevistaService.actualizarCita(id, linkMeet, fechaHoraEntrevista);
        return "redirect:/citas";
    }

    // ELIMINAR CITA
    @GetMapping("/citas/eliminar")
    public String eliminarCita(@RequestParam int id) {
        citasEntrevistaService.eliminarCita(id);
        return "redirect:/citas";
    }

    // OBTENER CITAS POR USUARIO
    @GetMapping("/citas/usuario")
    public String obtenerCitasPorUsuario(@RequestParam int idUser, Model model) {
        List<CitasEntrevista> lista = citasEntrevistaService.obtenerCitasPorUsuario(idUser);
        model.addAttribute("listaCitas", lista);
        return "citas-list";
    }
}
