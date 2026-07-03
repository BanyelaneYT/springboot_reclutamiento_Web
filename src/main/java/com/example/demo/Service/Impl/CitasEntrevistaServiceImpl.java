package com.example.demo.Service.Impl;

import com.example.demo.Repository.CitasEntrevistaRepository;
import com.example.demo.Service.CitasEntrevistaService;
import com.example.demo.model.CitasEntrevista;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitasEntrevistaServiceImpl implements CitasEntrevistaService {

    private final CitasEntrevistaRepository repo;

    public CitasEntrevistaServiceImpl(CitasEntrevistaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CitasEntrevista> listarCitas() {
        return repo.listarCitas();
    }

    @Override
    public CitasEntrevista obtenerCitaPorId(int id) {
        return repo.obtenerCitaPorId(id);
    }

    @Override
    public int guardarCita(int idUser, String linkMeet, String fechaHoraEntrevista) {
        return repo.guardarCita(idUser, linkMeet, fechaHoraEntrevista);
    }

    @Override
    public void actualizarCita(int id, String linkMeet, String fechaHoraEntrevista) {
        repo.actualizarCita(id, linkMeet, fechaHoraEntrevista);
    }

    @Override
    public void eliminarCita(int id) {
        repo.eliminarCita(id);
    }

    @Override
    public List<CitasEntrevista> obtenerCitasPorUsuario(int idUser) {
        return repo.obtenerCitasPorUsuario(idUser);
    }
}
