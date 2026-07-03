package com.example.demo.Controller;

import com.example.demo.Service.BitacoraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BitacoraController {

    private final BitacoraService bitacoraService;

    public BitacoraController(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    @GetMapping("/bitacora")
    public String listar(Model model) {
        model.addAttribute("listaBitacora", bitacoraService.listarBitacora());
        return "bitacora-list";
    }
}
