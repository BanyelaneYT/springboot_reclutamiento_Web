package com.example.demo.Repository;

import com.example.demo.model.Administrador;

import java.util.List;

public interface AdministradorRepository {
    List<Administrador> listarUsuarios();
    boolean autenticar(String correo, String contrasena);
    void actualizarUsuario(int id, String correo, String contrasena);
    void guardarUsuario(String correo, String contrasena);
}
