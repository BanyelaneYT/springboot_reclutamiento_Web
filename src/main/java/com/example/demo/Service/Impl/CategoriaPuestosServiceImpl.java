package com.example.demo.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.CategoriaPuestosRepository;
import com.example.demo.Service.CategoriaPuestosService;
import com.example.demo.model.CategoriaPuestos;

@Service
public class CategoriaPuestosServiceImpl implements CategoriaPuestosService {

    private final CategoriaPuestosRepository repo;

    public CategoriaPuestosServiceImpl(CategoriaPuestosRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CategoriaPuestos> listarCatalogo() {
        return repo.listarCatalogo();
    }

    @Override
    public List<CategoriaPuestos> listarActivos() {
        return repo.listarActivos();
    }

    @Override
    public void actualizarPuesto(int id, String nombre, int idCategoria, String descripcion, String presRem, String horario, int estado, int pago) {
        repo.actualizarPuesto(id, nombre, idCategoria, descripcion, presRem, horario, estado, pago);
    }

    @Override
    public void guardarPuesto(String nombre, int idCategoria, String descripcion, String presRem, String horario, int estado, int pago) {
        repo.guardarPuesto(nombre, idCategoria, descripcion, presRem, horario, estado, pago);
    }

    @Override
    public void cambiarEstado(int id, int estado) {
        repo.cambiarEstado(id, estado);
    }
    @Override
    public void cambiarEstadoPorCategoria(int idCategoria, int estado) {
        repo.cambiarEstadoPorCategoria(idCategoria, estado);
    }
}
