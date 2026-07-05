package com.example.demo.Service;

import java.util.List;
import java.util.Map;

public interface BitacoraService {

    int ADMIN_SISTEMA = 1;

    List<Map<String, Object>> listarBitacora();

    void registrarBitacora(int idUsuario, int idRecluta, String accion);

    default void registrarAccion(int idRecluta, String accion) {
        registrarBitacora(ADMIN_SISTEMA, idRecluta, accion);
    }
}
