package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String irMain() {
        return "main";
    }
    @GetMapping("/publicidad") //@CODEX
    public String mostrarPublicidad() {
        return "publicidad";
    }

    @GetMapping("/gestion")
    public String irGestion() {
        // Retorna el nombre del archivo JSP: gestion.jsp
        return "gestion";
    }
    @GetMapping("/contacto")
    public String mostrarFormulario() {
        return "contacto";
    }
}
