/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosaterceros;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoMovConv {

    public static infoMovConv movDetallePSE = new infoMovConv();
    private SimpleStringProperty fechatran;
    private SimpleStringProperty fechaaplic;
    private SimpleStringProperty codigoconv;
    private SimpleStringProperty ref1;
    private SimpleStringProperty ref2;
    private SimpleStringProperty ref3;
    private SimpleStringProperty descripcioncanal;
    private SimpleStringProperty montopago;
    private SimpleStringProperty metodopago;
    private SimpleStringProperty modoprocess;
    private SimpleStringProperty tipoctarec;
    private SimpleStringProperty numctarec;
    private SimpleStringProperty nitpagador;
    private SimpleStringProperty nombrepagador;
    private SimpleStringProperty codigobancpagador;
    private SimpleStringProperty numctatarjpagador;
    private SimpleStringProperty valororiginal;
    private SimpleStringProperty pagoefect;
    private SimpleStringProperty pagocheque;
    private SimpleStringProperty valorcomision;
    private SimpleStringProperty codoficinarec;
    private SimpleStringProperty descoficinarec;
    private SimpleStringProperty nomciudad;
    private SimpleStringProperty diascanje;
    private SimpleStringProperty modocaptura;

    public infoMovConv(String fechatran,
            String fechaaplic,
            String codigoconv,
            String ref1,
            String ref2,
            String ref3,
            String descripcioncanal,
            String montopago,
            String metodopago,
            String modoprocess,
            String tipoctarec,
            String numctarec,
            String nitpagador,
            String nombrepagador,
            String codigobancpagador,
            String numctatarjpagador,
            String valororiginal,
            String pagoefect,
            String pagocheque,
            String valorcomision,
            String codoficinarec,
            String descoficinarec,
            String nomciudad,
            String diascanje,
            String modocaptura) {


        this.fechatran = new SimpleStringProperty(fechatran);
        this.fechaaplic = new SimpleStringProperty(fechaaplic);
        this.codigoconv = new SimpleStringProperty(codigoconv);
        this.ref1 = new SimpleStringProperty(ref1);
        this.ref2 = new SimpleStringProperty(ref2);
        this.ref3 = new SimpleStringProperty(ref3);
        this.descripcioncanal = new SimpleStringProperty(descripcioncanal);
        this.montopago = new SimpleStringProperty(montopago);
        this.metodopago = new SimpleStringProperty(metodopago);
        this.modoprocess = new SimpleStringProperty(modoprocess);
        this.tipoctarec = new SimpleStringProperty(tipoctarec);
        this.numctarec = new SimpleStringProperty(numctarec);
        this.nitpagador = new SimpleStringProperty(nitpagador);
        this.nombrepagador = new SimpleStringProperty(nombrepagador);
        this.codigobancpagador = new SimpleStringProperty(codigobancpagador);
        this.numctatarjpagador = new SimpleStringProperty(numctatarjpagador);
        this.valororiginal = new SimpleStringProperty(valororiginal);
        this.pagoefect = new SimpleStringProperty(pagoefect);
        this.pagocheque = new SimpleStringProperty(pagocheque);
        this.valorcomision = new SimpleStringProperty(valorcomision);
        this.codoficinarec = new SimpleStringProperty(codoficinarec);
        this.descoficinarec = new SimpleStringProperty(descoficinarec);
        this.nomciudad = new SimpleStringProperty(nomciudad);
        this.diascanje = new SimpleStringProperty(diascanje);
        this.modocaptura = new SimpleStringProperty(modocaptura);
    }

    public static infoMovConv getMovDetallePSE() {
        return movDetallePSE;
    }

    public static void setMovDetallePSE(infoMovConv movDetallePSE) {
        infoMovConv.movDetallePSE = movDetallePSE;
    }

    private infoMovConv() {
    }

    public String getFechatran() {
        return fechatran.get();
    }

    public void setFechatran(String fechatran) {
        this.fechatran.set(fechatran);
    }

    public String getFechaaplic() {
        return fechaaplic.get();
    }

    public void setFechaaplic(String fechaaplic) {
        this.fechaaplic.set(fechaaplic);
    }

    public String getCodigoconv() {
        return codigoconv.get();
    }

    public void setCodigoconv(String codigoconv) {
        this.codigoconv.set(codigoconv);
    }

    public String getRef1() {
        return ref1.get();
    }

    public void setRef1(String ref1) {
        this.ref1.set(ref1);
    }

    public String getRef2() {
        return ref2.get();
    }

    public void setRef2(String ref2) {
        this.ref2.set(ref2);
    }

    public String getRef3() {
        return ref3.get();
    }

    public void setRef3(String ref3) {
        this.ref3.set(ref3);
    }

    public String getDescripcioncanal() {
        return descripcioncanal.get();
    }

    public void setDescripcioncanal(String descripcioncanal) {
        this.descripcioncanal.set(descripcioncanal);
    }

    public String getMontopago() {
        return montopago.get();
    }

    public void setMontopago(String montopago) {
        this.montopago.set(montopago);
    }

    public String getMetodopago() {
        return metodopago.get();
    }

    public void setMetodopago(String metodopago) {
        this.metodopago.set(metodopago);
    }

    public String getModoprocess() {
        return modoprocess.get();
    }

    public void setModoprocess(String modoprocess) {
        this.modoprocess.get();
    }

    public String getTipoctarec() {
        return tipoctarec.get();
    }

    public void setTipoctarec(String tipoctarec) {
        this.tipoctarec.set(tipoctarec);
    }

    public String getNumctarec() {
        return numctarec.get();
    }

    public void setNumctarec(String numctarec) {
        this.numctarec.get();
    }

    public String getNitpagador() {
        return nitpagador.get();
    }

    public void setNitpagador(String nitpagador) {
        this.nitpagador.set(nitpagador);
    }

    public String getNombrepagador() {
        return nombrepagador.get();
    }

    public void setNombrepagador(String nombrepagador) {
        this.nombrepagador.set(nombrepagador);
    }

    public String getCodigobancpagador() {
        return codigobancpagador.get();
    }

    public void setCodigobancpagador(String codigobancpagador) {
        this.codigobancpagador.set(codigobancpagador);
    }

    public String getNumctatarjpagador() {
        return numctatarjpagador.get();
    }

    public void setNumctatarjpagador(String numctatarjpagador) {
        this.numctatarjpagador.set(numctatarjpagador);
    }

    public String getValororiginal() {
        return valororiginal.get();
    }

    public void setValororiginal(String valororiginal) {
        this.valororiginal.set(valororiginal);
    }

    public String getPagoefect() {
        return pagoefect.get();
    }

    public void setPagoefect(String pagoefect) {
        this.pagoefect.set(pagoefect);
    }

    public String getPagocheque() {
        return pagocheque.get();
    }

    public void setPagocheque(String pagocheque) {
        this.pagocheque.set(pagocheque);
    }

    public String getValorcomision() {
        return valorcomision.get();
    }

    public void setValorcomision(String valorcomision) {
        this.valorcomision.set(valorcomision);
    }

    public String getCodoficinarec() {
        return codoficinarec.get();
    }

    public void setCodoficinarec(String codoficinarec) {
        this.codoficinarec.set(codoficinarec);
    }

    public String getDescoficinarec() {
        return descoficinarec.get();
    }

    public void setDescoficinarec(String descoficinarec) {
        this.descoficinarec.set(descoficinarec);
    }

    public String getNomciudad() {
        return nomciudad.get();
    }

    public void setNomciudad(String nomciudad) {
        this.nomciudad.set(nomciudad);
    }

    public String getDiascanje() {
        return diascanje.get();
    }

    public void setDiascanje(String diascanje) {
        this.diascanje.set(diascanje);
    }

    public String getModocaptura() {
        return modocaptura.get();
    }

    public void setModocaptura(String modocaptura) {
        this.modocaptura.get();
    }
    public static final String tipo_cta_rec = "Tipo de Cuenta Recaudadora";
    public static final String num_cta_rec = "Número de Cuenta Recaudadora";
    public static final String nit_pagador = "Nit del Pagador";
    public static final String nombre_pagador = "Nombre del Pagador";
    public static final String banco_cuenta_pagador = "Banco Cuenta del Pagador";
    public static final String tipo_cta_tdc_pagadora = "Tipo de Cuenta/Tarjeta de Crédito Pagadora";
    public static final String num_cta_tdc_pagadora = "Número de Cuenta/Tarjeta de Crédito Pagadora";
    public static final String forma_pago = "Forma de Pago";
    public static final String valor_orig_fact = "Valor Original de la Factura";
    public static final String valor_transaccion = "Valor Transacción";
    public static final String valor_efectivo = "Valor efectivo";
    public static final String valor_cheque = "Valor cheque";
    public static final String refer1 = "Referencia 1";
    public static final String refer2 = "Referencia 2";
    public static final String refer3 = "Referencia 3";
    public static final String valor_comision = "Valor comisión";
    public static final String hora_transaccion = "Hora de la transacción";
    public static final String desc_cod_resp = "Descripcion código de respuesta";
    public static final String fecha_aplic = "Fecha de aplicación";
    public static final String canal_pago = "Canal de pago";
    public static final String oficina_rec = "Oficina de recaudo";
    public static final String nom_ofi_rec = "Nombre oficina de recaudo";
    public static final String ciudad = "Ciudad";
    public static final String dias_canje = "Dias de Canje";
}
