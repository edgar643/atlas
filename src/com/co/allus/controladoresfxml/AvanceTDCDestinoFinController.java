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
import com.co.allus.modelo.avancestdc.datosListarTarjetas;
import com.co.allus.modelo.avancestdc.modeloAvancesTdc;
import com.co.allus.userComponent.DatePicker;
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
public class AvanceTDCDestinoFinController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Button buscarFechas;

    @FXML
    private Button regresar_trx;

    @FXML
    private Button cancelar_trx;

    @FXML
    private DatePicker fechafin;

    @FXML
    private DatePicker fechaini;

    @FXML
    private HBox menu_progreso;

    @FXML
    private StackPane panel_tabla;

    @FXML
    private ProgressBar progreso;

    @FXML
    private TableView<modeloAvancesTdc> tablaDatos;

    @FXML
    private TableColumn<modeloAvancesTdc, String> titular_destino;

    @FXML
    private TableColumn<modeloAvancesTdc, String> valor_avance;

    @FXML
    private TableColumn<modeloAvancesTdc, String> numero_tarjeta;

    @FXML
    private TableColumn<modeloAvancesTdc, String> cuenta_destino;

    @FXML
    private TableColumn<modeloAvancesTdc, String> fecha_trx;

    @FXML
    private TableColumn<modeloAvancesTdc, String> hora_trx;

    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static AtomicInteger numpagina = new AtomicInteger(0);
    private Service<ObservableList<modeloAvancesTdc>> task = new AvanceTDCDestinoFinController.GetAvanceTDCTask();
    public static ObservableList<modeloAvancesTdc> registros = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static String indicadorRegistros = "N";
    public static Calendar fechalimite = Calendar.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert buscarFechas != null : "fx:id=\"buscarFechas\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert regresar_trx != null : "fx:id=\"regresar_trx\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert cancelar_trx != null : "fx:id=\"cancelar_trx\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert cuenta_destino != null : "fx:id=\"cuenta_destino\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert fecha_trx != null : "fx:id=\"fecha_trx\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert hora_trx != null : "fx:id=\"hora_trx\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert numero_tarjeta != null : "fx:id=\"numero_tarjeta\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert titular_destino != null : "fx:id=\"titular_destino\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";
        assert valor_avance != null : "fx:id=\"valor_avance\" was not injected: check your FXML file 'AvanceTDCDestinoFin.fxml'.";

        this.resources = rb;
        this.location = url;

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaDatos.setPlaceholder(new Label("No se ha realizado ninguna consulta"));

        fecha_trx.setCellValueFactory(new PropertyValueFactory<modeloAvancesTdc, String>("fechaAvance"));
        hora_trx.setCellValueFactory(new PropertyValueFactory<modeloAvancesTdc, String>("horaAvance"));
        numero_tarjeta.setCellValueFactory(new PropertyValueFactory<modeloAvancesTdc, String>("numeroTarjeta"));
        valor_avance.setCellValueFactory(new PropertyValueFactory<modeloAvancesTdc, String>("valorAvance"));
        cuenta_destino.setCellValueFactory(new PropertyValueFactory<modeloAvancesTdc, String>("numeroCuenta"));
        titular_destino.setCellValueFactory(new PropertyValueFactory<modeloAvancesTdc, String>("nombreClienteDestino"));

        cuenta_destino.setVisible(false);

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);

        final Calendar instance = Calendar.getInstance();
        fechafin.setSelectedDate(instance.getTime());
        instance.add(Calendar.DAY_OF_MONTH, -90);
        fechaini.setSelectedDate(instance.getTime());
        setFechalimite(instance);

        progreso.setVisible(false);

        numpagina.set(0);
        cancelartarea.set(false);
        setIndicadorRegistros("N");
    }

    public void mostrarAvanceTDCDestinoFinController(final datosListarTarjetas data, final int origen) {
        if (origen == 0) {
            AvanceTDCDestinoFinController.registros.clear();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/AvanceTDCDestinoFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button regresar_op = (Button) root.lookup("#regresar_trx");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_trx");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final TableView<modeloAvancesTdc> tablaDatos = (TableView<modeloAvancesTdc>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    final DropShadow shadow = new DropShadow();
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
                                final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;

                                pagination = new Pagination(numpag, 0);
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
                                                    final List<modeloAvancesTdc> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<modeloAvancesTdc> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                                        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));

                                        final TreeView<String> arbol_infotdc = (TreeView<String>) pane.lookup("#arbol_infotdc");
                                        if (arbol_infotdc != null) {
                                            arbol_infotdc.setDisable(false);
                                        }
                                        arbol_infotdc.getSelectionModel().clearSelection();

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
                                                final List<modeloAvancesTdc> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<modeloAvancesTdc> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                Logger.getGlobal().log(Level.SEVERE, ex.toString());

                            }
                            pane.setAlignment(Pos.CENTER_LEFT);
                            pane.add(root, 1, 0);
                            Atlas.vista.show();
                        }
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });
    }

    public class GetAvanceTDCTask extends Service<ObservableList<modeloAvancesTdc>> {

        @Override
        protected Task<ObservableList<modeloAvancesTdc>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaAvancesTDC = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    final datosListarTarjetas seleccion = datosListarTarjetas.getDataListarTarjetas();

                    tramaAvancesTDC.append("850,076,");
                    tramaAvancesTDC.append(datosCliente.getRefid());
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append(AtlasConstantes.COD_AVANCESTDC_CONSULTA);
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append(datosCliente.getId_cliente());
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append("O");
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append(seleccion.getNumero());
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append(formatoFecha.format(fechaini.getSelectedDate()));
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append(formatoFecha.format(fechafin.getSelectedDate()));
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append(numpagina.addAndGet(1)); // cantReg
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append("50");
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append(seleccion.getBloqueoTarjeta());
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append(datosCliente.getContraseña());
                    tramaAvancesTDC.append(",");
                    tramaAvancesTDC.append(datosCliente.getTokenOauth());
                    tramaAvancesTDC.append(",~");

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Avances TDC SVP = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,076,000,0000,Accion ejecutada exitosamente,,1%000000055140775%JAIME DE JESUS GONZAL%0004099830163510054%1%000000055140829%JOSE2 MARIA VARGAS CASTRO%D%40610829000%20190805%112920%100000000.00%V;"
//                                    + "1%000000055140775%JAIME DE JESUS GONZAL%0004099830163510054%1%000000055140829%JOSE2 MARIA VARGAS CASTRO%D%40610829000%20190805%114307%10000.00%V;"
//                                    + "1%000000055140775%JAIME DE JESUS GONZAL%0004099830163510054%1%000000055140829%JOSE2 MARIA VARGAS CASTRO%D%40610829000%20190805%114843%10000.00%V;"
//                                    + "1%000000055140775%ROBERTO CEBALLOS%0004099830163510054%4%000001034987215%ROBERTO CEBALLOS%D%40617215000%20190821%95151%10000.00%V"
//                                    + ",~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaAvancesTDC.toString());

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Avances TDC SVP = " + "##" + gestorDoc.obtenerHoraActual());
                            //850,076,121,TRANSACCIÓN NO EFECTUADA,~
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaAvancesTDC.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta,0,3,",") + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex1) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (AvanceTDCDestinoFinController.cancelartarea.get()) {
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
                        final ObservableList<modeloAvancesTdc> emptyObservableList = FXCollections.emptyObservableList();
                        registros = FXCollections.observableArrayList();

                        String[] Lmovimientos = Respuesta.split(",")[6].split(";");

                        for (int i = 0; i < Lmovimientos.length; i++) {
                            String[] datos = Lmovimientos[i].split("%");

                            String FechaTrx = formatoFechaSalida.format(formatoFecha.parse(datos[9].trim()));
                            String HoraTrx = datos[10].trim();
                            if (HoraTrx.length() == 5) {
                                HoraTrx = "0" + HoraTrx;
                            }
                            HoraTrx = HoraTrx.substring(0, 2) + ":" + HoraTrx.substring(2, 4);
                            String ValorTrx = "$ " + formatonum.format(Double.parseDouble(datos[11].substring(0, datos[11].length() - 2))).replace(".", ",") + "." + datos[11].substring(datos[11].length() - 2, datos[11].length()).substring(0, 2);

                            final modeloAvancesTdc movimiento = new modeloAvancesTdc(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], datos[8], FechaTrx, HoraTrx, ValorTrx, datos[12]);
                            registros.add(movimiento);
                        }
                    } else {
                        if (AvanceTDCDestinoFinController.cancelartarea.get()) {
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
                    return registros;
                }
            };
        }
    }

    @FXML
    void regresar_op(ActionEvent event) {
        try {
            final AvanceTDCDestinoController controller = new AvanceTDCDestinoController();
            controller.mostrarAvanceTDCDestino();
        } catch (Exception e) {
            gestorDoc.imprimirExcepcion(e);
        }
    }

    @FXML
    void cancelar_op(ActionEvent event) {
        try {
            AvanceTDCDestinoFinController.cancelartarea.set(true);
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
    void buscarFechas(ActionEvent event) {

        AvanceTDCDestinoFinController.registros.clear();
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

                            try {
                                modeloAvancesTdc get = registros.get(0);

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
                                                        final List<modeloAvancesTdc> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                        tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                    } else {
                                                        final List<modeloAvancesTdc> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            task.reset();
                            numpagina.addAndGet(-1);
                        }
                    });

                } catch (Exception ex) {
                    Logger.getLogger(AvanceTDCDestinoFinController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public String getIndicadorRegistros() {
        return AvanceTDCDestinoFinController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        AvanceTDCDestinoFinController.indicadorRegistros = indicadorRegistros;
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

        if ((diferenciaDias <= 90) && (diferenciaDias != -1)) {
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
