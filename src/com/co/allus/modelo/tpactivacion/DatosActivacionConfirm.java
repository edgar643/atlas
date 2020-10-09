/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tpactivacion;

import com.co.allus.modelo.tpmovimientos.*;

/**
 *
 * @author stephania.rojas
 */
public class DatosActivacionConfirm {

    private static DatosActivacionConfirm dataAct = new DatosActivacionConfirm();
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

    public static DatosActivacionConfirm getDataAct() {
        return dataAct;
    }

    public static void setDataAct(DatosActivacionConfirm dataMF) {
        DatosActivacionConfirm.dataAct = dataMF;
    }

    public String getNumTarj() {
        return NumTarj;
    }

    public void setNumTarj(String NumTarj) {
        this.NumTarj = NumTarj;
    }
}
