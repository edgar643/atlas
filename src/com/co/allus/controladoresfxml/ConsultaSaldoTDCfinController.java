/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.corehipotecario.infoTablaSaldoH;
import com.co.allus.modelo.saldostdc.DatosSaldoTdc;
import com.co.allus.modelo.saldostdc.infoTablaSaldoTDC;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaSaldoTDCfinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button gotoPagar;
    @FXML
    private TableView<infoTablaSaldoTDC> tablaDatosSaldoTDC;
    @FXML
    private Button terminar_trx;
    @FXML
    private TableColumn<infoTablaSaldoTDC, String> descripcion;
    @FXML
    private TableColumn<infoTablaSaldoTDC, String> informacion;
    private static modelSaldoTarjeta tarjetaSeleccionada;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha2 = new SimpleDateFormat("ddMMyy", Locale.getDefault());
    // private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    // private transient final SimpleDateFormat formatoFechaFact = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @FXML
    void PagarTarjeta(ActionEvent event) {

        PagosTDCpropiasController controller = new PagosTDCpropiasController();
        controller.mostrarMenuPagosTDCpropia("PAGOS-TDC", getTarjetaSeleccionada());
        // Atlas.getVista().mostrarMenuPagosTDCpropia("PAGOS-TDC", getTarjetaSeleccionada());

    }

    @FXML
    void aceptar(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    gestorDoc.imprimir("Se presento un error inesperado en la aplicacion --->" + ex.toString());
                }
            }
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'ConsultaSaldoTDCfin.fxml'.";
        assert gotoPagar != null : "fx:id=\"gotoPagar\" was not injected: check your FXML file 'ConsultaSaldoTDCfin.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'ConsultaSaldoTDCfin.fxml'.";
        assert tablaDatosSaldoTDC != null : "fx:id=\"tablaDatosSaldoTDC\" was not injected: check your FXML file 'ConsultaSaldoTDCfin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ConsultaSaldoTDCfin.fxml'.";

        this.resources = rb;
        this.location = url;
        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaSaldoTDC, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<infoTablaSaldoTDC, String>("informacion"));

        final Cliente datos = Cliente.getCliente();
        final HashMap<String, ArrayList<String>> productos = datos.getProductos();

        if (productos.containsKey(CodigoProductos.CUENTA_AHORROS) || productos.containsKey(CodigoProductos.CUENTA_CORRIENTE)) {
            gotoPagar.setDisable(false);

        } else {
            gotoPagar.setDisable(true);
            gotoPagar.setTooltip(new Tooltip("Cliente No Posee Cuentas Deposito"));
        }

    }
    
    private static String enmascararNumero(String numero, int digitosVisibles) {
       int puntoCorte = numero.length() - digitosVisibles ;
       String salida ="**********"+ numero.substring(puntoCorte);
       return salida;
   }

    public void mostrarSaldoTDCfin(final DatosSaldoTdc Saldotarjeta, final modelSaldoTarjeta tarjeta) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaSaldoTDCfin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final Button irApagar = (Button) root.lookup("#gotoPagar");
                    final TableView<infoTablaSaldoH> tablaDatos = (TableView<infoTablaSaldoH>) root.lookup("#tablaDatosSaldoTDC");
                    final Label cliente = (Label) frameGnral.lookup("#cliente");
                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<infoTablaSaldoH> dataTabla = FXCollections.observableArrayList();
                    tablaDatos.setItems(dataTabla);
                    final String info = cliente.getText() + "\nNúmero de Tarjeta : " + enmascararNumero(tarjeta.getNumeroTarjeta(), 6) + " \nTipo de Tarjeta : " + tarjeta.getTipoTarjeta();
                    //final String info = cliente.getText() + "\nNúmero de Tarjeta : " + tarjeta.getNumeroTarjeta() + " \nTipo de Tarjeta : " + tarjeta.getTipoTarjeta();
                    //String info = cliente.getText() + "\n" + StringUtilities.rellenarDato(" ", setScale.intValue(), " ") + "C.C: " + datosCliente.getId_cliente();
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);
                    cliente.setText("");
                    cliente.setText(info.trim());
                    //cliente.setTextAlignment(TextAlignment.JUSTIFY);
                    setTarjetaSeleccionada(tarjeta);

                    // se agregan los datos a la tabla
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_MINIMO_PESOS, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getPagoMinPesos().substring(0, Saldotarjeta.getPagoMinPesos().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getPagoMinPesos().substring(Saldotarjeta.getPagoMinPesos().length() - 2, Saldotarjeta.getPagoMinPesos().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_MINIMO_PESOS, Saldotarjeta.getPagoMinPesos()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_MINIMO_DOLARES, "USD " + formatonum.format(Double.parseDouble(Saldotarjeta.getPagoMinDolares().substring(0, Saldotarjeta.getPagoMinDolares().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getPagoMinDolares().substring(Saldotarjeta.getPagoMinDolares().length() - 2, Saldotarjeta.getPagoMinDolares().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_MINIMO_DOLARES, Saldotarjeta.getPagoMinDolares()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_TOTAL_EN_PESOS, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getPagoTotPesos().substring(0, Saldotarjeta.getPagoTotPesos().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getPagoTotPesos().substring(Saldotarjeta.getPagoTotPesos().length() - 2, Saldotarjeta.getPagoTotPesos().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_TOTAL_EN_PESOS, Saldotarjeta.getPagoTotPesos()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_TOTAL_EN_DOLARES, "USD " + formatonum.format(Double.parseDouble(Saldotarjeta.getPagoTotDolares().substring(0, Saldotarjeta.getPagoTotDolares().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getPagoTotDolares().substring(Saldotarjeta.getPagoTotDolares().length() - 2, Saldotarjeta.getPagoTotDolares().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_TOTAL_EN_DOLARES, Saldotarjeta.getPagoTotDolares()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_MINIMO_ALTERNATIVO, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getPagoMinimoAlternativo().substring(0, Saldotarjeta.getPagoMinimoAlternativo().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getPagoMinimoAlternativo().substring(Saldotarjeta.getPagoMinimoAlternativo().length() - 2, Saldotarjeta.getPagoMinimoAlternativo().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGO_MINIMO_ALTERNATIVO, Saldotarjeta.getPagoMinimoAlternativo()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.CUPO_DISPONIBLE_AVANCES, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getCupoDispAvances().substring(0, Saldotarjeta.getCupoDispAvances().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getCupoDispAvances().substring(Saldotarjeta.getCupoDispAvances().length() - 2, Saldotarjeta.getCupoDispAvances().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.CUPO_DISPONIBLE_AVANCES, Saldotarjeta.getCupoDispAvances()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.CUPO_DISPONIBLE_TOTAL, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getCupoDispTotal().substring(0, Saldotarjeta.getCupoDispTotal().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getCupoDispTotal().substring(Saldotarjeta.getCupoDispTotal().length() - 2, Saldotarjeta.getCupoDispTotal().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.CUPO_DISPONIBLE_TOTAL, Saldotarjeta.getCupoDispTotal()));
                    }

                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.CUPO_TOTAL, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getCupoTotal().substring(0, Saldotarjeta.getCupoTotal().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getCupoTotal().substring(Saldotarjeta.getCupoTotal().length() - 2, Saldotarjeta.getCupoTotal().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.CUPO_TOTAL, Saldotarjeta.getCupoTotal()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.DEUDA_FECHA_PESOS, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getDeudaFechaPesos().substring(0, Saldotarjeta.getDeudaFechaPesos().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getDeudaFechaPesos().substring(Saldotarjeta.getDeudaFechaPesos().length() - 2, Saldotarjeta.getDeudaFechaPesos().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.DEUDA_FECHA_PESOS, Saldotarjeta.getDeudaFechaPesos()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.DEUDA_FECHA_DOLARES, "USD " + formatonum.format(Double.parseDouble(Saldotarjeta.getDeudaFechaDolares().substring(0, Saldotarjeta.getDeudaFechaDolares().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getDeudaFechaDolares().substring(Saldotarjeta.getDeudaFechaDolares().length() - 2, Saldotarjeta.getDeudaFechaDolares().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.DEUDA_FECHA_DOLARES, Saldotarjeta.getDeudaFechaDolares()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.AUT_PENDIENTES_PESOS, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getAutPendientesPesos().substring(0, Saldotarjeta.getAutPendientesPesos().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getAutPendientesPesos().substring(Saldotarjeta.getAutPendientesPesos().length() - 2, Saldotarjeta.getAutPendientesPesos().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.AUT_PENDIENTES_PESOS, Saldotarjeta.getAutPendientesPesos()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.AUT_PENDIENTES_DOLARES, "USD " + formatonum.format(Double.parseDouble(Saldotarjeta.getAutPendientesDolares().substring(0, Saldotarjeta.getAutPendientesDolares().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getAutPendientesDolares().substring(Saldotarjeta.getAutPendientesDolares().length() - 2, Saldotarjeta.getAutPendientesDolares().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.AUT_PENDIENTES_DOLARES, Saldotarjeta.getAutPendientesDolares()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGOS_PENDIENTES_PESOS, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getPagosPendientesPesos().substring(0, Saldotarjeta.getPagosPendientesPesos().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getPagosPendientesPesos().substring(Saldotarjeta.getPagosPendientesPesos().length() - 2, Saldotarjeta.getPagosPendientesPesos().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGOS_PENDIENTES_PESOS, Saldotarjeta.getPagosPendientesPesos()));
                    }

                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGOS_PENDIENTES_DOLARES, "USD " + formatonum.format(Double.parseDouble(Saldotarjeta.getPagosPendientesDolares().substring(0, Saldotarjeta.getPagosPendientesDolares().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getPagosPendientesDolares().substring(Saldotarjeta.getPagosPendientesDolares().length() - 2, Saldotarjeta.getPagosPendientesDolares().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.PAGOS_PENDIENTES_DOLARES, Saldotarjeta.getPagosPendientesDolares()));
                    }

                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.FECHA_LIMITE_PAGO, formatoFecha.format(formatoFecha2.parse(Integer.parseInt(Saldotarjeta.getFechalimitePago()) + ""))));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.FECHA_LIMITE_PAGO, Saldotarjeta.getFechalimitePago()));
                    }

                    dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.CICLO_FACTURACION, Saldotarjeta.getCicloFacturacion()));

                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.FECHA_FACTURACION, formatoFecha.format(formatoFecha2.parse(Integer.parseInt(Saldotarjeta.getFechaFacturacion()) + ""))));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.FECHA_FACTURACION, Saldotarjeta.getFechaFacturacion()));
                    }
                    try {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.SOBRE_CUPO, "$" + formatonum.format(Double.parseDouble(Saldotarjeta.getSobreCupo().substring(0, Saldotarjeta.getSobreCupo().length() - 2))).replace(".", ",") + "." + Saldotarjeta.getSobreCupo().substring(Saldotarjeta.getSobreCupo().length() - 2, Saldotarjeta.getSobreCupo().length())));
                    } catch (Exception ex) {
                        dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.SOBRE_CUPO, Saldotarjeta.getSobreCupo()));
                    }
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.BLOQUEO_TARJETA, Saldotarjeta.getBloqueoTarjeta()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.NUM_CUENTA,enmascararNumero(Saldotarjeta.getNumCuenta(), 6)));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.ESTADO_CUENTA_1, Saldotarjeta.getEstadoCuenta1()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.ESTADO_CUENTA_2, Saldotarjeta.getEstadoCuenta2()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoTdc.ESTADO_CUENTA_3, Saldotarjeta.getEstadoCuenta3()));


                    tablaDatos.setItems(dataTabla);

                    /**
                     *
                     */
                    final DropShadow shadow = new DropShadow();

                    continuar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.HAND);
                                    continuar_op.setEffect(shadow);
                                }
                            });

                    continuar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.DEFAULT);
                                    continuar_op.setEffect(null);

                                }
                            });

                    irApagar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.HAND);
                                    continuar_op.setEffect(shadow);
                                }
                            });

                    irApagar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.DEFAULT);
                                    continuar_op.setEffect(null);

                                }
                            });

                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que el  pago se halla realizado "
                            + "correctamente , por favor no volver a intertalo e informar al area tecnica",
                            "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_TDC);

                }

            }
        });



    }

    public modelSaldoTarjeta getTarjetaSeleccionada() {
        return tarjetaSeleccionada;
    }

    public void setTarjetaSeleccionada(modelSaldoTarjeta tarjetaSeleccionada) {
        this.tarjetaSeleccionada = tarjetaSeleccionada;
    }
}
