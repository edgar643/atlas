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
public class infoArchivoEstado {
    
    private SimpleStringProperty fecha_envio;
    private SimpleStringProperty numero_registros;
    private SimpleStringProperty valor_total;

    public infoArchivoEstado(String fecha_envio, String numero_registros, String valor_total) {
        this.fecha_envio = new SimpleStringProperty(fecha_envio);
        this.numero_registros = new SimpleStringProperty(numero_registros);
        this.valor_total = new SimpleStringProperty(valor_total);
    }
    
    

    public String getFecha_envio() {
        return fecha_envio.get();
    }

    public void setFecha_envio(String fecha_envio) {
        this.fecha_envio.set(fecha_envio);
    }

    public String getNumero_registros() {
        return numero_registros.get();
    }

    public void setNumero_registros(String numero_registros) {
        this.numero_registros.set(numero_registros);
    }

    public String getValor_total() {
        return valor_total.get();
    }

    public void setValor_total(String valor_total) {
        this.valor_total.set(valor_total);
    }
    
    
    
}
