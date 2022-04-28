package com.torresj.cliente.vedicorp.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class DtoClienteProducto {
    private Cliente cliente;
    private Producto producto;
    private List<PedidoDetalle>detalle;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private BigDecimal tina;
    private String ven;
    private String fza;
    private String numero;

    private Date fechaConsulta;
    private String codigoSeleccionado;


    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public List<PedidoDetalle> getDetalle() {
        return detalle;
    }
    public void setDetalle(List<PedidoDetalle> detalle) {
        this.detalle = detalle;
    }
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    public BigDecimal getIgv() {
        return igv;
    }
    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public BigDecimal getTina() {
        return tina;
    }
    public void setTina(BigDecimal tina) {
        this.tina = tina;
    }
    public String getVen() {
        return ven;
    }
    public void setVen(String ven) {
        this.ven = ven;
    }
    public String getFza() {
        return fza;
    }
    public void setFza(String fza) {
        this.fza = fza;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Date getFechaConsulta() {
        return fechaConsulta;
    }
    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }
    public String getCodigoSeleccionado() {
        return codigoSeleccionado;
    }
    public void setCodigoSeleccionado(String codigoSeleccionado) {
        this.codigoSeleccionado = codigoSeleccionado;
    }

}
