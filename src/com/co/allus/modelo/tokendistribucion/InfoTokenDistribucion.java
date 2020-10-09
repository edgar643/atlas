/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokendistribucion;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class InfoTokenDistribucion {

    private StringProperty colGuia;
    private StringProperty colSerial;
    private StringProperty colID;
    private StringProperty colNombre;
    private StringProperty colGestionTele;
    private StringProperty colGestionEnt;
    private StringProperty colContactoEnt;

    public InfoTokenDistribucion(String colNombre, String colID, String colSerial, String colGuia, String colGestionTele, String colGestionEnt, String colContactoEnt) {
        // Nombre Completo%ID%Cod Serial%Codigo Guia%Nombre Proveedor%Gestion Telefonica%Gestion Entrega%Contacto Entrega
        this.colGuia = new SimpleStringProperty(colGuia);
        this.colSerial = new SimpleStringProperty(colSerial);
        this.colID = new SimpleStringProperty(colID);
        this.colNombre = new SimpleStringProperty(colNombre);
        this.colGestionTele = new SimpleStringProperty(colGestionTele);
        this.colGestionEnt = new SimpleStringProperty(colGestionEnt);
        this.colContactoEnt = new SimpleStringProperty(colContactoEnt);

    }

    public String getColGuia() {
        return colGuia.get();
    }

    public void setColGuia(String colGuia) {
        this.colGuia.set(colGuia);
    }

    public String getColSerial() {
        return colSerial.get();
    }

    public void setColSerial(String colSerial) {
        this.colSerial.set(colSerial);
    }

    public String getColID() {
        return colID.get();
    }

    public void setColID(String colID) {
        this.colID.set(colID);
    }

    public String getColNombre() {
        return colNombre.get();
    }

    public void setColNombre(String colNombre) {
        this.colNombre.set(colNombre);
    }

    public String getColGestionTele() {
        return colGestionTele.get();
    }

    public void setColGestionTele(String colGestionTele) {
        this.colGestionTele.set(colGestionTele);
    }

    public String getColGestionEnt() {
        return colGestionEnt.get();
    }

    public void setColGestionEnt(String colGestionEnt) {
        this.colGestionEnt.set(colGestionEnt);
    }

    public String getColContactoEnt() {
        return colContactoEnt.get();
    }

    public void setColContactoEnt(String colContactoEnt) {
        this.colContactoEnt.set(colContactoEnt);
    }
}
