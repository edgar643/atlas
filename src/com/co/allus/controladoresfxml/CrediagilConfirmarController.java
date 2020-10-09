/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.hilos.EnvioTipAutFenix;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import com.co.allus.modelo.transfcrediagil.infoTransferenciaCrediagil;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
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
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class CrediagilConfirmarController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label lbl;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Button cancelarOP;
    @FXML
    private Button continuarOP;
    @FXML
    //private TextField textoconfirmar;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient Service<Void> serviceTranfCredi;
    private static String cuenta;
    private static String codTransfCredi;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //assert textoconfirmar != null : "fx:id=\"textoconfirmar\" was not injected: check your FXML file 'CrediagilConfirmar.fxml'.";
        assert lbl != null : "fx:id=\"lbl\" was not injected: check your FXML file 'CrediagilConfirmar.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'CrediagilConfirmar.fxml'.";
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'CrediagilConfirmar.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'CrediagilConfirmar.fxml'.";

        this.location = url;
        this.resources = rb;
        progreso.setVisible(false);
    }

    @FXML
    void cancelarOP(ActionEvent event) {
        final CrediagilController controller = new CrediagilController();
        controller.mostrarTransfCrediagil();
    }

    @FXML
    void continuarOP(ActionEvent event) {

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
        serviceTranfCredi = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String Respuesta = new String();
                        final StringBuilder tramaTransfCredi = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final infoTransferenciaCrediagil dataTrasnfCredi = infoTransferenciaCrediagil.getInfoTransfCrediagil();

                        /*CODIFICACION AUTOMATICA*/
                        final Runnable aFenix = new EnvioTipAutFenix(cliente.getCodemp(), "002");
                        final Thread aFen = new Thread(aFenix);
                        aFen.start();

                        tramaTransfCredi.append("850,032,");
                        tramaTransfCredi.append(cliente.getRefid());
                        tramaTransfCredi.append(",");
                        tramaTransfCredi.append(validarCodigoCuentaMQ(dataTrasnfCredi.getTipo_cuenta()));
                        tramaTransfCredi.append(",");
                        tramaTransfCredi.append(cliente.getId_cliente());
                        tramaTransfCredi.append(",");
                        tramaTransfCredi.append(dataTrasnfCredi.getplazo());
                        tramaTransfCredi.append(",");
                        tramaTransfCredi.append(validarTipoCuenta(dataTrasnfCredi.getTipo_cuenta()));
                        tramaTransfCredi.append(",");
                        tramaTransfCredi.append(dataTrasnfCredi.getCuenta_destino());
                        tramaTransfCredi.append(",");
                        tramaTransfCredi.append(dataTrasnfCredi.getValor_prestamo_ent());
                        tramaTransfCredi.append(",");
                        tramaTransfCredi.append(dataTrasnfCredi.getValor_prestamo_cent());
                        tramaTransfCredi.append(",");
                        tramaTransfCredi.append(cliente.getContraseña());
                        tramaTransfCredi.append(",");
                        tramaTransfCredi.append(cliente.getTokenOauth());
                        tramaTransfCredi.append(",~");



                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ TransferenciaCrediagil = " + "##" + obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTransfCredi.toString());
//                            Respuesta = "850,"
//                                    + "032,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"                                     
//                                    + "021452152"
//                                    + ","
//                                    + "~";
//                             System.out.println(" RESPUESTA TRAMA CREDIAGIL:" + Respuesta);

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ TransferenciaCrediagil = " + Respuesta + "##" + obtenerHoraActual());
                            //Thread.sleep(2000);
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ TRANSFERENCIA CREDIAGIL  = " + "##" + obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTransfCredi.toString());
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

                            dataTrasnfCredi.setNum_prestamo(Respuesta.split(",")[4].trim());
                            infoTransferenciaCrediagil.setInfoTransfCrediagil(dataTrasnfCredi);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final CrediagilFinController controller = new CrediagilFinController();
                                    controller.mostrarTablaCrediagil(infoTransferenciaCrediagil.getInfoTransfCrediagil());


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

        return serviceTranfCredi;
    }

    public void mostrarMenuConfDatos(final infoTransferenciaCrediagil dataTrasnfCredi) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/CrediagilConfirmar.fxml");
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

                        /**
                         * se organiza el valor a pagar en el formato requerido
                         */
                        final String val = dataTrasnfCredi.getValor().replace(",", "");
                        //dataPago.setValorPagarCompleto(val);

                        if (val.contains(".")) {
                            dataTrasnfCredi.setValor_prestamo_ent(val.split("\\.")[0]);
                            dataTrasnfCredi.setValor_prestamo_cent(val.split("\\.")[1]);
                        } else if (val.contains(",")) {
                            dataTrasnfCredi.setValor_prestamo_ent(val.split(",")[0]);
                            dataTrasnfCredi.setValor_prestamo_cent(val.split(",")[1]);
                        } else {
                            dataTrasnfCredi.setValor_prestamo_ent(val);
                            dataTrasnfCredi.setValor_prestamo_cent("00");
                        }


                        infoTransferenciaCrediagil.setInfoTransfCrediagil(dataTrasnfCredi);


                        String valorAmostrar = "$ " + formatonum.format(Double.parseDouble(infoTransferenciaCrediagil.getInfoTransfCrediagil().getValor_prestamo_ent())).replace(".", ",") + "." + infoTransferenciaCrediagil.getInfoTransfCrediagil().getValor_prestamo_cent();

                        //textoconfirmar.setText("¿Está seguro de realizar el desembolso a la cuenta \n" + dataTrasnfCredi.getCuenta_destino() + "\n" + "por valor de " + dataTrasnfCredi.getValor_prestamo() + " a "+ dataTrasnfCredi.getplazo() + " meses?");
                        lbl.setText("¿Está seguro de realizar el desembolso a la cuenta \n" +  
                                modelSaldoTarjeta.enmascararNumero(dataTrasnfCredi.getCuenta_destino(), 6) +
                                "\n" + "por valor de " + valorAmostrar + " a " + dataTrasnfCredi.getplazo() +
                                " meses?");

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

//                        botoncontinuarOp.setDisable(true);

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

    private String validarTipoCuenta(final String Cuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(Cuenta)) {
            retorno.append("7");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(Cuenta)) {
            retorno.append("1");
        }
        //para mas validaciones de tipo cuenta

        return retorno.toString();
    }

    private String validarCodigoCuentaMQ(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0505");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0405");
        }
        //para mas validaciones
        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
