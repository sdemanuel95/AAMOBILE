package com.tofitsolutions.armasdurasargentinas;

import java.sql.Date;

public class Merma {


    Integer id;
    String fecha;
    String referencia;
    String material;
    String descripcion;
    String cantidad;
    String UMB;
    String lote;
    String destinatario;
    String colada;
    String pesoPorBalanza;
    String kgTeorico;
    String kgProd;
    String kgDisponible;
    String codigo;
    String familia;

    public Merma() {
    }

    public Merma(Integer id, String fecha, String referencia, String material, String descripcion, String cantidad, String UMB, String lote, String destinatario, String colada, String pesoPorBalanza, String kgTeorico, String kgProd, String kgDisponible, String codigo, String familia) {
        this.id = id;
        this.fecha = fecha;
        this.referencia = referencia;
        this.material = material;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.UMB = UMB;
        this.lote = lote;
        this.destinatario = destinatario;
        this.colada = colada;
        this.pesoPorBalanza = pesoPorBalanza;
        this.kgTeorico = kgTeorico;
        this.kgProd = kgProd;
        this.kgDisponible = kgDisponible;
        this.codigo = codigo;
        this.familia = familia;
    }

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUMB() {
        return UMB;
    }

    public void setUMB(String UMB) {
        this.UMB = UMB;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getColada() {
        return colada;
    }

    public void setColada(String colada) {
        this.colada = colada;
    }

    public String getPesoPorBalanza() {
        return pesoPorBalanza;
    }

    public void setPesoPorBalanza(String pesoPorBalanza) {
        this.pesoPorBalanza = pesoPorBalanza;
    }

    public String getKgTeorico() {
        return kgTeorico;
    }

    public void setKgTeorico(String kgTeorico) {
        this.kgTeorico = kgTeorico;
    }

    public String getKgProd() {
        return kgProd;
    }

    public void setKgProd(String kgProd) {
        this.kgProd = kgProd;
    }

    public String getKgDisponible() {
        return kgDisponible;
    }

    public void setKgDisponible(String kgDisponible) {
        this.kgDisponible = kgDisponible;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }
}
