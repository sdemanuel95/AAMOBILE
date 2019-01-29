package com.tofitsolutions.armasdurasargentinas.models;

public class Formato {

    private long id;
    private long posicion;
    private String formato;
    private long lados;
    private long cant_doblados;

    public Formato() {
    }

    public Formato(long id, long posicion, String formato, long lados, long cant_doblados) {
        this.id = id;
        this.posicion = posicion;
        this.formato = formato;
        this.lados = lados;
        this.cant_doblados = cant_doblados;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPosicion() {
        return posicion;
    }

    public void setPosicion(long posicion) {
        this.posicion = posicion;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public long getLados() {
        return lados;
    }

    public void setLados(long lados) {
        this.lados = lados;
    }

    public long getCant_doblados() {
        return cant_doblados;
    }

    public void setCant_doblados(long cant_doblados) {
        this.cant_doblados = cant_doblados;
    }
}
