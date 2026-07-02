package com.example.demo.Service.Impl;

import com.example.demo.Repository.CategoriaPuestosRepository;
import com.example.demo.Service.CategoriaPuestosService;
import com.example.demo.model.CategoriaPuestos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaPuestosServiceImpl implements CategoriaPuestosService {

    @Autowired
    private CategoriaPuestosRepository categoriaPuestosRepository;

    @Override
    public List<CategoriaPuestos> listarCatalogo() {
        return categoriaPuestosRepository.listarCatalogo();
    }

    @Override
    public void actualizarPuesto(int id, String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago) {
        categoriaPuestosRepository.actualizarPuesto(id, nombre, tipo, descripcion, presRem, horario, estado, pago);
    }

    @Override
    public void eliminarPuesto(int id) {
        categoriaPuestosRepository.eliminarPuesto(id);
    }

    @Override
    public void guardarPuesto(String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago) {
        categoriaPuestosRepository.guardarPuesto(nombre, tipo, descripcion, presRem, horario, estado, pago);
    }
}
