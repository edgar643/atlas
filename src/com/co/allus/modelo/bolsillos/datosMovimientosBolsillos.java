/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bolsillos;

/**
 *
 * @author roberto.ceballos
 */
public class datosMovimientosBolsillos {
 
    private static datosMovimientosBolsillos dataMovimientosBolsillos = new datosMovimientosBolsillos();
    
    private String tipoOperacion = "";
    private String fechaOrigen = "";
    private String descripcionMovimiento = "";
    private String valorTransaccion = "";

    public static datosMovimientosBolsillos getDataMovimientosBolsillos() {
        return dataMovimientosBolsillos;
    }

    public static void setDataMovimientosBolsillos(datosMovimientosBolsillos dataMovimientosBolsillos) {
        datosMovimientosBolsillos.dataMovimientosBolsillos = dataMovimientosBolsillos;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getFechaOrigen() {
        return fechaOrigen;
    }

    public void setFechaOrigen(String fechaOrigen) {
        this.fechaOrigen = fechaOrigen;
    }

    public String getDescripcionMovimiento() {
        return descripcionMovimiento;
    }

    public void setDescripcionMovimiento(String descripcionMovimiento) {
        this.descripcionMovimiento = descripcionMovimiento;
    }

    public String getValorTransaccion() {
        return valorTransaccion;
    }

    public void setValorTransaccion(String valorTransaccion) {
        this.valorTransaccion = valorTransaccion;
    }

}
