/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoAccesoEmergencia {

    private StringProperty colID_usuario;
    private StringProperty colNombre_usuario;
    private StringProperty colCelular;
    private StringProperty colEmail;
    private StringProperty colAE;

    public infoAccesoEmergencia(String colID_usuario, String colNombre_usuario, String colCelular, String colEmail, String colAE) {

        this.colID_usuario = new SimpleStringProperty(colID_usuario == null ? "" : colID_usuario);
        this.colNombre_usuario = new SimpleStringProperty(colNombre_usuario == null ? "" : colNombre_usuario);
        this.colEmail = new SimpleStringProperty(colEmail == null ? "" : colEmail);
        this.colCelular = new SimpleStringProperty(colCelular == null ? "" : colCelular);
        this.colAE = new SimpleStringProperty(colAE == null ? "" : colAE);

    }

    public String getColID_usuario() {
        return colID_usuario.get();
    }

    public String getColNombre_usuario() {
        return colNombre_usuario.get();
    }

    public String getColCelular() {
        return colCelular.get();
    }

    public String getColEmail() {
        return colEmail.get();
    }

    public String getColAE() {
        return colAE.get();
    }

//  public StringProperty colID_usuarioProperty() {
//        return colID_usuario;
//     }
//  public StringProperty colNombre_usuarioProperty() {
//        return colNombre_usuario;
//     }
//  public StringProperty colCelularProperty() {
//        return colCelular;
//     }
//  public StringProperty colEmailProperty() {
//        return colEmail;
//     }
//  public StringProperty colAEProperty() {
//        return colAE;
//     }
    public void setColID_usuario(String colID_usuario) {
        this.colID_usuario.set(colID_usuario);
    }

    public void setColNombre_usuario(String colNombre_usuario) {
        this.colNombre_usuario.set(colNombre_usuario);
    }

    public void setColCelular(String colCelular) {
        this.colCelular.set(colCelular);
    }

    public void setColEmail(String colEmail) {
        this.colEmail.set(colEmail);
    }

    public void setColAE(String colAE) {
        this.colAE.set(colAE);
    }
}
