/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

/**
 *
 * @author alexander.lopez.o
 */
public class DatosSaldoPuntosCol {

    private static DatosSaldoPuntosCol datosSaldoPuntosCol = new DatosSaldoPuntosCol();
    private String estado = "";
    private String saldototal = "";
    private String saldo_prox_vencer = "";
    private String saldo_total = "";
    private String fecha_vencimiento = "";

    public static DatosSaldoPuntosCol getDatosSaldoPuntosCol() {
        return datosSaldoPuntosCol;
    }

    public static void setDatosSaldoPuntosCol(DatosSaldoPuntosCol datosSaldoPuntosCol) {
        DatosSaldoPuntosCol.datosSaldoPuntosCol = datosSaldoPuntosCol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSaldototal() {
        return saldototal;
    }

    public void setSaldototal(String saldototal) {
        this.saldototal = saldototal;
    }

    public String getSaldo_prox_vencer() {
        return saldo_prox_vencer;
    }

    public void setSaldo_prox_vencer(String saldo_prox_vencer) {
        this.saldo_prox_vencer = saldo_prox_vencer;
    }

    public String getSaldo_total() {
        return saldo_total;
    }

    public void setSaldo_total(String saldo_total) {
        this.saldo_total = saldo_total;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public static DatosSaldoPuntosCol getDatosSaldopuntosCol() {
        return datosSaldoPuntosCol;
    }

    public static void setDatosSaldopuntosCol(DatosSaldoPuntosCol datosSaldo) {
        DatosSaldoPuntosCol.datosSaldoPuntosCol = datosSaldo;
    }
    public static final String ESTADO = "Estado";
    public static final String SALDO_TOTAL = "Saldo Total";
    public static final String SALDO_VENCER = "Saldo próximo a vencer";
    public static final String FECHA_VENCIMIENTO = "Fecha del proximo vencimiento";
}
