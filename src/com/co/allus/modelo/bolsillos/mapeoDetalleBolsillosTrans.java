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
public class mapeoDetalleBolsillosTrans {
    public final static String SI = "SI";
    public final static String NO = "NO";
    public static final HashMap<String, String> mapeoDetalleBolsillosTrans = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("S", SI);
        result.put("N", NO);
        return result;
    }

    public HashMap<String, String> mapeoDetalleBolsillosTrans() {
        return this.mapeoDetalleBolsillosTrans;
    }
    
}
