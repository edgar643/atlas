/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.EmpresasTDCFinController;
import com.co.allus.modelo.informaciontdc.infotablatdcbuscar;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author stephania.rojas
 */
public class BusqFechaTask extends Service<ObservableList<infotablatdcbuscar>> {

    private String fecha;

    public BusqFechaTask(final String fecha) {
        this.fecha = fecha;
    }

    @Override
    protected Task<ObservableList<infotablatdcbuscar>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<infotablatdcbuscar> DatosTabla = FXCollections.observableArrayList();
                if (EmpresasTDCFinController.datosTabla.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (fecha.trim().isEmpty()) {
                        return EmpresasTDCFinController.datosTabla;

                    } else {

                        synchronized (EmpresasTDCFinController.datosTabla) {


                            for (Iterator<infotablatdcbuscar> it = EmpresasTDCFinController.datosTabla.iterator(); it.hasNext();) {
                                final infotablatdcbuscar data = it.next();
                                if (data.getCol_fecha().equalsIgnoreCase(fecha) || data.getCol_fecha().startsWith(fecha)) {
                                    DatosTabla.add(data);

                                }
                            }
                        }
                    }

                    if (DatosTabla.isEmpty()) {
//                     tabla_datos.setPlaceholder(new Label(""));
                        throw new Exception("No hay Datos");
                    } else {
                        return DatosTabla;
                    }
                }

            }
        };
    }
}