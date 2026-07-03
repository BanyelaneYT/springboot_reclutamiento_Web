package com.example.demo.Service.Impl;

import com.example.demo.Repository.PostulanteEvaRepository;
import com.example.demo.Service.PostulanteEvaService;
import com.example.demo.model.PostulanteEva;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulanteEvaServiceImpl implements PostulanteEvaService {

    private final PostulanteEvaRepository postulanteEvaRepository;

    public PostulanteEvaServiceImpl(PostulanteEvaRepository postulanteEvaRepository) {
        this.postulanteEvaRepository = postulanteEvaRepository;
    }

    @Override
    public List<PostulanteEva> listarPostulantes() {
        return postulanteEvaRepository.listarPostulantes();
    }

    @Override
    public PostulanteEva obtenerPostulantePorId(int id) {
        return postulanteEvaRepository.obtenerPostulantePorId(id);
    }

    @Override
    public void guardarPostulante(int idUser, int idPuesto, int puntaje, String descripcion, String estado, int idCita) {
        postulanteEvaRepository.guardarPostulante(idUser, idPuesto, puntaje, descripcion, estado, idCita);
    }

    @Override
    public void actualizarPostulante(int id, int idPuesto, int puntaje, String descripcion, String estado, int idCita) {
        postulanteEvaRepository.actualizarPostulante(id, idPuesto, puntaje, descripcion, estado, idCita);
    }

    @Override
    public void eliminarPostulante(int id) {
        postulanteEvaRepository.eliminarPostulante(id);
    }

    @Override
    public List<PostulanteEva> obtenerPostulantesPorPuesto(int idPuesto) {
        return postulanteEvaRepository.obtenerPostulantesPorPuesto(idPuesto);
    }

    @Override
    public List<PostulanteEva> obtenerPostulantesPorUsuario(int idUser) {
        return postulanteEvaRepository.obtenerPostulantesPorUsuario(idUser);
    }
}
