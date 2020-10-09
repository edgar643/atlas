/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.alertasnot;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class AlertasEnviadas {
    
private StringProperty colOperacion;
private StringProperty colEmail;
private StringProperty colCelular;
private StringProperty colFecha_envio;
private StringProperty colHora_envio;
private StringProperty colMensaje;
private StringProperty colIndalerta;

    public AlertasEnviadas(String colOperacion,
            String colEmail, 
            String colCelular,
            String colFecha_envio,
            String colHora_envio, 
            String colMensaje, 
            String colIndalerta) {
        this.colOperacion = new SimpleStringProperty(colOperacion);
        this.colEmail = new SimpleStringProperty(colEmail);
        this.colCelular = new SimpleStringProperty(colCelular);
        this.colFecha_envio = new SimpleStringProperty(colFecha_envio);
        this.colHora_envio = new SimpleStringProperty(colHora_envio);
        this.colMensaje = new SimpleStringProperty(colMensaje);
        this.colIndalerta = new SimpleStringProperty(colIndalerta);
    }

    public StringProperty colOperacionProperty() {
        return colOperacion;
    }

    public void setColOperacion(StringProperty colOperacion) {
        this.colOperacion = colOperacion;
    }

    public StringProperty colEmailProperty() {
        return colEmail;
    }

    public void setColEmail(StringProperty colEmail) {
        this.colEmail = colEmail;
    }

    public StringProperty colCelularProperty() {
        return colCelular;
    }

    public void setColCelular(StringProperty colCelular) {
        this.colCelular = colCelular;
    }

    public StringProperty colFecha_envioProperty() {
        return colFecha_envio;
    }

    public void setColFecha_envio(StringProperty colFecha_envio) {
        this.colFecha_envio = colFecha_envio;
    }

    public StringProperty colHora_envioProperty() {
        return colHora_envio;
    }

    public void setColHora_envio(StringProperty colHora_envio) {
        this.colHora_envio = colHora_envio;
    }

    public StringProperty colMensajeProperty() {
        return colMensaje;
    }

    public void setColMensaje(StringProperty colMensaje) {
        this.colMensaje = colMensaje;
    }

    public StringProperty colIndalertaProperty() {
        return colIndalerta;
    }

    public void setColIndalerta(StringProperty colIndalerta) {
        this.colIndalerta = colIndalerta;
    }
    
    
    








    
    
}
