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
import com.co.allus.modelo.girosnal.tablaInfoGnralGirosnal;
import com.co.allus.modelo.girosnal.mapeoEstadoGiro;
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
import javafx.beans.property.BooleanProperty;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
 * @author alexander.lopez.o
 */
public class GirosNalCancelacionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn seleccion;
    @FXML
    private TableColumn ColCelBenef;
    @FXML
    private TableColumn ColDocBenef;
    @FXML
    private TableColumn ColDocGirador;
    @FXML
    private TableColumn ColFechaGiro;
    @FXML
    private TableColumn ColNomGirador;
    @FXML
    private TableColumn ColCelGirador;
    @FXML
    private TableColumn ColValorGiro;
    @FXML
    private TableColumn ColContDiaGiro;
    @FXML
    private TableColumn ColEstadoGiro;
    @FXML
    private Button buscarFechas;
    @FXML
    private TableColumn colNomBenef;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private TextField consecutivoRef2;
    @FXML
    private Button indMasReg;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<tablaInfoGnralGirosnal> tablaDatos;
    @FXML
    private Button volverOP;
    @FXML
    private Button CancelarOP;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static final GestorDocumental gestorDoc = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static AtomicInteger numpagina = new AtomicInteger(0);
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static AtomicBoolean cancelarTareasGirosNal = new AtomicBoolean();
    public static ObservableList<tablaInfoGnralGirosnal> registros = FXCollections.observableArrayList();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaV = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    public static String indicadorRegistros = "N";
    private Service<ObservableList<tablaInfoGnralGirosnal>> task = new GirosNalCancelacionController.GetconveniosTask();
    public static Calendar fechalimite = Calendar.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert seleccion != null : "fx:id=\"ColCancelarG\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert ColCelBenef != null : "fx:id=\"ColCelBenef\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert ColDocBenef != null : "fx:id=\"ColDocBenef\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert ColDocGirador != null : "fx:id=\"ColDocGirador\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert ColFechaGiro != null : "fx:id=\"ColFechaGiro\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert ColNomGirador != null : "fx:id=\"ColNomGirador\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert ColValorGiro != null : "fx:id=\"ColValorGiro\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert ColEstadoGiro != null : "fx:id=\"ColEstadoGiro\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert buscarFechas != null : "fx:id=\"buscarFechas\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert colNomBenef != null : "fx:id=\"colNomBenef\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert volverOP != null : "fx:id=\"volverOP\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert CancelarOP != null : "fx:id=\"CancelarOP\" was not injected: check your FXML file 'GirosNalCancelacion.fxml'.";
        assert ColContDiaGiro != null : "fx:id=\"ColContDiaGiro\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert consecutivoRef2 != null : "fx:id=\"consecutivoRef2\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColCelGirador != null : "fx:id=\"ColCelGirador\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";

        this.resources = rb;
        this.location = url;

        indMasReg.setDisable(true);
//        buscarFechas.setDisable(true);

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaDatos.setEditable(true);
        tablaDatos.setPlaceholder(new Label("No se ha realizado ninguna consulta"));

        seleccion.setCellValueFactory(new PropertyValueFactory("seleccion"));
        ColFechaGiro.setCellValueFactory(new PropertyValueFactory("colFechaVta"));
        ColValorGiro.setCellValueFactory(new PropertyValueFactory("colValorGiro"));
        ColEstadoGiro.setCellValueFactory(new PropertyValueFactory("colEstadoGiro"));
        ColDocGirador.setCellValueFactory(new PropertyValueFactory("colDocGirador"));
        ColNomGirador.setCellValueFactory(new PropertyValueFactory("colNomGirador"));
        ColDocBenef.setCellValueFactory(new PropertyValueFactory("colDocBenef"));
        colNomBenef.setCellValueFactory(new PropertyValueFactory("colNomBenef"));
        ColCelBenef.setCellValueFactory(new PropertyValueFactory("colCelBenef"));
        ColContDiaGiro.setCellValueFactory(new PropertyValueFactory("colContDiaGiro"));
        ColCelGirador.setCellValueFactory(new PropertyValueFactory("colCelGirador"));           //5

        seleccion.setCellFactory(new Callback<TableColumn<tablaInfoGnralGirosnal, Boolean>, TableCell<tablaInfoGnralGirosnal, Boolean>>() {
            @Override
            public TableCell<tablaInfoGnralGirosnal, Boolean> call(TableColumn<tablaInfoGnralGirosnal, Boolean> p) {
                return new CheckBoxTableCell<tablaInfoGnralGirosnal, Boolean>();
            }
        });

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);

        final Calendar instance = Calendar.getInstance();
        fechafin.setSelectedDate(instance.getTime());
        instance.add(Calendar.DAY_OF_MONTH, -90);
        fechaini.setSelectedDate(instance.getTime());
        setFechalimite(instance);

        consecutivoRef2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    buscarFechas.setDisable(true);
                } else if (isnumber(newValue)) {
                    buscarFechas.setDisable(false);
                } else {
                    buscarFechas.setDisable(true);
                }
            }
        });

        numpagina.set(0);
        cancelartarea.set(false);
        setIndicadorRegistros("N");
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
                            final ObservableList<tablaInfoGnralGirosnal> TablaID = task.getValue();

                            if ("]".equals(getIndicadorRegistros())) {
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
                                                    final List<tablaInfoGnralGirosnal> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<tablaInfoGnralGirosnal> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
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
                            numpagina.addAndGet(-1);
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            numpagina.addAndGet(-1);
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });
                } catch (Exception ex) {
                    Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void buscarFechas(ActionEvent event) {
//        if (!getIndicadorRegistros().equals("]")) {

        GirosNalCancelacionController.registros.clear();
        numpagina.set(0);

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
                            // final ObservableList<tablaInfoGnralGirosnal> registros = task.getValue();

                            if ("]".equals(getIndicadorRegistros())) {
                                indMasReg.setDisable(false);
                            } else {
                                indMasReg.setDisable(true);
                            }

                            try {
                                tablaInfoGnralGirosnal get = registros.get(0);
                                final Cliente datosCliente = Cliente.getCliente();
                                final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                                if (GiroNalInitController.isGirador.get()) {
                                    String info = "";

                                    info = get.colnombreClienteProperty().getValue() + "\nC.C: " + datosCliente.getId_cliente();
                                    cliente.setText("");
                                    cliente.setText(info);
                                } else if (GiroNalInitController.isBeneficiario.get()) {
                                    String info = "";
                                    info = get.colnombreClienteProperty().getValue() + "\nC.C: " + datosCliente.getId_cliente();
                                    cliente.setText("");
                                    cliente.setText(info);
                                }

                                try {
                                    /**
                                     * configuracion de la paginacion
                                     */
                                    final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;

                                    pagination = new Pagination(numpag, 0);
                                    //paginationid.setId("paginationid");
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
                                                        final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                        tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                    } else {
                                                        final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                                    tablaDatos.getItems().setAll(registros.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= registros.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : registros.size())));
                                                }
                                            });
                                        }
                                    });
//                                    /**
//                                     * fin configuracion de la paginacion
//                                     */
//                                    try {
//                                        AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);
//
//                                    } catch (Exception e) {
//                                        System.out.println("no hay hijos para borrar");
//                                    }
                                    AnchorPane.getChildren().add(pagination);
                                    AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                    AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                    pagination.setVisible(true);
                                    //Atlas.vista.show(); 
                                } catch (Exception e) {
                                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                            } catch (Exception e) {
                                tablaDatos.setPlaceholder(new Label("No hay registros disponibles para cancelar"));
                                pagination.setVisible(false);
                            }
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            task.reset();
                            numpagina.addAndGet(-1);
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            task.reset();
                            numpagina.addAndGet(-1);
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });
                } catch (Exception ex) {
                    Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
//        } else {
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    new ModalMensajes("Hay registros pendientes de consultar por el canal", "Advertencia", ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.DEFAULT);
//                }
//            });
//        }
    }

    @FXML
    void CancelarOP(ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                final List<tablaInfoGnralGirosnal> listapagos = new ArrayList<tablaInfoGnralGirosnal>();
                for (Iterator<tablaInfoGnralGirosnal> it = registros.iterator(); it.hasNext();) {
                    tablaInfoGnralGirosnal next = it.next();

                    if (next.seleccionProperty().getValue()) {
                        listapagos.add(next);
                    }
                }
                tablaInfoGnralGirosnal data = listapagos.get(0);

                if (data.colCelclienteProperty().getValue().trim().isEmpty() && data.colnombreClienteProperty().getValue().trim().isEmpty()) {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("No se puede realizar la cancelacion no se cuentan con los datos Nombre y celular del cliente", "Advertencia", ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);
                        }
                    });
                } else if (data.colCanalVentaProperty().getValue().trim().equals("SUC")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("El Giro no puede ser cancelado por que se realizo desde una sucursal, por favor remitir al cliente a una Sucursal fisica para su cancelación.",
                                    "Advertencia", ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);
                        }
                    });
                } else {
                    GirosCancelConfirmfinController controller = new GirosCancelConfirmfinController();
                    controller.mostrarCancelGirosConfirm(data);
                }
            }
        });
    }

    @FXML
    void volverOP(ActionEvent event) {
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

        final TreeView<String> arbolGirosNal = (TreeView<String>) pane.lookup("#arbolGirosNal");
        if (arbolGirosNal != null) {
            arbolGirosNal.setDisable(false);
        }

        arbolGirosNal.getSelectionModel().clearSelection();
        GirosNalCancelacionController.registros.clear();
        numpagina.set(0);
        setIndicadorRegistros("N");
        try {
            pane.getChildren().remove(3);
        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        Atlas.vista.show();
    }

    public void mostrarGirosNalCancelar(final int origen) {

        if (origen == 0) {
            GirosNalCancelacionController.registros.clear();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/GirosNalCancelacion.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();

                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button masRegistros = (Button) root.lookup("#indMasReg");
                    final Button btnBuscar = (Button) root.lookup("#buscarFechas");
                    final Button btnvolver = (Button) root.lookup("#volverOP");
                    final Button cancelar = (Button) root.lookup("#CancelarOP");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<tablaInfoGnralGirosnal> tablaData = (TableView<tablaInfoGnralGirosnal>) root.lookup("#tablaDatos");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    if ("]".equals(getIndicadorRegistros())) {
                        masRegistros.setDisable(false);
                    } else {
                        masRegistros.setDisable(true);
                    }

                    cancelar.setDisable(true);

                    final DropShadow shadow = new DropShadow();
                    masRegistros.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    masRegistros.setCursor(Cursor.HAND);
                                    masRegistros.setEffect(shadow);
                                }
                            });

                    masRegistros.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    masRegistros.setCursor(Cursor.DEFAULT);
                                    masRegistros.setEffect(null);
                                }
                            });

                    btnBuscar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    btnBuscar.setCursor(Cursor.HAND);
                                    btnBuscar.setEffect(shadow);
                                }
                            });

                    btnBuscar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    btnBuscar.setCursor(Cursor.DEFAULT);
                                    btnBuscar.setEffect(null);
                                }
                            });

                    btnvolver.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    btnvolver.setCursor(Cursor.HAND);
                                    btnvolver.setEffect(shadow);
                                }
                            });

                    btnvolver.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    btnvolver.setCursor(Cursor.DEFAULT);
                                    btnvolver.setEffect(null);
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

                    try {
                        pane.getChildren().remove(3);

                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    label_menu.setVisible(false);

                    if (origen == 1) {
                        /**
                         * barra progreso
                         */
                        Region veil = new Region();
                        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                        ProgressIndicator p = new ProgressIndicator();
                        p.setMaxSize(150, 150);
                        panel_tabla.getChildren().addAll(veil, p);

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
                                                    final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaData.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                root.getChildren().get(root.getChildren().size() - 1).setLayoutX(1);
                                root.getChildren().get(root.getChildren().size() - 1).setLayoutY(95);
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
                                        final TreeView<String> arbolGirosNal = (TreeView<String>) pane.lookup("#arbolGirosNal");
                                        if (arbolGirosNal != null) {
                                            arbolGirosNal.setDisable(false);
                                        }
                                        arbolGirosNal.getSelectionModel().clearSelection();
                                    }
                                });
                            }
                        });

                        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
                            }
                        });

                    } else {

                        if (!registros.isEmpty()) {

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
                                                final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(1);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(95);
                            pagination.setVisible(true);
                            Atlas.vista.show();
                        }
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });
    }

    public String getIndicadorRegistros() {
        return GirosNalInfoGeneralController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        GirosNalInfoGeneralController.indicadorRegistros = indicadorRegistros;
    }

    public class GetconveniosTask extends Service<ObservableList<tablaInfoGnralGirosnal>> {

        @Override
        protected Task<ObservableList<tablaInfoGnralGirosnal>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaGirosNalGnral = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    String rnv = "";
                    if (GiroNalInitController.isBeneficiario.get()) {
                        rnv = "B";
                    } else if (GiroNalInitController.isGirador.get()) {
                        rnv = "G";
                    }
                    //850,069,connid,codtransaccion,identificacion,tigoConsulta,fechainicial,fechafinal,numeroPaginado,clavehardware,~

                    //tramaGirosNalGnral.append("850,069,");  //Trx Anterior
                    tramaGirosNalGnral.append("850,082,");
                    tramaGirosNalGnral.append(datosCliente.getRefid()); //CONNID
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(AtlasConstantes.COD_CONSULTA_GIROS_INFOGNRAL);//COD TRX
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(datosCliente.getId_cliente());//ID
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(rnv);
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(formatoFecha.format(fechaini.getSelectedDate()));
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(formatoFecha.format(fechafin.getSelectedDate()));
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(numpagina.addAndGet(1) - 1);
                    tramaGirosNalGnral.append(",");//
                    tramaGirosNalGnral.append(consecutivoRef2.getText());
                    tramaGirosNalGnral.append(",");//
                    tramaGirosNalGnral.append(datosCliente.getContraseña());//CLAVE HW
                    tramaGirosNalGnral.append(",");//
                    tramaGirosNalGnral.append(datosCliente.getTokenOauth());//CLAVE HW
                    tramaGirosNalGnral.append(",~");

//                    System.out.println("Trama Consulta GirosNal es: " + tramaGirosNalGnral);

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Consulta Giros Nacionales = " + "##" + gestorDoc.obtenerHoraActual());

                        //Respuesta = "850,069,000,TRANSACCION EXITOSA                                                   ,STEVEN  SUAREZ CANO                                         ,000006556556556,000000000038179,00,000000000043379,00,*,051,0000003342%0004%%000000015328887%ELVER GALARGA%0000%00000000%00777777%PEN%00000000%00000000%0000%000003146490563%00019608%00003137%00800522%000108%SUC%000000%   ;0000003295%0917%1%000000080031056%Andres Arce%0000%00000000%00055000%PEN%00000000%00000000%0001%000003121212121%00005000%00000950%00060000%012036%CNB%000000%   ;0000003294%0917%1%000000080031056%Andres Arce%0917%20180607%00050000%PAG%00000000%00000000%0001%000003121212121%00005000%00000950%00055000%012036%CNB%012036%CNB;0000003306%0917%1%000000080031056%Andres Arce%0000%00000000%00010000%VTA%00000000%00000000%0001%000003121212121%00005000%00000950%00015000%012036%CNB%000000%   ;0000003297%0917%1%000000080031056%Andres Arce%0000%00000000%00065000%PEN%00000000%00000000%0001%000003121212121%00005000%00000950%00070000%012036%CNB%000000%   ;0000003307%0917%1%000000080031056%Andres Arce%0000%00000000%00020000%VTA%00000000%00000000%0001%000003121212121%00005000%00000950%00025000%012036%CNB%000000%   ;0000003300%0917%1%000000080031056%Andres Arce%0000%00000000%00075000%PEN%00000000%00000000%0001%000003121212121%00005000%00000950%00080000%012036%CNB%000000%   ;0000003301%0917%1%000000080031056%Andres Arce%0000%00000000%00080000%VTA%00000000%00000000%0001%000003121212121%00005000%00000950%00085000%012036%CNB%000000%   ;0000003309%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00100000%ANU%20180607%00000000%0001%000001111111111%00004050%00000950%00105000%020020%CNB%000000%   ;0000003310%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00100000%ANU%20180607%00000000%0001%000001111111111%00004050%00000950%00105000%020020%CNB%000000%   ;0000003312%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%   ;0000003313%0917%1%000000080031056%Andres Arce%0000%00000000%00050000%ANU%20180608%00000000%0000%000003121212121%00005000%00000950%00055000%012036%CNB%000000%   ;0000003315%0917%1%000000080031056%Andres Arce%0000%00000000%00030000%ANU%20180608%00000000%0000%000003121212121%00005000%00000950%00035000%012036%CNB%000000%   ;0000003316%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%   ;0000003318%0917%1%000000043971496%LUISA FERNANDA BARGUIL HERNANDEZ%0917%20180608%00080000%PAG%00000000%00000000%0000%000003504557982%00005000%00000950%00085000%012036%CNB%012036%CNB;0000003320%0917%1%000000043971496%LUISA FERNANDA BARGUIL HERNANDEZ%0917%20180608%00090000%PAG%00000000%00000000%0000%000003504557982%00005000%00000950%00095000%012036%CNB%012036%CNB;0000003322%0917%1%000000043971496%LUISA FERNANDA BARGUIL HERNANDEZ%0917%20180608%00005000%PAG%00000000%00000000%0000%000003504557982%00005000%00000950%00010000%012036%CNB%012036%CNB;0000003323%0917%1%000000043971496%LUISA FERNANDA BARGUIL HERNANDEZ%0000%00000000%00399000%VTA%00000000%00000000%0000%000003504557982%00012000%00002280%00411000%012036%CNB%000000%   ;0000003324%0917%1%000000043971496%LUISA FERNANDA BARGUIL HERNANDEZ%0000%00000000%00500000%ANU%20180608%00000000%0000%000003504557982%00017500%00003325%00517500%012036%CNB%000000%   ;0000003326%0917%1%000000080031056%Andres Arce%0000%00000000%00020000%PEN%00000000%00000000%0000%000003121212121%00005000%00000950%00025000%012036%CNB%000000%   ;0000003327%0917%1%000000043971496%LUISA FERNANDA BARGUIL HERNANDEZ%0917%20180608%00111000%PAG%00000000%00000000%0000%000003504557982%00011000%00001000%00123000%020020%CNB%020020%CNB;0000003328%0917%1%000000043971496%LUISA FERNANDA BARGUIL HERNANDEZ%0917%20180608%00111000%PAG%00000000%00000000%0000%000003504557982%00011000%00001000%00123000%020020%CNB%020020%CNB;0000003330%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%   ;0000003331%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%   ;0000003332%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%   ;0000003333%0917%1%000000080031056%Andres Arce%0000%00000000%00050000%PEN%00000000%00000000%0000%000003121212121%00005000%00000950%00055000%012036%CNB%000000%   ;0000003334%0917%1%000000080031056%Andres Arce%0000%00000000%00015000%VTA%00000000%00000000%0000%000003121212121%00005000%00000950%00020000%012036%CNB%000000%   ;0000003335%0917%1%000000080031056%Andres Arce%0000%00000000%00060000%VTA%00000000%00000000%0000%000003121212121%00005000%00000950%00065000%012036%CNB%000000%   ;0000003336%0917%1%000000080031056%Andres Arce%0000%00000000%00056000%VTA%00000000%00000000%0000%000003121212121%00005000%00000950%00061000%012036%CNB%000000%   ;0000003337%0917%1%000000080031056%Andres Arce%0000%00000000%00055000%VTA%00000000%00000000%0000%000003121212121%00005000%00000950%00060000%012036%CNB%000000%   ;0000003338%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%   ;0000003339%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0917%20180608%00020000%PAG%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%020020%CNB;0000003340%0917%1%000000080031056%Andres Arce%0000%00000000%00098000%VTA%00000000%00000000%0000%000003121212121%00005000%00000950%00103000%012036%CNB%000000%   ;0000003344%0917%1%000000080031056%Andres Arce%0000%00000000%00025000%VTA%00000000%00000000%0000%000003121212121%00004075%00000005%00029075%012036%CNB%000000%   ;0000003345%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%   ;0000003346%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%   ;0000003347%0917%1%000000080031056%Andres Arce%0000%00000000%00050000%VTA%00000000%00000000%0000%000003121212121%00005000%00000950%00055000%012036%CNB%000000%   ;0000003348%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%   ;0000003349%0917%1%000000080031056%Andres Arce%0000%00000000%00060000%ANU%20180608%00000000%0000%000003121212121%00005000%00000950%00065000%012036%CNB%000000%   ;0000003360%0917%1%000000080031056%Andres Arce%0000%00000000%00190000%PEN%00000000%00000000%0000%000003121212121%00009000%00001710%00199000%012036%CNB%000000%   ;0000003351%0917%1%000001040739739%CIFIN CALIDAD IDENTIFICACION PRUEBAS%0000%00000000%00020000%VTA%00000000%00000000%0000%000001111111111%00000110%00000010%00001230%020020%CNB%000000%CNB;0000003361%0917%1%000000080031056%Andres Arce%0000%00000000%00006000%VTA%00000000%00000000%0000%000003121212121%00005000%00000950%00011000%012036%CNB%000000%   ;0000003352%0917%1%000000080031056%Andres Arce%0000%00000000%00110000%PEN%00000000%00000000%0000%000003121212121%00008000%00001520%00118000%012036%CNB%000000%   ;0000003354%0917%1%000000080031056%Andres Arce%0000%00000000%00130000%PEN%00000000%00000000%0000%000003121212121%00008000%00001520%00138000%012036%CNB%000000%   ;0000003355%0917%1%000000080031056%Andres Arce%0000%00000000%00140000%PEN%00000000%00000000%0000%000003121212121%00008000%00001520%00148000%012036%CNB%000000%   ;0000003356%0917%1%000000080031056%Andres Arce%0000%00000000%00150000%PEN%00000000%00000000%0000%000003121212121%00008000%00001520%00158000%012036%CNB%000000%   ;0000003357%0917%1%000000080031056%Andres Arce%0000%00000000%00160000%PEN%00000000%00000000%0000%000003121212121%00009000%00001710%00169000%012036%CNB%000000%   ;0000003358%0917%1%000000080031056%Andres Arce%0000%00000000%00170000%PEN%00000000%00000000%0000%000003121212121%00009000%00001710%00179000%012036%CNB%000000%   ;0000003359%0917%1%000000080031056%Andres Arce%0000%00000000%00180000%PEN%00000000%00000000%0000%000003121212121%00009000%00001710%00189000%012036%CNB%000000%   ;0000003362%0917%1%000000080031056%Andres Arce%0000%00000000%00200000%PEN%00000000%00000000%0000%000003121212121%00009000%00001710%00209000%012036%CNB%000000%   ;0000003363%0917%1%000000080031056%Andres Arce%0000%00000000%00011000%VTA%00000000%00000000%0000%000003121212121%00003105%00000465%00014105%012036%CNB%000000%   ,~";
//                        Respuesta="850,082,000,TRANSACCION EXITOSA                                              ,1,CIRO MARI?O PERLAZA SANCHEZ                                 ,000000000397600,00,000000000397600,00,1,069,41667%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000001540000%1%00000000%3002574561%3104587622%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41666%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000002340000%1%00000000%3002574561%3104587622%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41664%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41663%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41662%00917%01%201901094%RAUL EDWIN SANCHEZ CALLEJAS%00000%20190704%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41370%00917%01%201901047%ANA EDITH OROZCO NINO%00000%20190626%00000000000500000%3%20190704%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%NP%No hay plaza;41001%00917%01%100001154%FELIPE CAMILO ARAQUE CHICA%00000%20190626%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010201%CNB%%%%%%;40772%00917%01%100001235%FELIPE MARIO LONDO?O ROMERO%00000%20190626%00000000000500000%3%20190704%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%RP%Razones personales;39114%00917%01%100001019%JOHAN CARLOS LOZADA CARDONA%00000%20190626%00000000000500000%3%20190627%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%NP%No hay plaza;37980%00917%01%99100240%CIRO MARI?O PERLAZA SANCHEZ%00917%20190620%00000000001800012%5%20190620%3000000001%3000000002%00000000000360025%00000000000034201%00000000000012036%CNB%00000000000012036%CNB%%%%;37637%00917%01%100001033%YEISON IVAN GARCIA RUEDA%00000%20190606%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;37345%00917%01%100001201%ANDRES ANTONIO OCAMPO NAVARRO%00000%20190606%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;36222%00917%01%99103470%ANA MARGARITA CAICEDO%00000%20190529%00000000000500000%3%20190612%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;36190%00917%01%100001261%YURLEY ALICIA HIGUITA OCHOA%00000%20190529%00000000000500000%3%20190612%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;35882%00917%01%100001043%PABLO OSCAR PACHON PAJARO%00000%20190529%00000000000500000%3%20190604%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;35194%00917%01%100001256%ANGELICA ALEXANDRA ALVAREZ ACEVEDO%00000%20190529%00000000000500000%3%20190612%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;35089%00917%01%100001043%PABLO OSCAR PACHON PAJARO%00000%20190529%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;35068%00917%01%100001326%LEIDY CLAUDIA NU?EZ NAVIA%00000%20190529%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;33690%00917%01%100001229%ALEJANDRO MARIO SANCHEZ OCHOA%00000%20190529%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;33171%00917%01%99104016%FERNANDO RAFAEL SERNA ESCOBAR%00000%20190527%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;32616%00917%01%100001262%KARINA EDITH HIGUITA NORIEGA%00000%20190527%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;32093%00917%01%99104016%FERNANDO RAFAEL SERNA ESCOBAR%00000%20190527%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;28177%00917%01%1673605%GUILLERMO ALFONSO GARCIA CERQUERA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;27597%00917%01%100001347%YURLEY ELIZABETH HENAO ROSAS%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;27277%00917%01%100001208%ANDRES CAMILO ARAQUE RUEDA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;27251%00917%01%100001293%DIANA CATALINA HERNANDEZ MURILLO%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;26626%00917%01%100001361%MARCELA HELENA LOPEZ VANEGAS%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;26502%00917%01%99106858%ADRIANA PILAR GUERRERO CALLE%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;26030%00917%01%100001336%MARIA AMANDA SANABRIA GIRALDO%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25964%00917%01%99107111%CARLOS ARTURO DUQUE GAVIRIA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25777%00917%01%100001062%RAUL FABIAN PEREZ CAMPOS%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25513%00917%01%100001294%DANIELA OLGA OROZCO NI?O%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25352%00917%01%100001041%EDUARDO HUGO RESTREPO CARDONA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25068%00917%01%201901002%BEATRIZ AMANDA HERRERA NAVIA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;24520%00917%01%100001048%HUMBERTO IVAN GARCIA OCHOA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;24200%00917%01%100001200%EDUARDO CARLOS PEREZ VALENCIA%00917%20190521%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;23670%00917%01%100001338%PAULA OLGA HENAO NI?O%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;23613%00917%01%100001032%LUIS HUGO ARAQUE TAMAYO%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;23462%00917%01%100001374%LEIDY CINDY MEJIA NORIEGA%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;22837%00917%01%100001314%PAULA KATERINE MELO NEIRA%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;22416%00917%01%100001052%JUAN EDWIN TOVAR GOMEZ%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;22387%00917%01%1532527%CIFIN CALIDAD IDENTIFICACION PRUEBAS%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;22244%00917%01%100001280%LEIDY LUCIA MELO NAVARRO%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;21072%00917%01%99107590%KELLY CRISTINA AVILA CORDOBA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;20648%00917%01%100001171%JUAN IVAN OCAMPO CARDONA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;20597%00917%01%100001277%KARINA HELENA NU?EZ CRUZ%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;19803%00917%01%100001108%RAFAEL ELKIN RINCON ROMERO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;19425%00917%01%100001228%LUIS NICOLAS RUIZ UPEGUI%00000%20190517%00000000000500000%3%20190704%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%RP%Razones personales;19063%00917%01%99106858%ADRIANA PILAR GUERRERO CALLE%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;18216%00917%01%201901098%LUIS MARIO LOZADA MESA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;16933%00917%01%100001215%FELIPE GARCIA MESA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;16806%00917%01%100001143%ALEJANDRO FABIAN FORONDA CANO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;16473%00917%01%99103755%PEDRO MIGUEL SALAZAR HERRERA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15644%00917%01%100001040%TOMAS MARIO GARCIA VALENCIA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15527%00917%01%100001177%HERNAN HUGO LONDO?O ROMERO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15287%00917%01%100001350%LINA FLOR VELEZ ACEVEDO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15202%00917%01%100001297%MARIA ALICIA HERNANDEZ GIRALDO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15127%00917%01%201901002%BEATRIZ AMANDA HERRERA NAVIA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;14673%00917%01%99107111%CARLOS ARTURO DUQUE GAVIRIA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;14291%00917%01%100001279%KARINA ROSA HERNANDEZ MORALES%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;13607%00917%01%100001305%YULIANA ROSA SALAZAR TRUJILLO%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;13401%00917%01%100001311%BEATRIZ LUCIA ROMERO NAVIA%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;12275%00917%01%100001218%TOMAS EDWIN GAVIRIA MONTOYA%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;11628%00917%01%104685%CIFIN CALIDAD IDENTIFICACION PRUEBAS%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;11312%00917%01%99103470%ANA MARGARITA CAICEDO%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;11015%00917%01%20122745%CIFIN CALIDAD IDENTIFICACION PRUEBAS%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;8982%00917%01%100001252%DANIELA KATERINE SALAZAR NI?O%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;8882%00917%01%100001354%LUZ ELI OROZCO GIRALDO%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaGirosNalGnral.toString());
                        // System.out.println("Respuesta es : " + Respuesta);
                        //docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Consulta Giros Nacionales = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaGirosNalGnral.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex1) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    failed();
                                }
                            });
                            throw new Exception("ERROR");
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        setIndicadorRegistros(Respuesta.split(",")[10]);//Indicador de más registros                        
                        // numpagina.set(Respuesta.split(",")[9].split(";").length);
                        String nombreCliente = Respuesta.split(",")[5].trim();
//                        String celularCliente = Respuesta.split(",")[10].trim();
//                        String acomuladorMes = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[6])).replace(".", ",") + "." + Respuesta.split(",")[7];
//                        String acomuladorAnual = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[8])).replace(".", ",") + "." + Respuesta.split(",")[9];
                        String acomuladorMes = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[6]));
                        String acomuladorAnual = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[8]));

                        String[] Ltarjetas = Respuesta.split(",")[12].split(";");

                        for (int i = 0; i < Ltarjetas.length; i++) {
                            String[] datos = Ltarjetas[i].split("%");

                            String fechaPago = "";
                            String fechacancelado = "";
                            String fechapend = "";

                            String fechaEstado = "";
                            String celularCliente = "";
                            int DiaGiros = 0;
                            String AcumuladoMes2 = "";
                            String AcumuladoAnual2 = "";
                            String canalUltimoEstado = "";
                            String PuntoServicioPago = "";
                            String motivoCancelacion = "";

                            try {
                                fechaPago = formatoFecha2.format(formatoFecha.parse(StringUtilities.eliminarCerosLeft(datos[6].trim())));
                            } catch (Exception e) {
                                fechaPago = datos[6].trim();
                            }

                            try {
                                fechacancelado = formatoFecha2.format(formatoFecha.parse(StringUtilities.eliminarCerosLeft(datos[9].trim())));
                            } catch (Exception e) {
                                fechacancelado = datos[9].trim();
                            }

                            try {
                                celularCliente = datos[10].trim();
                            } catch (Exception e) {
                                celularCliente = "";
                            }

//                            try {
//                                fechapend = formatoFecha2.format(formatoFecha.parse(StringUtilities.eliminarCerosLeft((datos[10].trim()))));
//                            } catch (Exception e) {
//                                fechapend = datos[10].trim();
//                            }

                            try {
                                DiaGiros = Integer.parseInt(datos[11]);
                            } catch (Exception e) {
//                                System.out.println("se va por error diasgiro");
                                DiaGiros = 0;
                            }
                            Calendar instance = Calendar.getInstance();
                            instance.add(Calendar.DAY_OF_MONTH, -DiaGiros);

                            try {
                                AcumuladoMes2 = "$ " + formatonum.format(Double.parseDouble(datos[18]));
                            } catch (Exception e) {
                                AcumuladoMes2 = "";
                            }

                            try {
                                AcumuladoAnual2 = "$ " + formatonum.format(Double.parseDouble(datos[19]));
                            } catch (Exception e) {
                                AcumuladoAnual2 = "";
                            }

                            try {
                                canalUltimoEstado = datos[17];
                            } catch (Exception e) {
                            }

                            try {
                                PuntoServicioPago = StringUtilities.eliminarCerosLeft(datos[16]);
                            } catch (Exception e) {
                            }

                            try {
                                motivoCancelacion = StringUtilities.eliminarCerosLeft(datos[21]);
                            } catch (Exception e) {
                            }

                            String valorTotal = "$ " + formatonum.format(Double.parseDouble(datos[7].substring(0, datos[7].length() - 2)) + Double.parseDouble(datos[12].substring(0, datos[12].length() - 2)) + Double.parseDouble(datos[13].substring(0, datos[13].length() - 2)));

                            if (datos[8].equalsIgnoreCase("1")) {//|| datos[8].equalsIgnoreCase("PEN")
                                tablaInfoGnralGirosnal giros = new tablaInfoGnralGirosnal(
                                        datos[15],
                                        fechaPago,
                                        GiroNalInitController.isGirador.get() ? datosCliente.getId_cliente() : StringUtilities.eliminarCerosLeft(datos[3]), //colDocGirador
                                        GiroNalInitController.isGirador.get() ? nombreCliente : datos[4], //colNomGirador
                                        GiroNalInitController.isGirador.get() ? StringUtilities.eliminarCerosLeft(datos[10]) : StringUtilities.eliminarCerosLeft(datos[11]), //colCelGirador
                                        GiroNalInitController.isGirador.get() ? StringUtilities.eliminarCerosLeft(datos[3]) : datosCliente.getId_cliente(), //colDocBenef
                                        GiroNalInitController.isGirador.get() ? datos[4] : nombreCliente, //colNomBenef
                                        GiroNalInitController.isGirador.get() ? StringUtilities.eliminarCerosLeft(datos[11]) : StringUtilities.eliminarCerosLeft(datos[10]), //colCelBenef
                                        canalUltimoEstado,
                                        fechaPago,
                                        "$ " + formatonum.format(Double.parseDouble(datos[7].substring(0, datos[7].length() - 2))), //colValorGiro
                                        "$ " + formatonum.format(Double.parseDouble(datos[12].substring(0, datos[12].length() - 2))), //colValorComision
                                        valorTotal, //colValTotal
                                        "$ " + formatonum.format(Double.parseDouble(datos[13].substring(0, datos[13].length() - 2))), //colValIva
                                        mapeoEstadoGiro.mapeoEstadoGiro.get(datos[8]), //colEstadoGiro
                                        fechacancelado, //colFechaCancel
                                        fechapend, //colFechaPteCancel
                                        datos[0], //colContDiaGiro   transformada en RefGiro
                                        "Reenvio SMS",
                                        nombreCliente, //colnombreCliente
                                        celularCliente, //colCelcliente
                                        acomuladorAnual, //colAcumAnual
                                        acomuladorMes, //colAcumMes
                                        datos[0], //colConsecutivo
                                        StringUtilities.eliminarCerosLeft(datos[14]), //colPuntoservVenta
                                        datos[15], //colCanalVenta
                                        PuntoServicioPago, //colPuntoserPago
                                        false,
                                        AcumuladoAnual2, //colAcumAnual2
                                        AcumuladoMes2, //colAcumMes2
                                        motivoCancelacion);                                                                             //colMotCancel
                                registros.add(giros);
                            }
                        }
                    } else {
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCIÓN" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                numpagina.addAndGet(-1);
                            }
                        });
                        throw new Exception("ERROR");
                    }
                    return registros;
                }
            };
        }
    }

    /**
     * ANEXAR CHECKBOX EN COLUMNA
     *
     * @param <InfoTablaPagosTerceros>
     * @param <Boolean>
     */
    public class CheckBoxTableCell<S, T> extends TableCell<S, T> {

        private final CheckBox checkBox;
        private ObservableValue<T> ov;

        public CheckBoxTableCell() {
            this.checkBox = new CheckBox();
            this.checkBox.setAlignment(Pos.CENTER);
            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        }

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(checkBox);
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
            }
            int seleccion = 0;
            //***
            for (Iterator<tablaInfoGnralGirosnal> it = registros.iterator(); it.hasNext();) {
                tablaInfoGnralGirosnal next = it.next();

                if (next.seleccionProperty().getValue()) {
                    seleccion++;
                }
            }
            updateBotonera(seleccion);
        }
    }

    public void updateBotonera(final int seleccion) {
        if (seleccion == 1) {
            if (CancelarOP != null) {
                CancelarOP.setDisable(false);
            }
        } else if (seleccion > 1) {
            if (CancelarOP != null) {
                CancelarOP.setDisable(true);
            }
        } else if (seleccion == 0) {
            if (CancelarOP != null) {
                CancelarOP.setDisable(true);
            }
        }
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
        Calendar calendarFiaux = Calendar.getInstance();
        Calendar calendarFfaux = Calendar.getInstance();
        calendarFiaux.setTime(calendarFi);
        calendarFfaux.setTime(calendarFf);
        final int diferenciaDias = gestorDoc.CalcularDifFechas(calendarFiaux, calendarFfaux);
//        System.out.println("diferencia dias " + diferenciaDias);
        if ((diferenciaDias <= 90) && (diferenciaDias != -1)) {
            buscarFechas.setDisable(false);
        } else {
            buscarFechas.setDisable(true);
        }
        return true;
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    public static Calendar getFechalimite() {
        return fechalimite;
    }

    public static void setFechalimite(Calendar fechalimite) {
        PuntosColAjustePuntosController.fechalimite = fechalimite;
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
}
