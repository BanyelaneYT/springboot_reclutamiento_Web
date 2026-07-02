package com.example.demo.Repository;

import com.example.demo.model.CategoriaPuestos;

import java.util.List;

public interface CategoriaPuestosRepository {
    List<CategoriaPuestos> listarCatalogo();
    void actualizarPuesto(int id, String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago);
    void eliminarPuesto(int id);
    void guardarPuesto(String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago);
}
