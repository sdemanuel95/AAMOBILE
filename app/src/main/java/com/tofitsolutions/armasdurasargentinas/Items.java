package com.tofitsolutions.armasdurasargentinas;

import java.io.Serializable;

/**
 * Created by Abel on 16/10/2017.
 */

public class Items implements Serializable{



    private long id;
    private long idpedido;
    private long item;
    private String posicion;
    private String acero;
    private String material;
    private String diametro;
    private String cantidad;
    private String cantidadDec;
    private String formato;
    private String dibujo;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String h1;
    private String h2;
    private String lParcial;
    private String lTotal;
    private String lCortar;
    private String peso;
    private String observaciones;
    private String codigo;
    private String estructura;

    public Items(long idpedido, long item, String posicion){
        this.idpedido = idpedido;
        this.item = item;
        this.posicion = posicion;
    }
    public Items(long idpedido, long item, String posicion, String estructura){
        this.idpedido = idpedido;
        this.item = item;
        this.posicion = posicion;
        this.estructura = estructura;
    }

    public Items(long id, long idpedido, long item, String posicion, String acero, String material, String diametro, String cantidad,
                 String cantidadDec, String lParcial, String lTotal, String lCortar, String peso,
                 String observaciones, String codigo, String estructura) {
        super();
        this.id = id;
        this.idpedido = idpedido;
        this.item = item;
        this.posicion = posicion;
        this.acero = acero;
        this.material = material;
        this.diametro = diametro;
        this.cantidad = cantidad;
        this.cantidadDec = cantidadDec;
        this.lParcial = lParcial;
        this.lTotal = lTotal;
        this.lCortar = lCortar;
        this.peso = peso;
        this.observaciones = observaciones;
        this.codigo = codigo;
        this.estructura = estructura;
    }

    public Items(long id, long idpedido, long item, String posicion, String acero, String material, String diametro, String cantidad,
                 String cantidadDec, String formato, String dibujo, String a, String b, String c, String d, String e, String f,
                 String g, String h, String h1, String h2, String lParcial, String lTotal, String lCortar, String peso,
                 String observaciones, String codigo, String estructura) {
        super();
        this.id = id;
        this.idpedido = idpedido;
        this.item = item;
        this.posicion = posicion;
        this.acero = acero;
        this.material = material;
        this.diametro = diametro;
        this.cantidad = cantidad;
        this.cantidadDec = cantidadDec;
        this.formato = formato;
        this.dibujo = dibujo;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
        this.h1 = h1;
        this.h2 = h2;
        this.lParcial = lParcial;
        this.lTotal = lTotal;
        this.lCortar = lCortar;
        this.peso = peso;
        this.observaciones = observaciones;
        this.codigo = codigo;
        this.estructura = estructura;
    }

    public Items(long idpedido, long item) {
        this.idpedido = idpedido;
        this.item = item;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getIdpedido() {
        return idpedido;
    }
    public void setIdpedido(long idpedido) {
        this.idpedido = idpedido;
    }
    public long getItem() {
        return item;
    }
    public void setItem(long item) {
        this.item = item;
    }
    public String getPosicion() {
        return posicion;
    }
    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
    public String getAcero() {
        return acero;
    }
    public void setAcero(String acero) {
        this.acero = acero;
    }
    public String getDiametro() {
        return diametro;
    }
    public void setDiametro(String diametro) {
        this.diametro = diametro;
    }
    public String getCantidad() {
        return cantidad;
    }
    public String getCantidadDec() {
        return cantidadDec;
    }
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    public void setCantidadDec(String cantidadDec) {
        this.cantidadDec = cantidadDec;
    }
    public String getFormato() {
        return formato;
    }
    public void setFormato(String formato) {
        this.formato = formato;
    }
    public String getDibujo() {
        return dibujo;
    }
    public void setDibujo(String dibujo) {
        this.dibujo = dibujo;
    }
    public String getA() {
        return a;
    }
    public void setA(String a) {
        this.a = a;
    }
    public String getB() {
        return b;
    }
    public void setB(String b) {
        this.b = b;
    }
    public String getC() {
        return c;
    }
    public void setC(String c) {
        this.c = c;
    }
    public String getD() {
        return d;
    }
    public void setD(String d) {
        this.d = d;
    }
    public String getE() {
        return e;
    }
    public void setE(String e) {
        this.e = e;
    }
    public String getF() {
        return f;
    }
    public void setF(String f) {
        this.f = f;
    }
    public String getG() {
        return g;
    }
    public void setG(String g) {
        this.g = g;
    }
    public String getH() {
        return h;
    }
    public void setH(String h) {
        this.h = h;
    }
    public String getH1() {
        return h1;
    }
    public void setH1(String h1) {
        this.h1 = h1;
    }
    public String getH2() {
        return h2;
    }
    public void setH2(String h2) {
        this.h2 = h2;
    }
    public String getlParcial() {
        return lParcial;
    }
    public void setlParcial(String lParcial) {
        this.lParcial = lParcial;
    }
    public String getlTotal() {
        return lTotal;
    }
    public void setlTotal(String lTotal) {
        this.lTotal = lTotal;
    }
    public String getlCortar() {
        return lCortar;
    }
    public void setlCortar(String lCortar) {
        this.lCortar = lCortar;
    }
    public String getPeso() {
        return peso;
    }
    public void setPeso(String peso) {
        this.peso = peso;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public String getCodigo(){
        return codigo;
    }
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }
    public String getEstructura(){
        return estructura;
    }
    public void setEstructura(String estructura){
        this.estructura = estructura;
    }
    public String getMaterial() {
        return material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
}