/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas.tokendistribucion;

import com.co.allus.controladoresfxml.TokenDistribucionController;
import com.co.allus.modelo.tokendistribucion.InfoTokenDistribucion;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author stephania.rojas
 */
public class BusqIdTask extends Service<ObservableList<InfoTokenDistribucion>> {

    private String id;

    public BusqIdTask(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<InfoTokenDistribucion>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<InfoTokenDistribucion> DatosTablaDist = FXCollections.observableArrayList();
                if (TokenDistribucionController.tokendistribuidos.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return TokenDistribucionController.tokendistribuidos;

                    } else {

                        synchronized (TokenDistribucionController.tokendistribuidos) {


                            for (Iterator<InfoTokenDistribucion> it = TokenDistribucionController.tokendistribuidos.iterator(); it.hasNext();) {
                                final InfoTokenDistribucion data = it.next();
                                if (data.getColID().startsWith(id) || data.getColID().equalsIgnoreCase(id)) {
                                    DatosTablaDist.add(data);

                                }
                            }
                        }
                    }

                    if (DatosTablaDist.isEmpty()) {
                        throw new Exception("No hay Datos");
                    } else {
                        return DatosTablaDist;
                    }
                }

            }
        };
    }
}
