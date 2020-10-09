/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.transfctaprop;

import com.co.allus.modelo.pagosaterceros.*;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class informacionTransfin2 {

    private SimpleStringProperty num_comprobante;
    private SimpleStringProperty valor_transferido;
    private SimpleStringProperty fecha_transf;

    public informacionTransfin2(String numcomprobante, String valortrasnf, String fechatrans) {
        this.num_comprobante = new SimpleStringProperty(numcomprobante);
        this.valor_transferido = new SimpleStringProperty(valortrasnf);

        this.fecha_transf = new SimpleStringProperty(fechatrans);
    }

    public String getNum_comprobante() {
        return num_comprobante.get();
    }

    public void setNum_comprobante(String numcomprobante) {
        this.num_comprobante.set(numcomprobante);
    }

    public String getValor_transferido() {
        return valor_transferido.get();
    }

    public void setValor_transferido(String valortrans) {
        this.valor_transferido.set(valortrans);
    }

    public String getFecha_transf() {
        return fecha_transf.get();
    }

    public void setFecha_transf(String fechatrans) {
        this.fecha_transf.set(fechatrans);
    }
}
