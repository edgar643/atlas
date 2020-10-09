/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultamovimientos.InfoTablaDetMovPSE;
import com.co.allus.modelo.pagosaterceros.infoArchivoEstado;
import com.co.allus.modelo.pagosaterceros.infoArchivosEnviados;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaArhEnvDetController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoArchivoEstado, String> ColFechaEnv;
    @FXML
    private TableColumn<infoArchivoEstado, String> ColNumReg;
    @FXML
    private TableColumn<infoArchivoEstado, String> ColValTotal;
    @FXML
    private TableColumn<InfoTablaDetMovPSE, String> descripcion;
    @FXML
    private TableColumn<InfoTablaDetMovPSE, String> informacion;
    @FXML
    private TableView<InfoTablaDetMovPSE> tabla_datos;
    @FXML
    private TableView<infoArchivoEstado> tabla_datos1;
    @FXML
    private Button volver;
    @FXML
    private Button volverMenuEnv;
    private GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert ColFechaEnv != null : "fx:id=\"ColFechaEnv\" was not injected: check your FXML file 'ConsultaArhEnvDet.fxml'.";
        assert ColNumReg != null : "fx:id=\"ColNumReg\" was not injected: check your FXML file 'ConsultaArhEnvDet.fxml'.";
        assert ColValTotal != null : "fx:id=\"ColValTotal\" was not injected: check your FXML file 'ConsultaArhEnvDet.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'ConsultaArhEnvDet.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'ConsultaArhEnvDet.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'ConsultaArhEnvDet.fxml'.";
        assert tabla_datos1 != null : "fx:id=\"tabla_datos1\" was not injected: check your FXML file 'ConsultaArhEnvDet.fxml'.";
        assert volver != null : "fx:id=\"volver\" was not injected: check your FXML file 'ConsultaArhEnvDet.fxml'.";
        assert volverMenuEnv != null : "fx:id=\"volverMenuEnv\" was not injected: check your FXML file 'ConsultaArhEnvDet.fxml'.";

        descripcion.setCellValueFactory(new PropertyValueFactory<InfoTablaDetMovPSE, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<InfoTablaDetMovPSE, String>("informacion"));

        ColFechaEnv.setCellValueFactory(new PropertyValueFactory<infoArchivoEstado, String>("fecha_envio"));
        ColNumReg.setCellValueFactory(new PropertyValueFactory<infoArchivoEstado, String>("numero_registros"));
        ColValTotal.setCellValueFactory(new PropertyValueFactory<infoArchivoEstado, String>("valor_total"));


    }

    public void mostrarDetalleArcEnv(final infoArchivosEnviados infoarchEnv, final String estadoArchivo, final String valorTotal, final String fechaAplicacion) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaArhEnvDet.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#volverMenuEnv");
                    final Button volver = (Button) root.lookup("#volver");
                    final TableView<InfoTablaDetMovPSE> tablaDatos = (TableView<InfoTablaDetMovPSE>) root.lookup("#tabla_datos");

                    final TableView<infoArchivoEstado> tablaDatos1 = (TableView<infoArchivoEstado>) root.lookup("#tabla_datos1");

                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<InfoTablaDetMovPSE> dataTabla = FXCollections.observableArrayList();
                    tablaDatos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<infoArchivoEstado> dataTabla1 = FXCollections.observableArrayList();

                    // se agregan los datos a la tabla
                    dataTabla.add(new InfoTablaDetMovPSE("Secuencia del Archivo", infoarchEnv.getNum_rastreo()));
                    dataTabla.add(new InfoTablaDetMovPSE("Valor", infoarchEnv.getValor_total_arc()));
                  //  dataTabla.add(new InfoTablaDetMovPSE("Numero de Rastreo", infoarchEnv.getNum_rastreo()));
                    dataTabla.add(new InfoTablaDetMovPSE("Fecha Aplicacion", fechaAplicacion));
                    dataTabla.add(new InfoTablaDetMovPSE("Fecha Recibido", infoarchEnv.getFecha_process()));
                    
                    dataTabla.add(new InfoTablaDetMovPSE("Estado", estadoArchivo));
                   // dataTabla.add(new InfoTablaDetMovPSE("Respuesta", ""));
                   // dataTabla.add(new InfoTablaDetMovPSE("Medio Envio", ""));



                    tablaDatos.setItems(dataTabla);

                    // se agregan los datos a la tabla1
                    dataTabla1.add(new infoArchivoEstado(fechaAplicacion, infoarchEnv.getNun_registro(), infoarchEnv.getValor_total_arc()));
                    tablaDatos1.setItems(dataTabla1);

                    /**
                     *
                     */
                    final DropShadow shadow = new DropShadow();

                    continuar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.HAND);
                                    continuar_op.setEffect(shadow);
                                }
                            });

                    continuar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.DEFAULT);
                                    continuar_op.setEffect(null);

                                }
                            });

                    volver.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volver.setCursor(Cursor.HAND);
                                    volver.setEffect(shadow);
                                }
                            });

                    volver.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volver.setCursor(Cursor.DEFAULT);
                                    volver.setEffect(null);

                                }
                            });


                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_RIGHT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    Logger.getGlobal().log(Level.SEVERE, ex.toString());
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , por favor  informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });




    }
    
    @FXML
    void volverop(ActionEvent event) 
    {
         final Parent frameGnral = Atlas.vista.getScene().getRoot();
        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));

        final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
        if (arbol_pagos != null) {
            arbol_pagos.setDisable(false);
        }

        final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
        if (arbol_saldos != null) {
            arbol_saldos.setDisable(false);
        }

        final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
        if (arbol_transf != null) {
            arbol_transf.setDisable(false);
        }

        final TreeView<String> arbol_bloqueos = (TreeView<String>) pane.lookup("#arbolbloqueos");
        if (arbol_bloqueos != null) {
            arbol_bloqueos.setDisable(false);
        }

        final TreeView<String> arbol_servad = (TreeView<String>) pane.lookup("#arbol_servadicionales");
        if (arbol_servad != null) {
            arbol_servad.setDisable(false);
        }

        final TreeView<String> arbol_infotdc = (TreeView<String>) pane.lookup("#arbol_infotdc");
        if (arbol_infotdc != null) {
            arbol_infotdc.setDisable(false);
        }

        final TreeView<String> arbol_consultas = (TreeView<String>) pane.lookup("#arbol_consultas");
        if (arbol_consultas != null) {
            arbol_consultas.setDisable(false);
        }

        final TreeView<String> arbol_movmientos = (TreeView<String>) pane.lookup("#arbol_movimientos");
        if (arbol_movmientos != null) {
            arbol_movmientos.setDisable(false);
        }

        final TreeView<String> arbol_contrabloqueos = (TreeView<String>) pane.lookup("#arbol_contrabloqueos");
        if (arbol_contrabloqueos != null) {
            arbol_contrabloqueos.setDisable(false);
        }

        final TreeView<String> arbol_infoseguridad = (TreeView<String>) pane.lookup("#arbol_infoseguridad");
        if (arbol_infoseguridad != null) {
            arbol_infoseguridad.setDisable(false);
        }

        final TreeView<String> arbol_puntosCol = (TreeView<String>) pane.lookup("#arbolPuntosCol");
        if (arbol_puntosCol != null) {
            arbol_puntosCol.setDisable(false);
        }

        final TreeView<String> arbol_recaudos = (TreeView<String>) pane.lookup("#arbol_recaudos");
        if (arbol_recaudos != null) {
            arbol_recaudos.setDisable(false);
            arbol_recaudos.getSelectionModel().clearSelection();

        }

        try {
            pane.getChildren().remove(3);
        } catch (Exception e) {
            docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }
        Atlas.vista.show();
    }

           
    @FXML
    void volverMenuEnvOP(ActionEvent event) {
        ConsultaArhEnvController controller = new ConsultaArhEnvController();
        controller.mostrarArchEnv(null, null, null, 40, null, null, null, null, 0);
    }
}
