package com.example.demo.model;

public class PostulanteEva {
    private int id;
    private int idUser;
    private int idPuesto;
    private int puntaje;
    private String descripcion;
    private String estado;
    private int idCita;

    // Atributos extra para mapear nombres en los JOINS
    private String nombreUsuario;
    private String nombrePuesto;

    public PostulanteEva() {}

    public PostulanteEva(int id, int idUser, int idPuesto, int puntaje, String descripcion, String estado, int idCita) {
        this.id = id;
        this.idUser = idUser;
        this.idPuesto = idPuesto;
        this.puntaje = puntaje;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idCita = idCita;
    }

    // GETTERS Y SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public int getIdPuesto() { return idPuesto; }
    public void setIdPuesto(int idPuesto) { this.idPuesto = idPuesto; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getNombrePuesto() { return nombrePuesto; }
    public void setNombrePuesto(String nombrePuesto) { this.nombrePuesto = nombrePuesto; }
}
