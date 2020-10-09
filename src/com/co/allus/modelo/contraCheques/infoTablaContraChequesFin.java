/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.contraCheques;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTablaContraChequesFin {

    private SimpleStringProperty chequeini;
    private SimpleStringProperty chequefin;
    private SimpleStringProperty cuenta;
    private SimpleStringProperty fechahora;

    public infoTablaContraChequesFin(final String Chequeini, final String Chequefin, final String Cuenta, final String Fechahora) {
        this.chequeini = new SimpleStringProperty(Chequeini);
        this.chequefin = new SimpleStringProperty(Chequefin);
        this.cuenta = new SimpleStringProperty(Cuenta);
        this.fechahora = new SimpleStringProperty(Fechahora);
    }

    public String getCuenta() {
        return cuenta.get();
    }

    public void setCuenta(String cuenta) {
        this.cuenta.set(cuenta);
    }

    public String getChequeini() {
        return chequeini.get();
    }

    public void setChequeini(String chequeini) {
        this.chequeini.set(chequeini);
    }

    public String getChequefin() {
        return chequefin.get();
    }

    public void setChequefin(String chequefin) {
        this.chequefin.set(chequefin);
    }

    public String getFechahora() {
        return fechahora.get();
    }

    public void setFechahora(String fechahora) {
        this.fechahora.set(fechahora);
    }
}
