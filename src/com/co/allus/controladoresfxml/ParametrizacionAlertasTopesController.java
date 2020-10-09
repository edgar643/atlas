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
import com.co.allus.modelo.alertasnot.ParametrizacionTopes;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
public class ParametrizacionAlertasTopesController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button aceptar_trx;
    @FXML
    private Button cancelar_trx;
    @FXML
    private ComboBox<String> combo_cajero;
    @FXML
    private ComboBox<String> combo_pac;
    @FXML
    private Label label_cajero;
    @FXML
    private Label label_pac;
    @FXML
    private HBox menu_progreso;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<ParametrizacionTopes> tablaDatos;
    @FXML
    private TableColumn tope_canal;
    @FXML
    private TableColumn tope_monto;
    @FXML
    private TableColumn tope_montomin;
    @FXML
    private TableColumn tope_numoper;
    @FXML
    private TableColumn tope_opemin;
    @FXML
    private TableColumn tope_operacion;
    @FXML
    private ProgressBar progreso;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<ParametrizacionTopes>> task = new ParametrizacionAlertasTopesController.GetTopesTask();
    private transient Service<Void> serviceParamTopes = ParametrizacionTopesTask();
    public static ObservableList<ParametrizacionTopes> topes = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static AtomicInteger numpagina = new AtomicInteger(0);
    private SimpleDateFormat formatoFechaO = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat formatoHoraO = new SimpleDateFormat("kkmmss");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert aceptar_trx != null : "fx:id=\"aceptar_trx\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert cancelar_trx != null : "fx:id=\"cancelar_trx\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert combo_cajero != null : "fx:id=\"combo_cajero\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert combo_pac != null : "fx:id=\"combo_pac\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert label_cajero != null : "fx:id=\"label_cajero\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert label_pac != null : "fx:id=\"label_pac\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert tope_canal != null : "fx:id=\"tope_canal\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert tope_monto != null : "fx:id=\"tope_monto\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert tope_montomin != null : "fx:id=\"tope_montomin\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert tope_numoper != null : "fx:id=\"tope_numoper\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert tope_opemin != null : "fx:id=\"tope_opemin\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";
        assert tope_operacion != null : "fx:id=\"tope_operacion\" was not injected: check your FXML file 'ParametrizacionAlertasTopes.fxml'.";


        this.resources = rb;
        this.location = url;

        Callback<TableColumn, TableCell> cellFactory1 = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        Callback<TableColumn, TableCell> cellFactory2 = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCellNumop();
            }
        };

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tope_canal.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colCanal"));

        tope_montomin.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colMontoMax"));

        tope_opemin.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colNummaxOp"));
        tope_operacion.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colDescodOp"));

        tope_monto.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colMontoOp"));
        tope_monto.setCellFactory(cellFactory1);
        tope_monto.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ParametrizacionTopes, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ParametrizacionTopes, String> t) {
                ((ParametrizacionTopes) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColMontoOp(t.getNewValue());
            }
        });

        tope_numoper.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colNumOp"));
        tope_numoper.setCellFactory(cellFactory2);
        tope_numoper.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ParametrizacionTopes, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ParametrizacionTopes, String> t) {
                ((ParametrizacionTopes) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColNumOp(t.getNewValue());
            }
        });



        progreso.setVisible(false);

        ParametrizacionAlertasTopesController.cancelartarea.set(false);


    }

    public void mostrarParametrizarTopes() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ParametrizacionAlertasTopes.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button aceptar_op = (Button) root.lookup("#aceptar_trx");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_trx");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final TableView<ParametrizacionTopes> tablaDatos = (TableView<ParametrizacionTopes>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    final ComboBox<String> comboPac = (ComboBox<String>) root.lookup("#combo_pac");
                    final ComboBox<String> comboCajero = (ComboBox<String>) root.lookup("#combo_cajero");

                    final ObservableList<String> ListaCombo = FXCollections.emptyObservableList();
                    comboPac.setItems(ListaCombo);
                    comboCajero.setItems(ListaCombo);
                    final List<String> listaOpciones = new ArrayList<String>();
                    listaOpciones.add("SI");
                    listaOpciones.add("NO");
                    comboPac.setItems(FXCollections.observableArrayList(listaOpciones));
                    comboPac.getSelectionModel().select("NO");
                    comboCajero.setItems(FXCollections.observableArrayList(listaOpciones));
                    comboCajero.getSelectionModel().select("NO");
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
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
                    aceptar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    aceptar_op.setCursor(Cursor.HAND);
                                    aceptar_op.setEffect(shadow);
                                }
                            });

                    aceptar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    aceptar_op.setCursor(Cursor.DEFAULT);
                                    aceptar_op.setEffect(null);
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
                    tablaDatos.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
//                            System.out.println("TERMINO TAREA");
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = topes.size() % rowsPerPage() == 0 ? topes.size() / rowsPerPage() : topes.size() / rowsPerPage() + 1;

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
                                        int displace = topes.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = topes.size() / rowsPerPage();
                                        } else {
                                            lastIndex = topes.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<ParametrizacionTopes> subList = topes.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<ParametrizacionTopes> subList = topes.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaDatos.getItems().setAll(topes.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= topes.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : topes.size())));
                                        }
                                    });
                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(14);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(16);
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

//                                final TreeView<String> arbolBolsillos = (TreeView<String>) pane.lookup("#arbolBolsillos");
//                                if (arbolBolsillos != null) {
//                                    arbolBolsillos.setDisable(false);
//                                }
//                                arbolBolsillos.getSelectionModel().clearSelection();

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

                } catch (Exception e) {
                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });

    }

    public class GetTopesTask extends Service<ObservableList<ParametrizacionTopes>> {

        @Override
        protected Task<ObservableList<ParametrizacionTopes>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaTopesAlertas = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    /**
                     * DEf Trama 0750 Consulta topes
                     *
                     * envio :
                     * 754,060,connid,codTransaccion,identificacion,numPagina,numArchivo,RutaIFS,claveHw,tokenoauth,~
                     * recibe exito
                     * 754,060,000,transaccionExitosa,ExistePARAM,numArchivo,RutaIFS,numPagina,TOPES1;....TOPESSn,~
                     * TOPES(campos separados por %)
                     *
                     * def
                     * TOPES(CANAL%CODOPERACION%DESCOPERACION%NROMAXOPERACION%MONTOMAX%INDCTAINSCBANCO%TIPOIDENTIFICACION%NROIDENTIFICACION%NROOPERACIONES%MONTO%INDCTAINSCRITAS%FechaActual%HoraActual)
                     */
                    tramaTopesAlertas.append("754,060,");
                    tramaTopesAlertas.append(datosCliente.getRefid());
                    tramaTopesAlertas.append(",");
                    tramaTopesAlertas.append(AtlasConstantes.COD_PARAMETRIZACION_TOPES);
                    tramaTopesAlertas.append(",");
                    tramaTopesAlertas.append(datosCliente.getId_cliente());
                    tramaTopesAlertas.append(",");
                    tramaTopesAlertas.append("1");
                    tramaTopesAlertas.append(",");
                    tramaTopesAlertas.append(""); //NUM ARCHIVO
                    tramaTopesAlertas.append(",");
                    tramaTopesAlertas.append("");// RUTA IFS
                    tramaTopesAlertas.append(",");
                    tramaTopesAlertas.append(datosCliente.getContraseña());
                    tramaTopesAlertas.append(",");
                    tramaTopesAlertas.append(datosCliente.getTokenOauth());
                    tramaTopesAlertas.append(",~");

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Topes Alertas = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "750,061,000,TRANSACCION EXITOSA,56103,1,30,"
//                                + "ATM%100%RETIRO%999%1800000% %999%1800000%N%2017/05/26 17:45:48;"
//                                + "ATM%110%AVANCE%999%1800000% %999%600000%N%2017/05/26 17:45:48;"
//                                + "ATM%120%TRANSFERENCIAS%999%20000000% %4%4000000%N%2017/05/26 17:45:48;"
//                                + "ATM%140%PAGOS A TERCEROS Y FACTURAS%20%20000000% %999%999999999999999%N%2017/01/19 14:33:28;"
//                                + "AUD%120%TRANSFERENCIAS%999%20000000% %4%5000000%N%2017/05/26 17:45:48;"
//                                + "AUD%130%PAGO TDC NO PROPIA%999%20000000% %999%600000%N%2017/05/26 17:45:48;"
//                                + "AUD%140%PAGOS A TERCEROS Y FACTURAS%20%20000000% %999%200000%N%2017/01/19 14:33:28;"
//                                + "AUD%150%RECARGA DE CELULAR%15%20000000% %7%20000%N%2014/08/25 09:35:36;"
//                                + "BLP%110%AVANCES%999%20000000% %999%999999999999999%N%;"
//                                + "BLP%120%TRANSFERENCIAS%999%20000000% %4%5000000%N%2017/05/26 17:45:48;"
//                                + "BLP%130%PAGO TDC NO PROPIA%999%20000000% %999%600000%N%2017/05/26 17:45:48;"
//                                + "BLP%140%PAGOS A TERCEROS Y FACTURAS%20%20000000% %20%1000000%N%2017/01/19 14:33:28;"
//                                + "BLP%190%SOLICITUD TARJETA E-PREPAGO%999%999999999999999% %999%999999999999999%N%2017/01/19 14:33:28;"
//                                + "BLP%235%ABONO T.CREDITO DE UN TERCERO%999%999999999999999% %999%999999999999999%N%;"
//                                + "BLP%740%TRANSFERENCIAS INTERNACIONALES%3%5000% %999%999999999999999%N%;"
//                                + "CNB%100%RETIRO%999%9999999% %4%2000000%N%2017/05/26 17:45:48;"
//                                + "CNB%110%AVANCES%999%9999999% %999%600000%N%2017/05/26 17:45:48;"
//                                + "CNB%120%TRANSFERENCIAS%999%9999999% %4%2000000%N%2017/05/26 17:45:48;"
//                                + "CNT%120%TRANSFERENCIAS%999%20000000% %4%4000000%N%2017/05/26 17:45:48;"
//                                + "CNT%130%PAGO TDC NO PROPIA%999%20000000% %999%999999999999999%N%2017/01/19 14:33:28;"
//                                + "CNT%140%PAGOS A TERCEROS Y FACTURAS%20%20000000% %999%999999999999999%N%2017/01/19 14:33:28;"
//                                + "FCN%140%PAGOS A TERCEROS Y FACTURAS%20%20000000% %999%200000%N%2017/01/19 14:33:28;"
//                                + "MOV%120%TRANSFERENCIAS%999%20000000% %4%4000000%N%2017/05/26 17:45:48;"
//                                + "MOV%130%PAGO TDC BM%999%20000000% %999%999999999999999%N%2017/01/19 14:33:28;"
//                                + "MOV%140%PAGOS A TERCEROS Y FACTURAS%20%20000000% %20%1200000%N%2010/03/18 04:29:34;"
//                                + "MOV%150%RECARGA DE CELULAR%15%20000000% %7%20000%N%2014/08/29 10:35:57;"
//                                + "MOV%220%TRANSFERENCIAS COMUNIDAD BM%999%999999999999999% %10%300000%N%2017/02/15 09:40:48;"
//                                + "POS%160%COMPRA%999%999999999999999% %999%999999999999999%N%2017/01/19 14:33:28;"
//                                + "PSE%140%PAGOS A TERCEROS Y FACTURAS%20%20000000% %20%1000000%N%2017/01/19 14:33:28;"
//                                + "PYB%140%PAGOS A TERCEROS Y FACTURAS%20%20000000% %20%2400000%N%2009/04/25 05:25:18;"
//                                + ",~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTopesAlertas.toString());
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
                        //                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Topes Alertas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTopesAlertas.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ParametrizacionAlertasTopesController.cancelartarea.get()) {
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

                        if (ParametrizacionAlertasTopesController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final ObservableList<ParametrizacionTopes> emptyObservableList = FXCollections.emptyObservableList();
                            //  tablaDatos.setItems(emptyObservableList);
                            topes = FXCollections.observableArrayList();

                            String[] ListTopes = Respuesta.split(",")[8].split(";");

//def TOPES(CANAL%CODOPERACION%DESCOPERACION%NROMAXOPERACION%MONTOMAX%INDCTAINSCBANCO%TIPOIDENTIFICACION%NROIDENTIFICACION%NROOPERACIONES%MONTO%INDCTAINSCRITAS%FechaActual%HoraActual)
//                            private SimpleStringProperty colTopeCanal;
//    private SimpleStringProperty colTopeMonto;
//    private SimpleStringProperty colTopeMontomin;
//    private SimpleStringProperty colTopeNumoper;
//    private SimpleStringProperty colTopeOpemin;
//    private SimpleStringProperty colTopeOperacion;

                            for (int i = 0; i < ListTopes.length; i++) {
                                String[] datos = ListTopes[i].split("%");

                                String monto = "";

                                try {
                                    //monto = formatonum.format(Double.parseDouble(datos[10].substring(0, datos[10].length() - 2))).replace(",", ".") + "," + datos[10].substring(datos[10].length() - 2, datos[10].length());
                                    monto = formatonum.format(Double.parseDouble(datos[4]));
                                } catch (Exception e) {
                                    monto = datos[4];
                                }

                                String montomin = "";

                                try {
                                    //monto = formatonum.format(Double.parseDouble(datos[10].substring(0, datos[10].length() - 2))).replace(",", ".") + "," + datos[10].substring(datos[10].length() - 2, datos[10].length());
                                    montomin = formatonum.format(Double.parseDouble(datos[9]));
                                } catch (Exception e) {
                                    montomin = datos[9];
                                }

                                final ParametrizacionTopes cuenta = new ParametrizacionTopes(
                                        datos[0],
                                        datos[1],
                                        datos[2].toLowerCase(),
                                        datos[3],
                                        monto,
                                        datos[5],
                                        datos[6],
                                        datos[7],
                                        datos[8],
                                        montomin,
                                        datos[10],
                                        datos[11],
                                        datos[12]);
                                topes.add(cuenta);
                            }
                        }
                    } else {
                        if (ParametrizacionAlertasTopesController.cancelartarea.get()) {
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
                    return topes;
                }
            };
        }
    }

    @FXML
    void aceptar_op(ActionEvent event) {
        boolean validacion = true;

        for (Iterator<ParametrizacionTopes> it = topes.iterator(); it.hasNext();) {
            ParametrizacionTopes parametrizaciontopes = it.next();

        }

        if (validacion) {

            if (serviceParamTopes.isRunning()) {
                aceptar_trx.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceParamTopes.progressProperty());
                serviceParamTopes.reset();
                serviceParamTopes.start();
            } else {
                aceptar_trx.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceParamTopes.progressProperty());
                serviceParamTopes.reset();
                serviceParamTopes.start();
            }

            serviceParamTopes.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono parametrizar topes Alertas" + "##" + gestorDoc.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                    ParametrizacionAlertasTopesController.cancelartarea.set(false);
                }
            });

            serviceParamTopes.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Cancelar parametrizar topes Alertas" + "##" + gestorDoc.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            serviceParamTopes.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    // System.out.println("fallo");
                }
            });



        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new ModalMensajes("Existen Alertas sin parametrizar correctamente\n Verificar e intente nuevamente", "Advertencia", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                }
            });

        }

    }

    public Service<Void> ParametrizacionTopesTask() {
        serviceParamTopes = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final Cliente cliente = Cliente.getCliente();

                        String Respuesta = new String();
                        final StringBuilder tramaParametrizarTopes = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Date fecha = new Date();
                        StringBuilder listaTopes = new StringBuilder();

                        for (Iterator<ParametrizacionTopes> it = topes.iterator(); it.hasNext();) {
                            ParametrizacionTopes parametrizacionTopes = it.next();
                            listaTopes.append(parametrizacionTopes.getColCanal());
                            listaTopes.append(";");
                            listaTopes.append(parametrizacionTopes.getColCodigoOp());
                            listaTopes.append(";");
                            listaTopes.append(parametrizacionTopes.getColDescodOp());
                            listaTopes.append(";");
                            listaTopes.append(parametrizacionTopes.getColNummaxOp());
                            listaTopes.append(";");
                            listaTopes.append(parametrizacionTopes.getColMontoMax());
                            listaTopes.append(";");
                            listaTopes.append(parametrizacionTopes.getColIndCtainscB());
                            listaTopes.append(";");
                            if (parametrizacionTopes.getColMontoOp().equals("999.999.999.999.999")) {
                                listaTopes.append(" ");
                                listaTopes.append(";");
                                listaTopes.append(" ");
                                listaTopes.append(";");
                            } else if (Integer.parseInt(parametrizacionTopes.getColNumOp()) > Integer.parseInt(parametrizacionTopes.getColNummaxOp())) {
                                listaTopes.append(" ");
                                listaTopes.append(";");
                                listaTopes.append(" ");
                                listaTopes.append(";");

                            } else {
                                listaTopes.append(parametrizacionTopes.getColNumOp());
                                listaTopes.append(";");
                                listaTopes.append(parametrizacionTopes.getColMontoOp());
                                listaTopes.append(";");
                            }

                            listaTopes.append(parametrizacionTopes.getColIndCtainsc());
                            listaTopes.append(";");
                            if (parametrizacionTopes.getColFecha().trim().isEmpty()) {
                                listaTopes.append(" ");
                                listaTopes.append(";");
                                listaTopes.append(" ");
                            } else {
                                listaTopes.append(formatoFechaO.format(fecha));
                                listaTopes.append(";");
                                listaTopes.append(formatoHoraO.format(fecha));
                            }
                            listaTopes.append(";");
                            listaTopes.append(" ");
                            listaTopes.append(";");
                            listaTopes.append(" ");
                            listaTopes.append("%");


                        }


                        tramaParametrizarTopes.append("754,064,");
                        tramaParametrizarTopes.append(cliente.getRefid());
                        tramaParametrizarTopes.append(",");
                        tramaParametrizarTopes.append(AtlasConstantes.COD_PARAMETRIZAR_TOPES);
                        tramaParametrizarTopes.append(",");
                        tramaParametrizarTopes.append(cliente.getId_cliente());
                        tramaParametrizarTopes.append(",");
                        tramaParametrizarTopes.append("120");
                        tramaParametrizarTopes.append(",");
                        tramaParametrizarTopes.append(topes.size());
                        tramaParametrizarTopes.append(",");
                        tramaParametrizarTopes.append(listaTopes.toString());
                        tramaParametrizarTopes.append(",");
                        tramaParametrizarTopes.append(cliente.getContraseña());
                        tramaParametrizarTopes.append(",");
                        tramaParametrizarTopes.append(cliente.getTokenOauth());
                        tramaParametrizarTopes.append(",~");

//                        System.out.println("trama parametriacionTopes :" + tramaParametrizarTopes);
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ AlertasEnviadas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaParametrizarTopes.toString());
                            //  Respuesta = "754,064,000,TRANSACCION EXITOSA,TRACE~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listrarconsultaprestamos = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaParametrizarTopes.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                            } catch (Exception ex1) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ParametrizacionAlertasTopesController.cancelartarea.get()) {
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
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final ParametrizacionAlertasTopesfinController controller = new ParametrizacionAlertasTopesfinController();
                                    controller.motrarParametrizacionAlertasFin();
                                }
                            });
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ParametrizacionAlertasTopesController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        aceptar_trx.setDisable(false);
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
        return serviceParamTopes;
    }

    @FXML
    void cancelar_op(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                final Label label_menu = (Label) pane.lookup("#label_saldos");

                final TreeView<String> arbol_consultas = (TreeView<String>) pane.lookup("#arbol_consultas");
                if (arbol_consultas != null) {
                    arbol_consultas.setDisable(false);
                }

                arbol_consultas.getSelectionModel().clearSelection();
                ParametrizacionAlertasTopesController.numpagina.set(0);
                ParametrizacionAlertasTopesController.topes.clear();
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }
            }
        });
    }

    public class EditingCellNumop extends TableCell<ParametrizacionTopes, String> {

        private TextField textField;

        public EditingCellNumop() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textField.requestFocus();
                    textField.selectAll();
                }
            });
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        private void createTextField() {

            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        try {
                            final String nuopMax = getTableView().getItems().get(getTableRow().getIndex()).getColNummaxOp().replace(".", "");
                            if (Integer.parseInt(textField.getText()) >= Integer.parseInt(nuopMax)) {
                                cancelEdit();
                            } else {
                                if (Integer.parseInt(textField.getText()) == 0) {
                                    commitEdit("999");
                                } else {
                                    commitEdit(Integer.parseInt(textField.getText()) + "");
                                }
                            }

                        } catch (Exception e) {

                            if (Integer.parseInt(textField.getText()) > 999) {
                                cancelEdit();
                            } else {
                                cancelEdit();
                            }

                        }

                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        try {
                            final String nuopMax = getTableView().getItems().get(getTableRow().getIndex()).getColNummaxOp().replace(".", "");
                            if (Integer.parseInt(textField.getText()) >= Integer.parseInt(nuopMax)) {
                                cancelEdit();
                            } else {
                                if (Integer.parseInt(textField.getText()) == 0) {
                                    commitEdit("999");
                                } else {
                                    commitEdit(Integer.parseInt(textField.getText()) + "");
                                }
                            }

                        } catch (Exception e) {
                            if (Integer.parseInt(textField.getText()) > 999) {
                                cancelEdit();
                            } else {
                                cancelEdit();
                            }

                        }

                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                        }
                    }
                }
            });
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue && textField != null) {

                        try {
                            final String nuopMax = getTableView().getItems().get(getTableRow().getIndex()).getColNummaxOp().replace(".", "");
                            if (Integer.parseInt(textField.getText()) >= Integer.parseInt(nuopMax)) {
                                cancelEdit();
                            } else {
                                if (Integer.parseInt(textField.getText()) == 0) {
                                    commitEdit("999");
                                } else {
                                    commitEdit(Integer.parseInt(textField.getText()) + "");
                                }
                            }

                        } catch (Exception e) {
                            if (Integer.parseInt(textField.getText()) > 999) {
                                cancelEdit();
                            } else {
                                cancelEdit();
                            }

                        }
                    }
                }
            });

        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }

        /**
         *
         * @param forward true gets the column to the right, false the column to
         * the left of the current column
         * @return
         */
        private TableColumn<ParametrizacionTopes, ?> getNextColumn(boolean forward) {
            List<TableColumn<ParametrizacionTopes, ?>> columns = new ArrayList<>();
            for (TableColumn<ParametrizacionTopes, ?> column : getTableView().getColumns()) {
                columns.addAll(getLeaves(column));
            }
            //There is no other column that supports editing.
            if (columns.size() < 2) {
                return null;
            }
            int currentIndex = columns.indexOf(getTableColumn());
            int nextIndex = currentIndex;
            if (forward) {
                nextIndex++;
                if (nextIndex > columns.size() - 1) {
                    nextIndex = 0;
                }
            } else {
                nextIndex--;
                if (nextIndex < 0) {
                    nextIndex = columns.size() - 1;
                }
            }
            return columns.get(nextIndex);
        }

        private List<TableColumn<ParametrizacionTopes, ?>> getLeaves(TableColumn<ParametrizacionTopes, ?> root) {
            List<TableColumn<ParametrizacionTopes, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<ParametrizacionTopes, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }

    public class EditingCell extends TableCell<ParametrizacionTopes, String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textField.requestFocus();
                    textField.selectAll();
                }
            });
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            // setText("");
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        private void createTextField() {


            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setTooltip(new Tooltip("Verificar el valor máximo permitido"));
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        try {

                            final String topeMax = getTableView().getItems().get(getTableRow().getIndex()).getColMontoMax().replace(".", "");
                            try {
                                if (Double.parseDouble(textField.getText().replace(".", "")) > Double.parseDouble(topeMax)) {
                                    cancelEdit();
                                } else {
                                    if (textField.getText().trim().isEmpty()) {
                                        commitEdit(textField.getText());
                                    } else {

                                        if (Double.parseDouble(textField.getText().replace(".", "")) == 0) {
                                            commitEdit(formatonum.format(Double.parseDouble("999999999999999")));
                                        } else {
                                            commitEdit(formatonum.format(Double.parseDouble(textField.getText().replace(".", ""))));
                                        }


                                    }

                                }
                            } catch (Exception e) {
                                cancelEdit();
                            }

                            if (textField.getText().trim().isEmpty()) {
                                commitEdit(textField.getText());
                            } else {
                                if (textField.getText().trim().isEmpty()) {
                                    commitEdit(textField.getText());
                                } else {

                                    if (Double.parseDouble(textField.getText().replace(".", "")) == 0) {
                                        commitEdit(formatonum.format(Double.parseDouble("999999999999999")));
                                    } else {
                                        commitEdit(formatonum.format(Double.parseDouble(textField.getText().replace(".", ""))));
                                    }


                                }

                            }

                        } catch (Exception e) {
                            cancelEdit();
                        }

                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        try {

                            final String topeMax = getTableView().getItems().get(getTableRow().getIndex()).getColMontoMax().replace(".", "");
                            try {
                                if (Double.parseDouble(textField.getText().replace(".", "")) > Double.parseDouble(topeMax)) {
                                    cancelEdit();
                                } else {
                                    if (textField.getText().trim().isEmpty()) {
                                        commitEdit(textField.getText());
                                    } else {

                                        if (Double.parseDouble(textField.getText().replace(".", "")) == 0) {
                                            commitEdit(formatonum.format(Double.parseDouble("999999999999999")));
                                        } else {
                                            commitEdit(formatonum.format(Double.parseDouble(textField.getText().replace(".", ""))));
                                        }


                                    }

                                }
                            } catch (Exception e) {
                                cancelEdit();
                            }

                            if (textField.getText().trim().isEmpty()) {
                                commitEdit(textField.getText());
                            } else {
                                if (textField.getText().trim().isEmpty()) {
                                    commitEdit(textField.getText());
                                } else {

                                    if (Double.parseDouble(textField.getText().replace(".", "")) == 0) {
                                        commitEdit(formatonum.format(Double.parseDouble("999999999999999")));
                                    } else {
                                        commitEdit(formatonum.format(Double.parseDouble(textField.getText().replace(".", ""))));
                                    }


                                }

                            }

                        } catch (Exception e) {
                            cancelEdit();
                        }

                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                        }
                    }
                }
            });
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue && textField != null) {

                        try {

                            final String topeMax = getTableView().getItems().get(getTableRow().getIndex()).getColMontoMax().replace(".", "");
                            try {
                                if (Double.parseDouble(textField.getText().replace(".", "")) > Double.parseDouble(topeMax)) {
                                    cancelEdit();
                                } else {
                                    if (textField.getText().trim().isEmpty()) {
                                        commitEdit(textField.getText());
                                    } else {

                                        if (Double.parseDouble(textField.getText().replace(".", "")) == 0) {
                                            commitEdit(formatonum.format(Double.parseDouble("999999999999999")));
                                        } else {
                                            commitEdit(formatonum.format(Double.parseDouble(textField.getText().replace(".", ""))));
                                        }


                                    }

                                }
                            } catch (Exception e) {
                                cancelEdit();
                            }

                            if (textField.getText().trim().isEmpty()) {
                                commitEdit(textField.getText());
                            } else {
                                if (textField.getText().trim().isEmpty()) {
                                    commitEdit(textField.getText());
                                } else {

                                    if (Double.parseDouble(textField.getText().replace(".", "")) == 0) {
                                        commitEdit(formatonum.format(Double.parseDouble("999999999999999")));
                                    } else {
                                        commitEdit(formatonum.format(Double.parseDouble(textField.getText().replace(".", ""))));
                                    }


                                }

                            }

                        } catch (Exception e) {
                            cancelEdit();
                        }
                    }

                }
            });



        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }

        /**
         *
         * @param forward true gets the column to the right, false the column to
         * the left of the current column
         * @return
         */
        private TableColumn<ParametrizacionTopes, ?> getNextColumn(boolean forward) {
            List<TableColumn<ParametrizacionTopes, ?>> columns = new ArrayList<>();
            for (TableColumn<ParametrizacionTopes, ?> column : getTableView().getColumns()) {
                columns.addAll(getLeaves(column));
            }
            //There is no other column that supports editing.
            if (columns.size() < 2) {
                return null;
            }
            int currentIndex = columns.indexOf(getTableColumn());
            int nextIndex = currentIndex;
            if (forward) {
                nextIndex++;
                if (nextIndex > columns.size() - 1) {
                    nextIndex = 0;
                }
            } else {
                nextIndex--;
                if (nextIndex < 0) {
                    nextIndex = columns.size() - 1;
                }
            }
            return columns.get(nextIndex);
        }

        private List<TableColumn<ParametrizacionTopes, ?>> getLeaves(TableColumn<ParametrizacionTopes, ?> root) {
            List<TableColumn<ParametrizacionTopes, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<ParametrizacionTopes, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
