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
import com.co.allus.modelo.transestadocuentas.infoEstadoCuentasInscritas;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
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
public class TransfEstadoCuentasInscritasController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoEstadoCuentasInscritas, String> colBanco;
    @FXML
    private TableColumn<infoEstadoCuentasInscritas, String> colEstado;
    @FXML
    private TableColumn<infoEstadoCuentasInscritas, String> colNumCuenta;
    @FXML
    private TableColumn<infoEstadoCuentasInscritas, String> colTipoCuenta;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoEstadoCuentasInscritas> tabla_datos;
    @FXML
    private Button terminar_trx;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private Service<ObservableList<infoEstadoCuentasInscritas>> task = new TransfEstadoCuentasInscritasController.GetcuentasTask();
    private static ObservableList<infoEstadoCuentasInscritas> cuentasInsc = FXCollections.observableArrayList();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert colBanco != null : "fx:id=\"colBanco\" was not injected: check your FXML file 'TransfEstadoCuentasInscritas.fxml'.";
        assert colEstado != null : "fx:id=\"colEstado\" was not injected: check your FXML file 'TransfEstadoCuentasInscritas.fxml'.";
        assert colNumCuenta != null : "fx:id=\"colNumCuenta\" was not injected: check your FXML file 'TransfEstadoCuentasInscritas.fxml'.";
        assert colTipoCuenta != null : "fx:id=\"colTipoCuenta\" was not injected: check your FXML file 'TransfEstadoCuentasInscritas.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TransfEstadoCuentasInscritas.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TransfEstadoCuentasInscritas.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'TransfEstadoCuentasInscritas.fxml'.";
        this.location = url;
        this.resources = rb;

        tabla_datos.setEditable(false);
        colBanco.setCellValueFactory(new PropertyValueFactory("colBanco"));
        colEstado.setCellValueFactory(new PropertyValueFactory("colEstado"));
        colNumCuenta.setCellValueFactory(new PropertyValueFactory("colNumCuenta"));
        colTipoCuenta.setCellValueFactory(new PropertyValueFactory("colTipoCuenta"));
        TransfEstadoCuentasInscritasController.cancelartarea.set(false);

    }

    @FXML
    void terminar_trx(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    Atlas.getVista().gotoPrincipal();

                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });
    }

    public void mostrarCuentasInscritas() {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {


                    final URL location = getClass().getResource("/com/co/allus/vistas/TransfEstadoCuentasInscritas.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final TableView<infoEstadoCuentasInscritas> tablaData = (TableView<infoEstadoCuentasInscritas>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);

                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);



                    final DropShadow shadow = new DropShadow();
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



                    try {
                        pane.getChildren().remove(3);

                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

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

//                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
//                    if (arbol_transf != null) {
//                        arbol_transf.setDisable(true);
//                    }

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

                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = cuentasInsc.size() % rowsPerPage() == 0 ? cuentasInsc.size() / rowsPerPage() : cuentasInsc.size() / rowsPerPage() + 1;


                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {

                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = cuentasInsc.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = cuentasInsc.size() / rowsPerPage();
                                        } else {
                                            lastIndex = cuentasInsc.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();



                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<infoEstadoCuentasInscritas> subList = cuentasInsc.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<infoEstadoCuentasInscritas> subList = cuentasInsc.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaData.getItems().setAll(cuentasInsc.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= cuentasInsc.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : cuentasInsc.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(3).setLayoutX(33);
                            root.getChildren().get(3).setLayoutY(70);
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




                                    arbol_transf.getSelectionModel().clearSelection();

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
                        }
                    });


                } catch (Exception e) {

                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

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

    public class GetcuentasTask extends Service<ObservableList<infoEstadoCuentasInscritas>> {

        @Override
        protected Task<ObservableList<infoEstadoCuentasInscritas>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarCuentasInsc = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    //ENVIO
                    //850,046,connid,codtransaccion,identificacion,indicadorctasACH(S ó N),indicadorCtasBanco(S ó N),claveHardware,~
                    tramaListarCuentasInsc.append("850,046,");
                    tramaListarCuentasInsc.append(datosCliente.getRefid());
                    tramaListarCuentasInsc.append(",");
                    tramaListarCuentasInsc.append(AtlasConstantes.COD_CUENTAS_INSCRITAS);
                    tramaListarCuentasInsc.append(",");
                    tramaListarCuentasInsc.append(datosCliente.getId_cliente());
                    tramaListarCuentasInsc.append(",");
                    tramaListarCuentasInsc.append("S");
                    tramaListarCuentasInsc.append(",");
                    tramaListarCuentasInsc.append("S");
                    tramaListarCuentasInsc.append(",");
                    tramaListarCuentasInsc.append(datosCliente.getContraseña());
                    tramaListarCuentasInsc.append(",");
                    tramaListarCuentasInsc.append(datosCliente.getTokenOauth());
                    tramaListarCuentasInsc.append(",~");


                    if (MarcoPrincipalController.newCuentasInscritas) {
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listar ctas inscritas = " + "##" + gestorDoc.obtenerHoraActual());
                            //RESPUESTA
                            //850,046,000,TRANSACCION EXTIOSA,numcta%tipoCta%banco%estadoCta%codigoBanco;.........,~
//                            Respuesta = ""
//                                    + "850,"
//                                    + "046,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "1234567899%AHORROS%Banco de Bogota%A%1;"
//                                    + "7894561236%CORIENTE%BANCOLOMBIA%A%2;"
//                                    + "1597538246%AHORROS%BANCOLOMBIA%Z%3;"
//                                    + "1593577532%CORRIENTE%AV Villas%A%4;"
//                                    + "8462791384%AHORROS%Banco de Bogota%Z%5;"
//                                    + "2548623212%AHORROS%BANCOLOMBIA%A%6;"
//                                    + "3578951234%CORRIENTE%BANCOLOMBIA%V%7;"
//                                    + "6548529731%CORRIENTE%Villas%A%8;"
//                                    + "2468123789%AHORROS%BANCOLOMBIA%V%9;"
//                                    + "7984561238%CORRIENTE%Banco de Bogota%A%10;"
//                                    + "5447879521%AHORROS%BANCOLOMBIA%A%11;"
//                                    + "3145678924%CORRIENTE%Villas%Z%12,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarCuentasInsc.toString());

                            // Thread.sleep(2000);
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listar ctas inscritas = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarCuentasInsc.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (TransfEstadoCuentasInscritasController.cancelartarea.get()) {

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
                                + "046,"
                                + "000,"
                                + ",~";

                    }
                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (TransfEstadoCuentasInscritasController.cancelartarea.get()) {
                            cancel();
                        } else {
                            if (MarcoPrincipalController.newCuentasInscritas) {
                                //  tablaDatos.setItems(emptyObservableList);
                                cuentasInsc = FXCollections.observableArrayList();
                                String[] Lcuentas = Respuesta.split(",")[4].split(";");
                                for (int i = 0; i < Lcuentas.length; i++) {
                                    String[] datos = Lcuentas[i].split("%");
                                    final infoEstadoCuentasInscritas cuenInsc = new infoEstadoCuentasInscritas(
                                            datos[0].trim(),
                                            datos[1].trim().equalsIgnoreCase("S") ? "ahorros" : datos[1].trim().equalsIgnoreCase("D") ? "corriente" : "",
                                            datos[2].trim(),
                                            datos[3].trim(),
                                            datos[4].trim());

                                    cuentasInsc.add(cuenInsc);

                                }

                                synchronized (this) {
                                    MarcoPrincipalController.newCuentasInscritas = false;
                                }
                            }
                        }
                    } else {

                        if (TransfEstadoCuentasInscritasController.cancelartarea.get()) {
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
                    return cuentasInsc;

                }
            };
        }
    }
}
