/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tpactivacion;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class tablaFinActivacion {

    private SimpleStringProperty valor;

    public tablaFinActivacion(String valor) {
        this.valor = new SimpleStringProperty(valor);

    }

    public String getValor() {
        return valor.get();
    }

    public void setValor(String valor) {
        this.valor.set(valor);
    }
}
