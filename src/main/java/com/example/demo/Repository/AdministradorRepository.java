package com.example.demo.Repository;

import com.example.demo.model.Administrador;

import java.util.List;

public interface AdministradorRepository {
    List<Administrador> listarUsuarios();
    void actualizarUsuario(int id, String correo, String contrasena);
    void eliminarUsuario(int id);
    void guardarUsuario(String correo, String contrasena);
}
