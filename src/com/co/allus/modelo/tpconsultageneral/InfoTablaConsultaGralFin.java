/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tpconsultageneral;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class InfoTablaConsultaGralFin {

    private SimpleStringProperty descripcion;
    private SimpleStringProperty informacion;

    public InfoTablaConsultaGralFin(String Descripcion, String Informacion) {

        this.descripcion = new SimpleStringProperty(Descripcion);
        this.informacion = new SimpleStringProperty(Informacion);


    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public String getInformacion() {
        return informacion.get();
    }

    public void setDescripcion(String Descripcion) {
        this.descripcion.set(Descripcion);
    }

    public void setInformacion(String Informacion) {
        this.informacion.set(Informacion);
    }
}
