/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.informaciontdc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infotablatdcbuscar {

    private StringProperty Col_tpo;
    private StringProperty Col_hora;
    private StringProperty Col_fecha;
    private StringProperty Col_valor;
    private StringProperty Col_transaccion;
    private StringProperty Col_respuesta;
    private StringProperty Col_mensaje;
    private StringProperty Col_pais;
    private StringProperty Col_entidad;
    private StringProperty Col_origen;

    //(String Col_tpo, String Col_hora, String Col_fecha, String Col_valor, String Col_transaccion, String Col_respuesta, String Col_mensaje, String Col_pais, String Col_entidad, String Col_origen)
    public infotablatdcbuscar(String Col_tpo, String Col_hora, String Col_fecha, String Col_valor, String Col_transaccion, String Col_respuesta, String Col_mensaje, String Col_pais, String Col_entidad, String Col_origen) {
        this.Col_tpo = new SimpleStringProperty(Col_tpo);
        this.Col_hora = new SimpleStringProperty(Col_hora);
        this.Col_fecha = new SimpleStringProperty(Col_fecha);
        this.Col_valor = new SimpleStringProperty(Col_valor);
        this.Col_transaccion = new SimpleStringProperty(Col_transaccion);
        this.Col_respuesta = new SimpleStringProperty(Col_respuesta);
        this.Col_mensaje = new SimpleStringProperty(Col_mensaje);
        this.Col_pais = new SimpleStringProperty(Col_pais);
        this.Col_entidad = new SimpleStringProperty(Col_entidad);
        this.Col_origen = new SimpleStringProperty(Col_origen);
    }

    public String getCol_tpo() {
        return Col_tpo.get();
    }

    public void setCol_tpo(String Col_tpo) {
        this.Col_tpo.set(Col_tpo);
    }

    public String getCol_hora() {
        return Col_hora.get();
    }

    public void setCol_hora(String Col_hora) {
        this.Col_hora.set(Col_hora);
    }

    public String getCol_fecha() {
        return Col_fecha.get();
    }

    public void setCol_fecha(String Col_fecha) {
        this.Col_fecha.set(Col_fecha);
    }

    public String getCol_valor() {
        return Col_valor.get();
    }

    public void setCol_valor(String Col_valor) {
        this.Col_valor.set(Col_valor);
    }

    public String getCol_transaccion() {
        return Col_transaccion.get();
    }

    public void setCol_transaccion(String Col_transaccion) {
        this.Col_transaccion.set(Col_transaccion);
    }

    public String getCol_respuesta() {
        return Col_respuesta.get();
    }

    public void setCol_respuesta(String Col_respuesta) {
        this.Col_respuesta.set(Col_respuesta);
    }

    public String getCol_mensaje() {
        return Col_mensaje.get();
    }

    public void setCol_mensaje(String Col_mensaje) {
        this.Col_mensaje.set(Col_mensaje);
    }

    public String getCol_pais() {
        return Col_pais.get();
    }

    public void setCol_pais(String Col_pais) {
        this.Col_pais.set(Col_pais);
    }

    public String getCol_entidad() {
        return Col_entidad.get();
    }

    public void setCol_entidad(String Col_entidad) {
        this.Col_entidad.set(Col_entidad);
    }

    public String getCol_origen() {
        return Col_origen.get();
    }

    public void setCol_origen(String Col_origen) {
        this.Col_origen.set(Col_origen);
    }
}
