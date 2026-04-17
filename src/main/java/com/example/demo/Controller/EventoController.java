package com.example.demo.Controller;

import com.example.demo.model.Evento;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EventoController {

    private static List<Evento> bdEventos = new ArrayList<>();

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String irMain(Model model) {
        model.addAttribute("listaEventos", bdEventos);
        return "main";
    }

    @GetMapping("/Crudcreacion")
    public String listar(Model model) {
        model.addAttribute("listaEventos", bdEventos);
        return "main";
    }

    @PostMapping("/eventos/guardar")
    public String guardar(@RequestParam String nombre,
                          @RequestParam String tipo,
                          @RequestParam String fecha,
                          @RequestParam String descripcion) {

        int nuevoId = bdEventos.size() + 1;
        bdEventos.add(new Evento(nuevoId, nombre, tipo, fecha, descripcion));

        return "redirect:/main";
    }
}