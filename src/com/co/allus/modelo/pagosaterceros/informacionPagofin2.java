/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosaterceros;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class informacionPagofin2 {

    private SimpleStringProperty num_comprobante;
    private SimpleStringProperty valor;
    private SimpleStringProperty num_cuenta;
    private SimpleStringProperty tipo_cuenta;
    private SimpleStringProperty fecha_pago;

    public informacionPagofin2(String numcomprobante, String valorpago, String numcuenta, String tipocuenta, String fechapago) {
        this.num_comprobante = new SimpleStringProperty(numcomprobante);
        this.valor = new SimpleStringProperty(valorpago);
        this.num_cuenta = new SimpleStringProperty(numcuenta);
        this.tipo_cuenta = new SimpleStringProperty(tipocuenta);
        this.fecha_pago = new SimpleStringProperty(fechapago);
    }

    public String getNum_comprobante() {
        return num_comprobante.get();
    }

    public void setNum_comprobante(String numcomprobante) {
        this.num_comprobante.set(numcomprobante);
    }

    public String getValor() {
        return valor.get();
    }

    public void setValor(String valorpago) {
        this.valor.set(valorpago);
    }

    public String getNum_cuenta() {
        return num_cuenta.get();
    }

    public void setNum_cuenta(String numcuenta) {
        this.num_cuenta.set(numcuenta);
    }

    public String getTipo_cuenta() {
        return tipo_cuenta.get();
    }

    public void setTipo_cuenta(String tipocuenta) {
        this.tipo_cuenta.set(tipocuenta);
    }

    public String getFecha_pago() {
        return fecha_pago.get();
    }

    public void setFecha_pago(String fechapago) {
        this.fecha_pago.set(fechapago);
    }
}
