/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bolsillos;

/**
 *
 * @author roberto.ceballos
 */
public class datosDetalleBolsillos {
    
   private static datosDetalleBolsillos dataDetalleBolsillos = new datosDetalleBolsillos();
    
    private String saldoBolsillo = "";
    private String numeroBolsillo = "";
    private String fechaAperturaBolsillo = "";
    private String fechaInicioMeta = "";
    private String fechaFinMeta = "";
    private String valorMeta = "";
    private String fechaCancelacion = "";
    private String transferenciaProgramada = "";
    private String periodicidadTransferencia = "";
    private String diaTransProgramada = "";
    private String fecIniTransProgramada = "";
    private String fecFinTransProgramada = "";
    private String valorTransferencia = "";

    public static datosDetalleBolsillos getDataDetalleBolsillos() {
        return dataDetalleBolsillos;
    }

    public static void setDataDetalleBolsillos(datosDetalleBolsillos dataDetalleBolsillos) {
        datosDetalleBolsillos.dataDetalleBolsillos = dataDetalleBolsillos;
    }

    public String getSaldoBolsillo() {
        return saldoBolsillo;
    }

    public void setSaldoBolsillo(String saldoBolsillo) {
        this.saldoBolsillo = saldoBolsillo;
    }

    public String getNumeroBolsillo() {
        return numeroBolsillo;
    }

    public void setNumeroBolsillo(String numeroBolsillo) {
        this.numeroBolsillo = numeroBolsillo;
    }

    public String getFechaAperturaBolsillo() {
        return fechaAperturaBolsillo;
    }

    public void setFechaAperturaBolsillo(String fechaAperturaBolsillo) {
        this.fechaAperturaBolsillo = fechaAperturaBolsillo;
    }

    public String getFechaInicioMeta() {
        return fechaInicioMeta;
    }

    public void setFechaInicioMeta(String fechaInicioMeta) {
        this.fechaInicioMeta = fechaInicioMeta;
    }

    public String getFechaFinMeta() {
        return fechaFinMeta;
    }

    public void setFechaFinMeta(String fechaFinMeta) {
        this.fechaFinMeta = fechaFinMeta;
    }

    public String getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(String valorMeta) {
        this.valorMeta = valorMeta;
    }

    public String getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(String fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getTransferenciaProgramada() {
        return transferenciaProgramada;
    }

    public void setTransferenciaProgramada(String transferenciaProgramada) {
        this.transferenciaProgramada = transferenciaProgramada;
    }

    public String getPeriodicidadTransferencia() {
        return periodicidadTransferencia;
    }

    public void setPeriodicidadTransferencia(String periodicidadTransferencia) {
        this.periodicidadTransferencia = periodicidadTransferencia;
    }

    public String getDiaTransProgramada() {
        return diaTransProgramada;
    }

    public void setDiaTransProgramada(String diaTransProgramada) {
        this.diaTransProgramada = diaTransProgramada;
    }

    public String getFecIniTransProgramada() {
        return fecIniTransProgramada;
    }

    public void setFecIniTransProgramada(String fecIniTransProgramada) {
        this.fecIniTransProgramada = fecIniTransProgramada;
    }

    public String getFecFinTransProgramada() {
        return fecFinTransProgramada;
    }

    public void setFecFinTransProgramada(String fecFinTransProgramada) {
        this.fecFinTransProgramada = fecFinTransProgramada;
    }

    public String getValorTransferencia() {
        return valorTransferencia;
    }

    public void setValorTransferencia(String valorTransferencia) {
        this.valorTransferencia = valorTransferencia;
    }

    public static final String SALDOBOLSILLO = "Saldo";
    public static final String NUMEROBOLSILLO = "Numero";
    public static final String FECHAAPERTURABOLSILLO = "Fecha apertura";
    public static final String FECHAINICIOMETA = "Fecha Inicio de tu Meta";
    public static final String FECHAFINMETA = "Fecha Fin de tu Meta";
    public static final String VALORMETA = "Valor Total de la Meta";
    public static final String FECHACANCELACION = "Fecha Cancelación";
    public static final String TRANSFERENCIAPROGRAMADA = "Transferencia Programada";
    public static final String PERIODICIDADTRANSFERENCIA = "Periodicidad de la Transferencia Programada";
    public static final String DIATRANSPROGRAMADA = "Día transferencia programada";
    public static final String FECINITRANSPROGRAMADA = "Fecha inicio transferencia";
    public static final String FECFINTRANSPROGRAMADA = "Fecha fin transferencia";
    public static final String VALORTRANSFERENCIA = "Valor transferencia";
}
