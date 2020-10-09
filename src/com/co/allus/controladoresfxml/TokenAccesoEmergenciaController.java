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
import com.co.allus.modelo.tokenempresas.infoAccesoEmergencia;
import com.co.allus.modelo.tokenempresas.infoDetalleAE;
import com.co.allus.tareas.BusquedaIdTask;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
import javafx.event.Event;
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
import javafx.scene.control.TreeView;
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
 * @author stephania.rojas
 */
public class TokenAccesoEmergenciaController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private URL location;
    @FXML
    private TableColumn colAE;
    @FXML
    private TableColumn colCelular;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colID_usuario;
    @FXML
    private TableColumn colNombre_usuario;
    @FXML
    private Button continuar_op;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TextField tfAEpendientes;
    @FXML
    private TextField tfAEpermitidos;
    @FXML
    private TextField tfAEsolicitados;
    @FXML
    private TextField tfBusqueda_ID;
    @FXML
    private TextField tfCosto;
    @FXML
    private TextField tfNumCuenta;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextField tfTipoCuenta;
    @FXML
    private TextField tfNit;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TableView<infoAccesoEmergencia> tabla_datos;
    @FXML
    private Button obtMasReg;
    private transient Service<Void> serviceDetalleAE;
    String aepermitidos = "";
    String aesolicitados = "";
    String aependientes = "";
    String aeperiodo = "";
    String id = "";
    String nombre_usuario = "";
    public Service<ObservableList<infoAccesoEmergencia>> task = new TokenAccesoEmergenciaController.GetaeTask();
    public static ObservableList<infoAccesoEmergencia> tokenAE = FXCollections.observableArrayList();
    private static GestorDocumental docs = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Pagination pagination = new Pagination();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    int currentpageindex = 0;
    private static AtomicInteger numpagina = new AtomicInteger(0);
    private String indicadorRegistros;

    public String getIndicadorRegistros() {
        return indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        this.indicadorRegistros = indicadorRegistros;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert colAE != null : "fx:id=\"colAE\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert colCelular != null : "fx:id=\"colCelular\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert colEmail != null : "fx:id=\"colEmail\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert colID_usuario != null : "fx:id=\"colID_usuario\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert colNombre_usuario != null : "fx:id=\"colNombre_usuario\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tfAEpendientes != null : "fx:id=\"tfAEpendientes\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tfAEpermitidos != null : "fx:id=\"tfAEpermitidos\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tfAEsolicitados != null : "fx:id=\"tfAEsolicitados\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tfBusqueda_ID != null : "fx:id=\"tfBusqueda_ID\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tfCosto != null : "fx:id=\"tfCosto\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tfNumCuenta != null : "fx:id=\"tfNumCuenta\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tfPeriodo != null : "fx:id=\"tfPeriodo\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tfTipoCuenta != null : "fx:id=\"tfTipoCuenta\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert tfNit != null : "fx:id=\"tfNit\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert obtMasReg != null : "fx:id=\"obtMasReg\" was not injected: check your FXML file 'TokenEmpresasAE.fxml'.";
        this.location = url;
        this.resources = rb;
        continuar_op.setDisable(true);

        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colAE.setCellValueFactory(new PropertyValueFactory("colAE"));
        colCelular.setCellValueFactory(new PropertyValueFactory("colCelular"));
        colEmail.setCellValueFactory(new PropertyValueFactory("colEmail"));
        colID_usuario.setCellValueFactory(new PropertyValueFactory("colID_usuario"));
        colNombre_usuario.setCellValueFactory(new PropertyValueFactory("colNombre_usuario"));

        TokenAccesoEmergenciaController.cancelartarea.set(false);
        progreso.setVisible(false);

        tabla_datos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                if (tabla_datos.getSelectionModel().getSelectedItem() != null) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                    tabla_datos.getSelectionModel().clearSelection();
                }
            }
        });

        obtMasReg.setDisable(true);
        setIndicadorRegistros("N");
        numpagina.set(0);

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
                            final ObservableList<infoAccesoEmergencia> TablaID = task.getValue();

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
                                                    final List<infoAccesoEmergencia> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoAccesoEmergencia> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(5);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(273);
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

    public void mostrarDatosTokenAE() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenAccesoEmergencia.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");

                    final TextField tfAEpermitidos = (TextField) root.lookup("#tfAEpermitidos");
                    final TextField tfAEpendientes = (TextField) root.lookup("#tfAEpendientes");
                    final TextField tfAEsolicitados = (TextField) root.lookup("#tfAEsolicitados");
                    final TextField tfPeriodo = (TextField) root.lookup("#tfPeriodo");

                    final TextField tfCosto = (TextField) root.lookup("#tfCosto");
                    final TextField tfNumCuenta = (TextField) root.lookup("#tfNumCuenta");
                    final TextField tfTipoCuenta = (TextField) root.lookup("#tfTipoCuenta");
                    final TextField tfNit = (TextField) root.lookup("#tfNit");
                    final Button obtMasReg = (Button) root.lookup("#obtMasReg");


                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);

                    final TableView<infoAccesoEmergencia> tablaData = (TableView<infoAccesoEmergencia>) root.lookup("#tabla_datos");

                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    final DropShadow shadow = new DropShadow();



                    continuar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.HAND);
                                    continuar_op.setEffect(shadow);
                                }
                            });

                    continuar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.DEFAULT);
                                    continuar_op.setEffect(null);

                                }
                            });


                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

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
                        arbol_servadd.setDisable(false);
                    }


                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaData.itemsProperty().bind(task.valueProperty());
                    task.start();

                    /**
                     * configuracion de la paginacion
                     */
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tablaData.itemsProperty().unbind();
                            final Cliente datosCliente = Cliente.getCliente();
                            tfNit.setText(datosCliente.getId_cliente());

                            tfAEpendientes.setText(aependientes);
                            tfAEpermitidos.setText(aepermitidos);
                            tfAEsolicitados.setText(aesolicitados);
                            tfPeriodo.setText(aeperiodo);

                            if ("S".equals(getIndicadorRegistros())) {
                                obtMasReg.setDisable(false);
                            } else {
                                obtMasReg.setDisable(true);
                            }

                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = tokenAE.size() % rowsPerPage() == 0 ? tokenAE.size() / rowsPerPage() : tokenAE.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {

                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = tokenAE.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = tokenAE.size() / rowsPerPage();
                                        } else {
                                            lastIndex = tokenAE.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();
                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<infoAccesoEmergencia> subList = tokenAE.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<infoAccesoEmergencia> subList = tokenAE.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaData.getItems().setAll(tokenAE.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= tokenAE.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : tokenAE.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(5);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(273);
                            pagination.setVisible(true);
                            Atlas.vista.show();
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
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



                                    arbol_servadd.getSelectionModel().clearSelection();
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
                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    private void BusquedaById(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoAccesoEmergencia>> TaskTablaAE = new BusquedaIdTask(id);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(TaskTablaAE.progressProperty());
                    veil.visibleProperty().bind(TaskTablaAE.runningProperty());
                    p.visibleProperty().bind(TaskTablaAE.runningProperty());
                    tabla_datos.itemsProperty().bind(TaskTablaAE.valueProperty());
                    TaskTablaAE.reset();
                    TaskTablaAE.start();



                    TaskTablaAE.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<infoAccesoEmergencia> TablaID = TaskTablaAE.getValue();


                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaID.size() % rowsPerPageCL() == 0 ? TablaID.size() / rowsPerPageCL() : TablaID.size() / rowsPerPageCL() + 1;

                                pagination = new Pagination(numpag, 0);
                                //paginationid.setId("paginationid");
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaID.size() % rowsPerPageCL();
                                            if (displace >= 0) {
                                                lastIndex = TablaID.size() / rowsPerPageCL();
                                            } else {
                                                lastIndex = TablaID.size() / rowsPerPageCL() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infoAccesoEmergencia> subList = TablaID.subList(pageIndex * rowsPerPageCL(), pageIndex * rowsPerPageCL() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoAccesoEmergencia> subList = TablaID.subList(pageIndex * rowsPerPageCL(), pageIndex * rowsPerPageCL() + rowsPerPageCL());
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
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPageCL(), ((newValue.intValue() * rowsPerPageCL() + rowsPerPageCL() <= TablaID.size()) ? newValue.intValue() * rowsPerPageCL() + rowsPerPageCL() : TablaID.size())));
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
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(5);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(273);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

                        }
                    });

                    TaskTablaAE.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                    TaskTablaAE.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(TokenAccesoEmergenciaController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    private boolean istext(String val) {
        boolean retorno = false;
        String pattern = "^[a-zA-Z0-9]*$";
        if (val.matches(pattern)) {
            retorno = true;
        }
        return retorno;
    }

    @FXML
    void idbuscarkeytyped(final KeyEvent event) {

        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {
            if (istext(event.getCharacter())) {
                event.consume();
               // System.out.println("cedula a buscar :" + tfBusqueda_ID.getText() + event.getCharacter());
                synchronized (this) {
                    BusquedaById(tfBusqueda_ID.getText() + event.getCharacter());
                }
            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (tfBusqueda_ID.getText().isEmpty()) {
                        synchronized (this) {
                            BusquedaById("");
                        }
                    } else {
                        synchronized (this) {
                            BusquedaById(tfBusqueda_ID.getText());
                        }
                    }

                }
            }
        }
    }

    @FXML
    void idbuscarkeypress(final KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(tfBusqueda_ID, "*", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            tfBusqueda_ID.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();
        }
    }

    public class GetaeTask extends Service<ObservableList<infoAccesoEmergencia>> {

        @Override
        protected Task<ObservableList<infoAccesoEmergencia>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaAE = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    //850,043,connid,0174,identificacion,requiereRespuesta,tipoConsulta,idusuario,fechainiciCOnsulta,fechafin,clavehardware,~


                    tramaAE.append("850,043,");
                    tramaAE.append(datosCliente.getRefid());
                    tramaAE.append(",");
                    tramaAE.append(AtlasConstantes.COD_TOKEN_AE);
                    tramaAE.append(",");
                    tramaAE.append(datosCliente.getId_cliente());
                    tramaAE.append(",");
                    final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                    tramaAE.append(contra);
                    tramaAE.append(",");
                    tramaAE.append("1"); //TIPO CONSULTA
                    tramaAE.append(",");
                    tramaAE.append(""); //ID USUARIO
                    tramaAE.append(",");
                    tramaAE.append(""); //FECHA INI
                    tramaAE.append(",");
                    tramaAE.append(""); //FECHA FIN
                    tramaAE.append(",");
                    tramaAE.append(numpagina.incrementAndGet());
                    tramaAE.append(",");
                    tramaAE.append(datosCliente.getContraseña()); //ID USUARIO
                    tramaAE.append(",");
                    tramaAE.append(datosCliente.getTokenOauth()); //ID USUARIO
                    tramaAE.append(",~");

                    //System.out.println("trama Acceso Emergencia " + tramaAE.toString());


                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token acceso emergencia = " + "##" + docs.obtenerHoraActual());
                        //idUsurario%estadoUsuario%NombreUsuario%RolUsuario%PerfilUsuario%EstadoToken%DescEstadoToken%serialToken%fechaExp%indicadorAccesoEmergencia;..... 
//                        Respuesta = "850,"
//                                + "043,"
//                                + "000,"
//                                + "TRANSACCION EXITOSA,"
//                                + "BANCOLOMBIA,"
//                                + "7," //AE PERMITIDOS
//                                + "4," //AE SOLICITADOS
//                                + "3," //AE POR SOLICITAR
//                                + "7," //PERIODO AE
//                                + "10,"//CANTIDAD REGISTROS
//                                + "S,"
//                                + "angelagar%Angela Garzón%agarzon@bancolombia.com.co%3103330000%2%;"
//                                + "julianaper%Juliana Perez%jperez@bancolombia.com.co%3103330000%1%;"
//                                + "juancast%Juan Castaño%jcasta@bancolombia.com.co%3103330000%1,"
//                                + "~";
//
//
                        //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAE.toString());
                         Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaAE.toString());
                        //  System.out.println(" RESPUESTA TRAMA TOKEN EMPRESAS:" + Respuesta);

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token acceso emergencia = " + "##" + docs.obtenerHoraActual());
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAE.toString());
                             Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaAE.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (TokenAccesoEmergenciaController.cancelartarea.get()) {
                                        cancel();
                                        numpagina.decrementAndGet();
                                    } else {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        failed();
                                        numpagina.decrementAndGet();
                                    }
                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        aepermitidos = Respuesta.split(",")[5];
                        aesolicitados = Respuesta.split(",")[6];
                        aependientes = Respuesta.split(",")[7];
                        aeperiodo = Respuesta.split(",")[8];
                        setIndicadorRegistros(Respuesta.split(",")[10]);

                        if (TokenAccesoEmergenciaController.cancelartarea.get()) {
                            cancel();
                        } else {
                            tokenAE.clear();
                            if (tokenAE.isEmpty()) {
                                tokenAE = FXCollections.observableArrayList();
                            }

                            String[] Ltoken = Respuesta.split(",")[11].split(";");

                            for (int i = 0; i < Ltoken.length; i++) {
                                String[] datos = Ltoken[i].split("%");
//                                    private StringProperty colID_usuario;
//    private StringProperty colNombre_usuario;
//    private StringProperty colCelular;
//    private StringProperty colEmail;
//    private StringProperty colAE;
                                final infoAccesoEmergencia data = new infoAccesoEmergencia(
                                        datos[0].trim(),
                                        datos[1].trim(),
                                        datos[3].trim(),
                                        datos[2].trim(),
                                        datos[4].trim());
                                tokenAE.add(data);
                            }

                        }
                    } else {
                        if (TokenAccesoEmergenciaController.cancelartarea.get()) {
                            cancel();
                            numpagina.decrementAndGet();
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
                    }
                    return tokenAE;

                }
            };
        }
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 2;
    }

    public int rowsPerPageCL() {
        return 2;
    }

    @FXML
    void continuarOP(final ActionEvent event) {
        infoAccesoEmergencia selectedItem = tabla_datos.getSelectionModel().getSelectedItem();
        dataTablaAE infotabla = dataTablaAE.getDataTablaAE();
        infotabla.setId_usuario(selectedItem.getColID_usuario());
        infotabla.setNombre_usuario(selectedItem.getColNombre_usuario());
        dataTablaAE.setDataTablaAE(infotabla);

        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle token ae" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle token" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }
    }

    public Service<Void> continuar_Op() {
        serviceDetalleAE = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramaAEDetalleFecha = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();


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
                        tramaAEDetalleFecha.append("1");
                        tramaAEDetalleFecha.append(",");
                        tramaAEDetalleFecha.append(datosCliente.getContraseña()); //ID USUARIO
                         tramaAEDetalleFecha.append(",");
                        tramaAEDetalleFecha.append(datosCliente.getTokenOauth()); //ID USUARIO
                        tramaAEDetalleFecha.append(",~");
                        //System.out.println("TRAMA DETALLE AE :" + tramaAEDetalleFecha);


                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token acceso emergencia detalle = " + "##" + docs.obtenerHoraActual());
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
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAEDetalleFecha.toString());
                             Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaAEDetalleFecha.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token acceso emergencia detalle = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaAEDetalleFecha.toString());
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaAEDetalleFecha.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
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

                        if ("000".equals(Respuesta.split(",")[2])) {
                            final String indMasReg = Respuesta.split(",")[10];
                            final List<infoDetalleAE> lista = new ArrayList<infoDetalleAE>();

                            try {

                               // System.out.println(Respuesta.split(",")[11]);
                                String[] data = Respuesta.split(",")[11].split(";");

                                for (int i = 0; i < data.length; i++) {
                                    final String[] datos = data[i].split("%");

                                    final infoDetalleAE ObjDet = new infoDetalleAE(
                                            datos[0].trim(), // fecha  
                                            datos[1].trim(), // hora
                                            " ", //valor novedad
                                            datos[2].trim(), //vigencia
                                            datos[3], // destino
                                            datos[4].trim()); //estado

                                    lista.add(ObjDet);

                                }
                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }



                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final TokenAccesoEmergenciaDetalleController controller = new TokenAccesoEmergenciaDetalleController();
                                    controller.mostrarDetalleAE(lista, dataTablaAE.getDataTablaAE(), indMasReg, 1);
                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    cancel();
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

        return serviceDetalleAE;

    }
}
