/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.cambiomecanismo;

/**
 *
 * @author alexander.lopez.o
 */
public class ModeloDatosSeguridad {

    private static ModeloDatosSeguridad datosSeguridad = new ModeloDatosSeguridad();
    private String celular = "";
    private String email = "";
    private String tipo_email = "";
    private String registradoAlertas = "";
    private String cambio_mecanismo = "";
    private static boolean isHappy = true;

    public static boolean isIsHappy() {
        return isHappy;
    }

    public String getCambio_mecanismo() {
        return cambio_mecanismo;
    }

    public void setCambio_mecanismo(String cambio_mecanismo) {
        this.cambio_mecanismo = cambio_mecanismo;
    }

    public String getRegistradoAlertas() {
        return registradoAlertas;
    }

    public void setRegistradoAlertas(String registradoAlertas) {
        this.registradoAlertas = registradoAlertas;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo_email() {
        return tipo_email;
    }

    public void setTipo_email(String tipo_email) {
        this.tipo_email = tipo_email;
    }

    public static ModeloDatosSeguridad getDatosSeguridad() {
        return datosSeguridad;
    }

    public static void setDatosSeguridad(ModeloDatosSeguridad datosSeguridad) {
        ModeloDatosSeguridad.datosSeguridad = datosSeguridad;
    }
}
