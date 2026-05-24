package com.example.demo.model;

public class Preg_Recluta {
    private int id;
    private int dni;
    private String nombre;
    private int edad;
    private int res1;
    private int res2;
    private int res3;
    private int res4;
    private int res5;
    private int res6;
    private int res7;
    private int res8;
    private String ubicacion;
    private String estado;
    private Integer id_evento; // Nuevo atributo
    private String nombreEvento; // Para almacenar el nombre recuperado por SQL JOIN

    // Constructor vacío obligatorio para RowMapper
    public Preg_Recluta() {}

    public Preg_Recluta(int id, int dni, String nombre, int edad, int res1, int res2, int res3, int res4, int res5, int res6, int res7, int res8, String ubicacion, String estado, Integer id_evento, String nombreEvento) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.res1 = res1;
        this.res2 = res2;
        this.res3 = res3;
        this.res4 = res4;
        this.res5 = res5;
        this.res6 = res6;
        this.res7 = res7;
        this.res8 = res8;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.id_evento = id_evento;
        this.nombreEvento=nombreEvento;
    }

    // GETTERS Y SETTERS EN MINÚSCULAS (Estándar)
    public Integer getId_evento() { return id_evento; }
    public void setId_evento(Integer id_evento) { this.id_evento = id_evento; }

    public String getNombreEvento() { return nombreEvento; }
    public void setNombreEvento(String nombreEvento) { this.nombreEvento = nombreEvento; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDni() { return dni; }
    public void setDni(int dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public int getRes1() { return res1; }
    public void setRes1(int res1) { this.res1 = res1; }

    public int getRes2() { return res2; }
    public void setRes2(int res2) { this.res2 = res2; }

    public int getRes3() { return res3; }
    public void setRes3(int res3) { this.res3 = res3; }

    public int getRes4() { return res4; }
    public void setRes4(int res4) { this.res4 = res4; }

    public int getRes5() { return res5; }
    public void setRes5(int res5) { this.res5 = res5; }

    public int getRes6() { return res6; }
    public void setRes6(int res6) { this.res6 = res6; }

    public int getRes7() { return res7; }
    public void setRes7(int res7) { this.res7 = res7; }

public int getRes8() { return res8; }
public void setRes8(int res8) { this.res8 = res8; }

public String getUbicacion() { return ubicacion; }
public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

public String getEstado() { return estado; }
public void setEstado(String estado) { this.estado = estado; }
}