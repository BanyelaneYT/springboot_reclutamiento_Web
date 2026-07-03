package com.example.demo.model;

public class CitasEntrevista {
    private int id;
    private int idUser;
    private String linkMeet;
    private String fechaHoraEntrevista;

    // Atributo extra para mapear nombre del usuario
    private String nombreUsuario;

    public CitasEntrevista() {}

    public CitasEntrevista(int id, int idUser, String linkMeet, String fechaHoraEntrevista) {
        this.id = id;
        this.idUser = idUser;
        this.linkMeet = linkMeet;
        this.fechaHoraEntrevista = fechaHoraEntrevista;
    }

    // GETTERS Y SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getLinkMeet() { return linkMeet; }
    public void setLinkMeet(String linkMeet) { this.linkMeet = linkMeet; }

    public String getFechaHoraEntrevista() { return fechaHoraEntrevista; }
    public void setFechaHoraEntrevista(String fechaHoraEntrevista) { this.fechaHoraEntrevista = fechaHoraEntrevista; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
