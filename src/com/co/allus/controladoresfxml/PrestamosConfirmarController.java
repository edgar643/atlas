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
import com.co.allus.modelo.pagoprestamos.DatosPagoPrestamo;
import com.co.allus.modelo.pagoprestamos.infoTablaPagosPrest;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class PrestamosConfirmarController implements Initializable {

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
    private ProgressBar progreso;
    @FXML
    private Label lblpago;
    private transient Service<Void> serviceConsMov;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'PrestamosConfirmar.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'PrestamosConfirmar.fxml'.";
        assert lblconfirmar != null : "fx:id=\"lblconfirmar\" was not injected: check your FXML file 'PrestamosConfirmar.fxml'.";
        assert lblpago != null : "fx:id=\"lblpago\" was not injected: check your FXML file 'PrestamosConfirmar.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PrestamosConfirmar.fxml'.";
        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);
    }

    @FXML
    void cancelarOP(final ActionEvent event) {
        final PrestamosPagosController controller = new PrestamosPagosController();
        controller.mostrarPagoPrestamos(DatosPagoPrestamo.getDatosTablaDetalle(), DatosPagoPrestamo.getDatosTablaDetalle().getOrigenPago());
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
                        final StringBuilder consultaPagoPrestAtlas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final int origen = DatosPagoPrestamo.getDatosTablaDetalle().getOrigenPago();
                        List<infoTablaPagosPrest> seleccionPagos = DatosPagoPrestamo.getDatosTablaDetalle().getSeleccionPagos();



                        if (AtlasConstantes.PAGO_POR_DETALLE == origen) {
                            consultaPagoPrestAtlas.append("850,040,");
                            consultaPagoPrestAtlas.append(cliente.getRefid());
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(validarCodigoCuentaMQ(DatosPagoPrestamo.getDatosTablaDetalle().getTipoCtaPago()));
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(cliente.getId_cliente());
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(DatosPagoPrestamo.getDatosTablaDetalle().getNumeroCredito());
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(validarTipoCuenta(DatosPagoPrestamo.getDatosTablaDetalle().getTipoCtaPago()));
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(DatosPagoPrestamo.getDatosTablaDetalle().getNumctaPago());
                            consultaPagoPrestAtlas.append(",");
                            if (DatosPagoPrestamo.getDatosTablaDetalle().isPago_min_sel()) {
                                consultaPagoPrestAtlas.append("1");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append(DatosPagoPrestamo.getDatosTablaDetalle().getPagoCuotaMinEnt() + DatosPagoPrestamo.getDatosTablaDetalle().getPagoCuotaMinCent());
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");

                            } else if (DatosPagoPrestamo.getDatosTablaDetalle().isPago_total_sel()) {
                                consultaPagoPrestAtlas.append("2");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append(DatosPagoPrestamo.getDatosTablaDetalle().getPagoTotalEnt() + DatosPagoPrestamo.getDatosTablaDetalle().getPagoTotalCent());
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");

                            } else if (DatosPagoPrestamo.getDatosTablaDetalle().isOtro_valor_sel()) {
                                consultaPagoPrestAtlas.append("3");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append(DatosPagoPrestamo.getDatosTablaDetalle().getOtroValorEnt() + DatosPagoPrestamo.getDatosTablaDetalle().getOtroValorCent());
                                consultaPagoPrestAtlas.append(",");

                            }

                            consultaPagoPrestAtlas.append(cliente.getContraseña());
                            consultaPagoPrestAtlas.append(",");
                             consultaPagoPrestAtlas.append(cliente.getTokenOauth());
                            consultaPagoPrestAtlas.append(",~");

                        } else if (AtlasConstantes.PAGO_POR_LOTE == origen) {

                            infoTablaPagosPrest data = seleccionPagos.get(DatosPagoPrestamo.getDatosTablaDetalle().getPagoActual() - 1);
                            consultaPagoPrestAtlas.append("850,040,");
                            consultaPagoPrestAtlas.append(cliente.getRefid());
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(validarCodigoCuentaMQ(DatosPagoPrestamo.getDatosTablaDetalle().getTipoCtaPago()));
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(cliente.getId_cliente());
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(data.colCreditoProperty().getValue());
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(validarTipoCuenta(DatosPagoPrestamo.getDatosTablaDetalle().getTipoCtaPago()));
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(DatosPagoPrestamo.getDatosTablaDetalle().getNumctaPago());
                            consultaPagoPrestAtlas.append(",");
                            if (DatosPagoPrestamo.getDatosTablaDetalle().isPago_min_sel()) {
                                consultaPagoPrestAtlas.append("1");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append(data.colPagoMinProperty().getValue().replace("$", "").replace(".", "").replace(",", "").trim());
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");

                            } else if (DatosPagoPrestamo.getDatosTablaDetalle().isPago_total_sel()) {
                                consultaPagoPrestAtlas.append("2");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append(data.colSaldoTotalProperty().getValue().replace("$", "").replace(".", "").replace(",", "").trim());
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");

                            } else if (DatosPagoPrestamo.getDatosTablaDetalle().isOtro_valor_sel()) {
                                consultaPagoPrestAtlas.append("3");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append("0");
                                consultaPagoPrestAtlas.append(",");
                                consultaPagoPrestAtlas.append(DatosPagoPrestamo.getDatosTablaDetalle().getOtroValorEnt() + DatosPagoPrestamo.getDatosTablaDetalle().getOtroValorCent());
                                consultaPagoPrestAtlas.append(",");

                            }

                            consultaPagoPrestAtlas.append(cliente.getContraseña());
                            consultaPagoPrestAtlas.append(",");
                            consultaPagoPrestAtlas.append(cliente.getTokenOauth());
                            consultaPagoPrestAtlas.append(",~");
                        }


//                        System.out.println("TRAMA PAGO PRESTAMO " + consultaPagoPrestAtlas.toString());

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ PagoPrestamos = " + "##" + obtenerHoraActual());
                            RespuestaPagoPrest = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, consultaPagoPrestAtlas.toString());
                            //RespuestaPagoPrest = "850,040,000,TRANSACCION EXITOSA,0124536978,~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ PagoPrestamos = " + RespuestaPagoPrest + "##" + obtenerHoraActual());
                            // Thread.sleep(1000);
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ pagoprestamos = " + "##" + obtenerHoraActual());
                                RespuestaPagoPrest = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, consultaPagoPrestAtlas.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + RespuestaPagoPrest + "##" + obtenerHoraActual());

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

                        if ("000".equals(RespuestaPagoPrest.split(",")[2])) {
                            //
                            final DatosPagoPrestamo datosTablaDetalle = DatosPagoPrestamo.getDatosTablaDetalle();
                            datosTablaDetalle.setComprobante(RespuestaPagoPrest.split(",")[4]);

                            if (AtlasConstantes.PAGO_POR_DETALLE == origen) {

                                if (DatosPagoPrestamo.getDatosTablaDetalle().isPago_min_sel()) {
                                    final String pago_Total = datosTablaDetalle.getPago_Total();
                                    if (pago_Total.trim().isEmpty()) {

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(datosTablaDetalle.getPagoCuotaMinEnt())).replace(".", "") + "." + datosTablaDetalle.getPagoCuotaMinCent());
                                    } else {

                                        String var2 = formatonum.format(Double.parseDouble(datosTablaDetalle.getPagoCuotaMinEnt())).replace(".", "") + "." + datosTablaDetalle.getPagoCuotaMinCent();
                                        String cadena = "";

                                        final BigDecimal num1 = new BigDecimal(pago_Total);
                                        final BigDecimal num2 = new BigDecimal(var2);

                                        try {
                                            final BigDecimal suma = num1.add(num2);

                                            cadena = String.valueOf(suma);
//                                            System.out.println("suma cadena es " + cadena);

                                        } catch (Exception e) {
                                            Logger.getGlobal().log(Level.SEVERE, e.toString());
                                        }

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(cadena)).replace(".", ",") + "." + cadena.substring(cadena.length() - 2, cadena.length()));

                                    }



                                } else if (DatosPagoPrestamo.getDatosTablaDetalle().isPago_total_sel()) {
                                    final String pago_Total = datosTablaDetalle.getPago_Total();
                                    if (pago_Total.trim().isEmpty()) {

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(datosTablaDetalle.getPagoTotalEnt())).replace(".", "") + "." + datosTablaDetalle.getPagoTotalCent());
                                    } else {

                                        String var2 = formatonum.format(Double.parseDouble(datosTablaDetalle.getPagoTotalEnt())).replace(".", "") + "." + datosTablaDetalle.getPagoTotalCent();
                                        String cadena = "";

                                        final BigDecimal num1 = new BigDecimal(pago_Total);
                                        final BigDecimal num2 = new BigDecimal(var2);

                                        try {
                                            final BigDecimal suma = num1.add(num2);

                                            cadena = String.valueOf(suma);

                                        } catch (Exception e) {
                                            Logger.getGlobal().log(Level.SEVERE, e.toString());
                                        }

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(cadena)).replace(".", ",") + "." + cadena.substring(cadena.length() - 2, cadena.length()));

                                    }


                                } else if (DatosPagoPrestamo.getDatosTablaDetalle().isOtro_valor_sel()) {

                                    final String pago_Total = datosTablaDetalle.getPago_Total();
                                    if (pago_Total.trim().isEmpty()) {

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(datosTablaDetalle.getOtroValorEnt())).replace(".", "") + "." + datosTablaDetalle.getOtroValorCent());
                                    } else {

                                        String var2 = formatonum.format(Double.parseDouble(datosTablaDetalle.getOtroValorEnt())).replace(".", "") + "." + datosTablaDetalle.getOtroValorCent();
                                        String cadena = "";

                                        final BigDecimal num1 = new BigDecimal(pago_Total);
                                        final BigDecimal num2 = new BigDecimal(var2);

                                        try {
                                            final BigDecimal suma = num1.add(num2);

                                            cadena = String.valueOf(suma);
//                                            System.out.println("suma cadena es " + cadena);

                                        } catch (Exception e) {
                                            Logger.getGlobal().log(Level.SEVERE, e.toString());
                                        }

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(cadena)).replace(".", "") + "." + cadena.substring(cadena.length() - 2, cadena.length()));

                                    }


                                }



                            } else if (AtlasConstantes.PAGO_POR_LOTE == origen) {

                                final infoTablaPagosPrest data = seleccionPagos.get(DatosPagoPrestamo.getDatosTablaDetalle().getPagoActual() - 1);

                                if (DatosPagoPrestamo.getDatosTablaDetalle().isPago_min_sel()) {

                                    final String pago_Total = datosTablaDetalle.getPago_Total();

                                    if (pago_Total.trim().isEmpty()) {

                                        datosTablaDetalle.setPago_Total(data.colPagoMinProperty().getValue().replace("$", "").replace(",", "").trim());
                                    } else {

                                        String var2 = data.colPagoMinProperty().getValue().replace("$", "").replace(",", "").trim();
                                        String cadena = "";

                                        final BigDecimal num1 = new BigDecimal(pago_Total);
                                        final BigDecimal num2 = new BigDecimal(var2);

                                        try {
                                            final BigDecimal suma = num1.add(num2);

                                            cadena = String.valueOf(suma);
//                                            System.out.println("suma cadena es " + cadena);

                                        } catch (Exception e) {
                                            Logger.getGlobal().log(Level.SEVERE, e.toString());
                                        }

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(cadena)).replace(".", "") + "." + cadena.substring(cadena.length() - 2, cadena.length()));


                                    }



                                } else if (DatosPagoPrestamo.getDatosTablaDetalle().isPago_total_sel()) {


                                    final String pago_Total = datosTablaDetalle.getPago_Total();

                                    if (pago_Total.trim().isEmpty()) {

                                        datosTablaDetalle.setPago_Total(data.colSaldoTotalProperty().getValue().replace("$", "").replace(",", "").trim());
                                    } else {

                                        final String var2 = data.colSaldoTotalProperty().getValue().replace("$", "").replace(",", "").trim();
                                        String cadena = "";

                                        final BigDecimal num1 = new BigDecimal(pago_Total);
                                        final BigDecimal num2 = new BigDecimal(var2);

                                        try {
                                            final BigDecimal suma = num1.add(num2);

                                            cadena = String.valueOf(suma);

                                        } catch (Exception e) {
                                            Logger.getGlobal().log(Level.SEVERE, e.toString());
                                        }

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(cadena)).replace(".", "") + "." + cadena.substring(cadena.length() - 2, cadena.length()));

                                    }


                                } else if (DatosPagoPrestamo.getDatosTablaDetalle().isOtro_valor_sel()) {

                                    final String pago_Total = datosTablaDetalle.getPago_Total();
                                    if (pago_Total.trim().isEmpty()) {

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(datosTablaDetalle.getOtroValorEnt())).replace(".", "") + "." + datosTablaDetalle.getOtroValorCent());
                                    } else {

                                        final String var2 = formatonum.format(Double.parseDouble(datosTablaDetalle.getOtroValorEnt())).replace(".", "") + "." + datosTablaDetalle.getOtroValorCent();
                                        String cadena = "";

                                        final BigDecimal num1 = new BigDecimal(pago_Total);
                                        final BigDecimal num2 = new BigDecimal(var2);

                                        try {
                                            final BigDecimal suma = num1.add(num2);

                                            cadena = String.valueOf(suma);
//                                            System.out.println("suma cadena es " + cadena);

                                        } catch (Exception e) {
                                            Logger.getGlobal().log(Level.SEVERE, e.toString());
                                        }

                                        datosTablaDetalle.setPago_Total(formatonum.format(Double.parseDouble(cadena)).replace(".", "") + "." + cadena.substring(cadena.length() - 2, cadena.length()));

                                    }


                                }


                            }

                            DatosPagoPrestamo.setDatosTablaDetalle(datosTablaDetalle);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final PrestamosFinController controller = new PrestamosFinController();
                                    controller.mostrarPagosFin(DatosPagoPrestamo.getDatosTablaDetalle());
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

    public void mostrarPagoPrestamosConfirm(final DatosPagoPrestamo infoTablaDetalle) {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/PrestamosConfirmar.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Label lblconfirmar = (Label) root.lookup("#lblconfirmar");
                    final Label lblCreditoAct = (Label) root.lookup("#lblpago");
                    final List<infoTablaPagosPrest> seleccionPagos = infoTablaDetalle.getSeleccionPagos();
                    final StringBuilder mensaje = new StringBuilder();
                    if (AtlasConstantes.PAGO_POR_DETALLE == infoTablaDetalle.getOrigenPago()) {

                        mensaje.append(" ¿ Esta seguro que desea realizar el pago del crédito ");
                        mensaje.append(infoTablaDetalle.getNumeroCredito());
                        mensaje.append("\n");
                        mensaje.append("por un valor de ");
                        if (infoTablaDetalle.isPago_min_sel()) {
                            mensaje.append("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoCuotaMinEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoCuotaMinCent());

                        } else if (infoTablaDetalle.isPago_total_sel()) {
                            mensaje.append("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoTotalEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoTotalCent());

                        } else if (infoTablaDetalle.isOtro_valor_sel()) {
                            mensaje.append("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getOtroValorEnt())).replace(".", ",") + "." + infoTablaDetalle.getOtroValorCent());

                        }
                        mensaje.append(" desde la ");
                        mensaje.append(infoTablaDetalle.getTipoCtaPago());
                        mensaje.append("\n");
                        mensaje.append(infoTablaDetalle.getNumctaPago());
                        mensaje.append(" ?");


                    } else if (AtlasConstantes.PAGO_POR_LOTE == infoTablaDetalle.getOrigenPago()) {
                        final infoTablaPagosPrest data = seleccionPagos.get(infoTablaDetalle.getPagoActual() - 1);
                        mensaje.append(" ¿ Esta seguro que desea realizar el pago del crédito ");
                        mensaje.append(data.colCreditoProperty().getValue());
                        mensaje.append("\n");
                        mensaje.append("por un valor de ");
                        if (infoTablaDetalle.isPago_min_sel()) {
                            mensaje.append(data.colPagoMinProperty().getValue());

                        } else if (infoTablaDetalle.isPago_total_sel()) {
                            mensaje.append(data.colSaldoTotalProperty().getValue());

                        } else if (infoTablaDetalle.isOtro_valor_sel()) {
                            mensaje.append("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getOtroValorEnt())).replace(".", ",") + "." + infoTablaDetalle.getOtroValorCent());

                        }
                        mensaje.append(" desde la ");
                        mensaje.append(infoTablaDetalle.getTipoCtaPago());
                        mensaje.append("\n");
                        mensaje.append(infoTablaDetalle.getNumctaPago());
                        mensaje.append(" ?");

                    }




                    lblconfirmar.setText(mensaje.toString());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    if (AtlasConstantes.PAGO_POR_DETALLE == infoTablaDetalle.getOrigenPago()) {
                        final String infoCredito = "Pago " + infoTablaDetalle.getPagoActual() + "/" + infoTablaDetalle.getPagoActual() + "\nCrédito " + infoTablaDetalle.getNumeroCredito();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);
                    } else if (AtlasConstantes.PAGO_POR_LOTE == infoTablaDetalle.getOrigenPago()) {
                        final infoTablaPagosPrest data = seleccionPagos.get(infoTablaDetalle.getPagoActual() - 1);
                        final String infoCredito = "Pago " + infoTablaDetalle.getPagoActual() + "/" + infoTablaDetalle.getSeleccionPagos().size() + "\nCrédito " + data.colCreditoProperty().getValue();
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
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }

            }
        });



    }

    private String validarTipoCuenta(final String Cuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(Cuenta)) {
            retorno.append("7");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(Cuenta)) {
            retorno.append("1");
        }

        return retorno.toString();
    }

    private String validarCodigoCuentaMQ(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0565");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0465");
        }
        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
