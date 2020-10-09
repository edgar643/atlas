/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.hilos.EnvioTipAutFenix;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagoprestamos.DatosPagoPrestamo;
import com.co.allus.modelo.pagoprestamos.infoTablaPagosPrest;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
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
import javafx.event.Event;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
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
public class PrestamosController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    Button cancelarOp;
    @FXML
    private TableColumn colCredito;
    @FXML
    private TableColumn colFechaProx;
    @FXML
    private TableColumn colPagoMin;
    @FXML
    private TableColumn colSaldoTotal;
    @FXML
    private TableColumn colTipoPres;
    @FXML
    private TableColumn colValorIni;
    @FXML
    private TableColumn<infoTablaPagosPrest, Boolean> seleccion;
    @FXML
    Button detalleOP;
    @FXML
    Button pagarOP;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label label;
    @FXML
    private TableView<infoTablaPagosPrest> tabla_prestamos;
    private static String cuenta;
    private Pagination pagination = new Pagination();
    private transient Service<Void> servicePrestamoDetalle;
    int currentpageindex = 0;
    private static GestorDocumental docs = new GestorDocumental();
    private static List<infoTablaPagosPrest> dataTabla;
    private Service<ObservableList<infoTablaPagosPrest>> task = new PrestamosController.GetprestamosTask();
    private static ObservableList<infoTablaPagosPrest> prestamos = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static AtomicBoolean cancelartarea = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelarOp != null : "fx:id=\"cancelarOp\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert colCredito != null : "fx:id=\"colCredito\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert colFechaProx != null : "fx:id=\"colFechaProx\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert colPagoMin != null : "fx:id=\"colPagoMin\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert colSaldoTotal != null : "fx:id=\"colSaldoTotal\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert colTipoPres != null : "fx:id=\"colTipoPres\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert colValorIni != null : "fx:id=\"colValorIni\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert detalleOP != null : "fx:id=\"detalleOP\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert pagarOP != null : "fx:id=\"pagarOP\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert tabla_prestamos != null : "fx:id=\"tabla_prestamos\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'Prestamos.fxml'.";
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'Prestamos.fxml'.";
        this.resources = rb;
        this.location = url;

        progreso.setVisible(false);
        tabla_prestamos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_prestamos.setEditable(true);
        seleccion.setCellValueFactory(new PropertyValueFactory("seleccion"));
        colCredito.setCellValueFactory(new PropertyValueFactory("colCredito"));
        colTipoPres.setCellValueFactory(new PropertyValueFactory("colTipoPres"));
        colFechaProx.setCellValueFactory(new PropertyValueFactory("colFechaProx"));
        colValorIni.setCellValueFactory(new PropertyValueFactory("colValorIni"));
        colPagoMin.setCellValueFactory(new PropertyValueFactory("colPagoMin"));
        colSaldoTotal.setCellValueFactory(new PropertyValueFactory("colSaldoTotal"));

        PrestamosController.cancelartarea.set(false);
        detalleOP.setDisable(true);
        pagarOP.setDisable(true);


        seleccion.setCellFactory(new Callback<TableColumn<infoTablaPagosPrest, Boolean>, TableCell<infoTablaPagosPrest, Boolean>>() {
            @Override
            public TableCell<infoTablaPagosPrest, Boolean> call(TableColumn<infoTablaPagosPrest, Boolean> p) {
                return new CheckBoxTableCell<infoTablaPagosPrest, Boolean>();
            }
        });


    }

    //CheckBoxTableCell for creating a CheckBox in a table cell
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
            for (Iterator<infoTablaPagosPrest> it = prestamos.iterator(); it.hasNext();) {
                infoTablaPagosPrest next = it.next();

                if (next.seleccionProperty().getValue()) {
                    seleccion++;
                }

            }
            updateBotonera(seleccion);


        }
    }

    public void updateBotonera(final int seleccion) {
        if (seleccion == 1) {

            if ((detalleOP != null) && (pagarOP != null)) {
                detalleOP.setDisable(false);
                pagarOP.setDisable(false);
            }
        } else if (seleccion > 1) {
            if ((detalleOP != null) && (pagarOP != null)) {
                detalleOP.setDisable(true);
                pagarOP.setDisable(false);
            }
        } else if (seleccion == 0) {
            if ((detalleOP != null) && (pagarOP != null)) {
                detalleOP.setDisable(true);
                pagarOP.setDisable(true);
            }
        }

    }

    @FXML
    void continuarOP(final ActionEvent event) {


        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ir a Detalle prestamo" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
//                System.out.println("ENTRO2");
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario canceloir a Detalle prestamo" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            detalleOP.setDisable(true);
            pagarOP.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            detalleOP.setDisable(true);
            pagarOP.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }


    }

    public void mostrarDatosPrestamos() {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {


                    final URL location = getClass().getResource("/com/co/allus/vistas/Prestamos.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button detalleOP = (Button) root.lookup("#detalleOP");
                    final Button pagarOP = (Button) root.lookup("#pagarOP");
                    final Button cancelarOp = (Button) root.lookup("#cancelarOP");

                    final TableView<infoTablaPagosPrest> tablaData = (TableView<infoTablaPagosPrest>) root.lookup("#tabla_prestamos");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");


                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
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
                    detalleOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    detalleOP.setCursor(Cursor.HAND);
                                    detalleOP.setEffect(shadow);
                                }
                            });

                    detalleOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    detalleOP.setCursor(Cursor.DEFAULT);
                                    detalleOP.setEffect(null);

                                }
                            });

                    pagarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    pagarOP.setCursor(Cursor.HAND);
                                    pagarOP.setEffect(shadow);
                                }
                            });

                    pagarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    pagarOP.setCursor(Cursor.DEFAULT);
                                    pagarOP.setEffect(null);

                                }
                            });


                    cancelarOp.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarOp.setCursor(Cursor.HAND);
                                    cancelarOp.setEffect(shadow);
                                }
                            });

                    cancelarOp.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarOp.setCursor(Cursor.DEFAULT);
                                    cancelarOp.setEffect(null);

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
                        arbol_servadd.setDisable(true);
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
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = prestamos.size() % rowsPerPage() == 0 ? prestamos.size() / rowsPerPage() : prestamos.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {

                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = prestamos.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = prestamos.size() / rowsPerPage();
                                        } else {
                                            lastIndex = prestamos.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<infoTablaPagosPrest> subList = prestamos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<infoTablaPagosPrest> subList = prestamos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaData.getItems().setAll(prestamos.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= prestamos.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : prestamos.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(4).setLayoutX(0);
                            root.getChildren().get(4).setLayoutY(50);
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

                                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                                    if (arbol_servadd != null) {
                                        arbol_servadd.setDisable(false);
                                    }



                                    arbol_pagos.getSelectionModel().clearSelection();
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
//                            System.out.println("cancelo la tarea");
                        }
                    });


                } catch (Exception e) {

                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });
    }

    public Service<Void> continuar_Op() {
        servicePrestamoDetalle = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();
                        final StringBuilder tramaPagoPrestamos = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final DatosPagoPrestamo infoTablaDetalle = DatosPagoPrestamo.getDatosTablaDetalle();

                        String credito = "";

                        for (Iterator<infoTablaPagosPrest> it = prestamos.iterator(); it.hasNext();) {
                            infoTablaPagosPrest next = it.next();

                            if (next.seleccionProperty().getValue()) {
                                credito = next.colCreditoProperty().getValue();
                                break;
                            }

                        }




                        tramaPagoPrestamos.append("850,039,");
                        tramaPagoPrestamos.append(cliente.getRefid());
                        tramaPagoPrestamos.append(",");
                        tramaPagoPrestamos.append(AtlasConstantes.COD_CONS_DET_PRESTAMO);
                        tramaPagoPrestamos.append(",");
                        tramaPagoPrestamos.append(cliente.getId_cliente());
                        tramaPagoPrestamos.append(",");
                        tramaPagoPrestamos.append(credito);
                        tramaPagoPrestamos.append(",");
                        tramaPagoPrestamos.append(cliente.getContraseña());
                        tramaPagoPrestamos.append(",");
                        tramaPagoPrestamos.append(cliente.getTokenOauth());
                        tramaPagoPrestamos.append(",~");

//                        System.out.println("TRAMA SALDO PRESTAMOS :" + tramaPagoPrestamos);



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ pago prestamos= " + "##" + docs.obtenerHoraActual());

                            //850,039,000,TRANSACCION EXITOSA,descripcionPrestamo,fechaDesembolso,fechaProximoPago,ValorInicialEnt,ValoriniciaCent,InteresMoraEnt,InteresMoraCent,InteresCorrienteEnt,InteresCorrientCent,CapitalvigenteEnt,CapitaVigentCent,OtrosCargosEnt,OtrosCargosCent,PagoCuotaMinEnt,PagoCuotaMinCent,PagoTotalEnt,PagoTotalCent,numPrestamo,~

//                            Respuesta = "850,"
//                                    + "039,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "Credito de Consumo," //descripcion Prestamo 4
//                                    + "08/10/2015," //fecha Desembolso 5
//                                    + "26/08/2016," // fecha Proximo Pago 6
//                                    + "10000000," //Valor Inicial Entero 7
//                                    + "23," //Valor Inicial Decimales 8
//                                    + "2500," //Interes Mora Entero 9
//                                    + "12," //Interes Mora Decimales 10
//                                    + "2000," //Interes Corriente Entero 11 
//                                    + "00," //Interes Corrient Decimales 12
//                                    + "7500000," //Capital Vigente Entero 13
//                                    + "25," //Capital Vigente Decimales 14
//                                    + "1250," //Otros Cargos Entero 15
//                                    + "09," //Otros Cargos Decimales 16
//                                    + "000000," //Pago Cuota Minima Entero 17
//                                    + "00," //Pago Cuota Minima Decimales 18
//                                    + "7750000," //Pago Total Entero 19
//                                    + "23," //Pago Total Decimales 20 
//                                    + "121236," //Numero Prestamo-Credito 21
//                                    + "~";

                            // Thread.sleep(2000);

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaPagoPrestamos.toString());
                            // System.out.println(" RESPUESTA TRAMA PRESTAMOS:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ pago prestamops = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaPagoPrestamos.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        detalleOP.setDisable(false);
                                        pagarOP.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {


                            infoTablaDetalle.setTipoPrestamo(Respuesta.split(",")[4].trim());
                            infoTablaDetalle.setFechaDesembolso(Respuesta.split(",")[5].trim());
                            infoTablaDetalle.setFechaProxPago(Respuesta.split(",")[6].trim());
                            infoTablaDetalle.setValorInicialEnt(Respuesta.split(",")[7].trim());
                            infoTablaDetalle.setValorInicialCent(Respuesta.split(",")[8].trim());
                            infoTablaDetalle.setInteresesMoraEnt(Respuesta.split(",")[9].trim());
                            infoTablaDetalle.setInteresesMoraCent(Respuesta.split(",")[10].trim());
                            infoTablaDetalle.setInteresesCorrientesEnt(Respuesta.split(",")[11].trim());
                            infoTablaDetalle.setInteresesCorrientesCent(Respuesta.split(",")[12].trim());
                            infoTablaDetalle.setCapitalVigenteEnt(Respuesta.split(",")[13].trim());
                            infoTablaDetalle.setCapitalVigenteCent(Respuesta.split(",")[14].trim());
                            infoTablaDetalle.setOtrosCargosEnt(Respuesta.split(",")[15].trim());
                            infoTablaDetalle.setOtrosCargosCent(Respuesta.split(",")[16].trim());
                            infoTablaDetalle.setPagoCuotaMinEnt(Respuesta.split(",")[17].trim());
                            infoTablaDetalle.setPagoCuotaMinCent(Respuesta.split(",")[18].trim());
                            infoTablaDetalle.setPagoTotalEnt(Respuesta.split(",")[19].trim());
                            infoTablaDetalle.setPagoTotalCent(Respuesta.split(",")[20].trim());


                            infoTablaDetalle.setNumeroCredito(Respuesta.split(",")[21].trim());
                            DatosPagoPrestamo.setDatosTablaDetalle(infoTablaDetalle);



                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final PrestamosDetalleController controller = new PrestamosDetalleController();
                                    controller.mostrarTablaDetalle(DatosPagoPrestamo.getDatosTablaDetalle());
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
                                    detalleOP.setDisable(false);
                                    pagarOP.setDisable(false);
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

        return servicePrestamoDetalle;

    }

    public class GetprestamosTask extends Service<ObservableList<infoTablaPagosPrest>> {

        @Override
        protected Task<ObservableList<infoTablaPagosPrest>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA listas prestamos
                    //850,038,connid,0345,1128425958,163B74E95B4A3209,~
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarPrestamo = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    /*CODIFICACION AUTOMATICA*/
                    final Runnable aFenix = new EnvioTipAutFenix(datosCliente.getCodemp(), "001");
                    final Thread aFen = new Thread(aFenix);
                    aFen.start();

                    tramaListarPrestamo.append("850,038,");
                    tramaListarPrestamo.append(datosCliente.getRefid());
                    tramaListarPrestamo.append(",");
                    tramaListarPrestamo.append(AtlasConstantes.COD_CONS_PRESTAMOS);
                    tramaListarPrestamo.append(",");
                    tramaListarPrestamo.append(datosCliente.getId_cliente());
                    tramaListarPrestamo.append(",");
                    tramaListarPrestamo.append(datosCliente.getContraseña());
                    tramaListarPrestamo.append(",");
                    tramaListarPrestamo.append(datosCliente.getTokenOauth());
                    tramaListarPrestamo.append(",~");



                    if (MarcoPrincipalController.newConsultaBloqTDc) {
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listarconsultaprestaos = " + "##" + gestorDoc.obtenerHoraActual());

//                            Respuesta = ""
//                                    + "850,"
//                                    + "038,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "001,"
//                                    + "AUDIOPRESTAMO_123456%2016/20/15%00000010000000%00%00000009511681%00%1994065900%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%000000000000%00%19940659%00000000012500%25;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO       %    /  /  %00000010000000%00%00000009511681%00%19940659%00000000000000%00;"
//                                    + "AUDIOPRESTAMO  OS   %2016/08/17%00000010000000%00%00000009511681%00%19940659%00000000000000%00,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarPrestamo.toString());




                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listrarconsultaprestamos = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarPrestamo.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (PrestamosController.cancelartarea.get()) {
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            failed();

                                        }
                                    }
                                });

                            }

                        }

                    } else {
                        Respuesta = "850,"
                                + "038,"
                                + "000,"
                                + ",~";

                    }


                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (PrestamosController.cancelartarea.get()) {
                            cancel();
                        } else {
                            if (MarcoPrincipalController.newConsultaPrestamo) {
                                //  tablaDatos.setItems(emptyObservableList);
                                prestamos = FXCollections.observableArrayList();

                                String[] Ltarjetas = Respuesta.split(",")[5].split(";");

                                for (int i = 0; i < Ltarjetas.length; i++) {
                                    String[] datos = Ltarjetas[i].split("%");
                                    final String Valorinicial = "$ " + formatonum.format(Double.parseDouble(datos[2])).replace(".", ",") + "." + datos[3];
                                    final String PagoMin = "$ " + formatonum.format(Double.parseDouble(datos[7])).replace(".", ",") + "." + datos[8];
                                    final String SaldoActual = "$ " + formatonum.format(Double.parseDouble(datos[4])).replace(".", ",") + "." + datos[5];
                                    final infoTablaPagosPrest tarjeta = new infoTablaPagosPrest(false, datos[6], datos[0].trim().toLowerCase(), datos[1].trim(), Valorinicial, PagoMin, SaldoActual);
                                    prestamos.add(tarjeta);
                                }

                                synchronized (this) {
                                    MarcoPrincipalController.newConsultaPrestamo = false;
                                }

                            }


                        }


                    } else {

                        if (PrestamosController.cancelartarea.get()) {
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



                    return prestamos;

                }
            };
        }
    }

    @FXML
    void gotoPagar(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                final DatosPagoPrestamo datosTablaDetalle = DatosPagoPrestamo.getDatosTablaDetalle();
                final List<infoTablaPagosPrest> listprestamos = new ArrayList<infoTablaPagosPrest>();
                for (Iterator<infoTablaPagosPrest> it = prestamos.iterator(); it.hasNext();) {
                    infoTablaPagosPrest next = it.next();

                    if (next.seleccionProperty().getValue()) {
                        listprestamos.add(next);

                    }

                }
                datosTablaDetalle.setPagoActual(datosTablaDetalle.getPagoActual() + 1);
                datosTablaDetalle.setSeleccionPagos(listprestamos);
                datosTablaDetalle.setOrigenPago(AtlasConstantes.PAGO_POR_LOTE);
                DatosPagoPrestamo.setDatosTablaDetalle(datosTablaDetalle);


                final PrestamosPagosController controller = new PrestamosPagosController();
                controller.mostrarPagoPrestamos(DatosPagoPrestamo.getDatosTablaDetalle(), AtlasConstantes.PAGO_POR_LOTE);

            }
        });

    }

    @FXML
    void cancelar(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    servicePrestamoDetalle.cancel();
                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }

                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    Logger.getLogger(PrestamosController.class.getName()).log(Level.SEVERE, null, ex);
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
}
