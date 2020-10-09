/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosaterceros;

import java.util.HashMap;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class ModelConvenios {

    private SimpleIntegerProperty codigo_conv;
    private SimpleStringProperty nombre_conv;
    private SimpleIntegerProperty nit;
    private SimpleStringProperty categoria;
    private SimpleStringProperty valida_banco;
    public static HashMap<String, String> codigosRef;

    public ModelConvenios(int codigoconv, String nombreconv, int nit, String categoria, String valBanco) {

        this.codigo_conv = new SimpleIntegerProperty(codigoconv);
        this.nombre_conv = new SimpleStringProperty(nombreconv);
        this.nit = new SimpleIntegerProperty(nit);
        this.categoria = new SimpleStringProperty(categoria);
        this.valida_banco = new SimpleStringProperty(valBanco);


    }

    public int getCodigo_conv() {
        return codigo_conv.get();
    }

    public void setCodigo_conv(int codigoconv) {
        codigo_conv.set(codigoconv);
    }

    public String getNombre_conv() {
        return nombre_conv.get();
    }

    public void setNombre_conv(String nombreconv) {
        nombre_conv.set(nombreconv);
    }

    public int getNit() {
        return nit.get();
    }

    public void setNit(int nitconv) {
        nit.set(nitconv);
    }

    public String getCategoria() {
        return categoria.get();
    }

    public void setCategoria(String categoriaconv) {
        categoria.set(categoriaconv);
    }

    public String getValida_banco() {
        return valida_banco.get();
    }

    public void setValida_banco(String val_banco) {
        valida_banco.set(val_banco);
    }

//    public String getReferencia() {
//        return referencia.get();
//    }
//
//    public void setReferencia(String referenciaconv) {
//       referencia.set(referenciaconv);
//    }
    public static void AddMapdata(final List<String> codigos_ref) {
        codigosRef = new HashMap<String, String>();

        for (int i = 0; i < codigos_ref.size(); i++) {
            if (codigos_ref.get(i).split(",").length > 1) {
                codigosRef.put(codigos_ref.get(i).split(",")[0], codigos_ref.get(i).split(",")[1]);
            } else {
                codigosRef.put(codigos_ref.get(i).split(",")[0], "");
            }

        }

    }
}
