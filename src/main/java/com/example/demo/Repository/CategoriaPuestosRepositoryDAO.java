package com.example.demo.Repository;

import com.example.demo.model.CategoriaPuestos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaPuestosRepositoryDAO implements CategoriaPuestosRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CategoriaPuestos> listarCatalogo() {
        String sql = "SELECT id, nombre, tipo, descripcion, pres_rem AS presRem, horario, estado, pago FROM categoria_puestos";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CategoriaPuestos.class));
    }

    @Override
    public void actualizarPuesto(int id, String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago) {
        String sql = "UPDATE categoria_puestos SET nombre=?, tipo=?, descripcion=?, pres_rem=?, horario=?, estado=?, pago=? WHERE id=?";
        jdbcTemplate.update(sql, nombre, tipo, descripcion, presRem, horario, estado, pago, id);
    }

    @Override
    public void eliminarPuesto(int id) {
        String sql = "DELETE FROM categoria_puestos WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void guardarPuesto(String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago) {
        String sql = "INSERT INTO categoria_puestos (nombre, tipo, descripcion, pres_rem, horario, estado, pago) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, nombre, tipo, descripcion, presRem, horario, estado, pago);
    }
}
