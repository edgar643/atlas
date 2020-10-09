/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosterceros;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class DatosPagoFactura {

    private SimpleStringProperty nombre;
    private SimpleStringProperty ref1;
    private SimpleStringProperty ref2;
    private SimpleStringProperty ref3;

    public DatosPagoFactura(String colNombre, String colValor1, String colValor2, String colValor3) {
        this.nombre = new SimpleStringProperty(colNombre);
        this.ref1 = new SimpleStringProperty(colValor1);
        this.ref2 = new SimpleStringProperty(colValor2);
        this.ref3 = new SimpleStringProperty(colValor3);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getRef1() {
        return ref1.get();
    }

    public void setRef1(String ref1) {
        this.ref1.set(ref1);
    }

    public String getRef2() {
        return ref2.get();
    }

    public void setRef2(String ref2) {
        this.ref2.set(ref2);
    }

    public String getRef3() {
        return ref3.get();
    }

    public void setRef3(String ref3) {
        this.ref3.set(ref3);
    }
}
