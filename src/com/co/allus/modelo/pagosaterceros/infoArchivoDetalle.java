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
public class infoArchivoDetalle {
    
    private SimpleStringProperty idregistro;
    private SimpleStringProperty codigorespuesta;
    private SimpleStringProperty descripcion;
    private SimpleStringProperty nitentidadpag;
    private SimpleStringProperty nomentidadpag;
    private SimpleStringProperty ref1;
    private SimpleStringProperty ref2;
    private SimpleStringProperty numctapagador;
    private SimpleStringProperty descripcionctaPagador;
    private SimpleStringProperty valordetransaccion;
    private SimpleStringProperty nombanco;
    private SimpleStringProperty fechaprocesacion;
    private SimpleStringProperty valordebitado;
    
    

    public infoArchivoDetalle(String idregistro, 
            String codigorespuesta, 
            String descripcion, 
            String nitentidadpag, 
            String nomentidadpag, 
            String ref1, 
            String ref2,
            String numctapagador, 
            String descripcionctaPagador, 
            String valordetransaccion, 
            String nombanco, 
            String fechaprocesacion, 
            String valordebitado) {
        this.idregistro = new SimpleStringProperty(idregistro);
        this.codigorespuesta = new SimpleStringProperty(codigorespuesta);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.nitentidadpag = new SimpleStringProperty(nitentidadpag);
        this.nomentidadpag = new SimpleStringProperty(nomentidadpag);
        this.ref1 = new SimpleStringProperty(ref1);
        this.ref2 = new SimpleStringProperty(ref2);
        this.numctapagador = new SimpleStringProperty(numctapagador);
        this.descripcionctaPagador = new SimpleStringProperty(descripcionctaPagador);
        this.valordetransaccion = new SimpleStringProperty(valordetransaccion);
        this.nombanco = new SimpleStringProperty(nombanco);
        this.fechaprocesacion = new SimpleStringProperty(fechaprocesacion);
        this.valordebitado = new SimpleStringProperty(valordebitado);
    }
    
 

    public String getIdregistro() {
        return idregistro.get();
    }

    public void setIdregistro(String idregistro) {
        this.idregistro.set(idregistro);
    }

    public String getCodigorespuesta() {
        return codigorespuesta.get();
    }

    public void setCodigorespuesta(String codigorespuesta) {
        this.codigorespuesta.set(codigorespuesta);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getNitentidadpag() {
        return nitentidadpag.get();
    }

    public void setNitentidadpag(String nitentidadpag) {
        this.nitentidadpag.set(nitentidadpag);
    }

    public String getNomentidadpag() {
        return nomentidadpag.get();
    }

    public void setNomentidadpag(String nomentidadpag) {
        this.nomentidadpag.set(nomentidadpag);
    }

    public String getRef1() {
        return ref1.get();
    }

    public void setRef1(String ref1) {
        this.ref1.set(ref1);
    }

    public String getRef2() {
        return ref2.get();
    }

    public void setRef2(String ref2) {
        this.ref2.set(ref2);
    }

    public String getNumctapagador() {
        return numctapagador.get();
    }

    public void setNumctapagador(String numctapagador) {
        this.numctapagador.set(numctapagador);
    }

    public String getDescripcionctaPagador() {
        return descripcionctaPagador.get();
    }

    public void setDescripcionctaPagador(String descripcionctaPagador) {
        this.descripcionctaPagador.set(descripcionctaPagador);
    }

    public String getValordetransaccion() {
        return valordetransaccion.get();
    }

    public void setValordetransaccion(String valordetransaccion) {
        this.valordetransaccion.set(valordetransaccion);
    }

    public String getNombanco() {
        return nombanco.get();
    }

    public void setNombanco(String nombanco) {
        this.nombanco.set(nombanco);
    }

    public String getFechaprocesacion() {
        return fechaprocesacion.get();
    }

    public void setFechaprocesacion(String fechaprocesacion) {
        this.fechaprocesacion.set(fechaprocesacion);
    }

    public String getValordebitado() {
        return valordebitado.get();
    }

    public void setValordebitado(String valordebitado) {
        this.valordebitado.set(valordebitado);
    }
    
    
    

    
    
}
