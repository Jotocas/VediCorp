package com.torresj.cliente.vedicorp.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Producto implements Serializable {
    private String ncodigo;
    private String desc;
    private BigDecimal stock;
    private BigDecimal precio;
    private BigDecimal cae;
    private String combo;
    private BigDecimal reserva;
    private String codigo;
    private BigDecimal igv;
    private BigDecimal cantidad;
    private String mensajeError;


    public static final String FIELDS = "ncodigo,desc,stock,precio,cae,combo,reserva,codigo,igv";


    public String getNcodigo() {
        return this.ncodigo;
    }

    public String getDesc() {
        return this.desc;
    }

    public BigDecimal getStock() {
        return this.stock;
    }

    public BigDecimal getPrecio() {
        return this.precio;
    }

    public BigDecimal getCae() {
        return this.cae;
    }

    public void setNcodigo(String ncodigo) {
        this.ncodigo = ncodigo;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setCae(BigDecimal cae) {
        this.cae = cae;
    }

    public BigDecimal getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        System.out.println("que pasa con la cantidad");
        System.out.println(cantidad);
        this.cantidad = cantidad;
    }

    public String getCombo() {
        return this.combo;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }

    public BigDecimal getReserva() {
        return this.reserva;
    }

    public void setReserva(BigDecimal reserva) {
        this.reserva = reserva;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getIgv() {
        return this.igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
