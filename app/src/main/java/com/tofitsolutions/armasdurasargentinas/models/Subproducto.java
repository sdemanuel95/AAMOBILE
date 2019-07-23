package com.tofitsolutions.armasdurasargentinas.models;

import java.io.Serializable;

public class Subproducto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String item;
	boolean subproducto;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public boolean isSubproducto() {
		return subproducto;
	}

	public void setSubproducto(boolean subproducto) {
		this.subproducto = subproducto;
	}

	public Subproducto(String item, boolean subproducto) {
		super();
		this.item = item;
		this.subproducto = subproducto;
	}

	public Subproducto() {

	}

}
