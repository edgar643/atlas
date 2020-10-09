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
public class MapeoLogos {

    public static final HashMap<String, String> mapeoEstados = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("700", "Amex Azul");
        result.put("709", "Amex Azul");
        result.put("703", "Amex Oro");
        result.put("702", "Amex Verde");
        result.put("702", "Amex Verde");
        result.put("701", "Amex Platinum");
        result.put("704", "Mileageplus Clásica");
        result.put("705", "Mileageplus Premium");
        result.put("706", "Amex Business Verde");
        result.put("707", "Amex Business Oro");
        result.put("600", "Mastercard Clasica E- Card");
        result.put("602", "Mastercard Clásica");
        result.put("605", "Mastercard Sufi Clásica");
        result.put("608", "Mastercard Clásica");
        result.put("615", "Mastercard Clásica");
        result.put("616", "Mastercard Esso Mobil Clásica");
        result.put("616", "Mastercard Esso Mobil Clásica");
        result.put("617", "Mastercard Clásica Joven");
        result.put("618", "Mastercard Clásica Intelecto");
        result.put("603", "Mastercard Oro");
        result.put("606", "Mastercard Sufi Oro");
        result.put("609", "Mastercard Oro");
        result.put("619", "Mastercard Esso Mobil Oro");
        result.put("619", "Mastercard Esso Mobil Oro");
        result.put("620", "Mastercard Oro Intelecto");
        result.put("611", "Mastercard Platinum");
        result.put("612", "Mastercard Black");
        result.put("604", "Mastercard Empresarial");
        result.put("610", "Mastercard Empresarial");
        result.put("613", "Mastercard Empresarial");
        result.put("400", "Visa Clásica");
        result.put("401", "Visa Clásica Selección");
        result.put("412", "Visa Clásica");
        result.put("407", "Visa Oro");
        result.put("402", "Visa Platinum");
        result.put("405", "Visa Platinum");
        result.put("403", "Visa Empresarial");
        result.put("408", "Visa Empresarial");
        result.put("409", "Visa Empresarial");
        result.put("410", "Visa Empresarial");
        result.put("404", "Visa Infinite");
        result.put("404", "Visa Infinite");
        return result;
    }

    public HashMap<String, String> getMapeoEstados() {
        return this.mapeoEstados;
    }
}
