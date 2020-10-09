/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladores.socket;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import java.sql.Time;
import java.util.Date;
import javafx.application.Application;

/**
 *
 * @author alexander.lopez.o
 */
public class ProcesLaunch implements Runnable {

    private transient final GestorDocumental DOCUMENTOS = new GestorDocumental();

    @Override
    public void run() {

        try {
            Application.launch(Atlas.class, (java.lang.String[]) null);
        } catch (Exception ex) {
            DOCUMENTOS.imprimir("error Subiendo Hilo FX:" + ex.toString() + " " + obtenerHoraActual());
            System.exit(0);
        }

    }

    private String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
