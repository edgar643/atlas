/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import javafx.collections.ObservableList;
import com.co.allus.modelo.consultadebitosaut.infoDebAut1;
import com.co.allus.controladoresfxml.MovDebAut2Controller;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author stephania.rojas
 */
public class BusqConvDebAut extends Service<ObservableList<infoDebAut1>> {

    private String id;

    public BusqConvDebAut(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<infoDebAut1>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoDebAut1> DatosTabla = FXCollections.observableArrayList();
                if (MovDebAut2Controller.registros.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return MovDebAut2Controller.registros;

                    } else {

                        synchronized (MovDebAut2Controller.registros) {


                            for (Iterator<infoDebAut1> it = MovDebAut2Controller.registros.iterator(); it.hasNext();) {
                                infoDebAut1 data = it.next();
                                if (data.getCodConvenio().equalsIgnoreCase(id) || data.getCodConvenio().startsWith(id)) {
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
