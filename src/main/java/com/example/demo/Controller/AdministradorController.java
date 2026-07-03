
//@CODEX

package com.example.demo.Controller;

import com.example.demo.Service.AdministradorService;
import com.example.demo.model.Administrador;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    //Usuarios
    @GetMapping("/usuarios")
    public String usuario(Model model) {
        List<Administrador> lista = administradorService.listarUsuarios();

        model.addAttribute("listaUsuarios", lista);
        return "usuarios-crud";
    }
    //ACTUALIZAR (UPDATE)
    @PostMapping("/usuarios/actualizar")
    public String actualizar(@RequestParam int id, @RequestParam String correo,
                             @RequestParam String contrasena) {
        administradorService.actualizarUsuario(id, correo, contrasena);
        return "redirect:/usuarios";
    }
    //INSERT (SQL para insertar, el ID no se pone porque es AUTO_INCREMENT en la BD)
    @PostMapping("/usuarios/guardar")
    public String guardar(@RequestParam String correo,
                          @RequestParam String contrasena){
        administradorService.guardarUsuario(correo, contrasena);
        return "redirect:/usuarios";
    }
}
