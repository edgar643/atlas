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
import com.co.allus.modelo.tokenempresas.infoTokenDisponibles;
import com.co.allus.tareas.BusqSerialTask;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
 * @author stephania.rojas
 */
public class TokenEmpresasDisponiblesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn colfecha_exp;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Button regresar_op;
    @FXML
    private TableColumn colserial;
    @FXML
    private TextField tfBusqueda_Serial;
    @FXML
    private TextField tfEsquema_Seguridad;
    @FXML
    private TextField tfNit;
    @FXML
    private TableView<infoTokenDisponibles> tabla_datos;
    @FXML
    private Label lbltitulo;
    @FXML
    private Button obtMasReg;
    private static List<infoTokenDisponibles> dataTabla = new ArrayList<infoTokenDisponibles>();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static ObservableList<infoTokenDisponibles> datos = FXCollections.observableArrayList();
    private Service<ObservableList<infoTokenDisponibles>> task = new TokenEmpresasDisponiblesController.getTokenDisp();
    private static AtomicInteger numpagina = new AtomicInteger(0);
    private String indicadorRegistros;

    public String getIndicadorRegistros() {
        return indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        this.indicadorRegistros = indicadorRegistros;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert colfecha_exp != null : "fx:id=\"colfecha_exp\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert regresar_op != null : "fx:id=\"regresar_op\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert colserial != null : "fx:id=\"colserial\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert tfBusqueda_Serial != null : "fx:id=\"tfBusqueda_Serial\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert tfEsquema_Seguridad != null : "fx:id=\"tfEsquema_Seguridad\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert tfNit != null : "fx:id=\"tfNit\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert lbltitulo != null : "fx:id=\"lbltitulo\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        assert obtMasReg != null : "fx:id=\"obtMasReg\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";

        this.location = url;
        this.resources = rb;
        progreso.setVisible(false);
        tfBusqueda_Serial.setEditable(true);
        obtMasReg.setDisable(true);
        colserial.setCellValueFactory(new PropertyValueFactory<infoTokenDisponibles, String>("colserial"));
        colfecha_exp.setCellValueFactory(new PropertyValueFactory<infoTokenDisponibles, String>("colfecha_exp"));

    }

    @FXML
    void obtMasRegOp(ActionEvent event) {
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
                    obtMasReg.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<infoTokenDisponibles> TablaID = task.getValue();


                            if ("S".equals(getIndicadorRegistros())) {
                                obtMasReg.setDisable(false);
                            } else {
                                obtMasReg.setDisable(true);
                            }

                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaID.size() % rowsPerPageId() == 0 ? TablaID.size() / rowsPerPageId() : TablaID.size() / rowsPerPageId() + 1;

                                pagination = new Pagination(numpag, 0);
                                //paginationid.setId("paginationid");
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaID.size() % rowsPerPageId();
                                            if (displace >= 0) {
                                                lastIndex = TablaID.size() / rowsPerPageId();
                                            } else {
                                                lastIndex = TablaID.size() / rowsPerPageId() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infoTokenDisponibles> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoTokenDisponibles> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + rowsPerPageId());
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
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPageId(), ((newValue.intValue() * rowsPerPageId() + rowsPerPageId() <= TablaID.size()) ? newValue.intValue() * rowsPerPageId() + rowsPerPageId() : TablaID.size())));
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
                                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(142);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(255);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            numpagina.decrementAndGet();
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            numpagina.decrementAndGet();
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public class getTokenDisp extends Service<ObservableList<infoTokenDisponibles>> {

        @Override
        protected Task<ObservableList<infoTokenDisponibles>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    String Respuesta = new String();

                    final Cliente datosCliente = Cliente.getCliente();
                    final StringBuilder tramaTokenEmpresas = new StringBuilder();
                    final List<infoTokenDisponibles> listadis = FXCollections.observableArrayList();
                    final ConectSS servicioSS = new ConectSS();
                    final Cliente cliente = Cliente.getCliente();
                    //850,034,connid,codTransaccion,tipoDoc,identificacion,requiereResp,claveHardware,~
                    tramaTokenEmpresas.append("850,034,");
                    tramaTokenEmpresas.append(datosCliente.getRefid());
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(AtlasConstantes.COD_TOKEN_EMPRESAS_DISPO);
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append("1");
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(datosCliente.getId_cliente());
                    tramaTokenEmpresas.append(",");
                    final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                    tramaTokenEmpresas.append(contra);
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(numpagina.incrementAndGet()); // numPagina
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(datosCliente.getContraseña());
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(datosCliente.getTokenOauth());
                    tramaTokenEmpresas.append(",~");

                    // System.out.println("TRAMA TOKEN EMPRESAS DISPONIBLE :" + tramaTokenEmpresas);
                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token empresas = " + "##" + gestorDoc.obtenerHoraActual());
                        //850,034,000,descripcion,nombreCompañia,tokenSecurity,registrosRecuperados,serial1%fechaExp1;serial2%fechaExp2;.....serialn%fechaExpn,~
//                            Respuesta = "850,"
//                                    + "034,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "BANCOLOMBIA,"
//                                    + "tokenSecurity,"
//                                    + "registrosRecuperados,"
//                                    + (numpagina.get()<=3?"S,":"N,")
//                                    + "10245864%06/09/2016;"
//                                    + "11445795%08/09/2016;"
//                                    + "1445795%08/09/2016;"
//                                    + "11445795%08/09/2016,"
//                                    + "~";
                        // System.out.println(" RESPUESTA TRAMA TOKEN EMPRESAS DISPONIBLE:" + Respuesta);
                        //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenEmpresas.toString());
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenEmpresas.toString());

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token empresas  = " + "##" + gestorDoc.obtenerHoraActual());
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenEmpresas.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenEmpresas.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    numpagina.decrementAndGet();

                                }
                            });

                        }

                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        setIndicadorRegistros(Respuesta.split(",")[7]);
                        String[] data = Respuesta.split(",")[8].split(";");
                        if (datos.isEmpty()) {
                            datos = FXCollections.observableArrayList();
                        }

                        for (int i = 0; i < data.length; i++) {
                            final String[] datoss = data[i].split("%");

                            final infoTokenDisponibles data2 = new infoTokenDisponibles(
                                    datoss[0].trim(),
                                    datoss[1].trim());

                            datos.add(data2);

                        }

                    } else {
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                cancel();
                                numpagina.decrementAndGet();
                            }
                        });

                    }

                    return datos;

                }
            };
        }
    }

    @FXML
    void regresar_op(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dataTabla.clear();
                TokenEmpresasController controller = new TokenEmpresasController();
                controller.mostrarDatosToken();
            }
        });
    }

    public void mostrarDispoToken(final List<infoTokenDisponibles> data, final String esquemadis, final String indiCadoMasReg, final int numPag) {
        this.dataTabla.clear();
        this.dataTabla.addAll(data);
        numpagina.set(numPag);
        setIndicadorRegistros(indiCadoMasReg);
        // System.out.println("INDICADOR MAS REGISTROS " + getIndicadorRegistros() + "---  NUMPAG "  + numpagina.get());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenEmpresasDisponibles.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<infoTokenDisponibles> tabla_datos = (TableView<infoTokenDisponibles>) root.lookup("#tabla_datos");

                    final TextField tfBusqueda_Serial = (TextField) root.lookup("#tfBusqueda_Serial");
                    final TextField tfNit = (TextField) root.lookup("#tfNit");
                    final TextField tfEsquema_Seguridad = (TextField) root.lookup("#tfEsquema_Seguridad");
                    final Button limpiar_op = (Button) root.lookup("#limpiar_op");
                    final Button regresar_op = (Button) root.lookup("#regresar_op");
                    final Button obtMasReg = (Button) root.lookup("#obtMasReg");
                    final Cliente datosCliente = Cliente.getCliente();
                    tfNit.setText(datosCliente.getId_cliente());
                    tfEsquema_Seguridad.setText(esquemadis);

                    if ("S".equalsIgnoreCase(getIndicadorRegistros())) {
                        obtMasReg.setDisable(false);
                    } else {
                        obtMasReg.setDisable(true);
                    }

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

                    obtMasReg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            obtMasReg.setCursor(Cursor.HAND);
                            obtMasReg.setEffect(shadow);
                        }
                    });

                    obtMasReg.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            obtMasReg.setCursor(Cursor.DEFAULT);
                            obtMasReg.setEffect(null);

                        }
                    });

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    /**
                     * Configuracion de la Paginación
                     */
                    datos.clear();
                    datos.addAll(data);
                    final int numpag = datos.size() % rowsPerPage() == 0 ? datos.size() / rowsPerPage() : datos.size() / rowsPerPage() + 1;

                    pagination = new Pagination(numpag, 0);
                    pagination.setPageFactory(new Callback<Integer, Node>() {
                        @Override
                        public Node call(final Integer pageIndex) {
                            if (pageIndex > numpag) {
                                return null;
                            } else {
                                int lastIndex = 0;
                                int displace = datos.size() % rowsPerPage();
                                if (displace >= 0) {
                                    lastIndex = datos.size() / rowsPerPage();
                                } else {
                                    lastIndex = datos.size() / rowsPerPage() - 1;
                                }
                                int page = pageIndex * itemsPerPage();

                                for (int i = page; i < page + itemsPerPage(); i++) {

                                    if (lastIndex == pageIndex) {
                                        final List<infoTokenDisponibles> subList = datos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoTokenDisponibles> subList = datos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());

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
                                    tabla_datos.getItems().setAll(datos.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= datos.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : datos.size())));
                                }
                            });

                        }
                    });

                    root.getChildren().add(pagination);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutX(142);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(255);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception ex) {
//                    ex.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n ,informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }

    /**
     * BUSCAR POR ID
     */
    private void BusqSerialTask(final String serial) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoTokenDisponibles>> TaskTablaSerial = new BusqSerialTask(serial);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(TaskTablaSerial.progressProperty());
                    veil.visibleProperty().bind(TaskTablaSerial.runningProperty());
                    p.visibleProperty().bind(TaskTablaSerial.runningProperty());
                    tabla_datos.itemsProperty().bind(TaskTablaSerial.valueProperty());
                    TaskTablaSerial.reset();
                    TaskTablaSerial.start();

                    TaskTablaSerial.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<infoTokenDisponibles> TablaID = TaskTablaSerial.getValue();

                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaID.size() % rowsPerPageSr() == 0 ? TablaID.size() / rowsPerPageSr() : TablaID.size() / rowsPerPageSr() + 1;

                                pagination = new Pagination(numpag, 0);
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaID.size() % rowsPerPageSr();
                                            if (displace >= 0) {
                                                lastIndex = TablaID.size() / rowsPerPageSr();
                                            } else {
                                                lastIndex = TablaID.size() / rowsPerPageSr() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infoTokenDisponibles> subList = TablaID.subList(pageIndex * rowsPerPageSr(), pageIndex * rowsPerPageSr() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoTokenDisponibles> subList = TablaID.subList(pageIndex * rowsPerPageSr(), pageIndex * rowsPerPageSr() + rowsPerPageSr());
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
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPageSr(), ((newValue.intValue() * rowsPerPageSr() + rowsPerPageSr() <= TablaID.size()) ? newValue.intValue() * rowsPerPageSr() + rowsPerPageSr() : TablaID.size())));
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
                                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(142);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(255);
                                pagination.setVisible(true);
                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

                        }
                    });

                    TaskTablaSerial.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                    TaskTablaSerial.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
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
    void serialbuscarkeytyped(final KeyEvent event) {

        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {
            if (isnumber(event.getCharacter())) {
                event.consume();
                synchronized (this) {
                    BusqSerialTask(tfBusqueda_Serial.getText() + event.getCharacter());
                }
            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (tfBusqueda_Serial.getText().isEmpty()) {
                        synchronized (this) {
                            BusqSerialTask("");
                        }
                    } else {
                        synchronized (this) {
                            BusqSerialTask(tfBusqueda_Serial.getText());
                        }
                    }

                }
            }
        }
    }

    @FXML
    void serialbuscarkeypress(final KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(tfBusqueda_Serial, "*", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            tfBusqueda_Serial.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();
        }
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPageId() {
        return 3;
    }

    public int rowsPerPage() {
        return 3;
    }

    public int rowsPerPageSr() {
        return 3;
    }
}
