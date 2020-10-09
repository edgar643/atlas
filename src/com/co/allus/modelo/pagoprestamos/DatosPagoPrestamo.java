/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagoprestamos;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author stephania.rojas
 */
public class DatosPagoPrestamo {

    private static DatosPagoPrestamo datosTablaDetalle = new DatosPagoPrestamo();
    private String numeroCredito = "";
    private String tipoPrestamo = "";
    private String fechaDesembolso = "";
    private String fechaProxPago = "";
    private String pagoCuotaMinEnt = "";
    private String pagoCuotaMinCent = "";
    private String pagoTotalEnt = "";
    private String pagoTotalCent = "";
    private String valorInicialEnt = "";
    private String valorInicialCent = "";
    private String capitalVigenteEnt = "";
    private String capitalVigenteCent = "";
    private String interesesMoraEnt = "";
    private String interesesMoraCent = "";
    private String interesesCorrientesEnt = "";
    private String interesesCorrientesCent = "";
    private String otrosCargosEnt = "";
    private String otrosCargosCent = "";
    private boolean otro_valor_sel = false;
    private boolean pago_min_sel = false;
    private boolean pago_total_sel = false;
    private String otroValor = "";
    private String otroValorEnt = "";
    private String otroValorCent = "";
    private String pagoMin = "";
    private String pagoMinEnt = "";
    private String pagoMinCent = "";
    private String pago_Total = "";
    private String pago_TotalEnt = "";
    private String pago_TotalCent = "";
    private String tipoCtaPago = "";
    private String numctaPago = "";
    private int pagoActual = 0;
    private int origenPago;
    private String comprobante = "";

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getTipoCtaPago() {
        return tipoCtaPago;
    }

    public void setTipoCtaPago(String tipoCtaPago) {
        this.tipoCtaPago = tipoCtaPago;
    }

    public String getNumctaPago() {
        return numctaPago;
    }

    public void setNumctaPago(String numctaPago) {
        this.numctaPago = numctaPago;
    }

    public int getOrigenPago() {
        return origenPago;
    }

    public void setOrigenPago(int origenPago) {
        this.origenPago = origenPago;
    }

    public int getPagoActual() {
        return pagoActual;
    }

    public void setPagoActual(int pagoActual) {
        this.pagoActual = pagoActual;
    }
    private List<infoTablaPagosPrest> seleccionPagos = Collections.emptyList();

    public List<infoTablaPagosPrest> getSeleccionPagos() {
        return seleccionPagos;
    }

    public void setSeleccionPagos(List<infoTablaPagosPrest> seleccionPagos) {
        this.seleccionPagos = seleccionPagos;
    }

    public boolean isOtro_valor_sel() {
        return otro_valor_sel;
    }

    public void setOtro_valor_sel(boolean otro_valor_sel) {
        this.otro_valor_sel = otro_valor_sel;
    }

    public boolean isPago_min_sel() {
        return pago_min_sel;
    }

    public void setPago_min_sel(boolean pago_min_sel) {
        this.pago_min_sel = pago_min_sel;
    }

    public boolean isPago_total_sel() {
        return pago_total_sel;
    }

    public void setPago_total_sel(boolean pago_total_sel) {
        this.pago_total_sel = pago_total_sel;
    }

    public String getOtroValor() {
        return otroValor;
    }

    public void setOtroValor(String otroValor) {
        this.otroValor = otroValor;
    }

    public String getOtroValorEnt() {
        return otroValorEnt;
    }

    public void setOtroValorEnt(String otroValorEnt) {
        this.otroValorEnt = otroValorEnt;
    }

    public String getOtroValorCent() {
        return otroValorCent;
    }

    public void setOtroValorCent(String otroValorCent) {
        this.otroValorCent = otroValorCent;
    }

    public String getPagoMin() {
        return pagoMin;
    }

    public void setPagoMin(String pagoMin) {
        this.pagoMin = pagoMin;
    }

    public String getPagoMinEnt() {
        return pagoMinEnt;
    }

    public void setPagoMinEnt(String pagoMinEnt) {
        this.pagoMinEnt = pagoMinEnt;
    }

    public String getPagoMinCent() {
        return pagoMinCent;
    }

    public void setPagoMinCent(String pagoMinCent) {
        this.pagoMinCent = pagoMinCent;
    }

    public String getPago_Total() {
        return pago_Total;
    }

    public void setPago_Total(String pago_Total) {
        this.pago_Total = pago_Total;
    }

    public String getPago_TotalEnt() {
        return pago_TotalEnt;
    }

    public void setPago_TotalEnt(String pago_TotalEnt) {
        this.pago_TotalEnt = pago_TotalEnt;
    }

    public String getPago_TotalCent() {
        return pago_TotalCent;
    }

    public void setPago_TotalCent(String pago_TotalCent) {
        this.pago_TotalCent = pago_TotalCent;
    }

    public static void setDatosTablaDetalle(DatosPagoPrestamo valor) {
        datosTablaDetalle = valor;
    }

    public static DatosPagoPrestamo getDatosTablaDetalle() {
        return datosTablaDetalle;
    }

    public String getNumeroCredito() {
        return numeroCredito;
    }

    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public String getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(String fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public String getFechaProxPago() {
        return fechaProxPago;
    }

    public void setFechaProxPago(String fechaProxPago) {
        this.fechaProxPago = fechaProxPago;
    }

    public String getPagoCuotaMinEnt() {
        return pagoCuotaMinEnt;
    }

    public void setPagoCuotaMinEnt(String pagoCuotaMinEnt) {
        this.pagoCuotaMinEnt = pagoCuotaMinEnt;
    }

    public String getPagoCuotaMinCent() {
        return pagoCuotaMinCent;
    }

    public void setPagoCuotaMinCent(String pagoCuotaMinCent) {
        this.pagoCuotaMinCent = pagoCuotaMinCent;
    }

    public String getPagoTotalEnt() {
        return pagoTotalEnt;
    }

    public void setPagoTotalEnt(String pagoTotalEnt) {
        this.pagoTotalEnt = pagoTotalEnt;
    }

    public String getPagoTotalCent() {
        return pagoTotalCent;
    }

    public void setPagoTotalCent(String pagoTotalCent) {
        this.pagoTotalCent = pagoTotalCent;
    }

    public String getValorInicialEnt() {
        return valorInicialEnt;
    }

    public void setValorInicialEnt(String valorInicialEnt) {
        this.valorInicialEnt = valorInicialEnt;
    }

    public String getValorInicialCent() {
        return valorInicialCent;
    }

    public void setValorInicialCent(String valorInicialCent) {
        this.valorInicialCent = valorInicialCent;
    }

    public String getCapitalVigenteEnt() {
        return capitalVigenteEnt;
    }

    public void setCapitalVigenteEnt(String capitalVigenteEnt) {
        this.capitalVigenteEnt = capitalVigenteEnt;
    }

    public String getCapitalVigenteCent() {
        return capitalVigenteCent;
    }

    public void setCapitalVigenteCent(String capitalVigenteCent) {
        this.capitalVigenteCent = capitalVigenteCent;
    }

    public String getInteresesMoraEnt() {
        return interesesMoraEnt;
    }

    public void setInteresesMoraEnt(String interesesMoraEnt) {
        this.interesesMoraEnt = interesesMoraEnt;
    }

    public String getInteresesMoraCent() {
        return interesesMoraCent;
    }

    public void setInteresesMoraCent(String interesesMoraCent) {
        this.interesesMoraCent = interesesMoraCent;
    }

    public String getInteresesCorrientesEnt() {
        return interesesCorrientesEnt;
    }

    public void setInteresesCorrientesEnt(String interesesCorrientesEnt) {
        this.interesesCorrientesEnt = interesesCorrientesEnt;
    }

    public String getInteresesCorrientesCent() {
        return interesesCorrientesCent;
    }

    public void setInteresesCorrientesCent(String interesesCorrientesCent) {
        this.interesesCorrientesCent = interesesCorrientesCent;
    }

    public String getOtrosCargosEnt() {
        return otrosCargosEnt;
    }

    public void setOtrosCargosEnt(String otrosCargosEnt) {
        this.otrosCargosEnt = otrosCargosEnt;
    }

    public String getOtrosCargosCent() {
        return otrosCargosCent;
    }

    public void setOtrosCargosCent(String otrosCargosCent) {
        this.otrosCargosCent = otrosCargosCent;
    }
    public static final String NUMERO_CREDITO = "Número Crédito";
    public static final String TIPO_PRESTAMO = "Tipo Préstamo";
    public static final String FECHA_DESEMBOLSO = "Fecha de desembolso";
    public static final String FECHA_PROXIMO_PAGO = "Fecha próximo pago";
    public static final String PAGO_MIN = "Pago cuota mínima";
    public static final String PAGO_TOTAL = "Pago total";
    public static final String VALOR_INICIAL = "Valor inicial";
    public static final String CAPITAL_VIGENTE = "Capital Vigente";
    public static final String INTERESES_MORA = "Intereses de mora";
    public static final String INTERESES_CORRIENTES = "Intereses corrientes";
    public static final String OTROS_CARGOS = "Otros cargos";
}
