/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.ConsultaArhEnvController;
import com.co.allus.modelo.pagosaterceros.infoArchivosEnviados;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author alexander.lopez.o
 */
public class BusNumRegArcEnv extends Service<ObservableList<infoArchivosEnviados>> {

    private String id;

    public BusNumRegArcEnv(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<infoArchivosEnviados>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoArchivosEnviados> DatosTabla = FXCollections.observableArrayList();
                if (ConsultaArhEnvController.registros.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return ConsultaArhEnvController.registros;

                    } else {

                        synchronized (ConsultaArhEnvController.registros) {


                            for (Iterator<infoArchivosEnviados> it = ConsultaArhEnvController.registros.iterator(); it.hasNext();) {
                                infoArchivosEnviados data = it.next();
                                if (data.getNun_registro().equalsIgnoreCase(id) || data.getNun_registro().startsWith(id)) {
                                    DatosTabla.add(data);

                                }
                            }
                        }
                    }

                    if (DatosTabla.isEmpty()) {
                        throw new Exception("No hay Datos");
                    } else {
                        return DatosTabla;
                    }
                }

            }
        };
    }
}
