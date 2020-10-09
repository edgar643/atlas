/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.transestadocuentas;

/**
 *
 * @author alexander.lopez.o
 */
public class dataCuentasInscritas {

    public static dataCuentasInscritas infoCtas = new dataCuentasInscritas();
    private String numcta = "";
    private String tipoCta = "";
    private String estado = "";
    private String banco = "";
    private String codigoBanco = "";

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public static dataCuentasInscritas getInfoCtas() {
        return infoCtas;
    }

    public static void setInfoCtas(dataCuentasInscritas infoCtas) {
        dataCuentasInscritas.infoCtas = infoCtas;
    }

    public String getNumcta() {
        return numcta;
    }

    public void setNumcta(String numcta) {
        this.numcta = numcta;
    }

    public String getTipoCta() {
        return tipoCta;
    }

    public void setTipoCta(String tipoCta) {
        this.tipoCta = tipoCta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
}
