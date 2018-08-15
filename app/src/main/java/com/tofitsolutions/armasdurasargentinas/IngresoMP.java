package com.tofitsolutions.armasdurasargentinas;


import java.sql.Date;

public class IngresoMP {
    private long id;
    private Date fecha;
    private String referencia;
    private String material;
    private String descripcion;
    private String cantidad;
    private String umb;
    private String lote;
    private String destinatario;
    private String colada;
    private String pesoPorBalanza;
    private String kgProd;
    private String kgTeorico;
    private String kgDisponible;

    public IngresoMP(long id, java.util.Date fecha, String referencia, String material, String descripcion,
                     String cantidad, String umb, String lote, String destinatario, String colada, String pesoPorBalanza) {
        this.id = id;
        this.fecha = (Date) fecha;
        this.referencia = referencia;
        this.material = material;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.umb = umb;
        this.lote = lote;
        this.destinatario = destinatario;
        this.colada = colada;
        this.pesoPorBalanza = pesoPorBalanza;
    }

    public IngresoMP(long id, java.util.Date fecha, String referencia, String material, String descripcion,
                     String cantidad, String umb, String lote, String destinatario, String colada, String pesoPorBalanza,String kgProd,String kgTeo, String kgDisp) {
        this.id = id;
        this.fecha = (Date) fecha;
        this.referencia = referencia;
        this.material = material;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.umb = umb;
        this.lote = lote;
        this.destinatario = destinatario;
        this.colada = colada;
        this.pesoPorBalanza = pesoPorBalanza;
    }


    public IngresoMP() {
        // TODO Auto-generated constructor stub
    }

    public String[] separarPorPipe (String linea) {
        linea = linea.replace('|', '}');
        linea = linea.substring(1);
        String[] lineaSeparada = linea.split("}");
        return lineaSeparada;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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

    public String getUmb() {
        return umb;
    }

    public void setUmb(String umb) {
        this.umb = umb;
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

    public String getKgProd() {
        return kgProd;
    }

    public void setKgProd(String kgProd) {
        this.kgProd = kgProd;
    }

    public String getKgTeorico() {
        return kgTeorico;
    }

    public void setKgTeorico(String kgTeorico) {
        this.kgTeorico = kgTeorico;
    }

    public String getKgDisponible() {
        return kgDisponible;
    }

    public void setKgDisponible(String kgDisponible) {
        this.kgDisponible = kgDisponible;
    }

}
