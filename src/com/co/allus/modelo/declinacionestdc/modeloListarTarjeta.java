/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.declinacionestdc;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author roberto.ceballos
 */
public class modeloListarTarjeta {

    private SimpleStringProperty numeroTarjeta;
    private SimpleStringProperty tipoTarjeta;
    private SimpleStringProperty bloqueoTarjeta;

    public modeloListarTarjeta(String numTarjeta, String tipTarjeta, String bloTarjeta) {
        this.numeroTarjeta = new SimpleStringProperty(numTarjeta);
        this.tipoTarjeta = new SimpleStringProperty(tipTarjeta);
        this.bloqueoTarjeta = new SimpleStringProperty(bloTarjeta);
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
    
}
