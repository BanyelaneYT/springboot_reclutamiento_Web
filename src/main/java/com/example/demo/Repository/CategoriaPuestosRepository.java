package com.example.demo.Repository;

import com.example.demo.model.CategoriaPuestos;

import java.util.List;

public interface CategoriaPuestosRepository {
    List<CategoriaPuestos> listarCatalogo();
    List<CategoriaPuestos> listarActivos();
    void actualizarPuesto(int id, String nombre, int idCategoria, String descripcion, String presRem, String horario, int estado, int pago);
    void guardarPuesto(String nombre, int idCategoria, String descripcion, String presRem, String horario, int estado, int pago);
    void cambiarEstado(int id, int estado);
}
