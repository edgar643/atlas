/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author roberto.ceballos
 */
public class infoTablaListarMovimientos {

    private SimpleStringProperty fecha_trx;
    private SimpleStringProperty cod_trx;
    private SimpleStringProperty descripcion_trx;
    private SimpleStringProperty valor_trx_pesos;
    private SimpleStringProperty puntos;
    private SimpleStringProperty extra_puntos;
    private SimpleStringProperty respuesta;
    private SimpleStringProperty hora_trx;
    private SimpleStringProperty tipo_doc;
    private SimpleStringProperty documento;
    private SimpleStringProperty valor_trx_dolares;
    private SimpleStringProperty tipo_doc_benef;
    private SimpleStringProperty doc_benef;
    private SimpleStringProperty cod_unico;
    private SimpleStringProperty descod_unico;
    private SimpleStringProperty mcc;
    private SimpleStringProperty desc_mcc;
    private SimpleStringProperty logo;
    private SimpleStringProperty id_trx;

    public infoTablaListarMovimientos(String fecha_trx,
            String cod_trx,
            String descripcion_trx,
            String valor_trx_pesos,
            String puntos,
            String extra_puntos,
            String respuesta,
            String hora_trx,
            String tipo_doc,
            String documento,
            String valor_trx_dolares,
            String tipo_doc_benef,
            String doc_benef,
            String cod_unico,
            String descod_unico,
            String mcc,
            String desc_mcc,
            String logo,
            String id_trx) {
        this.fecha_trx = new SimpleStringProperty(fecha_trx);
        this.cod_trx = new SimpleStringProperty(cod_trx);
        this.descripcion_trx = new SimpleStringProperty(descripcion_trx);
        this.valor_trx_pesos = new SimpleStringProperty(valor_trx_pesos);
        this.puntos = new SimpleStringProperty(puntos);
        this.extra_puntos = new SimpleStringProperty(extra_puntos);
        this.respuesta = new SimpleStringProperty(respuesta);
        this.hora_trx = new SimpleStringProperty(hora_trx);
        this.tipo_doc = new SimpleStringProperty(tipo_doc);
        this.documento = new SimpleStringProperty(documento);
        this.valor_trx_dolares = new SimpleStringProperty(valor_trx_dolares);
        this.tipo_doc_benef = new SimpleStringProperty(tipo_doc_benef);
        this.doc_benef = new SimpleStringProperty(doc_benef);
        this.cod_unico = new SimpleStringProperty(cod_unico);
        this.descod_unico = new SimpleStringProperty(descod_unico);
        this.mcc = new SimpleStringProperty(mcc);
        this.desc_mcc = new SimpleStringProperty(desc_mcc);
        this.logo = new SimpleStringProperty(logo);
        this.id_trx = new SimpleStringProperty(id_trx);




    }

    public String getId_trx() {
        return id_trx.get();
    }

    public void setId_trx(String id_trx) {
        this.id_trx.set(id_trx);
    }

    public String getHora_trx() {
        return hora_trx.get();
    }

    public void setHora_trx(String hora_trx) {
        this.hora_trx.set(hora_trx);
    }

    public String getTipo_doc() {
        return tipo_doc.get();
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc.set(tipo_doc);
    }

    public String getDocumento() {
        return documento.get();
    }

    public void setDocumento(String documento) {
        this.documento.set(documento);
    }

    public String getValor_trx_dolares() {
        return valor_trx_dolares.get();
    }

    public void setValor_trx_dolares(String valor_trx_dolares) {
        this.valor_trx_dolares.set(valor_trx_dolares);
    }

    public String getTipo_doc_benef() {
        return tipo_doc_benef.get();
    }

    public void setTipo_doc_benef(String tipo_doc_benef) {
        this.tipo_doc_benef.set(tipo_doc_benef);
    }

    public String getDoc_benef() {
        return doc_benef.get();
    }

    public void setDoc_benef(String doc_benef) {
        this.doc_benef.set(doc_benef);
    }

    public String getCod_unico() {
        return cod_unico.get();
    }

    public void setCod_unico(String cod_unico) {
        this.cod_unico.set(cod_unico);
    }

    public String getDescod_unico() {
        return descod_unico.get();
    }

    public void setDescod_unico(String descod_unico) {
        this.descod_unico.set(descod_unico);
    }

    public String getMcc() {
        return mcc.get();
    }

    public void setMcc(String mcc) {
        this.mcc.set(mcc);
    }

    public String getDesc_mcc() {
        return desc_mcc.get();
    }

    public void setDesc_mcc(String desc_mcc) {
        this.desc_mcc.set(desc_mcc);
    }

    public String getLogo() {
        return logo.get();
    }

    public void setLogo(String logo) {
        this.logo.set(logo);
    }

    public String getFecha_trx() {
        return fecha_trx.get();
    }

    public void setFecha_trx(String fecha_trx) {
        this.fecha_trx.set(fecha_trx);
    }

    public String getCod_trx() {
        return cod_trx.get();
    }

    public void setCod_trx(String cod_trx) {
        this.cod_trx.set(cod_trx);
    }

    public String getDescripcion_trx() {
        return descripcion_trx.get();
    }

    public void setDescripcion_trx(String descripcion_trx) {
        this.descripcion_trx.set(descripcion_trx);
    }

    public String getValor_trx_pesos() {
        return valor_trx_pesos.get();
    }

    public void setValor_trx_pesos(String valor_trx_pesos) {
        this.valor_trx_pesos.get();
    }

    public String getPuntos() {
        return puntos.get();
    }

    public void setPuntos(String puntos) {
        this.puntos.set(puntos);
    }

    public String getExtra_puntos() {
        return extra_puntos.get();
    }

    public void setExtra_puntos(String extra_puntos) {
        this.extra_puntos.set(extra_puntos);
    }

    public String getRespuesta() {
        return respuesta.get();
    }

    public void setRespuesta(String respuesta) {
        this.respuesta.set(respuesta);
    }
    public static final String TIPO_DOC = "Tipo Documento";
    public static final String DOCUMENTO = "Documento";
    public static final String FECHA_TRX = "Fecha Transacción";
    public static final String HORA_TRX = "Hora Transacción";
    public static final String VALOR_DOLAR = "Valor Transacción Dolares";
    public static final String TIPO_DOC_BENEF = "Tipo Doc Beneficiario";
    public static final String DOC_BENEF = "Documento Beneficiario";
    public static final String COD_UNICO = "Código Unico";
    public static final String DESCOD_UNICO = "Descripción Código Unico";
    public static final String MCC = "Categoría de Comercio";
    public static final String DESC_MSCC = "Descripción de Comercio";
    public static final String LOGO = "Tipo de Tarjeta";
    public static final String COD_RESP = "Código Respuesta";
}
