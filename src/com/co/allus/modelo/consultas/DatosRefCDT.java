/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultas;

/**
 *
 * @author stephania.rojas
 */
public class DatosRefCDT {

    private static DatosRefCDT datosRefCdt = new DatosRefCDT();
    private String numeroCdt = "";
    private String fechaApertura = "";
    private String estado = "";
    private String saldo = "";

    public String getNumeroCdt() {
        return numeroCdt;
    }

    public void setNumeroCdt(String numeroCdt) {
        this.numeroCdt = numeroCdt;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String apertura) {
        this.fechaApertura = apertura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public static DatosRefCDT getDatosRefCDT() {
        return datosRefCdt;
    }

    public static void setDatosRefCDT(DatosRefCDT datosConsultaCdt) {
        DatosRefCDT.datosRefCdt = datosRefCdt;
    }
    public static final String NUMERO_CDT = "Número de CDT";
    public static final String FECHA_APERTURA = "Fecha de Apertura";
    public static final String ESTADO = "Estado";
    public static final String SALDO = "Saldo";
}
