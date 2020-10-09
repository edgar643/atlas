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
import com.co.allus.modelo.tdcprepago.ModelTdcPrepago;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class BloqueoTDCprepIniController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<ModelTdcPrepago, String> bloqueo_tarjeta;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar_op;
    @FXML
    private TableColumn<ModelTdcPrepago, String> nomEmpresa;
    @FXML
    private TableColumn<ModelTdcPrepago, String> numero;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TableView<ModelTdcPrepago> tablaDatos;
    @FXML
    private TableColumn<ModelTdcPrepago, String> tipo_tarjeta;
    int currentpageindex = 0;
    private Pagination pagination = new Pagination();
    private Service<ObservableList<ModelTdcPrepago>> task = new BloqueoTDCprepIniController.GetTarjetasTask();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static ObservableList<ModelTdcPrepago> tarjetas = FXCollections.observableArrayList();
    public static HashMap<String, String> tarjetasOri = new HashMap<String, String>();
    private static GestorDocumental gestorDoc = new GestorDocumental();

    @FXML
    void cancelarOP(ActionEvent event) {
        try {

            BloqueoTDCprepIniController.cancelartarea.set(true);

        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        } finally {
            final Parent frameGnral = Atlas.vista.getScene().getRoot();
            final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
            final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
            pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
            final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
            arboltdcPrepago.setDisable(false);
            arboltdcPrepago.getSelectionModel().clearSelection();

            try {
                pane.getChildren().remove(3);

            } catch (Exception e) {
                gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
            }
        }
    }

    @FXML
    void continuarOP(ActionEvent event) {
        try {
            final ModelTdcPrepago seleccion = tablaDatos.getSelectionModel().getSelectedItem();
            final BloqueoTDCPrepConfirmController controller = new BloqueoTDCPrepConfirmController();
            controller.mostrarBloqueosTDCPconfirm(seleccion);

        } catch (Exception ex) {
            Logger.getLogger(BloqueoTDCPrepFinController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert bloqueo_tarjeta != null : "fx:id=\"bloqueo_tarjeta\" was not injected: check your FXML file 'BloqueoTDCprepIni.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'BloqueoTDCprepIni.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'BloqueoTDCprepIni.fxml'.";
        assert nomEmpresa != null : "fx:id=\"nomEmpresa\" was not injected: check your FXML file 'BloqueoTDCprepIni.fxml'.";
        assert numero != null : "fx:id=\"numero\" was not injected: check your FXML file 'BloqueoTDCprepIni.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'BloqueoTDCprepIni.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'BloqueoTDCprepIni.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'BloqueoTDCprepIni.fxml'.";
        assert tipo_tarjeta != null : "fx:id=\"tipo_tarjeta\" was not injected: check your FXML file 'BloqueoTDCprepIni.fxml'.";

        this.location = url;
        this.resources = rb;

        progreso.setVisible(false);

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {

                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });


        numero.setCellValueFactory(new PropertyValueFactory<ModelTdcPrepago, String>("numeroTarjeta"));
        tipo_tarjeta.setCellValueFactory(new PropertyValueFactory<ModelTdcPrepago, String>("tipoTarjeta"));
        bloqueo_tarjeta.setCellValueFactory(new PropertyValueFactory<ModelTdcPrepago, String>("bloqueoTarjeta"));
        nomEmpresa.setCellValueFactory(new PropertyValueFactory<ModelTdcPrepago, String>("nomEmpresa"));
        BloqueoTDCprepIniController.cancelartarea.set(false);

    }

    public void mostrarBloqueoTDCPrepago(final String menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/BloqueoTDCprepIni.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<ModelTdcPrepago> tablaData = (TableView<ModelTdcPrepago>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
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

                    botoncontinuarOp.setDisable(true);
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
                    tablaData.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tablaData.itemsProperty().unbind();
                   
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = tarjetas.size() % rowsPerPage() == 0 ? tarjetas.size() / rowsPerPage() : tarjetas.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {
                                  
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
                                                final List<ModelTdcPrepago> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<ModelTdcPrepago> subList = tarjetas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(3);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(50);
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

                                    final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                                    if (arboltdcPrepago != null) {
                                        arboltdcPrepago.setDisable(false);
                                    }
                                    //arboltdcPrepago
                                    arboltdcPrepago.getSelectionModel().clearSelection();
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
                            gestorDoc.imprimir("cancelo Tarea BloqueoTDC -->" + "  :" + gestorDoc.obtenerHoraActual());                              
                        }
                    });




                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }




            }
        });


    }

    public class GetTarjetasTask extends Service<ObservableList<ModelTdcPrepago>> {

        @Override
        protected Task<ObservableList<ModelTdcPrepago>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarTDC = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramaListarTDC.append("850,065,");
                    tramaListarTDC.append(datosCliente.getRefid());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(AtlasConstantes.COD_COD_CONS_TARJ_PREPAGO);
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getId_cliente());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append("1"); //tipoIdentificacion
                    tramaListarTDC.append(",");
                    tramaListarTDC.append("N"); // TIPO DE TARJETA
                    tramaListarTDC.append(",");
                    tramaListarTDC.append("7003"); // dinamico
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getContraseña());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getTokenOauth());
                    tramaListarTDC.append(",~");



                    if (MarcoPrincipalController.newConsultaBloqTDcf) {
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ LISTAR TARJETAS PREPAGO  = " + "##" + gestorDoc.obtenerHoraActual());

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                          

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ LISTAR TDC PREPAGO = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (BloqueoTDCprepIniController.cancelartarea.get()) {                                          
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

                        if (BloqueoTDCprepIniController.cancelartarea.get()) {
                            cancel();
                        } else {
                            
                            tarjetas = FXCollections.observableArrayList();

                            final String[] Ltarjetas = Respuesta.split(",")[5].split(";");



                            for (int i = 0; i < Ltarjetas.length; i++) {

                                String[] datos = Ltarjetas[i].split("##");

                                String tarjetaOrig = datos[0];
                                String tarjetaPIN = datos[0].substring(0, 6) + StringUtilities.rellenarDato(" ", datos[0].length() - 10, "*") + datos[0].substring(datos[0].length() - 4, datos[0].length());
                                tarjetasOri.put(tarjetaPIN, tarjetaOrig);

                                final ModelTdcPrepago tarjeta = new ModelTdcPrepago(
                                        tarjetaPIN,
                                        datos[1].trim(),
                                        datos[2].trim(),
                                        datos[3].trim());
                                tarjetas.add(tarjeta);
                            }

                        }

                    } else {

                        if (BloqueoTDCprepIniController.cancelartarea.get()) {
                            cancel();
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
