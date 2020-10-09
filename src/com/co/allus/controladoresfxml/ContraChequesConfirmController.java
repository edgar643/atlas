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
import com.co.allus.modelo.contraCheques.dataContraorden;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import com.co.allus.modelo.transfcrediagil.infoTransferenciaCrediagil;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
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
public class ContraChequesConfirmController implements Initializable {

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
    private Label lbl;
    @FXML
    private ProgressBar progreso;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient Service<Void> serviceContraorden;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'ContraChequesConfirm.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'ContraChequesConfirm.fxml'.";
        assert lbl != null : "fx:id=\"lbl\" was not injected: check your FXML file 'ContraChequesConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ContraChequesConfirm.fxml'.";
        progreso.setVisible(false);
    }

    public void mostrarContraChequesConfirm(final dataContraorden datosContraorden) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/ContraChequesConfirm.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        //final TextField textoconfirmar = (TextField) root.lookup("#textoconfirmar");
                        final Label lbl = (Label) root.lookup("#lbl");
                        final Button cancelarOP = (Button) root.lookup("#cancelarOP");
                        final Button continuarOP = (Button) root.lookup("#continuarOP");
                        final String chequefin = datosContraorden.getChequefin();
                        final String chequeini = datosContraorden.getChequeini();


                        if (chequefin.trim().isEmpty()) {
                            lbl.setText("¿Está seguro de contraordenar el cheque " 
                                    + chequeini + "\n" + "asociado a la cuenta corriente " 
                                    + modelSaldoTarjeta.enmascararNumero(datosContraorden.getNumcta(), 6) + " ?");

                        } else {

                            lbl.setText("¿Está seguro de contraordenar los cheques del " + chequeini + "\n" + "al " + chequefin + " asociados a la cuenta corriente " +  modelSaldoTarjeta.enmascararNumero(datosContraorden.getNumcta(), 6) + " ?");

                        }


                        final DropShadow shadow = new DropShadow();
                        continuarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(final MouseEvent event) {
                                        continuarOP.setCursor(Cursor.HAND);
                                        continuarOP.setEffect(shadow);
                                    }
                                });

                        continuarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(final MouseEvent event) {
                                        continuarOP.setCursor(Cursor.DEFAULT);
                                        continuarOP.setEffect(null);

                                    }
                                });

                        cancelarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(final MouseEvent event) {
                                        cancelarOP.setCursor(Cursor.HAND);
                                        cancelarOP.setEffect(shadow);
                                    }
                                });

                        cancelarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(final MouseEvent event) {
                                        cancelarOP.setCursor(Cursor.DEFAULT);
                                        cancelarOP.setEffect(null);

                                    }
                                });




                        try {
                            pane.getChildren().remove(3);
                        } catch (Exception ex) {
                            gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                        }
                        pane.setAlignment(Pos.CENTER_LEFT);
                        pane.add(root, 1, 0);
                        Atlas.vista.show();
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }




    }

    @FXML
    void cancelarOP(final ActionEvent event) {
        final ContraChequesDatosController controller = new ContraChequesDatosController();
        controller.mostrarContraChequesDatos(dataContraorden.getDatosCCheque());
    }

    @FXML
    void continuarOP(final ActionEvent event) {
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuarió confirmó desembolso" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo desembolso" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuarOP.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuarOP.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }
    }

    public Service<Void> continuar_Op() {
        serviceContraorden = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String Respuesta = new String();
                        final StringBuilder tramacontraOrden = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        dataContraorden datosCCheque = dataContraorden.getDatosCCheque();

                        tramacontraOrden.append("850,044,");
                        tramacontraOrden.append(cliente.getRefid());
                        tramacontraOrden.append(",");
                        tramacontraOrden.append(AtlasConstantes.COD_CONTRA_ORDEN_CHEQUES);
                        tramacontraOrden.append(",");
                        tramacontraOrden.append(cliente.getId_cliente());
                        tramacontraOrden.append(",");
                        tramacontraOrden.append(datosCCheque.getNumcta());
                        tramacontraOrden.append(",");
                        tramacontraOrden.append(datosCCheque.getChequeini().trim());
                        tramacontraOrden.append(",");
                        tramacontraOrden.append(datosCCheque.getChequefin().trim());
                        tramacontraOrden.append(",");
                        tramacontraOrden.append(cliente.getContraseña());
                        tramacontraOrden.append(",");
                        tramacontraOrden.append(cliente.getTokenOauth());
                        tramacontraOrden.append(",~");

//                        System.out.println("tramaa contraorden : " + tramacontraOrden.toString());

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CONTRAORDENES  = " + "##" + obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramacontraOrden.toString());
//                            Respuesta = "850,"
//                                    + "044,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA"
//                                    + ","
//                                    + "~";


                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ Contraorden = " + Respuesta + "##" + obtenerHoraActual());
                            //Thread.sleep(2000);
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ CONTRAORDENES = " + "##" + obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramacontraOrden.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        cancel();
                                        // continuar_Op.cancel();
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

                                    final ContraChequesFinController controller = new ContraChequesFinController();
                                    controller.mostrarContraordenFin(dataContraorden.getDatosCCheque());
                                }
                            });


                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    cancel();
                                    continuarOP.setDisable(false);
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

        return serviceContraorden;
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
