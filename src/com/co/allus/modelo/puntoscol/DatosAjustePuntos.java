/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

/**
 *
 * @author roberto.ceballos
 */
public class DatosAjustePuntos {

    private String fecha = "";
    private String puntos = "";
    private String descripcion = "";
    private String concepto = "";
    private String resultado = "";

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public static final String FECHA = "fecha";
    public static final String PUNTOS = "puntos";
    public static final String DESCRIPCION = "descripcion";
    public static final String CONCEPTO = "concepto";
    public static final String RESULTADO = "resultado";
}
