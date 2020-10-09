/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.desbloqueoalm;

import java.util.HashMap;

/**
 *
 * @author roberto.ceballos
 */
public class MapeoEstadosAlm {
    public final static String ACTIVO = "ACTIVO";
    public final static String INACTIVO = "INACTIVO";
    public static final HashMap<String, String> mapeoEstadosAlm = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("A", ACTIVO);
        result.put("I", INACTIVO);
        return result;
    }

    public HashMap<String, String> getMapeoEstados() {
        return this.mapeoEstadosAlm;
    }
    
}
