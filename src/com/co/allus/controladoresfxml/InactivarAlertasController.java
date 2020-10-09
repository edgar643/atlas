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
public class InactivarAlertasController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button continuar;

    @FXML
    private Button cancelar;

    @FXML
    private TextField correoCliente;

    @FXML
    private TextField telefonoCliente;

    @FXML
    private TextField avisos;

    @FXML
    private ProgressBar progreso;

    @FXML
    private Label ConfirmInactivarAlertas;

    @FXML
    private Label titulosActDatSeguridad;

    private transient Service<Void> serviceInactivarAlertas = InactivarAlertasTask();
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ModeloDatosSeguridad> task = new InactivarAlertasController.GetDatosAlertas();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert ConfirmInactivarAlertas != null : "fx:id=\"ConfirmInactivarAlertas\" was not injected: check your FXML file 'InactivarAlertas.fxml'.";
        assert avisos != null : "fx:id=\"avisos\" was not injected: check your FXML file 'InactivarAlertas.fxml'.";
        assert continuar != null : "fx:id=\"continuar_no\" was not injected: check your FXML file 'InactivarAlertas.fxml'.";
        assert cancelar != null : "fx:id=\"continuar_si\" was not injected: check your FXML file 'InactivarAlertas.fxml'.";
        assert correoCliente != null : "fx:id=\"correoCliente\" was not injected: check your FXML file 'InactivarAlertas.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'InactivarAlertas.fxml'.";
        assert telefonoCliente != null : "fx:id=\"telefonoCliente\" was not injected: check your FXML file 'InactivarAlertas.fxml'.";
        assert titulosActDatSeguridad != null : "fx:id=\"titulosActDatSeguridad\" was not injected: check your FXML file 'InactivarAlertas.fxml'.";

        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);
        InactivarAlertasController.cancelartarea.set(false);
        correoCliente.setEditable(false);
        telefonoCliente.setEditable(false);
    }

    public void mostrarInactivarAlertas() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/InactivarAlertas.fxml");
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

                    final TextField Celular = (TextField) root.lookup("#telefonoCliente");
                    final TextField Email = (TextField) root.lookup("#correoCliente");

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
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setPrefSize(150, 150);
                    p.setLayoutX(200);
                    p.setLayoutY(50);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
//                    Atlas.vista.show();
                    root.getChildren().addAll(veil, p);
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

                                        Celular.setText(Cel);
                                        Email.setText(Mail);

                                        Atlas.vista.show();
                                    } catch (Exception ex) {
                                        Logger.getLogger(InactivarAlertasController.class.getName()).log(Level.SEVERE, null, ex);
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
                } catch (Exception ex) {
                    Logger.getLogger(InactivarAlertasController.class.getName()).log(Level.SEVERE, null, ex);
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });
    }

    public class GetDatosAlertas extends Service<ModeloDatosSeguridad> {

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

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ DATOS SEGURIDAD = " + gestorDoc.obtenerHoraActual());
                        Respuesta = "850,012,000,TRANSACCION EXITOSA,S,alexlopez@gmail.com,P,3003565356,~";
                        // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDatosSeg.toString());
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ DATOS SEGURIDAD = " + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDatosSeg.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (InactivarAlertasController.cancelartarea.get()) {
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
                        if ("N".equals(Respuesta.split(",")[4])) {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = "EL CLIENTE NO ESTA INSCRITO EN EL SERVICIO DE ALERTAS Y NOTIFICACIONES";
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                }
                            });
                            throw new Exception("ERROR");
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
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (InactivarAlertasController.cancelartarea.get()) {
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
            InactivarAlertasController.cancelartarea.set(true);
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
        if (serviceInactivarAlertas.isRunning()) {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceInactivarAlertas.progressProperty());
            serviceInactivarAlertas.reset();
            serviceInactivarAlertas.start();
        } else {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceInactivarAlertas.progressProperty());
            serviceInactivarAlertas.reset();
            serviceInactivarAlertas.start();
        }

        serviceInactivarAlertas.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono SI Inactivar Alertas" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                InactivarAlertasController.cancelartarea.set(false);
            }
        });

        serviceInactivarAlertas.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono NO Inactivar Alertas" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceInactivarAlertas.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                // System.out.println("fallo");
            }
        });
    }

    public Service<Void> InactivarAlertasTask() {
        serviceInactivarAlertas = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final Cliente cliente = Cliente.getCliente();

                        String Respuesta = new String();
                        final StringBuilder tramaInactivarAlertas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();

                        tramaInactivarAlertas.append("754,062,");
                        tramaInactivarAlertas.append(cliente.getRefid());
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(AtlasConstantes.COD_INACTIVAR_REGISTRO_ALERTAS);
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(cliente.getId_cliente());
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(System.getProperties().getProperty("user.name").substring(0, 15));
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(cliente.getContraseña());
                        tramaInactivarAlertas.append(",");
                        tramaInactivarAlertas.append(cliente.getTokenOauth());
                        tramaInactivarAlertas.append(",~");
//                        System.out.println("trama confirmar desbloqueo alm :" + tramaDesbloqueoAlmConfir);
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ AlertasEnviadas = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaInactivarAlertas.toString());
                            // Respuesta = "754,062,000,TRANSACCION EXITOSA,TRACE~";
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
                                        if (InactivarAlertasController.cancelartarea.get()) {
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
                                    final InactivarAlertasFinController controller = new InactivarAlertasFinController();
                                    controller.mostrarInactivarAlertasFinController();
                                }
                            });
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (InactivarAlertasController.cancelartarea.get()) {
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
        return serviceInactivarAlertas;
    }
}
