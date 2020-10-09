/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokendistribucion;

import com.sun.javafx.css.StyleableStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class InfoTokenContacto {

    private StringProperty colNombreComp;
    private StringProperty colNumTelefono;
    private StringProperty colNumTelAlterno;
    private StringProperty colNumCel;
    private StringProperty colDireccion;
    private StringProperty colCodDepartamento;
    private StringProperty colCodCiudad;
    private StringProperty colTipoDireccion;
    private StringProperty colNombreMensajero;
    private StringProperty colNumCcMensajero;
    private StringProperty colNombreContacto;
    private StringProperty colSucursal;
    private StringProperty colTipoContacto;
    private StringProperty colTipodoc;
    private StringProperty coldocumento;

    public InfoTokenContacto(String colNombreComp,
            String colNumTelefono,
            String colNumTelAlterno,
            String colNumCel,
            String colDireccion,
            String colCodDepartamento,
            String colCodCiudad,
            String colTipoDireccion,
            String colNombreMensajero,
            String colNumCcMensajero,
            String colNombreContacto,
            String colsucursal,
            String colTipoContacto,
            String colTipodoc,
            String coldocumento) {
        //NombreCompleto%NumeroTelefono%NumeroTelAlterno%NumeroCelular%Direccion%CodigoDepartamento%CodigoCiudad%TipoDireccion%NombreMensajero%NumeroDocumentoMensajero%NombreContacto
        this.colNombreComp = new SimpleStringProperty(colNombreComp);
        this.colNumTelefono = new SimpleStringProperty(colNumTelefono);
        this.colNumTelAlterno = new SimpleStringProperty(colNumTelAlterno);
        this.colNumCel = new SimpleStringProperty(colNumCel);
        this.colDireccion = new SimpleStringProperty(colDireccion);
        this.colCodCiudad = new SimpleStringProperty(colCodCiudad);
        this.colTipoDireccion = new SimpleStringProperty(colTipoDireccion);
        this.colNombreMensajero = new SimpleStringProperty(colNombreMensajero);
        this.colNumCcMensajero = new SimpleStringProperty(colNumCcMensajero);
        this.colNombreContacto = new SimpleStringProperty(colNombreContacto);
        this.colCodDepartamento = new SimpleStringProperty(colCodDepartamento);
        this.colSucursal = new SimpleStringProperty(colsucursal);
        this.colTipoContacto = new SimpleStringProperty(colTipoContacto);
         this.colTipodoc = new SimpleStringProperty(colTipodoc);
          this.coldocumento = new SimpleStringProperty(coldocumento);

    }

    public String getColTipodoc() {
        return colTipodoc.get();
    }

    public void setColTipodoc(String colTipodoc) {
        this.colTipodoc.set(colTipodoc);
    }

    public String getColdocumento() {
        return coldocumento.get();
    }

    public void setColdocumento(String coldocumento) {
        this.coldocumento.set(coldocumento);
    }
    
    

    public String getColSucursal() {
        return colSucursal.get();
    }

    public void setColSucursal(String colSucursal) {
        this.colSucursal.set(colSucursal);
    }

    public String getColTipoContacto() {
        return colTipoContacto.get();
    }

    public void setColTipoContacto(String colTipoContacto) {
        this.colTipoContacto.set(colTipoContacto);
    }

    public String getColNombreComp() {
        return colNombreComp.get();
    }

    public void setColNombreComp(String colNombreComp) {
        this.colNombreComp.set(colNombreComp);
    }

    public String getColNumTelAlterno() {
        return colNumTelAlterno.get();
    }

    public void setColNumTelAlterno(String colNumTelAlterno) {
        this.colNumTelAlterno.set(colNumTelAlterno);
    }

    public String getColCodDepartamento() {
        return colCodDepartamento.get();
    }

    public void setColCodDepartamento(String colCodDepartamento) {
        this.colCodDepartamento.set(colCodDepartamento);
    }

    public String getColNumTelefono() {
        return colNumTelefono.get();
    }

    public void setColNumTelefono(String colNumTelefono) {
        this.colNumTelefono.set(colNumTelefono);
    }

    public String getColNumCel() {
        return colNumCel.get();
    }

    public void setColNumCel(String colNumCel) {
        this.colNumCel.set(colNumCel);
    }

    public String getColDireccion() {
        return colDireccion.get();
    }

    public void setColDireccion(String colDireccion) {
        this.colDireccion.set(colDireccion);
    }

    public String getColCodCiudad() {
        return colCodCiudad.get();
    }

    public void setColCodCiudad(String colCodCiudad) {
        this.colCodCiudad.set(colCodCiudad);
    }

    public String getColTipoDireccion() {
        return colTipoDireccion.get();
    }

    public void setColTipoDireccion(String colTipoDireccion) {
        this.colTipoDireccion.set(colTipoDireccion);
    }

    public String getColNombreMensajero() {
        return colNombreMensajero.get();
    }

    public void setColNombreMensajero(String colNombreMensajero) {
        this.colNombreMensajero.set(colNombreMensajero);
    }

    public String getColNumCcMensajero() {
        return colNumCcMensajero.get();
    }

    public void setColNumCcMensajero(String colNumCcMensajero) {
        this.colNumCcMensajero.set(colNumCcMensajero);
    }

    public String getColNombreContacto() {
        return colNombreContacto.get();
    }

    public void setColNombreContacto(String colNombreContacto) {
        this.colNombreContacto.set(colNombreContacto);
    }
}
