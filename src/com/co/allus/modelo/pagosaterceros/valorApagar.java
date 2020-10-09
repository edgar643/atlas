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
public class valorApagar {

    private SimpleStringProperty val_pagar;

    public valorApagar(String valpagar) {
        this.val_pagar = new SimpleStringProperty(valpagar);
    }

    public String getVal_pagar() {
        return val_pagar.get();
    }

    public void setVal_pagar(String valpagar) {
        val_pagar.set(valpagar);
    }
}
