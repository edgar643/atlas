/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.declinacionestdc;

/**
 *
 * @author roberto.ceballos
 */
public class DatosTDCDeclinacion {

    private static DatosTDCDeclinacion datadeclinacion = new DatosTDCDeclinacion();
    private String numero = "";
    private String tipoTarjeta = "";
    private String bloqueoTarjeta = "";

    public static DatosTDCDeclinacion getDatadeclinacion() {
        return datadeclinacion;
    }

    public static void setDatadeclinacion(DatosTDCDeclinacion datadeclinacion) {
        DatosTDCDeclinacion.datadeclinacion = datadeclinacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getBloqueoTarjeta() {
        return bloqueoTarjeta;
    }

    public void setBloqueoTarjeta(String bloqueoTarjeta) {
        this.bloqueoTarjeta = bloqueoTarjeta;
    }
    
}
