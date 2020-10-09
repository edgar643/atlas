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
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import com.co.allus.modelo.transestadocuentas.dataCuentasInscritas;
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
public class EliminacionCtasAchConfController implements Initializable {

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
    private transient Service<Void> serviceElimiCtasACH;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'eliminacionCtasAchConf.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'eliminacionCtasAchConf.fxml'.";
        assert lbl != null : "fx:id=\"lbl\" was not injected: check your FXML file 'eliminacionCtasAchConf.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'eliminacionCtasAchConf.fxml'.";

        progreso.setVisible(false);
    }

    @FXML
    void cancelarOP(final ActionEvent event) {
        final EliminacionCtasACHController controller = new EliminacionCtasACHController();
        controller.mostrarELiminacionCuentasACH();
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

    public void mostrarMenuConfDatos(final dataCuentasInscritas dataCtas) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/eliminacionCtasAchConf.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Label lbl = (Label) root.lookup("#lbl");
                        final Button cancelarOP = (Button) root.lookup("#cancelarOP");
                        final Button continuarOP = (Button) root.lookup("#continuarOP");

                        lbl.setText("¿Está seguro que desea eliminar la cuenta " + modelSaldoTarjeta.enmascararNumero(dataCtas.getNumcta(), 6)  + " de \n" + dataCtas.getBanco() + " ,de sus cuentas inscritas para realizar \n transferencias ?");

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

    public Service<Void> continuar_Op() {
        serviceElimiCtasACH = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String Respuesta = new String();
                        final StringBuilder tramaElimCtasACH = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final dataCuentasInscritas datactas = dataCuentasInscritas.getInfoCtas();;

                        tramaElimCtasACH.append("850,047,");
                        tramaElimCtasACH.append(cliente.getRefid());
                        tramaElimCtasACH.append(",");
                        tramaElimCtasACH.append("9041");
                        tramaElimCtasACH.append(",");
                        tramaElimCtasACH.append(cliente.getId_cliente());
                        tramaElimCtasACH.append(",");
                        tramaElimCtasACH.append(datactas.getCodigoBanco());
                        tramaElimCtasACH.append(",");
                        tramaElimCtasACH.append("0");
                        tramaElimCtasACH.append(",");
                        tramaElimCtasACH.append("0");
                        tramaElimCtasACH.append(",");
                        tramaElimCtasACH.append(datactas.getNumcta());
                        tramaElimCtasACH.append(",");
                        tramaElimCtasACH.append(validarTipoCuenta(datactas.getTipoCta()));
                        tramaElimCtasACH.append(",");
                        tramaElimCtasACH.append(cliente.getContraseña());
                        tramaElimCtasACH.append(",");
                        tramaElimCtasACH.append(cliente.getTokenOauth());
                        tramaElimCtasACH.append(",~");

//                        System.out.println("tramaa eliminacion ctas ach : " + tramaElimCtasACH.toString());

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ eliminar cta ach = " + "##" + obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaElimCtasACH.toString());
//                            Respuesta = "850,"
//                                    + "047,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA"
//                                    + ","
//                                    + "~";
//                             System.out.println(" RESPUESTA TRAMA CREDIAGIL:" + Respuesta);
    
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ  eliminar cta ach = " + Respuesta + "##" + obtenerHoraActual());
                            //Thread.sleep(2000);
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ ELIMINAR CTA ACH = " + "##" + obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaElimCtasACH.toString());
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
                                    final dataCuentasInscritas infoCtas = dataCuentasInscritas.getInfoCtas();


                                    final EliminacionCtasAchFinController controller = new EliminacionCtasAchFinController();
                                    controller.mostrarCtasACHfin(infoCtas);

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

        return serviceElimiCtasACH;
    }

    private String validarTipoCuenta(final String Cuenta) {
        final StringBuilder retorno = new StringBuilder();

        if ("ahorros".equalsIgnoreCase(Cuenta)) {
            retorno.append("7");

        } else if ("corriente".equalsIgnoreCase(Cuenta)) {
            retorno.append("1");
        }
        //para mas validaciones de tipo cuenta

        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
