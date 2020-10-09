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
import com.co.allus.modelo.tokendistribucion.InfoTokenEntrega;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
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
public class TokenDistribucionEntregaController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn colCiudad;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colObservaciones;
    @FXML
    private TableColumn colResultado;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Button regresar_op;
    @FXML
    private TableView<InfoTokenEntrega> tabla_datos;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfNit;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfSerial;
    @FXML
    private Button obtMasReg;
    private static ObservableList<InfoTokenEntrega> dataTabla = FXCollections.observableArrayList();
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static ObservableList<InfoTokenEntrega> datos = FXCollections.observableArrayList();
    private Service<ObservableList<InfoTokenEntrega>> task = new TokenDistribucionEntregaController.getTokenDistDetalle();
    private static AtomicInteger numpagina = new AtomicInteger(0);
    private String indicadorRegistros;
    private transient DecimalFormat formatoint = new DecimalFormat("#");

    public String getIndicadorRegistros() {
        return indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        this.indicadorRegistros = indicadorRegistros;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert colCiudad != null : "fx:id=\"colCiudad\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert colDireccion != null : "fx:id=\"colDireccion\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert colEstado != null : "fx:id=\"colEstado\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert colFecha != null : "fx:id=\"colFecha\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert colObservaciones != null : "fx:id=\"colObservaciones\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert colResultado != null : "fx:id=\"colResultado\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert regresar_op != null : "fx:id=\"regresar_op\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert tfID != null : "fx:id=\"tfID\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert tfNit != null : "fx:id=\"tfNit\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert tfNombre != null : "fx:id=\"tfNombre\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert tfSerial != null : "fx:id=\"tfSerial\" was not injected: check your FXML file 'TokenDistribucionEntrega.fxml'.";
        assert obtMasReg != null : "fx:id=\"obtMasReg\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        this.location = url;
        this.resources = rb;
        progreso.setVisible(false);

        colFecha.setCellValueFactory(new PropertyValueFactory<InfoTokenEntrega, String>("colFecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<InfoTokenEntrega, String>("colEstado"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<InfoTokenEntrega, String>("colDireccion"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<InfoTokenEntrega, String>("colCiudad"));
        colResultado.setCellValueFactory(new PropertyValueFactory<InfoTokenEntrega, String>("colResultado"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<InfoTokenEntrega, String>("colObservacion"));

    }

    @FXML
    void regresar_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dataTabla.clear();
                TokenDistribucionController controller = new TokenDistribucionController();
                controller.mostrarTokenDistribuidos();
            }
        });
    }

    public class getTokenDistDetalle extends Service<ObservableList<InfoTokenEntrega>> {

        @Override
        protected Task<ObservableList<InfoTokenEntrega>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    String Respuesta = new String();

                    final Cliente datosCliente = Cliente.getCliente();
                    final StringBuilder tramaTokenDistEntrega = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Cliente cliente = Cliente.getCliente();
                    final String CodSerial = formatoint.format(Double.parseDouble(DatosDisToken.getDatosdistri().getCodigo_serial()));

                    //850,035,connid,codTransaccion,tipoDoc,identificacion,requiereResp,opcionConsulta,CodigoGuia,CodigoSerial,clavehardware
                    tramaTokenDistEntrega.append("850,035,");
                    tramaTokenDistEntrega.append(datosCliente.getRefid());
                    tramaTokenDistEntrega.append(",");
                    tramaTokenDistEntrega.append(AtlasConstantes.COD_TOKEN_DIST);
                    tramaTokenDistEntrega.append(",");
                    tramaTokenDistEntrega.append("1");
                    tramaTokenDistEntrega.append(",");
                    tramaTokenDistEntrega.append(datosCliente.getId_cliente());
                    tramaTokenDistEntrega.append(",");
                    final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                    tramaTokenDistEntrega.append(contra);
                    tramaTokenDistEntrega.append(",");
                    tramaTokenDistEntrega.append("05");
                    tramaTokenDistEntrega.append(",");
                    tramaTokenDistEntrega.append(DatosDisToken.getDatosdistri().getCodigo_guia());//NUM GUIA
                    tramaTokenDistEntrega.append(",");
                    tramaTokenDistEntrega.append(StringUtilities.eliminarCerosLeft(CodSerial));//NUM SERIAL
                    tramaTokenDistEntrega.append(",");
                    tramaTokenDistEntrega.append(numpagina.incrementAndGet());
                    tramaTokenDistEntrega.append(",");
                    final String chw = datosCliente.getContraseña().isEmpty() ? " " : datosCliente.getContraseña();
                    tramaTokenDistEntrega.append(chw);
                    tramaTokenDistEntrega.append(",");
                    tramaTokenDistEntrega.append(datosCliente.getTokenOauth());
                    tramaTokenDistEntrega.append(",~");

                    // System.out.println("TRAMA TOKENDIST ENT :" + tramaTokenDistEntrega);
                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token distribucion entrega  = " + "##" + docs.obtenerHoraActual());
                        //DescripcionEstado%FechaEnvio%GestionEntrega%Direccion%NombreCiudad%Observacion

//                            Respuesta = "850,"
//                                    + "035,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "NOMBRE,"
//                                    + "COD SERIAL,"
//                                    + "CODIGO GUIA,"
//                                    + "NOM PROOVEDOR,"
//                                    + "150,"
//                                    + "S,"
//                                    + "30072016%CALLE FALSA 123%BOGOTA%BOGOTA%ENTREGA EFECTIVA%ENTREGADO%TOKEN 1 OBS;"
//                                    + "30072017%CALLE FALSA 124%BOGOTA%BOGOTA%ENTREGA EFECTIVA%ENTREGADO%TOKEN 2 OBS,"
//                                    + "~";
                        // System.out.println("RESPUESTA TRAMA TOKENDIST ENT:" + Respuesta);
                        // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDistEntrega.toString());
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDistEntrega.toString());

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion entrega = " + "##" + docs.obtenerHoraActual());
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDistEntrega.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDistEntrega.toString());
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

                        String data = Respuesta.split(",")[10];
                        setIndicadorRegistros(Respuesta.split(",")[9]);

                        if (dataTabla.isEmpty()) {
                            dataTabla = FXCollections.observableArrayList();
                        }

                        String[] detalles = data.split(";");
                        for (int i = 0; i < detalles.length; i++) {
                            final String[] datamov = detalles[i].split("%");

//                                    String colEstado,
//                                    String colFecha,
//                                    String colResultado,
//                                    String colDireccion,
//                                    String colCiudad,
//                                    String colObservacion
                            final InfoTokenEntrega ObjMov = new InfoTokenEntrega(
                                    datamov[5].trim(),
                                    datamov[0].trim(),
                                    datamov[4].trim(),
                                    datamov[1].trim(),
                                    datamov[2].trim(),
                                    datamov[6].trim());

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
                            final ObservableList<InfoTokenEntrega> TablaID = task.getValue();

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
                                                    final List<InfoTokenEntrega> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTokenEntrega> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getGlobal().log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(212);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getGlobal().log(Level.SEVERE, null, ex);

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

    public void mostrarTokenDistEnt(final List<InfoTokenEntrega> data, final DatosDisToken datosditri, final String indiCadoMasReg, final int numPag) {
        this.dataTabla.clear();
        this.dataTabla.addAll(data);
        numpagina.set(numPag);
        setIndicadorRegistros(indiCadoMasReg);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenDistribucionEntrega.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<InfoTokenEntrega> tabla_datos = (TableView<InfoTokenEntrega>) root.lookup("#tabla_datos");

                    final TextField tfID = (TextField) root.lookup("#tfID");
                    final TextField tfNombre = (TextField) root.lookup("#tfNombre");
                    final TextField tfNit = (TextField) root.lookup("#tfNit");
                    final TextField tfSerial = (TextField) root.lookup("#tfSerial");

                    final Button regresar_op = (Button) root.lookup("#regresar_op");
                    final Button obtMasReg = (Button) root.lookup("#obtMasReg");

                    final Cliente datosCliente = Cliente.getCliente();
                    tfNit.setText(datosCliente.getId_cliente());
                    tfID.setText(datosditri.getId_usuario());
                    tfNombre.setText(datosditri.getNombre_usuario());
                    tfSerial.setText(datosditri.getCodigo_serial());

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
                                        final List<InfoTokenEntrega> subList = datos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<InfoTokenEntrega> subList = datos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());

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
                    Logger.getGlobal().log(Level.SEVERE, ex.toString());
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
