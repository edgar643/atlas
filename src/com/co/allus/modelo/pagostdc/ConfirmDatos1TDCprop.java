/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagostdc;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class ConfirmDatos1TDCprop {

    private SimpleStringProperty tarjeta_pagar;
    private SimpleStringProperty franquicia;
    private SimpleStringProperty tipo_pago;

    public ConfirmDatos1TDCprop(String tarjeta_pagar, String franquicia, String tipo_pago) {
        this.tarjeta_pagar = new SimpleStringProperty(tarjeta_pagar);
        this.franquicia = new SimpleStringProperty(franquicia);
        this.tipo_pago = new SimpleStringProperty(tipo_pago);
        ;
    }

    public String getTarjeta_pagar() {
        return tarjeta_pagar.get();
    }

    public void setTarjeta_pagar(String tarjeta_pagar) {
        this.tarjeta_pagar.set(tarjeta_pagar);
    }

    public String getFranquicia() {
        return franquicia.get();
    }

    public void setFranquicia(String franquicia) {
        this.franquicia.set(franquicia);
    }

    public String getTipo_pago() {
        return tipo_pago.get();
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago.set(tipo_pago);
    }
}
