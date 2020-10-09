/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosterceros;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class ModelPagofin1 {

    private SimpleStringProperty nunconv;
    private SimpleStringProperty valorPagado;

    public ModelPagofin1(String numconv, String valorPagado) {
        this.nunconv = new SimpleStringProperty(numconv);
        this.valorPagado = new SimpleStringProperty(valorPagado);
    }

    public String getNumCredito() {
        return nunconv.get();
    }

    public void setNumCredito(String numCredito) {
        this.nunconv.setValue(numCredito);
    }

    public String getValorPagado() {
        return valorPagado.get();
    }

    public void setValorPagado(String valorPagado) {
        this.valorPagado.set(valorPagado);
    }
}
