/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.saldos;

/**
 *
 * @author alexander.lopez.o
 */
import javafx.beans.property.SimpleStringProperty;

public class ConsultaSaldoTabla2 {

    private final SimpleStringProperty saldoDisponible = new SimpleStringProperty("");
    private final SimpleStringProperty saldoCanje = new SimpleStringProperty("");
    private final SimpleStringProperty saldoTotal = new SimpleStringProperty("");
    private final SimpleStringProperty diasSobregiro = new SimpleStringProperty("");

    public ConsultaSaldoTabla2() {
        this("", "", "", "");
    }

    public ConsultaSaldoTabla2(String saldoDisponible, String saldoCanje, String saldoTotal, String diasSobregiro) {
        setSaldoDisponible(saldoDisponible);
        setSaldoCanje(saldoCanje);
        setSaldoTotal(saldoTotal);
        setDiasSobregiro(diasSobregiro);
    }

    public String getSaldoDisponible() {
        return saldoDisponible.get();
    }

    public void setSaldoDisponible(String fname) {
        saldoDisponible.set(fname);
    }

    public String getSaldoCanje() {
        return saldoCanje.get();
    }

    public void setSaldoCanje(String fName) {
        saldoCanje.set(fName);
    }

    public String getSaldoTotal() {
        return saldoTotal.get();
    }

    public void setSaldoTotal(String fName) {
        saldoTotal.set(fName);
    }

    public String getDiasSobregiro() {
        return diasSobregiro.get();
    }

    public void setDiasSobregiro(String fName) {
        diasSobregiro.set(fName);
    }
}
