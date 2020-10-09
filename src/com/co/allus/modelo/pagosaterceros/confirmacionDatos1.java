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
public class confirmacionDatos1 {

    private SimpleStringProperty codigo_conv;
    private SimpleStringProperty nombre_conv;
    private SimpleStringProperty refpago2;
    private SimpleStringProperty ref_pago;

    public confirmacionDatos1(String codigoconv, String nombreconv, String refpago2, String refpago) {
        this.codigo_conv = new SimpleStringProperty(codigoconv);
        this.nombre_conv = new SimpleStringProperty(nombreconv);
        this.refpago2 = new SimpleStringProperty(refpago2);
        this.ref_pago = new SimpleStringProperty(refpago);
    }

    public String getCodigo_conv() {
        return codigo_conv.get();
    }

    public void setCodigo_conv(String codigoconv) {
        this.codigo_conv.set(codigoconv);
    }

    public String getNombre_conv() {
        return nombre_conv.get();
    }

    public void setNombre_conv(String nombreconv) {
        this.nombre_conv.set(nombreconv);
    }

    public String getRefpago2() {
        return refpago2.get();
    }

    public void setRefpago2(String refpago2) {
        this.refpago2.set(refpago2);
    }

    public String getRef_pago() {
        return ref_pago.get();
    }

    public void setRef_pago(String refpago) {
        this.ref_pago.set(refpago);
    }
}
