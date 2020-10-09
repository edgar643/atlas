/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class MenuModel {

    private final SimpleStringProperty menuHijo;
    private final SimpleStringProperty menuPadre;

    public MenuModel(String menuhijo, String menupadre) {
        this.menuHijo = new SimpleStringProperty(menuhijo);
        this.menuPadre = new SimpleStringProperty(menupadre);
    }

    public String getMenuHijo() {
        return menuHijo.get();
    }

    public void setMenuiHijo(String fName) {
        menuHijo.set(fName);
    }

    public String getMenuPadre() {
        return menuPadre.get();
    }

    public void setMenuPadre(String fName) {
        menuHijo.set(fName);
    }
}
