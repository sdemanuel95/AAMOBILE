package com.tofitsolutions.armasdurasargentinas.models;

public class ResumenPedidoItem {

	public String item;
	public String diametro;
	public String totalUn;
	public String totalKg;
	public String producidaUn;
	public String producidaKg;
	public String pendientesUn;
	public String pendientesKg;

	public ResumenPedidoItem(String item, String diametro, String totalUn, String totalKg, String producidaUn,
			String producidaKg, String pendientesUn, String pendientesKg) {
		super();
		this.item = item;
		this.diametro = diametro;
		this.totalUn = totalUn;
		this.totalKg = totalKg;
		this.producidaUn = producidaUn;
		this.producidaKg = producidaKg;
		this.pendientesUn = pendientesUn;
		this.pendientesKg = pendientesKg;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDiametro() {
		return diametro;
	}
	public void setDiametro(String diametro) {
		this.diametro = diametro;
	}
	public String getTotalUn() {
		return totalUn;
	}
	public void setTotalUn(String totalUn) {
		this.totalUn = totalUn;
	}
	public String getTotalKg() {
		return totalKg;
	}
	public void setTotalKg(String totalKg) {
		this.totalKg = totalKg;
	}
	public String getProducidaUn() {
		return producidaUn;
	}
	public void setProducidaUn(String producidaUn) {
		this.producidaUn = producidaUn;
	}
	public String getProducidaKg() {
		return producidaKg;
	}
	public void setProducidaKg(String producidaKg) {
		this.producidaKg = producidaKg;
	}
	public String getPendientesUn() {
		return pendientesUn;
	}
	public void setPendientesUn(String pendientesUn) {
		this.pendientesUn = pendientesUn;
	}
	public String getPendientesKg() {
		return pendientesKg;
	}
	public void setPendientesKg(String pendientesKg) {
		this.pendientesKg = pendientesKg;
	}
	
	
}
