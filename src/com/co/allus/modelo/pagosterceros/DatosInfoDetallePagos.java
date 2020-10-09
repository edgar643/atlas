/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosterceros;

/**
 *
 * @author stephania.rojas
 */
public class DatosInfoDetallePagos {

    private static DatosInfoDetallePagos datosinfodetallepagos = new DatosInfoDetallePagos();
    private String Pagar = "";
    private String CodigoConvenio = "";
    private String NombreConvenio = "";
    private String Tipocuenta = "";
    private String Númerocuenta = "";
    private String NombreBanco = "";
    private String Frecuenciapago = "";
    private String Criteriosemana = "";
    private String Criteriodia = "";
    private String Criteriomes = "";
    private String Díareintentos = "";
    private String Fechafinprogramacion = "";
    private String Descripcion = "";
    private String Canalprogramacion = "";
    private String Fechaprogramacion = "";
    private String Estadoprogramacion = "";
    private String Fechadebito = "";
    private String ReferenciaFija = "";
    private String ReferencialVariable1 = "";
    private String ReferenciaVariable2 = "";
    private String Valor = "";

    public String getPagar() {
        return Pagar;
    }

    public void setPagar(String Pagar) {
        this.Pagar = Pagar;
    }

    public String getCodigoConvenio() {
        return CodigoConvenio;
    }

    public void setCodigoConvenio(String CodigoConvenio) {
        this.CodigoConvenio = CodigoConvenio;
    }

    public String getNombreConvenio() {
        return NombreConvenio;
    }

    public void setNombreConvenio(String NombreConvenio) {
        this.NombreConvenio = NombreConvenio;
    }

    public String getTipocuenta() {
        return Tipocuenta;
    }

    public void setTipocuenta(String Tipocuenta) {
        this.Tipocuenta = Tipocuenta;
    }

    public String getNúmerocuenta() {
        return Númerocuenta;
    }

    public void setNúmerocuenta(String Númerocuenta) {
        this.Númerocuenta = Númerocuenta;
    }

    public String getNombreBanco() {
        return NombreBanco;
    }

    public void setNombreBanco(String NombreBanco) {
        this.NombreBanco = NombreBanco;
    }

    public String getFrecuenciapago() {
        return Frecuenciapago;
    }

    public void setFrecuenciapago(String Frecuenciapago) {
        this.Frecuenciapago = Frecuenciapago;
    }

    public String getCriteriosemana() {
        return Criteriosemana;
    }

    public void setCriteriosemana(String Criteriosemana) {
        this.Criteriosemana = Criteriosemana;
    }

    public String getCriteriodia() {
        return Criteriodia;
    }

    public void setCriteriodia(String Criteriodia) {
        this.Criteriodia = Criteriodia;
    }

    public String getCriteriomes() {
        return Criteriomes;
    }

    public void setCriteriomes(String Criteriomes) {
        this.Criteriomes = Criteriomes;
    }

    public String getDíareintentos() {
        return Díareintentos;
    }

    public void setDíareintentos(String Díareintentos) {
        this.Díareintentos = Díareintentos;
    }

    public String getFechafinprogramacion() {
        return Fechafinprogramacion;
    }

    public void setFechafinprogramacion(String Fechafinprogramacion) {
        this.Fechafinprogramacion = Fechafinprogramacion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getCanalprogramacion() {
        return Canalprogramacion;
    }

    public void setCanalprogramacion(String Canalprogramacion) {
        this.Canalprogramacion = Canalprogramacion;
    }

    public String getFechaprogramacion() {
        return Fechaprogramacion;
    }

    public void setFechaprogramacion(String Fechaprogramacion) {
        this.Fechaprogramacion = Fechaprogramacion;
    }

    public String getEstadoprogramacion() {
        return Estadoprogramacion;
    }

    public void setEstadoprogramacion(String Estadoprogramacion) {
        this.Estadoprogramacion = Estadoprogramacion;
    }

    public String getFechadebito() {
        return Fechadebito;
    }

    public void setFechadebito(String Fechadebito) {
        this.Fechadebito = Fechadebito;
    }

    public String getReferenciaFija() {
        return ReferenciaFija;
    }

    public void setReferenciaFija(String ReferenciaFija) {
        this.ReferenciaFija = ReferenciaFija;
    }

    public String getReferencialVariable1() {
        return ReferencialVariable1;
    }

    public void setReferencialVariable1(String ReferencialVariable1) {
        this.ReferencialVariable1 = ReferencialVariable1;
    }

    public String getReferenciaVariable2() {
        return ReferenciaVariable2;
    }

    public void setReferenciaVariable2(String ReferenciaVariable2) {
        this.ReferenciaVariable2 = ReferenciaVariable2;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    public static void setDatosInfoDetallePagos(DatosInfoDetallePagos valor) {
        datosinfodetallepagos = valor;
    }

    public static DatosInfoDetallePagos getDatosInfoDetallePagos() {
        return datosinfodetallepagos;
    }
}
