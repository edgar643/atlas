/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultamovimientos;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoMovPSE {

    public static infoMovPSE movDetallePSE = new infoMovPSE();
    private SimpleStringProperty codigoconv;
    private SimpleStringProperty numconv;
    private SimpleStringProperty fechaPago;
    private SimpleStringProperty fechaContable;
    private SimpleStringProperty ref1;
    private SimpleStringProperty nomRef1;
    private SimpleStringProperty ref2;
    private SimpleStringProperty nomRef2;
    private SimpleStringProperty ref3;
    private SimpleStringProperty nomRef3;
    private SimpleStringProperty valorFactura;
    private SimpleStringProperty valorPagado;
    private SimpleStringProperty meotodoPagado;
    private SimpleStringProperty cuentaRecaudoadora;
    private SimpleStringProperty paymentID;
    private SimpleStringProperty canalPago;
    private SimpleStringProperty nombreCompania;
    private SimpleStringProperty nombreOficina;
    private SimpleStringProperty codOficina;
    private SimpleStringProperty codPuntoPago;

    public infoMovPSE(String codigoconv,
            String numconv,
            String fechaPago,
            String fechaContable,
            String ref1,
            String nomRef1,
            String ref2,
            String nomRef2,
            String ref3,
            String nomRef3,
            String valorFactura,
            String valorPagado,
            String meotodoPagado,
            String cuentaRecaudoadora,
            String paymentID,
            String canalPago,
            String nombreCompania,
            String nombreOficina,
            String codOficina,
            String codPuntoPago) {
        this.codigoconv = new SimpleStringProperty(codigoconv);
        this.numconv = new SimpleStringProperty(numconv);
        this.fechaPago = new SimpleStringProperty(fechaPago);
        this.fechaContable = new SimpleStringProperty(fechaContable);
        this.ref1 = new SimpleStringProperty(ref1);
        this.nomRef1 = new SimpleStringProperty(nomRef1);
        this.ref2 = new SimpleStringProperty(ref2);
        this.nomRef2 = new SimpleStringProperty(nomRef2);
        this.ref3 = new SimpleStringProperty(ref3);
        this.nomRef3 = new SimpleStringProperty(nomRef3);
        this.valorFactura = new SimpleStringProperty(valorFactura);
        this.valorPagado = new SimpleStringProperty(valorPagado);
        this.meotodoPagado = new SimpleStringProperty(meotodoPagado);
        this.cuentaRecaudoadora = new SimpleStringProperty(cuentaRecaudoadora);
        this.paymentID = new SimpleStringProperty(paymentID);
        this.canalPago = new SimpleStringProperty(canalPago);
        this.nombreCompania = new SimpleStringProperty(nombreCompania);
        this.nombreOficina = new SimpleStringProperty(nombreOficina);
        this.codOficina = new SimpleStringProperty(codOficina);
        this.codPuntoPago = new SimpleStringProperty(codPuntoPago);
    }

    public infoMovPSE() {
    }

    public String getFechaContable() {
        return fechaContable.get();
    }

    public void setFechaContable(String fechaContable) {
        this.fechaContable.set(fechaContable);
    }

    public String getValorFactura() {
        return valorFactura.get();
    }

    public void setValorFactura(String valorFactura) {
        this.valorFactura.set(valorFactura);
    }

    public String getCodigoconv() {
        return codigoconv.get();
    }

    public void setCodigoconv(String codigoconv) {
        this.codigoconv.set(codigoconv);
    }

    public String getNumconv() {
        return numconv.get();
    }

    public void setNumconv(String numconv) {
        this.numconv.set(numconv);
    }

    public String getFechaPago() {
        return fechaPago.get();
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago.set(fechaPago);
    }

    public String getRef1() {
        return ref1.get();
    }

    public void setRef1(String ref1) {
        this.ref1.set(ref1);
    }

    public String getNomRef1() {
        return nomRef1.get();
    }

    public void setNomRef1(String nomRef1) {
        this.nomRef1.set(nomRef1);
    }

    public String getRef2() {
        return ref2.get();
    }

    public void setRef2(String ref2) {
        this.ref2.set(ref2);
    }

    public String getNomRef2() {
        return nomRef2.get();
    }

    public void setNomRef2(String nomRef2) {
        this.nomRef2.set(nomRef2);
    }

    public String getRef3() {
        return ref3.get();
    }

    public void setRef3(String ref3) {
        this.ref3.set(ref3);
    }

    public String getNomRef3() {
        return nomRef3.get();
    }

    public void setNomRef3(String nomRef3) {
        this.nomRef3.set(nomRef3);
    }

    public String getValorPagado() {
        return valorPagado.get();
    }

    public void setValorPagado(String valorPagado) {
        this.valorPagado.set(valorPagado);
    }

    public String getMeotodoPagado() {
        return meotodoPagado.get();
    }

    public void setMeotodoPagado(String meotodoPagado) {
        this.meotodoPagado.set(meotodoPagado);
    }

    public String getCuentaRecaudoadora() {
        return cuentaRecaudoadora.get();
    }

    public void setCuentaRecaudoadora(String cuentaRecaudoadora) {
        this.cuentaRecaudoadora.set(cuentaRecaudoadora);
    }

    public String getPaymentID() {
        return paymentID.get();
    }

    public void setPaymentID(String paymentID) {
        this.paymentID.set(paymentID);
    }

    public String getCanalPago() {
        return canalPago.get();
    }

    public void setCanalPago(String canalPago) {
        this.canalPago.set(canalPago);
    }

    public String getNombreCompania() {
        return nombreCompania.get();
    }

    public void setNombreCompania(String nombreCompania) {
        this.nombreCompania.set(nombreCompania);
    }

    public String getNombreOficina() {
        return nombreOficina.get();
    }

    public void setNombreOficina(String nombreOficina) {
        this.nombreOficina.set(nombreOficina);
    }

    public String getCodOficina() {
        return codOficina.get();
    }

    public void setCodOficina(String codOficina) {
        this.codOficina.set(codOficina);
    }

    public String getCodPuntoPago() {
        return codPuntoPago.get();
    }

    public void setCodPuntoPago(String codPuntoPago) {
        this.codPuntoPago.set(codPuntoPago);
    }

    public static infoMovPSE getMovDetallePSE() {
        return movDetallePSE;
    }

    public static void setMovDetallePSE(infoMovPSE movDetallePSE) {
        infoMovPSE.movDetallePSE = movDetallePSE;

    }
    public static final String codigo_conv = "Código del Convenio";
    public static final String num_conv = "Nombre del Convenio";
    public static final String fecha_Pago = "Fecha y Hora del Pago";
    public static final String ref_1 = "Referencia 1";
    public static final String ref_2 = "Referencia 2";
    public static final String ref_3 = "Referencia 3";
    public static final String valor_Pagado = "Valor Pagado";
    public static final String valor_factura = "Valor de la Factura";
    public static final String meotodo_Pagado = "Método de Pago";
    public static final String cuenta_Recaudoadora_tipo = "Tipo de cuenta / Tarjeta Credito";
    public static final String cuenta_Recaudoadora_num = "Número de cuenta / Tarjeta Credito";
    public static final String payment_ID = "Payment ID";
    public static final String payment_StatusID = "Estado";
    public static final String canal_Pago = "Canal de Pago";
    public static final String nombre_Compania = "Nombre de la Compañia";
    public static final String nombre_Oficina = "Nombre de la Oficina";
    public static final String cod_Oficina = "Código de la oficina";
    public static final String cod_PuntoPago = "Código del punto de pago";
}
