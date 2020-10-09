/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.TokenEmpresasDisponiblesController;
import com.co.allus.modelo.tokenempresas.infoTablaToken;
import com.co.allus.modelo.tokenempresas.infoTokenDisponibles;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author stephania.rojas
 */
public class BusqSerialTask extends Service<ObservableList<infoTokenDisponibles>> {

    private String serial;

    public BusqSerialTask(final String serial) {
        this.serial = serial;
    }

    @Override
    protected Task<ObservableList<infoTokenDisponibles>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoTokenDisponibles> DatosTabla = FXCollections.observableArrayList();
                if (TokenEmpresasDisponiblesController.datos.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (serial.trim().isEmpty()) {
                        return TokenEmpresasDisponiblesController.datos;

                    } else {

                        synchronized (TokenEmpresasDisponiblesController.datos) {


                            for (Iterator<infoTokenDisponibles> it = TokenEmpresasDisponiblesController.datos.iterator(); it.hasNext();) {
                                final infoTokenDisponibles data = it.next();
                                if (data.getColserial().equalsIgnoreCase(serial) || data.getColserial().startsWith(serial)) {
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
