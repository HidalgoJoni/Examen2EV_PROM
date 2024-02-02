package com.example.examen2ev;

import android.widget.EditText;

public class Elemento {
    private int identificacion;
    private String nombre;
    private String simbolo;
    private int numAtomico;
    private String estado;

    public Elemento() {

    }

    // Constructor creado para insertar en la bbdd, ya que la he creado con id autoincrement
    public Elemento(String nombre, String simbolo, int numAtomico, String estado) {
        this.nombre=nombre;
        this.simbolo=simbolo;
        this.numAtomico=numAtomico;
        this.estado=estado;
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
