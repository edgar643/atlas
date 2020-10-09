/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

/**
 *
 * @author roberto.ceballos
 */
public class DatosMigradosPuntosCol {

    private static DatosMigradosPuntosCol datosMigradosPuntosCol = new DatosMigradosPuntosCol();
    private String numid = "";
    private String tipoid = "";
    private String puntosmigrados = "";
    private String equivpuntos = "";
    private String fecha = "";

    public static DatosMigradosPuntosCol getDatosMigradosPuntosCol() {
        return datosMigradosPuntosCol;
    }

    public static void setDatosSaldoPuntosCol(DatosMigradosPuntosCol datosMigradosPuntosCol) {
        DatosMigradosPuntosCol.datosMigradosPuntosCol = datosMigradosPuntosCol;
    }

    public String getNumid() {
        return numid;
    }

    public void setNumid(String numid) {
        this.numid = numid;
    }

    public String getTipoid() {
        return tipoid;
    }

    public void setTipoid(String tipoid) {
        this.tipoid = tipoid;
    }

    public String getPuntosmigrados() {
        return puntosmigrados;
    }

    public void setPuntosmigrados(String puntosmigrados) {
        this.puntosmigrados = puntosmigrados;
    }

    public String getEquivpuntos() {
        return equivpuntos;
    }

    public void setEquivpuntos(String equivpuntos) {
        this.equivpuntos = equivpuntos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public static final String NUMID = "numid";
    public static final String TIPOID = "tipoid";
    public static final String PUNTOSMIGRADOS = "puntosmigrados";
    public static final String EQUIVPUNTOS = "equivpuntos";
    public static final String FECHA = "fecha";
}
