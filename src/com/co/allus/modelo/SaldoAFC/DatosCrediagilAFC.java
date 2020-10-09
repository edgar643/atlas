/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.SaldoAFC;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class DatosCrediagilAFC {

    private SimpleStringProperty ValorCupoAsignadoEnt;
    private SimpleStringProperty ValorCupoUtilizadoEnt;
    private SimpleStringProperty ValorDisponibleEnt;
    private SimpleStringProperty ValorCanjeEnt;

    public DatosCrediagilAFC(String ValorCupoAsignadoEnt, String ValorCupoUtilizadoEnt, String ValorDisponibleEnt, String ValorCanjeEnt) {
        this.ValorCupoAsignadoEnt = new SimpleStringProperty(ValorCupoAsignadoEnt);
        this.ValorCupoUtilizadoEnt = new SimpleStringProperty(ValorCupoUtilizadoEnt);
        this.ValorDisponibleEnt = new SimpleStringProperty(ValorDisponibleEnt);
        this.ValorCanjeEnt = new SimpleStringProperty(ValorCanjeEnt);
    }

    public String getValorCupoAsignadoEnt() {
        return ValorCupoAsignadoEnt.get();
    }

    public void setValorCupoAsignadoEnt(String ValorCupoAsignadoEnt) {
        this.ValorCupoAsignadoEnt.set(ValorCupoAsignadoEnt);
    }

    public String getValorCupoUtilizadoEnt() {
        return ValorCupoUtilizadoEnt.get();
    }

    public void setValorCupoUtilizadoEnt(String ValorCupoUtilizadoEnt) {
        this.ValorCupoUtilizadoEnt.set(ValorCupoUtilizadoEnt);
    }

    public String getValorDisponibleEnt() {
        return ValorDisponibleEnt.get();
    }

    public void setValorDisponibleEnt(String ValorDisponibleEnt) {
        this.ValorDisponibleEnt.set(ValorDisponibleEnt);
    }

    public String getValorCanjeEnt() {
        return ValorCanjeEnt.get();
    }

    public void setValorCanjeEnt(String ValorCanjeEnt) {
        this.ValorCanjeEnt.set(ValorCanjeEnt);
    }
}
