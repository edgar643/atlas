/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.saldostdc;

/**
 *
 * @author alexander.lopez.o
 */
public class DatosSaldoTdc {

    private static DatosSaldoTdc datosSaldotdc = new DatosSaldoTdc();
    private String pagoMinPesos = "";
    private String pagoMinDolares = "";
    private String pagoTotPesos = "";
    private String pagoTotDolares = "";
    private String cupoDispAvances = "";
    private String cupoDispTotal = "";
    private String cupoTotal = "";
    private String deudaFechaPesos = "";
    private String deudaFechaDolares = "";
    private String autPendientesPesos = "";
    private String autPendientesDolares = "";
    private String pagosPendientesPesos = "";
    private String pagosPendientesDolares = "";
    private String fechalimitePago = "";
    private String cicloFacturacion = "";
    private String fechaFacturacion = "";
    private String sobreCupo = "";
    private String bloqueoTarjeta = "";
    private String numCuenta = "";
    private String estadoCuenta1 = "";
    private String estadoCuenta2 = "";
    private String estadoCuenta3 = "";
    private String pagoMinimoAlterno = "";
    public static final String PAGO_MINIMO_PESOS = "Pago Mínimo en Pesos";
    public static final String PAGO_MINIMO_DOLARES = "Pago Mínimo en Dólares";
    public static final String PAGO_TOTAL_EN_PESOS = "Pago Total en Pesos";
    public static final String PAGO_TOTAL_EN_DOLARES = "Pago Total en Dólares";
    public static final String CUPO_DISPONIBLE_AVANCES = "Cupo Disponible Avances";
    public static final String CUPO_DISPONIBLE_TOTAL = "Cupo Disponible Total";
    public static final String CUPO_TOTAL = "Cupo Total";
    public static final String DEUDA_FECHA_PESOS = "Deuda a la Fecha en Pesos";
    public static final String DEUDA_FECHA_DOLARES = "Deuda a la Fecha en Dólares";
    public static final String AUT_PENDIENTES_PESOS = "Autorizaciones Pendientes en Pesos";
    public static final String AUT_PENDIENTES_DOLARES = "Autorizaciones Pendientes en Dólares";
    public static final String PAGOS_PENDIENTES_PESOS = "Pagos Pendientes en Pesos";
    public static final String PAGOS_PENDIENTES_DOLARES = "Pagos Pendientes en Dólares";
    public static final String FECHA_LIMITE_PAGO = "Fecha límite de Pago";
    public static final String CICLO_FACTURACION = "Ciclo de Facturación";
    public static final String FECHA_FACTURACION = "Fecha de Facturación";
    public static final String SOBRE_CUPO = "SobreCupo";
    public static final String BLOQUEO_TARJETA = "Bloqueo Tarjeta";
    public static final String NUM_CUENTA = "No. de Cuenta";
    public static final String ESTADO_CUENTA_1 = "Estado de Cuenta 1";
    public static final String ESTADO_CUENTA_2 = "Estado de Cuenta 2";
    public static final String ESTADO_CUENTA_3 = "Estado de Cuenta 3";
    public static final String PAGO_MINIMO_ALTERNATIVO = "Pago Mínimo Alternativo";

    public static DatosSaldoTdc getDatosSaldoTdc() {
        return datosSaldotdc;
    }

    public static void setDatosSaldoh(DatosSaldoTdc datosSaldotc) {
        DatosSaldoTdc.datosSaldotdc = datosSaldotc;
    }

    public String getPagoMinPesos() {
        return pagoMinPesos;
    }

    public void setPagoMinPesos(String pagoMinPesos) {
        this.pagoMinPesos = pagoMinPesos;
    }

    public String getPagoMinDolares() {
        return pagoMinDolares;
    }

    public void setPagoMinDolares(String pagoMinDolares) {
        this.pagoMinDolares = pagoMinDolares;
    }

    public String getPagoTotPesos() {
        return pagoTotPesos;
    }

    public void setPagoTotPesos(String pagoTotPesos) {
        this.pagoTotPesos = pagoTotPesos;
    }

    public String getPagoTotDolares() {
        return pagoTotDolares;
    }

    public void setPagoTotDolares(String pagoTotDolares) {
        this.pagoTotDolares = pagoTotDolares;
    }

    public String getCupoDispAvances() {
        return cupoDispAvances;
    }

    public void setCupoDispAvances(String cupoDispAvances) {
        this.cupoDispAvances = cupoDispAvances;
    }

    public String getCupoDispTotal() {
        return cupoDispTotal;
    }

    public void setCupoDispTotal(String cupoDispTotal) {
        this.cupoDispTotal = cupoDispTotal;
    }

    public String getCupoTotal() {
        return cupoTotal;
    }

    public void setCupoTotal(String cupoTotal) {
        this.cupoTotal = cupoTotal;
    }

    public String getDeudaFechaPesos() {
        return deudaFechaPesos;
    }

    public void setDeudaFechaPesos(String deudaFechaPesos) {
        this.deudaFechaPesos = deudaFechaPesos;
    }

    public String getDeudaFechaDolares() {
        return deudaFechaDolares;
    }

    public void setDeudaFechaDolares(String deudaFechaDolares) {
        this.deudaFechaDolares = deudaFechaDolares;
    }

    public String getAutPendientesPesos() {
        return autPendientesPesos;
    }

    public void setAutPendientesPesos(String autPendientesPesos) {
        this.autPendientesPesos = autPendientesPesos;
    }

    public String getAutPendientesDolares() {
        return autPendientesDolares;
    }

    public void setAutPendientesDolares(String autPendientesDolares) {
        this.autPendientesDolares = autPendientesDolares;
    }

    public String getPagosPendientesPesos() {
        return pagosPendientesPesos;
    }

    public void setPagosPendientesPesos(String pagosPendientesPesos) {
        this.pagosPendientesPesos = pagosPendientesPesos;
    }

    public String getPagosPendientesDolares() {
        return pagosPendientesDolares;
    }

    public void setPagosPendientesDolares(String pagosPendientesDolares) {
        this.pagosPendientesDolares = pagosPendientesDolares;
    }

    public String getFechalimitePago() {
        return fechalimitePago;
    }

    public void setFechalimitePago(String fechalimitePago) {
        this.fechalimitePago = fechalimitePago;
    }

    public String getCicloFacturacion() {
        return cicloFacturacion;
    }

    public void setCicloFacturacion(String cicloFacturacion) {
        this.cicloFacturacion = cicloFacturacion;
    }

    public String getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(String fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public String getSobreCupo() {
        return sobreCupo;
    }

    public void setSobreCupo(String sobreCupo) {
        this.sobreCupo = sobreCupo;
    }

    public String getBloqueoTarjeta() {
        return bloqueoTarjeta;
    }

    public void setBloqueoTarjeta(String bloqueoTarjeta) {
        this.bloqueoTarjeta = bloqueoTarjeta;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getEstadoCuenta1() {
        return estadoCuenta1;
    }

    public void setEstadoCuenta1(String estadoCuenta1) {
        this.estadoCuenta1 = estadoCuenta1;
    }

    public String getEstadoCuenta2() {
        return estadoCuenta2;
    }

    public void setEstadoCuenta2(String estadoCuenta2) {
        this.estadoCuenta2 = estadoCuenta2;
    }

    public String getEstadoCuenta3() {
        return estadoCuenta3;
    }

    public void setEstadoCuenta3(String estadoCuenta3) {
        this.estadoCuenta3 = estadoCuenta3;
    }
    
    public static DatosSaldoTdc getDatosSaldotdc() {
        return datosSaldotdc;
    }

    public String getPagoMinimoAlternativo() {
        return pagoMinimoAlterno;
    }

    public void setPagoMinimoAlternativo(String pagoMinimoAlterno) {
        this.pagoMinimoAlterno = pagoMinimoAlterno;
    }
}
