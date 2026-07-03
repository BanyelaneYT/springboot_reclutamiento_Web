package com.example.demo.Service.Impl;

import com.example.demo.Repository.CitasEntrevistaRepository;
import com.example.demo.Service.CitasEntrevistaService;
import com.example.demo.model.CitasEntrevista;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitasEntrevistaServiceImpl implements CitasEntrevistaService {

    private final CitasEntrevistaRepository citasEntrevistaRepository;

    public CitasEntrevistaServiceImpl(CitasEntrevistaRepository citasEntrevistaRepository) {
        this.citasEntrevistaRepository = citasEntrevistaRepository;
    }

    @Override
    public List<CitasEntrevista> listarCitas() {
        return citasEntrevistaRepository.listarCitas();
    }

    @Override
    public CitasEntrevista obtenerCitaPorId(int id) {
        return citasEntrevistaRepository.obtenerCitaPorId(id);
    }

    @Override
    public int guardarCita(int idUser, String linkMeet, String fechaHoraEntrevista) {
        return citasEntrevistaRepository.guardarCita(idUser, linkMeet, fechaHoraEntrevista);
    }

    @Override
    public void actualizarCita(int id, String linkMeet, String fechaHoraEntrevista) {
        citasEntrevistaRepository.actualizarCita(id, linkMeet, fechaHoraEntrevista);
    }

    @Override
    public void eliminarCita(int id) {
        citasEntrevistaRepository.eliminarCita(id);
    }

    @Override
    public List<CitasEntrevista> obtenerCitasPorUsuario(int idUser) {
        return citasEntrevistaRepository.obtenerCitasPorUsuario(idUser);
    }
}
