/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.ConsultaMovConvDetController;
import com.co.allus.controladoresfxml.ConsultaPagDebAutoFinController;
import com.co.allus.modelo.pagosaterceros.infoMovConv;
import com.co.allus.modelo.pagosaterceros.infoPagDebAuto;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author alexander.lopez.o
 */
public class BusqIDPagDebAuto extends Service<ObservableList<infoPagDebAuto>> {

    private String id;

    public BusqIDPagDebAuto(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<infoPagDebAuto>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                final ObservableList<infoPagDebAuto> DatosTabla = FXCollections.observableArrayList();
                if (ConsultaPagDebAutoFinController.registros.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return ConsultaPagDebAutoFinController.registros;

                    } else {

                        synchronized (ConsultaPagDebAutoFinController.registros) {


                            for (Iterator<infoPagDebAuto> it = ConsultaPagDebAutoFinController.registros.iterator(); it.hasNext();) {
                                infoPagDebAuto data = it.next();
                                if (data.getNumdoc().equalsIgnoreCase(id) || data.getNumdoc().startsWith(id)) {
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
