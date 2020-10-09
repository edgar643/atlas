/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokendistribucion;

/**
 *
 * @author stephania.rojas
 */
public class DatosDisToken {

    private static DatosDisToken datosdistri = new DatosDisToken();
    private String codigo_guia = "";
    private String codigo_serial = "";
    private String id_usuario = "";
    private String nombre_usuario = "";

    public String getCodigo_guia() {
        return codigo_guia;
    }

    public void setCodigo_guia(String codigo_guia) {
        this.codigo_guia = codigo_guia;
    }

    public String getCodigo_serial() {
        return codigo_serial;
    }

    public void setCodigo_serial(String codigo_serial) {
        this.codigo_serial = codigo_serial;
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

    public static DatosDisToken getDatosdistri() {
        return datosdistri;
    }

    public static void setDatosdistri(DatosDisToken datosdistri) {
        DatosDisToken.datosdistri = datosdistri;
    }
}
