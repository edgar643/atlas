/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoTokenDisponibles {

    private StringProperty colserial;
    private StringProperty colfecha_exp;

    public infoTokenDisponibles(String colserial, String colfecha_exp) {

        this.colserial = new SimpleStringProperty(colserial);
        this.colfecha_exp = new SimpleStringProperty(colfecha_exp);

    }

    public String getColserial() {
        return colserial.get();
    }
//    public StringProperty colserialProperty() {
//        return colserial;
//    }

    public StringProperty colfecha_expProperty() {
        return colfecha_exp;
    }

    public void setColserial(String colserial) {
        this.colserial.set(colserial);
    }

    public void setColfecha_exp(String colfecha_exp) {
        this.colfecha_exp.set(colfecha_exp);
    }
}
