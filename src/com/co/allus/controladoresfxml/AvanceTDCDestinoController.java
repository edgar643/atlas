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
import com.co.allus.modelo.avancestdc.datosListarTarjetas;
import com.co.allus.modelo.avancestdc.modeloListarTarjetas;
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
public class AvanceTDCDestinoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelar_trx;

    @FXML
    private Button consultar_trx;

    @FXML
    private HBox menu_progreso;

    @FXML
    private StackPane panel_tabla;

    @FXML
    private ProgressBar progreso;

    @FXML
    private TableView<modeloListarTarjetas> tablaDatos;

    @FXML
    private TableColumn<modeloListarTarjetas, String> numero;

    @FXML
    private TableColumn<modeloListarTarjetas, String> tipo_tarjeta;

    @FXML
    private TableColumn<modeloListarTarjetas, String> bloqueo_tarjeta;

    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<modeloListarTarjetas>> task = new AvanceTDCDestinoController.GetTarjetasTask();
    public static ObservableList<modeloListarTarjetas> tarjetas = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert bloqueo_tarjeta != null : "fx:id=\"bloqueo_tarjeta\" was not injected: check your FXML file 'AvanceTDCDestino.fxml'.";
        assert cancelar_trx != null : "fx:id=\"cancelar_trx\" was not injected: check your FXML file 'AvanceTDCDestino.fxml'.";
        assert consultar_trx != null : "fx:id=\"consultar_trx\" was not injected: check your FXML file 'AvanceTDCDestino.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'AvanceTDCDestino.fxml'.";
        assert numero != null : "fx:id=\"numero\" was not injected: check your FXML file 'AvanceTDCDestino.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'AvanceTDCDestino.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'AvanceTDCDestino.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'AvanceTDCDestino.fxml'.";
        assert tipo_tarjeta != null : "fx:id=\"tipo_tarjeta\" was not injected: check your FXML file 'AvanceTDCDestino.fxml'.";

        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {
                    consultar_trx.setDisable(false);
                } else {
                    consultar_trx.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });
        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        numero.setCellValueFactory(new PropertyValueFactory<modeloListarTarjetas, String>("numeroTarjeta"));
        tipo_tarjeta.setCellValueFactory(new PropertyValueFactory<modeloListarTarjetas, String>("tipoTarjeta"));
        bloqueo_tarjeta.setCellValueFactory(new PropertyValueFactory<modeloListarTarjetas, String>("bloqueoTarjeta"));

        AvanceTDCDestinoController.cancelartarea.set(false);
    }

    public void mostrarAvanceTDCDestino() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/AvanceTDCDestino.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button consultar_op = (Button) root.lookup("#consultar_trx");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_trx");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final TableView<modeloListarTarjetas> tablaDatos = (TableView<modeloListarTarjetas>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

                    final DropShadow shadow = new DropShadow();
                    consultar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    consultar_op.setCursor(Cursor.HAND);
                                    consultar_op.setEffect(shadow);
                                }
                            });

                    consultar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    consultar_op.setCursor(Cursor.DEFAULT);
                                    consultar_op.setEffect(null);
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

                    consultar_op.setDisable(true);
                    label_menu.setVisible(false);

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaDatos.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
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
                                                final List<modeloListarTarjetas> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<modeloListarTarjetas> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaDatos.getItems().setAll(tarjetas.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= tarjetas.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : tarjetas.size())));
                                        }
                                    });

                                }
                            });

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

                                    final TreeView<String> arbol_infotdc = (TreeView<String>) pane.lookup("#arbol_infotdc");
                                    if (arbol_infotdc != null) {
                                        arbol_infotdc.setDisable(true);
                                    }

                                    arbol_infotdc.getSelectionModel().clearSelection();

                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception ex) {
                                        Logger.getLogger(AvanceTDCDestinoController.class.getName()).log(Level.SEVERE, null, ex);
                                        gestorDoc.imprimir("Warning -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
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
                } catch (Exception ex) {
                    Logger.getLogger(AvanceTDCDestinoController.class.getName()).log(Level.SEVERE, null, ex);
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });
    }

    public class GetTarjetasTask extends Service<ObservableList<modeloListarTarjetas>> {

        @Override
        protected Task<ObservableList<modeloListarTarjetas>> createTask() {
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

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());

                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listar tarjetas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                            //850,005,000,TRANSACCIÓN EXITOSA,~
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex1) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (AvanceTDCDestinoController.cancelartarea.get()) {
                                        cancel();
                                    } else {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        failed();
                                    }
                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {
                        if (AvanceTDCDestinoController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final ObservableList<modeloListarTarjetas> emptyObservableList = FXCollections.emptyObservableList();
                            tarjetas = FXCollections.observableArrayList();

                            String[] Ltarjetas = Respuesta.split(",")[4].split(";");

                            for (int i = 0; i < Ltarjetas.length; i++) {
                                String[] datos = Ltarjetas[i].split("##");
                                final modeloListarTarjetas tarjeta = new modeloListarTarjetas(datos[0], datos[1], datos[2]);
                                tarjetas.add(tarjeta);
                            }
                        }
                    } else {
                        if (AvanceTDCDestinoController.cancelartarea.get()) {
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
                    return tarjetas;
                }
            };
        }
    }

    @FXML
    void cancelar_op(ActionEvent event) {
        try {
            AvanceTDCDestinoController.cancelartarea.set(true);
        } catch (Exception ex) {
            Logger.getLogger(AvanceTDCDestinoController.class.getName()).log(Level.SEVERE, null, ex);
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        } finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Atlas.getVista().gotoPrincipal();
                    } catch (IOException ex) {
                        gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
                        Logger.getLogger(AvanceTDCDestinoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    @FXML
    void consultar_op(ActionEvent event) {
        try {
            final modeloListarTarjetas seleccion = tablaDatos.getSelectionModel().getSelectedItem();
            final datosListarTarjetas dataListarTarjetas = datosListarTarjetas.getDataListarTarjetas();
            dataListarTarjetas.setNumero(seleccion.getNumeroTarjeta());
            dataListarTarjetas.setTipoTarjeta(seleccion.getTipoTarjeta());
            dataListarTarjetas.setBloqueoTarjeta(seleccion.getBloqueoTarjeta());
            datosListarTarjetas.setDataListarTarjetas(dataListarTarjetas);
            final AvanceTDCDestinoFinController controller = new AvanceTDCDestinoFinController();
            controller.mostrarAvanceTDCDestinoFinController(datosListarTarjetas.getDataListarTarjetas(), 0);
        } catch (Exception ex) {
            Logger.getLogger(AvanceTDCDestinoController.class.getName()).log(Level.SEVERE, null, ex);
            gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());

        }
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

}
