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
import com.co.allus.modelo.declinacionestdc.DatosTDCDeclinacion;
import com.co.allus.modelo.declinacionestdc.modeloListarTarjeta;
import com.co.allus.tareas.BusqTdcDeclinacion;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
public class DeclinacionesTDCController implements Initializable {

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
    private TableColumn<modeloListarTarjeta, String> numero;

    @FXML
    private TableColumn<modeloListarTarjeta, String> tipo_tarjeta;

    @FXML
    private TableColumn<modeloListarTarjeta, String> bloqueo_tarjeta;

    @FXML
    private Label LabelNumeroTDC;

    @FXML
    private RestrictiveTextField NumeroTDC;

    @FXML
    private Button cancelar_trx;

    @FXML
    private Button declinar_trx;

    @FXML
    private HBox menu_progreso;

    @FXML
    private StackPane panel_tabla;

    @FXML
    private ProgressBar progreso;

    @FXML
    private TableView<modeloListarTarjeta> tablaDatos;

    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
//    private transient Service<Void> serviceDeclinacionTDC = declinar_trx();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
//    private static AtomicBoolean cancelartareaDeclinacion = new AtomicBoolean();
    private Service<ObservableList<modeloListarTarjeta>> task = new DeclinacionesTDCController.GetTarjetasTask();
    public static ObservableList<modeloListarTarjeta> tarjetas = FXCollections.observableArrayList();
//    public static ObservableList<modeloListarTarjeta> registros = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert LabelNumeroTDC != null : "fx:id=\"LabelNumeroTDC\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert NumeroTDC != null : "fx:id=\"NumeroTDC\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert bloqueo_tarjeta != null : "fx:id=\"bloqueo_tarjeta\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert cancelar_trx != null : "fx:id=\"cancelar_trx\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert declinar_trx != null : "fx:id=\"declinar_trx\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert numero != null : "fx:id=\"numero\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";
        assert tipo_tarjeta != null : "fx:id=\"tipo_tarjeta\" was not injected: check your FXML file 'DeclinacionesTDC.fxml'.";

        LabelNumeroTDC.setVisible(false);
        NumeroTDC.setVisible(false);

        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {

                    declinar_trx.setDisable(false);
                } else {
                    declinar_trx.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });
        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        numero.setCellValueFactory(new PropertyValueFactory<modeloListarTarjeta, String>("numeroTarjeta"));
        tipo_tarjeta.setCellValueFactory(new PropertyValueFactory<modeloListarTarjeta, String>("tipoTarjeta"));
        bloqueo_tarjeta.setCellValueFactory(new PropertyValueFactory<modeloListarTarjeta, String>("bloqueoTarjeta"));

        DeclinacionesTDCController.cancelartarea.set(false);
//        DeclinacionesTDCController.cancelartareaDeclinacion.set(false);

    }

    public void mostrarDeclinacionesTdc(final String menu, final boolean isnuevaConsulta) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/DeclinacionesTDC.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button declinar_op = (Button) root.lookup("#declinar_trx");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_trx");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final TableView<modeloListarTarjeta> tablaDatos = (TableView<modeloListarTarjeta>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    // final BigDecimal dato = new BigDecimal((datosCliente.getNombre().length() / 2d));
                    // final BigDecimal setScale = dato.setScale(0, BigDecimal.ROUND_UP);
                    // final String info = datosCliente.getNombre() + "\n" + StringUtilities.rellenarDato(" ", setScale.intValue(), " ") + "C.C: " + datosCliente.getId_cliente();
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

                    final DropShadow shadow = new DropShadow();
                    declinar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            declinar_op.setCursor(Cursor.HAND);
                            declinar_op.setEffect(shadow);
                        }
                    });

                    declinar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            declinar_op.setCursor(Cursor.DEFAULT);
                            declinar_op.setEffect(null);

                        }
                    });

                    cancelar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            cancelar_op.setCursor(Cursor.HAND);
                            cancelar_op.setEffect(shadow);
                        }
                    });

                    cancelar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            cancelar_op.setCursor(Cursor.DEFAULT);
                            cancelar_op.setEffect(null);

                        }
                    });

                    declinar_op.setDisable(true);
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
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    // Use binding to be notified whenever the data source chagnes
                    // task = new GetTarjetasTask();
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaDatos.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
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
                                                final List<modeloListarTarjeta> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<modeloListarTarjeta> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaDatos.getItems().setAll(tarjetas.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= tarjetas.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : tarjetas.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(10);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(95);
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

                                    final TreeView<String> arbol_declinacionestdc = (TreeView<String>) pane.lookup("#arbolDeclinacionesTdc");
                                    if (arbol_declinacionestdc != null) {
                                        arbol_declinacionestdc.setDisable(false);
                                    }

                                    arbol_declinacionestdc.getSelectionModel().clearSelection();

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
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

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

                    if (MarcoPrincipalController.newConsultaDeclinacionTDC) {
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());        
                            //850,005,000,TRANSACCIÓN EXITOSA,~
                            // Thread.sleep(3000);
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
//                            System.out.println("contingencia");
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (DeclinacionesTDCController.cancelartarea.get()) {
//                                            System.out.println("cancelo tarea");
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
                                + "002,"
                                + "000,"
                                + ",~";

                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (DeclinacionesTDCController.cancelartarea.get()) {
                            cancel();
                        } else {

                            if (MarcoPrincipalController.newConsultaDeclinacionTDC) {
                                final ObservableList<modeloListarTarjeta> emptyObservableList = FXCollections.emptyObservableList();
                                //  tablaDatos.setItems(emptyObservableList);
                                tarjetas = FXCollections.observableArrayList();

                                String[] Ltarjetas = Respuesta.split(",")[4].split(";");

                                for (int i = 0; i < Ltarjetas.length; i++) {
                                    String[] datos = Ltarjetas[i].split("##");
                                    final modeloListarTarjeta tarjeta = new modeloListarTarjeta(datos[0], datos[1], datos[2]);
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

                        if (DeclinacionesTDCController.cancelartarea.get()) {
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

                    // updateProgress(500, 500);
                    return tarjetas;
                }
            };
        }
    }

    @FXML
    void cancelar_op(final ActionEvent event) {
        try {

            DeclinacionesTDCController.cancelartarea.set(true);
        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        } finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Atlas.getVista().gotoPrincipal();
                    } catch (IOException ex) {
                        gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
                    }
                }
            });
        }
    }

    @FXML
    void declinar_op(final ActionEvent event) {
        try {
            //Declinacion1
            final modeloListarTarjeta seleccion = tablaDatos.getSelectionModel().getSelectedItem();
            final DatosTDCDeclinacion datadeclinacion = DatosTDCDeclinacion.getDatadeclinacion();
            datadeclinacion.setNumero(seleccion.getNumeroTarjeta());
            datadeclinacion.setTipoTarjeta(seleccion.getTipoTarjeta());
            datadeclinacion.setBloqueoTarjeta(seleccion.getBloqueoTarjeta());
            DatosTDCDeclinacion.setDatadeclinacion(datadeclinacion);
            //System.out.println("Tarjeta :" + seleccion.getNumeroTarjeta());
            //System.out.println("Franquicia :" + seleccion.getTipoTarjeta());
            //System.out.println("Bloqueo Tarjeta :" + seleccion.getBloqueoTarjeta());
            final DeclinacionesTDCConfirmController controller = new DeclinacionesTDCConfirmController();
            controller.mostrarDeclinacionesTDCConfirm(DatosTDCDeclinacion.getDatadeclinacion());
            //Declinacion2
//            final DatosTDCDeclinacion datadeclinacion = DatosTDCDeclinacion.getDatadeclinacion();
//            DatosTDCDeclinacion.setDatadeclinacion(datadeclinacion);
//            final DeclinacionesTDCConfirmController controller = new DeclinacionesTDCConfirmController();
//            controller.mostrarDeclinacionesTDCConfirm();
        } catch (Exception ex) {
            Logger.getLogger(DeclinacionesTDCController.class.getName()).log(Level.SEVERE, ex.toString());

        }
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
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
    void NumeroTDCkeyTyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {
            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {
                    BusquedaTDC(NumeroTDC.getText() + event.getCharacter());
                }
            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (NumeroTDC.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {
                            BusquedaTDC("");
                        }
                    } else {
                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {
                            BusquedaTDC(NumeroTDC.getText());
                        }
                    }
                }
            }
        }
    }

    @FXML
    void NumeroTDCkeypress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {
            KeyEvent keyEvent = KeyEvent.impl_keyEvent(NumeroTDC, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            NumeroTDC.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {
            event.consume();
        }
    }

    private void BusquedaTDC(final String NumTdc) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<modeloListarTarjeta>> TaskTablaId = new BusqTdcDeclinacion(NumTdc);

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
                            final ObservableList<modeloListarTarjeta> TablaID = TaskTablaId.getValue();

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

                                } catch (Exception ex) {
                                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(3);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(70);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
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

}
