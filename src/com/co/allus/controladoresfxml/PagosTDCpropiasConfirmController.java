/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

/**
 *
 * @author alexander.lopez.o
 */
import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.hilos.EnvioTipAutFenix;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagostdc.ConfirmDatos1TDCprop;
import com.co.allus.modelo.pagostdc.ConfirmDatos2TDCprop;
import com.co.allus.modelo.pagostdc.InfoDatosTdcPropia;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class PagosTDCpropiasConfirmController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar_op;
    @FXML
    private Button continuar_op;
    @FXML
    private TableColumn<ConfirmDatos1TDCprop, String> franquicia;
    @FXML
    private HBox menu_progreso;
    @FXML
    private TableColumn<ConfirmDatos2TDCprop, String> num_cuenta;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TableView<ConfirmDatos1TDCprop> tabla_Datos1;
    @FXML
    private TableView<ConfirmDatos2TDCprop> tabla_Datos2;
    @FXML
    private TableColumn<ConfirmDatos1TDCprop, String> tarjeta_pagar;
    @FXML
    private TableColumn<ConfirmDatos2TDCprop, String> tipo_cuenta;
    @FXML
    private TableColumn<ConfirmDatos1TDCprop, String> tipo_pago;
    private transient Service<Void> servicePagosTDCprop;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert cancelar_op != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert franquicia != null : "fx:id=\"franquicia\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert num_cuenta != null : "fx:id=\"num_cuenta\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert tabla_Datos1 != null : "fx:id=\"tabla_Datos1\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert tabla_Datos2 != null : "fx:id=\"tabla_Datos2\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert tarjeta_pagar != null : "fx:id=\"tarjeta_pagar\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";
        assert tipo_pago != null : "fx:id=\"tipo_pago\" was not injected: check your FXML file 'PagosTDCnoPropiasConfirm.fxml'.";

        this.resources = resources;
        this.location = location;

        tarjeta_pagar.setCellValueFactory(new PropertyValueFactory<ConfirmDatos1TDCprop, String>("tarjeta_pagar"));
        franquicia.setCellValueFactory(new PropertyValueFactory<ConfirmDatos1TDCprop, String>("franquicia"));
        tipo_pago.setCellValueFactory(new PropertyValueFactory<ConfirmDatos1TDCprop, String>("tipo_pago"));
        tipo_cuenta.setCellValueFactory(new PropertyValueFactory<ConfirmDatos2TDCprop, String>("tipo_cuenta"));
        num_cuenta.setCellValueFactory(new PropertyValueFactory<ConfirmDatos2TDCprop, String>("num_cuenta"));

        tabla_Datos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_Datos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        progreso.setVisible(false);
    }

    public void mostrarTDCpropiasConfirmData(final InfoDatosTdcPropia infoDatos) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/PagosTDCpropiasConfirm.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final TableView<ConfirmDatos1TDCprop> tablaDatos1 = (TableView<ConfirmDatos1TDCprop>) root.lookup("#tabla_Datos1");
                        final TableView<ConfirmDatos2TDCprop> tablaDatos2 = (TableView<ConfirmDatos2TDCprop>) root.lookup("#tabla_Datos2");

                        /**
                         * se organiza el valor a pagar en el formato requerido
                         */
                        if (infoDatos.isOtro_valor()) {
                            String val = infoDatos.getValor_pago().replace(",", "");

                            if (val.contains(".")) {
                                infoDatos.setValor_pago_ent(val.split("\\.")[0]);
                                infoDatos.setValor_pago_cent(val.split("\\.")[1]);
                            } else if (val.contains(",")) {
                                infoDatos.setValor_pago_ent(val.split(",")[0]);
                                infoDatos.setValor_pago_cent(val.split(",")[1]);
                            } else {
                                infoDatos.setValor_pago_ent(val);
                                infoDatos.setValor_pago_cent("00");
                            }

                        } else {
                            infoDatos.setValor_pago("");
                            infoDatos.setValor_pago_ent("");
                            infoDatos.setValor_pago_cent("");

                        }

                        InfoDatosTdcPropia.setInfoTdcProp(infoDatos);

                        tablaDatos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        tablaDatos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        final ObservableList<ConfirmDatos1TDCprop> dataTabla1 = FXCollections.observableArrayList();
                        final ObservableList<ConfirmDatos2TDCprop> dataTabla2 = FXCollections.observableArrayList();

                        // se pinta deacuerdo a la seleccion de tipo de pago y moneda seleccionada
                        if (InfoDatosTdcPropia.getInfoTdcProp().isPago_pesos()) {
                            if (InfoDatosTdcPropia.getInfoTdcProp().isOtro_valor()) {
                                dataTabla1.add(new ConfirmDatos1TDCprop(InfoDatosTdcPropia.getInfoTdcProp().getTarjetaCredito(), InfoDatosTdcPropia.getInfoTdcProp().getFranquicia(), "$ " + formatonum.format(Double.parseDouble(InfoDatosTdcPropia.getInfoTdcProp().getValor_pago_ent())).replace(".", ",") + "." + InfoDatosTdcPropia.getInfoTdcProp().getValor_pago_cent()));
                                tablaDatos1.getColumns().get(2).setText("Valor a Pagar\n   (Pesos)");
                            } else if (InfoDatosTdcPropia.getInfoTdcProp().isPago_minnimo()) {

                                dataTabla1.add(new ConfirmDatos1TDCprop(InfoDatosTdcPropia.getInfoTdcProp().getTarjetaCredito(), InfoDatosTdcPropia.getInfoTdcProp().getFranquicia(), "Pago Mínimo"));
                                tablaDatos1.getColumns().get(2).setText("Valor a Pagar\n   (Pesos)");
                            } else if (InfoDatosTdcPropia.getInfoTdcProp().isPago_total()) {

                                dataTabla1.add(new ConfirmDatos1TDCprop(InfoDatosTdcPropia.getInfoTdcProp().getTarjetaCredito(), InfoDatosTdcPropia.getInfoTdcProp().getFranquicia(), "Pago Total"));
                                tablaDatos1.getColumns().get(2).setText("Valor a Pagar\n   (Pesos)");
                            }

                        } else if (InfoDatosTdcPropia.getInfoTdcProp().isPago_dolares()) {

                            if (InfoDatosTdcPropia.getInfoTdcProp().isOtro_valor()) {

                                dataTabla1.add(new ConfirmDatos1TDCprop(InfoDatosTdcPropia.getInfoTdcProp().getTarjetaCredito(), InfoDatosTdcPropia.getInfoTdcProp().getFranquicia(), "$ " + formatonum.format(Double.parseDouble(InfoDatosTdcPropia.getInfoTdcProp().getValor_pago_ent())).replace(".", ",") + "." + InfoDatosTdcPropia.getInfoTdcProp().getValor_pago_cent()));
                                tablaDatos1.getColumns().get(2).setText("Valor a Pagar\n  (Dólares)");

                            } else if (InfoDatosTdcPropia.getInfoTdcProp().isPago_minnimo()) {

                                dataTabla1.add(new ConfirmDatos1TDCprop(InfoDatosTdcPropia.getInfoTdcProp().getTarjetaCredito(), InfoDatosTdcPropia.getInfoTdcProp().getFranquicia(), "Pago Mínimo"));
                                tablaDatos1.getColumns().get(2).setText("Valor a Pagar\n  (Dólares)");
                            } else if (InfoDatosTdcPropia.getInfoTdcProp().isPago_total()) {

                                dataTabla1.add(new ConfirmDatos1TDCprop(InfoDatosTdcPropia.getInfoTdcProp().getTarjetaCredito(), InfoDatosTdcPropia.getInfoTdcProp().getFranquicia(), "Pago Total"));
                                tablaDatos1.getColumns().get(2).setText("Valor a Pagar\n  (Dólares)");
                            }

                        }

                        dataTabla2.add(new ConfirmDatos2TDCprop(InfoDatosTdcPropia.getInfoTdcProp().getCta_origen(), InfoDatosTdcPropia.getInfoTdcProp().getTipo_cta_origen()));

                        tablaDatos1.setItems(dataTabla1);
                        tablaDatos2.setItems(dataTabla2);

                        /**
                         * se repinta la vista en particular
                         */
                        tablaDatos1.setLayoutX(80);
                        tablaDatos1.setLayoutY(148);
                        try {
                            pane.getChildren().remove(3);
                        } catch (Exception ex) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                        }
                        pane.setAlignment(Pos.CENTER);
                        pane.add(root, 1, 0);
                        Atlas.vista.show();
                    } catch (Exception ex) {
//                        ex.printStackTrace();
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }

                }
            });

        } catch (Exception ex) {
//            ex.printStackTrace();
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }

    }

    @FXML
    void cancel_op(final ActionEvent event) {
        try {
            continuar_Op().cancel();
        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }
        final PagosTDCpropiasController controller = new PagosTDCpropiasController();
        // Atlas.getVista().mostrarMenuPagosTDCpropia("PAGOS-TDC","");
        controller.mostrarMenuPagosTDCpropia("PAGOS-TDC", new modelSaldoTarjeta("", "", ""));
    }

    @FXML
    void continuar_OP(final ActionEvent event) {
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono PagosTDC propias" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo PagosTDC no propias" + "##" + obtenerHoraActual());
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

    private String validarCodigoCuentaMQ(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("9529");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("9429");
        }
        //para mas validaciones
        return retorno.toString();
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

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }

    public Service<Void> continuar_Op() {
        servicePagosTDCprop = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String RespuestaPagosTdcprop = new String();
                        final StringBuilder tramaPagoTDCnoprop = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final InfoDatosTdcPropia infotdcnoprop = InfoDatosTdcPropia.getInfoTdcProp();
                        // final infoTranferenciaCtaProp infotransf = infoTranferenciaCtaProp.getInfoTranfCtaProp();

                        final Runnable aFenix = new EnvioTipAutFenix(cliente.getCodemp(), "006");
                        final Thread aFen = new Thread(aFenix);
                        aFen.start();

                        final Date fecha = new Date();
                        tramaPagoTDCnoprop.append("900,015,");
                        tramaPagoTDCnoprop.append(cliente.getRefid());
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(validarCodigoCuentaMQ(infotdcnoprop.getTipo_cta_origen()));
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(cliente.getId_cliente());
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(formatoFecha.format(fecha));
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(formatoHora.format(fecha));
                        tramaPagoTDCnoprop.append(",");

                        if (infotdcnoprop.isPago_pesos()) {
                            tramaPagoTDCnoprop.append("0");
                        } else if (infotdcnoprop.isPago_dolares()) {
                            tramaPagoTDCnoprop.append("1");
                        }
                        tramaPagoTDCnoprop.append(",");
                        if (infotdcnoprop.isOtro_valor()) {
                            tramaPagoTDCnoprop.append("3");
                        } else if (infotdcnoprop.isPago_minnimo()) {
                            tramaPagoTDCnoprop.append("1");

                        } else if (infotdcnoprop.isPago_total()) {
                            tramaPagoTDCnoprop.append("2");
                        }
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(StringUtilities.eliminarCerosLeft(infotdcnoprop.getValor_pago_ent()));
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(infotdcnoprop.getValor_pago_cent());
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(infotdcnoprop.getTarjetaCredito());
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(validarTipoCuenta(infotdcnoprop.getTipo_cta_origen()));
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(infotdcnoprop.getCta_origen());
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(cliente.getContraseña());
                        tramaPagoTDCnoprop.append(",");
                        tramaPagoTDCnoprop.append(cliente.getTokenOauth());
                        tramaPagoTDCnoprop.append(",~");

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ PagoTDC  propias = " + "##" + obtenerHoraActual());
                            //System.out.println(tramaPagoTDCprop.toString());
                            //RespuestaPagosTdcprop = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, tramaPagoTDCnoprop.toString());
                            RespuestaPagosTdcprop = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaPagoTDCnoprop.toString());
                            // RespuestaTrasnfin = "900,015,000,1245456,12520035,PAGO EXITOSO,~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ PagoTDC  propias = " + RespuestaPagosTdcprop + "##" + obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ pagotdc propias = " + "##" + obtenerHoraActual());
                                // RespuestaPagosTdcprop = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, tramaPagoTDCnoprop.toString());
                                RespuestaPagosTdcprop = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaPagoTDCnoprop.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + RespuestaPagosTdcprop + "##" + obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TDC_PROPIA);
                                        cancel();
                                        // continuar_Op.cancel();
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }
                        }

                        if ("000".equals(RespuestaPagosTdcprop.split(",")[2])) {
                            final Date Fechapago = new Date();
                            infotdcnoprop.setComprobante(StringUtilities.eliminarCerosLeft(RespuestaPagosTdcprop.split(",")[3]));
                            infotdcnoprop.setFechaPago(formatoFechaPago.format(Fechapago));
                            infotdcnoprop.setValor_pago_ent(RespuestaPagosTdcprop.split(",")[4].substring(0, RespuestaPagosTdcprop.split(",")[4].length() - 2));
                            infotdcnoprop.setValor_pago_cent(RespuestaPagosTdcprop.split(",")[4].substring(RespuestaPagosTdcprop.split(",")[4].length() - 2, RespuestaPagosTdcprop.split(",")[4].length()));
                            // }

                            InfoDatosTdcPropia.setInfoTdcProp(infotdcnoprop);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final PagosTDCpropiasTrxFinController controller = new PagosTDCpropiasTrxFinController();
                                    controller.mostrarTrxPagoTDCpropFin(InfoDatosTdcPropia.getInfoTdcProp());
                                }
                            });

                        } else {

                            final String coderror = RespuestaPagosTdcprop.split(",")[2];
                            final String mensaje = RespuestaPagosTdcprop.split(",")[3];

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje.trim(), coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TDC_PROPIA);
                                    cancel();
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

        return servicePagosTDCprop;
    }
}
