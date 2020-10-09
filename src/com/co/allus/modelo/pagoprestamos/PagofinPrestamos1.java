/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagoprestamos;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class PagofinPrestamos1 {

    private SimpleStringProperty numCredito;
    private SimpleStringProperty valorPagado;

    public PagofinPrestamos1(String numCredito, String valorPagado) {
        this.numCredito = new SimpleStringProperty(numCredito);
        this.valorPagado = new SimpleStringProperty(valorPagado);
    }

    public String getNumCredito() {
        return numCredito.get();
    }

    public void setNumCredito(String numCredito) {
        this.numCredito.setValue(numCredito);
    }

    public String getValorPagado() {
        return valorPagado.get();
    }

    public void setValorPagado(String valorPagado) {
        this.valorPagado.set(valorPagado);
    }
}
