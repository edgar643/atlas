/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tdcprepago;

/**
 *
 * @author stephania.rojas
 */
public class DatosConsultaGeneral {

    private static DatosConsultaGeneral datosConsultaGral = new DatosConsultaGeneral();
    private String tipotarjeta = "";
    private String numtarjeta = "";
    private String nombreEmpresa = "";
    private String numCuenta = "";
    private String saldoDispo = "";
    private String saldoDiaAnterior = "";
    private String descProducto = "";
    private String cicloFact = "";
    private String fechaFacturacion = "";
    private String bloqueoTarjeta = "";
    private String bloqueoCuenta1 = "";
    private String bloqueoCuenta2 = "";
    private String fechaActivacion = "";
    private String indicadorActivacion = "";
    private String fechaActivacionV = "";
    private String indicadorActivacionViejo = "";
    private String fechaUltimaRecarga = "";
    private String valorUltimaRecarga = "";
    private String fechaUltimaDescarga = "";
    private String valorUltimaDescarga = "";
    private String excentoGMF = "";

    public static DatosConsultaGeneral getDatosConsultaGral() {
        return datosConsultaGral;
    }

    public static void setDatosConsultaGral(DatosConsultaGeneral datosConsultaGral) {
        DatosConsultaGeneral.datosConsultaGral = datosConsultaGral;
    }

    public String getTipotarjeta() {
        return tipotarjeta;
    }

    public void setTipotarjeta(String tipotarjeta) {
        this.tipotarjeta = tipotarjeta;
    }

    public String getNumtarjeta() {
        return numtarjeta;
    }

    public void setNumtarjeta(String numtarjeta) {
        this.numtarjeta = numtarjeta;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getSaldoDispo() {
        return saldoDispo;
    }

    public void setSaldoDispo(String saldoDispo) {
        this.saldoDispo = saldoDispo;
    }

    public String getSaldoDiaAnterior() {
        return saldoDiaAnterior;
    }

    public void setSaldoDiaAnterior(String saldoDiaAnterior) {
        this.saldoDiaAnterior = saldoDiaAnterior;
    }

    public String getDescProducto() {
        return descProducto;
    }

    public void setDescProducto(String descProducto) {
        this.descProducto = descProducto;
    }

    public String getCicloFact() {
        return cicloFact;
    }

    public void setCicloFact(String cicloFact) {
        this.cicloFact = cicloFact;
    }

    public String getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(String fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public String getBloqueoTarjeta() {
        return bloqueoTarjeta;
    }

    public void setBloqueoTarjeta(String bloqueoTarjeta) {
        this.bloqueoTarjeta = bloqueoTarjeta;
    }

    public String getBloqueoCuenta1() {
        return bloqueoCuenta1;
    }

    public void setBloqueoCuenta1(String bloqueoCuenta1) {
        this.bloqueoCuenta1 = bloqueoCuenta1;
    }

    public String getBloqueoCuenta2() {
        return bloqueoCuenta2;
    }

    public void setBloqueoCuenta2(String bloqueoCuenta2) {
        this.bloqueoCuenta2 = bloqueoCuenta2;
    }

    public String getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(String fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public String getIndicadorActivacion() {
        return indicadorActivacion;
    }

    public void setIndicadorActivacion(String indicadorActivacion) {
        this.indicadorActivacion = indicadorActivacion;
    }

    public String getFechaActivacionV() {
        return fechaActivacionV;
    }

    public void setFechaActivacionV(String fechaActivacionV) {
        this.fechaActivacionV = fechaActivacionV;
    }

    public String getIndicadorActivacionViejo() {
        return indicadorActivacionViejo;
    }

    public void setIndicadorActivacionViejo(String indicadorActivacionViejo) {
        this.indicadorActivacionViejo = indicadorActivacionViejo;
    }

    public String getFechaUltimaRecarga() {
        return fechaUltimaRecarga;
    }

    public void setFechaUltimaRecarga(String fechaUltimaRecarga) {
        this.fechaUltimaRecarga = fechaUltimaRecarga;
    }

    public String getValorUltimaRecarga() {
        return valorUltimaRecarga;
    }

    public void setValorUltimaRecarga(String valorUltimaRecarga) {
        this.valorUltimaRecarga = valorUltimaRecarga;
    }

    public String getFechaUltimaDescarga() {
        return fechaUltimaDescarga;
    }

    public void setFechaUltimaDescarga(String fechaUltimaDescarga) {
        this.fechaUltimaDescarga = fechaUltimaDescarga;
    }

    public String getValorUltimaDescarga() {
        return valorUltimaDescarga;
    }

    public void setValorUltimaDescarga(String valorUltimaDescarga) {
        this.valorUltimaDescarga = valorUltimaDescarga;
    }

    public String getExcentoGMF() {
        return excentoGMF;
    }

    public void setExcentoGMF(String excentoGMF) {
        this.excentoGMF = excentoGMF;
    }
    public static final String NUMTARJETA = "Número de tarjeta";
    public static final String NOMBREEMPRESA = "Nombre de la empresa";
    public static final String NUMCUENTA = "Número de cuenta";
    public static final String SALDODISPO = "Saldo disponible";
    public static final String SALDODIAANTERIOR = "Saldo del día anterior";
    public static final String DESCPRODUCTO = "Descripción del producto";
    public static final String CICLOFACT = "Ciclo de facturación";
    public static final String FECHAFACTURACION = "Fecha de facturación";
    public static final String BLOQUEOTARJETA = "Bloqueo de tarjeta";
    public static final String BLOQUEOCUENTA1 = "Bloqueo de la cuenta 1";
    public static final String BLOQUEOCUENTA2 = "Bloqueo de la cuenta 2";
    public static final String FECHAACTIVACION = "Fecha activación";
    public static final String INDICADORACTIVACION = "Indicador de Activación Tarjeta Actual (Y o N)";
    public static final String FECHAACTIVACIONV = "Fecha de activación de la tarjeta actual";
    public static final String INDICADORACTIVACIONVIEJO = "Indicador de activación de la tarjeta vieja (N)";
    public static final String FECHAULTIMARECARGA = "Fecha última recarga";
    public static final String VALORULTIMARECARGA = "Valor última recarga";
    public static final String FECHAULTIMADESCARGA = "Fecha última descarga";
    public static final String VALORULTIMADESCARGA = "Valor última descarga";
    public static final String EXCENTOGMF = "Exento de GMF";
}
