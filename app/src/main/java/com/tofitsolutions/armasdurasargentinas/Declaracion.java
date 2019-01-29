package com.tofitsolutions.armasdurasargentinas;

/**
 * Created by Abel on 5/11/2017.
 */

public class Declaracion {
    private String id;
    private String fecha;
    private String usuario;
    private String ayudante;
    private String equipo;
    private String precintoA;
    private String precintoB;
    private String item;
    private String cantidad;
    private String cantidadKG;

    public Declaracion(String id, String fecha, String usuario, String ayudante,
                       String equipo, String precintoA, String precintoB,
                       String item, String cantidad, String cantidadKG) {
        this.id = id;
        this.fecha = fecha;
        this.usuario = usuario;
        this.ayudante = ayudante;
        this.equipo = equipo;
        this.precintoA = precintoA;
        this.precintoB = precintoB;
        this.item = item;
        this.cantidad = cantidad;
        this.cantidadKG = cantidadKG;
    }

    public Declaracion(String usuario, String ayudante, String equipo, String precintoA, String precintoB, String item, String cantidad) {
        this.usuario = usuario;
        this.ayudante = ayudante;
        this.equipo = equipo;
        this.precintoA = precintoA;
        this.precintoB = precintoB;
        this.item = item;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAyudante() {
        return ayudante;
    }

    public void setAyudante(String ayudante) {
        this.ayudante = ayudante;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getPrecintoA() {
        return precintoA;
    }

    public void setPrecintoA(String precintoA) {
        this.precintoA = precintoA;
    }

    public String getPrecintoB() {
        return precintoB;
    }

    public void setPrecintoB(String precintoB) {
        this.precintoB = precintoB;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCantidadKG() {
        return cantidadKG;
    }

    public void setCantidadKG(String cantidadKG) {
        this.cantidadKG = cantidadKG;
    }
}
