/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.bloqueopreventivo.datosBloqueoPreventivo;
import com.co.allus.modelo.bloqueopreventivo.infoTablaBloqueo;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
import javafx.event.Event;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
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
 * @author stephania.rojas
 */
public class AgilityBloqueoPreventivoController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn Col_Bloqueo;
    @FXML
    private TableColumn Col_Numero;
    @FXML
    private TableColumn Col_TipoTarj;
    @FXML
    private Button continuar_op;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoTablaBloqueo> tabla_datos;
    @FXML
    private ProgressBar progreso;
    private Service<ObservableList<infoTablaBloqueo>> task = new AgilityBloqueoPreventivoController.GetTarjetaTask();
    private static ObservableList<infoTablaBloqueo> bloqueoprev = FXCollections.observableArrayList();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private static GestorDocumental docs = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Pagination pagination = new Pagination();
    private transient Service<Void> serviceBloqueoPrev;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        assert Col_Bloqueo != null : "fx:id=\"Col_Bloqueo\" was not injected: check your FXML file 'AgilityBloqueoPreventivo.fxml'.";
        assert Col_Numero != null : "fx:id=\"Col_Numero\" was not injected: check your FXML file 'AgilityBloqueoPreventivo.fxml'.";
        assert Col_TipoTarj != null : "fx:id=\"Col_TipoTarj\" was not injected: check your FXML file 'AgilityBloqueoPreventivo.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'AgilityBloqueoPreventivo.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'AgilityBloqueoPreventivo.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'AgilityBloqueoPreventivo.fxml'.";
        this.resources = rb;
        this.location = url;
        continuar_op.setDisable(true);
        progreso.setVisible(false);
        Col_Numero.setCellValueFactory(new PropertyValueFactory<infoTablaBloqueo, String>("col_Numero"));
        Col_TipoTarj.setCellValueFactory(new PropertyValueFactory<infoTablaBloqueo, String>("col_TipoTarj"));
        Col_Bloqueo.setCellValueFactory(new PropertyValueFactory<infoTablaBloqueo, String>("col_Bloqueo"));

        tabla_datos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                if (tabla_datos.getSelectionModel().getSelectedItem() != null) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                    tabla_datos.getSelectionModel().clearSelection();
                }
            }
        });
    }

    public void mostrarBloqueoPreventivo() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/AgilityBloqueoPreventivo.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);
                    final TableView<infoTablaBloqueo> tabla_datos = (TableView<infoTablaBloqueo>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();

                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setPrefSize(110, 110);
                    p.setLayoutX(230);
                    p.setLayoutY(190);

                    root.getChildren().addAll(veil, p);


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

                    label_menu.setVisible(false);

                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                    if (arbol_pagos != null) {
                        arbol_pagos.setDisable(true);
                    }

                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                    if (arbol_saldos != null) {
                        arbol_saldos.setDisable(true);
                    }

                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                    if (arbol_transf != null) {
                        arbol_transf.setDisable(true);
                    }

                    final TreeView<String> arbol_bloqueos = (TreeView<String>) pane.lookup("#arbolbloqueos");
                    if (arbol_bloqueos != null) {
                        arbol_bloqueos.setDisable(true);
                    }

                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servadd != null) {
                        arbol_servadd.setDisable(true);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }

                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);


                    // Use binding to be notified whenever the data source chagnes

                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            //System.out.println("TERMINO TAREA");


                            final ObservableList<infoTablaBloqueo> cuentas = FXCollections.observableArrayList();
                            cuentas.addAll(bloqueoprev);
                            tabla_datos.setItems(FXCollections.observableArrayList(cuentas));

                            panel_tabla.getChildren().clear();
                            tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                            panel_tabla.getChildren().add(tabla_datos);
                            panel_tabla.setVisible(true);
                            Atlas.vista.show();


                        }
                    });



                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

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

                                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                                    if (arbol_servadd != null) {
                                        arbol_servadd.setDisable(false);
                                    }

                                    arbol_servadd.getSelectionModel().clearSelection();
                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception e) {
                                        docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
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
                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    public class GetTarjetaTask extends Service<ObservableList<infoTablaBloqueo>> {

        @Override
        protected Task<ObservableList<infoTablaBloqueo>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaBloqueoPreventivo = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    tramaBloqueoPreventivo.append("850,005,");
                    tramaBloqueoPreventivo.append(datosCliente.getRefid());
                    tramaBloqueoPreventivo.append(",");
                    tramaBloqueoPreventivo.append(AtlasConstantes.COD_COD_CONS_TARJ);
                    tramaBloqueoPreventivo.append(",");
                    tramaBloqueoPreventivo.append(datosCliente.getId_cliente());
                    tramaBloqueoPreventivo.append(",");
                    tramaBloqueoPreventivo.append(formatoFecha.format(fecha));
                    tramaBloqueoPreventivo.append(",");
                    tramaBloqueoPreventivo.append(formatoHora.format(fecha));
                    tramaBloqueoPreventivo.append(",");
                    tramaBloqueoPreventivo.append("CRE");
                    tramaBloqueoPreventivo.append(",");
                    tramaBloqueoPreventivo.append(datosCliente.getContraseña());
                    tramaBloqueoPreventivo.append(",");
                    tramaBloqueoPreventivo.append(datosCliente.getTokenOauth());
                    tramaBloqueoPreventivo.append(",~");




                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ LISTAR TARJETAS TDC = " + docs.obtenerHoraActual());
//                           





                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoPreventivo.toString());



                       
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ  LISTAR TARJETAS = " + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoPreventivo.toString());                       

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (AgilityBloqueoPreventivoController.cancelartarea.get()) {

                                        cancel();
                                    } else {
                                        new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        failed();

                                    }
                                }
                            });

                        }

                    }



                    if ("000".equals(Respuesta.split(",")[2])) {
//                                    bloqueoprev.clear();
                        bloqueoprev = FXCollections.observableArrayList();
                        
                        String[] Ltoken = Respuesta.split(",")[4].split(";");

                        for (int i = 0; i < Ltoken.length; i++) {
                            String[] datos = Ltoken[i].split("##");
                            final infoTablaBloqueo data = new infoTablaBloqueo(
                                    datos[0].trim(),
                                    datos[1].trim(),
                                    datos[2].trim());
                            bloqueoprev.add(data);
                        }

                    } else {


                        final String coderror = Respuesta.split(",")[1];
                        final String mensaje = Respuesta.split(",")[2].trim();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                            }
                        });
                        throw new Exception("ERROR");

                    }
                    return bloqueoprev;

                }
            };
        }
    }

    @FXML
    void continuarOP(final ActionEvent event) {

        infoTablaBloqueo selectedItem = tabla_datos.getSelectionModel().getSelectedItem();
        datosBloqueoPreventivo datosBP = datosBloqueoPreventivo.getDataBP();
        datosBP.setNum_tarjeta(selectedItem.col_NumeroProperty().getValue());
        datosBloqueoPreventivo.setDataBP(datosBP);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception e) {
                      docs.imprimirExcepcion(e);
                }
            }
        });

        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle token" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });


        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle token" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }
    }

    public Service<Void> continuar_Op() {
        serviceBloqueoPrev = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {


                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramabloqueopreventivo = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();


                        final datosBloqueoPreventivo datosBP = datosBloqueoPreventivo.getDataBP();
                        //132,connid,nroTarjeta,documento,usuarioDeRed,~
                        tramabloqueopreventivo.append("132,000,");
                        tramabloqueopreventivo.append(datosCliente.getRefid());
                        tramabloqueopreventivo.append(",");
                        tramabloqueopreventivo.append(datosBP.getNum_tarjeta());
                        tramabloqueopreventivo.append(",");
                        tramabloqueopreventivo.append(datosCliente.getId_cliente());
                        tramabloqueopreventivo.append(",");
                        tramabloqueopreventivo.append((System.getProperties().getProperty("user.name")));
                        tramabloqueopreventivo.append(",~");


                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ BLOQUEO PREVENTIVO = " + docs.obtenerHoraActual());
                            servicioSS.enviarRecibirDoBot(AtlasConstantes.IP_BLOQ_AGI, AtlasConstantes.PUERTO_BLOQ_AGI, tramabloqueopreventivo.toString());
                        } catch (Exception ex) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        }
                        return null;
                    }
                };
            }
        };
        return serviceBloqueoPrev;
    }
}
