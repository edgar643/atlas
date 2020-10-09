/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultanovedades;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTablaNovedades {

    private SimpleStringProperty estado_serv;
    private SimpleStringProperty tipo_mecanismo;
    private SimpleStringProperty fecha_nov;
    private SimpleStringProperty hora_nov;
    private SimpleStringProperty canal_nov;

    public infoTablaNovedades(String estado_servicio, String tipo_mec, String fecha, String hora, String canal) {
        this.estado_serv = new SimpleStringProperty(estado_servicio);
        this.tipo_mecanismo = new SimpleStringProperty(tipo_mec);
        this.fecha_nov = new SimpleStringProperty(fecha);
        this.hora_nov = new SimpleStringProperty(hora);
        this.canal_nov = new SimpleStringProperty(canal);

    }

    public String getEstado_serv() {
        return estado_serv.get();
    }

    public void setEstado_serv(String estado_serv) {
        this.estado_serv.set(estado_serv);
    }

    public String getTipo_mecanismo() {
        return tipo_mecanismo.get();
    }

    public void setTipo_mecanismo(String tipo_mecanismo) {
        this.tipo_mecanismo.set(tipo_mecanismo);
    }

    public String getFecha_nov() {
        return fecha_nov.get();
    }

    public void setFecha_nov(String fecha_nov) {
        this.fecha_nov.set(fecha_nov);
    }

    public String getHora_nov() {
        return hora_nov.get();
    }

    public void setHora_nov(String hora_nov) {
        this.hora_nov.set(hora_nov);
    }

    public String getCanal_nov() {
        return canal_nov.get();
    }

    public void setCanal_nov(String canal_nov) {
        this.canal_nov.set(canal_nov);
    }
}
