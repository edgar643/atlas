/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.contraCheques;

/**
 *
 * @author alexander.lopez.o
 */
public class dataContraorden {

    private static dataContraorden datosCCheque = new dataContraorden();
    private String chequeini = "";
    private String chequefin = "";
    private String numcta = "";
    private String fechaHora = "";

    public static dataContraorden getDatosCCheque() {
        return datosCCheque;
    }

    public static void setDatosCCheque(dataContraorden datosCCheque) {
        datosCCheque = datosCCheque;
    }

    public String getChequeini() {
        return chequeini;
    }

    public void setChequeini(String chequeini) {
        this.chequeini = chequeini;
    }

    public String getChequefin() {
        return chequefin;
    }

    public void setChequefin(String chequefin) {
        this.chequefin = chequefin;
    }

    public String getNumcta() {
        return numcta;
    }

    public void setNumcta(String numcta) {
        this.numcta = numcta;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
