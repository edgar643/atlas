/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author roberto.ceballos
 */
public class infoTablapcolmigrados {

    private SimpleStringProperty numid;
    private SimpleStringProperty tipoid;
    private SimpleStringProperty puntosmigrados;
    private SimpleStringProperty equivpuntos;
    private SimpleStringProperty fecha;

    public infoTablapcolmigrados(final String numid, final String tipoid, final String puntosmigrados, final String equivpuntos, final String fecha) {
        this.numid = new SimpleStringProperty(numid);
        this.tipoid = new SimpleStringProperty(tipoid);
        this.puntosmigrados = new SimpleStringProperty(puntosmigrados);
        this.equivpuntos = new SimpleStringProperty(equivpuntos);
        this.fecha = new SimpleStringProperty(fecha);
    }

    public String getNumid() {
        return numid.get();
    }

    public void setNumid(String numid) {
        this.numid.set(numid);
    }

    public String getTipoid() {
        return tipoid.get();
    }

    public void setTipoid(String tipoid) {
        this.tipoid.set(tipoid);
    }

    public String getPuntosmigrados() {
        return puntosmigrados.get();
    }

    public void setPuntosmigrados(String puntosmigrados) {
        this.puntosmigrados.set(puntosmigrados);
    }

    public String getEquivpuntos() {
        return equivpuntos.get();
    }

    public void setEquivpuntos(String equivpuntos) {
        this.equivpuntos.set(equivpuntos);
    }

    public String getFecha() {
        return fecha.get();
    }

    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }
}
