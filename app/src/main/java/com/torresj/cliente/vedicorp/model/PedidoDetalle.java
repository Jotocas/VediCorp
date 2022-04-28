package com.torresj.cliente.vedicorp.model;

import com.torresj.cliente.vedicorp.utils.UValidador;

import java.math.BigDecimal;

public class PedidoDetalle {
    private String dcto;
    private String numero;
    private String numite;
    private String codigo;
    private String combo;
    private String descri;
    private String td;
    private String tk;
    private BigDecimal cant;
    private BigDecimal precio;
    private BigDecimal igv;
    private BigDecimal tot1;
    private BigDecimal d1;
    private BigDecimal d2;
    private String clte;
    private String nclte;
    private String paso;
    private BigDecimal stock;
    private BigDecimal reserva;
    private BigDecimal stock2;
    public static final String FIELDS = " dcto, numero, numite, codigo, combo, descri, td, tk, cant, precio, igv, tot1, clte, nclte, paso, stock, reserva, stock2,d1,d2 ";

    public String getDcto() {
        return this.dcto;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getNumite() {
        return this.numite;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getCombo() {
        return this.combo;
    }

    public String getDescri() {
        return this.descri;
    }

    public String getTd() {
        return this.td;
    }

    public String getTk() {
        return this.tk;
    }

    public BigDecimal getCant() {
        if (this.cant == null) {
            this.cant = BigDecimal.valueOf(0);
        }
        return this.cant;
    }

    public BigDecimal getPrecio() {
        return this.precio;
    }

    public BigDecimal getIgv() {
        return this.igv;
    }

    public BigDecimal getTot1() {
        return this.tot1;
    }

    public String getClte() {
        return this.clte;
    }

    public String getNclte() {
        return this.nclte;
    }

    public String getPaso() {
        return this.paso;
    }

    public BigDecimal getStock() {
        return this.stock;
    }

    public BigDecimal getReserva() {
        return this.reserva;
    }

    public BigDecimal getStock2() {
        return this.stock2;
    }

    public void setDcto(String dcto) {
        this.dcto = dcto;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setNumite(String numite) {
        this.numite = numite;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public void setCant(BigDecimal cant) {
        this.cant = cant;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public void setTot1(BigDecimal tot1) {
        this.tot1 = tot1;
    }

    public void setClte(String clte) {
        this.clte = clte;
    }

    public void setNclte(String nclte) {
        this.nclte = nclte;
    }

    public void setPaso(String paso) {
        this.paso = paso;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public void setReserva(BigDecimal reserva) {
        this.reserva = reserva;
    }

    public void setStock2(BigDecimal stock2) {
        this.stock2 = stock2;
    }

    public BigDecimal getD1() {
        if (UValidador.esNulo(this.d1)) {
            this.d1 = BigDecimal.ZERO;
        }

        return this.d1;
    }

    public BigDecimal getD2() {
        if (UValidador.esNulo(this.d2)) {
            this.d2 = BigDecimal.ZERO;
        }

        return this.d2;
    }

    public void addOne() {
        this.cant=  this.cant.add(BigDecimal.ONE);
    }

    public void removeOne() {
        this.cant=  this.cant.subtract(BigDecimal.ONE);
    }

    public void setD1(BigDecimal d1) {
        this.d1 = d1;
    }

    public void setD2(BigDecimal d2) {
        this.d2 = d2;
    }
}
