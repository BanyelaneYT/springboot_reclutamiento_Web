package com.example.demo.Repository;

import com.example.demo.model.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdministradorRepositoryDAO implements AdministradorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Administrador> listarUsuarios() {
        String sql = "SELECT * FROM usuarios";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Administrador.class));
    }

    @Override
    public void actualizarUsuario(int id, String correo, String contrasena) {
        String sql = "UPDATE administradores SET correo=?, contrasena=? WHERE id=?";
        jdbcTemplate.update(sql, correo, contrasena, id);
    }

    @Override
    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM administradores WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void guardarUsuario(String correo, String contrasena) {
        String sql = "INSERT INTO administradores (correo, contrasena) VALUES (?, ?)";
        jdbcTemplate.update(sql, correo, contrasena);
    }
}
