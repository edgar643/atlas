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
import com.co.allus.modelo.cambiomecanismo.ModeloDatosSeguridad;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class CambioMecanismoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button continuar_op;
    @FXML
    private TextField email;
    @FXML
    private Label mensajeAd;
    @FXML
    private TextField num_cel;
    @FXML
    private RadioButton odatoemail;
    @FXML
    private RadioButton odatosms;
    @FXML
    private TextField tipoMecanismoAct;
    @FXML
    private TextField tipo_email;
    @FXML
    private Label warningcelular;
    @FXML
    private Label warningemail;
    @FXML
    private ProgressBar progreso;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ModeloDatosSeguridad> task = new CambioMecanismoController.GetDatosSeguridadTask();
    private static GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert mensajeAd != null : "fx:id=\"mensajeAd\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert num_cel != null : "fx:id=\"num_cel\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert odatoemail != null : "fx:id=\"odatoemail\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert odatosms != null : "fx:id=\"odatosms\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert tipoMecanismoAct != null : "fx:id=\"tipoMecanismoAct\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert tipo_email != null : "fx:id=\"tipo_email\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert warningcelular != null : "fx:id=\"warningcelular\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert warningemail != null : "fx:id=\"warningemail\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'CambioMecanismo.fxml'.";
        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);
        CambioMecanismoController.cancelartarea.set(false);

    }

    @FXML
    void cancelarOP(final ActionEvent event) {
        try {
            task.cancel();
        } catch (Exception e) {
            Logger.getLogger(ActualizacionDatosSeguridadController.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            Atlas.getVista().gotoPrincipal();
        } catch (IOException ex) {
            gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
        }
    }

    @FXML
    void continuarOP(final ActionEvent event) {

        final ModeloDatosSeguridad datosSeguridad = ModeloDatosSeguridad.getDatosSeguridad();

        if (odatoemail.isSelected()) {
            datosSeguridad.setCambio_mecanismo(AtlasConstantes.CAMBIO_TO_EMAIL);
        } else if (odatosms.isSelected()) {
            datosSeguridad.setCambio_mecanismo(AtlasConstantes.CAMBIO_TO_SMS);
        }

        ModeloDatosSeguridad.setDatosSeguridad(datosSeguridad);

        final CambioMecanismoTrxConfirmController controller = new CambioMecanismoTrxConfirmController();
        controller.mostrarCambioMecanismoTrxConfirm();

    }

    @FXML
    void selodatoemail(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion
                odatosms.setSelected(false);

                if (odatoemail.isSelected()) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                }

                //
            }
        });
    }

    @FXML
    void selodatosms(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion
                odatoemail.setSelected(false);

                if (odatosms.isSelected()) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                }

            }
        });

    }

    public void mostrarCambioMecanismo(final String menu, final boolean validaEvidente) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/CambioMecanismo.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TextField num_cel = (TextField) root.lookup("#num_cel");
                    final TextField email = (TextField) root.lookup("#email");
                    final TextField tipo_email = (TextField) root.lookup("#tipo_email");
                    final TextField tipoMecanismoAct = (TextField) root.lookup("#tipoMecanismoAct");
                    final Label warningcelular = (Label) root.lookup("#warningcelular");
                    final Label warningemail = (Label) root.lookup("#warningemail");
                    final Label mensajeAd = (Label) root.lookup("#mensajeAd");
                    final RadioButton odatosms = (RadioButton) root.lookup("#odatosms");
                    final RadioButton odatoemail = (RadioButton) root.lookup("#odatoemail");
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();

                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setPrefSize(150, 150);
                    p.setLayoutX(200);
                    p.setLayoutY(50);

                    root.getChildren().addAll(veil, p);

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

                    botoncontinuarOp.setDisable(true);
                    label_menu.setVisible(false);

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

                    odatoemail.setDisable(true);
                    odatosms.setDisable(true);
                    // Use binding to be notified whenever the data source chagnes
                    // task = new GetTarjetasTask();
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

//                            System.out.println("TERMINO TAREA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        odatoemail.setDisable(false);
                                        odatosms.setDisable(false);
                                        final ModeloDatosSeguridad datosSeguridad = task.getValue();
                                        num_cel.setText(datosSeguridad.getCelular().trim());
                                        email.setText(datosSeguridad.getEmail());
                                        tipo_email.setText(datosSeguridad.getTipo_email().equals("P") ? "Personal" : datosSeguridad.getTipo_email().equals("L") ? "Laboral" : datosSeguridad.getTipo_email());
//                                        tipoMecanismoAct.setText(Cliente.getCliente().getTipo_otp().equalsIgnoreCase(AtlasConstantes.OTP_SOFTOKEN) ? AtlasConstantes.ITEM_OTP_SOFTOKEN : Cliente.getCliente().getTipo_otp() + " " + Cliente.getCliente().getTipo_oda());

                                        tipoMecanismoAct.setText(Cliente.getCliente().getTipo_otp().equalsIgnoreCase(AtlasConstantes.OTP_SOFTOKEN) ? AtlasConstantes.ITEM_OTP_SOFTOKEN : ("SMS".equalsIgnoreCase(Cliente.getCliente().getTipo_oda()) ? "SMS" : "Mail"));

                                        if (Cliente.getCliente().getTipo_otp().equalsIgnoreCase(AtlasConstantes.OTP_SOFTOKEN)) {
//                                            if ((num_cel.getText().isEmpty()) || (!(num_cel.getText().startsWith("3")) || (num_cel.getText().length() < 10))) {
                                            if ((num_cel.getText().isEmpty()) || (num_cel.getText().length() < 10)) {
                                                warningcelular.setVisible(true);
                                                odatosms.setDisable(true);
//                                                mensajeAd.setText("No es posible activar el cambio de mecanismo a celular. Número de celular inválido");
//                                                mensajeAd.setText("No es posible realizar el cambio de mecanismo a celular. Porque el número de celular es inválido");
                                                mensajeAd.setText("No es posible realizar el cambio de mecanismo a SMS. \nPorque no se tiene el número de celular del cliente");
                                            }

                                            if (!StringUtilities.validateEmail(email.getText())) {
                                                warningemail.setVisible(true);
                                                odatoemail.setDisable(true);
//                                                mensajeAd.setText("No es posible activar el cambio de mecanismo a email. Email inválido");
                                                mensajeAd.setText("No es posible realizar el cambio de mecanismo a Mail. \nPorque no se tiene el Mail del cliente");
                                            }

                                            if (odatosms.isDisable() && odatoemail.isDisable()) {
//                                                mensajeAd.setText("No se tiene información de los datos de seguridad del cliente");
                                                mensajeAd.setText("No es posible realizar el cambio de mecanismo. \nPorque no se tiene datos de seguridad del cliente o son inválidos");
                                            }

                                        } else if (Cliente.getCliente().getTipo_otp().equalsIgnoreCase(AtlasConstantes.OTP_ODA)) { //
                                            if (Cliente.getCliente().getTipo_oda().equalsIgnoreCase(AtlasConstantes.ODA_EMAIL)) {
                                                odatoemail.setDisable(true);

//                                                if ((num_cel.getText().isEmpty()) || (!(num_cel.getText().startsWith("3")) || (num_cel.getText().length() < 10))) {
//                                                    warningcelular.setVisible(true);
//                                                    odatosms.setDisable(true);
//                                                    mensajeAd.setText("No es posible activar el cambio de mecanismo a celular. Número de celular inválido");
//
//                                                }
//                                                if ((num_cel.getText().isEmpty()) || (!(num_cel.getText().startsWith("3")) || (num_cel.getText().length() < 10))) {
                                                if ((num_cel.getText().isEmpty()) || (num_cel.getText().length() < 10)) {
                                                    warningcelular.setVisible(true);
                                                    odatosms.setDisable(true);
                                                    mensajeAd.setText("No es posible realizar el cambio de mecanismo a SMS. \nPorque no se tiene el número de celular del cliente");
//                                                    mensajeAd.setText("No es posible activar el cambio de mecanismo a celular. Número de celular inválido");
                                                }

                                                if (!StringUtilities.validateEmail(email.getText())) {
                                                    warningemail.setVisible(true);
                                                    odatoemail.setDisable(true);
                                                    // mensajeAd.setText("No es posible activar el cambio de mecanismo a email. Email inválido");
                                                }

//                                                if (odatosms.isDisable() && odatoemail.isDisable()) {
////                                                    mensajeAd.setText("No se tiene información de los datos de seguridad del cliente");
//                                                    mensajeAd.setText("No es posible realizar el cambio de mecanismo. \nPorque no se tiene datos de seguridad del cliente o son inválidos");
//
//                                                }
                                            } else if (Cliente.getCliente().getTipo_oda().equalsIgnoreCase(AtlasConstantes.ODA_SMS)) {
                                                odatosms.setDisable(true);

//                                                if (!StringUtilities.validateEmail(email.getText())) {
//                                                    warningemail.setVisible(true);
//                                                    odatoemail.setDisable(true);
//                                                    mensajeAd.setText("No es posible activar el cambio de mecanismo a email. Email inválido");
//                                                }
                                                if ((num_cel.getText().isEmpty()) || (!(num_cel.getText().startsWith("3")) || (num_cel.getText().length() < 10))) {
                                                    warningcelular.setVisible(true);
                                                    odatosms.setDisable(true);
                                                    //  mensajeAd.setText("No es posible activar el cambio de mecanismo a celular. Número de celular inválido");

                                                }

                                                if (!StringUtilities.validateEmail(email.getText()) || email.getText().isEmpty()) {
                                                    warningemail.setVisible(true);
                                                    odatoemail.setDisable(true);
//                                                    mensajeAd.setText("No es posible activar el cambio de mecanismo a email. Email inválido");
                                                    mensajeAd.setText("No es posible realizar el cambio de mecanismo a Mail. \nPorque no se tiene el Mail del cliente");
                                                }

//                                                if (odatosms.isDisable() && odatoemail.isDisable()) {
////                                                    mensajeAd.setText("No se tiene información de los datos de seguridad del cliente");
//                                                    mensajeAd.setText("No es posible realizar el cambio de mecanismo. \nPorque no se tiene datos de seguridad del cliente o son inválidos");
//                                                }
                                            } else {
                                                odatosms.setDisable(true);
                                                odatoemail.setDisable(true);
//                                                mensajeAd.setText("No es posible activar el cambio de mecanismo. No se tiene información del OTP");
                                                mensajeAd.setText("No es posible realizar el cambio de mecanismo. \nPorque no se tiene datos de seguridad del cliente o son inválidos");

                                            }

                                        } else {
                                        }

                                        Atlas.vista.show();
                                    } catch (Exception e) {
                                        Logger.getLogger(ActualizacionDatosSeguridadController.class.getName()).log(Level.SEVERE, null, e);
                                    }
                                }
                            });
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("ERROR EN LA CONSULTA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                    final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                                    pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
                                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                                    if (arbol_pagos != null) {
                                        arbol_pagos.setDisable(false);
                                    }

                                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                                    if (arbol_saldos != null) {
                                        arbol_saldos.setDisable(false);
                                    }

                                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                                    if (arbol_transf != null) {
                                        arbol_transf.setDisable(false);
                                    }

                                    final TreeView<String> arbol_bloqueos = (TreeView<String>) pane.lookup("#arbolbloqueos");
                                    if (arbol_bloqueos != null) {
                                        arbol_bloqueos.setDisable(false);
                                    }

                                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                                    if (arbol_servadd != null) {
                                        arbol_servadd.setDisable(false);
                                    }

                                    arbol_servadd.getSelectionModel().clearSelection();
                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception e) {
                                        gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                    }
                                    Atlas.vista.show();
                                }
                            });

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            // System.out.println("cancelo la tarea");
                        }
                    });

                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });

    }

    public class GetDatosSeguridadTask extends Service<ModeloDatosSeguridad> {

        @Override
        protected Task<ModeloDatosSeguridad> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA datos de seguridad             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaDatosSeg = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramaDatosSeg.append("850,012,");
                    tramaDatosSeg.append(datosCliente.getRefid());
                    tramaDatosSeg.append(",");
                    tramaDatosSeg.append(datosCliente.getId_cliente());
                    tramaDatosSeg.append(",");
                    tramaDatosSeg.append(datosCliente.getContraseña());
                    tramaDatosSeg.append(",");
                    tramaDatosSeg.append(datosCliente.getTokenOauth());
                    tramaDatosSeg.append(",~");

//                    System.out.println("trama DatosSeguridad" + tramaDatosSeg.toString());
                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ DATOS SEGURIDAD = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,012,000,Consulta de datos de seguridad exitosa,N,,P,3002588382,~";
                        //850,012,000,EXITOSO,N,                                                            , ,                    ,~ 
                        //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaDatosSeg.toString());
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDatosSeg.toString());
//                        System.out.println("respuesta Lista : " + Respuesta);

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ DATOS SEGURIDAD = " + "##" + gestorDoc.obtenerHoraActual());
                            //850,012,000,EXITOSO,N,                                                            , ,                    ,~ 
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP_CTG, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaDatosSeg.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDatosSeg.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (CambioMecanismoController.cancelartarea.get()) {
                                        //  System.out.println("cancelo tarea");
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

                        if (CambioMecanismoController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final ModeloDatosSeguridad datosSeguridad = ModeloDatosSeguridad.getDatosSeguridad();
                            String[] data = Respuesta.split(",");
                            datosSeguridad.setRegistradoAlertas(data[4].trim());
                            datosSeguridad.setEmail(data[5].trim());
                            datosSeguridad.setTipo_email(data[6].trim());
                            datosSeguridad.setCelular(data[7].trim());

                            ModeloDatosSeguridad.setDatosSeguridad(datosSeguridad);

                        }

                    } else {

                        if (CambioMecanismoController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                }
                            });
                            throw new Exception("ERROR");
                        }
                    }
                                  return ModeloDatosSeguridad.getDatosSeguridad();
                }
            };
        }
    }
}
