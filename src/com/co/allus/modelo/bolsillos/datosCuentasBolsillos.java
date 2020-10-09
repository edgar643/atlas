/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bolsillos;

/**
 *
 * @author roberto.ceballos
 */
public class datosCuentasBolsillos {

    private static datosCuentasBolsillos dataCuentasBolsillos = new datosCuentasBolsillos();
    
    private String tipoCuenta = "";
    private String numeroCuenta = "";
    private String saldoCuenta = "";

    public static datosCuentasBolsillos getDataCuentasBolsillos() {
        return dataCuentasBolsillos;
    }

    public static void setDataCuentasBolsillos(datosCuentasBolsillos dataCuentasBolsillos) {
        datosCuentasBolsillos.dataCuentasBolsillos = dataCuentasBolsillos;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(String saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }
    
}
