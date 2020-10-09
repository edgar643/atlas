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
import com.co.allus.utils.AtlasConstantes;
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
public class BloqueoClaveppalConfirmController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar;
    @FXML
    private Label mensaje_bloqueosclaves;
    @FXML
    private HBox menu_progreso;
    @FXML
    private Pane pane_modabloqclaveppal;
    @FXML
    private Label titulosBloqueosClaveppal;
    @FXML
    private ProgressBar progreso;
    private transient final SimpleDateFormat formatoFechabloqueo = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
    private transient Service<Void> serviceBlqueoClavePpal;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'BloqueoClaveppalConfirm.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'BloqueoClaveppalConfirm.fxml'.";
        assert mensaje_bloqueosclaves != null : "fx:id=\"mensaje_bloqueosclaves\" was not injected: check your FXML file 'BloqueoClaveppalConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'BloqueoClaveppalConfirm.fxml'.";
        assert pane_modabloqclaveppal != null : "fx:id=\"pane_modabloqclaveppal\" was not injected: check your FXML file 'BloqueoClaveppalConfirm.fxml'.";
        assert titulosBloqueosClaveppal != null : "fx:id=\"titulosBloqueosClaveppal\" was not injected: check your FXML file 'BloqueoClaveppalConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'BloqueoClaveppalConfirm.fxml'.";
        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);
    }

    @FXML
    void cancel(final ActionEvent event) {
        try {
            continuar_Op().cancel();
        } catch (Exception ex) {
            gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
            Logger.getLogger(BloqueoClaveppalConfirmController.class.getName()).log(Level.SEVERE, null, ex);

        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception ex) {
                    gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
                }
            }
        });
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

    public void mostrarBlqueosClavePpal() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/BloqueoClaveppalConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    // String info = datosCliente.getNombre() + "\n" + StringUtilities.rellenarDato(" ", setScale.intValue(), " ") + "C.C: " + datosCliente.getId_cliente();
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

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
                        Logger.getLogger(BloqueoClaveppalConfirmController.class.getName()).log(Level.SEVERE, null, ex);
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

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
        serviceBlqueoClavePpal = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        final Date fecha = new Date();

                        final StringBuilder tramaBloqueoClaves = new StringBuilder();

//                          /*CODIFICACION AUTOMATICA*/
                        final Runnable aFenix = new EnvioTipAutFenix(cliente.getCodemp(), "007");
                        final Thread aFen = new Thread(aFenix);
                        aFen.start();

                        tramaBloqueoClaves.append("850,004,");
                        tramaBloqueoClaves.append(cliente.getRefid());
                        tramaBloqueoClaves.append(",");
                        tramaBloqueoClaves.append(AtlasConstantes.COD_BLOQ_CLAVE_PPAL);
                        tramaBloqueoClaves.append(",");
                        tramaBloqueoClaves.append(cliente.getId_cliente());
                        tramaBloqueoClaves.append(",");
                        tramaBloqueoClaves.append(formatoFecha.format(fecha));
                        tramaBloqueoClaves.append(",");
                        tramaBloqueoClaves.append(formatoHora.format(fecha));
                        tramaBloqueoClaves.append(",");
                        tramaBloqueoClaves.append(cliente.getContraseña());
                        tramaBloqueoClaves.append(",");
                        tramaBloqueoClaves.append(cliente.getTokenOauth());
                        tramaBloqueoClaves.append(",~");

                        try {
                            // gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ = " + tramaBloqueoClaves.toString() + "##" + gestorDoc.obtenerHoraActual());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ BLOQUEO CLAVE PPAL = " + "##" + gestorDoc.obtenerHoraActual());
                         
                            //850,004,000,000: TRANSACCION EXISTOSA,~ 
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaBloqueoClaves.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ BLOQUEO CLAVE PPAL = " + "##" + gestorDoc.obtenerHoraActual());
                                //850,004,000,000: TRANSACCION EXISTOSA,~ 
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaBloqueoClaves.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT); // CAMBIAR
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

                                    final BloqueoClaveppalFinController controller = new BloqueoClaveppalFinController();
                                    controller.mostrarBloqueoClaveppalFin(formatoFechabloqueo.format(new Date()));

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

        return serviceBlqueoClavePpal;

    }
}
