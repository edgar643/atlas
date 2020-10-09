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
import com.co.allus.modelo.transestadocuentas.dataCuentasInscritas;
import com.co.allus.modelo.transestadocuentas.infoEstadoCuentasInscritas;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
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
 * @author alexander.lopez.o
 */
public class EliminacionCtasACHController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    private Button continuar;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoEstadoCuentasInscritas> tabla_datos;
    @FXML
    private Button terminar_trx;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private Service<ObservableList<infoEstadoCuentasInscritas>> task = new EliminacionCtasACHController.GetcuentasTask();
    private static ObservableList<infoEstadoCuentasInscritas> cuentasACHInsc = FXCollections.observableArrayList();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert colBanco != null : "fx:id=\"colBanco\" was not injected: check your FXML file 'eliminacionCtasACH.fxml'.";
        assert colEstado != null : "fx:id=\"colEstado\" was not injected: check your FXML file 'eliminacionCtasACH.fxml'.";
        assert colNumCuenta != null : "fx:id=\"colNumCuenta\" was not injected: check your FXML file 'eliminacionCtasACH.fxml'.";
        assert colTipoCuenta != null : "fx:id=\"colTipoCuenta\" was not injected: check your FXML file 'eliminacionCtasACH.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'eliminacionCtasACH.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'eliminacionCtasACH.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'eliminacionCtasACH.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'eliminacionCtasACH.fxml'.";

        tabla_datos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tabla_datos.getSelectionModel().getSelectedItem() != null) {

                    continuar.setDisable(false);
                } else {
                    continuar.setDisable(true);
                    tabla_datos.getSelectionModel().clearSelection();
                }
            }
        });
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colBanco.setCellValueFactory(new PropertyValueFactory("colBanco"));
        colEstado.setCellValueFactory(new PropertyValueFactory("colEstado"));
        colNumCuenta.setCellValueFactory(new PropertyValueFactory("colNumCuenta"));
        colTipoCuenta.setCellValueFactory(new PropertyValueFactory("colTipoCuenta"));
        EliminacionCtasACHController.cancelartarea.set(false);
        continuar.setDisable(true);

    }

    @FXML
    void continuarOP(final ActionEvent event) {
        infoEstadoCuentasInscritas selectedItem = tabla_datos.getSelectionModel().getSelectedItem();
        dataCuentasInscritas infoCtas = dataCuentasInscritas.getInfoCtas();
        infoCtas.setBanco(selectedItem.colBancoProperty().getValue());
        infoCtas.setNumcta(selectedItem.colNumCuentaProperty().getValue());
        infoCtas.setTipoCta(selectedItem.colTipoCuentaProperty().getValue());
        infoCtas.setEstado(selectedItem.colEstadoProperty().getValue());
        infoCtas.setCodigoBanco(selectedItem.codBancoProperty().getValue());
        dataCuentasInscritas.setInfoCtas(infoCtas);
        final EliminacionCtasAchConfController controller = new EliminacionCtasAchConfController();
        controller.mostrarMenuConfDatos(dataCuentasInscritas.getInfoCtas());

    }

    @FXML
    void terminar_trx(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    Atlas.getVista().gotoPrincipal();

                } catch (Exception ex) {
                    Logger.getLogger(DeclinacionesTDCController.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        });

    }

    public void mostrarELiminacionCuentasACH() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/eliminacionCtasACH.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final Button continuar = (Button) root.lookup("#continuar");
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

                    continuar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            continuar.setCursor(Cursor.HAND);
                            continuar.setEffect(shadow);
                        }
                    });

                    continuar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            continuar.setCursor(Cursor.DEFAULT);
                            continuar.setEffect(null);

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
//                            System.out.println("ENTRÓ TAREA");
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = cuentasACHInsc.size() % rowsPerPage() == 0 ? cuentasACHInsc.size() / rowsPerPage() : cuentasACHInsc.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {

                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = cuentasACHInsc.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = cuentasACHInsc.size() / rowsPerPage();
                                        } else {
                                            lastIndex = cuentasACHInsc.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<infoEstadoCuentasInscritas> subList = cuentasACHInsc.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<infoEstadoCuentasInscritas> subList = cuentasACHInsc.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaData.getItems().setAll(cuentasACHInsc.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= cuentasACHInsc.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : cuentasACHInsc.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(4).setLayoutX(33);
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
//                            System.out.println("cancelo la tarea");
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
                    tramaListarCuentasInsc.append("N");
                    tramaListarCuentasInsc.append(",");
                    tramaListarCuentasInsc.append(datosCliente.getContraseña());
                    tramaListarCuentasInsc.append(",");
                    tramaListarCuentasInsc.append(datosCliente.getTokenOauth());
                    tramaListarCuentasInsc.append(",~");

//                    System.out.println("trama LISTAR CTA INSCRITAS " + tramaListarCuentasInsc.toString());
                    if (MarcoPrincipalController.newCuentasACHInscritas) {
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ LISTAR CTAS INSCRITAS  = " + "##" + gestorDoc.obtenerHoraActual());
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
                            // Respuesta ="850,046,000,TRANSACCIÓN NO EFECTUADA                                              ,789524539        %S%BANCO DE BOGOTA%P%000001001;8662777133       %S%BANCO DE BOGOTA%A%000001001;8662777133       %S%BANCO SANTANDER%P%000001006;86627771337      %S%BANCO DE BOGOTA%P%000001001;3214569872086    %S%BANCO DAVIVIENDA%P%000001051;9876523690852    %S%BANCO AV VILLAS%P%000001052;10376262402020   %S%BANCO DE BOGOTA%P%000001001,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarCuentasInsc.toString());
                            //System.out.println("respuesta cuentas inscritas : " + Respuesta);
                            //Thread.sleep(2000);
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
//                            System.out.println("contingencia");
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ LISTAR CTAS INSCRITAS = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaListarCuentasInsc.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + gestorDoc.obtenerHoraActual());
                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (EliminacionCtasACHController.cancelartarea.get()) {
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
                                + "046,"
                                + "000,"
                                + ",~";

                    }
                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (EliminacionCtasACHController.cancelartarea.get()) {
                            cancel();
                        } else {
                            if (MarcoPrincipalController.newCuentasACHInscritas) {
                                //  tablaDatos.setItems(emptyObservableList);
                                cuentasACHInsc = FXCollections.observableArrayList();
                                String[] Lcuentas = Respuesta.split(",")[4].split(";");
                                for (int i = 0; i < Lcuentas.length; i++) {
                                    String[] datos = Lcuentas[i].split("%");
                                    final infoEstadoCuentasInscritas cuenInsc = new infoEstadoCuentasInscritas(
                                            datos[0].trim(),
                                            datos[1].trim().equalsIgnoreCase("S") ? "ahorros" : datos[1].trim().equalsIgnoreCase("D") ? "corriente" : "",
                                            datos[2].trim(),
                                            datos[3].trim(),
                                            datos[4].trim());

                                    cuentasACHInsc.add(cuenInsc);

                                }

                                synchronized (this) {
                                    MarcoPrincipalController.newCuentasACHInscritas = false;
                                }
                            }
                        }
                    } else {

                        if (EliminacionCtasACHController.cancelartarea.get()) {
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
                    return cuentasACHInsc;

                }
            };
        }
    }
}
