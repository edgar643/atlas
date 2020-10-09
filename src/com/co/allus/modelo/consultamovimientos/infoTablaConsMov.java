/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultamovimientos;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTablaConsMov {

    private SimpleStringProperty cod_trx;
    private SimpleStringProperty descripcion;
    private SimpleStringProperty fecha_trx;
    private SimpleStringProperty origen_trx;
    private SimpleStringProperty valor_transaccion;
    private SimpleStringProperty ofi_origen;
    private SimpleStringProperty num_cheque;

    public infoTablaConsMov(String cod_trx, String descripcion, String fecha_trx, String origen_trx, String valor_transaccion, String ofi_origen, String num_cheque) {
        this.cod_trx = new SimpleStringProperty(cod_trx);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.fecha_trx = new SimpleStringProperty(fecha_trx);
        this.origen_trx = new SimpleStringProperty(origen_trx);
        this.valor_transaccion = new SimpleStringProperty(valor_transaccion);
        this.ofi_origen = new SimpleStringProperty(ofi_origen);
        this.num_cheque = new SimpleStringProperty(num_cheque);
    }

    public String getCod_trx() {
        return cod_trx.get();
    }

    public void setCod_trx(String cod_trx) {
        this.cod_trx.set(cod_trx);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getFecha_trx() {
        return fecha_trx.get();
    }

    public void setFecha_trx(String fecha_trx) {
        this.fecha_trx.set(fecha_trx);
    }

    public String getOrigen_trx() {
        return origen_trx.get();
    }

    public void setOrigen_trx(String origen_trx) {
        this.origen_trx.set(origen_trx);
    }

    public String getValor_transaccion() {
        return valor_transaccion.get();
    }

    public void setValor_transaccion(String valor_transaccion) {
        this.valor_transaccion.set(valor_transaccion);
    }

    public String getOfi_origen() {
        return ofi_origen.get();
    }

    public void setOfi_origen(String ofi_origen) {
        this.ofi_origen.set(ofi_origen);
    }

    public String getNum_cheque() {
        return num_cheque.get();
    }

    public void setNum_cheque(String num_cheque) {
        this.num_cheque.set(num_cheque);
    }
}
