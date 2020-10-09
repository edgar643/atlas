/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.PuntosColAjustePuntosController;
import com.co.allus.modelo.puntoscol.infoTablaAjustePuntos;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author roberto.ceballos
 */
public class BusqDescripAjustes extends Service<ObservableList<infoTablaAjustePuntos>> {

    private String descr;

    public BusqDescripAjustes(String descr) {
        this.descr = descr;
    }

    @Override
    protected Task<ObservableList<infoTablaAjustePuntos>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoTablaAjustePuntos> DatosTabla = FXCollections.observableArrayList();
                if (PuntosColAjustePuntosController.registros.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (descr.trim().isEmpty()) {
                        return PuntosColAjustePuntosController.registros;

                    } else {

                        synchronized (PuntosColAjustePuntosController.registros) {


                            for (Iterator<infoTablaAjustePuntos> it = PuntosColAjustePuntosController.registros.iterator(); it.hasNext();) {
                                infoTablaAjustePuntos data = it.next();
                                if (data.getDescripcion().equalsIgnoreCase(descr)
                                        || data.getDescripcion().toLowerCase().startsWith(descr.toLowerCase())
                                        || data.getDescripcion().toLowerCase().endsWith(descr.toLowerCase())
                                        || data.getDescripcion().toLowerCase().contains(descr.toLowerCase())) {
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
