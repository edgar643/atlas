/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosterceros;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTablaMasFacturas {

    private StringProperty codigoConvenio;
    private StringProperty tipoRecaudo;
    private StringProperty cuentaRecaudo;
    private StringProperty cantidadReferencias;
    private StringProperty nombreConvenio;
    private StringProperty claseCuenta;
    private StringProperty indicadorBD;
    private StringProperty indicadorContingenciaBD;
    private StringProperty indicadorPagoParcial;
    private StringProperty indicadorPagoMayor;
    private StringProperty indicadorPagoEnCero;
    private StringProperty textoReferencia1;
    private StringProperty textoReferencia2;
    private StringProperty textoReferencia3;
    private StringProperty indicadorReferenciaFija;
    private StringProperty colnumFact;
    private StringProperty colReferencia1;
    private StringProperty colReferencia2;
    private StringProperty colReferencia3;
    private StringProperty colfechaVen;
    private StringProperty colvalorPagar;
    private StringProperty colvalorpagarRestante;

    public infoTablaMasFacturas(String codigoConvenio,
            String tipoRecaudo,
            String cuentaRecaudo,
            String cantidadReferencias,
            String nombreConvenio,
            String claseCuenta,
            String indicadorBD,
            String indicadorContingenciaBD,
            String indicadorPagoParcial,
            String indicadorPagoMayor,
            String indicadorPagoEnCero,
            String textoReferencia1,
            String textoReferencia2,
            String textoReferencia3,
            String indicadorReferenciaFija,
            String numeroFactura,
            String referencia1,
            String referencia2,
            String referencia3,
            String fechaDeVencimiento,
            String valorFactura,
            String valorRestanteDelPago) {

        this.codigoConvenio = new SimpleStringProperty(codigoConvenio);
        this.tipoRecaudo = new SimpleStringProperty(tipoRecaudo);
        this.cuentaRecaudo = new SimpleStringProperty(cuentaRecaudo);
        this.cantidadReferencias = new SimpleStringProperty(cantidadReferencias);
        this.nombreConvenio = new SimpleStringProperty(nombreConvenio);
        this.claseCuenta = new SimpleStringProperty(claseCuenta);
        this.indicadorBD = new SimpleStringProperty(indicadorBD);
        this.indicadorContingenciaBD = new SimpleStringProperty(indicadorContingenciaBD);
        this.indicadorPagoParcial = new SimpleStringProperty(indicadorPagoParcial);
        this.indicadorPagoMayor = new SimpleStringProperty(indicadorPagoMayor);
        this.indicadorPagoEnCero = new SimpleStringProperty(indicadorPagoEnCero);
        this.textoReferencia1 = new SimpleStringProperty(textoReferencia1);
        this.textoReferencia2 = new SimpleStringProperty(textoReferencia2);
        this.textoReferencia3 = new SimpleStringProperty(textoReferencia3);
        this.indicadorReferenciaFija = new SimpleStringProperty(indicadorReferenciaFija);
        this.colnumFact = new SimpleStringProperty(numeroFactura);
        this.colReferencia1 = new SimpleStringProperty(referencia1);
        this.colReferencia2 = new SimpleStringProperty(referencia2);
        this.colReferencia3 = new SimpleStringProperty(referencia3);
        this.colfechaVen = new SimpleStringProperty(fechaDeVencimiento);
        this.colvalorPagar = new SimpleStringProperty(valorFactura);
        this.colvalorpagarRestante = new SimpleStringProperty(valorRestanteDelPago);
    }
    
    

//    public StringProperty colcodigoConvenioProperty() {
//        return codigoConvenio;
//    }
//
//    public void setCodigoConvenio(StringProperty codigoConvenio) {
//        this.codigoConvenio = codigoConvenio;
//    }
//
//    public StringProperty coltipoRecaudoProperty() {
//        return tipoRecaudo;
//    }
//
//    public void setTipoRecaudo(StringProperty tipoRecaudo) {
//        this.tipoRecaudo = tipoRecaudo;
//    }
//
//    public StringProperty colcuentaRecaudoProperty() {
//        return cuentaRecaudo;
//    }
//
//    public void setCuentaRecaudo(StringProperty cuentaRecaudo) {
//        this.cuentaRecaudo = cuentaRecaudo;
//    }
//
//    public StringProperty colcantidadReferenciasProperty() {
//        return cantidadReferencias;
//    }
//
//    public void setCantidadReferencias(StringProperty cantidadReferencias) {
//        this.cantidadReferencias = cantidadReferencias;
//    }
//
//    public StringProperty colnombreConvenioProperty() {
//        return nombreConvenio;
//    }
//
//    public void setNombreConvenio(StringProperty nombreConvenio) {
//        this.nombreConvenio = nombreConvenio;
//    }
//
//    public StringProperty colclaseCuentaProperty() {
//        return claseCuenta;
//    }
//
//    public void setClaseCuenta(StringProperty claseCuenta) {
//        this.claseCuenta = claseCuenta;
//    }
//
//    public StringProperty colindicadorBDProperty() {
//        return indicadorBD;
//    }
//
//    public void setIndicadorBD(StringProperty indicadorBD) {
//        this.indicadorBD = indicadorBD;
//    }
//
//    public StringProperty colindicadorContingenciaBDProperty() {
//        return indicadorContingenciaBD;
//    }
//
//    public void setIndicadorContingenciaBD(StringProperty indicadorContingenciaBD) {
//        this.indicadorContingenciaBD = indicadorContingenciaBD;
//    }
//
//    public StringProperty colindicadorPagoParcialProperty() {
//        return indicadorPagoParcial;
//    }
//
//    public void setIndicadorPagoParcial(StringProperty indicadorPagoParcial) {
//        this.indicadorPagoParcial = indicadorPagoParcial;
//    }
//
//    public StringProperty colindicadorPagoMayorProperty() {
//        return indicadorPagoMayor;
//    }
//
//    public void setIndicadorPagoMayor(StringProperty indicadorPagoMayor) {
//        this.indicadorPagoMayor = indicadorPagoMayor;
//    }
//
//    public StringProperty colindicadorPagoEnCeroProperty() {
//        return indicadorPagoEnCero;
//    }
//
//    public void setIndicadorPagoEnCero(StringProperty indicadorPagoEnCero) {
//        this.indicadorPagoEnCero = indicadorPagoEnCero;
//    }
//
//    public StringProperty coltextoReferencia1Property() {
//        return textoReferencia1;
//    }
//
//    public void setTextoReferencia1(StringProperty textoReferencia1) {
//        this.textoReferencia1 = textoReferencia1;
//    }
//
//    public StringProperty coltextoReferencia2Property() {
//        return textoReferencia2;
//    }
//
//    public void setTextoReferencia2(StringProperty textoReferencia2) {
//        this.textoReferencia2 = textoReferencia2;
//    }
//
//    public StringProperty coltextoReferencia3Property() {
//        return textoReferencia3;
//    }
//
//    public void setTextoReferencia3(StringProperty textoReferencia3) {
//        this.textoReferencia3 = textoReferencia3;
//    }
//
//    public StringProperty colindicadorReferenciaFijaProperty() {
//        return indicadorReferenciaFija;
//    }
//
//    public void setIndicadorReferenciaFija(StringProperty indicadorReferenciaFija) {
//        this.indicadorReferenciaFija = indicadorReferenciaFija;
//    }
//
//    public StringProperty colnumeroFacturaProperty() {
//        return colnumFact;
//    }
//
//    public void setNumeroFactura(StringProperty numeroFactura) {
//        this.colnumFact = numeroFactura;
//    }
//
//    public StringProperty colreferencia1Property() {
//        return colReferencia1;
//    }
//
//    public void setReferencia1(StringProperty referencia1) {
//        this.colReferencia1 = referencia1;
//    }
//
//    public StringProperty colreferencia2Property() {
//        return colReferencia2;
//    }
//
//    public void setReferencia2(StringProperty referencia2) {
//        this.colReferencia2 = referencia2;
//    }
//
//    public StringProperty colreferencia3Property() {
//        return colReferencia3;
//    }
//
//    public void setReferencia3(StringProperty referencia3) {
//        this.colReferencia3 = referencia3;
//    }
//
//    public StringProperty colfechaDeVencimientoProperty() {
//        return colfechaVen;
//    }
//
//    public void setFechaDeVencimiento(StringProperty fechaDeVencimiento) {
//        this.colfechaVen = fechaDeVencimiento;
//    }
//
//    public StringProperty colvalorFacturaProperty() {
//        return colvalorPagar;
//    }
//
//    public void setValorFactura(StringProperty valorFactura) {
//        this.colvalorPagar = valorFactura;
//    }
//
//    public StringProperty colvalorRestanteDelPagoProperty() {
//        return colvalorpagarRestante;
//    }
//
//    public void setValorRestanteDelPago(StringProperty valorRestanteDelPago) {
//        this.colvalorpagarRestante = valorRestanteDelPago;
//    }

    public StringProperty getCodigoConvenio() {
        return codigoConvenio;
    }

    public void setCodigoConvenio(StringProperty codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
    }

    public StringProperty getTipoRecaudo() {
        return tipoRecaudo;
    }

    public void setTipoRecaudo(StringProperty tipoRecaudo) {
        this.tipoRecaudo = tipoRecaudo;
    }

    public StringProperty getCuentaRecaudo() {
        return cuentaRecaudo;
    }

    public void setCuentaRecaudo(StringProperty cuentaRecaudo) {
        this.cuentaRecaudo = cuentaRecaudo;
    }

    public StringProperty getCantidadReferencias() {
        return cantidadReferencias;
    }

    public void setCantidadReferencias(StringProperty cantidadReferencias) {
        this.cantidadReferencias = cantidadReferencias;
    }

    public StringProperty getNombreConvenio() {
        return nombreConvenio;
    }

    public void setNombreConvenio(StringProperty nombreConvenio) {
        this.nombreConvenio = nombreConvenio;
    }

    public StringProperty getClaseCuenta() {
        return claseCuenta;
    }

    public void setClaseCuenta(StringProperty claseCuenta) {
        this.claseCuenta = claseCuenta;
    }

    public StringProperty getIndicadorBD() {
        return indicadorBD;
    }

    public void setIndicadorBD(StringProperty indicadorBD) {
        this.indicadorBD = indicadorBD;
    }

    public StringProperty getIndicadorContingenciaBD() {
        return indicadorContingenciaBD;
    }

    public void setIndicadorContingenciaBD(StringProperty indicadorContingenciaBD) {
        this.indicadorContingenciaBD = indicadorContingenciaBD;
    }

    public StringProperty getIndicadorPagoParcial() {
        return indicadorPagoParcial;
    }

    public void setIndicadorPagoParcial(StringProperty indicadorPagoParcial) {
        this.indicadorPagoParcial = indicadorPagoParcial;
    }

    public StringProperty getIndicadorPagoMayor() {
        return indicadorPagoMayor;
    }

    public void setIndicadorPagoMayor(StringProperty indicadorPagoMayor) {
        this.indicadorPagoMayor = indicadorPagoMayor;
    }

    public StringProperty getIndicadorPagoEnCero() {
        return indicadorPagoEnCero;
    }

    public void setIndicadorPagoEnCero(StringProperty indicadorPagoEnCero) {
        this.indicadorPagoEnCero = indicadorPagoEnCero;
    }

    public StringProperty getTextoReferencia1() {
        return textoReferencia1;
    }

    public void setTextoReferencia1(StringProperty textoReferencia1) {
        this.textoReferencia1 = textoReferencia1;
    }

    public StringProperty getTextoReferencia2() {
        return textoReferencia2;
    }

    public void setTextoReferencia2(StringProperty textoReferencia2) {
        this.textoReferencia2 = textoReferencia2;
    }

    public StringProperty getTextoReferencia3() {
        return textoReferencia3;
    }

    public void setTextoReferencia3(StringProperty textoReferencia3) {
        this.textoReferencia3 = textoReferencia3;
    }

    public StringProperty getIndicadorReferenciaFija() {
        return indicadorReferenciaFija;
    }

    public void setIndicadorReferenciaFija(StringProperty indicadorReferenciaFija) {
        this.indicadorReferenciaFija = indicadorReferenciaFija;
    }

    public StringProperty colnumFactProperty() {
        return colnumFact;
    }

    public void setColnumFact(StringProperty colnumFact) {
        this.colnumFact = colnumFact;
    }

    public StringProperty colReferencia1Property() {
        return colReferencia1;
    }

    public void setColReferencia1(StringProperty colReferencia1) {
        this.colReferencia1 = colReferencia1;
    }

    public StringProperty colReferencia2Property() {
        return colReferencia2;
    }

    public void setColReferencia2(StringProperty colReferencia2) {
        this.colReferencia2 = colReferencia2;
    }

    public StringProperty colReferencia3Property() {
        return colReferencia3;
    }

    public void setColReferencia3(StringProperty colReferencia3) {
        this.colReferencia3 = colReferencia3;
    }

    public StringProperty colfechaVenProperty() {
        return colfechaVen;
    }

    public void setColfechaVen(StringProperty colfechaVen) {
        this.colfechaVen = colfechaVen;
    }

    public StringProperty colvalorPagarProperty() {
        return colvalorPagar;
    }

    public void setColvalorPagar(StringProperty colvalorPagar) {
        this.colvalorPagar = colvalorPagar;
    }

    public StringProperty colvalorpagarRestanteProperty() {
        return colvalorpagarRestante;
    }

    public void setColvalorpagarRestante(StringProperty colvalorpagarRestante) {
        this.colvalorpagarRestante = colvalorpagarRestante;
    }
}
