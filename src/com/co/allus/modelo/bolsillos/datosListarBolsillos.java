/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bolsillos;

/**
 *
 * @author roberto.ceballos
 */
public class datosListarBolsillos {
    
    private static datosListarBolsillos dataListarBolsillos = new datosListarBolsillos();
    
    private String TipoCuenta = "";
    private String NumeroCuenta = "";
    private String NumeroBolsillo = "";
    private String NombreBolsillo = "";
    private String CodigoCategoria = "";
    private String EstadoBolsillo = "";
    private String FechaInicioBolsillo = "";
    private String FechaFinBolsillo = "";
    private String TransferenciaProgramada = "";
    private String PeriodicidadBolsillo = "";
    private String DiaTransProgramada = "";
    private String FecIniTransProgramada = "";
    private String FecFinTransProgramada = "";
    private String FechaCancelacion = "";
    private String ValorMeta = "";
    private String ValorTransferencia = "";
    private String SaldoBolsillo = "";

    public static datosListarBolsillos getDataListarBolsillos() {
        return dataListarBolsillos;
    }

    public static void setDataListarBolsillos(datosListarBolsillos dataListarBolsillos) {
        datosListarBolsillos.dataListarBolsillos = dataListarBolsillos;
    }

    public String getTipoCuenta() {
        return TipoCuenta;
    }

    public void setTipoCuenta(String TipoCuenta) {
        this.TipoCuenta = TipoCuenta;
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    public void setNumeroCuenta(String NumeroCuenta) {
        this.NumeroCuenta = NumeroCuenta;
    }

    public String getNumeroBolsillo() {
        return NumeroBolsillo;
    }

    public void setNumeroBolsillo(String NumeroBolsillo) {
        this.NumeroBolsillo = NumeroBolsillo;
    }

    public String getNombreBolsillo() {
        return NombreBolsillo;
    }

    public void setNombreBolsillo(String NombreBolsillo) {
        this.NombreBolsillo = NombreBolsillo;
    }

    public String getCodigoCategoria() {
        return CodigoCategoria;
    }

    public void setCodigoCategoria(String CodigoCategoria) {
        this.CodigoCategoria = CodigoCategoria;
    }

    public String getEstadoBolsillo() {
        return EstadoBolsillo;
    }

    public void setEstadoBolsillo(String EstadoBolsillo) {
        this.EstadoBolsillo = EstadoBolsillo;
    }

    public String getFechaInicioBolsillo() {
        return FechaInicioBolsillo;
    }

    public void setFechaInicioBolsillo(String FechaInicioBolsillo) {
        this.FechaInicioBolsillo = FechaInicioBolsillo;
    }

    public String getFechaFinBolsillo() {
        return FechaFinBolsillo;
    }

    public void setFechaFinBolsillo(String FechaFinBolsillo) {
        this.FechaFinBolsillo = FechaFinBolsillo;
    }

    public String getTransferenciaProgramada() {
        return TransferenciaProgramada;
    }

    public void setTransferenciaProgramada(String TransferenciaProgramada) {
        this.TransferenciaProgramada = TransferenciaProgramada;
    }

    public String getPeriodicidadBolsillo() {
        return PeriodicidadBolsillo;
    }

    public void setPeriodicidadBolsillo(String PeriodicidadBolsillo) {
        this.PeriodicidadBolsillo = PeriodicidadBolsillo;
    }

    public String getDiaTransProgramada() {
        return DiaTransProgramada;
    }

    public void setDiaTransProgramada(String DiaTransProgramada) {
        this.DiaTransProgramada = DiaTransProgramada;
    }

    public String getFecIniTransProgramada() {
        return FecIniTransProgramada;
    }

    public void setFecIniTransProgramada(String FecIniTransProgramada) {
        this.FecIniTransProgramada = FecIniTransProgramada;
    }

    public String getFecFinTransProgramada() {
        return FecFinTransProgramada;
    }

    public void setFecFinTransProgramada(String FecFinTransProgramada) {
        this.FecFinTransProgramada = FecFinTransProgramada;
    }

    public String getFechaCancelacion() {
        return FechaCancelacion;
    }

    public void setFechaCancelacion(String FechaCancelacion) {
        this.FechaCancelacion = FechaCancelacion;
    }

    public String getValorMeta() {
        return ValorMeta;
    }

    public void setValorMeta(String ValorMeta) {
        this.ValorMeta = ValorMeta;
    }

    public String getValorTransferencia() {
        return ValorTransferencia;
    }

    public void setValorTransferencia(String ValorTransferencia) {
        this.ValorTransferencia = ValorTransferencia;
    }

    public String getSaldoBolsillo() {
        return SaldoBolsillo;
    }

    public void setSaldoBolsillo(String SaldoBolsillo) {
        this.SaldoBolsillo = SaldoBolsillo;
    }


}