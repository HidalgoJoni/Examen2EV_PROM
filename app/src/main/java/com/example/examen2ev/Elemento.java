package com.example.examen2ev;

public class Elemento {
    private int identificacion;
    private String nombre;
    private String simbolo;
    private int numAtomico;
    private String estado;

    public Elemento (int id, String nom, String simbolo, int num, String estado){
        this.identificacion=id;
        this.nombre=nom;
        this.simbolo=simbolo;
        this.numAtomico=num;
        this.estado=estado;
    }

    public Elemento() {

    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public int getNumAtomico() {
        return numAtomico;
    }

    public void setNumAtomico(int numAtomico) {
        this.numAtomico = numAtomico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
