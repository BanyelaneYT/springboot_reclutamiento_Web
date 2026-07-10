package com.example.demo.Controller;

import com.example.demo.Service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/categorias")
    public String listar(Model model) {
        model.addAttribute("listaCategorias", categoriaService.listarCatalogo());
        return "categorias-crud";
    }

    @PostMapping("/categorias/guardar")
    public String guardar(@RequestParam String nombre,
                          @RequestParam(required = false, defaultValue = "") String descripcion,
                          @RequestParam int estado) {
        categoriaService.guardarCategoria(nombre, descripcion, estado);
        return "redirect:/categorias";
    }

    @PostMapping("/categorias/actualizar")
    public String actualizar(@RequestParam int id, @RequestParam String nombre,
                             @RequestParam(required = false, defaultValue = "") String descripcion,
                             @RequestParam int estado) {
        categoriaService.actualizarCategoria(id, nombre, descripcion, estado);
        return "redirect:/categorias";
    }

    @PostMapping("/categorias/cambiar-estado")
    public String cambiarEstado(@RequestParam int id, @RequestParam int estado) {
        if (estado == 0 && categoriaService.contarPuestosPorCategoria(id) > 0) {
            return "redirect:/categorias?error=EN_USO";
        }
        categoriaService.cambiarEstado(id, estado);
        return "redirect:/categorias";
    }
}
