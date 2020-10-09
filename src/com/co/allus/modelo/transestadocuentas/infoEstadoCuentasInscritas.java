/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.transestadocuentas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoEstadoCuentasInscritas {

    private StringProperty colBanco;
    private StringProperty colEstado;
    private StringProperty colNumCuenta;
    private StringProperty colTipoCuenta;
    private StringProperty codBanco;

    public infoEstadoCuentasInscritas(String colNumCuenta, String colTipoCuenta, String colBanco, String colEstado, String codBanco) {

        this.colNumCuenta = new SimpleStringProperty(colNumCuenta);
        this.colTipoCuenta = new SimpleStringProperty(colTipoCuenta);
        this.colBanco = new SimpleStringProperty(colBanco);
        this.colEstado = new SimpleStringProperty(colEstado);
        this.codBanco = new SimpleStringProperty(codBanco);

    }

    public StringProperty colNumCuentaProperty() {
        return colNumCuenta;
    }

    public void setColNumCuenta(String colNumCuenta) {
        this.colNumCuenta.set(colNumCuenta);
    }

    public StringProperty colTipoCuentaProperty() {
        return colTipoCuenta;
    }

    public void setColTipoCuenta(String colTipoCuenta) {
        this.colTipoCuenta.set(colTipoCuenta);
    }

    public StringProperty colBancoProperty() {
        return colBanco;
    }

    public void setColBanco(String colBanco) {
        this.colBanco.set(colBanco);
    }

    public StringProperty colEstadoProperty() {
        return colEstado;
    }

    public void setColEstado(String colEstado) {
        this.colEstado.set(colEstado);
    }

    public StringProperty codBancoProperty() {
        return codBanco;
    }

    public void setCodBanco(String codBanco) {
        this.codBanco.set(codBanco);
    }
}
