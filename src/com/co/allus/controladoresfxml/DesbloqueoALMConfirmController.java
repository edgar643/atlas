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
import com.co.allus.modelo.desbloqueoalm.DatosDesbloqueoAlm;
import com.co.allus.modelo.desbloqueoalm.InfoTablaDesbloqueoAlm;
import com.co.allus.modelo.desbloqueoalm.MapeoEstadosAlm;
import com.co.allus.utils.AtlasConstantes;
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
 * @author roberto.ceballos
 */
public class DesbloqueoALMConfirmController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label ConfirmDesbloqueoAlm;

    @FXML
    private Button continuar;

    @FXML
    private HBox menu_progreso;

    @FXML
    private ProgressBar progreso;

    private transient Service<Void> serviceDesbloqueoAlm = continuar_Op();
    private static AtomicBoolean cancelartareaDesbloqueoAlm = new AtomicBoolean();
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert ConfirmDesbloqueoAlm != null : "fx:id=\"ConfirmDesbloqueoAlm\" was not injected: check your FXML file 'DesbloqueoALMConfirm.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'DesbloqueoALMConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'DesbloqueoALMConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'DesbloqueoALMConfirm.fxml'.";
        this.resources = rb;
        this.location = url;
        DesbloqueoALMConfirmController.cancelartareaDesbloqueoAlm.set(false);
        progreso.setVisible(false);
    }

    public void mostrarDesbloqueoALMConfirmController() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/DesbloqueoALMConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();

                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        Logger.getLogger(DeclinacionesTDCController.class.getName()).log(Level.WARNING, null, ex);
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
    void continuarOP(ActionEvent event) {
        if (serviceDesbloqueoAlm.isRunning()) {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceDesbloqueoAlm.progressProperty());
            serviceDesbloqueoAlm.reset();
            serviceDesbloqueoAlm.start();

        } else {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceDesbloqueoAlm.progressProperty());
            serviceDesbloqueoAlm.reset();
            serviceDesbloqueoAlm.start();
        }

        serviceDesbloqueoAlm.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Confirmar Desbloqueo Alm" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                DesbloqueoALMConfirmController.cancelartareaDesbloqueoAlm.set(false);

            }
        });

        serviceDesbloqueoAlm.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Confirmar Desbloqueo Alm" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceDesbloqueoAlm.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                // System.out.println("fallo");
            }
        });
    }

    public Service<Void> continuar_Op() {
        serviceDesbloqueoAlm = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final Cliente cliente = Cliente.getCliente();

                        String Respuesta = new String();
                        final StringBuilder tramaDesbloqueoAlmConfir = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();

                        final DatosDesbloqueoAlm datosDesbloqueoAlm = DatosDesbloqueoAlm.getDatosDesbloqueoAlm();

                        tramaDesbloqueoAlmConfir.append("850,077,");
                        tramaDesbloqueoAlmConfir.append(cliente.getRefid());
                        tramaDesbloqueoAlmConfir.append(",");
                        tramaDesbloqueoAlmConfir.append(AtlasConstantes.COD_DESBLOQUEO_DES);
                        tramaDesbloqueoAlmConfir.append(",");
                        tramaDesbloqueoAlmConfir.append(cliente.getId_cliente());
                        tramaDesbloqueoAlmConfir.append(",");
//                        tramaDesbloqueoAlmConfir.append("2018-12-12-12.01.04.970000");
                        tramaDesbloqueoAlmConfir.append(datosDesbloqueoAlm.getFecha_novedad());
                        tramaDesbloqueoAlmConfir.append(",");
                        tramaDesbloqueoAlmConfir.append(cliente.getContraseña());
                        tramaDesbloqueoAlmConfir.append(",");
                        tramaDesbloqueoAlmConfir.append(cliente.getTokenOauth());
                        tramaDesbloqueoAlmConfir.append(",~");

//                        System.out.println("trama confirmar desbloqueo alm :" + tramaDesbloqueoAlmConfir);
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ DesbloqueoALM = " + "##" + gestorDoc.obtenerHoraActual());
//                            Respuesta = "850,"
//                                    + "077,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaDesbloqueoAlmConfir.toString());
                            //Thread.sleep(3000);

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ desbloqueo alm= " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaDesbloqueoAlmConfir.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (DesbloqueoALMConfirmController.cancelartareaDesbloqueoAlm.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
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
                                    final DesbloqueoALMFinController controller = new DesbloqueoALMFinController();
                                    controller.mostrarDesbloqueoALMFinController();
                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (DesbloqueoALMConfirmController.cancelartareaDesbloqueoAlm.get()) {
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

        return serviceDesbloqueoAlm;

    }

}
