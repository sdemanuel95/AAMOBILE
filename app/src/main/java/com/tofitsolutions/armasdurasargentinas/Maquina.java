package com.tofitsolutions.armasdurasargentinas;

import java.lang.reflect.Constructor;

public class Maquina {
    long id;
    String clasificacion;
    String marca;
    String modelo;
    String tipo_mp;
    String diametro_minimo;
    String diametro_maximo;
    String merma;

    public Maquina(long id, String clasificacion, String marca, String modelo, String tipo_mp, String diametro_minimo, String diametro_maximo, String merma) {
        this.id = id;
        this.clasificacion = clasificacion;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo_mp = tipo_mp;
        this.diametro_minimo = diametro_minimo;
        this.diametro_maximo = diametro_maximo;
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

    public String getTipo_mp() {
        return tipo_mp;
    }

    public void setTipo_mp(String tipo_mp) {
        this.tipo_mp = tipo_mp;
    }

    public String getDiametro_minimo() {
        return diametro_minimo;
    }

    public void setDiametro_minimo(String diametro_minimo) {
        this.diametro_minimo = diametro_minimo;
    }

    public String getDiametro_maximo() {
        return diametro_maximo;
    }

    public void setDiametro_maximo(String diametro_maximo) {
        this.diametro_maximo = diametro_maximo;
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
