/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.empresasTDC.infoTablaEmpTDC;
import java.net.URL;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import com.co.allus.modelo.Cliente;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.modelo.informaciontdc.dataTablaTdc;
import com.co.allus.modelo.informaciontdc.infotablatdcbuscar;
import java.util.Date;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class EmpresasTDCController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn Col_Bloqueo;
    @FXML
    private TableColumn Col_Numero;
    @FXML
    private TableColumn Col_TipoTarj;
    @FXML
    private Button continuar_op;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TableView<infoTablaEmpTDC> tabla_datos;
    private static GestorDocumental docs = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<infoTablaEmpTDC>> task = new EmpresasTDCController.GetTDCTask();
    private static ObservableList<infoTablaEmpTDC> tdcemp = FXCollections.observableArrayList();
    private transient Service<Void> serviceInfoTdcFin;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        assert Col_Bloqueo != null : "fx:id=\"Col_Bloqueo\" was not injected: check your FXML file 'EmpresasTDC.fxml'.";
        assert Col_Numero != null : "fx:id=\"Col_Numero\" was not injected: check your FXML file 'EmpresasTDC.fxml'.";
        assert Col_TipoTarj != null : "fx:id=\"Col_TipoTarj\" was not injected: check your FXML file 'EmpresasTDC.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'EmpresasTDC.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'EmpresasTDC.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'EmpresasTDC.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'EmpresasTDC.fxml'.";
        this.resources = rb;
        this.location = url;
        continuar_op.setDisable(true);
        progreso.setVisible(false);
        Col_Numero.setCellValueFactory(new PropertyValueFactory<infoTablaEmpTDC, String>("col_Numero"));
        Col_TipoTarj.setCellValueFactory(new PropertyValueFactory<infoTablaEmpTDC, String>("col_TipoTarj"));
        Col_Bloqueo.setCellValueFactory(new PropertyValueFactory<infoTablaEmpTDC, String>("col_Bloqueo"));
        EmpresasTDCController.cancelartarea.set(false);

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
    }

    public void mostrarTDCEmpresas() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/EmpresasTDC.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);
                    final TableView<infoTablaEmpTDC> tabla_datos = (TableView<infoTablaEmpTDC>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");


                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();

                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setPrefSize(110, 110);
                    p.setLayoutX(230);
                    p.setLayoutY(190);

                    root.getChildren().addAll(veil, p);


//                    final DropShadow shadow = new DropShadow();
//                    continuar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    continuar_op.setCursor(Cursor.HAND);
//                                    continuar_op.setEffect(shadow);
//                                }
//                            });
//
//                    continuar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    continuar_op.setCursor(Cursor.DEFAULT);
//                                    continuar_op.setEffect(null);
//
//                                }
//                            });

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

                    final TreeView<String> arbol_comex = (TreeView<String>) pane.lookup("#arbol_comex");
                    if (arbol_comex != null) {
                        arbol_comex.setDisable(false);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }

                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);


                    // Use binding to be notified whenever the data source chagnes

                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

//                            System.out.println("TERMINO TAREA");

                            tabla_datos.itemsProperty().unbind();
                            final int numpag = tdcemp.size() % rowsPerPage() == 0 ? tdcemp.size() / rowsPerPage() : tdcemp.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {
                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = tdcemp.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = tdcemp.size() / rowsPerPage();
                                        } else {
                                            lastIndex = tdcemp.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<infoTablaEmpTDC> subList = tdcemp.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<infoTablaEmpTDC> subList = tdcemp.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tabla_datos.getItems().setAll(tdcemp.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= tdcemp.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : tdcemp.size())));
                                        }
                                    });

                                }
                            });
                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(6).setLayoutX(7);
                            root.getChildren().get(6).setLayoutY(154);
//                            System.out.println("tamaño del anchor ini " + root.getChildren().size());
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
//                            System.out.println("cancelo la tarea");
                        }
                    });
                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 5;
    }

    public class GetTDCTask extends Service<ObservableList<infoTablaEmpTDC>> {

        @Override
        protected Task<ObservableList<infoTablaEmpTDC>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramatdcempresas = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    tramatdcempresas.append("850,005,");
                    tramatdcempresas.append(datosCliente.getRefid());
                    tramatdcempresas.append(",");
                    tramatdcempresas.append(AtlasConstantes.COD_COD_CONS_TARJ);
                    tramatdcempresas.append(",");
                    tramatdcempresas.append(datosCliente.getId_cliente());
                    tramatdcempresas.append(",");
                    tramatdcempresas.append(formatoFecha.format(fecha));
                    tramatdcempresas.append(",");
                    tramatdcempresas.append(formatoHora.format(fecha));
                    tramatdcempresas.append(",");
                    tramatdcempresas.append("CRE");
                    tramatdcempresas.append(",");
                    tramatdcempresas.append(datosCliente.getContraseña());
                    tramatdcempresas.append(",");
                    tramatdcempresas.append(datosCliente.getTokenOauth());
                    tramatdcempresas.append(",~");





                    if (MarcoPrincipalController.newConsultaInfoTdc) {
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ LISTAR TDC = " + "##" + docs.obtenerHoraActual());
//                            Respuesta = "850,"
//                                    + "005,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "354852685895589##American Express Blue##A;"
//                                    + "452685785459825962##Master Card Clasica##L;"
//                                    + "5698521458525932##Visa Oro##L;"
//                                    + "1245847987545485##American Express Blue##L,"
//                                    + "~";

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramatdcempresas.toString());
                            //System.out.println(" RESPUESTA TRAMA TDC EMPRESAS:" + Respuesta);


                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " +StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+ "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ LISTAR TDC = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramatdcempresas.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (EmpresasTDCController.cancelartarea.get()) {
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
                                + "005,"
                                + "000,"
                                + ",~";
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (EmpresasTDCController.cancelartarea.get()) {
                            cancel();
                        } else {
                            if (MarcoPrincipalController.newConsultaInfoTdc) {
                                tdcemp = FXCollections.observableArrayList();
                                //tabla_datos.getItems();
                                //tfNit.setText(token.get(0).nit().getValue());
                                String[] Ltoken = Respuesta.split(",")[4].split(";");

                                for (int i = 0; i < Ltoken.length; i++) {
                                    String[] datos = Ltoken[i].split("##");
                                    final infoTablaEmpTDC data = new infoTablaEmpTDC(
                                            datos[0].trim(),
                                            datos[1].trim(),
                                            datos[2].trim());
                                    tdcemp.add(data);
                                }
                                synchronized (this) {
                                    MarcoPrincipalController.newConsultaInfoTdc = false;
                                }
                            }
                        }

                    } else {

                        if (EmpresasTDCController.cancelartarea.get()) {
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
                    return tdcemp;

                }
            };
        }
    }

    @FXML
    void continuarOP(final ActionEvent event) {

        infoTablaEmpTDC selectedItem = tabla_datos.getSelectionModel().getSelectedItem();
        dataTablaTdc infotabla = dataTablaTdc.getDataTablaTdc();
        infotabla.setNum_tdc(selectedItem.getCol_Numero());
        dataTablaTdc.setDataTablaTdc(infotabla);


        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono detalle comex" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo detalle comex" + "##" + docs.obtenerHoraActual());
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
        serviceInfoTdcFin = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramainfotdcfin = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        dataTablaTdc infotabla = dataTablaTdc.getDataTablaTdc();
                        //850,048,connid,codtransaccion,identificacion,numtarjeta,mesdia,clavehardware,~

                        tramainfotdcfin.append("850,048,");
                        tramainfotdcfin.append(datosCliente.getRefid());
                        tramainfotdcfin.append(",");
                        tramainfotdcfin.append(AtlasConstantes.COD_MOV_DIA);
                        tramainfotdcfin.append(",");
                        tramainfotdcfin.append(datosCliente.getId_cliente());
                        tramainfotdcfin.append(",");
                        tramainfotdcfin.append(infotabla.getNum_tdc());
                        tramainfotdcfin.append(",");
                        tramainfotdcfin.append(docs.obtenerFechaActualSinY());
                        tramainfotdcfin.append(",");
                        tramainfotdcfin.append(datosCliente.getContraseña());
                        tramainfotdcfin.append(",");
                        tramainfotdcfin.append(datosCliente.getTokenOauth());
                        tramainfotdcfin.append(",~");



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ INFO TDC = " + "##" + docs.obtenerHoraActual());
                            //,fecha%canaltran%codigotran%valor%horaTran%aprobaciontransaccion%estado%paisTran%ENTIDAD;......;~
//                            Respuesta = "850,"
//                                    + "048,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "1205%MIN%226%3039123%141600%0% %US%UNIVERSITY INN%ON2;"
//                                    + "1204%MIN%226%3039123%141600%0% %US%UNIVERSITY INN%ON2;"
//                                    + "1203%MIN%226%4039456%141600%0% %US%UNIVERSITY INN%ON2;"
//                                    + "1202%MIN%226%5039789%141600%0% %US%UNIVERSITY INN%ON2,"
//                                   
////                                    + "MIN%13:10.29%26/10/2016%3.039,20%226%0%TC%US%THE PORCHES%ON2;"
////                                    + "MIN%19:00:00%27/10/2016%3.039,20%226%0%TC%US%THE PORCHES%ON2,"
//                                    + "~";
//
//                            System.out.println(" RESPUESTA TRAMA INFO TDC FIN:" + Respuesta);
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramainfotdcfin.toString());

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ INFO TDC= " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramainfotdcfin.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

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


                            String datab = Respuesta.split(",")[4];

                            final List<infotablatdcbuscar> listabuscar = new ArrayList<infotablatdcbuscar>();
                            String[] detalle = datab.split(";");
                            for (int i = 0; i < detalle.length; i++) {

                                final String[] datosbuscar = detalle[i].split("%");
                                final String Fecha = datosbuscar[0].substring(0, datosbuscar[0].length() - 2) + "/" + datosbuscar[0].substring(datosbuscar[0].length() - 2);
                                final String Valor = "$ " + formatonum.format(Double.parseDouble(datosbuscar[3].substring(0, datosbuscar[3].length() - 2))).replace(".", ",") + "." + datosbuscar[3].substring(datosbuscar[3].length() - 2);
                                final infotablatdcbuscar dataDet = new infotablatdcbuscar(
                                        datosbuscar[1].trim(),
                                        datosbuscar[4].trim(),
                                        Fecha,
                                        Valor,
                                        datosbuscar[2].trim(),
                                        datosbuscar[5].trim(),
                                        "",
                                        datosbuscar[7].trim(),
                                        datosbuscar[8].trim(),
                                        "");

                                //Fecha,
                                // datosbuscar[1].trim(),
                                // datosbuscar[2].trim(),
                                // Valor,
                                // datosbuscar[4].trim(),
                                //datosbuscar[5].trim(),
                                // "",
                                // datosbuscar[7].trim(),
                                // datosbuscar[8].trim(),
                                // "");

                                listabuscar.add(dataDet);


                            }





                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
//                                    System.out.println("ENTRO A MOSTRAR");
                                    final EmpresasTDCFinController controller = new EmpresasTDCFinController();
                                    controller.mostrarTDCEmpresasFin(listabuscar, dataTablaTdc.getDataTablaTdc());


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

        return serviceInfoTdcFin;

    }
}
