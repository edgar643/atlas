/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bolsillos;

import java.util.HashMap;

/**
 *
 * @author roberto.ceballos
 */
public class mapeoListarBolsillos {
    public final static String ACTIVO = "ACTIVO";
    public final static String CANCELADO = "CANCELADO";
    public static final HashMap<String, String> mapeoListarBolsillos = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("A", ACTIVO);
        result.put("C", CANCELADO);
        return result;
    }

    public HashMap<String, String> mapeoListarBolsillos() {
        return this.mapeoListarBolsillos;
    }
}
