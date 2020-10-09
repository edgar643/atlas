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
import com.co.allus.modelo.alertasnot.ConsultaParametrizacion;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
public class ParametrizacionAlertasConsController implements Initializable {

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
    private Label email;
    @FXML
    private TableColumn fechaultParam2;
    @FXML
    private TableColumn fechaultparam;
    @FXML
    private Button indmasreg;
    @FXML
    private TableColumn monto;
    @FXML
    private TableColumn nroOp;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private StackPane panel_tabla2;
    @FXML
    private TableView<ConsultaParametrizacion> tabla_datos;
    @FXML
    private TableView<ConsultaParametrizacion> tabla_datos2;
    @FXML
    private Label telefono;
    @FXML
    private Button terminar_trx;
    @FXML
    private TableColumn tipotrx;
    @FXML
    private TableColumn tipotrx2;
    private Pagination pagination = new Pagination();
    private Pagination pagination2 = new Pagination();
    private static GestorDocumental docs = new GestorDocumental();
    int currentpageindex = 0;
    int currentpageindex2 = 0;
    public static AtomicInteger numpagina = new AtomicInteger(0);
    public static String numPaginaTotal = "";
    public static String txtemail = "";
    public static String txttelefono = "";
    public static String rutaArchivo = "";
    public static String rutaIfs = "";
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static ObservableList<ConsultaParametrizacion> parametrizacion = FXCollections.observableArrayList();
    public static ObservableList<ConsultaParametrizacion> parametrizacion2 = FXCollections.observableArrayList();
    private Service<ObservableList<ConsultaParametrizacion>> task = new ParametrizacionAlertasConsController.GetParemtrizacionesTask();
    private SimpleDateFormat formatoFechaO = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat formatoHoraO = new SimpleDateFormat("kkmmss");
    private SimpleDateFormat formatoFechaN = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat formatoHoraN = new SimpleDateFormat("kk:mm:ss");
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert fechaultParam2 != null : "fx:id=\"fechaultParam2\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert fechaultparam != null : "fx:id=\"fechaultparam\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert indmasreg != null : "fx:id=\"indmasreg\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert monto != null : "fx:id=\"monto\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert nroOp != null : "fx:id=\"nroOp\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert panel_tabla2 != null : "fx:id=\"panel_tabla2\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert tabla_datos2 != null : "fx:id=\"tabla_datos2\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert telefono != null : "fx:id=\"telefono\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert tipotrx != null : "fx:id=\"tipotrx\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";
        assert tipotrx2 != null : "fx:id=\"tipotrx2\" was not injected: check your FXML file 'ParametrizacionAlertasCons.fxml'.";

        this.resources = rb;
        this.location = url;

        ParametrizacionAlertasConsController.cancelartarea.set(false);
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tipotrx.setCellValueFactory(new PropertyValueFactory("colDescodigoOp"));
        monto.setCellValueFactory(new PropertyValueFactory("colMontoHabilitado"));
        nroOp.setCellValueFactory(new PropertyValueFactory("colNumOperacion"));
        fechaultparam.setCellValueFactory(new PropertyValueFactory("colFechaHora"));

        tipotrx2.setCellValueFactory(new PropertyValueFactory("colDescodigoOp"));
        fechaultParam2.setCellValueFactory(new PropertyValueFactory("colFechaHora"));

    }

    public void mostrarAlertasEnviadas() {

        // alertasEnv.clear();
        // numpagina.set(0);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ParametrizacionAlertasCons.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminartrx = (Button) root.lookup("#terminar_trx");
                    final Button indMasregistros = (Button) root.lookup("#indmasreg");
                    final Label email = (Label) root.lookup("#email");
                    final Label telefono = (Label) root.lookup("#telefono");
                    final TableView<ConsultaParametrizacion> tablaData = (TableView<ConsultaParametrizacion>) root.lookup("#tabla_datos");
                    final TableView<ConsultaParametrizacion> tablaData2 = (TableView<ConsultaParametrizacion>) root.lookup("#tabla_datos2");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final StackPane panel_tabla2 = (StackPane) root.lookup("#panel_tabla2");

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
                    //panel_tabla2.getChildren().addAll(veil, p);

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

                    try {
                        pane.getChildren().remove(3);

                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaData.itemsProperty().bind(task.valueProperty());
                    //tablaData2.itemsProperty().bind(task.valueProperty());
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

                            telefono.setText(parametrizacion.get(0).colCelularProperty().getValue());
                            email.setText(parametrizacion.get(0).colEmailProperty().getValue().trim().toLowerCase());
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = parametrizacion.size() % rowsPerPage() == 0 ? parametrizacion.size() / rowsPerPage() : parametrizacion.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {

                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = parametrizacion.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = parametrizacion.size() / rowsPerPage();
                                        } else {
                                            lastIndex = parametrizacion.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<ConsultaParametrizacion> subList = parametrizacion.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<ConsultaParametrizacion> subList = parametrizacion.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaData.getItems().setAll(parametrizacion.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= parametrizacion.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : parametrizacion.size())));
                                        }
                                    });

                                }
                            });

////                             /**
////                             * configuracion de la paginacion 2
////                             */
//                            final int numpag2 = parametrizacion2.size() % rowsPerPage2() == 0 ? parametrizacion2.size() / rowsPerPage2() : parametrizacion2.size() / rowsPerPage2() + 1;
//
//                            pagination2 = new Pagination(numpag2, 0);
//                            pagination2.setPageFactory(new Callback<Integer, Node>() {
//                                @Override
//                                public Node call(final Integer pageIndex) {
//
//                                    if (pageIndex > numpag2) {
//                                        return null;
//                                    } else {
//                                        int lastIndex = 0;
//                                        int displace = parametrizacion2.size() % rowsPerPage2();
//                                        if (displace >= 0) {
//                                            lastIndex = parametrizacion2.size() / rowsPerPage2();
//                                        } else {
//                                            lastIndex = parametrizacion2.size() / rowsPerPage2() - 1;
//                                        }
//                                        int page = pageIndex * itemsPerPage2();
//
//                                        for (int i = page; i < page + itemsPerPage2(); i++) {
//
//                                            if (lastIndex == pageIndex) {
//                                                final List<ConsultaParametrizacion> subList = parametrizacion2.subList(pageIndex * rowsPerPage2(), pageIndex * rowsPerPage2() + displace);
//                                                tablaData2.setItems(FXCollections.observableArrayList(subList));
//                                            } else {
//                                                final List<ConsultaParametrizacion> subList = parametrizacion2.subList(pageIndex * rowsPerPage2(), pageIndex * rowsPerPage2() + rowsPerPage2());
//                                                tablaData2.setItems(FXCollections.observableArrayList(subList));
//                                            }
//
//                                            panel_tabla2.getChildren().clear();
//                                            tablaData2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//                                            panel_tabla2.getChildren().add(tablaData2);
//                                            panel_tabla2.setVisible(true);
//                                        }
//                                        return panel_tabla2;
//                                    }
//                                }
//                            });
////
//
//                            pagination2.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
//                                @Override
//                                public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
//                                    currentpageindex2 = newValue.intValue();
//                                    Platform.runLater(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            tablaData2.getItems().setAll(parametrizacion2.subList(newValue.intValue() * rowsPerPage2(), ((newValue.intValue() * rowsPerPage2() + rowsPerPage2() <= parametrizacion2.size()) ? newValue.intValue() * rowsPerPage2() + rowsPerPage2() : parametrizacion2.size())));
//                                        }
//                                    });
//
//                                }
//                            });
                            tablaData2.setItems(parametrizacion2);

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(-2);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(55);
//                            root.getChildren().add(pagination2);
//                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(25);
//                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(328);

                            pagination.setVisible(true);
                            pagination2.setVisible(true);
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
                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });

    }

    public class GetParemtrizacionesTask extends Service<ObservableList<ConsultaParametrizacion>> {

        @Override
        protected Task<ObservableList<ConsultaParametrizacion>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA PARAMETRIZACION ALERTAS
                    //754,059,connid,codigoTransaccion,identificacion,numPagina,numArchivo,RutaIFS,claveHw,tokenoauth,~
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaConsultaParam = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    /*CODIFICACION AUTOMATICA*/
//                    final Runnable aFenix = new EnvioTipAutFenix(datosCliente.getCodemp(), "001");
//                    final Thread aFen = new Thread(aFenix);
//                    aFen.start();

                    tramaConsultaParam.append("754,059,");
                    tramaConsultaParam.append(datosCliente.getRefid());
                    tramaConsultaParam.append(",");
                    tramaConsultaParam.append(AtlasConstantes.COD_CONSULTA_PARAMETRIZACION);
                    tramaConsultaParam.append(",");
                    tramaConsultaParam.append(datosCliente.getId_cliente());
                    tramaConsultaParam.append(",");
                    tramaConsultaParam.append(numpagina.addAndGet(1)); //NUM PAGINA
                    tramaConsultaParam.append(",");
                    tramaConsultaParam.append(getRutaArchivo());
                    tramaConsultaParam.append(",");
                    tramaConsultaParam.append(getRutaIfs());
                    tramaConsultaParam.append(",");
                    tramaConsultaParam.append(datosCliente.getContraseña());
                    tramaConsultaParam.append(",");
                    tramaConsultaParam.append(datosCliente.getTokenOauth());
                    tramaConsultaParam.append(",~");

                    /**
                     * 754,059,000,transaccionExitosa,numPagina,numArchivo,RutaIFS,ALERTAS1;....ALERTASn,~
                     * ALERTAS(campos separados por %) def ALERTAS (-----)
                     * rebice erorr 754,059,codError,DescodError,~
                     *
                     * def ALERTAS (Código de operación%Descripción de la
                     * operación%Tipo de Operación%Tipo ID%Numero ID%Numero de
                     * celular%Email%Tipo de Cuenta%Numero de Cuenta%Numero de
                     * Operación%Monto Habilitado%Indicador de
                     * eliminación%Fecha%Hora;)
                     *
                     */
                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ AlertasEnviadas = " + "##" + docs.obtenerHoraActual());

//                        Respuesta = "754,059,000,transaccionExitosa,5,numArchivo,RutaIFS,"
//                                + "233%BLOQUEO POR SEGURIDAD T.CRED1%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%5000000%S%20190605%121523;"
//                                + "234%BLOQUEO POR SEGURIDAD T.CRED2%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%6000000%S%20190605%121523;"
//                                + "235%BLOQUEO POR SEGURIDAD T.CRED3%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%7000000%S%20190605%121523;"
//                                + "236%BLOQUEO POR SEGURIDAD T.CRED4%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%8000000%S%20190605%121523;"
//                                + "237%BLOQUEO POR SEGURIDAD T.CRED5%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%1000000%S%20190605%121523;"
//                                + "238%BLOQUEO POR SEGURIDAD T.CRED6%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%2000000%S%20190605%121523;"
//                                + "239%BLOQUEO POR SEGURIDAD T.CRED7%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%3000000%S%20190605%121523;"
//                                + "240%BLOQUEO POR SEGURIDAD T.CRED%CD%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%4000000%S%20190605%121523;"
//                                + "233%BLOQUEO POR SEGURIDAD T.CRED1%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%7000000%S%20190605%121523;"
//                                + "240%BLOQUEO POR SEGURIDAD T.CRED%CD%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%800000%S%20190605%121523;"
//                                + "240%BLOQUEO POR SEGURIDAD T.CRED%CD%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%4000000%S%20190605%121523;"
//                                + "240%BLOQUEO POR SEGURIDAD T.CRED%CD%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%300000%S%20190605%121523;"
//                                + "242%BLOQUEO POR SEGURIDAD T.CRED10%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%400000%S%20190605%121523;"
//                                + "233%BLOQUEO POR SEGURIDAD T.CRED1%NM%1%1020416841%3002589656%correo@eo.com%7%12345678901%3%500000%S%20190605%121523,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaConsultaParam.toString());

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listrarconsultaprestamos = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaConsultaParam.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            
                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ParametrizacionAlertasConsController.cancelartarea.get()) {
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

                        if (ParametrizacionAlertasConsController.cancelartarea.get()) {
                            cancel();
                        } else {

                            //  tablaDatos.setItems(emptyObservableList);
                            // alertasEnv = FXCollections.observableArrayList();
                            setNumPaginaTotal(Respuesta.split(",")[4]);
                            setRutaArchivo(Respuesta.split(",")[5]);
                            setRutaIfs(Respuesta.split(",")[6]);
//                            setTxttelefono(Respuesta.split(",")[5]);
//                            setTxtemail(Respuesta.split(",")[6]);

                            String[] Ltarjetas = Respuesta.split(",")[7].split(";");

                            for (int i = 0; i < Ltarjetas.length; i++) {

                                String[] datos = Ltarjetas[i].split("%");
                                String fecha = "";
                                try {

                                    fecha = formatoFechaN.format(formatoFechaO.parse(StringUtilities.eliminarCerosLeft(datos[12])));
                                } catch (Exception e) {
                                    fecha = StringUtilities.eliminarCerosLeft(datos[12]);
                                }
                                String hora = "";
                                try {

                                    //hora=StringUtilities.eliminarCerosLeft(datos[13]).trim().isEmpty()?"":(datos[13].substring(0, 2)+":"+datos[13].substring(2, 4)+":"+datos[13].substring(4, 6)+" "+datos[13].substring(6, 8)+"ms");
                                    hora = StringUtilities.eliminarCerosLeft(datos[13]).trim().isEmpty() ? "" : (datos[13].substring(2, 4) + ":" + datos[13].substring(4, 6) + ":" + datos[13].substring(6, 8));
                                    //hora =StringUtilities.eliminarCerosLeft(datos[13]).trim().isEmpty()?"": formatoHoraN.format(formatoHoraO.parse(datos[13]));
                                } catch (Exception e) {
                                    hora = StringUtilities.eliminarCerosLeft(datos[13]);
                                }

                                String monto = "";

                                try {
                                    //monto = formatonum.format(Double.parseDouble(datos[10].substring(0, datos[10].length() - 2))).replace(",", ".") + "," + datos[10].substring(datos[10].length() - 2, datos[10].length());
                                    monto = "$ " + formatonum.format(Double.parseDouble(datos[10]));
                                } catch (Exception e) {
                                    monto = datos[10];
                                }

                                ConsultaParametrizacion data = new ConsultaParametrizacion(
                                        datos[0],
                                        datos[1].toLowerCase().trim(),
                                        datos[2],
                                        datos[3],
                                        datos[4],
                                        datos[5],
                                        datos[6],
                                        datos[7],
                                        datos[8],
                                        datos[9],
                                        monto,
                                        datos[11],
                                        fecha + " " + hora);

                                if (datos[2].equalsIgnoreCase("NM")) {
                                    parametrizacion2.add(data);
                                } else {
                                    parametrizacion.add(data);
                                }

                            }

                        }

                    } else {

                        if (ParametrizacionAlertasConsController.cancelartarea.get()) {
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

                    return parametrizacion;

                }
            };
        }
    }

    public static String getNumPaginaTotal() {
        return numPaginaTotal;
    }

    public static void setNumPaginaTotal(String numPaginaTotal) {
        ParametrizacionAlertasConsController.numPaginaTotal = numPaginaTotal;
    }

    public static String getTxtemail() {
        return txtemail;
    }

    public static void setTxtemail(String txtemail) {
        ParametrizacionAlertasConsController.txtemail = txtemail;
    }

    public static String getTxttelefono() {
        return txttelefono;
    }

    public static void setTxttelefono(String txttelefono) {
        ParametrizacionAlertasConsController.txttelefono = txttelefono;
    }

    public static String getRutaArchivo() {
        return rutaArchivo;
    }

    public static void setRutaArchivo(String rutaArchivo) {
        ParametrizacionAlertasConsController.rutaArchivo = rutaArchivo;
    }

    public static String getRutaIfs() {
        return rutaIfs;
    }

    public static void setRutaIfs(String rutaIfs) {
        ParametrizacionAlertasConsController.rutaIfs = rutaIfs;
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
                            final ObservableList<ConsultaParametrizacion> TablaID = task.getValue();

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
                                                    final List<ConsultaParametrizacion> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<ConsultaParametrizacion> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                tabla_datos2.setItems(parametrizacion2);
                                try {
                                    AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);

                                } catch (Exception ex) {
                                    Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(-2);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(55);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);

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
                ParametrizacionAlertasConsController.parametrizacion.clear();
                ParametrizacionAlertasConsController.parametrizacion2.clear();
                ParametrizacionAlertasConsController.numpagina.set(0);
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }
            }
        });

    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 7;
    }

    public int itemsPerPage2() {
        return 1;
    }

    public int rowsPerPage2() {
        return 3;
    }
}
