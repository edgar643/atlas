/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.ConsultaMovConvDetController;
import com.co.allus.modelo.pagosaterceros.infoMovConv;
import com.co.allus.userComponent.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author alexander.lopez.o
 */
public class BusqFechaMovConv extends Service<ObservableList<infoMovConv>> {

    private DatePicker fechaini;
    private DatePicker fechafin;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

    public BusqFechaMovConv(final DatePicker fechaini, DatePicker fechafin) {
        this.fechaini = fechaini;
        this.fechafin = fechafin;
    }

    @Override
    protected Task<ObservableList<infoMovConv>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                ObservableList<infoMovConv> datosae = FXCollections.observableArrayList();


                for (Iterator<infoMovConv> it = ConsultaMovConvDetController.registros.iterator(); it.hasNext();) {
                    final infoMovConv data = it.next();

                    if (!data.getFechaaplic().trim().isEmpty()) {
                        Date fechaEnvio = null;
                        try {
                            fechaEnvio = formatoFecha.parse(data.getFechaaplic().substring(0, 10));
                        } catch (Exception e) {

                            fechaEnvio = formatoFecha.parse(data.getFechaaplic());


                        }

                        if ((fechaini.getSelectedDate().before(fechaEnvio) && fechafin.getSelectedDate().after(fechaEnvio)) || (fechaini.getSelectedDate().equals(fechaEnvio) || fechafin.getSelectedDate().equals(fechaEnvio))) {
                            datosae.add(data);
                        }
                    }
                }


                if (datosae.isEmpty()) {
                    throw new Exception("No hay Datos");
                } else {
                    return datosae;
                }

            }
        };
    }
}
