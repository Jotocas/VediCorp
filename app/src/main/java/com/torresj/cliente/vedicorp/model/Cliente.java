package com.torresj.cliente.vedicorp.model;

import com.torresj.cliente.vedicorp.utils.UValidador;

import java.io.Serializable;

public class Cliente implements Serializable {
    private String nclte;
    private String clte;
    private String nomb;
    private String dire;
    private String ciud;
    private String cond;
    private String canal;
    private String tdoc;
    private String tipo;
    private String ruc;
    private String mensajeError;
    public static final String FIELDS = "nclte,clte,nomb,dire,ciud,cond,canal,tdoc,tipo,ruc";

    public String getNclte() {
        return this.nclte;
    }

    public String getClte() {
        return this.clte;
    }

    public String getNomb() {
        return this.nomb;
    }

    public String getDire() {
        return this.dire;
    }

    public String getCiud() {
        return this.ciud;
    }

    public String getCond() {
        return this.cond;
    }

    public String getCanal() {
        return this.canal;
    }

    public String getTdoc() {
        if (!UValidador.estaVacio(this.ruc)) {
            this.tdoc = "PXF";
        } else {
            this.tdoc = "PXB";
        }
        return this.tdoc;
    }

    public void setNclte(String nclte) {
        this.nclte = nclte;
    }

    public void setClte(String clte) {
        this.clte = clte;
    }

    public void setNomb(String nomb) {
        this.nomb = nomb;
    }

    public void setDire(String dire) {
        this.dire = dire;
    }

    public void setCiud(String ciud) {
        this.ciud = ciud;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public void setTdoc(String tdoc) {
        this.tdoc = tdoc;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTd() {
        if (!UValidador.esNulo(this.tdoc)) {
            if (this.tdoc.equals("PXB")) {
                return "11";
            }
            if (this.tdoc.equals("PXF")) {
                return "12";
            }
        }
        return null;
    }

    public String getRuc() {
        return this.ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
