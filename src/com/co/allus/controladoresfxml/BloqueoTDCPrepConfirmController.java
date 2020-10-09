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
import com.co.allus.modelo.tdcprepago.ModelTdcPrepago;
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
public class BloqueoTDCPrepConfirmController implements Initializable {
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
    private Label mensaje_bloqueostdcp;
    @FXML
    private HBox menu_progreso;
    @FXML
    private Pane pane_modalbloqueostdcP;
    @FXML
    private Label titulosBloqueosTDCp;
    @FXML
    private ProgressBar progreso;
    private static ModelTdcPrepago dataTarjeta;

    public static ModelTdcPrepago getDataTarjeta() {
        return dataTarjeta;
    }

    public static void setDataTarjeta(ModelTdcPrepago dataTarjeta) {
        BloqueoTDCPrepConfirmController.dataTarjeta = dataTarjeta;
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            continuar_Op().cancel();
        } catch (Exception e) {
            Logger.getLogger(BloqueoTDCPrepConfirmController.class.getName()).log(Level.WARNING, null, e);
        }
        try {
            if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                final BloqueoTDCprepIniController controller = new BloqueoTDCprepIniController();
                controller.mostrarBloqueoTDCPrepago("N");
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
                            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                        }
                    }
                });
            }
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
    private transient Service<Void> serviceBlqueoTDCP;
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFechabloqueo = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'BloqueoTDCPrepConfirm.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'BloqueoTDCPrepConfirm.fxml'.";
        assert mensaje_bloqueostdcp != null : "fx:id=\"mensaje_bloqueostdcp\" was not injected: check your FXML file 'BloqueoTDCPrepConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'BloqueoTDCPrepConfirm.fxml'.";
        assert pane_modalbloqueostdcP != null : "fx:id=\"pane_modalbloqueostdcP\" was not injected: check your FXML file 'BloqueoTDCPrepConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'BloqueoTDCPrepConfirm.fxml'.";
        assert titulosBloqueosTDCp != null : "fx:id=\"titulosBloqueosTDCp\" was not injected: check your FXML file 'BloqueoTDCPrepConfirm.fxml'.";
        progreso.setVisible(false);
    }

    public void mostrarBloqueosTDCPconfirm(final ModelTdcPrepago data) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/BloqueoTDCPrepConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Label mensaje = (Label) root.lookup("#mensaje_bloqueostdcp");

                    final Cliente datosCliente = Cliente.getCliente();
                    setDataTarjeta(data);
                    if ("N".equalsIgnoreCase(datosCliente.getTipoClientePrepago())) {
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                        final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
                        cliente.setText("");
                        cliente.setText(info);
                        String tarjeta = data.getTarjetacf().substring(0, 6) + StringUtilities.rellenarDato(" ", data.getTarjetacf().length() - 10, "*") + data.getTarjetacf().substring(data.getTarjetacf().length() - 4, data.getTarjetacf().length());
                        mensaje.setText("¿ ESTA SEGURO QUE DESEA BLOQUEAR SU TARJETA  " + data.getTipoTarjeta().toUpperCase() + "\n" + tarjeta + " ? ");
                    } else if ("E".equalsIgnoreCase(datosCliente.getTipoClientePrepago())) {
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String nombreC = datosCliente.getNombreEmpresa();
                        final String info = nombreC + "\nNIT : " + datosCliente.getNitEmpresa() + "\n" + data.getTarjetacf();
                        cliente.setText("");
                        cliente.setText(info);
                        String tarjeta = data.getTarjetacf().substring(0, 6) + StringUtilities.rellenarDato(" ", data.getTarjetacf().length() - 10, "*") + data.getTarjetacf().substring(data.getTarjetacf().length() - 4, data.getTarjetacf().length());
                        mensaje.setText("¿ ESTA SEGURO QUE DESEA BLOQUEAR SU TARJETA  " + data.getTipoTarjeta().toUpperCase() + "\n" + tarjeta + " ? ");
                    }
                    //final String tarjeta=BloqueoTDCprepIniController.tarjetasOri.get(data.getNumeroTarjeta());
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
        serviceBlqueoTDCP = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        ModelTdcPrepago dataTarjeta1 = getDataTarjeta();
                        final StringBuilder tramaBloqueoTDCP = new StringBuilder();

                        tramaBloqueoTDCP.append("850,066,");
                        tramaBloqueoTDCP.append(cliente.getRefid());
                        tramaBloqueoTDCP.append(",");
                        tramaBloqueoTDCP.append(AtlasConstantes.COD_BLOQUEOTDC_PREP);
                        tramaBloqueoTDCP.append(",");
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append(cliente.getId_cliente());
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append(cliente.getNitEmpresa());
                        }
                        tramaBloqueoTDCP.append(",");
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append("1");
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append("3");  //TIPO CLIENTE
                        }
                        tramaBloqueoTDCP.append(",");
                        tramaBloqueoTDCP.append(Cliente.getCliente().getTipoTarjeta()); // variable
                        tramaBloqueoTDCP.append(",");
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append(BloqueoTDCprepIniController.tarjetasOri.get(dataTarjeta1.getNumeroTarjeta()));
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramaBloqueoTDCP.append(getDataTarjeta().getNumeroTarjeta());
                        }
                        tramaBloqueoTDCP.append(",");
                        tramaBloqueoTDCP.append(cliente.getContraseña());
                        tramaBloqueoTDCP.append(",");
                        tramaBloqueoTDCP.append(cliente.getTokenOauth());
                        tramaBloqueoTDCP.append(",~");
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ BLOQEUO TARJETA PREPAGO = " + "##" + gestorDoc.obtenerHoraActual());
                            //850,066,000,                                                                      ,E,P,~
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoTDCP.toString());
                            //  System.out.println("Respuesta TRAMA bloqueo TDC :" + Respuesta);
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ BLOQUEO TARJETA PREPAGO = " + "##" + gestorDoc.obtenerHoraActual());
                                //850,066,000,                                                                      ,E,P,~
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaBloqueoTDCP.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
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
                                    final BloqueoTDCPrepFinController controller = new BloqueoTDCPrepFinController();
                                    controller.mostrarBloqueoTDCFfin(getDataTarjeta(), formatoFechabloqueo.format(new Date()));
                                }
                            });
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
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
        return serviceBlqueoTDCP;
    }
}
