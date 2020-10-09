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
import com.co.allus.modelo.SaldoAFC.infoTablaSaldoAFC;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class SaldoAFCController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private HBox barraprogreso;
    @FXML
    private Button cancelarop;
    @FXML
    private Button continuarop;
    @FXML
    private RestrictiveTextField numctaafc;
    @FXML
    private ProgressBar progreso;
    private Service<Void> serviceSaldoAFC;
    private GestorDocumental docs = new GestorDocumental();
    public static AtomicBoolean cancelOP = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert barraprogreso != null : "fx:id=\"barraprogreso\" was not injected: check your FXML file 'SaldoAFC.fxml'.";
        assert cancelarop != null : "fx:id=\"cancelarop\" was not injected: check your FXML file 'SaldoAFC.fxml'.";
        assert continuarop != null : "fx:id=\"continuarop\" was not injected: check your FXML file 'SaldoAFC.fxml'.";
        assert numctaafc != null : "fx:id=\"numctaafc\" was not injected: check your FXML file 'SaldoAFC.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'SaldoAFC.fxml'.";

        cancelOP.set(false);
        progreso.setVisible(false);
        continuarop.setDisable(true);

    }

    @FXML
    void cancelarop(ActionEvent event) {
        try {
            continuar_Op().cancel();
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }

        final Parent frameGnral = Atlas.vista.getScene().getRoot();
        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
        final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
        arbol_saldos.setDisable(false);
        arbol_saldos.getSelectionModel().clearSelection();

        try {
            pane.getChildren().remove(3);

        } catch (Exception e) {
            docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }

    }

    @FXML
    void continuarop(ActionEvent event) {
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Saldo Credito AFC" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Saldo Credito AFC" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuarop.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuarop.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }
    }

    public Service<Void> continuar_Op() {
        serviceSaldoAFC = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaSaldoH = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        final Date fecha = new Date();
                        tramaSaldoH.append("850,058,");
                        tramaSaldoH.append(cliente.getRefid());
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(AtlasConstantes.COD_SALDO_AFC);
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(cliente.getContraseña().trim().isEmpty() ? "T" : "S");
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(cliente.getId_cliente());
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(numctaafc.getText());
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(cliente.getContraseña());
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(cliente.getTokenOauth());
                        tramaSaldoH.append(",~");

//                        System.out.println("TRAMA SALDO AFC :" + tramaSaldoH);



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ saldo afc= " + "##" + docs.obtenerHoraActual());

                            //850,058,000,TRANSACCION EXITOSA,valorahoBenef,RendimientoBeneficio,valorBeneficio,valorahosinbenef,valorcongelado,valorbeneficioganado,valorbeneficioperdido,valorSaldoCanje,saldodispVivienda,beneficioGanVivienda,GMretiroVivienda,BeneficioTributarioGMF,valorahorDec2344,valorbenefDecrt2344,gmfvivienda2344,beneficiotributario2344,saldoDispDiferentVivi,BeneficioDiferenteVivienda,GmfperdidoDifVivi,valorAhorro2344sinBenef,valorbenefPerdio2344,retirosEntramite,RazonBloqueo,~

//                            Respuesta = "850,058,000,TRANSACCION EXITOSA,"
//                                    + "-6400000000,"
//                                    + "01200000000,"
//                                    + "01500000000,"
//                                    + "01500000000,"
//                                    + "0800000000,"
//                                    + "0100000000,"
//                                    + "0200000000,"
//                                    + "0100000000,"
//                                    + "01200000000,"
//                                    + "1300000000,"
//                                    + "1700000000,"
//                                    + "90000000,"
//                                    + "0400000000,"
//                                    + "-200000000,"
//                                    + "01000000,"
//                                    + "0100000000,"
//                                    + "04500000000,"
//                                    + "01800000000,"
//                                    + "06000000,"
//                                    + "1500000000,"
//                                    + "55000000,"
//                                    + "63000000,"
//                                    + "Embargo|Investigacion|Fallecido|Otros,~";

                            //                                    Respuesta="850,058,000,TRANSACCION EXITOSA,"
//                                    + "valorahoBenef,"
//                                    + "RendimientoBeneficio,"
                            //+"valorbeneficio"
//                                    + "valorahosinbenef,"
//                                    + "valorcongelado2344,"
//                                    + "valorbeneficioganado,"
//                                    + "valorbeneficioperdido,"
//                                    + "valorSaldoCanje,"
//                                    + "saldodispVivienda,"
//                                    + "beneficioGanVivienda,"
//                                    + "GMretiroVivienda,"
//                                    + "BeneficioTributarioGMF,"
//                                    + "valorahorDec2344,"
//                                    + "valorbenefDecrt2344,"
//                                    + "gmfvivienda2344,"
//                                    + "beneficiotributarioperdido2344,"
//                                    + "saldoDispDiferentVivi,"
//                                    + "BeneficioDiferenteVivienda,"
//                                    + "GmfperdidoDifVivi,"
//                                    + "valorAhorro2344sinBenef,"
//                                    + "valorbenefPerdio2344,"
//                                    + "retirosEntramite,"
//                                    + "RazonBloqueo,~";




                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU3, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU3, tramaSaldoH.toString());
//                            System.out.println(" RESPUESTA TRAMA SALDO AFC :" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ saldo afc= " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU3, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU3, tramaSaldoH.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDOS);
                                        continuarop.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            infoTablaSaldoAFC datosAFC = infoTablaSaldoAFC.getInfoAFC();




                            datosAFC.setAhorroDisBenef(Respuesta.split(",")[4]);
                            datosAFC.setRendimientos(Respuesta.split(",")[5]);
                            datosAFC.setValorBenef(Respuesta.split(",")[6]);
                            datosAFC.setAhorrosinBenef(Respuesta.split(",")[7]);
                            datosAFC.setValorcongelado2344(Respuesta.split(",")[8]);
                            datosAFC.setBeneficioGanado(Respuesta.split(",")[9]);
                            datosAFC.setBeneficioPerdido(Respuesta.split(",")[10]);
                            datosAFC.setSaldocanje(Respuesta.split(",")[11]);
                            datosAFC.setSaldoDispVivienda(Respuesta.split(",")[12]);
                            datosAFC.setBenefGanarVivienda(Respuesta.split(",")[13]);
                            datosAFC.setGmfRetiroVivienda(Respuesta.split(",")[14]);
                            datosAFC.setBenefPerdidogmf(Respuesta.split(",")[15]);
                            datosAFC.setAhorro2344(Respuesta.split(",")[16]);
                            datosAFC.setBenefCongelar2344(Respuesta.split(",")[17]);
                            datosAFC.setGmfVivienda2344(Respuesta.split(",")[18]);
                            datosAFC.setBenefPerdido2344gmf(Respuesta.split(",")[19]);
                            datosAFC.setSaldoDispNoVivienda(Respuesta.split(",")[20]);
                            datosAFC.setBenefPerderNoVivienda(Respuesta.split(",")[21]);
                            datosAFC.setGmfRetiroNoVivnda(Respuesta.split(",")[22]);
                            datosAFC.setAhorro2344NoBenef(Respuesta.split(",")[23]);
                            datosAFC.setBenefPerder2344(Respuesta.split(",")[24]);
                            datosAFC.setValorRetirosTram(Respuesta.split(",")[25]);
                            datosAFC.setRazonBloqueo(Respuesta.split(",")[26]);


                            infoTablaSaldoAFC.setInfoAFC(datosAFC);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    SaldoAFCfinController controller = new SaldoAFCfinController();
                                    /// controller.mostrarSaldoAFCfin(infoTablaSaldoAFC.getInfoAFC(), numctaafc.getText());

                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            if (!cancelOP.get()) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_HIP);
                                        cancel();
                                        continuarop.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                });
                            }

                        }

                        return null;
                    }
                };
            }
        };

        return serviceSaldoAFC;

    }

    @FXML
    void numctakeypress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                continuarop.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(numctaafc, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                numctaafc.clear();
                numctaafc.fireEvent(keyEvent);
                continuarop.setDisable(true);
            }


        }
    }

    @FXML
    void numctakeytyped(KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button continuarOP = (Button) root.lookup("#continuarop");
                    final RestrictiveTextField valor = (RestrictiveTextField) root.lookup("#numctaafc");

                    if ((valor.getText().isEmpty())) {
                        continuarOP.setDisable(true);
                    } else {
                        continuarOP.setDisable(false);
                    }

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });

    }

    public void MostrarSaldoAFCcontroller() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/SaldoAFC.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelar = (Button) root.lookup("#cancelarop");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuarop");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);


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

                    final TreeView<String> arbol_servad = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servad != null) {
                        arbol_servad.setDisable(true);
                    }

                    final TreeView<String> arbol_infotdc = (TreeView<String>) pane.lookup("#arbol_infotdc");
                    if (arbol_infotdc != null) {
                        arbol_infotdc.setDisable(true);
                    }

                    final TreeView<String> arbol_consultas = (TreeView<String>) pane.lookup("#arbol_consultas");
                    if (arbol_consultas != null) {
                        arbol_consultas.setDisable(true);
                    }

                    final TreeView<String> arbol_movmientos = (TreeView<String>) pane.lookup("#arbol_movimientos");
                    if (arbol_movmientos != null) {
                        arbol_movmientos.setDisable(true);
                    }

                    final TreeView<String> arbol_contrabloqueos = (TreeView<String>) pane.lookup("#arbol_contrabloqueos");
                    if (arbol_contrabloqueos != null) {
                        arbol_contrabloqueos.setDisable(true);
                    }

                    final TreeView<String> arbol_infoseguridad = (TreeView<String>) pane.lookup("#arbol_infoseguridad");
                    if (arbol_infoseguridad != null) {
                        arbol_infoseguridad.setDisable(true);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });



    }
}
