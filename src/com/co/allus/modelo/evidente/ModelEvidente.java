/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.evidente;

import java.util.HashMap;

/**
 *
 * @author luis.cuervo
 */
public class ModelEvidente {
    // PARA TODAS LAS TRANSACCIONES

    private static String tipoIdentificacion; // SEGUN TIPO DE DOCUMENTO
    // RETONA DE VALIDACION 
    private static String regValidacion;
    private static String resPorcesoValidacion;
    // RETORNA CONSULTA CUESTIONARIO 
    private static String regCuestionario;
    private static String idCuestionario;
    // Key es la pregunta, en la lista pongo en la primera posicion la pregunta y las demas las respuestas
    private static String PregAndResp;
    private static boolean isHappy = false;

    public static boolean isIsHappy() {
        return isHappy;
    }

    public void setPregAndResp(final String PregyResp) {
        PregAndResp = PregyResp;
    }

    public static String getPregAndResp() {
        return PregAndResp;
    }
    // Key es la pregunta, en la lista pongo las respuestas seleccionadas
    private static HashMap<String, String> RespSelect = new HashMap<String, String>();

    public void setRespSelect(final HashMap<String, String> Has) {
        this.RespSelect.clear();
        this.RespSelect.putAll(Has);
    }

    public static HashMap<String, String> getRespSelect() {
        return RespSelect;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public String getRegValidacion() {
        return regValidacion;
    }

    public String getResPorcesoValidacion() {
        return resPorcesoValidacion;
    }

    public String getRegCuestionario() {
        return regCuestionario;
    }

    public String getIdCuestionario() {
        return idCuestionario;
    }

    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void setRegValidacion(final String regValidacion) {
        this.regValidacion = regValidacion;
    }

    public void setResPorcesoValidacion(final String resPorcesoValidacion) {
        this.resPorcesoValidacion = resPorcesoValidacion;
    }

    public void setRegCuestionario(final String regCuestionario) {
        this.regCuestionario = regCuestionario;
    }

    public void setIdCuestionario(final String idCuestionario) {
        this.idCuestionario = idCuestionario;
    }
}
