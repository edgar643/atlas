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
import com.co.allus.modelo.pagosterceros.DatosPagosTerceros;
import com.co.allus.modelo.pagosterceros.InfoTablaPagosTerceros;
import com.co.allus.modelo.pagosterceros.infoTablaMasFacturas;
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
public class PagosATerceroMasFactController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label codConv;
    @FXML
    private TableColumn<infoTablaMasFacturas, String> colReferencia1;
    @FXML
    private TableColumn<infoTablaMasFacturas, String> colReferencia2;
    @FXML
    private TableColumn<infoTablaMasFacturas, String> colReferencia3;
    @FXML
    private TableColumn<infoTablaMasFacturas, String> colfechaVen;
    @FXML
    private TableColumn<infoTablaMasFacturas, String> colnumFact;
    @FXML
    private TableColumn<infoTablaMasFacturas, String> colvalorPagar;
    @FXML
    private TableColumn<infoTablaMasFacturas, String> colvalorpagarRestante;
    @FXML
    private Button masRegistros;
    @FXML
    private Label nomConv;
    @FXML
    private Button pagarOP;
    @FXML
    private Button regresarOp;
    @FXML
    private Label lblpagoX;
    @FXML
    private TableView<infoTablaMasFacturas> tabla_datos;
    @FXML
    private StackPane panel_tabla;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static ObservableList<infoTablaMasFacturas> registros = FXCollections.observableArrayList();
    private Service<ObservableList<infoTablaMasFacturas>> task = new PagosATerceroMasFactController.getMasFacturasTask();
    private transient final SimpleDateFormat formatoFechaon = new SimpleDateFormat("yyyy/MM/dd ", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaven = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static AtomicInteger numpagina = new AtomicInteger(40);
    public static AtomicInteger totalREg = new AtomicInteger(0);
    public static AtomicInteger acumREg = new AtomicInteger(0);
    public static String indicadorRegistros = "N";
    public static String codigoConv = "";
    public static String tracePaginacion = "";
    public static String indBaseDatos = "";
    public static String refFija = "";
    public static String tipoPersona = "";
    public static AtomicBoolean clearBusqueda = new AtomicBoolean();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert codConv != null : "fx:id=\"codConv\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert colReferencia1 != null : "fx:id=\"colReferencia1\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert colReferencia2 != null : "fx:id=\"colReferencia2\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert colReferencia3 != null : "fx:id=\"colReferencia3\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert colfechaVen != null : "fx:id=\"colfechaVen\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert colnumFact != null : "fx:id=\"colnumFact\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert colvalorPagar != null : "fx:id=\"colvalorPagar\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert colvalorpagarRestante != null : "fx:id=\"colvalorpagarRestante\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert lblpagoX != null : "fx:id=\"lblpagoX\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert masRegistros != null : "fx:id=\"masRegistros\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert nomConv != null : "fx:id=\"nomConv\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert pagarOP != null : "fx:id=\"pagarOP\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert regresarOp != null : "fx:id=\"regresarOp\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'PagosATerceroMasFact.fxml'.";

        tabla_datos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tabla_datos.getSelectionModel().getSelectedItem() != null) {

                    pagarOP.setDisable(false);
                } else {
                    pagarOP.setDisable(true);
                    tabla_datos.getSelectionModel().clearSelection();
                }
            }
        });

        masRegistros.setDisable(true);

        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colnumFact.setCellValueFactory(new PropertyValueFactory("colnumFact"));
        colvalorPagar.setCellValueFactory(new PropertyValueFactory("colvalorPagar"));
        colvalorpagarRestante.setCellValueFactory(new PropertyValueFactory("colvalorpagarRestante"));
        colfechaVen.setCellValueFactory(new PropertyValueFactory("colfechaVen"));
        colReferencia1.setCellValueFactory(new PropertyValueFactory("colReferencia1"));
        colReferencia2.setCellValueFactory(new PropertyValueFactory("colReferencia2"));
        colReferencia3.setCellValueFactory(new PropertyValueFactory("colReferencia3"));

    }

    public static String getTracePaginacion() {
        return tracePaginacion;
    }

    public static void setTracePaginacion(String tracePaginacion) {
        PagosATerceroMasFactController.tracePaginacion = tracePaginacion;
    }

    public String getCodigoConv() {
        return PagosATerceroMasFactController.codigoConv;
    }

    public void setCodigoConv(String codigoConv) {
        PagosATerceroMasFactController.codigoConv = codigoConv;
    }

    public static String getIndBaseDatos() {
        return indBaseDatos;
    }

    public static void setIndBaseDatos(String indBaseDatos) {
        PagosATerceroMasFactController.indBaseDatos = indBaseDatos;
    }

    public static String getRefFija() {
        return refFija;
    }

    public static void setRefFija(String refFija) {
        PagosATerceroMasFactController.refFija = refFija;
    }

    public static String getTipoPersona() {
        return tipoPersona;
    }

    public static void setTipoPersona(String tipoPersona) {
        PagosATerceroMasFactController.tipoPersona = tipoPersona;
    }

    public String getIndicadorRegistros() {
        return PagosATerceroMasFactController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        PagosATerceroMasFactController.indicadorRegistros = indicadorRegistros;
    }

    public void mostrarMasFacturas(final DatosPagosTerceros datosPagosTerceros,
            final String indMasReg,
            final int totalReg,
            final String tracePaginacion,
            final String tipoPersona,
            final String refFija,
            final String convenio,
            final String indBD,
            final int origen) {

        if (origen == 1) {

            setIndicadorRegistros(indMasReg);
            totalREg.set(totalReg);
            acumREg.set(0);
            if ("S".equalsIgnoreCase(indMasReg)) {
                setTracePaginacion(tracePaginacion);
                setCodigoConv(convenio);
                setRefFija(refFija);
                setIndBaseDatos(indBD);
                setTipoPersona(tipoPersona);

            } else {
                setTracePaginacion("");
                setCodigoConv("");
                setRefFija("");
                setIndBaseDatos("");
                setTipoPersona("");
            }

            final List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
            final InfoTablaPagosTerceros data = seleccionPagos.get(datosPagosTerceros.getPagoActual() - 1);

            PagosATerceroMasFactController.registros.clear();
            PagosATerceroMasFactController.registros.addAll(data.getMasFacturas());
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosATerceroMasFact.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button botoncontinuarOp = (Button) root.lookup("#pagarOP");
                    final Button indMasRegistros = (Button) root.lookup("#masRegistros");
                    final Button volverIni = (Button) root.lookup("#regresarOp");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final Label lblCreditoAct = (Label) root.lookup("#lblpagoX");
                    final Label convenio = (Label) root.lookup("#codConv");
                    final Label nombreConv = (Label) root.lookup("#nomConv");
                    final TableView<infoTablaMasFacturas> tablaData = (TableView<infoTablaMasFacturas>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
                    final InfoTablaPagosTerceros data = seleccionPagos.get(datosPagosTerceros.getPagoActual() - 1);

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    convenio.setText(data.colCodConvenioProperty().getValue());
                    nombreConv.setText(data.colNombreConvenioProperty().getValue());

                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);

                    if (AtlasConstantes.PAGO_X_DETALLE == origen) {
                        final String infoCredito = "Pago " + datosPagosTerceros.getPagoActual() + "/" + datosPagosTerceros.getPagoActual();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);
                    } else if (AtlasConstantes.PAGO_X_LOTE == origen) {
                        final String infoCredito = "Pago " + datosPagosTerceros.getPagoActual() + "/" + datosPagosTerceros.getSeleccionPagos().size();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);

                    }

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
                        Logger.getGlobal().log(Level.SEVERE, ex.toString());
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
                                        final List<infoTablaMasFacturas> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoTablaMasFacturas> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(105);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception e) {

                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });

    }

    @FXML
    void masRegistros(ActionEvent event) {
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
                    masRegistros.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<infoTablaMasFacturas> TablaID = task.getValue();

                            if ("S".equals(getIndicadorRegistros())) {
                                masRegistros.setDisable(false);
                            } else {
                                masRegistros.setDisable(true);
                                MarcoPrincipalController.newConsultaPagosT = false;
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
                                                    final List<infoTablaMasFacturas> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoTablaMasFacturas> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(105);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(PagosATerceroMasFactController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            acumREg.addAndGet(-numpagina.get());
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            acumREg.addAndGet(-numpagina.get());
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(PagosATerceroMasFactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public class getMasFacturasTask extends Service<ObservableList<infoTablaMasFacturas>> {

        @Override
        protected Task<ObservableList<infoTablaMasFacturas>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    final StringBuilder tramaMasFacturas = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final AtlasAcceso Atlasacceso = new AtlasAcceso();
                    String Respuesta = new String();
                    final DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();
                    List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
                    final InfoTablaPagosTerceros data = seleccionPagos.get(datosPagosTerceros.getPagoActual() - 1);

                    tramaMasFacturas.append("850,079,");
                    tramaMasFacturas.append(datosCliente.getRefid()); //CONNID
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(AtlasConstantes.COD_PAGOS_MAS_FACTURAS);//COD TRX
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append("S".equalsIgnoreCase(getIndicadorRegistros()) ? getTracePaginacion() : "");//TRACE PAGINACION
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(datosCliente.getId_cliente());//ID
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(getTipoPersona());//INDICADOR(empresas-personas)
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(acumREg.addAndGet(numpagina.get()));//CANTIDAD REGISTROS
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(totalREg.get());//CANTIDAD REGISTROS TOAL                        
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(getCodigoConv());//CONVENIO
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(getRefFija());//REFERENCIA FIJA
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(getIndBaseDatos());//ind base datos
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(datosCliente.getContraseña());//CLAVE HW
                    tramaMasFacturas.append(",");
                    tramaMasFacturas.append(datosCliente.getTokenOauth());//CLAVE HW
                    tramaMasFacturas.append(",~");

//                    System.out.println("TRAMA MAS  FACTURAS :" + tramaMasFacturas);
                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Trama detalle = " + "##" + docs.obtenerHoraActual());

                        // Respuesta="850,079,000,TRANSACCION EXITOSA,N,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~850,079,000,TRANSACCION EXITOSA,N,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMasFacturas.toString());
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion entrega  = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMasFacturas.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    cancel();

                                }
                            });

                        }

                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        try {
                            final String indMAsReg = Respuesta.split(",")[4];//Indicador de más registros
                            final String tracePag = Respuesta.split(",")[5];
                            setIndicadorRegistros(indMAsReg);
                            setTracePaginacion(tracePag);
                            //  tablaDatos.setItems(emptyObservableList);

                            String[] Ltarjetas = Respuesta.split(",")[22].split(";");

                            for (int i = 0; i < Ltarjetas.length; i++) {
                                String[] datos = Ltarjetas[i].split("%");

//    private StringProperty codigoConvenio;
//    private StringProperty tipoRecaudo;
//    private StringProperty cuentaRecaudo;
//    private StringProperty cantidadReferencias;
//    private StringProperty nombreConvenio;
//    private StringProperty claseCuenta;
//    private StringProperty indicadorBD;
//    private StringProperty indicadorContingenciaBD;
//    private StringProperty indicadorPagoParcial;
//    private StringProperty indicadorPagoMayor;
//    private StringProperty indicadorPagoEnCero;
//    private StringProperty textoReferencia1;
//    private StringProperty textoReferencia2;
//    private StringProperty textoReferencia3;
//    private StringProperty indicadorReferenciaFija;
//    private StringProperty numeroFactura;
//    private StringProperty referencia1;
//    private StringProperty referencia2;
//    private StringProperty referencia3;
//    private StringProperty fechaDeVencimiento;
//    private StringProperty valorFactura;
//    private StringProperty valorRestanteDelPago;
                                String valorFactura = "";
                                String valorRestPago = "";
                                String fechavenc = "";

                                try {
                                    fechavenc = formatoFechaon.format(formatoFechaven.parse(datos[4].trim()));
                                } catch (Exception e) {
                                    fechavenc = datos[4].trim().equals("0") ? "" : datos[4].trim();
                                }

                                try {
                                    valorFactura = "$" + formatonum.format(Double.parseDouble(datos[5].substring(0, datos[5].length() - 2))).replace(".", ",") + "." + datos[5].substring(datos[5].length() - 2);
                                } catch (Exception e) {
                                    valorFactura = datos[5].trim();
                                }
                                try {
                                    valorRestPago = "$" + formatonum.format(Double.parseDouble(datos[6].substring(0, datos[6].length() - 2))).replace(".", ",") + "." + datos[6].substring(datos[6].length() - 2);
                                } catch (Exception e) {
                                    try {
                                        valorRestPago = datos[6].trim();
                                    } catch (Exception e2) {
                                        valorRestPago = "";
                                    }

                                }

                                infoTablaMasFacturas facturas = new infoTablaMasFacturas(Respuesta.split(",")[7],
                                        Respuesta.split(",")[8].trim(),
                                        Respuesta.split(",")[9].trim(),
                                        Respuesta.split(",")[10].trim(),
                                        Respuesta.split(",")[11].trim(),
                                        Respuesta.split(",")[12].trim(),
                                        Respuesta.split(",")[13].trim(),
                                        Respuesta.split(",")[14].trim(),
                                        Respuesta.split(",")[15].trim(),
                                        Respuesta.split(",")[16].trim(),
                                        Respuesta.split(",")[17].trim(),
                                        Respuesta.split(",")[18].trim(),
                                        Respuesta.split(",")[19].trim(),
                                        Respuesta.split(",")[20].trim(),
                                        Respuesta.split(",")[21].trim(),
                                        datos[0].trim(),
                                        datos[1].trim(),
                                        datos[2].trim(),
                                        datos[3].trim(),
                                        fechavenc,
                                        valorFactura,
                                        valorRestPago);

                                registros.add(facturas);

                            }
                            data.setMasFacturas(registros);
                            seleccionPagos.set(datosPagosTerceros.getPagoActual() - 1, data);
                            datosPagosTerceros.setSeleccionPagos(seleccionPagos);
                            DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros);

                        } catch (Exception e) {
                            Logger.getGlobal().log(Level.SEVERE, e.toString());
                            final String mensaje = e.toString();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje, "ERROR DE RESPUESTA", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    cancel();
                                    final DatosPagosTerceros datosPagosTerceros1 = DatosPagosTerceros.getDatosPagosTerceros();
                                    datosPagosTerceros1.setPagoActual(datosPagosTerceros1.getPagoActual() - 1);
                                    DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros1);
                                }
                            });
                        }

                    } else {
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                cancel();
                            }
                        });

                    }

                    return registros;
                }
            };
        }
    }

    @FXML
    void pagarOP(ActionEvent event) {
        infoTablaMasFacturas selectedItem = tabla_datos.getSelectionModel().getSelectedItem();

        final DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();
        List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
        final InfoTablaPagosTerceros data = seleccionPagos.get(datosPagosTerceros.getPagoActual() - 1);

        data.setColValorPagar(selectedItem.colvalorpagarRestanteProperty().getValue());
        data.setColmontoRestante(selectedItem.colvalorpagarRestanteProperty().getValue());
        data.setColValorPagOri(selectedItem.colvalorPagarProperty().getValue());
        data.setColNumeroFactura(selectedItem.colnumFactProperty().getValue());
        data.setColNomRef2(selectedItem.colReferencia2Property().getValue());
        data.setColNomRef3(selectedItem.colReferencia3Property().getValue());
        seleccionPagos.set(datosPagosTerceros.getPagoActual() - 1, data);
        datosPagosTerceros.setSeleccionPagos(seleccionPagos);
        DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros);

        PagosATercerosFacturasController controller = new PagosATercerosFacturasController();
        controller.mostrarPagosTerceros(DatosPagosTerceros.getDatosPagosTerceros(), AtlasConstantes.PAGO_X_LOTE);

    }

    @FXML
    void regresarOp(ActionEvent event) {
        final DatosPagosTerceros datosCero = new DatosPagosTerceros();
        DatosPagosTerceros.setDatosPagosTerceros(datosCero);
        MarcoPrincipalController.newConsultaPagosT = true;
        final PagosATercerosInitController controller = new PagosATercerosInitController();
        controller.mostrarDatosPagosTerceros(1);
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
