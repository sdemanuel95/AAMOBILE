package com.tofitsolutions.armasdurasargentinas;

import java.io.Serializable;

/**
 * Created by Abel on 5/11/2017.
 */

public class CodigoMP implements Serializable{

    private final long serialVersionUID = 1L;
    private long id;
    private String codSap;
    private String familia;
    private String descripcion;
    private String tipoMaterial;

    public CodigoMP(long id, String codSap, String familia, String descripcion, String tipoMaterial) {
        this.id = id;
        this.codSap = codSap;
        this.familia = familia;
        this.descripcion = descripcion;
        this.tipoMaterial = tipoMaterial;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodSap() {
        return codSap;
    }

    public void setCodSap(String codSap) {
        this.codSap = codSap;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }
}
