package com.example.demo.Controller;

import com.example.demo.Service.CategoriaPuestosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoriaPuestosController {

    private final CategoriaPuestosService categoriaPuestosService;

    public CategoriaPuestosController(CategoriaPuestosService categoriaPuestosService) {
        this.categoriaPuestosService = categoriaPuestosService;
    }

    @GetMapping("/catalogo")
    public String listar(Model model) {
        model.addAttribute("listaCatalogo", categoriaPuestosService.listarCatalogo());
        return "catalogo-crud";
    }

    @PostMapping("/catalogo/actualizar")
    public String actualizar(@RequestParam int id, @RequestParam String nombre, @RequestParam String tipo,
                             @RequestParam String descripcion, @RequestParam String presRem,
                             @RequestParam String horario, @RequestParam int estado, @RequestParam int pago) {
        categoriaPuestosService.actualizarPuesto(id, nombre, tipo, descripcion, presRem, horario, estado, pago);
        return "redirect:/catalogo";
    }

    @GetMapping("/catalogo/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        return "redirect:/catalogo";
    }

    @PostMapping("/catalogo/guardar")
    public String guardar(@RequestParam String nombre, @RequestParam String tipo, @RequestParam String descripcion,
                          @RequestParam String presRem, @RequestParam String horario,
                          @RequestParam int estado, @RequestParam int pago) {
        categoriaPuestosService.guardarPuesto(nombre, tipo, descripcion, presRem, horario, estado, pago);
        return "redirect:/catalogo";
    }
}
