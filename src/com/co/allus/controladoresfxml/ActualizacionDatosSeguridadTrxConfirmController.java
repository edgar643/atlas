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
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import com.co.allus.modelo.actualizaciondatosseguridad.ModeloDatosDeSeguridad;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author luis.cuervo
 */
public class ActualizacionDatosSeguridadTrxConfirmController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField Email;
    @FXML
    private Button cancelar;
    @FXML
    private TextField cedulaCliente;
    @FXML
    private TextField celularCliente;
    @FXML
    private Button continuar_op;
    @FXML
    private TextField nombreCliente;
    @FXML
    private TextField tipoMail;
    @FXML
    private HBox menu_progreso;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label titulosActDatSeguridad;

    @FXML
    void cancel_op(ActionEvent event) {

        final ActualizacionDatosSeguridadController volver = new ActualizacionDatosSeguridadController();
        volver.mostrarActualizacionDatosSeguridadBotonRegresar(menu1);

    }

    @FXML
    void continuar_OP(ActionEvent event) {
        if (serviceEnviarRespuestas.isRunning()) {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceEnviarRespuestas.progressProperty());
            serviceEnviarRespuestas.reset();
            serviceEnviarRespuestas.start();

        } else {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceEnviarRespuestas.progressProperty());
            serviceEnviarRespuestas.reset();
            serviceEnviarRespuestas.start();
        }

        serviceEnviarRespuestas.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Evidente" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceEnviarRespuestas.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Cambio Mecanismo" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceEnviarRespuestas.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
            }
        });

    }

    public Service<Void> continuar_Op() {
        serviceEnviarRespuestas = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        final StringUtilities asesor = new StringUtilities();


                        final String tipomail = tipoMail.getText();
                        String tipoMail = "";
                        if ("Personal".equals(tipomail)) {
                            tipoMail = "P";
                        } else {
                            if ("Laboral".equals(tipomail)) {

                                tipoMail = "L";
                            } else {

                                tipoMail = "";
                            }
                        }



                        final String celular = celularCliente.getText();
                        final String celularC = celularCliente.getText();

                        final String email = Email.getText();
                        final String emailC = Email.getText();


                        final String cedula = cedulaCliente.getText();


                        /*Antes de esto aqui validacion de tipos*/

                        final StringBuilder tramaEnviarActualizacion = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente datosCliente = Cliente.getCliente();

                        tramaEnviarActualizacion.append("850,014,");
                        tramaEnviarActualizacion.append(datosCliente.getRefid());
                        tramaEnviarActualizacion.append(",");
                        tramaEnviarActualizacion.append("0612"); // operacion confirmar
                        tramaEnviarActualizacion.append(",");
                        tramaEnviarActualizacion.append(datosCliente.getId_cliente());
                        tramaEnviarActualizacion.append(",");
                        tramaEnviarActualizacion.append(datosCliente.getContraseña());
                        tramaEnviarActualizacion.append(",");
                        tramaEnviarActualizacion.append(celular);
                        tramaEnviarActualizacion.append(",");
                        tramaEnviarActualizacion.append(email);
                        tramaEnviarActualizacion.append(",");

                        tramaEnviarActualizacion.append(tipoMail);
                        tramaEnviarActualizacion.append(",");

                        tramaEnviarActualizacion.append(asesor.traerUsRed());
                        tramaEnviarActualizacion.append(",~");




                        String Respuesta = new String();
//                        850,014,019SFK417SBCBDFUE8LH9B5AES02C7F4,codOper,111110038,5D26F171F2AC4183, celular (20), email(60), tipomail(1), usRedAsesor(15),~



                        try {


                            if (ModeloDatosDeSeguridad.isIsHappy()) {

                                Respuesta = "850,014,000,TRANSACCION EXITOSA,~";

                            } else {  
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ ACTUALIZACION DATOS SEGURIDAD = " + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,012,000,Consulta de datos de seguridad exitosa,N,,P,3002588382,~";

                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaEnviarActualizacion.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaEnviarActualizacion.toString());

                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                            }
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ ACTUALIZACION DE DATOS SEGURIDAD = " + gestorDoc.obtenerHoraActual());                               
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaEnviarActualizacion.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ActualizacionDatosSeguridadTrxConfirmController.cancelartarea.get()) {

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


                            if (ActualizacionDatosSeguridadTrxConfirmController.cancelartarea.get()) {
                                cancel();
                            } else {

                                final ActualizacionDatosSeguridadTrxFinController controller = new ActualizacionDatosSeguridadTrxFinController();
                                controller.mostrarActualizacionDatosSeguridadTrxFinController(menu1);
                            }
                        } else {

                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();



                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ActualizacionDatosSeguridadTrxConfirmController.cancelartarea.get()) {
                                        //System.out.println("cancelo tarea");
                                        cancel();
                                    } else {

                                        new ModalMensajes(mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
//                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                        continuar_op.setDisable(false);

                                    }
                                }
                            });

                        }


                        /**/
                        return null;
                    }
                };
            }
        };
        return serviceEnviarRespuestas;

    }
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private transient Service<Void> serviceEnviarRespuestas = continuar_Op();
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static String menu1 = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert Email != null : "fx:id=\"Email\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        assert cedulaCliente != null : "fx:id=\"cedulaCliente\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        assert celularCliente != null : "fx:id=\"celularCliente\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        assert nombreCliente != null : "fx:id=\"nombreCliente\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        assert tipoMail != null : "fx:id=\"tipoMail\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        assert titulosActDatSeguridad != null : "fx:id=\"titulosActDatSeguridad\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxConfirm.fxml'.";
        ActualizacionDatosSeguridadTrxConfirmController.cancelartarea.set(false);
        progreso.setVisible(false);
    }

    public void mostrarActualizacionDatosSeguridadController(final ModeloDatosDeSeguridad modelo, final String menu) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/ActualizacionDatosSeguridadTrxConfirm.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final Button cancelar = (Button) root.lookup("#cancelar");
                        final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");

                        final Label titulosActDatSeguridad = (Label) root.lookup("#titulosActDatSeguridad");
                        titulosActDatSeguridad.setText(menu);
                        menu1 = menu;

                        /*Pintar Informacion*/


                        final TextField nombreCliente = (TextField) root.lookup("#nombreCliente");
                        final TextField cedulaCliente = (TextField) root.lookup("#cedulaCliente");
                        final TextField celularCliente = (TextField) root.lookup("#celularCliente");
                        final TextField Email = (TextField) root.lookup("#Email");
                        final TextField tipoMail = (TextField) root.lookup("#tipoMail");


                        nombreCliente.setText(modelo.getNombre());
                        nombreCliente.setEditable(false);
                        cedulaCliente.setText(modelo.getIdentificacion());
                        cedulaCliente.setEditable(false);
                        celularCliente.setText(modelo.getCelular());
                        celularCliente.setEditable(false);
                        Email.setText(modelo.getEmail());
                        Email.setEditable(false);

                        final String tipoEmail = modelo.getTipoEmail();
                        if ("P".equals(tipoEmail)) {
                            tipoMail.setEditable(false);
                            tipoMail.setText("Personal");
                        } else {
                            if ("L".equals(tipoEmail)) {
                                tipoMail.setEditable(false);
                                tipoMail.setText("Laboral");
                            } else {
                                tipoMail.setEditable(false);
                                tipoMail.setText("");
                            }
                        }





                        /*Fin pintar informacion*/


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
                            gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                        }
                        pane.setAlignment(Pos.CENTER_LEFT);
                        pane.add(root, 1, 0);
                        Atlas.vista.show();
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }

                }
            });


        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }

    }
}
