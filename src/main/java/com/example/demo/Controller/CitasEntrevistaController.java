package com.example.demo.Controller;

import com.example.demo.Service.CitasEntrevistaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CitasEntrevistaController {

    private final CitasEntrevistaService citasEntrevistaService;

    public CitasEntrevistaController(CitasEntrevistaService citasEntrevistaService) {
        this.citasEntrevistaService = citasEntrevistaService;
    }

    @GetMapping("/citas")
    public String listar(Model model) {
        model.addAttribute("listaCitas", citasEntrevistaService.listarCitas());
        return "citas-list";
    }

    @GetMapping("/citas/usuario")
    public String listarPorUsuario(@RequestParam int idUser, Model model) {
        model.addAttribute("listaCitas", citasEntrevistaService.obtenerCitasPorUsuario(idUser));
        return "citas-list";
    }

    @PostMapping("/citas/guardar")
    public String guardar(@RequestParam int idUser, @RequestParam String linkMeet,
                            @RequestParam String fechaHoraEntrevista) {
        citasEntrevistaService.guardarCita(idUser, linkMeet, fechaHoraEntrevista);
        return "redirect:/citas";
    }

    @PostMapping("/citas/actualizar")
    public String actualizar(@RequestParam int id, @RequestParam String linkMeet,
                             @RequestParam String fechaHoraEntrevista) {
        citasEntrevistaService.actualizarCita(id, linkMeet, fechaHoraEntrevista);
        return "redirect:/citas";
    }

    @GetMapping("/citas/eliminar")
    public String eliminar(@RequestParam int id) {
        citasEntrevistaService.eliminarCita(id);
        return "redirect:/citas";
    }
}
