/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.ConsultaArhRecibController;
import com.co.allus.modelo.pagosaterceros.infoArchivosRecibidos;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author alexander.lopez.o
 */
public class BusNumRegArcRecib extends Service<ObservableList<infoArchivosRecibidos>> {

    private String id;

    public BusNumRegArcRecib(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<infoArchivosRecibidos>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoArchivosRecibidos> DatosTabla = FXCollections.observableArrayList();
                if (ConsultaArhRecibController.registros.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return ConsultaArhRecibController.registros;

                    } else {

                        synchronized (ConsultaArhRecibController.registros) {


                            for (Iterator<infoArchivosRecibidos> it = ConsultaArhRecibController.registros.iterator(); it.hasNext();) {
                                infoArchivosRecibidos data = it.next();
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
