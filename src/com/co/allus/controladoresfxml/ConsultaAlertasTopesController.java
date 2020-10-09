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
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
public class ConsultaAlertasTopesController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button terminar_trx;
    @FXML
    private ComboBox<String> combo_cajero;
    @FXML
    private ComboBox<String> combo_pac;
    @FXML
    private Label label_cajero;
    @FXML
    private Label label_pac;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<ParametrizacionTopes> tablaDatos;
    @FXML
    private TableColumn<ParametrizacionTopes, String> tope_canal;
    @FXML
    private TableColumn<ParametrizacionTopes, String> tope_monto;
    @FXML
    private TableColumn<ParametrizacionTopes, String> tope_montomin;
    @FXML
    private TableColumn<ParametrizacionTopes, String> tope_numoper;
    @FXML
    private TableColumn<ParametrizacionTopes, String> tope_opemin;
    @FXML
    private TableColumn<ParametrizacionTopes, String> tope_operacion;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<ParametrizacionTopes>> task = new ConsultaAlertasTopesController.GetTopesTask();
    public static ObservableList<ParametrizacionTopes> topes = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static AtomicInteger numpagina = new AtomicInteger(0);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert combo_cajero != null : "fx:id=\"combo_cajero\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert combo_pac != null : "fx:id=\"combo_pac\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert label_cajero != null : "fx:id=\"label_cajero\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert label_pac != null : "fx:id=\"label_pac\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";       
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert tope_canal != null : "fx:id=\"tope_canal\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert tope_monto != null : "fx:id=\"tope_monto\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert tope_montomin != null : "fx:id=\"tope_montomin\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert tope_numoper != null : "fx:id=\"tope_numoper\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert tope_opemin != null : "fx:id=\"tope_opemin\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";
        assert tope_operacion != null : "fx:id=\"tope_operacion\" was not injected: check your FXML file 'ConsultaAlertasTopes.fxml'.";


        this.resources = rb;
        this.location = url;
       

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tope_canal.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colCanal"));
        tope_monto.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colMontoOp"));
        tope_montomin.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colMontoMax"));
        tope_numoper.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colNumOp"));
        tope_opemin.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colNummaxOp"));
        tope_operacion.setCellValueFactory(new PropertyValueFactory<ParametrizacionTopes, String>("colDescodOp"));

        ConsultaAlertasTopesController.cancelartarea.set(false);

    }


    public void mostrarConsultaAlertasTopes() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaAlertasTopes.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button aceptar_op = (Button) root.lookup("#terminar_trx");
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
                    comboCajero.setItems(FXCollections.observableArrayList(listaOpciones));

                    comboCajero.getSelectionModel().select("NO");
                    comboPac.getSelectionModel().select("NO");

                    comboCajero.setDisable(true);
                    comboPac.setDisable(true);

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
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(17);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(27);
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
//                            System.out.println("cancelo la tarea");
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
                    tramaTopesAlertas.append(" "); //NUM ARCHIVO
                    tramaTopesAlertas.append(",");
                    tramaTopesAlertas.append(" ");// RUTA IFS
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
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
                        //                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Topes Alertas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTopesAlertas.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ConsultaAlertasTopesController.cancelartarea.get()) {
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

                        if (ConsultaAlertasTopesController.cancelartarea.get()) {
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
                        if (ConsultaAlertasTopesController.cancelartarea.get()) {
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
                ConsultaAlertasTopesController.numpagina.set(0);
                ConsultaAlertasTopesController.topes.clear();
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

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
}
