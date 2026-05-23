package com.example.demo.model;
import java.time.LocalDateTime;

public class Bitacora {
    private Integer id;
    private Integer idUsuario;     // Vinculado a Usuarios
    private Integer idRecluta;     // Vinculado a Preg_Recluta (id)
    private Integer idEvento;      // Vinculado a Evento
    private String accion;         // Ejemplo: "Registro de respuestas", "Login exitoso", etc.
    private LocalDateTime fechaRegistro; // Para saber cuándo ocurrió el movimiento

    // Constructor vacío obligatorio para RowMapper / JPA
    public Bitacora() {
    }

    // Constructor completo
    public Bitacora(Integer id, Integer idUsuario, Integer idRecluta, Integer idEvento, String accion, LocalDateTime fechaRegistro) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idRecluta = idRecluta;
        this.idEvento = idEvento;
        this.accion = accion;
        this.fechaRegistro = fechaRegistro;
    }

    // GETTERS Y SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdRecluta() {
        return idRecluta;
    }

    public void setIdRecluta(Integer idRecluta) {
        this.idRecluta = idRecluta;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}