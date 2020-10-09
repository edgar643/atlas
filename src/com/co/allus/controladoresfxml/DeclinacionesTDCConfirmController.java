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
import com.co.allus.modelo.declinacionestdc.DatosTDCDeclinacion;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author roberto.ceballos
 */
public class DeclinacionesTDCConfirmController implements Initializable {

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
    private Pane pane_modalbloqueostdc;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label titulosBloqueosTDC;
    private transient Service<Void> serviceDeclinacionTDC = continuar_Op();
    private static AtomicBoolean cancelartareaDeclinacionTDC = new AtomicBoolean();
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechabloqueo = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'DeclinacionesTDCConfirm.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'DeclinacionesTDCConfirm.fxml'.";
        assert mensaje_bloqueostdc != null : "fx:id=\"mensaje_bloqueostdc\" was not injected: check your FXML file 'DeclinacionesTDCConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'DeclinacionesTDCConfirm.fxml'.";
        assert pane_modalbloqueostdc != null : "fx:id=\"pane_modalbloqueostdc\" was not injected: check your FXML file 'DeclinacionesTDCConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'DeclinacionesTDCConfirm.fxml'.";
        assert titulosBloqueosTDC != null : "fx:id=\"titulosBloqueosTDC\" was not injected: check your FXML file 'DeclinacionesTDCConfirm.fxml'.";
        this.location = url;
        this.resources = rb;
        DeclinacionesTDCConfirmController.cancelartareaDeclinacionTDC.set(false);
        progreso.setVisible(false);

    }

    public void mostrarDeclinacionesTDCConfirm(final DatosTDCDeclinacion data) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/DeclinacionesTDCConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    final Label mensaje = (Label) root.lookup("#mensaje_bloqueostdc");

                    String tarjeta = data.getNumero().substring(0, 6) + StringUtilities.rellenarDato(" ", data.getNumero().length() - 10, "*") + data.getNumero().substring(data.getNumero().length() - 4, data.getNumero().length());
                    mensaje.setText("¿ Esta seguro que desea levantar la declinación de su \n " + data.getTipoTarjeta().toUpperCase() + " " + tarjeta + " ? ");

                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        Logger.getLogger(DeclinacionesTDCConfirmController.class.getName()).log(Level.SEVERE, ex.toString());
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
    void cancel(ActionEvent event) {
//        try {
//            continuar_Op().cancel();
//        } catch (Exception e) {
//            Logger.getLogger(DeclinacionesTDCConfirmController.class.getName()).log(Level.WARNING, null, e);
//        }
//        try {
        final DeclinacionesTDCController controller = new DeclinacionesTDCController();
//            synchronized (this) {
//                // no realiza consulta
//                MarcoPrincipalController.newConsultaDeclinacionTDC = false;
//            }
        controller.mostrarDeclinacionesTdc("DECLINACIONES-TDC", true);
//        } catch (Exception e) {
//            Logger.getLogger(DeclinacionesTDCConfirmController.class.getName()).log(Level.WARNING, null, e);
//        }
    }

    @FXML
    void continuarOP(ActionEvent event) {
        if (serviceDeclinacionTDC.isRunning()) {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceDeclinacionTDC.progressProperty());
            serviceDeclinacionTDC.reset();
            serviceDeclinacionTDC.start();

        } else {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceDeclinacionTDC.progressProperty());
            serviceDeclinacionTDC.reset();
            serviceDeclinacionTDC.start();
        }

        serviceDeclinacionTDC.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Confirmar Declinacion TDC" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                DeclinacionesTDCConfirmController.cancelartareaDeclinacionTDC.set(false);
            }
        });

        serviceDeclinacionTDC.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Cancelar Declinacion TDC" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceDeclinacionTDC.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                // System.out.println("fallo");
            }
        });

    }

    public Service<Void> continuar_Op() {
        serviceDeclinacionTDC = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final Cliente cliente = Cliente.getCliente();

                        String Respuesta = new String();
                        final StringBuilder tramaDeclinacionTDC = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();

                        final DatosTDCDeclinacion seleccion = DatosTDCDeclinacion.getDatadeclinacion();
                        final Date fecha = new Date();

                        // System.out.println("Tarjeta :" +seleccion.getNumero());
                        // System.out.println("Franquicia :" +seleccion.getTipoTarjeta());

//                        /*CODIFICACION AUTOMATICA*/
//                        final Runnable aFenix = new EnvioTipAutFenix(cliente.getCodemp(), "006");
//                        final Thread aFen = new Thread(aFenix);
//                        aFen.start();

                        tramaDeclinacionTDC.append("850,073,");
                        tramaDeclinacionTDC.append(cliente.getRefid());
                        tramaDeclinacionTDC.append(",");
                        tramaDeclinacionTDC.append(AtlasConstantes.COD_DECLINACIONES_TDC);
                        tramaDeclinacionTDC.append(",");
                        tramaDeclinacionTDC.append(cliente.getId_cliente());
                        tramaDeclinacionTDC.append(",");
                        tramaDeclinacionTDC.append(seleccion.getNumero());
                        tramaDeclinacionTDC.append(",");
                        tramaDeclinacionTDC.append(cliente.getContraseña());
                        tramaDeclinacionTDC.append(",");
                        tramaDeclinacionTDC.append(cliente.getTokenOauth());
                        tramaDeclinacionTDC.append(",~");

//                        System.out.println("TRAMA declinacion TDC :" + tramaDeclinacionTDC.toString());

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ DECLINACIONES TDC = " + "##" + gestorDoc.obtenerHoraActual());
                            //gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ = " + tramaBloqueoTDC.toString() + "##" + gestorDoc.obtenerHoraActual());
//                            Respuesta = "850,"
//                                    + "073,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "2019-01-10,"
//                                    + "2019-01-11,~";

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaDeclinacionTDC.toString());

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                //gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ = " + tramaBloqueoTDC.toString() + "##" + gestorDoc.obtenerHoraActual());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ declinacion tdc = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaDeclinacionTDC.toString());
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

                                    final DeclinacionesTDCFinController controller = new DeclinacionesTDCFinController();
                                    controller.mostrarDeclinacionesTDCFinController();
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
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Confirmar Declinación TDC" + "##" + gestorDoc.obtenerHoraActual());
                                progreso.progressProperty().unbind();
                                progreso.setProgress(0);
                                progreso.setVisible(false);
                            }
                        });

                        setOnCancelled(new EventHandler() {
                            @Override
                            public void handle(final Event event) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Cancelar Declinación TDC" + "##" + gestorDoc.obtenerHoraActual());
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

        return serviceDeclinacionTDC;

    }
}
