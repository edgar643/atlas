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
import com.co.allus.modelo.girosnal.tablaInfoGnralGirosnal;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class GirosCancelConfirmfinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelarOP;
    @FXML
    private Button continuarOP;
    @FXML
    private Label lblconfirmar;
    @FXML
    private Label lblmotivo;
    @FXML
    private ComboBox<String> motivoCancelacion;
    @FXML
    private Pane pane_modalGiros;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Label titulosGiros;
    @FXML
    private ImageView icono;
    private static GestorDocumental docs = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient Service<Void> serviceCancelGiro;
    private static tablaInfoGnralGirosnal selectedGiro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'GirosNalCancelConfirmfin.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'GirosNalCancelConfirmfin.fxml'.";
        assert lblconfirmar != null : "fx:id=\"lblconfirmar\" was not injected: check your FXML file 'GirosNalCancelConfirmfin.fxml'.";
        assert lblmotivo != null : "fx:id=\"lblmotivo\" was not injected: check your FXML file 'GirosNalCancelConfirmfin.fxml'.";
        assert motivoCancelacion != null : "fx:id=\"motivoCancelacion\" was not injected: check your FXML file 'GirosNalCancelConfirmfin.fxml'.";
        assert pane_modalGiros != null : "fx:id=\"pane_modalGiros\" was not injected: check your FXML file 'GirosNalCancelConfirmfin.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'GirosNalCancelConfirmfin.fxml'.";
        assert titulosGiros != null : "fx:id=\"titulosGiros\" was not injected: check your FXML file 'GirosNalCancelConfirmfin.fxml'.";
        assert icono != null : "fx:id=\"icono\" was not injected: check your FXML file 'GirosNalCancelConfirmfin.fxml'.";

        progreso.setVisible(false);
    }

    @FXML
    void cancelarOP(ActionEvent event) {
        final GirosNalCancelacionController controller = new GirosNalCancelacionController();
        controller.mostrarGirosNalCancelar(0);
    }

    @FXML
    void continuarOP(ActionEvent event) {
        consultar_Costo().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario presionó cancelarGiro" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        consultar_Costo().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo CancelarGiro" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (consultar_Costo().isRunning()) {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(consultar_Costo().progressProperty());
            consultar_Costo().reset();
            consultar_Costo().start();
        } else {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(consultar_Costo().progressProperty());
            consultar_Costo().start();
        }
    }

    public static tablaInfoGnralGirosnal getSelectedGiro() {
        return selectedGiro;
    }

    public static void setSelectedGiro(tablaInfoGnralGirosnal selectedGiro) {
        GirosCancelConfirmfinController.selectedGiro = selectedGiro;
    }

    public void mostrarCancelGirosConfirm(final tablaInfoGnralGirosnal selecteditem) {

        setSelectedGiro(selecteditem);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/GirosNalCancelConfirmfin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Label lblconfirmar = (Label) root.lookup("#lblconfirmar");
                    final Label lblmotivo = (Label) root.lookup("#lblmotivo");
                    final StringBuilder mensaje = new StringBuilder();

                    final ComboBox<String> comboMotivoCancelacion = (ComboBox<String>) root.lookup("#motivoCancelacion");
                    final ObservableList<String> emptyList1 = FXCollections.emptyObservableList();
                    comboMotivoCancelacion.setItems(emptyList1);
                    final List<String> listTipoMail = new ArrayList<String>();
                    listTipoMail.add("Razones personales/fallecimiento");
                    listTipoMail.add("No hay plaza");
                    comboMotivoCancelacion.setItems(FXCollections.observableArrayList(listTipoMail));

                    mensaje.append("¿ Esta Seguro de realizar cancelacion del\n ");
                    mensaje.append("Giro por valor ");
                    mensaje.append(selecteditem.colValorGiroProperty().getValue());
                    mensaje.append(" del ");
                    mensaje.append(selecteditem.colFechaVtaProperty().getValue());
                    mensaje.append("?");

                    lblconfirmar.setText(mensaje.toString());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();

                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    public Service<Void> consultar_Costo() {
        serviceCancelGiro = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramacancelGiro = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final tablaInfoGnralGirosnal selectedGiro1 = getSelectedGiro();

                        //850,070,connid,codTransaccion,identificacion,concecutivoRef2,ceLulaGirador,CelularBeneficiario,claveHardware,~
                        //tramacancelGiro.append("850,070,");
                        tramacancelGiro.append("850,083,");
                        tramacancelGiro.append(datosCliente.getRefid()); //CONNID
                        tramacancelGiro.append(",");
                        tramacancelGiro.append(AtlasConstantes.COD_CANCELACION_GIRO);//CODTRX
                        tramacancelGiro.append(",");
                        tramacancelGiro.append(datosCliente.getId_cliente());//CEDULA
                        tramacancelGiro.append(",");
                        tramacancelGiro.append(selectedGiro1.colConsecutivoProperty().getValue());//TIPOPERSONA
                        tramacancelGiro.append(",");
                        if (motivoCancelacion.getValue().trim().equals("Razones personales/fallecimiento")) {
                            tramacancelGiro.append("RP");
                        } else {
                            tramacancelGiro.append("NP");
                        }
                        tramacancelGiro.append(",");
                        tramacancelGiro.append(datosCliente.getContraseña());
                        tramacancelGiro.append(",");
                        tramacancelGiro.append(datosCliente.getTokenOauth());
                        tramacancelGiro.append(",~");

//                        System.out.println("TRAMA cancelacion giros :" + tramacancelGiro);

                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Cancelar Giros Nacionales = " + "##" + docs.obtenerHoraActual());
                            // Respuesta = "850,070,000,TRANSACCION EXITOSA,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramacancelGiro.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  cancelar Giro = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal Cancelar Giros Nacionales = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Cancelar Giros Nacionales = " + "##" + docs.obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor("172.20.1.70", 9930, tramaCosto.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramacancelGiro.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde cancelar giro ctg = " + Respuesta + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctgcancelar giro  = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        icono.setVisible(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                        cancel();
                                    }
                                });
                            }
                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tablaInfoGnralGirosnal selecteditem = getSelectedGiro();
                                    final StringBuilder mensaje = new StringBuilder();

                                    lblmotivo.setVisible(false);
                                    motivoCancelacion.setVisible(false);
                                    mensaje.append("El Giro por valor ");
                                    mensaje.append(selecteditem.colValorGiroProperty().getValue());
                                    mensaje.append(" del ");
                                    mensaje.append(selecteditem.colFechaVtaProperty().getValue());
                                    mensaje.append(" ha sido cancelado.");

                                    icono.setVisible(false);
                                    continuarOP.setLayoutX(200);
                                    lblconfirmar.setText(mensaje.toString());
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                    cancelarOP.setVisible(false);
                                    continuarOP.setText("Continuar");
                                    continuarOP.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent e) {
                                            GirosNalCancelacionController controller = new GirosNalCancelacionController();
                                            GirosNalCancelacionController.numpagina.set(0);
                                            GirosNalCancelacionController.indicadorRegistros = "N";
                                            GirosNalCancelacionController.registros.clear();
                                            controller.mostrarGirosNalCancelar(0);
                                        }
                                    });
                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3];

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    icono.setVisible(false);
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                    cancel();
                                }
                            });
                        }
                        return null;
                    }
                };
            }
        };
        return serviceCancelGiro;
    }
}
