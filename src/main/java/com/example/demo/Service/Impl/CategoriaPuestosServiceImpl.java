package com.example.demo.Service.Impl;

import com.example.demo.Repository.CategoriaPuestosRepository;
import com.example.demo.Service.CategoriaPuestosService;
import com.example.demo.model.CategoriaPuestos;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void actualizarPuesto(int id, String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago) {
        repo.actualizarPuesto(id, nombre, tipo, descripcion, presRem, horario, estado, pago);
    }

    @Override
    public void guardarPuesto(String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago) {
        repo.guardarPuesto(nombre, tipo, descripcion, presRem, horario, estado, pago);
    }
}
