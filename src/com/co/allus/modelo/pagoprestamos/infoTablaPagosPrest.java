/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagoprestamos;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author stephania.rojas
 */
public class infoTablaPagosPrest {

    private StringProperty colCredito;
    private StringProperty colTipoPres;
    private StringProperty colFechaProx;
    private StringProperty colValorIni;
    private StringProperty colPagoMin;
    private StringProperty colSaldoTotal;
    private BooleanProperty seleccion;

    public infoTablaPagosPrest(boolean seleleccion, String colCredito, String colTipoPres, String colFechaProx, String colValorIni, String colPagoMin, String colSaldoTotal) {
        this.seleccion = new SimpleBooleanProperty(seleleccion);
        this.colCredito = new SimpleStringProperty(colCredito);
        this.colTipoPres = new SimpleStringProperty(colTipoPres);
        this.colFechaProx = new SimpleStringProperty(colFechaProx);
        this.colValorIni = new SimpleStringProperty(colValorIni);
        this.colPagoMin = new SimpleStringProperty(colPagoMin);
        this.colSaldoTotal = new SimpleStringProperty(colSaldoTotal);


    }

    public BooleanProperty seleccionProperty() {
        return seleccion;
    }

    public StringProperty colCreditoProperty() {
        return colCredito;
    }

    public StringProperty colTipoPresProperty() {
        return colTipoPres;
    }

    public StringProperty colFechaProxProperty() {
        return colFechaProx;
    }

    public StringProperty colValorIniProperty() {
        return colValorIni;
    }

    public StringProperty colPagoMinProperty() {
        return colPagoMin;
    }

    public StringProperty colSaldoTotalProperty() {
        return colSaldoTotal;
    }

    public void setColCredito(String colCredito) {
        this.colCredito.set(colCredito);
    }

    public void setColTipoPres(String colTipoPres) {
        this.colTipoPres.set(colTipoPres);
    }

    public void setColFechaProx(String colFechaProx) {
        this.colFechaProx.set(colFechaProx);
    }

    public void setColValorIni(String colValorIni) {
        this.colValorIni.set(colValorIni);
    }

    public void setColPagoMin(String colPagoMin) {
        this.colPagoMin.set(colPagoMin);
    }

    public void setColSaldoTotal(String colSaldoTotal) {
        this.colSaldoTotal.set(colSaldoTotal);
    }
}
