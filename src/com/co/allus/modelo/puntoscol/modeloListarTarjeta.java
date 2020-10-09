/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author roberto.ceballos
 */
public class modeloListarTarjeta {

    private SimpleStringProperty numerotarjeta;
    private SimpleStringProperty desctarjeta;
    private SimpleStringProperty franquiciatarjeta;
    private SimpleStringProperty numtdcori;

    public modeloListarTarjeta(String numero_tarjeta, String franquicia_tarjeta, String tipo_tarjeta, String numtdcorignial) {

        this.numerotarjeta = new SimpleStringProperty(numero_tarjeta);
        this.desctarjeta = new SimpleStringProperty(tipo_tarjeta);
        this.franquiciatarjeta = new SimpleStringProperty(franquicia_tarjeta);
        this.numtdcori = new SimpleStringProperty(numtdcorignial);
    }

    public String getNumerotarjeta() {
        return numerotarjeta.get();
    }

    public void setNumerotarjeta(String numerotarjeta) {
        this.numerotarjeta.set(numerotarjeta);
    }

    public String getDesctarjeta() {
        return desctarjeta.get();
    }

    public void setDesctarjeta(String desctarjeta) {
        this.desctarjeta.set(desctarjeta);
    }

    public String getFranquiciatarjeta() {
        return franquiciatarjeta.get();
    }

    public void setFranquiciatarjeta(String franquiciatarjeta) {
        this.franquiciatarjeta.set(franquiciatarjeta);
    }

    public String getNumtdcori() {
        return numtdcori.get();
    }

    public void setNumtdcori(String numtdcori) {
        this.numtdcori.set(numtdcori);
    }
}
