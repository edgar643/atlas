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
import com.co.allus.modelo.consultadebitosaut.infoDebAut1;
import com.co.allus.tareas.BusqConvDebAut;

import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
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
public class MovDebAut2Controller implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn ColEstado;
    @FXML
    private TableColumn ColFecha;
    @FXML
    private TableColumn ColNomConv;
    @FXML
    private TableColumn ColNumConv;
    @FXML
    private TableColumn ColRef2;
    @FXML
    private TableColumn ColRefFija;
    @FXML
    private TableColumn ColValor;
    @FXML
    private Button buscarFechas;
//    @FXML
//    private ImageView buscarOP;
    @FXML
    private TextField codConvT;
    @FXML
    private Button detalleOP;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private TableView<infoDebAut1> tablaDatos;
    @FXML
    private Button indMasReg;
    @FXML
    private Button limpiarOP;
//    @FXML
//    private TextField nrocta;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private Button volverOP;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static ObservableList<infoDebAut1> registros = FXCollections.observableArrayList();
    private Service<ObservableList<infoDebAut1>> task = new MovDebAut2Controller.GetRegistrosTask();
    private Service<ObservableList<infoDebAut1>> TaskdataTabla = new MovDebAut2Controller.BusquedaFechaTask();
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPagoO = new SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static AtomicInteger numpagina = new AtomicInteger(0);
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

        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert ColEstado != null : "fx:id=\"ColEstado\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert ColFecha != null : "fx:id=\"ColFecha\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert ColNomConv != null : "fx:id=\"ColNomConv\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert ColNumConv != null : "fx:id=\"ColNumConv\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert ColRef2 != null : "fx:id=\"ColRef2\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert ColRefFija != null : "fx:id=\"ColRefFija\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert ColValor != null : "fx:id=\"ColValor\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert buscarFechas != null : "fx:id=\"buscarFechas\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        // assert buscarOP != null : "fx:id=\"buscarOP\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert codConvT != null : "fx:id=\"codConvT\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert detalleOP != null : "fx:id=\"detalleOP\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert limpiarOP != null : "fx:id=\"limpiarOP\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        //assert nrocta != null : "fx:id=\"nrocta\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert panel_tabla != null : "fx:id=\"panelTabla\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'MovDebAut2.fxml'.";
        assert volverOP != null : "fx:id=\"volverOP\" was not injected: check your FXML file 'MovDebAut2.fxml'.";

        this.location = url;
        this.resources = rb;

        buscarFechas.setVisible(true);

        ///buscarOP.setVisible(true);
        indMasReg.setDisable(true);

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

        ColEstado.setCellValueFactory(new PropertyValueFactory<infoDebAut1, String>("EstadoPago"));
        ColFecha.setCellValueFactory(new PropertyValueFactory<infoDebAut1, String>("FechaPago"));
        ColNomConv.setCellValueFactory(new PropertyValueFactory<infoDebAut1, String>("NomConvenio"));
        ColNumConv.setCellValueFactory(new PropertyValueFactory<infoDebAut1, String>("CodConvenio"));
        ColRef2.setCellValueFactory(new PropertyValueFactory<infoDebAut1, String>("Ref2"));
        ColRefFija.setCellValueFactory(new PropertyValueFactory<infoDebAut1, String>("Referencia1"));
        ColValor.setCellValueFactory(new PropertyValueFactory<infoDebAut1, String>("ValorFactura"));
    }

    @FXML
    void detalleOP(ActionEvent event) {
        final infoDebAut1 selectedItem = tablaDatos.getSelectionModel().getSelectedItem();

        final MovDebAutDetalleController controller = new MovDebAutDetalleController();
        controller.mostrarMovDebdetalle(selectedItem);

    }

    @FXML
    void volverOP(ActionEvent event) {
        final MovDebAutIniController controller = new MovDebAutIniController();
        controller.mostrarMovDebitoAut(true);
    }

    @FXML
    void limpiarOP(final ActionEvent event) {
        try {
            codConvT.setText("");
            //nrocta.setText("");
            fechaini.setSelectedDate(null);
            fechafin.setSelectedDate(null);
            clearBusqueda.set(true);
            ConsultarFechar();
        } catch (Exception ex) {
            Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     * BUSCAR POR ID
     */
    private void BusquedaConvenio(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoDebAut1>> TaskTablaId = new BusqConvDebAut(id);

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
                            final ObservableList<infoDebAut1> TablaID = TaskTablaId.getValue();

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
                                                    final List<infoDebAut1> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoDebAut1> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(2);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(88);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);

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

                } catch (Exception ex) {
                    Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);

                }

            }
        });
    }

    @FXML
    void codConvkPress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(codConvT, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            codConvT.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void codConvktyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    BusquedaConvenio(codConvT.getText() + event.getCharacter());

                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (codConvT.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {

                            BusquedaConvenio("");

                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {

                            BusquedaConvenio(codConvT.getText());

                        }
                    }

                }

            }

        }
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
    void buscarFechas(ActionEvent event) {
        try {

            if (esSeleccionado(fechaini.getCalendarView().getCalendar()) && esSeleccionado(fechafin.getCalendarView().getCalendar())) {
                consumirDatos();

            }
        } catch (Exception ex) {
            Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Error buscarfecha");
        }

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
        } catch (Exception ex) {
            Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);

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

//            System.out.println("DIFERENCIA ENTRE FECHAS: " + diferenciaDias);
            if (diferenciaDias > 60) {
                tablaDatos.setPlaceholder(new Label("Rango mayor a 60 días"));
                return false;
            } else {
                tablaDatos.setPlaceholder(new Label("No hay registros asociados"));
            }

            if (diferenciaDias == -1) {
                tablaDatos.setPlaceholder(new Label("El rango de búsqueda no es válido"));
                ObservableList<infoDebAut1> dataempty = FXCollections.emptyObservableList();
                tablaDatos.setItems(dataempty);
                pagination.setVisible(false);

//                System.out.println("rango no es valido");
                return false;
            } else {
                tablaDatos.setPlaceholder(new Label("No hay registros asociados"));
            }

            if ((fini1 <= hoy1) && (fini1 <= ffin1) && (ffin1 <= hoy1)) {

                return true;
            } else {

                return false;

            }

        } catch (Exception ex) {
            Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }

    private void consumirDatos() {
        if (esRangoValido(fechaini.getSelectedDate(), fechafin.getSelectedDate())) {
            clearBusqueda.set(false);
            ConsultarFechar();
        } else {
            fechaini.setSelectedDate(fechaini.getSelectedDate());
            fechafin.setSelectedDate(fechafin.getSelectedDate());
            final ObservableList<infoDebAut1> dataempty = FXCollections.emptyObservableList();
            tablaDatos.setItems(dataempty);
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
                    tablaDatos.itemsProperty().bind(TaskdataTabla.valueProperty());
                    TaskdataTabla.reset();

                    TaskdataTabla.start();

                    limpiarOP.setDisable(true);

                    TaskdataTabla.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            limpiarOP.setDisable(false);
                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<infoDebAut1> TablaBusFecha = TaskdataTabla.getValue();

                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaBusFecha.size() % rowsPerPage() == 0 ? TablaBusFecha.size() / rowsPerPage() : TablaBusFecha.size() / rowsPerPage() + 1;

                                pagination = new Pagination(numpag, 0);

                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaBusFecha.size() % rowsPerPage();
                                            if (displace >= 0) {
                                                lastIndex = TablaBusFecha.size() / rowsPerPage();
                                            } else {
                                                lastIndex = TablaBusFecha.size() / rowsPerPage() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infoDebAut1> subList = TablaBusFecha.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoDebAut1> subList = TablaBusFecha.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                                tablaDatos.getItems().setAll(TablaBusFecha.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaBusFecha.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaBusFecha.size())));
                                            }
                                        });

                                    }
                                });

                                /**
                                 * fin configuracion de la paginacion
                                 */
//                                System.out.println("tamaño del anchor busqueda ref" + AnchorPane.getChildren().size());
                                try {

                                    AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);

                                } catch (Exception ex) {

                                    Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(2);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(88);

                            } catch (Exception ex) {
                                Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });

                    TaskdataTabla.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            limpiarOP.setDisable(false);
                            
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    String message = t.getSource().getException().getMessage();
                                    ObservableList<infoDebAut1> dataempty = FXCollections.emptyObservableList();
                                    tablaDatos.setItems(dataempty);
                                }
                            });
                        }
                    });

                    TaskdataTabla.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            limpiarOP.setDisable(false);
                            
                            tablaDatos.setVisible(false);
                        }
                    });

                    
                } catch (Exception ex) {
                    Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);
//                    ex.printStackTrace();
                }

            }
        });

    }

    public class BusquedaFechaTask extends Service<ObservableList<infoDebAut1>> {

        @Override
        protected Task<ObservableList<infoDebAut1>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    ObservableList<infoDebAut1> datosae = FXCollections.observableArrayList();
//                    System.out.println("FECHA BUSQ ES" + registros.get(0).getFechaPago());
                    if (clearBusqueda.get()) {

                        datosae.addAll(MovDebAut2Controller.registros);

                    } else {

                        for (Iterator<infoDebAut1> it = MovDebAut2Controller.registros.iterator(); it.hasNext();) {
                            final infoDebAut1 data = it.next();
                            Date fechaEnvio = null;
                            if (!data.getFechaPago().trim().isEmpty()) {
                                try {

                                    fechaEnvio = formatoFecha.parse(data.getFechaPago().substring(0, 10));
                                } catch (Exception e) {
                                    fechaEnvio = formatoFecha.parse(data.getFechaPago());
                                }

                                if ((fechaini.getSelectedDate().before(fechaEnvio) && fechafin.getSelectedDate().after(fechaEnvio)) || (fechaini.getSelectedDate().equals(fechaEnvio) || fechafin.getSelectedDate().equals(fechaEnvio))) {
                                    datosae.add(data);

                                }
                            }
                        }
                    }

                    if (datosae.isEmpty()) {
                        tablaDatos.setPlaceholder(new Label("No hay registros asociados"));
                        throw new Exception("No hay Datos");
                    } else {
                        return datosae;
                    }

                }
            };
        }
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
                            final ObservableList<infoDebAut1> TablaID = task.getValue();

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
                                                    final List<infoDebAut1> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoDebAut1> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(2);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(88);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);

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
                    Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public static String getTracePaginacion() {
        return tracePaginacion;
    }

    public static void setTracePaginacion(String tracePaginacion) {
        MovDebAut2Controller.tracePaginacion = tracePaginacion;
    }

    public String getCodigoConv() {
        return codigoConv;
    }

    public void setCodigoConv(String codigoConv) {
        MovDebAut2Controller.codigoConv = codigoConv;
    }

    public String getFechainiAux() {
        return MovDebAut2Controller.fechainiAux;
    }

    public void setFechainiAux(String fechainiAux) {
        MovDebAut2Controller.fechainiAux = fechainiAux;
    }

    public String getFechafinAux() {
        return MovDebAut2Controller.fechafinAux;
    }

    public void setFechafinAux(String fechafinAux) {
        MovDebAut2Controller.fechafinAux = fechafinAux;
    }

    public String getIndicadorRegistros() {
        return MovDebAut2Controller.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        MovDebAut2Controller.indicadorRegistros = indicadorRegistros;
    }

    public void mostrarMovDebAut(final List<infoDebAut1> registross, final String indMasReg, final String totalReg, final int registrosPag, final String fechaini, final String fechafin, final String codigoconv, final String tracePaginacion, int origen) {
        if (origen == 1) {
            this.registros.clear();
            this.registros.addAll(registross);
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

            //MovDebAut2Controller.registros.clear();
            // MovDebAut2Controller.registros.addAll(registross);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/MovDebAut2.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Button botoncontinuarOp = (Button) root.lookup("#detalleOP");
                    final Button indMasRegistros = (Button) root.lookup("#indMasReg");
                    final Button volverIni = (Button) root.lookup("#volverOP");
                    final Button buscarFechas = (Button) root.lookup("#buscarFechas");
                    final Button limpiar = (Button) root.lookup("#limpiarOP");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<infoDebAut1> tablaDatos = (TableView<infoDebAut1>) root.lookup("#tablaDatos");

                    final DatePicker fechaini = (DatePicker) root.lookup("#fechaini");
                    final DatePicker fechafin = (DatePicker) root.lookup("#fechafin");

                    fechaini.setDateFormat(formatoFechaSalida);
                    fechafin.setDateFormat(formatoFechaSalida);

                    limpiar.setTooltip(new Tooltip("Borrar los filtros de busqueda"));
                    buscarFechas.setTooltip(new Tooltip("Buscar registros por fecha"));
                    /// codigo para inyectar los registros                   
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
                        Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    /**
                     * configuracion de la paginacion
                     */
//                    registros.clear();
//                    registros.addAll(registross);
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
                                        final List<infoDebAut1> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoDebAut1> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
//

                    pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                            currentpageindex = newValue.intValue();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tablaDatos.getItems().setAll(registros.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= registros.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : registros.size())));
                                }
                            });

                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    root.getChildren().add(pagination);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutX(2);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(88);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception e) {
//                    e.printStackTrace();
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });

    }

    public class GetRegistrosTask extends Service<ObservableList<infoDebAut1>> {

        @Override
        protected Task<ObservableList<infoDebAut1>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // setNumpagina(getNumpagina() + 1);
                    final Cliente cliente = Cliente.getCliente();
                    String Respuesta = new String();

                    final StringBuilder tramaMovDeb = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    //850,063,connid,codtransaccion,tracePaginacion,identificacion,fechaini(DDMMYYYY),fechafin(DDMMYYYY),
                    //cantReg,cantREgPaginacion,codConv,INDICADOR(empresas-personas),claveHardware,~
                    tramaMovDeb.append("850,063,");
                    tramaMovDeb.append(cliente.getRefid());
                    tramaMovDeb.append(",");
                    tramaMovDeb.append(AtlasConstantes.COD_CONS_MOV_DEB);
                    tramaMovDeb.append(",");
                    tramaMovDeb.append("S".equalsIgnoreCase(getIndicadorRegistros()) ? getTracePaginacion() : ""); // tracePaginacion
                    tramaMovDeb.append(",");
                    tramaMovDeb.append(cliente.getId_cliente());
                    tramaMovDeb.append(",");
                    tramaMovDeb.append(getFechainiAux());
                    tramaMovDeb.append(",");
                    tramaMovDeb.append(getFechafinAux());
                    tramaMovDeb.append(",");
                    tramaMovDeb.append(totalREg.get()); // registrosCanal
                    tramaMovDeb.append(",");
                    tramaMovDeb.append(acumREg.addAndGet(numpagina.get())); //registrosPaginacion
                    tramaMovDeb.append(",");
                    tramaMovDeb.append(getCodigoConv());
                    tramaMovDeb.append(",");
                    final AtlasAcceso Atlasacceso = new AtlasAcceso();
                    String rnv = "";
                    if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                        rnv = "E";
                    } else {

                        rnv = "P";
                    }
                    tramaMovDeb.append(rnv);//INDICADOR(empresas-personas)
                    tramaMovDeb.append(",");
                    tramaMovDeb.append(cliente.getContraseña());
                    tramaMovDeb.append(",");
                    tramaMovDeb.append(cliente.getTokenOauth());
                    tramaMovDeb.append(",~");

//                    System.out.println("TRAMA mov Deb2 :" + tramaMovDeb);
                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ movimientos DebitoAut = " + "##" + docs.obtenerHoraActual());
                        // Respuesta = "850,063,000,TRANSACCION EXITOSA,S,000063,101611542587,63149          %CRAZY -TÓWN &(COMPAÑÍA)#3.2.                                                    %20171026194319%20171026%78809                              %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%3%78809                              %                                                                                %Pago programado               %4343.00         %434300.00       %Corriente                     %40610115007         %04%04%EXITOSO                       %                                                                                %406396      %10074 %BANCOLOMBIA                             ;63149          %CRAZY -TÓWN &(COMPAÑÍA)#3.2.                                                    %20171026190852%20171026%89898                              %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%3%89898                              %                                                                                %Pago programado               %7878.00         %787800.00       %Corriente                     %40610115007         %00%00%EXITOSO                       %                                                                                %406385      %10074 %BANCOLOMBIA                             ;63036          %RECA-REF FIJA 1(FINACLE 1)                                                      %20171026185447%20171027%AR12345665432123456765432          %DIRECCIÓN#1                                       %                                   %                                                  %                                   %                                                  %1%AR12345665432123456765432          %                                                                                %Pago programado               %32300.00        %3230000.00      %Corriente                     %40610115007         %00%00%RECHAZADO                     %                                                                                %406375      %10074 %BANCOLOMBIA                             ;63149          %CRAZY -TÓWN &(COMPAÑÍA)#3.2.                                                    %20171026184922%20171026%67603                              %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%4                                  %NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%3%67603                              %                                                                                %Pago programado               %1000.00         %100000.00       %Corriente                     %40610115007         %00%00%EXITOSO                       %                                                                                %406369      %10074 %BANCOLOMBIA                             ,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMovDeb.toString());
//                        System.out.println("RESPUESTA TRAMA DEBAUT2:" + Respuesta);

                        // Respuesta="850,006,000,TRANSACCIÓN EXITOSA,000000000100000,000000000000000,000000001500000,000000000000000,-35000000,000001500000000,000000000000000,000000001500000,000000000000000,,               ,,000000000000000,11151215,00,0000000000,000000000000000,0,0000000000000000000,,0,,~";
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {
                        Logger.getLogger(MovDebAut2Controller.class.getName()).log(Level.SEVERE, null, ex);

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMovDeb.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + docs.obtenerHoraActual());

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

                        setIndicadorRegistros(indMasREg);
                        setTracePaginacion(Respuesta.split(",")[6]);
                        totalREg.set(Integer.parseInt(numReg));
                        numpagina.set(Respuesta.split(",")[7].split(";").length);

                        String[] LMov = Respuesta.split(",")[7].split(";");

                        for (int i = 0; i < LMov.length; i++) {

                            String[] dato = LMov[i].split("%");
                            String fechaPago = "";
                            String valorPagado = "";
                            String valorFactura = "";

                            try {
                                fechaPago = formatoFechaPago.format(formatoFechaPagoO.parse(dato[2].trim()));

                            } catch (Exception e) {
                                fechaPago = dato[2].trim();
                            }

                            try {
                                valorPagado = "$ " + formatonum.format(Double.parseDouble(dato[15].trim().split("\\.")[0])).replace(".", ",") + "." + dato[15].trim().split("\\.")[1];
                            } catch (Exception e) {
                                valorPagado = "$ " + dato[15];
                            }

                            try {
                                valorFactura = "$ " + formatonum.format(Double.parseDouble(dato[14].trim().split("\\.")[0])).replace(".", ",") + "." + dato[14].trim().split("\\.")[1];
                            } catch (Exception e) {
                                valorFactura = "$ " + dato[14];
                            }

                            infoDebAut1 mov2 = new infoDebAut1(
                                    dato[0].trim(),
                                    dato[1].trim(),
                                    fechaPago,
                                    dato[3].trim(),
                                    dato[4].trim(),
                                    dato[5].trim(),
                                    dato[6].trim(),
                                    dato[7].trim(),
                                    dato[8].trim(),
                                    dato[9].trim(),
                                    dato[10].trim(),
                                    dato[11].trim(),
                                    dato[12].trim(),
                                    dato[13].trim(),
                                    valorFactura,
                                    valorPagado,
                                    // dato[15].trim(),
                                    dato[16].trim(),
                                    dato[17].trim(),
                                    dato[18].trim(),
                                    dato[19].trim(),
                                    dato[20].trim(),
                                    dato[21].trim(),
                                    dato[22].trim(),
                                    dato[23].trim(),
                                    dato[24].trim());

                            registros.add(mov2);

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

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
