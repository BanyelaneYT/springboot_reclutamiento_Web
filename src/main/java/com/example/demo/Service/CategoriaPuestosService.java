package com.example.demo.Service;

import java.util.List;

import com.example.demo.model.CategoriaPuestos;

public interface CategoriaPuestosService {
    List<CategoriaPuestos> listarCatalogo();
    List<CategoriaPuestos> listarActivos();
    void actualizarPuesto(int id, String nombre, int idCategoria, String descripcion, String presRem, String horario, int estado, int pago);
    void guardarPuesto(String nombre, int idCategoria, String descripcion, String presRem, String horario, int estado, int pago);
    void cambiarEstado(int id, int estado);
    void cambiarEstadoPorCategoria(int idCategoria, int estado);
}
