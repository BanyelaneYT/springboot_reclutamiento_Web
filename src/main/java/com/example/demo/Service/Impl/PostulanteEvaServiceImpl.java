package com.example.demo.Service.Impl;

import com.example.demo.Repository.PostulanteEvaRepository;
import com.example.demo.Service.PostulanteEvaService;
import com.example.demo.model.PostulanteEva;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulanteEvaServiceImpl implements PostulanteEvaService {

    private final PostulanteEvaRepository repo;

    public PostulanteEvaServiceImpl(PostulanteEvaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<PostulanteEva> listarPostulantes() {
        return repo.listarPostulantes();
    }

    @Override
    public PostulanteEva obtenerPostulantePorId(int id) {
        return repo.obtenerPostulantePorId(id);
    }

    @Override
    public PostulanteEva obtenerUltimaPorUsuario(int idUser) {
        return repo.obtenerUltimaPorUsuario(idUser);
    }

    @Override
    public void guardarPostulante(int idUser, int idPuesto, int puntaje, String descripcion, String estado, int idCita) {
        repo.guardarPostulante(idUser, idPuesto, puntaje, descripcion, estado, idCita);
    }

    @Override
    public void actualizarPostulante(int id, int idPuesto, int puntaje, String descripcion, String estado, int idCita) {
        repo.actualizarPostulante(id, idPuesto, puntaje, descripcion, estado, idCita);
    }

    @Override
    public int actualizarEvaluacion(int id, int puntaje, String descripcion) {
        PostulanteEva eva = repo.obtenerPostulantePorId(id);
        if (eva == null) {
            return -1;
        }
        repo.actualizarPostulante(id, eva.getIdPuesto(), puntaje, descripcion, eva.getEstado(), eva.getIdCita());
        return eva.getIdUser();
    }

    @Override
    public int cambiarEstado(int id, String nuevoEstado) {
        PostulanteEva eva = repo.obtenerPostulantePorId(id);
        if (eva == null) {
            return -1;
        }
        repo.actualizarPostulante(id, eva.getIdPuesto(), eva.getPuntaje(), eva.getDescripcion(), nuevoEstado, eva.getIdCita());
        return eva.getIdUser();
    }

    @Override
    public void vincularEntrevista(int idUser, int citaId) {
        PostulanteEva eva = repo.obtenerUltimaPorUsuario(idUser);
        if (eva != null && citaId > 0) {
            repo.actualizarPostulante(eva.getId(), eva.getIdPuesto(), eva.getPuntaje(), eva.getDescripcion(), "ENTREVISTA", citaId);
        }
    }

    @Override
    public void eliminarPostulante(int id) {
        repo.eliminarPostulante(id);
    }

    @Override
    public List<PostulanteEva> obtenerPostulantesPorPuesto(int idPuesto) {
        return repo.obtenerPostulantesPorPuesto(idPuesto);
    }

    @Override
    public List<PostulanteEva> obtenerPostulantesPorUsuario(int idUser) {
        return repo.obtenerPostulantesPorUsuario(idUser);
    }
}
