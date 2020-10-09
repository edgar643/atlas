/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tdcprepago;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class DataAutenticacionEmp {

    private SimpleStringProperty pregunta;
    private SimpleStringProperty respuesta;

    public DataAutenticacionEmp(final String pregunta, final String respuesta) {
        this.pregunta = new SimpleStringProperty(pregunta);
        this.respuesta = new SimpleStringProperty(respuesta);
    }

    public String getPregunta() {
        return pregunta.get();
    }

    public void setPregunta(String pregunta) {
        this.pregunta.set(pregunta);
    }

    public String getRespuesta() {
        return respuesta.get();
    }

    public void setRespuesta(String respuesta) {
        this.respuesta.set(respuesta);
    }
}
