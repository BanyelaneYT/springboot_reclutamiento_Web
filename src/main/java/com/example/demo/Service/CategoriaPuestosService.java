package com.example.demo.Service;

import com.example.demo.model.CategoriaPuestos;

import java.util.List;

public interface CategoriaPuestosService {
    List<CategoriaPuestos> listarCatalogo();
    void actualizarPuesto(int id, String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago);
    void eliminarPuesto(int id);
    void guardarPuesto(String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago);
}
