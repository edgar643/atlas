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
import com.co.allus.modelo.consultadepositos.InfoTablaConsDepo;
import com.co.allus.modelo.consultamovimientos.infoTablaCtaDepMov;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaDepositosController implements Initializable {

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
    private Button continuar_op;
    @FXML
    private TableColumn<infoTablaCtaDepMov, String> numcta;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TableView<infoTablaCtaDepMov> tablaDatos;
    @FXML
    private Label titulo;
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient Service<Void> serviceConsDep;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO.
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ConsultaMovimientos.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'ConsultaMovimientos.fxml'.";
        assert numcta != null : "fx:id=\"numcta\" was not injected: check your FXML file 'ConsultaMovimientos.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaMovimientos.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaMovimientos.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ConsultaMovimientos.fxml'.";
        assert titulo != null : "fx:id=\"titulo\" was not injected: check your FXML file 'ConsultaMovimientos.fxml'.";


        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        numcta.setCellValueFactory(new PropertyValueFactory<infoTablaCtaDepMov, String>("cuenta"));
        progreso.setVisible(false);
    }

    public void mostrarCosultaDepot() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaDepositos.fxml");
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
                    final TableView<infoTablaCtaDepMov> tablaData = (TableView<infoTablaCtaDepMov>) root.lookup("#tablaDatos");
                    /// codigo para inyectar los datos                   
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

                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servadd != null) {
                        arbol_servadd.setDisable(true);
                    }

                    final TreeView<String> arbol_consMov = (TreeView<String>) pane.lookup("#arbol_movimientos");
                    if (arbol_consMov != null) {
                        arbol_consMov.setDisable(true);
                    }

                    final TreeView<String> arbol_cons = (TreeView<String>) pane.lookup("#arbol_consultas");
                    if (arbol_cons != null) {
                        arbol_cons.setDisable(true);
                    }



                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);


                    final ObservableList<infoTablaCtaDepMov> cuentas = FXCollections.observableArrayList();

                    /* para proyecto CASA
                     if (datosCliente.getProductos().containsKey(CodigoProductos.CUENTA_AHORROS)) {
                     cuentas.add(new infoTablaCtaDepMov(AtlasConstantes.Cuenta_AHORROS));
                     }
                     if (datosCliente.getProductos().containsKey(CodigoProductos.CUENTA_CORRIENTE)) {
                     cuentas.add(new infoTablaCtaDepMov(AtlasConstantes.Cuenta_CORRIENTE));
                     } */
                    cuentas.add(new infoTablaCtaDepMov(AtlasConstantes.Cuenta_AFC));

                    tablaData.setItems(cuentas);


                    Atlas.vista.show();


                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }


            }
        });




    }

    @FXML
    void cancelarOP(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    Logger.getLogger(ConsultaDepositosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void continuarOP(final ActionEvent event) {
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Transferencias ctas propias" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Transferencias ctas propias" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }
    }

    public Service<Void> continuar_Op() {
        serviceConsDep = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String RespuestaConsDep = new String();
                        final StringBuilder consultaDepot = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        consultaDepot.append("850,049,");
                        consultaDepot.append(cliente.getRefid());
                        consultaDepot.append(",");
                        consultaDepot.append(AtlasConstantes.COD_CONS_DEPOT);
                        consultaDepot.append(",");
                        consultaDepot.append(cliente.getId_cliente());
                        consultaDepot.append(",");
                        consultaDepot.append(cliente.getContraseña().isEmpty() ? "T" : "C");
                        consultaDepot.append(",");
                        consultaDepot.append(cliente.getContraseña());
                         consultaDepot.append(",");
                        consultaDepot.append(cliente.getTokenOauth());
                        consultaDepot.append(",~");


                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CosultaDepot = " + "##" + gestorDoc.obtenerHoraActual());
                            RespuestaConsDep = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, consultaDepot.toString());

                            // RespuestaConsDep="850,049,000,,S%99101000209     %D% % % % % % % % % % %AFC  % % % % % %,~";
                            // RespuestaConsDep="850,049,000,TRANSACCION EXITOSA,S%99101000331     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000330     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000329     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000328     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000327     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000326     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000325     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000324     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000323     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000322     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000321     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000320     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000319     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000318     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000317     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000316     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000315     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000314     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000313     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000312     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000311     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000310     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000309     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000308     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000307     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000306     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000305     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000304     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000303     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000302     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000301     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000300     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000299     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000298     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000297     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000296     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000295     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000294     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000293     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000292     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000291     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000290     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000289     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000288     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000287     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000286     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000285     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000284     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000283     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000282     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000281     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000280     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000279     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000278     %A% % % % % % % % % % %AFC  % % % % % %;S%99101000277     %A% % % % % % % % % % %AFC  % % % % % %,~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ ConsultaDepot = " + StringUtilities.tramaSubString(RespuestaConsDep, 0, 2, ",") + "##" + gestorDoc.obtenerHoraActual());
                            //Thread.sleep(2000);
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ CONSULTA DEPOSITOS = " + "##" + gestorDoc.obtenerHoraActual());
                                RespuestaConsDep = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, consultaDepot.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(RespuestaConsDep, 0, 2, ",") + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
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

                        if ("000".equals(RespuestaConsDep.split(",")[2])) {
                            //tipoTran%fecha%grupo%cheque%valor%descripcion%oficina
                            String data = RespuestaConsDep.split(",")[4];
                            final List<InfoTablaConsDepo> lista = new ArrayList<InfoTablaConsDepo>();
                            String[] depositos = data.split(";");
                            for (int i = 0; i < depositos.length; i++) {
                                final String[] datadep = depositos[i].split("%");

                                if ("AFC".equalsIgnoreCase(datadep[13].trim())) {
                                    final InfoTablaConsDepo ObjDep = new InfoTablaConsDepo(
                                            datadep[1].trim(),
                                            datadep[2].trim());
                                    lista.add(ObjDep);
                                }

                            }



                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    ConsultaDepositosFinController controller = new ConsultaDepositosFinController();
                                    controller.mostrarDepositosFin(lista, tablaDatos.getSelectionModel().getSelectedItem().getCuenta());


                                }
                            });


                        } else {
                            final String coderror = RespuestaConsDep.split(",")[2];
                            final String mensaje = RespuestaConsDep.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    cancel();
                                    //continuar_Op.cancel();
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

        return serviceConsDep;
    }
}
