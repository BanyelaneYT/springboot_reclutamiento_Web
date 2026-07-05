package com.example.demo.Repository;

import com.example.demo.model.PostulanteEva;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostulanteEvaRepositoryDAO implements PostulanteEvaRepository {

    private static final String ESTADO_INICIAL = "PENDIENTE EN EVALUACION";
    private static final BeanPropertyRowMapper<PostulanteEva> MAPPER =
            new BeanPropertyRowMapper<>(PostulanteEva.class);

    private static final String PE = """
            pe.id, pe.id_user idUser, pe.id_puesto idPuesto, pe.puntaje,
            pe.descripcion, pe.estado, COALESCE(pe.id_cita, 0) idCita""";

    private static final String JOIN_USER = " LEFT JOIN user_inf u ON pe.id_user = u.id";
    private static final String JOIN_PUESTO = " LEFT JOIN categoria_puestos cp ON pe.id_puesto = cp.id";

    private final JdbcTemplate jdbc;

    public PostulanteEvaRepositoryDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<PostulanteEva> listarPostulantes() {
        return selectList("");
    }

    @Override
    public PostulanteEva obtenerPostulantePorId(int id) {
        return selectOne(JOIN_USER, ", u.nombre nombreUsuario", " WHERE pe.id = ?", id);
    }

    @Override
    public void guardarPostulante(int idUser, int idPuesto, int puntaje, String descripcion, String estado, int idCita) {
        jdbc.update(
                "INSERT INTO postulante_eva (id_user, id_puesto, puntaje, descripcion, estado, id_cita) VALUES (?, ?, ?, ?, ?, ?)",
                idUser, idPuesto, puntaje, descripcion, normalizarEstado(estado), normalizarIdCita(idCita));
    }

    @Override
    public void actualizarPostulante(int id, int idPuesto, int puntaje, String descripcion, String estado, int idCita) {
        jdbc.update(
                "UPDATE postulante_eva SET id_puesto = ?, puntaje = ?, descripcion = ?, estado = ?, id_cita = ? WHERE id = ?",
                idPuesto, puntaje, descripcion, normalizarEstado(estado), normalizarIdCita(idCita), id);
    }

    @Override
    public void eliminarPostulante(int id) {
        jdbc.update("DELETE FROM postulante_eva WHERE id = ?", id);
    }

    @Override
    public List<PostulanteEva> obtenerPostulantesPorPuesto(int idPuesto) {
        return selectList(" WHERE pe.id_puesto = ?", idPuesto);
    }

    @Override
    public List<PostulanteEva> obtenerPostulantesPorUsuario(int idUser) {
        return selectCore(" WHERE pe.id_user = ?", idUser);
    }

    @Override
    public PostulanteEva obtenerUltimaPorUsuario(int idUser) {
        return selectOne(JOIN_USER, ", u.nombre nombreUsuario",
                " WHERE pe.id_user = ? ORDER BY pe.id DESC LIMIT 1", idUser);
    }

    private List<PostulanteEva> selectList(String where, Object... args) {
        return select(JOIN_USER + JOIN_PUESTO, ", u.nombre nombreUsuario, cp.nombre nombrePuesto", where, args);
    }

    private List<PostulanteEva> selectCore(String where, Object... args) {
        return select("", "", where, args);
    }

    private PostulanteEva selectOne(String joins, String extraCols, String where, Object arg) {
        return select(joins, extraCols, where, arg).stream().findFirst().orElse(null);
    }

    private List<PostulanteEva> select(String joins, String extraCols, String where, Object... args) {
        String sql = "SELECT " + PE + extraCols + " FROM postulante_eva pe" + joins + where;
        return args.length == 0 ? jdbc.query(sql, MAPPER) : jdbc.query(sql, MAPPER, args);
    }

    private int normalizarIdCita(int idCita) {
        return Math.max(idCita, 0);
    }

    private String normalizarEstado(String estado) {
        return (estado == null || estado.isBlank() || "PENDIENTE".equals(estado)) ? ESTADO_INICIAL : estado;
    }
}
