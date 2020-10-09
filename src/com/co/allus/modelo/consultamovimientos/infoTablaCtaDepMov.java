/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultamovimientos;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTablaCtaDepMov {

    private SimpleStringProperty cuenta;

    public infoTablaCtaDepMov(final String cuenta) {
        this.cuenta = new SimpleStringProperty(cuenta);
    }

    public String getCuenta() {
        return cuenta.get();
    }

    public void setCuenta(String cuenta) {
        this.cuenta.set(cuenta);
    }
}
