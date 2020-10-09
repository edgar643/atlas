/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

/**
 *
 * @author stephania.rojas
 */
public class DatosTokenEmpresas {

    private static DatosTokenEmpresas datosTokenEmpresas = new DatosTokenEmpresas();
    private String nit = "";
    private String estado_empresa = "";
    private String esquema_seguridad = "";
    private String total_token = "";

    public static void setDatosTokenEmpresas(DatosTokenEmpresas valor) {
        datosTokenEmpresas = valor;
    }

    public static DatosTokenEmpresas getDatosTokenEmpresas() {
        return datosTokenEmpresas;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

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

    public String getTotal_token() {
        return total_token;
    }

    public void setTotal_token(String total_token) {
        this.total_token = total_token;
    }
}
