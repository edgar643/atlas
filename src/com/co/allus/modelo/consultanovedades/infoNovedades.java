/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultanovedades;

/**
 *
 * @author alexander.lopez.o
 */
public class infoNovedades {

    private static infoNovedades novedades = new infoNovedades();
    private String fechainicial = "";
    private String fechaFinal = "";
    private String novedad = "";

    public static infoNovedades getNovedades() {
        return novedades;
    }

    public static void setNovedades(infoNovedades novedades) {
        infoNovedades.novedades = novedades;
    }

    public String getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(String fechainicial) {
        this.fechainicial = fechainicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public static infoNovedades getInfoNovedades() {
        return novedades;
    }

    public static void setInfoNovedades(infoNovedades infonov) {
        infoNovedades.novedades = infonov;
    }
}
