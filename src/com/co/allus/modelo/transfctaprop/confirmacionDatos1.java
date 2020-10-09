/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.transfctaprop;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class confirmacionDatos1 {

    private SimpleStringProperty cuenta_origen;
    private SimpleStringProperty tipo_cta_origen;
    private SimpleStringProperty cuenta_destino;
    private SimpleStringProperty tipo_cta_destino;

    public confirmacionDatos1(String ctaorigen, String tipoctaorigen, String ctadestino, String tipoctadestino) {
        this.cuenta_origen = new SimpleStringProperty(ctaorigen);
        this.tipo_cta_origen = new SimpleStringProperty(tipoctaorigen);
        this.cuenta_destino = new SimpleStringProperty(ctadestino);
        this.tipo_cta_destino = new SimpleStringProperty(tipoctadestino);
    }

    public String getCuenta_origen() {
        return cuenta_origen.get();
    }

    public void setCuenta_origen(String ctaorigen) {
        this.cuenta_origen.set(ctaorigen);
    }

    public String getTipo_cta_origen() {
        return tipo_cta_origen.get();
    }

    public void setTipo_cta_origen(String tipo_cta_origen) {
        this.tipo_cta_origen.set(tipo_cta_origen);
    }

    public String getCuenta_destino() {
        return cuenta_destino.get();
    }

    public void setCuenta_destino(String cuenta_destino) {
        this.cuenta_destino.set(cuenta_destino);
    }

    public String getTipo_cta_destino() {
        return tipo_cta_destino.get();
    }

    public void setTipo_cta_destino(String tipo_cta_destino) {
        this.tipo_cta_destino.set(tipo_cta_destino);
    }
}
