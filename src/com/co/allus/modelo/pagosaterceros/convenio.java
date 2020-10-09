/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosaterceros;

/**
 *
 * @author alexander.lopez.o
 */
public class convenio {

    private static convenio dataconvenio = new convenio();
    private String cod_conv = "";
    private String nom_conv = "";
    private String cat_conv = "";
    private String referencia = "";
    private String comprobante = "";
    private String lugar_validadacion = "";
    private String tipo_recaudo = "";
    private String numero_referencia = "";
    private String texto_ayuda1 = "";
    private String texto_ayuda2 = "";
    private String texto_ayuda3 = "";
    private String texto_ayuda4 = "";
    private String nit_conv = "";
    private String referencia2 = "";

    public static void setConvenio(convenio valor) {
        dataconvenio = valor;
    }

    public static convenio getConvenio() {
        return dataconvenio;
    }

    public String getNit_conv() {
        return nit_conv;
    }

    public void setNit_conv(String nit_conv) {
        this.nit_conv = nit_conv;
    }

    public String getReferencia2() {
        return referencia2;
    }

    public void setReferencia2(String referencia2) {
        this.referencia2 = referencia2;
    }

    public String getCod_conv() {
        return cod_conv;
    }

    public void setCod_conv(String cod_conv) {
        this.cod_conv = cod_conv;
    }

    public String getNom_conv() {
        return nom_conv;
    }

    public void setNom_conv(String nom_conv) {
        this.nom_conv = nom_conv;
    }

    public String getCat_conv() {
        return cat_conv;
    }

    public void setCat_conv(String cat_conv) {
        this.cat_conv = cat_conv;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getLugar_validadacion() {
        return lugar_validadacion;
    }

    public void setLugar_validadacion(String lugar_validadacion) {
        this.lugar_validadacion = lugar_validadacion;
    }

    public String getTipo_recaudo() {
        return tipo_recaudo;
    }

    public void setTipo_recaudo(String tipo_recaudo) {
        this.tipo_recaudo = tipo_recaudo;
    }

    public String getNumero_referencia() {
        return numero_referencia;
    }

    public void setNumero_referencia(String numero_referencia) {
        this.numero_referencia = numero_referencia;
    }

    public String getTexto_ayuda1() {
        return texto_ayuda1;
    }

    public void setTexto_ayuda1(String texto_ayuda1) {
        this.texto_ayuda1 = texto_ayuda1;
    }

    public String getTexto_ayuda2() {
        return texto_ayuda2;
    }

    public void setTexto_ayuda2(String texto_ayuda2) {
        this.texto_ayuda2 = texto_ayuda2;
    }

    public String getTexto_ayuda3() {
        return texto_ayuda3;
    }

    public void setTexto_ayuda3(String texto_ayuda3) {
        this.texto_ayuda3 = texto_ayuda3;
    }

    public String getTexto_ayuda4() {
        return texto_ayuda4;
    }

    public void setTexto_ayuda4(String texto_ayuda4) {
        this.texto_ayuda4 = texto_ayuda4;
    }
}
