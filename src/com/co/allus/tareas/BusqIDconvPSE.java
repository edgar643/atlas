/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.MovRecaudosPSEController;
import com.co.allus.modelo.consultamovimientos.infoMovPSE;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author alexander.lopez.o
 */
public class BusqIDconvPSE extends Service<ObservableList<infoMovPSE>> {

    private String id;

    public BusqIDconvPSE(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<infoMovPSE>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoMovPSE> DatosTabla = FXCollections.observableArrayList();
                if (MovRecaudosPSEController.registros.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return MovRecaudosPSEController.registros;

                    } else {

                        synchronized (MovRecaudosPSEController.registros) {


                            for (Iterator<infoMovPSE> it = MovRecaudosPSEController.registros.iterator(); it.hasNext();) {
                                infoMovPSE data = it.next();
                                if (data.getCodigoconv().equalsIgnoreCase(id) || data.getCodigoconv().startsWith(id)) {
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
