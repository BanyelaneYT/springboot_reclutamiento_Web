package com.example.demo.Controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Service.BitacoraService;

@Controller
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @GetMapping("/bitacora")
    public String listarBitacora(Model model) {
        List<Map<String, Object>> listaHistorial = bitacoraService.listarBitacora();
        model.addAttribute("listaBitacora", listaHistorial);

        return "bitacora-list";
    }
}