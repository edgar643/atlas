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
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class DesbloqSClaveCorpValDatosController implements Initializable {

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
    private ProgressBar progreso;
    private GestorDocumental docs = new GestorDocumental();
    private transient Service<Void> servicedesBloqSCcorp;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaDesbloqueo = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'DesbloqSClaveCorpValDatos.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'DesbloqSClaveCorpValDatos.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'DesbloqSClaveCorpValDatos.fxml'.";
        this.resources = rb;
        this.location = url;

        progreso.setVisible(false);
    }

    public void mostrarDesbloqueoSCValDatos() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/DesbloqSClaveCorpValDatos.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

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


                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });


    }

    @FXML
    void cancel(final ActionEvent event) {
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

    @FXML
    void continuarOP(final ActionEvent event) {
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono desblqueo Segunda Clave corporativa" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo desblqueo Segunda Clave corporativa" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }

    }

    public Service<Void> continuar_Op() {
        servicedesBloqSCcorp = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String RespuestaTrasnfin = new String();
                        final StringBuilder tramaDesbloqSC = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        final Date fecha = new Date();
                        tramaDesbloqSC.append("900,018,");
                        tramaDesbloqSC.append(cliente.getRefid());
                        tramaDesbloqSC.append(",");
                        tramaDesbloqSC.append(AtlasConstantes.COD_DESBLOQUEOSC_MQ);
                        tramaDesbloqSC.append(",");
                        tramaDesbloqSC.append(cliente.getId_cliente());
                        tramaDesbloqSC.append(",");
                        tramaDesbloqSC.append(formatoFecha.format(fecha));
                        tramaDesbloqSC.append(",");
                        tramaDesbloqSC.append(formatoHora.format(fecha));
                        tramaDesbloqSC.append(",");
                        tramaDesbloqSC.append(cliente.getContraseña());
                        tramaDesbloqSC.append(",");
                        tramaDesbloqSC.append(cliente.getTokenOauth());
                        tramaDesbloqSC.append(",~");


                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Desbloqueo Segunda Clave Corp = " + "##" + obtenerHoraActual());
                            RespuestaTrasnfin = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, tramaDesbloqSC.toString());
                            // RespuestaTrasnfin = "900,018,001,Error,~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ Desbloqueo Segunda Clave Corp = " + StringUtilities.tramaSubString(RespuestaTrasnfin, 0, 2, ",") + "##" + obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ DESBLOQUEO SEGUNDA CLAVE X SEG = " + "##" + obtenerHoraActual());
                                RespuestaTrasnfin = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, tramaDesbloqSC.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(RespuestaTrasnfin, 0, 2, ",") + "##" + obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_DESBLOQUEO_SEGCLAVE);
                                        cancel();
                                        // continuar_Op.cancel();
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }
                        }

                        if ("000".equals(RespuestaTrasnfin.split(",")[2])) {
                            final Date fechapago = new Date();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final DesbloqueoSClaveCorpFinController controller = new DesbloqueoSClaveCorpFinController();
                                    controller.mostrarDesbloqueoSClaveFin(formatoFechaDesbloqueo.format(fechapago));
                                }
                            });


                        } else {
                            final String coderror = RespuestaTrasnfin.split(",")[2];
                            final String mensaje = RespuestaTrasnfin.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_DESBLOQUEO_SEGCLAVE);
                                    cancel();
                                    //continuar_Op.cancel();
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                }
                            });

                        }

                        return null;
                    }
                };


            }
        };

        return servicedesBloqSCcorp;
    }

    ; 

     private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
