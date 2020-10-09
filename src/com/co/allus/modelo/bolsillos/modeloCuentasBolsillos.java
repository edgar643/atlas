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
public class modeloCuentasBolsillos {

    private SimpleStringProperty tipoCuenta;
    private SimpleStringProperty numeroCuenta;
    private SimpleStringProperty saldoCuenta;

    public modeloCuentasBolsillos(String tipCuenta, String numCuenta, String salCuenta) {
        this.tipoCuenta = new SimpleStringProperty(tipCuenta);
        this.numeroCuenta = new SimpleStringProperty(numCuenta);
        this.saldoCuenta = new SimpleStringProperty(salCuenta);
    }
    
    public String getTipoCuenta() {
        return tipoCuenta.get();
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta.set(tipoCuenta);
    }

    public String getNumeroCuenta() {
        return numeroCuenta.get();
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta.set(numeroCuenta);
    }

    public String getSaldoCuenta() {
        return saldoCuenta.get();
    }

    public void setSaldoCuenta(String saldoCuenta) {
        this.saldoCuenta.set(saldoCuenta);
    }
    
    
    
    
    
    
}
