package com.example.demo.Controller;

import com.example.demo.Service.CategoriaPuestosService;
import com.example.demo.Service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoriaPuestosController {

    private final CategoriaPuestosService categoriaPuestosService;
    private final CategoriaService categoriaService;

    public CategoriaPuestosController(CategoriaPuestosService categoriaPuestosService,
                                      CategoriaService categoriaService) {
        this.categoriaPuestosService = categoriaPuestosService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/categoria")
    public String listar(Model model) {
        model.addAttribute("listaCategorias", categoriaPuestosService.listarCatalogo());
        model.addAttribute("listaCategoriasDisponibles", categoriaService.listarActivas());
        return "categoria-crud";
    }

    @PostMapping("/categoria/actualizar")
    public String actualizar(@RequestParam int id, @RequestParam String nombre, @RequestParam int idCategoria,
                             @RequestParam String descripcion, @RequestParam String presRem,
                             @RequestParam String horario, @RequestParam int estado, @RequestParam int pago) {
        categoriaPuestosService.actualizarPuesto(id, nombre, idCategoria, descripcion, presRem, horario, estado, pago);
        return "redirect:/categoria";
    }

    @PostMapping("/categoria/guardar")
    public String guardar(@RequestParam String nombre, @RequestParam int idCategoria, @RequestParam String descripcion,
                          @RequestParam String presRem, @RequestParam String horario,
                          @RequestParam int estado, @RequestParam int pago) {
        categoriaPuestosService.guardarPuesto(nombre, idCategoria, descripcion, presRem, horario, estado, pago);
        return "redirect:/categoria";
    }

    @PostMapping("/categoria/cambiar-estado")
    public String cambiarEstado(@RequestParam int id, @RequestParam int estado) {
        try {
            categoriaPuestosService.cambiarEstado(id, estado);
            return "redirect:/categoria";
        } catch (RuntimeException e) {
            if ("CATEGORIA_INACTIVA".equals(e.getMessage())) {
                return "redirect:/categoria?error=CAT_INACTIVA";
            }
            throw e;
        }
    }
}
