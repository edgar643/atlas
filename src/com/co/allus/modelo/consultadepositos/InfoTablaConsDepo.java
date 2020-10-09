/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultadepositos;

import com.co.allus.modelo.consultamovimientos.*;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class InfoTablaConsDepo {

    private SimpleStringProperty numCta;
    private SimpleStringProperty estado;

    public InfoTablaConsDepo(String numerocuenta, String estado) {

        this.numCta = new SimpleStringProperty(numerocuenta);
        this.estado = new SimpleStringProperty(estado);

    }

    public String getNumCta() {
        return numCta.get();
    }

    public void setNumCta(String numCta) {
        this.numCta.set(numCta);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }
}
