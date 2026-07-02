package com.example.demo.Service;

import com.example.demo.model.UserInf;

import java.util.List;
import java.util.Map;

public interface ReclutaService {
    List<UserInf> listarPostulantes();
    void agendarCita(Integer idUser, String linkMeet, String fechaHora);
    void cambiarEstado(int id, String accion);
    void eliminar(int id);
    List<Map<String, Object>> consultarEstadoPorDni(int dni);
}
