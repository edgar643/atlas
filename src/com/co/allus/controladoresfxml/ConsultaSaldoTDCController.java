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
import com.co.allus.modelo.saldostdc.DatosSaldoTdc;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author maria.castro
 */
public class ConsultaSaldoTDCController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<modelSaldoTarjeta, String> bloqueo_tarjeta;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar_op;
    @FXML
    private TableColumn<modelSaldoTarjeta, String> numero;
    @FXML
    private TableView<modelSaldoTarjeta> tablaDatos;
    @FXML
    private TableColumn<modelSaldoTarjeta, String> tipo_tarjeta;
    @FXML
    private ProgressBar progreso;
    @FXML
    private transient StackPane panel_tabla;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private transient Service<Void> serviceSaldoCreditoTDC = continuar_Op();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static AtomicBoolean cancelartareaSaldo = new AtomicBoolean();
    private Service<ObservableList<modelSaldoTarjeta>> task = new ConsultaSaldoTDCController.GetTarjetasTask();
    private static ObservableList<modelSaldoTarjeta> tarjetas = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        assert bloqueo_tarjeta != null : "fx:id=\"bloqueo_tarjeta\" was not injected: check your FXML file 'ConsultaSaldoTDC.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ConsultaSaldoTDC.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'ConsultaSaldoTDC.fxml'.";
        assert numero != null : "fx:id=\"numero\" was not injected: check your FXML file 'ConsultaSaldoTDC.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ConsultaSaldoTDC.fxml'.";
        assert tipo_tarjeta != null : "fx:id=\"tipo_tarjeta\" was not injected: check your FXML file 'ConsultaSaldoTDC.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaSaldoTDC.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaSaldoTDC.fxml'.";

        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {

                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });
        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        numero.setCellValueFactory(new PropertyValueFactory<modelSaldoTarjeta, String>("mascaraNumero"));
        tipo_tarjeta.setCellValueFactory(new PropertyValueFactory<modelSaldoTarjeta, String>("tipoTarjeta"));
        bloqueo_tarjeta.setCellValueFactory(new PropertyValueFactory<modelSaldoTarjeta, String>("bloqueoTarjeta"));

        ConsultaSaldoTDCController.cancelartarea.set(false);
        ConsultaSaldoTDCController.cancelartareaSaldo.set(false);

    }

    @FXML
    void cancelarOP(final ActionEvent event) {


        ConsultaSaldoTDCController.cancelartarea.set(true);
        ConsultaSaldoTDCController.cancelartareaSaldo.set(true);

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
            arbol_servadd.setDisable(true);
        }


        arbol_saldos.getSelectionModel().clearSelection();
        final Label cliente = (Label) frameGnral.lookup("#cliente");
        cliente.setText(Cliente.getCliente().getNombre());

        try {
            pane.getChildren().remove(3);

        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }

    }

    @FXML
    void continuarOP(final ActionEvent event) {


        if (serviceSaldoCreditoTDC.isRunning()) {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceSaldoCreditoTDC.progressProperty());
            serviceSaldoCreditoTDC.reset();
            serviceSaldoCreditoTDC.start();

        } else {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceSaldoCreditoTDC.progressProperty());
            serviceSaldoCreditoTDC.start();
        }

        serviceSaldoCreditoTDC.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Saldo TDC" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                ConsultaSaldoTDCController.cancelartareaSaldo.set(false);

            }
        });



        serviceSaldoCreditoTDC.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Saldo TDC" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceSaldoCreditoTDC.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
//                System.out.println("fallo");
            }
        });


    }

    public void mostrarSaldosTDC(final String menu, final boolean isnuevaConsulta) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaSaldoTDC.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<modelSaldoTarjeta> tablaData = (TableView<modelSaldoTarjeta>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    // final BigDecimal dato = new BigDecimal((datosCliente.getNombre().length() / 2d));
                    // final BigDecimal setScale = dato.setScale(0, BigDecimal.ROUND_UP);
                    // final String info = datosCliente.getNombre() + "\n" + StringUtilities.rellenarDato(" ", setScale.intValue(), " ") + "C.C: " + datosCliente.getId_cliente();
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);

                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);


                    final DropShadow shadow = new DropShadow();
                    botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botoncontinuarOp.setCursor(Cursor.HAND);
                                    botoncontinuarOp.setEffect(shadow);
                                }
                            });

                    botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botoncontinuarOp.setCursor(Cursor.DEFAULT);
                                    botoncontinuarOp.setEffect(null);

                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.HAND);
                                    cancelar.setEffect(shadow);
                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.DEFAULT);
                                    cancelar.setEffect(null);

                                }
                            });

                    botoncontinuarOp.setDisable(true);
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
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);


                    // Use binding to be notified whenever the data source chagnes
                    // task = new GetTarjetasTask();
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaData.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tablaData.itemsProperty().unbind();
//                            System.out.println("TERMINO TAREA");
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = tarjetas.size() % rowsPerPage() == 0 ? tarjetas.size() / rowsPerPage() : tarjetas.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {
                                    //ObservableList<modelSaldoTarjeta> tarjetastemp = FXCollections.observableArrayList();
                                    //tarjetastemp.addAll(tarjetas);
                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = tarjetas.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = tarjetas.size() / rowsPerPage();
                                        } else {
                                            lastIndex = tarjetas.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<modelSaldoTarjeta> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<modelSaldoTarjeta> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            }

                                            panel_tabla.getChildren().clear();
                                            tablaData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                            panel_tabla.getChildren().add(tablaData);
                                            panel_tabla.setVisible(true);
                                        }
                                        return panel_tabla;
                                    }
                                }
                            });
//

                            pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                @Override
                                public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                    currentpageindex = newValue.intValue();
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            tablaData.getItems().setAll(tarjetas.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= tarjetas.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : tarjetas.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(5).setLayoutX(3);
                            root.getChildren().get(5).setLayoutY(50);
                            pagination.setVisible(true);
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



                                    arbol_saldos.getSelectionModel().clearSelection();
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
//                            System.out.println("cancelo la tarea");
                        }
                    });



                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }


            }
        });


    }

    public class GetTarjetasTask extends Service<ObservableList<modelSaldoTarjeta>> {

        @Override
        protected Task<ObservableList<modelSaldoTarjeta>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarTDC = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    tramaListarTDC.append("850,005,");
                    tramaListarTDC.append(datosCliente.getRefid());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(AtlasConstantes.COD_COD_CONS_TARJ);
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getId_cliente());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(formatoFecha.format(fecha));
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(formatoHora.format(fecha));
                    tramaListarTDC.append(",");
                    tramaListarTDC.append("CRE");
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getContraseña());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getTokenOauth());
                    tramaListarTDC.append(",~");



                    if (MarcoPrincipalController.newConsultaBloqTDc) {
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());
                           
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                            //850,005,000,TRANSACCIÓN EXITOSA,~

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ConsultaSaldoTDCController.cancelartarea.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            failed();

                                        }
                                    }
                                });

                            }

                        }

                    } else {
                        Respuesta = "850,"
                                + "002,"
                                + "000,"
                                + ",~";

                    }


                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (ConsultaSaldoTDCController.cancelartarea.get()) {
                            cancel();
                        } else {

                            if (MarcoPrincipalController.newConsultaBloqTDc) {
                                final ObservableList<modelSaldoTarjeta> emptyObservableList = FXCollections.emptyObservableList();
                                //  tablaDatos.setItems(emptyObservableList);
                                tarjetas = FXCollections.observableArrayList();

                                String[] Ltarjetas = Respuesta.split(",")[4].split(";");

                                for (int i = 0; i < Ltarjetas.length; i++) {
                                    String[] datos = Ltarjetas[i].split("##");
                                    final modelSaldoTarjeta tarjeta = new modelSaldoTarjeta(datos[0], datos[1], datos[2]);
                                    tarjetas.add(tarjeta);
                                }
                            }


//                            for (int i = 0; i < 850; i++) {
//                                final modelSaldoTarjeta tarjeta = new modelSaldoTarjeta("2222145875845825", "Master Card Clasica", "A");
//                                final modelSaldoTarjeta tarjeta2 = new modelSaldoTarjeta("14587845825", "Visa Clasica", "A");
//                                tarjetas.add(tarjeta);
//                                tarjetas.add(tarjeta2);
//                            }


                        }


                    } else {

                        if (ConsultaSaldoTDCController.cancelartarea.get()) {
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

                    // updateProgress(500, 500);





                    return tarjetas;

                }
            };
        }
    }

    public Service<Void> continuar_Op() {
        serviceSaldoCreditoTDC = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaSaldoTDC = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        final Date fecha = new Date();

                        final modelSaldoTarjeta seleccion = tablaDatos.getSelectionModel().getSelectedItem();

  //    850,006,connid,0260,100020,20200413,112605,4099840360511755,,ca56ad9a-ccd9-4c8f-b8eb-881677ug8961,~

                        tramaSaldoTDC.append("850,006,");
                        tramaSaldoTDC.append(cliente.getRefid());
                        tramaSaldoTDC.append(",");
                        tramaSaldoTDC.append(AtlasConstantes.COD_SALDO_TDC_MQ);
                        tramaSaldoTDC.append(",");
                        tramaSaldoTDC.append(cliente.getId_cliente());
                        tramaSaldoTDC.append(",");
                        tramaSaldoTDC.append(formatoFecha.format(fecha));
                        tramaSaldoTDC.append(",");
                        tramaSaldoTDC.append(formatoHora.format(fecha));
                        tramaSaldoTDC.append(",");
                        tramaSaldoTDC.append(seleccion.getNumeroTarjeta());
                        tramaSaldoTDC.append(",");
                        tramaSaldoTDC.append(cliente.getContraseña());
                        tramaSaldoTDC.append(",");
                        tramaSaldoTDC.append(cliente.getTokenOauth());
                        tramaSaldoTDC.append(",~");




                        /**
                         * 900, 009, codresp, DESCRIPCION, NUMOBLIGACION,
                         * numero_cuota, tipo_prestamo, saldo_capitalent,
                         * saldo_capital_cent, saldo_intereses_ent,
                         * saldo_interesescent, saldo_moraEnt, Saldo_mora_Cent,
                         * Saldo_seguros_Ent, Saldo_seguros_Cent,
                         * Otros_Saldos_Ent, Otros_Saldos_Cent, Saldo_Total_Ent,
                         * Saldo_Total_Cent, fecha_vencimiento, num_prox_cuota,
                         * fecha_prox_cuota, Cuota_sinSeguro_Ent,
                         * Cuota_sin_seguro_Cent, num_cuotas_mora, PLAN,
                         * tasa_interes, Valor_cuota_Ent, Valor_cuota_Cent,
                         * Valor_inicial_Ent, Valor_inicial_Cent,
                         * fecha_desmbolso, indicador_mora, ~
                         *
                         */
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ SALDO TDC = " + "##" + gestorDoc.obtenerHoraActual());
//                            Respuesta = "900,"
//                                    + "020,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "12548578954785451,"
//                                    + "2,"
//                                    + "Tipo_prestamo,"
//                                    + "98578258,"
//                                    + "12,"
//                                    + "235852,"
//                                    + "00,"
//                                    + "0,"
//                                    + "00,"
//                                    + "1253685,"
//                                    + "05,"
//                                    + "125256,"
//                                    + "25,"
//                                    + "1378941,"
//                                    + "03,"
//                                    + "15022015,"
//                                    + "3,"
//                                    + "15032015,"
//                                    + "1253652,"
//                                    + "12,"
//                                    + "0,"
//                                    + "Cuota Fija en Pesos - Vivienda,"
//                                    + "5.2,"
//                                    + "1452365,"
//                                    + "00,"
//                                    + "150000000,"
//                                    + "00,"
//                                    + "12032013,"
//                                    + "D,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaSaldoTDC.toString());
                            //850,006,000,TRANSACCIÓN EXITOSA,~                            
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ SALDO TDC  = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaSaldoTDC.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ConsultaSaldoTDCController.cancelartareaSaldo.get()) {

                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_TDC);
                                            continuar_op.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);
                                        }
                                    }
                                });
                            }
                        }

                        if ("000".equals(Respuesta.split(",")[2])) {
                            final DatosSaldoTdc saldotdc = DatosSaldoTdc.getDatosSaldoTdc();
                            saldotdc.setPagoMinPesos(Respuesta.split(",")[4]);
                            saldotdc.setPagoMinDolares(Respuesta.split(",")[5]);
                            saldotdc.setPagoTotPesos(Respuesta.split(",")[6]);
                            saldotdc.setPagoTotDolares(Respuesta.split(",")[7]);
                            saldotdc.setCupoDispAvances(Respuesta.split(",")[8]);
                            saldotdc.setCupoDispTotal(Respuesta.split(",")[9]);
                            saldotdc.setCupoTotal(Respuesta.split(",")[10]);
                            saldotdc.setDeudaFechaPesos(Respuesta.split(",")[11]);
                            saldotdc.setDeudaFechaDolares(Respuesta.split(",")[12]);
                            saldotdc.setAutPendientesPesos(Respuesta.split(",")[13]);
                            saldotdc.setAutPendientesDolares(Respuesta.split(",")[14]);
                            saldotdc.setPagosPendientesPesos(Respuesta.split(",")[15]);
                            saldotdc.setPagosPendientesDolares(Respuesta.split(",")[16]);
                            saldotdc.setFechalimitePago(Respuesta.split(",")[17]);
                            saldotdc.setCicloFacturacion(Respuesta.split(",")[18]);
                            saldotdc.setFechaFacturacion(Respuesta.split(",")[19]);
                            saldotdc.setSobreCupo(Respuesta.split(",")[20]);
                            saldotdc.setBloqueoTarjeta(Respuesta.split(",")[21]);
                            saldotdc.setNumCuenta(Respuesta.split(",")[22]);
                            saldotdc.setEstadoCuenta1(Respuesta.split(",")[23]);
                            saldotdc.setEstadoCuenta2(Respuesta.split(",")[24]);
                            saldotdc.setEstadoCuenta3(Respuesta.split(",")[25]);
                            saldotdc.setPagoMinimoAlternativo(Respuesta.split(",")[26]);

                            DatosSaldoTdc.setDatosSaldoh(saldotdc);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {



                                    final ConsultaSaldoTDCfinController controller = new ConsultaSaldoTDCfinController();
                                    controller.mostrarSaldoTDCfin(DatosSaldoTdc.getDatosSaldoTdc(), seleccion);

                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ConsultaSaldoTDCController.cancelartareaSaldo.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_TDC);
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                }
                            });

                        }



                        return null;
                    }
                };
            }
        };



        return serviceSaldoCreditoTDC;

    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
