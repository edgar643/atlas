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
public class Infodet1 {

    private SimpleStringProperty descripcion;
    private SimpleStringProperty informacion;

    public Infodet1(final String descripcion, final String informacion) {
        this.descripcion = new SimpleStringProperty(descripcion);
        this.informacion = new SimpleStringProperty(informacion);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getInformacion() {
        return informacion.get();
    }

    public void setInformacion(String informacion) {
        this.informacion.set(informacion);
    }
}
