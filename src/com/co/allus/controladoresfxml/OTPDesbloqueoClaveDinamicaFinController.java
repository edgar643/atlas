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
import com.co.allus.modelo.desbloqueoCvDinam.TablaBloqueosCvDinam;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.EstadoSgdaClave;
import com.co.allus.utils.StringUtilities;
import com.co.allus.utils.TipoCteGde;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
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
public class OTPDesbloqueoClaveDinamicaFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private Button terminar_trx;
    @FXML
    private TableView<TablaBloqueosCvDinam> tabla_datos;
    @FXML
    private TableColumn<TablaBloqueosCvDinam, String> FechaHora_desbloq;
    private static GestorDocumental docs = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static ObservableList<TablaBloqueosCvDinam> desbloqdinamic = FXCollections.observableArrayList();
    private Service<ObservableList<TablaBloqueosCvDinam>> task = new OTPDesbloqueoClaveDinamicaFinController.GetOTPDesTask();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'OTPDesbloqueoClaveDinamicaFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'OTPDesbloqueoClaveDinamicaFin.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'OTPDesbloqueoClaveDinamicaFin.fxml'.";
        assert FechaHora_desbloq != null : "fx:id=\"FechaHora_desbloq\" was not injected: check your FXML file 'OTPDesbloqueoClaveDinamicaFin.fxml'.";
        this.location = url;
        this.resources = rb;
        OTPDesbloqueoClaveDinamicaFinController.cancelartarea.set(false);
        FechaHora_desbloq.setCellValueFactory(new PropertyValueFactory<TablaBloqueosCvDinam, String>("FechaHora_desbloq"));
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    @FXML
    void terminar_trx(ActionEvent event) {

        final ConectSS servicioSS = new ConectSS();
        final TipoCteGde tipoClte = new TipoCteGde();
        final Cliente cliente = Cliente.getCliente();
        final EstadoSgdaClave estado2da = new EstadoSgdaClave();
        /*Aqui debe traer regla de negocio*/
        String EstadoClave = "ACTA";

        try {

            final ArrayList<String> estado = estado2da.codigosProd.get(cliente.getTipo_clinete_gtel());
            if (estado != null) {
                EstadoClave = estado.get(0);
            }

            final String RetornarTipoClienteGde = tipoClte.RetornarTipoClienteGde("01010",//cliente.getEstado_opt(),
                    //cliente.getTipo_otp(),//"ODA",//datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("STK")? "STK": (datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("SMS")|| datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("EML")) ? "ODA":"",
                    cliente.getTipo_otp(),//"ODA",//datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("STK")? "STK": (datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("SMS")|| datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("EML")) ? "ODA":"",
                    cliente.getTiene_otp(),
                    cliente.getTipo_oda(),//datosSeguridad.getCambio_mecanismo(), tipoODA // SMS O EML
                    cliente.getOtp_servicio(),
                    cliente.getOtp_antitramites(),
                    EstadoClave,
                    cliente.getbDMigracion() + ",",
                    cliente.getControlDual() + ",",
                    "0"); //

            final StringBuilder tramaAFenix = new StringBuilder();
            tramaAFenix.append("%300%");
            tramaAFenix.append(RetornarTipoClienteGde + " %");
            tramaAFenix.append(" %"); // siempre vacio
            tramaAFenix.append(" %");
            tramaAFenix.append("01010%"); // EstadoOTP
            tramaAFenix.append(" %"); // tipoODA // SMS O EML
            tramaAFenix.append(" %"); // tipoOTP
            tramaAFenix.append(" %"); // Otp Antitramite
            tramaAFenix.append(" %"); // OTP Servicio
            tramaAFenix.append(" %"); // Tiene OTP            
            tramaAFenix.append(" %"); // OPCMenu //

//            System.out.println("Datos:"+RetornarTipoClienteGde);
//            System.out.println("Datos:");
            docs.imprimir("Envio Fenix --> " + tramaAFenix.toString());
            servicioSS.enviarRecibirServidorsoftphone(AtlasConstantes.HOST_FENIX, AtlasConstantes.PUERTO_FENIX, tramaAFenix.toString());

        } catch (Exception ex) {

            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new ModalMensajes("Error en la comunicacion con  Softphone\n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                }
            });
        } finally {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Atlas.getVista().gotoPrincipal();
                    } catch (Exception e) {
                        docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                    }
                }
            });
        }
    }

    public void mostrarDesbloqueOTPfin() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/OTPDesbloqueoClaveDinamicaFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<TablaBloqueosCvDinam> tabla_datos = (TableView<TablaBloqueosCvDinam>) root.lookup("#tabla_datos");
//                    final TableView tabla_datos = (TableView) root.lookup("#tabla_datos");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");

                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

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

                    final TreeView<String> arbol_servad = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servad != null) {
                        arbol_servad.setDisable(false);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }

                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tabla_datos.itemsProperty().bind(task.valueProperty());
                    task.reset();
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            try {

                                ObservableList<TablaBloqueosCvDinam> value = task.getValue();
                                tabla_datos.itemsProperty().unbind();
                                tabla_datos.setItems(value);
                                Atlas.vista.show();
                            } catch (Exception e) {
                                docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                            }
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
                            docs.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });

                } catch (Exception e) {
//                    e.printStackTrace();
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    public class GetOTPDesTask extends Service<ObservableList<TablaBloqueosCvDinam>> {

        @Override
        protected Task<ObservableList<TablaBloqueosCvDinam>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    String Respuesta = new String();
//                    ObservableList<TablaBloqueosCvDinam> desbloqdinamic = FXCollections.observableArrayList();
                    final Cliente datosCliente = Cliente.getCliente();
                    final StringBuilder tramaOTPDesbClaveDin = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
//                    final Cliente cliente = Cliente.getCliente();

                    //850,055,connid,0618,docid,clave,Indicador de proveedor,~
                    tramaOTPDesbClaveDin.append("850,055,");
                    tramaOTPDesbClaveDin.append(datosCliente.getRefid());
                    tramaOTPDesbClaveDin.append(",");
                    tramaOTPDesbClaveDin.append(AtlasConstantes.COD_DESBLOQ_CLAVE_DIN);
                    tramaOTPDesbClaveDin.append(",");
                    tramaOTPDesbClaveDin.append(datosCliente.getId_cliente());
                    tramaOTPDesbClaveDin.append(",");
                    tramaOTPDesbClaveDin.append(datosCliente.getContraseña());
                    tramaOTPDesbClaveDin.append(",");
                    tramaOTPDesbClaveDin.append(StringUtilities.traerUsRed());
                    tramaOTPDesbClaveDin.append(",");
                    tramaOTPDesbClaveDin.append(datosCliente.getTokenOauth());
                    tramaOTPDesbClaveDin.append(",~");

                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ otpdesbloqueo = " + "##" + docs.obtenerHoraActual());

                        //Respuesta
                        //850,055,codigo,descripcion,~
                        //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaOTPDesbClaveDin.toString());
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaOTPDesbClaveDin.toString());
//                        Respuesta = "850,055,000,TRANSACCION EXITOSA,~";
                        //Respuesta = "850,055,763,Time Out en Espera de Respuesta,~";
//                        System.out.println(" RESPUESTA TRAMA OTP DESBLOQUEO:" + Respuesta);
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ otpdesbloqueo = " + "##" + docs.obtenerHoraActual());
//                            Respuesta = "850,055,763,Time Out en Espera de Respuesta,~";                          

                            // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP_CTG, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaOTPDesbClaveDin.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaOTPDesbClaveDin.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (OTPDesbloqueoClaveDinamicaFinController.cancelartarea.get()) {
                                        cancel();
                                    } else {
                                        new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        failed();
                                    }

                                }
                            });
                        }
                    }
                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (OTPDesbloqueoClaveDinamicaFinController.cancelartarea.get()) {
                            cancel();
                        } else {
                            desbloqdinamic = FXCollections.observableArrayList();
                            final String fecha = docs.obtenerFechaActualOTP() + " " + docs.obtenerHoraActual();
                            final TablaBloqueosCvDinam fechaTabla = new TablaBloqueosCvDinam(fecha);
                            desbloqdinamic.add(fechaTabla);
                        }

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
                    return desbloqdinamic;
                }
            };
        }
    }
}
