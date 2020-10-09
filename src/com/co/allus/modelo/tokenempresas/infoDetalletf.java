/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

/**
 *
 * @author stephania.rojas
 */
public class infoDetalletf {

    private static infoDetalletf infodetalletf = new infoDetalletf();
    private String estado_empresa = "";
    private String esquema_seguridad = "";
    private String id_usuario = "";
    private String nombre = "";

    public String getEstado_empresa() {
        return estado_empresa;
    }

    public void setEstado_empresa(String estado_empresa) {
        this.estado_empresa = estado_empresa;
    }

    public String getEsquema_seguridad() {
        return esquema_seguridad;
    }

    public void setEsquema_seguridad(String esquema_seguridad) {
        this.esquema_seguridad = esquema_seguridad;
    }

    public static infoDetalletf getInfoDetalletf() {
        return infodetalletf;
    }

    public static void setInfoDetalletf(infoDetalletf infodetalletf) {
        infoDetalletf.infodetalletf = infodetalletf;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
