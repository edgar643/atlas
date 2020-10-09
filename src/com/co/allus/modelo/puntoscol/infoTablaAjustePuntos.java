/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author roberto.ceballos
 */
public class infoTablaAjustePuntos {

    private SimpleStringProperty fecha;
    private SimpleStringProperty concepto;
    private SimpleStringProperty puntos;
    private SimpleStringProperty descripcion;
    private SimpleStringProperty resultado;

    public infoTablaAjustePuntos(String fecha, String hora, String puntos, String descripcion, String resultado) {
        this.fecha = new SimpleStringProperty(fecha);
        this.puntos = new SimpleStringProperty(puntos);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.concepto = new SimpleStringProperty(hora);
        this.resultado = new SimpleStringProperty(resultado);
    }

    public String getFecha() {
        return fecha.get();
    }

    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }

    public String getPuntos() {
        return puntos.get();
    }

    public void setPuntos(String puntos) {
        this.puntos.set(puntos);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getConcepto() {
        return concepto.get();
    }

    public void setConcepto(String concepto) {
        this.concepto.set(concepto);
    }

    public String getResultado() {
        return resultado.get();
    }

    public void setResultado(String resultado) {
        this.resultado.set(resultado);
    }
}
