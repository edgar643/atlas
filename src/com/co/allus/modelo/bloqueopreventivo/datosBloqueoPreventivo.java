/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bloqueopreventivo;

/**
 *
 * @author stephania.rojas
 */
public class datosBloqueoPreventivo {

    private static datosBloqueoPreventivo dataBP = new datosBloqueoPreventivo();
    private String num_tarjeta = "";

    public static datosBloqueoPreventivo getDataBP() {
        return dataBP;
    }

    public static void setDataBP(datosBloqueoPreventivo dataBP) {
        dataBP.dataBP = dataBP;
    }

    public String getNum_tarjeta() {
        return num_tarjeta;
    }

    public void setNum_tarjeta(String num_tarjeta) {
        this.num_tarjeta = num_tarjeta;
    }
}
