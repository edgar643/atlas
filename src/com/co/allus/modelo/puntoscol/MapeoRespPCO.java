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
public class MapeoRespPCO {

    public static final HashMap<String, String> mapeoRespuestas = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("SUCCESS", "Aprobada");
        result.put("ATRN_IDENTIFIER_WRONG_STATUS", "Estado de indentificación Errado");
        result.put("ATRN_CUSTOMER_WRONG_STATUS", "Estado de Cliente Errado");
        result.put("ATRN_ACCOUNT_WRONG_STATUS", "Estado de Cuenta Puntos No Acumula");
        result.put("ATRN_DUPLICATE_TRANSACTION", "Transacción Duplicada");
        result.put("ATRN_WARN_OWNER_NOT_FOUND", "Aprobada");
        result.put("COMMON_INTERNAL_PROCESSING_ERROR", "Transacción no procesada");
        result.put("COMMON_RECORD_BUSINESS_ERROR", "Transacción no procesada");
        result.put("COMMON_RECORD_MISSING_VALUE", "Transacción no procesada");
        result.put("COMMON_RECORD_INCORRECT_VALUE", "Transacción no procesada");
        result.put("ADJ_CUSTOMER_NOT_FOUND", "Cliente no existe en puntos Colombia");
        result.put("ADJ_IDENTIFIER_WRONG_STATUS", "Estado de identificación errado");
        result.put("ADJ_CUSTOMER_WRONG_STATUS", "Estado del cliente errado");
        result.put("ADJ_ACCOUNT_WRONG_STATUS", "Estado de cuenta puntos no acumula");


        result.put("", "Pendiente Respuesta");

        return result;
    }

    public HashMap<String, String> getMapeoEstados() {
        return this.mapeoRespuestas;
    }
}
