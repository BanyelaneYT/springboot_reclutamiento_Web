package com.example.demo.model;

public class CategoriaPuestos {
    private Integer id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private String presRem; // Presencial o Remoto
    private String horario;
    private int estado;
    private int pago;

    public CategoriaPuestos() {}

    public CategoriaPuestos(Integer id, String nombre, String tipo, String descripcion, String presRem, String horario, int estado, int pago) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.presRem = presRem;
        this.horario = horario;
        this.estado = estado;
        this.pago = pago;
    }

    // GETTERS Y SETTERS
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getPresRem() { return presRem; }
    public void setPresRem(String presRem) { this.presRem = presRem; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
    public int getPago() { return pago; }
    public void setPago(int pago) { this.pago = pago; }
}