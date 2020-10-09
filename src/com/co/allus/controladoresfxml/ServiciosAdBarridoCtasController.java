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
import com.co.allus.modelo.serviciosad.infoTablaBarridoCtas;
import com.co.allus.utils.AtlasConstantes;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
public class ServiciosAdBarridoCtasController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTablaBarridoCtas, String> depur_reasig;
    @FXML
    private TableColumn<infoTablaBarridoCtas, String> est_depos;
    @FXML
    private TableColumn<infoTablaBarridoCtas, String> estado_act_tarjeta;
    @FXML
    private TableColumn<infoTablaBarridoCtas, String> fecha_hora;
    @FXML
    private TableColumn<infoTablaBarridoCtas, String> id_cliente;
    @FXML
    private TableColumn<infoTablaBarridoCtas, String> nombre_cte;
    @FXML
    private TableColumn<infoTablaBarridoCtas, String> num_cta;
    @FXML
    private TableColumn<infoTablaBarridoCtas, String> num_tarjeta;
    @FXML
    private TableColumn<infoTablaBarridoCtas, String> tipo_cta;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoTablaBarridoCtas> tablaDatos;
    @FXML
    private Button terminar_trx;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static AtomicBoolean cancelartareaSaldo = new AtomicBoolean();
    private Service<ObservableList<infoTablaBarridoCtas>> task = new ServiciosAdBarridoCtasController.GetBarridoCtasTask();
    private static ObservableList<infoTablaBarridoCtas> BarridoCtas = FXCollections.observableArrayList();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private SimpleDateFormat formatoBarridoCtas = new SimpleDateFormat("yyyyMMdd kkmmssSS");
    private SimpleDateFormat formatoformatoFecha = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert depur_reasig != null : "fx:id=\"depur_reasig\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert est_depos != null : "fx:id=\"est_depos\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert estado_act_tarjeta != null : "fx:id=\"estado_act_tarjeta\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert fecha_hora != null : "fx:id=\"fecha_hora\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert id_cliente != null : "fx:id=\"id_cliente\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert nombre_cte != null : "fx:id=\"nombre_cte\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert num_cta != null : "fx:id=\"num_cta\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert num_tarjeta != null : "fx:id=\"num_tarjeta\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert tipo_cta != null : "fx:id=\"tipo_cta\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";

        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ServiciosAdBarridoCtas.fxml'.";



        this.resources = rb;
        this.location = url;

        //  tablaDatos.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        id_cliente.setCellValueFactory(new PropertyValueFactory<infoTablaBarridoCtas, String>("id_cliente"));
        nombre_cte.setCellValueFactory(new PropertyValueFactory<infoTablaBarridoCtas, String>("nombre_cte"));
        num_tarjeta.setCellValueFactory(new PropertyValueFactory<infoTablaBarridoCtas, String>("num_tarjeta"));
        estado_act_tarjeta.setCellValueFactory(new PropertyValueFactory<infoTablaBarridoCtas, String>("estado_act_tarjeta"));
        num_cta.setCellValueFactory(new PropertyValueFactory<infoTablaBarridoCtas, String>("num_cta"));
        tipo_cta.setCellValueFactory(new PropertyValueFactory<infoTablaBarridoCtas, String>("tipo_cta"));
        est_depos.setCellValueFactory(new PropertyValueFactory<infoTablaBarridoCtas, String>("est_depos"));
        fecha_hora.setCellValueFactory(new PropertyValueFactory<infoTablaBarridoCtas, String>("fecha_hora"));
        depur_reasig.setCellValueFactory(new PropertyValueFactory<infoTablaBarridoCtas, String>("depur_reasig"));


        ServiciosAdBarridoCtasController.cancelartarea.set(false);
    }

    @FXML
    void aceptar(final ActionEvent event) {
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

    public void mostrarBarridoCuentas(final String menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ServiciosAdBarridoCtas.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                  
                    final Button botoncontinuarOp = (Button) root.lookup("#terminar_trx");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<infoTablaBarridoCtas> tablaData = (TableView<infoTablaBarridoCtas>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) frameGnral.lookup("#cliente");
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
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

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
                            final int numpag = BarridoCtas.size() % rowsPerPage() == 0 ? BarridoCtas.size() / rowsPerPage() : BarridoCtas.size() / rowsPerPage() + 1;

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
                                        int displace = BarridoCtas.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = BarridoCtas.size() / rowsPerPage();
                                        } else {
                                            lastIndex = BarridoCtas.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<infoTablaBarridoCtas> subList = BarridoCtas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<infoTablaBarridoCtas> subList = BarridoCtas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaData.getItems().setAll(BarridoCtas.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= BarridoCtas.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : BarridoCtas.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(3).setLayoutX(3);
                            root.getChildren().get(3).setLayoutY(30);
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

    public class GetBarridoCtasTask extends Service<ObservableList<infoTablaBarridoCtas>> {

        @Override
        protected Task<ObservableList<infoTablaBarridoCtas>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaBarridoCtas = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    tramaBarridoCtas.append("850,011,");
                    tramaBarridoCtas.append(datosCliente.getRefid());
                    tramaBarridoCtas.append(",");
                    tramaBarridoCtas.append(AtlasConstantes.COD_BARR_CTAS);
                    tramaBarridoCtas.append(",");
                    tramaBarridoCtas.append(datosCliente.getId_cliente());
                    tramaBarridoCtas.append(",");
                    tramaBarridoCtas.append(datosCliente.getContraseña());
                     tramaBarridoCtas.append(",");
                    tramaBarridoCtas.append(datosCliente.getTokenOauth());
                    tramaBarridoCtas.append(",~");

                    // System.out.println("trama BARRIDOCTAS" + tramaBarridoCtas.toString());


                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ barrido ctas= " + "##" + gestorDoc.obtenerHoraActual());
                        //   Respuesta = "850,011,000,TRANSACCION EXITOSA,6016609999654005##A##90654005031##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005032##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005033##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005034##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005035##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005036##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005037##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005038##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005039##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005040##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005041##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005042##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005043##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005044##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005045##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005046##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005047##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005048##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005049##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005050##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005051##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005052##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005053##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005054##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005055##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005056##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005057##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005058##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005059##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005060##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005101##C##A##20160215##08033400##DEPURACION  ;6016609999654005##A##90654005999##C##A##20160215##08033400##REASIGNACION;6016609999654005##A##90654005999##C##A##20160215##08033500##REASIGNACION;6016609999654005##A##90654005999##C##A##20160215##08033500##REASIGNACION;6016609999654005##A##90654005999##C##A##20160215##08365000##REASIGNACION;6016609999654005##A##90654005999##C##A##20160215##08365000##REASIGNACION;6016609999654005##A##90654005999##C##A##20160215##08365100##REASIGNACION;6016609999654005##A##90654005999##C##A##20160215##15130100##REASIGNACION,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBarridoCtas.toString());
//                        System.out.println("respuesta Lista : " + Respuesta);
                        // Thread.sleep(3000);

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ barrido ctas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBarridoCtas.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ServiciosAdBarridoCtasController.cancelartarea.get()) {
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

                        if (ServiciosAdBarridoCtasController.cancelartarea.get()) {
                            cancel();
                        } else {

                            if (MarcoPrincipalController.newConsultaBloqTDc) {
                                final ObservableList<infoTablaBarridoCtas> emptyObservableList = FXCollections.emptyObservableList();
                                //  tablaDatos.setItems(emptyObservableList);
                                BarridoCtas = FXCollections.observableArrayList();

                                String[] LBarridoCtas = Respuesta.split(",")[4].split(";");
                                final Cliente cliente = Cliente.getCliente();

                                for (int i = 0; i < LBarridoCtas.length; i++) {
                                    String[] datos = LBarridoCtas[i].split("##");
                                    String tarjeta = datos[0];
                                    try {
                                        tarjeta = tarjeta.substring(0, 6) + tarjeta.substring(6, tarjeta.length() - 4).replaceAll("[0-9]", "*") + tarjeta.substring(tarjeta.length() - 4, tarjeta.length());
                                    } catch (Exception e) {

//                                        e.printStackTrace();
                                        tarjeta = datos[0];
                                    }
                                    final StringBuilder fechaBtas = new StringBuilder();
                                    try {
                                        final Date parse = formatoBarridoCtas.parse(datos[5] + " " + datos[6]);
                                        fechaBtas.append(formatoformatoFecha.format(parse));

                                    } catch (Exception e) {
                                        fechaBtas.delete(0, fechaBtas.length());
                                        fechaBtas.append(datos[5]);
                                        fechaBtas.append(" ");
                                        fechaBtas.append(datos[6]);
                                    }



                                    final infoTablaBarridoCtas cuentasBarrido = new infoTablaBarridoCtas(cliente.getId_cliente(), cliente.getNombre(), tarjeta, datos[1], datos[2], datos[3].equalsIgnoreCase("C") ? "Corriente" : datos[3].equalsIgnoreCase("A") ? "Ahorros" : datos[3], datos[4], fechaBtas.toString(), datos[7]);
                                    BarridoCtas.add(cuentasBarrido);
                                }
                            }





                        }


                    } else {

                        if (ServiciosAdBarridoCtasController.cancelartarea.get()) {
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





                    return BarridoCtas;

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
