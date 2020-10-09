/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.desbsccorp;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTrxFinSCcorp {

    private SimpleStringProperty fecha_desbloqueo;

    public infoTrxFinSCcorp(String fecha_desbloqueoSC) {
        this.fecha_desbloqueo = new SimpleStringProperty(fecha_desbloqueoSC);

    }

    public String getFecha_desbloqueo() {
        return fecha_desbloqueo.get();
    }

    public void setFecha_desbloqueo(String fecha_desbloqueo) {
        this.fecha_desbloqueo.set(fecha_desbloqueo);
    }
}
