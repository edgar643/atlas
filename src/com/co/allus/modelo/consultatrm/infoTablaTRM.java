/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultatrm;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoTablaTRM {

    public SimpleStringProperty valortrm;
    public SimpleStringProperty trm;

    public infoTablaTRM(final String valortrm, final String trm) {
        this.valortrm = new SimpleStringProperty(valortrm);
        this.trm = new SimpleStringProperty(trm);
    }

    public String getValortrm() {
        return valortrm.get();
    }

    public void setValortrm(String valortrm) {
        this.valortrm.set(valortrm);
    }

    public String getTrm() {
        return trm.get();
    }

    public void setTrm(String trm) {
        this.trm.set(trm);
    }
}
