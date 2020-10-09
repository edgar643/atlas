/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosterceros;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author stephania.rojas
 */
public class DatosPagosTerceros {

    private static DatosPagosTerceros datosPagosTerceros = new DatosPagosTerceros();
    private String codConvenio = "";
    private String ref1 = "";
    private String ref2 = "";
    private String ref3 = "";
    private String indRefFija = "";
    private String numFactura = "";
    private String valorFactura = "";
    private String valorPagado = "";
    private String cuentaRecaudadora = "";
    private String tipoCuenta = "";
    private String claseCuenta = "";
    private String fechaLimite = "";
    private String numCuentaPagadora = "";
    private String tipoCuentaPagadora = "";
    private String indicadorBaseDatos = "";
    private String nombreConvenio = "";
    private boolean valorPagar = false;
    private boolean otroValor = false;
    private int pagoActual = 0;
    private int origenPago;
    private String comprobante = "";
    private String tipoCtaPago = "";
    private String numCtaPago = "";
    private String pagoTotalAcum = "";
    private String montoRestante="";
    private String indMasFacturas="";

    public String getIndMasFacturas() {
        return indMasFacturas;
    }

    public void setIndMasFacturas(String indMasFacturas) {
        this.indMasFacturas = indMasFacturas;
    }
       

    public String getNombreConvenio() {
        return nombreConvenio;
    }

    public void setNombreConvenio(String nombreConvenio) {
        this.nombreConvenio = nombreConvenio;
    }

    public String getMontoRestante() {
        return montoRestante;
    }

    public void setMontoRestante(String montoRestante) {
        this.montoRestante = montoRestante;
    }
        

    public String getPagoTotalAcum() {
        return pagoTotalAcum;
    }

    public void setPagoTotalAcum(String pagoTotalAcum) {
        this.pagoTotalAcum = pagoTotalAcum;
    }

    public String getTipoCtaPago() {
        return tipoCtaPago;
    }

    public void setTipoCtaPago(String tipoCtaPago) {
        this.tipoCtaPago = tipoCtaPago;
    }

    public String getNumCtaPago() {
        return numCtaPago;
    }

    public void setNumCtaPago(String numCtaPago) {
        this.numCtaPago = numCtaPago;
    }

    public String getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(String valorPagado) {
        this.valorPagado = valorPagado;
    }
    private List<InfoTablaPagosTerceros> seleccionPagos = Collections.emptyList();
    
    

    public static void setDatosPagosTerceros(DatosPagosTerceros valor) {
        datosPagosTerceros = valor;
    }

    public static DatosPagosTerceros getDatosPagosTerceros() {
        return datosPagosTerceros;
    }

    public String getCodconvenio() {
        return codConvenio;
    }

    public String getRef1() {
        return ref1;
    }

    public String getRef2() {
        return ref2;
    }

    public String getRef3() {
        return ref3;
    }

    public String getIndreffija() {
        return indRefFija;
    }

    public String getNumfactura() {
        return numFactura;
    }

    public String getValorfactura() {
        return valorFactura;
    }

    public String getCuentarecaudadora() {
        return cuentaRecaudadora;
    }

    public String getTipocuenta() {
        return tipoCuenta;
    }

    public String getClasecuenta() {
        return claseCuenta;
    }

    public String getFechalimite() {
        return fechaLimite;
    }

    public String getNumcuentapagadora() {
        return numCuentaPagadora;
    }

    public String getTipocuentapagadora() {
        return tipoCuentaPagadora;
    }

    public String getIndicadorbasedatos() {
        return indicadorBaseDatos;
    }

    public String getNombreconvenio() {
        return nombreConvenio;
    }

    public int getPagoActual() {
        return pagoActual;
    }

    public void setPagoActual(int pagoActual) {
        this.pagoActual = pagoActual;
    }

    public List<InfoTablaPagosTerceros> getSeleccionPagos() {
        return seleccionPagos;
    }

    public void setSeleccionPagos(List<InfoTablaPagosTerceros> seleccionPagos) {
        this.seleccionPagos = seleccionPagos;
    }

    public boolean isOtroValor() {
        return otroValor;
    }

    public void setOtroValor(boolean otroValor) {
        this.otroValor = otroValor;
    }

    public boolean isValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(boolean valorPagar) {
        this.valorPagar = valorPagar;
    }

    public int getOrigenPago() {
        return origenPago;
    }

    public void setOrigenPago(int origenPago) {
        this.origenPago = origenPago;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }
}
