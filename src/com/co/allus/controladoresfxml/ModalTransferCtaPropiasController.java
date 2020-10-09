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
import com.co.allus.modelo.pagosaterceros.infoPago;
import com.co.allus.modelo.transfctaprop.infoTranferenciaCtaProp;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ModalTransferCtaPropiasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private transient ResourceBundle resources;
    @FXML
    private transient URL location;
    @FXML
    private transient Button cancelar;
    @FXML
    private transient Button consultar_costo;
    @FXML
    private transient Button continuar;
    @FXML
    private transient Label mensaje_tranf;
    @FXML
    private transient ProgressBar progreso;
    @FXML
    private transient ImageView icono;
    private transient Service<Void> serviceCcosto;
    private transient Service<Void> servicetrasnfctaprop;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechatrasnf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ModalTransferCtaPropias.fxml'.";
        assert consultar_costo != null : "fx:id=\"consultar_costo\" was not injected: check your FXML file 'ModalTransferCtaPropias.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'ModalTransferCtaPropias.fxml'.";
        assert icono != null : "fx:id=\"icono\" was not injected: check your FXML file 'ModalTransferCtaPropias.fxml'.";
        assert mensaje_tranf != null : "fx:id=\"mensaje_tranf\" was not injected: check your FXML file 'ModalTransferCtaPropias.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ModalTransferCtaPropias.fxml'.";

        this.resources = resources;
        this.location = location;

        progreso.setVisible(false);
    }

    public void mostrarModalTranfctaprop(final infoTranferenciaCtaProp dataTransf) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/ModalTransferCtaPropias.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Label infoCosto = (Label) root.lookup("#mensaje_tranf");
                    infoCosto.setText("RECUERDE QUE ESTA OPERACION PUEDE TENER COSTO");
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();;

                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }
            }
        });
    }

    @FXML
    void cancel(final ActionEvent event) {
        try {
            serviceCcosto.cancel();
        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        try {
            servicetrasnfctaprop.cancel();
        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        Atlas.getVista().mostrarMenuTransfctasprop("Transferencias cuentas Bancolombia propias");
    }

    @FXML
    void consulta_costo(final ActionEvent event) {

        consultar_Costo().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario presionó ConsultarCosto" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        consultar_Costo().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ConsultarCosto" + "##" + obtenerHoraActual());
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

    @FXML
    void continuarOP(final ActionEvent event) {
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Transferencias ctas propias" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Transferencias ctas propias" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }

    }

    public Service<Void> continuar_Op() {
        servicetrasnfctaprop = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String RespuestaTrasnfin = new String();
                        final StringBuilder tramatransfctaprop = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final infoTranferenciaCtaProp infotransf = infoTranferenciaCtaProp.getInfoTranfCtaProp();
                        final Date fecha = new Date();
                        tramatransfctaprop.append("900,008,");
                        tramatransfctaprop.append(cliente.getRefid());
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(validarCodigoCuentaMQ(infotransf.getTipo_cta_origen()));
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(cliente.getId_cliente());
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(formatoFecha.format(fecha));
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(formatoHora.format(fecha));
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(validarTipoCuenta(infotransf.getTipo_cta_origen()));
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(infotransf.getCta_origen());
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(validarTipoCuenta(infotransf.getTipo_cta_destino()));
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(infotransf.getCta_destino());
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(StringUtilities.eliminarCerosLeft(infotransf.getValor_transferirEnt()));
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(infotransf.getValor_transferirCent());
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(cliente.getContraseña());
                        tramatransfctaprop.append(",");
                        tramatransfctaprop.append(cliente.getTokenOauth());
                        tramatransfctaprop.append(",~");


                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Transferencias ctas propias = " + "##" + obtenerHoraActual());
                            RespuestaTrasnfin = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, tramatransfctaprop.toString());
                            // RespuestaTrasnfin = "900,008,000,1245456,PAGO EXITOSO,~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ Transferencias ctas propias = " + StringUtilities.tramaSubString(RespuestaTrasnfin, 0, 3, ",") + "##" + obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ TRANSFERENCIAS CTAS PROPIAS = " + "##" + obtenerHoraActual());
                                RespuestaTrasnfin = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, tramatransfctaprop.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(RespuestaTrasnfin, 0, 3, ",") + "##" + obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TRANSF_CTA_PROP);
                                        cancel();
                                        // continuar_Op.cancel();
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }
                        }

                        if ("000".equals(RespuestaTrasnfin.split(",")[2])) {
                            final Date fechapago = new Date();

                            infotransf.setComprobante(StringUtilities.eliminarCerosLeft(RespuestaTrasnfin.split(",")[3]));
                            infotransf.setFechaTransf(formatoFechatrasnf.format(fechapago));
                            infoTranferenciaCtaProp.setInfoTranfCtaProp(infotransf);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Atlas.getVista().mostrasmenuTransfctapropfin(infotransf);
                                }
                            });


                        } else {
                            final String coderror = RespuestaTrasnfin.split(",")[2];
                            final String mensaje = RespuestaTrasnfin.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TRANSF_CTA_PROP);
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

        return servicetrasnfctaprop;
    }

    ;

    public Service<Void> consultar_Costo() {
        serviceCcosto = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaCosto = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final infoTranferenciaCtaProp datatransf = infoTranferenciaCtaProp.getInfoTranfCtaProp();
                        final infoPago datainfoPago = infoPago.getInfoPago();

//                        tramaCosto.append("930,000,");
//                        tramaCosto.append(validarTipoCuenta(datatransf.getTipo_cta_origen()));
//                        tramaCosto.append(",");
//                        tramaCosto.append(cliente.getId_cliente());
//                        tramaCosto.append(",");
//                        tramaCosto.append("GDE,");
//                        tramaCosto.append(validarTipoTransaccion(datatransf.getTipo_cta_origen()));
//                        tramaCosto.append(",");
//                        tramaCosto.append(datatransf.getCta_origen());
//                        tramaCosto.append(",");
//                        tramaCosto.append(validarTipoCuenta(datatransf.getTipo_cta_origen()));
//                        tramaCosto.append(",~");


                        //850,023,CONNID,DOCID,ORIGEN,CODTRANSACCION,TIPOCTA,NUMCTA,CODFONDO,CLAVEHW,~
                        tramaCosto.append("850,023,");
                        tramaCosto.append(cliente.getRefid());
                        tramaCosto.append(",");
                        tramaCosto.append(cliente.getId_cliente());
                        tramaCosto.append(",");
                        tramaCosto.append("GDE,");
                        tramaCosto.append(validarTipoTransaccion(datatransf.getTipo_cta_origen()));
                        tramaCosto.append(",");
                        tramaCosto.append(validarTipoCuenta(datatransf.getTipo_cta_origen()));
                        tramaCosto.append(",");
                        tramaCosto.append(datatransf.getCta_origen());
                        tramaCosto.append(",");
                        tramaCosto.append("");//codigo fondo 
                        tramaCosto.append(",");
                        tramaCosto.append(cliente.getContraseña());
                        tramaCosto.append(",");
                        tramaCosto.append(cliente.getTokenOauth());
                        tramaCosto.append(",~");

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo = " + "##" + obtenerHoraActual());
                            //Respuesta = "930,000,000,12,1750,~";
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_COSTO, AtlasConstantes.PUERTO_COM_CONSULTA_COSTO, tramaCosto.toString());
//                           Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  Consulta Costo = " + Respuesta + "##" + obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal Consulta Costo = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo ctg = " + "##" + obtenerHoraActual());
//                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_COSTO_CTG, AtlasConstantes.PUERTO_COM_CONSULTA_COSTO, tramaCosto.toString());
//                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  Consulta costo ctg = " + Respuesta + "##" + obtenerHoraActual());
                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctg Consulta Costo = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        icono.setVisible(false);
                                        consultar_costo.setVisible(false);
                                        continuar.setLayoutX(84);
                                        mensaje_tranf.setText(" ");
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                        cancel();

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
                                    consultar_costo.setVisible(false);
                                    continuar.setLayoutX(84);
                                    mensaje_tranf.setText("EL COSTO PARA ESTA TRANSACCION ES DE $" + aux + "\nMAS EL 19% DE IVA VALIDAR TABLA DE TASAS Y TARIFAS");
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
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
                                    consultar_costo.setVisible(false);
                                    continuar.setLayoutX(84);
                                    mensaje_tranf.setText("");
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                    cancel();

                                }
                            });


                        }

//                        else if ("001".equals(Respuesta.split(",")[1])) {
//                            final String coderror = Respuesta.split(",")[2];
//                            final String mensaje = Respuesta.split(",")[3];
//
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    new ModalMensajes(mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
//                                    icono.setVisible(false);
//                                    consultar_costo.setVisible(false);
//                                    continuar.setLayoutX(84);
//                                    mensaje_tranf.setText("");
//                                    progreso.progressProperty().unbind();
//                                    progreso.setProgress(0);
//                                    progreso.setVisible(false);
//                                    cancel();
//
//                                }
//                            });
//
//
//                        } else if ("998".equals(Respuesta.split(",")[1])) {
//
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    new ModalMensajes("Error Consultando el Costo", "Error WS", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
//                                    icono.setVisible(false);
//                                    consultar_costo.setVisible(false);
//                                    continuar.setLayoutX(84);
//                                    mensaje_tranf.setText("");
//                                    progreso.progressProperty().unbind();
//                                    progreso.setProgress(0);
//                                    progreso.setVisible(false);
//                                    cancel();
//
//                                }
//                            });
//
//
//                        } else if ("997".equals(Respuesta.split(",")[1])) {
//
//                            final String respuesta = Respuesta.split(",")[2];
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    new ModalMensajes(respuesta, "Time out", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
//                                    icono.setVisible(false);
//                                    consultar_costo.setVisible(false);
//                                    continuar.setLayoutX(84);
//                                    mensaje_tranf.setText("");
//                                    progreso.progressProperty().unbind();
//                                    progreso.setProgress(0);
//                                    progreso.setVisible(false);
//                                    cancel();
//                                }
//                            });
//
//
//                            // return;
//                        }

                        return null;
                    }
                };


            }
        };

        return serviceCcosto;
    }

    ;
    
    
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

    private String validarTipoTransaccion(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

//        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
//            retorno.append("TRFEAHOGDE");
//
//        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
//            retorno.append("TRFECTEGDE");
//        }
        //para mas validaciones de tipo cuenta
        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0538");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0438");
        }
        return retorno.toString();
    }

    private String validarCodigoCuentaMQ(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0538");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0438");
        }
        //para mas validaciones
        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
