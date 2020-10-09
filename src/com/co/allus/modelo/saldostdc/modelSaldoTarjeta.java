/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.saldostdc;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class modelSaldoTarjeta {

    private SimpleStringProperty numeroTarjeta;
    private SimpleStringProperty tipoTarjeta;
    private SimpleStringProperty bloqueoTarjeta;
    private SimpleStringProperty mascaraNumero;

    public modelSaldoTarjeta(String numtarjeta, String tipotarjeta, String bloqueotarjeta) {

        this.numeroTarjeta = new SimpleStringProperty(numtarjeta);
        this.tipoTarjeta = new SimpleStringProperty(tipotarjeta);
        this.bloqueoTarjeta = new SimpleStringProperty(bloqueotarjeta);
        if (!numtarjeta.equals("")) {
            this.mascaraNumero = new SimpleStringProperty(enmascararNumero(numtarjeta, 6));
        } else {
            this.mascaraNumero = new SimpleStringProperty("");
        }
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta.get();
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta.set(numeroTarjeta);
    }

    public String getTipoTarjeta() {
        return tipoTarjeta.get();
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta.set(tipoTarjeta);
    }

    public String getBloqueoTarjeta() {
        return bloqueoTarjeta.get();
    }

    public void setBloqueoTarjeta(String bloqueoTarjeta) {
        this.bloqueoTarjeta.set(bloqueoTarjeta);
    }

    public String getMascaraNumero() {
        return mascaraNumero.get();
    }

    public void setMascaraNumero(String mascaraNumero) {
        this.mascaraNumero.set(mascaraNumero);
    }

    public static String enmascararNumero(String numero, int digitosVisibles) {
        int puntoCorte = numero.length() - digitosVisibles;
        String salida = "**********" + numero.substring(puntoCorte);
        return salida;
    }
}
