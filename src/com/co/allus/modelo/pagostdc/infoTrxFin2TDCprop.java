/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagostdc;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTrxFin2TDCprop {

    private SimpleStringProperty comprobante;
    private SimpleStringProperty num_cuenta;
    private SimpleStringProperty tipo_cuenta;
    private SimpleStringProperty fecha_pago;

    public infoTrxFin2TDCprop(String comprobante, String num_cuenta, String tipo_cuenta, String fecha_pago) {
        this.comprobante = new SimpleStringProperty(comprobante);
        this.num_cuenta = new SimpleStringProperty(num_cuenta);
        this.tipo_cuenta = new SimpleStringProperty(tipo_cuenta);
        this.fecha_pago = new SimpleStringProperty(fecha_pago);
    }

    public String getComprobante() {
        return comprobante.get();
    }

    public void setComprobante(String comprobante) {
        this.comprobante.set(comprobante);
    }

    public String getNum_cuenta() {
        return num_cuenta.get();
    }

    public void setNum_cuenta(String num_cuenta) {
        this.num_cuenta.set(num_cuenta);
    }

    public String getTipo_cuenta() {
        return tipo_cuenta.get();
    }

    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta.set(tipo_cuenta);
    }

    public String getFecha_pago() {
        return fecha_pago.get();
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago.set(fecha_pago);
    }
}
