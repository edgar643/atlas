/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.tokenempresas.InfoTokenToAE;
import com.co.allus.modelo.tokenempresas.infoDetalletf;
import com.co.allus.userComponent.DatePicker;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
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
public class TokenEmpresasAEController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn colCosto;
    @FXML
    private TableColumn colDestino;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colHora;
    @FXML
    private TableColumn colVigencia;
    @FXML
    private TableColumn colEstado;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Button regresar_op;
    @FXML
    private Button limpiar_op;
    @FXML
    private Button buscar_op;
    @FXML
    private TableView<InfoTokenToAE> tabla_datos;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfNombre;
    @FXML
    private DatePicker tfFechaFin;
    @FXML
    private DatePicker tfFechaIni;
    private static List<InfoTokenToAE> dataTabla;
    private static GestorDocumental docs = new GestorDocumental();
    public static AtomicBoolean clearBusqueda = new AtomicBoolean();
    private Pagination pagination = new Pagination();
    private Service<ObservableList<InfoTokenToAE>> TaskdataTabla = new BusquedaFechaTask();
    public static ObservableList<InfoTokenToAE> datosTabla = FXCollections.observableArrayList();
    int currentpageindex = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert colCosto != null : "fx:id=\"colCosto\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert colDestino != null : "fx:id=\"colDestino\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert colFecha != null : "fx:id=\"colFecha\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert colHora != null : "fx:id=\"colHora\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert colVigencia != null : "fx:id=\"colVigencia\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert colEstado != null : "fx:id=\"colEstado\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert regresar_op != null : "fx:id=\"regresar_op\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert limpiar_op != null : "fx:id=\"limpiar_op\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert buscar_op != null : "fx:id=\"buscar_op\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert tfID != null : "fx:id=\"tfID\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert tfNombre != null : "fx:id=\"tfNombre\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert tfFechaFin != null : "fx:id=\"tfFechaFin\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        assert tfFechaIni != null : "fx:id=\"tfFechaIni\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";

        this.location = url;
        this.resources = rb;
        progreso.setVisible(false);
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colFecha.setCellValueFactory(new PropertyValueFactory<InfoTokenToAE, String>("fecha_envio"));
        colHora.setCellValueFactory(new PropertyValueFactory<InfoTokenToAE, String>("hora_envio"));
        colCosto.setCellValueFactory(new PropertyValueFactory<InfoTokenToAE, String>("vigencia"));
        colVigencia.setCellValueFactory(new PropertyValueFactory<InfoTokenToAE, String>("vigencia"));
        colDestino.setCellValueFactory(new PropertyValueFactory<InfoTokenToAE, String>("email"));
        colEstado.setCellValueFactory(new PropertyValueFactory<InfoTokenToAE, String>("estado"));

    }

    @FXML
    void buscar_op(ActionEvent event) {
        try {
            if (esSeleccionado(tfFechaIni.getCalendarView().getCalendar()) && esSeleccionado(tfFechaFin.getCalendarView().getCalendar())) {
                consumirDatos();
            } else {
                final ObservableList<InfoTokenToAE> dataempty = FXCollections.emptyObservableList();
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
      docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
        }
    }

    @FXML
    void regresar_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dataTabla.clear();
                TokenEmpresasController controller = new TokenEmpresasController();
                controller.mostrarDatosToken();
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

            if ((fini1 <= hoy1) && (fini1 <= ffin1) && (ffin1 <= hoy1)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
         docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
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
            final ObservableList<InfoTokenToAE> dataempty = FXCollections.emptyObservableList();
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
                            final ObservableList<InfoTokenToAE> TablaBusFecha = TaskdataTabla.getValue();

                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaBusFecha.size() % rowsPerPageDate() == 0 ? TablaBusFecha.size() / rowsPerPageDate() : TablaBusFecha.size() / rowsPerPageDate() + 1;

                                pagination = new Pagination(numpag, 0);
                                //paginationid.setId("paginationid");
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaBusFecha.size() % rowsPerPageDate();
                                            if (displace >= 0) {
                                                lastIndex = TablaBusFecha.size() / rowsPerPageDate();
                                            } else {
                                                lastIndex = TablaBusFecha.size() / rowsPerPageDate() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<InfoTokenToAE> subList = TablaBusFecha.subList(pageIndex * rowsPerPageDate(), pageIndex * rowsPerPageDate() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTokenToAE> subList = TablaBusFecha.subList(pageIndex * rowsPerPageDate(), pageIndex * rowsPerPageDate() + rowsPerPageDate());
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
                                                tabla_datos.getItems().setAll(TablaBusFecha.subList(newValue.intValue() * rowsPerPageDate(), ((newValue.intValue() * rowsPerPageDate() + rowsPerPageDate() <= TablaBusFecha.size()) ? newValue.intValue() * rowsPerPageDate() + rowsPerPageDate() : TablaBusFecha.size())));
                                            }
                                        });

                                    }
                                });

                                /**
                                 * fin configuracion de la paginacion
                                 */
                                try {

                                    AnchorPane.getChildren().remove(8);

                                } catch (Exception e) {
                                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(8).setLayoutX(4);
                                AnchorPane.getChildren().get(8).setLayoutY(273);
//                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {

                                Logger.getLogger(TokenEmpresasAEController.class.getName()).log(Level.SEVERE, null, ex);

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
                                    ObservableList<InfoTokenToAE> dataempty = FXCollections.emptyObservableList();
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
                    Logger.getGlobal().log(Level.SEVERE, ex.toString());
                }

            }
        });

    }

    public class BusquedaFechaTask extends Service<ObservableList<InfoTokenToAE>> {

        @Override
        protected Task<ObservableList<InfoTokenToAE>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    ObservableList<InfoTokenToAE> datosae = FXCollections.observableArrayList();

                    if (TokenEmpresasAEController.clearBusqueda.get()) {

                        datosae.addAll(TokenEmpresasAEController.datosTabla);

                    } else {
                        for (Iterator<InfoTokenToAE> it = TokenEmpresasAEController.datosTabla.iterator(); it.hasNext();) {
                            final InfoTokenToAE data = it.next();
                            if (data.getFecha_envio().equalsIgnoreCase(tfFechaIni.getDateFormat().format(tfFechaIni.getSelectedDate())) || data.getFecha_envio().startsWith(tfFechaFin.getDateFormat().format(tfFechaFin.getSelectedDate()))) {
                                datosae.add(data);
                            }
                        }
                    }
                    return datosae;
                }
            };
        }
    }

    public void mostrarDatosTokenEmpresasAE(final List<InfoTokenToAE> data, final infoDetalletf infoDetalletf) {
        this.dataTabla.clear();
        this.dataTabla = data;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenEmpresasAE.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    final Button regresar_op = (Button) root.lookup("#regresar_op");
                    final Button buscar_op = (Button) root.lookup("#buscar_op");
                    final Button limpiar_op = (Button) root.lookup("#limpiar_op");

                    final DatePicker tfFechaIni = (DatePicker) root.lookup("#tfFechaIni");
                    final DatePicker tfFechaFin = (DatePicker) root.lookup("#tfFechaFin");

                    final TextField tfID = (TextField) root.lookup("#tfID");
                    final TextField tfNombre = (TextField) root.lookup("#tfNombre");

                    tfID.setText(infoDetalletf.getId_usuario());
                    tfNombre.setText(infoDetalletf.getNombre());

                    tfFechaIni.setDateFormat(new SimpleDateFormat("YYYY/MM/dd"));
                    tfFechaFin.setDateFormat(new SimpleDateFormat("YYYY/MM/dd"));

                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);

                    final TableView<InfoTokenToAE> tablaData = (TableView<InfoTokenToAE>) root.lookup("#tabla_datos");

//                    final DropShadow shadow = new DropShadow();
//                    regresar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    regresar_op.setCursor(Cursor.HAND);
//                                    regresar_op.setEffect(shadow);
//                                }
//                            });
//
//                    regresar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    regresar_op.setCursor(Cursor.DEFAULT);
//                                    regresar_op.setEffect(null);
//
//                                }
//                            });
//                    
//                    buscar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    buscar_op.setCursor(Cursor.HAND);
//                                    buscar_op.setEffect(shadow);
//                                }
//                            });
//
//                    buscar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    buscar_op.setCursor(Cursor.DEFAULT);
//                                    buscar_op.setEffect(null);
//
//                                }
//                            });
//                    
//                    limpiar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    limpiar_op.setCursor(Cursor.HAND);
//                                    limpiar_op.setEffect(shadow);
//                                }
//                            });
//
//                    limpiar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    limpiar_op.setCursor(Cursor.DEFAULT);
//                                    limpiar_op.setEffect(null);
//
//                                }
//                            });
//                    
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    datosTabla.clear();
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
                                        final List<InfoTokenToAE> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<InfoTokenToAE> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutX(4);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(273);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception e) {

                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 2;
    }

    public int rowsPerPageDate() {
        return 2;
    }
//    @FXML
//    void continuarOP(final ActionEvent event) {
//        infoAccesoEmergencia selectedItem = tabla_datos.getSelectionModel().getSelectedItem();
//        dataTablaAE infotabla = dataTablaAE.getDataTablaAE();
//        infotabla.setId_usuario(selectedItem.getColID_usuario());
//        infotabla.setNombre_usuario(selectedItem.getColNombre_usuario());
//        dataTablaAE.setDataTablaAE(infotabla);
//        
//        continuar_Op().setOnSucceeded(new EventHandler() {
//            @Override
//            public void handle(final Event event) {
//                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle token ae" + "##" + docs.obtenerHoraActual());
//                progreso.progressProperty().unbind();
//                progreso.setProgress(0);
//                progreso.setVisible(false);
//            }
//        });
//        
//        continuar_Op().setOnCancelled(new EventHandler() {
//            @Override
//            public void handle(final Event event) {
//                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle token" + "##" + docs.obtenerHoraActual());
//                progreso.progressProperty().unbind();
//                progreso.setProgress(0);
//                progreso.setVisible(false);
//            }
//        });
//
//        if (continuar_Op().isRunning()) {
//            continuar_op.setDisable(true);
//            progreso.setVisible(true);
//            progreso.progressProperty().unbind();
//            progreso.progressProperty().bind(continuar_Op().progressProperty());
//            continuar_Op().reset();
//            continuar_Op().start();
//
//        } else {
//            continuar_op.setDisable(true);
//            progreso.setVisible(true);
//            progreso.progressProperty().unbind();
//            progreso.progressProperty().bind(continuar_Op().progressProperty());
//            continuar_Op().start();
//        }
//    }
//
//    public Service<Void> continuar_Op() {
//        serviceDetalleAE = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        String Respuesta = new String();
//
//                        final Cliente datosCliente = Cliente.getCliente();
//                        final StringBuilder tramaAEDetalleFecha = new StringBuilder();
//                        final ConectSS servicioSS = new ConectSS();
//                        final Cliente cliente = Cliente.getCliente();
//                        
//
////                        id = tokenAE.get(0).getColID_usuario();
////                        nombre_usuario = tokenAE.get(0).getColNombre_usuario();
//
//                        tramaAEDetalleFecha.append("850,043,");
//                        tramaAEDetalleFecha.append(datosCliente.getRefid());
//                        tramaAEDetalleFecha.append(",");
//                        tramaAEDetalleFecha.append(AtlasConstantes.COD_TOKEN_AE);
//                        tramaAEDetalleFecha.append(",");
//                        tramaAEDetalleFecha.append(datosCliente.getId_cliente());
//                        tramaAEDetalleFecha.append(",");
//                        final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
//                        tramaAEDetalleFecha.append(contra);
//                        tramaAEDetalleFecha.append(",");
//                        tramaAEDetalleFecha.append("2"); //TIPO CONSULTA
//                        tramaAEDetalleFecha.append(",");
//                        tramaAEDetalleFecha.append(dataTablaAE.getDataTablaAE().getId_usuario()); //ID USUARIO
//                        tramaAEDetalleFecha.append(",");
//                        tramaAEDetalleFecha.append(""); //fini
//                        tramaAEDetalleFecha.append(",");
//                        tramaAEDetalleFecha.append(""); //ffin
//                        tramaAEDetalleFecha.append(",");
//                        tramaAEDetalleFecha.append(datosCliente.getContraseña()); //ID USUARIO
//                        tramaAEDetalleFecha.append(",~");
//                        System.out.println("TRAMA DETALLE AE :" + tramaAEDetalleFecha);
//
//
//                        try {
//                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ = " + tramaAEDetalleFecha.toString() + "##" + docs.obtenerHoraActual());
//                            //850,033,000,descripcion,tipoConsulta,nombreCompañia,EsquemaSeguridad,EstadoDelaCompañia,CantidadToken,IndicadorAuth,
//                            //idUsuario%nombreUsuario%DescripcionNovedad%EstadoNovedad%CostoNovedad%FechaNovedad%HoraNovedad%CanalNovedad; .....(repite CantidadToken veces)
////                            Respuesta = "850,"
////                                    + "043,"
////                                    + "000,"
////                                    + "TRANSACCION EXITOSA,"
////                                    + "BANCOLOMBIA,"
////                                    + "7," //AE PERMITIDOS
////                                    + "4," //AE SOLICITADOS
////                                    + "3," //AE POR SOLICITAR
////                                    + "7," //PERIODO AE
////                                    + "10,"//CANTIDAD REGISTROS
////                                    + "21/07/2015%11:13:05%0%15%agarzon@bancolombia.com.co%SI;"
////                                    + "22/07/2015%11:14:05%0%16%agarzon@bancolombia.com.co%NO;"
////                                    + "23/07/2015%11:15:05%0%17%agarzon@bancolombia.com.co%NO,"
////                                    + "~";
////
////                            System.out.println(" RESPUESTA TRAMA TOKEN EMPRESAS:" + Respuesta);
//                             Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAEDetalleFecha.toString());
//                             System.out.println(" RESPUESTA TRAMA TOKEN AE:" + Respuesta);
//                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
//                        } catch (Exception ex) {
//
//                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
//                            //envio a contingencia
//                            try {
//                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ = " + tramaAEDetalleFecha.toString() + "##" + docs.obtenerHoraActual());
//                             Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAEDetalleFecha.toString());
//                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
//
//                            } catch (Exception ex1) {
//                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
//                                Platform.runLater(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
//                                        continuar_op.setDisable(false);
//                                        progreso.progressProperty().unbind();
//                                        progreso.setProgress(0);
//                                        progreso.setVisible(false);
//
//                                    }
//                                });
//
//                            }
//
//                        }
//
//                        if ("000".equals(Respuesta.split(",")[2])) {
//                             final List<infoDetalleAE> lista = new ArrayList<infoDetalleAE>();
//                            try {
//                              
//                            System.out.println(Respuesta.split(",")[10]);
//                            String[] data = Respuesta.split(",")[10].split(";");
//
//                            for (int i = 0; i < data.length; i++) {
//                                final String[] datos = data[i].split("%");
//
//                                final infoDetalleAE ObjDet = new infoDetalleAE(
//                                        datos[0].trim(), // fecha  
//                                        datos[1].trim(), // hora
//                                        " ", //valor novedad
//                                        datos[2].trim(), //vigencia
//                                        datos[3], // destino
//                                        datos[4].trim()); //estado
//
//                                lista.add(ObjDet);
//
//                            }  
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            
//                           
//
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    final TokenAccesoEmergenciaDetalleController controller = new TokenAccesoEmergenciaDetalleController();
//                                    controller.mostrarDetalleAE(lista, dataTablaAE.getDataTablaAE());
//                                }
//                            });
//
//                        } else {
//                            final String coderror = Respuesta.split(",")[2];
//                            final String mensaje = Respuesta.split(",")[3].trim();
//
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
//                                    cancel();
//                                    continuar_op.setDisable(false);
//                                    progreso.progressProperty().unbind();
//                                    progreso.setProgress(0);
//                                    progreso.setVisible(false);
//                                }
//                            });
//
//                        }
//
//                        return null;
//                    }
//                };
//            }
//        };
//
//        return serviceDetalleAE;
//
//    }
}
