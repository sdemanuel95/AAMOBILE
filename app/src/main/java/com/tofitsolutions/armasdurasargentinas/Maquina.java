package com.tofitsolutions.armasdurasargentinas;

import java.io.Serializable;
import java.lang.reflect.Constructor;

public class Maquina implements Serializable{


    private static final long serialVersionId = 1L;

    long id;
    String clasificacion;
    String marca;
    String modelo;
    String tipoMP;
    String diametroMin;
    String diametroMax;
    String merma;

    public Maquina(long id, String clasificacion, String marca, String modelo, String tipoMP, String diametroMin, String diametroMax, String merma) {
        this.id = id;
        this.clasificacion = clasificacion;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoMP = tipoMP;
        this.diametroMin = diametroMin;
        this.diametroMax = diametroMax;
        this.merma = merma;
    }
    public Maquina(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String gettipoMP() {
        return tipoMP;
    }

    public void settipoMP(String tipoMP) {
        this.tipoMP = tipoMP;
    }

    public String getdiametroMin() {
        return diametroMin;
    }

    public void setdiametroMin(String diametroMin) {
        this.diametroMin = diametroMin;
    }

    public String getdiametroMax() {
        return diametroMax;
    }

    public void setdiametroMax(String diametroMax) {
        this.diametroMax = diametroMax;
    }

    public String getMerma() {
        return merma;
    }

    public void setMerma(String merma) {
        this.merma = merma;
    }

    public boolean existe(String classmodel){
        boolean existe = false;
        if((this.getMarca() + "-" + this.getModelo()).equals(classmodel)){
            existe = true;
        }
        return existe;
    }

}
