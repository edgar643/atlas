/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bolsillos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author roberto.ceballos
 */
public class modeloListarBolsillos {
    
    private SimpleStringProperty tipoCuenta;
    private SimpleStringProperty numeroCuenta;
    private SimpleStringProperty numeroBolsillo;
    private SimpleStringProperty nombreBolsillo;
    private SimpleStringProperty codigoCategoria;
    private SimpleStringProperty estadoBolsillo;
    private SimpleStringProperty fechaInicioBolsillo;
    private SimpleStringProperty fechaFinBolsillo;
    private SimpleStringProperty transferenciaProgramada;
    private SimpleStringProperty periodicidadBolsillo;
    private SimpleStringProperty diaTransProgramada;
    private SimpleStringProperty fecIniTransProgramada;
    private SimpleStringProperty fecFinTransProgramada;
    private SimpleStringProperty fechaCancelacion;
    private SimpleStringProperty valorMeta;
    private SimpleStringProperty valorTransferencia;
    private SimpleStringProperty saldoBolsillo;

    public modeloListarBolsillos(String tipCuenta,
                                String numCuenta,
                                String numBolsillo,
                                String nomBolsillo,
                                String codCategoria,
                                String estBolsillo,
                                String fecInicioBolsillo,
                                String fecFinBolsillo,
                                String traProgramada,
                                String perBolsillo,
                                String diaTraProgramada,
                                String fecIniTraProgramada,
                                String fecFinTraProgramada,
                                String fecCancelacion,
                                String valMeta,
                                String valTransferencia,
                                String salBolsillo) {
        this.tipoCuenta = new SimpleStringProperty(tipCuenta);
        this.numeroCuenta = new SimpleStringProperty(numCuenta);
        this.numeroBolsillo = new SimpleStringProperty(numBolsillo);
        this.nombreBolsillo = new SimpleStringProperty(nomBolsillo);
        this.codigoCategoria = new SimpleStringProperty(codCategoria);
        this.estadoBolsillo = new SimpleStringProperty(estBolsillo);
        this.fechaInicioBolsillo = new SimpleStringProperty(fecInicioBolsillo);
        this.fechaFinBolsillo = new SimpleStringProperty(fecFinBolsillo);
        this.transferenciaProgramada = new SimpleStringProperty(traProgramada);
        this.periodicidadBolsillo = new SimpleStringProperty(perBolsillo);
        this.diaTransProgramada = new SimpleStringProperty(diaTraProgramada);
        this.fecIniTransProgramada = new SimpleStringProperty(fecIniTraProgramada);
        this.fecFinTransProgramada = new SimpleStringProperty(fecFinTraProgramada);
        this.fechaCancelacion = new SimpleStringProperty(fecCancelacion);
        this.valorMeta = new SimpleStringProperty(valMeta);
        this.valorTransferencia = new SimpleStringProperty(valTransferencia);
        this.saldoBolsillo = new SimpleStringProperty(salBolsillo);
    }

    public String getTipoCuenta() {
        return tipoCuenta.get();
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta.set(tipoCuenta);
    }

    public String getNumeroCuenta() {
        return numeroCuenta.get();
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta.set(numeroCuenta);
    }

    public String getNumeroBolsillo() {
        return numeroBolsillo.get();
    }

    public void setNumeroBolsillo(String numeroBolsillo) {
        this.numeroBolsillo.set(numeroBolsillo);
    }

    public String getNombreBolsillo() {
        return nombreBolsillo.get();
    }

    public void setNombreBolsillo(String nombreBolsillo) {
        this.nombreBolsillo.set(nombreBolsillo);
    }

    public String getCodigoCategoria() {
        return codigoCategoria.get();
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria.set(codigoCategoria);
    }

    public String getEstadoBolsillo() {
        return estadoBolsillo.get();
    }

    public void setEstadoBolsillo(String estadoBolsillo) {
        this.estadoBolsillo.set(estadoBolsillo);
    }

    public String getFechaInicioBolsillo() {
        return fechaInicioBolsillo.get();
    }

    public void setFechaInicioBolsillo(String fechaInicioBolsillo) {
        this.fechaInicioBolsillo.set(fechaInicioBolsillo);
    }

    public String getFechaFinBolsillo() {
        return fechaFinBolsillo.get();
    }

    public void setFechaFinBolsillo(String fechaFinBolsillo) {
        this.fechaFinBolsillo.set(fechaFinBolsillo);
    }

    public String getTransferenciaProgramada() {
        return transferenciaProgramada.get();
    }

    public void setTransferenciaProgramada(String transferenciaProgramada) {
        this.transferenciaProgramada.set(transferenciaProgramada);
    }

    public String getPeriodicidadBolsillo() {
        return periodicidadBolsillo.get();
    }

    public void setPeriodicidadBolsillo(String periodicidadBolsillo) {
        this.periodicidadBolsillo.set(periodicidadBolsillo);
    }

    public String getDiaTransProgramada() {
        return diaTransProgramada.get();
    }

    public void setDiaTransProgramada(String diaTransProgramada) {
        this.diaTransProgramada.set(diaTransProgramada);
    }

    public String getFecIniTransProgramada() {
        return fecIniTransProgramada.get();
    }

    public void setFecIniTransProgramada(String fecIniTransProgramada) {
        this.fecIniTransProgramada.set(fecIniTransProgramada);
    }

    public String getFecFinTransProgramada() {
        return fecFinTransProgramada.get();
    }

    public void setFecFinTransProgramada(String fecFinTransProgramada) {
        this.fecFinTransProgramada.set(fecFinTransProgramada);
    }

    public String getFechaCancelacion() {
        return fechaCancelacion.get();
    }

    public void setFechaCancelacion(String fechaCancelacion) {
        this.fechaCancelacion.set(fechaCancelacion);
    }

    public String getValorMeta() {
        return valorMeta.get();
    }

    public void setValorMeta(String valorMeta) {
        this.valorMeta.set(valorMeta);
    }

    public String getValorTransferencia() {
        return valorTransferencia.get();
    }

    public void setValorTransferencia(String valorTransferencia) {
        this.valorTransferencia.set(valorTransferencia);
    }

    public String getSaldoBolsillo() {
        return saldoBolsillo.get();
    }

    public void setSaldoBolsillo(String saldoBolsillo) {
        this.saldoBolsillo.set(saldoBolsillo);
    }

}
