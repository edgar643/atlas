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
import com.co.allus.modelo.actualizaciondatosseguridad.ModeloDatosDeSeguridad;
import com.co.allus.modelo.enrolamiento.Enrolamiento;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
public class InscripcionServicioClaveDinamicaTrxConfirmController implements Initializable {

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
    private TextField tipoMecanismo;
    @FXML
    private HBox menu_progreso;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label titulosActDatSeguridad;

    @FXML
    void cancel_op(ActionEvent event) {

        final InscripcionServicioClaveDinamicaController volver = new InscripcionServicioClaveDinamicaController();
        volver.mostrarInscripcionServicioClaveDinamicaBotonRegresar(menu1, flujo1, opcion1);
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
//                System.out.println("fallo");
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

                        final String tipomec = tipoMecanismo.getText();
                        String tipoMecanismo = "";
                        if ("E-mail".equals(tipomec)) {
                            tipoMecanismo = "EML";
                        } else {
                            if ("SMS".equals(tipomec)) {

                                tipoMecanismo = "SMS";
                            } else {

                                tipoMecanismo = "";
                            }
                        }


                        final String celular = celularCliente.getText();
                        final String celularC = celularCliente.getText();

                        final String email = Email.getText();
                        final String emailC = Email.getText();


                        final String cedula = cedulaCliente.getText();


                        /*Antes de esto aqui validacion de tipos*/

                        final StringBuilder tramaEnviarInscripcion = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente datosCliente = Cliente.getCliente();
                        final Date fecha = new Date();

                        if (opcion1 == 1) // ok evidente
                        {
                            tramaEnviarInscripcion.append("850,017,");
                        }
                        if (opcion1 == 0) // nok evidente
                        {
                            tramaEnviarInscripcion.append("850,018,");
                        }
                        tramaEnviarInscripcion.append(datosCliente.getRefid());
                        tramaEnviarInscripcion.append(",");
                        if (opcion1 == 1) // ok evidente
                        {
                            tramaEnviarInscripcion.append("0606");
                        }
                        if (opcion1 == 0) // nok evidente
                        {
                            tramaEnviarInscripcion.append("0615");
                        }

                        final Enrolamiento enrolado = Enrolamiento.getEnrolado();

                        enrolado.setTipo_mecanismo(tipoMecanismo);
                        enrolado.setCelular(celular);
                        enrolado.setEmail(email);
                        enrolado.setTipo_email(tipoMail);

                        Enrolamiento.setEnrolado(enrolado);



                        tramaEnviarInscripcion.append(",");
                        tramaEnviarInscripcion.append(datosCliente.getId_cliente());
                        tramaEnviarInscripcion.append(",");
                        tramaEnviarInscripcion.append(formatoFecha.format(fecha));
                        tramaEnviarInscripcion.append(",");
                        tramaEnviarInscripcion.append(formatoHora.format(fecha));
                        tramaEnviarInscripcion.append(",");
                        tramaEnviarInscripcion.append(datosCliente.getContraseña());
                        tramaEnviarInscripcion.append(",");
                        tramaEnviarInscripcion.append(celular);
                        tramaEnviarInscripcion.append(",");
                        tramaEnviarInscripcion.append(email);
                        tramaEnviarInscripcion.append(",");
                        tramaEnviarInscripcion.append(tipoMail);
                        tramaEnviarInscripcion.append(",");
                        tramaEnviarInscripcion.append(tipoMecanismo);
                        tramaEnviarInscripcion.append(",");
                        tramaEnviarInscripcion.append(asesor.traerUsRed());
                        tramaEnviarInscripcion.append(",~");

                        String Respuesta = new String();
// 850,017,019SFK417SBCBDFUE8LH9B5AES02C7F4,0606/0605,111110038,YYYYMMDD,HHMMSS,5D26F171F2AC4183, celular (20), email(60), tipomail(1),SMS/EML, usRedAsesor(15),~
// 850,017,connid,codtransaccion(0606),docid,fecha,hora,clave,Celular,Email,tipoMail(L o P),mecanismo(SMS o EML),usAsesor,~

                        try {

                            if (ModeloDatosDeSeguridad.isIsHappy()) {

                                Respuesta = "850,017,000,TRANSACCION EXITOSA,~";
//                                Respuesta = "850,018,000,TRANSACCION EXITOSA,~";
//                                Respuesta = "850,014,095,FALLO DE CONEXION,~";
//                                System.out.println("Respuesta happy:" + Respuesta);
                            } else {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ inscripcionOTP = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,012,000,Consulta de datos de seguridad exitosa,N,,P,3002588382,~";
//                                System.out.println("Envio - :" + tramaEnviarInscripcion.toString());
                                // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaEnviarInscripcion.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaEnviarInscripcion.toString());
//                                System.out.println("respuesta Lista : " + Respuesta);

                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                            }
                        } catch (Exception ex) {
//                            System.out.println("contingencia");
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ inscripcionOTP = " + "##" + gestorDoc.obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP_CTG, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaEnviarInscripcion.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaEnviarInscripcion.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (InscripcionServicioClaveDinamicaTrxConfirmController.cancelartarea.get()) {
//                                            System.out.println("cancelo tarea");
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

//                            System.out.println("Bolean Cancelar Tarea:" + InscripcionServicioClaveDinamicaTrxConfirmController.cancelartarea.get());
                            if (InscripcionServicioClaveDinamicaTrxConfirmController.cancelartarea.get()) {
                                cancel();
                            } else {
//                                System.out.println("Entra a disparar mensaje");

                                if (opcion1 == 1) // 606 todo ok
                                {
                                    final InscripcionServicioClaveDinamicaTrxFinController controller = new InscripcionServicioClaveDinamicaTrxFinController();
                                    controller.mostrarFinInscripcionClaveDinamicaOk(menu1, flujo1, opcion1);
                                }

                                if (opcion1 == 0) // 615 ok pendiente validacion
                                {
                                    // mensaje pendiente
                                    final InscripcionServicioClaveDinamicaPendienteValidacionController controller = new InscripcionServicioClaveDinamicaPendienteValidacionController();
                                    controller.mostrarFinInscripcionClaveDinamicaPendienteValidacion(menu1, flujo1, opcion1);
                                }
                            }
                        } else {

                            if ("018".equals(Respuesta.split(",")[2]) && opcion1 == 1) // 606, cliente ya enrolado(cuando se enrola en paralelo)
                            {
                                final InscripcionServicioClaveDinamicaYaEnroladoController controller = new InscripcionServicioClaveDinamicaYaEnroladoController();
                                controller.mostrarFinInscripcionClaveDinamicaYaEnrolado(menu1, flujo1, opcion1);
                            } else {
                                final String coderror = Respuesta.split(",")[2];
                                final String mensaje = Respuesta.split(",")[3].trim();
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (InscripcionServicioClaveDinamicaTrxConfirmController.cancelartarea.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes(mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);
                                            continuar_op.setDisable(false);
                                        }
                                    }
                                });
                            }
                        }
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
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private static String menu1 = "";
    private static int flujo1 = 0;
    private static int opcion1 = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert Email != null : "fx:id=\"Email\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        assert cedulaCliente != null : "fx:id=\"cedulaCliente\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        assert celularCliente != null : "fx:id=\"celularCliente\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        assert nombreCliente != null : "fx:id=\"nombreCliente\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        assert tipoMail != null : "fx:id=\"tipoMail\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        assert tipoMecanismo != null : "fx:id=\"tipoMecanismo\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        assert titulosActDatSeguridad != null : "fx:id=\"titulosActDatSeguridad\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxConfirm.fxml'.";
        InscripcionServicioClaveDinamicaTrxConfirmController.cancelartarea.set(false);
        progreso.setVisible(false);
    }

    public void mostrarInscripcionServicioClaveDinamicaTrxConfirmController(final ModeloDatosDeSeguridad modelo, final String menu, final int flujo, final int opcion) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/InscripcionServicioClaveDinamicaTrxConfirm.fxml");
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
//                        System.out.println("------ Lo que pasa en menu:" + menu);
                        menu1 = menu;
                        flujo1 = flujo;
                        opcion1 = opcion;

                        /**
                         * modo feliz evidente SOLO
                         * PRUEBAS!!!!!!!!!!!!!!!!!!!!!!!!
                         */
                        // opcion1 = 1;   
                        /**
                         *
                         */
                        /*Pintar Informacion*/
                        final TextField nombreCliente = (TextField) root.lookup("#nombreCliente");
                        final TextField cedulaCliente = (TextField) root.lookup("#cedulaCliente");
                        final TextField celularCliente = (TextField) root.lookup("#celularCliente");
                        final TextField Email = (TextField) root.lookup("#Email");
                        final TextField tipoMail = (TextField) root.lookup("#tipoMail");
                        final TextField tipoMecanismo = (TextField) root.lookup("#tipoMecanismo");


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

                        final String tipoMec = modelo.getOTP();

                        if ("EML".equals(tipoMec)) {
                            tipoMecanismo.setEditable(false);
                            tipoMecanismo.setText("E-mail");
                        } else {
                            if ("SMS".equals(tipoMec)) {
                                tipoMecanismo.setEditable(false);
                                tipoMecanismo.setText("SMS");
                            } else {
                                tipoMecanismo.setEditable(false);
                                tipoMecanismo.setText("");
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
//                        ex.printStackTrace();
                    }

                }
            });


        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//            ex.printStackTrace();
        }

    }
}
