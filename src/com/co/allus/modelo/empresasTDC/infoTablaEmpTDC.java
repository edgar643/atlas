/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.empresasTDC;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stephania.rojas
 */
public class infoTablaEmpTDC {

    private StringProperty col_Numero;
    private StringProperty col_TipoTarj;
    private StringProperty col_Bloqueo;

    public infoTablaEmpTDC(String col_Numero, String col_TipoTarj, String col_Bloqueo) {

        this.col_Numero = new SimpleStringProperty(col_Numero);
        this.col_TipoTarj = new SimpleStringProperty(col_TipoTarj);
        this.col_Bloqueo = new SimpleStringProperty(col_Bloqueo);

    }

    public String getCol_Numero() {
        return col_Numero.get();
    }

    public String getCol_TipoTarj() {
        return col_TipoTarj.get();
    }

    public String getCol_Bloqueo() {
        return col_Bloqueo.get();
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
