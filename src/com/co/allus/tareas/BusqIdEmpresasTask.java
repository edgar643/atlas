/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.TokenEmpresasController;
import com.co.allus.modelo.tokenempresas.infoTablaToken;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author stephania.rojas
 */
public class BusqIdEmpresasTask extends Service<ObservableList<infoTablaToken>> {

    private String id;

    public BusqIdEmpresasTask(final String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<infoTablaToken>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infoTablaToken> DatosTabla = FXCollections.observableArrayList();
                if (TokenEmpresasController.token.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return TokenEmpresasController.token;

                    } else {

                        synchronized (TokenEmpresasController.token) {


                            for (Iterator<infoTablaToken> it = TokenEmpresasController.token.iterator(); it.hasNext();) {
                                final infoTablaToken data = it.next();
                                if (data.getColId_usuario().equalsIgnoreCase(id) || data.getColId_usuario().startsWith(id)) {
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
