/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultadepositos;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class Infodet3 {

    private SimpleStringProperty descripcion3;
    private SimpleStringProperty informacion3;

    public Infodet3(final String descripcion3, final String informacion3) {
        this.descripcion3 = new SimpleStringProperty(descripcion3);
        this.informacion3 = new SimpleStringProperty(informacion3);
    }

    public String getDescripcion3() {
        return descripcion3.get();
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3.set(descripcion3);
    }

    public String getInformacion3() {
        return informacion3.get();
    }

    public void setInformacion3(String informacion3) {
        this.informacion3.set(informacion3);
    }
}
