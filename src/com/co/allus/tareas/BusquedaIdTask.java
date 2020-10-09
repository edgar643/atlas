/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.TokenAccesoEmergenciaController;
import com.co.allus.modelo.tokenempresas.infoAccesoEmergencia;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author stephania.rojas
 */
public class BusquedaIdTask extends Service<ObservableList<infoAccesoEmergencia>> {

    private String id;

    public BusquedaIdTask(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<infoAccesoEmergencia>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoAccesoEmergencia> DatosTablaAE = FXCollections.observableArrayList();
                if (TokenAccesoEmergenciaController.tokenAE.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return TokenAccesoEmergenciaController.tokenAE;

                    } else {

                        synchronized (TokenAccesoEmergenciaController.tokenAE) {


                            for (Iterator<infoAccesoEmergencia> it = TokenAccesoEmergenciaController.tokenAE.iterator(); it.hasNext();) {
                                final infoAccesoEmergencia data = it.next();
                                if (data.getColID_usuario().equalsIgnoreCase(id) || data.getColID_usuario().startsWith(id)) {
                                    DatosTablaAE.add(data);

                                }
                            }
                        }
                    }

                    if (DatosTablaAE.isEmpty()) {
                        throw new Exception("No hay Datos");
                    } else {
                        return DatosTablaAE;
                    }
                }

            }
        };
    }
}