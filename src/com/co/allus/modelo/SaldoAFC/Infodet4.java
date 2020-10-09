/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.SaldoAFC;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class Infodet4 {

    private SimpleStringProperty descripcion4;
    private SimpleStringProperty informacion4;

    public Infodet4(final String descripcion4, final String informacion4) {
        this.descripcion4 = new SimpleStringProperty(descripcion4);
        this.informacion4 = new SimpleStringProperty(informacion4);
    }

    public String getDescripcion4() {
        return descripcion4.get();
    }

    public void setDescripcion4(String descripcion4) {
        this.descripcion4.set(descripcion4);
    }

    public String getInformacion4() {
        return informacion4.get();
    }

    public void setInformacion4(String informacion4) {
        this.informacion4.set(informacion4);
    }
}
