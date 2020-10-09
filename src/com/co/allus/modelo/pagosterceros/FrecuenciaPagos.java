/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosterceros;

import java.util.HashMap;

/**
 *
 * @author stephania.rojas
 */
public class FrecuenciaPagos {

    public static final HashMap<String, String> frecuenciaPagos = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("D", "Diario");
        result.put("M", "Mensual");
        result.put("T", "2 veces al mes");
        result.put("W", "Semanal");
        result.put("F", "Quincenal");
        result.put("Q", "Trimestral");
        result.put("H", "Semestral");
        result.put("Y", "Anual");
        result.put("N", "Cada N días");


        return result;
    }

    public HashMap<String, String> getFrecuenciaPago() {
        return this.frecuenciaPagos;
    }
}
