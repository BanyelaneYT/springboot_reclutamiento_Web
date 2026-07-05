package com.example.demo.Controller;

import com.example.demo.Service.AdministradorService;
import com.example.demo.Service.BitacoraService;
import com.example.demo.Service.CategoriaPuestosService;
import com.example.demo.Service.ReclutaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    private final AdministradorService administradorService;
    private final CategoriaPuestosService categoriaPuestosService;
    private final BitacoraService bitacoraService;
    private final ReclutaService reclutaService;

    public ViewController(AdministradorService administradorService,
                          CategoriaPuestosService categoriaPuestosService,
                          BitacoraService bitacoraService,
                          ReclutaService reclutaService) {
        this.administradorService = administradorService;
        this.categoriaPuestosService = categoriaPuestosService;
        this.bitacoraService = bitacoraService;
        this.reclutaService = reclutaService;
    }

    @GetMapping({"/", "/main"})
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String contrasena,
                                Model model) {
        if (administradorService.autenticar(correo, contrasena)) {
            return "redirect:/gestion";
        }
        model.addAttribute("error", "Correo electrónico o contraseña incorrectos.");
        return "login";
    }

    @GetMapping("/publicidad")
    public String publicidad() {
        return "publicidad";
    }

    @GetMapping("/gestion")
    public String gestion() {
        return "gestion";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/metricas")
    public String metricas() {
        return "metricas";
    }

    @GetMapping("/postular")
    public String postular(@RequestParam(required = false) Integer puestoId, Model model) {
        model.addAttribute("listaCatalogo", categoriaPuestosService.listarActivos());
        model.addAttribute("puestoSeleccionadoId", puestoId);
        return "postular";
    }

    @PostMapping("/postular/guardar")
    public String guardarPostulacion(@RequestParam int dni,
                                     @RequestParam String nombre,
                                     @RequestParam int edad,
                                     @RequestParam("id_puesto") int puesto) {
        try {
            Integer idRecluta = reclutaService.registrarPostulante(dni, nombre, edad, puesto);
            if (idRecluta != null) {
                bitacoraService.registrarAccion(idRecluta, "Nueva postulación registrada para " + nombre);
            }
            return "redirect:/login?exitoPostulacion=true";
        } catch (Exception e) {
            return "redirect:/postular?puestoId=" + puesto + "&error=true";
        }
    }

    @GetMapping("/evento")
    public String evento(Model model) {
        model.addAttribute("listaCatalogo", categoriaPuestosService.listarCatalogo());
        return "puestos-vist";
    }
}
