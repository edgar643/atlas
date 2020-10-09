/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

/**
 *
 * @author roberto.ceballos
 */
public class DatosListarTarjeta {

    private String numero_tarjeta = "";
    private String tipo_tarjeta = "";
    private String franquicia_tarjeta = "";

    public String getNumeroTarjeta() {
        return numero_tarjeta;
    }

    public void setNumeroTarjeta(String numero_tarjeta) {
        this.numero_tarjeta = numero_tarjeta;
    }

    public String getTipoTarjeta() {
        return tipo_tarjeta;
    }

    public void setTipoTarjeta(String tipo_tarjeta) {
        this.tipo_tarjeta = tipo_tarjeta;
    }

    public String getFranquiciaTarjeta() {
        return franquicia_tarjeta;
    }

    public void setFranquiciaTarjeta(String franquicia_tarjeta) {
        this.franquicia_tarjeta = franquicia_tarjeta;
    }
}
