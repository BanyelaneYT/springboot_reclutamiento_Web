
//@CODEX

package com.example.demo.Controller;

import com.example.demo.Service.AdministradorService;
import com.example.demo.model.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

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
    //ELIMINAR (DELETE)
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        administradorService.eliminarUsuario(id);
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
