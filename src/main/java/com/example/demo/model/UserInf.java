package com.example.demo.model;

public class UserInf {
    private int id;
    private int dni;          // <- Definido como int (Spring mapeará el número sin problemas)
    private String nombre;
    private int edad;
    private Integer idQuest;
    private int puesto;       // <- Definido como int (coincide con el INT de la tabla)
    private String estado;
    private String nombrePuesto; // <- String para capturar el texto del LEFT JOIN

    public UserInf() {
    }

    // GETTERS Y SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(Integer idQuest) {
        this.idQuest = idQuest;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }
}