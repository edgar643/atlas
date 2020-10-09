/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.ConsultaMovConvDetController;
import com.co.allus.modelo.pagosaterceros.infoMovConv;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author alexander.lopez.o
 */
public class BusdIDnitRecaudador extends Service<ObservableList<infoMovConv>> {

    private String id;

    public BusdIDnitRecaudador(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<infoMovConv>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoMovConv> DatosTabla = FXCollections.observableArrayList();
                if (ConsultaMovConvDetController.registros.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return ConsultaMovConvDetController.registros;

                    } else {

                        synchronized (ConsultaMovConvDetController.registros) {


                            for (Iterator<infoMovConv> it = ConsultaMovConvDetController.registros.iterator(); it.hasNext();) {
                                infoMovConv data = it.next();
                                if (data.getNitpagador().equalsIgnoreCase(id) || data.getNitpagador().startsWith(id)) {
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
