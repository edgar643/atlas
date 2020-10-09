/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoDetalleAE {

    private SimpleStringProperty colFechaEnvio;
    private SimpleStringProperty colHoraEnvio;
    private SimpleStringProperty colCostoNovedad;
    private SimpleStringProperty colVigencia;
    private SimpleStringProperty colDestino;
    private SimpleStringProperty colEstado;

    public infoDetalleAE(String colFechaEnvio, String colHoraEnvio, String colCostoNovedad, String colVigencia, String colDestino, String colEstado) {
        this.colFechaEnvio = new SimpleStringProperty(colFechaEnvio);
        this.colHoraEnvio = new SimpleStringProperty(colHoraEnvio);
        this.colCostoNovedad = new SimpleStringProperty(colCostoNovedad);
        this.colVigencia = new SimpleStringProperty(colVigencia);
        this.colDestino = new SimpleStringProperty(colDestino);
        this.colEstado = new SimpleStringProperty(colEstado);
    }

    public String getColFechaEnvio() {
        return colFechaEnvio.get();
    }

    public String getColHoraEnvio() {
        return colHoraEnvio.get();
    }

    public String getColCostoNovedad() {
        return colCostoNovedad.get();
    }

    public String getColVigencia() {
        return colVigencia.get();
    }

    public String getColDestino() {
        return colDestino.get();
    }

    public String getColEstado() {
        return colEstado.get();
    }

    public void setColFechaEnvio(String colFechaEnvio) {
        this.colFechaEnvio.set(colFechaEnvio);
    }

    public void setColHoraEnvio(String colHoraEnvio) {
        this.colHoraEnvio.set(colHoraEnvio);
    }

    public void setColCostoNovedad(String colCostoNovedad) {
        this.colCostoNovedad.set(colCostoNovedad);
    }

    public void setColVigencia(String colVigencia) {
        this.colVigencia.set(colVigencia);
    }

    public void setColDestino(String colDestino) {
        this.colDestino.set(colDestino);
    }

    public void setColEstado(String colEstado) {
        this.colEstado.set(colEstado);
    }
}
