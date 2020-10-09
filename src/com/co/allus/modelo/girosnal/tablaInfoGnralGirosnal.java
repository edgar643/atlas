/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.girosnal;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class tablaInfoGnralGirosnal {

    private StringProperty colCanalOri;
    private StringProperty colFechaVta;
    private StringProperty colDocGirador;
    private StringProperty colNomGirador;
    private StringProperty colCelGirador;
    private StringProperty colDocBenef;
    private StringProperty colNomBenef;
    private StringProperty colCelBenef;
    private StringProperty colCanalPago;
    private StringProperty colFechaPago;
    private StringProperty colValorGiro;
    private StringProperty colValorComision;
    private StringProperty colValTotal;
    private StringProperty colValIva;
    private StringProperty colEstadoGiro;
    private StringProperty colFechaCancel;
    private StringProperty colFechaPteCancel;
    private StringProperty colContDiaGiro;
    private StringProperty colSms;
    private StringProperty colnombreCliente;
    private StringProperty colCelcliente;           ////
    private StringProperty colAcumAnual;
    private StringProperty colAcumMes;
    private StringProperty colConsecutivo;
    private StringProperty colPuntoservVenta;
    private StringProperty colCanalVenta;
    private StringProperty colPuntoserPago;
    private BooleanProperty seleccion;
    private StringProperty colAcumAnual2;
    private StringProperty colAcumMes2;
    private StringProperty colMotCancel;

    public tablaInfoGnralGirosnal(String ColCanalOri,
            String ColFechaVta,
            String ColDocGirador,
            String ColNomGirador,
            String ColCelGirador,
            String ColDocBenef,
            String colNomBenef,
            String ColCelBenef,
            String ColCanalPago,
            String ColFechaPago,
            String ColValorGiro,
            String ColValorComision,
            String ColValTotal,
            String ColValIva,
            String ColEstadoGiro,
            String ColFechaCancel,
            String ColFechaPteCancel,
            String ColContDiaGiro,
            String ColSms,
            String ColnombreCliente,
            String ColCelcliente,
            String ColAcumAnual,
            String ColAcumMes,
            String ColConsecutivo,
            String colPuntoservVenta,
            String colCanalVenta,
            String colPuntoserPago,
            boolean seleccion,
            String ColAcumAnual2,
            String ColAcumMes2,
            String ColMotCancel) {
        this.colCanalOri = new SimpleStringProperty(ColCanalOri);
        this.colFechaVta = new SimpleStringProperty(ColFechaVta);
        this.colDocGirador = new SimpleStringProperty(ColDocGirador);
        this.colNomGirador = new SimpleStringProperty(ColNomGirador);
        this.colCelGirador = new SimpleStringProperty(ColCelGirador);
        this.colDocBenef = new SimpleStringProperty(ColDocBenef);
        this.colNomBenef = new SimpleStringProperty(colNomBenef);
        this.colCelBenef = new SimpleStringProperty(ColCelBenef);
        this.colCanalPago = new SimpleStringProperty(ColCanalPago);
        this.colFechaPago = new SimpleStringProperty(ColFechaPago);
        this.colValorGiro = new SimpleStringProperty(ColValorGiro);
        this.colValorComision = new SimpleStringProperty(ColValorComision);
        this.colValTotal = new SimpleStringProperty(ColValTotal);
        this.colValIva = new SimpleStringProperty(ColValIva);
        this.colEstadoGiro = new SimpleStringProperty(ColEstadoGiro);
        this.colFechaCancel = new SimpleStringProperty(ColFechaCancel);
        this.colFechaPteCancel = new SimpleStringProperty(ColFechaPteCancel);
        this.colContDiaGiro = new SimpleStringProperty(ColContDiaGiro);
        this.colSms = new SimpleStringProperty(ColSms);
        this.colnombreCliente = new SimpleStringProperty(ColnombreCliente);
        this.colCelcliente = new SimpleStringProperty(ColCelcliente);
        this.colAcumAnual = new SimpleStringProperty(ColAcumAnual);
        this.colAcumMes = new SimpleStringProperty(ColAcumMes);
        this.colConsecutivo = new SimpleStringProperty(ColConsecutivo);
        this.colPuntoservVenta = new SimpleStringProperty(colPuntoservVenta);
        this.colCanalVenta = new SimpleStringProperty(colCanalVenta);
        this.colPuntoserPago = new SimpleStringProperty(colPuntoserPago);
        this.seleccion = new SimpleBooleanProperty(seleccion);
        this.colAcumAnual2 = new SimpleStringProperty(ColAcumAnual2);
        this.colAcumMes2 = new SimpleStringProperty(ColAcumMes2);
        this.colMotCancel = new SimpleStringProperty(ColMotCancel);
    }

    public StringProperty colPuntoservVentaProperty() {
        return colPuntoservVenta;
    }

    public void setColPuntoservVenta(String colPuntoservVenta) {
        this.colPuntoservVenta.set(colPuntoservVenta);
    }

    public StringProperty colCanalVentaProperty() {
        return colCanalVenta;
    }

    public void setColCanalVenta(String colCanalVenta) {
        this.colCanalVenta.set(colCanalVenta);
    }

    public StringProperty colPuntoserPagoProperty() {
        return colPuntoserPago;
    }

    public void setColPuntoserPago(String colPuntoserPago) {
        this.colPuntoserPago.set(colPuntoserPago);
    }

    public BooleanProperty seleccionProperty() {
        return seleccion;
    }

    public StringProperty colCanalOriProperty() {
        return colCanalOri;
    }

    public void setColCanalOri(String ColCanalOri) {
        this.colCanalOri.set(ColCanalOri);
    }

    public StringProperty colFechaVtaProperty() {
        return colFechaVta;
    }

    public void setColFechaVta(String ColFechaVta) {
        this.colFechaVta.set(ColFechaVta);
    }

    public StringProperty colDocGiradorProperty() {
        return colDocGirador;
    }

    public void setColDocGirador(String ColDocGirador) {
        this.colDocGirador.set(ColDocGirador);
    }

    public StringProperty colNomGiradorProperty() {
        return colNomGirador;
    }

    public void setColNomGirador(String ColNomGirador) {
        this.colNomGirador.set(ColNomGirador);
    }

    public StringProperty colCelGiradorProperty() {
        return colCelGirador;
    }

    public void setColCelGirador(String ColCelGirador) {
        this.colCelGirador.set(ColCelGirador);
    }

    public StringProperty colDocBenefProperty() {
        return colDocBenef;
    }

    public void setColDocBenef(String ColDocBenef) {
        this.colDocBenef.set(ColDocBenef);
    }

    public StringProperty colNomBenefProperty() {
        return colNomBenef;
    }

    public void setColNomBenef(String colNomBenef) {
        this.colNomBenef.set(colNomBenef);
    }

    public StringProperty colCelBenefProperty() {
        return colCelBenef;
    }

    public void setColCelBenef(String ColCelBenef) {
        this.colCelBenef.set(ColCelBenef);
    }

    public StringProperty colCanalPagoProperty() {
        return colCanalPago;
    }

    public void setColCanalPago(String ColCanalPago) {
        this.colCanalPago.set(ColCanalPago);
    }

    public StringProperty colFechaPagoProperty() {
        return colFechaPago;
    }

    public void setColFechaPago(String ColFechaPago) {
        this.colFechaPago.set(ColFechaPago);
    }

    public StringProperty colValorGiroProperty() {
        return colValorGiro;
    }

    public void setColValorGiro(String ColValorGiro) {
        this.colValorGiro.set(ColValorGiro);
    }

    public StringProperty colValorComisionProperty() {
        return colValorComision;
    }

    public void setColValorComision(String ColValorComision) {
        this.colValorComision.set(ColValorComision);
    }

    public StringProperty colValTotalProperty() {
        return colValTotal;
    }

    public void setColValTotal(String ColValTotal) {
        this.colValTotal.set(ColValTotal);
    }

    public StringProperty colValIvaProperty() {
        return colValIva;
    }

    public void setColValIva(String ColValIva) {
        this.colValIva.set(ColValIva);
    }

    public StringProperty colEstadoGiroProperty() {
        return colEstadoGiro;
    }

    public void setColEstadoGiro(String ColEstadoGiro) {
        this.colEstadoGiro.set(ColEstadoGiro);
    }

    public StringProperty colFechaPteCancelProperty() {
        return colFechaPteCancel;
    }

    public void setColFechaPteCancel(String ColFechaPteCancel) {
        this.colFechaPteCancel.set(ColFechaPteCancel);
    }

    public StringProperty colContDiaGiroProperty() {
        return colContDiaGiro;
    }

    public void setColContDiaGiro(String ColContDiaGiro) {
        this.colContDiaGiro.set(ColContDiaGiro);
    }

    public StringProperty colSmsProperty() {
        return colSms;
    }

    public void setColSms(String ColSms) {
        this.colSms.set(ColSms);
    }

    public StringProperty colnombreClienteProperty() {
        return colnombreCliente;
    }

    public void setColnombreCliente(String ColnombreCliente) {
        this.colnombreCliente.set(ColnombreCliente);
    }

    public StringProperty colCelclienteProperty() {
        return colCelcliente;
    }

    public void setColCelcliente(String ColCelcliente) {
        this.colCelcliente.set(ColCelcliente);
    }

    public StringProperty colAcumAnualProperty() {
        return colAcumAnual;
    }

    public void setColAcumAnual(String ColAcumAnual) {
        this.colAcumAnual.set(ColAcumAnual);
    }

    public StringProperty colAcumMesProperty() {
        return colAcumMes;
    }

    public void setColAcumMes(String ColAcumMes) {
        this.colAcumMes.set(ColAcumMes);
    }

    public StringProperty colConsecutivoProperty() {
        return colConsecutivo;
    }

    public void setColConsecutivo(String ColConsecutivo) {
        this.colConsecutivo.set(ColConsecutivo);
    }

    public StringProperty colFechaCancelProperty() {
        return colFechaCancel;
    }

    public void setColFechaCancel(String ColFechaCancel) {
        this.colFechaCancel.set(ColFechaCancel);
    }

    public StringProperty colAcumAnual2Property() {
        return colAcumAnual2;
    }

    public void setColAcumAnual2(String ColAcumAnual2) {
        this.colAcumAnual2.set(ColAcumAnual2);
    }

    public StringProperty colAcumMes2Property() {
        return colAcumMes2;
    }

    public void setColAcumMes2(String ColAcumMes2) {
        this.colAcumMes2.set(ColAcumMes2);
    }
    
    public StringProperty colMotCancelProperty() {
        return colMotCancel;
    }

    public void setColMotCancel(String ColMotCancel) {
        this.colMotCancel.set(ColMotCancel);
    }
    
}
