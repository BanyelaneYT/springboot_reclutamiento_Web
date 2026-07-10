package com.example.demo.Repository;

import com.example.demo.model.Categoria;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaRepositoryDAO implements CategoriaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaRepositoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_CATEGORIA = """
            SELECT id, nombre, descripcion, estado FROM categorias""";
    private static final BeanPropertyRowMapper<Categoria> MAPPER =
            new BeanPropertyRowMapper<>(Categoria.class);

    @Override
    public List<Categoria> listarCatalogo() {
        return jdbcTemplate.query(SELECT_CATEGORIA + " ORDER BY nombre", MAPPER);
    }

    @Override
    public List<Categoria> listarActivas() {
        return jdbcTemplate.query(SELECT_CATEGORIA + " WHERE estado = 1 ORDER BY nombre", MAPPER);
    }

    @Override
    public void guardarCategoria(String nombre, String descripcion, int estado) {
        jdbcTemplate.update(
                "INSERT INTO categorias (nombre, descripcion, estado) VALUES (?, ?, ?)",
                nombre, descripcion, estado);
    }

    @Override
    public void actualizarCategoria(int id, String nombre, String descripcion, int estado) {
        jdbcTemplate.update(
                "UPDATE categorias SET nombre=?, descripcion=?, estado=? WHERE id=?",
                nombre, descripcion, estado, id);
    }

    @Override
    public void cambiarEstado(int id, int estado) {
        jdbcTemplate.update("UPDATE categorias SET estado = ? WHERE id = ?", estado, id);
    }

    @Override
    public int contarPuestosPorCategoria(int idCategoria) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM categoria_puestos WHERE id_categoria = ?",
                Integer.class, idCategoria);
        return count != null ? count : 0;
    }
}
