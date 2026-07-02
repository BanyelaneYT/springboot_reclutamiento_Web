package com.example.demo.Service.Impl;

import com.example.demo.Repository.AdministradorRepository;
import com.example.demo.Service.AdministradorService;
import com.example.demo.model.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public List<Administrador> listarUsuarios() {
        return administradorRepository.listarUsuarios();
    }

    @Override
    public void actualizarUsuario(int id, String correo, String contrasena) {
        administradorRepository.actualizarUsuario(id, correo, contrasena);
    }

    @Override
    public void eliminarUsuario(int id) {
        administradorRepository.eliminarUsuario(id);
    }

    @Override
    public void guardarUsuario(String correo, String contrasena) {
        administradorRepository.guardarUsuario(correo, contrasena);
    }
}
