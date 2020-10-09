/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.saldos;

import javafx.beans.property.SimpleStringProperty;

public class ConsultaSaldoTabla1 {

    private final SimpleStringProperty entidad = new SimpleStringProperty("");
    private final SimpleStringProperty tipoCuenta = new SimpleStringProperty("");
    private final SimpleStringProperty numeroCuenta = new SimpleStringProperty("");

    public ConsultaSaldoTabla1() {
        this("", "", "");
    }

    public ConsultaSaldoTabla1(String entidad, String tipoCuenta, String numeroCuenta) {
        setEntidad(entidad);
        setTipoCuenta(tipoCuenta);
        setNumeroCuenta(numeroCuenta);
    }

    public String getEntidad() {
        return entidad.get();
    }

    public void setEntidad(String fname) {
        entidad.set(fname);
    }

    public String getTipoCuenta() {
        return tipoCuenta.get();
    }

    public void setTipoCuenta(String fName) {
        tipoCuenta.set(fName);
    }

    public String getNumeroCuenta() {
        return numeroCuenta.get();
    }

    public void setNumeroCuenta(String fName) {
        numeroCuenta.set(fName);
    }
}
