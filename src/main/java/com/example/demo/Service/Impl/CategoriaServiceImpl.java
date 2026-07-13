package com.example.demo.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Service.CategoriaService;
import com.example.demo.model.Categoria;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repo;

    public CategoriaServiceImpl(CategoriaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Categoria> listarCatalogo() {
        return repo.listarCatalogo();
    }

    @Override
    public List<Categoria> listarActivas() {
        return repo.listarActivas();
    }

    @Override
    public void guardarCategoria(String nombre, String descripcion, int estado) {
        repo.guardarCategoria(nombre, descripcion, estado);
    }

    @Override
    public void actualizarCategoria(int id, String nombre, String descripcion, int estado) {
        repo.actualizarCategoria(id, nombre, descripcion, estado);
    }

    @Override
    public void cambiarEstado(int id, int estado) {
        repo.cambiarEstado(id, estado);
    }

    @Override
    public int contarPuestosPorCategoria(int idCategoria) {
        return repo.contarPuestosPorCategoria(idCategoria);
    }
    // ==================== NUEVO MÉTODO ====================
    @Override
    public void desactivarPorCategoria(int idCategoria) {
        repo.desactivarPorCategoria(idCategoria);
    }
    // =====================================================
}
