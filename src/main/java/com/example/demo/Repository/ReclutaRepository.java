package com.example.demo.Repository;

import com.example.demo.model.UserInf;

import java.util.List;
import java.util.Map;

public interface ReclutaRepository {
    List<UserInf> listarPostulantes();
    void agendarCita(Integer idUser, String linkMeet, String fechaHora);
    void cambiarEstado(int id, String estado);
    void eliminar(int id);
    List<Map<String, Object>> consultarEstadoPorDni(int dni);
}
