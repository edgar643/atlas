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
import com.co.allus.modelo.alm.modeloAlmLog;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class ALMLogVinculacionController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button aceptar_trx;
    @FXML
    private Button buscarFechas;
    @FXML
    private RestrictiveTextField Celular;
    @FXML
    private DatePicker fechaini;
    @FXML
    private HBox menu_progreso;
    @FXML
    private TableView<modeloAlmLog> tablaDatos;
    @FXML
    private TableColumn<modeloAlmLog, String> numero_identificacion;
    @FXML
    private TableColumn<modeloAlmLog, String> numero_celular;
    @FXML
    private TableColumn<modeloAlmLog, String> fecha_vinculacion;
    @FXML
    private TableColumn<modeloAlmLog, String> descripcion_trx;
    @FXML
    private TableColumn<modeloAlmLog, String> hora;
    @FXML
    private TableColumn<modeloAlmLog, String> fecha_nacimiento;
    @FXML
    private TableColumn<modeloAlmLog, String> fecha_redeban;
    @FXML
    private TableColumn<modeloAlmLog, String> fecha_cifin;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Button limpiarOP;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static AtomicInteger numpagina = new AtomicInteger(0);
    private Service<ObservableList<modeloAlmLog>> task = new ALMLogVinculacionController.GetLogVinculacionTask();
    public static ObservableList<modeloAlmLog> registros = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static String indicadorRegistros = "N";
    public static Calendar fechalimite = Calendar.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert Celular != null : "fx:id=\"Celular\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert aceptar_trx != null : "fx:id=\"aceptar_trx\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert buscarFechas != null : "fx:id=\"buscarFechas\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert descripcion_trx != null : "fx:id=\"descripcion_trx\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert fecha_cifin != null : "fx:id=\"fecha_cifin\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert fecha_nacimiento != null : "fx:id=\"fecha_nacimiento\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert fecha_redeban != null : "fx:id=\"fecha_redeban\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert fecha_vinculacion != null : "fx:id=\"fecha_vinculacion\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert hora != null : "fx:id=\"hora\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert limpiarOP != null : "fx:id=\"limpiarOP\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert numero_celular != null : "fx:id=\"numero_celular\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert numero_identificacion != null : "fx:id=\"numero_identificacion\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ALMLogVinculacion.fxml'.";

        this.resources = rb;
        this.location = url;

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaDatos.setPlaceholder(new Label("No se ha realizado ninguna consulta"));

        numero_identificacion.setCellValueFactory(new PropertyValueFactory<modeloAlmLog, String>("numeroIdentificación"));
        numero_celular.setCellValueFactory(new PropertyValueFactory<modeloAlmLog, String>("numeroCelular"));
        fecha_vinculacion.setCellValueFactory(new PropertyValueFactory<modeloAlmLog, String>("fechaVinculación"));
        descripcion_trx.setCellValueFactory(new PropertyValueFactory<modeloAlmLog, String>("descripcion"));
        hora.setCellValueFactory(new PropertyValueFactory<modeloAlmLog, String>("horaMensaje"));
        fecha_nacimiento.setCellValueFactory(new PropertyValueFactory<modeloAlmLog, String>("fechaNacimiento"));
        fecha_redeban.setCellValueFactory(new PropertyValueFactory<modeloAlmLog, String>("fechaRedeban"));
        fecha_cifin.setCellValueFactory(new PropertyValueFactory<modeloAlmLog, String>("fechaCifin"));

        final Calendar instance = Calendar.getInstance();
        //fechaini.setSelectedDate(instance.getTime());

        buscarFechas.setDisable(true);

        progreso.setVisible(false);

        numpagina.set(0);
        cancelartarea.set(false);
        setIndicadorRegistros("N");

    }

    @FXML
    void limpiarOP(ActionEvent event) {
        fechaini.setSelectedDate(null);
    }

    public void mostrarALMLogVinculacion() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ALMLogVinculacion.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button botonbuscar = (Button) root.lookup("#buscarFechas");
                    final Button aceptar_op = (Button) root.lookup("#aceptar_trx");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final TableView<modeloAlmLog> tablaDatos = (TableView<modeloAlmLog>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
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
                    final DropShadow shadow = new DropShadow();

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

                    label_menu.setVisible(false);
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
                    //  task.start();

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
                                                final List<modeloAlmLog> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<modeloAlmLog> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(14);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(119);
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

                                    final TreeView<String> arbolDesbloqueoAlm = (TreeView<String>) pane.lookup("#arbolDesbloqueoAlm");
                                    if (arbolDesbloqueoAlm != null) {
                                        arbolDesbloqueoAlm.setDisable(false);
                                    }
                                    arbolDesbloqueoAlm.getSelectionModel().clearSelection();

                                }
                            });
                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                        }
                    });

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();

                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });
    }

    public class GetLogVinculacionTask extends Service<ObservableList<modeloAlmLog>> {

        @Override
        protected Task<ObservableList<modeloAlmLog>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaAlmLog = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    tramaAlmLog.append("850,085,");
                    tramaAlmLog.append(datosCliente.getRefid());
                    tramaAlmLog.append(",");
                    tramaAlmLog.append(AtlasConstantes.COD_CONSULTA_ALMLOG);
                    tramaAlmLog.append(",");
                    tramaAlmLog.append(datosCliente.getId_cliente());
                    tramaAlmLog.append(",");
                    tramaAlmLog.append(Celular.getText());
                    tramaAlmLog.append(",");
                    tramaAlmLog.append(fechaini.getSelectedDate() == null ? " " : formatoFecha.format(fechaini.getSelectedDate()));
                    tramaAlmLog.append(",");
                    tramaAlmLog.append(datosCliente.getContraseña());
                    tramaAlmLog.append(",");
                    tramaAlmLog.append(datosCliente.getTokenOauth());
                    tramaAlmLog.append(",~");

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Alm Log = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,081,000,TRANSACCION EXITOSA,"
//                                    + "1%72211012345%3003565356%20170101%Nueva ALM%13:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20170101%Nueva ALM%11:33%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20170101%Nueva ALM%09:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20170101%Nueva ALM%10:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20180301%Nueva ALM%11:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20180401%Nueva ALM%12:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20180501%Nueva ALM%13:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20180601%Nueva ALM%14:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20190101%Nueva ALM%15:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20190201%Nueva ALM%16:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20190301%Nueva ALM%17:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20190401%Nueva ALM%18:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20190501%Nueva ALM%19:45%19750413%20190101%20200101;"
//                                    + "1%72211012345%3003565356%20190601%Nueva ALM%20:45%19750413%20190101%20200101;"
//                                    + ",~";
                        // Respuesta="850,085,000,000 :TRANSACCION EXITOSA                                              ,001%000005432167131%00003005437131%20180515%%113333%1311I199%11231000%          ;002%000005432167131%00003005437131%20180301%%101000%19911231%20110101%01/01/2011;003%000005432167131%00003005437131%20180301%%101001%        %        %          ;004%000005432167131%00003005437131%20180301%%102721%19910131%20110101%01/01/2011;005%000005432167131%00003005437131%20180301%%102722%        %        %          ;006%000005432167131%00003005437131%20180301%%105517%19910131%20110101%01/01/2011;007%000005432167131%00003005437131%20180301%%105518%        %        %          ;008%000005432167131%00003005437131%20180301%%133252%19911231%20110101%01/01/2011;009%000005432167131%00003005437131%20180301%%133252%        %        %          ,~";

                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAlmLog.toString());
//                            System.out.println("respuesta Movimientos Bolsillo : " + Respuesta);
                        //Thread.sleep(3000);
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Alm Log = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAlmLog.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ALMLogVinculacionController.cancelartarea.get()) {
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
                        String[] Lmovimientos = Respuesta.split(",")[4].split(";");
                        for (int i = 0; i < Lmovimientos.length; i++) {

                            String[] datos = Lmovimientos[i].split("%");
                            String fechaVinculacion = "";
                            String fechaNacimiento = "";
                            String fechaRedeban = "";
                            String fechaCifin = "";
                            try {
                                fechaVinculacion = formatoFechaSalida.format(formatoFecha.parse(datos[3].trim()));
                            } catch (Exception e) {
                                fechaVinculacion = datos[3].trim();
                            }
                            try {
                                fechaNacimiento = formatoFechaSalida.format(formatoFecha.parse(datos[6].trim()));
                            } catch (Exception e) {
                                fechaNacimiento = datos[6].trim();
                            }

                            try {
                                fechaRedeban = formatoFechaSalida.format(formatoFecha.parse(datos[7].trim()));
                            } catch (Exception e) {
                                fechaRedeban = datos[7].trim();
                            }

//                            try {
//                                fechaCifin = formatoFechaSalida.format(formatoFecha.parse(datos[8].trim()));
//                            } catch (Exception e) {
//                                fechaCifin = datos[8].trim();
//                            }
                            String horamensaje = "";
                            try {
                                horamensaje = datos[5].substring(0, 2) + ":" + datos[5].substring(2, 4) + ":" + datos[5].substring(4, 6);
                            } catch (Exception e) {
                                horamensaje = datos[5];
                            }

                            modeloAlmLog movimiento = new modeloAlmLog(
                                    datos[0],
                                    StringUtilities.eliminarCerosLeft(datos[1]),
                                    StringUtilities.eliminarCerosLeft(datos[2]),
                                    fechaVinculacion,
                                    datos[4],
                                    horamensaje,
                                    fechaNacimiento,
                                    fechaRedeban,
                                    datos[8]);
                            registros.add(movimiento);
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
    void buscarFechas(ActionEvent event) {
        ALMLogVinculacionController.registros.clear();
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
                                modeloAlmLog get = registros.get(0);

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
                                                        final List<modeloAlmLog> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                        tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                    } else {
                                                        final List<modeloAlmLog> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                    Logger.getLogger(ALMLogVinculacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @FXML
    void aceptar_op(ActionEvent event) {
        try {
            ALMLogVinculacionController.cancelartarea.set(true);
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
    void cancelar_op(ActionEvent event) {
        try {
            ALMLogVinculacionController.cancelartarea.set(true);
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

    public String getIndicadorRegistros() {
        return ALMLogVinculacionController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        ALMLogVinculacionController.indicadorRegistros = indicadorRegistros;
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 8;
    }

    @FXML
    void valkeypressed(KeyEvent event) {

        if (!KeyCode.CONTROL.equals(event.getCode())) {
            if (KeyCode.DELETE.equals(event.getCode())) {
                if (!(event.getCode().impl_getChar().trim().equals(""))) {
                    buscarFechas.setDisable(false);
                } else {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(Celular, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    Celular.clear();
                    Celular.fireEvent(keyEvent);
                    buscarFechas.setDisable(true);
                }
            }

            if (Celular.getText().isEmpty()) {
                buscarFechas.setDisable(true);
            } else if (!Celular.getText().startsWith("3") || Celular.getText().trim().length() != 10) {
                buscarFechas.setDisable(true);
            } else {
                buscarFechas.setDisable(false);
            }
        }
    }
}
