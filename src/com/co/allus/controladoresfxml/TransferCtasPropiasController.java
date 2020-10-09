/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.transfctaprop.infoTranferenciaCtaProp;
import com.co.allus.userComponent.RestrictiveTextField;
import java.io.IOException;
import java.net.URL;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class TransferCtasPropiasController implements Initializable {

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
    private transient Button continuar_op;
    @FXML
    private transient ComboBox<String> cuenta_destino;
    @FXML
    private transient ComboBox<String> cuenta_origen;
    @FXML
    private transient ComboBox<String> tipocta_destino;
    @FXML
    private transient ComboBox<String> tipocta_origen;
    @FXML
    private transient TextField valor_transferir;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'TransferCtasPropias.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'TransferCtasPropias.fxml'.";
        assert cuenta_destino != null : "fx:id=\"cuenta_destino\" was not injected: check your FXML file 'TransferCtasPropias.fxml'.";
        assert cuenta_origen != null : "fx:id=\"cuenta_origen\" was not injected: check your FXML file 'TransferCtasPropias.fxml'.";
        assert tipocta_destino != null : "fx:id=\"tipocta_destino\" was not injected: check your FXML file 'TransferCtasPropias.fxml'.";
        assert tipocta_origen != null : "fx:id=\"tipocta_origen\" was not injected: check your FXML file 'TransferCtasPropias.fxml'.";
        assert valor_transferir != null : "fx:id=\"valor_transferir\" was not injected: check your FXML file 'TransferCtasPropias.fxml'.";
        this.location = url;
        this.resources = rb;

    }

    @FXML
    void cancel_op(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
                }
            }
        });
    }

    @FXML
    void continuar_OP(final ActionEvent event) {
        final infoTranferenciaCtaProp datosTransf = new infoTranferenciaCtaProp();
        datosTransf.setCta_origen(cuenta_origen.getSelectionModel().getSelectedItem());
        datosTransf.setTipo_cta_origen(tipocta_origen.getSelectionModel().getSelectedItem());
        datosTransf.setCta_destino(cuenta_destino.getSelectionModel().getSelectedItem());
        datosTransf.setTipo_cta_destino(tipocta_destino.getSelectionModel().getSelectedItem());
        String valor = valor_transferir.getText().replace(",", ".");
        if (valor.split("\\.").length == 2) {
            if (valor.split("\\.")[1].length() != 2) {
                valor = valor.split("\\.")[0] + "." + valor.split("\\.")[1] + "0";
            }
        }

        datosTransf.setValor_transferir(valor);
        infoTranferenciaCtaProp.setInfoTranfCtaProp(datosTransf);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final TransferCtasPropiasConfirmController controller = new TransferCtasPropiasConfirmController();
                controller.mostrarMenuConfDatos(infoTranferenciaCtaProp.getInfoTranfCtaProp());

            }
        });


    }

    @FXML
    void selCuenta_destino(final ActionEvent event) {

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                        final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                        final ComboBox<String> tipocta_destino = (ComboBox<String>) root.lookup("#tipocta_destino");
                        final ComboBox<String> cuenta_destino = (ComboBox<String>) root.lookup("#cuenta_destino");
                        final Button buttonCont = (Button) root.lookup("#continuar_op");
                        final RestrictiveTextField contop = (RestrictiveTextField) root.lookup("#valor_transferir");


                        if (!("Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem().toString())
                                || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem().toString())
                                || "Tipo de Cuenta".equalsIgnoreCase(tipocta_destino.getSelectionModel().getSelectedItem().toString())
                                || "Seleccione una cuenta".equalsIgnoreCase(cuenta_destino.getSelectionModel().getSelectedItem().toString())) && !contop.getText().isEmpty()) {
                            buttonCont.setDisable(false);
                        } else {
                            buttonCont.setDisable(true);
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
    void selCuenta_origen(final ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                        final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                        final ComboBox<String> tipocta_destino = (ComboBox<String>) root.lookup("#tipocta_destino");
                        final ComboBox<String> cuenta_destino = (ComboBox<String>) root.lookup("#cuenta_destino");
                        final Button buttonCont = (Button) root.lookup("#continuar_op");
                        final RestrictiveTextField contop = (RestrictiveTextField) root.lookup("#valor_transferir");
                        if (!"Tipo de Cuenta".equalsIgnoreCase(tipocta_destino.getSelectionModel().getSelectedItem())) {
                            if (cuenta_origen.getSelectionModel().getSelectedItem().equalsIgnoreCase(cuenta_destino.getSelectionModel().getSelectedItem())) {
                                final String tipoC = tipocta_destino.getSelectionModel().getSelectedItem();
                                final String ctaori = cuenta_origen.getSelectionModel().getSelectedItem();
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
                                final List<String> datosctas = new ArrayList<String>();
                                datosctas.add("Seleccione una cuenta");
                                if (listacuentas != null) {
                                    for (int i = 0; i < listacuentas.size(); i++) {
                                        if (!ctaori.equalsIgnoreCase(listacuentas.get(i))) {
                                            datosctas.add(listacuentas.get(i));
                                        }
                                    }

                                }
                                cuenta_destino.setItems(FXCollections.observableArrayList(datosctas));
                                cuenta_destino.getSelectionModel().select("Seleccione una cuenta");
                            }

                            if ("Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem()) || "Seleccione una cuenta".equalsIgnoreCase(cuenta_destino.getSelectionModel().getSelectedItem()) || contop.getText().isEmpty()) {
                                buttonCont.setDisable(true);
                            } else {
                                buttonCont.setDisable(false);
                            }

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
    void selTipocta_destino(final ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                        final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                        final ComboBox<String> tipocta_destino = (ComboBox<String>) root.lookup("#tipocta_destino");
                        final ComboBox<String> cuenta_destino = (ComboBox<String>) root.lookup("#cuenta_destino");
                        final Button buttonCont = (Button) root.lookup("#continuar_op");
                        final String tipoC = tipocta_destino.getSelectionModel().getSelectedItem();
                        final String ctaori = cuenta_origen.getSelectionModel().getSelectedItem();
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

                        final List<String> datosctas = new ArrayList<String>();
                        datosctas.add("Seleccione una cuenta");
                        if (listacuentas != null) {
                            for (int i = 0; i < listacuentas.size(); i++) {
                                if (!ctaori.equalsIgnoreCase(listacuentas.get(i))) {
                                    datosctas.add(listacuentas.get(i));
                                }
                            }

                        }
                        cuenta_destino.setItems(FXCollections.observableArrayList(datosctas));
                        cuenta_destino.getSelectionModel().select("Seleccione una cuenta");


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
    void selTipocta_origen(final ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                        final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                        final ComboBox<String> tipocta_destino = (ComboBox<String>) root.lookup("#tipocta_destino");
                        //final ComboBox<String> cuenta_destino = (ComboBox<String>) root.lookup("#cuenta_destino");
                        final Button buttonCont = (Button) root.lookup("#continuar_op");
                        cuenta_origen.getSelectionModel().clearSelection();
                        final String tipoC = tipocta_origen.getSelectionModel().getSelectedItem();
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

                        final List<String> datosctas = new ArrayList<String>();
                        final ArrayList<String> listacuentas = productostrx.get(tipoC);
                        datosctas.add("Seleccione una cuenta");
                        if (listacuentas != null) {
                            datosctas.addAll(listacuentas);
                        }
                        cuenta_origen.setItems(FXCollections.observableArrayList(datosctas));
                        cuenta_origen.getSelectionModel().select("Seleccione una cuenta");
                        final Set<String> keySettrx = productostrx.keySet();
                        final List<String> datos = new ArrayList<String>();
                        datos.add("Tipo de Cuenta");
                        for (Iterator<String> val = keySettrx.iterator(); val.hasNext();) {
                            String cuentatipo = val.next();

                            /* validacion solo se permite agregar cuentas que no se hallan seleccionado */

                            if (!(productos.get(cuentatipo).size() == 1 && cuentatipo.equalsIgnoreCase(tipoC))) {
                                datos.add(cuentatipo);
                            }


                        }
                        tipocta_destino.setItems(FXCollections.observableArrayList(datos));
                        tipocta_destino.getSelectionModel().select("Tipo de Cuenta");

                        try {
                            pane.getChildren().remove(3);
                        } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

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
    void valkeypressed(final KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode())) {


            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                continuar_op.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_transferir, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                valor_transferir.clear();
                valor_transferir.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void valkeytyped(final KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuar_op");
                    final RestrictiveTextField contop = (RestrictiveTextField) root.lookup("#valor_transferir");

                    if (!(contop.getText().isEmpty())) {
                        if ("Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem().toString())
                                || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem().toString())
                                || "Tipo de Cuenta".equalsIgnoreCase(tipocta_destino.getSelectionModel().getSelectedItem().toString())
                                || "Seleccione una cuenta".equalsIgnoreCase(cuenta_destino.getSelectionModel().getSelectedItem().toString())) {
                            buttonCont.setDisable(true);

                        } else {
                            buttonCont.setDisable(false);
                        }

                    } else {
                        buttonCont.setDisable(true);
                    }


                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });
    }
}
