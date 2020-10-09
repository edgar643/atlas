/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ModalSaldosController implements Initializable {

    @FXML
    private transient ResourceBundle resources;
    @FXML
    private transient URL location;
    @FXML
    private transient Button cancelar;
    @FXML
    private transient Button consulta_costo;
    @FXML
    private transient Button continuar;
    @FXML
    private transient Label costo_operacion;
    @FXML
    private transient Label informacion_costo;
    @FXML
    private transient Label informacion_cuenta;
    @FXML
    private transient ImageView icono;
    @FXML
    private transient HBox botones;
    @FXML
    private ProgressBar progreso;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ModalSaldos.fxml'.";
        assert consulta_costo != null : "fx:id=\"consulta_costo\" was not injected: check your FXML file 'ModalSaldos.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'ModalSaldos.fxml'.";
        assert costo_operacion != null : "fx:id=\"costo_operacion\" was not injected: check your FXML file 'ModalSaldos.fxml'.";
        assert informacion_costo != null : "fx:id=\"informacion_costo\" was not injected: check your FXML file 'ModalSaldos.fxml'.";
        assert informacion_cuenta != null : "fx:id=\"informacion_cuenta\" was not injected: check your FXML file 'ModalSaldos.fxml'.";
        assert icono != null : "fx:id=\"icono\" was not injected: check your FXML file 'ModalSaldos.fxml'.";
        assert botones != null : "fx:id=\"botones\" was not injected: check your FXML file 'ModalSaldos.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ModalSaldos.fxml'.";
        this.location = location;
        this.resources = resources;
        progreso.setVisible(false);
    }

    @FXML
    void cancel(final ActionEvent event) {

        final Parent frameGnral = Atlas.vista.getScene().getRoot();
        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
        final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
        arbol_saldos.setDisable(false);
        arbol_saldos.getSelectionModel().clearSelection();
        ConsultaCosto.cancel();
        ConsultarSaldo.cancel();
        try {
            pane.getChildren().remove(3);

        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }

    }

    @FXML
    void consulta_costo(final ActionEvent event) {

        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario presionó ConsultarCosto" + "##" + obtenerHoraActual());
        consulta_costo.setDisable(true);
        if (ConsultarSaldo.isRunning()) {
            ConsultarSaldo.cancel();
        }
        progreso.setVisible(true);
        progreso.progressProperty().unbind();
        progreso.progressProperty().bind(ConsultaCosto.progressProperty());


        ConsultaCosto.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Termino ConsultarSaldo" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        ConsultaCosto.setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ConsultarSaldo" + "##" + obtenerHoraActual());
                consulta_costo.setDisable(false);
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });
        new Thread(ConsultaCosto).start();

    }

    /**
     *
     * @param Cuenta
     * @return
     */
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

    /**
     *
     * @param tipo
     * @return
     */
    private String validarCuentaporTipo(final String tipo) {
        final StringBuilder retorno = new StringBuilder();
        switch (Integer.parseInt(tipo)) {
            case 1:
                retorno.append(CodigoProductos.CUENTA_CORRIENTE);
                break;
            case 7:
                retorno.append(CodigoProductos.CUENTA_AHORROS);
                break;
            default:
                retorno.append(tipo);
                break;

        }

        return retorno.toString();
    }

    /**
     *
     * @param TipoCuenta
     * @return
     */
    private String validarTipoTransaccion(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

//        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
//            retorno.append("XBALAHOGDE");
//
//        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
//            retorno.append("XBALCTEGDE");
//        }

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0560");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0460");
        }

        //para mas validaciones de tipo cuenta

        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }

    /**
     *
     * @param TipoCuenta
     * @return
     */
    private String validarCodigoCuentaMQ(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0560");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0460");
        }
        //para mas validaciones
        return retorno.toString();
    }

    @FXML
    void continuarOP(final ActionEvent event) {

        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario presionó ConsultarSaldo" + "##" + obtenerHoraActual());
        if (ConsultaCosto.isRunning()) {
            ConsultaCosto.cancel();
        }

        continuar.setDisable(true);
        progreso.setVisible(true);
        progreso.progressProperty().unbind();
        progreso.progressProperty().bind(ConsultarSaldo.progressProperty());


        ConsultarSaldo.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Termino  consultarSaldo" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        ConsultarSaldo.setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                continuar.setDisable(false);
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Cancelo  ConsultarSaldo" + "##" + obtenerHoraActual());

            }
        });
        new Thread(ConsultarSaldo).start();


    }
    Task ConsultaCosto = new Task<Void>() {
        @Override
        public Void call() {

            String Respuesta = new String();

            final StringBuilder tramaCosto = new StringBuilder();
            final ConectSS servicioSS = new ConectSS();
            final Cliente cliente = Cliente.getCliente();
            final String infoCuenta = informacion_cuenta.getText();
            final String tipoCuenta = infoCuenta.split(":")[0].trim();
            final String numeroCuenta = infoCuenta.split(":")[1].trim();

            //850,023,CONNID,DOCID,ORIGEN,CODTRANSACCION,TIPOCTA,NUMCTA,CODFONDO,CLAVEHW,~
            tramaCosto.append("850,023,");
            tramaCosto.append(cliente.getRefid());
            tramaCosto.append(",");
            tramaCosto.append(cliente.getId_cliente());
            tramaCosto.append(",");
            tramaCosto.append("GDE,");
            tramaCosto.append(validarTipoTransaccion(tipoCuenta));
            tramaCosto.append(",");
            tramaCosto.append(validarTipoCuenta(tipoCuenta));
            tramaCosto.append(",");
            tramaCosto.append(numeroCuenta);
            tramaCosto.append(",");
            tramaCosto.append("");//codigo fondo 
            tramaCosto.append(",");
            tramaCosto.append(cliente.getContraseña());
            tramaCosto.append(",");
            tramaCosto.append(cliente.getTokenOauth());
            tramaCosto.append(",~");


//            tramaCosto.append("930,000,");
//            tramaCosto.append(validarTipoCuenta(tipoCuenta));
//            tramaCosto.append(",");
//            tramaCosto.append(cliente.getId_cliente());
//            tramaCosto.append(",");
//            tramaCosto.append("GDE,");
//            tramaCosto.append(validarTipoTransaccion(tipoCuenta));
//            tramaCosto.append(",");
//            tramaCosto.append(numeroCuenta);
//            tramaCosto.append(",");
//            tramaCosto.append(validarTipoCuenta(tipoCuenta));
//            tramaCosto.append(",~");

            try {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo = " + "##" + obtenerHoraActual());
                // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_COSTO, AtlasConstantes.PUERTO_COM_CONSULTA_COSTO, tramaCosto.toString());
                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  Consulta Costo = " + Respuesta + "##" + obtenerHoraActual());
            } catch (Exception ex) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal Consulta Costo = " + ex.toString() + "##" + obtenerHoraActual());
                //envio a contingencia
                try {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo ctg = " + "##" + obtenerHoraActual());
                    // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_COSTO_CTG, AtlasConstantes.PUERTO_COM_CONSULTA_COSTO, tramaCosto.toString());
                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  Consulta costo ctg = " + Respuesta + "##" + obtenerHoraActual());
                } catch (Exception ex1) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctg Consulta Costo = " + ex1.toString() + "##" + obtenerHoraActual());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                            icono.setVisible(false);
                            consulta_costo.setVisible(false);
                            continuar.setLayoutX(23);
                            informacion_costo.setText(" ");
                            progreso.progressProperty().unbind();
                            progreso.setProgress(0);
                            progreso.setVisible(false);
                            //return;
                        }
                    });

                }

            }

            if ("000".equals(Respuesta.split(",")[2])) {
                final String costo = Respuesta.split(",")[4];

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String aux = costo;
                        try {
                            aux = formatonum.format(Double.parseDouble(aux)).replace(".", ",") + "." + aux.substring(aux.length() - 2, aux.length());
                        } catch (Exception e) {
                            // e.printStackTrace();
                            aux = costo;
                        }
                        icono.setVisible(false);
                        consulta_costo.setVisible(false);
                        continuar.setLayoutX(23);
                        informacion_costo.setText("EL COSTO PARA ESTA TRANSACCION ES DE $" + aux + "\nMAS EL 19% DE IVA VALIDAR TABLA DE TASAS Y TARIFAS");
                        // return;
                    }
                });

            } else {
                final String coderror = Respuesta.split(",")[2];
                final String mensaje = Respuesta.split(",")[3];

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new ModalMensajes(mensaje, coderror, ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);
                        icono.setVisible(false);
                        consulta_costo.setVisible(false);
                        continuar.setLayoutX(23);
                        informacion_costo.setText("");
                        //return;
                    }
                });


            }
            //           else if ("001".equals(Respuesta.split(",")[1])) {
//                final String coderror = Respuesta.split(",")[2];
//                final String mensaje = Respuesta.split(",")[3];
//
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        new ModalMensajes(mensaje, coderror, ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);
//                        icono.setVisible(false);
//                        consulta_costo.setVisible(false);
//                        continuar.setLayoutX(23);
//                        informacion_costo.setText("");
//                        //return;
//                    }
//                });
//
//
//            } else if ("998".equals(Respuesta.split(",")[1])) {
//
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        new ModalMensajes("Error Consultando el Costo", "Error WS", ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.MODAL_SALDOS);
//                        icono.setVisible(false);
//                        consulta_costo.setVisible(false);
//                        continuar.setLayoutX(23);
//                        informacion_costo.setText("");
//
//                    }
//                });
//
//                // return;
//
//            } else if ("997".equals(Respuesta.split(",")[1])) {
//
//                final String respuesta = Respuesta.split(",")[2];
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        new ModalMensajes(respuesta, "Time out", ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);
//                        icono.setVisible(false);
//                        consulta_costo.setVisible(false);
//                        continuar.setLayoutX(23);
//                        informacion_costo.setText("");
//                    }
//                });
//
//
//                // return;
//            }


            return null;
        }
    };
    Task ConsultarSaldo = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            String Respuesta = new String();
            final SaldosController controller = new SaldosController();
            final String infoCuenta = informacion_cuenta.getText();
            final String tipoCuenta = infoCuenta.split(":")[0].trim();
            final String numeroCuenta = infoCuenta.split(":")[1].trim();
            final Cliente cliente = Cliente.getCliente();
            final StringBuilder tramaSaldo = new StringBuilder();

            final Date fecha = new Date();
            tramaSaldo.append("900,001,");
            tramaSaldo.append(cliente.getRefid());
            tramaSaldo.append(",");
            tramaSaldo.append(validarCodigoCuentaMQ(tipoCuenta));
            tramaSaldo.append(",");
            tramaSaldo.append(cliente.getId_cliente());
            tramaSaldo.append(",");
            tramaSaldo.append(formatoFecha.format(fecha));
            tramaSaldo.append(",");
            tramaSaldo.append(formatoHora.format(fecha));
            tramaSaldo.append(",");
            tramaSaldo.append(numeroCuenta);
            tramaSaldo.append(",");
            tramaSaldo.append(validarTipoCuenta(tipoCuenta));
            tramaSaldo.append(",");
            tramaSaldo.append(cliente.getContraseña());
            tramaSaldo.append(",");
            tramaSaldo.append(cliente.getTokenOauth());
            tramaSaldo.append(",~");


            // System.out.println("trama para MQ :" + tramaSaldo.toString());

            final ConectSS servicioSS = new ConectSS();

            try {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ SALDOS MQ = " + "##" + obtenerHoraActual());
                // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, tramaSaldo.toString());
                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaSaldo.toString());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + obtenerHoraActual());
            } catch (Exception ex) {

                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                //envio a contingencia
                try {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ SALDOS MQ = " + "##" + obtenerHoraActual());
                    // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, tramaSaldo.toString());
                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaSaldo.toString());
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + obtenerHoraActual());

                } catch (Exception ex1) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDOS);
                            continuar.setDisable(false);
                            ConsultarSaldo.cancel();
                            progreso.progressProperty().unbind();
                            progreso.setProgress(0);
                            progreso.setVisible(false);

                        }
                    });

                }

            }

            if ("000".equals(Respuesta.split(",")[2])) {

                // COMPROBANTE PARA LOS LOGS
                final String Comprobante = Respuesta.split(",")[3];
                final String numerocta = Respuesta.split(",")[4];
                final String tipocuenta = validarCuentaporTipo(Respuesta.split(",")[5]);
                final String saldoDisponilble = Respuesta.split(",")[6];
                final String SDcentavos = Respuesta.split(",")[7];
                final String saldoCanje = Respuesta.split(",")[8];
                final String SCcentavos = Respuesta.split(",")[9];
                final String saldoTotal = Respuesta.split(",")[10];
                final String STcentavos = Respuesta.split(",")[11];
                final String diasSobregiro = Respuesta.split(",")[12];

                // System.out.println(Respuesta);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        controller.handleConsultaSaldo(numerocta, tipocuenta, saldoDisponilble, SDcentavos, saldoCanje, SCcentavos, saldoTotal, STcentavos, diasSobregiro, "BANCOLOMBIA");
                    }
                });

            } else {

                final String coderror = Respuesta.split(",")[2];
                final String mensaje = Respuesta.split(",")[3].trim();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.MODAL_SALDOS);
                        continuar.setDisable(false);
                        //return;
                    }
                });


            }

            return null;
        }
    };
}
