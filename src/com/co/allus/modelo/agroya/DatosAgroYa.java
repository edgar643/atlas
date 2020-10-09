/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.agroya;

/**
 *
 * @author roberto.ceballos
 */
public class DatosAgroYa {
    
    private static DatosAgroYa datosAgroYa = new DatosAgroYa();
    private String cupo_asignado = "";
    private String cupo_utilizado = "";
    private String cupo_disponible = "";
    private String valor_canje = "";

    public static DatosAgroYa getDatosAgroYa() {
        return datosAgroYa;
    }

    public static void setDatosAgroYa(DatosAgroYa datosAgroYa) {
        DatosAgroYa.datosAgroYa = datosAgroYa;
    }

    public String getCupo_asignado() {
        return cupo_asignado;
    }

    public void setCupo_asignado(String cupo_asignado) {
        this.cupo_asignado = cupo_asignado;
    }

    public String getCupo_utilizado() {
        return cupo_utilizado;
    }

    public void setCupo_utilizado(String cupo_utilizado) {
        this.cupo_utilizado = cupo_utilizado;
    }

    public String getCupo_disponible() {
        return cupo_disponible;
    }

    public void setCupo_disponible(String cupo_disponible) {
        this.cupo_disponible = cupo_disponible;
    }

    public String getValor_canje() {
        return valor_canje;
    }

    public void setValor_canje(String valor_canje) {
        this.valor_canje = valor_canje;
    }
    
    public static final String CUPO_ASIGNADO = "Cupo asignado";
    public static final String CUPO_UTILIZADO = "Cupo utilizado";
    public static final String CUPO_DISPONIBLE = "Cupo disponible";
    public static final String VALOR_CANJE = "Valor en canje";

}
