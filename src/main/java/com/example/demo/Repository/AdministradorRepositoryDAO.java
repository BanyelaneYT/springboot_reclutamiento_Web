package com.example.demo.Repository;

import com.example.demo.model.Administrador;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdministradorRepositoryDAO implements AdministradorRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdministradorRepositoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final BeanPropertyRowMapper<Administrador> MAPPER =
            new BeanPropertyRowMapper<>(Administrador.class);

    @Override
    public List<Administrador> listarUsuarios() {
        return jdbcTemplate.query("SELECT id, correo, contrasena FROM administradores", MAPPER);
    }

    @Override
    public boolean autenticar(String correo, String contrasena) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM administradores WHERE correo = ? AND contrasena = ?",
                Integer.class, correo, contrasena);
        return count != null && count > 0;
    }

    @Override
    public void actualizarUsuario(int id, String correo, String contrasena) {
        String sql = "UPDATE administradores SET correo=?, contrasena=? WHERE id=?";
        jdbcTemplate.update(sql, correo, contrasena, id);
    }

    @Override
    public void guardarUsuario(String correo, String contrasena) {
        String sql = "INSERT INTO administradores (correo, contrasena) VALUES (?, ?)";
        jdbcTemplate.update(sql, correo, contrasena);
    }
}
