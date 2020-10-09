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
import com.co.allus.modelo.cambiomecanismo.ModeloDatosSeguridad;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class CambioMecanismoTrxConfirmController implements Initializable {

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
    private Button continuar;
    @FXML
    private HBox menu_progreso;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label ConfirmCambioMec;
    private transient Service<Void> serviceCambioMecanismo = continuar_Op();
    private static AtomicBoolean cancelartareaCambioMec = new AtomicBoolean();
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'CambioMecanismoTrxConfirm.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'CambioMecanismoTrxConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'CambioMecanismoTrxConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'CambioMecanismoTrxConfirm.fxml'.";
        assert ConfirmCambioMec != null : "fx:id=\"ConfirmCambioMec\" was not injected: check your FXML file 'CambioMecanismoTrxConfirm.fxml'.";
        this.resources = rb;
        this.location = url;
        CambioMecanismoTrxConfirmController.cancelartareaCambioMec.set(false);
        progreso.setVisible(false);

    }

    public void mostrarCambioMecanismoTrxConfirm() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/CambioMecanismoTrxConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Label label_CambioMec = (Label) root.lookup("#ConfirmCambioMec");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    final ModeloDatosSeguridad datosCambioMec = ModeloDatosSeguridad.getDatosSeguridad();
                    final String MecAnterior = Cliente.getCliente().getTipo_otp().equalsIgnoreCase(AtlasConstantes.OTP_SOFTOKEN) ? AtlasConstantes.ITEM_OTP_SOFTOKEN : ("SMS".equalsIgnoreCase(Cliente.getCliente().getTipo_oda()) ? "SMS" : "MAIL");
                    final String MecNuevo = ("SMS".equalsIgnoreCase(datosCambioMec.getCambio_mecanismo()) ? "SMS" : "MAIL");

                    label_CambioMec.setText("¿ ESTA SEGURO QUE DESEA CAMBIAR DE " + MecAnterior + " A " + MecNuevo + " ?");

                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                    Logger.getLogger(ActualizacionDatosSeguridadController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();

                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });


    }

    @FXML
    void cancel(final ActionEvent event) {
        final CambioMecanismoController controller = new CambioMecanismoController();
        controller.mostrarCambioMecanismo("CAMBIO DE TIPO DE MECANISMO", true);

    }

    @FXML
    void continuarOP(final ActionEvent event) {
        if (serviceCambioMecanismo.isRunning()) {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceCambioMecanismo.progressProperty());
            serviceCambioMecanismo.reset();
            serviceCambioMecanismo.start();

        } else {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceCambioMecanismo.progressProperty());
            serviceCambioMecanismo.reset();
            serviceCambioMecanismo.start();
        }

        serviceCambioMecanismo.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Cambio Mecanismo" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                CambioMecanismoTrxConfirmController.cancelartareaCambioMec.set(false);

            }
        });



        serviceCambioMecanismo.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Cambio Mecanismo" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceCambioMecanismo.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                // System.out.println("fallo");
            }
        });

    }

    public Service<Void> continuar_Op() {
        serviceCambioMecanismo = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();
                        final ModeloDatosSeguridad datosCambioMec = ModeloDatosSeguridad.getDatosSeguridad();
                        final StringBuilder tramaCambioMecanismo = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        tramaCambioMecanismo.append("850,013,");
                        tramaCambioMecanismo.append(cliente.getRefid());
                        tramaCambioMecanismo.append(",");
                        tramaCambioMecanismo.append(cliente.getId_cliente());
                        tramaCambioMecanismo.append(",");
                        tramaCambioMecanismo.append(datosCambioMec.getCambio_mecanismo());
                        tramaCambioMecanismo.append(",");
                        tramaCambioMecanismo.append(System.getProperty("user.name"));
                        tramaCambioMecanismo.append(",");
                        tramaCambioMecanismo.append(cliente.getContraseña());
                        tramaCambioMecanismo.append(",");
                        tramaCambioMecanismo.append(cliente.getTokenOauth());
                        tramaCambioMecanismo.append(",~");



//                        System.out.println("TRAMA CAMBIO MECANISMO :" + tramaCambioMecanismo);


                        try {
                            //gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ = " + tramaCambioMecanismo.toString() + "##" + gestorDoc.obtenerHoraActual());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CAMBIO MECANISMO = " + "##" + gestorDoc.obtenerHoraActual());
//                          // Respuesta = "850,"
//                                    + "013,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,~";

                            //Thread.sleep(2000);

                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaCambioMecanismo.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaCambioMecanismo.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 2, ",") + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ CAMBIO MECANISMO = " + "##" + gestorDoc.obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP_CTG, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaCambioMecanismo.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaCambioMecanismo.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 2, ",") + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (CambioMecanismoTrxConfirmController.cancelartareaCambioMec.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_TDC);
                                            continuar.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);
                                        }
                                    }
                                });
                            }
                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final CambioMecanismoTrxfinController controller = new CambioMecanismoTrxfinController();
                                    controller.mostrarCambioMecanismoTrxFin();
                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (CambioMecanismoTrxConfirmController.cancelartareaCambioMec.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuar.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                }
                            });

                        }



                        return null;
                    }
                };
            }
        };



        return serviceCambioMecanismo;

    }
}
