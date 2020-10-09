/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tpmovimientos;

/**
 *
 * @author stephania.rojas
 */
public class DatosMovimientosFin {

    private static DatosMovimientosFin dataMF = new DatosMovimientosFin();
    private String NumTarj = "";
    private String NumTarjcf = "";

    public String getNumTarjcf() {
        return NumTarjcf;
    }

    public void setNumTarjcf(String NumTarjcf) {
        this.NumTarjcf = NumTarjcf;
    }

    public static DatosMovimientosFin getDataMF() {
        return dataMF;
    }

    public static void setDataMF(DatosMovimientosFin dataMF) {
        DatosMovimientosFin.dataMF = dataMF;
    }

    public String getNumTarj() {
        return NumTarj;
    }

    public void setNumTarj(String NumTarj) {
        this.NumTarj = NumTarj;
    }
}
