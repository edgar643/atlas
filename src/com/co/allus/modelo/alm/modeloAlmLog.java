/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.alm;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author roberto.ceballos
 */
public class modeloAlmLog {
    
    private SimpleStringProperty numeroRegistro;
    private SimpleStringProperty numeroIdentificación;
    private SimpleStringProperty numeroCelular;
    private SimpleStringProperty fechaVinculación;
    private SimpleStringProperty descripcion;
    private SimpleStringProperty horaMensaje;
    private SimpleStringProperty fechaNacimiento;
    private SimpleStringProperty fechaRedeban;
    private SimpleStringProperty fechaCifin;

    public modeloAlmLog(String numeroRegistro, String numeroIdentificación, String numeroCelular, String fechaVinculación,
                        String descripcion, String horaMensaje, String fechaNacimiento, String fechaRedeban, String fechaCifin) {
        this.numeroRegistro = new SimpleStringProperty(numeroRegistro);
        this.numeroIdentificación = new SimpleStringProperty(numeroIdentificación);
        this.numeroCelular = new SimpleStringProperty(numeroCelular);
        this.fechaVinculación = new SimpleStringProperty(fechaVinculación);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.horaMensaje = new SimpleStringProperty(horaMensaje);
        this.fechaNacimiento = new SimpleStringProperty(fechaNacimiento);
        this.fechaRedeban = new SimpleStringProperty(fechaRedeban);
        this.fechaCifin = new SimpleStringProperty(fechaCifin);
    }

    public String getNumeroRegistro() {
        return numeroRegistro.get();
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro.set(numeroRegistro);
    }

    public String getNumeroIdentificación() {
        return numeroIdentificación.get();
    }

    public void setNumeroIdentificación(String numeroIdentificación) {
        this.numeroIdentificación.set(numeroIdentificación);
    }

    public String getNumeroCelular() {
        return numeroCelular.get();
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular.set(numeroCelular);
    }

    public String getFechaVinculación() {
        return fechaVinculación.get();
    }

    public void setFechaVinculación(String fechaVinculación) {
        this.fechaVinculación.set(fechaVinculación);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getHoraMensaje() {
        return horaMensaje.get();
    }

    public void setHoraMensaje(String horaMensaje) {
        this.horaMensaje.set(horaMensaje);
    }

    public String getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }

    public String getFechaRedeban() {
        return fechaRedeban.get();
    }

    public void setFechaRedeban(String fechaRedeban) {
        this.fechaRedeban.set(fechaRedeban);
    }

    public String getFechaCifin() {
        return fechaCifin.get();
    }

    public void setFechaCifin(String fechaCifin) {
        this.fechaCifin.set(fechaCifin);
    }

}
