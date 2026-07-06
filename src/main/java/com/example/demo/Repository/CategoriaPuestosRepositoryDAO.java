package com.example.demo.Repository;

import com.example.demo.model.CategoriaPuestos;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaPuestosRepositoryDAO implements CategoriaPuestosRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaPuestosRepositoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_PUESTO = """
            SELECT id, nombre, tipo, descripcion, pres_rem presRem, horario, estado, pago FROM categoria_puestos""";
    private static final BeanPropertyRowMapper<CategoriaPuestos> MAPPER =
            new BeanPropertyRowMapper<>(CategoriaPuestos.class);

    @Override
    public List<CategoriaPuestos> listarCatalogo() {
        return jdbcTemplate.query(SELECT_PUESTO, MAPPER);
    }

    @Override
    public List<CategoriaPuestos> listarActivos() {
        return jdbcTemplate.query(SELECT_PUESTO + " WHERE estado = 1", MAPPER);
    }

    @Override
    public void actualizarPuesto(int id, String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago) {
        String sql = "UPDATE categoria_puestos SET nombre=?, tipo=?, descripcion=?, pres_rem=?, horario=?, estado=?, pago=? WHERE id=?";
        jdbcTemplate.update(sql, nombre, tipo, descripcion, presRem, horario, estado, pago, id);
    }

    @Override
    public void guardarPuesto(String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago) {
        String sql = "INSERT INTO categoria_puestos (nombre, tipo, descripcion, pres_rem, horario, estado, pago) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, nombre, tipo, descripcion, presRem, horario, estado, pago);
    }

    @Override
    public void cambiarEstado(int id, int estado) {
        jdbcTemplate.update("UPDATE categoria_puestos SET estado = ? WHERE id = ?", estado, id);
    }
}
