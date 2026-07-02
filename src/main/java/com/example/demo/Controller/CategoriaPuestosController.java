package com.example.demo.Controller;

import com.example.demo.Service.CategoriaPuestosService;
import com.example.demo.model.CategoriaPuestos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoriaPuestosController {

    @Autowired
    private CategoriaPuestosService categoriaPuestosService;

    // LISTAR TODOS LOS PUESTOS (Para el panel de administración)
    @GetMapping("/catalogo")
    public String listar(Model model) {
        List<CategoriaPuestos> lista = categoriaPuestosService.listarCatalogo();

        model.addAttribute("listaCatalogo", lista);
        return "catalogo-crud"; // Corregido: Retorna directamente el nombre del archivo sin "/"
    }

    // ACTUALIZAR PUESTO EXISTENTE
    @PostMapping("/catalogo/actualizar")
    public String actualizar(@RequestParam int id,
                             @RequestParam String nombre,
                             @RequestParam String tipo,
                             @RequestParam String descripcion,
                             @RequestParam String presRem,
                             @RequestParam String horario,
                             @RequestParam int estado,
                             @RequestParam int pago) {
        categoriaPuestosService.actualizarPuesto(id, nombre, tipo, descripcion, presRem, horario, estado, pago);
        return "redirect:/catalogo";
    }

    // ELIMINAR PUESTO POR ID
    @GetMapping("/catalogo/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        categoriaPuestosService.eliminarPuesto(id);
        return "redirect:/catalogo";
    }

    // REGISTRAR NUEVO PUESTO DE TRABAJO
    @PostMapping("/catalogo/guardar")
    public String guardar(@RequestParam String nombre,
                          @RequestParam String tipo,
                          @RequestParam String descripcion,
                          @RequestParam String presRem,
                          @RequestParam String horario,
                          @RequestParam int estado,
                          @RequestParam int pago) {
        categoriaPuestosService.guardarPuesto(nombre, tipo, descripcion, presRem, horario, estado, pago);
        return "redirect:/catalogo";
    }
}