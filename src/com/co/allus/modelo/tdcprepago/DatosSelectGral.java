/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tdcprepago;

/**
 *
 * @author stephania.rojas
 */
public class DatosSelectGral {

    private static DatosSelectGral dataGral = new DatosSelectGral();

    public static DatosSelectGral getDataGral() {
        return dataGral;
    }

    public static void setDataGral(DatosSelectGral dataGral) {
        DatosSelectGral.dataGral = dataGral;
    }

    public String getNumTarj() {
        return NumTarj;
    }

    public void setNumTarj(String NumTarj) {
        this.NumTarj = NumTarj;
    }

    public String getNumTarjsf() {
        return NumTarjsf;
    }

    public void setNumTarjsf(String NumTarjsf) {
        this.NumTarjsf = NumTarjsf;
    }

    public String getTipoTarj() {
        return TipoTarj;
    }

    public void setTipoTarj(String TipoTarj) {
        this.TipoTarj = TipoTarj;
    }
    private String NumTarj = "";
    private String NumTarjsf = "";
    private String TipoTarj = "";
}
