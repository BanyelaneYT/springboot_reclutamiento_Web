package com.example.demo.model;

public class Preg_Recluta {
    private int id;
    private int idUser; // Enlazado a UserInf (id)
    private int res1;
    private int res2;
    private int res3;
    private int res4;
    private int res5;
    private int res6;
    private int res7;
    private int res8;
    private String estado;

    public Preg_Recluta() {}

    // GETTERS Y SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
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
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}