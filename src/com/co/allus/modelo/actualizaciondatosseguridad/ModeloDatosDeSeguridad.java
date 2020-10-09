/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.actualizaciondatosseguridad;

import com.co.allus.utils.StringUtilities;

/**
 *
 * @author luis.cuervo
 */
public class ModeloDatosDeSeguridad {

    public static String getIdentificacion() {
        return identificacion;
    }

    public static String getNombre() {
        return nombre;
    }

    public static String getEmail() {
        return Email;
    }

    public static String getTipoEmail() {
        return TipoEmail;
    }

    public static String getCelular() {
        return celular;
    }

    public static String getOTP() {
        return OTP;
    }

    public static void setIdentificacion(String identificacion) {
        ModeloDatosDeSeguridad.identificacion = identificacion;
    }

    public static void setNombre(String nombre) {
        ModeloDatosDeSeguridad.nombre = nombre;
    }

    public static void setEmail(String Email) {
        ModeloDatosDeSeguridad.Email = Email;
    }

    public static void setTipoEmail(String TipoEmail) {
        ModeloDatosDeSeguridad.TipoEmail = TipoEmail;
    }

    public static void setCelular(String celular) {
        ModeloDatosDeSeguridad.celular = celular;
    }

    public static void setOTP(String OTP) {
        ModeloDatosDeSeguridad.OTP = OTP;
    }
    private static String identificacion;
    private static String nombre;
    private static String Email;
    private static String TipoEmail;
    private static String celular;
    private static String OTP;
    private static boolean isHappy = false;

    public static boolean isIsHappy() {
        return isHappy;
    }
}
