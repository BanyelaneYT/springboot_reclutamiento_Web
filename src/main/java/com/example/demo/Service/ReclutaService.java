package com.example.demo.Service;

import com.example.demo.model.UserInf;

import java.util.List;
import java.util.Map;

public interface ReclutaService {
    List<UserInf> listarPostulantes(Integer idPuesto, String filtroResultado);
    void agendarCita(Integer idUser, String linkMeet, String fechaHora);
    void cambiarEstado(int id, String accion);
    void eliminar(int id);
    Map<String, Object> buscarEstadoPorDni(int dni);
    Integer registrarPostulante(int dni, String nombre, int edad, int idPuesto);
}
