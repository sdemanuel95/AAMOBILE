package com.tofitsolutions.armasdurasargentinas;


    public class Pedido { //ACA FALTA PONER PLANO, SECTOR Y SUBSECTOR PERO SI NO JODE EN NADA, LO DEJO ASI
        private long id;
        private String entrega;
        private String codigo;
        private String cliente;
        private String obra;
        private String descripcion;
        private String tipo;
        private String totalKg;
        private String cuatrocomados;
        private String seis;
        private String ocho;
        private String diez;
        private String doce;
        private String dieciseis;
        private String veinte;
        private String veinticinco;
        private String treintaydos;
        private String otros;
        private String estado;
        private String oc;
        String pedido;

        public Pedido(long id, String entrega, String codigo, String cliente, String obra, String descripcion, String tipo,
                      String totalKg, String cuatrocomados, String seis, String ocho, String diez, String doce, String dieciseis,
                      String veinte, String veinticinco, String treintaydos, String estado, String pedido, String oc) {
            super();
            this.id = id;
            this.entrega = entrega;
            this.codigo = codigo;
            this.cliente = cliente;
            this.obra = obra;
            this.descripcion = descripcion;
            this.tipo = tipo;
            this.totalKg = totalKg;
            this.cuatrocomados = cuatrocomados;
            this.seis = seis;
            this.ocho = ocho;
            this.diez = diez;
            this.doce = doce;
            this.dieciseis = dieciseis;
            this.veinte = veinte;
            this.veinticinco = veinticinco;
            this.treintaydos = treintaydos;
            this.estado = estado;
            this.pedido = pedido;
            this.oc = oc;
        }

        public Pedido(long id, String entrega, String codigo, String cliente, String obra, String descripcion, String tipo,
                      String totalKg, String cuatrocomados, String seis, String ocho, String diez, String doce, String dieciseis,
                      String veinte, String veinticinco, String treintaydos, String otros, String estado, String oc,
                      String pedido) {
            super();
            this.id = id;
            this.entrega = entrega;
            this.codigo = codigo;
            this.cliente = cliente;
            this.obra = obra;
            this.descripcion = descripcion;
            this.tipo = tipo;
            this.totalKg = totalKg;
            this.cuatrocomados = cuatrocomados;
            this.seis = seis;
            this.ocho = ocho;
            this.diez = diez;
            this.doce = doce;
            this.dieciseis = dieciseis;
            this.veinte = veinte;
            this.veinticinco = veinticinco;
            this.treintaydos = treintaydos;
            this.otros = otros;
            this.estado = estado;
            this.oc = oc;
            this.pedido = pedido;
        }

        public String getOtros() {
            return otros;
        }

        public void setOtros(String otros) {
            this.otros = otros;
        }

        public String getOc() {
            return oc;
        }

        public void setOc(String oc) {
            this.oc = oc;
        }

        public String getPedido() {
            return pedido;
        }

        public void setPedido(String pedido) {
            this.pedido = pedido;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getEntrega() {
            return entrega;
        }

        public void setEntrega(String entrega) {
            this.entrega = entrega;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public String getObra() {
            return obra;
        }

        public void setObra(String obra) {
            this.obra = obra;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getTotalKg() {
            return totalKg;
        }

        public void setTotalKg(String totalKg) {
            this.totalKg = totalKg;
        }

        public String getCuatrocomados() {
            return cuatrocomados;
        }

        public void setCuatrocomados(String cuatrocomados) {
            this.cuatrocomados = cuatrocomados;
        }

        public String getSeis() {
            return seis;
        }

        public void setSeis(String seis) {
            this.seis = seis;
        }

        public String getOcho() {
            return ocho;
        }

        public void setOcho(String ocho) {
            this.ocho = ocho;
        }

        public String getDiez() {
            return diez;
        }

        public void setDiez(String diez) {
            this.diez = diez;
        }

        public String getDoce() {
            return doce;
        }

        public void setDoce(String doce) {
            this.doce = doce;
        }

        public String getDieciseis() {
            return dieciseis;
        }

        public void setDieciseis(String dieciseis) {
            this.dieciseis = dieciseis;
        }

        public String getVeinte() {
            return veinte;
        }

        public void setVeinte(String veinte) {
            this.veinte = veinte;
        }

        public String getVeinticinco() {
            return veinticinco;
        }

        public void setVeinticinco(String veinticinco) {
            this.veinticinco = veinticinco;
        }

        public String getTreintaydos() {
            return treintaydos;
        }

        public void setTreintaydos(String treintaydos) {
            this.treintaydos = treintaydos;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
