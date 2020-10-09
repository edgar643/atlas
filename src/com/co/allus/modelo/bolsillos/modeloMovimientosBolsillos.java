/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bolsillos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author roberto.ceballos
 */
public class modeloMovimientosBolsillos {
    
    private SimpleStringProperty tipoOperacion;
    private SimpleStringProperty fechaOrigen;
    private SimpleStringProperty descripcionMovimiento;
    private SimpleStringProperty valorTransaccion;

    public modeloMovimientosBolsillos(String tipOperacion, String fecOrigen, String desMovimiento, String valTransaccion) {
        this.tipoOperacion = new SimpleStringProperty(tipOperacion);
        this.fechaOrigen = new SimpleStringProperty(fecOrigen);
        this.descripcionMovimiento = new SimpleStringProperty(desMovimiento);
        this.valorTransaccion = new SimpleStringProperty(valTransaccion);
    }

    public String getTipoOperacion() {
        return tipoOperacion.get();
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion.set(tipoOperacion);
    }

    public String getFechaOrigen() {
        return fechaOrigen.get();
    }

    public void setFechaOrigen(String fechaOrigen) {
        this.fechaOrigen.set(fechaOrigen);
    }

    public String getDescripcionMovimiento() {
        return descripcionMovimiento.get();
    }

    public void setDescripcionMovimiento(String descripcionMovimiento) {
        this.descripcionMovimiento.set(descripcionMovimiento);
    }

    public String getValorTransaccion() {
        return valorTransaccion.get();
    }

    public void setValorTransaccion(String valorTransaccion) {
        this.valorTransaccion.set(valorTransaccion);
    }
    
}
