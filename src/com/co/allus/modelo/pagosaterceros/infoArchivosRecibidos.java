/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosaterceros;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoArchivosRecibidos {

    public static infoArchivosRecibidos infoarchrec = new infoArchivosRecibidos();
    private SimpleStringProperty nun_registro;
    private SimpleStringProperty valor_total_arc;
    private SimpleStringProperty fecha_process;
    private SimpleStringProperty num_rastreo;
    private SimpleStringProperty nombre_archivo;

    public infoArchivosRecibidos(String nun_registro,
            String valor_total_arc,
            String fecha_process,
            String num_rastreo,
            String nombre_archivo) {
        this.nun_registro = new SimpleStringProperty(nun_registro);
        this.valor_total_arc = new SimpleStringProperty(valor_total_arc);
        this.fecha_process = new SimpleStringProperty(fecha_process);
        this.num_rastreo = new SimpleStringProperty(num_rastreo);
        this.nombre_archivo = new SimpleStringProperty(nombre_archivo);
    }

    private infoArchivosRecibidos() {
    }

    public static infoArchivosRecibidos getInfoarchenv() {
        return infoarchrec;
    }

    public static void setInfoarchenv(infoArchivosEnviados infoarchenv) {
        infoArchivosEnviados.infoarchenv = infoarchenv;
    }

    public String getNun_registro() {
        return nun_registro.get();
    }

    public void setNun_registro(String nun_registro) {
        this.nun_registro.set(nun_registro);
    }

    public String getValor_total_arc() {
        return valor_total_arc.get();
    }

    public void setValor_total_arc(String valor_total_arc) {
        this.valor_total_arc.set(valor_total_arc);
    }

    public String getFecha_process() {
        return fecha_process.get();
    }

    public void setFecha_process(String fecha_process) {
        this.fecha_process.set(fecha_process);
    }

    public String getNum_rastreo() {
        return num_rastreo.get();
    }

    public void setNum_rastreo(String num_rastreo) {
        this.num_rastreo.set(num_rastreo);
    }

    public String getNombre_archivo() {
        return nombre_archivo.get();
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo.set(nombre_archivo);
    }
    public static final String valor = "Valor";
    public static final String num_registros = "Numero de Registro";
    public static final String fecha_apli = "Fecha de aplicación";
    public static final String fecha_reci = "Fecha recibido";
    public static final String secuencia_arc = "Secuencia del Archivo";
    public static final String estado = "Estado";
    public static final String respuesta = "Respuesta";
    public static final String medio_envio = "Medio de Envio";
}
