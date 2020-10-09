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
import com.co.allus.modelo.tokenempresas.dataTablaAE;
import com.co.allus.modelo.tokenempresas.infoDetalleAE;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class TokenAccesoEmergenciaDetalleController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button buscar_op;
    @FXML
    private TableColumn<infoDetalleAE, String> colCostoNovedad;
    @FXML
    private TableColumn<infoDetalleAE, String> colDestino;
    @FXML
    private TableColumn<infoDetalleAE, String> colEstado;
    @FXML
    private TableColumn<infoDetalleAE, String> colFechaEnvio;
    @FXML
    private TableColumn<infoDetalleAE, String> colHoraEnvio;
    @FXML
    private TableColumn<infoDetalleAE, String> colVigencia;
    @FXML
    private Button limpiar_op;
    @FXML
    private Button regresar_op;
    @FXML
    private TableView<infoDetalleAE> tabla_datos;
    @FXML
    private DatePicker tfFechaFin;
    @FXML
    private DatePicker tfFechaIni;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfNombreUsuario;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button obtMasReg;
    private static GestorDocumental docs = new GestorDocumental();
    private static List<infoDetalleAE> dataTabla;
    private Service<ObservableList<infoDetalleAE>> TaskdataTabla = new BusquedaFechaTask();
    private Service<ObservableList<infoDetalleAE>> task = new TokenAccesoEmergenciaDetalleController.getTokenAEdtalle();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static AtomicBoolean clearBusqueda = new AtomicBoolean();
    private static AtomicInteger numpagina = new AtomicInteger(0);
    private String indicadorRegistros;
    // public static ObservableList<infoDetalleAE> dataAE = FXCollections.observableArrayList();

    public String getIndicadorRegistros() {
        return indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        this.indicadorRegistros = indicadorRegistros;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert buscar_op != null : "fx:id=\"buscar_op\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert colCostoNovedad != null : "fx:id=\"colCostoNovedad\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert colDestino != null : "fx:id=\"colDestino\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert colEstado != null : "fx:id=\"colEstado\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert colFechaEnvio != null : "fx:id=\"colFechaEnvio\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert colHoraEnvio != null : "fx:id=\"colHoraEnvio\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert colVigencia != null : "fx:id=\"colVigencia\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert limpiar_op != null : "fx:id=\"limpiar_op\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert regresar_op != null : "fx:id=\"regresar_op\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert tfFechaFin != null : "fx:id=\"tfFechaFin\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert tfFechaIni != null : "fx:id=\"tfFechaIni\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert tfID != null : "fx:id=\"tfID\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert tfNombreUsuario != null : "fx:id=\"tfNombreUsuario\" was not injected: check your FXML file 'TokenAccesoEmergenciaDetalleController.fxml'.";
        assert obtMasReg != null : "fx:id=\"obtMasReg\" was not injected: check your FXML file 'TokenEmpresasDisponibles.fxml'.";
        this.location = url;
        this.resources = rb;
        TokenAccesoEmergenciaDetalleController.cancelartarea.set(false);
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colFechaEnvio.setCellValueFactory(new PropertyValueFactory<infoDetalleAE, String>("colFechaEnvio"));
        colHoraEnvio.setCellValueFactory(new PropertyValueFactory<infoDetalleAE, String>("colHoraEnvio"));
        colCostoNovedad.setCellValueFactory(new PropertyValueFactory<infoDetalleAE, String>("colCostoNovedad"));
        colVigencia.setCellValueFactory(new PropertyValueFactory<infoDetalleAE, String>("colVigencia"));
        colDestino.setCellValueFactory(new PropertyValueFactory<infoDetalleAE, String>("colDestino"));
        colEstado.setCellValueFactory(new PropertyValueFactory<infoDetalleAE, String>("colEstado"));
        clearBusqueda.set(false);

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
                            final ObservableList<infoDetalleAE> TablaID = task.getValue();


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
                                                    final List<infoDetalleAE> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoDetalleAE> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(287);
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

    @FXML
    void regresar_op(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dataTabla.clear();
                TokenAccesoEmergenciaController controller = new TokenAccesoEmergenciaController();
                controller.mostrarDatosTokenAE();
            }
        });
    }

    @FXML
    void buscar_op(ActionEvent event) {
        try {
            if (esSeleccionado(tfFechaIni.getCalendarView().getCalendar()) && esSeleccionado(tfFechaFin.getCalendarView().getCalendar())) {
                consumirDatos();
            } else {
                final ObservableList<infoDetalleAE> dataempty = FXCollections.emptyObservableList();
                tabla_datos.setItems(dataempty);
            }
        } catch (Exception ex) {
            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
        }
    }

    @FXML
    void limpiar_op(ActionEvent event) {
        try {
            tfFechaFin.setSelectedDate(null);
            tfFechaIni.setSelectedDate(null);
            clearBusqueda.set(true);
            ConsultarFechar();

        } catch (Exception ex) {
            Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void mostrarDetalleAE(final List<infoDetalleAE> data, final dataTablaAE dataTablaAE, final String indiCadoMasReg, final int numPag) {
        this.dataTabla = data;
        numpagina.set(numPag);
        setIndicadorRegistros(indiCadoMasReg);
        //System.out.println("INDICADOR MAS REGISTROS " + getIndicadorRegistros() + "---  NUMPAG " + numpagina.get());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenAccesoEmergenciaDetalle.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button limpiar_op = (Button) root.lookup("#limpiar_op");
                    final Button buscar_op = (Button) root.lookup("#buscar_op");
                    final Button regresar_op = (Button) root.lookup("#regresar_op");
                    final Button obtMasReg = (Button) root.lookup("#obtMasReg");

                    final TextField tfID = (TextField) root.lookup("#tfID");
                    final TextField tfNombreUsuario = (TextField) root.lookup("#tfNombreUsuario");
                    tfID.setText(dataTablaAE.getId_usuario());
                    tfNombreUsuario.setText(dataTablaAE.getNombre_usuario());
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<infoDetalleAE> tablaData = (TableView<infoDetalleAE>) root.lookup("#tabla_datos");

                    final DatePicker tfFechaIni = (DatePicker) root.lookup("#tfFechaIni");
                    final DatePicker tfFechaFin = (DatePicker) root.lookup("#tfFechaFin");

                    tfFechaIni.setDateFormat(new SimpleDateFormat("dd/MM/YYYY"));
                    tfFechaFin.setDateFormat(new SimpleDateFormat("dd/MM/YYYY"));

                    if ("S".equalsIgnoreCase(getIndicadorRegistros())) {
                        obtMasReg.setDisable(false);
                    } else {
                        obtMasReg.setDisable(true);
                    }

                    final DropShadow shadow = new DropShadow();
                    limpiar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            limpiar_op.setCursor(Cursor.HAND);
                            limpiar_op.setEffect(shadow);
                        }
                    });

                    limpiar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            limpiar_op.setCursor(Cursor.DEFAULT);
                            limpiar_op.setEffect(null);

                        }
                    });

                    buscar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            buscar_op.setCursor(Cursor.HAND);
                            buscar_op.setEffect(shadow);
                        }
                    });

                    buscar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            buscar_op.setCursor(Cursor.DEFAULT);
                            buscar_op.setEffect(null);

                        }
                    });

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

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    final ObservableList<infoDetalleAE> datosTabla = FXCollections.observableArrayList();
                    datosTabla.addAll(data);

                    final int numpag = datosTabla.size() % rowsPerPage() == 0 ? datosTabla.size() / rowsPerPage() : datosTabla.size() / rowsPerPage() + 1;

                    pagination = new Pagination(numpag, 0);
                    pagination.setPageFactory(new Callback<Integer, Node>() {
                        @Override
                        public Node call(final Integer pageIndex) {
                            if (pageIndex > numpag) {
                                return null;
                            } else {
                                int lastIndex = 0;
                                int displace = datosTabla.size() % rowsPerPage();
                                if (displace >= 0) {
                                    lastIndex = datosTabla.size() / rowsPerPage();
                                } else {
                                    lastIndex = datosTabla.size() / rowsPerPage() - 1;
                                }
                                int page = pageIndex * itemsPerPage();

                                for (int i = page; i < page + itemsPerPage(); i++) {

                                    if (lastIndex == pageIndex) {
                                        final List<infoDetalleAE> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoDetalleAE> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    tablaData.getItems().setAll(datosTabla.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= datosTabla.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : datosTabla.size())));
                                }
                            });

                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    root.getChildren().add(pagination);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutX(1);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(287);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

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

    private boolean esRangoValido(final Date calendarFi, final Date calendarFf) {
        try {
            final String hoy = docs.obtenerFechaActualHoy();
            //final String dosMant = docs.obtenerFechaActualMesAnt2();
            final String fini = docs.convertirFecha2(calendarFi);
            final String ffin = docs.convertirFecha2(calendarFf);
            final long hoy1 = Long.parseLong(hoy); // hoy
            //final long dosMant1 = Long.parseLong(dosMant); // hace 2 meses
            final long fini1 = Long.parseLong(fini); //finicial
            final long ffin1 = Long.parseLong(ffin); //ffinal
            Calendar calendarFiaux = Calendar.getInstance();
            Calendar calendarFfaux = Calendar.getInstance();

            calendarFiaux.setTime(calendarFi);
            calendarFfaux.setTime(calendarFf);
            final int diferenciaDias = docs.CalcularDifFechas(calendarFiaux, calendarFfaux);

            // System.out.println("DIFERENCIA ENTRE FECHAS: " + diferenciaDias);
//            if (diferenciaDias > 60) {
//                return false;
//            }
            if ((fini1 <= hoy1) && (fini1 <= ffin1) && (ffin1 <= hoy1)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    private void consumirDatos() {
        if (esRangoValido(tfFechaIni.getSelectedDate(), tfFechaFin.getSelectedDate())) {
            clearBusqueda.set(false);
            ConsultarFechar();
        } else {
            tfFechaIni.setSelectedDate(tfFechaIni.getSelectedDate());
            tfFechaIni.setSelectedDate(tfFechaFin.getSelectedDate());
            final ObservableList<infoDetalleAE> dataempty = FXCollections.emptyObservableList();
            tabla_datos.setItems(dataempty);
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

                    TaskdataTabla.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<infoDetalleAE> TablaBusFecha = TaskdataTabla.getValue();

                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaBusFecha.size() % rowsPerPageCL() == 0 ? TablaBusFecha.size() / rowsPerPageCL() : TablaBusFecha.size() / rowsPerPageCL() + 1;

                                pagination = new Pagination(numpag, 0);
                                //paginationid.setId("paginationid");
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaBusFecha.size() % rowsPerPageCL();
                                            if (displace >= 0) {
                                                lastIndex = TablaBusFecha.size() / rowsPerPageCL();
                                            } else {
                                                lastIndex = TablaBusFecha.size() / rowsPerPageCL() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infoDetalleAE> subList = TablaBusFecha.subList(pageIndex * rowsPerPageCL(), pageIndex * rowsPerPageCL() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoDetalleAE> subList = TablaBusFecha.subList(pageIndex * rowsPerPageCL(), pageIndex * rowsPerPageCL() + rowsPerPageCL());
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
                                                tabla_datos.getItems().setAll(TablaBusFecha.subList(newValue.intValue() * rowsPerPageCL(), ((newValue.intValue() * rowsPerPageCL() + rowsPerPageCL() <= TablaBusFecha.size()) ? newValue.intValue() * rowsPerPageCL() + rowsPerPageCL() : TablaBusFecha.size())));
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
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(287);
//                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                        }
                    });

                    TaskdataTabla.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    String message = t.getSource().getException().getMessage();
                                    ObservableList<infoDetalleAE> dataempty = FXCollections.emptyObservableList();
                                    tabla_datos.setItems(dataempty);
                                }
                            });
                        }
                    });

                    TaskdataTabla.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            tabla_datos.setVisible(false);
                        }
                    });

                } catch (Exception ex) {
                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

                }

            }
        });

    }

    public class BusquedaFechaTask extends Service<ObservableList<infoDetalleAE>> {

        @Override
        protected Task<ObservableList<infoDetalleAE>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    ObservableList<infoDetalleAE> datosae = FXCollections.observableArrayList();

                    if (TokenAccesoEmergenciaDetalleController.clearBusqueda.get()) {

                        datosae.addAll(TokenAccesoEmergenciaDetalleController.dataTabla);

                    } else {
                        for (Iterator<infoDetalleAE> it = TokenAccesoEmergenciaDetalleController.dataTabla.iterator(); it.hasNext();) {
                            final infoDetalleAE data = it.next();
                            if (data.getColFechaEnvio().equalsIgnoreCase(tfFechaIni.getDateFormat().format(tfFechaIni.getSelectedDate())) || data.getColFechaEnvio().startsWith(tfFechaFin.getDateFormat().format(tfFechaFin.getSelectedDate()))) {
                                datosae.add(data);

                            }
                        }
                    }

                    return datosae;

                }
            };
        }
    }

    public class getTokenAEdtalle extends Service<ObservableList<infoDetalleAE>> {

        @Override
        protected Task<ObservableList<infoDetalleAE>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    String Respuesta = new String();

                    final Cliente datosCliente = Cliente.getCliente();
                    final StringBuilder tramaAEDetalleFecha = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    // final Cliente cliente = Cliente.getCliente();

//                        id = tokenAE.get(0).getColID_usuario();
//                        nombre_usuario = tokenAE.get(0).getColNombre_usuario();
                    tramaAEDetalleFecha.append("850,043,");
                    tramaAEDetalleFecha.append(datosCliente.getRefid());
                    tramaAEDetalleFecha.append(",");
                    tramaAEDetalleFecha.append(AtlasConstantes.COD_TOKEN_AE);
                    tramaAEDetalleFecha.append(",");
                    tramaAEDetalleFecha.append(datosCliente.getId_cliente());
                    tramaAEDetalleFecha.append(",");
                    final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                    tramaAEDetalleFecha.append(contra);
                    tramaAEDetalleFecha.append(",");
                    tramaAEDetalleFecha.append("2"); //TIPO CONSULTA
                    tramaAEDetalleFecha.append(",");
                    tramaAEDetalleFecha.append(dataTablaAE.getDataTablaAE().getId_usuario()); //ID USUARIO
                    tramaAEDetalleFecha.append(",");
                    tramaAEDetalleFecha.append(""); //fini
                    tramaAEDetalleFecha.append(",");
                    tramaAEDetalleFecha.append(""); //ffin
                    tramaAEDetalleFecha.append(",");
                    tramaAEDetalleFecha.append(numpagina.incrementAndGet());
                    tramaAEDetalleFecha.append(",");
                    tramaAEDetalleFecha.append(datosCliente.getContraseña()); //ID USUARIO
                    tramaAEDetalleFecha.append(",");
                    tramaAEDetalleFecha.append(datosCliente.getTokenOauth()); //ID USUARIO
                    tramaAEDetalleFecha.append(",~");
                    //System.out.println("TRAMA DETALLE AE :" + tramaAEDetalleFecha);

                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  token acceso emer detalle = " + "##" + docs.obtenerHoraActual());
                        //850,033,000,descripcion,tipoConsulta,nombreCompañia,EsquemaSeguridad,EstadoDelaCompañia,CantidadToken,IndicadorAuth,
                        //idUsuario%nombreUsuario%DescripcionNovedad%EstadoNovedad%CostoNovedad%FechaNovedad%HoraNovedad%CanalNovedad; .....(repite CantidadToken veces)
//                            Respuesta = "850,"
//                                    + "043,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "BANCOLOMBIA,"
//                                    + "7," //AE PERMITIDOS
//                                    + "4," //AE SOLICITADOS
//                                    + "3," //AE POR SOLICITAR
//                                    + "7," //PERIODO AE
//                                    + "10,"//CANTIDAD REGISTROS
                        //+ "N,"
//                                    + "21/07/2015%11:13:05%0%15%agarzon@bancolombia.com.co%SI;"
//                                    + "22/07/2015%11:14:05%0%16%agarzon@bancolombia.com.co%NO;"
//                                    + "23/07/2015%11:15:05%0%17%agarzon@bancolombia.com.co%NO,"
//                                    + "~";
//
//                            System.out.println(" RESPUESTA TRAMA TOKEN EMPRESAS:" + Respuesta);
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaAEDetalleFecha.toString());
                        // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAEDetalleFecha.toString());
                        // System.out.println(" RESPUESTA TRAMA TOKEN AE:" + Respuesta);
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token acceso emer detalle = " + "##" + docs.obtenerHoraActual());
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAEDetalleFecha.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaAEDetalleFecha.toString());
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
                        setIndicadorRegistros(Respuesta.split(",")[10]);

                        try {

                            //System.out.println(Respuesta.split(",")[11]);
                            String[] data = Respuesta.split(",")[11].split(";");

                            if (dataTabla.isEmpty()) {
                                dataTabla = FXCollections.observableArrayList();
                            }

                            for (int i = 0; i < data.length; i++) {
                                final String[] datos = data[i].split("%");

                                final infoDetalleAE ObjDet = new infoDetalleAE(
                                        datos[0].trim(), // fecha  
                                        datos[1].trim(), // hora
                                        " ", //valor novedad
                                        datos[2].trim(), //vigencia
                                        datos[3], // destino
                                        datos[4].trim()); //estado

                                dataTabla.add(ObjDet);

                            }
                        } catch (Exception ex) {
                            Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

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

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 1;
    }

    public int rowsPerPageCL() {
        return 1;
    }
}
