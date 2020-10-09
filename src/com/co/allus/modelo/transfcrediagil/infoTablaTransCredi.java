/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.transfcrediagil;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoTablaTransCredi {

    private SimpleStringProperty cuenta_destino;
    private SimpleStringProperty valor_prestamo;

    public infoTablaTransCredi(String cuenta_destino, String valor_prestamo) {
        this.cuenta_destino = new SimpleStringProperty(cuenta_destino);
        this.valor_prestamo = new SimpleStringProperty(valor_prestamo);
    }

    public String getCuenta_destino() {
        return cuenta_destino.get();
    }

    public void setCuenta_destino(String cuenta_destino) {
        this.cuenta_destino.set(cuenta_destino);
    }

    public String getValor_prestamo() {
        return valor_prestamo.get();
    }

    public void setValor_prestamo(String valor_prestamo) {
        this.cuenta_destino.set(valor_prestamo);
    }
}
