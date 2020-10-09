/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.tareas;

import com.co.allus.controladoresfxml.ConsultaArhEnvController;
import com.co.allus.modelo.pagosaterceros.infoArchivosEnviados;
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
public class BusFechaArchEnv extends Service<ObservableList<infoArchivosEnviados>> {

    private DatePicker fechaini;
    private DatePicker fechafin;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public BusFechaArchEnv(final DatePicker fechaini, DatePicker fechafin) {
        this.fechaini = fechaini;
        this.fechafin = fechafin;
    }

    @Override
    protected Task<ObservableList<infoArchivosEnviados>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                ObservableList<infoArchivosEnviados> datosae = FXCollections.observableArrayList();


                for (Iterator<infoArchivosEnviados> it = ConsultaArhEnvController.registros.iterator(); it.hasNext();) {
                    final infoArchivosEnviados data = it.next();

                    if (!data.getFecha_process().trim().isEmpty()) {
                        Date fechaEnvio = null;
                        try {
                            fechaEnvio = formatoFecha.parse(data.getFecha_process().substring(0, 10));
                        } catch (Exception e) {

                            fechaEnvio = formatoFecha.parse(data.getFecha_process());


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
