/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tpmovimientos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class InfoTablaMovFin {
    //850,069,000,TRANSACCION EXITOSA,TipoTarjeta,numtarjeta,contadorMovimientos,##fechaTransaccion##DescripcionTransaccion##DescripcionComercio##ValorTransaccion;...~  

    private StringProperty colFechaTransaccion;
    private StringProperty colDescripcionTransaccion;
    private StringProperty colDescripcionComercio;
    private StringProperty colValorTransaccion;

    public InfoTablaMovFin(String colFechaTransaccion, String colDescripcionTransaccion, String colDescripcionComercio, String colValorTransaccion) {

        this.colFechaTransaccion = new SimpleStringProperty(colFechaTransaccion);
        this.colDescripcionTransaccion = new SimpleStringProperty(colDescripcionTransaccion);
        this.colDescripcionComercio = new SimpleStringProperty(colDescripcionComercio);
        this.colValorTransaccion = new SimpleStringProperty(colValorTransaccion);

    }

    public String getColFechaTransaccion() {
        return colFechaTransaccion.get();
    }

    public String getColDescripcionTransaccion() {
        return colDescripcionTransaccion.get();
    }

    public String getColDescripcionComercio() {
        return colDescripcionComercio.get();
    }

    public String getColValorTransaccion() {
        return colValorTransaccion.get();
    }

    public void setColFechaTransaccion(String colFechaTransaccion) {
        this.colFechaTransaccion.set(colFechaTransaccion);
    }

    public void setColDescripcionTransaccion(String colDescripcionTransaccion) {
        this.colDescripcionTransaccion.set(colDescripcionTransaccion);
    }

    public void setColDescripcionComercio(String colDescripcionComercio) {
        this.colDescripcionComercio.set(colDescripcionComercio);
    }

    public void setColValorTransaccion(String colValorTransaccion) {
        this.colValorTransaccion.set(colValorTransaccion);
    }
}
