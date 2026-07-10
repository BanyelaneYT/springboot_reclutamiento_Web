package com.example.demo.Service;

import com.example.demo.model.Categoria;

import java.util.List;

public interface CategoriaService {
    List<Categoria> listarCatalogo();
    List<Categoria> listarActivas();
    void guardarCategoria(String nombre, String descripcion, int estado);
    void actualizarCategoria(int id, String nombre, String descripcion, int estado);
    void cambiarEstado(int id, int estado);
    int contarPuestosPorCategoria(int idCategoria);
}
