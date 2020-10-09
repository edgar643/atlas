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
public class Infodet2 {

    private SimpleStringProperty descripcion2;
    private SimpleStringProperty informacion2;

    public Infodet2(final String descripcion2, final String informacion2) {
        this.descripcion2 = new SimpleStringProperty(descripcion2);
        this.informacion2 = new SimpleStringProperty(informacion2);
    }

    public String getDescripcion2() {
        return descripcion2.get();
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2.set(descripcion2);
    }

    public String getInformacion2() {
        return informacion2.get();
    }

    public void setInformacion2(String informacion2) {
        this.informacion2.set(informacion2);
    }
}
