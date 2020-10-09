/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

/**
 *
 * @author stephania.rojas
 */
public class dataTablaAE {

    public static dataTablaAE dataTablaAE = new dataTablaAE();
    private String id_usuario = "";
    private String nombre_usuario = "";

    public static dataTablaAE getDataTablaAE() {
        return dataTablaAE;
    }

    public static void setDataTablaAE(dataTablaAE dataTablaAE) {
        dataTablaAE.dataTablaAE = dataTablaAE;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
}
