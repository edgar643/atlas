/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.serviciosad;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTablaBarridoCtas {

    private SimpleStringProperty id_cliente;
    private SimpleStringProperty nombre_cte;
    private SimpleStringProperty num_tarjeta;
    private SimpleStringProperty estado_act_tarjeta;
    private SimpleStringProperty num_cta;
    private SimpleStringProperty tipo_cta;
    private SimpleStringProperty est_depos;
    private SimpleStringProperty fecha_hora;
    private SimpleStringProperty depur_reasig;

    public infoTablaBarridoCtas(String id_cliente, String nombre_cte, String num_tarjeta, String estado_act_tarjeta, String num_cta, String tipo_cta, String est_depos, String fecha_hora, String depur_reasig) {
        this.id_cliente = new SimpleStringProperty(id_cliente);
        this.nombre_cte = new SimpleStringProperty(nombre_cte);
        this.num_tarjeta = new SimpleStringProperty(num_tarjeta);
        this.estado_act_tarjeta = new SimpleStringProperty(estado_act_tarjeta);
        this.num_cta = new SimpleStringProperty(num_cta);
        this.est_depos = new SimpleStringProperty(est_depos);
        this.fecha_hora = new SimpleStringProperty(fecha_hora);
        this.depur_reasig = new SimpleStringProperty(depur_reasig);
        this.tipo_cta = new SimpleStringProperty(tipo_cta);
    }

    public String getId_cliente() {
        return id_cliente.get();
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente.set(id_cliente);
    }

    public String getNombre_cte() {
        return nombre_cte.get();
    }

    public void setNombre_cte(String nombre_cte) {
        this.nombre_cte.set(nombre_cte);
    }

    public String getNum_tarjeta() {
        return num_tarjeta.get();
    }

    public void setNum_tarjeta(String num_tarjeta) {
        this.num_tarjeta.set(num_tarjeta);
    }

    public String getEstado_act_tarjeta() {
        return estado_act_tarjeta.get();
    }

    public void setEstado_act_tarjeta(String estado_act_tarjeta) {
        this.estado_act_tarjeta.set(estado_act_tarjeta);
    }

    public String getNum_cta() {
        return num_cta.get();
    }

    public void setNum_cta(String num_cta) {
        this.num_cta.set(num_cta);
    }

    public String getEst_depos() {
        return est_depos.get();
    }

    public void setEst_depos(String est_depos) {
        this.est_depos.set(est_depos);
    }

    public String getFecha_hora() {
        return fecha_hora.get();
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora.set(fecha_hora);
    }

    public String getDepur_reasig() {
        return depur_reasig.get();
    }

    public void setDepur_reasig(String depur_reasig) {
        this.depur_reasig.set(depur_reasig);
    }

    public String getTipo_cta() {
        return tipo_cta.get();
    }

    public void setTipo_cta(String tipo_cta) {
        this.tipo_cta.set(tipo_cta);
    }
}
