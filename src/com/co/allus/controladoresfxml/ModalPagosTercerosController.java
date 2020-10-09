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
import com.co.allus.modelo.pagosaterceros.convenio;
import com.co.allus.modelo.pagosaterceros.infoPago;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ModalPagosTercerosController implements Initializable {

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
    private transient Label mensaje_pagos;
    @FXML
    private transient HBox menu_progreso;
    @FXML
    private transient Pane pane_modalpagos;
    @FXML
    private transient ProgressBar progreso;
    @FXML
    private transient Label titulo_conv;
    @FXML
    private transient Label titulo_pagos;
    @FXML
    private transient ImageView icono;
    private transient Service<Void> serviceCcosto;
    private transient Service<Void> servicePagoTercero;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        assert consultar_costo != null : "fx:id=\"consultar_costo\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        assert mensaje_pagos != null : "fx:id=\"mensaje_pagos\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        assert pane_modalpagos != null : "fx:id=\"pane_modalpagos\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        assert titulo_conv != null : "fx:id=\"titulo_conv\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        assert titulo_pagos != null : "fx:id=\"titulo_pagos\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        assert icono != null : "fx:id=\"icono\" was not injected: check your FXML file 'ModalPagosTerceros.fxml'.";
        this.resources = resources;
        this.location = location;

        progreso.setVisible(false);

    }

    public void mostrarModalPagos(final convenio dataconvenio, final infoPago datapaPago) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/ModalPagosTerceros.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Label infoCosto = (Label) root.lookup("#mensaje_pagos");
                    final Label titulo_conv = (Label) root.lookup("#titulo_conv");
                    titulo_conv.setText(dataconvenio.getCod_conv() + " - " + dataconvenio.getNom_conv());
                    infoCosto.setText("RECUERDE QUE ESTA OPERACION PUEDE TENER COSTO");
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();

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
            servicePagoTercero.cancel();
        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        Atlas.getVista().mostrarPanePagos("PAGOS A TERCEROS");

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
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono PagarCuentas" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo PagarCuentas" + "##" + obtenerHoraActual());
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
        servicePagoTercero = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String Respuestapagofin = new String();
                        final StringBuilder tramapagaCuentas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final convenio dataconvenio = convenio.getConvenio();
                        final infoPago datainfoPago = infoPago.getInfoPago();
                        final Date fecha = new Date();

                        /*CODIFICACION AUTOMATICA*/
                        final Runnable aFenix = new EnvioTipAutFenix(cliente.getCodemp(), "003");
                        final Thread aFen = new Thread(aFenix);
                        aFen.start();


                        tramapagaCuentas.append("900,005,");
                        tramapagaCuentas.append(cliente.getRefid());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(validarCodigoCuentaMQ(datainfoPago.getDescripcionCuentaOrigen()));
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(cliente.getId_cliente());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(formatoFecha.format(fecha));
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(formatoHora.format(fecha));
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(validarTipoCuenta(datainfoPago.getDescripcionCuentaOrigen()));
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getCuentaOrigen());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(dataconvenio.getCod_conv());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getNumeroFactura());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(StringUtilities.eliminarCerosLeft(datainfoPago.getValorPagarent()));
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getValorpagarCent());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getReferenciaPago1());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getReferenciaPago2());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getConcepto1());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getValor1());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getConcepto2());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getValor2());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getConcepto3());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getValor3());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getConcepto4());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getValor4());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getConcepto5());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getValor5());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getConcepto6());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(datainfoPago.getValor6());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(cliente.getContraseña());
                        tramapagaCuentas.append(",");
                        tramapagaCuentas.append(cliente.getTokenOauth());
                        tramapagaCuentas.append(",~");


                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ PAGAR CUENTAS = " + "##" + obtenerHoraActual());
                            Respuestapagofin = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, tramapagaCuentas.toString());
                            // Respuestapagofin = "900,005,000,comprobante,valorpagado,valpagcent,nomconv,refconv,ref1,ref2,mostrarref2,concepto,nombrepagador,tipoRec,cuentaOri,tipoCuenta,numFactura,c1,v1,c2,v2,c3,v3,c4,v4,c5,v5,c6,v6,~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ PAGAR CUENTAS = " + StringUtilities.tramaSubString(Respuestapagofin, 0, 1, ",") + "##" + obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ  MQ PAGAR CTAS = " + "##" + obtenerHoraActual());
                                Respuestapagofin = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, tramapagaCuentas.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuestapagofin, 0, 1, ",") + "##" + obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                                        cancel();
                                        // continuar_Op.cancel();
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }
                        }

                        if ("000".equals(Respuestapagofin.split(",")[2])) {
                            final Date fechapago = new Date();

                            datainfoPago.setComprobantefinPago(StringUtilities.eliminarCerosLeft(Respuestapagofin.split(",")[3]));
//                                    dataPago.setValorPagarent(Respuestapagofin.split(",")[4]);
//                                    dataPago.setValorpagarCent(Respuestapagofin.split(",")[5]);
//                                    dataPago.setReferenciaConvenio(Respuestapagofin.split(",")[6]);
//                                    dataPago.setCuentaOrigen(Respuestapagofin.split(",")[7]);
//                                    dataPago.setDescripcionCuentaOrigen(Respuestapagofin.split(",")[8]);
//                                    dataPago.setNumeroFactura(Respuestapagofin.split(",")[9]);
//                                    dataPago.setNombreConvenio(Respuestapagofin.split(",")[10]);
                            datainfoPago.setFechaPago(formatoFechaPago.format(fechapago));
                            infoPago.setInfoPago(datainfoPago);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Atlas.getVista().mostrasmenuPagosfin(convenio.getConvenio(), infoPago.getInfoPago());
                                }
                            });


                        } else {
                            final String coderror = Respuestapagofin.split(",")[2];
                            final String mensaje = Respuestapagofin.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
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

        return servicePagoTercero;
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
                        final infoPago datainfoPago = infoPago.getInfoPago();

                        //850,023,CONNID,DOCID,ORIGEN,CODTRANSACCION,TIPOCTA,NUMCTA,CODFONDO,CLAVEHW,~
                        tramaCosto.append("850,023,");
                        tramaCosto.append(cliente.getRefid());
                        tramaCosto.append(",");
                        tramaCosto.append(cliente.getId_cliente());
                        tramaCosto.append(",");
                        tramaCosto.append("GDE,");
                        tramaCosto.append(validarTipoTransaccion(datainfoPago.getDescripcionCuentaOrigen()));
                        tramaCosto.append(",");
                        tramaCosto.append(validarTipoCuenta(datainfoPago.getDescripcionCuentaOrigen()));
                        tramaCosto.append(",");
                        tramaCosto.append(datainfoPago.getCuentaOrigen());
                        tramaCosto.append(",");
                        tramaCosto.append("");//codigo fondo 
                        tramaCosto.append(",");
                        tramaCosto.append(cliente.getContraseña());
                         tramaCosto.append(",");
                        tramaCosto.append(cliente.getTokenOauth());
                        tramaCosto.append(",~");


//                        tramaCosto.append("930,000,");
//                        tramaCosto.append(validarTipoCuenta(datainfoPago.getDescripcionCuentaOrigen()));
//                        tramaCosto.append(",");
//                        tramaCosto.append(cliente.getId_cliente());
//                        tramaCosto.append(",");
//                        tramaCosto.append("GDE,");
//                        tramaCosto.append(validarTipoTransaccion(datainfoPago.getDescripcionCuentaOrigen()));
//                        tramaCosto.append(",");
//                        tramaCosto.append(datainfoPago.getCuentaOrigen());
//                        tramaCosto.append(",");
//                        tramaCosto.append(validarTipoCuenta(datainfoPago.getDescripcionCuentaOrigen()));
//                        tramaCosto.append(",~");




                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo = " + "##" + obtenerHoraActual());
                            //Respuesta = "930,000,000,12,1750,~";
                            //Respuesta = servicioSS.enviarRecibirServidor("172.20.1.70", 9930, tramaCosto.toString());

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());

//                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  Consulta Costo = " + Respuesta + "##" + obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal Consulta Costo = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo ctg = " + "##" + obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor("172.20.1.70", 9930, tramaCosto.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());

//                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
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
                                        mensaje_pagos.setText(" ");
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
                                    mensaje_pagos.setText("EL COSTO PARA ESTA TRANSACCION ES DE $" + aux + "\nMAS EL 19% DE IVA VALIDAR TABLA DE TASAS Y TARIFAS");
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
                                    mensaje_pagos.setText("");
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
//                                    mensaje_pagos.setText("");
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
//                                    mensaje_pagos.setText("");
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
//                                    mensaje_pagos.setText("");
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
//            retorno.append("CPAGAHOGDE");
//
//        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
//            retorno.append("CPAGCTEGDE");
//        }
        //para mas validaciones de tipo cuenta
        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0529");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0429");
        }

        return retorno.toString();
    }

    private String validarCodigoCuentaMQ(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0529");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0429");
        }
        //para mas validaciones
        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
