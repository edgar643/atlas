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
public class ModelTablaInfoPago {

    private SimpleStringProperty referenciaPago;
    private SimpleStringProperty tipoCuenta;
    private SimpleStringProperty cuentaDebitar;

    public ModelTablaInfoPago(String referenciaPago, String tipoCuenta, String cuentaDebitar) {

        this.referenciaPago = new SimpleStringProperty(referenciaPago);
        this.tipoCuenta = new SimpleStringProperty(tipoCuenta);
        this.cuentaDebitar = new SimpleStringProperty(cuentaDebitar);
    }

    public String getReferenciaPago() {
        return referenciaPago.get();
    }

    public void setReferenciaPago(String refPago) {
        referenciaPago.set(refPago);
    }

    public String getTipoCuenta() {
        return tipoCuenta.get();
    }

    public void setTipoCuenta(String tCuenta) {
        tipoCuenta.set(tCuenta);
    }

    public String getCuentaDebitar() {
        return cuentaDebitar.get();
    }

    public void setCuentaDebitar(String cDebitar) {
        cuentaDebitar.set(cDebitar);
    }
}
