/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.saldostdc;

/**
 *
 * @author alexander.lopez.o
 */
public class DatosTarjeta {

    private String numero = "";
    private String tipoTarjeta = "";
    private String bloqueoTarjeta = "";

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getBloqueoTarjeta() {
        return bloqueoTarjeta;
    }

    public void setBloqueoTarjeta(String bloqueoTarjeta) {
        this.bloqueoTarjeta = bloqueoTarjeta;
    }
}
