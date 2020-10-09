/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.hilos.EnvioTipAutFenix;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.puntoscol.infoTablapcolmigrados;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author roberto.ceballos
 */
public class PuntosMigradosColController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTablapcolmigrados, String> numid;
    @FXML
    private TableColumn<infoTablapcolmigrados, String> tipoid;
    @FXML
    private TableColumn<infoTablapcolmigrados, String> puntosmigrados;
    @FXML
    private TableColumn<infoTablapcolmigrados, String> equivpuntos;
    @FXML
    private TableColumn<infoTablapcolmigrados, String> fecha;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoTablapcolmigrados> tablaDatosPuntosCol;
    @FXML
    private Button terminar_trx;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<infoTablapcolmigrados>> task = new GetinfoMigradospuntosColTask();
    private transient final DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @FXML
    void aceptar(ActionEvent event) {
        cancelartarea.set(true);
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
            arbol_puntosCol.getSelectionModel().clearSelection();

        }

        try {
            pane.getChildren().remove(3);
        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        Atlas.vista.show();
    }

    public void mostrarPuntosColMigrados() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PuntosMigradosCol.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final TableView<infoTablapcolmigrados> tablaDatosPuntosCol = (TableView<infoTablapcolmigrados>) root.lookup("#tablaDatosPuntosCol");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    cliente.setText("");
                    cliente.setText(info);
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    /**
                     * barra de progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

                    label_menu.setVisible(false);
                    /**
                     * TAREAS
                     */
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaDatosPuntosCol.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            ObservableList<infoTablapcolmigrados> value = task.getValue();
                            tablaDatosPuntosCol.itemsProperty().unbind();
//                            System.out.println("TERMINO TAREA");
                            /**
                             * configuracion de la paginacion
                             */
                            tablaDatosPuntosCol.setItems(value);
                            Atlas.vista.show();
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("ERROR EN LA CONSULTA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
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
                                        arbol_puntosCol.getSelectionModel().clearSelection();

                                    }

                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception e) {
                                        gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                    }
                                    Atlas.vista.show();
                                }
                            });


                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });


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


                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    Atlas.vista.show();
                } catch (Exception ex) {
//                  ex.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado correctamente , por favor no volver a intertalo e informar al area tecnica","Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert numid != null : "fx:id=\"numid\" was not injected: check your FXML file 'PuntosMigradosCol.fxml'.";
        assert tipoid != null : "fx:id=\"tipoid\" was not injected: check your FXML file 'PuntosMigradosCol.fxml'.";
        assert puntosmigrados != null : "fx:id=\"puntosmigrados\" was not injected: check your FXML file 'PuntosMigradosCol.fxml'.";
        assert equivpuntos != null : "fx:id=\"equivpuntos\" was not injected: check your FXML file 'PuntosMigradosCol.fxml'.";
        assert fecha != null : "fx:id=\"fecha\" was not injected: check your FXML file 'PuntosMigradosCol.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'PuntosMigradosCol.fxml'.";
        assert tablaDatosPuntosCol != null : "fx:id=\"tablaDatosPuntosCol\" was not injected: check your FXML file 'PuntosMigradosCol.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'PuntosMigradosCol.fxml'.";



        numid.setCellValueFactory(new PropertyValueFactory<infoTablapcolmigrados, String>("numid"));
        tipoid.setCellValueFactory(new PropertyValueFactory<infoTablapcolmigrados, String>("tipoid"));
        puntosmigrados.setCellValueFactory(new PropertyValueFactory<infoTablapcolmigrados, String>("puntosmigrados"));
        equivpuntos.setCellValueFactory(new PropertyValueFactory<infoTablapcolmigrados, String>("equivpuntos"));
        fecha.setCellValueFactory(new PropertyValueFactory<infoTablapcolmigrados, String>("fecha"));
        cancelartarea.set(false);
    }

    public class GetinfoMigradospuntosColTask extends Service<ObservableList<infoTablapcolmigrados>> {

        @Override
        protected Task<ObservableList<infoTablapcolmigrados>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {


                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaconsultafotoMigracion = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    /*CODIFICACION AUTOMATICA*/
                    final Runnable aFenix = new EnvioTipAutFenix(datosCliente.getCodemp(), "006");
                    final Thread aFen = new Thread(aFenix);
                    aFen.start();


                    //String Respuesta = new String();                   
                    final ObservableList<infoTablapcolmigrados> infoseg = FXCollections.observableArrayList();


                    tramaconsultafotoMigracion.append("850,074,");
                    tramaconsultafotoMigracion.append(datosCliente.getRefid());
                    tramaconsultafotoMigracion.append(",");
                    tramaconsultafotoMigracion.append(datosCliente.getTipide());
                    tramaconsultafotoMigracion.append(",");
                    tramaconsultafotoMigracion.append(datosCliente.getId_cliente());
                    tramaconsultafotoMigracion.append(",~");




//                    System.out.println("trama migracion pco : " + tramaconsultafotoMigracion.toString());

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio consulta fotomigracion = " + "##" + gestorDoc.obtenerHoraActual());

                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaconsultafotoMigracion.toString());

//                        System.out.println("respuesta Lista : " + Respuesta);


                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " +StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+ "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal  = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ  = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaconsultafotoMigracion.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    failed();


                                }
                            });

                        }

                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (PuntosMigradosColController.cancelartarea.get()) {
                            cancel();
                        } else {
                            infoseg.add(new infoTablapcolmigrados(Respuesta.split(",")[4],
                                    Respuesta.split(",")[5],
                                    Respuesta.split(",")[6],
                                    Respuesta.split(",")[7],
                                    Respuesta.split(",")[8]));

                        }

                    } else {

                        if (PuntosMigradosColController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3];

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                                }
                            });
                            throw new Exception("ERROR");
                        }
                    }

                    return infoseg;

                }
            };
        }
    }
}
