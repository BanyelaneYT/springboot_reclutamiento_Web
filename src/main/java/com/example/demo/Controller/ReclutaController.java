package com.example.demo.Controller;



import com.example.demo.Service.CategoriaPuestosService;

import com.example.demo.Service.ReclutaService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;



import java.util.List;
import java.util.Map;



@Controller

public class ReclutaController {

    private final ReclutaService reclutaService;

    private final CategoriaPuestosService categoriaPuestosService;

    public ReclutaController(ReclutaService reclutaService,
                             CategoriaPuestosService categoriaPuestosService) {
        this.reclutaService = reclutaService;
        this.categoriaPuestosService = categoriaPuestosService;
    }



    @GetMapping("/crudpostulantes")

    public String listar(@RequestParam(required = false) Integer puestoId,
                         @RequestParam(required = false) String resultado,
                         Model model) {
        model.addAttribute("listaPostulantes", reclutaService.listarPostulantes(puestoId, resultado));
        model.addAttribute("listaPuestos", categoriaPuestosService.listarCatalogo());
        model.addAttribute("puestoSeleccionado", puestoId);
        model.addAttribute("resultadoSeleccionado", resultado);
        return "crudpostulantes";
    }

    @GetMapping("/consultar-estado")
    public String mostrarConsultaEstado() {
        return "consultar-estado";
    }

    @PostMapping("/consultar-estado")
    public String procesarConsultaEstado(@RequestParam int dni, Model model) {
        List<Map<String, Object>> postulaciones = reclutaService.buscarPostulacionesPorDni(dni);
        if (!postulaciones.isEmpty()) {
            model.addAttribute("postulante", postulaciones.get(0));
            model.addAttribute("postulaciones", postulaciones);
            return "resultado-estado";
        }
        model.addAttribute("error", "No se encontró ninguna postulación con el DNI ingresado.");
        return "consultar-estado";
    }
}


