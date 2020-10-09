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
import com.co.allus.modelo.tdcprepago.DatosConsultaGeneral;
import com.co.allus.modelo.tdcprepago.DatosSelectGral;
import com.co.allus.modelo.tpconsultageneral.InfoTablaConsultaGralFin;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class TPConsultaGeneralFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colInformacion;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private Button regresar_op;
    @FXML
    private TableView<InfoTablaConsultaGralFin> tabla_datos;
    private static GestorDocumental docs = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private Service<ObservableList<InfoTablaConsultaGralFin>> task = new TPConsultaGeneralFinController.GetConsultaGeneralTarjeta();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert colDescripcion != null : "fx:id=\"colDescripcion\" was not injected: check your FXML file 'TPConsultaGeneralFin.fxml'.";
        assert colInformacion != null : "fx:id=\"colInformacion\" was not injected: check your FXML file 'TPConsultaGeneralFin.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TPConsultaGeneralFin.fxml'.";
        assert regresar_op != null : "fx:id=\"regresar_op\" was not injected: check your FXML file 'TPConsultaGeneralFin.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TPConsultaGeneralFin.fxml'.";
        this.location = url;
        this.resources = rb;
        colDescripcion.setCellValueFactory(new PropertyValueFactory<InfoTablaConsultaGralFin, String>("Descripcion"));
        colInformacion.setCellValueFactory(new PropertyValueFactory<InfoTablaConsultaGralFin, String>("Informacion"));
    }

    @FXML
    void regresar_op(ActionEvent event) {

        try {
            if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {



                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        final TPConsultaGeneralController controller = new TPConsultaGeneralController();
                        controller.mostrarConsultaGral();
                    }
                });


            } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Label label_menu = (Label) pane.lookup("#label_saldos");

                        final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                        if (arboltdcPrepago != null) {
                            arboltdcPrepago.setDisable(false);
                        }

                        arboltdcPrepago.getSelectionModel().clearSelection();
                        label_menu.setVisible(true);

                        try {
                            pane.getChildren().remove(3);
                        } catch (Exception ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                        }



                    }
                });

            }

        } catch (Exception e) {
            Logger.getLogger(TPConsultaGeneralFinController.class.getName()).log(Level.WARNING, null, e);
        }
    }

    public void mostrarConsultaGralFinTarjeta(final DatosSelectGral datosTarjeta) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TPConsultaGeneralFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button continuar_op = (Button) root.lookup("#regresar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<InfoTablaConsultaGralFin> tabla_datos = (TableView<InfoTablaConsultaGralFin>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    // codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String nombreC = datosCliente.getNombreEmpresa();
                    final String info = nombreC + "\nNIT : " + datosCliente.getNitEmpresa() + "\n" + datosTarjeta.getNumTarj();
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

                    label_menu.setVisible(false);
                    tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);


                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tabla_datos.itemsProperty().bind(task.valueProperty());
                    task.reset();
                    task.start();

                    /**
                     * barra progreso
                     */
                    // Use binding to be notified whenever the data source chagnes
                    // task = new GetTarjetasTask();
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("ENTRO OK");

                            tabla_datos.itemsProperty().unbind();
                            ObservableList<InfoTablaConsultaGralFin> value = task.getValue();

                            tabla_datos.setItems(value);

                            Atlas.vista.show();
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("ENTRO FAILED");
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

                                    final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                                    if (arboltdcPrepago != null) {
                                        arboltdcPrepago.setDisable(false);
                                    }


                                    arboltdcPrepago.getSelectionModel().clearSelection();
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

    public class GetConsultaGeneralTarjeta extends Service<ObservableList<InfoTablaConsultaGralFin>> {

        @Override
        protected Task<ObservableList<InfoTablaConsultaGralFin>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final ObservableList<InfoTablaConsultaGralFin> dataTabla = FXCollections.observableArrayList();
                    final DatosConsultaGeneral datos = DatosConsultaGeneral.getDatosConsultaGral();
                    String Respuesta = new String();

                    final StringBuilder tramaconsultagral = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Cliente cliente = Cliente.getCliente();

//                        850,070,connid,7007,docid,tipoTarjeta,numtarjeta,clavehardware,~

                    tramaconsultagral.append("850,070,");
                    tramaconsultagral.append(cliente.getRefid());
                    tramaconsultagral.append(",");
                    tramaconsultagral.append(AtlasConstantes.COD_CONSULTA_GENERAL);
                    tramaconsultagral.append(",");
                    if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        tramaconsultagral.append(cliente.getId_cliente());
                    } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        tramaconsultagral.append(cliente.getNitEmpresa());
                    }
                    tramaconsultagral.append(",");
                    if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        tramaconsultagral.append("1"); // tipoID
                    } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        tramaconsultagral.append("3"); //tipoID
                    }
                    tramaconsultagral.append(",");
                    tramaconsultagral.append(Cliente.getCliente().getTipoClientePrepago());
                    tramaconsultagral.append(",");
                    tramaconsultagral.append(DatosSelectGral.getDataGral().getNumTarjsf());
                    tramaconsultagral.append(",");
                    tramaconsultagral.append(cliente.getContraseña());
                    tramaconsultagral.append(",~");

//                    System.out.println("TRAMA CONSULTA GENERAL :" + tramaconsultagral);


                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ consultagnral = " + "##" + docs.obtenerHoraActual());
                        // Respuesta="850;070;000;                                                                      ;N;0004198190021156289;EL CARIBE                     ;4198194066258044366;-6,020.00            ; 6,020.00            ;VISA PP REGALO                ;31;31/05/2017;E; ; ;20/12/2016;Y;0000000000;N;0000000000; .00                 ;0000000000; .00                 ;0;~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaconsultagral.toString());
//                        System.out.println(" RESPUESTA TRAMA CONSULTA GENERAL:" + Respuesta);
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ consultagnral = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaconsultagral.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    failed();

                                }
                            });

                        }

                    }

                    if ("000".equals(Respuesta.split(";")[2])) {

//                            tipotarjeta=""; 
//                            numtarjeta=""; 
//                            nombreEmpresa=""; 
//                            nunCuenta=""; 
//                            saldoDispo=""; 
//                            saldoDiaAnterior=""; 
//                            descProducto=""; 
//                            cicloFact=""; 
//                            fechaFacturacion=""; 
//                            bloqueoTarjeta=""; 
//                            bloqueoCuenta1=""; 
//                            bloqueoCuenta2=""; 
//                            fechaActivacion=""; 
//                            indicadorActivacion=""; 
//                            fechaActivacion=""; 
//                            indicadorActivacionViejo=""; 
//                            fechaUltimaRecarga=""; 
//                            valorUltimaRecarga=""; 
//                            fechaUltimaDescarga=""; 
//                            valorUltimaDescarga=""; 
//                            excentoGMF=""; 
                        datos.setTipotarjeta(Respuesta.split(";")[4].trim());
                        // datos.setNumtarjeta(StringUtilities.eliminarCerosLeft(Respuesta.split(";")[5].trim()));
                        datos.setNumtarjeta(DatosSelectGral.getDataGral().getNumTarj());
                        datos.setNombreEmpresa(Respuesta.split(";")[6].trim());
                        datos.setNumCuenta(Respuesta.split(";")[7].trim());
                        datos.setSaldoDispo("$" + Respuesta.split(";")[8].trim());
                        datos.setSaldoDiaAnterior("$" + Respuesta.split(";")[9].trim());
                        datos.setDescProducto(Respuesta.split(";")[10].trim());
                        datos.setCicloFact(Respuesta.split(";")[11].trim());

                        try {

                            datos.setFechaFacturacion(Respuesta.split(";")[12]);
                        } catch (Exception e) {
                            datos.setFechaFacturacion(Respuesta.split(";")[12]);
                        }



                        datos.setBloqueoTarjeta(Respuesta.split(";")[13].trim());
                        datos.setBloqueoCuenta1(Respuesta.split(";")[14].trim());
                        datos.setBloqueoCuenta2(Respuesta.split(";")[15].trim());

                        try {

                            datos.setFechaActivacion(Respuesta.split(";")[16]);
                        } catch (Exception e) {
                            datos.setFechaActivacion(Respuesta.split(";")[16]);
                        }


                        datos.setIndicadorActivacion(Respuesta.split(";")[17].trim());
                        datos.setFechaActivacionV(Respuesta.split(";")[18].trim());
                        datos.setIndicadorActivacionViejo(Respuesta.split(";")[19].trim());

                        try {

                            datos.setFechaUltimaRecarga(Respuesta.split(";")[20]);
                        } catch (Exception e) {
                            datos.setFechaUltimaRecarga(Respuesta.split(";")[20]);
                        }



                        datos.setValorUltimaRecarga("$" + Respuesta.split(";")[21]);

                        try {

                            datos.setFechaUltimaDescarga(Respuesta.split(";")[22]);
                        } catch (Exception e) {
                            datos.setFechaUltimaDescarga(Respuesta.split(";")[22]);
                        }


                        datos.setValorUltimaDescarga("$" + Respuesta.split(";")[23]);

                        datos.setExcentoGMF(Respuesta.split(";")[24].trim());



                        DatosConsultaGeneral.setDatosConsultaGral(datos);
                        DatosConsultaGeneral datosgral = DatosConsultaGeneral.getDatosConsultaGral();



                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.NOMBREEMPRESA, datosgral.getNombreEmpresa()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.NUMCUENTA, datosgral.getNumCuenta()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.NUMTARJETA, datosgral.getNumtarjeta()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.SALDODISPO, datosgral.getSaldoDispo()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.SALDODIAANTERIOR, datosgral.getSaldoDiaAnterior()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.DESCPRODUCTO, datosgral.getDescProducto()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.CICLOFACT, datosgral.getCicloFact()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAFACTURACION, datosgral.getFechaFacturacion()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.BLOQUEOTARJETA, datosgral.getBloqueoTarjeta()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.BLOQUEOCUENTA1, datosgral.getBloqueoCuenta1()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.BLOQUEOCUENTA2, datosgral.getBloqueoCuenta2()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAACTIVACION, datosgral.getFechaActivacion()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.INDICADORACTIVACION, datosgral.getIndicadorActivacion()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAACTIVACIONV, datosgral.getFechaActivacionV()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.INDICADORACTIVACIONVIEJO, datosgral.getIndicadorActivacionViejo()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAULTIMARECARGA, datosgral.getFechaUltimaRecarga()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.VALORULTIMARECARGA, datosgral.getValorUltimaRecarga()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAULTIMADESCARGA, datosgral.getFechaUltimaDescarga()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.VALORULTIMADESCARGA, datosgral.getValorUltimaDescarga()));
                        dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.EXCENTOGMF, datosgral.getExcentoGMF()));

                    } else {
                        final String coderror = Respuesta.split(";")[2];
                        final String mensaje = Respuesta.split(";")[3].trim();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                cancel();

                            }
                        });

                    }

                    return dataTabla;

                }
            };
        }
    }

    public void mostrarConsultaGralFin(final DatosConsultaGeneral datosgral) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
//                    System.out.println("entra fin genreral");
                    final URL location = getClass().getResource("/com/co/allus/vistas/TPConsultaGeneralFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#regresar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<InfoTablaConsultaGralFin> tabla_datos = (TableView<InfoTablaConsultaGralFin>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    // codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente() + "\n" + DatosSelectGral.getDataGral().getNumTarj();
                    cliente.setText("");
                    cliente.setText(info);

                    label_menu.setVisible(false);
                    tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<InfoTablaConsultaGralFin> dataTabla = FXCollections.observableArrayList();

                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.NOMBREEMPRESA, datosgral.getNombreEmpresa()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.NUMCUENTA, datosgral.getNumCuenta()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.NUMTARJETA, datosgral.getNumtarjeta()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.SALDODISPO, datosgral.getSaldoDispo()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.SALDODIAANTERIOR, datosgral.getSaldoDiaAnterior()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.DESCPRODUCTO, datosgral.getDescProducto()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.CICLOFACT, datosgral.getCicloFact()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAFACTURACION, datosgral.getFechaFacturacion()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.BLOQUEOTARJETA, datosgral.getBloqueoTarjeta()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.BLOQUEOCUENTA1, datosgral.getBloqueoCuenta1()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.BLOQUEOCUENTA2, datosgral.getBloqueoCuenta2()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAACTIVACION, datosgral.getFechaActivacion()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.INDICADORACTIVACION, datosgral.getIndicadorActivacion()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAACTIVACIONV, datosgral.getFechaActivacionV()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.INDICADORACTIVACIONVIEJO, datosgral.getIndicadorActivacionViejo()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAULTIMARECARGA, datosgral.getFechaUltimaRecarga()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.VALORULTIMARECARGA, datosgral.getValorUltimaRecarga()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.FECHAULTIMADESCARGA, datosgral.getFechaUltimaDescarga()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.VALORULTIMADESCARGA, datosgral.getValorUltimaDescarga()));
                    dataTabla.add(new InfoTablaConsultaGralFin(DatosConsultaGeneral.EXCENTOGMF, datosgral.getExcentoGMF()));

                    tabla_datos.setItems(dataTabla);







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
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);






                } catch (Exception e) {
//                    e.printStackTrace();
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });
    }
}
