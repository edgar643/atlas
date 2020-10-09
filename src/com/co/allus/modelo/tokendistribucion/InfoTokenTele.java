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
public class InfoTokenTele {

    private StringProperty colFecha;
    private StringProperty colTelefono;
    private StringProperty colCiudad;
    private StringProperty colEstado;
    private StringProperty colResultado;
    private StringProperty colObservacion;
    private StringProperty coljornada;
    private StringProperty colFechaVisita;

    public InfoTokenTele(String colEstado, String colFecha, String colCiudad, String colResultado, String colObservacion, String colTelefono, String coljornada, String colfechavisita) {
        //EstadoGestion%FechaEnvio%NombreCiudad%Resultado%Observcion%NumeroTelefono
        this.colEstado = new SimpleStringProperty(colEstado);
        this.colFecha = new SimpleStringProperty(colFecha);
        this.colCiudad = new SimpleStringProperty(colCiudad);
        this.colResultado = new SimpleStringProperty(colResultado);
        this.colObservacion = new SimpleStringProperty(colObservacion);
        this.colTelefono = new SimpleStringProperty(colTelefono);
        this.coljornada = new SimpleStringProperty(coljornada);
        this.colFechaVisita = new SimpleStringProperty(colfechavisita);

    }

    public String getColjornada() {
        return coljornada.get();
    }

    public void setColjornada(String coljornada) {
        this.coljornada.set(coljornada);
    }

    public String getColFechaVisita() {
        return colFechaVisita.get();
    }

    public void setColFechaVisita(String colFechaVisita) {
        this.colFechaVisita.set(colFechaVisita);
    }

    public String getColFecha() {
        return colFecha.get();
    }

    public void setColFecha(String colFecha) {
        this.colFecha.set(colFecha);
    }

    public String getColTelefono() {
        return colTelefono.get();
    }

    public void setColTelefono(String colTelefono) {
        this.colTelefono.set(colTelefono);
    }

    public String getColCiudad() {
        return colCiudad.get();
    }

    public void setColCiudad(String colCiudad) {
        this.colCiudad.set(colCiudad);
    }

    public String getColEstado() {
        return colEstado.get();
    }

    public void setColEstado(String colEstado) {
        this.colEstado.set(colEstado);
    }

    public String getColResultado() {
        return colResultado.get();
    }

    public void setColResultado(String colResultado) {
        this.colResultado.set(colResultado);
    }

    public String getColObservacion() {
        return colObservacion.get();
    }

    public void setcolObservacion(String colObservacion) {
        this.colObservacion.set(colObservacion);
    }
}
