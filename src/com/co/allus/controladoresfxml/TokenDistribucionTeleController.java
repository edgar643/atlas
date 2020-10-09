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
import com.co.allus.modelo.tokendistribucion.DatosDisToken;
import com.co.allus.modelo.tokendistribucion.InfoTokenTele;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
 * @author stephania.rojas
 */
public class TokenDistribucionTeleController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn colCiudad;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colResultado;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colObservaciones;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Button regresar_op;
    @FXML
    private TableView<InfoTokenTele> tabla_datos;
    @FXML
    private TextField tfFecha;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfJornada;
    @FXML
    private TextField tfNit;
    @FXML
    private TextField tfNombre;
    @FXML
    private Button obtMasReg;
    private static ObservableList<InfoTokenTele> dataTabla = FXCollections.observableArrayList();
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static ObservableList<InfoTokenTele> datos = FXCollections.observableArrayList();
    private Service<ObservableList<InfoTokenTele>> task = new TokenDistribucionTeleController.getTokenDistTele();
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
        assert colObservaciones != null : "fx:id=\"colObservaciones\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert colCiudad != null : "fx:id=\"colCiudad\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert colEstado != null : "fx:id=\"colEstado\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert colFecha != null : "fx:id=\"colFecha\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert colResultado != null : "fx:id=\"colResultado\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert colTelefono != null : "fx:id=\"colTelefono\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert regresar_op != null : "fx:id=\"regresar_op\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert tfFecha != null : "fx:id=\"tfFecha\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert tfID != null : "fx:id=\"tfID\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert tfJornada != null : "fx:id=\"tfJornada\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert tfNit != null : "fx:id=\"tfNit\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert tfNombre != null : "fx:id=\"tfNombre\" was not injected: check your FXML file 'TokenDistribucionGestTele.fxml'.";
        assert obtMasReg != null : "fx:id=\"obtMasReg\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        this.location = url;
        this.resources = rb;
        progreso.setVisible(false);
        colFecha.setCellValueFactory(new PropertyValueFactory<InfoTokenTele, String>("colFecha"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<InfoTokenTele, String>("colTelefono"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<InfoTokenTele, String>("colCiudad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<InfoTokenTele, String>("colEstado"));
        colResultado.setCellValueFactory(new PropertyValueFactory<InfoTokenTele, String>("colResultado"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<InfoTokenTele, String>("colObservacion"));

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
                            final ObservableList<InfoTokenTele> TablaID = task.getValue();


                            if ("S".equals(getIndicadorRegistros())) {
                                obtMasReg.setDisable(false);
                            } else {
                                obtMasReg.setDisable(true);
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
                                                    final List<InfoTokenTele> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTokenTele> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                                } catch (Exception ex) {
                                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(212);
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

    public class getTokenDistTele extends Service<ObservableList<InfoTokenTele>> {

        @Override
        protected Task<ObservableList<InfoTokenTele>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    String Respuesta = new String();

                    final Cliente datosCliente = Cliente.getCliente();
                    final StringBuilder tramaTokenDisEmpresas = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Cliente cliente = Cliente.getCliente();

                    //850,035,connid,codTransaccion,tipoDoc,identificacion,requiereResp,opcionConsulta,CodigoGuia,CodigoSerial,clavehardware
                    tramaTokenDisEmpresas.append("850,035,");
                    tramaTokenDisEmpresas.append(datosCliente.getRefid());
                    tramaTokenDisEmpresas.append(",");
                    tramaTokenDisEmpresas.append(AtlasConstantes.COD_TOKEN_DIST);
                    tramaTokenDisEmpresas.append(",");
                    tramaTokenDisEmpresas.append("1");
                    tramaTokenDisEmpresas.append(",");
                    tramaTokenDisEmpresas.append(datosCliente.getId_cliente());
                    tramaTokenDisEmpresas.append(",");
                    final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                    tramaTokenDisEmpresas.append(contra);
                    tramaTokenDisEmpresas.append(",");
                    tramaTokenDisEmpresas.append("04");
                    tramaTokenDisEmpresas.append(",");
                    tramaTokenDisEmpresas.append(DatosDisToken.getDatosdistri().getCodigo_guia());//NUM GUIA
                    tramaTokenDisEmpresas.append(",");
                    tramaTokenDisEmpresas.append("");//NUM SERIAL
                    tramaTokenDisEmpresas.append(",");
                    tramaTokenDisEmpresas.append("1");
                    tramaTokenDisEmpresas.append(",");
                    tramaTokenDisEmpresas.append(numpagina.incrementAndGet());
                    tramaTokenDisEmpresas.append(",");
                    final String chw = datosCliente.getContraseña().isEmpty() ? " " : datosCliente.getContraseña();
                    tramaTokenDisEmpresas.append(chw);
                    tramaTokenDisEmpresas.append(",");
                    tramaTokenDisEmpresas.append(datosCliente.getTokenOauth());
                    tramaTokenDisEmpresas.append(",~");

                    // System.out.println("TRAMA TOKENDIST TELE :" + tramaTokenDisEmpresas);
                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  token ditribucion = " + "##" + docs.obtenerHoraActual());
                        //EstadoGestion%FechaEnvio%NombreCiudad%Resultado%Observcion%NumeroTelefono

//                            Respuesta = "850,"
//                                    + "035,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "NOMBRE COMPLETO,"
//                                    + "COD SERIAL,"
//                                    + "COD GUIA,"
//                                    + "NOM PROOVEDOR,"
//                                    + "02/10/2017,"
//                                    + "MAÑANA,"
//                                    + "90,"
//                                    + "S,"
//                                    + "20/02/2017%30072016%Bogotá%Bogota%Gestion ok%Gestion Tel%observaciones;"
//                                    + "21/02/2017%30072017%Bogotá%Bogota%Gestion ok2%Gestion Tel2%observaciones2,"
//                                    + "~";
                        //System.out.println("RESPUESTA TRAMA TOKENDIST TELE:" + Respuesta);
                        //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDisEmpresas.toString());
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDisEmpresas.toString());

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion  = " + "##" + docs.obtenerHoraActual());
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDisEmpresas.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDisEmpresas.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
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

                        setIndicadorRegistros(Respuesta.split(",")[11]);
                        String data = Respuesta.split(",")[12];

                        if (dataTabla.isEmpty()) {
                            dataTabla = FXCollections.observableArrayList();
                        }
                        String[] detalles = data.split(";");
                        for (int i = 0; i < detalles.length; i++) {
                            final String[] datamov = detalles[i].split("%");
//
//                                   String colEstado,
//                                   String colFecha,
//                                   String colCiudad,
//                                   String colResultado,
//                                   String colObservacion,
//                                   String colTelefono
//                                    String coljornada,
//                                   String colfechavisita

                            final InfoTokenTele ObjMov = new InfoTokenTele(
                                    "", // ESTADO NO LLEGA EN LA TRAMA
                                    datamov[0].trim(),
                                    datamov[2].trim(),
                                    datamov[4].trim(),
                                    datamov[6].trim(),
                                    datamov[1].trim(),
                                    Respuesta.split(",")[9],
                                    Respuesta.split(",")[8]);

                            dataTabla.add(ObjMov);
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

                    return dataTabla;

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
                TokenDistribucionController controller = new TokenDistribucionController();
                controller.mostrarTokenDistribuidos();
            }
        });
    }

    public void mostrarTokenDistTele(final List<InfoTokenTele> data, final DatosDisToken datosditri, final String indiCadoMasReg, final int numPag) {
        this.dataTabla.clear();
        this.dataTabla.addAll(data);
        numpagina.set(numPag);
        setIndicadorRegistros(indiCadoMasReg);
        //System.out.println("INDICADOR MAS REGISTROS " + getIndicadorRegistros() + "---  NUMPAG "  + numpagina.get());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenDistribucionGestTele.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<InfoTokenTele> tabla_datos = (TableView<InfoTokenTele>) root.lookup("#tabla_datos");

                    final TextField tfID = (TextField) root.lookup("#tfID");
                    final TextField tfNombre = (TextField) root.lookup("#tfNombre");
                    final TextField tfNit = (TextField) root.lookup("#tfNit");
                    final TextField tffechav = (TextField) root.lookup("#tfFecha");
                    final TextField tfjornada = (TextField) root.lookup("#tfJornada");
                    final Button obtMasReg = (Button) root.lookup("#obtMasReg");

                    final Button regresar_op = (Button) root.lookup("#regresar_op");

                    final Cliente datosCliente = Cliente.getCliente();
                    tfNit.setText(datosCliente.getId_cliente());
                    tfID.setText(datosditri.getId_usuario());
                    tfNombre.setText(datosditri.getNombre_usuario());
                    tffechav.setText(data.get(0).getColFechaVisita());
                    tfjornada.setText(data.get(0).getColjornada());

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
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

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
                                        final List<InfoTokenTele> subList = datos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<InfoTokenTele> subList = datos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());

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
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutX(1);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(212);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , informar al área tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 5;
    }
}
