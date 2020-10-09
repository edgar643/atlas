/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosaterceros;

/**
 *
 * @author alexander.lopez.o
 */
public class infoPago {

    private static infoPago datainfopago = new infoPago();
    private String comprobante;
    private String valorPagarent = "";
    private String valorpagarCent = "";
    private String valorPagarCompleto = "";
    private String nombreConvenio = "";
    private String referenciaConvenio = "";
    private String cuentaOrigen = "";
    private String descripcionCuentaOrigen = "";
    private String numeroFactura = "";
    private String concepto1 = "";
    private String valor1 = "";
    private String concepto2 = "";
    private String valor2 = "";
    private String concepto3 = "";
    private String valor3 = "";
    private String concepto4 = "";
    private String valor4 = "";
    private String concepto5 = "";
    private String valor5 = "";
    private String concepto6 = "";
    private String valor6 = "";
    private String comprobantefinPago = "";
    private String fechaPago = "";
    private String referenciaPago1 = "";
    private String referenciaPago2 = "";

    public static void setInfoPago(infoPago valor) {
        datainfopago = valor;
    }

    public static infoPago getInfoPago() {
        return datainfopago;
    }

    public String getReferenciaPago1() {
        return referenciaPago1;
    }

    public void setReferenciaPago1(String referenciaPago) {
        this.referenciaPago1 = referenciaPago;
    }

    public String getValorPagarent() {
        return valorPagarent;
    }

    public String getValorPagarCompleto() {
        return valorPagarCompleto;
    }

    public void setValorPagarCompleto(String valorPagarCompleto) {
        this.valorPagarCompleto = valorPagarCompleto;
    }

    public void setValorPagarent(String valorPagarent) {
        this.valorPagarent = valorPagarent;
    }

    public String getValorpagarCent() {
        return valorpagarCent;
    }

    public void setValorpagarCent(String valorpagarCent) {
        this.valorpagarCent = valorpagarCent;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getNombreConvenio() {
        return nombreConvenio;
    }

    public void setNombreConvenio(String nombreConvenio) {
        this.nombreConvenio = nombreConvenio;
    }

    public String getReferenciaConvenio() {
        return referenciaConvenio;
    }

    public void setReferenciaConvenio(String referenciaConvenio) {
        this.referenciaConvenio = referenciaConvenio;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getDescripcionCuentaOrigen() {
        return descripcionCuentaOrigen;
    }

    public void setDescripcionCuentaOrigen(String descripcionCuentaOrigen) {
        this.descripcionCuentaOrigen = descripcionCuentaOrigen;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getConcepto1() {
        return concepto1;
    }

    public void setConcepto1(String concepto1) {
        this.concepto1 = concepto1;
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getConcepto2() {
        return concepto2;
    }

    public void setConcepto2(String concepto2) {
        this.concepto2 = concepto2;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    public String getConcepto3() {
        return concepto3;
    }

    public void setConcepto3(String concepto3) {
        this.concepto3 = concepto3;
    }

    public String getValor3() {
        return valor3;
    }

    public void setValor3(String valor3) {
        this.valor3 = valor3;
    }

    public String getConcepto4() {
        return concepto4;
    }

    public void setConcepto4(String concepto4) {
        this.concepto4 = concepto4;
    }

    public String getValor4() {
        return valor4;
    }

    public void setValor4(String valor4) {
        this.valor4 = valor4;
    }

    public String getConcepto5() {
        return concepto5;
    }

    public void setConcepto5(String concepto5) {
        this.concepto5 = concepto5;
    }

    public String getValor5() {
        return valor5;
    }

    public void setValor5(String valor5) {
        this.valor5 = valor5;
    }

    public String getConcepto6() {
        return concepto6;
    }

    public void setConcepto6(String concepto6) {
        this.concepto6 = concepto6;
    }

    public String getValor6() {
        return valor6;
    }

    public void setValor6(String valor6) {
        this.valor6 = valor6;
    }

    public String getComprobantefinPago() {
        return comprobantefinPago;
    }

    public void setComprobantefinPago(String comprobantefinPago) {
        this.comprobantefinPago = comprobantefinPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getReferenciaPago2() {
        return referenciaPago2;
    }

    public void setReferenciaPago2(String referenciaPago2) {
        this.referenciaPago2 = referenciaPago2;
    }
}
