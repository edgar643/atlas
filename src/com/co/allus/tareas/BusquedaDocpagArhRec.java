/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.ConsultaArhRecibDetController;
import com.co.allus.controladoresfxml.ConsultaMovConvDetController;
import com.co.allus.modelo.pagosaterceros.infoArchivoDetalle;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author alexander.lopez.o
 */
public class BusquedaDocpagArhRec extends Service<ObservableList<infoArchivoDetalle>> {

    private String id;

    public BusquedaDocpagArhRec(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<infoArchivoDetalle>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoArchivoDetalle> DatosTabla = FXCollections.observableArrayList();
                if (ConsultaArhRecibDetController.registros.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return ConsultaArhRecibDetController.registros;

                    } else {

                        synchronized (ConsultaArhRecibDetController.registros) {


                            for (Iterator<infoArchivoDetalle> it = ConsultaArhRecibDetController.registros.iterator(); it.hasNext();) {
                                infoArchivoDetalle data = it.next();
                                if (data.getNitentidadpag().equalsIgnoreCase(id) || data.getNitentidadpag().startsWith(id)) {
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
