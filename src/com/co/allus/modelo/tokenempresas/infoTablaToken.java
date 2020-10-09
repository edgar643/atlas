/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

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
public class infoTablaToken {

    private StringProperty colId_usuario;
    private StringProperty colNombre_usuario;
    private StringProperty colEstado1;
    private StringProperty colRol;
    private StringProperty colPerfil;
    private StringProperty colEstado2;
    private StringProperty colSerial;
    private StringProperty colFecha_exp;
    private StringProperty colAe;
    private StringProperty nit;
    private StringProperty estado_empresa;
    private StringProperty esquema_seguridad;
    private StringProperty token_disponibles;
    private StringProperty serial_certificado;
    private StringProperty codigoEstadoCertificado;
    private StringProperty descripcionEstCert;
    //private BooleanProperty button;

    public infoTablaToken(String colId_usuario, String colEstado1, String colNombre_usuario, String colRol, String colPerfil, String colEstado2, String colSerial, String colFecha_exp, String colAe, String nit, String estado_empresa, String esquema_seguridad, String token_disponibles, String serial_certificado, String codigoEstadoCertificado, String descripcionEstCert) {
        //Superusuario%MACRO_PROFILE%E%Descripcion Token%10245864%06/09/2016%NO;"
        this.colId_usuario = new SimpleStringProperty(colId_usuario);
        this.colEstado1 = new SimpleStringProperty(colEstado1);
        this.colNombre_usuario = new SimpleStringProperty(colNombre_usuario);
        this.colRol = new SimpleStringProperty(colRol);
        this.colPerfil = new SimpleStringProperty(colPerfil);
        this.colEstado2 = new SimpleStringProperty(colEstado2);
        this.colSerial = new SimpleStringProperty(colSerial);
        this.colFecha_exp = new SimpleStringProperty(colFecha_exp);
        this.colAe = new SimpleStringProperty(colAe);
        this.nit = new SimpleStringProperty(nit);
        this.estado_empresa = new SimpleStringProperty(estado_empresa);
        this.esquema_seguridad = new SimpleStringProperty(esquema_seguridad);
        this.token_disponibles = new SimpleStringProperty(token_disponibles);
        this.serial_certificado = new SimpleStringProperty(serial_certificado);
        this.codigoEstadoCertificado = new SimpleStringProperty(codigoEstadoCertificado);
        this.descripcionEstCert = new SimpleStringProperty(descripcionEstCert);

        //this.button = new SimpleBooleanProperty(button);

//        this.button.addListener(new ChangeListener<Boolean>() {
//            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
//                System.out.println(seleccionProperty() + " invited: " + t1);
//            }
//        });
    }

//    public BooleanProperty seleccionProperty() {
//        return button;
//    }
    public String getColId_usuario() {
        return colId_usuario.get();
    }

    public StringProperty colNombre_usuarioProperty() {
        return colNombre_usuario;
    }

    public StringProperty colEstado1Property() {
        return colEstado1;
    }

    public StringProperty colRolProperty() {
        return colRol;
    }

    public StringProperty colPerfilProperty() {
        return colPerfil;
    }

    public StringProperty colEstado2Property() {
        return colEstado2;
    }

    public StringProperty colSerialProperty() {
        return colSerial;
    }

    public StringProperty colFecha_expProperty() {
        return colFecha_exp;
    }

    public StringProperty colAeProperty() {
        return colAe;
    }

    public StringProperty nit() {
        return nit;
    }

    public StringProperty estado_empresa() {
        return estado_empresa;
    }

    public StringProperty esquema_seguridad() {
        return esquema_seguridad;
    }

    public StringProperty token_disponibles() {
        return token_disponibles;
    }

    public StringProperty serial_certificado() {
        return serial_certificado;
    }

    public StringProperty codigoEstadoCertificado() {
        return codigoEstadoCertificado;
    }

    public StringProperty descripcionEstCert() {
        return descripcionEstCert;
    }

    public void setColId_usuario(String colId_usuario) {
        this.colId_usuario.set(colId_usuario);
    }

    public void setColNombre_usuario(String colNombre_usuario) {
        this.colNombre_usuario.set(colNombre_usuario);
    }

    public void setColEstado1(String colEstado1) {
        this.colEstado1.set(colEstado1);
    }

    public void setColRol(String colRol) {
        this.colRol.set(colRol);
    }

    public void setColPerfil(String colPerfil) {
        this.colPerfil.set(colPerfil);
    }

    public void setColEstado2(String colEstado2) {
        this.colEstado2.set(colEstado2);
    }

    public void setColSerial(String colSerial) {
        this.colSerial.set(colSerial);
    }

    public void setColFecha_exp(String colFecha_exp) {
        this.colFecha_exp.set(colFecha_exp);
    }

    public void setColAe(String colAe) {
        this.colAe.set(colAe);
    }

    public void setNit(String nit) {
        this.nit.set(nit);
    }

    public void setEstado_empresa(String estado_empresa) {
        this.estado_empresa.set(estado_empresa);
    }

    public void setEsquema_seguridad(String esquema_seguridad) {
        this.esquema_seguridad.set(esquema_seguridad);
    }

    public void setToken_disponibles(String token_disponibles) {
        this.token_disponibles.set(token_disponibles);
    }

    public void setSerial_certificado(String serial_certificado) {
        this.serial_certificado.set(serial_certificado);
    }

    public void setCodigoEstadoCertificado(String codigoEstadoCertificado) {
        this.codigoEstadoCertificado.set(codigoEstadoCertificado);
    }

    public void setDescripcionEstCert(String descripcionEstCert) {
        this.descripcionEstCert.set(descripcionEstCert);
    }
}
