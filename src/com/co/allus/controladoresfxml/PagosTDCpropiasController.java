/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagostdc.InfoDatosTdcPropia;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import com.co.allus.utils.AtlasConstantes;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.ComboBox;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class PagosTDCpropiasController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<modelSaldoTarjeta, String> bloqueo_tarjeta;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar_op;
    @FXML
    private ComboBox<String> cuenta_origen;
    @FXML
    private TableColumn<modelSaldoTarjeta, String> numero;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<modelSaldoTarjeta> tablaDatos;
    @FXML
    private TableColumn<modelSaldoTarjeta, String> tipo_tarjeta;
    @FXML
    private ComboBox<String> tipocta_origen;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private ProgressBar progresoConsulta;
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static ObservableList<modelSaldoTarjeta> tarjetas = FXCollections.observableArrayList();
    private Pagination pagination = new Pagination();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert bloqueo_tarjeta != null : "fx:id=\"bloqueo_tarjeta\" was not injected: check your FXML file 'PagosTDCpropias.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'PagosTDCpropias.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'PagosTDCpropias.fxml'.";
        assert cuenta_origen != null : "fx:id=\"cuenta_origen\" was not injected: check your FXML file 'PagosTDCpropias.fxml'.";
        assert numero != null : "fx:id=\"numero\" was not injected: check your FXML file 'PagosTDCpropias.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'PagosTDCpropias.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'PagosTDCpropias.fxml'.";
        assert tipo_tarjeta != null : "fx:id=\"tipo_tarjeta\" was not injected: check your FXML file 'PagosTDCpropias.fxml'.";
        assert tipocta_origen != null : "fx:id=\"tipocta_origen\" was not injected: check your FXML file 'PagosTDCpropias.fxml'.";
        this.location = url;
        this.resources = rb;

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {

                    if ("Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem()) || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem())) {
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

        numero.setCellValueFactory(new PropertyValueFactory<modelSaldoTarjeta, String>("numeroTarjeta"));
        tipo_tarjeta.setCellValueFactory(new PropertyValueFactory<modelSaldoTarjeta, String>("tipoTarjeta"));
        bloqueo_tarjeta.setCellValueFactory(new PropertyValueFactory<modelSaldoTarjeta, String>("bloqueoTarjeta"));

        PagosTDCpropiasController.cancelartarea.set(false);
        // ConsultaSaldoTDCController.cancelartareaSaldo.set(false);

    }

    /**
     * MENU PAGOS TDC PROPIA
     *
     * @param menu
     */
    public void mostrarMenuPagosTDCpropia(final String menu, final modelSaldoTarjeta TarjetaApagar) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosTDCpropias.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<modelSaldoTarjeta> tablaData = (TableView<modelSaldoTarjeta>) root.lookup("#tablaDatos");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    // final BigDecimal dato = new BigDecimal((datosCliente.getNombre().length() / 2d));
                    // final BigDecimal setScale = dato.setScale(0, BigDecimal.ROUND_UP);
                    // final String info = datosCliente.getNombre() + "\n" + StringUtilities.rellenarDato(" ", setScale.intValue(), " ") + "C.C: " + datosCliente.getId_cliente();
                    final String info = datosCliente.getNombre() + "\n C.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    /**
                     * barra de progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
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
                    // desabilitar arboles y boton conutinuar 
                    botoncontinuarOp.setDisable(true);
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

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    //
                    label_menu.setVisible(false);
                    // cuenta de origen 
                    final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                    final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                    //  final ComboBox<String> tarjetascte = (ComboBox<String>) root.lookup("#tarjetas_cte");

                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    tipocta_origen.setItems(emptyList);
                    cuenta_origen.setItems(emptyList);

                    final List<String> datos = new ArrayList<String>();
                    final List<String> datos2 = new ArrayList<String>();
                    // final List<String> datos3 = new ArrayList<String>();

                    datos.add("Tipo de Cuenta");
                    datos2.add("Seleccione una cuenta");
                    // datos3.add("Seleccione una Tarjeta");

                    HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                    // ArrayList<String> tarjetas = productos.get(CodigoProductos.TARJETA_DE_CREDITO);

                    productos = datosCliente.getProductos();
                    // tarjetas = productos.get(CodigoProductos.TARJETA_DE_CREDITO);

                    final Set<String> keySet = productos.keySet();

                    for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        final String cuentatipo = val.next();

                        /* validacion solo se pérmite cuenta ahorros y corriente */
                        if (CodigoProductos.CUENTA_AHORROS.equals(cuentatipo) || CodigoProductos.CUENTA_CORRIENTE.equals(cuentatipo)) {

                            datos.add(cuentatipo);
                        }
                    }

                    tipocta_origen.setItems(FXCollections.observableArrayList(datos));
                    tipocta_origen.getSelectionModel().select("Tipo de Cuenta");
                    cuenta_origen.setItems(FXCollections.observableArrayList(datos2));
                    cuenta_origen.getSelectionModel().select("Seleccione una cuenta");

                    final Service<ObservableList<modelSaldoTarjeta>> task = new PagosTDCpropiasController.GetTarjetasTask(TarjetaApagar);
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaData.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tablaData.itemsProperty().unbind();
//                            System.out.println("TERMINO TAREA");
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = tarjetas.size() % rowsPerPage() == 0 ? tarjetas.size() / rowsPerPage() : tarjetas.size() / rowsPerPage() + 1;

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
                                        int displace = tarjetas.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = tarjetas.size() / rowsPerPage();
                                        } else {
                                            lastIndex = tarjetas.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<modelSaldoTarjeta> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<modelSaldoTarjeta> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaData.getItems().setAll(tarjetas.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= tarjetas.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : tarjetas.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(7).setLayoutX(0);
                            root.getChildren().get(7).setLayoutY(30);
                            pagination.setVisible(true);
                            Atlas.vista.show();
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
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
                                    arbol_pagos.getSelectionModel().clearSelection();
                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception e) {
                                        gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                    }
                                    // Atlas.vista.show();
                                }
                            });

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
                        }
                    });

                    // Atlas.vista.show();
                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });

    }

    @FXML
    void cancel_op(final ActionEvent event) {

        PagosTDCpropiasController.cancelartarea.set(true);
        // ConsultaSaldoTDCController.cancelartareaSaldo.set(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception ex) {
                    gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
                }
            }
        });
    }

    @FXML
    void continuar_OP(final ActionEvent event) {
        final InfoDatosTdcPropia datosTdcProp = new InfoDatosTdcPropia();
        datosTdcProp.setTarjetaCredito(tablaDatos.getSelectionModel().getSelectedItem().getNumeroTarjeta());
        datosTdcProp.setFranquicia(tablaDatos.getSelectionModel().getSelectedItem().getTipoTarjeta());
        datosTdcProp.setTarjeta(tablaDatos.getSelectionModel().getSelectedItem());
        datosTdcProp.setTipo_cta_origen(tipocta_origen.getSelectionModel().getSelectedItem());
        datosTdcProp.setCta_origen(cuenta_origen.getSelectionModel().getSelectedItem());
        InfoDatosTdcPropia.setInfoTdcProp(datosTdcProp);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final PagosTDCpropiasTrxDatosController controller = new PagosTDCpropiasTrxDatosController();
                controller.mostrarTDCpropiasTrxDatos(InfoDatosTdcPropia.getInfoTdcProp(), new modelSaldoTarjeta("", "", ""));
            }
        });
    }

    @FXML
    void selCuenta_origen(final ActionEvent event) {

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                        final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                        //  final ComboBox<String> tarjetastdc = (ComboBox<String>) root.lookup("#tarjetas_cte");
                        final TableView<modelSaldoTarjeta> tabladatos = (TableView<modelSaldoTarjeta>) root.lookup("#tablaDatos");
                        final Button buttonCont = (Button) root.lookup("#continuar_op");

                        if (tabladatos.getSelectionModel().getSelectedItem() == null || "Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem()) || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem())) {
                            buttonCont.setDisable(true);
                        } else {
                            buttonCont.setDisable(false);
                        }

                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }

                }
            });

        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }

    }

//    @FXML
//    void selTarjetas_cte(final ActionEvent event) {
//        try {
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//
//                    try {
//                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
//                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
//                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
//                        final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
//                        final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
//                        final ComboBox<String> tarjetastdc = (ComboBox<String>) root.lookup("#tarjetas_cte");
//                        final Button buttonCont = (Button) root.lookup("#continuar_op");
//
//
//                        if ("Seleccione una Tarjeta".equalsIgnoreCase(tarjetastdc.getSelectionModel().getSelectedItem()) || "Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem()) || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem())) {
//                            buttonCont.setDisable(true);
//                        } else {
//                            buttonCont.setDisable(false);
//                        }
//
//                    } catch (Exception ex) {
//                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//
//                    }
//
//                }
//            });
//
//
//        } catch (Exception ex) {
//            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//
//        }
//
//    }
    @FXML
    void selTipocta_origen(final ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                        final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                        //  final ComboBox<String> tarjetastdc = (ComboBox<String>) root.lookup("#tarjetas_cte");
                        final TableView<modelSaldoTarjeta> tabladatos = (TableView<modelSaldoTarjeta>) root.lookup("#tablaDatos");
                        final Button buttonCont = (Button) root.lookup("#continuar_op");
                        cuenta_origen.getSelectionModel().clearSelection();
                        final String tipoC = tipocta_origen.getSelectionModel().getSelectedItem();
                        final Cliente datosCliente = Cliente.getCliente();
                        final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();

                        final List<String> datosctas = new ArrayList<String>();
                        final ArrayList<String> listacuentas = productos.get(tipoC);
                        datosctas.add("Seleccione una cuenta");

                        if (listacuentas != null) {
                            datosctas.addAll(listacuentas);
                        }
                        cuenta_origen.setItems(FXCollections.observableArrayList(datosctas));
                        cuenta_origen.getSelectionModel().select("Seleccione una cuenta");

//                        if ("Seleccione una Tarjeta".equalsIgnoreCase(tarjetastdc.getSelectionModel().getSelectedItem()) || "Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem()) || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem())) {
//                            buttonCont.setDisable(true);
//                        } else {
//                            buttonCont.setDisable(false);
//                        }
                        // System.out.println("Seleccion" + tabladatos.getSelectionModel().getSelectedItem().getNumeroTarjeta());
                        // System.out.println("Seleccion" + tabladatos.getSelectionModel().getSelectedItem().getNumeroTarjeta());
                        if (tabladatos.getSelectionModel().getSelectedItem() == null || "Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem()) || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem())) {
                            buttonCont.setDisable(true);
                        } else {
                            buttonCont.setDisable(false);
                        }

                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//                        ex.printStackTrace();
                    }

                }
            });

        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//            ex.printStackTrace();
        }
    }

    public class GetTarjetasTask extends Service<ObservableList<modelSaldoTarjeta>> {

        private modelSaldoTarjeta tarjeta;

        public GetTarjetasTask(modelSaldoTarjeta tarjetaApagar) {
            this.tarjeta = tarjetaApagar;
        }

        @Override
        protected Task<ObservableList<modelSaldoTarjeta>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarTDC = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    tramaListarTDC.append("850,005,");
                    tramaListarTDC.append(datosCliente.getRefid());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(AtlasConstantes.COD_COD_CONS_TARJ);
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getId_cliente());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(formatoFecha.format(fecha));
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(formatoHora.format(fecha));
                    tramaListarTDC.append(",");
                    tramaListarTDC.append("CRE");
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getContraseña());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getTokenOauth());
                    tramaListarTDC.append(",~");

//                    System.out.println("trama listarTarjetas" + tramaListarTDC.toString());
                    if (tarjeta.getNumeroTarjeta().isEmpty()) {
                        if (MarcoPrincipalController.newConsultaBloqTDc) {
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listartdc = " + "##" + gestorDoc.obtenerHoraActual());
//                            Respuesta = "850,"
//                                    + "002,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "354852685895589##"
//                                    + "American Express Blue##"
//                                    + "A##"
//                                    + "1;"
//                                    + "452685785459825962##"
//                                    + "Master Card Clasica##"
//                                    + "A##"
//                                    + "1;"
//                                    + "5698521458525932##"
//                                    + "Visa Oro##"
//                                    + "B##"
//                                    + "2;"
//                                    + ",~";
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
//                                System.out.println("respuesta Lista : " + Respuesta);
///                            Thread.sleep(3000);

                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " +Respuesta+ "##" + gestorDoc.obtenerHoraActual());
                            } catch (Exception ex) {
//                                System.out.println("contingencia");
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listar  = " + "##" + gestorDoc.obtenerHoraActual());
                                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " +Respuesta+ "##" + gestorDoc.obtenerHoraActual());

                                } catch (Exception ex1) {

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (PagosTDCpropiasController.cancelartarea.get()) {
                                                cancel();
                                            } else {

                                                new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                                            }
                                        }
                                    });

                                    throw new Exception("Error en la Conectandose al Servidor");

                                }

                            }

                        } else {
                            Respuesta = "850,"
                                    + "002,"
                                    + "000,"
                                    + ",~";

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            if (PagosTDCpropiasController.cancelartarea.get()) {
                                cancel();
                            } else {

                                if (MarcoPrincipalController.newConsultaBloqTDc) {
                                    final ObservableList<modelSaldoTarjeta> emptyObservableList = FXCollections.emptyObservableList();
                                    //  tablaDatos.setItems(emptyObservableList);
                                    tarjetas = FXCollections.observableArrayList();

                                    String[] Ltarjetas = Respuesta.split(",")[4].split(";");

                                    for (int i = 0; i < Ltarjetas.length; i++) {
                                        String[] datos = Ltarjetas[i].split("##");
                                        final modelSaldoTarjeta tarjeta = new modelSaldoTarjeta(datos[0], datos[1], datos[2]);
                                        tarjetas.add(tarjeta);
                                    }
                                }

//                            for (int i = 0; i < 850; i++) {
//                                final modelSaldoTarjeta tarjeta = new modelSaldoTarjeta("2222145875845825", "Master Card Clasica", "A");
//                                final modelSaldoTarjeta tarjeta2 = new modelSaldoTarjeta("14587845825", "Visa Clasica", "A");
//                                tarjetas.add(tarjeta);
//                                tarjetas.add(tarjeta2);
//                            }
                            }

                        } else {

                            if (PagosTDCpropiasController.cancelartarea.get()) {
                                cancel();
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

                    } else {
                        tarjetas.clear();
                        tarjetas.add(tarjeta);

                    }

                    return tarjetas;

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
