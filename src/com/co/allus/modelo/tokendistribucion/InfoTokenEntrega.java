/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokendistribucion;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class InfoTokenEntrega {

    private StringProperty colEstado;
    private StringProperty colFecha;
    private StringProperty colResultado;
    private StringProperty colDireccion;
    private StringProperty colCiudad;
    private StringProperty colObservacion;

    public InfoTokenEntrega(String colEstado, String colFecha, String colResultado, String colDireccion, String colCiudad, String colObservacion) {
        //DescripcionEstado%FechaEnvio%GestionEntrega%Direccion%NombreCiudad%Observacion
        this.colEstado = new SimpleStringProperty(colEstado);
        this.colFecha = new SimpleStringProperty(colFecha);
        this.colResultado = new SimpleStringProperty(colResultado);
        this.colDireccion = new SimpleStringProperty(colDireccion);
        this.colCiudad = new SimpleStringProperty(colCiudad);
        this.colObservacion = new SimpleStringProperty(colObservacion);

    }

    public String getColEstado() {
        return colEstado.get();
    }

    public void setColEstado(String colEstado) {
        this.colEstado.set(colEstado);
    }

    public String getColFecha() {
        return colFecha.get();
    }

    public void setColFecha(String colFecha) {
        this.colFecha.set(colFecha);
    }

    public String getColResultado() {
        return colResultado.get();
    }

    public void setColResultado(String colResultado) {
        this.colResultado.set(colResultado);
    }

    public String getColDireccion() {
        return colDireccion.get();
    }

    public void setColDireccion(String colDireccion) {
        this.colDireccion.set(colDireccion);
    }

    public String getColCiudad() {
        return colCiudad.get();
    }

    public void setColCiudad(String colCiudad) {
        this.colCiudad.set(colCiudad);
    }

    public String getColObservacion() {
        return colObservacion.get();
    }

    public void setColObservacion(String colObservacion) {
        this.colObservacion.set(colObservacion);
    }
}
