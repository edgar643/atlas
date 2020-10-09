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
import com.co.allus.modelo.tdcprepago.DatosConsultaGeneral;
import com.co.allus.modelo.tdcprepago.DatosSelectGral;
import com.co.allus.modelo.tpconsultageneral.InfoTablaConsultaGral;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import javafx.event.Event;
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
 * @author stephania.rojas
 */
public class TPConsultaGeneralController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button cancelar_op;
    @FXML
    private TableColumn colEstadoTarjeta;
    @FXML
    private TableColumn colNombreEmpresa;
    @FXML
    private TableColumn colNumtarjeta;
    @FXML
    private TableColumn colTipoTarjeta;
    @FXML
    private Button continuar_op;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TableView<InfoTablaConsultaGral> tabla_datos;

    @FXML
    void cancelar_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                final Label label_menu = (Label) pane.lookup("#label_saldos");

                final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                if (arboltdcPrepago != null) {
                    arboltdcPrepago.setDisable(false);
                }

                arboltdcPrepago.getSelectionModel().clearSelection();
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }



            }
        });

    }
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static ObservableList<InfoTablaConsultaGral> congral = FXCollections.observableArrayList();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private static GestorDocumental docs = new GestorDocumental();
    private Service<ObservableList<InfoTablaConsultaGral>> task = new TPConsultaGeneralController.GetTarjetasTask();
    private transient Service<Void> ServiceConsultaGeneral;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient final SimpleDateFormat formatoFecha2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        assert cancelar_op != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        assert colEstadoTarjeta != null : "fx:id=\"colEstadoTarjeta\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        assert colNombreEmpresa != null : "fx:id=\"colNombreEmpresa\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        assert colNumtarjeta != null : "fx:id=\"colNumtarjeta\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        assert colTipoTarjeta != null : "fx:id=\"colTipoTarjeta\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TPConsultaGeneral.fxml'.";
        this.location = url;
        this.resources = rb;

        colNumtarjeta.setCellValueFactory(new PropertyValueFactory<InfoTablaConsultaGral, String>("col_Numero"));
        colTipoTarjeta.setCellValueFactory(new PropertyValueFactory<InfoTablaConsultaGral, String>("col_TipoTarj"));
        colEstadoTarjeta.setCellValueFactory(new PropertyValueFactory<InfoTablaConsultaGral, String>("col_Bloqueo"));
        colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<InfoTablaConsultaGral, String>("col_Empresa"));

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

        progreso.setVisible(false);
        continuar_op.setDisable(true);

    }

    public void mostrarConsultaGral() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TPConsultaGeneral.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_op");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<InfoTablaConsultaGral> tabla_datos = (TableView<InfoTablaConsultaGral>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    // codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    label_menu.setVisible(false);
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);




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

                    cancelar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_op.setCursor(Cursor.HAND);
                                    cancelar_op.setEffect(shadow);
                                }
                            });

                    cancelar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_op.setCursor(Cursor.DEFAULT);
                                    cancelar_op.setEffect(null);

                                }
                            });



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

                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tabla_datos.itemsProperty().bind(task.valueProperty());
                    task.reset();
                    task.start();

                    /**
                     * barra progreso
                     */
                    // Use binding to be notified whenever the data source chagnes
                    // task = new GetTarjetasTask();
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("ENTRO OK");

                            tabla_datos.itemsProperty().unbind();

                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = congral.size() % rowsPerPage() == 0 ? congral.size() / rowsPerPage() : congral.size() / rowsPerPage() + 1;

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
                                        int displace = congral.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = congral.size() / rowsPerPage();
                                        } else {
                                            lastIndex = congral.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<InfoTablaConsultaGral> subList = congral.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<InfoTablaConsultaGral> subList = congral.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
                                                tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                            }

                                            panel_tabla.getChildren().clear();
                                            tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                            panel_tabla.getChildren().add(tabla_datos);
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
                                            tabla_datos.getItems().setAll(congral.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= congral.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : congral.size())));
                                        }
                                    });
                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(3);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(68);

                            pagination.setVisible(true);
                            Atlas.vista.show();
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("ENTRO FAILED");
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

                                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                                    if (arbol_servadd != null) {
                                        arbol_servadd.setDisable(false);
                                    }

                                    final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                                    if (arboltdcPrepago != null) {
                                        arboltdcPrepago.setDisable(false);
                                    }


                                    arboltdcPrepago.getSelectionModel().clearSelection();
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
//                    e.printStackTrace();
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    public class GetTarjetasTask extends Service<ObservableList<InfoTablaConsultaGral>> {

        @Override
        protected Task<ObservableList<InfoTablaConsultaGral>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarTDC = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramaListarTDC.append("850,065,");
                    tramaListarTDC.append(datosCliente.getRefid());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(AtlasConstantes.COD_COD_CONS_TARJ_PREPAGO);
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getId_cliente());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append("1"); // tipoId
                    tramaListarTDC.append(",");
                    tramaListarTDC.append("N"); // TIPO DE TARJETA
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(AtlasConstantes.COD_CONSULTAS_GNR_PREP); // dinamico
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getContraseña());
                    tramaListarTDC.append(",~");

//                    System.out.println("trama listarTarjetas" + tramaListarTDC.toString());


                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listar prepagos = " + "##" + docs.obtenerHoraActual());
//                            Respuesta = "850,"
//                                    + "002,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "N,"
//                                    + "354852685895589##"
//                                    + "NOMINA##"
//                                    + "A##+ "EMPRESA 1;"
//                                    + "452685785459825962##"
//                                    + "NOMINA##"
//                                    + "A##"
//                                    + "NOMBRE EMPRESA 2;"
//                                    + "5698521458525932##"
//                                    + "NOMINA##"
//                                    + "B##"
//                                    + "NOMBRE EMPRESA TRES;"
//                                    + ",~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
//                        System.out.println("Respuesta listar prepago: " + Respuesta);

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " +StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+ "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listar prepago = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (TPConsultaGeneralController.cancelartarea.get()) {
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

                        if (TPConsultaGeneralController.cancelartarea.get()) {
                            cancel();
                        } else {



                            //  tablaDatos.setItems(emptyObservableList);

                            congral.clear();
                            final String[] Ltarjetas = Respuesta.split(",")[5].split(";");



                            for (int i = 0; i < Ltarjetas.length; i++) {

                                String[] datos = Ltarjetas[i].split("##");

                                String tarjetaPIN = datos[0].substring(0, 6) + StringUtilities.rellenarDato(" ", datos[0].length() - 10, "*") + datos[0].substring(datos[0].length() - 4, datos[0].length());

                                final InfoTablaConsultaGral tarjeta = new InfoTablaConsultaGral(
                                        tarjetaPIN,
                                        //datos[0].trim(),
                                        datos[1].trim().toLowerCase(),
                                        datos[2].trim(),
                                        datos[3].trim().toLowerCase(),
                                        datos[0].trim());
                                congral.add(tarjeta);
                            }

                        }

                    } else {

                        if (TPConsultaGeneralController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCIÓN" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                }
                            });
                            throw new Exception("ERROR");
                        }
                    }

                    return congral;

                }
            };
        }
    }

    @FXML
    void continuar_op(ActionEvent event) {
        final InfoTablaConsultaGral selectedItem = tabla_datos.getSelectionModel().getSelectedItem();

        DatosSelectGral datosGral = DatosSelectGral.getDataGral();
        datosGral.setNumTarj(selectedItem.col_NumeroProperty().getValue());
        datosGral.setNumTarjsf(selectedItem.valorsinforProperty().getValue());
        datosGral.setTipoTarj(selectedItem.col_TipoTarjProperty().getValue());

        DatosSelectGral.setDataGral(datosGral);



        continuar_op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Saldo Credito Hipotecario" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Saldo Credito Hipotecario" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_op().isRunning()) {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_op().progressProperty());
            continuar_op().reset();
            continuar_op().start();

        } else {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_op().progressProperty());
            continuar_op().start();
        }


    }

    public Service<Void> continuar_op() {
        ServiceConsultaGeneral = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaconsultagral = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

//                        850,070,connid,7007,docid,tipoTarjeta,numtarjeta,clavehardware,~

                        tramaconsultagral.append("850,070,");
                        tramaconsultagral.append(cliente.getRefid());
                        tramaconsultagral.append(",");
                        tramaconsultagral.append(AtlasConstantes.COD_CONSULTA_GENERAL);
                        tramaconsultagral.append(",");
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaconsultagral.append(cliente.getId_cliente());
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaconsultagral.append(cliente.getNitEmpresa());
                        }
                        tramaconsultagral.append(",");
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaconsultagral.append("1"); // tipoID
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaconsultagral.append("3"); //tipoID
                        }
                        tramaconsultagral.append(",");
                        tramaconsultagral.append(Cliente.getCliente().getTipoClientePrepago());
                        tramaconsultagral.append(",");
                        tramaconsultagral.append(DatosSelectGral.getDataGral().getNumTarjsf());
                        tramaconsultagral.append(",");
                        tramaconsultagral.append(cliente.getContraseña());
                        tramaconsultagral.append(",~");

//                        System.out.println("TRAMA CONSULTA GENERAL :" + tramaconsultagral);



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ consulta gnral prepago = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaconsultagral.toString());
                            //Respuesta="850;070;000;                                                                      ;N;0004198190021156289;EL CARIBE                     ;4198194066258044366;-6,020.00            ; 6,020.00            ;VISA PP REGALO                ;31;31/05/2017;E; ; ;20/12/2016;Y;0000000000;N;0000000000; .00                 ;0000000000; .00                 ;0;~";
//                            System.out.println(" RESPUESTA TRAMA CONSULTA GENERAL:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ consulta gnral prepago= " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaconsultagral.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }
//                        System.out.println(Respuesta.split(";")[2]);
                        try {
                            if ("000".equals(Respuesta.split(";")[2])) {
//                                System.out.println("entro aca");
                                final DatosConsultaGeneral infoConsultagral = DatosConsultaGeneral.getDatosConsultaGral();

//                            tipotarjeta=""; 
//                            numtarjeta=""; 
//                            nombreEmpresa=""; 
//                            nunCuenta=""; 
//                            saldoDispo=""; 
//                            saldoDiaAnterior=""; 
//                            descProducto=""; 
//                            cicloFact=""; 
//                            fechaFacturacion=""; 
//                            bloqueoTarjeta=""; 
//                            bloqueoCuenta1=""; 
//                            bloqueoCuenta2=""; 
//                            fechaActivacion=""; 
//                            indicadorActivacion=""; 
//                            fechaActivacion=""; 
//                            indicadorActivacionViejo=""; 
//                            fechaUltimaRecarga=""; 
//                            valorUltimaRecarga=""; 
//                            fechaUltimaDescarga=""; 
//                            valorUltimaDescarga=""; 
//                            excentoGMF=""; 
                                infoConsultagral.setTipotarjeta(Respuesta.split(";")[4].trim());
                                // infoConsultagral.setNumtarjeta(StringUtilities.eliminarCerosLeft(Respuesta.split(";")[5].trim()));
                                infoConsultagral.setNumtarjeta(DatosSelectGral.getDataGral().getNumTarj());
                                infoConsultagral.setNombreEmpresa(Respuesta.split(";")[6].trim());
                                infoConsultagral.setNumCuenta(Respuesta.split(";")[7].trim());
                                infoConsultagral.setSaldoDispo("$" + Respuesta.split(";")[8].trim());
                                infoConsultagral.setSaldoDiaAnterior("$" + Respuesta.split(";")[9].trim());
                                infoConsultagral.setDescProducto(Respuesta.split(";")[10].trim());
                                infoConsultagral.setCicloFact(Respuesta.split(";")[11].trim());
//                                System.out.println("entro aca 2");
                                try {

                                    infoConsultagral.setFechaFacturacion(Respuesta.split(";")[12]);
                                } catch (Exception e) {
                                    infoConsultagral.setFechaFacturacion(Respuesta.split(";")[12]);
                                }



                                infoConsultagral.setBloqueoTarjeta(Respuesta.split(";")[13].trim());
                                infoConsultagral.setBloqueoCuenta1(Respuesta.split(";")[14].trim());
                                infoConsultagral.setBloqueoCuenta2(Respuesta.split(";")[15].trim());

                                try {

                                    infoConsultagral.setFechaActivacion(Respuesta.split(";")[16]);
                                } catch (Exception e) {
                                    infoConsultagral.setFechaActivacion(Respuesta.split(";")[16]);
                                }


                                infoConsultagral.setIndicadorActivacion(Respuesta.split(";")[17].trim());
                                infoConsultagral.setFechaActivacionV(Respuesta.split(";")[18].trim());
                                infoConsultagral.setIndicadorActivacionViejo(Respuesta.split(";")[19].trim());

                                try {

                                    infoConsultagral.setFechaUltimaRecarga(Respuesta.split(";")[20]);
                                } catch (Exception e) {
                                    infoConsultagral.setFechaUltimaRecarga(Respuesta.split(";")[20]);
                                }



                                infoConsultagral.setValorUltimaRecarga("$" + Respuesta.split(";")[21]);

                                try {

                                    infoConsultagral.setFechaUltimaDescarga(Respuesta.split(";")[22]);
                                } catch (Exception e) {
                                    infoConsultagral.setFechaUltimaDescarga(Respuesta.split(";")[22]);
                                }


                                infoConsultagral.setValorUltimaDescarga("$" + Respuesta.split(";")[23]);

                                infoConsultagral.setExcentoGMF(Respuesta.split(";")[24].trim());



                                DatosConsultaGeneral.setDatosConsultaGral(infoConsultagral);



                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final TPConsultaGeneralFinController controller = new TPConsultaGeneralFinController();
                                        controller.mostrarConsultaGralFin(DatosConsultaGeneral.getDatosConsultaGral());
                                    }
                                });

                            } else {
                                final String coderror = Respuesta.split(";")[2];
                                final String mensaje = Respuesta.split(";")[3].trim();

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        cancel();
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                });

                            }
                        } catch (Exception e) {
                            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                        }

                        return null;
                    }
                };
            }
        };

        return ServiceConsultaGeneral;

    }
}
