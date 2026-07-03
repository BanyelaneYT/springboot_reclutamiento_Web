package com.example.demo.Repository;

import java.util.List;
import java.util.Map;

import com.example.demo.model.UserInf;

public interface ReclutaRepository {
    List<UserInf> listarPostulantes(Integer idPuesto, String filtroResultado);
    void agendarCita(Integer idUser, String linkMeet, String fechaHora);
    void cambiarEstado(int id, String estado);
    void eliminar(int id);
    List<Map<String, Object>> consultarEstadoPorDni(int dni);
    Integer registrarPostulante(int dni, String nombre, int edad, int idPuesto);
}
