/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.hilos.EnvioTipAutFenix;
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
public class BloqueoTDCConfirmController implements Initializable {

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
    private Label mensaje_bloqueostdc;
    @FXML
    private HBox menu_progreso;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label titulosBloqueosTDC;
    @FXML
    private Pane pane_modalbloqueostdc;
    private transient Service<Void> serviceBlqueoTDC;
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechabloqueo = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'BloqueoTDCConfirm.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'BloqueoTDCConfirm.fxml'.";
        assert mensaje_bloqueostdc != null : "fx:id=\"mensaje_bloqueostdc\" was not injected: check your FXML file 'BloqueoTDCConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'BloqueoTDCConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'BloqueoTDCConfirm.fxml'.";
        assert titulosBloqueosTDC != null : "fx:id=\"titulosBloqueosTDC\" was not injected: check your FXML file 'BloqueoTDCConfirm.fxml'.";
        assert pane_modalbloqueostdc != null : "fx:id=\"pane_modalbloqueostdc\" was not injected: check your FXML file 'BloqueoTDCConfirm.fxml'.";
        this.location = url;
        this.resources = rb;
        progreso.setVisible(false);
    }

    public void mostrarBloqueosTDCconfirm(final DatosTDCBloqueos data) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/BloqueoTDCConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final Label mensaje = (Label) root.lookup("#mensaje_bloqueostdc");

                    String tarjeta = data.getNumero().substring(0, 6) + StringUtilities.rellenarDato(" ", data.getNumero().length() - 10, "*") + data.getNumero().substring(data.getNumero().length() - 4, data.getNumero().length());
                    mensaje.setText("¿ ESTA SEGURO QUE DESEA BLOQUEAR SU TDC \n " + data.getTipoTarjeta().toUpperCase() + " " + tarjeta + " ? ");

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
                   Logger.getLogger(BloqueoTDCConfirmController.class.getName()).log(Level.SEVERE, null, ex);

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
        try {
            continuar_Op().cancel();
        } catch (Exception e) {
            Logger.getLogger(BloqueoTDCConfirmController.class.getName()).log(Level.WARNING, null, e);
        }
        try {
            final BloqueosTDCController controller = new BloqueosTDCController();
            synchronized (this) {
                // no realiza consulta
                MarcoPrincipalController.newConsultaBloqTDc = false;
            }
            controller.mostrarBloqueosTDC("BLOQUEOS-TDC");
        } catch (Exception e) {
            Logger.getLogger(BloqueoTDCConfirmController.class.getName()).log(Level.WARNING, null, e);
        }
    }

    @FXML
    void continuarOP(final ActionEvent event) {
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
        serviceBlqueoTDC = new Service<Void>() {
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
            

                        final StringBuilder tramaBloqueoTDC = new StringBuilder();

                        /*CODIFICACION AUTOMATICA*/
                        final Runnable aFenix = new EnvioTipAutFenix(cliente.getCodemp(), "006");
                        final Thread aFen = new Thread(aFenix);
                        aFen.start();

                        tramaBloqueoTDC.append("850,002,");
                        tramaBloqueoTDC.append(cliente.getRefid());
                        tramaBloqueoTDC.append(",");
                        tramaBloqueoTDC.append(AtlasConstantes.COD_BLOQ_TDC);
                        tramaBloqueoTDC.append(",");
                        tramaBloqueoTDC.append(cliente.getId_cliente());
                        tramaBloqueoTDC.append(",");
                        tramaBloqueoTDC.append(formatoFecha.format(fecha));
                        tramaBloqueoTDC.append(",");
                        tramaBloqueoTDC.append(formatoHora.format(fecha));
                        tramaBloqueoTDC.append(",");
                        tramaBloqueoTDC.append(seleccion.getNumero());
                        tramaBloqueoTDC.append(",");
                        tramaBloqueoTDC.append(cliente.getContraseña());
                        tramaBloqueoTDC.append(",");
                        tramaBloqueoTDC.append(cliente.getTokenOauth());
                        tramaBloqueoTDC.append(",~");

                       

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ BLOQUEO TDC = " + "##" + gestorDoc.obtenerHoraActual());
                            //850,002,000,TRANSACCIÓN EXITOSA,~
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoTDC.toString());
                            //  System.out.println("Respuesta TRAMA bloqueo TDC :" + Respuesta);
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                //gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ = " + tramaBloqueoTDC.toString() + "##" + gestorDoc.obtenerHoraActual());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ BLOQUEO TDC = " + "##" + gestorDoc.obtenerHoraActual());
                                //850,002,000,TRANSACCIÓN EXITOSA,~
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoTDC.toString());
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
                                    final BlqueoTDCFinController controller = new BlqueoTDCFinController();
                                    controller.mostrarBloqueoTDCfin(DatosTDCBloqueos.getDatabloqueo(), formatoFechabloqueo.format(new Date()));
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
        return serviceBlqueoTDC;
    }
}
