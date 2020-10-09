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
import com.co.allus.modelo.consultatrm.Datostrmhistorico;
import com.co.allus.modelo.consultatrm.Datosyearmonth;
import com.co.allus.modelo.consultatrm.infoTablaTRM;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
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
public class ConsultaTRMController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private RadioButton rbhistorico;
    @FXML
    private RadioButton rbhoy;
    @FXML
    private TableView<infoTablaTRM> tabla_datos;
    @FXML
    private Button terminar_trx;
    @FXML
    private TableColumn<infoTablaTRM, String> trm;
    @FXML
    private TableColumn<infoTablaTRM, String> valortrm;
    private transient Service<Void> serviceConsultaTRM;
    final GestorDocumental docs = new GestorDocumental();
    private Service<ObservableList<infoTablaTRM>> task = new ConsultaTRMController.GetTRMTask();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ConsultaTRM.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaTRM.fxml'.";
        assert rbhistorico != null : "fx:id=\"rbhistorico\" was not injected: check your FXML file 'ConsultaTRM.fxml'.";
        assert rbhoy != null : "fx:id=\"rbhoy\" was not injected: check your FXML file 'ConsultaTRM.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'ConsultaTRM.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ConsultaTRM.fxml'.";
        assert trm != null : "fx:id=\"trm\" was not injected: check your FXML file 'ConsultaTRM.fxml'.";
        assert valortrm != null : "fx:id=\"valortrm\" was not injected: check your FXML file 'ConsultaTRM.fxml'.";
        this.resources = rb;
        this.location = url;
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        valortrm.setCellValueFactory(new PropertyValueFactory("valortrm"));
        trm.setCellValueFactory(new PropertyValueFactory("trm"));




    }

    public void mostrarConsultaTRM() {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {


                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaTRM.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final RadioButton rbhistorico = (RadioButton) root.lookup("#rbhistorico");
                    final RadioButton rbhoy = (RadioButton) root.lookup("#rbhoy");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);
                    final TableView<infoTablaTRM> tabla_datos = (TableView<infoTablaTRM>) root.lookup("#tabla_datos");

//                  infoValidacionDatos dataVal = infoValidacionDatos.getDatosValidacion();




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
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

//                    final TreeView<String> arbol_informacion = (TreeView<String>) pane.lookup("#arbol_informacion");
//                    if (arbol_informacion != null) {
//                        arbol_informacion.setDisable(false);
//                    }


                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tabla_datos.itemsProperty().bind(task.valueProperty());
                    task.start();


                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("TERMINO TAREA");
                            tabla_datos.itemsProperty().unbind();


                            //final ObservableList<infoTablaValidacion> dataTabla = FXCollections.observableArrayList();
                            //final ObservableList<infoValidacionDatos> value = FXCollections.observableArrayList();       
                            tabla_datos.setItems(task.getValue());


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

                                    arbol_pagos.getSelectionModel().clearSelection();
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

    public class GetTRMTask extends Service<ObservableList<infoTablaTRM>> {

        @Override
        protected Task<ObservableList<infoTablaTRM>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    ObservableList<infoTablaTRM> infotablatrm = FXCollections.observableArrayList();
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder TramaConsultaTRM = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    //850,042,connid,0197,identificacion,requiereRespuesta( C ó T),periodo,claveHardware,~

                    TramaConsultaTRM.append("850,042,");
                    TramaConsultaTRM.append(datosCliente.getRefid());
                    TramaConsultaTRM.append(",");
                    TramaConsultaTRM.append(AtlasConstantes.COD_CONSULTA_TRM);
                    TramaConsultaTRM.append(",");
                    TramaConsultaTRM.append(datosCliente.getId_cliente());
                    TramaConsultaTRM.append(",");
                    TramaConsultaTRM.append("C");
                    TramaConsultaTRM.append(",");
                    TramaConsultaTRM.append(docs.obtenerFechaActualSinDia2());
                    TramaConsultaTRM.append(",");
                    TramaConsultaTRM.append(datosCliente.getContraseña());
                    TramaConsultaTRM.append(",");
                    TramaConsultaTRM.append(datosCliente.getTokenOauth());
                    TramaConsultaTRM.append(",~");

//                    System.out.println("Trama ValidacionDatos " + TramaConsultaTRM.toString());

                    if (MarcoPrincipalController.newConsultaTRM) {
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CONSULTA TRM = " + "##" + docs.obtenerHoraActual());
                            //850,042,000,TRANSACCION EXITOSA,trmhoy,trmdia01,....trmdia31,~
//                            Respuesta = "850,"
//                                    + "042,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "0295982000," //TRM HOY
//                                    + "0296982000,"
//                                    + "0296976000,"
//                                    + "0106924000,"
//                                    + "0116934000,"
//                                    + "0126944000,"
//                                    + "0136954000,"
//                                    + "0146964000,"
//                                    + "0156974000,"
//                                    + "0166984000,"
//                                    + "0176994000,"
//                                    + "0186937000,"
//                                    + "0196947000,"
//                                    + "0206968000,"
//                                    + "0216992000,"
//                                    + "0226955000,"
//                                    + "0236982000,"
//                                    + "0246982000,"
//                                    + "0256982000,"
//                                    + "0266982000,"
//                                    + "0276962000,"
//                                    + "0286982000,"
//                                    + "0123945000,"
//                                    + "0134982000,"
//                                    + "0145982000,"
//                                    + "0156982000,"
//                                    + "0167916000,"
//                                    + "0189915000,"
//                                    + "0234914000,"
//                                    + "0245913000,"
//                                    + "0256912000,"
//                                    + "0267911000,"
//                                    + "~";

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU7c, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU7c, TramaConsultaTRM.toString());
//                            System.out.println(" RESPUESTA Validacion Datos:" + Respuesta);
//                            Thread.sleep(2000);

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ CONSULTA TRM = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU7c, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU7c, TramaConsultaTRM.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ConsultaTRMController.cancelartarea.get()) {
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
                        Respuesta = "800,"
                                + "022,"
                                + "000,"
                                + ",~";

                    }
                    if ("000".equals(Respuesta.split(",")[2])) {


                        if (ConsultaTRMController.cancelartarea.get()) {
                            cancel();
                        } else {
                            if (MarcoPrincipalController.newConsultaTRM) {
                                String trm1 = Respuesta.split(",")[4];

                                infotablatrm.add(new infoTablaTRM("$" + formatonum.format(Double.parseDouble(trm1.substring(0, trm1.length() - 5))).replace(",", ".") + "," + trm1.substring(5, trm1.length()), "Hoy"));
//                                    synchronized (this) {
//                                        MarcoPrincipalController.newValidacionDatos = false;
//                                    }
                            }
                        }

                    } else {

                        if (ConsultaTRMController.cancelartarea.get()) {
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

                    return infotablatrm;

                }
            };
        }
    }

    @FXML
    void continuarOP(final ActionEvent event) {
        rbhoy.setSelected(false);
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ir a Detalle prestamo" + "##" + docs.obtenerHoraActual());
//                progreso.progressProperty().unbind();
//                progreso.setProgress(0);
//                progreso.setVisible(false);

            }
        });

        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario canceloir a Detalle prestamo" + "##" + docs.obtenerHoraActual());
//                progreso.progressProperty().unbind();
//                progreso.setProgress(0);
//                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
////            progreso.setVisible(true);
////            progreso.progressProperty().unbind();
////            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
//            progreso.setVisible(true);
//            progreso.progressProperty().unbind();
//            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }


    }

    public Service<Void> continuar_Op() {
        serviceConsultaTRM = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String Respuesta = new String();
                        final StringBuilder tramaconsultatrmdia = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        tramaconsultatrmdia.append("850,042,");
                        tramaconsultatrmdia.append(cliente.getRefid());
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append(AtlasConstantes.COD_CONSULTA_TRM);
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append(cliente.getId_cliente());
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append("C");
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append(docs.obtenerFechaActualSinDia2());
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append(cliente.getContraseña());
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append(cliente.getTokenOauth());
                        tramaconsultatrmdia.append(",~");



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Consulta TRM dia = " + "##" + docs.obtenerHoraActual());
                            //RespuestaConsMov = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, consultaMovAtlas.toString());
                            //850,042,000,TRANSACCION EXITOSA,trmhoy,trmdia01,....trmdia31,~
                            //,,,,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296912000,0297619000,0296982000,0297619000,0296982000,0296982000,0297619000,0297619000,0297619000,0296982000,0297619000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000, ,~

//                            Respuesta = "850,"
//                                    + "042,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "0295982000," //TRM HOY
//                                    + "0296982000,"
//                                    + "0296976000,"
//                                    + "0106924000,"
//                                    + "0116934000,"
//                                    + "0126944000,"
//                                    + "0136954000,"
//                                    + "0146964000,"
//                                    + "0156974000,"
//                                    + "0166984000,"
//                                    + "0176994000,"
//                                    + "0186937000,"
//                                    + "0196947000,"
//                                    + "0206968000,"
//                                    + "0216992000,"
//                                    + "0226955000,"
//                                    + "0236982000,"
//                                    + "0246982000,"
//                                    + "0256982000,"
//                                    + "0266982000,"
//                                    + "0276962000,"
//                                    + "0286982000,"
//                                    + "0123945000,"
//                                    + "0134982000,"
//                                    + "0145982000,"
//                                    + "0156982000,"
//                                    + "0167916000,"
//                                    + "0189915000,"
//                                    + "0234914000,"
//                                    + "0245913000,"
//                                    + "0256912000,"
//                                    + "0267911000,"
//                                    + "~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU7c, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU7c, tramaconsultatrmdia.toString());

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ ConsultaMovimientos = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                            // Thread.sleep(2000);
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ = " + tramaconsultatrmdia.toString() + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU7c, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU7c, tramaconsultatrmdia.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        cancel();
                                        // continuar_Op.cancel();
//                                        progreso.progressProperty().unbind();
//                                        progreso.setProgress(0);
//                                        progreso.setVisible(false);

                                    }
                                });

                            }
                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            final Datostrmhistorico datosTrmHistorico = Datostrmhistorico.getDatostrmhistorico();

                            try {
                                datosTrmHistorico.setValor1(Respuesta.split(",")[5]);
                                datosTrmHistorico.setValor2(Respuesta.split(",")[6]);
                                datosTrmHistorico.setValor3(Respuesta.split(",")[7]);
                                datosTrmHistorico.setValor4(Respuesta.split(",")[8]);
                                datosTrmHistorico.setValor5(Respuesta.split(",")[9]);
                                datosTrmHistorico.setValor6(Respuesta.split(",")[10]);
                                datosTrmHistorico.setValor7(Respuesta.split(",")[11]);
                                datosTrmHistorico.setValor8(Respuesta.split(",")[12]);
                                datosTrmHistorico.setValor9(Respuesta.split(",")[13]);
                                datosTrmHistorico.setValor10(Respuesta.split(",")[14]);
                                datosTrmHistorico.setValor11(Respuesta.split(",")[15]);
                                datosTrmHistorico.setValor12(Respuesta.split(",")[16]);
                                datosTrmHistorico.setValor13(Respuesta.split(",")[17]);
                                datosTrmHistorico.setValor14(Respuesta.split(",")[18]);
                                datosTrmHistorico.setValor15(Respuesta.split(",")[19]);
                                datosTrmHistorico.setValor16(Respuesta.split(",")[20]);
                                datosTrmHistorico.setValor17(Respuesta.split(",")[21]);
                                datosTrmHistorico.setValor18(Respuesta.split(",")[22]);
                                datosTrmHistorico.setValor19(Respuesta.split(",")[23]);
                                datosTrmHistorico.setValor20(Respuesta.split(",")[24]);
                                datosTrmHistorico.setValor21(Respuesta.split(",")[25]);
                                datosTrmHistorico.setValor22(Respuesta.split(",")[26]);
                                datosTrmHistorico.setValor23(Respuesta.split(",")[27]);
                                datosTrmHistorico.setValor24(Respuesta.split(",")[28]);
                                datosTrmHistorico.setValor25(Respuesta.split(",")[29]);
                                datosTrmHistorico.setValor26(Respuesta.split(",")[30]);
                                datosTrmHistorico.setValor27(Respuesta.split(",")[31]);
                                datosTrmHistorico.setValor28(Respuesta.split(",")[32]);
                                datosTrmHistorico.setValor29(Respuesta.split(",")[33]);
                                datosTrmHistorico.setValor30(Respuesta.split(",")[34]);
                                datosTrmHistorico.setValor31(Respuesta.split(",")[35]);

                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

                            Datostrmhistorico.setDatostrmhistorico(datosTrmHistorico);
                            Datosyearmonth datosyearmonth = Datosyearmonth.getDatosyearmonth();
                            datosyearmonth.setValoryear(docs.obtenerFechaActualSinDia());


                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final ConsultaTRMfinController controller = new ConsultaTRMfinController();
                                    controller.mostrarConsultaTRMfin(Datostrmhistorico.getDatostrmhistorico());
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
                                    //continuar_Op.cancel();
//                                    progreso.progressProperty().unbind();
//                                    progreso.setProgress(0);
//                                    progreso.setVisible(false);
                                }
                            });

                        }

                        return null;
                    }
                };


            }
        };

        return serviceConsultaTRM;
    }
}
