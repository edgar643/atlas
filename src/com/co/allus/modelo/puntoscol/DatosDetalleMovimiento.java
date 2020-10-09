/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

/**
 *
 * @author roberto.ceballos
 */
public class DatosDetalleMovimiento {

    private static DatosDetalleMovimiento datosMovimientosPuntosCol = new DatosDetalleMovimiento();
    private String tipoid = "";
    private String numid = "";
    private String fechatxs = "";
    private String horatxs = "";
    private String valortxs = "";
    private String tipoidben = "";
    private String numidben = "";
    private String codunico = "";
    private String descodunico = "";
    private String mcc = "";
    private String desmcc = "";
    private String logo = "";
    private String codrespuesta = "";

    public static DatosDetalleMovimiento getdatosMovimientosPuntosCol() {
        return datosMovimientosPuntosCol;
    }

    public static void setdatosMovimientosPuntosCol(DatosDetalleMovimiento datosMovimientosPuntosCol) {
        DatosDetalleMovimiento.datosMovimientosPuntosCol = datosMovimientosPuntosCol;
    }

    public String getTipoid() {
        return tipoid;
    }

    public void setTipoid(String tipoid) {
        this.tipoid = tipoid;
    }

    public String getNumid() {
        return numid;
    }

    public void setNumid(String numid) {
        this.numid = numid;
    }

    public String getFechatxs() {
        return fechatxs;
    }

    public void setFechatxs(String fechatxs) {
        this.fechatxs = fechatxs;
    }

    public String getHoratxs() {
        return horatxs;
    }

    public void setHoratxs(String horatxs) {
        this.horatxs = horatxs;
    }

    public String getValortxs() {
        return valortxs;
    }

    public void setValortxs(String valortxs) {
        this.valortxs = valortxs;
    }

    public String getTipoidben() {
        return tipoidben;
    }

    public void setTipoidben(String tipoidben) {
        this.tipoidben = tipoidben;
    }

    public String getNumidben() {
        return numidben;
    }

    public void setNumidben(String numidben) {
        this.numidben = numidben;
    }

    public String getCodunico() {
        return codunico;
    }

    public void setCodunico(String codunico) {
        this.codunico = codunico;
    }

    public String getDescodunico() {
        return descodunico;
    }

    public void setDescodunico(String descodunico) {
        this.descodunico = descodunico;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getDesmcc() {
        return desmcc;
    }

    public void setDesmcc(String desmcc) {
        this.desmcc = desmcc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCodrespuesta() {
        return codrespuesta;
    }

    public void setCodrespuesta(String codrespuesta) {
        this.codrespuesta = codrespuesta;
    }
    public static final String TIPOID = "Tipo Documento";
    public static final String NUMID = "Documento";
    public static final String FECHATXS = "Fecha Txs";
    public static final String HORATXS = "Hora Txs";
    public static final String VALORTXS = "Valor txs en dólares";
    public static final String TIPOIDBEN = "Tipo doc Beneficiario";
    public static final String NUMIDBEN = "Documento beneficiario";
    public static final String CODUNICO = "Cód único";
    public static final String DESCODUNICO = "Descripción cód único";
    public static final String MCC = "MCC";
    public static final String DESMCC = "Descripción MCC";
    public static final String LOGO = "Logo";
    public static final String CODRESPUESTA = "Código de respuesta";
}
