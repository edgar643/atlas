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
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagosaterceros.infoMovConv;
import com.co.allus.tareas.BusdIDnitRecaudador;
import com.co.allus.tareas.BusqFechaMovConv;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
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
public class ConsultaMovConvDetController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoMovConv, String> ColEstado;
    @FXML
    private TableColumn<infoMovConv, String> ColNumCtaRec;
    @FXML
    private TableColumn<infoMovConv, String> Colnombre;
    @FXML
    private TableColumn<infoMovConv, String> Colfechaapl;
    @FXML
    private TableColumn<infoMovConv, String> Colnitrec;
    @FXML
    private Button buscar;
    @FXML
    private TextField codConv;
    @FXML
    private Button detalleOP;
    @FXML
    private Button indMasReg;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoMovConv> tablaDatos;
    @FXML
    private Button volverOP;
    @FXML
    private Button limpiar;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static ObservableList<infoMovConv> registros = FXCollections.observableArrayList();
    private Service<ObservableList<infoMovConv>> task = new ConsultaMovConvDetController.GetRegistrosTask();
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPagoO = new SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static AtomicInteger numpagina = new AtomicInteger(40);
    public static AtomicInteger totalREg = new AtomicInteger(0);
    public static AtomicInteger acumREg = new AtomicInteger(0);
    public static String indicadorRegistros = "N";
    public static String fechainiAux = "";
    public static String fechafinAux = "";
    public static String codigoConv = "";
    public static String tipoctarec = "";
    public static String numctarec = "";
    public static String tracePaginacion = "";
    public static AtomicBoolean clearBusqueda = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert ColEstado != null : "fx:id=\"ColEstado\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert ColNumCtaRec != null : "fx:id=\"ColNumCtaRec\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert Colfechaapl != null : "fx:id=\"Colfechaapl\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert Colnitrec != null : "fx:id=\"Colnitrec\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert Colnombre != null : "fx:id=\"Colnombre\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert buscar != null : "fx:id=\"buscar\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert codConv != null : "fx:id=\"codConv\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert detalleOP != null : "fx:id=\"detalleOP\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert volverOP != null : "fx:id=\"volverOP\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'ConsultaMovConvDet.fxml'.";

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

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Colnombre.setCellValueFactory(new PropertyValueFactory<infoMovConv, String>("codigoconv"));
        ColEstado.setCellValueFactory(new PropertyValueFactory<infoMovConv, String>("valororiginal"));
        ColNumCtaRec.setCellValueFactory(new PropertyValueFactory<infoMovConv, String>("numctarec"));
        Colnitrec.setCellValueFactory(new PropertyValueFactory<infoMovConv, String>("nitpagador"));
        Colfechaapl.setCellValueFactory(new PropertyValueFactory<infoMovConv, String>("fechaaplic"));

        indMasReg.setDisable(true);
        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);
        fechaini.setDateFormat(formatoFecha);
        fechafin.setDateFormat(formatoFecha);
        buscar.setDisable(true);

    }

    public static String getTipoctarec() {
        return tipoctarec;
    }

    public static void setTipoctarec(String tipoctarec) {
        ConsultaMovConvDetController.tipoctarec = tipoctarec;
    }

    public static String getNumctarec() {
        return numctarec;
    }

    public static void setNumctarec(String numctarec) {
        ConsultaMovConvDetController.numctarec = numctarec;
    }

    public static String getTracePaginacion() {
        return tracePaginacion;
    }

    public static void setTracePaginacion(String tracePaginacion) {
        ConsultaMovConvDetController.tracePaginacion = tracePaginacion;
    }

    public String getCodigoConv() {
        return codigoConv;
    }

    public void setCodigoConv(String codigoConv) {
        ConsultaMovConvDetController.codigoConv = codigoConv;
    }

    public String getFechainiAux() {
        return ConsultaMovConvDetController.fechainiAux;
    }

    public void setFechainiAux(String fechainiAux) {
        ConsultaMovConvDetController.fechainiAux = fechainiAux;
    }

    public String getFechafinAux() {
        return ConsultaMovConvDetController.fechafinAux;
    }

    public void setFechafinAux(String fechafinAux) {
        ConsultaMovConvDetController.fechafinAux = fechafinAux;
    }

    public String getIndicadorRegistros() {
        return ConsultaMovConvDetController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        ConsultaMovConvDetController.indicadorRegistros = indicadorRegistros;
    }

    public void mostrarMovConv(final ObservableList<infoMovConv> registross,
            final String indMasReg,
            final String totalReg,
            final int registrosPag,
            final String fechaini,
            final String fechafin,
            final String codigoconv,
            final String tipoctaRec,
            final String numctarec,
            final String tracePaginacion,
            int origen) {

        if (origen == 1) {
            setCodigoConv(codigoconv);
            setTipoctarec(tipoctaRec);
            setNumctarec(numctarec);
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

            ConsultaMovConvDetController.registros.clear();
            ConsultaMovConvDetController.registros.addAll(registross);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaMovConvDet.fxml");
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
                    final TableView<infoMovConv> tablaData = (TableView<infoMovConv>) root.lookup("#tablaDatos");
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
                                        final List<infoMovConv> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoMovConv> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                    Logger.getLogger(ConsultaMovConvDetController.class.getName()).log(Level.SEVERE, null, e);
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
                            final ObservableList<infoMovConv> TablaID = task.getValue();

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
                                                    final List<infoMovConv> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoMovConv> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getLogger(ConsultaMovConvDetController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getLogger(ConsultaMovConvDetController.class.getName()).log(Level.SEVERE, null, e);
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

    public class GetRegistrosTask extends Service<ObservableList<infoMovConv>> {

        @Override
        protected Task<ObservableList<infoMovConv>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // setNumpagina(getNumpagina() + 1);
                    final Cliente cliente = Cliente.getCliente();
                    String Respuesta = new String();

                    final StringBuilder tramaMovConv = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    final AtlasAcceso Atlasacceso = new AtlasAcceso();
                    String rnv = "";
                    if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                        rnv = "E";
                    } else {

                        rnv = "P";
                    }

                    //850,068,connid,codigoTransaccion,tracepaginacion,empresa/persona(E o P),identificacion,fechadesde,fechahasta,registrosCanal,registroInicial,codConv,tipoCtaRec,cuentaRec,clavehw,~
                    tramaMovConv.append("850,068,");
                    tramaMovConv.append(cliente.getRefid());
                    tramaMovConv.append(",");
                    tramaMovConv.append(AtlasConstantes.COD_RECAUDOS_MOV_CONV);
                    tramaMovConv.append(",");
                    tramaMovConv.append("S".equalsIgnoreCase(getIndicadorRegistros()) ? getTracePaginacion() : ""); // tracePaginacion
                    tramaMovConv.append(",");
                    tramaMovConv.append(rnv); //regla negocio
                    tramaMovConv.append(",");
                    tramaMovConv.append(cliente.getId_cliente());
                    tramaMovConv.append(",");
                    tramaMovConv.append(getFechainiAux());
                    tramaMovConv.append(",");
                    tramaMovConv.append(getFechafinAux());
                    tramaMovConv.append(",");
                    tramaMovConv.append(numpagina); // registrosCanal
                    tramaMovConv.append(",");
                    tramaMovConv.append(acumREg.addAndGet(numpagina.get())); //registrosPaginacion
                    tramaMovConv.append(",");
                    tramaMovConv.append(getCodigoConv());
                    tramaMovConv.append(",");
                    tramaMovConv.append(validarTipoCuenta(getTipoctarec()));
                    tramaMovConv.append(",");
                    tramaMovConv.append(getNumctarec());
                    tramaMovConv.append(",");
                    tramaMovConv.append(cliente.getContraseña());
                    tramaMovConv.append(",");
                    tramaMovConv.append(cliente.getTokenOauth());
                    tramaMovConv.append(",~");

//                    System.out.println("TRAMA mov Conv :" + tramaMovConv);
                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ movmimientos PES = " + "##" + docs.obtenerHoraActual());

//                        Respuesta = "850,068,000,TRANSACCION EXITOSA,S,10,TracePaginacion,"
//                                + "20180730%20180731%63815%reference1%reference2%reference3%descripcionCanal%1350000%metodoDePago%modoDeprocesamiento%7%25377634449%228888968%nombreDelPagador%0354%123456789235689%4500000%3500000%1500000%500000%1569%descripcionOficinaRecaudadora%Medellin%10%modoDeCaptura;"
//                                + "20180730%20180731%63815%reference1%reference2%reference3%descripcionCanal%1350000%metodoDePago%modoDeprocesamiento%7%25377634449%228888968%nombreDelPagador%0354%123456789235689%4500000%3500000%1500000%500000%1569%descripcionOficinaRecaudadora%Medellin%10%modoDeCaptura;"
//                                + "20180730%20180731%63815%reference1%reference2%reference3%descripcionCanal%1350000%metodoDePago%modoDeprocesamiento%7%25377634449%228888968%nombreDelPagador%0354%123456789235689%4500000%3500000%1500000%500000%1569%descripcionOficinaRecaudadora%Medellin%10%modoDeCaptura;"
//                                + "20180730%20180731%63815%reference1%reference2%reference3%descripcionCanal%1350000%metodoDePago%modoDeprocesamiento%7%25377634449%228888968%nombreDelPagador%0354%123456789235689%4500000%3500000%1500000%500000%1569%descripcionOficinaRecaudadora%Medellin%10%modoDeCaptura;,~";
                        // Respuesta="850,068,000,TRANSACCION EXITOSA,S,000168,tracepaginac,20180629112242%20180629%1206540        %09561111                           %                                   %                                   %Sucursal Virtual Personas     %350000.00       %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889921                         %Pagostren35                                                                     %1007    %40619921000         %35000000%00%00%00%     %                                                                                %                                                  %0  %0;20180629112240%20180629%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180628154315%20180628%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145237%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145236%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145236%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145236%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145235%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145206%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180620093324%20180620%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180619173630%20180619%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180618113912%20180618%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180618113912%20180618%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180616152825%20180616%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180615105513%20180615%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180614100509%20180614%1206540        %1234566                            %                                   %                                   %SUCURSAL F?SICA               %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180614100403%20180614%1206540        %09561111                           %                                   %                                   %Sucursal Virtual Personas     %350000.00       %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889921                         %Pagostren35                                                                     %1007    %40619921000         %35000000%00%00%00%     %                                                                                %                                                  %0  %0;20180613100750%20180613%1206540        %44446                              %                                   %                                   %Sucursal Virtual Personas     %2000000.00      %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920000         %200000000%00%00%00%     %                                                                                %                                                  %0  %0;20180613100330%20180613%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112213%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112213%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112213%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112212%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112212%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112212%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112203%20180612%1206540        %2222222                            %                                   %                                   %Sucursal Virtual Personas     %1000.00         %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889939                         %Pagostren53                                                                     %1007    %40619939000         %100000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154709%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154709%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154708%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154708%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154708%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154706%20180606%1206540        %2222222                            %                                   %                                   %Sucursal Virtual Personas     %1000.00         %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889939                         %Pagostren53                                                                     %1007    %40619939000         %100000%00%00%00%     %                                                                                %                                                  %0  %0;20180601154929%20180601%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111611%20180531%1206540        %1115                               %                                   %                                   %Sucursal Virtual Personas     %131.00          %Cuenta de Ahorros             %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889903                         %Pagostren03                                                                     %1007    %40679903010         %13100%00%00%00%     %                                                                                %                                                  %0  %0;20180531111215%20180531%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111154%20180531%1206540        %09561111                           %                                   %                                   %Sucursal Virtual Personas     %350000.00       %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889921                         %Pagostren35                                                                     %1007    %40619921000         %35000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111152%20180531%1206540        %1109                               %                                   %                                   %Sucursal Virtual Personas     %131000.00       %Cuenta de Ahorros             %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889903                         %Pagostren03                                                                     %1007    %40679903009         %13100000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111116%20180531%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111114%20180531%1206540        %4440                               %                                   %                                   %Sucursal Virtual Personas     %3000000000000.00%Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920000         %300000000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531110856%20180531%1206540        %1111                               %                                   %                                   %Sucursal Virtual Personas     %13100.00        %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889903                         %Pagostren03                                                                     %1007    %40619903001         %1310000%00%00%00%     %                                                                                %                                                  %0  %0,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMovConv.toString());

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 2, ",") + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMovConv.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 2, ",") + "##" + docs.obtenerHoraActual());

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
                        final String tracePaginacion = Respuesta.split(",")[6];

                        setIndicadorRegistros(indMasREg);
                        setTracePaginacion(tracePaginacion);
                        totalREg.set(Integer.parseInt(numReg));

                        String[] LMov = Respuesta.split(",")[7].split(";");

                        for (int i = 0; i < LMov.length; i++) {

                            String[] datos = LMov[i].split("%");
                            String fechatran = "";
                            String fechaaplic = "";
                            String montopago = "";
                            String valororiginal = "";
                            String valorcomision = "";
                            String pagoefectivo = "";
                            String pagocheuqe = "";

                            try {
                                fechatran = formatoFechaPago.format(formatoFechaPagoO.parse(datos[0].trim()));

                            } catch (Exception e) {
                                fechatran = datos[0].trim();
                            }

                            try {
                                fechaaplic = formatoFechaSalida.format(formatoFecha.parse(datos[1].trim()));

                            } catch (Exception e) {
                                fechaaplic = datos[1].trim();
                            }

                            try {
                                montopago = "$ " + formatonum.format(Double.parseDouble(datos[7].trim().split("\\.")[0])).replace(".", ",") + "." + datos[7].trim().split("\\.")[1];
                            } catch (Exception e) {
                                montopago = "$ " + datos[7];
                            }

                            try {
                                valororiginal = "$ " + formatonum.format(Double.parseDouble(datos[16].trim().split("\\.")[0])).replace(".", ",") + "." + datos[16].trim().split("\\.")[1];
                            } catch (Exception e) {
                                valororiginal = "$ " + datos[16];
                            }

                            try {
                                valorcomision = "$ " + formatonum.format(Double.parseDouble(datos[19].trim().split("\\.")[0])).replace(".", ",") + "." + datos[19].trim().split("\\.")[1];
                            } catch (Exception e) {
                                valorcomision = "$ " + datos[19];
                            }

                            try {
                                pagocheuqe = "$ " + formatonum.format(Double.parseDouble(datos[18].trim().split("\\.")[0])).replace(".", ",") + "." + datos[18].trim().split("\\.")[1];
                            } catch (Exception e) {
                                pagocheuqe = "$ " + datos[18];
                            }

                            try {
                                pagoefectivo = "$ " + formatonum.format(Double.parseDouble(datos[17].trim().split("\\.")[0])).replace(".", ",") + "." + datos[17].trim().split("\\.")[1];
                            } catch (Exception e) {
                                pagoefectivo = "$ " + datos[17];
                            }

//  private SimpleStringProperty fechatran;
//  private SimpleStringProperty fechaaplic;
//  private SimpleStringProperty codigoconv;
//  private SimpleStringProperty ref1;
//  private SimpleStringProperty ref2;
//  private SimpleStringProperty ref3;
//  private SimpleStringProperty descripcioncanal;
//  private SimpleStringProperty montopago;
//  private SimpleStringProperty metodopago;
//  private SimpleStringProperty modoprocess;
//  private SimpleStringProperty tipoctarec;
//  private SimpleStringProperty numctarec;
//  private SimpleStringProperty nitpagador;
//  private SimpleStringProperty nombrepagador;
//  private SimpleStringProperty codigobancpagador;
//  private SimpleStringProperty numctatarjpagador;
//  private SimpleStringProperty valororiginal;
//  private SimpleStringProperty pagoefect;
//  private SimpleStringProperty pagocheque;
//  private SimpleStringProperty valorcomision;
//  private SimpleStringProperty codoficinarec;
//  private SimpleStringProperty descoficinarec;
//  private SimpleStringProperty nomciudad;
//  private SimpleStringProperty diascanje;
//  private SimpleStringProperty modocaptura;
//                            infoMovConv mov = new infoMovConv(datos[0],
//                                    datos[1],
//                                    datos[2],
//                                    datos[3],
//                                    datos[4],
//                                    datos[5],
//                                    datos[6],
//                                    datos[7],
//                                    datos[8],
//                                    datos[9],
//                                    datos[10],
//                                    datos[11],
//                                    datos[12],
//                                    datos[13],
//                                    datos[14],
//                                    datos[15],
//                                    datos[16],
//                                    datos[17],
//                                    datos[18],
//                                    datos[19],
//                                    datos[20],
//                                    datos[21],
//                                    datos[22],
//                                    datos[23],
//                                    datos[24]);
                            infoMovConv mov = new infoMovConv(fechatran,
                                    fechaaplic,
                                    datos[2].trim(),
                                    datos[3].trim(),
                                    datos[4].trim(),
                                    datos[5].trim(),
                                    datos[6].trim(),
                                    montopago.trim(),
                                    datos[8].trim(),
                                    datos[9].trim(),
                                    datos[10].trim(),
                                    datos[11].trim(),
                                    datos[12].trim(),
                                    datos[13].trim(),
                                    datos[14].trim(),
                                    datos[15].trim(),
                                    valororiginal.trim(),
                                    pagoefectivo.trim(),
                                    pagocheuqe.trim(),
                                    valorcomision.trim(),
                                    datos[20].trim(),
                                    datos[21].trim(),
                                    datos[22].trim(),
                                    datos[23].trim(),
                                    datos[24]);

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

    /**
     * BUSCAR POR ID
     */
    private void BusquedanitRecaudo(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoMovConv>> TaskTablaId = new BusdIDnitRecaudador(id);

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
                            final ObservableList<infoMovConv> TablaID = TaskTablaId.getValue();

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
                                                    final List<infoMovConv> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoMovConv> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getLogger(ConsultaMovConvDetController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(ConsultaMovConvDetController.class.getName()).log(Level.SEVERE, null, ex);

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
    void buscarOP(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoMovConv>> TaskTablaId = new BusqFechaMovConv(fechaini, fechafin);

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
                            final ObservableList<infoMovConv> TablaID = TaskTablaId.getValue();

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
                                                    final List<infoMovConv> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoMovConv> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getLogger(ConsultaMovConvDetController.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(ConsultaMovConvDetController.class.getName()).log(Level.SEVERE, null, ex);

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
    void codConvKeyTyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    BusquedanitRecaudo(codConv.getText() + event.getCharacter());

                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (codConv.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {

                            BusquedanitRecaudo("");

                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {

                            BusquedanitRecaudo(codConv.getText());

                        }
                    }

                }

            }

        }
    }

    @FXML
    void codConvkeypress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(codConv, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            codConv.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    private String validarTipoCuenta(final String Cuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(Cuenta)) {
            retorno.append("7");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(Cuenta)) {
            retorno.append("1");
        } else {
            retorno.append("0");
        }

        return retorno.toString();
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
    void detalleOP(ActionEvent event) {
        final infoMovConv selectedItem = tablaDatos.getSelectionModel().getSelectedItem();
        ConsultaMovConvFinController controller = new ConsultaMovConvFinController();
        controller.mostrarMovPSEdetalle(selectedItem);
    }

    @FXML
    void volverOP(ActionEvent event) {
        ConsultaMovConvinitController controller = new ConsultaMovConvinitController();
        controller.mostrarMovConvenio(true);
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
        final int diferenciaDias = docs.CalcularDifFechas(calendarFiaux, calendarFfaux);

//        System.out.println("diferencia dias " + diferenciaDias);
        if ((diferenciaDias <= 360) && (diferenciaDias != -1)) {
            buscar.setDisable(false);
        } else {
            // numConv.setText("");
            // numConv.setDisable(true);
            buscar.setDisable(true);
        }

        return true;

    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
