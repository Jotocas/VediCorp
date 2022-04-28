package com.torresj.cliente.vedicorp.model;

import java.math.BigDecimal;

public class DtoPedidoClienteResumen {
    private String clienteCodigo;
    private String clienteNombre;
    private BigDecimal montoBruto;
    private BigDecimal montoIgv;
    private BigDecimal montoTotal;

    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private BigDecimal tina;
    private String mensajeError;

    public String getClienteCodigo() {
        return this.clienteCodigo;
    }

    public String getClienteNombre() {
        return this.clienteNombre;
    }

    public BigDecimal getMontoBruto() {
        return this.montoBruto;
    }

    public BigDecimal getMontoIgv() {
        return this.montoIgv;
    }

    public BigDecimal getMontoTotal() {
        return this.montoTotal;
    }

    public void setClienteCodigo(String clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public void setMontoBruto(BigDecimal montoBruto) {
        this.montoBruto = montoBruto;
    }

    public void setMontoIgv(BigDecimal montoIgv) {
        this.montoIgv = montoIgv;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
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
}
