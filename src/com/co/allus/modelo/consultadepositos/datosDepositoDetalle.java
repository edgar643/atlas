/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultadepositos;

/**
 *
 * @author stephania.rojas
 */
public class datosDepositoDetalle {

    private static datosDepositoDetalle dataDetalle = new datosDepositoDetalle();
    private String num_tarjeta = "";
    private String tipocta = "";

    public static datosDepositoDetalle getDataDetalle() {
        return dataDetalle;
    }

    public static void setDataDetalle(datosDepositoDetalle dataDetalle) {
        dataDetalle.dataDetalle = dataDetalle;
    }

    public String getNum_tarjeta() {
        return num_tarjeta;
    }

    public void setNum_tarjeta(String num_tarjeta) {
        this.num_tarjeta = num_tarjeta;
    }

    public String getTipocta() {
        return tipocta;
    }

    public void setTipocta(String tipocta) {
        this.tipocta = tipocta;
    }
}
