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
import com.co.allus.modelo.alertasnot.ParametrizacionAlertas;
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
import javafx.scene.control.TableColumn.CellEditEvent;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ParametrizacionAlertasController implements Initializable {

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
    private TableColumn confalerta2;
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
    private TableView<ParametrizacionAlertas> tabla_datos;
    @FXML
    private TableView<ParametrizacionAlertas> tabla_datos2;
    @FXML
    private Label telefono;
    @FXML
    private Button terminar_trx;
    @FXML
    private TableColumn tipotrx;
    @FXML
    private TableColumn tipotrx2;
    @FXML
    private Button continuarParam;
    @FXML
    private TableColumn confalerta;
    @FXML
    private TableColumn validacion;
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
    @FXML
    private ProgressBar progreso;
    public static ObservableList<ParametrizacionAlertas> parametrizacion = FXCollections.observableArrayList();
    public static ObservableList<ParametrizacionAlertas> parametrizacion2 = FXCollections.observableArrayList();
    private Service<ObservableList<ParametrizacionAlertas>> task = new ParametrizacionAlertasController.GetParemtrizacionesTask();
    private SimpleDateFormat formatoFechaO = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat formatoHoraO = new SimpleDateFormat("kkmmss");
    private SimpleDateFormat formatoFechaN = new SimpleDateFormat("yyyy/MM/dd");
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient Service<Void> serviceRegAln = RegistrarAlertastask();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert confalerta != null : "fx:id=\"confalerta\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert confalerta2 != null : "fx:id=\"confalerta2\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert continuarParam != null : "fx:id=\"continuarParam\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert indmasreg != null : "fx:id=\"indmasreg\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert monto != null : "fx:id=\"monto\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert nroOp != null : "fx:id=\"nroOp\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert panel_tabla2 != null : "fx:id=\"panel_tabla2\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert tabla_datos2 != null : "fx:id=\"tabla_datos2\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert telefono != null : "fx:id=\"telefono\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert tipotrx != null : "fx:id=\"tipotrx\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert tipotrx2 != null : "fx:id=\"tipotrx2\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";
        assert validacion != null : "fx:id=\"validacion\" was not injected: check your FXML file 'ParametrizacionAlertas.fxml'.";

        this.resources = rb;
        this.location = url;

        ParametrizacionAlertasController.cancelartarea.set(false);
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tipotrx.setCellValueFactory(new PropertyValueFactory("colDescodigoOp"));

        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new ComboBoxCell();
            }
        };

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

        Callback<TableColumn, TableCell> cellFactory3 = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCellVal();
            }
        };
        Callback<TableColumn, TableCell> cellFactory4 = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new ComboBoxCell();
            }
        };

        confalerta.setCellValueFactory(new PropertyValueFactory<ParametrizacionAlertas, String>("colIndEliminacion"));
        confalerta.setCellFactory(cellFactory);
        confalerta.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ParametrizacionAlertas, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ParametrizacionAlertas, String> t) {
                ((ParametrizacionAlertas) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setColIndEliminacion(t.getNewValue());
                if (t.getNewValue().equalsIgnoreCase("N")) {
                    ((ParametrizacionAlertas) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setColMontoHabilitado("0");
                    ((ParametrizacionAlertas) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setColNumOperacion("0");

                }

            }
        });

        monto.setCellValueFactory(new PropertyValueFactory("colMontoHabilitado"));
        monto.setCellFactory(cellFactory1);
        monto.setOnEditCommit(new EventHandler<CellEditEvent<ParametrizacionAlertas, String>>() {
            @Override
            public void handle(CellEditEvent<ParametrizacionAlertas, String> t) {
                ((ParametrizacionAlertas) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColMontoHabilitado(t.getNewValue());
            }
        });

        nroOp.setCellValueFactory(new PropertyValueFactory("colNumOperacion"));
        nroOp.setCellFactory(cellFactory2);
        nroOp.setOnEditCommit(new EventHandler<CellEditEvent<ParametrizacionAlertas, String>>() {
            @Override
            public void handle(CellEditEvent<ParametrizacionAlertas, String> t) {
                ((ParametrizacionAlertas) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColNumOperacion(t.getNewValue());
            }
        });

        validacion.setCellValueFactory(new PropertyValueFactory("colvalidacion"));
        validacion.setCellFactory(cellFactory3);
        tipotrx2.setCellValueFactory(new PropertyValueFactory("colDescodigoOp"));
        confalerta2.setCellValueFactory(new PropertyValueFactory<ParametrizacionAlertas, String>("colIndEliminacion"));
        confalerta2.setCellFactory(cellFactory4);

        progreso.setVisible(false);

    }

    public void mostrarParamAlertas() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ParametrizacionAlertas.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminartrx = (Button) root.lookup("#terminar_trx");
                    final Button continuarop = (Button) root.lookup("#continuarParam");
                    final Button indMasregistros = (Button) root.lookup("#indmasreg");
                    final Label email = (Label) root.lookup("#email");
                    final Label telefono = (Label) root.lookup("#telefono");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<ParametrizacionAlertas> tablaData = (TableView<ParametrizacionAlertas>) root.lookup("#tabla_datos");
                    final TableView<ParametrizacionAlertas> tablaData2 = (TableView<ParametrizacionAlertas>) root.lookup("#tabla_datos2");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final StackPane panel_tabla2 = (StackPane) root.lookup("#panel_tabla2");

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    label_menu.setVisible(false);

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

                    continuarop.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            continuarop.setCursor(Cursor.HAND);
                            continuarop.setEffect(shadow);
                        }
                    });

                    continuarop.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            continuarop.setCursor(Cursor.DEFAULT);
                            continuarop.setEffect(null);

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
                        docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

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
                                                final List<ParametrizacionAlertas> subList = parametrizacion.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<ParametrizacionAlertas> subList = parametrizacion.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                            tablaData2.setItems(parametrizacion2);

                            tablaData.requestFocus();

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(1);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(35);
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
                                    } catch (Exception ex) {
                                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
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

                } catch (Exception ex) {
                    docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                }

            }
        });

    }

    public class GetParemtrizacionesTask extends Service<ObservableList<ParametrizacionAlertas>> {

        @Override
        protected Task<ObservableList<ParametrizacionAlertas>> createTask() {
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

                        // Respuesta = "754,059,000,000: Transacción exitosa,001,GDE0000002018213421201912101317171370S.txt,/tmp/prueba/,100%RETIROS                       %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;110%AVANCES                       %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;120%TRANSFERENCIAS                %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;130%PAGOS DE TARJETA DE CREDITO   %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;140%PAGOS A TERCEROS              %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;150%RECARGA DE CELULAR            %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;160%COMPRAS                       %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;170%DESEMBOLSO DE CREDIAGIL       %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;180%CARGA DE TARJETA E-PREPAGO    %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;190%SOLICITUD TARJETA E-PREPAGO   %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;222%TRANSFERENCIA CUENTA DESTINO  %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;223%ALERTAS DE GIROS              %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;224%ALERTA ENVÍO GIRO BENEFICIARIO%MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;227%ALERTA PAGAR GIRO             %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;228%ALERTA CANCELAR GIRO          %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;229%SOLICITUD CANCELACION GIRO    %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;232%BLOQUEO POR SEGURIDAD T.CRED  %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000000000%0%20191210%00083722;233%DECLINACION Y BLOQUEO T.CRED  %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;234%DECLINACION T.CRED            %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;235%ABONO T.CRED DE UN TERCERO    %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;236%ALERTA REENVIO GIRO           %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;237%ALERTA REVERSO CREAR GIRO     %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;270%DESEMBOLSO CREDIAGIL          %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;280%CARGA DE TARJETA E-PREPAGO    %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;290%SOLICITUD TARJETA E-PREPAGO   %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;291%Alerta Rev Pagar Gir Nacional %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;292%Alerta Rev Canelar Giro Nacion%MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;300%INSC. CUENTA DE TERCERO       %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000000000%0%20191210%00083722;310%GENERACION DE PRIMERA CLAVE   %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000000000%0%20191210%00083722;319%INSCRIPCIÓN MIGRACIÓN ODA     %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000000000%0%20191210%00083722;320%GENERACION SEGUNDA CLAVE      %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000000000%0%20191210%00083722;330%CAMBIO DE PRIMERA CLAVE       %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;340%CAMBIO DE SEGUNDA CLAVE       %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000000000%0%20191210%00083722;350%BLOQUEO DE PRIMERA CLAVE      %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000000000%0%20191210%00083722;360%BLOQUEO DE SEGUNDA CLAVE      %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;365%BLOQUEO CLAVE DINÁMICA        %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;370%CAMBIOS EN TOPES              %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;380%CAMBIO PARAMETROS ALERTAS     %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;600%RETIRO DE FONDOS DE INVERSION %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;610%CANCELACION FONDO DE INVERSION%NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;640%AVANCE SVC                    %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;660%NOTIFICACION PAGO EN CUENTA   %MO%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000050000%0%20191210%00083722;790%INSCRI FACTURA DELAY APROBADA %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000000000%0%20191210%00083722;800%INSCRI FACTURA DELAY APROBADA %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%001%000000000000000%0%20191210%00083722;801%INSCRIPCIÓN DE FACTURAS       %MN%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;802%PROGRAMACIÓN DE FACTURAS      %MN%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;803%INSCRIPCIÓN DE FACTURAS       %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000;804%PROGRAMACIÓN DE FACTURAS      %NM%01%000000201821342%3132569665          %alejocorreo25@gmail.com                                     %00%000000000000000%000%000000000000000%0%00000000%00000000,~";
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
                                    if (ParametrizacionAlertasController.cancelartarea.get()) {
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

                        if (ParametrizacionAlertasController.cancelartarea.get()) {
                            cancel();
                        } else {

                            final ObservableList<String> listacodigos = FXCollections.observableArrayList();

                            listacodigos.add("S");
                            listacodigos.add("N");

                            setNumPaginaTotal(Respuesta.split(",")[4]);
                            setRutaArchivo(Respuesta.split(",")[5]);
                            setRutaIfs(Respuesta.split(",")[6]);

                            String[] Ltarjetas = Respuesta.split(",")[7].split(";");

                            for (int i = 0; i < Ltarjetas.length; i++) {

                                String[] datos = Ltarjetas[i].split("%");
                                String fecha = "";
                                try {

                                    fecha = formatoFechaN.format(formatoFechaO.parse(StringUtilities.eliminarCerosLeft(datos[12])));
                                } catch (Exception ex) {
                                    docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                                }
                                {
                                    fecha = StringUtilities.eliminarCerosLeft(datos[12]);
                                }
                                String hora = "";
                                try {

                                    //hora=StringUtilities.eliminarCerosLeft(datos[13]).trim().isEmpty()?"":(datos[13].substring(0, 2)+":"+datos[13].substring(2, 4)+":"+datos[13].substring(4, 6)+" "+datos[13].substring(6, 8)+"ms");
                                    hora = StringUtilities.eliminarCerosLeft(datos[13]).trim().isEmpty() ? "" : (datos[13].substring(2, 4) + ":" + datos[13].substring(4, 6) + ":" + datos[13].substring(6, 8));
                                    //hora =StringUtilities.eliminarCerosLeft(datos[13]).trim().isEmpty()?"": formatoHoraN.format(formatoHoraO.parse(datos[13]));
                                } catch (Exception ex) {
                                    docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                                }
                                {
                                    hora = StringUtilities.eliminarCerosLeft(datos[13]);
                                }

                                String monto = "";

                                try {
                                    //monto = formatonum.format(Double.parseDouble(datos[10].substring(0, datos[10].length() - 2))).replace(",", ".") + "," + datos[10].substring(datos[10].length() - 2, datos[10].length());
                                    monto = formatonum.format(Double.parseDouble(datos[10]));
                                } catch (Exception ex) {
                                    docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                                }
                                {
                                    monto = datos[10];
                                }

                                String numOpe = "";
                                try {
                                    numOpe = Integer.parseInt(datos[9]) + "";

                                } catch (Exception ex) {
                                    docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                                }
                                {

                                    numOpe = datos[9];
                                }

                                String indEliminacion = "N";
                                try {
                                    if (datos[11].trim().equalsIgnoreCase("0") || datos[11].trim().isEmpty()) {
                                        indEliminacion = "S";
                                    }
                                    if (datos[11].trim().equalsIgnoreCase("1")) {
                                        indEliminacion = "N";
                                    }
                                    if (!getValidacionEliminacionAlert(monto, numOpe) && (datos[2].equalsIgnoreCase("MO"))) {
                                        indEliminacion = "N";
                                    }
                                    if (datos[2].equalsIgnoreCase("NM")) {
                                        if (fecha.trim().isEmpty() || hora.trim().isEmpty()) {
                                            indEliminacion = "N";
                                        }

                                    }
                                } catch (Exception ex) {
                                    docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                                }
                                {
                                    indEliminacion = datos[11];
                                }

                                ParametrizacionAlertas data = new ParametrizacionAlertas(
                                        datos[0],
                                        datos[1].toLowerCase().trim(),
                                        datos[2],
                                        datos[3],
                                        datos[4],
                                        datos[5],
                                        datos[6],
                                        datos[7],
                                        datos[8],
                                        numOpe,
                                        monto,
                                        indEliminacion,
                                        fecha + " " + hora,
                                        listacodigos);

                                if (datos[2].equalsIgnoreCase("NM")) {

                                    parametrizacion2.add(data);
                                } else if (datos[2].equalsIgnoreCase("MO")) {
                                    parametrizacion.add(data);
                                }

                            }

                        }

                    } else {

                        if (ParametrizacionAlertasController.cancelartarea.get()) {
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
        ParametrizacionAlertasController.numPaginaTotal = numPaginaTotal;
    }

    public static String getTxtemail() {
        return txtemail;
    }

    public static void setTxtemail(String txtemail) {
        ParametrizacionAlertasController.txtemail = txtemail;
    }

    public static String getTxttelefono() {
        return txttelefono;
    }

    public static void setTxttelefono(String txttelefono) {
        ParametrizacionAlertasController.txttelefono = txttelefono;
    }

    public static String getRutaArchivo() {
        return rutaArchivo;
    }

    public static void setRutaArchivo(String rutaArchivo) {
        ParametrizacionAlertasController.rutaArchivo = rutaArchivo;
    }

    public static String getRutaIfs() {
        return rutaIfs;
    }

    public static void setRutaIfs(String rutaIfs) {
        ParametrizacionAlertasController.rutaIfs = rutaIfs;
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
                            final ObservableList<ParametrizacionAlertas> TablaID = task.getValue();

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
                                                    final List<ParametrizacionAlertas> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<ParametrizacionAlertas> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                                }

                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(-2);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(55);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
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

                    docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });
    }

    @FXML
    void continuarOp(ActionEvent event) {
        boolean validacion = true;

        for (Iterator<ParametrizacionAlertas> it = parametrizacion.iterator(); it.hasNext();) {
            ParametrizacionAlertas parametrizacionAlertas = it.next();

            if (!(getTextoReal(parametrizacionAlertas.colvalidacionProperty().getValue()).split(":", -1)[0].equalsIgnoreCase("SI"))) {
                validacion = false;
                break;
            }

        }

        if (validacion) {

            if (serviceRegAln.isRunning()) {
                continuarParam.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceRegAln.progressProperty());
                serviceRegAln.reset();
                serviceRegAln.start();
            } else {
                continuarParam.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceRegAln.progressProperty());
                serviceRegAln.reset();
                serviceRegAln.start();
            }

            serviceRegAln.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Registrar Alertas" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                    ParametrizacionAlertasController.cancelartarea.set(false);
                }
            });

            serviceRegAln.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Cancelar Registrar Alertas" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            serviceRegAln.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    docs.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
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

    public Service<Void> RegistrarAlertastask() {
        serviceRegAln = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final Cliente cliente = Cliente.getCliente();

                        String Respuesta = new String();
                        final StringBuilder tramaRegistrarAlertas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();

                        final String celularCliente = telefono.getText();
                        final String emailCliente = email.getText();

                        StringBuilder Parametrizacion = new StringBuilder();
                        final Date fecha = new Date();
                        for (Iterator<ParametrizacionAlertas> it = parametrizacion.iterator(); it.hasNext();) {
                            ParametrizacionAlertas parametrizacionAlertas = it.next();

                            Parametrizacion.append(parametrizacionAlertas.colCodigoOpProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colDescodigoOpProperty().getValue().toUpperCase());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colTipoOpProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colTipoctaProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colNumCtaProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colNumOperacionProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colMontoHabilitadoProperty().getValue());
                            Parametrizacion.append(";");
                            String indEliminacion = parametrizacionAlertas.colIndEliminacionProperty().getValue().equalsIgnoreCase("S") ? "0" : "1";
                            Parametrizacion.append(indEliminacion);
                            Parametrizacion.append(";");
                            Parametrizacion.append(formatoFechaO.format(fecha));
                            Parametrizacion.append(";");
                            Parametrizacion.append(formatoHoraO.format(fecha));
                            Parametrizacion.append(";");
                            Parametrizacion.append(" ");
                            Parametrizacion.append(";");
                            Parametrizacion.append(" ");
                            Parametrizacion.append("%");

                        }

                        for (Iterator<ParametrizacionAlertas> it = parametrizacion2.iterator(); it.hasNext();) {
                            ParametrizacionAlertas parametrizacionAlertas = it.next();

                            Parametrizacion.append(parametrizacionAlertas.colCodigoOpProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colDescodigoOpProperty().getValue().toUpperCase());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colTipoOpProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colTipoctaProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colNumCtaProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colNumOperacionProperty().getValue());
                            Parametrizacion.append(";");
                            Parametrizacion.append(parametrizacionAlertas.colMontoHabilitadoProperty().getValue());
                            Parametrizacion.append(";");
                            String indEliminacion = parametrizacionAlertas.colIndEliminacionProperty().getValue().equalsIgnoreCase("S") ? "0" : "1";
                            Parametrizacion.append(indEliminacion);
                            Parametrizacion.append(";");
                            Parametrizacion.append(formatoFechaO.format(fecha));
                            Parametrizacion.append(";");
                            Parametrizacion.append(formatoHoraO.format(fecha));
                            Parametrizacion.append(";");
                            Parametrizacion.append(" ");
                            Parametrizacion.append(";");
                            Parametrizacion.append(" ");
                            Parametrizacion.append("%");

                        }

                        tramaRegistrarAlertas.append("754,067,");
                        tramaRegistrarAlertas.append(cliente.getRefid());
                        tramaRegistrarAlertas.append(",");
                        tramaRegistrarAlertas.append(AtlasConstantes.COD_PARAMETRIZAR_ALERTAS);
                        tramaRegistrarAlertas.append(",");
                        tramaRegistrarAlertas.append(cliente.getId_cliente());
                        tramaRegistrarAlertas.append(",");
                        tramaRegistrarAlertas.append(celularCliente);
                        tramaRegistrarAlertas.append(",");
                        tramaRegistrarAlertas.append(emailCliente);
                        tramaRegistrarAlertas.append(",");
                        tramaRegistrarAlertas.append(parametrizacion.size() + parametrizacion2.size());
                        tramaRegistrarAlertas.append(",");
                        tramaRegistrarAlertas.append(Parametrizacion.toString());
                        tramaRegistrarAlertas.append(",");
                        tramaRegistrarAlertas.append(cliente.getContraseña());
                        tramaRegistrarAlertas.append(",");
                        tramaRegistrarAlertas.append(cliente.getTokenOauth());
                        tramaRegistrarAlertas.append(",~");

//                        System.out.println("trama parametrizar  aln :" + tramaRegistrarAlertas);
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ AlertasEnviadas = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaRegistrarAlertas.toString());
                            // Respuesta = "754,062,000,TRANSACCION EXITOSA,TRACE~";
                            // Thread.sleep(2000);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listrarconsultaprestamos = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaRegistrarAlertas.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex1) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ParametrizacionAlertasController.cancelartarea.get()) {
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
                                    final ParametrizacionAlertasFinController controller = new ParametrizacionAlertasFinController();
                                    controller.motrarParametrizacionAlertasFin();
                                }
                            });
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ParametrizacionAlertasController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuarParam.setDisable(false);
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
        return serviceRegAln;
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
                ParametrizacionAlertasController.parametrizacion.clear();
                ParametrizacionAlertasController.parametrizacion2.clear();
                ParametrizacionAlertasController.numpagina.set(0);
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }
            }
        });

    }

    class ComboBoxCell extends TableCell<ParametrizacionAlertas, String> {

        private ComboBox<String> comboBox;

        public ComboBoxCell() {
            comboBox = new ComboBox();
            comboBox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(comboBox.getSelectionModel().getSelectedItem());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        try {
                            commitEdit(comboBox.getSelectionModel().getSelectedItem());
                        } catch (Exception ex) {
                            docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                        }

                    }
                }
            });

            comboBox.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(comboBox.getSelectionModel().getSelectedItem());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        try {
                            commitEdit(comboBox.getSelectionModel().getSelectedItem());
                        } catch (Exception ex) {
                            docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                        }


                    }

                }
            });
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                comboBox.setItems(getTableView().getItems().get(getIndex()).getColumn1List());
                comboBox.getSelectionModel().select(getItem());

                comboBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (!newValue) {
                            commitEdit(comboBox.getSelectionModel().getSelectedItem());
                        }
                    }
                });

                setText(null);
                setGraphic(comboBox);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    setText(null);
                    setGraphic(comboBox);
                } else {
                    setText(getItem());
                    setGraphic(null);

                    //
                }
            }
        }
    }

    public class EditingCell extends TableCell<ParametrizacionAlertas, String> {

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
                            commitEdit(formatonum.format(Double.parseDouble(textField.getText().replace(".", ""))));
                        } catch (Exception ex) {
                            docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                        }
                        {
                            cancelEdit();
                        }

                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        try {
                            commitEdit(formatonum.format(Double.parseDouble(textField.getText().replace(".", ""))));
                        } catch (Exception ex) {
                            docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
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
                            commitEdit(formatonum.format(Double.parseDouble(textField.getText().replace(".", ""))));
                        } catch (Exception ex) {
                            docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                        }
                        {
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
        private TableColumn<ParametrizacionAlertas, ?> getNextColumn(boolean forward) {
            List<TableColumn<ParametrizacionAlertas, ?>> columns = new ArrayList<>();
            for (TableColumn<ParametrizacionAlertas, ?> column : getTableView().getColumns()) {
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

        private List<TableColumn<ParametrizacionAlertas, ?>> getLeaves(TableColumn<ParametrizacionAlertas, ?> root) {
            List<TableColumn<ParametrizacionAlertas, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<ParametrizacionAlertas, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }

    public class EditingCellNumop extends TableCell<ParametrizacionAlertas, String> {

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
                            if (Integer.parseInt(textField.getText()) >= 999) {
                                cancelEdit();
                            } else {
                                commitEdit(Integer.parseInt(textField.getText()) + "");

                            }

                        } catch (Exception ex) {
                            docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                        }
                        {
                            cancelEdit();
                        }

                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        try {
                            if (Integer.parseInt(textField.getText()) >= 999) {
                                cancelEdit();
                            } else {
                                commitEdit(Integer.parseInt(textField.getText()) + "");
                            }

                        } catch (Exception ex) {
                            docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
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

                        commitEdit(textField.getText());
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
        private TableColumn<ParametrizacionAlertas, ?> getNextColumn(boolean forward) {
            List<TableColumn<ParametrizacionAlertas, ?>> columns = new ArrayList<>();
            for (TableColumn<ParametrizacionAlertas, ?> column : getTableView().getColumns()) {
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

        private List<TableColumn<ParametrizacionAlertas, ?>> getLeaves(TableColumn<ParametrizacionAlertas, ?> root) {
            List<TableColumn<ParametrizacionAlertas, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<ParametrizacionAlertas, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }

    public class EditingCellVal extends TableCell<ParametrizacionAlertas, String> {

        private TextField textField;

        public EditingCellVal() {
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

                        textField.setText(getTextoReal(getString()));
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getTextoReal(getString()));
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        public String getTextoReal(String text) {
            String dataPut = "";
            String[] string = text.split(":", -1);
            if (string[0].equalsIgnoreCase("N")) {
                dataPut = "SI:Cliente no recibirá alerta";
            } else if (string[0].equalsIgnoreCase("S")) {
                if (Double.parseDouble(string[1].replace(".", "")) <= 0) {
                    if (Integer.parseInt(string[2]) <= 0) {
                        dataPut = "NO:Monto y Operaciones Incorrecto";
                    } else {
                        dataPut = "NO:Monto Incorrecto";
                    }
                } else if (Integer.parseInt(string[2]) <= 0) {
                    dataPut = "NO:Operaciones Incorrecto";
                } else {
                    dataPut = "SI:Cliente recibirá alerta";
                }
            }

            return dataPut;

        }

        private void createTextField() {

            textField = new TextField(getTextoReal(getString()));
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());

                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        try {
                            commitEdit(textField.getText());

                        } catch (Exception ex) {
                            docs.imprimir("Error -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
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
                        commitEdit(textField.getText());
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
        private TableColumn<ParametrizacionAlertas, ?> getNextColumn(boolean forward) {
            List<TableColumn<ParametrizacionAlertas, ?>> columns = new ArrayList<>();
            for (TableColumn<ParametrizacionAlertas, ?> column : getTableView().getColumns()) {
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

        private List<TableColumn<ParametrizacionAlertas, ?>> getLeaves(TableColumn<ParametrizacionAlertas, ?> root) {
            List<TableColumn<ParametrizacionAlertas, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<ParametrizacionAlertas, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }

    public String getTextoReal(String text) {
        String dataPut = "";
        String[] string = text.split(":", -1);
        if (string[0].equalsIgnoreCase("N")) {
            dataPut = "SI:Cliente no recibirá alerta";
        } else if (string[0].equalsIgnoreCase("S")) {
            if (Double.parseDouble(string[1].replace(".", "")) <= 0) {
                if (Integer.parseInt(string[2]) <= 0) {
                    dataPut = "NO:Monto y Operaciones Incorrecto";
                } else {
                    dataPut = "NO:Monto Incorrecto";
                }
            } else if (Integer.parseInt(string[2]) <= 0) {
                dataPut = "NO:Operaciones Incorrecto";
            } else {
                dataPut = "SI:Cliente recibirá alerta";
            }
        }

        return dataPut;

    }

    public boolean getValidacionEliminacionAlert(String monto, String numOp) {
        boolean dataPut = true;

        if (Double.parseDouble(monto.replace(".", "")) <= 0) {
            if (Integer.parseInt(numOp) <= 0) {
                dataPut = false;
            } else {
                dataPut = false;
            }
        } else if (Integer.parseInt(numOp) <= 0) {
            dataPut = false;
        } else {
            dataPut = true;
        }

        return dataPut;

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
