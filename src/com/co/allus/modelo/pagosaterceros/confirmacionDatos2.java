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
public class confirmacionDatos2 {

    private SimpleStringProperty valor;
    private SimpleStringProperty num_cuenta;
    private SimpleStringProperty tipo_cuenta;

    public confirmacionDatos2(String valor, String num_cuenta, String tipo_cuenta) {
        this.valor = new SimpleStringProperty(valor);
        this.num_cuenta = new SimpleStringProperty(num_cuenta);
        this.tipo_cuenta = new SimpleStringProperty(tipo_cuenta);
    }

    public String getValor() {
        return valor.get();
    }

    public void setValor(String valor) {
        this.valor.set(valor);
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
}
