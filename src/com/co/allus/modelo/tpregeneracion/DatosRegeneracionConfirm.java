/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tpregeneracion;

/**
 *
 * @author stephania.rojas
 */
public class DatosRegeneracionConfirm {

    private static DatosRegeneracionConfirm dataAct = new DatosRegeneracionConfirm();
    private String NumTarj = "";
    private String NumTarjsf = "";
    private String TipoTarj = "";

    public String getTipoTarj() {
        return TipoTarj;
    }

    public void setTipoTarj(String TipoTarj) {
        this.TipoTarj = TipoTarj;
    }

    public String getNumTarjsf() {
        return NumTarjsf;
    }

    public void setNumTarjsf(String NumTarjsf) {
        this.NumTarjsf = NumTarjsf;
    }

    public static DatosRegeneracionConfirm getDataAct() {
        return dataAct;
    }

    public static void setDataAct(DatosRegeneracionConfirm dataMF) {
        DatosRegeneracionConfirm.dataAct = dataMF;
    }

    public String getNumTarj() {
        return NumTarj;
    }

    public void setNumTarj(String NumTarj) {
        this.NumTarj = NumTarj;
    }
}
