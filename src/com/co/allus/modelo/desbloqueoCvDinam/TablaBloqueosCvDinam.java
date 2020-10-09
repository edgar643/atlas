/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.desbloqueoCvDinam;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author luis.cuervo
 */
public class TablaBloqueosCvDinam {

    private StringProperty FechaHora_desbloq;//FechaHora_desbloq

    public TablaBloqueosCvDinam(String FechaHora_desbloq) {
        this.FechaHora_desbloq = new SimpleStringProperty(FechaHora_desbloq);
    }

    public String getFechaHora_desbloq() {
        return FechaHora_desbloq.get();
    }

    public void setFechaHora_desbloq(String FechaHora_desbloq) {
        this.FechaHora_desbloq.set(FechaHora_desbloq);
    }
}
