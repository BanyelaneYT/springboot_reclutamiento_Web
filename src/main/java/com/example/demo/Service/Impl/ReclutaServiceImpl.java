package com.example.demo.Service.Impl;

import com.example.demo.Repository.ReclutaRepository;
import com.example.demo.Service.ReclutaService;
import com.example.demo.model.UserInf;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReclutaServiceImpl implements ReclutaService {

    private final ReclutaRepository reclutaRepository;

    public ReclutaServiceImpl(ReclutaRepository reclutaRepository) {
        this.reclutaRepository = reclutaRepository;
    }

    @Override
    public List<UserInf> listarPostulantes() {
        return reclutaRepository.listarPostulantes();
    }

    @Override
    public void agendarCita(Integer idUser, String linkMeet, String fechaHora) {
        reclutaRepository.agendarCita(idUser, linkMeet, fechaHora);
    }

    @Override
    public void cambiarEstado(int id, String accion) {
        String estadoDb = accion.equals("aprobar") ? "APROBADO" : "RECHAZADO";
        reclutaRepository.cambiarEstado(id, estadoDb);
    }

    @Override
    public void eliminar(int id) {
        reclutaRepository.eliminar(id);
    }

    @Override
    public List<Map<String, Object>> consultarEstadoPorDni(int dni) {
        return reclutaRepository.consultarEstadoPorDni(dni);
    }

    @Override
    public Integer registrarPostulante(int dni, String nombre, int edad, int idPuesto) {
        return reclutaRepository.registrarPostulante(dni, nombre, edad, idPuesto);
    }
}
