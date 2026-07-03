package com.example.demo.Service;

import java.util.List;
import java.util.Map;

public interface BitacoraService {
    List<Map<String, Object>> listarBitacora();
    void registrarBitacora(int idUsuario, int idRecluta, String accion);
}
