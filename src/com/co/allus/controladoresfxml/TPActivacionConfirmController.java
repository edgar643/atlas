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
import com.co.allus.modelo.tpactivacion.DatosActivacionConfirm;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class TPActivacionConfirmController implements Initializable {

    @FXML
    private ProgressBar progreso;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button aceptar_op;
    @FXML
    private Button cancelar_op;
    @FXML
    private Label lblmensaje;
    private transient final SimpleDateFormat formatoFechabloqueo = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
    private static GestorDocumental docs = new GestorDocumental();
    private transient Service<Void> serviceActivacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert aceptar_op != null : "fx:id=\"aceptar_op\" was not injected: check your FXML file 'TPActivacionConfirm.fxml'.";
        assert cancelar_op != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'TPActivacionConfirm.fxml'.";
        assert lblmensaje != null : "fx:id=\"lblmensaje\" was not injected: check your FXML file 'TPActivacionConfirm.fxml'.";
        aceptar_op.setDisable(false);
        progreso.setVisible(false);

    }

    @FXML
    void aceptar_op(ActionEvent event) {
        if (aceptar_op().isRunning()) {
            aceptar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(aceptar_op().progressProperty());
            aceptar_op().reset();
            aceptar_op().start();

        } else {
            aceptar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(aceptar_op().progressProperty());
            aceptar_op().start();
        }
    }

    public Service<Void> aceptar_op() {
        serviceActivacion = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        final StringBuilder tramaBloqueoTDCP = new StringBuilder();

                        tramaBloqueoTDCP.append("850,067,");
                        tramaBloqueoTDCP.append(cliente.getRefid());
                        tramaBloqueoTDCP.append(",");
                        tramaBloqueoTDCP.append(AtlasConstantes.COD_ACTIVACION_PREP);
                        tramaBloqueoTDCP.append(",");
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append(cliente.getId_cliente());
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append(cliente.getNitEmpresa());
                        }
                        tramaBloqueoTDCP.append(",");
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append("1"); //tipoid
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append("3"); //tipoid
                        }
                        tramaBloqueoTDCP.append(",");
                        tramaBloqueoTDCP.append(Cliente.getCliente().getTipoClientePrepago()); // variable
                        tramaBloqueoTDCP.append(",");
                        tramaBloqueoTDCP.append(DatosActivacionConfirm.getDataAct().getNumTarjsf());
                        tramaBloqueoTDCP.append(",");
                        tramaBloqueoTDCP.append(cliente.getContraseña());
                        tramaBloqueoTDCP.append(",~");



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ activacion prepago = " + "##" + docs.obtenerHoraActual());
//                            Respuesta = "850,"
//                                    + "003,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,tipotarjeta,estadoBloqueo~";

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoTDCP.toString());

//                            System.out.println("Respuesta TRAMA activación TDC :" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ activacion prepago = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoTDCP.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        aceptar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    final TPActivacionFinController controller = new TPActivacionFinController();
                                    controller.mostrarActivacionTDCFfin(DatosActivacionConfirm.getDataAct(), formatoFechabloqueo.format(new Date()));
                                }
                            });



//                            final String respuestatrx= Respuesta.split(",")[3];
//
//                            if (respuestatrx.trim().isEmpty()){
//                            final String coderror = Respuesta.split(",")[2];
//                            final String mensaje = Respuesta.split(",")[3].trim();
//                            
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    new ModalMensajes(mensaje.isEmpty() ? "Tarjeta activada con éxito" : mensaje, coderror, ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.DEFAULT);
//                              
//                                }
//                            });
//                            
//                            }else{
//                            
//                            final String coderror = Respuesta.split(",")[2];
//                            final String mensaje = Respuesta.split(",")[3].trim();
//                            
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    new ModalMensajes(mensaje.isEmpty() ? respuestatrx : mensaje, coderror, ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.DEFAULT);
//                              
//                                }
//                            });
//                            
//                            }

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "LA ACTIVACIÓN NO SE COMPLETÓ EXITOSAMENTE , INTENTE NUEVAMENTE" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                                }
                            });

                        }

                        setOnSucceeded(new EventHandler() {
                            @Override
                            public void handle(final Event event) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Bloqueo TDC" + "##" + docs.obtenerHoraActual());
                                progreso.progressProperty().unbind();
                                progreso.setProgress(0);
                                progreso.setVisible(false);
                            }
                        });



                        setOnCancelled(new EventHandler() {
                            @Override
                            public void handle(final Event event) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Bloqueo TDC" + "##" + docs.obtenerHoraActual());
                                progreso.progressProperty().unbind();
                                progreso.setProgress(0);
                                progreso.setVisible(false);
                            }
                        });

                        return null;
                    }
                };
            }
        };



        return serviceActivacion;

    }

    @FXML
    void cancelar_op(ActionEvent event) {

        try {
            if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        final TPActivacionIniController controller = new TPActivacionIniController();
                        controller.mostrarActivacionIni();
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
            Logger.getLogger(TPActivacionConfirmController.class.getName()).log(Level.WARNING, null, e);
        }

    }

    public void mostrarConfirmarActivacion(final DatosActivacionConfirm numtarj) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TPActivacionConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button aceptar_op = (Button) root.lookup("#aceptar_op");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_op");
                    final Label lblmensaje = (Label) root.lookup("#lblmensaje");

                    final Cliente datosCliente = Cliente.getCliente();

                    if ("N".equalsIgnoreCase(datosCliente.getTipoClientePrepago())) {
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                        final String info = nombreC + "\nC.C: " + numtarj.getNumTarj();
                        cliente.setText("");
                        cliente.setText(info);
                        String tarjeta = numtarj.getNumTarj().substring(0, 6) + StringUtilities.rellenarDato(" ", numtarj.getNumTarj().length() - 10, "*") + numtarj.getNumTarj().substring(numtarj.getNumTarj().length() - 4, numtarj.getNumTarj().length());
                        lblmensaje.setText("¿Está seguro que desea activar su tarjeta " + numtarj.getTipoTarj() + "\n" + tarjeta + "?");

                    } else if ("E".equalsIgnoreCase(datosCliente.getTipoClientePrepago())) {
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String nombreC = datosCliente.getNombreEmpresa();
                        String tarjeta = numtarj.getNumTarj().substring(0, 6) + StringUtilities.rellenarDato(" ", numtarj.getNumTarj().length() - 10, "*") + numtarj.getNumTarj().substring(numtarj.getNumTarj().length() - 4, numtarj.getNumTarj().length());
                        final String info = nombreC + "\nNIT : " + datosCliente.getNitEmpresa() + "\n" + numtarj.getNumTarj();
                        cliente.setText("");
                        cliente.setText(info);
                        lblmensaje.setText("¿Está seguro que desea activar su tarjeta " + numtarj.getTipoTarj() + "\n" + tarjeta + "?");

                    }



                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final DropShadow shadow = new DropShadow();
                    aceptar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    aceptar_op.setCursor(Cursor.HAND);
                                    aceptar_op.setEffect(shadow);
                                }
                            });

                    aceptar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    aceptar_op.setCursor(Cursor.DEFAULT);
                                    aceptar_op.setEffect(null);

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

//                    aceptar_op.setDisable(true);
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

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);




                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });
    }
}
