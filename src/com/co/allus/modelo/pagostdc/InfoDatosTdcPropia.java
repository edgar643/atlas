/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagostdc;

import com.co.allus.modelo.saldostdc.DatosTarjeta;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;

/**
 *
 * @author alexander.lopez.o
 */
public class InfoDatosTdcPropia {

    private static InfoDatosTdcPropia infoTdcProp = new InfoDatosTdcPropia();
    private modelSaldoTarjeta tarjeta = new modelSaldoTarjeta("", "", "");
    private String tipo_cta_origen = "";
    private String cta_origen = "";
    private String tarjetaCredito = "";
    private String franquicia = "";
    private boolean pago_total = false;
    private boolean pago_minnimo = false;
    private boolean otro_valor = false;
    private boolean pago_pesos = false;
    private boolean pago_dolares = false;
    private String valor_pago_ent;
    private String valor_pago_cent;
    private String valor_pago;
    private String comprobante = "";
    private String fechaPago = "";

    public modelSaldoTarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(modelSaldoTarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(String valor_pago) {
        this.valor_pago = valor_pago;
    }

    public static InfoDatosTdcPropia getInfoTdcProp() {
        return infoTdcProp;
    }

    public static void setInfoTdcProp(InfoDatosTdcPropia infoTdcProp) {
        InfoDatosTdcPropia.infoTdcProp = infoTdcProp;
    }

    public String getValor_pago_ent() {
        return valor_pago_ent;
    }

    public void setValor_pago_ent(String valor_pago_ent) {
        this.valor_pago_ent = valor_pago_ent;
    }

    public String getValor_pago_cent() {
        return valor_pago_cent;
    }

    public void setValor_pago_cent(String valor_pago_cent) {
        this.valor_pago_cent = valor_pago_cent;
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

    public String getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(String tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public boolean isPago_total() {
        return pago_total;
    }

    public void setPago_total(boolean pago_total) {
        this.pago_total = pago_total;
    }

    public boolean isPago_minnimo() {
        return pago_minnimo;
    }

    public void setPago_minnimo(boolean pago_minnimo) {
        this.pago_minnimo = pago_minnimo;
    }

    public boolean isOtro_valor() {
        return otro_valor;
    }

    public void setOtro_valor(boolean otro_valor) {
        this.otro_valor = otro_valor;
    }

    public boolean isPago_pesos() {
        return pago_pesos;
    }

    public void setPago_pesos(boolean pago_pesos) {
        this.pago_pesos = pago_pesos;
    }

    public boolean isPago_dolares() {
        return pago_dolares;
    }

    public void setPago_dolares(boolean pago_dolares) {
        this.pago_dolares = pago_dolares;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }
}
