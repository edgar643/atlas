/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.AtlasAcceso;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagosaterceros.infoArchivoDetalle;
import com.co.allus.modelo.pagosaterceros.infoArchivosRecibidos;
import com.co.allus.modelo.pagosterceros.DataArchivoRecibidosDet;
import com.co.allus.tareas.BusFechaArchRecib;
import com.co.allus.tareas.BusNumRegArcRecib;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
 * @author alexander.lopez.o
 */
public class ConsultaArhRecibController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoArchivosRecibidos, String> ColNomArh;
    @FXML
    private TableColumn<infoArchivosRecibidos, String> ColNumReg;
    @FXML
    private TableColumn<infoArchivosRecibidos, String> ColValTotal;
    @FXML
    private TableColumn<infoArchivosRecibidos, String> ColfechaEnv;
    @FXML
    private Button buscar;
    @FXML
    private Button detalleOP;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private Button indMasReg;
    @FXML
    private TextField numReg;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoArchivosRecibidos> tablaDatos;
    @FXML
    private Button volverOP;
    @FXML
    private ProgressBar progreso;
    @FXML
    private AnchorPane AnchorPane;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static ObservableList<infoArchivosRecibidos> registros = FXCollections.observableArrayList();
    private Service<ObservableList<infoArchivosRecibidos>> task = new ConsultaArhRecibController.GetRegistrosTask();
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient Service<Void> serviceConsArchEnv = continuar_Op();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static AtomicInteger numpagina = new AtomicInteger(40);
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
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert ColNomArh != null : "fx:id=\"ColNomArh\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert ColNumReg != null : "fx:id=\"ColNumReg\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert ColValTotal != null : "fx:id=\"ColValTotal\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert ColfechaEnv != null : "fx:id=\"ColfechaEnv\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert buscar != null : "fx:id=\"buscar\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert detalleOP != null : "fx:id=\"detalleOP\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert numReg != null : "fx:id=\"numReg\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";
        assert volverOP != null : "fx:id=\"volverOP\" was not injected: check your FXML file 'ConsultaArhRecib.fxml'.";

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {

                    detalleOP.setDisable(false);
                } else {
                    detalleOP.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });

        progreso.setVisible(false);
        indMasReg.setDisable(true);

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ColfechaEnv.setCellValueFactory(new PropertyValueFactory<infoArchivosRecibidos, String>("fecha_process"));
        ColNumReg.setCellValueFactory(new PropertyValueFactory<infoArchivosRecibidos, String>("nun_registro"));
        ColValTotal.setCellValueFactory(new PropertyValueFactory<infoArchivosRecibidos, String>("valor_total_arc"));
        ColNomArh.setCellValueFactory(new PropertyValueFactory<infoArchivosRecibidos, String>("nombre_archivo"));


    }

    public static String getTracePaginacion() {
        return tracePaginacion;
    }

    public static void setTracePaginacion(String tracePaginacion) {
        ConsultaArhRecibController.tracePaginacion = tracePaginacion;
    }

    public String getCodigoConv() {
        return ConsultaArhRecibController.codigoConv;
    }

    public void setCodigoConv(String codigoConv) {
        ConsultaArhRecibController.codigoConv = codigoConv;
    }

    public String getFechainiAux() {
        return ConsultaArhRecibController.fechainiAux;
    }

    public void setFechainiAux(String fechainiAux) {
        ConsultaArhRecibController.fechainiAux = fechainiAux;
    }

    public String getFechafinAux() {
        return ConsultaArhRecibController.fechafinAux;
    }

    public void setFechafinAux(String fechafinAux) {
        ConsultaArhRecibController.fechafinAux = fechafinAux;
    }

    public String getIndicadorRegistros() {
        return ConsultaArhRecibController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        ConsultaArhRecibController.indicadorRegistros = indicadorRegistros;
    }

    public void mostrarArchRecib(final ObservableList<infoArchivosRecibidos> registross,
            final String indMasReg,
            final String totalReg,
            final int registrosPag,
            final String fechaini,
            final String fechafin,
            final String codigoconv,
            final String tracePaginacion,
            int origen) {

        if (origen == 1) {
            setCodigoConv(codigoconv);
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


            ConsultaArhRecibController.registros.clear();
            ConsultaArhRecibController.registros.addAll(registross);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaArhRecib.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button botoncontinuarOp = (Button) root.lookup("#detalleOP");
                    final Button indMasRegistros = (Button) root.lookup("#indMasReg");
                    final Button volverIni = (Button) root.lookup("#volverOP");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<infoArchivosRecibidos> tablaData = (TableView<infoArchivosRecibidos>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");



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
                                        final List<infoArchivosRecibidos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoArchivosRecibidos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(95);
                    pagination.setVisible(true);
                    Atlas.vista.show();



                } catch (Exception e) {
                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
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
                            final ObservableList<infoArchivosRecibidos> TablaID = task.getValue();



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
                                                    final List<infoArchivosRecibidos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoArchivosRecibidos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());

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

    public class GetRegistrosTask extends Service<ObservableList<infoArchivosRecibidos>> {

        @Override
        protected Task<ObservableList<infoArchivosRecibidos>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // setNumpagina(getNumpagina() + 1);
                    final Cliente cliente = Cliente.getCliente();
                    String Respuesta = new String();

                    final StringBuilder tramaArchEnv = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    final AtlasAcceso Atlasacceso = new AtlasAcceso();
                    String rnv = "";
                    if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                        rnv = "E";
                    } else {

                        rnv = "P";
                    }

                    //850,068,connid,codigoTransaccion,tracepaginacion,empresa/persona(E o P),identificacion,fechadesde,fechahasta,registrosCanal,registroInicial,codConv,tipoCtaRec,cuentaRec,clavehw,~

                    tramaArchEnv.append("850,070,");
                    tramaArchEnv.append(cliente.getRefid());
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(AtlasConstantes.COD_RECAUDOS_ARCH_REC);
                    tramaArchEnv.append(",");
                    tramaArchEnv.append("S".equalsIgnoreCase(getIndicadorRegistros()) ? getTracePaginacion() : ""); // tracePaginacion
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(rnv); //regla negocio
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(cliente.getId_cliente());
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(getCodigoConv());
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(getFechainiAux());
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(getFechafinAux());
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(numpagina); // registrosCanal
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(acumREg.addAndGet(numpagina.get())); //registrosPaginacion
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(cliente.getContraseña());
                    tramaArchEnv.append(",");
                    tramaArchEnv.append(cliente.getTokenOauth());
                    tramaArchEnv.append(",~");


//                    System.out.println("TRAMA archivos recibidos :" + tramaArchEnv);



                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  archivosENv = " + "##" + docs.obtenerHoraActual());


//                        Respuesta = "850,070,000,TRANSACCION EXITOSA,S,tracepaginacion,52,"
//                                + "1234%1000.00%20180325%abc12324%Nombre del archivo1;"
//                                + "12345%1150.00%20180812%a13e3232%Nombre del archivo2;"
//                                + "125863%135000.00%20180315%c12234f%Nombre del archivo3;"
//                                + "1259632%1650.00%20180905%a3e765gg%Nombre del archivo4;"
//                                + "1258235%16365.00%20180504%cb4rff%Nombre del archivo5;"
//                                + "15269500%18652.00%20180713%a1fgb11%Nombre del archivo6,~";

                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaArchEnv.toString());

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaArchEnv.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

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
                        final String numReg = Respuesta.split(",")[6];
                        final String tracePaginacion = Respuesta.split(",")[5];


                        setIndicadorRegistros(indMasREg);
                        setTracePaginacion(tracePaginacion);
                        totalREg.set(Integer.parseInt(numReg));

                        String[] LMov = Respuesta.split(",")[7].split(";");

                        for (int i = 0; i < LMov.length; i++) {

                            String[] datos = LMov[i].split("%");
                            String fechaapli = "";
                            String valorTotalArchivo = "";

                            try {
                                fechaapli = formatoFechaSalida.format(formatoFecha2.parse(datos[2].trim()));

                            } catch (Exception e) {
                                fechaapli = datos[2].trim();
                            }


                            try {
                                valorTotalArchivo = "$ " + formatonum.format(Double.parseDouble(datos[1].trim().split("\\.")[0])).replace(".", ",") + "." + datos[1].trim().split("\\.")[1];
                            } catch (Exception e) {
                                valorTotalArchivo = "$ " + datos[1];
                            }



                            infoArchivosRecibidos mov = new infoArchivosRecibidos(
                                    datos[0].trim(),
                                    valorTotalArchivo.trim(),
                                    fechaapli.trim(),
                                    datos[3].trim(),
                                    datos[4].trim());

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
    void buscarOP(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoArchivosRecibidos>> TaskTablaId = new BusFechaArchRecib(fechaini, fechafin);

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
                            final ObservableList<infoArchivosRecibidos> TablaID = TaskTablaId.getValue();

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
                                                    final List<infoArchivosRecibidos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoArchivosRecibidos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println(t.getSource().getException().getMessage());
                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
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
    void detalleOP(ActionEvent event) {
        if (serviceConsArchEnv.isRunning()) {
            buscar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceConsArchEnv.progressProperty());
            serviceConsArchEnv.reset();
            serviceConsArchEnv.start();

        } else {
            buscar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceConsArchEnv.progressProperty());
            serviceConsArchEnv.reset();
            serviceConsArchEnv.start();
        }

        serviceConsArchEnv.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Detalle archivos enviados " + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);


            }
        });



        serviceConsArchEnv.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Detalle archivos enviados" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceConsArchEnv.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                Logger.getGlobal().log(Level.SEVERE, t.getSource().getException().toString());
//                System.out.println("fallo");
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                buscar.setDisable(false);
            }
        });
    }

    public Service<Void> continuar_Op() {
        serviceConsArchEnv = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaArchEnv = new StringBuilder();
                        final StringBuilder tramadetalleAch = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final ObservableList<infoArchivoDetalle> registrosDet = FXCollections.observableArrayList();

                        final AtlasAcceso Atlasacceso = new AtlasAcceso();
                        String rnv = "";
                        if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                            rnv = "E";
                        } else {

                            rnv = "P";
                        }
                        final infoArchivosRecibidos selectedItem = tablaDatos.getSelectionModel().getSelectedItem();

                        //850,071,connid,codigotransaccion,empresa/persona(E o P),identificacion,codigoconv,fechadesde,fechahasta,regIni,totalRegCanal,clavehardware,~

                        tramaArchEnv.append("850,071,");
                        tramaArchEnv.append(cliente.getRefid());
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(AtlasConstantes.COD_RECUADOS_ESTADO_ARCH);
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(rnv); //regla negocio
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(cliente.getId_cliente());
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(getCodigoConv());
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(selectedItem.getNum_rastreo());
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(cliente.getContraseña());
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(cliente.getTokenOauth());
                        tramaArchEnv.append(",~");


//                        System.out.println("TRAMA estado archivos :" + tramaArchEnv);



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  archivosEstado = " + "##" + docs.obtenerHoraActual());


                            // Respuesta = "850,071,000,TRANSACCION EXITOSA,Exitoso,15000000.00,30082018,~";



                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaArchEnv.toString());

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES= " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaArchEnv.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        buscar.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });
                            }
                        }



                        if ("000".equals(Respuesta.split(",")[2])) {



                            final String estadoArchivo = Respuesta.split(",")[4];
                            final String valortotal = Respuesta.split(",")[5];
                            final String fechaaplicacion = Respuesta.split(",")[6];

                            String fechaapli = "";
                            String valorTotalArchivo = "";

                            try {
                                fechaapli = formatoFechaSalida.format(formatoFecha.parse(fechaaplicacion.trim()));

                            } catch (Exception e) {
                                fechaapli = fechaaplicacion.trim();
                            }


                            try {
                                valorTotalArchivo = "$ " + formatonum.format(Double.parseDouble(valortotal.trim().split("\\.")[0])).replace(".", ",") + "." + valortotal.trim().split("\\.")[1];
                            } catch (Exception e) {
                                valorTotalArchivo = "$ " + valortotal;
                            }

                            final String fechaapliaux = fechaapli;
                            final String valorTotalArchivoaux = valorTotalArchivo;

                            /**
                             * consulta detallearchivos
                             */
                            //850,072,connid,codigoTransaccion,tracepaginacion,empresa/persona (E ó P),identificacion,totalreg,numregini,codigoconv,numrastreo,refFija,valorTotalArhicvo,tipoDocPagador,numDocPagador,estadoArchivo,claveHardware,~
                            tramadetalleAch.append("850,072,");
                            tramadetalleAch.append(cliente.getRefid());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(AtlasConstantes.COD_RECAUDOS_DETALLE_ARCH);
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(""); //tracePagincacion
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(rnv); //regla negocio
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(cliente.getId_cliente());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append("40"); // registros cannal
                            tramadetalleAch.append(",");
                            tramadetalleAch.append("0"); //registros ini
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(getCodigoConv());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(selectedItem.getNum_rastreo());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(""); //referencia fija?
                            tramadetalleAch.append(",");
                            tramadetalleAch.append("0"); // valor total
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(" "); //doc pagador
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(" "); //tipoDocPagador                      
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(" "); //registros ini                      
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(cliente.getContraseña());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(cliente.getTokenOauth());
                            tramadetalleAch.append(",~");


//                            System.out.println("TRAMA detalle archivos :" + tramadetalleAch);



                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  archivosEstado = " + "##" + docs.obtenerHoraActual());





                                // Respuesta = "850,072,000,Success                                                                         ,S,000110,152509121382,Success        ,0000      ,Success                                                                         ,S,63136          ,F,BAN,ArchivoEnviadoLuisa_24_2                                                                            ,Procesado      ,40678909004         ,228888909      ,CRAZY -T?WN &(COMPA??A)#1.1.                                                    ,BDInterna      ,0000000001100000,000110,10/10/2018,10/10/2018,G  ,110   ,040,35309292    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000020               %REF1MiguelAngel 00000000000020     %REF2MiguelAngel 00000000000020     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309293    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000021               %REF1MiguelAngel 00000000000021     %REF2MiguelAngel 00000000000021     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309294    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000022               %REF1MiguelAngel 00000000000022     %REF2MiguelAngel 00000000000022     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309295    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000023               %REF1MiguelAngel 00000000000023     %REF2MiguelAngel 00000000000023     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309296    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000024               %REF1MiguelAngel 00000000000024     %REF2MiguelAngel 00000000000024     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309297    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000025               %REF1MiguelAngel 00000000000025     %REF2MiguelAngel 00000000000025     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309298    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000026               %REF1MiguelAngel 00000000000026     %REF2MiguelAngel 00000000000026     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309299    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000027               %REF1MiguelAngel 00000000000027     %REF2MiguelAngel 00000000000027     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309300    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000028               %REF1MiguelAngel 00000000000028     %REF2MiguelAngel 00000000000028     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309301    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000029               %REF1MiguelAngel 00000000000029     %REF2MiguelAngel 00000000000029     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309302    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000030               %REF1MiguelAngel 00000000000030     %REF2MiguelAngel 00000000000030     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309303    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000031               %REF1MiguelAngel 00000000000031     %REF2MiguelAngel 00000000000031     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309304    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000032               %REF1MiguelAngel 00000000000032     %REF2MiguelAngel 00000000000032     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309305    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000033               %REF1MiguelAngel 00000000000033     %REF2MiguelAngel 00000000000033     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309306    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000034               %REF1MiguelAngel 00000000000034     %REF2MiguelAngel 00000000000034     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309307    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000035               %REF1MiguelAngel 00000000000035     %REF2MiguelAngel 00000000000035     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309308    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000036               %REF1MiguelAngel 00000000000036     %REF2MiguelAngel 00000000000036     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309309    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000037               %REF1MiguelAngel 00000000000037     %REF2MiguelAngel 00000000000037     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309310    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000038               %REF1MiguelAngel 00000000000038     %REF2MiguelAngel 00000000000038     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309311    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000039               %REF1MiguelAngel 00000000000039     %REF2MiguelAngel 00000000000039     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309312    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000040               %REF1MiguelAngel 00000000000040     %REF2MiguelAngel 00000000000040     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309313    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000041               %REF1MiguelAngel 00000000000041     %REF2MiguelAngel 00000000000041     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309314    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000042               %REF1MiguelAngel 00000000000042     %REF2MiguelAngel 00000000000042     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309315    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000043               %REF1MiguelAngel 00000000000043     %REF2MiguelAngel 00000000000043     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309316    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000044               %REF1MiguelAngel 00000000000044     %REF2MiguelAngel 00000000000044     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309317    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000045               %REF1MiguelAngel 00000000000045     %REF2MiguelAngel 00000000000045     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309318    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000046               %REF1MiguelAngel 00000000000046     %REF2MiguelAngel 00000000000046     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309319    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000047               %REF1MiguelAngel 00000000000047     %REF2MiguelAngel 00000000000047     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309320    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000048               %REF1MiguelAngel 00000000000048     %REF2MiguelAngel 00000000000048     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309321    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000049               %REF1MiguelAngel 00000000000049     %REF2MiguelAngel 00000000000049     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309322    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000050               %REF1MiguelAngel 00000000000050     %REF2MiguelAngel 00000000000050     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309323    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000051               %REF1MiguelAngel 00000000000051     %REF2MiguelAngel 00000000000051     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309324    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000052               %REF1MiguelAngel 00000000000052     %REF2MiguelAngel 00000000000052     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309325    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000053               %REF1MiguelAngel 00000000000053     %REF2MiguelAngel 00000000000053     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309326    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000054               %REF1MiguelAngel 00000000000054     %REF2MiguelAngel 00000000000054     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309327    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000055               %REF1MiguelAngel 00000000000055     %REF2MiguelAngel 00000000000055     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309328    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000056               %REF1MiguelAngel 00000000000056     %REF2MiguelAngel 00000000000056     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309329    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000057               %REF1MiguelAngel 00000000000057     %REF2MiguelAngel 00000000000057     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309330    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000058               %REF1MiguelAngel 00000000000058     %REF2MiguelAngel 00000000000058     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309331    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000059               %REF1MiguelAngel 00000000000059     %REF2MiguelAngel 00000000000059     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000,~";
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramadetalleAch.toString());

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex) {

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES= " + "##" + docs.obtenerHoraActual());
                                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaArchEnv.toString());
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                                } catch (Exception ex1) {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {

                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            buscar.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);

                                        }
                                    });
                                }
                            }

                            if ("000".equals(Respuesta.split(",")[2])) {



                                final String indMasReg = Respuesta.split(",")[4];
                                final String registrosfinalcle = Respuesta.split(",")[5];
                                final String tracePaginacion = Respuesta.split(",")[6];
                                final String codigoconv = Respuesta.split(",")[11];
                                final String tipoArch = Respuesta.split(",")[12];
                                final String formatoArch = Respuesta.split(",")[13];
                                final String nombreArch = Respuesta.split(",")[14];
                                final String estadoArch = Respuesta.split(",")[15];
                                final String numeroctaabono = Respuesta.split(",")[16];
                                final String nitEntRecaudo = Respuesta.split(",")[17];
                                final String nombEntRecaudo = Respuesta.split(",")[18];
                                final String tipoRecaudador = Respuesta.split(",")[19];
                                String valorTotaltrx = Respuesta.split(",")[20];
                                final String totalRegArch = Respuesta.split(",")[21];
                                final String fechaAplicacion = Respuesta.split(",")[22];
                                final String fechaGeneracion = Respuesta.split(",")[23];
                                final String secuencia = Respuesta.split(",")[24];
                                final String todoslosReg = Respuesta.split(",")[25];
                                final String listaDetallada = Respuesta.split(",")[26];

                                String valorTotalA = "";
                                String valorTotalf = "";
                                try {
                                    valorTotalA = "$ " + formatonum.format(Double.parseDouble(valorTotaltrx.trim().substring(0, valorTotaltrx.length() - 2))).replace(".", ",") + "." + valorTotaltrx.trim().substring(valorTotaltrx.length() - 2, valorTotaltrx.length());
                                } catch (Exception e) {
                                    valorTotalA = "$ " + valorTotaltrx;
                                }

                                try {
                                    valorTotalf = "$ " + formatonum.format(Double.parseDouble(valortotal.trim().substring(0, valortotal.length() - 2))).replace(".", ",") + "." + valortotal.trim().substring(valortotal.length() - 2, valortotal.length());
                                } catch (Exception e) {
                                    valorTotalf = "$ " + valortotal;
                                }

                                DataArchivoRecibidosDet dataarch = DataArchivoRecibidosDet.getDataarch();
                                dataarch.setIndMasReg(indMasReg);
                                dataarch.setRegistrosfinalcle(registrosfinalcle);
                                dataarch.setTracePaginacion(tracePaginacion);
                                dataarch.setCodigoconv(codigoconv);
                                dataarch.setTipoArch(tipoArch);
                                dataarch.setFormatoArch(formatoArch);
                                dataarch.setNombreArch(nombreArch);
                                dataarch.setEstadoArch(estadoArch);
                                dataarch.setNumeroctaabono(numeroctaabono);
                                dataarch.setNitEntRecaudo(nitEntRecaudo);
                                dataarch.setNombEntRecaudo(nombEntRecaudo);
                                dataarch.setTipoRecaudador(tipoRecaudador);
                                dataarch.setValorTotaltrx(valorTotalA);
                                dataarch.setTotalRegArch(totalRegArch);
                                dataarch.setFechaAplicacion(fechaAplicacion);
                                dataarch.setFechaGeneracion(fechaGeneracion);
                                dataarch.setSecuencia(secuencia);
                                dataarch.setTodoslosReg(todoslosReg);
                                dataarch.setListaDetallada(listaDetallada);
                                dataarch.setEstadoarchivo(estadoArchivo);
                                dataarch.setFechaAplic_estado(fechaapliaux);
                                dataarch.setValorTotalArchivo(valorTotalf);

                                DataArchivoRecibidosDet.setDataarch(dataarch);

                                String[] LMov = Respuesta.split(",")[27].split(";");

                                for (int i = 0; i < LMov.length; i++) {


                                    String[] datos = LMov[i].split("%");


                                    String valordetransaccion = "";
                                    String valordebitado = "";

                                    try {
                                        valordetransaccion = "$ " + formatonum.format(Double.parseDouble(datos[9].trim().substring(0, datos[9].length() - 2))).replace(".", ",") + "." + datos[9].trim().substring(datos[9].length() - 2, datos[9].length());
                                    } catch (Exception e) {
                                        valordetransaccion = "$ " + datos[9];
                                    }

                                    try {
                                        valordebitado = "$ " + formatonum.format(Double.parseDouble(datos[12].trim().substring(0, datos[12].length() - 2))).replace(".", ",") + "." + datos[12].trim().substring(datos[12].length() - 2, datos[12].length());
                                    } catch (Exception e) {
                                        valordebitado = "$ " + datos[12];
                                    }


                                    infoArchivoDetalle mov = new infoArchivoDetalle(
                                            datos[0].trim(),
                                            datos[1].trim(),
                                            datos[2].trim(),
                                            datos[3].trim(),
                                            datos[4].trim(),
                                            datos[5].trim(),
                                            datos[6].trim(),
                                            datos[7].trim(),
                                            datos[8].trim(),
                                            valordetransaccion,
                                            datos[10].trim(),
                                            datos[11].trim(),
                                            valordebitado);

                                    registrosDet.add(mov);

                                }


                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

                                        final ConsultaArhRecibDetController controller = new ConsultaArhRecibDetController();
                                        controller.mostrarArchivosDetalle(registrosDet,
                                                selectedItem,
                                                DataArchivoRecibidosDet.getDataarch(),
                                                1);


                                    }
                                });

                            } else {

                                final String coderror = Respuesta.split(",")[2];
                                final String mensaje = Respuesta.split(",")[3].trim();

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        buscar.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }


                        } else {
                            /**
                             * consulta estado falla
                             */
                            final String estadoArchivo = "";
                            final String valortotal = "";
                            final String fechaaplicacion = "";

                            String fechaapli = "";
                            String valorTotalArchivo = "";

                            try {
                                fechaapli = formatoFechaSalida.format(formatoFecha.parse(fechaaplicacion.trim()));

                            } catch (Exception e) {
                                fechaapli = fechaaplicacion.trim();
                            }


                            try {
                                valorTotalArchivo = "$ " + formatonum.format(Double.parseDouble(valortotal.trim().split("\\.")[0])).replace(".", ",") + "." + valortotal.trim().split("\\.")[1];
                            } catch (Exception e) {
                                valorTotalArchivo = "$ " + valortotal;
                            }

                            final String fechaapliaux = fechaapli;
                            final String valorTotalArchivoaux = valorTotalArchivo;

                            /**
                             * consulta detallearchivos
                             */
                            //850,072,connid,codigoTransaccion,tracepaginacion,empresa/persona (E ó P),identificacion,totalreg,numregini,codigoconv,numrastreo,refFija,valorTotalArhicvo,tipoDocPagador,numDocPagador,estadoArchivo,claveHardware,~
                            tramadetalleAch.append("850,072,");
                            tramadetalleAch.append(cliente.getRefid());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(AtlasConstantes.COD_RECAUDOS_DETALLE_ARCH);
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(""); //tracePagincacion
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(rnv); //regla negocio
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(cliente.getId_cliente());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append("40"); // registros cannal
                            tramadetalleAch.append(",");
                            tramadetalleAch.append("0"); //registros ini
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(getCodigoConv());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(selectedItem.getNum_rastreo().replace("$ ", "").replace(",", "").trim());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(" "); //referencia fija?
                            tramadetalleAch.append(",");
                            tramadetalleAch.append("0"); // valor total
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(" "); //doc pagador
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(" "); //tipoDocPagador                      
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(" "); //registros ini                      
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(cliente.getContraseña());
                            tramadetalleAch.append(",");
                            tramadetalleAch.append(cliente.getTokenOauth());
                            tramadetalleAch.append(",~");


//                            System.out.println("TRAMA detalle archivos :" + tramadetalleAch);



                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  archivosEstado = " + "##" + docs.obtenerHoraActual());


                                // Respuesta = "850,071,000,TRANSACCION EXITOSA,Exitoso,15000000.00,30082018,~";

//                                Respuesta = "850,072,000,"
//                                        + "TRANSACCION EXITOSA,"
//                                        + "S,"
//                                        + "150,"
//                                        + "Trace de paginación,"
//                                        + "codigoConvenio,"
//                                        + "tipoDeArchivo,"
//                                        + "formatoDelArchivo,"
//                                        + "nombreDelArchivo,"
//                                        + "estadoDelArchivo,"
//                                        + "numeroCuentaAbono,"
//                                        + "nitEntidadRecaudadora,"
//                                        + "nombreEntidadRecaudadora,"
//                                        + "tipoRecaudador,"
//                                        + "valorTotalTransaccion,"
//                                        + "totalRegistrosArchivo,"
//                                        + "fechaDeAplicacion,"
//                                        + "fechaDeGeneracion,"
//                                        + "secuencia,"
//                                        + "todosLosRegistros,"
//                                        + "listaDetalla,"
//                                        + "%idRegistro%codigoDeRespuesta%descripcion%nitEntidadPagadora%nombreEntidadPagadora%referencia1%referencia2%numeroCuentaPagador%descripcionCuentaPagador%valorDeTransaccion%nombreDelBanco%fechaDeProcesamiento%valorDebitado,~";


                                // Respuesta = "850,072,000,Success                                                                         ,S,000110,145859715010,Success        ,0000      ,Success                                                                         ,S,63136          ,F,BAN,ArchivoEnviadoLuisa_24_2                                                                            ,Procesado      ,40678909004         ,228888909      ,CRAZY -T?WN &(COMPA??A)#1.1.                                                    ,BDInterna      ,0000000001100000,000110,10/10/2018,10/10/2018,G  ,110   ,040,35309292    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000020               %REF1MiguelAngel 00000000000020     %REF2MiguelAngel 00000000000020     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309293    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000021               %REF1MiguelAngel 00000000000021     %REF2MiguelAngel 00000000000021     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309294    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000022               %REF1MiguelAngel 00000000000022     %REF2MiguelAngel 00000000000022     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309295    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000023               %REF1MiguelAngel 00000000000023     %REF2MiguelAngel 00000000000023     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309296    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000024               %REF1MiguelAngel 00000000000024     %REF2MiguelAngel 00000000000024     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309297    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000025               %REF1MiguelAngel 00000000000025     %REF2MiguelAngel 00000000000025     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309298    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000026               %REF1MiguelAngel 00000000000026     %REF2MiguelAngel 00000000000026     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309299    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000027               %REF1MiguelAngel 00000000000027     %REF2MiguelAngel 00000000000027     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309300    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000028               %REF1MiguelAngel 00000000000028     %REF2MiguelAngel 00000000000028     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309301    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000029               %REF1MiguelAngel 00000000000029     %REF2MiguelAngel 00000000000029     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309302    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000030               %REF1MiguelAngel 00000000000030     %REF2MiguelAngel 00000000000030     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309303    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000031               %REF1MiguelAngel 00000000000031     %REF2MiguelAngel 00000000000031     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309304    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000032               %REF1MiguelAngel 00000000000032     %REF2MiguelAngel 00000000000032     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309305    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000033               %REF1MiguelAngel 00000000000033     %REF2MiguelAngel 00000000000033     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309306    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000034               %REF1MiguelAngel 00000000000034     %REF2MiguelAngel 00000000000034     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309307    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000035               %REF1MiguelAngel 00000000000035     %REF2MiguelAngel 00000000000035     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309308    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000036               %REF1MiguelAngel 00000000000036     %REF2MiguelAngel 00000000000036     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309309    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000037               %REF1MiguelAngel 00000000000037     %REF2MiguelAngel 00000000000037     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309310    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000038               %REF1MiguelAngel 00000000000038     %REF2MiguelAngel 00000000000038     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309311    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000039               %REF1MiguelAngel 00000000000039     %REF2MiguelAngel 00000000000039     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309312    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000040               %REF1MiguelAngel 00000000000040     %REF2MiguelAngel 00000000000040     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309313    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000041               %REF1MiguelAngel 00000000000041     %REF2MiguelAngel 00000000000041     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309314    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000042               %REF1MiguelAngel 00000000000042     %REF2MiguelAngel 00000000000042     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309315    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000043               %REF1MiguelAngel 00000000000043     %REF2MiguelAngel 00000000000043     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309316    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000044               %REF1MiguelAngel 00000000000044     %REF2MiguelAngel 00000000000044     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309317    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000045               %REF1MiguelAngel 00000000000045     %REF2MiguelAngel 00000000000045     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309318    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000046               %REF1MiguelAngel 00000000000046     %REF2MiguelAngel 00000000000046     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309319    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000047               %REF1MiguelAngel 00000000000047     %REF2MiguelAngel 00000000000047     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309320    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000048               %REF1MiguelAngel 00000000000048     %REF2MiguelAngel 00000000000048     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309321    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000049               %REF1MiguelAngel 00000000000049     %REF2MiguelAngel 00000000000049     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309322    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000050               %REF1MiguelAngel 00000000000050     %REF2MiguelAngel 00000000000050     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309323    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000051               %REF1MiguelAngel 00000000000051     %REF2MiguelAngel 00000000000051     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309324    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000052               %REF1MiguelAngel 00000000000052     %REF2MiguelAngel 00000000000052     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309325    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000053               %REF1MiguelAngel 00000000000053     %REF2MiguelAngel 00000000000053     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309326    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000054               %REF1MiguelAngel 00000000000054     %REF2MiguelAngel 00000000000054     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309327    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000055               %REF1MiguelAngel 00000000000055     %REF2MiguelAngel 00000000000055     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309328    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000056               %REF1MiguelAngel 00000000000056     %REF2MiguelAngel 00000000000056     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309329    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000057               %REF1MiguelAngel 00000000000057     %REF2MiguelAngel 00000000000057     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309330    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000058               %REF1MiguelAngel 00000000000058     %REF2MiguelAngel 00000000000058     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000;35309331    %OK0%NO PAGADO                          %1037987654                         %Miguel Angel 0000059               %REF1MiguelAngel 00000000000059     %REF2MiguelAngel 00000000000059     %4067654011          %00                                 %10000%                                                                                %10/10/2018 00:00:00%10000,~";
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramadetalleAch.toString());

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex) {

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES= " + "##" + docs.obtenerHoraActual());
                                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaArchEnv.toString());
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                                } catch (Exception ex1) {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {

                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            buscar.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);

                                        }
                                    });
                                }
                            }

                            if ("000".equals(Respuesta.split(",")[2])) {

                                final String indMasReg = Respuesta.split(",")[4];
                                final String registrosfinalcle = Respuesta.split(",")[5];
                                final String tracePaginacion = Respuesta.split(",")[6];
                                final String codigoconv = Respuesta.split(",")[11];
                                final String tipoArch = Respuesta.split(",")[12];
                                final String formatoArch = Respuesta.split(",")[13];
                                final String nombreArch = Respuesta.split(",")[14];
                                final String estadoArch = Respuesta.split(",")[15];
                                final String numeroctaabono = Respuesta.split(",")[16];
                                final String nitEntRecaudo = Respuesta.split(",")[17];
                                final String nombEntRecaudo = Respuesta.split(",")[18];
                                final String tipoRecaudador = Respuesta.split(",")[19];
                                String valorTotaltrx = Respuesta.split(",")[20];
                                final String totalRegArch = Respuesta.split(",")[21];
                                final String fechaAplicacion = Respuesta.split(",")[22];
                                final String fechaGeneracion = Respuesta.split(",")[23];
                                final String secuencia = Respuesta.split(",")[24];
                                final String todoslosReg = Respuesta.split(",")[25];
                                final String listaDetallada = Respuesta.split(",")[26];

                                String valorTotalA = "";
                                String valorTotalf = "";
                                try {
                                    valorTotalA = "$ " + formatonum.format(Double.parseDouble(valorTotaltrx.trim().substring(0, valorTotaltrx.length() - 2))).replace(".", ",") + "." + valorTotaltrx.trim().substring(valorTotaltrx.length() - 2, valorTotaltrx.length());
                                } catch (Exception e) {
                                    valorTotalA = "$ " + valorTotaltrx;
                                }

                                try {
                                    valorTotalf = "$ " + formatonum.format(Double.parseDouble(valortotal.trim().substring(0, valortotal.length() - 2))).replace(".", ",") + "." + valortotal.trim().substring(valortotal.length() - 2, valortotal.length());
                                } catch (Exception e) {
                                    valorTotalf = "$ " + valortotal;
                                }

                                DataArchivoRecibidosDet dataarch = DataArchivoRecibidosDet.getDataarch();
                                dataarch.setIndMasReg(indMasReg);
                                dataarch.setRegistrosfinalcle(registrosfinalcle);
                                dataarch.setTracePaginacion(tracePaginacion);
                                dataarch.setCodigoconv(codigoconv);
                                dataarch.setTipoArch(tipoArch);
                                dataarch.setFormatoArch(formatoArch);
                                dataarch.setNombreArch(nombreArch);
                                dataarch.setEstadoArch(estadoArch);
                                dataarch.setNumeroctaabono(numeroctaabono);
                                dataarch.setNitEntRecaudo(nitEntRecaudo);
                                dataarch.setNombEntRecaudo(nombEntRecaudo);
                                dataarch.setTipoRecaudador(tipoRecaudador);
                                dataarch.setValorTotaltrx(valorTotalA);
                                dataarch.setTotalRegArch(totalRegArch);
                                dataarch.setFechaAplicacion(fechaAplicacion);
                                dataarch.setFechaGeneracion(fechaGeneracion);
                                dataarch.setSecuencia(secuencia);
                                dataarch.setTodoslosReg(todoslosReg);
                                dataarch.setListaDetallada(listaDetallada);
                                dataarch.setEstadoarchivo(estadoArchivo);
                                dataarch.setFechaAplic_estado(fechaapliaux);
                                dataarch.setValorTotalArchivo(valorTotalf);

                                DataArchivoRecibidosDet.setDataarch(dataarch);

                                String[] LMov = Respuesta.split(",")[27].split(";");

                                for (int i = 0; i < LMov.length; i++) {


                                    String[] datos = LMov[i].split("%");

//                                       private SimpleStringProperty idregistro;
//    private SimpleStringProperty codigorespuesta;
//    private SimpleStringProperty descripcion;
//    private SimpleStringProperty nitentidadpag;
//    private SimpleStringProperty nomentidadpag;
//    private SimpleStringProperty ref1;
//    private SimpleStringProperty ref2;
//    private SimpleStringProperty numctapagador;
//    private SimpleStringProperty descripcionctaPagador;
//    private SimpleStringProperty valordetransaccion;
//    private SimpleStringProperty nombanco;
//    private SimpleStringProperty fechaprocesacion;
//    private SimpleStringProperty valordebitado;

                                    String valordetransaccion = "";
                                    String valordebitado = "";

                                    try {
                                        valordetransaccion = "$ " + formatonum.format(Double.parseDouble(datos[9].trim().substring(0, datos[9].length() - 2))).replace(".", ",") + "." + datos[9].trim().substring(datos[9].length() - 2, datos[9].length());
                                    } catch (Exception e) {
                                        valordetransaccion = "$ " + datos[9];
                                    }

                                    try {
                                        valordebitado = "$ " + formatonum.format(Double.parseDouble(datos[12].trim().substring(0, datos[12].length() - 2))).replace(".", ",") + "." + datos[12].trim().substring(datos[12].length() - 2, datos[12].length());
                                    } catch (Exception e) {
                                        valordebitado = "$ " + datos[12];
                                    }


                                    infoArchivoDetalle mov = new infoArchivoDetalle(
                                            datos[0].trim(),
                                            datos[1].trim(),
                                            datos[2].trim(),
                                            datos[3].trim(),
                                            datos[4].trim(),
                                            datos[5].trim(),
                                            datos[6].trim(),
                                            datos[7].trim(),
                                            datos[8].trim(),
                                            valordetransaccion,
                                            datos[10].trim(),
                                            datos[11].trim(),
                                            valordebitado);

                                    registrosDet.add(mov);

                                }


                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

                                        final ConsultaArhRecibDetController controller = new ConsultaArhRecibDetController();
                                        controller.mostrarArchivosDetalle(registrosDet,
                                                selectedItem,
                                                DataArchivoRecibidosDet.getDataarch(),
                                                1);


                                    }
                                });

                            } else {

                                final String coderror = Respuesta.split(",")[2];
                                final String mensaje = Respuesta.split(",")[3].trim();

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        buscar.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }


                        }



                        return null;
                    }
                };
            }
        };



        return serviceConsArchEnv;

    }

    @FXML
    void numRegkPress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(numReg, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            numReg.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void numRegktyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    BusqudaNumRegistro(numReg.getText() + event.getCharacter());


                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (numReg.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {

                            BusqudaNumRegistro("");


                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {

                            BusqudaNumRegistro(numReg.getText());


                        }
                    }


                }

            }

        }
    }

    /**
     * BUSCAR POR ID
     */
    private void BusqudaNumRegistro(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoArchivosRecibidos>> TaskTablaId = new BusNumRegArcRecib(id);

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
                            final ObservableList<infoArchivosRecibidos> TablaID = TaskTablaId.getValue();

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
                                                    final List<infoArchivosRecibidos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoArchivosRecibidos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println(t.getSource().getException().getMessage());
                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
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
    void volverOP(ActionEvent event) {
        ConsultaArchRecinitController controller = new ConsultaArchRecinitController();
        controller.mostrarConsultaArchRecibidosInit(true);
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

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
