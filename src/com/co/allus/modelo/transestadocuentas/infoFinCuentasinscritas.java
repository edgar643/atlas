/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.transestadocuentas;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoFinCuentasinscritas {

    private SimpleStringProperty numcta;
    private SimpleStringProperty tipocta;
    private SimpleStringProperty banco;

    public infoFinCuentasinscritas(final String numerocta, final String tipocta, final String descbanco) {
        this.numcta = new SimpleStringProperty(numerocta);
        this.tipocta = new SimpleStringProperty(tipocta);
        this.banco = new SimpleStringProperty(descbanco);
    }

    public String getNumcta() {
        return numcta.get();
    }

    public void setNumcta(String numcta) {
        this.numcta.set(numcta);
    }

    public String getTipocta() {
        return tipocta.get();
    }

    public void setTipocta(String tipocta) {
        this.tipocta.set(tipocta);
    }

    public String getBanco() {
        return banco.get();
    }

    public void setBanco(String banco) {
        this.banco.set(banco);
    }
}
