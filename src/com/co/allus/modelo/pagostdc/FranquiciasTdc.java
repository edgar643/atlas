/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagostdc;

import java.util.HashMap;

/**
 *
 * @author alexander.lopez.o
 */
public class FranquiciasTdc {

    public final static String AMEX = "American Express";
    public final static String MASTERCARD = "MasterCard";
    public final static String VISA = "Visa";
    public static final HashMap<String, String> franquiciasTDC = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("0", AMEX);
        result.put("3", AMEX);
        result.put("4", VISA);
        result.put("5", MASTERCARD);
        return result;
    }

    public HashMap<String, String> getFranquiciasTDC() {
        return this.franquiciasTDC;
    }
}
