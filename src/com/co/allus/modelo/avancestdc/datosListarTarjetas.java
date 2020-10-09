/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.avancestdc;

/**
 *
 * @author roberto.ceballos
 */
public class datosListarTarjetas {

     private static datosListarTarjetas dataListarTarjetas = new datosListarTarjetas();
    private String numero = "";
    private String tipoTarjeta = "";
    private String bloqueoTarjeta = "";

    public static datosListarTarjetas getDataListarTarjetas() {
        return dataListarTarjetas;
    }

    public static void setDataListarTarjetas(datosListarTarjetas dataListarTarjetas) {
        datosListarTarjetas.dataListarTarjetas = dataListarTarjetas;
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
