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
import com.co.allus.modelo.consultamovimientos.infoMovPSE;
import com.co.allus.modelo.tpmovimientos.InfoTablaMovFin;
import com.co.allus.tareas.BusqIDconvPSE;
import com.co.allus.tareas.BusqNctaPSE;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class MovRecaudosPSEController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn<infoMovPSE, String> canal;
    @FXML
    private TextField codConvT;
    @FXML
    private TableColumn<infoMovPSE, String> codConvenio;
    @FXML
    private Button detalle;
    @FXML
    private DatePicker fechafin;
    @FXML
    private TableColumn<infoMovPSE, String> fechahora;
    @FXML
    private DatePicker fechaini;
    @FXML
    private TableColumn<infoMovPSE, String> nomConv;
    @FXML
    private TextField nrocta;
    @FXML
    private TableColumn<infoMovPSE, String> numcta;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoMovPSE> tablaDatos;
    @FXML
    private TableColumn<infoMovPSE, String> valor;
    @FXML
    private Button indMasReg;
    @FXML
    private Button volverInit;
    @FXML
    private Button buscarFechas;
    @FXML
    private Button limpiar;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static ObservableList<infoMovPSE> registros = FXCollections.observableArrayList();
    private Service<ObservableList<infoMovPSE>> task = new MovRecaudosPSEController.GetRegistrosTask();
    private Service<ObservableList<infoMovPSE>> TaskdataTabla = new MovRecaudosPSEController.BusquedaFechaTask();
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPagoO = new SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static AtomicInteger numpagina = new AtomicInteger(0);
    public static AtomicInteger totalREg = new AtomicInteger(0);
    public static AtomicInteger acumREg = new AtomicInteger(0);
    public static String indicadorRegistros = "N";
    public static String fechainiAux = "";
    public static String fechafinAux = "";
    public static String codigoConv = "";
    public static String tracePaginacion = "";
    public static AtomicBoolean clearBusqueda = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert canal != null : "fx:id=\"canal\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert codConvT != null : "fx:id=\"codConvT\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert codConvenio != null : "fx:id=\"codConvenio\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert detalle != null : "fx:id=\"detalle\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert fechahora != null : "fx:id=\"fechahora\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert nomConv != null : "fx:id=\"nomConv\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert nrocta != null : "fx:id=\"nrocta\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert numcta != null : "fx:id=\"numcta\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert valor != null : "fx:id=\"valor\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert volverInit != null : "fx:id=\"volverInit\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert buscarFechas != null : "fx:id=\"buscarFechas\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";
        assert limpiar != null : "fx:id=\"limpiar\" was not injected: check your FXML file 'MovRecaudosPSE.fxml'.";

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {

                    detalle.setDisable(false);
                } else {
                    detalle.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        codConvenio.setCellValueFactory(new PropertyValueFactory<infoMovPSE, String>("codigoconv"));
        nomConv.setCellValueFactory(new PropertyValueFactory<infoMovPSE, String>("numconv"));
        canal.setCellValueFactory(new PropertyValueFactory<infoMovPSE, String>("canalPago"));
        valor.setCellValueFactory(new PropertyValueFactory<infoMovPSE, String>("valorPagado"));
        fechahora.setCellValueFactory(new PropertyValueFactory<infoMovPSE, String>("fechaPago"));
        numcta.setCellValueFactory(new PropertyValueFactory<infoMovPSE, String>("cuentaRecaudoadora"));

//        setIndicadorRegistros("N");
//        setTracePaginacion("");
//        numpagina.set(0);
//        totalREg.set(0);
//        acumREg.set(0);
        indMasReg.setDisable(true);

    }

    public static String getTracePaginacion() {
        return tracePaginacion;
    }

    public static void setTracePaginacion(String tracePaginacion) {
        MovRecaudosPSEController.tracePaginacion = tracePaginacion;
    }

    public String getCodigoConv() {
        return codigoConv;
    }

    public void setCodigoConv(String codigoConv) {
        MovRecaudosPSEController.codigoConv = codigoConv;
    }

    public String getFechainiAux() {
        return MovRecaudosPSEController.fechainiAux;
    }

    public void setFechainiAux(String fechainiAux) {
        MovRecaudosPSEController.fechainiAux = fechainiAux;
    }

    public String getFechafinAux() {
        return MovRecaudosPSEController.fechafinAux;
    }

    public void setFechafinAux(String fechafinAux) {
        MovRecaudosPSEController.fechafinAux = fechafinAux;
    }

    public String getIndicadorRegistros() {
        return MovRecaudosPSEController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        MovRecaudosPSEController.indicadorRegistros = indicadorRegistros;
    }

    @FXML
    void ContinuarOP(ActionEvent event) {
        final infoMovPSE selectedItem = tablaDatos.getSelectionModel().getSelectedItem();
        final MovRecaudosPSEdetController controller = new MovRecaudosPSEdetController();
        controller.mostrarMovPSEdetalle(selectedItem);

    }

    public class BusquedaFechaTask extends Service<ObservableList<infoMovPSE>> {

        @Override
        protected Task<ObservableList<infoMovPSE>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    ObservableList<infoMovPSE> datosae = FXCollections.observableArrayList();

                    if (clearBusqueda.get()) {

                        datosae.addAll(MovRecaudosPSEController.registros);

                    } else {

                        for (Iterator<infoMovPSE> it = MovRecaudosPSEController.registros.iterator(); it.hasNext();) {
                            final infoMovPSE data = it.next();

                            if (!data.getFechaPago().trim().isEmpty()) {
                                Date fechaEnvio = null;
                                try {
                                    fechaEnvio = formatoFecha.parse(data.getFechaPago().substring(0, 10));
                                } catch (Exception e) {

                                    fechaEnvio = formatoFecha.parse(data.getFechaPago());

                                }

                                if ((fechaini.getSelectedDate().before(fechaEnvio) && fechafin.getSelectedDate().after(fechaEnvio)) || (fechaini.getSelectedDate().equals(fechaEnvio) || fechafin.getSelectedDate().equals(fechaEnvio))) {
                                    datosae.add(data);
                                }
                            }
                        }
                    }

                    if (datosae.isEmpty()) {
                        tablaDatos.setPlaceholder(new Label("No hay registros asociados"));
                        throw new Exception("No hay Datos");
                    } else {
                        return datosae;
                    }

                }
            };
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
                    indMasReg.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<infoMovPSE> TablaID = task.getValue();

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
                                                    final List<infoMovPSE> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoMovPSE> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                                } catch (Exception ex) {
                                    Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(88);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println(t.getSource().getException().getMessage());
                            acumREg.addAndGet(-numpagina.get());
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
                            acumREg.addAndGet(-numpagina.get());
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {

                    Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public class GetRegistrosTask extends Service<ObservableList<infoMovPSE>> {

        @Override
        protected Task<ObservableList<infoMovPSE>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // setNumpagina(getNumpagina() + 1);
                    final Cliente cliente = Cliente.getCliente();
                    String Respuesta = new String();

                    final StringBuilder tramaMOvPES = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramaMOvPES.append("850,062,");
                    tramaMOvPES.append(cliente.getRefid());
                    tramaMOvPES.append(",");
                    tramaMOvPES.append(AtlasConstantes.COD_CONS_MOV_PSE);
                    tramaMOvPES.append(",");
                    tramaMOvPES.append("S".equalsIgnoreCase(getIndicadorRegistros()) ? getTracePaginacion() : ""); // tracePaginacion
                    tramaMOvPES.append(",");
                    tramaMOvPES.append(cliente.getId_cliente());
                    tramaMOvPES.append(",");
                    tramaMOvPES.append(getFechainiAux());
                    tramaMOvPES.append(",");
                    tramaMOvPES.append(getFechafinAux());
                    tramaMOvPES.append(",");
                    tramaMOvPES.append(totalREg.get()); // registrosCanal
                    tramaMOvPES.append(",");
                    tramaMOvPES.append(acumREg.addAndGet(numpagina.get())); //registrosPaginacion
                    tramaMOvPES.append(",");
                    tramaMOvPES.append(getCodigoConv());
                    tramaMOvPES.append(",");
                    tramaMOvPES.append(cliente.getContraseña());
                    tramaMOvPES.append(",");
                    tramaMOvPES.append(cliente.getTokenOauth());
                    tramaMOvPES.append(",~");

//                    System.out.println("TRAMA mov PSE :" + tramaMOvPES);
                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ movmimientos PES = " + "##" + docs.obtenerHoraActual());

//
//                        Respuesta = "850,062,000,TRANSACCION EXITOSA,S,130,"
//                                + "087%C.E.S prepago Medicina%02/04/2017 14:25:18%02/04/2017%123456%nomref1%102087459%nomref2%000023%nomRef3%$115.000%$115.000%Cuenta Ahorro%2537763449%250723489%pse%Allus%carabobo%1033%1023;"
//                                + "1004%Instituto de Artes%02/04/2017 16:00:00%02/04/2017%321456%nomref1%69854782%nomref2%149955%nomRef3%$120.000%$120.000%Cuenta Ahorro%2537763449%250824569%pse%Konecta%la 33 %1034%1489;"
//                                + "1953%DIRECTV%04/04/2017 17:30:15%02/04/2017%123652%nomref1%4258746%nomref2%1478500%nomRef3%$87.500%$87.500%Cuenta de Ahorro%2537763449%250736895%telefónico%EPM%Centro Carabobo%1035%2936;"
//                                + "2014%EPM BOGOTA%03/04/2017 09:26:23%02/04/2017%188455451%nomref1%ref2%nomref2%000052%nomRef3%$95.000%$95.000%Cuenta de Ahorro%2537763449%250736985%virtual%Sempai S.A%Puerta Norte%1533%1026;"
//                                + "1015%UNE%04/04/2017 13:25:26%02/04/2017%125482%nomref1%1258961%nomref2%1485555%nomRef3%$113.000%$113.000%Cuenta de Ahorro%2537763449%250735896%pse%Directv%Bello Parque%1545%3696;,~";
                        // Respuesta = "850,062,000,TRANSACCION EXITOSA,N,000045,1564564564,26014          %PSE -DIAN                                                                       %20170601104441%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Corriente                     %00113806311         %26436       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601102603%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Corriente                     %00113806311         %26423       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601100332%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Corriente                     %00113806311         %26419       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601084239%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%102.03          %Ahorros                       %00113806311         %26378       %03                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601072521%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%102.03          %Ahorros                       %00113806311         %26372       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ,~";
                        // Thread.sleep(2000);
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMOvPES.toString());
                        // Respuesta="850,006,000,TRANSACCIÓN EXITOSA,000000000100000,000000000000000,000000001500000,000000000000000,-35000000,000001500000000,000000000000000,000000001500000,000000000000000,,               ,,000000000000000,11151215,00,0000000000,000000000000000,0,0000000000000000000,,0,,~";
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMOvPES.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_TDC);

                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        final String indMasREg = Respuesta.split(",")[4];
                        final String numReg = Respuesta.split(",")[5];

                        setIndicadorRegistros(indMasREg);
                        setTracePaginacion(Respuesta.split(",")[6]);
                        totalREg.set(Integer.parseInt(numReg));
                        numpagina.set(Respuesta.split(",")[7].split(";").length);

                        String[] LMov = Respuesta.split(",")[7].split(";");

                        for (int i = 0; i < LMov.length; i++) {

//            String codigoconv, 
//            String numconv, 
//            String fechaPago,
//            String fechaContable                    
//            String ref1, 
//            String nomRef1,
//            String ref2, 
//            String nomRef2, 
//            String ref3, 
//            String nomRef3,
//            String valorFactura                                
//            String valorPagado, 
//            String meotodoPagado, 
//            String cuentaRecaudoadora,
//            String paymentID, 
//            String canalPago,
//            String nombreCompania, 
//            String nombreOficina,
//            String codOficina,
//            String codPuntoPago
                            String[] datos = LMov[i].split("%");
                            String fechaPago = "";
                            String valorPagado = "";
                            String valorFactura = "";

                            try {
                                fechaPago = formatoFechaPago.format(formatoFechaPagoO.parse(datos[2].trim()));

                            } catch (Exception e) {
                                fechaPago = datos[2].trim();
                            }

                            try {
                                valorPagado = "$ " + formatonum.format(Double.parseDouble(datos[10].trim().split("\\.")[0])).replace(".", ",") + "." + datos[10].trim().split("\\.")[1];
                            } catch (Exception e) {
                                valorPagado = "$ " + datos[10];
                            }

                            try {
                                valorFactura = "$ " + formatonum.format(Double.parseDouble(datos[11].trim().split("\\.")[0])).replace(".", ",") + "." + datos[11].trim().split("\\.")[1];
                            } catch (Exception e) {
                                valorFactura = "$ " + datos[11];
                            }

                            infoMovPSE mov = new infoMovPSE(datos[0].trim(),
                                    datos[1].trim(),
                                    fechaPago,
                                    datos[3].trim(),
                                    datos[4].trim(),
                                    datos[5].trim(),
                                    datos[6].trim(),
                                    datos[7].trim(),
                                    datos[8].trim(),
                                    datos[9].trim(),
                                    valorPagado,
                                    valorFactura,
                                    datos[12].trim(),
                                    datos[13].trim(),
                                    datos[14].trim(),
                                    datos[15].trim(),
                                    datos[16].trim(),
                                    datos[17].trim(),
                                    datos[18].trim(),
                                    datos[19].trim());

                            registros.add(mov);

                        }

                    } else {
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                acumREg.addAndGet(-numpagina.get());
                            }
                        });

                    }

                    return registros;

                }
            };
        }
    }

    @FXML
    void buscarFechas(ActionEvent event) {
        try {

            if (esSeleccionado(fechaini.getCalendarView().getCalendar()) && esSeleccionado(fechafin.getCalendarView().getCalendar())) {
                consumirDatos();

            }
        } catch (Exception ex) {
            Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private boolean esSeleccionado(final Calendar calendar) {
        try {
            final int hora = calendar.get(Calendar.HOUR_OF_DAY);
            final int minu = calendar.get(Calendar.MINUTE);
            final int segu = calendar.get(Calendar.SECOND);

            if (hora == 0 && minu == 0 && segu == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    private void consumirDatos() {
        if (esRangoValido(fechaini.getSelectedDate(), fechafin.getSelectedDate())) {
            clearBusqueda.set(false);
            ConsultarFechar();
        } else {
            fechaini.setSelectedDate(fechaini.getSelectedDate());
            fechafin.setSelectedDate(fechafin.getSelectedDate());
            final ObservableList<infoMovPSE> dataempty = FXCollections.emptyObservableList();
            tablaDatos.setItems(dataempty);
        }
    }

    private boolean esRangoValido(final Date calendarFi, final Date calendarFf) {
        try {

            final String hoy = docs.obtenerFechaActualHoy();

            final String fini = docs.convertirFecha2(calendarFi);
            final String ffin = docs.convertirFecha2(calendarFf);

            final long hoy1 = Long.parseLong(hoy); // hoy

            final long fini1 = Long.parseLong(fini); //finicial
            final long ffin1 = Long.parseLong(ffin); //ffinal
            Calendar calendarFiaux = Calendar.getInstance();
            Calendar calendarFfaux = Calendar.getInstance();

            calendarFiaux.setTime(calendarFi);
            calendarFfaux.setTime(calendarFf);
            final int diferenciaDias = docs.CalcularDifFechas(calendarFiaux, calendarFfaux);

//            System.out.println("DIFERENCIA ENTRE FECHAS: " + diferenciaDias);
            if (diferenciaDias > 60) {
                tablaDatos.setPlaceholder(new Label("Rango mayor a 60 días"));
                return false;
            } else {
                tablaDatos.setPlaceholder(new Label("No hay registros asociados"));
            }

            if (diferenciaDias == -1) {
                tablaDatos.setPlaceholder(new Label("El rango de búsqueda no es válido"));
                ObservableList<infoMovPSE> dataempty = FXCollections.emptyObservableList();
                tablaDatos.setItems(dataempty);
                pagination.setVisible(false);

//                System.out.println("rango no es valido");
                return false;
            } else {
                tablaDatos.setPlaceholder(new Label("No hay registros asociados"));
            }

            if ((fini1 <= hoy1) && (fini1 <= ffin1) && (ffin1 <= hoy1)) {

                return true;
            } else {

                return false;

            }

        } catch (Exception e) {
            return false;
        }
    }

    private void ConsultarFechar() {
        // 1 fi,ff y codigo
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(TaskdataTabla.progressProperty());
                    veil.visibleProperty().bind(TaskdataTabla.runningProperty());
                    p.visibleProperty().bind(TaskdataTabla.runningProperty());
                    tablaDatos.itemsProperty().bind(TaskdataTabla.valueProperty());
                    TaskdataTabla.reset();
                    TaskdataTabla.start();

                    limpiar.setDisable(true);

                    TaskdataTabla.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            limpiar.setDisable(false);
                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<infoMovPSE> TablaBusFecha = TaskdataTabla.getValue();

                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaBusFecha.size() % rowsPerPage() == 0 ? TablaBusFecha.size() / rowsPerPage() : TablaBusFecha.size() / rowsPerPage() + 1;

                                pagination = new Pagination(numpag, 0);

                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaBusFecha.size() % rowsPerPage();
                                            if (displace >= 0) {
                                                lastIndex = TablaBusFecha.size() / rowsPerPage();
                                            } else {
                                                lastIndex = TablaBusFecha.size() / rowsPerPage() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infoMovPSE> subList = TablaBusFecha.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoMovPSE> subList = TablaBusFecha.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                                tablaDatos.getItems().setAll(TablaBusFecha.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaBusFecha.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaBusFecha.size())));
                                            }
                                        });

                                    }
                                });

                                /**
                                 * fin configuracion de la paginacion
                                 */
//                                System.out.println("tamaño del anchor busqueda ref" + AnchorPane.getChildren().size());
                                try {

                                    AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);

                                } catch (Exception ex) {
                                    Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(88);

                            } catch (Exception ex) {
                                Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                        }
                    });

                    TaskdataTabla.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            limpiar.setDisable(false);
//                            System.out.println("ERROR EN LA CONSULTA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    String message = t.getSource().getException().getMessage();
                                    ObservableList<infoMovPSE> dataempty = FXCollections.emptyObservableList();
                                    tablaDatos.setItems(dataempty);
                                }
                            });
                        }
                    });

                    TaskdataTabla.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            limpiar.setDisable(false);
//                            System.out.println("cancelo la tarea");
                            tablaDatos.setVisible(false);
                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {
                    Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);

                }

            }
        });

    }

    @FXML
    void limpiarFiltros(final ActionEvent event) {
        try {
            codConvT.setText("");
            nrocta.setText("");
            fechaini.setSelectedDate(null);
            fechafin.setSelectedDate(null);
            clearBusqueda.set(true);
            ConsultarFechar();
        } catch (Exception ex) {
            Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    @FXML
    void VolverPseInit(ActionEvent event) {
        final MovRecaudosPSEinitController controller = new MovRecaudosPSEinitController();
        controller.mostrarMovRecaudosPSE(true);
    }

    public void mostrarMovRecaudosPSE(final ObservableList<infoMovPSE> registross, final String indMasReg, final String totalReg, final int registrosPag, final String fechaini, final String fechafin, final String codigoconv, final String tracePaginacion, int origen) {
        if (origen == 1) {
            setFechafinAux(fechafin);
            setFechainiAux(fechaini);
            setIndicadorRegistros(indMasReg);
            totalREg.set(Integer.parseInt(totalReg));
            numpagina.set(registrosPag);
            acumREg.set(0);
            if ("S".equalsIgnoreCase(indMasReg)) {
                setTracePaginacion(tracePaginacion);
            } else {
                setTracePaginacion("");
            }

            MovRecaudosPSEController.registros.clear();
            MovRecaudosPSEController.registros.addAll(registross);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/MovRecaudosPSE.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button botoncontinuarOp = (Button) root.lookup("#detalle");
                    final Button indMasRegistros = (Button) root.lookup("#indMasReg");
                    final Button volverIni = (Button) root.lookup("#volverInit");
                    final Button buscarFechas = (Button) root.lookup("#buscarFechas");
                    final Button limpiar = (Button) root.lookup("#limpiar");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<infoMovPSE> tablaData = (TableView<infoMovPSE>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final DatePicker fechaini = (DatePicker) root.lookup("#fechaini");
                    final DatePicker fechafin = (DatePicker) root.lookup("#fechafin");

                    fechaini.setDateFormat(formatoFechaSalida);
                    fechafin.setDateFormat(formatoFechaSalida);

                    limpiar.setTooltip(new Tooltip("Borrar los filtros de busqueda"));
                    buscarFechas.setTooltip(new Tooltip("Buscar registros por fecha"));
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);

                    if ("S".equals(getIndicadorRegistros())) {
                        indMasRegistros.setDisable(false);
                    } else {
                        indMasRegistros.setDisable(true);
                    }

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

                    indMasRegistros.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            indMasRegistros.setCursor(Cursor.HAND);
                            indMasRegistros.setEffect(shadow);
                        }
                    });

                    indMasRegistros.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            indMasRegistros.setCursor(Cursor.DEFAULT);
                            indMasRegistros.setEffect(null);

                        }
                    });

                    volverIni.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            volverIni.setCursor(Cursor.HAND);
                            volverIni.setEffect(shadow);
                        }
                    });

                    volverIni.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            volverIni.setCursor(Cursor.DEFAULT);
                            volverIni.setEffect(null);

                        }
                    });

                    botoncontinuarOp.setDisable(true);
                    label_menu.setVisible(false);

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

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
                                        final List<infoMovPSE> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoMovPSE> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    tablaData.getItems().setAll(registros.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= registros.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : registros.size())));
                                }
                            });

                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    root.getChildren().add(pagination);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutX(0);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(88);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception e) {
                    Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, e);
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });

    }

    /**
     * BUSCAR POR ID
     */
    private void BusquedaConvenio(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoMovPSE>> TaskTablaId = new BusqIDconvPSE(id);

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
                            final ObservableList<infoMovPSE> TablaID = TaskTablaId.getValue();

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
                                                    final List<infoMovPSE> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoMovPSE> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(88);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            docs.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            docs.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });

                } catch (Exception ex) {
                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * BUSCAR POR ID
     */
    private void Busquedaxcuenta(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoMovPSE>> TaskTablaId = new BusqNctaPSE(id);

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
                            final ObservableList<infoMovPSE> TablaID = TaskTablaId.getValue();


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
                                                    final List<infoMovPSE> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoMovPSE> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(88);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            docs.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            docs.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    @FXML
    void codConvkPress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(codConvT, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            codConvT.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void codConvktyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    BusquedaConvenio(codConvT.getText() + event.getCharacter());

                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (codConvT.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {

                            BusquedaConvenio("");

                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {

                            BusquedaConvenio(codConvT.getText());

                        }
                    }

                }

            }

        }
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

    @FXML
    void nctakPress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(nrocta, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            nrocta.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void nctakTyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    Busquedaxcuenta(nrocta.getText() + event.getCharacter());

                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (nrocta.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {

                            Busquedaxcuenta("");

                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {

                            Busquedaxcuenta(nrocta.getText());

                        }
                    }

                }

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
