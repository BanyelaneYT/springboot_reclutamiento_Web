package com.example.demo.Controller;

import com.example.demo.model.Preg_Recluta;
import com.example.demo.model.UserInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReclutaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. LISTAR POSTULANTES EN EL PANEL RR.HH.
    @GetMapping("/crudpostulante")
    public String listarPostulantes(Model model) {
        // Usa la tabla 'user_inf' y realiza un LEFT JOIN con 'categoria_puestos'
        String sql = "SELECT u.*, c.nombre AS nombrePuesto " +
                "FROM user_inf u " +
                "LEFT JOIN categoria_puestos c ON u.puesto = c.id";

        List<UserInf> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserInf.class));
        model.addAttribute("listaPostulantes", lista);
        return "crudpostulantes";
    }

    // 2. GUARDAR POSTULANTE (CON FILTRO DE DNI Y EVALUACIÓN AUTOMÁTICA)
    @PostMapping("/reclutar/guardar")
    public String guardarPostulante(@RequestParam int dni,
                                    @RequestParam String nombre,
                                    @RequestParam int edad,
                                    @RequestParam int puesto, // ID de la categoria_puestos elegido
                                    @RequestParam int res1,
                                    @RequestParam int res2,
                                    @RequestParam int res8) {

        // Validar si ya existe el DNI en la tabla de información de usuario
        String sqlCheck = "SELECT COUNT(*) FROM user_inf WHERE dni = ?";
        Integer count = jdbcTemplate.queryForObject(sqlCheck, Integer.class, dni);

        if (count != null && count > 0) {
            return "redirect:/postular?error=duplicado";
        }

        // Evaluación de puntaje basado en respuestas (Ejemplo: la opción 4 otorga 5 puntos)
        int puntajeTotal = 0;
        if (res1 == 4) puntajeTotal += 5;
        if (res2 == 4) puntajeTotal += 5;
        if (res8 == 4) puntajeTotal += 5;

        // Determinar estado inicial automático
        String estadoInicial = (puntajeTotal >= 10) ? "PENDIENTE EN EVALUACION" : "RECHAZADO";

        // Paso A: Insertar los datos personales en 'user_inf' (usando la columna 'puesto')
        String sqlUser = "INSERT INTO user_inf (dni, nombre, edad, puesto, estado) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlUser, dni, nombre, edad, puesto, estadoInicial);

        // Obtener el ID generado para el usuario recién creado
        String sqlGetId = "SELECT id FROM user_inf WHERE dni = ? ORDER BY id DESC LIMIT 1";
        Integer idUsuarioGen = jdbcTemplate.queryForObject(sqlGetId, Integer.class, dni);

        if (idUsuarioGen != null) {
            // Paso B: Insertar las respuestas en 'preg_recluta' enlazado mediante 'idUser'
            String sqlPreg = "INSERT INTO preg_recluta (idUser, res1, res2, res3, res4, res5, res6, res7, res8, estado) " +
                    "VALUES (?, ?, ?, 0, 0, 0, 0, 0, ?, ?)";
            jdbcTemplate.update(sqlPreg, idUsuarioGen, res1, res2, res8, estadoInicial);
        }

        return "redirect:/consultar-estado";
    }

    // 3. ACTUALIZAR ESTADO DESDE EL PANEL DE CONTROL (Y registrar en Bitácora)
    @GetMapping("/crudpostulantes/estado/{id}/{accion}")
    public String cambiarEstado(@PathVariable int id, @PathVariable String accion) {
        String estadoDb = "";
        String accionBitacora = "";

        if (accion.equals("entrevista")) {
            estadoDb = "ENTREVISTA";
            accionBitacora = "Citó al candidato a entrevista virtual";
        } else if (accion.equals("aprobar")) {
            estadoDb = "APROBADO";
            accionBitacora = "Aprobó la postulación para contratación";
        } else if (accion.equals("rechazar")) {
            estadoDb = "RECHAZADO";
            accionBitacora = "Rechazó al postulante en la revisión manual";
        }

        // Actualiza el estado principal en 'user_inf'
        String sqlUpdateUser = "UPDATE user_inf SET estado = ? WHERE id = ?";
        jdbcTemplate.update(sqlUpdateUser, estadoDb, id);

        // Sincroniza el estado en la tabla de preguntas 'preg_recluta' (antiguo idUser)
        String sqlUpdatePreg = "UPDATE preg_recluta SET estado = ? WHERE idUser = ?";
        jdbcTemplate.update(sqlUpdatePreg, estadoDb, id);

        // Registro de auditoría en bitácora (apuntando al id de user_inf)
        String sqlBitacora = "INSERT INTO bitacora (id_usuario, id_recluta, accion, fecha_registro) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sqlBitacora, null, id, accionBitacora);

        return "redirect:/crudpostulante";
    }

    // 4. ELIMINAR POSTULANTE COMPLETO
    @GetMapping("/crudpostulantes/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        // Elimina primero los registros hijos en 'preg_recluta' para evitar fallos de llaves foráneas
        String sqlDelPreg = "DELETE FROM preg_recluta WHERE idUser = ?";
        jdbcTemplate.update(sqlDelPreg, id);

        // Elimina de la tabla principal 'user_inf'
        String sqlDelUser = "DELETE FROM user_inf WHERE id = ?";
        jdbcTemplate.update(sqlDelUser, id);

        return "redirect:/crudpostulante";
    }

    // 5. MOSTRAR PÁGINA PARA CONSULTAR DNI
    @GetMapping("/consultar-estado")
    public String mostrarConsultaEstado() {
        return "consultar-estado";
    }

    // 6. PROCESAR EL DNI Y BUSCAR AL POSTULANTE
    @PostMapping("/consultar-estado")
    public String procesarConsultaEstado(@RequestParam("dni") int dni, Model model) {
        String sql = "SELECT * FROM user_inf WHERE dni = ?";
        List<UserInf> lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserInf.class), dni);

        if (!lista.isEmpty()) {
            model.addAttribute("postulante", lista.get(0));
            return "resultado-estado";
        } else {
            model.addAttribute("error", "No se encontró ninguna postulación con el DNI ingresado.");
            return "consultar-estado";
        }
    }
}