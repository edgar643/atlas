/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultanovedades;

import java.util.HashMap;

/**
 *
 * @author alexander.lopez.o
 */
public class CodigosNovedades {

    public static final HashMap<String, String> codigosProd = createMap();

    private static HashMap<String, String> createMap() {
        final HashMap<String, String> result = new HashMap<String, String>();

        result.put("0001", "0001 Inscripción");
        result.put("0002", "0002 Bloqueo intentos fallidos");
        result.put("0003", "0003 Bloqueo voluntario");
        result.put("0004", "0004 Bloqueo seguridad");
        result.put("0005", "0005 Desbloqueo seguridad");

        //        (Enrolamiento OTP - ODA: 0001, Bloqueo por intentos fallidos: 0002, Bloqueo voluntario: 0003, Bloqueo seguridad: 0004) 


//        result.put("0010", "Enrolamiento");
//        result.put("0011", "Desenrolar");
//        result.put("0012", "Bloqueo Voluntario");
//        result.put("0013", "Desbloqueo");
//        result.put("0014", "Cambio de Mecanismo");
//        result.put("0015", "Actualización de Datos");
//        result.put("0016", "Bloqueo de Seguridad");
//        result.put("0017", "Desbloqueo de Seguridad");





        return result;
    }

    public HashMap<String, String> getCodigosProd() {
        return this.codigosProd;
    }
}
