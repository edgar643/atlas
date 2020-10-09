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
import com.co.allus.modelo.bolsillos.datosCuentasBolsillos;
import com.co.allus.modelo.bolsillos.datosListarBolsillos;
import com.co.allus.modelo.bolsillos.mapeoListarBolsillos;
import com.co.allus.modelo.bolsillos.modeloListarBolsillos;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author roberto.ceballos
 */
public class BolsillosListarController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button movimientos_trx;

    @FXML
    private Button detalle_trx;

    @FXML
    private Button regresar_trx;
    
    @FXML
    private Button cancelar_trx;

    @FXML
    private Label mensaje_cuentanumero;

    @FXML
    private TableColumn<modeloListarBolsillos, String> nombre_bolsillo;

    @FXML
    private TableColumn<modeloListarBolsillos, String> estado_bolsillo;

    @FXML
    private TableColumn<modeloListarBolsillos, String> saldo_bolsillo;

    @FXML
    private HBox menu_progreso;

    @FXML
    private StackPane panel_tabla;

    @FXML
    private ProgressBar progreso;

    @FXML
    private TableView<modeloListarBolsillos> tablaDatos;

    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<modeloListarBolsillos>> task = new BolsillosListarController.GetBolsillosTask();
    public static ObservableList<modeloListarBolsillos> bolsillo = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    final GestorDocumental docs = new GestorDocumental();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancelar_trx != null : "fx:id=\"cancelar_trx\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert detalle_trx != null : "fx:id=\"detalle_trx\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert estado_bolsillo != null : "fx:id=\"estado_bolsillo\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert mensaje_cuentanumero != null : "fx:id=\"mensaje_cuentanumero\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert movimientos_trx != null : "fx:id=\"movimientos_trx\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert nombre_bolsillo != null : "fx:id=\"nombre_bolsillo\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert regresar_trx != null : "fx:id=\"regresar_trx\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert saldo_bolsillo != null : "fx:id=\"saldo_bolsillo\" was not injected: check your FXML file 'BolsillosListar.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'BolsillosListar.fxml'.";

        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {
                    movimientos_trx.setDisable(false);
                    detalle_trx.setDisable(false);
                } else {
                    movimientos_trx.setDisable(true);
                    detalle_trx.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });
        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nombre_bolsillo.setCellValueFactory(new PropertyValueFactory<modeloListarBolsillos, String>("nombreBolsillo"));
        estado_bolsillo.setCellValueFactory(new PropertyValueFactory<modeloListarBolsillos, String>("estadoBolsillo"));
        saldo_bolsillo.setCellValueFactory(new PropertyValueFactory<modeloListarBolsillos, String>("saldoBolsillo"));

        BolsillosListarController.cancelartarea.set(false);
    }    
   
    public void mostrarListarBolsillo(final datosCuentasBolsillos data) {
    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            try {

                final URL location = getClass().getResource("/com/co/allus/vistas/BolsillosListar.fxml");
                final FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                final Button movimientos_op = (Button) root.lookup("#movimientos_trx");
                final Button detalle_op = (Button) root.lookup("#detalle_trx");
                final Button cancelar_op = (Button) root.lookup("#cancelar_trx");
                final Label label_menu = (Label) pane.lookup("#label_saldos");

                final TableView<modeloListarBolsillos> tablaDatos = (TableView<modeloListarBolsillos>) root.lookup("#tablaDatos");
                final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                /// codigo para inyectar los datos                   
                final Cliente datosCliente = Cliente.getCliente();
                final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                cliente.setText("");
                cliente.setText(info);
                final Label mensaje = (Label) root.lookup("#mensaje_cuentanumero");
                mensaje.setText(data.getNumeroCuenta());

                // cliente.setTextAlignment(TextAlignment.JUSTIFY);

                /**
                 * barra progreso
                 */
                final Region veil = new Region();
                veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                final ProgressIndicator p = new ProgressIndicator();
                p.setMaxSize(150, 150);
                panel_tabla.getChildren().addAll(veil, p);

                final DropShadow shadow = new DropShadow();
                movimientos_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(final MouseEvent event) {
                                movimientos_op.setCursor(Cursor.HAND);
                                movimientos_op.setEffect(shadow);
                            }
                        });

                movimientos_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(final MouseEvent event) {
                                movimientos_op.setCursor(Cursor.DEFAULT);
                                movimientos_op.setEffect(null);
                            }
                        });

                detalle_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(final MouseEvent event) {
                                detalle_op.setCursor(Cursor.HAND);
                                detalle_op.setEffect(shadow);
                            }
                        });

                detalle_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(final MouseEvent event) {
                                detalle_op.setCursor(Cursor.DEFAULT);
                                detalle_op.setEffect(null);
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

                movimientos_op.setDisable(true);
                detalle_op.setDisable(true);
                label_menu.setVisible(false);

                final TreeView<String> arbolBolsillos = (TreeView<String>) pane.lookup("#arbolBolsillos");
                if (arbolBolsillos != null) {
                    arbolBolsillos.setDisable(false);
                }
                arbolBolsillos.getSelectionModel().clearSelection();

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
                pane.setAlignment(Pos.CENTER_LEFT);
                pane.add(root, 1, 0);

                p.progressProperty().bind(task.progressProperty());
                veil.visibleProperty().bind(task.runningProperty());
                p.visibleProperty().bind(task.runningProperty());
                tablaDatos.itemsProperty().bind(task.valueProperty());
                task.start();

                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {

                        tablaDatos.itemsProperty().unbind();
                        /**
                         * configuracion de la paginacion
                         */
                        final int numpag = bolsillo.size() % rowsPerPage() == 0 ? bolsillo.size() / rowsPerPage() : bolsillo.size() / rowsPerPage() + 1;

                        pagination = new Pagination(numpag, 0);
                        pagination.setPageFactory(new Callback<Integer, Node>() {
                            @Override
                            public Node call(final Integer pageIndex) {

                                if (pageIndex > numpag) {
                                    return null;
                                } else {
                                    int lastIndex = 0;
                                    int displace = bolsillo.size() % rowsPerPage();
                                    if (displace >= 0) {
                                        lastIndex = bolsillo.size() / rowsPerPage();
                                    } else {
                                        lastIndex = bolsillo.size() / rowsPerPage() - 1;
                                    }
                                    int page = pageIndex * itemsPerPage();

                                    for (int i = page; i < page + itemsPerPage(); i++) {

                                        if (lastIndex == pageIndex) {
                                            final List<modeloListarBolsillos> subList = bolsillo.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                            tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                        } else {
                                            final List<modeloListarBolsillos> subList = bolsillo.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
                                            tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                        }

                                        panel_tabla.getChildren().clear();
                                        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                        panel_tabla.getChildren().add(tablaDatos);
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
                                        tablaDatos.getItems().setAll(bolsillo.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= bolsillo.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : bolsillo.size())));
                                    }
                                });
                            }
                        });

                        /**
                         * fin configuracion de la paginacion
                         */
                        root.getChildren().add(pagination);
                        root.getChildren().get(root.getChildren().size() - 1).setLayoutX(15);
                        root.getChildren().get(root.getChildren().size() - 1).setLayoutY(80);
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

                                final TreeView<String> arbolBolsillos = (TreeView<String>) pane.lookup("#arbolBolsillos");
                                if (arbolBolsillos != null) {
                                    arbolBolsillos.setDisable(false);
                                }
                                arbolBolsillos.getSelectionModel().clearSelection();
                                
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
                      gestorDoc.imprimir("Cancelo tarea Bolsillos -->" + "  :" + gestorDoc.obtenerHoraActual());
                    }
                });

            } catch (Exception e) {
                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
            }
        }
    });
    }
 
    public class GetBolsillosTask extends Service<ObservableList<modeloListarBolsillos>> {

        @Override
        protected Task<ObservableList<modeloListarBolsillos>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarBolsillos = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();
                    final datosCuentasBolsillos seleccion = datosCuentasBolsillos.getDataCuentasBolsillos();
                    
                    tramaListarBolsillos.append("850,079,");
                    tramaListarBolsillos.append(datosCliente.getRefid());
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append(AtlasConstantes.COD_BOLSILLOS_LISTAR);
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append(datosCliente.getId_cliente());
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append("1");
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append("50");
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append(seleccion.getTipoCuenta());
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append(seleccion.getNumeroCuenta());
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append("");
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append("");
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append("");
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append(datosCliente.getContraseña());
                    tramaListarBolsillos.append(",");
                    tramaListarBolsillos.append(datosCliente.getTokenOauth());
                    tramaListarBolsillos.append(",~");

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listar bolsillos = " + "##" + gestorDoc.obtenerHoraActual());
                            /*850,079,000,0901 - TRANSACCION EXITOSA,001,N,S%33186551929%331865519291
                            %Mi casa propia%0006%A%00000000%00000000%S%M%30%20200130%20311130%00000000
                            %00000001500000000000%00000000010489510000%00000000043468530000%20200103home,~ */
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarBolsillos.toString());
                       

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Listar Bolsillo = " + "##" + gestorDoc.obtenerHoraActual());
                                 /*850,079,000,0901 - TRANSACCION EXITOSA,001,N,S%33186551929%331865519291
                            %Mi casa propia%0006%A%00000000%00000000%S%M%30%20200130%20311130%00000000
                            %00000001500000000000%00000000010489510000%00000000043468530000%20200103home,~ */
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarBolsillos.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (BolsillosListarController.cancelartarea.get()) {
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
                        if (BolsillosListarController.cancelartarea.get()) {
                            cancel();
                        } else {
                            if (MarcoPrincipalController.newConsultaBolsillosListar) {
                                final ObservableList<modeloListarBolsillos> emptyObservableList = FXCollections.emptyObservableList();
                                //  tablaDatos.setItems(emptyObservableList);
                                bolsillo = FXCollections.observableArrayList();

                                String[] Lbolsillo = Respuesta.split(",")[6].split(";");

                                for (int i = 0; i < Lbolsillo.length; i++) {
                                    String[] datos = Lbolsillo[i].split("%");
                                    String EstadoBolsillo = mapeoListarBolsillos.mapeoListarBolsillos.get(datos[5]);
                                    String SaldoBolsillo = "$ " + formatonum.format(Double.parseDouble(datos[16].substring(0, datos[16].length() - 4))).replace(".", ",") + "." + datos[16].substring(datos[16].length() - 4, datos[16].length()).substring(0,2);
                                    final modeloListarBolsillos cuenta = new modeloListarBolsillos(datos[0], datos[1], datos[2], datos[3], datos[4], EstadoBolsillo, datos[6], datos[7], datos[8], datos[9], datos[10], datos[11], datos[12], datos[13], datos[14], datos[15], SaldoBolsillo);
                                    bolsillo.add(cuenta);
                                }
                            }
                        }
                    } else {

                        if (BolsillosListarController.cancelartarea.get()) {
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
                    return bolsillo;
                }
            };
        }
    }
    
    @FXML
    void movimientos_op(ActionEvent event) {
        try {
            //Declinacion1
            final modeloListarBolsillos seleccion = tablaDatos.getSelectionModel().getSelectedItem();
            final datosListarBolsillos dataListarBolsillos = datosListarBolsillos.getDataListarBolsillos();
            dataListarBolsillos.setTipoCuenta(seleccion.getTipoCuenta());
            dataListarBolsillos.setNumeroCuenta(seleccion.getNumeroCuenta());
            dataListarBolsillos.setNumeroBolsillo(seleccion.getNumeroBolsillo());
            dataListarBolsillos.setNombreBolsillo(seleccion.getNombreBolsillo());
            datosListarBolsillos.setDataListarBolsillos(dataListarBolsillos);
            final BolsillosMovimientosController controller = new BolsillosMovimientosController();
            controller.mostrarMovimientosBolsillo(datosListarBolsillos.getDataListarBolsillos(), 0);
        } catch (Exception ex) {
            docs.imprimir("Error en la aplicacion Listar Bolsillos -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
        }
    }

    @FXML
    void detalle_op(ActionEvent event) {
        try {
            final modeloListarBolsillos seleccion = tablaDatos.getSelectionModel().getSelectedItem();
            final datosListarBolsillos dataListarBolsillos = datosListarBolsillos.getDataListarBolsillos();
            dataListarBolsillos.setTipoCuenta(seleccion.getTipoCuenta());
            dataListarBolsillos.setNumeroCuenta(seleccion.getNumeroCuenta());
            dataListarBolsillos.setNumeroBolsillo(seleccion.getNumeroBolsillo());
            dataListarBolsillos.setNombreBolsillo(seleccion.getNombreBolsillo());
            datosListarBolsillos.setDataListarBolsillos(dataListarBolsillos);
            final BolsillosDetalleController controller = new BolsillosDetalleController();
            controller.mostrarDetalleBolsillo(datosListarBolsillos.getDataListarBolsillos());
        } catch (Exception ex) {
            docs.imprimir("Error en la aplicacion Listar Bolsillos -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
        }
    }

    @FXML
    void regresar_op(ActionEvent event) {
            try {
                final BolsillosCuentasController controller = new BolsillosCuentasController();
                controller.mostrarCuentasBolsillo("", true);
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion Listar Bolsillos -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
            }
    }
    
    @FXML
    void cancelar_op(ActionEvent event) {
        try {
            BolsillosListarController.cancelartarea.set(true);
        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        } finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Atlas.getVista().gotoPrincipal();
                    } catch (IOException ex) {
                        gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
                    }
                }
            });
        }
    }

    public int itemsPerPage() {
        return 1;
    }
    
    public int rowsPerPage() {
        return 10;
    }
    
    private boolean isnumber(String val) {
        boolean retorno = false;

        try {
            Integer.parseInt(val);
            retorno = true;
        } catch (Exception e) {
            retorno = false;
        }
        return retorno;
    }

}
