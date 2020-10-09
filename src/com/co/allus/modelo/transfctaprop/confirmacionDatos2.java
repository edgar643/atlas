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
public class confirmacionDatos2 {

    private SimpleStringProperty valor;

    public confirmacionDatos2(String valor) {
        this.valor = new SimpleStringProperty(valor);

    }

    public String getValor() {
        return valor.get();
    }

    public void setValor(String valor) {
        this.valor.set(valor);
    }
}
