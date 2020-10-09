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
public class mapeoMovimientosBolsillos {
    public final static String BLOQUEO = "Bloqueo";
    public final static String DESBLOQUEO = "Desbloqueo";
    public final static String TRANSFERENCIA = "Transferencia";
    public static final HashMap<String, String> mapeoMovimientosBolsillos = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("B", BLOQUEO);
        result.put("D", DESBLOQUEO);
        result.put("T", TRANSFERENCIA);
        return result;
    }

    public HashMap<String, String> mapeoMovimientosBolsillos() {
        return this.mapeoMovimientosBolsillos;
    }
   
}
