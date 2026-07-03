package com.example.demo.Service;

import com.example.demo.model.PostulanteEva;

import java.util.List;

public interface PostulanteEvaService {
    List<PostulanteEva> listarPostulantes();
    PostulanteEva obtenerPostulantePorId(int id);
    void guardarPostulante(int idUser, int idPuesto, int puntaje, String descripcion, String estado, int idCita);
    void actualizarPostulante(int id, int idPuesto, int puntaje, String descripcion, String estado, int idCita);
    void eliminarPostulante(int id);
    List<PostulanteEva> obtenerPostulantesPorPuesto(int idPuesto);
    List<PostulanteEva> obtenerPostulantesPorUsuario(int idUser);
}
