package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.AdministradorService;

@Controller
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping("/administradores")
    public String listar(Model model) {
        model.addAttribute("listaUsuarios", administradorService.listarUsuarios());
        return "usuarios-crud";
    }

    @PostMapping("/administradores/actualizar")
    public String actualizar(@RequestParam int id, @RequestParam String correo, @RequestParam String contrasena) {
        administradorService.actualizarUsuario(id, correo, contrasena);
        return "redirect:/administradores";
    }

    @PostMapping("/administradores/guardar")
    public String guardar(@RequestParam String correo, @RequestParam String contrasena) {
        administradorService.guardarUsuario(correo, contrasena);
        return "redirect:/administradores";
    }
}
