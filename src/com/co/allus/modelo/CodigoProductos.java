/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo;

import java.util.HashMap;

/**
 *
 * @author alexander.lopez.o
 */
public class CodigoProductos {

    public final static String CUENTA_AHORROS = "Cuenta Ahorros";
    public final static String CUENTA_CORRIENTE = "Cuenta Corriente";
    public final static String TARJETA_DE_CREDITO = "Tarjeta de Credito";
    public final static String CREDITO_HIPOTECARIO = "Credito Hipotecario";
    public final static String CUENTA_AFC = "Cuenta AFC";
    public final static String CREDITO_CDT = "CDT";
    public final static String CARTERA = "Cartera";
    public static final HashMap<String, String> codigosProd = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("01002", CUENTA_AHORROS);
        result.put("01001", CUENTA_CORRIENTE);
        result.put("01003", TARJETA_DE_CREDITO);
        result.put("01006", CREDITO_HIPOTECARIO);
        result.put("01004", CARTERA);
        result.put("01005", CREDITO_CDT);
        result.put("01007", CUENTA_AFC);
        return result;
    }

    public HashMap<String, String> getCodigosProd() {
        return this.codigosProd;
    }
}
