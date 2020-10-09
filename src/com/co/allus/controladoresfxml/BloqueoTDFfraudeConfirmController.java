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
import com.co.allus.modelo.bloqueos.DatosTDCBloqueos;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class BloqueoTDFfraudeConfirmController implements Initializable {

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
    private Label mensaje_bloqueostdcF;
    @FXML
    private HBox menu_progreso;
    @FXML
    private Pane pane_modalbloqueostdcF;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label titulosBloqueosTDCf;

    @FXML
    void cancel(ActionEvent event) {
        try {
            continuar_Op().cancel();
        } catch (Exception e) {
            Logger.getLogger(BloqueoTDCConfirmController.class.getName()).log(Level.WARNING, null, e);
        }
        try {
            final BloqueosTDCController controller = new BloqueosTDCController();
            synchronized (this) {
                // no realiza consulta
                MarcoPrincipalController.newConsultaBloqTDcf = false;
            }
            controller.mostrarBloqueosTDC("BLOQUEOS-TDC");
        } catch (Exception e) {
            Logger.getLogger(BloqueoTDCConfirmController.class.getName()).log(Level.WARNING, null, e);
        }
    }

    @FXML
    void continuarOP(ActionEvent event) {
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

    private transient Service<Void> serviceBlqueoTDCF;
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechabloqueo = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'BloqueoTDFfraudeConfirm.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'BloqueoTDFfraudeConfirm.fxml'.";
        assert mensaje_bloqueostdcF != null : "fx:id=\"mensaje_bloqueostdcF\" was not injected: check your FXML file 'BloqueoTDFfraudeConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'BloqueoTDFfraudeConfirm.fxml'.";
        assert pane_modalbloqueostdcF != null : "fx:id=\"pane_modalbloqueostdcF\" was not injected: check your FXML file 'BloqueoTDFfraudeConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'BloqueoTDFfraudeConfirm.fxml'.";
        assert titulosBloqueosTDCf != null : "fx:id=\"titulosBloqueosTDCf\" was not injected: check your FXML file 'BloqueoTDFfraudeConfirm.fxml'.";
        progreso.setVisible(false);
    }

    public void mostrarBloqueosTDCFconfirm(final DatosTDCBloqueos data) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/BloqueoTDFfraudeConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Label mensaje = (Label) root.lookup("#mensaje_bloqueostdcF");

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    String tarjeta = data.getNumero().substring(0, 6) + StringUtilities.rellenarDato(" ", data.getNumero().length() - 10, "*") + data.getNumero().substring(data.getNumero().length() - 4, data.getNumero().length());
                    mensaje.setText("¿ ESTA SEGURO QUE DESEA REALIZAR EL BLOQUEO \nPOR FRAUDE DE LA TDC " + data.getTipoTarjeta().toUpperCase() + " " + tarjeta + " ? ");

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
                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        Logger.getLogger(BloqueoTDFfraudeConfirmController.class.getName()).log(Level.SEVERE, null, ex);

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

    public Service<Void> continuar_Op() {
        serviceBlqueoTDCF = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        final Date fecha = new Date();
                        final DatosTDCBloqueos seleccion = DatosTDCBloqueos.getDatabloqueo();
                        final StringBuilder tramaBloqueoTDCF = new StringBuilder();

                        tramaBloqueoTDCF.append("850,056,");
                        tramaBloqueoTDCF.append(cliente.getRefid());
                        tramaBloqueoTDCF.append(",");
                        tramaBloqueoTDCF.append(AtlasConstantes.COD_BLOQ_TDC_FRAUDE);
                        tramaBloqueoTDCF.append(",");
                        tramaBloqueoTDCF.append(cliente.getId_cliente());
                        tramaBloqueoTDCF.append(",");
                        tramaBloqueoTDCF.append(formatoFecha.format(fecha));
                        tramaBloqueoTDCF.append(",");
                        tramaBloqueoTDCF.append(formatoHora.format(fecha));
                        tramaBloqueoTDCF.append(",");
                        tramaBloqueoTDCF.append(seleccion.getNumero());
                        tramaBloqueoTDCF.append(",");
                        tramaBloqueoTDCF.append(cliente.getContraseña());
                        tramaBloqueoTDCF.append(",");
                        tramaBloqueoTDCF.append(cliente.getTokenOauth());
                        tramaBloqueoTDCF.append(",~");

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ BLOQUEO TDC FRAUDE = " + "##" + gestorDoc.obtenerHoraActual());
                            //850,056,000,TRANSACCIÓN EXITOSA,~        
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoTDCF.toString());

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ BLOQUEO TDC FRAUDE = " + "##" + gestorDoc.obtenerHoraActual());
                                //850,056,000,TRANSACCIÓN EXITOSA,~
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoTDCF.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_BLOQUEOS_TDC);
                                        continuar.setDisable(false);
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
                                    final BloqueoTDCFraudeFinController controller = new BloqueoTDCFraudeFinController();
                                    controller.mostrarBloqueoTDCFfin(DatosTDCBloqueos.getDatabloqueo(), formatoFechabloqueo.format(new Date()));
                                }
                            });
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_TDC);
                                    // cancel();
                                    continuar.setDisable(false);
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                }
                            });
                        }

                        setOnSucceeded(new EventHandler() {
                            @Override
                            public void handle(final Event event) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Bloqueo TDC" + "##" + gestorDoc.obtenerHoraActual());
                                progreso.progressProperty().unbind();
                                progreso.setProgress(0);
                                progreso.setVisible(false);
                            }
                        });

                        setOnCancelled(new EventHandler() {
                            @Override
                            public void handle(final Event event) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Bloqueo TDC" + "##" + gestorDoc.obtenerHoraActual());
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
        return serviceBlqueoTDCF;
    }
}
