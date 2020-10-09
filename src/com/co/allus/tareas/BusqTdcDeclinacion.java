/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.DeclinacionesTDCController;
import com.co.allus.modelo.declinacionestdc.modeloListarTarjeta;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author roberto.ceballos
 */
public class BusqTdcDeclinacion extends Service<ObservableList<modeloListarTarjeta>> {

    private String NumTdc;

    public BusqTdcDeclinacion(String NumTdc) {
        this.NumTdc = NumTdc;
    }

    @Override
    protected Task<ObservableList<modeloListarTarjeta>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<modeloListarTarjeta> DatosTabla = FXCollections.observableArrayList();
                if (DeclinacionesTDCController.tarjetas.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (NumTdc.trim().isEmpty()) {
                        return DeclinacionesTDCController.tarjetas;

                    } else {

                        synchronized (DeclinacionesTDCController.tarjetas) {


                            for (Iterator<modeloListarTarjeta> it = DeclinacionesTDCController.tarjetas.iterator(); it.hasNext();) {
                                modeloListarTarjeta data = it.next();
                                if (data.getNumeroTarjeta().equalsIgnoreCase(NumTdc) || data.getNumeroTarjeta().startsWith(NumTdc)) {
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
