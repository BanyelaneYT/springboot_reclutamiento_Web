package com.example.demo.Repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CategoriaPuestos;

@Repository
public class CategoriaPuestosRepositoryDAO implements CategoriaPuestosRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaPuestosRepositoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_PUESTO = """
            SELECT cp.id, cp.id_categoria idCategoria, c.nombre nombreCategoria, c.estado estadoCategoria,
                   cp.nombre, cp.descripcion, cp.pres_rem presRem, cp.horario, cp.estado, cp.pago
            FROM categoria_puestos cp
            JOIN categorias c ON cp.id_categoria = c.id""";
    private static final BeanPropertyRowMapper<CategoriaPuestos> MAPPER =
            new BeanPropertyRowMapper<>(CategoriaPuestos.class);

    @Override
    public List<CategoriaPuestos> listarCatalogo() {
        return jdbcTemplate.query(SELECT_PUESTO + " ORDER BY cp.nombre", MAPPER);
    }

    @Override
    public List<CategoriaPuestos> listarActivos() {
        return jdbcTemplate.query(SELECT_PUESTO + " WHERE cp.estado = 1 ORDER BY cp.nombre", MAPPER);
    }

    @Override
    public void actualizarPuesto(int id, String nombre, int idCategoria, String descripcion, String presRem, String horario, int estado, int pago) {
        String sql = "UPDATE categoria_puestos SET nombre=?, id_categoria=?, descripcion=?, pres_rem=?, horario=?, estado=?, pago=? WHERE id=?";
        jdbcTemplate.update(sql, nombre, idCategoria, descripcion, presRem, horario, estado, pago, id);
    }

    @Override
    public void guardarPuesto(String nombre, int idCategoria, String descripcion, String presRem, String horario, int estado, int pago) {
        String sql = "INSERT INTO categoria_puestos (nombre, id_categoria, descripcion, pres_rem, horario, estado, pago) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, nombre, idCategoria, descripcion, presRem, horario, estado, pago);
    }

    @Override
    public void cambiarEstado(int id, int estado) {
        if (estado == 1) {
            String sqlCheck = "SELECT c.estado FROM categorias c JOIN categoria_puestos cp ON c.id = cp.id_categoria WHERE cp.id = ?";
            Integer estadoCat = jdbcTemplate.queryForObject(sqlCheck, Integer.class, id);

            if (estadoCat == null || estadoCat == 0) {
                throw new RuntimeException("CATEGORIA_INACTIVA");
            }
        }
        jdbcTemplate.update("UPDATE categoria_puestos SET estado = ? WHERE id = ?", estado, id);
    }

    @Override
    public void cambiarEstadoPorCategoria(int idCategoria, int estado) {
        String sql = "UPDATE categoria_puestos SET estado = ? WHERE id_categoria = ?";
        jdbcTemplate.update(sql, estado, idCategoria);
    }
}
