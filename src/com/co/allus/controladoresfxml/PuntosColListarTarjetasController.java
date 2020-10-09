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
import com.co.allus.modelo.puntoscol.MapeoRespPCO;
import com.co.allus.modelo.puntoscol.infoTablaListarMovimientos;
import com.co.allus.modelo.puntoscol.modeloListarTarjeta;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javafx.geometry.Insets;
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
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author roberto.ceballos
 */
public class PuntosColListarTarjetasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar_op;
    @FXML
    private Button indMasReg;
    @FXML
    private TableColumn<modeloListarTarjeta, String> desc_tarjeta;
    @FXML
    private TableColumn<modeloListarTarjeta, String> franquicia_tarjeta;
    @FXML
    private HBox menu_progreso;
    @FXML
    private TableColumn<modeloListarTarjeta, String> numero_tarjeta;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private TableView<modeloListarTarjeta> tablaDatos;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private transient Service<Void> serviceMovPco = ContinuarOP();
    public static AtomicInteger numpagina = new AtomicInteger(0);
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechamov = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechamov2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private transient final DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static ObservableList<modeloListarTarjeta> registros = FXCollections.observableArrayList();
    public static String indicadorRegistros = "N";
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private Service<ObservableList<modeloListarTarjeta>> task = new PuntosColListarTarjetasController.GetTarjetasTask();
    public static Calendar fechalimite = Calendar.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert desc_tarjeta != null : "fx:id=\"desc_tarjeta\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert franquicia_tarjeta != null : "fx:id=\"franquicia_tarjeta\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert numero_tarjeta != null : "fx:id=\"numero_tarjeta\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'PuntosColListarTarjetas.fxml'.";

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);
        final Calendar instance = Calendar.getInstance();

        fechafin.setSelectedDate(instance.getTime());

        instance.add(Calendar.DAY_OF_MONTH, -60);

        fechaini.setSelectedDate(instance.getTime());

        setFechalimite(instance);

        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {
                    if ((fechaini.getSelectedDate() == null) || (fechafin.getSelectedDate() == null)) {
                        continuar_op.setDisable(true);
                    } else {
                        continuar_op.setDisable(false);
                    }
                } else {
                    continuar_op.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });
        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        numero_tarjeta.setCellValueFactory(new PropertyValueFactory<modeloListarTarjeta, String>("numerotarjeta"));
        franquicia_tarjeta.setCellValueFactory(new PropertyValueFactory<modeloListarTarjeta, String>("franquiciatarjeta"));
        desc_tarjeta.setCellValueFactory(new PropertyValueFactory<modeloListarTarjeta, String>("desctarjeta"));


    }

    public static Calendar getFechalimite() {
        return fechalimite;
    }

    public static void setFechalimite(Calendar fechalimite) {
        PuntosColAjustePuntosController.fechalimite = fechalimite;
    }

    public String getIndicadorRegistros() {
        return PuntosColListarTarjetasController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        PuntosColListarTarjetasController.indicadorRegistros = indicadorRegistros;
    }

    public void mostrarListarTarjetaPCO(final ObservableList<modeloListarTarjeta> registross, String indMasReg, final int registrosPag, final int origen) {
        if (origen == 1) {
            setIndicadorRegistros(indMasReg);
            numpagina.set(registrosPag);
            PuntosColListarTarjetasController.registros.clear();
            PuntosColListarTarjetasController.registros.addAll(registross);
        }


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PuntosColListarTarjetas.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Button botonMasReg = (Button) root.lookup("#indMasReg");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<modeloListarTarjeta> tablaData = (TableView<modeloListarTarjeta>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
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

                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.HAND);
                                    cancelar.setEffect(shadow);
                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.DEFAULT);
                                    cancelar.setEffect(null);

                                }
                            });

                    botonMasReg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.HAND);
                                    cancelar.setEffect(shadow);
                                }
                            });

                    botonMasReg.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.DEFAULT);
                                    cancelar.setEffect(null);

                                }
                            });

                    botoncontinuarOp.setDisable(true);
                    label_menu.setVisible(false);

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }


                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    if (origen == 1) {

                        // Use binding to be notified whenever the data source chagnes
                        // task = new GetTarjetasTask();
                        p.progressProperty().bind(task.progressProperty());
                        veil.visibleProperty().bind(task.runningProperty());
                        p.visibleProperty().bind(task.runningProperty());
                        tablaData.itemsProperty().bind(task.valueProperty());
                        task.start();

                        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {

                                tablaData.itemsProperty().unbind();
//                                System.out.println("TERMINO TAREA");

                                if ("S".equals(getIndicadorRegistros())) {
                                    botonMasReg.setDisable(false);
                                } else {
                                    botonMasReg.setDisable(true);
                                }

                                /**
                                 * configuracion de la paginacion
                                 */
                                // final ObservableList<modeloListarTarjeta> registros = task.getValue();
                                final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;

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
                                            int displace = registros.size() % rowsPerPage();
                                            if (displace >= 0) {
                                                lastIndex = registros.size() / rowsPerPage();
                                            } else {
                                                lastIndex = registros.size() / rowsPerPage() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<modeloListarTarjeta> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaData.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<modeloListarTarjeta> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                root.getChildren().get(root.getChildren().size() - 1).setLayoutX(3);
                                root.getChildren().get(root.getChildren().size() - 1).setLayoutY(75);
                                pagination.setVisible(true);
                                Atlas.vista.show();
                            }
                        });

                        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
//                                System.out.println("ERROR EN LA CONSULTA");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                                        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
                                        final TreeView<String> arbolPuntosCol = (TreeView<String>) pane.lookup("#arbolPuntosCol");
                                        if (arbolPuntosCol != null) {
                                            arbolPuntosCol.setDisable(false);
                                        }

                                        arbolPuntosCol.getSelectionModel().clearSelection();
                                        try {
                                            pane.getChildren().remove(3);
                                        } catch (Exception e) {
                                            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                        }
                                        Atlas.vista.show();
                                    }
                                });


                            }
                        });

                        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                            }
                        });

                    } else {

                        if ("S".equals(getIndicadorRegistros())) {
                            botonMasReg.setDisable(false);
                        } else {
                            botonMasReg.setDisable(true);
                        }

                        final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;

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
                                    int displace = registros.size() % rowsPerPage();
                                    if (displace >= 0) {
                                        lastIndex = registros.size() / rowsPerPage();
                                    } else {
                                        lastIndex = registros.size() / rowsPerPage() - 1;
                                    }
                                    int page = pageIndex * itemsPerPage();

                                    for (int i = page; i < page + itemsPerPage(); i++) {

                                        if (lastIndex == pageIndex) {
                                            final List<modeloListarTarjeta> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                            tablaData.setItems(FXCollections.observableArrayList(subList));
                                        } else {
                                            final List<modeloListarTarjeta> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                        root.getChildren().get(root.getChildren().size() - 1).setLayoutX(3);
                        root.getChildren().get(root.getChildren().size() - 1).setLayoutY(75);
                        pagination.setVisible(true);
                        Atlas.vista.show();

                    }



                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

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
                            final ObservableList<modeloListarTarjeta> TablaID = task.getValue();


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
                                                    final List<modeloListarTarjeta> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<modeloListarTarjeta> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(3);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(75);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println(t.getSource().getException().getMessage());
                            numpagina.addAndGet(-1);
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
                            numpagina.addAndGet(-1);
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {
                    Logger.getLogger(PuntosColListarTarjetasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public class GetTarjetasTask extends Service<ObservableList<modeloListarTarjeta>> {

        @Override
        protected Task<ObservableList<modeloListarTarjeta>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarTDC = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramaListarTDC.append("850,072,");
                    tramaListarTDC.append(datosCliente.getRefid());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(AtlasConstantes.COD_LISTAR_TDC_PCO);
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getId_cliente());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(numpagina.addAndGet(1)); //numero pagina
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getContraseña());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getTokenOauth());
                    tramaListarTDC.append(",~");

//                    System.out.println("trama listar pco : " + tramaListarTDC.toString());

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,"
//                                + "071,"
//                                + "000,"
//                                + "TRANSACCION EXITOSA,"
//                                + "S,"
//                                + "354852685895589%"
//                                + "AMEX%"
//                                + "American Express Blue;"
//                                + "452685785459825962%"
//                                + "Master%"
//                                + "Master Card Clasica;"
//                                + "5698521458525932%"
//                                + "Visa%"
//                                + "Visa Oro;"
//                                + ",~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarTDC.toString());

//                        System.out.println("respuesta Lista : " + Respuesta);


                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " +StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+ "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarTDC.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    failed();


                                }
                            });

                        }

                    }



                    if ("000".equals(Respuesta.split(",")[2])) {


                        setIndicadorRegistros(Respuesta.split(",")[4]);



                        String[] Ltarjetas = Respuesta.split(",")[5].split(";");

                        for (int i = 0; i < Ltarjetas.length; i++) {
                            String[] datos = Ltarjetas[i].split("%");
                            String tarjetaPIN = datos[0].substring(0, 6) + StringUtilities.rellenarDato(" ", datos[0].length() - 10, "*") + datos[0].substring(datos[0].length() - 4, datos[0].length());
                            final modeloListarTarjeta tarjeta = new modeloListarTarjeta(tarjetaPIN, datos[1], datos[2], datos[0]);
                            registros.add(tarjeta);
                        }






                    } else {

                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                            }
                        });
                        throw new Exception("ERROR");

                    }

                    return registros;

                }
            };
        }
    }

    @FXML
    void cancelarOP(ActionEvent event) {
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

        final TreeView<String> arbol_servad = (TreeView<String>) pane.lookup("#arbol_servadicionales");
        if (arbol_servad != null) {
            arbol_servad.setDisable(false);
        }

        final TreeView<String> arbol_infotdc = (TreeView<String>) pane.lookup("#arbol_infotdc");
        if (arbol_infotdc != null) {
            arbol_infotdc.setDisable(false);
        }

        final TreeView<String> arbol_consultas = (TreeView<String>) pane.lookup("#arbol_consultas");
        if (arbol_consultas != null) {
            arbol_consultas.setDisable(false);
        }

        final TreeView<String> arbol_movmientos = (TreeView<String>) pane.lookup("#arbol_movimientos");
        if (arbol_movmientos != null) {
            arbol_movmientos.setDisable(false);
        }

        final TreeView<String> arbol_contrabloqueos = (TreeView<String>) pane.lookup("#arbol_contrabloqueos");
        if (arbol_contrabloqueos != null) {
            arbol_contrabloqueos.setDisable(false);
        }

        final TreeView<String> arbol_infoseguridad = (TreeView<String>) pane.lookup("#arbol_infoseguridad");
        if (arbol_infoseguridad != null) {
            arbol_infoseguridad.setDisable(false);
        }

        final TreeView<String> arbol_puntosCol = (TreeView<String>) pane.lookup("#arbolPuntosCol");
        if (arbol_puntosCol != null) {
            arbol_puntosCol.setDisable(false);
        }



        arbol_puntosCol.getSelectionModel().clearSelection();
        PuntosColListarTarjetasController.registros.clear();
        numpagina.set(0);
        try {
            pane.getChildren().remove(3);
        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        Atlas.vista.show();
    }

    public Service<Void> ContinuarOP() {
        serviceMovPco = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();
                        final StringBuilder tramamovtcpco = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final modeloListarTarjeta selectedItem = tablaDatos.getSelectionModel().getSelectedItem();


                        // 850,063,connid,codtransaccion,tracePaginacion,identificacion,fechaini(DDMMYYYY),fechafin(DDMMYYYY),cantReg,cantREgPaginacion,codConv,INDICADOR(empresas-personas),claveHardware,~
//                        850,063,01N5LN2U38CEP2REF0LH9B5AES03C855,0046,,1036929363,01112017,02112017,,,121213,,77EA68BDA4AFC567,~
                        tramamovtcpco.append("850,073,");
                        tramamovtcpco.append(cliente.getRefid());
                        tramamovtcpco.append(",");
                        tramamovtcpco.append(AtlasConstantes.COD_LISTAR_MOV_TDC_PCO);
                        tramamovtcpco.append(",");
                        tramamovtcpco.append(cliente.getId_cliente());
                        tramamovtcpco.append(",");
                        tramamovtcpco.append(selectedItem.getNumtdcori());
                        tramamovtcpco.append(",");
                        tramamovtcpco.append(formatoFechamov.format(fechaini.getSelectedDate()));
                        tramamovtcpco.append(",");
                        tramamovtcpco.append(formatoFechamov.format(fechafin.getSelectedDate()));
                        tramamovtcpco.append(",");
                        tramamovtcpco.append("1"); // cantReg
                        tramamovtcpco.append(",");
                        tramamovtcpco.append(cliente.getContraseña());
                        tramamovtcpco.append(",");
                        tramamovtcpco.append(cliente.getTokenOauth());
                        tramamovtcpco.append(",~");





//                        System.out.println("TRAMA listar mov  :" + tramamovtcpco);

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ MOVIMIENTOS tc pco = " + "##" + gestorDoc.obtenerHoraActual());

                            //850,072,000,TRANSACCION EXITOSA,indMasReg,%FECHA%HORA%ID TRX*%TIPO DOCUMENTO%DOCUMENTO%VALOR TRX PESOS%VALOR TRX DÓLARES%COD TRX%DESCRIP TRX%COD RESPUESTA %PUNTOS%EXTRA PUNTOS%TIPO DOC BENEFICI%DOC BENEFICIARIO%COD UNICO%DESCRIPC COD UNICO%MCC%DESCRIPCIÓN MCC%LOGO;.....,~
                            // Thread.sleep(2000);
//                            Respuesta = "850,"
//                                    + "072,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "S,"
//                                    + "31052018%120000%COMPRA*%1%1020416841%5000%0%588%COMPRA ARTURO CALLE%056%699%123%1%10665889654%563%COMPRA UNICA%2442%ARTURO CALLE%673,~";

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramamovtcpco.toString());

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg mov pco= " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramamovtcpco.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });
                            }
                        }

                        //  try {

                        if ("000".equals(Respuesta.split(",")[2])) {

                            try {


                                final String indMasREg = Respuesta.split(",")[4];



                                final List<infoTablaListarMovimientos> movtdcpco = new ArrayList<infoTablaListarMovimientos>();

                                String[] LMov = Respuesta.split(",")[5].split(";");



                                for (int i = 0; i < LMov.length; i++) {


                                    String[] datos = LMov[i].split("%");
                                    String fecha = "";
                                    String valordolares = "";
                                    String valorpesos = "";


                                    try {
                                        fecha = formatoFechamov2.format(formatoFechamov.parse(datos[0].trim()));

                                    } catch (Exception e) {
                                        fecha = datos[0].trim();
                                    }
//                                
                                    try {
                                        valorpesos = "$ " + formatonum.format(Double.parseDouble(datos[5].trim().split("\\.")[0])).replace(".", ",") + "." + datos[5].trim().split("\\.")[1];
                                    } catch (Exception e) {
                                        valorpesos = "$ " + datos[5];
                                    }
//                                
                                    try {
                                        valordolares = "$ " + formatonum.format(Double.parseDouble(datos[6].trim().split("\\.")[0])).replace(".", ",") + "." + datos[6].trim().split("\\.")[1];
                                    } catch (Exception e) {
                                        valordolares = "$ " + datos[6];
                                    }
                                    // System.out.println("Datos 25 es" + datos[24].trim());  


//    private SimpleStringProperty fecha_trx;
//    private SimpleStringProperty cod_trx;
//    private SimpleStringProperty descripcion_trx;
//    private SimpleStringProperty valor_trx_pesos;
//    private SimpleStringProperty puntos;
//    private SimpleStringProperty extra_puntos;
//    private SimpleStringProperty respuesta;
//    private SimpleStringProperty hora_trx;
//    private SimpleStringProperty tipo_doc;
//    private SimpleStringProperty documento;
//    private SimpleStringProperty valor_trx_dolares;
//    private SimpleStringProperty tipo_doc_benef;
//    private SimpleStringProperty doc_benef;
//    private SimpleStringProperty cod_unico;
//    private SimpleStringProperty descod_unico;
//    private SimpleStringProperty mcc;
//    private SimpleStringProperty desc_mcc;
//    private SimpleStringProperty logo;

//%FECHA%
//HORA%
//ID TRX*%
//TIPO DOCUMENTO%
//DOCUMENTO%
//VALOR TRX PESOS%
//VALOR TRX DÓLARES%
//COD TRX%
//DESCRIP TRX%
//COD RESPUESTA%
//PUNTOS%
//EXTRA PUNTOS%
//TIPO DOC BENEFICI%
//DOC BENEFICIARIO%
//COD UNICO%
//DESCRIPC COD UNICO%
//MCC%
//DESCRIPCIÓN MCC%
//LOGO                                


                                    infoTablaListarMovimientos mov = new infoTablaListarMovimientos(
                                            fecha,
                                            datos[7].trim(),
                                            datos[8].trim(),
                                            valorpesos,
                                            datos[10].trim(),
                                            datos[11].trim(),
                                            MapeoRespPCO.mapeoRespuestas.get(datos[9].trim().toUpperCase()),
                                            datos[1].trim(),
                                            datos[3].trim(),
                                            datos[4].trim(),
                                            valordolares,
                                            datos[12].trim(),
                                            datos[13].trim(),
                                            datos[14].trim(),
                                            datos[15].trim(),
                                            datos[16].trim(),
                                            datos[17].trim(),
                                            datos[18].trim(),
                                            datos[2].trim());


                                    movtdcpco.add(mov);


                                }



                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        PuntosColListarMovimientosController controller = new PuntosColListarMovimientosController();
                                        controller.mostrasMovmientosTDCpco(
                                                movtdcpco,
                                                selectedItem,
                                                formatoFecha.format(fechaini.getSelectedDate()),
                                                formatoFecha.format(fechafin.getSelectedDate()),
                                                1,
                                                indMasREg,
                                                1);
                                    }
                                });

                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    continuar_op.setDisable(false);
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);

                                }
                            });

                        }



                        return null;
                    }
                };
            }
        };



        return serviceMovPco;

    }

    @FXML
    void continuarOP(ActionEvent event) {
        if (serviceMovPco.isRunning()) {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceMovPco.progressProperty());
            serviceMovPco.reset();
            serviceMovPco.start();
        } else {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceMovPco.progressProperty());
            serviceMovPco.reset();
            serviceMovPco.start();
        }

        serviceMovPco.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Movimientos PCO TDC" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);

            }
        });


        serviceMovPco.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Movimientos PCO TDC" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceMovPco.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                Logger.getLogger(PuntosColListarTarjetasController.class.getName())
                        .log(Level.SEVERE,  t.getSource().getException().toString());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                continuar_op.setDisable(false);
            }
        });


    }

    private void analisisFecha() {
        if (fechaini.getSelectedDate() != null && fechafin.getSelectedDate() != null) {

            rangoFecha(fechaini.getSelectedDate(), fechafin.getSelectedDate());
        } else {
//            System.out.println("Entro aca");
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


//        Calendar calendarFiaux = Calendar.getInstance();
//        Calendar calendarFfaux = Calendar.getInstance();
//
//        calendarFiaux.setTime(calendarFi);
//        calendarFfaux.setTime(calendarFf);
//        final int diferenciaDias = gestorDoc.CalcularDifFechas(calendarFiaux, calendarFfaux);
//
//        System.out.println("diferencia dias " + diferenciaDias);
//        if ((diferenciaDias <= 60) && (diferenciaDias != -1)) {
//
//            continuar_op.setDisable(false);
//        } else {
//            continuar_op.setDisable(true);
//        }
//
//
//        return true;
        try {
//            final URL location = getClass().getResource("/com/co/allus/vistas/PuntosColAjustePuntos.fxml");
//            final FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(location);
//            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
//            final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());                  
//            final Button buscarFechas = (Button) root.lookup("#buscarFechas");


            Calendar calendarFiaux = Calendar.getInstance();
            Calendar calendarFfaux = Calendar.getInstance();

            calendarFiaux.setTime(calendarFi);
            calendarFfaux.setTime(calendarFf);
            final int diferenciaDias = gestorDoc.CalcularDifFechas(calendarFiaux, calendarFfaux);

//            System.out.println("diferencia dias " + diferenciaDias);
            //
//            System.out.println("fecha limite " + formatoFecha.format(getFechalimite().getTime()));
//            System.out.println("fecha fecha ini " + formatoFecha.format(fechaini.getSelectedDate().getTime()));

            if (getFechalimite().before(fechaini.getCalendarView().getCalendar()) || formatoFecha.format(getFechalimite().getTime()).equals(formatoFecha.format(fechaini.getSelectedDate().getTime()))) {
                continuar_op.setDisable(false);

            } else {
                continuar_op.setDisable(true);
                return true;
            }

            if ((diferenciaDias <= 60) && (diferenciaDias != -1)) {

                continuar_op.setDisable(false);
            } else {

                continuar_op.setDisable(true);
            }


            return true;
        } catch (Exception e) {
//           e.printStackTrace();
            return true;
        }


    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
