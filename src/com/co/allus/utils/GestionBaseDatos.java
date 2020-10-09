/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.utils;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author alexander.lopez.o
 */
public class GestionBaseDatos {

    private static final String NO_DATA = "NODATA";
    private static final String ERROR_BD = "BD998";
    public static final List<String> datos = new ArrayList<String>();
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    /**
     * Consulta las Categorias Disponibles en la Base de datos de convenios
     *
     * @return
     */
    public ObservableList<String> consultarDatosCategorias() {
        final ConectSS conexion = new ConectSS();

        if (datos.isEmpty()) {
            try {
                final String datosCategorias = conexion.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, "900##100##~");
                final String[] Categorias = datosCategorias.split("##");

                if (NO_DATA.equals(Categorias[0])) {
                    datos.add("NO SE ENCUENTRAN REGISTROS EN LA BD");

                } else if (ERROR_BD.equals(Categorias[0])) {
                    datos.add("ERROR EN BASE DE DATOS");
                } else {
                    datos.add("SELECCIONE");
                    for (int i = 0; i < Categorias.length - 1; i++) {
                        datos.add(Categorias[i].trim());
                    }

                }
            } catch (Exception ex) {
                gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                try {
                    final String datosCategorias = conexion.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, "900##100##~");
                } catch (Exception ex1) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            final Parent frameGnral = Atlas.vista.getScene().getRoot();
                            final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                            final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                            pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
                            final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                            final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                            arbol_saldos.setDisable(false);
                            arbol_saldos.getSelectionModel().clearSelection();
                            arbol_pagos.setDisable(false);
                            arbol_pagos.getSelectionModel().clearSelection();
                            new ModalMensajes("ha ocurrido un error en procesar la peticion", "Cod.999", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                        }
                    });
                }
            }
        }

        return FXCollections.observableList(datos);

    }
}
