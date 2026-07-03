package com.example.demo.Service;

import com.example.demo.model.CitasEntrevista;

import java.util.List;

public interface CitasEntrevistaService {
    List<CitasEntrevista> listarCitas();
    CitasEntrevista obtenerCitaPorId(int id);
    int guardarCita(int idUser, String linkMeet, String fechaHoraEntrevista);
    void actualizarCita(int id, String linkMeet, String fechaHoraEntrevista);
    void eliminarCita(int id);
    List<CitasEntrevista> obtenerCitasPorUsuario(int idUser);
}
