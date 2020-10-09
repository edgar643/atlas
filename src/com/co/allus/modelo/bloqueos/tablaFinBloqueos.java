/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bloqueos;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class tablaFinBloqueos {

    private SimpleStringProperty valor;

    public tablaFinBloqueos(String valor) {
        this.valor = new SimpleStringProperty(valor);

    }

    public String getValor() {
        return valor.get();
    }

    public void setValor(String valor) {
        this.valor.set(valor);
    }
}
