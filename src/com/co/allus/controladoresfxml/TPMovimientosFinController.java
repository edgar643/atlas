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
import com.co.allus.modelo.tpmovimientos.DatosMovimientosFin;
import com.co.allus.modelo.tpmovimientos.InfoTablaMovFin;
import com.co.allus.modelo.tpmovimientos.InfoTablaMovIni;
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
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
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
public class TPMovimientosFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button buscar_op;
    @FXML
    private TableColumn colDescComer;
    @FXML
    private TableColumn colDesctrx;
    @FXML
    private TableColumn colFechatrx;
    @FXML
    private TableColumn colValortrx;
    @FXML
    private Button limpiar_op;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private Button regresar_op;
    @FXML
    private TableView<InfoTablaMovFin> tabla_datos;
    @FXML
    private DatePicker tfFechaFin;
    @FXML
    private DatePicker tfFechaIni;
    @FXML
    private Button terminar_trx;
    @FXML
    private Label lblmensaje;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private Service<ObservableList<InfoTablaMovFin>> TaskdataTabla = new BusquedaFechaTask();
    private Service<ObservableList<InfoTablaMovFin>> task = new TPMovimientosFinController.GetMovmimientosTarjeta();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    public static List<InfoTablaMovFin> dataTabla = FXCollections.observableArrayList();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static AtomicBoolean clearBusqueda = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert lblmensaje != null : "fx:id=\"lblmensaje\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert buscar_op != null : "fx:id=\"buscar_op\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert colDescComer != null : "fx:id=\"colDescComer\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert colDesctrx != null : "fx:id=\"colDesctrx\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert colFechatrx != null : "fx:id=\"colFechatrx\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert colValortrx != null : "fx:id=\"colValortrx\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert limpiar_op != null : "fx:id=\"limpiar_op\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert regresar_op != null : "fx:id=\"regresar_op\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert tfFechaFin != null : "fx:id=\"tfFechaFin\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert tfFechaIni != null : "fx:id=\"tfFechaIni\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'TPMovimientosFin.fxml'.";

        this.location = url;
        this.resources = rb;
        lblmensaje.setVisible(false);

        colDescComer.setCellValueFactory(new PropertyValueFactory<InfoTablaMovFin, String>("colDescripcionComercio"));
        colDesctrx.setCellValueFactory(new PropertyValueFactory<InfoTablaMovFin, String>("colDescripcionTransaccion"));
        colFechatrx.setCellValueFactory(new PropertyValueFactory<InfoTablaMovFin, String>("colFechaTransaccion"));
        colValortrx.setCellValueFactory(new PropertyValueFactory<InfoTablaMovFin, String>("colValorTransaccion"));
//        tabla_datos.setPlaceholder(new Label(""));
        tabla_datos.setPlaceholder(new Label("No hay registros asociados"));

    }

    @FXML
    void limpiar_op(ActionEvent event) {
        try {
            tfFechaFin.setSelectedDate(null);
            tfFechaIni.setSelectedDate(null);
            clearBusqueda.set(true);
            ConsultarFechar();
            lblmensaje.setVisible(false);
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }
    }

    @FXML
    void terminar_trx(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                final Label label_menu = (Label) pane.lookup("#label_saldos");

                final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                if (arboltdcPrepago != null) {
                    arboltdcPrepago.setDisable(false);
                }

                arboltdcPrepago.getSelectionModel().clearSelection();
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }



            }
        });
    }

    @FXML
    void regresar_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                    final TPMovimientosIniController controller = new TPMovimientosIniController();
                    controller.mostrarMovimientoPrepago();
                } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            final Parent frameGnral = Atlas.vista.getScene().getRoot();
                            final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                            final Label label_menu = (Label) pane.lookup("#label_saldos");

                            final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                            if (arboltdcPrepago != null) {
                                arboltdcPrepago.setDisable(false);
                            }

                            arboltdcPrepago.getSelectionModel().clearSelection();
                            label_menu.setVisible(true);

                            try {
                                pane.getChildren().remove(3);
                            } catch (Exception ex) {
                                docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                            }



                        }
                    });

                }
            }
        });
    }

    @FXML
    void buscar_op(ActionEvent event) {
        try {


            if (esSeleccionado(tfFechaIni.getCalendarView().getCalendar()) && esSeleccionado(tfFechaFin.getCalendarView().getCalendar())) {
                consumirDatos();
                lblmensaje.setVisible(false);
            } else {
                lblmensaje.setVisible(true);

            }
        } catch (Exception ex) {
            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
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


//            System.out.println("DIFERENCIA ENTRE FECHAS: " + diferenciaDias);
            if (diferenciaDias > 90) {
                tabla_datos.setPlaceholder(new Label("Rango mayor a 90 días"));
                return false;
            } else {
                tabla_datos.setPlaceholder(new Label("No hay registros asociados"));
            }


            if (diferenciaDias == -1) {
                tabla_datos.setPlaceholder(new Label("El rango de búsqueda no es válido"));
                ObservableList<InfoTablaMovFin> dataempty = FXCollections.emptyObservableList();
                tabla_datos.setItems(dataempty);
                pagination.setVisible(false);

//                System.out.println("rango no es valido");

                return false;
            } else {
                tabla_datos.setPlaceholder(new Label("No hay registros asociados"));
            }




            if ((fini1 <= hoy1) && (fini1 <= ffin1) && (ffin1 <= hoy1)) {

                return true;
            } else {

                return false;

            }


        } catch (Exception e) {
            return false;
        }
    }

    public class BusquedaFechaTask extends Service<ObservableList<InfoTablaMovFin>> {

        @Override
        protected Task<ObservableList<InfoTablaMovFin>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    ObservableList<InfoTablaMovFin> datosae = FXCollections.observableArrayList();

                    if (clearBusqueda.get()) {

                        datosae.addAll(dataTabla);

                    } else {
                        for (Iterator<InfoTablaMovFin> it = dataTabla.iterator(); it.hasNext();) {
                            final InfoTablaMovFin data = it.next();
                            Date fechaEnvio = formatoFecha.parse(data.getColFechaTransaccion());
                            if ((tfFechaIni.getSelectedDate().before(fechaEnvio) && tfFechaFin.getSelectedDate().after(fechaEnvio)) || (tfFechaIni.getSelectedDate().equals(fechaEnvio) || tfFechaFin.getSelectedDate().equals(fechaEnvio))) {
                                datosae.add(data);
                            }
                        }
                    }

                    if (datosae.isEmpty()) {
                        tabla_datos.setPlaceholder(new Label("No hay registros asociados"));
                        throw new Exception("No hay Datos");
                    } else {
                        return datosae;
                    }

                }
            };
        }
    }

    private void consumirDatos() {
        if (esRangoValido(tfFechaIni.getSelectedDate(), tfFechaFin.getSelectedDate())) {
            clearBusqueda.set(false);
            ConsultarFechar();
        } else {
            tfFechaIni.setSelectedDate(tfFechaIni.getSelectedDate());
            tfFechaFin.setSelectedDate(tfFechaFin.getSelectedDate());
            final ObservableList<InfoTablaMovFin> dataempty = FXCollections.emptyObservableList();
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


                    limpiar_op.setDisable(true);

                    TaskdataTabla.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            limpiar_op.setDisable(false);
                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<InfoTablaMovFin> TablaBusFecha = TaskdataTabla.getValue();


                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaBusFecha.size() % rowsPerPageDate() == 0 ? TablaBusFecha.size() / rowsPerPageDate() : TablaBusFecha.size() / rowsPerPageDate() + 1;

                                pagination = new Pagination(numpag, 0);

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
                                                    final List<InfoTablaMovFin> subList = TablaBusFecha.subList(pageIndex * rowsPerPageDate(), pageIndex * rowsPerPageDate() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTablaMovFin> subList = TablaBusFecha.subList(pageIndex * rowsPerPageDate(), pageIndex * rowsPerPageDate() + rowsPerPageDate());
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
//                                System.out.println("tamaño del anchor busqueda ref" + AnchorPane.getChildren().size());
                                try {

                                    AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);

                                } catch (Exception e) {
                                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(4);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(85);

                            } catch (Exception e) {
                                docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                            }

                        }
                    });

                    TaskdataTabla.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            limpiar_op.setDisable(false);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    String message = t.getSource().getException().getMessage();
                                    ObservableList<InfoTablaMovFin> dataempty = FXCollections.emptyObservableList();
                                    tabla_datos.setItems(dataempty);
                                }
                            });
                        }
                    });

                    TaskdataTabla.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            limpiar_op.setDisable(false);
                            tabla_datos.setVisible(false);
                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                }

            }
        });

    }

    public void mostrarMovimientosTarjetaEmpresa(final DatosMovimientosFin datosTarjeta) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TPMovimientosFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button buscar_op = (Button) root.lookup("#buscar_op");
                    final Button regresar_op = (Button) root.lookup("#regresar_op");
                    final Button limpiar_op = (Button) root.lookup("#limpiar_op");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");

                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final Label lblmensaje = (Label) root.lookup("#lblmensaje");

                    final TableView<InfoTablaMovFin> tabla_datos = (TableView<InfoTablaMovFin>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final DatePicker tfFechaIni = (DatePicker) root.lookup("#tfFechaIni");
                    final DatePicker tfFechaFin = (DatePicker) root.lookup("#tfFechaFin");

                    tfFechaIni.setDateFormat(new SimpleDateFormat("yyyy/MM/dd"));
                    tfFechaFin.setDateFormat(new SimpleDateFormat("yyyy/MM/dd"));

                    // codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String nombreC = datosCliente.getNombreEmpresa();
                    final String info = nombreC + "\nNIT : " + datosCliente.getNitEmpresa() + "\n" + datosTarjeta.getNumTarjcf();
                    cliente.setText("");
                    cliente.setText(info);


                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);


                    final DropShadow shadow = new DropShadow();
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

                    terminar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminar_trx.setCursor(Cursor.HAND);
                                    terminar_trx.setEffect(shadow);
                                }
                            });

                    terminar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminar_trx.setCursor(Cursor.DEFAULT);
                                    terminar_trx.setEffect(null);

                                }
                            });



                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                    if (arbol_pagos != null) {
                        arbol_pagos.setDisable(true);
                    }

                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                    if (arbol_saldos != null) {
                        arbol_saldos.setDisable(true);
                    }

                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                    if (arbol_transf != null) {
                        arbol_transf.setDisable(true);
                    }

                    final TreeView<String> arbol_bloqueos = (TreeView<String>) pane.lookup("#arbolbloqueos");
                    if (arbol_bloqueos != null) {
                        arbol_bloqueos.setDisable(true);
                    }

                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servadd != null) {
                        arbol_servadd.setDisable(true);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);


                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tabla_datos.itemsProperty().bind(task.valueProperty());
                    task.reset();
                    task.start();

                    /**
                     * barra progreso
                     */
                    // Use binding to be notified whenever the data source chagnes
                    // task = new GetTarjetasTask();
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<InfoTablaMovFin> movini = task.getValue();

                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = movini.size() % rowsPerPage() == 0 ? movini.size() / rowsPerPage() : movini.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {
                                    //ObservableList<modelSaldoTarjeta> tarjetastemp = FXCollections.observableArrayList();
                                    //tarjetastemp.addAll(tarjetas);
                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = movini.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = movini.size() / rowsPerPage();
                                        } else {
                                            lastIndex = movini.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<InfoTablaMovFin> subList = movini.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<InfoTablaMovFin> subList = movini.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
//

                            pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                @Override
                                public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                    currentpageindex = newValue.intValue();
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            tabla_datos.getItems().setAll(movini.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= movini.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : movini.size())));
                                        }
                                    });
                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(4);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(85);

                            pagination.setVisible(true);
                            Atlas.vista.show();
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("ENTRO FAILED");
//                            System.out.println("ERROR EN LA CONSULTA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                    final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                                    pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
                                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                                    if (arbol_pagos != null) {
                                        arbol_pagos.setDisable(false);
                                    }

                                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                                    if (arbol_saldos != null) {
                                        arbol_saldos.setDisable(false);
                                    }

                                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                                    if (arbol_transf != null) {
                                        arbol_transf.setDisable(false);
                                    }

                                    final TreeView<String> arbol_bloqueos = (TreeView<String>) pane.lookup("#arbolbloqueos");
                                    if (arbol_bloqueos != null) {
                                        arbol_bloqueos.setDisable(false);
                                    }

                                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                                    if (arbol_servadd != null) {
                                        arbol_servadd.setDisable(false);
                                    }

                                    final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                                    if (arboltdcPrepago != null) {
                                        arboltdcPrepago.setDisable(false);
                                    }


                                    arboltdcPrepago.getSelectionModel().clearSelection();
                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception e) {
                                        docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                                    }
                                    Atlas.vista.show();
                                }
                            });


                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                        }
                    });



                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });

    }

    public class GetMovmimientosTarjeta extends Service<ObservableList<InfoTablaMovFin>> {

        @Override
        protected Task<ObservableList<InfoTablaMovFin>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {


                    String Respuesta = new String();
                    final Cliente datosCliente = Cliente.getCliente();
                    final StringBuilder tramamovimientosfin = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Cliente cliente = Cliente.getCliente();
                    //850,069,connid,7006,45129087,N,4594270098711362,CH,~
//                    System.out.println("ENTRO SERVICIO Estados");


                    tramamovimientosfin.append("850,069,");
                    tramamovimientosfin.append(datosCliente.getRefid());
                    tramamovimientosfin.append(",");
                    tramamovimientosfin.append(AtlasConstantes.COD_MOVIMIENTO_PREP);
                    tramamovimientosfin.append(",");
                    tramamovimientosfin.append(datosCliente.getNitEmpresa());
                    tramamovimientosfin.append(",");
                    tramamovimientosfin.append("3");
                    tramamovimientosfin.append(",");
                    tramamovimientosfin.append("E");
                    tramamovimientosfin.append(",");
                    tramamovimientosfin.append(DatosMovimientosFin.getDataMF().getNumTarj());
                    tramamovimientosfin.append(",");
                    tramamovimientosfin.append(datosCliente.getContraseña());
                    tramamovimientosfin.append(",~");
//                    System.out.println("TRAMA MOVMIENTOS:" + tramamovimientosfin);



                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ movmientos prepago= " + "##" + docs.obtenerHoraActual());

                        // Respuesta = "850,069,000,,N,0004745540216776226,039,20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##CARGO POR USO CAJERO NO PROPIO##CARGO POR USO CAJERO N        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170411##CARGA TARJETA PREPAGO AUTOMATI##CARGA TARJETA PREPAGO         ##+000000000500000.00;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170407##CARGA TARJETA PREPAGO AUTOMATI##CARGA TARJETA PREPAGO         ##+000000000500000.00;20170404##CARGA TARJETA PREPAGO AUTOMATI##CARGA TARJETA PREPAGO         ##+000000000500000.00;20170330##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170330##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170322##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170322##CARGO POR USO CAJERO NO PROPIO##CARGO POR USO CAJERO N        ##-000000000004087.00;20170322##CARGA TARJETA PREPAGO AUTOMATI##CARGA TARJETA PREPAGO         ##+000000000400000.00;20170312##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000032.46;20170312##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000109.79;20170312##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000040.96;20170312##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000128.27;20170312##COMISION AVANCE INTERNACIONAL ##COMISION AVANCE INTERN        ##-000000000004087.00;20170306##RETIRO CAJERO INTERNACIONAL   ##OF. C. NACIONA/OF. C.         ##-000000000027979.00;20170303##COMPRA INTERNACIONAL          ##MARSHALLS 90372               ##-000000000008114.00;20170303##COMPRA INTERNACIONAL          ##OPTUS PRE PAID                ##-000000000027448.00;20170303##COMPRA INTERNACIONAL          ##Uber BV                       ##-000000000010240.00,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramamovimientosfin.toString());
//                        System.out.println(" RESPUESTA TRAMA CONSULTA DIRECCIONES:" + Respuesta);
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ movmientos prepago= " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramamovimientosfin.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    failed();

                                }
                            });

                        }

                    }



                    if ("000".equals(Respuesta.split(",")[2])) {


                        String[] datosmovfin = Respuesta.split(",")[7].split(";");


                        dataTabla.clear();


                        for (int i = 0; i < datosmovfin.length; i++) {
                            String[] datos = datosmovfin[i].split("##");
                            final String ValorOr = datos[3].replace(".", "");
                            //final String ValorOr= Valorsm.replace("-", ""); 

                            final String Valor = ("$ " + formatonum.format(Double.parseDouble(ValorOr.substring(0, ValorOr.length() - 2))).replace(".", ",") + "." + ValorOr.substring(ValorOr.length() - 2));

                            String Fecha = (datos[0].substring(0, datos[0].length() - 4)) + "/" + (datos[0].substring(4, datos[0].length() - 2)) + "/" + (datos[0].substring(datos[0].length() - 2));
                            final InfoTablaMovFin data = new InfoTablaMovFin(
                                    Fecha,
                                    datos[1].trim().toLowerCase(),
                                    datos[2].trim().toLowerCase(),
                                    Valor);

                            dataTabla.add(data);

                        }



                    } else {


                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCIÓN" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                            }
                        });
                        throw new Exception("ERROR");

                    }

                    return dataTabla;

                }
            };
        }
    }

    public void mostrarMovimientoPrepagoFin(final List<InfoTablaMovFin> data, final DatosMovimientosFin numtarjsf) {
        this.dataTabla = data;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TPMovimientosFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button buscar_op = (Button) root.lookup("#buscar_op");
                    final Button regresar_op = (Button) root.lookup("#regresar_op");
                    final Button limpiar_op = (Button) root.lookup("#limpiar_op");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");

                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final Label lblmensaje = (Label) root.lookup("#lblmensaje");

                    final TableView<InfoTablaMovFin> tabla_datos = (TableView<InfoTablaMovFin>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final DatePicker tfFechaIni = (DatePicker) root.lookup("#tfFechaIni");
                    final DatePicker tfFechaFin = (DatePicker) root.lookup("#tfFechaFin");

                    tfFechaIni.setDateFormat(new SimpleDateFormat("yyyy/MM/dd"));
                    tfFechaFin.setDateFormat(new SimpleDateFormat("yyyy/MM/dd"));

                    // codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente() + "\n" + numtarjsf.getNumTarjcf();
                    cliente.setText("");
                    cliente.setText(info);


                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);


                    final DropShadow shadow = new DropShadow();
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

                    terminar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminar_trx.setCursor(Cursor.HAND);
                                    terminar_trx.setEffect(shadow);
                                }
                            });

                    terminar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminar_trx.setCursor(Cursor.DEFAULT);
                                    terminar_trx.setEffect(null);

                                }
                            });


                    label_menu.setVisible(false);

                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                    if (arbol_pagos != null) {
                        arbol_pagos.setDisable(true);
                    }

                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                    if (arbol_saldos != null) {
                        arbol_saldos.setDisable(true);
                    }

                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                    if (arbol_transf != null) {
                        arbol_transf.setDisable(true);
                    }

                    final TreeView<String> arbol_bloqueos = (TreeView<String>) pane.lookup("#arbolbloqueos");
                    if (arbol_bloqueos != null) {
                        arbol_bloqueos.setDisable(true);
                    }

                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servadd != null) {
                        arbol_servadd.setDisable(true);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);




                    tabla_datos.itemsProperty().unbind();
//                    System.out.println("TERMINO TAREA");
                    /**
                     * configuracion de la paginacion
                     */
                    final ObservableList<InfoTablaMovFin> dataMovFinal = FXCollections.observableArrayList();
                    dataMovFinal.addAll(dataTabla);

                    final int numpag = dataMovFinal.size() % rowsPerPage() == 0 ? dataMovFinal.size() / rowsPerPage() : dataMovFinal.size() / rowsPerPage() + 1;

                    pagination = new Pagination(numpag, 0);
                    pagination.setPageFactory(new Callback<Integer, Node>() {
                        @Override
                        public Node call(final Integer pageIndex) {
                            //ObservableList<modelSaldoTarjeta> tarjetastemp = FXCollections.observableArrayList();
                            //tarjetastemp.addAll(tarjetas);
                            if (pageIndex > numpag) {
                                return null;
                            } else {
                                int lastIndex = 0;
                                int displace = dataMovFinal.size() % rowsPerPage();
                                if (displace >= 0) {
                                    lastIndex = dataMovFinal.size() / rowsPerPage();
                                } else {
                                    lastIndex = dataMovFinal.size() / rowsPerPage() - 1;
                                }
                                int page = pageIndex * itemsPerPage();

                                for (int i = page; i < page + itemsPerPage(); i++) {

                                    if (lastIndex == pageIndex) {
                                        final List<InfoTablaMovFin> subList = dataMovFinal.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<InfoTablaMovFin> subList = dataMovFinal.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
//

                    pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                            currentpageindex = newValue.intValue();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tabla_datos.getItems().setAll(dataMovFinal.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= dataMovFinal.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : dataMovFinal.size())));
                                }
                            });
                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    root.getChildren().add(pagination);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutX(4);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(85);

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
        return 10;
    }

    public int rowsPerPageDate() {
        return 10;
    }
}
