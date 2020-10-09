/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

/**
 *
 * @author stephania.rojas
 */
public class InfoTokentToAEgs {

    private static InfoTokentToAEgs InfoTokentToAEgs = new InfoTokentToAEgs();
    //nombreCompañia,accesoEmerPermitidos,AccesoEmerUsados,AccesoEmerDispo,periodoAccesoEmer,CantidadRegistros
    private String nombre_compania = "";
    private String ae_permitidos = "";
    private String ae_usados = "";
    private String ae_disponibles = "";
    private String periodo_ae = "";
    private String cantidad_registros = "";

    public String getNombre_compania() {
        return nombre_compania;
    }

    public void setNombre_compania(String nombre_compania) {
        this.nombre_compania = nombre_compania;
    }

    public String getAe_permitidos() {
        return ae_permitidos;
    }

    public void setAe_permitidos(String ae_permitidos) {
        this.ae_permitidos = ae_permitidos;
    }

    public String getAe_usados() {
        return ae_usados;
    }

    public void setAe_usados(String ae_usados) {
        this.ae_usados = ae_usados;
    }

    public String getAe_disponibles() {
        return ae_disponibles;
    }

    public void setAe_disponibles(String ae_disponibles) {
        this.ae_disponibles = ae_disponibles;
    }

    public String getPeriodo_ae() {
        return periodo_ae;
    }

    public void setPeriodo_ae(String periodo_ae) {
        this.periodo_ae = periodo_ae;
    }

    public String getCantidad_registros() {
        return cantidad_registros;
    }

    public void setCantidad_registros(String cantidad_registros) {
        this.cantidad_registros = cantidad_registros;
    }

    public static InfoTokentToAEgs getInfotokentoaegs() {
        return InfoTokentToAEgs;
    }

    public static void setInfotokentoaegs(InfoTokentToAEgs InfoTokentToAEgs) {
        InfoTokentToAEgs.InfoTokentToAEgs = InfoTokentToAEgs;
    }
}
