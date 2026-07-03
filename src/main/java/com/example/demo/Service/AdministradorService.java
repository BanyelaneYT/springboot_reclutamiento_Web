package com.example.demo.Service;

import com.example.demo.model.Administrador;

import java.util.List;

public interface AdministradorService {
    List<Administrador> listarUsuarios();
    void actualizarUsuario(int id, String correo, String contrasena);
    void guardarUsuario(String correo, String contrasena);
}
