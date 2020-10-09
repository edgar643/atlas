/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultatrm;

/**
 *
 * @author stephania.rojas
 */
public class Datosyearmonth {

    private static Datosyearmonth datosyearmonth = new Datosyearmonth();
    private String valoryear = "";

    public String getValoryear() {
        return valoryear;
    }

    public void setValoryear(String valoryear) {
        this.valoryear = valoryear;
    }

    public static Datosyearmonth getDatosyearmonth() {
        return datosyearmonth;
    }

    public static void setDatosyearmonth(Datosyearmonth datosyearmonth) {
        Datosyearmonth.datosyearmonth = datosyearmonth;
    }
}
