/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.ConsultaPagDebAutoFinController;
import com.co.allus.modelo.pagosaterceros.infoPagDebAuto;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class BusqrefPagDebAuto extends Service<ObservableList<infoPagDebAuto>> {

    private String descr;

    public BusqrefPagDebAuto(String descr) {
        this.descr = descr;
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
                    if (descr.trim().isEmpty()) {
                        return ConsultaPagDebAutoFinController.registros;

                    } else {

                        synchronized (ConsultaPagDebAutoFinController.registros) {


                            for (Iterator<infoPagDebAuto> it = ConsultaPagDebAutoFinController.registros.iterator(); it.hasNext();) {
                                infoPagDebAuto data = it.next();
                                if (data.getReffija().equalsIgnoreCase(descr)
                                        || data.getReffija().toLowerCase().startsWith(descr.toLowerCase())
                                        || data.getReffija().toLowerCase().endsWith(descr.toLowerCase())
                                        || data.getReffija().toLowerCase().contains(descr.toLowerCase())) {
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
