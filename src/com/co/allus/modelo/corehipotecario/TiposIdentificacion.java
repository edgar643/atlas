/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.corehipotecario;

import java.util.HashMap;

/**
 *
 * @author alexander.lopez.o
 */
public class TiposIdentificacion {

    public final static String NUIP = "Nuip";
    public final static String CEDULA_CIUDADANIA = "Cedula de Ciudadania";
    public final static String CEDULA_EXTRANJERIA = "Cedula de Extranjeria";
    public final static String NIT = "Nit";
    public final static String TARJETA_IDENTIDAD = "Tarjeta de Identidad";
    public final static String PASAPORTE = "Pasaporte";
    public final static String ID_EXTRANJERO_PN_NR_COL = "Id Extranjero PN No Residente en Colombia";
    public final static String ID_EXTRANJERO_PJ_NR_COL = "Id Extranjero PJ No Residente en Colombia";
    public final static String FIDEICOMISO = "Fideicomiso";
    public final static String REGISTRO_CIVIL = "Registro Civil";
    public static final HashMap<String, String> TiposIdentificacion = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("0", NUIP);
        result.put("1", CEDULA_CIUDADANIA);
        result.put("2", CEDULA_EXTRANJERIA);
        result.put("3", NIT);
        result.put("4", TARJETA_IDENTIDAD);
        result.put("5", PASAPORTE);
        result.put("6", ID_EXTRANJERO_PN_NR_COL);
        result.put("7", ID_EXTRANJERO_PJ_NR_COL);
        result.put("8", FIDEICOMISO);
        result.put("9", REGISTRO_CIVIL);

        return result;
    }

    public HashMap<String, String> getTiposIdentificacion() {
        return this.TiposIdentificacion;
    }
}
