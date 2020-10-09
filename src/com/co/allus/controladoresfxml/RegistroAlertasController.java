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
import com.co.allus.modelo.actualizaciondatosseguridad.ModeloDatosDeSeguridad;
import com.co.allus.modelo.cambiomecanismo.ModeloDatosSeguridad;
import com.co.allus.userComponent.RestrictiveTextField;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class RegistroAlertasController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private RestrictiveTextField Celular;
    @FXML
    private TextField Email;
    @FXML
    private TextField avisos;
    @FXML
    private Button cancelar;
    @FXML
    private TextField cedulaCliente;
    @FXML
    private RestrictiveTextField confirmarCelular;
    @FXML
    private TextField confirmarEmail;
    @FXML
    private Button continuar;
    @FXML
    private TextField nombreCliente;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label tituloregAln;
    private transient Service<Void> serviceActualizarRegAln = ActualizarAlertasTask();
    private transient Service<Void> serviceInscripcionRegAln = InscripcionAlertasTask();
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ModeloDatosSeguridad> task = new RegistroAlertasController.GetDatosRegistro();
    private static String registrado = "N";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert Celular != null : "fx:id=\"Celular\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert Email != null : "fx:id=\"Email\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert avisos != null : "fx:id=\"avisos\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert cedulaCliente != null : "fx:id=\"cedulaCliente\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert confirmarCelular != null : "fx:id=\"confirmarCelular\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert confirmarEmail != null : "fx:id=\"confirmarEmail\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert continuar != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert nombreCliente != null : "fx:id=\"nombreCliente\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";
        assert tituloregAln != null : "fx:id=\"titulosActDatSeguridad\" was not injected: check your FXML file 'RegistroAlertas.fxml'.";

        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);
        RegistroAlertasController.cancelartarea.set(false);
        continuar.setDisable(true);
        avisos.setEditable(false);
    }

    public static String getRegistrado() {
        return registrado;
    }

    public static void setRegistrado(String registrado) {
        RegistroAlertasController.registrado = registrado;
    }

    public void mostrarRegistroAlertas() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/RegistroAlertas.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button continuar_trx = (Button) root.lookup("#continuar");
                    final Button cancelar_trx = (Button) root.lookup("#cancelar");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    final TextField nombreCliente = (TextField) root.lookup("#nombreCliente");
                    final TextField cedulaCliente = (TextField) root.lookup("#cedulaCliente");

                    nombreCliente.setText(datosCliente.getNombre());
                    nombreCliente.setEditable(false);
                    cedulaCliente.setText(datosCliente.getId_cliente());
                    cedulaCliente.setEditable(false);

                    final Label Titulo = (Label) root.lookup("#tituloregAln");
                    final TextField Celular = (TextField) root.lookup("#Celular");
                    final TextField Email = (TextField) root.lookup("#Email");

                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setPrefSize(150, 150);
                    p.setLayoutX(200);
                    p.setLayoutY(50);

                    root.getChildren().addAll(veil, p);

                    final DropShadow shadow = new DropShadow();
                    continuar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_trx.setCursor(Cursor.HAND);
                                    continuar_trx.setEffect(shadow);
                                }
                            });

                    continuar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_trx.setCursor(Cursor.DEFAULT);
                                    continuar_trx.setEffect(null);

                                }
                            });

                    cancelar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_trx.setCursor(Cursor.HAND);
                                    cancelar_trx.setEffect(shadow);
                                }
                            });

                    cancelar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_trx.setCursor(Cursor.DEFAULT);
                                    cancelar_trx.setEffect(null);

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

                    final TreeView<String> arbol_servad = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servad != null) {
                        arbol_servad.setDisable(false);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
//                    Atlas.vista.show();


                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        final ModeloDatosSeguridad datosSeguridad = task.getValue();

                                        final String Cel = datosSeguridad.getCelular().trim();
                                        final String Mail = datosSeguridad.getEmail().trim();
                                        final String Registrado = datosSeguridad.getRegistradoAlertas().trim();



                                        if ("S".equals(Registrado.trim())) {
                                            setRegistrado(Registrado.trim());
                                            Celular.setText(Cel);
                                            Email.setText(Mail);
                                            Titulo.setText("ACTUALIZAR SERVICIO DE ALERTAS Y NOTIFICACIONES");
                                        } else if ("N".equals(Registrado.trim())) {
                                            setRegistrado(Registrado.trim());
                                            Titulo.setText("REGISTRO AL SERVICIO DE ALERTAS Y NOTIFICACIONES");
                                        }

                                        Atlas.vista.show();
                                    } catch (Exception e) {
                                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                    }
                                }
                            });
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                                System.out.println("ERROR EN LA CONSULTA");
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
                        }
                    });



                } catch (Exception e) {
//                    e.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }
            }
        });
    }

    public class GetDatosRegistro extends Service<ModeloDatosSeguridad> {

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

//                    tramaDatosSeg.append("754,059,");
//                    tramaDatosSeg.append(datosCliente.getRefid());
//                    tramaDatosSeg.append(",");
//                    tramaDatosSeg.append(AtlasConstantes.COD_CONSULTA_PARAMETRIZACION);
//                    tramaDatosSeg.append(",");
//                    tramaDatosSeg.append(datosCliente.getId_cliente());
//                    tramaDatosSeg.append(",");
//                    tramaDatosSeg.append("1"); //NUM PAGINA
//                    tramaDatosSeg.append(",");
//                    tramaDatosSeg.append(" ");
//                    tramaDatosSeg.append(",");
//                    tramaDatosSeg.append(" ");
//                    tramaDatosSeg.append(",");
//                    tramaDatosSeg.append(datosCliente.getContraseña());
//                    tramaDatosSeg.append(",");
//                    tramaDatosSeg.append(datosCliente.getTokenOauth());
//                    tramaDatosSeg.append(",~");





                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ DATOS SEGURIDAD = " + gestorDoc.obtenerHoraActual());
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDatosSeg.toString());
                        //Respuesta = "850,012,000,TRANSACCION EXITOSA,S,alexlopez@gmail.com,P,3003565356,~";
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ DATOS SEGURIDAD = " + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDatosSeg.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (RegistroAlertasController.cancelartarea.get()) {
                                        cancel();
                                    } else {
                                        new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_ACT_DATOS_SEG);
                                        failed();
                                    }
                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {
                        if ("S".equals(Respuesta.split(",")[4])) {
                            final ModeloDatosSeguridad datosSeguridad = ModeloDatosSeguridad.getDatosSeguridad();

                            String[] data = Respuesta.split(",");
                            datosSeguridad.setRegistradoAlertas(data[4].trim());
                            datosSeguridad.setEmail(data[5].trim());
                            datosSeguridad.setTipo_email(data[6].trim());
                            datosSeguridad.setCelular(data[7].trim());
                            ModeloDatosSeguridad.setDatosSeguridad(datosSeguridad);
                        }
                        {
                            final ModeloDatosSeguridad datosSeguridad = ModeloDatosSeguridad.getDatosSeguridad();

                            String[] data = Respuesta.split(",");
                            datosSeguridad.setRegistradoAlertas(data[4].trim());
                            ModeloDatosSeguridad.setDatosSeguridad(datosSeguridad);

                        }
                    } else {
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (RegistroAlertasController.cancelartarea.get()) {
                                    cancel();
                                } else {
                                    new ModalMensajes(mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_ACT_DATOS_SEG);
//                                        continuar_op.setDisable(false);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                            final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                            final Label label_menu = (Label) pane.lookup("#label_saldos");

                                            final TreeView<String> arbol_consultas = (TreeView<String>) pane.lookup("#arbol_consultas");
                                            if (arbol_consultas != null) {
                                                arbol_consultas.setDisable(false);
                                            }

                                            arbol_consultas.getSelectionModel().clearSelection();
                                            label_menu.setVisible(true);

                                            try {
                                                pane.getChildren().remove(3);
                                            } catch (Exception ex) {
                                                gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                    return ModeloDatosSeguridad.getDatosSeguridad();
                }
            };
        }
    }

    @FXML
    void cancelar_trx(ActionEvent event) {
        try {
            RegistroAlertasController.cancelartarea.set(true);
        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        } finally {
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
    }

    @FXML
    void continuar_trx(ActionEvent event) {

        if ("S".equalsIgnoreCase(getRegistrado())) {
            if (serviceActualizarRegAln.isRunning()) {
                continuar.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceActualizarRegAln.progressProperty());
                serviceActualizarRegAln.reset();
                serviceActualizarRegAln.start();
            } else {
                continuar.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceActualizarRegAln.progressProperty());
                serviceActualizarRegAln.reset();
                serviceActualizarRegAln.start();
            }

            serviceActualizarRegAln.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono SI Inactivar Alertas" + "##" + gestorDoc.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                    RegistroAlertasController.cancelartarea.set(false);
                }
            });

            serviceActualizarRegAln.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono NO Inactivar Alertas" + "##" + gestorDoc.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            serviceActualizarRegAln.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                }
            });

        } else {

            if (serviceInscripcionRegAln.isRunning()) {
                continuar.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceInscripcionRegAln.progressProperty());
                serviceInscripcionRegAln.reset();
                serviceInscripcionRegAln.start();
            } else {
                continuar.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceInscripcionRegAln.progressProperty());
                serviceInscripcionRegAln.reset();
                serviceInscripcionRegAln.start();
            }

            serviceInscripcionRegAln.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono SI Inactivar Alertas" + "##" + gestorDoc.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                    RegistroAlertasController.cancelartarea.set(false);
                }
            });

            serviceInscripcionRegAln.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono NO Inactivar Alertas" + "##" + gestorDoc.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            serviceInscripcionRegAln.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                }
            });

        }


    }

    public Service<Void> ActualizarAlertasTask() {
        serviceActualizarRegAln = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final Cliente cliente = Cliente.getCliente();

                        String Respuesta = new String();
                        final StringBuilder tramaInactivarAlertas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();

                        final String celularCliente = Celular.getText();
                        final String emailCliente = Email.getText();

                        tramaInactivarAlertas.append("754,063,");
                        tramaInactivarAlertas.append(cliente.getRefid());
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(AtlasConstantes.COD_ACTUALIZAR_INSCRIPCION_ALERTAS);
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(cliente.getId_cliente());
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(celularCliente);
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(emailCliente);
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append("");
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append("");
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(System.getProperties().getProperty("user.name").substring(0, 10));
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(cliente.getContraseña());
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(cliente.getTokenOauth());
                        tramaInactivarAlertas.append(",~");

                        //System.out.println("trama confirmar desbloqueo alm :" + tramaDesbloqueoAlmConfir);
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ AlertasEnviadas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaInactivarAlertas.toString());
                            //Respuesta = "754,062,000,TRANSACCION EXITOSA,TRACE~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listrarconsultaprestamos = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaInactivarAlertas.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                            } catch (Exception ex1) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (RegistroAlertasController.cancelartarea.get()) {
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            failed();
                                        }
                                    }
                                });
                            }
                        }

                        if ("000".equals(Respuesta.split(",")[2])) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final RegistroAlertasFinController controller = new RegistroAlertasFinController();
                                    controller.mostrarRegistroAlertasFinController(getRegistrado());
                                }
                            });
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (RegistroAlertasController.cancelartarea.get()) {
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
        return serviceActualizarRegAln;
    }

    public Service<Void> InscripcionAlertasTask() {
        serviceInscripcionRegAln = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final Cliente cliente = Cliente.getCliente();

                        String Respuesta = new String();
                        final StringBuilder tramainscripcionalertas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();

                        final String celularCliente = Celular.getText();
                        final String emailCliente = Email.getText();

                        tramainscripcionalertas.append("754,065,");
                        tramainscripcionalertas.append(cliente.getRefid());
                        tramainscripcionalertas.append(",");
                        tramainscripcionalertas.append(AtlasConstantes.COD_INSCRIPCION_REGISTRO_ALERTAS);
                        tramainscripcionalertas.append(",");
                        tramainscripcionalertas.append(cliente.getId_cliente());
                        tramainscripcionalertas.append(",");
                        tramainscripcionalertas.append(celularCliente);
                        tramainscripcionalertas.append(",");
                        tramainscripcionalertas.append(emailCliente);
                        tramainscripcionalertas.append(",");
                        tramainscripcionalertas.append("");
                        tramainscripcionalertas.append(",");
                        tramainscripcionalertas.append("");
                        tramainscripcionalertas.append(",");
                        tramainscripcionalertas.append(System.getProperties().getProperty("user.name").substring(0, 10));
                        tramainscripcionalertas.append(",");
                        tramainscripcionalertas.append(cliente.getContraseña());
                        tramainscripcionalertas.append(",");
                        tramainscripcionalertas.append(cliente.getTokenOauth());
                        tramainscripcionalertas.append(",~");

//                        System.out.println("trama tramainscripcionalertas :" + tramainscripcionalertas);
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ AlertasEnviadas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramainscripcionalertas.toString());
                            // Respuesta = "754,064,000,TRANSACCION EXITOSA,TRACE~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listrarconsultaprestamos = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramainscripcionalertas.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                            } catch (Exception ex1) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (RegistroAlertasController.cancelartarea.get()) {
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            failed();
                                        }
                                    }
                                });
                            }
                        }

                        if ("000".equals(Respuesta.split(",")[2])) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final RegistroAlertasFinController controller = new RegistroAlertasFinController();
                                    controller.mostrarRegistroAlertasFinController(getRegistrado());
                                }
                            });
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (RegistroAlertasController.cancelartarea.get()) {
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
        return serviceInscripcionRegAln;
    }

    @FXML
    void keyReleasedEmail(KeyEvent event) {
        StringUtilities.borrarClipBoard();
        if (KeyCode.CONTROL.equals(event.getCode())) {
            Runtime rt = Runtime.getRuntime();
            try {
                String comandoEnvio = "cmd.exe /C echo off | clip";
                Process proceso = rt.exec(comandoEnvio);
            } catch (IOException ex) {
                Logger.getLogger(ActualizacionDatosSeguridadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!KeyCode.CONTROL.equals(event.getCode())) {
            String tipiado = event.getText(); // lo que agrega (caracter)
            final StringUtilities validaciones = new StringUtilities();
            if (Email.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS2);                //Debes ingresar un E-mail
            } else if (!validaciones.validateEmail(Email.getText())) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS3);                //Email no valido
            } else if (confirmarEmail.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS4);                //Debes ingresar un Confirmar E-mail
            } else if (!validaciones.validateEmail(confirmarEmail.getText())) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS5);                //Confirmar Email no valido
            } else if (!(Email.getText().trim().equals(confirmarEmail.getText().trim()))) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS6);                //Confirmación de E-mail diferente
            } else if (Celular.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS7);                //Debes ingresar un Celular
            } else if (!Celular.getText().startsWith("3") || Celular.getText().trim().length() != 10) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS8);                //Número de Celular no válido
            } else if (confirmarCelular.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS9);                //Debes ingresar un Confirmar Celular
            } else if (!confirmarCelular.getText().startsWith("3") || Celular.getText().trim().length() != 10) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS10);                //Número de Confirmar Celular no válido
            } else if (!(Celular.getText().trim().equals(confirmarCelular.getText().trim()))) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS11);                //Confirmación de Número de Celular diferente
            } else {
                avisos.setText("");
                continuar.setDisable(false);
            }
        } else {
            if (KeyCode.CONTROL.equals(event.getCode())) {
                String mailtemp = Email.getText();
                StringUtilities.borrarClipBoard();
                if (!Email.getText().endsWith("@")) {
                    Email.setText(ModeloDatosDeSeguridad.getEmail());
                }
                Email.end();
            }
        }
        StringUtilities.borrarClipBoard();
    }

    @FXML
    void keyReleasedconfirmarEmail(KeyEvent event) {
        StringUtilities.borrarClipBoard();
        if (KeyCode.CONTROL.equals(event.getCode())) {
            Runtime rt = Runtime.getRuntime();
            try {
                String comandoEnvio = "cmd.exe /C echo off | clip";
                Process proceso = rt.exec(comandoEnvio);
            } catch (IOException ex) {
                Logger.getLogger(ActualizacionDatosSeguridadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!KeyCode.CONTROL.equals(event.getCode())) {
            String tipiado = event.getText(); // lo que agrega (caracter)
            final StringUtilities validaciones = new StringUtilities();
            if (Email.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS2);                //Debes ingresar un E-mail
            } else if (!validaciones.validateEmail(Email.getText())) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS3);                //Email no valido
            } else if (confirmarEmail.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS4);                //Debes ingresar un Confirmar E-mail
            } else if (!validaciones.validateEmail(confirmarEmail.getText())) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS5);                //Confirmar Email no valido
            } else if (!(Email.getText().trim().equals(confirmarEmail.getText().trim()))) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS6);                //Confirmación de E-mail diferente
            } else if (Celular.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS7);                //Debes ingresar un Celular
            } else if (!Celular.getText().startsWith("3") || Celular.getText().trim().length() != 10) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS8);                //Número de Celular no válido
            } else if (confirmarCelular.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS9);                //Debes ingresar un Confirmar Celular
            } else if (!confirmarCelular.getText().startsWith("3") || Celular.getText().trim().length() != 10) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS10);                //Número de Confirmar Celular no válido
            } else if (!(Celular.getText().trim().equals(confirmarCelular.getText().trim()))) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS11);                //Confirmación de Número de Celular diferente
            } else {
                avisos.setText("");
                continuar.setDisable(false);
            }
        } else {
            if (KeyCode.CONTROL.equals(event.getCode())) {
                String mailtempconfirmar = confirmarEmail.getText();
                StringUtilities.borrarClipBoard();
                if (!confirmarEmail.getText().endsWith("@")) {
                    confirmarEmail.setText(ModeloDatosDeSeguridad.getEmail());
                }
                confirmarEmail.end();
            }
        }
        StringUtilities.borrarClipBoard();
    }

    @FXML
    void valkeypressed(KeyEvent event) {
        StringUtilities.borrarClipBoard();
        if (KeyCode.CONTROL.equals(event.getCode())) {

//            System.out.println("Presiono CTRL");
            Runtime rt = Runtime.getRuntime();
            try {
                String comandoEnvio = "cmd.exe /C echo off | clip";
                Process proceso = rt.exec(comandoEnvio);

            } catch (IOException ex) {
                Logger.getLogger(ActualizacionDatosSeguridadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        final StringUtilities validaciones = new StringUtilities();
        if (!KeyCode.CONTROL.equals(event.getCode())) {

            ModeloDatosDeSeguridad.setCelular(Celular.getText());
            if (KeyCode.DELETE.equals(event.getCode())) {
                if (!(event.getCode().impl_getChar().trim().equals(""))) {
                    continuar.setDisable(false);
                } else {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(Celular, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    Celular.clear();
                    Celular.fireEvent(keyEvent);
                    continuar.setDisable(true);
                }
            }

            if (Celular.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS7);                //Debes ingresar un Celular
            } else if (!Celular.getText().startsWith("3") || Celular.getText().trim().length() != 10) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS8);                //Número de Celular no válido
            } else if (confirmarCelular.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS9);                //Debes ingresar un Confirmar Celular
            } else if (!confirmarCelular.getText().startsWith("3") || Celular.getText().trim().length() != 10) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS10);                //Número de Confirmar Celular no válido
            } else if (!(Celular.getText().trim().equals(confirmarCelular.getText().trim()))) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS11);                //Confirmación de Número de Celular diferente
            } else if (Email.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS2);                //Debes ingresar un E-mail
            } else if (!validaciones.validateEmail(Email.getText())) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS3);                //Email no valido
            } else if (confirmarEmail.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS4);                //Debes ingresar un Confirmar E-mail
            } else if (!validaciones.validateEmail(confirmarEmail.getText())) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS5);                //Confirmar Email no valido
            } else if (!(Email.getText().trim().equals(confirmarEmail.getText().trim()))) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS6);                //Confirmación de E-mail diferente
            } else {
                avisos.setText("");
                continuar.setDisable(false);
            }

        } else {
//            final String dato = Celular.getText();
            if (KeyCode.CONTROL.equals(event.getCode())) {
                StringUtilities.borrarClipBoard();
//                final ModeloDatosDeSeguridad modelo = new ModeloDatosDeSeguridad();
                Celular.setText(ModeloDatosDeSeguridad.getCelular());
//                Celular.setText(celTemp);
                Celular.end();
            }
        }
    }

    @FXML
    void valkeyconfirmarpressed(KeyEvent event) {
        StringUtilities.borrarClipBoard();
        if (KeyCode.CONTROL.equals(event.getCode())) {

//            System.out.println("Presiono CTRL");
            Runtime rt = Runtime.getRuntime();
            try {
                String comandoEnvio = "cmd.exe /C echo off | clip";
                Process proceso = rt.exec(comandoEnvio);

            } catch (IOException ex) {
                Logger.getLogger(ActualizacionDatosSeguridadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        final StringUtilities validaciones = new StringUtilities();
        if (!KeyCode.CONTROL.equals(event.getCode())) {

            ModeloDatosDeSeguridad.setCelular(confirmarCelular.getText());
            if (KeyCode.DELETE.equals(event.getCode())) {
                if (!(event.getCode().impl_getChar().trim().equals(""))) {
                    continuar.setDisable(false);
                } else {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(confirmarCelular, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    confirmarCelular.clear();
                    confirmarCelular.fireEvent(keyEvent);
                    continuar.setDisable(true);
                }
            }

            if (Celular.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS7);                //Debes ingresar un Celular
            } else if (!Celular.getText().startsWith("3") || Celular.getText().trim().length() != 10) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS8);                //Número de Celular no válido
            } else if (confirmarCelular.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS9);                //Debes ingresar un Confirmar Celular
            } else if (!confirmarCelular.getText().startsWith("3") || Celular.getText().trim().length() != 10) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS10);                //Número de Confirmar Celular no válido
            } else if (!(Celular.getText().trim().equals(confirmarCelular.getText().trim()))) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS11);                //Confirmación de Número de Celular diferente
            } else if (Email.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS2);                //Debes ingresar un E-mail
            } else if (!validaciones.validateEmail(Email.getText())) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS3);                //Email no valido
            } else if (confirmarEmail.getText().isEmpty()) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS4);                //Debes ingresar un Confirmar E-mail
            } else if (!validaciones.validateEmail(confirmarEmail.getText())) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS5);                //Confirmar Email no valido
            } else if (!(Email.getText().trim().equals(confirmarEmail.getText().trim()))) {
                continuar.setDisable(true);
                avisos.setText(AtlasConstantes.AVISOS_ALERTAS6);                //Confirmación de E-mail diferente
            } else {
                avisos.setText("");
                continuar.setDisable(false);
            }

        } else {
//            final String dato = confirmarCelular.getText();
            if (KeyCode.CONTROL.equals(event.getCode())) {
                StringUtilities.borrarClipBoard();
//                final ModeloDatosDeSeguridad modelo = new ModeloDatosDeSeguridad();
                confirmarCelular.setText(ModeloDatosDeSeguridad.getCelular());
//                confirmarCelular.setText(celTemp);
                confirmarCelular.end();
            }
        }
    }
}
