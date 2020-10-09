/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosterceros;

import java.util.Collections;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author stephania.rojas
 */
public class InfoTablaPagosTerceros {

    private BooleanProperty seleccion;
    private StringProperty colCodConvenio;
    private StringProperty colNombreConvenio;
    private StringProperty colCanal;
    private StringProperty colNomRef1;
    private StringProperty colRef1;
    private StringProperty colNomRef2;
    private StringProperty colRef2;
    private StringProperty colNomRef3;
    private StringProperty colRef3;
    private StringProperty colValorPagar;
    private StringProperty colDescripcion;
    private StringProperty colEstado;
    private StringProperty colPagoFacturaHabi;
    private StringProperty colIndicadorRefFija;
    private StringProperty colNumeroFactura;
    private StringProperty colFechaVencimiento;
    private StringProperty colClaseCuenta;
    private StringProperty colCuentaRecaudadora;
    private StringProperty colTipoCuenta;
    private StringProperty colPagoMenor;
    private StringProperty colPagoMayor;
    private StringProperty colPagoCero;
    private StringProperty colIndicadorBD;
    private StringProperty colFechaInscripcion;
    private StringProperty colmontoRestante;
    private StringProperty colindicadorMasFact;
    private StringProperty colValorPagOri;
    private List<infoTablaMasFacturas> masFacturas = Collections.emptyList();

    public InfoTablaPagosTerceros(boolean seleccion, String colCodConvenio, String colNombreConvenio, String colCanal, String colNomRef1, String colRef1, String colNomRef2, String colRef2, String colNomRef3, String colRef3, String colValorPagar, String colDescripcion, String colEstado, String colPagoFacturaHabi, String colIndicadorRefFija, String colNumeroFactura, String colFechaVencimiento,
            String colClaseCuenta, String colCuentaRecaudadora, String colTipoCuenta, String colPagoMenor, String colPagoMayor, String colPagoCero, String colIndicadorBD, String colFechaInscripcion,String colMontorestante,String colindmasfact, String valorpagarori) {

        this.seleccion = new SimpleBooleanProperty(seleccion);
        this.colCodConvenio = new SimpleStringProperty(colCodConvenio);
        this.colNombreConvenio = new SimpleStringProperty(colNombreConvenio);
        this.colCanal = new SimpleStringProperty(colCanal);
        this.colNomRef1 = new SimpleStringProperty(colNomRef1);
        this.colRef1 = new SimpleStringProperty(colRef1);
        this.colNomRef2 = new SimpleStringProperty(colNomRef2);
        this.colRef2 = new SimpleStringProperty(colRef2);
        this.colNomRef3 = new SimpleStringProperty(colNomRef3);
        this.colRef3 = new SimpleStringProperty(colRef3);
        this.colValorPagar = new SimpleStringProperty(colValorPagar);
        this.colDescripcion = new SimpleStringProperty(colDescripcion);
        this.colEstado = new SimpleStringProperty(colEstado);
        this.colPagoFacturaHabi = new SimpleStringProperty(colPagoFacturaHabi);
        this.colIndicadorRefFija = new SimpleStringProperty(colIndicadorRefFija);
        this.colNumeroFactura = new SimpleStringProperty(colNumeroFactura);
        this.colFechaVencimiento = new SimpleStringProperty(colFechaVencimiento);
        this.colClaseCuenta = new SimpleStringProperty(colClaseCuenta);
        this.colCuentaRecaudadora = new SimpleStringProperty(colCuentaRecaudadora);
        this.colTipoCuenta = new SimpleStringProperty(colTipoCuenta);
        this.colPagoMenor = new SimpleStringProperty(colPagoMenor);
        this.colPagoMayor = new SimpleStringProperty(colPagoMayor);
        this.colPagoCero = new SimpleStringProperty(colPagoCero);
        this.colIndicadorBD = new SimpleStringProperty(colIndicadorBD);
        this.colFechaInscripcion = new SimpleStringProperty(colFechaInscripcion);
        this.colmontoRestante=new SimpleStringProperty(colMontorestante);
        this.colindicadorMasFact=new SimpleStringProperty(colindmasfact);
        this.colValorPagOri=new SimpleStringProperty(valorpagarori);

        this.seleccion.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
            }
        });
    }

    public List<infoTablaMasFacturas> getMasFacturas() {
        return masFacturas;
    }

    public void setMasFacturas(List<infoTablaMasFacturas> masFacturas) {
        this.masFacturas = masFacturas;
    }

    public StringProperty colValorPagOriProperty() {
        return colValorPagOri;
    }

    public void setColValorPagOri(String colValorPagOri) {
        this.colValorPagOri.set(colValorPagOri);
    }


    public StringProperty colmontoRestanteProperty() {
        return colmontoRestante;
    }

    public void setColmontoRestante(String colmontoRestante) {
        this.colmontoRestante.set(colmontoRestante);
    }

    public StringProperty colindicadorMasFactProperty() {
        return colindicadorMasFact;
    }

    public void setColindicadorMasFact(StringProperty colindicadorMasFact) {
        this.colindicadorMasFact = colindicadorMasFact;
    }
      

    public BooleanProperty seleccionProperty() {
        return seleccion;
    }

    public StringProperty colCodConvenioProperty() {
        return colCodConvenio;
    }

    public StringProperty colNombreConvenioProperty() {
        return colNombreConvenio;
    }

    public StringProperty colCanalProperty() {
        return colCanal;
    }

    public StringProperty colNomRef1Property() {
        return colNomRef1;
    }

    public StringProperty colRef1Property() {
        return colRef1;
    }

    public StringProperty colNomRef2Property() {
        return colNomRef2;
    }

    public StringProperty colRef2Property() {
        return colRef2;
    }

    public StringProperty colNomRef3Property() {
        return colNomRef3;
    }

    public StringProperty colRef3Property() {
        return colRef3;
    }

    public StringProperty colValorPagarProperty() {
        return colValorPagar;
    }

    public StringProperty colDescripcionProperty() {
        return colDescripcion;
    }

    public StringProperty colEstadoProperty() {
        return colEstado;
    }

    public StringProperty colPagoFacturaHabiProperty() {
        return colPagoFacturaHabi;
    }

    public StringProperty colIndicadorRefFijaProperty() {
        return colIndicadorRefFija;
    }

    public StringProperty colNumeroFacturaProperty() {
        return colNumeroFactura;
    }

    public StringProperty colFechaVencimientoProperty() {
        return colFechaVencimiento;
    }

    public StringProperty colClaseCuentaProperty() {
        return colClaseCuenta;
    }

    public StringProperty colCuentaRecaudadoraProperty() {
        return colCuentaRecaudadora;
    }

    public StringProperty colTipoCuentaProperty() {
        return colTipoCuenta;
    }

    public StringProperty colPagoMenorProperty() {
        return colPagoMenor;
    }

    public StringProperty colPagoMayorProperty() {
        return colPagoMayor;
    }

    public StringProperty colPagoCeroProperty() {
        return colPagoCero;
    }

    public StringProperty colIndicadorBDProperty() {
        return colIndicadorBD;
    }

    public StringProperty colFechaInscripcionProperty() {
        return colFechaInscripcion;
    }

//    public void setSeleccion(Boolean seleccion) {
//        this.seleccion.set(seleccion);
//    }
    public void setColCodConvenio(String colCodConvenio) {
        this.colCodConvenio.set(colCodConvenio);
    }

    public void setColNombreConvenio(String colNombreConvenio) {
        this.colNombreConvenio.set(colNombreConvenio);
    }

    public void setColCanal(String colCanal) {
        this.colCanal.set(colCanal);
    }

    public void setColNomRef1(String colNomRef1) {
        this.colNomRef1.set(colNomRef1);
    }

    public void setColRef1(String colRef1) {
        this.colRef1.set(colRef1);
    }

    public void setColNomRef2(String colNomRef2) {
        this.colNomRef2.set(colNomRef2);
    }

    public void setColRef2(String colRef2) {
        this.colRef2.set(colRef2);
    }

    public void setColNomRef3(String colNomRef3) {
        this.colNomRef3.set(colNomRef3);
    }

    public void setColRef3(String colRef3) {
        this.colRef3.set(colRef3);
    }

    public void setColValorPagar(String colValorPagar) {
        this.colValorPagar.set(colValorPagar);
    }

    public void setColDescripcion(String colDescripcion) {
        this.colDescripcion.set(colDescripcion);
    }

    public void setColEstado(String colEstado) {
        this.colEstado.set(colEstado);
    }

    public void setColPagoFacturaHabi(String colPagoFacturaHabi) {
        this.colPagoFacturaHabi.set(colPagoFacturaHabi);
    }

    public void setColIndicadorRefFija(String colIndicadorRefFija) {
        this.colIndicadorRefFija.set(colIndicadorRefFija);
    }

    public void setColNumeroFactura(String colNumeroFactura) {
        this.colNumeroFactura.set(colNumeroFactura);
    }

    public void setColFechaVencimiento(String colFechaVencimiento) {
        this.colFechaVencimiento.set(colFechaVencimiento);
    }

    public void setColClaseCuenta(String colClaseCuenta) {
        this.colClaseCuenta.set(colClaseCuenta);
    }

    public void setColCuentaRecaudadora(String colCuentaRecaudadora) {
        this.colCuentaRecaudadora.set(colCuentaRecaudadora);
    }

    public void setColTipoCuenta(String colTipoCuenta) {
        this.colTipoCuenta.set(colTipoCuenta);
    }

    public void setColPagoMenor(String colPagoMenor) {
        this.colPagoMenor.set(colPagoMenor);
    }

    public void setColPagoMayor(String colPagoMayor) {
        this.colPagoMayor.set(colPagoMayor);
    }

    public void setColPagoCero(String colPagoCero) {
        this.colPagoCero.set(colPagoCero);
    }

    public void setColIndicadorBD(String colIndicadorBD) {
        this.colIndicadorBD.set(colIndicadorBD);
    }

    public void setColFechaInscripcion(String colFechaInscripcion) {
        this.colFechaInscripcion.set(colFechaInscripcion);
    }
}
