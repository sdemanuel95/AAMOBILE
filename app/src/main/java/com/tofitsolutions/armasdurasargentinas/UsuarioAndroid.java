package com.tofitsolutions.armasdurasargentinas;

import java.io.Serializable;

public class UsuarioAndroid implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long id;
    private String codigo;
    private String nombre;
    private String apellido;
    private String contrasenia;
    private String fechaDeNacimiento;
    private boolean produccion;
    private boolean stock;
    private boolean despacho;
    private boolean descarga;
    private boolean stockinicial;
    private boolean inventario;


    public UsuarioAndroid() {

    }
    public UsuarioAndroid(long id, String codigo, String nombre, String apellido, String contrasenia,
                          String fechaDeNacimiento) {
        super();
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }


    public UsuarioAndroid(long id, String codigo, String nombre, String apellido, String contrasenia,
                          String fechaDeNacimiento,boolean produccion,boolean stock, boolean despacho, boolean descarga,
                          boolean stockinicial, boolean inventario) {
        super();
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.produccion = produccion;
        this.stock = stock;
        this.despacho = despacho;
        this.descarga = descarga;
        this.stockinicial = stockinicial;
        this.inventario = inventario;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContraseña(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }





    public boolean isStock() {
        return stock;
    }


    public void setStock(boolean stock) {
        this.stock = stock;
    }


    public boolean isDespacho() {
        return despacho;
    }


    public void setDespacho(boolean despacho) {
        this.despacho = despacho;
    }


    public boolean isDescarga() {
        return descarga;
    }


    public void setDescarga(boolean descarga) {
        this.descarga = descarga;
    }


    public boolean isStockinicial() {
        return stockinicial;
    }


    public void setStockinicial(boolean stockinicial) {
        this.stockinicial = stockinicial;
    }


    public boolean isInventario() {
        return inventario;
    }


    public void setInventario(boolean inventario) {
        this.inventario = inventario;
    }


    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }


    public boolean isProduccion() {
        return produccion;
    }


    public void setProduccion(boolean produccion) {
        this.produccion = produccion;
    }



}
