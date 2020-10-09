/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultatrm;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoTablaHistorico {

    private SimpleStringProperty colfecha;
    private SimpleStringProperty colvalor;

    public infoTablaHistorico(final String colfecha, final String colvalor) {
        this.colfecha = new SimpleStringProperty(colfecha);
        this.colvalor = new SimpleStringProperty(colvalor);
    }

    public String getColfecha() {
        return colfecha.get();
    }

    public void setColfecha(String colfecha) {
        this.colfecha.set(colfecha);
    }

    public String getColvalor() {
        return colvalor.get();
    }

    public void setColvalor(String colvalor) {
        this.colvalor.set(colvalor);
    }
}
