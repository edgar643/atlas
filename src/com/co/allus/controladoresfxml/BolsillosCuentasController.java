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
import com.co.allus.modelo.bolsillos.datosCuentasBolsillos;
import com.co.allus.modelo.bolsillos.modeloCuentasBolsillos;
import com.co.allus.utils.AtlasConstantes;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
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
public class BolsillosCuentasController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelar_trx;

    @FXML
    private Button continuar_trx;

    @FXML
    private HBox menu_progreso;

    @FXML
    private TableColumn<modeloCuentasBolsillos, String> tipo_cuenta;

    @FXML
    private TableColumn<modeloCuentasBolsillos, String> numero_cuenta;

    @FXML
    private TableColumn<modeloCuentasBolsillos, String> saldo_cuenta;

    @FXML
    private StackPane panel_tabla;

    @FXML
    private ProgressBar progreso;

    @FXML
    private TableView<modeloCuentasBolsillos> tablaDatos;

    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<modeloCuentasBolsillos>> task = new BolsillosCuentasController.GetCuentasTask();
    public static ObservableList<modeloCuentasBolsillos> cuentas = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancelar_trx != null : "fx:id=\"cancelar_trx\" was not injected: check your FXML file 'BolsillosCuentas.fxml'.";
        assert continuar_trx != null : "fx:id=\"continuar_trx\" was not injected: check your FXML file 'BolsillosCuentas.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'BolsillosCuentas.fxml'.";
        assert numero_cuenta != null : "fx:id=\"numero_cuenta\" was not injected: check your FXML file 'BolsillosCuentas.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'BolsillosCuentas.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'BolsillosCuentas.fxml'.";
        assert saldo_cuenta != null : "fx:id=\"saldo_cuenta\" was not injected: check your FXML file 'BolsillosCuentas.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'BolsillosCuentas.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'BolsillosCuentas.fxml'.";

        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {
                    continuar_trx.setDisable(false);
                } else {
                    continuar_trx.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });
        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tipo_cuenta.setCellValueFactory(new PropertyValueFactory<modeloCuentasBolsillos, String>("tipoCuenta"));
        numero_cuenta.setCellValueFactory(new PropertyValueFactory<modeloCuentasBolsillos, String>("numeroCuenta"));
        saldo_cuenta.setCellValueFactory(new PropertyValueFactory<modeloCuentasBolsillos, String>("saldoCuenta"));

        BolsillosCuentasController.cancelartarea.set(false);
    }    

    public void mostrarCuentasBolsillo(final String menu, final boolean isnuevaConsulta) {
    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            try {

                final URL location = getClass().getResource("/com/co/allus/vistas/BolsillosCuentas.fxml");
                final FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                final Button continuar_op = (Button) root.lookup("#continuar_trx");
                final Button cancelar_op = (Button) root.lookup("#cancelar_trx");
                final Label label_menu = (Label) pane.lookup("#label_saldos");

                final TableView<modeloCuentasBolsillos> tablaDatos = (TableView<modeloCuentasBolsillos>) root.lookup("#tablaDatos");
                final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                /// codigo para inyectar los datos                   
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

                continuar_op.setDisable(true);
                label_menu.setVisible(false);

                final TreeView<String> arbolBolsillos = (TreeView<String>) pane.lookup("#arbolBolsillos");
                if (arbolBolsillos != null) {
                    arbolBolsillos.setDisable(false);
                }
                arbolBolsillos.getSelectionModel().clearSelection();

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }
                pane.setAlignment(Pos.CENTER_LEFT);
                pane.add(root, 1, 0);

                // Use binding to be notified whenever the data source chagnes
                p.progressProperty().bind(task.progressProperty());
                veil.visibleProperty().bind(task.runningProperty());
                p.visibleProperty().bind(task.runningProperty());
                tablaDatos.itemsProperty().bind(task.valueProperty());
                task.start();

                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {

                        tablaDatos.itemsProperty().unbind();
                        /**
                         * configuracion de la paginacion
                         */
                        final int numpag = cuentas.size() % rowsPerPage() == 0 ? cuentas.size() / rowsPerPage() : cuentas.size() / rowsPerPage() + 1;

                        pagination = new Pagination(numpag, 0);
                        pagination.setPageFactory(new Callback<Integer, Node>() {
                            @Override
                            public Node call(final Integer pageIndex) {
                               
                                if (pageIndex > numpag) {
                                    return null;
                                } else {
                                    int lastIndex = 0;
                                    int displace = cuentas.size() % rowsPerPage();
                                    if (displace >= 0) {
                                        lastIndex = cuentas.size() / rowsPerPage();
                                    } else {
                                        lastIndex = cuentas.size() / rowsPerPage() - 1;
                                    }
                                    int page = pageIndex * itemsPerPage();

                                    for (int i = page; i < page + itemsPerPage(); i++) {

                                        if (lastIndex == pageIndex) {
                                            final List<modeloCuentasBolsillos> subList = cuentas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                            tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                        } else {
                                            final List<modeloCuentasBolsillos> subList = cuentas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                        tablaDatos.getItems().setAll(cuentas.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= cuentas.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : cuentas.size())));
                                    }
                                });
                            }
                        });

                        /**
                         * fin configuracion de la paginacion
                         */
                        root.getChildren().add(pagination);
                        root.getChildren().get(root.getChildren().size() - 1).setLayoutX(15);
                        root.getChildren().get(root.getChildren().size() - 1).setLayoutY(55);
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

                                final TreeView<String> arbolBolsillos = (TreeView<String>) pane.lookup("#arbolBolsillos");
                                if (arbolBolsillos != null) {
                                    arbolBolsillos.setDisable(false);
                                }
                                arbolBolsillos.getSelectionModel().clearSelection();
                                
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
                        gestorDoc.imprimir("Cancelo tarea Bolsillos -->" + "  :" + gestorDoc.obtenerHoraActual());
                    }
                });

            } catch (Exception e) {
                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
            }
        }
    });

    }

    public class GetCuentasTask extends Service<ObservableList<modeloCuentasBolsillos>> {

        @Override
        protected Task<ObservableList<modeloCuentasBolsillos>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaCuentasBolsillos = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    tramaCuentasBolsillos.append("850,078,");
                    tramaCuentasBolsillos.append(datosCliente.getRefid());
                    tramaCuentasBolsillos.append(",");
                    tramaCuentasBolsillos.append(AtlasConstantes.COD_BOLSILLOS_CONSULTA);
                    tramaCuentasBolsillos.append(",");
                    tramaCuentasBolsillos.append(datosCliente.getId_cliente());
                    tramaCuentasBolsillos.append(",");
                    tramaCuentasBolsillos.append("1");
                    tramaCuentasBolsillos.append(",");
                    tramaCuentasBolsillos.append(datosCliente.getContraseña());
                    tramaCuentasBolsillos.append(",");
                    tramaCuentasBolsillos.append(datosCliente.getTokenOauth());
                    tramaCuentasBolsillos.append(",~");

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ cuentas bolsillos = " + "##" + gestorDoc.obtenerHoraActual());
                          //850,078,000,0900 - TRANSACCION EXITOSA                                            ,,~   
                          Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCuentasBolsillos.toString());
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde MQ cuentas bolsillos = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ cuentas bolsillos = " + "##" + gestorDoc.obtenerHoraActual());
                            //850,078,000,0900 - TRANSACCION EXITOSA                                            ,,~ 
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCuentasBolsillos.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (BolsillosCuentasController.cancelartarea.get()) {
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

                        if (BolsillosCuentasController.cancelartarea.get()) {
                            cancel();
                        } else {

                            if (MarcoPrincipalController.newConsultaBolsillosCuentas) {
                                final ObservableList<modeloCuentasBolsillos> emptyObservableList = FXCollections.emptyObservableList();                                
                                cuentas = FXCollections.observableArrayList();

                                String[] Lbolsillo = Respuesta.split(",")[4].split(";");

                                for (int i = 0; i < Lbolsillo.length; i++) {
                                    String[] datos = Lbolsillo[i].split("%");
                                    String SaldoBolsillo = "$ " + formatonum.format(Double.parseDouble(datos[2].substring(0, datos[2].length() - 4))).replace(".", ",") + "." + datos[2].substring(datos[2].length() - 4, datos[2].length()).substring(0,2);
                                    final modeloCuentasBolsillos cuenta = new modeloCuentasBolsillos(datos[0], datos[1], SaldoBolsillo);
                                    cuentas.add(cuenta);
                                }
                            }
                        }
                    } else {
                        if (BolsillosCuentasController.cancelartarea.get()) {
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
                    return cuentas;
                }
            };
        }
    }
    
    @FXML
    void continuar_op(final ActionEvent event) {
        try {
            final modeloCuentasBolsillos seleccion = tablaDatos.getSelectionModel().getSelectedItem();
            final datosCuentasBolsillos dataCuentasBolsillos = datosCuentasBolsillos.getDataCuentasBolsillos();
            dataCuentasBolsillos.setTipoCuenta(seleccion.getTipoCuenta());
            dataCuentasBolsillos.setNumeroCuenta(seleccion.getNumeroCuenta());
            dataCuentasBolsillos.setSaldoCuenta(seleccion.getSaldoCuenta());
            datosCuentasBolsillos.setDataCuentasBolsillos(dataCuentasBolsillos);
            final BolsillosListarController controller = new BolsillosListarController();
            controller.mostrarListarBolsillo(datosCuentasBolsillos.getDataCuentasBolsillos());
        } catch (Exception e) {
             gestorDoc.imprimirExcepcion(e);
        }
    }

    @FXML
    void cancelar_op(final ActionEvent event) {
        try {
            BolsillosCuentasController.cancelartarea.set(true);
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
 
}
