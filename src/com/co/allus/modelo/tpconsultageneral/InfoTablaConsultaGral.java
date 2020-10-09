/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.tpconsultageneral;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class InfoTablaConsultaGral {

    private StringProperty col_Numero;
    private StringProperty col_TipoTarj;
    private StringProperty col_Bloqueo;
    private StringProperty col_Empresa;
    private StringProperty valorsinfor;

    public InfoTablaConsultaGral(String col_Numero, String col_TipoTarj, String col_Bloqueo, String col_Empresa, String valorsinfor) {

        this.col_Numero = new SimpleStringProperty(col_Numero);
        this.col_TipoTarj = new SimpleStringProperty(col_TipoTarj);
        this.col_Bloqueo = new SimpleStringProperty(col_Bloqueo);
        this.col_Empresa = new SimpleStringProperty(col_Empresa);
        this.valorsinfor = new SimpleStringProperty(valorsinfor);

    }

    public StringProperty col_NumeroProperty() {
        return col_Numero;
    }

    public StringProperty col_TipoTarjProperty() {
        return col_TipoTarj;
    }

    public StringProperty col_BloqueoProperty() {
        return col_Bloqueo;
    }

    public StringProperty col_EmpresaProperty() {
        return col_Empresa;
    }

    public StringProperty valorsinforProperty() {
        return valorsinfor;
    }

    public void setCol_Numero(String col_Numero) {
        this.col_Numero.set(col_Numero);
    }

    public void setCol_TipoTarj(String col_TipoTarj) {
        this.col_TipoTarj.set(col_TipoTarj);
    }

    public void setCol_Bloqueo(String col_Bloqueo) {
        this.col_Bloqueo.set(col_Bloqueo);
    }

    public void setCol_Empresa(String col_Empresa) {
        this.col_Empresa.set(col_Empresa);
    }

    public void setValorsinfor(String valorsinfor) {
        this.valorsinfor.set(valorsinfor);
    }
}
