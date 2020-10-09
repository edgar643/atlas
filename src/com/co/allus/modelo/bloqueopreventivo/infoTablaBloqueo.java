/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.bloqueopreventivo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoTablaBloqueo {

    private StringProperty col_Numero;
    private StringProperty col_TipoTarj;
    private StringProperty col_Bloqueo;

    public infoTablaBloqueo(String col_Numero, String col_TipoTarj, String col_Bloqueo) {

        this.col_Numero = new SimpleStringProperty(col_Numero);
        this.col_TipoTarj = new SimpleStringProperty(col_TipoTarj);
        this.col_Bloqueo = new SimpleStringProperty(col_Bloqueo);

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

    public void setCol_Numero(String col_Numero) {
        this.col_Numero.set(col_Numero);
    }

    public void setCol_TipoTarj(String col_TipoTarj) {
        this.col_TipoTarj.set(col_TipoTarj);
    }

    public void setCol_Bloqueo(String col_Bloqueo) {
        this.col_Bloqueo.set(col_Bloqueo);
    }
}
