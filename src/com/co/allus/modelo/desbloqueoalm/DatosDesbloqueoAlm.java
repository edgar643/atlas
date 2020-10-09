/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.desbloqueoalm;

/**
 *
 * @author roberto.ceballos
 */
public class DatosDesbloqueoAlm {

    private static DatosDesbloqueoAlm datosDesbloqueoAlm = new DatosDesbloqueoAlm();
    private String estado_canal = "";
    private String fecha_novedad = "";
    private String marca_novedad = "";
    private String modelo_novedad = "";
    private String marca_registrado = "";
    private String modelo_registrado = "";
    private String numero_cuenta = "";

   public static DatosDesbloqueoAlm getDatosDesbloqueoAlm() {
        return datosDesbloqueoAlm;
    }

    public static void setDesbloqueoAlm(DatosDesbloqueoAlm datosDesbloqueoAlm) {
        DatosDesbloqueoAlm.datosDesbloqueoAlm = datosDesbloqueoAlm;
    }

    public String getEstado_canal() {
        return estado_canal;
    }

    public void setEstado_canal(String estado_canal) {
        this.estado_canal = estado_canal;
    }

    public String getFecha_novedad() {
        return fecha_novedad;
    }

    public void setFecha_novedad(String fecha_novedad) {
        this.fecha_novedad = fecha_novedad;
    }

    public String getMarca_novedad() {
        return marca_novedad;
    }

    public void setMarca_novedad(String marca_novedad) {
        this.marca_novedad = marca_novedad;
    }

    public String getModelo_novedad() {
        return modelo_novedad;
    }

    public void setModelo_novedad(String modelo_novedad) {
        this.modelo_novedad = modelo_novedad;
    }

    public String getMarca_registrado() {
        return marca_registrado;
    }

    public void setMarca_registrado(String marca_registrado) {
        this.marca_registrado = marca_registrado;
    }

    public String getModelo_registrado() {
        return modelo_registrado;
    }

    public void setModelo_registrado(String modelo_registrado) {
        this.modelo_registrado = modelo_registrado;
    }
    
    public String getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(String numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    public static final String ESTADO_CANAL = "Estado cliente";
    public static final String FECHA_NOVEDAD = "Fecha última novedad";
    public static final String MARCA_NOVEDAD = "Marca dispositivo intento";
    public static final String MODELO_NOVEDAD = "Modelo dispositivo intento";
    public static final String MARCA_REGISTRADO = "Marca dispositivo registrado";
    public static final String MODELO_REGISTRADO = "Modelo dispositivo registrado";
    public static final String NUMERO_CUENTA = "Numero de Cuenta";
    
}
