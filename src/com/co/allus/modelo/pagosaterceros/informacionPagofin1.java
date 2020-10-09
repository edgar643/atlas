/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosaterceros;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class informacionPagofin1 {

    private SimpleStringProperty cod_conv;
    private SimpleStringProperty entidad_pagada;
    private SimpleStringProperty ref_pago;
    private SimpleStringProperty ref_pago2;

    public informacionPagofin1(String codconv, String entidadpagada, String refpago, String refpago2) {
        this.cod_conv = new SimpleStringProperty(codconv);
        this.entidad_pagada = new SimpleStringProperty(entidadpagada);
        this.ref_pago = new SimpleStringProperty(refpago);
        this.ref_pago2 = new SimpleStringProperty(refpago2);
    }

    public String getCod_conv() {
        return cod_conv.get();
    }

    public void setCod_conv(String codconv) {
        this.cod_conv.set(codconv);
    }

    public String getEntidad_pagada() {
        return entidad_pagada.get();
    }

    public void setEntidad_pagada(String entidadpagada) {
        this.entidad_pagada.set(entidadpagada);
    }

    public String getRef_pago() {
        return ref_pago.get();
    }

    public void setRef_pago(String refpago) {
        this.ref_pago.set(refpago);
    }

    public String getRef_pago2() {
        return ref_pago2.get();
    }

    public void setRef_pago2(String refpago2) {
        this.ref_pago2.set(refpago2);
    }
}
