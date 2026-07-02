package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.ReclutaService;
import com.example.demo.model.UserInf;

@Controller
public class ReclutaController {

    @Autowired
    private ReclutaService reclutaService;

    @GetMapping("/crudpostulantes")
    public String listarPostulantes(
            @RequestParam(value = "citarId", required = false) Integer citarId,
            Model model) {

        try {
            List<UserInf> lista = reclutaService.listarPostulantes();
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
            reclutaService.agendarCita(idUser, linkMeet, fechaHora);

            return "redirect:/crudpostulantes?success=citaAgendada";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/crudpostulantes?error=errorGeneral";
        }
    }

    @GetMapping("/crudpostulantes/estado/{id}/{accion}")
    public String cambiarEstado(@PathVariable int id, @PathVariable String accion) {
        reclutaService.cambiarEstado(id, accion);
        return "redirect:/crudpostulantes";
    }

    @GetMapping("/crudpostulantes/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        reclutaService.eliminar(id);
        return "redirect:/crudpostulantes";
    }


    @GetMapping("/consultar-estado")
    public String mostrarConsultaEstado() { return "consultar-estado"; }

    @PostMapping("/consultar-estado")
    public String procesarConsultaEstado(@RequestParam("dni") int dni, Model model) {
        List<Map<String, Object>> lista = reclutaService.consultarEstadoPorDni(dni);

        if (!lista.isEmpty()) {
            model.addAttribute("postulante", lista.get(0));
            return "resultado-estado";
        } else {
            model.addAttribute("error", "No se encontró ninguna postulación con el DNI ingresado.");
            return "consultar-estado";
        }
    }
}