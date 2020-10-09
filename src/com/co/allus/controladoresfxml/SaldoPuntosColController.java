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
import com.co.allus.modelo.puntoscol.DatosSaldoPuntosCol;
import com.co.allus.modelo.puntoscol.InfoTablaSaldoPuntosCol;
import com.co.allus.modelo.puntoscol.MapeoEstados;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Tooltip;
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
 * @author alexander.lopez.o
 */
public class SaldoPuntosColController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<InfoTablaSaldoPuntosCol, String> descripcion;
    @FXML
    private TableColumn<InfoTablaSaldoPuntosCol, String> informacion;
    @FXML
    private TableView<InfoTablaSaldoPuntosCol> tablaDatosPuntosCol;
    @FXML
    private Button terminar_trx;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<InfoTablaSaldoPuntosCol>> task = new GetinfoSaldopuntosColTask();
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
        }

        arbol_puntosCol.getSelectionModel().clearSelection();
        try {
            pane.getChildren().remove(3);
        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        Atlas.vista.show();

    }

    public void mostrarSaldosPuntosCol() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/SaldoPuntosCol.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final TableView<InfoTablaSaldoPuntosCol> tablaDatosPuntosCol = (TableView<InfoTablaSaldoPuntosCol>) root.lookup("#tablaDatosPuntosCol");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
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

                            ObservableList<InfoTablaSaldoPuntosCol> value = task.getValue();
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
                                    }

                                    arbol_puntosCol.getSelectionModel().clearSelection();


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
//                    ex.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado correctamente , por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'SaldoPuntosCol.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'SaldoPuntosCol.fxml'.";
        assert tablaDatosPuntosCol != null : "fx:id=\"tablaDatosPuntosCol\" was not injected: check your FXML file 'SaldoPuntosCol.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'SaldoPuntosCol.fxml'.";


        tablaDatosPuntosCol.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                if (tablaDatosPuntosCol.getSelectionModel().getSelectedItem() != null) {
                    if (tablaDatosPuntosCol.getSelectionModel().getSelectedItem().getDescripcion().equalsIgnoreCase(DatosSaldoPuntosCol.ESTADO)) {
                        // aca va posible  mapeo
                        tablaDatosPuntosCol.getSelectionModel().getTableView().tooltipProperty().set(new Tooltip(tablaDatosPuntosCol.getSelectionModel().getSelectedItem().getInformacion()));
                    }

                } else {
                    tablaDatosPuntosCol.getSelectionModel().clearSelection();
                    tablaDatosPuntosCol.getSelectionModel().getTableView().tooltipProperty().set(new Tooltip(""));
                }
            }
        });

        descripcion.setCellValueFactory(new PropertyValueFactory<InfoTablaSaldoPuntosCol, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<InfoTablaSaldoPuntosCol, String>("informacion"));
        cancelartarea.set(false);

    }

    public class GetinfoSaldopuntosColTask extends Service<ObservableList<InfoTablaSaldoPuntosCol>> {

        @Override
        protected Task<ObservableList<InfoTablaSaldoPuntosCol>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {


                    final Cliente datosCliente = Cliente.getCliente();

                    /*CODIFICACION AUTOMATICA*/
                    final Runnable aFenix = new EnvioTipAutFenix(datosCliente.getCodemp(), "784");
                    final Thread aFen = new Thread(aFenix);
                    aFen.start();

                    String Respuesta = new String();
                    final StringBuilder tramaSaldopuntosCol = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final ObservableList<InfoTablaSaldoPuntosCol> infoseg = FXCollections.observableArrayList();

                    tramaSaldopuntosCol.append("850,068,");
                    tramaSaldopuntosCol.append(datosCliente.getRefid());
                    tramaSaldopuntosCol.append(",");
                    tramaSaldopuntosCol.append(AtlasConstantes.COD_SALDO_PUNTOSCOL);
                    tramaSaldopuntosCol.append(",");
                    tramaSaldopuntosCol.append("GDE");
                    tramaSaldopuntosCol.append(",");
                    tramaSaldopuntosCol.append(datosCliente.getId_cliente());
                    tramaSaldopuntosCol.append(",");
                    tramaSaldopuntosCol.append(datosCliente.getContraseña());
                    tramaSaldopuntosCol.append(",");
                    tramaSaldopuntosCol.append(datosCliente.getTokenOauth());
                    tramaSaldopuntosCol.append(",~");

//                    System.out.println("trama puntosCol " + tramaSaldopuntosCol.toString());

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ puntosCol = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,"
//                                + "067,"
//                                + "000,"
//                                + "TRANSACCION EXITOSA,"                              
//                                + "Alexander Lopez,"
//                                + "F,"
//                                + "B,"
//                                + "C,"
//                                + "15000,"
//                                + "2018-04-30,"
//                                + "0"
//                                + ",~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaSaldopuntosCol.toString());
                        // System.out.println("respuesta Lista : " + Respuesta);
                        //Thread.sleep(3000);

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ infoseguridad= " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaSaldopuntosCol.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (SaldoPuntosColController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {

                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        failed();
                                    }
                                }
                            });
                        }

                    }



                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (SaldoPuntosColController.cancelartarea.get()) {
                            cancel();
                        } else {


                            infoseg.add(new InfoTablaSaldoPuntosCol(DatosSaldoPuntosCol.ESTADO, MapeoEstados.mapeoEstados.get(Respuesta.split(",")[5].trim())));
                            try {
                                infoseg.add(new InfoTablaSaldoPuntosCol(DatosSaldoPuntosCol.SALDO_TOTAL, formatonum.format(Double.parseDouble(Respuesta.split(",")[8]))));

                            } catch (Exception e) {
                                infoseg.add(new InfoTablaSaldoPuntosCol(DatosSaldoPuntosCol.SALDO_TOTAL, Respuesta.split(",")[8]));
                            }

                            try {
                                infoseg.add(new InfoTablaSaldoPuntosCol(DatosSaldoPuntosCol.SALDO_VENCER, formatonum.format(Double.parseDouble(Respuesta.split(",")[10]))));
                            } catch (Exception e) {

                                infoseg.add(new InfoTablaSaldoPuntosCol(DatosSaldoPuntosCol.SALDO_VENCER, Respuesta.split(",")[10]));
                            }

                            infoseg.add(new InfoTablaSaldoPuntosCol(DatosSaldoPuntosCol.FECHA_VENCIMIENTO, Respuesta.split(",")[9]));


                        }

                    } else {

                        if (SaldoPuntosColController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

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
