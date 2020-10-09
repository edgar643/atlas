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
public class ModelPagofin2 {

    private SimpleStringProperty comprobante;
    private SimpleStringProperty numcta;
    private SimpleStringProperty tipocta;
    private SimpleStringProperty fechapago;

    public ModelPagofin2(String comprobante, String numcta, String tipocta, String fechapago) {
        this.comprobante = new SimpleStringProperty(comprobante);
        this.numcta = new SimpleStringProperty(numcta);
        this.tipocta = new SimpleStringProperty(tipocta);
        this.fechapago = new SimpleStringProperty(fechapago);
    }

    public String getComprobante() {
        return comprobante.get();
    }

    public void setComprobante(String comprobante) {
        this.comprobante.set(comprobante);
    }

    public String getNumcta() {
        return numcta.get();
    }

    public void setNumcta(String numcta) {
        this.numcta.set(numcta);
    }

    public String getTipocta() {
        return tipocta.get();
    }

    public void setTipocta(String tipocta) {
        this.tipocta.set(tipocta);
    }

    public String getFechapago() {
        return fechapago.get();
    }

    public void setFechapago(String fechapago) {
        this.fechapago.set(fechapago);
    }
}
