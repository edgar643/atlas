/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.AtlasAcceso;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagosterceros.DatosPagosTerceros;
import com.co.allus.modelo.pagosterceros.InfoTablaPagosTerceros;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import com.co.allus.utils.AtlasConstantes;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author stephania.rojas
 */
public class PagosATercerosConfirmController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button cancelarOP;
    @FXML
    private Button continuarOP;
    @FXML
    private Button costoOP;
    @FXML
    private Label lblconfirmar;
    @FXML
    private Label lblpago;
    @FXML
    private ProgressBar progreso;
    @FXML
    private ImageView icono;
    @FXML
    private Button continuar;
    private transient Service<Void> serviceConsMov;
    private transient Service<Void> serviceCcosto;
    private static GestorDocumental docs = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'PagosATercerosConfirm.fxml'.";
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'PagosATercerosConfirm.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'PagosATercerosConfirm.fxml'.";
        assert costoOP != null : "fx:id=\"costoOP\" was not injected: check your FXML file 'PagosATercerosConfirm.fxml'.";
        assert icono != null : "fx:id=\"icono\" was not injected: check your FXML file 'PagosATercerosConfirm.fxml'.";
        assert lblconfirmar != null : "fx:id=\"lblconfirmar\" was not injected: check your FXML file 'PagosATercerosConfirm.fxml'.";
        assert lblpago != null : "fx:id=\"lblpago\" was not injected: check your FXML file 'PagosATercerosConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosATercerosConfirm.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'PagosATercerosConfirm.fxml'.";


        progreso.setVisible(false);
        icono.setVisible(false);
        costoOP.setVisible(false);
        //continuarOP.setLayoutX(34d)
        continuarOP.setVisible(false);


    }

    @FXML
    void continuar(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblconfirmar.setText("RECUERDE QUE ESTA OPERACION PUEDE TENER COSTO");
                continuar.setVisible(false);
                costoOP.setVisible(true);
                continuarOP.setVisible(true);
            }
        });
    }

    public void mostrarPagoPrestamosConfirm(final DatosPagosTerceros infoTablaDetalle) {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosATercerosConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Label lblconfirmar = (Label) root.lookup("#lblconfirmar");
                    final Label lblCreditoAct = (Label) root.lookup("#lblpago");

                    List<InfoTablaPagosTerceros> seleccionPagos = infoTablaDetalle.getSeleccionPagos();
                    final StringBuilder mensaje = new StringBuilder();
                    if (AtlasConstantes.PAGO_X_DETALLE == infoTablaDetalle.getOrigenPago()) {
                        InfoTablaPagosTerceros pago = seleccionPagos.get(0);
                        mensaje.append(" ¿ Esta seguro que desea pagar el convenio \n");
                        mensaje.append(pago.colNombreConvenioProperty().getValue().trim());
                        mensaje.append("\n");
                        mensaje.append("por un valor de ");
                        if (infoTablaDetalle.isValorPagar()) {
                            // mensaje.append("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoCuotaMinEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoCuotaMinCent());
                            mensaje.append(infoTablaDetalle.getValorPagado());
                        } else if (infoTablaDetalle.isOtroValor()) {
                            // mensaje.append("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoTotalEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoTotalCent());
                            mensaje.append(infoTablaDetalle.getValorPagado());

                        }

                        mensaje.append(" desde la ");
                        mensaje.append(infoTablaDetalle.getTipoCtaPago());
                        mensaje.append("\n");
                        mensaje.append(infoTablaDetalle.getNumCtaPago());
                        mensaje.append(" ?");


                    } else if (AtlasConstantes.PAGO_X_LOTE == infoTablaDetalle.getOrigenPago()) {
                        InfoTablaPagosTerceros data = seleccionPagos.get(infoTablaDetalle.getPagoActual() - 1);
                        mensaje.append(" ¿ Esta seguro que desea pagar el convenio \n ");
                        mensaje.append(data.colNombreConvenioProperty().getValue());
                        mensaje.append("\n");
                        mensaje.append("por un valor de ");
                        if (infoTablaDetalle.isValorPagar()) {
                            // mensaje.append("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoCuotaMinEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoCuotaMinCent());
                            mensaje.append(infoTablaDetalle.getValorPagado());
                        } else if (infoTablaDetalle.isOtroValor()) {
                            // mensaje.append("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoTotalEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoTotalCent());
                            mensaje.append(infoTablaDetalle.getValorPagado());

                        }
                        mensaje.append(" desde la ");
                        mensaje.append(infoTablaDetalle.getTipoCtaPago());
                        mensaje.append("\n");
                        mensaje.append(modelSaldoTarjeta.enmascararNumero(infoTablaDetalle.getNumCtaPago(), 6));
                        mensaje.append(" ?");

                    }




                    lblconfirmar.setText(mensaje.toString());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    if (AtlasConstantes.PAGO_POR_DETALLE == infoTablaDetalle.getOrigenPago()) {
                        final String infoCredito = "Pago " + infoTablaDetalle.getPagoActual() + "/" + infoTablaDetalle.getPagoActual();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);
                    } else if (AtlasConstantes.PAGO_POR_LOTE == infoTablaDetalle.getOrigenPago()) {
                        InfoTablaPagosTerceros data = seleccionPagos.get(infoTablaDetalle.getPagoActual() - 1);
                        final String infoCredito = "Pago " + infoTablaDetalle.getPagoActual() + "/" + infoTablaDetalle.getSeleccionPagos().size();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);

                    }

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

    @FXML
    void cancelarOP(ActionEvent event) {
        PagosATercerosFacturasController controller = new PagosATercerosFacturasController();
        controller.mostrarPagosTerceros(DatosPagosTerceros.getDatosPagosTerceros(), DatosPagosTerceros.getDatosPagosTerceros().getOrigenPago());
    }

    @FXML
    void continuarOP(ActionEvent event) {
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono PagosTerceros  " + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo PagosTerceros  " + "##" + obtenerHoraActual());
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
        serviceConsMov = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String RespuestaPagoPrest = new String();
                        final StringBuilder consultaPagoATerceros = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final int origen = DatosPagosTerceros.getDatosPagosTerceros().getOrigenPago();
                        DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();


//                                        850,
//                065,
//                connid,
//                codTransaccion,
//                TipoPersona(P=persona , E=empresa),
//                IVR o GDE,
//                identificacion,
//                Código de convenio,
//                Referencia 1 de la factura,
//                Referencia 2 de la factura,
//                Referencia 3 de la factura,
//                Indicador referencia fija,
//                Número  Factura,
//                Valor de la factura,
//                Valor pagado,
//                Cuenta recuadadora,
//                Tipo cuenta recaudadora,
//                Clase cuenta,
//                Fecha limite de pago,
//                Número de cuenta pagadora,
//                Tipo de cuenta pagadora,
//                Indicador de base de datos,
//                Nombre del convenio,
//                clavehardware,~
                        final AtlasAcceso Atlasacceso = new AtlasAcceso();
                        String rnv = "";
                        if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                            rnv = "E";
                        } else {
                            rnv = "P";
                        }


                        if (AtlasConstantes.PAGO_X_DETALLE == origen) {
                            InfoTablaPagosTerceros infopago = datosPagosTerceros.getSeleccionPagos().get(0);
                            consultaPagoATerceros.append("850,065,");
                            consultaPagoATerceros.append(cliente.getRefid());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(validarCodigoCuentaMQ(DatosPagosTerceros.getDatosPagosTerceros().getTipoCtaPago()));
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(rnv);
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append("GDE");
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(cliente.getId_cliente());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colCodConvenioProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colNomRef1Property().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colNomRef2Property().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colNomRef3Property().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colIndicadorRefFijaProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colNumeroFacturaProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colValorPagOriProperty().getValue().replace("$", "").replace(",", "").trim());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(DatosPagosTerceros.getDatosPagosTerceros().getValorPagado().replace("$", "").replace(",", "").trim());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colCuentaRecaudadoraProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colTipoCuentaProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colClaseCuentaProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colFechaVencimientoProperty().getValue().replace("/", "").trim());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(DatosPagosTerceros.getDatosPagosTerceros().getNumCtaPago());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(validarTipoCuenta(DatosPagosTerceros.getDatosPagosTerceros().getTipoCtaPago()));
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colIndicadorBDProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(infopago.colNombreConvenioProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(cliente.getContraseña());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(cliente.getTokenOauth());
                            consultaPagoATerceros.append(",~");

                        } else if (AtlasConstantes.PAGO_X_LOTE == origen) {
                            InfoTablaPagosTerceros data = datosPagosTerceros.getSeleccionPagos().get(datosPagosTerceros.getPagoActual() - 1);
                            consultaPagoATerceros.append("850,065,");
                            consultaPagoATerceros.append(cliente.getRefid());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(validarCodigoCuentaMQ(DatosPagosTerceros.getDatosPagosTerceros().getTipoCtaPago()));
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(rnv);
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append("GDE");
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(cliente.getId_cliente());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colCodConvenioProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colNomRef1Property().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colNomRef2Property().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colNomRef3Property().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colIndicadorRefFijaProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colNumeroFacturaProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colValorPagOriProperty().getValue().replace("$", "").replace(",", "").trim());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(DatosPagosTerceros.getDatosPagosTerceros().getValorPagado().replace("$", "").replace(",", "").trim());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colCuentaRecaudadoraProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colTipoCuentaProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colClaseCuentaProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colFechaVencimientoProperty().getValue().replace("/", "").trim());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(DatosPagosTerceros.getDatosPagosTerceros().getNumCtaPago());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(validarTipoCuenta(DatosPagosTerceros.getDatosPagosTerceros().getTipoCtaPago()));
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colIndicadorBDProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(data.colNombreConvenioProperty().getValue());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(cliente.getContraseña());
                            consultaPagoATerceros.append(",");
                            consultaPagoATerceros.append(cliente.getTokenOauth());
                            consultaPagoATerceros.append(",~");
                        }


//                        System.out.println("TRAMA PAGO PAGOSTERCEROS " + consultaPagoATerceros.toString());

                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ PagoPrestamos = " + "##" + obtenerHoraActual());

                            RespuestaPagoPrest = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, consultaPagoATerceros.toString());
                            //  RespuestaPagoPrest = "850,065,000,TRANSACCION EXITOSA,0124536978,~";
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ Pagosterceros = " + RespuestaPagoPrest + "##" + obtenerHoraActual());
                            // Thread.sleep(1000);
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ pagoprestamos = " + "##" + obtenerHoraActual());
                                RespuestaPagoPrest = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, consultaPagoATerceros.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + RespuestaPagoPrest + "##" + obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
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

                        if ("000".equals(RespuestaPagoPrest.split(",")[2])) {
                            //
                            DatosPagosTerceros datosTablaDetalle = DatosPagosTerceros.getDatosPagosTerceros();

                            datosTablaDetalle.setComprobante(RespuestaPagoPrest.split(",")[4]);

                            if (AtlasConstantes.PAGO_X_DETALLE == origen) {


//                                    final String pago_Total = datosTablaDetalle.getPagoTotalAcum();
//                                    if (pago_Total.trim().isEmpty()) {
//
//                                        datosTablaDetalle.setPagoTotalAcum(formatonum.format(Double.parseDouble(datosTablaDetalle.getPagoCuotaMinEnt())).replace(".", "") + "." + datosTablaDetalle.getPagoCuotaMinCent());
//                                    } else {
//
//                                        String var2 = formatonum.format(Double.parseDouble(datosTablaDetalle.getPagoCuotaMinEnt())).replace(".", "") + "." + datosTablaDetalle.getPagoCuotaMinCent();
//                                        String cadena = "";
//
//                                        final BigDecimal num1 = new BigDecimal(pago_Total);
//                                        final BigDecimal num2 = new BigDecimal(var2);
//
//                                        try {
//                                            final BigDecimal suma = num1.add(num2);
//
//                                            cadena = String.valueOf(suma);
//                                            System.out.println("suma cadena es " + cadena);
//
//                                        } catch (Exception e) {
//                                            e.printStackTrace(System.out);
//                                        }
//
//                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(cadena)).replace(".", ",") + "." + cadena.substring(cadena.length() - 2, cadena.length()));
//
//                                    }



                                datosTablaDetalle.setPagoTotalAcum(datosPagosTerceros.getValorPagado());



                            } else if (AtlasConstantes.PAGO_X_LOTE == origen) {

                                // InfoTablaPagosTerceros data = datosTablaDetalle.getSeleccionPagos().get(DatosPagoPrestamo.getDatosTablaDetalle().getPagoActual() - 1);
                                String pago_Total = datosTablaDetalle.getPagoTotalAcum();

                                if (pago_Total.trim().isEmpty()) {

                                    datosTablaDetalle.setPagoTotalAcum(datosTablaDetalle.getValorPagado().replace("$", "").trim());
                                } else {

                                    pago_Total = pago_Total.replace("$", "").replace(",", "").trim();
                                    String var2 = datosTablaDetalle.getValorPagado().replace("$", "").replace(",", "").trim();
                                    String cadena = "";

                                    final BigDecimal num1 = new BigDecimal(pago_Total);
                                    final BigDecimal num2 = new BigDecimal(var2);

                                    try {
                                        final BigDecimal suma = num1.add(num2);

                                        cadena = String.valueOf(suma);

                                    } catch (Exception e) {
                                        Logger.getGlobal().log(Level.SEVERE, e.toString());
                                    }

                                    datosTablaDetalle.setPagoTotalAcum(formatonum.format(Double.parseDouble(cadena)).replace(".", ",") + "." + cadena.substring(cadena.length() - 2, cadena.length()));


                                }



                            }


                            DatosPagosTerceros.setDatosPagosTerceros(datosTablaDetalle);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final PagosATercerosFinController controller = new PagosATercerosFinController();
                                    controller.mostrarPagosFin(DatosPagosTerceros.getDatosPagosTerceros());
                                }
                            });


                        } else {
                            final String coderror = RespuestaPagoPrest.split(",")[2];
                            final String mensaje = RespuestaPagoPrest.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    cancel();
                                    //continuar_Op.cancel();
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

        return serviceConsMov;
    }

    @FXML
    void costoOP(ActionEvent event) {
        consultar_Costo().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario presionó ConsultarCosto" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        consultar_Costo().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ConsultarCosto" + "##" + obtenerHoraActual());
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
                        DatosPagosTerceros datainfoPago = DatosPagosTerceros.getDatosPagosTerceros();

                        //850,023,CONNID,DOCID,ORIGEN,CODTRANSACCION,TIPOCTA,NUMCTA,CODFONDO,CLAVEHW,~
                        tramaCosto.append("850,023,");
                        tramaCosto.append(cliente.getRefid());
                        tramaCosto.append(",");
                        tramaCosto.append(cliente.getId_cliente());
                        tramaCosto.append(",");
                        tramaCosto.append("GDE,");
                        tramaCosto.append(validarTipoTransaccion(datainfoPago.getTipoCtaPago()));
                        tramaCosto.append(",");
                        tramaCosto.append(validarTipoCuenta(datainfoPago.getTipoCtaPago()));
                        tramaCosto.append(",");
                        tramaCosto.append(datainfoPago.getNumCtaPago());
                        tramaCosto.append(",");
                        tramaCosto.append("");//codigo fondo 
                        tramaCosto.append(",");
                        tramaCosto.append(cliente.getContraseña());
                         tramaCosto.append(",");
                        tramaCosto.append(cliente.getTokenOauth());
                        tramaCosto.append(",~");


                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo = " + "##" + obtenerHoraActual());
                            //Respuesta = "930,000,000,12,1750,~";
                            //Respuesta = servicioSS.enviarRecibirServidor("172.20.1.70", 9930, tramaCosto.toString());

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());

//                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  Consulta Costo = " + Respuesta + "##" + obtenerHoraActual());
                        } catch (Exception ex) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal Consulta Costo = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo ctg = " + "##" + obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor("172.20.1.70", 9930, tramaCosto.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());

//                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaCosto.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  Consulta costo ctg = " + Respuesta + "##" + obtenerHoraActual());
                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctg Consulta Costo = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        icono.setVisible(false);
                                        costoOP.setVisible(false);
                                        continuarOP.setLayoutX(84);
                                        lblconfirmar.setText(" ");
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
                                    costoOP.setVisible(false);
                                    continuarOP.setLayoutX(84);
                                    lblconfirmar.setText("EL COSTO PARA ESTA TRANSACCION ES DE $" + aux + "\nMAS EL 19% DE IVA VALIDAR TABLA DE TASAS Y TARIFAS");
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
                                    costoOP.setVisible(false);
                                    continuarOP.setLayoutX(84);
                                    lblconfirmar.setText("");
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

        return retorno.toString();
    }

    private String validarTipoTransaccion(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

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
            retorno.append("1529");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("1429");
        }
        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
