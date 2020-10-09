/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.transfcrediagil.infoTransferenciaCrediagil;
import com.co.allus.userComponent.RestrictiveTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
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
public class CrediagilController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelarOP;
    @FXML
    private Button continuarOP;
    @FXML
    private ComboBox<String> cuenta_destino;
    @FXML
    private ComboBox<String> plazo;
    @FXML
    private ComboBox<String> tipo_cuenta;
    @FXML
    private TextField valor_prestamo;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'Crediagil.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'Crediagil.fxml'.";
        assert cuenta_destino != null : "fx:id=\"cuenta_destino\" was not injected: check your FXML file 'Crediagil.fxml'.";
        assert plazo != null : "fx:id=\"plazo\" was not injected: check your FXML file 'Crediagil.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'Crediagil.fxml'.";
        assert valor_prestamo != null : "fx:id=\"valor_prestamo\" was not injected: check your FXML file 'Crediagil.fxml'.";
        this.location = url;
        this.resources = rb;
    }

    @FXML
    void continuarOP(ActionEvent event) {

        final infoTransferenciaCrediagil datosTransfCredi = new infoTransferenciaCrediagil();
        datosTransfCredi.setPlazo(plazo.getSelectionModel().getSelectedItem().toString());
        datosTransfCredi.setTipo_cuenta(tipo_cuenta.getSelectionModel().getSelectedItem().toString());
        datosTransfCredi.setCuenta_destino(cuenta_destino.getSelectionModel().getSelectedItem().toString());
        // String valorpres = valor_prestamo.getText();

        String valor = valor_prestamo.getText().replace(",", ".");
        if (valor.split("\\.").length == 2) {
            if (valor.split("\\.")[1].length() != 2) {
                valor = valor.split("\\.")[0] + "." + valor.split("\\.")[1] + "0";
            }
        }

        datosTransfCredi.setValor(valor);
        infoTransferenciaCrediagil.setInfoTransfCrediagil(datosTransfCredi);
        final CrediagilConfirmarController controller = new CrediagilConfirmarController();
        controller.mostrarMenuConfDatos(infoTransferenciaCrediagil.getInfoTransfCrediagil());
    }

    @FXML
    void cancelarOP(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    gestorDoc.imprimir("Ocurrio un error inesperado en la aplicación -->" + ex.toString());
                }
            }
        });
    }

    public void mostrarTransfCrediagil() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/Crediagil.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelarOP = (Button) root.lookup("#cancelarOP");
                    final Button continuarOP = (Button) root.lookup("#continuarOP");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final ComboBox<String> plazo = (ComboBox<String>) root.lookup("#plazo");
                    final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");
                    final ComboBox<String> cuenta_destino = (ComboBox<String>) root.lookup("#cuenta_destino");
                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    plazo.setItems(emptyList);
                    tipo_cuenta.setItems(emptyList);
                    cuenta_destino.setItems(emptyList);

                    final List<String> datos = new ArrayList<String>();
                    final List<String> datos2 = new ArrayList<String>();
                    final List<String> datos3 = new ArrayList<String>();

                    final Cliente datosCliente = Cliente.getCliente();

                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                    final Set<String> keySet = productos.keySet();

                    for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        String cuentatipo = val.next();

                        /* validacion solo se pérmite cuenta ahorros y corriente */
                        if (CodigoProductos.CUENTA_AHORROS.equals(cuentatipo) || CodigoProductos.CUENTA_CORRIENTE.equals(cuentatipo)) {

                            datos2.add(cuentatipo);
                        }
                    }

                    datos.add("24");
                    //datos.add("60");
                    datos3.add("Seleccione una cuenta");

                    plazo.setItems(FXCollections.observableArrayList(datos));
                    plazo.getSelectionModel().select("24");
                    tipo_cuenta.setItems(FXCollections.observableArrayList(datos2));
                    tipo_cuenta.getSelectionModel().select("Cuenta Ahorros");
                    cuenta_destino.setItems(FXCollections.observableArrayList(datos3));
                    cuenta_destino.getSelectionModel().select("Seleccione una cuenta");

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
    void selTipocta_destino(final ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");
                        final ComboBox<String> cuenta_destino = (ComboBox<String>) root.lookup("#cuenta_destino");
                        final Button continuarOP = (Button) root.lookup("#continuarOP");
                        final RestrictiveTextField valorPrestamo = (RestrictiveTextField) root.lookup("#valor_prestamo");

                        final String tipoC = tipo_cuenta.getSelectionModel().getSelectedItem();
                        final String ctadestino = cuenta_destino.getSelectionModel().getSelectedItem();
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
                                if (!ctadestino.equalsIgnoreCase(listacuentas.get(i))) {
                                    datosctas.add(listacuentas.get(i));
                                }
                            }
                        }
                        cuenta_destino.setItems(FXCollections.observableArrayList(datosctas));
                        cuenta_destino.getSelectionModel().select("Seleccione una cuenta");

                        try {
                            pane.getChildren().remove(3);
                        } catch (Exception ex) {
                            java.util.logging.Logger.getLogger(CrediagilController.class.getName()).log(Level.SEVERE, ex.toString());

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
    void selCuenta_destino(final ActionEvent event) {

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");
                        final ComboBox<String> cuenta_destino = (ComboBox<String>) root.lookup("#cuenta_destino");
                        final Button continuarOP = (Button) root.lookup("#continuarOP");
                        final RestrictiveTextField valorPrestamo = (RestrictiveTextField) root.lookup("#valor_prestamo");

                        if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                                || "Seleccione una cuenta".equalsIgnoreCase(cuenta_destino.getSelectionModel().getSelectedItem().toString())) && !valorPrestamo.getText().isEmpty()) {
                            continuarOP.setDisable(false);
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
    void valkeypressed(final KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode())) {

            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                continuarOP.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_prestamo, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                valor_prestamo.clear();
                valor_prestamo.fireEvent(keyEvent);
                continuarOP.setDisable(true);
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
                    final Button continuarOP = (Button) root.lookup("#continuarOP");
                    final RestrictiveTextField valorPrestamo = (RestrictiveTextField) root.lookup("#valor_prestamo");

                    if (!(valorPrestamo.getText().isEmpty())) {
                        if ("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                                || "Seleccione una cuenta".equalsIgnoreCase(cuenta_destino.getSelectionModel().getSelectedItem().toString())) {
                            continuarOP.setDisable(true);
                        } else {
                            continuarOP.setDisable(false);
                        }
                    } else {
                        continuarOP.setDisable(true);
                    }

                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });
    }
}
