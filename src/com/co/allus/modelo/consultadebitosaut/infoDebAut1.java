/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultadebitosaut;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoDebAut1 {
//   public static infoDebAut1 movDetalleDeb = new infoDebAut1();

    private SimpleStringProperty CodConvenio;
    private SimpleStringProperty NomConvenio;
    private SimpleStringProperty FechaPago;
    private SimpleStringProperty FechaContable;
    private SimpleStringProperty Referencia1;
    private SimpleStringProperty NomRef1;
    private SimpleStringProperty Ref2;
    private SimpleStringProperty NomRef2;
    private SimpleStringProperty Ref3;
    private SimpleStringProperty NomRef3;
    private SimpleStringProperty IndrefFija;
    private SimpleStringProperty Numfactura;
    private SimpleStringProperty Descfactura;
    private SimpleStringProperty ModoProcesamiento;
    private SimpleStringProperty ValorFactura;
    private SimpleStringProperty ValorPagado;
    private SimpleStringProperty MetodoPago;
    private SimpleStringProperty Ctarecaudadora;
    private SimpleStringProperty NumReintentos;
    private SimpleStringProperty NumReintentosPen;
    private SimpleStringProperty EstadoPago;
    private SimpleStringProperty DescRechazo;
    private SimpleStringProperty Paymendid;
    private SimpleStringProperty CodBanco;
    private SimpleStringProperty NumBanco;

    public infoDebAut1(String CodConvenio, String NomConvenio, String FechaPago, String FechaContable, String Referencia1, String NomRef1, String Ref2, String NomRef2, String Ref3, String NomRef3, String IndrefFija, String Numfactura, String Descfactura, String ModoProcesamiento, String ValorFactura, String ValorPagado, String MetodoPago, String Ctarecaudadora, String NumReintentos, String NumReintentosPen, String EstadoPago, String DescRechazo, String Paymendid, String CodBanco, String NumBanco) {

        this.CodConvenio = new SimpleStringProperty(CodConvenio);
        this.NomConvenio = new SimpleStringProperty(NomConvenio);
        this.FechaPago = new SimpleStringProperty(FechaPago);
        this.FechaContable = new SimpleStringProperty(FechaContable);
        this.Referencia1 = new SimpleStringProperty(Referencia1);
        this.NomRef1 = new SimpleStringProperty(NomRef1);
        this.Ref2 = new SimpleStringProperty(Ref2);
        this.NomRef2 = new SimpleStringProperty(NomRef2);
        this.Ref3 = new SimpleStringProperty(Ref3);
        this.NomRef3 = new SimpleStringProperty(NomRef3);
        this.IndrefFija = new SimpleStringProperty(IndrefFija);
        this.Numfactura = new SimpleStringProperty(Numfactura);
        this.Descfactura = new SimpleStringProperty(Descfactura);
        this.ModoProcesamiento = new SimpleStringProperty(ModoProcesamiento);
        this.ValorFactura = new SimpleStringProperty(ValorFactura);
        this.ValorPagado = new SimpleStringProperty(ValorPagado);
        this.MetodoPago = new SimpleStringProperty(MetodoPago);
        this.Ctarecaudadora = new SimpleStringProperty(Ctarecaudadora);
        this.NumReintentos = new SimpleStringProperty(NumReintentos);
        this.NumReintentosPen = new SimpleStringProperty(NumReintentosPen);
        this.EstadoPago = new SimpleStringProperty(EstadoPago);
        this.DescRechazo = new SimpleStringProperty(DescRechazo);
        this.Paymendid = new SimpleStringProperty(Paymendid);
        this.CodBanco = new SimpleStringProperty(CodBanco);
        this.NumBanco = new SimpleStringProperty(NumBanco);

    }
//
// public infoDebAut1() {
//    }

// public static infoDebAut1 getMovDetallePSE() {
//        return movDetalleDeb;
//    }
//
//    public static void setMovDetallePSE(infoDebAut1 movDetalleDeb) {
//        infoDebAut1.movDetalleDeb = movDetalleDeb;
//
//    }
    public String getCodConvenio() {
        return CodConvenio.get();
    }

    public void setCodConvenio(String CodConvenio) {
        this.CodConvenio.set(CodConvenio);
    }

    public String getNomConvenio() {
        return NomConvenio.get();
    }

    public void setNomConvenio(String NomConvenio) {
        this.NomConvenio.set(NomConvenio);
    }

    public String getFechaPago() {
        return FechaPago.get();
    }

    public void setFechaPago(String FechaPago) {
        this.FechaPago.set(FechaPago);
    }

    public String getFechaContable() {
        return FechaContable.get();
    }

    public void setFechaContable(String FechaContable) {
        this.FechaContable.set(FechaContable);
    }

    public String getReferencia1() {
        return Referencia1.get();
    }

    public void setReferencia1(String Referencia1) {
        this.Referencia1.set(Referencia1);
    }

    public String getNomRef1() {
        return NomRef1.get();
    }

    public void setNomRef1(String NomRef1) {
        this.NomRef1.set(NomRef1);
    }

    public String getRef2() {
        return Ref2.get();
    }

    public void setRef2(String Ref2) {
        this.Ref2.set(Ref2);
    }

    public String getNomRef2() {
        return NomRef2.get();
    }

    public void setNomRef2(String NomRef2) {
        this.NomRef2.set(NomRef2);
    }

    public String getRef3() {
        return Ref3.get();
    }

    public void setRef3(String Ref3) {
        this.Ref3.set(Ref3);
    }

    public String getNomRef3() {
        return NomRef3.get();
    }

    public void setNomRef3(String NomRef3) {
        this.NomRef3.set(NomRef3);
    }

    public String getIndrefFija() {
        return IndrefFija.get();
    }

    public void setIndrefFija(String IndrefFija) {
        this.IndrefFija.set(IndrefFija);
    }

    public String getNumfactura() {
        return Numfactura.get();
    }

    public void setNumfactura(String Numfactura) {
        this.Numfactura.set(Numfactura);
    }

    public String getDescfactura() {
        return Descfactura.get();
    }

    public void setDescfactura(String Descfactura) {
        this.Descfactura.set(Descfactura);
    }

    public String getModoProcesamiento() {
        return ModoProcesamiento.get();
    }

    public void setModoProcesamiento(String ModoProcesamiento) {
        this.ModoProcesamiento.set(ModoProcesamiento);
    }

    public String getValorFactura() {
        return ValorFactura.get();
    }

    public void setValorFactura(String ValorFactura) {
        this.ValorFactura.set(ValorFactura);
    }

    public String getValorPagado() {
        return ValorPagado.get();
    }

    public void setValorPagado(String ValorPagado) {
        this.ValorPagado.set(ValorPagado);
    }

    public String getMetodoPago() {
        return MetodoPago.get();
    }

    public void setMetodoPago(String MetodoPago) {
        this.MetodoPago.set(MetodoPago);
    }

    public String getCtarecaudadora() {
        return Ctarecaudadora.get();
    }

    public void setCtarecaudadora(String Ctarecaudadora) {
        this.Ctarecaudadora.set(Ctarecaudadora);
    }

    public String getNumReintentos() {
        return NumReintentos.get();
    }

    public void setNumReintentos(String NumReintentos) {
        this.NumReintentos.set(NumReintentos);
    }

    public String getNumReintentosPen() {
        return NumReintentosPen.get();
    }

    public void setNumReintentosPen(String NumReintentosPen) {
        this.NumReintentosPen.set(NumReintentosPen);
    }

    public String getEstadoPago() {
        return EstadoPago.get();
    }

    public void setEstadoPago(String EstadoPago) {
        this.EstadoPago.set(EstadoPago);
    }

    public String getDescRechazo() {
        return DescRechazo.get();
    }

    public void setDescRechazo(String DescRechazo) {
        this.DescRechazo.set(DescRechazo);
    }

    public String getPaymendid() {
        return Paymendid.get();
    }

    public void setPaymendid(String Paymendid) {
        this.Paymendid.set(Paymendid);
    }

    public String getCodBanco() {
        return CodBanco.get();
    }

    public void setCodBanco(String CodBanco) {
        this.CodBanco.set(CodBanco);
    }

    public String getNumBanco() {
        return NumBanco.get();
    }

    public void setNumBanco(String NumBanco) {
        this.NumBanco.set(NumBanco);
    }
    public static final String descripcion = "Descripción";
    public static final String empresa = "Empresa/Servicio";
    public static final String reffija = "Referencia Fija";
    public static final String ref2 = "Referencia 2";
    public static final String ref3 = "Referencia 3";
    public static final String modopro = "Modo de Procesamiento";
    public static final String valorfactura = "Valor de la Factura";
    public static final String valorpagado = "Valor Pagado";
    public static final String fechahora = "Fecha y hora del Débito";
    public static final String resultadopago = "Resultado del Pago";
    public static final String descrechazo = "Descripción del Rechazo";
    public static final String numcompro = "Número de Comprobante";
    public static final String bancodueño = "Banco dueño del Producto";
    public static final String tipoprod = "Tipo de Producto";
    public static final String numprod = "Número de Producto";
    public static final String numprog = "Número de Reintentos Programados";
    public static final String numejec = "Número de Reintentos ejecutados";
}
