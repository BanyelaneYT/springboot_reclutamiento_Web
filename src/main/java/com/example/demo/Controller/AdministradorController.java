
//@CODEX

package com.example.demo.Controller;

import com.example.demo.model.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private JdbcTemplate jdbcTemplate;

    //Usuarios
    @GetMapping("/usuarios")
    public String usuario(Model model) {
        String sql = "SELECT * FROM usuarios";
        List<Administrador> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Administrador>(Administrador.class));

        model.addAttribute("listaUsuarios", lista);
        return "usuarios-crud";

    }
    //ACTUALIZAR (UPDATE)
    @PostMapping("/usuarios/actualizar")
    public String actualizar(@RequestParam int id, @RequestParam String correo,
                             @RequestParam String contrasena) {
        String sql = "UPDATE administradores SET correo=?, contrasena=? WHERE id=?";
        jdbcTemplate.update(sql, correo, contrasena, id);
        return "redirect:/usuarios";
    }
    //ELIMINAR (DELETE)
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        String sql = "DELETE from administradores WHERE id=?";
        jdbcTemplate.update(sql, id);
        return "redirect:/usuarios";
    }

    //INSERT (SQL para insertar, el ID no se pone porque es AUTO_INCREMENT en la BD)
    @PostMapping("/usuarios/guardar")
    public String guardar(@RequestParam String correo,
                          @RequestParam String contrasena){
        String sql = "INSERT INTO administradores (correo, contrasena) VALUES (?, ?)";
        jdbcTemplate.update(sql, correo, contrasena);
        return "redirect:/usuarios";
    }
}
