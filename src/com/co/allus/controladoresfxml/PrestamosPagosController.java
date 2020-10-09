/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.pagoprestamos.DatosPagoPrestamo;
import com.co.allus.modelo.pagoprestamos.infoTablaPagosPrest;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class PrestamosPagosController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelarPago;
    @FXML
    private Button cancelarRestantes;
    @FXML
    private Button continuarOP;
    @FXML
    private Label lblpagominimo;
    @FXML
    private Label lblpagototal;
    @FXML
    private RadioButton otroVal;
    @FXML
    private RadioButton pagoMin;
    @FXML
    private RadioButton pagoTotal;
    @FXML
    private ComboBox<String> selCuenta;
    @FXML
    private TextField tfotrovalor;
    @FXML
    private ComboBox<String> tipo_cuenta;
    @FXML
    private Label lblpago;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        assert cancelarPago != null : "fx:id=\"cancelarPago\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert cancelarRestantes != null : "fx:id=\"cancelarRestantes\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert lblpagominimo != null : "fx:id=\"lblpagominimo\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert lblpagototal != null : "fx:id=\"lblpagototal\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert otroVal != null : "fx:id=\"otroVal\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert pagoMin != null : "fx:id=\"pagoMin\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert pagoTotal != null : "fx:id=\"pagoTotal\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert selCuenta != null : "fx:id=\"selCuenta\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert tfotrovalor != null : "fx:id=\"tfotrovalor\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        assert lblpago != null : "fx:id=\"lblpago\" was not injected: check your FXML file 'PrestamosPagos.fxml'.";
        this.resources = rb;
        this.location = url;

    }

    @FXML
    void continuarOP(final ActionEvent event) {

        DatosPagoPrestamo datosTablaDetalle = DatosPagoPrestamo.getDatosTablaDetalle();

        if (pagoMin.isSelected()) {
            datosTablaDetalle.setOtro_valor_sel(false);
            datosTablaDetalle.setPago_min_sel(true);
            datosTablaDetalle.setPago_total_sel(false);
        } else if (pagoTotal.isSelected()) {
            datosTablaDetalle.setOtro_valor_sel(false);
            datosTablaDetalle.setPago_min_sel(false);
            datosTablaDetalle.setPago_total_sel(true);

        } else if (otroVal.isSelected()) {
            datosTablaDetalle.setOtro_valor_sel(true);
            datosTablaDetalle.setPago_min_sel(false);
            datosTablaDetalle.setPago_total_sel(false);

            String valor = tfotrovalor.getText().replace(",", ".");
            if (valor.split("\\.").length == 2) {
                if (valor.split("\\.")[1].length() != 2) {
                    valor = valor.split("\\.")[0] + "." + valor.split("\\.")[1] + "0";
                }
            }
            datosTablaDetalle.setOtroValor(valor);

            valor = valor.replace(",", "");
            if (valor.contains(".")) {
                datosTablaDetalle.setOtroValorEnt(valor.split("\\.")[0]);
                datosTablaDetalle.setOtroValorCent(valor.split("\\.")[1]);
            } else if (valor.contains(",")) {
                datosTablaDetalle.setOtroValorEnt(valor.split(",")[0]);
                datosTablaDetalle.setOtroValorCent(valor.split(",")[1]);
            } else {
                datosTablaDetalle.setOtroValorEnt(valor);
                datosTablaDetalle.setOtroValorCent("00");
            }


        }
        datosTablaDetalle.setTipoCtaPago(tipo_cuenta.getSelectionModel().getSelectedItem());
        datosTablaDetalle.setNumctaPago(selCuenta.getSelectionModel().getSelectedItem());
        DatosPagoPrestamo.setDatosTablaDetalle(datosTablaDetalle);
        final PrestamosConfirmarController controller = new PrestamosConfirmarController();
        controller.mostrarPagoPrestamosConfirm(DatosPagoPrestamo.getDatosTablaDetalle());

    }

    @FXML
    void cancelPagoActual(final ActionEvent event) {
        DatosPagoPrestamo datosTablaDetalle = DatosPagoPrestamo.getDatosTablaDetalle();
        if (AtlasConstantes.PAGO_POR_DETALLE == datosTablaDetalle.getOrigenPago()) {

            DatosPagoPrestamo datosCero = new DatosPagoPrestamo();
            DatosPagoPrestamo.setDatosTablaDetalle(datosCero);
            MarcoPrincipalController.newConsultaPrestamo = true;
            final PrestamosController controller = new PrestamosController();
            controller.mostrarDatosPrestamos();

        } else if (AtlasConstantes.PAGO_POR_LOTE == datosTablaDetalle.getOrigenPago()) {
            if (datosTablaDetalle.getPagoActual() == datosTablaDetalle.getSeleccionPagos().size()) {
                DatosPagoPrestamo datosCero = new DatosPagoPrestamo();
                DatosPagoPrestamo.setDatosTablaDetalle(datosCero);
                MarcoPrincipalController.newConsultaPrestamo = true;
                final PrestamosController controller = new PrestamosController();
                controller.mostrarDatosPrestamos();

            } else {
                datosTablaDetalle.setPagoActual(datosTablaDetalle.getPagoActual() + 1);
                DatosPagoPrestamo.setDatosTablaDetalle(datosTablaDetalle);
                mostrarPagoPrestamos(DatosPagoPrestamo.getDatosTablaDetalle(), AtlasConstantes.PAGO_POR_LOTE);

            }

        }

    }

    @FXML
    void canceltodosPagos(final ActionEvent event) {

        final DatosPagoPrestamo datosCero = new DatosPagoPrestamo();
        DatosPagoPrestamo.setDatosTablaDetalle(datosCero);
        MarcoPrincipalController.newConsultaPrestamo = true;
        final PrestamosController controller = new PrestamosController();
        controller.mostrarDatosPrestamos();

    }

    public void mostrarPagoPrestamos(final DatosPagoPrestamo infoTablaDetalle, final int origen) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PrestamosPagos.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelarPago = (Button) root.lookup("#cancelarPago");
                    final Button cancelarRestantes = (Button) root.lookup("#cancelarRestantes");
                    final Button continuarOP = (Button) root.lookup("#continuarOP");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final Label lblpagominimo = (Label) root.lookup("#lblpagominimo");
                    final Label lblpagototal = (Label) root.lookup("#lblpagototal");
                    final Label lblCreditoAct = (Label) root.lookup("#lblpago");
                    final RadioButton rPagoMin = (RadioButton) root.lookup("#pagoMin");
                    final RadioButton rPagoTotal = (RadioButton) root.lookup("#pagoTotal");

                    List<infoTablaPagosPrest> seleccionPagos = infoTablaDetalle.getSeleccionPagos();

                    if (AtlasConstantes.PAGO_POR_DETALLE == origen) {
                        lblpagominimo.setText("$" + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoCuotaMinEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoCuotaMinCent());
                        lblpagototal.setText("$" + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoTotalEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoTotalCent());

                        if ("0.00".equals(formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoCuotaMinEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoCuotaMinCent())) {
                            rPagoMin.setDisable(true);
                        }
                        if ("0.00".equals(formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoTotalEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoTotalCent())) {
                            rPagoTotal.setDisable(true);
                        }

                    } else if (AtlasConstantes.PAGO_POR_LOTE == origen) {
                        final infoTablaPagosPrest data = seleccionPagos.get(infoTablaDetalle.getPagoActual() - 1);
                        lblpagominimo.setText(data.colPagoMinProperty().getValue());
                        lblpagototal.setText(data.colSaldoTotalProperty().getValue());

                        if ("0.00".equals(data.colPagoMinProperty().getValue().replace("$", "").trim())) {
                            rPagoMin.setDisable(true);
                        }
                        if ("0.00".equals(data.colSaldoTotalProperty().getValue().replace("$", "").trim())) {
                            rPagoTotal.setDisable(true);
                        }
                    }


                    final ComboBox<String> selCuenta = (ComboBox<String>) root.lookup("#selCuenta");
                    final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");
                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    selCuenta.setItems(emptyList);
                    tipo_cuenta.setItems(emptyList);

                    final List<String> datosT = new ArrayList<String>();
                    final List<String> datosC = new ArrayList<String>();

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    if (AtlasConstantes.PAGO_POR_DETALLE == origen) {
                        final String infoCredito = "Pago " + infoTablaDetalle.getPagoActual() + "/" + infoTablaDetalle.getPagoActual() + "\nCrédito " + infoTablaDetalle.getNumeroCredito();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);
                    } else if (AtlasConstantes.PAGO_POR_LOTE == origen) {
                        final infoTablaPagosPrest data = seleccionPagos.get(infoTablaDetalle.getPagoActual() - 1);
                        final String infoCredito = "Pago " + infoTablaDetalle.getPagoActual() + "/" + infoTablaDetalle.getSeleccionPagos().size() + "\nCrédito " + data.colCreditoProperty().getValue();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);

                    }



                    final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                    final Set<String> keySet = productos.keySet();

                    datosT.add("Tipo de Cuenta");

                    for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        String cuentatipo = val.next();

                        /* validacion solo se pérmite cuenta ahorros y corriente */

                        if (CodigoProductos.CUENTA_AHORROS.equals(cuentatipo) || CodigoProductos.CUENTA_CORRIENTE.equals(cuentatipo)) {

                            datosT.add(cuentatipo);
                        }
                    }

                    datosC.add("Seleccione una cuenta");

                    tipo_cuenta.setItems(FXCollections.observableArrayList(datosT));
                    tipo_cuenta.getSelectionModel().select("Tipo de Cuenta");
                    selCuenta.setItems(FXCollections.observableArrayList(datosC));
                    selCuenta.getSelectionModel().select("Seleccione una cuenta");


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

                    cancelarPago.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarPago.setCursor(Cursor.HAND);
                                    cancelarPago.setEffect(shadow);
                                }
                            });

                    cancelarPago.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarPago.setCursor(Cursor.DEFAULT);
                                    cancelarPago.setEffect(null);
                                }
                            });

                    cancelarRestantes.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarRestantes.setCursor(Cursor.HAND);
                                    cancelarRestantes.setEffect(shadow);
                                }
                            });

                    cancelarRestantes.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarRestantes.setCursor(Cursor.DEFAULT);
                                    cancelarRestantes.setEffect(null);
                                }
                            });



                    continuarOP.setDisable(true);
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


                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });


    }

    @FXML
    void selTipocta(final ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");
                        final ComboBox<String> selCuenta = (ComboBox<String>) root.lookup("#selCuenta");
                        final Button continuarOP = (Button) root.lookup("#continuarOP");


                        final String tipoC = tipo_cuenta.getSelectionModel().getSelectedItem();
                        final String ctadestino = selCuenta.getSelectionModel().getSelectedItem();
                        final Cliente datosCliente = Cliente.getCliente();
                        final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                        final HashMap<String, ArrayList<String>> productostrx = new HashMap<String, ArrayList<String>>();
                        final Set<String> keySet = productos.keySet();

                        for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                            final String tipocuenta = val.next();

                            if (CodigoProductos.CUENTA_AHORROS.equals(tipocuenta) || CodigoProductos.CUENTA_CORRIENTE.equals(tipocuenta)) {
                                final ArrayList<String> cuentas = productos.get(tipocuenta);
                                productostrx.put(tipocuenta, cuentas);
                            }
                        }

                        final ArrayList<String> listacuentas = productostrx.get(tipoC);
                        final List<String> datosC = new ArrayList<String>();
                        datosC.add("Seleccione una cuenta");
                        if (listacuentas != null) {
                            for (int i = 0; i < listacuentas.size(); i++) {
                                if (!ctadestino.equalsIgnoreCase(listacuentas.get(i))) {
                                    datosC.add(listacuentas.get(i));
                                }
                            }
                        }
                        selCuenta.setItems(FXCollections.observableArrayList(datosC));
                        selCuenta.getSelectionModel().select("Seleccione una cuenta");


                        try {
                            pane.getChildren().remove(3);
                        } catch (Exception e) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                        }

                        pane.setAlignment(Pos.CENTER_LEFT);
                        pane.add(root, 1, 0);
                        Atlas.vista.show();
                    } catch (Exception ex) {

                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } catch (Exception ex) {

            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }

    }

    @FXML
    void selCuenta(final ActionEvent event) {

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");
                        final ComboBox<String> selCuenta = (ComboBox<String>) root.lookup("#selCuenta");
                        final RestrictiveTextField Otroval = (RestrictiveTextField) root.lookup("#tfotrovalor");
                        final RadioButton otroValPesos = (RadioButton) root.lookup("#otroVal");
                        final RadioButton pagomin = (RadioButton) root.lookup("#pagoMin");
                        final RadioButton pagoTotal = (RadioButton) root.lookup("#pagoTotal");
                        final Button continuarOP = (Button) root.lookup("#continuarOP");


                        if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                                || "Seleccione una cuenta".equalsIgnoreCase(selCuenta.getSelectionModel().getSelectedItem().toString()))) {

                            if (pagomin.isSelected() || pagoTotal.isSelected() || (!(Otroval.getText().isEmpty()) && otroValPesos.isSelected())) {
                                continuarOP.setDisable(false);
                                final KeyEvent keyEvent = KeyEvent.impl_keyEvent(Otroval, " ", KeyCode.ENTER.name(), KeyCode.ENTER.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                                Otroval.fireEvent(keyEvent);
                            } else {
                                continuarOP.setDisable(true);
                            }


                        } else {
                            continuarOP.setDisable(true);
                        }



                    } catch (Exception ex) {

                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }

                }
            });
        } catch (Exception ex) {

            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
    }

    @FXML
    void selPagoMin(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion
                otroVal.setSelected(false);
                pagoTotal.setSelected(false);
                tfotrovalor.setText("");

                if (pagoMin.isSelected()) {
                    continuarOP.setDisable(false);
                } else {
                    continuarOP.setDisable(true);
                }

                if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                        || "Seleccione una cuenta".equalsIgnoreCase(selCuenta.getSelectionModel().getSelectedItem().toString()))) {
                    if (pagoMin.isSelected()) {
                        continuarOP.setDisable(false);
                    }
                } else {
                    continuarOP.setDisable(true);
                }

            }
        });
    }

    @FXML
    void selPagoTotal(ActionEvent event) {
        otroVal.setSelected(false);
        pagoMin.setSelected(false);
        tfotrovalor.setText("");

        if (pagoTotal.isSelected()) {
            continuarOP.setDisable(false);
        } else {
            continuarOP.setDisable(true);
        }

        if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                || "Seleccione una cuenta".equalsIgnoreCase(selCuenta.getSelectionModel().getSelectedItem().toString()))) {
            if (pagoTotal.isSelected()) {
                continuarOP.setDisable(false);
            }
        } else {
            continuarOP.setDisable(true);
        }
    }

    @FXML
    void selOtrovalor(ActionEvent event) {
        pagoTotal.setSelected(false);
        pagoMin.setSelected(false);
        tfotrovalor.setText("");

        if (otroVal.isSelected() && !(tfotrovalor.getText().isEmpty())) {
            continuarOP.setDisable(false);
        } else {
            continuarOP.setDisable(true);
        }

        if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                || "Seleccione una cuenta".equalsIgnoreCase(selCuenta.getSelectionModel().getSelectedItem().toString()))) {
            if (otroVal.isSelected() && !(tfotrovalor.getText().isEmpty())) {
                continuarOP.setDisable(false);
            }
        } else {
            continuarOP.setDisable(true);
        }
    }

    @FXML
    void otroValKey(KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuarOP");
                    final RestrictiveTextField Otroval = (RestrictiveTextField) root.lookup("#tfotrovalor");
                    final Label pagoTotal = (Label) root.lookup("#lblpagototal");
                    final RadioButton otroValPesos = (RadioButton) root.lookup("#otroVal");
                    if (Otroval.getText().isEmpty() && otroValPesos.isSelected()) {
                        buttonCont.setDisable(true);
                    } else if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                            || "Seleccione una cuenta".equalsIgnoreCase(selCuenta.getSelectionModel().getSelectedItem().toString()))) {
                        buttonCont.setDisable(false);

                        try {
                            String otroavalor = Otroval.getText();
                            if (".".equals(otroavalor.substring(otroavalor.length() - 1, otroavalor.length())) || ",".equals(otroavalor.substring(otroavalor.length() - 1, otroavalor.length()))) {
                                otroavalor = otroavalor + "00";
                            }

                            BigDecimal valtotal = new BigDecimal(pagoTotal.getText().replace("$", "").replace(",", "").trim());
                            BigDecimal valotro = new BigDecimal(otroavalor.replace(",", "."));

                            if (valotro.doubleValue() > valtotal.doubleValue()) {
                                buttonCont.setDisable(true);
                            } else {
                                buttonCont.setDisable(false);
                            }
                        } catch (Exception e) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                        }
                    }




                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });

    }

    @FXML
    void otroValPressed(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                continuarOP.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(tfotrovalor, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                tfotrovalor.clear();
                tfotrovalor.fireEvent(keyEvent);

            }


        }
    }
}
