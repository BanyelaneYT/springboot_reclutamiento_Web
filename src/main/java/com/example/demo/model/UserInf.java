package com.example.demo.model;

public class UserInf {
    private int id;
    private int dni;
    private String nombre;
    private int edad;
    private int puesto;
    private String estado;

    // Atributos extra para mapear nombres en los JOINS de los listados
    private String nombrePuesto;

    public UserInf() {}

    // GETTERS Y SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getDni() { return dni; }
    public void setDni(int dni) { this.dni = dni; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public int getPuesto() { return puesto; }
    public void setPuesto(int puesto) { this.puesto = puesto; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNombrePuesto() { return nombrePuesto; }
    public void setNombrePuesto(String nombrePuesto) { this.nombrePuesto = nombrePuesto; }
}