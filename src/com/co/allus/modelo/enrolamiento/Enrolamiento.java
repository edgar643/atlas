/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.enrolamiento;

import com.co.allus.modelo.cambiomecanismo.ModeloDatosSeguridad;

/**
 *
 * @author luis.cuervo
 */
public class Enrolamiento {

    private static Enrolamiento enrolado = new Enrolamiento();
    private String celular = "";
    private String email = "";
    private String tipo_email = "";
    private String tipo_mecanismo = "";

    public static Enrolamiento getEnrolado() {
        return enrolado;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo_email() {
        return tipo_email;
    }

    public String getTipo_mecanismo() {
        return tipo_mecanismo;
    }

    public static void setEnrolado(Enrolamiento enrolado) {
        Enrolamiento.enrolado = enrolado;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipo_email(String tipo_email) {
        this.tipo_email = tipo_email;
    }

    public void setTipo_mecanismo(String tipo_mecanismo) {
        this.tipo_mecanismo = tipo_mecanismo;
    }
}
