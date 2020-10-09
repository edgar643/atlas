/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

import java.util.HashMap;

/**
 *
 * @author alexander.lopez.o
 */
public class MapeoEstados {

    public final static String ACTIVA = "Activa";
    public final static String MIGRADO = "Migrado";
    public final static String PENDIENTE_REG = "Pendiente de Registro";
    public final static String CERRADA = "Cerrada";
    public final static String BLOQUEADA = "Bloqueada";
    public static final HashMap<String, String> mapeoEstados = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("A", ACTIVA);
        result.put("T", ACTIVA);
        result.put("M", MIGRADO);
        result.put("P", PENDIENTE_REG);
        result.put("C", CERRADA);
        result.put("F", CERRADA);
        result.put("B", BLOQUEADA);
        return result;
    }

    public HashMap<String, String> getMapeoEstados() {
        return this.mapeoEstados;
    }
}
