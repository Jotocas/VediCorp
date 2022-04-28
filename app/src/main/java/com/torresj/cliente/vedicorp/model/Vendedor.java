package com.torresj.cliente.vedicorp.model;

public class Vendedor {
    private String ven;
    private String nom;
    private String clave;
    private String fza;
    private String mensajeError;

    public String getVen() {
        return this.ven;
    }

    public String getNom() {
        return this.nom;
    }

    public String getClave() {
        return this.clave;
    }

    public String getFza() {
        return this.fza;
    }

    public void setVen(String ven) {
        this.ven = ven;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setFza(String fza) {
        this.fza = fza;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
