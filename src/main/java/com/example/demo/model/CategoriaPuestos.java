package com.example.demo.model;

public class CategoriaPuestos {
    private Integer id;
    private Integer idCategoria;
    private String nombreCategoria;
    private String nombre;
    private String descripcion;
    private String presRem;
    private String horario;
    private int estado;
    private int pago;
    private int estadoCategoria;

    public CategoriaPuestos() {}

    public CategoriaPuestos(Integer id, Integer idCategoria, String nombreCategoria, String nombre,
                            String descripcion, String presRem, String horario, int estado, int pago) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presRem = presRem;
        this.horario = horario;
        this.estado = estado;
        this.pago = pago;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

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

    public int getEstadoCategoria() { return estadoCategoria; }
    public void setEstadoCategoria(int estadoCategoria) { this.estadoCategoria = estadoCategoria; }
}
