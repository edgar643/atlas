/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosterceros;

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
public class InfoTablaIni {

    private BooleanProperty pagar;
    private StringProperty colCodigoConvenio;
    private StringProperty colNombreConvenio;
    private StringProperty colDescripcion;
    private StringProperty colReferencia1;
    private StringProperty colReferencia2;
    private StringProperty colReferencia3;
    private StringProperty colEstado;
    private StringProperty colValorPagar;
    private StringProperty colFechaHora;
    private StringProperty colFechavencimiento;
    private StringProperty colCanal;

    public InfoTablaIni(boolean pagar, String colCodigoConvenio, String colNombreConvenio, String colDescripcion, String colReferencia1, String colReferencia2, String colReferencia3,
            String colEstado, String colValorPagar, String colFechaHora, String colFechavencimiento, String colCanal) {

        this.pagar = new SimpleBooleanProperty(pagar);
        this.colCodigoConvenio = new SimpleStringProperty(colCodigoConvenio);
        this.colNombreConvenio = new SimpleStringProperty(colNombreConvenio);
        this.colDescripcion = new SimpleStringProperty(colDescripcion);
        this.colReferencia1 = new SimpleStringProperty(colReferencia1);
        this.colReferencia2 = new SimpleStringProperty(colReferencia2);
        this.colReferencia3 = new SimpleStringProperty(colReferencia3);
        this.colEstado = new SimpleStringProperty(colEstado);
        this.colValorPagar = new SimpleStringProperty(colValorPagar);
        this.colFechaHora = new SimpleStringProperty(colFechaHora);
        this.colFechavencimiento = new SimpleStringProperty(colFechavencimiento);
        this.colCanal = new SimpleStringProperty(colCanal);


        this.pagar.addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
            }
        });
    }

    public BooleanProperty pagarProperty() {
        return pagar;
    }

    public StringProperty colCodigoConvenioProperty() {
        return colCodigoConvenio;
    }

    public StringProperty colNombreConvenioProperty() {
        return colNombreConvenio;
    }

    public StringProperty colDescripcionProperty() {
        return colDescripcion;
    }

    public StringProperty colReferencia1Property() {
        return colReferencia1;
    }

    public StringProperty colReferencia2Property() {
        return colReferencia2;
    }

    public StringProperty colReferencia3Property() {
        return colReferencia3;
    }

    public StringProperty colEstadoProperty() {
        return colEstado;
    }

    public StringProperty colValorPagarProperty() {
        return colValorPagar;
    }

    public StringProperty colFechaHoraProperty() {
        return colFechaHora;
    }

    public StringProperty colFechavencimientoProperty() {
        return colFechavencimiento;
    }

    public StringProperty colCanalProperty() {
        return colCanal;
    }
}
