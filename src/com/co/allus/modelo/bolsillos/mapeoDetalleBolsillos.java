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
public class mapeoDetalleBolsillos {
    public final static String DOMINGO = "DOMINGO";
    public final static String LUNES = "LUNES";
    public final static String MARTES = "MARTES";
    public final static String MIERCOLES = "MIERCOLES";
    public final static String JUEVES = "JUEVES";
    public final static String VIERNES = "VIERNES";
    public final static String SABADO = "SABADO";
    public final static String DIARIA = "DIARIA";
    public final static String SEMANAL = "SEMANAL";
    public final static String QUINCENAL = "QUINCENAL";
    public final static String MENSUAL = "MENSUAL";
    public static final HashMap<String, String> mapeoDetalleBolsillos = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("1", DOMINGO);
        result.put("2", LUNES);
        result.put("3", MARTES);
        result.put("4", MIERCOLES);
        result.put("5", JUEVES);
        result.put("6", VIERNES);
        result.put("7", SABADO);
        result.put("D", DIARIA);
        result.put("S", SEMANAL);
        result.put("Q", QUINCENAL);
        result.put("M", MENSUAL);
        return result;
    }

    public HashMap<String, String> mapeoDetalleBolsillos() {
        return this.mapeoDetalleBolsillos;
    }
}
