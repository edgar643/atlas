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
import com.co.allus.modelo.puntoscol.MapeoRespPCO;
import com.co.allus.modelo.puntoscol.infoTablaAjustePuntos;
import com.co.allus.tareas.BusqDescripAjustes;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class PuntosColAjustePuntosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button cancelar;
    @FXML
    private TableColumn<infoTablaAjustePuntos, String> fecha;
    @FXML
    private TableColumn<infoTablaAjustePuntos, String> puntos;
    @FXML
    private TableColumn<infoTablaAjustePuntos, String> descripcion;
    @FXML
    private TableColumn<infoTablaAjustePuntos, String> concepto;
    @FXML
    private TableColumn<infoTablaAjustePuntos, String> resultado;
    @FXML
    private TextField descripcionT;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private Button indMasReg;
    @FXML
    private HBox menu_progreso;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Button buscarFechas;
    @FXML
    private TableView<infoTablaAjustePuntos> tablaDatos;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
//    private transient Service<Void> serviceMovPco = ContinuarOP();
    public static AtomicInteger numpagina = new AtomicInteger(0);
    // private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechamov = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechamov2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private transient final DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static ObservableList<infoTablaAjustePuntos> registros = FXCollections.observableArrayList();
    public static String indicadorRegistros = "N";
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private Service<ObservableList<infoTablaAjustePuntos>> task = new PuntosColAjustePuntosController.GetAjustePuntosTask();
    public static Calendar fechalimite = Calendar.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert concepto != null : "fx:id=\"concepto\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert fecha != null : "fx:id=\"fecha\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert puntos != null : "fx:id=\"puntos\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert resultado != null : "fx:id=\"resultado\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert buscarFechas != null : "fx:id=\"buscarFechas\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";
        assert descripcionT != null : "fx:id=\"descripcionT\" was not injected: check your FXML file 'PuntosColAjustePuntos.fxml'.";

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);
        final Calendar instance = Calendar.getInstance();

        fechafin.setSelectedDate(instance.getTime());

        instance.add(Calendar.DAY_OF_MONTH, -60);

        fechaini.setSelectedDate(instance.getTime());

        setFechalimite(instance);

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        fecha.setCellValueFactory(new PropertyValueFactory<infoTablaAjustePuntos, String>("fecha"));
        concepto.setCellValueFactory(new PropertyValueFactory<infoTablaAjustePuntos, String>("concepto"));
        puntos.setCellValueFactory(new PropertyValueFactory<infoTablaAjustePuntos, String>("puntos"));
        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaAjustePuntos, String>("descripcion"));
        resultado.setCellValueFactory(new PropertyValueFactory<infoTablaAjustePuntos, String>("resultado"));

        setIndicadorRegistros("N");
        buscarFechas.setDisable(false);
        indMasReg.setDisable(true);

        this.resources = rb;
        this.location = url;
        // progreso.setVisible(false);

    }

    public static Calendar getFechalimite() {
        return fechalimite;
    }

    public static void setFechalimite(Calendar fechalimite) {
        PuntosColAjustePuntosController.fechalimite = fechalimite;
    }

    public String getIndicadorRegistros() {
        return PuntosColAjustePuntosController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        PuntosColAjustePuntosController.indicadorRegistros = indicadorRegistros;
    }

    public void mostrarPuntosColAjuste(final ObservableList<infoTablaAjustePuntos> registross, String indMasReg, final int registrosPag, final int origen) {
        if (origen == 1) {
            setIndicadorRegistros(indMasReg);
            numpagina.set(registrosPag);
            PuntosColAjustePuntosController.registros.clear();
            PuntosColAjustePuntosController.registros.addAll(registross);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PuntosColAjustePuntos.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botonMasReg = (Button) root.lookup("#indMasReg");
                    final Button botonbuscar = (Button) root.lookup("#buscarFechas");

                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    /**
                     * barra progreso
                     */
//                    Region veil = new Region();
//                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
//                    ProgressIndicator p = new ProgressIndicator();
//                    p.setMaxSize(150, 150);
//                    panel_tabla.getChildren().addAll(veil, p);
                    final DropShadow shadow = new DropShadow();
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

                    botonMasReg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            botonMasReg.setCursor(Cursor.HAND);
                            botonMasReg.setEffect(shadow);
                        }
                    });

                    botonMasReg.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            botonMasReg.setCursor(Cursor.DEFAULT);
                            botonMasReg.setEffect(null);

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

                    label_menu.setVisible(false);

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }

                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

//                    if (origen == 1) {
//
//                        // Use binding to be notified whenever the data source chagnes
//                        // task = new GetTarjetasTask();
//                        p.progressProperty().bind(task.progressProperty());
//                        veil.visibleProperty().bind(task.runningProperty());
//                        p.visibleProperty().bind(task.runningProperty());
//                        tablaData.itemsProperty().bind(task.valueProperty());
//                        task.start();
//
//                        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//                            @Override
//                            public void handle(WorkerStateEvent t) {
//
//                                tablaData.itemsProperty().unbind();
//                                System.out.println("TERMINO TAREA");
//
//                                if ("S".equals(getIndicadorRegistros())) {
//                                    botonMasReg.setDisable(false);
//                                } else {
//                                    botonMasReg.setDisable(true);
//                                }
//
//                                /**
//                                 * configuracion de la paginacion
//                                 */
//                                // final ObservableList<infoTablaAjustePuntos> registros = task.getValue();
//                                final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;
//
//                                pagination = new Pagination(numpag, 0);
//                                pagination.setPageFactory(new Callback<Integer, Node>() {
//                                    @Override
//                                    public Node call(final Integer pageIndex) {
//                                        //ObservableList<modelSaldoTarjeta> tarjetastemp = FXCollections.observableArrayList();
//                                        //tarjetastemp.addAll(tarjetas);
//                                        if (pageIndex > numpag) {
//                                            return null;
//                                        } else {
//                                            int lastIndex = 0;
//                                            int displace = registros.size() % rowsPerPage();
//                                            if (displace >= 0) {
//                                                lastIndex = registros.size() / rowsPerPage();
//                                            } else {
//                                                lastIndex = registros.size() / rowsPerPage() - 1;
//                                            }
//                                            int page = pageIndex * itemsPerPage();
//
//                                            for (int i = page; i < page + itemsPerPage(); i++) {
//
//                                                if (lastIndex == pageIndex) {
//                                                    final List<infoTablaAjustePuntos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
//                                                    tablaData.setItems(FXCollections.observableArrayList(subList));
//                                                } else {
//                                                    final List<infoTablaAjustePuntos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
//                                                    tablaData.setItems(FXCollections.observableArrayList(subList));
//                                                }
//
//                                                panel_tabla.getChildren().clear();
//                                                tablaData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//                                                panel_tabla.getChildren().add(tablaData);
//                                                panel_tabla.setVisible(true);
//                                            }
//                                            return panel_tabla;
//                                        }
//                                    }
//                                });
////
//
//                                pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
//                                    @Override
//                                    public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
//                                        currentpageindex = newValue.intValue();
//                                        Platform.runLater(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                tablaData.getItems().setAll(registros.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= registros.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : registros.size())));
//                                            }
//                                        });
//
//                                    }
//                                });
//
//                                /**
//                                 * fin configuracion de la paginacion
//                                 */
//                                root.getChildren().add(pagination);
//                                root.getChildren().get(root.getChildren().size() - 1).setLayoutX(3);
//                                root.getChildren().get(root.getChildren().size() - 1).setLayoutY(75);
//                                pagination.setVisible(true);
//                                Atlas.vista.show();
//                            }
//                        });
//
//                        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
//                            @Override
//                            public void handle(WorkerStateEvent t) {
//                                System.out.println("ERROR EN LA CONSULTA");
//                                Platform.runLater(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
//                                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
//                                        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
//                                        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
//                                        final TreeView<String> arbolPuntosCol = (TreeView<String>) pane.lookup("#arbolPuntosCol");
//                                        if (arbolPuntosCol != null) {
//                                            arbolPuntosCol.setDisable(false);
//                                        }
//
//                                        arbolPuntosCol.getSelectionModel().clearSelection();
//                                        try {
//                                            pane.getChildren().remove(3);
//                                        } catch (Exception e) {
//                                            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
//                                        }
//                                        Atlas.vista.show();
//                                    }
//                                });
//
//
//                            }
//                        });
//
//                        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
//                            @Override
//                            public void handle(WorkerStateEvent t) {
//                                System.out.println("cancelo la tarea");
//                            }
//                        });
//
//                    } else {
//
//                        if ("S".equals(getIndicadorRegistros())) {
//                            botonMasReg.setDisable(false);
//                        } else {
//                            botonMasReg.setDisable(true);
//                        }
//
//                        final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;
//
//                        pagination = new Pagination(numpag, 0);
//                        pagination.setPageFactory(new Callback<Integer, Node>() {
//                            @Override
//                            public Node call(final Integer pageIndex) {
//                                //ObservableList<modelSaldoTarjeta> tarjetastemp = FXCollections.observableArrayList();
//                                //tarjetastemp.addAll(tarjetas);
//                                if (pageIndex > numpag) {
//                                    return null;
//                                } else {
//                                    int lastIndex = 0;
//                                    int displace = registros.size() % rowsPerPage();
//                                    if (displace >= 0) {
//                                        lastIndex = registros.size() / rowsPerPage();
//                                    } else {
//                                        lastIndex = registros.size() / rowsPerPage() - 1;
//                                    }
//                                    int page = pageIndex * itemsPerPage();
//
//                                    for (int i = page; i < page + itemsPerPage(); i++) {
//
//                                        if (lastIndex == pageIndex) {
//                                            final List<infoTablaAjustePuntos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
//                                            tablaData.setItems(FXCollections.observableArrayList(subList));
//                                        } else {
//                                            final List<infoTablaAjustePuntos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
//                                            tablaData.setItems(FXCollections.observableArrayList(subList));
//                                        }
//
//                                        panel_tabla.getChildren().clear();
//                                        tablaData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//                                        panel_tabla.getChildren().add(tablaData);
//                                        panel_tabla.setVisible(true);
//                                    }
//                                    return panel_tabla;
//                                }
//                            }
//                        });
////
//
//                        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
//                            @Override
//                            public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
//                                currentpageindex = newValue.intValue();
//                                Platform.runLater(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        tablaData.getItems().setAll(registros.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= registros.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : registros.size())));
//                                    }
//                                });
//
//                            }
//                        });
//
//                        /**
//                         * fin configuracion de la paginacion
//                         */
//                        root.getChildren().add(pagination);
//                        root.getChildren().get(root.getChildren().size() - 1).setLayoutX(3);
//                        root.getChildren().get(root.getChildren().size() - 1).setLayoutY(75);
//                        pagination.setVisible(true);
//                        Atlas.vista.show();
//
//                    }
                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
//                    e.printStackTrace();
                }

            }
        });
    }

    public class GetAjustePuntosTask extends Service<ObservableList<infoTablaAjustePuntos>> {

        @Override
        protected Task<ObservableList<infoTablaAjustePuntos>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR BONOS Y AJUSTES             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarAjustes = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramaListarAjustes.append("850,075,");
                    tramaListarAjustes.append(datosCliente.getRefid());
                    tramaListarAjustes.append(",");
                    tramaListarAjustes.append(AtlasConstantes.COD_LISTAR_AJU_PCO);
                    tramaListarAjustes.append(",");
                    tramaListarAjustes.append(datosCliente.getId_cliente());
                    tramaListarAjustes.append(",");
                    tramaListarAjustes.append(formatoFecha.format(fechaini.getSelectedDate()));
                    tramaListarAjustes.append(",");
                    tramaListarAjustes.append(formatoFecha.format(fechafin.getSelectedDate()));
                    tramaListarAjustes.append(",");
                    tramaListarAjustes.append("75");
                    tramaListarAjustes.append(",");
                    tramaListarAjustes.append(numpagina.addAndGet(1)); //numero pagina
                    tramaListarAjustes.append(",");
                    tramaListarAjustes.append(datosCliente.getContraseña());
                    tramaListarAjustes.append(",");
                    tramaListarAjustes.append(datosCliente.getTokenOauth());
                    tramaListarAjustes.append(",~");

//                    System.out.println("trama listar aju : " + tramaListarAjustes.toString());
                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());
                        //850,072,000,TRANSACCION EXITOSA,indMasReg,%PUNTOS%DESCRIPCION%RESULTADO%FECHA%hora;.....,~
                        //Thread.sleep(2000);
//                        Respuesta = "850,"
//                                + "075,"
//                                + "000,"
//                                + "TRANSACCION EXITOSA,"
//                                + "S,"
//                                + "20000%BANC Puntos de Bienvenida%A%ADJ_CUSTOMER_WRONG_STATUS%20180627%132523;"
//                                + "21000%COLP Puntos de Bienvenida%A%ADJ_CUSTOMER_WRONG_STATUS%20180627%132523;"
//                                + "22000%COOM Puntos de Bienvenida%A%ADJ_CUSTOMER_WRONG_STATUS%20180627%132523;"
//                                + "23000%BOGO Puntos de Bienvenida%A%ADJ_CUSTOMER_WRONG_STATUS%20180627%132523;"
//                                + "24000%BBVA Puntos de Bienvenida%A%ADJ_CUSTOMER_WRONG_STATUS%20180627%132523;"
//                                + "25000%CITY Puntos de Bienvenida%A%ADJ_CUSTOMER_WRONG_STATUS%20180627%132523;"
//                                + "30000%AMEX Puntos de Bienvenida%A%ADJ_CUSTOMER_WRONG_STATUS%20180627%132523;,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarAjustes.toString());
//                        System.out.println("respuesta Lista : " + Respuesta);

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " +StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+ "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarAjustes.toString());
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
                        try {
                            final String indMasREg = Respuesta.split(",")[4];
                            setIndicadorRegistros(indMasREg);
                            // final List<infoTablaListarMovimientos> movtdcpco = new ArrayList<infoTablaListarMovimientos>();

                            String[] LAjustes = Respuesta.split(",")[5].split(";");

                            for (int i = 0; i < LAjustes.length; i++) {
                                String[] datos = LAjustes[i].split("%");
                                String fechab = "";
                                String puntosb = "";

                                try {
                                    fechab = formatoFechamov2.format(formatoFechamov.parse(datos[4].trim()));
                                } catch (Exception e) {
                                    fechab = datos[4].trim();
                                }
//                                
                                try {
                                    puntosb = formatonum.format(Double.parseDouble(datos[0].trim()));
                                } catch (Exception e) {
                                    puntosb = datos[0].trim();
                                }
//                                

                                String descripb = datos[1].trim();
                                String resultb = MapeoRespPCO.mapeoRespuestas.get(datos[3].trim().toUpperCase());
                                String concepto = datos[2].trim();

                                infoTablaAjustePuntos bonosajustes = new infoTablaAjustePuntos(
                                        fechab,
                                        concepto,
                                        puntosb,
                                        descripb,
                                        resultb);
                                registros.add(bonosajustes);
                            }
                        } catch (Exception e) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

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

                    return registros;

                }
            };
        }
    }

    @FXML
    void buscarFechas(ActionEvent event) {
        PuntosColAjustePuntosController.registros.clear();
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
                    indMasReg.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<infoTablaAjustePuntos> TablaID = task.getValue();

                            if ("S".equals(getIndicadorRegistros())) {
                                indMasReg.setDisable(false);
                            } else {
                                indMasReg.setDisable(true);
                            }

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
                                                    final List<infoTablaAjustePuntos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoTablaAjustePuntos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    //AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);
                                    AnchorPane.getChildren().remove(pagination);

                                } catch (Exception e) {
                                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(3);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(70);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
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

                    Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

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
                    indMasReg.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<infoTablaAjustePuntos> TablaID = task.getValue();

                            if ("S".equals(getIndicadorRegistros())) {
                                indMasReg.setDisable(false);
                            } else {
                                indMasReg.setDisable(true);
                            }

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
                                                    final List<infoTablaAjustePuntos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoTablaAjustePuntos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(3);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(70);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
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
//                            System.out.println("cancelo la tarea");
                            numpagina.addAndGet(-1);
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                } catch (Exception ex) {
                    Logger.getLogger(PuntosColAjustePuntosController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    @FXML
    void CancelarAction(ActionEvent event) {
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
        PuntosColAjustePuntosController.registros.clear();
        numpagina.set(0);
        try {
            pane.getChildren().remove(3);
        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        Atlas.vista.show();
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

        try {
//            final URL location = getClass().getResource("/com/co/allus/vistas/PuntosColAjustePuntos.fxml");
//            final FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(location);
//            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
//            final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());                  
//            final Button buscarFechas = (Button) root.lookup("#buscarFechas");

            Calendar calendarFiaux = Calendar.getInstance();
            Calendar calendarFfaux = Calendar.getInstance();

            calendarFiaux.setTime(calendarFi);
            calendarFfaux.setTime(calendarFf);
            final int diferenciaDias = gestorDoc.CalcularDifFechas(calendarFiaux, calendarFfaux);

//            System.out.println("diferencia dias " + diferenciaDias);
//            //
//            System.out.println("fecha limite " + formatoFecha.format(getFechalimite().getTime()));
//            System.out.println("fecha fecha ini " + formatoFecha.format(fechaini.getSelectedDate().getTime()));
            if (getFechalimite().before(fechaini.getCalendarView().getCalendar()) || formatoFecha.format(getFechalimite().getTime()).equals(formatoFecha.format(fechaini.getSelectedDate().getTime()))) {
                buscarFechas.setDisable(false);

            } else {
                buscarFechas.setDisable(true);
                return true;
            }

            if ((diferenciaDias <= 60) && (diferenciaDias != -1)) {

                buscarFechas.setDisable(false);
            } else {

                buscarFechas.setDisable(true);
            }

            return true;
        } catch (Exception e) {
//           e.printStackTrace();
            return true;
        }

    }

    @FXML
    void descripcionTkPress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(descripcionT, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            descripcionT.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void descripcionTktyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {
            synchronized (this) {
                BusquedaDescripcion(descripcionT.getText() + event.getCharacter());
            }
        } else {
            if (event.getCharacter().trim().isEmpty()) {
                if (descripcionT.getText().isEmpty()) {
                    // System.out.println("cargo todos de nuevo");
                    synchronized (this) {
                        BusquedaDescripcion("");
                    }
                } else {
                    // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                    synchronized (this) {
                        BusquedaDescripcion(descripcionT.getText());
                    }
                }
            }
        }
    }

    private void BusquedaDescripcion(final String descr) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoTablaAjustePuntos>> TaskTablaId = new BusqDescripAjustes(descr);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(TaskTablaId.progressProperty());
                    veil.visibleProperty().bind(TaskTablaId.runningProperty());
                    p.visibleProperty().bind(TaskTablaId.runningProperty());
                    tablaDatos.itemsProperty().bind(TaskTablaId.valueProperty());
                    TaskTablaId.reset();
                    TaskTablaId.start();

                    TaskTablaId.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<infoTablaAjustePuntos> TablaID = TaskTablaId.getValue();

//                            System.out.println("TERMINO TAREA");
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
                                                    final List<infoTablaAjustePuntos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoTablaAjustePuntos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(3);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(70);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
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
