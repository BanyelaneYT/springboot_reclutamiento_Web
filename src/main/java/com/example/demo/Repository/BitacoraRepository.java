package com.example.demo.Repository;

import java.util.List;
import java.util.Map;

public interface BitacoraRepository {
    List<Map<String, Object>> listarBitacora();
    void registrarBitacora(int idUsuario, int idRecluta, String accion);
}
