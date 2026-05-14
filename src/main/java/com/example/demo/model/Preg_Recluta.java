package com.example.demo.model;

public class Preg_Recluta {
    private int Id;
    private int Dni;
    private String Nombre;
    private int Edad;
    private int Res1;
    private int Res2;
    private int Res3;
    private int Res4;
    private int Res5;
    private int Res6;
    private int Res7;
    private int Res8;
    private String Ubicacion;
    private String Estado;
    private String Res_eva;

    public void Preg_Recluta(){};

    public Preg_Recluta(String res_eva, String estado, String ubicacion, int res8, int res7, int res6, int res5, int res4, int res3, int res2, int res1, int edad, String nombre, int dni, int id) {
        Res_eva = res_eva;
        Estado = estado;
        Ubicacion = ubicacion;
        Res8 = res8;
        Res7 = res7;
        Res6 = res6;
        Res5 = res5;
        Res4 = res4;
        Res3 = res3;
        Res2 = res2;
        Res1 = res1;
        Edad = edad;
        Nombre = nombre;
        Dni = dni;
        Id = id;
    }
    //getters
    public int getId() {
        return Id;
    }

    public int getDni() {
        return Dni;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getEdad() {
        return Edad;
    }

    public int getRes1() {
        return Res1;
    }

    public int getRes2() {
        return Res2;
    }

    public int getRes3() {
        return Res3;
    }

    public int getRes4() {
        return Res4;
    }

    public int getRes5() {
        return Res5;
    }

    public int getRes6() {
        return Res6;
    }

    public int getRes7() {
        return Res7;
    }

    public int getRes8() {
        return Res8;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public String getEstado() {
        return Estado;
    }

    public String getRes_eva() {
        return Res_eva;
    }

    //setters
    public void setId(int id) {
        Id = id;
    }

    public void setDni(int dni) {
        Dni = dni;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public void setRes1(int res1) {
        Res1 = res1;
    }

    public void setRes2(int res2) {
        Res2 = res2;
    }

    public void setRes3(int res3) {
        Res3 = res3;
    }

    public void setRes4(int res4) {
        Res4 = res4;
    }

    public void setRes5(int res5) {
        Res5 = res5;
    }

    public void setRes6(int res6) {
        Res6 = res6;
    }

    public void setRes7(int res7) {
        Res7 = res7;
    }

    public void setRes8(int res8) {
        Res8 = res8;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public void setRes_eva(String res_eva) {
        Res_eva = res_eva;
    }
}

