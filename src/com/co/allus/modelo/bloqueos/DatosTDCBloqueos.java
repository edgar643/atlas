/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bloqueos;

/**
 *
 * @author alexander.lopez.o
 */
public class DatosTDCBloqueos {

    private String numero = "";
    private String tipoTarjeta = "";
    private String bloqueoTarjeta = "";
    private static DatosTDCBloqueos databloqueo = new DatosTDCBloqueos();

    public static DatosTDCBloqueos getDatabloqueo() {
        return databloqueo;
    }

    public static void setDatabloqueo(DatosTDCBloqueos databloqueo) {
        DatosTDCBloqueos.databloqueo = databloqueo;
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
