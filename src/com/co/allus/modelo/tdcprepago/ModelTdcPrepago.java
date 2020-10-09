/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tdcprepago;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class ModelTdcPrepago {

    private SimpleStringProperty numeroTarjeta;
    private SimpleStringProperty tipoTarjeta;
    private SimpleStringProperty bloqueoTarjeta;
    private SimpleStringProperty nomEmpresa;
    private SimpleStringProperty tarjetacf = new SimpleStringProperty("");

    public ModelTdcPrepago(String numtarjeta, String tipotarjeta, String bloqueotarjeta, String nomEmpresa) {

        this.numeroTarjeta = new SimpleStringProperty(numtarjeta);
        this.tipoTarjeta = new SimpleStringProperty(tipotarjeta);
        this.bloqueoTarjeta = new SimpleStringProperty(bloqueotarjeta);
        this.nomEmpresa = new SimpleStringProperty(nomEmpresa);

    }

    public String getTarjetacf() {
        return tarjetacf.get();
    }

    public void setTarjetacf(String tarjetacf) {
        this.tarjetacf.set(tarjetacf);
    }

    public String getNomEmpresa() {
        return nomEmpresa.get();
    }

    public void setNomEmpresa(String nomEmpresa) {
        this.nomEmpresa.set(nomEmpresa);
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta.get();
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta.set(numeroTarjeta);
    }

    public String getTipoTarjeta() {
        return tipoTarjeta.get();
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta.set(tipoTarjeta);
    }

    public String getBloqueoTarjeta() {
        return bloqueoTarjeta.get();
    }

    public void setBloqueoTarjeta(String bloqueoTarjeta) {
        this.bloqueoTarjeta.set(bloqueoTarjeta);
    }
}
