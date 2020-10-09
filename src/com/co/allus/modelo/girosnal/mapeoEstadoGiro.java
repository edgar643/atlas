/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.girosnal;

import java.util.HashMap;

/**
 *
 * @author roberto.ceballos
 */
public class mapeoEstadoGiro {
    public final static String DIS = "DIS";
    public final static String PAG = "PAG";
    public final static String PEN = "PEN";
    public final static String CAN = "CAN";
    public final static String REV = "REV";
    public static final HashMap<String, String> mapeoEstadoGiro = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("1", DIS);
        result.put("2", PAG);
        result.put("3", PEN);
        result.put("4", CAN);
        result.put("5", REV);
        return result;
    }

    public HashMap<String, String> mapeoEstadoGiro() {
        return this.mapeoEstadoGiro;
    }
}
