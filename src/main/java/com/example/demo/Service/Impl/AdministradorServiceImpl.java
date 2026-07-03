package com.example.demo.Service.Impl;

import com.example.demo.Repository.AdministradorRepository;
import com.example.demo.Service.AdministradorService;
import com.example.demo.model.Administrador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorRepository repo;

    public AdministradorServiceImpl(AdministradorRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Administrador> listarUsuarios() {
        return repo.listarUsuarios();
    }

    @Override
    public boolean autenticar(String correo, String contrasena) {
        return repo.autenticar(correo, contrasena);
    }

    @Override
    public void actualizarUsuario(int id, String correo, String contrasena) {
        repo.actualizarUsuario(id, correo, contrasena);
    }

    @Override
    public void guardarUsuario(String correo, String contrasena) {
        repo.guardarUsuario(correo, contrasena);
    }
}
