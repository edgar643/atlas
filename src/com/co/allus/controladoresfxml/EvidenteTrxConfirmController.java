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
import com.co.allus.modelo.evidente.ModelEvidente;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
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

public class EvidenteTrxConfirmController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar_op;
    @FXML
    private Label descpreg1;
    @FXML
    private Label descpreg2;
    @FXML
    private Label descpreg3;
    @FXML
    private Label descpreg4;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label resppreg1;
    @FXML
    private Label resppreg2;
    @FXML
    private Label resppreg3;
    @FXML
    private Label resppreg4;
    @FXML
    private Label titulosEvidente;
    @FXML
    private HBox menu_progreso;

    @FXML
    void cancel_op(ActionEvent event) {

        EvidenteTrxpreguntasController preguntasAgain = new EvidenteTrxpreguntasController();
        final ModelEvidente modelo = new ModelEvidente();
        preguntasAgain.mostrarEvidenteCuestionarioPreguntas(modelo, menu1, flujo1);
    }
    private transient Service<Void> serviceEnviarRespuestas = continuar_Op();
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static AtomicBoolean cancelartareaConfirmEvidente = new AtomicBoolean();
    private static String menu1 = "";
    private static int flujo1 = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert descpreg1 != null : "fx:id=\"descpreg1\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert descpreg2 != null : "fx:id=\"descpreg2\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert descpreg3 != null : "fx:id=\"descpreg3\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert descpreg4 != null : "fx:id=\"descpreg4\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert resppreg1 != null : "fx:id=\"resppreg1\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert resppreg2 != null : "fx:id=\"resppreg2\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert resppreg3 != null : "fx:id=\"resppreg3\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert resppreg4 != null : "fx:id=\"resppreg4\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";
        assert titulosEvidente != null : "fx:id=\"titulosEvidente\" was not injected: check your FXML file 'EvidenteTrxConfirm.fxml'.";

        EvidenteTrxConfirmController.cancelartareaConfirmEvidente.set(false);
        progreso.setVisible(false);
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

//                        Thread.sleep(3000);
                        final ModelEvidente infoModel = new ModelEvidente();
                        final Cliente datosCliente = Cliente.getCliente();
                        final String idCliente = datosCliente.getCliente().getId_cliente();
                        final String tipoCliente = infoModel.getTipoIdentificacion();
                        final String nombreCliente = datosCliente.getCliente().getNombre();
                        final String connid = datosCliente.getCliente().getRefid();

                        final String idCuestionario = infoModel.getIdCuestionario();
                        final String regCuestionario = infoModel.getRegCuestionario();

                        final String rep1 = infoModel.getRespSelect().get("Respuesta1").split(",")[0];
                        final String rep2 = infoModel.getRespSelect().get("Respuesta2").split(",")[0];
                        final String rep3 = infoModel.getRespSelect().get("Respuesta3").split(",")[0];
                        final String rep4 = infoModel.getRespSelect().get("Respuesta4").split(",")[0];


                        final StringBuilder tramaEnviarRespuestasEvidente = new StringBuilder();
//                                        Ejemplo
//                              922,005,00CDOVIO3SBBPFSFE8LH9B5AES00RJPV,007,728,<Respuestas idCuestionario="00023389" regCuestionario="4234304"><Identificacion numero="1046666873" tipo="1"/><Respuesta idPregunta="1" idRespuesta="002"/><Respuesta idPregunta="2" idRespuesta="004"/><Respuesta idPregunta="3" idRespuesta="004"/><Respuesta idPregunta="4" idRespuesta="002"/></Respuestas>,0229,alexander.lopez.o,71782820,~
                        tramaEnviarRespuestasEvidente.append("922,005,");
                        tramaEnviarRespuestasEvidente.append(connid);
                        tramaEnviarRespuestasEvidente.append(",007,");


                        if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
                            tramaEnviarRespuestasEvidente.append("2732,");
                        }
                        if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                            tramaEnviarRespuestasEvidente.append("2733,");
                        }



//                                <Respuestas idCuestionario="00023389" regCuestionario="4234304">
//                                <Identificacion numero="1046666873" tipo="1"/>
//                                <Respuesta idPregunta="1" idRespuesta="002"/>
//                                <Respuesta idPregunta="2" idRespuesta="004"/>
//                                <Respuesta idPregunta="3" idRespuesta="004"/>
//                                <Respuesta idPregunta="4" idRespuesta="002"/>
//                                final String add = "<Respuestas idCuestionario="00023389" regCuestionario="4234304"><Identificacion numero="1046666873" tipo="1"/><Respuesta idPregunta="1" idRespuesta="002"/><Respuesta idPregunta="2" idRespuesta="004"/><Respuesta idPregunta="3" idRespuesta="004"/><Respuesta idPregunta="4" idRespuesta="002"/></Respuestas>";
                        final String add = "<Respuestas idCuestionario=\"" + idCuestionario + "\" regCuestionario=\"" + regCuestionario + "\"><Identificacion numero=\"" + idCliente + "\" tipo=\"" + tipoCliente + "\"/><Respuesta idPregunta=\"1\" idRespuesta=\"" + rep1.split("###")[0] + "\"/><Respuesta idPregunta=\"2\" idRespuesta=\"" + rep2.split("###")[0] + "\"/><Respuesta idPregunta=\"3\" idRespuesta=\"" + rep3.split("###")[0] + "\"/><Respuesta idPregunta=\"4\" idRespuesta=\"" + rep4.split("###")[0] + "\"/></Respuestas>,";

                        tramaEnviarRespuestasEvidente.append(add);

//                        tramaEnviarRespuestasEvidente.append(",0129,");
                        if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
//                            tramaEnviarRespuestasEvidente.append("0129,");
//                            tramaEnviarRespuestasEvidente.append("0429,");
                            tramaEnviarRespuestasEvidente.append("0440,");
                        }
                        if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                            tramaEnviarRespuestasEvidente.append("0329,");
                        }
                        if (flujo1 == AtlasConstantes.EVIDENTE_DESBLOQUEO_CLAVE_DINAMICA) {
                            tramaEnviarRespuestasEvidente.append("0129,");
                        }

                        tramaEnviarRespuestasEvidente.append(StringUtilities.traerUsRed());
                        tramaEnviarRespuestasEvidente.append(","); // Usuario Red
                        tramaEnviarRespuestasEvidente.append("43869940"); // cc Autentificador        
                        tramaEnviarRespuestasEvidente.append(",~");


//                        System.out.println("Trama de respuestas enviada : " + tramaEnviarRespuestasEvidente.toString());

                        final StringBuilder respuesta = new StringBuilder();
                        final ModelEvidente modeloVal = new ModelEvidente();
                        try {
//                            System.out.println("principal");
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a WAS = " + tramaEnviarRespuestasEvidente.toString() + "##" + gestorDoc.obtenerHoraActual());

                            if (modeloVal.isIsHappy()) {
                                // PRUEBA RESPUESTA NO OK
                                //respuestaPreguntas.append("922,005,<?xml version=\"1.0\" encoding=\"UTF-8\"?><Evaluacion resultado=\"true\" aprobacion=\"false\" preguntasCompletas=\"true\" score=\"0\" codigoSeguridad=\"BZA17EB\" aprobado100PorCientoOK=\"false\" />,2,null,Resultado del proceso de verificación de respuestas: true, Aprobación: false ,~");
                                // PRUEBA RESPUESTA OK
                                respuesta.append("922,005,<?xml version=\"1.0\" encoding=\"UTF-8\"?><Evaluacion resultado=\"true\" aprobacion=\"true\" preguntasCompletas=\"true\" score=\"0\" codigoSeguridad=\"BZA17EB\" aprobado100PorCientoOK=\"false\" />,1,null111,Resultado del proceso de verificación de respuestas: true, Aprobación: false ,~");


                            } else {
                                final ConectSS servicioSS = new ConectSS();
                                respuesta.append(servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_EVIDENTE, AtlasConstantes.PUERTO_COM_EVIDENTE, tramaEnviarRespuestasEvidente.toString()));

//                                System.out.println("respuesta Lista : " + respuesta.toString());
                            }

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  WAS = " + StringUtilities.tramaSubString(respuesta.toString(), 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
//                            System.out.println("contingencia");
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal WAS = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg WAS = " + tramaEnviarRespuestasEvidente.toString() + "##" + gestorDoc.obtenerHoraActual());

                                final ConectSS servicioSS = new ConectSS();
                                //                String respuesta  = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_EVIDENTE_CTG, AtlasConstantes.PUERTO_COM_EVIDENTE_CTG, tramaValidarClienteEvidente.toString());
                                respuesta.append(servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_EVIDENTE_CTG, AtlasConstantes.PUERTO_COM_EVIDENTE_CTG, tramaEnviarRespuestasEvidente.toString()));

                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg WAS = " +  StringUtilities.tramaSubString(respuesta.toString(), 0, 3, ",")  + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (EvidenteTrxConfirmController.cancelartareaConfirmEvidente.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            continuar_op.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);
                                        }

                                    }
                                });
                            }
                        }

                        final String datos[] = respuesta.toString().split(",");


                        if (datos[1].equals("998") || datos[1].equals("997") || datos[1].equals("996")) {

//                                Aviso Respuesta mala
                            final String coderror = datos[2];
                            final String mensaje = "NO SE PUDO REALIZAR LA TRANSACCION";

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (EvidenteTrxConfirmController.cancelartareaConfirmEvidente.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_EVIDENTE);
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                }
                            });

                        } else {

//                            System.out.println("Entra a Confirmación");
//                            System.out.println("" + respuesta.toString());

                            final String aprobacion = "aprobacion=\"true\"";
                            if ("1".equals(datos[3]) && datos[2].contains(aprobacion)) {
//                                final EvidenteTrxFinController mostrarfin = new EvidenteTrxFinController();
//                                mostrarfin.mostrarFinEvidenteOk(menu1);
                                if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
                                    final EvidenteTrxFinController mostrarfin = new EvidenteTrxFinController();
                                    mostrarfin.mostrarFinEvidenteOk(menu1);
                                }
                                if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                    final InscripcionServicioClvDinEvideOkController controller = new InscripcionServicioClvDinEvideOkController();
                                    controller.mostrarFinInscripcionClaveDinamicaOk(menu1, flujo1);
                                }
                                if (flujo1 == AtlasConstantes.EVIDENTE_DESBLOQUEO_CLAVE_DINAMICA) {
                                    final OTPDesbloqueoClaveDinamicaFinController controller = new OTPDesbloqueoClaveDinamicaFinController();
                                    controller.mostrarDesbloqueOTPfin();
                                }
                            } else {

                                if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
                                    final EvidenteTrxFalloController controller = new EvidenteTrxFalloController();
                                    controller.mostrarEvidenteFallo(menu1);
                                }
                                if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                    final InscripcionServicoClaveDinamicaTrxFalloController controller = new InscripcionServicoClaveDinamicaTrxFalloController();
                                    controller.mostrarInscripcionServicoClaveDinamicaTrxFallo(menu1, flujo1);
                                }
                                if (flujo1 == AtlasConstantes.EVIDENTE_DESBLOQUEO_CLAVE_DINAMICA) {
                                    final EvidenteTrxFalloController controller = new EvidenteTrxFalloController();
                                    controller.mostrarEvidenteFallo(menu1);
                                }


                            }




                        }
                        return null;

                    }
                };
            }
        };
        return serviceEnviarRespuestas;
    }

    public void mostrarConfirmacionPreguntasEvidente(final ModelEvidente modelo, final String menu, final int flujo) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/EvidenteTrxConfirm.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final Button cancelar = (Button) root.lookup("#cancelar");
                        final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");

                        final Label titulosEvidente = (Label) root.lookup("#titulosEvidente");
                        titulosEvidente.setText(menu);
//                        System.out.println("------ Lo que pasa en menu:" + menu);
                        menu1 = menu;
                        flujo1 = flujo;

                        /*Pintar Informacion*/

                        final Label descpreg1 = (Label) root.lookup("#descpreg1");
                        final Label descpreg2 = (Label) root.lookup("#descpreg2");
                        final Label descpreg3 = (Label) root.lookup("#descpreg3");
                        final Label descpreg4 = (Label) root.lookup("#descpreg4");

                        final String PregResp = modelo.getPregAndResp();
//                        System.out.println("PregRespE1-->" + PregResp);

//                        Preguntas

                        final String datos[] = PregResp.split("%");

                        descpreg1.setText(datos[0]);
//                        System.out.println("---");

                        final String respuestas1[] = datos[1].split("&");
                        int Resp1 = respuestas1.length - 1;

                        descpreg2.setText(respuestas1[Resp1]);
                        final String respuestas2[] = PregResp.split("%")[2].split("&");
                        int Resp2 = respuestas2.length - 1;

                        descpreg3.setText(respuestas2[Resp2]);
                        final String respuestas3[] = PregResp.split("%")[3].split("&");
                        int Resp3 = respuestas3.length - 1;

                        descpreg4.setText(respuestas3[Resp3]);
                        final String respuestas4[] = PregResp.split("%")[4].split("&");


//                        respuestas
                        final Label resppreg1 = (Label) root.lookup("#resppreg1");
                        resppreg1.setText(modelo.getRespSelect().get("Respuesta1").split("###")[1]);
                        final Label resppreg2 = (Label) root.lookup("#resppreg2");
                        resppreg2.setText(modelo.getRespSelect().get("Respuesta2").split("###")[1]);
                        final Label resppreg3 = (Label) root.lookup("#resppreg3");
                        resppreg3.setText(modelo.getRespSelect().get("Respuesta3").split("###")[1]);
                        final Label resppreg4 = (Label) root.lookup("#resppreg4");
                        resppreg4.setText(modelo.getRespSelect().get("Respuesta4").split("###")[1]);



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
