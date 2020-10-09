/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

/**
 *
 * @author stephania.rojas
 */
public class infoDetalleAccesoEmergencia {

    private static infoDetalleAccesoEmergencia datosDetalleAE = new infoDetalleAccesoEmergencia();
    private String fechaEnvio = "";
    private String horaEnvio = "";
    private String costoNovedad = "";
    private String vigencia = "";
    private String destinoEnvio = "";
    private String estadoEnvio = "";

    public static infoDetalleAccesoEmergencia getInfoDetalleAE() {
        return datosDetalleAE;
    }

    public static void setInfoDetalleAE(infoDetalleAccesoEmergencia infoDetalleAE) {
        infoDetalleAccesoEmergencia.datosDetalleAE = infoDetalleAE;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getHoraEnvio() {
        return horaEnvio;
    }

    public void setHoraEnvio(String horaEnvio) {
        this.horaEnvio = horaEnvio;
    }

    public String getCostoNovedad() {
        return costoNovedad;
    }

    public void setCostoNovedad(String costoNovedad) {
        this.costoNovedad = costoNovedad;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getDestinoEnvio() {
        return destinoEnvio;
    }

    public void setDestinoEnvio(String destinoEnvio) {
        this.destinoEnvio = destinoEnvio;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }
}
