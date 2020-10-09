/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosaterceros;

import com.co.allus.controladoresfxml.PagosATercerosInitController;
import com.co.allus.modelo.pagosterceros.InfoTablaPagosTerceros;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author alexander.lopez.o
 */
public class BusquedaRefPagosT extends Service<ObservableList<InfoTablaPagosTerceros>> {

    String id;

    public BusquedaRefPagosT(String id) {
        this.id = id;
    }

    @Override
    protected Task<ObservableList<InfoTablaPagosTerceros>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                final ObservableList<InfoTablaPagosTerceros> DatosTabla = FXCollections.observableArrayList();
                if (PagosATercerosInitController.pagos.isEmpty()) {
                    throw new Exception("NO HAY DATOS EN LA LISTA");

                } else {
                    if (id.trim().isEmpty()) {
                        return PagosATercerosInitController.pagos;

                    } else {

                        synchronized (PagosATercerosInitController.pagos) {


                            for (Iterator<InfoTablaPagosTerceros> it = PagosATercerosInitController.pagos.iterator(); it.hasNext();) {
                                InfoTablaPagosTerceros data = it.next();
                                if ((data.colNomRef1Property().getValue().equalsIgnoreCase(id) || data.colNomRef1Property().getValue().startsWith(id))
                                        || (data.colNomRef2Property().getValue().equalsIgnoreCase(id) || data.colNomRef2Property().getValue().startsWith(id))
                                        || (data.colNomRef3Property().getValue().equalsIgnoreCase(id) || data.colNomRef3Property().getValue().startsWith(id))) {
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
