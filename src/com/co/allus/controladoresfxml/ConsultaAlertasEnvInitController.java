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
import com.co.allus.modelo.alertasnot.AlertasEnviadas;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
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
 * @author alexander.lopez.o
 */
public class ConsultaAlertasEnvInitController implements Initializable {

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
    private TableColumn fecha;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private TableColumn horamensaje;
    @FXML
    private Button indmasreg;
    @FXML
    private TableColumn medioenvi;
    @FXML
    private TableColumn mensaje;
    @FXML
    private Button terminar_trx;
    @FXML
    private TableColumn tipotrx;
    @FXML
    private TableView<AlertasEnviadas> tabla_datos;
    @FXML
    private Button limpiarOp;
    @FXML
    private Label telefono;
    @FXML
    private Button buscarOp;
    @FXML
    private Label email;
    @FXML
    private StackPane panel_tabla;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private Service<ObservableList<AlertasEnviadas>> task = new ConsultaAlertasEnvInitController.GetAlertasEnviadasTask();
    private Service<ObservableList<AlertasEnviadas>> TaskdataTabla = new ConsultaAlertasEnvInitController.BusquedaFechaTask();
    public static ObservableList<AlertasEnviadas> alertasEnv = FXCollections.observableArrayList();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static AtomicInteger numpagina = new AtomicInteger(0);
    public static String numPaginaTotal = "";
    public static String txtemail = "";
    public static String txttelefono = "";
    private SimpleDateFormat formatoFechaO = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat formatoHoraO = new SimpleDateFormat("kkmmss");
    private SimpleDateFormat formatoFechaN = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat formatoHoraN = new SimpleDateFormat("kk:mm:ss");
    public static AtomicBoolean clearBusqueda = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert buscarOp != null : "fx:id=\"buscarOp\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert fecha != null : "fx:id=\"fecha\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert horamensaje != null : "fx:id=\"horamensaje\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert indmasreg != null : "fx:id=\"indmasreg\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert limpiarOp != null : "fx:id=\"limpiarOp\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert medioenvi != null : "fx:id=\"medioenvi\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert mensaje != null : "fx:id=\"mensaje\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert telefono != null : "fx:id=\"telefono\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";
        assert tipotrx != null : "fx:id=\"tipotrx\" was not injected: check your FXML file 'consultaAlertasEnvInit.fxml'.";

        this.resources = rb;
        this.location = url;

        ConsultaAlertasEnvInitController.cancelartarea.set(false);

        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tipotrx.setCellValueFactory(new PropertyValueFactory("colOperacion"));
        medioenvi.setCellValueFactory(new PropertyValueFactory("colIndalerta"));
        fecha.setCellValueFactory(new PropertyValueFactory("colFecha_envio"));
        horamensaje.setCellValueFactory(new PropertyValueFactory("colHora_envio"));
        mensaje.setCellValueFactory(new PropertyValueFactory("colMensaje"));
    }

    public String getNumPaginaTotal() {
        return numPaginaTotal;
    }

    public void setNumPaginaTotal(String numPaginaTotal) {
        ConsultaAlertasEnvInitController.numPaginaTotal = numPaginaTotal;
    }

    public String getTxtemail() {
        return txtemail;
    }

    public void setTxtemail(String txtemail) {
        ConsultaAlertasEnvInitController.txtemail = txtemail;
    }

    public String getTxttelefono() {
        return txttelefono;
    }

    public void setTxttelefono(String txttelefono) {
        ConsultaAlertasEnvInitController.txttelefono = txttelefono;
    }

    public void mostrarAlertasEnviadas() {
        // alertasEnv.clear();
        // numpagina.set(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/consultaAlertasEnvInit.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button buscarOP = (Button) root.lookup("#buscarOp");
                    final Button limpiarOp = (Button) root.lookup("#limpiarOp");
                    final Button terminartrx = (Button) root.lookup("#terminar_trx");
                    final Button indMasregistros = (Button) root.lookup("#indmasreg");
                    final Label email = (Label) root.lookup("#email");
                    final Label telefono = (Label) root.lookup("#telefono");
                    final TableView<AlertasEnviadas> tablaData = (TableView<AlertasEnviadas>) root.lookup("#tabla_datos");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

                    final DropShadow shadow = new DropShadow();

                    indMasregistros.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            indMasregistros.setCursor(Cursor.HAND);
                            indMasregistros.setEffect(shadow);
                        }
                    });

                    indMasregistros.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            indMasregistros.setCursor(Cursor.DEFAULT);
                            indMasregistros.setEffect(null);
                        }
                    });

                    buscarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            buscarOP.setCursor(Cursor.HAND);
                            buscarOP.setEffect(shadow);
                        }
                    });

                    buscarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            buscarOP.setCursor(Cursor.DEFAULT);
                            buscarOP.setEffect(null);
                        }
                    });

                    limpiarOp.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            limpiarOp.setCursor(Cursor.HAND);
                            limpiarOp.setEffect(shadow);
                        }
                    });

                    limpiarOp.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            limpiarOp.setCursor(Cursor.DEFAULT);
                            limpiarOp.setEffect(null);
                        }
                    });

                    terminartrx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            terminartrx.setCursor(Cursor.HAND);
                            terminartrx.setEffect(shadow);
                        }
                    });

                    terminartrx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            terminartrx.setCursor(Cursor.DEFAULT);
                            terminartrx.setEffect(null);
                        }
                    });

                    final TreeView<String> arbol_consultas = (TreeView<String>) pane.lookup("#arbol_consultas");
                    if (arbol_consultas != null) {
                        arbol_consultas.setDisable(true);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                        Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaData.itemsProperty().bind(task.valueProperty());
                    task.start();
                    /**
                     * configuracion de la paginacion
                     */
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tablaData.itemsProperty().unbind();


                            if (Integer.parseInt(getNumPaginaTotal()) > numpagina.get()) {
                                indMasregistros.setDisable(false);
                            } else {
                                indMasregistros.setDisable(true);
                            }

                            telefono.setText(getTxttelefono());
                            email.setText(getTxtemail());
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = alertasEnv.size() % rowsPerPage() == 0 ? alertasEnv.size() / rowsPerPage() : alertasEnv.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {
                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = alertasEnv.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = alertasEnv.size() / rowsPerPage();
                                        } else {
                                            lastIndex = alertasEnv.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<AlertasEnviadas> subList = alertasEnv.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<AlertasEnviadas> subList = alertasEnv.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                            pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                @Override
                                public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                    currentpageindex = newValue.intValue();
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            tablaData.getItems().setAll(alertasEnv.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= alertasEnv.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : alertasEnv.size())));
                                        }
                                    });
                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(0);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(102);
                            pagination.setVisible(true);
                            Atlas.vista.show();
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("ERROR EN LA CONSULTA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                    final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                                    pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));

                                    final TreeView<String> arbol_consultas = (TreeView<String>) pane.lookup("#arbol_consultas");
                                    arbol_consultas.getSelectionModel().clearSelection();
                                    numpagina.addAndGet(-1);
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
                            numpagina.addAndGet(-1);
                        }
                    });
                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    public class GetAlertasEnviadasTask extends Service<ObservableList<AlertasEnviadas>> {

        @Override
        protected Task<ObservableList<AlertasEnviadas>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    // CONSULTA ALERTAS ENVIADAS
                    //754,061,connid,codigoTransaccion,identificacion,numPagina,claveHw,tokenoauth,~
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaAlertasEnviadas = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    /*CODIFICACION AUTOMATICA*/
//                    final Runnable aFenix = new EnvioTipAutFenix(datosCliente.getCodemp(), "001");
//                    final Thread aFen = new Thread(aFenix);
//                    aFen.start();
                    tramaAlertasEnviadas.append("754,061,");
                    tramaAlertasEnviadas.append(datosCliente.getRefid());
                    tramaAlertasEnviadas.append(",");
                    tramaAlertasEnviadas.append(AtlasConstantes.COD_ALERTAS_ENV);
                    tramaAlertasEnviadas.append(",");
                    tramaAlertasEnviadas.append(datosCliente.getId_cliente());
                    tramaAlertasEnviadas.append(",");
                    tramaAlertasEnviadas.append(numpagina.addAndGet(1)); //NUM PAGINA
                    tramaAlertasEnviadas.append(",");
                    tramaAlertasEnviadas.append(datosCliente.getContraseña());
                    tramaAlertasEnviadas.append(",");
                    tramaAlertasEnviadas.append(datosCliente.getTokenOauth());
                    tramaAlertasEnviadas.append(",~");
                    /**
                     * 750,061,000,TRANSACCION
                     * EXITOSA,NUMPAGINA,CELULAR,CORREO,ALERTAS,~
                     *
                     * ALERTAS : descoperacion + "%" + email + "%" + numcelular
                     * + "%" + fechaenvio + "%" + horaenvio + "%" +
                     * tipomensajeenv + "%" + indalertaenv + ";
                     */
                    //System.out.println("ENVIO CONS ALERTAS : " + tramaAlertasEnviadas.toString());
                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ AlertasEnviadas = " + "##" + docs.obtenerHoraActual());
                        // Respuesta="754,061,000,TRANSACCI?N EXITOSA                                                   ,001,                    ,                                                            ,SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190627%0000152206%Bancolombia le informa el c?digo 384789 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190627%0000152440%Bancolombia le informa el c?digo 248710 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190627%0000152658%Bancolombia le informa el c?digo 746960 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190628%0000105137%Bancolombia le informa el c?digo 220567 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190628%0000105405%Bancolombia le informa el c?digo 632981 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190628%0000105620%Bancolombia le informa el c?digo 458494 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190628%0000160513%Bancolombia le informa el c?digo 821701 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190628%0000160718%Bancolombia le informa el c?digo 228598 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190628%0000160911%Bancolombia le informa el c?digo 326395 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190702%0000120626%Bancolombia le informa el c?digo 916434 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190702%0000120832%Bancolombia le informa el c?digo 104961 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190703%0000095652%Bancolombia le informa el c?digo 250258 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190703%0000095924%Bancolombia le informa el c?digo 686074 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190703%0000100139%Bancolombia le informa el c?digo 219662 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190703%0000185828%Bancolombia le informa el c?digo 541258 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190703%0000190012%Bancolombia le informa el c?digo 822062 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190703%0000190146%Bancolombia le informa el c?digo 610902 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190704%0000165211%Bancolombia le informa el c?digo 155326 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN RETIRO APP ALM  %                                                            %3203453215          %20190704%0000165513%Bancolombia le informa el c?digo 229480 para retirar el dinero. Inquietudes 018000931987.                                                   %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000110247%Bancolombia le informa el c?digo 493616 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000120237%Bancolombia le informa el c?digo 797022 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000120533%Bancolombia le informa el c?digo 947229 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000133627%Bancolombia le informa el c?digo 908786 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000133925%Bancolombia le informa el c?digo 803236 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000134214%Bancolombia le informa el c?digo 298071 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000135408%Bancolombia le informa el c?digo 639764 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000135541%Bancolombia le informa el c?digo 354249 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000140420%Bancolombia le informa el c?digo 800069 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000140639%Bancolombia le informa el c?digo 451541 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000141131%Bancolombia le informa el c?digo 352741 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000141358%Bancolombia le informa el c?digo 469070 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000141923%Bancolombia le informa el c?digo 660029 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000143305%Bancolombia le informa el c?digo 365590 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000144130%Bancolombia le informa el c?digo 139604 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000145640%Bancolombia le informa el c?digo 887114 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000161510%Bancolombia le informa el c?digo 173624 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190628%0000161829%Bancolombia le informa el c?digo 584244 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000121934%Bancolombia le informa el c?digo 152745 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000122142%Bancolombia le informa el c?digo 397873 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000150826%Bancolombia le informa el c?digo 650873 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000151350%Bancolombia le informa el c?digo 949988 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000152620%Bancolombia le informa el c?digo 303140 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000153115%Bancolombia le informa el c?digo 892151 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000153614%Bancolombia le informa el c?digo 704968 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000153954%Bancolombia le informa el c?digo 277844 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000155251%Bancolombia le informa el c?digo 665602 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000160001%Bancolombia le informa el c?digo 501284 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000161116%Bancolombia le informa el c?digo 773140 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000163353%Bancolombia le informa el c?digo 791551 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000164244%Bancolombia le informa el c?digo 502291 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000164831%Bancolombia le informa el c?digo 215495 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190702%0000165641%Bancolombia le informa el c?digo 315257 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000100749%Bancolombia le informa el c?digo 415390 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000101113%Bancolombia le informa el c?digo 496069 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000101511%Bancolombia le informa el c?digo 032622 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000122027%Bancolombia le informa el c?digo 036378 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000122706%Bancolombia le informa el c?digo 293384 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000124351%Bancolombia le informa el c?digo 518206 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000125508%Bancolombia le informa el c?digo 474947 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000130116%Bancolombia le informa el c?digo 859949 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000131837%Bancolombia le informa el c?digo 338560 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000145016%Bancolombia le informa el c?digo 546328 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000150346%Bancolombia le informa el c?digo 340553 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000150657%Bancolombia le informa el c?digo 617368 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000150909%Bancolombia le informa el c?digo 351251 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000154214%Bancolombia le informa el c?digo 783105 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000154537%Bancolombia le informa el c?digo 876138 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000155902%Bancolombia le informa el c?digo 794114 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000160217%Bancolombia le informa el c?digo 312479 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000160519%Bancolombia le informa el c?digo 902689 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000161625%Bancolombia le informa el c?digo 254068 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000161950%Bancolombia le informa el c?digo 746757 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000162252%Bancolombia le informa el c?digo 872436 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000165327%Bancolombia le informa el c?digo 508743 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000190701%Bancolombia le informa el c?digo 941917 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190703%0000190950%Bancolombia le informa el c?digo 161552 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190704%0000170116%Bancolombia le informa el c?digo 586729 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190704%0000170445%Bancolombia le informa el c?digo 516501 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN TRANS APP ALM   %                                                            %3203453215          %20190704%0000170726%Bancolombia le informa el c?digo 022402 para el env?o de dineXXXXXXquietudes al 018000931987.                                               %CELULAR   ;SOLICITUD PIN 1               %                                                            %3203453215          %20190610%0000123705%Bancolombia te informa el c?digo para abrir tu cuenta: 56271206130N XXXXXX                                                                  %CELULAR   ;SOLICITUD PIN 1               %                                                            %3203453215          %20190628%0000120825%77793806137N Bancolombia te informa el c?digo para cambiar tu correo electr?nico: XXXXXX                                                    %CELULAR   ;SOLICITUD PIN 1               %                                                            %                    %20190628%0000140744%56843006137N Bancolombia te informa el c?digo para cambiar tu n?mero de cuenta: XXXXXX                                                      %CORREO    ,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaAlertasEnviadas.toString());
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listrarconsultaprestamos = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaAlertasEnviadas.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex1) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ConsultaAlertasEnvInitController.cancelartarea.get()) {                                        
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

                        if (ConsultaAlertasEnvInitController.cancelartarea.get()) {
                            cancel();
                        } else {

                            //  tablaDatos.setItems(emptyObservableList);
                            // alertasEnv = FXCollections.observableArrayList();
                            setNumPaginaTotal(Respuesta.split(",")[4]);
                            setTxttelefono(Respuesta.split(",")[5]);
                            setTxtemail(Respuesta.split(",")[6]);

                            String[] Ltarjetas = Respuesta.split(",")[7].split(";");

                            for (int i = 0; i < Ltarjetas.length; i++) {
                                String[] datos = Ltarjetas[i].split("%");

                                String fecha = "";
                                try {

                                    fecha = formatoFechaN.format(formatoFechaO.parse(datos[3]));
                                } catch (Exception e) {
                                    fecha = datos[3];
                                }
                                String hora = "";
                                try {

                                    hora = formatoHoraN.format(formatoHoraO.parse(StringUtilities.eliminarCerosLeft(datos[4])));
                                } catch (Exception ex) {
                                    hora = datos[4];
                                }

                                AlertasEnviadas data = new AlertasEnviadas(
                                        datos[0].toLowerCase().trim(),
                                        datos[1].trim(),
                                        datos[2].trim(),
                                        fecha,
                                        hora,
                                        datos[5].trim(),
                                        datos[6].trim());

                                alertasEnv.add(data);

                            }

                        }

                    } else {

                        if (ConsultaAlertasEnvInitController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                                    final TreeView<String> arbol_consultas = (TreeView<String>) pane.lookup("#arbol_consultas");
                                    if (arbol_consultas != null) {
                                        arbol_consultas.setDisable(false);
                                    }

                                    arbol_consultas.getSelectionModel().clearSelection();
                                }
                            });

                            throw new Exception("ERROR");
                        }
                    }

                    return alertasEnv;

                }
            };
        }
    }

    @FXML
    void MasRegistros(ActionEvent event) {
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
                    tabla_datos.itemsProperty().bind(task.valueProperty());
                    task.reset();
                    //indmasreg.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<AlertasEnviadas> TablaID = task.getValue();

                            
                            if (Integer.parseInt(getNumPaginaTotal()) > numpagina.get()) {

                                indmasreg.setDisable(false);
                            } else {
                                indmasreg.setDisable(true);
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
                                                    final List<AlertasEnviadas> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<AlertasEnviadas> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                                pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                    @Override
                                    public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                        currentpageindex = newValue.intValue();
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaID.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaID.size())));
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
                                    Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, e);
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(102);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, e);
                            }

                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
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

                    Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    @FXML
    void aceptar(ActionEvent event) {
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
                ConsultaAlertasEnvInitController.alertasEnv.clear();
                ConsultaAlertasEnvInitController.numpagina.set(0);
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void buscarFecha(ActionEvent event) {
        try {

            if (esSeleccionado(fechaini.getCalendarView().getCalendar()) && esSeleccionado(fechafin.getCalendarView().getCalendar())) {
                consumirDatos();

            }
        } catch (Exception ex) {
            Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void limpiarConsulta(ActionEvent event) {
        try {
            fechaini.setSelectedDate(null);
            fechafin.setSelectedDate(null);
            clearBusqueda.set(true);
            ConsultarFechar();
        } catch (Exception e) {
            Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void consumirDatos() {
        if (esRangoValido(fechaini.getSelectedDate(), fechafin.getSelectedDate())) {
            clearBusqueda.set(false);
            ConsultarFechar();
        } else {
            fechaini.setSelectedDate(fechaini.getSelectedDate());
            fechafin.setSelectedDate(fechafin.getSelectedDate());
            final ObservableList<AlertasEnviadas> dataempty = FXCollections.emptyObservableList();
            tabla_datos.setItems(dataempty);
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
        } catch (Exception ex) {
            Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public class BusquedaFechaTask extends Service<ObservableList<AlertasEnviadas>> {

        @Override
        protected Task<ObservableList<AlertasEnviadas>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    ObservableList<AlertasEnviadas> datosae = FXCollections.observableArrayList();

                    if (clearBusqueda.get()) {

                        datosae.addAll(ConsultaAlertasEnvInitController.alertasEnv);

                    } else {

                        for (Iterator<AlertasEnviadas> it = ConsultaAlertasEnvInitController.alertasEnv.iterator(); it.hasNext();) {
                            final AlertasEnviadas data = it.next();

                            if (!data.colFecha_envioProperty().getValue().trim().isEmpty()) {
                                Date fechaEnvio = null;
                                try {
                                    fechaEnvio = formatoFechaN.parse(data.colFecha_envioProperty().getValue());
                                } catch (Exception e) {

                                    fechaEnvio = formatoFechaO.parse(data.colFecha_envioProperty().getValue());

                                }

                                if ((fechaini.getSelectedDate().before(fechaEnvio) && fechafin.getSelectedDate().after(fechaEnvio)) || (fechaini.getSelectedDate().equals(fechaEnvio) || fechafin.getSelectedDate().equals(fechaEnvio))) {
                                    datosae.add(data);
                                }
                            }
                        }
                    }

                    if (datosae.isEmpty()) {
                        tabla_datos.setPlaceholder(new Label("No hay registros asociados"));
                        throw new Exception("No hay Datos");
                    } else {
                        return datosae;
                    }

                }
            };
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
                    tabla_datos.itemsProperty().bind(TaskdataTabla.valueProperty());
                    TaskdataTabla.reset();
                    TaskdataTabla.start();

                    limpiarOp.setDisable(true);

                    TaskdataTabla.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            limpiarOp.setDisable(false);
                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<AlertasEnviadas> TablaBusFecha = TaskdataTabla.getValue();

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
                                                    final List<AlertasEnviadas> subList = TablaBusFecha.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<AlertasEnviadas> subList = TablaBusFecha.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                                pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                    @Override
                                    public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                        currentpageindex = newValue.intValue();
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                tabla_datos.getItems().setAll(TablaBusFecha.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaBusFecha.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaBusFecha.size())));
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
                                    Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(102);

                            } catch (Exception ex) {
                                Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });

                    TaskdataTabla.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            limpiarOp.setDisable(false);
//                            System.out.println("ERROR EN LA CONSULTA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    String message = t.getSource().getException().getMessage();
                                    ObservableList<AlertasEnviadas> dataempty = FXCollections.emptyObservableList();
                                    tabla_datos.setItems(dataempty);
                                }
                            });
                        }
                    });

                    TaskdataTabla.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            limpiarOp.setDisable(false);
//                            System.out.println("cancelo la tarea");
                            tabla_datos.setVisible(false);
                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {
                    Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

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
                tabla_datos.setPlaceholder(new Label("Rango mayor a 60 días"));
                return false;
            } else {
                tabla_datos.setPlaceholder(new Label("No hay registros asociados"));
            }

            if (diferenciaDias == -1) {
                tabla_datos.setPlaceholder(new Label("El rango de búsqueda no es válido"));
                ObservableList<AlertasEnviadas> dataempty = FXCollections.emptyObservableList();
                tabla_datos.setItems(dataempty);
                pagination.setVisible(false);

//                System.out.println("rango no es valido");
                return false;
            } else {
                tabla_datos.setPlaceholder(new Label("No hay registros asociados"));
            }

            if ((fini1 <= hoy1) && (fini1 <= ffin1) && (ffin1 <= hoy1)) {

                return true;
            } else {

                return false;

            }

        } catch (Exception ex) {
            Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
