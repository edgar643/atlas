/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.avancestdc;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author roberto.ceballos
 */
public class modeloAvancesTdc {
    
    private SimpleStringProperty tipoDocumentoOrigen;
    private SimpleStringProperty numeroDocumentoOrigen;
    private SimpleStringProperty nombreClienteOrigen;
    private SimpleStringProperty numeroTarjeta;
    private SimpleStringProperty tipoDocumentoDestino;
    private SimpleStringProperty numeroDocumentoDestino;
    private SimpleStringProperty nombreClienteDestino;
    private SimpleStringProperty tipoCuentaDestino;
    private SimpleStringProperty numeroCuenta;
    private SimpleStringProperty fechaAvance;
    private SimpleStringProperty horaAvance;
    private SimpleStringProperty valorAvance;
    private SimpleStringProperty franquicia;

    public modeloAvancesTdc(String tipoDocumentoOrigen,
                            String numeroDocumentoOrigen,
                            String nombreClienteOrigen,
                            String numeroTarjeta,
                            String tipoDocumentoDestino,
                            String numeroDocumentoDestino,
                            String nombreClienteDestino,
                            String tipoCuentaDestino,
                            String numeroCuenta,
                            String fechaAvance,
                            String horaAvance,
                            String valorAvance,
                            String franquicia) {
        this.tipoDocumentoOrigen = new SimpleStringProperty(tipoDocumentoOrigen);
        this.numeroDocumentoOrigen = new SimpleStringProperty(numeroDocumentoOrigen);
        this.nombreClienteOrigen = new SimpleStringProperty(nombreClienteOrigen);
        this.numeroTarjeta = new SimpleStringProperty(numeroTarjeta);
        this.tipoDocumentoDestino = new SimpleStringProperty(tipoDocumentoDestino);
        this.numeroDocumentoDestino = new SimpleStringProperty(numeroDocumentoDestino);
        this.nombreClienteDestino = new SimpleStringProperty(nombreClienteDestino);
        this.tipoCuentaDestino = new SimpleStringProperty(tipoCuentaDestino);
        this.numeroCuenta = new SimpleStringProperty(numeroCuenta);
        this.fechaAvance = new SimpleStringProperty(fechaAvance);
        this.horaAvance = new SimpleStringProperty(horaAvance);
        this.valorAvance = new SimpleStringProperty(valorAvance);
        this.franquicia = new SimpleStringProperty(franquicia);
    }

    public String getTipoDocumentoOrigen() {
        return tipoDocumentoOrigen.get();
    }

    public void setTipoDocumentoOrigen(String tipoDocumentoOrigen) {
        this.tipoDocumentoOrigen.set(tipoDocumentoOrigen);
    }

    public String getNumeroDocumentoOrigen() {
        return numeroDocumentoOrigen.get();
    }

    public void setNumeroDocumentoOrigen(String numeroDocumentoOrigen) {
        this.numeroDocumentoOrigen.set(numeroDocumentoOrigen);
    }

    public String getNombreClienteOrigen() {
        return nombreClienteOrigen.get();
    }

    public void setNombreClienteOrigen(String nombreClienteOrigen) {
        this.nombreClienteOrigen.set(nombreClienteOrigen);
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta.get();
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta.set(numeroTarjeta);
    }

    public String getTipoDocumentoDestino() {
        return tipoDocumentoDestino.get();
    }

    public void setTipoDocumentoDestino(String tipoDocumentoDestino) {
        this.tipoDocumentoDestino.set(tipoDocumentoDestino);
    }

    public String getNumeroDocumentoDestino() {
        return numeroDocumentoDestino.get();
    }

    public void setNumeroDocumentoDestino(String numeroDocumentoDestino) {
        this.numeroDocumentoDestino.set(numeroDocumentoDestino);
    }

    public String getNombreClienteDestino() {
        return nombreClienteDestino.get();
    }

    public void setNombreClienteDestino(String nombreClienteDestino) {
        this.nombreClienteDestino.set(nombreClienteDestino);
    }

    public String getTipoCuentaDestino() {
        return tipoCuentaDestino.get();
    }

    public void setTipoCuentaDestino(String tipoCuentaDestino) {
        this.tipoCuentaDestino.set(tipoCuentaDestino);
    }

    public String getNumeroCuenta() {
        return numeroCuenta.get();
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta.set(numeroCuenta);
    }

    public String getFechaAvance() {
        return fechaAvance.get();
    }

    public void setFechaAvance(String fechaAvance) {
        this.fechaAvance.set(fechaAvance);
    }

    public String getHoraAvance() {
        return horaAvance.get();
    }

    public void setHoraAvance(String horaAvance) {
        this.horaAvance.set(horaAvance);
    }

    public String getValorAvance() {
        return valorAvance.get();
    }

    public void setValorAvance(String valorAvance) {
        this.valorAvance.set(valorAvance);
    }

    public String getFranquicia() {
        return franquicia.get();
    }

    public void setFranquicia(String franquicia) {
        this.franquicia.set(franquicia);
    }

}