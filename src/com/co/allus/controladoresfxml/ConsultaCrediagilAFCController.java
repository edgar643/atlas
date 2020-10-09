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
import com.co.allus.modelo.SaldoAFC.DatosCrediagilAFC;
import com.co.allus.utils.AtlasConstantes;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author stephania.rojas
 */
public class ConsultaCrediagilAFCController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn colCupoAsignado;
    @FXML
    private TableColumn colCupoDisponible;
    @FXML
    private TableColumn colCupoUtilzado;
    @FXML
    private TableColumn colValor;
    @FXML
    private Button desembolso_op;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<?> tabla_datos;
    @FXML
    private Button terminar_trx;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static GestorDocumental docs = new GestorDocumental();
    private Service<ObservableList<DatosCrediagilAFC>> task = new ConsultaCrediagilAFCController.GetCrediagilask();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert colCupoAsignado != null : "fx:id=\"colCupoAsignado\" was not injected: check your FXML file 'ConsultaCrediagilAFC.fxml'.";
        assert colCupoDisponible != null : "fx:id=\"colCupoDisponible\" was not injected: check your FXML file 'ConsultaCrediagilAFC.fxml'.";
        assert colCupoUtilzado != null : "fx:id=\"colCupoUtilzado\" was not injected: check your FXML file 'ConsultaCrediagilAFC.fxml'.";
        assert colValor != null : "fx:id=\"colValor\" was not injected: check your FXML file 'ConsultaCrediagilAFC.fxml'.";
        assert desembolso_op != null : "fx:id=\"desembolso_op\" was not injected: check your FXML file 'ConsultaCrediagilAFC.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaCrediagilAFC.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'ConsultaCrediagilAFC.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ConsultaCrediagilAFC.fxml'.";
        this.location = url;
        this.resources = rb;
        colCupoAsignado.setCellValueFactory(new PropertyValueFactory<DatosCrediagilAFC, String>("ValorCupoAsignadoEnt"));
        colCupoDisponible.setCellValueFactory(new PropertyValueFactory<DatosCrediagilAFC, String>("ValorDisponibleEnt"));
        colCupoUtilzado.setCellValueFactory(new PropertyValueFactory<DatosCrediagilAFC, String>("ValorCupoUtilizadoEnt"));
        colValor.setCellValueFactory(new PropertyValueFactory<DatosCrediagilAFC, String>("ValorCanjeEnt"));

    }

    @FXML
    void terminar_trx(ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    docs.imprimir("Se presento un error inesperado en la aplicación --->" + ex.toString());
                }
            }
        });
    }

    @FXML
    void desembolso_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                CrediagilController controller = new CrediagilController();
                controller.mostrarTransfCrediagil();
            }
        });
    }

    public void mostrarConsultaCrediagilAFC() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaCrediagilAFC.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final Button desembolso_op = (Button) root.lookup("#desembolso_op");

                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    if ("K".equalsIgnoreCase(datosCliente.getClavesn()) || "V".equalsIgnoreCase(datosCliente.getClavesn())) {

                        desembolso_op.setDisable(false);

                    } else {
                        desembolso_op.setDisable(true);
                    }



                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");



                    final TableView<DatosCrediagilAFC> tabla_datos = (TableView<DatosCrediagilAFC>) root.lookup("#tabla_datos");

                    //dataTabla.add(new infoTablaPlanesPagos(planesdatos.getCodplan(), dataPP.getNumunidad(), dataPP.getClasePago(), dataPP.getUnidadesTiempo() , dataPP.getNumUnidades(), dataPP.getNumPagos(), dataPP.getValor(), dataPP.getDiaFijo()));


                    final DropShadow shadow = new DropShadow();
                    desembolso_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    desembolso_op.setCursor(Cursor.HAND);
                                    desembolso_op.setEffect(shadow);
                                }
                            });

                    desembolso_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    desembolso_op.setCursor(Cursor.DEFAULT);
                                    desembolso_op.setEffect(null);

                                }
                            });

                    terminar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminar_trx.setCursor(Cursor.HAND);
                                    terminar_trx.setEffect(shadow);
                                }
                            });

                    terminar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminar_trx.setCursor(Cursor.DEFAULT);
                                    terminar_trx.setEffect(null);

                                }
                            });


                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    final Region veil = new Region();

                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setPrefSize(110, 110);
                    p.setLayoutX(230);
                    p.setLayoutY(190);

                    root.getChildren().addAll(veil, p);
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    task.reset();
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            // System.out.println("TERMINO TAREA");

                            final ObservableList<DatosCrediagilAFC> credi = task.getValue();
                            // final ObservableList<DatosCrediagilAFC> credi = FXCollections.observableArrayList();
                            //credi.addAll(crediagilafc);
                            tabla_datos.setItems(FXCollections.observableArrayList(credi));

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
                            //  System.out.println("ERROR EN LA CONSULTA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                    final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                                    pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));

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

                                    final TreeView<String> arbol_consMov = (TreeView<String>) pane.lookup("#arbol_movimientos");
                                    if (arbol_consMov != null) {
                                        arbol_consMov.setDisable(true);
                                    }

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
                            //System.out.println("cancelo la tarea");
                        }
                    });


                    Atlas.vista.show();

                } catch (Exception e) {
//                    e.printStackTrace();
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });


    }

    public class GetCrediagilask extends Service<ObservableList<DatosCrediagilAFC>> {

        @Override
        protected Task<ObservableList<DatosCrediagilAFC>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaCrediagilAFC = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final ObservableList<DatosCrediagilAFC> crediagilafc = FXCollections.observableArrayList();



                    //850,059,connid,0025,Origen(IVR),identificacion,Clavehw,~


                    tramaCrediagilAFC.append("850,059,");
                    tramaCrediagilAFC.append(datosCliente.getRefid());
                    tramaCrediagilAFC.append(",");
                    tramaCrediagilAFC.append(AtlasConstantes.COD_AFC_CREDIAGIL);
                    tramaCrediagilAFC.append(",");
                    tramaCrediagilAFC.append("GDE");
                    tramaCrediagilAFC.append(",");
                    tramaCrediagilAFC.append(datosCliente.getId_cliente());
                    tramaCrediagilAFC.append(",");
                    tramaCrediagilAFC.append(datosCliente.getContraseña());
                    tramaCrediagilAFC.append(",");
                    tramaCrediagilAFC.append(datosCliente.getTokenOauth());
                    tramaCrediagilAFC.append(",~");


                    //System.out.println("trama crediagil afc " + tramaCrediagilAFC.toString());

                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ SALDO CREDIAGIL = " + "##" + docs.obtenerHoraActual());

//                            Respuesta = "850,"
//                                    + "059,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "150000,"
//                                    + "20,"
//                                    + "1500000,"
//                                    + "20,"
//                                    + "15000000,"
//                                    + "20,"
//                                    + "150000,"
//                                    + "20,"
//                                    + "~";


                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCrediagilAFC.toString());


                        // System.out.println(" RESPUESTA TRAMA CREDIAGIL AFC:" + Respuesta);


                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ SALDO CREDIAGIL = " + Respuesta + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ SALDO CREDIAGIL = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCrediagilAFC.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ConsultaCrediagilAFCController.cancelartarea.get()) {
                                        // System.out.println("cancelo tarea");
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


                        String ValorCupoAsignado = (formatonum.format(Double.parseDouble(Respuesta.split(",")[4])).replace(".", "") + "." + Respuesta.split(",")[5]);
                        String ValorCupoUtilizado = (formatonum.format(Double.parseDouble(Respuesta.split(",")[6])).replace(".", "") + "." + Respuesta.split(",")[7]);
                        String ValorDisponible = (formatonum.format(Double.parseDouble(Respuesta.split(",")[8])).replace(".", "") + "." + Respuesta.split(",")[9]);
                        String ValorCanje = (formatonum.format(Double.parseDouble(Respuesta.split(",")[10])).replace(".", "") + "." + Respuesta.split(",")[11]);
                        try {
                            final DatosCrediagilAFC data = new DatosCrediagilAFC(
                                    ValorCupoAsignado,
                                    ValorCupoUtilizado,
                                    ValorDisponible,
                                    ValorCanje);
                            crediagilafc.add(data);

                        } catch (Exception ex) {
                            Logger.getLogger(ConsultaCrediagilAFCController.class.getName()).log(Level.SEVERE, null, ex);
                        }


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
                    return crediagilafc;

                }
            };
        }
    }
}
