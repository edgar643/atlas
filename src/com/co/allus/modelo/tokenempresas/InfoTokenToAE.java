/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class InfoTokenToAE {

    private SimpleStringProperty fecha_envio;
    private SimpleStringProperty hora_envio;
    private SimpleStringProperty vigencia;
    private SimpleStringProperty email;
    private SimpleStringProperty estado;

    public InfoTokenToAE(String fecha_envio, String hora_envio, String vigencia, String email, String estado) {

        this.fecha_envio = new SimpleStringProperty(fecha_envio);
        this.hora_envio = new SimpleStringProperty(hora_envio);
        this.vigencia = new SimpleStringProperty(vigencia);
        this.email = new SimpleStringProperty(email);
        this.estado = new SimpleStringProperty(estado);

    }

    public String getFecha_envio() {
        return fecha_envio.get();
    }

    public void setFecha_envio(String fecha_envio) {
        this.fecha_envio.set(fecha_envio);
    }

    public String getHora_envio() {
        return hora_envio.get();
    }

    public void setHora_envio(String hora_envio) {
        this.hora_envio.set(hora_envio);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getVigencia() {
        return vigencia.get();
    }

    public void setVigencia(String vigencia) {
        this.vigencia.set(vigencia);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }
}
