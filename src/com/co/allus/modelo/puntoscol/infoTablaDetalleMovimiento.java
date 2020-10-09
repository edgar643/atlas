/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author roberto.ceballos
 */
public class infoTablaDetalleMovimiento {

    private SimpleStringProperty descripcion;
    private SimpleStringProperty informacion;

    public infoTablaDetalleMovimiento(final String descripcion, final String valor) {
        this.descripcion = new SimpleStringProperty(descripcion);
        this.informacion = new SimpleStringProperty(valor);
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