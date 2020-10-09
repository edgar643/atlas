/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.avancestdc;

/**
 *
 * @author roberto.ceballos
 */
public class datosAvancesTdc {
    
    private static datosAvancesTdc dataAvancesTdc = new datosAvancesTdc();
    
    private String tipoDocumentoOrigen = "";
    private String numeroDocumentoOrigen = "";
    private String nombreClienteOrigen = "";
    private String numeroTarjeta = "";
    private String tipoDocumentoDestino = "";
    private String numeroDocumentoDestino = "";
    private String nombreClienteDestino = "";
    private String tipoCuentaDestino = "";
    private String numeroCuenta = "";
    private String fechaAvance = "";
    private String horaAvance = "";
    private String valorAvance = "";
    private String franquicia = "";

    public static datosAvancesTdc getDataAvancesTdc() {
        return dataAvancesTdc;
    }

    public static void setDataAvancesTdc(datosAvancesTdc dataAvancesTdc) {
        datosAvancesTdc.dataAvancesTdc = dataAvancesTdc;
    }

    public String getTipoDocumentoOrigen() {
        return tipoDocumentoOrigen;
    }

    public void setTipoDocumentoOrigen(String tipoDocumentoOrigen) {
        this.tipoDocumentoOrigen = tipoDocumentoOrigen;
    }

    public String getNumeroDocumentoOrigen() {
        return numeroDocumentoOrigen;
    }

    public void setNumeroDocumentoOrigen(String numeroDocumentoOrigen) {
        this.numeroDocumentoOrigen = numeroDocumentoOrigen;
    }

    public String getNombreClienteOrigen() {
        return nombreClienteOrigen;
    }

    public void setNombreClienteOrigen(String nombreClienteOrigen) {
        this.nombreClienteOrigen = nombreClienteOrigen;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoDocumentoDestino() {
        return tipoDocumentoDestino;
    }

    public void setTipoDocumentoDestino(String tipoDocumentoDestino) {
        this.tipoDocumentoDestino = tipoDocumentoDestino;
    }

    public String getNumeroDocumentoDestino() {
        return numeroDocumentoDestino;
    }

    public void setNumeroDocumentoDestino(String numeroDocumentoDestino) {
        this.numeroDocumentoDestino = numeroDocumentoDestino;
    }

    public String getNombreClienteDestino() {
        return nombreClienteDestino;
    }

    public void setNombreClienteDestino(String nombreClienteDestino) {
        this.nombreClienteDestino = nombreClienteDestino;
    }

    public String getTipoCuentaDestino() {
        return tipoCuentaDestino;
    }

    public void setTipoCuentaDestino(String tipoCuentaDestino) {
        this.tipoCuentaDestino = tipoCuentaDestino;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getFechaAvance() {
        return fechaAvance;
    }

    public void setFechaAvance(String fechaAvance) {
        this.fechaAvance = fechaAvance;
    }

    public String getHoraAvance() {
        return horaAvance;
    }

    public void setHoraAvance(String horaAvance) {
        this.horaAvance = horaAvance;
    }

    public String getValorAvance() {
        return valorAvance;
    }

    public void setValorAvance(String valorAvance) {
        this.valorAvance = valorAvance;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }
    
}
