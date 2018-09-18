package com.tofitsolutions.armasdurasargentinas;

public class Stock {
    private long id;
    private String familia, codMat, descripcion, tipoMaterial, kgteorico, kgprod, kgdisponible;

    public Stock(long iD, String familia, String codMat, String descripcion, String tipoMaterial,
                 String kGTeorico, String kGProd, String kGDisponible) {
        super();
        id = iD;
        familia = familia;
        codMat = codMat;
        descripcion = descripcion;
        tipoMaterial = tipoMaterial;
        kgteorico = kGTeorico;
        kgprod = kGProd;
        kgdisponible = kGDisponible;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getCodMat() {
        return codMat;
    }

    public void setCodMat(String codMat) {
        this.codMat = codMat;
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

    public String getKgteorico() {
        return kgteorico;
    }

    public void setKgteorico(String kgteorico) {
        this.kgteorico = kgteorico;
    }

    public String getKgprod() {
        return kgprod;
    }

    public void setKgprod(String kgprod) {
        this.kgprod = kgprod;
    }

    public String getKgdisponible() {
        return kgdisponible;
    }

    public void setKgdisponible(String kgdisponible) {
        this.kgdisponible = kgdisponible;
    }
}
