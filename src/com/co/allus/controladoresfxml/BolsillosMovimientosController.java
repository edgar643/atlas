/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.bolsillos.datosCuentasBolsillos;
import com.co.allus.modelo.bolsillos.datosListarBolsillos;
import com.co.allus.modelo.bolsillos.mapeoMovimientosBolsillos;
import com.co.allus.modelo.bolsillos.modeloMovimientosBolsillos;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BolsillosMovimientosController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button buscarFechas;
    @FXML
    private Button indMasReg;
    @FXML
    private Button cancelar_trx;
    @FXML
    private Button detalle_trx;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private Label mensaje_movimientobolsillo;
    @FXML
    private HBox menu_progreso;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Button regresar_trx;
    @FXML
    private TableView<modeloMovimientosBolsillos> tablaDatos;
    @FXML
    private TableColumn<modeloMovimientosBolsillos, String> tipo_operacion;
    @FXML
    private TableColumn<modeloMovimientosBolsillos, String> fecha_operacion;
    @FXML
    private TableColumn<modeloMovimientosBolsillos, String> descripcion_operacion;
    @FXML
    private TableColumn<modeloMovimientosBolsillos, String> valor_operacion;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static AtomicInteger numpagina = new AtomicInteger(0);
    private Service<ObservableList<modeloMovimientosBolsillos>> task = new BolsillosMovimientosController.GetMovimientosTask();
    public static ObservableList<modeloMovimientosBolsillos> registros = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechamov = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechamov2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static String indicadorRegistros = "N";
    public static Calendar fechalimite = Calendar.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert buscarFechas != null : "fx:id=\"buscarFechas\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert cancelar_trx != null : "fx:id=\"cancelar_trx\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert descripcion_operacion != null : "fx:id=\"descripcion_operacion\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert detalle_trx != null : "fx:id=\"detalle_trx\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert fecha_operacion != null : "fx:id=\"fecha_operacion\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert mensaje_movimientobolsillo != null : "fx:id=\"mensaje_movimientobolsillo\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert regresar_trx != null : "fx:id=\"regresar_trx\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert tipo_operacion != null : "fx:id=\"tipo_operacion\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";
        assert valor_operacion != null : "fx:id=\"valor_operacion\" was not injected: check your FXML file 'BolsillosMovimientos.fxml'.";

        this.resources = rb;
        this.location = url;

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaDatos.setPlaceholder(new Label("No se ha realizado ninguna consulta"));

        tipo_operacion.setCellValueFactory(new PropertyValueFactory<modeloMovimientosBolsillos, String>("tipoOperacion"));
        fecha_operacion.setCellValueFactory(new PropertyValueFactory<modeloMovimientosBolsillos, String>("fechaOrigen"));
        descripcion_operacion.setCellValueFactory(new PropertyValueFactory<modeloMovimientosBolsillos, String>("descripcionMovimiento"));
        valor_operacion.setCellValueFactory(new PropertyValueFactory<modeloMovimientosBolsillos, String>("valorTransaccion"));

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);

        final Calendar instance = Calendar.getInstance();
        fechafin.setSelectedDate(instance.getTime());
        instance.add(Calendar.DAY_OF_MONTH, -365);
        fechaini.setSelectedDate(instance.getTime());
        setFechalimite(instance);

        indMasReg.setDisable(true);
        indMasReg.setVisible(false);
        //buscarFechas.setDisable(true);
        progreso.setVisible(false);

        numpagina.set(0);
        cancelartarea.set(false);
        setIndicadorRegistros("N");
    }

    public void mostrarMovimientosBolsillo(final datosListarBolsillos data, final int origen) {

        if (origen == 0) {
            BolsillosMovimientosController.registros.clear();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/BolsillosMovimientos.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button botonbuscar = (Button) root.lookup("#buscarFechas");
                    final Button masRegistros = (Button) root.lookup("#indMasReg");
                    final Button detalle_op = (Button) root.lookup("#detalle_trx");
                    final Button regresar_op = (Button) root.lookup("#regresar_trx");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_trx");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final TableView<modeloMovimientosBolsillos> tablaDatos = (TableView<modeloMovimientosBolsillos>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    final Label mensaje = (Label) root.lookup("#mensaje_movimientobolsillo");
                    mensaje.setText(data.getNombreBolsillo());

                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);
                    /**
                     * barra progreso
                     */
                    if ("S".equals(getIndicadorRegistros())) {
                        masRegistros.setDisable(false);
                    } else {
                        masRegistros.setDisable(true);
                    }

                    final DropShadow shadow = new DropShadow();

                    masRegistros.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            masRegistros.setCursor(Cursor.HAND);
                            masRegistros.setEffect(shadow);
                        }
                    });

                    masRegistros.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            masRegistros.setCursor(Cursor.DEFAULT);
                            masRegistros.setEffect(null);
                        }
                    });

                    botonbuscar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            botonbuscar.setCursor(Cursor.HAND);
                            botonbuscar.setEffect(shadow);
                        }
                    });

                    botonbuscar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            botonbuscar.setCursor(Cursor.DEFAULT);
                            botonbuscar.setEffect(null);
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

                    regresar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            regresar_op.setCursor(Cursor.HAND);
                            regresar_op.setEffect(shadow);
                        }
                    });

                    regresar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            regresar_op.setCursor(Cursor.DEFAULT);
                            regresar_op.setEffect(null);
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

                    if (origen == 1) {
                        /**
                         * barra progreso
                         */
                        Region veil = new Region();
                        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                        ProgressIndicator p = new ProgressIndicator();
                        p.setMaxSize(150, 150);
                        panel_tabla.getChildren().addAll(veil, p);

                        // Use binding to be notified whenever the data source chagnes
                        // task = new GetTarjetasTask();
                        p.progressProperty().bind(task.progressProperty());
                        veil.visibleProperty().bind(task.runningProperty());
                        p.visibleProperty().bind(task.runningProperty());
                        tablaDatos.itemsProperty().bind(task.valueProperty());
                        task.start();

                        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {

                                tablaDatos.itemsProperty().unbind();
//                                System.out.println("TERMINO TAREA");
                                /**
                                 * configuracion de la paginacion
                                 */
                                // final ObservableList<modeloListarTarjeta> registros = task.getValue();
                                final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;

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
                                            int displace = registros.size() % rowsPerPage();
                                            if (displace >= 0) {
                                                lastIndex = registros.size() / rowsPerPage();
                                            } else {
                                                lastIndex = registros.size() / rowsPerPage() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<modeloMovimientosBolsillos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<modeloMovimientosBolsillos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                                pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                    @Override
                                    public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                        currentpageindex = newValue.intValue();
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                tablaDatos.getItems().setAll(registros.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= registros.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : registros.size())));
                                            }
                                        });

                                    }
                                });

                                /**
                                 * fin configuracion de la paginacion
                                 */
                                root.getChildren().add(pagination);
                                root.getChildren().get(root.getChildren().size() - 1).setLayoutX(15);
                                root.getChildren().get(root.getChildren().size() - 1).setLayoutY(110);
                                pagination.setVisible(true);
                                Atlas.vista.show();
                            }
                        });

                        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
//                                System.out.println("ERROR EN LA CONSULTA");
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

                                    }
                                });
                            }
                        });

                        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
                            }
                        });

                    } else {

                        if (!registros.isEmpty()) {

                            final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;

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
                                        int displace = registros.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = registros.size() / rowsPerPage();
                                        } else {
                                            lastIndex = registros.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<modeloMovimientosBolsillos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<modeloMovimientosBolsillos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                            pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                @Override
                                public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                    currentpageindex = newValue.intValue();
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            tablaDatos.getItems().setAll(registros.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= registros.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : registros.size())));
                                        }
                                    });
                                }
                            });
                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(15);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(110);
                            pagination.setVisible(true);
                            Atlas.vista.show();
                        } else {
                            try {
                                pane.getChildren().remove(3);

                            } catch (Exception ex) {
                                Logger.getLogger(BolsillosMovimientosController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            pane.setAlignment(Pos.CENTER_LEFT);
                            pane.add(root, 1, 0);
                            Atlas.vista.show();
                        }
                    }

                } catch (Exception e) {
                    Logger.getLogger(BolsillosMovimientosController.class.getName()).log(Level.SEVERE, null, e);

                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });
    }

    public class GetMovimientosTask extends Service<ObservableList<modeloMovimientosBolsillos>> {

        @Override
        protected Task<ObservableList<modeloMovimientosBolsillos>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaMovimientosBolsillos = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();
                    final datosListarBolsillos seleccion = datosListarBolsillos.getDataListarBolsillos();

                    tramaMovimientosBolsillos.append("850,081,");
                    tramaMovimientosBolsillos.append(datosCliente.getRefid());
                    tramaMovimientosBolsillos.append(",");
                    tramaMovimientosBolsillos.append(AtlasConstantes.COD_BOLSILLOS_MOVIMIENTO);
                    tramaMovimientosBolsillos.append(",");
                    tramaMovimientosBolsillos.append(datosCliente.getId_cliente());
                    tramaMovimientosBolsillos.append(",");
                    tramaMovimientosBolsillos.append("1");
                    tramaMovimientosBolsillos.append(",");
                    tramaMovimientosBolsillos.append(seleccion.getNumeroCuenta());
                    tramaMovimientosBolsillos.append(",");
                    tramaMovimientosBolsillos.append(seleccion.getNumeroBolsillo());
                    tramaMovimientosBolsillos.append(",");
                    tramaMovimientosBolsillos.append(formatoFecha.format(fechaini.getSelectedDate()));
                    tramaMovimientosBolsillos.append(",");
                    tramaMovimientosBolsillos.append(formatoFecha.format(fechafin.getSelectedDate()));
                    tramaMovimientosBolsillos.append(",");
                    tramaMovimientosBolsillos.append(datosCliente.getContraseña());
                    tramaMovimientosBolsillos.append(",");
                    tramaMovimientosBolsillos.append(datosCliente.getTokenOauth());
                    tramaMovimientosBolsillos.append(",~");

//                    System.out.println("trama movimientos bolsillo " + tramaMovimientosBolsillos.toString());
                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Movimientos bolsillos = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,081,000,TRANSACCION EXITOSA,N,"
//                                    + "D%20190410%TRANSFERENCIA CUENTA01%00000000000700000000;"
//                                    + "B%20190410%TRANSFERENCIA CUENTA02%00000000001500000000;"
//                                    + "D%20190404%TRANSFERENCIA CUENTA03%00000000000800000000;"
//                                    + "B%20190404%TRANSFERENCIA CUENTA04%00000000000100000000;"
//                                    + "B%20190404%TRANSFERENCIA CUENTA05%00000000000100000000;"
//                                    + "B%20190404%TRANSFERENCIA CUENTA06%00000000000200000000;"
//                                    + ",~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaMovimientosBolsillos.toString());
//                            System.out.println("respuesta Movimientos Bolsillo : " + Respuesta);
                        //Thread.sleep(3000);
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",")  + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Movimientos Bolsillo = " + "##" + gestorDoc.obtenerHoraActual());
                            /*
                            850,081,000,0903 - TRANSACCION EXITOSA                                            ,N,
                            D%20200519%TRANSFERENCIA CTA SUC VIRTUAL%00000000003200000000;
                            B%20200519%TRANSFERENCIA CTA SUC VIRTUAL%00000000003000000000;
                            B%20200115%TRANSFERENCIA CTA SUC VIRTUAL%00000000000100000000;
                            B%20200115%TRANSFERENCIA CTA SUC VIRTUAL%00000000000100000000,~ 
                            */
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaMovimientosBolsillos.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " +  StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (BolsillosMovimientosController.cancelartarea.get()) {
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

                        setIndicadorRegistros(Respuesta.split(",")[4]);//Indicador de más registros
//                        setIndicadorRegistros("S");//Indicador de más registros

                        if (Respuesta.split(",")[5].isEmpty()) {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = "EL CLIENTE NO PRESENTA MOVIMIENTOS EN LAS FECHAS SELECCIONADAS";

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                }
                            });
                            throw new Exception("ERROR");
                        } else {
                            String[] Lmovimientos = Respuesta.split(",")[5].split(";");

                            for (int i = 0; i < Lmovimientos.length; i++) {
                                String[] datos = Lmovimientos[i].split("%");

                                String TipoOperacion = mapeoMovimientosBolsillos.mapeoMovimientosBolsillos.get(datos[0]);
                                String ValorTransaccion = "$ " + formatonum.format(Double.parseDouble(datos[3].substring(0, datos[3].length() - 4))).replace(".", ",") + "." + datos[3].substring(datos[3].length() - 4, datos[3].length()).substring(0, 2);
                                String FechaOrigen = formatoFechaSalida.format(formatoFecha.parse(datos[1].trim()));

                                modeloMovimientosBolsillos movimiento = new modeloMovimientosBolsillos(TipoOperacion, FechaOrigen, datos[2], ValorTransaccion);
                                registros.add(movimiento);
                            }
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
                    // updateProgress(500, 500);
                    return registros;
                }
            };
        }
    }

    @FXML
    void detalle_op(ActionEvent event) {
        try {
            final datosListarBolsillos dataListarBolsillos = datosListarBolsillos.getDataListarBolsillos();
            datosListarBolsillos.setDataListarBolsillos(dataListarBolsillos);
            final BolsillosDetalleController controller = new BolsillosDetalleController();
            controller.mostrarDetalleBolsillo(datosListarBolsillos.getDataListarBolsillos());
        } catch (Exception e) {
            Logger.getLogger(BolsillosMovimientosController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void regresar_op(ActionEvent event) {
        try {
            final BolsillosListarController controller = new BolsillosListarController();
            controller.mostrarListarBolsillo(datosCuentasBolsillos.getDataCuentasBolsillos());
        } catch (Exception e) {
            Logger.getLogger(BolsillosMovimientosController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    void cancelar_op(ActionEvent event) {
        try {
            BolsillosMovimientosController.cancelartarea.set(true);
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

    @FXML
    void MasRegAction(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaDatos.itemsProperty().bind(task.valueProperty());
                    task.reset();
//                    indMasReg.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<modeloMovimientosBolsillos> TablaID = task.getValue();

//                            if ("S".equals(getIndicadorRegistros())) {
//                                indMasReg.setDisable(false);
//                            } else {
//                                indMasReg.setDisable(true);
//                            }
                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaID.size() % rowsPerPage() == 0 ? TablaID.size() / rowsPerPage() : TablaID.size() / rowsPerPage() + 1;

                                pagination = new Pagination(numpag, 0);
                                //paginationid.setId("paginationid");
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaID.size() % rowsPerPage();
                                            if (displace >= 0) {
                                                lastIndex = TablaID.size() / rowsPerPage();
                                            } else {
                                                lastIndex = TablaID.size() / rowsPerPage() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<modeloMovimientosBolsillos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<modeloMovimientosBolsillos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                                pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                    @Override
                                    public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                        currentpageindex = newValue.intValue();
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                tablaDatos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaID.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaID.size())));
                                            }
                                        });

                                    }
                                });

                                /**
                                 * fin configuracion de la paginacion
                                 */
                                try {
                                    AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);

                                } catch (Exception e) {
                                    Logger.getLogger(BolsillosMovimientosController.class.getName()).log(Level.SEVERE, null, e);
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(15);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(110);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getLogger(BolsillosMovimientosController.class.getName()).log(Level.SEVERE, null, e);
                            }
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println(t.getSource().getException().getMessage());
                            numpagina.addAndGet(-1);
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            numpagina.addAndGet(-1);
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });
                } catch (Exception ex) {
                    Logger.getLogger(BolsillosMovimientosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void buscarFechas(ActionEvent event) {
//        if (!getIndicadorRegistros().equals("]")) {

        BolsillosMovimientosController.registros.clear();
        numpagina.set(0);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaDatos.itemsProperty().bind(task.valueProperty());
                    task.reset();
//                    indMasReg.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
                            // final ObservableList<tablaInfoGnralGirosnal> registros = task.getValue();
//                            if ("S".equals(getIndicadorRegistros())) {
//                                indMasReg.setDisable(false);
//                            } else {
//                                indMasReg.setDisable(true);
//                            }

                            try {
                                modeloMovimientosBolsillos get = registros.get(0);

                                final Cliente datosCliente = Cliente.getCliente();
                                final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                                try {
                                    /**
                                     * configuracion de la paginacion
                                     */
                                    final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;

                                    pagination = new Pagination(numpag, 0);
                                    //paginationid.setId("paginationid");
                                    pagination.setPageFactory(new Callback<Integer, Node>() {
                                        @Override
                                        public Node call(final Integer pageIndex) {

                                            if (pageIndex > numpag) {
                                                return null;
                                            } else {
                                                int lastIndex = 0;
                                                int displace = registros.size() % rowsPerPage();
                                                if (displace >= 0) {
                                                    lastIndex = registros.size() / rowsPerPage();
                                                } else {
                                                    lastIndex = registros.size() / rowsPerPage() - 1;
                                                }
                                                int page = pageIndex * itemsPerPage();

                                                for (int i = page; i < page + itemsPerPage(); i++) {

                                                    if (lastIndex == pageIndex) {
                                                        final List<modeloMovimientosBolsillos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                        tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                    } else {
                                                        final List<modeloMovimientosBolsillos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                                    pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                        @Override
                                        public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                            currentpageindex = newValue.intValue();
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    tablaDatos.getItems().setAll(registros.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= registros.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : registros.size())));
                                                }
                                            });
                                        }
                                    });

//                                    /**
//                                     * fin configuracion de la paginacion
//                                     */
//                                    try {
//                                        AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);
//
//                                    } catch (Exception e) {
//                                        System.out.println("no hay hijos para borrar");
//                                    }
                                    AnchorPane.getChildren().add(pagination);
                                    AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(15);
                                    AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(110);
                                    pagination.setVisible(true);
                                    //Atlas.vista.show(); 
                                } catch (Exception e) {
                                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                            } catch (Exception e) {
                                tablaDatos.setPlaceholder(new Label("No hay registros disponibles para cancelar"));
                                pagination.setVisible(false);
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            task.reset();
                            numpagina.addAndGet(-1);
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            task.reset();
                            numpagina.addAndGet(-1);
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });
                } catch (Exception ex) {
                    Logger.getLogger(BolsillosMovimientosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
//        } else {
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    new ModalMensajes("Hay registros pendientes de consultar por el canal", "Advertencia", ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.DEFAULT);
//                }
//            });
//        }
    }

    public String getIndicadorRegistros() {
        return BolsillosMovimientosController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        BolsillosMovimientosController.indicadorRegistros = indicadorRegistros;
    }

    private void analisisFecha() {
        if (fechaini.getSelectedDate() != null && fechafin.getSelectedDate() != null) {
            rangoFecha(fechaini.getSelectedDate(), fechafin.getSelectedDate());
        } 
    }
    private transient final ChangeListener<Date> eventoMenuFechaV = new ChangeListener<Date>() {
        @Override
        public void changed(ObservableValue<? extends Date> ov, Date viejoValor, Date nuevoVal) {
            analisisFecha();
        }
    };
    private transient final ChangeListener<Date> eventoMenuFechaN = new ChangeListener<Date>() {
        @Override
        public void changed(ObservableValue<? extends Date> ov, Date viejoValor, Date nuevoVal) {
            analisisFecha();
        }
    };

    private boolean rangoFecha(final Date calendarFi, final Date calendarFf) {
        Calendar calendarFiaux = Calendar.getInstance();
        Calendar calendarFfaux = Calendar.getInstance();

        calendarFiaux.setTime(calendarFi);
        calendarFfaux.setTime(calendarFf);
        final int diferenciaDias = gestorDoc.CalcularDifFechas(calendarFiaux, calendarFfaux);
//        System.out.println("diferencia dias " + diferenciaDias);
        if ((diferenciaDias <= 365) && (diferenciaDias != -1)) {
            buscarFechas.setDisable(false);
        } else {
            buscarFechas.setDisable(true);
        }
        return true;
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    public static Calendar getFechalimite() {
        return fechalimite;
    }

    public static void setFechalimite(Calendar fechalimite) {
        PuntosColAjustePuntosController.fechalimite = fechalimite;
    }
}
