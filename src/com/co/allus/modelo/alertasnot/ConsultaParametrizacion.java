/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.alertasnot;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author alexander.lopez.o
 */
public class ConsultaParametrizacion {

    private StringProperty colCodigoOp;
    private StringProperty colDescodigoOp;
    private StringProperty colTipoOp;
    private StringProperty colTipoid;
    private StringProperty colNumid;
    private StringProperty colCelular;
    private StringProperty colEmail;
    private StringProperty colTipocta;
    private StringProperty colNumCta;
    private StringProperty colNumOperacion;
    private StringProperty colMontoHabilitado;
    private StringProperty colIndEliminacion;
    private StringProperty colFechaHora;

    public ConsultaParametrizacion(String colCodigoOp,
            String colDescodigoOp,
            String colTipoOp,
            String colTipoid,
            String colNumid,
            String colCelular,
            String colEmail,
            String colTipocta,
            String colNumCta,
            String colNumOperacion,
            String colMontoHabilitado,
            String colIndEliminacion,
            String colFechaHora) {
        this.colCodigoOp = new SimpleStringProperty(colCodigoOp);
        this.colDescodigoOp = new SimpleStringProperty(colDescodigoOp);
        this.colTipoOp = new SimpleStringProperty(colTipoOp);
        this.colTipoid = new SimpleStringProperty(colTipoid);
        this.colNumid = new SimpleStringProperty(colNumid);
        this.colCelular = new SimpleStringProperty(colCelular);
        this.colEmail = new SimpleStringProperty(colEmail);
        this.colTipocta = new SimpleStringProperty(colTipocta);
        this.colNumCta = new SimpleStringProperty(colNumCta);
        this.colNumOperacion = new SimpleStringProperty(colNumOperacion);
        this.colMontoHabilitado = new SimpleStringProperty(colMontoHabilitado);
        this.colIndEliminacion = new SimpleStringProperty(colIndEliminacion);
        this.colFechaHora = new SimpleStringProperty(colFechaHora);


    }

    public StringProperty colCodigoOpProperty() {
        return colCodigoOp;
    }

    public void setColCodigoOp(StringProperty colCodigoOp) {
        this.colCodigoOp = colCodigoOp;
    }

    public StringProperty colDescodigoOpProperty() {
        return colDescodigoOp;
    }

    public void setColDescodigoOp(StringProperty colDescodigoOp) {
        this.colDescodigoOp = colDescodigoOp;
    }

    public StringProperty colTipoOpProperty() {
        return colTipoOp;
    }

    public void setColTipoOp(StringProperty colTipoOp) {
        this.colTipoOp = colTipoOp;
    }

    public StringProperty colTipoidProperty() {
        return colTipoid;
    }

    public void setColTipoid(StringProperty colTipoid) {
        this.colTipoid = colTipoid;
    }

    public StringProperty colNumidProperty() {
        return colNumid;
    }

    public void setColNumid(StringProperty colNumid) {
        this.colNumid = colNumid;
    }

    public StringProperty colCelularProperty() {
        return colCelular;
    }

    public void setColCelular(StringProperty colCelular) {
        this.colCelular = colCelular;
    }

    public StringProperty colEmailProperty() {
        return colEmail;
    }

    public void setColEmail(StringProperty colEmail) {
        this.colEmail = colEmail;
    }

    public StringProperty colTipoctaProperty() {
        return colTipocta;
    }

    public void setColTipocta(StringProperty colTipocta) {
        this.colTipocta = colTipocta;
    }

    public StringProperty colNumCtaProperty() {
        return colNumCta;
    }

    public void setColNumCta(StringProperty colNumCta) {
        this.colNumCta = colNumCta;
    }

    public StringProperty colNumOperacionProperty() {
        return colNumOperacion;
    }

    public void setColNumOperacion(StringProperty colNumOperacion) {
        this.colNumOperacion = colNumOperacion;
    }

    public StringProperty colMontoHabilitadoProperty() {
        return colMontoHabilitado;
    }

    public void setColMontoHabilitado(StringProperty colMontoHabilitado) {
        this.colMontoHabilitado = colMontoHabilitado;
    }

    public StringProperty colIndEliminacionProperty() {
        return colIndEliminacion;
    }

    public void setColIndEliminacion(StringProperty colIndEliminacion) {
        this.colIndEliminacion = colIndEliminacion;
    }

    public StringProperty colFechaHoraProperty() {
        return colFechaHora;
    }

    public void setColFechaHora(StringProperty colFechaHora) {
        this.colFechaHora = colFechaHora;
    }
}
