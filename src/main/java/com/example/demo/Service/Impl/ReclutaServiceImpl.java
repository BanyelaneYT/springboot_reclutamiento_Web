package com.example.demo.Service.Impl;

import com.example.demo.Repository.ReclutaRepository;
import com.example.demo.Service.ReclutaService;
import com.example.demo.model.UserInf;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReclutaServiceImpl implements ReclutaService {

    private final ReclutaRepository repo;

    public ReclutaServiceImpl(ReclutaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<UserInf> listarPostulantes(Integer idPuesto, String filtroResultado) {
        return repo.listarPostulantes(idPuesto, filtroResultado);
    }

    @Override
    public void agendarCita(Integer idUser, String linkMeet, String fechaHora) {
        repo.agendarCita(idUser, linkMeet, fechaHora);
    }

    @Override
    public void cambiarEstado(int id, String accion) {
        String estado = "aprobar".equalsIgnoreCase(accion) ? "APROBADO" : "RECHAZADO";
        repo.cambiarEstado(id, estado);
    }

    @Override
    public void eliminar(int id) {
        repo.eliminar(id);
    }

    @Override
    public Map<String, Object> buscarEstadoPorDni(int dni) {
        List<Map<String, Object>> resultados = repo.consultarEstadoPorDni(dni);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public Integer registrarPostulante(int dni, String nombre, int edad, int idPuesto) {
        return repo.registrarPostulante(dni, nombre, edad, idPuesto);
    }
}
