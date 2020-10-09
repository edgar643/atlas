/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.transfctaprop;

import com.co.allus.modelo.pagosaterceros.infoPago;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTranferenciaCtaProp {

    private static infoTranferenciaCtaProp infoTranfCtaProp = new infoTranferenciaCtaProp();
    private String tipo_cta_origen = "";
    private String cta_origen = "";
    private String tipo_cta_destino = "";
    private String cta_destino = "";
    private String valor_transferir = "";
    private String valor_transferirEnt = "";
    private String valor_transferirCent = "";
    private String comprobante = "";
    private String fechaTransf = "";

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getFechaTransf() {
        return fechaTransf;
    }

    public void setFechaTransf(String fechaTransf) {
        this.fechaTransf = fechaTransf;
    }

    public String getValor_transferirEnt() {
        return valor_transferirEnt;
    }

    public void setValor_transferirEnt(String valor_transferirEnt) {
        this.valor_transferirEnt = valor_transferirEnt;
    }

    public String getValor_transferirCent() {
        return valor_transferirCent;
    }

    public void setValor_transferirCent(String valor_transferirCent) {
        this.valor_transferirCent = valor_transferirCent;
    }

    public static void setInfoTranfCtaProp(infoTranferenciaCtaProp valor) {
        infoTranfCtaProp = valor;
    }

    public static infoTranferenciaCtaProp getInfoTranfCtaProp() {
        return infoTranfCtaProp;
    }

    public String getTipo_cta_origen() {
        return tipo_cta_origen;
    }

    public void setTipo_cta_origen(String tipo_cta_origen) {
        this.tipo_cta_origen = tipo_cta_origen;
    }

    public String getCta_origen() {
        return cta_origen;
    }

    public void setCta_origen(String cta_origen) {
        this.cta_origen = cta_origen;
    }

    public String getTipo_cta_destino() {
        return tipo_cta_destino;
    }

    public void setTipo_cta_destino(String tipo_cta_destino) {
        this.tipo_cta_destino = tipo_cta_destino;
    }

    public String getCta_destino() {
        return cta_destino;
    }

    public void setCta_destino(String cta_destino) {
        this.cta_destino = cta_destino;
    }

    public String getValor_transferir() {
        return valor_transferir;
    }

    public void setValor_transferir(String valor_transferir) {
        this.valor_transferir = valor_transferir;
    }
}
