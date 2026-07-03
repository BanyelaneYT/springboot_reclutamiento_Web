package com.example.demo.Service;

import com.example.demo.model.PostulanteEva;

import java.util.List;

public interface PostulanteEvaService {
    List<PostulanteEva> listarPostulantes();
    PostulanteEva obtenerPostulantePorId(int id);
    PostulanteEva obtenerUltimaPorUsuario(int idUser);
    void guardarPostulante(int idUser, int idPuesto, int puntaje, String descripcion, String estado, int idCita);
    void actualizarPostulante(int id, int idPuesto, int puntaje, String descripcion, String estado, int idCita);
    int actualizarEvaluacion(int id, int puntaje, String descripcion);
    int cambiarEstado(int id, String nuevoEstado);
    void vincularEntrevista(int idUser, int citaId);
    void eliminarPostulante(int id);
    List<PostulanteEva> obtenerPostulantesPorPuesto(int idPuesto);
    List<PostulanteEva> obtenerPostulantesPorUsuario(int idUser);
}
