/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.transfcrediagil;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class tablaFinDesembolsos {

    private SimpleStringProperty cuenta_destino;
    private SimpleStringProperty valor_prestamo;
//    private SimpleStringProperty num_prestamo;

    public tablaFinDesembolsos(final String cuenta_destino, final String valor) {
        this.cuenta_destino = new SimpleStringProperty(cuenta_destino);
        this.valor_prestamo = new SimpleStringProperty(valor);
//        this.num_prestamo = new SimpleStringProperty(num_prestamo);
    }

    public String getCuenta_destino() {
        return cuenta_destino.get();
    }

    public void setCuenta_destino(String cuenta_destino) {
        this.cuenta_destino.set(cuenta_destino);
    }

    public String getValor_prestamo() {
        return valor_prestamo.get();
    }

    public void setValor_prestamo(String valor_prestamo) {
        this.valor_prestamo.set(valor_prestamo);
    }
//    public String getNum_prestamo() {
//        return num_prestamo.get();
//    }
//
//    public void setNum_prestamo(String num_prestamo) {
//        this.num_prestamo.set(num_prestamo);
//    }
}
