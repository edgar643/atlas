/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tokenempresas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoTablaDetalle {

    private SimpleStringProperty id_usuario;
    private SimpleStringProperty nombre_usuario;
    private SimpleStringProperty estado_novedad;
    private SimpleStringProperty novedad;
    private SimpleStringProperty costo_novedad;
    private SimpleStringProperty fecha_novedad;
    private SimpleStringProperty hora_novedad;
    private SimpleStringProperty canal;

    public infoTablaDetalle(String id_usuario, String nombre_usuario, String estado_novedad, String novedad, String costo_novedad, String fecha_novedad, String hora_novedad, String canal) {

        this.id_usuario = new SimpleStringProperty(id_usuario);
        this.nombre_usuario = new SimpleStringProperty(nombre_usuario);
        this.estado_novedad = new SimpleStringProperty(estado_novedad);
        this.novedad = new SimpleStringProperty(novedad);
        this.costo_novedad = new SimpleStringProperty(costo_novedad);
        this.fecha_novedad = new SimpleStringProperty(fecha_novedad);
        this.hora_novedad = new SimpleStringProperty(hora_novedad);
        this.canal = new SimpleStringProperty(canal);

    }

    public StringProperty id_usuarioProperty() {
        return id_usuario;
    }

    public StringProperty nombre_usuarioProperty() {
        return nombre_usuario;
    }

    public StringProperty estado_novedadProperty() {
        return estado_novedad;
    }

    public StringProperty novedadProperty() {
        return novedad;
    }

    public StringProperty costo_novedadProperty() {
        return costo_novedad;
    }

    public StringProperty fecha_novedadProperty() {
        return fecha_novedad;
    }

    public StringProperty hora_novedadProperty() {
        return hora_novedad;
    }

    public StringProperty canalProperty() {
        return canal;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario.set(id_usuario);
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario.set(nombre_usuario);
    }

    public void setEstado_novedad(String estado_novedad) {
        this.estado_novedad.set(estado_novedad);
    }

    public void setNovedad(String novedad) {
        this.novedad.set(novedad);
    }

    public void setCosto_novedad(String costo_novedad) {
        this.costo_novedad.set(costo_novedad);
    }

    public void setFecha_novedad(String fecha_novedad) {
        this.fecha_novedad.set(fecha_novedad);
    }

    public void setHora_novedad(String hora_novedad) {
        this.hora_novedad.set(hora_novedad);
    }

    public void setCanal(String canal) {
        this.canal.set(canal);
    }
    //    public String getId_usuario() {
//        return id_usuario.get();
//    }  
//    public String getNombre_usuario() {
//        return nombre_usuario.get();
//    }    
    //    public String getEstado_novedad() {
//        return estado_novedad.get();
//    }   
    //    public String getNovedad() {
//        return novedad.get();
//    }
    //    public String getCosto_novedad() {
//        return costo_novedad.get();
//    }
//    public String getCanal() {
////        return canal.get();
////    }
//    
    //    public String getHora_novedad() {
//        return hora_novedad.get();
//    }  
    //    public String getFecha_novedad() {
//        return fecha_novedad.get();
//    } 
//    public void setNovedad(String novedad) {
//        this.novedad.set(novedad);
//    }
//    public String getEstado_novedad() {
//        return estado_novedad.get();
//    }
//
//    public void setEstado_novedad(String estado_novedad) {
//        this.estado_novedad.set(estado_novedad);
//    }
//    public String getCosto_novedad() {
//        return costo_novedad.get();
//    }
//
//    public void setCosto_novedad(String costo_novedad) {
//        this.costo_novedad.set(costo_novedad);
//    }
//    public String getFecha_novedad() {
//        return fecha_novedad.get();
//    }
//
//    public void setFecha_novedad(String fecha_novedad) {
//        this.fecha_novedad.set(fecha_novedad);
//    }
//    public String getHora_novedad() {
//        return hora_novedad.get();
//    }
//
//    public void setHora_novedad(String hora_novedad) {
//        this.hora_novedad.set(hora_novedad);
//    }
//    public String getCanal() {
//        return canal.get();
//    }
//
//    public void setCanal(String canal) {
//        this.canal.set(canal);
//    }
}
