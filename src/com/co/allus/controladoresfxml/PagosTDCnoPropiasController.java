/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.pagostdc.FranquiciasTdc;
import com.co.allus.modelo.pagostdc.InfoDatosTdcPropia;
import com.co.allus.userComponent.RestrictiveTDCtextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class PagosTDCnoPropiasController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar_op;
    @FXML
    private ComboBox<String> cuenta_origen;
    @FXML
    private RestrictiveTDCtextField num_tarjeta;
    @FXML
    private ComboBox<String> tipocta_origen;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'PagosTDCnoPropias.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'PagosTDCnoPropias.fxml'.";
        assert cuenta_origen != null : "fx:id=\"cuenta_origen\" was not injected: check your FXML file 'PagosTDCnoPropias.fxml'.";
        assert num_tarjeta != null : "fx:id=\"num_tarjeta\" was not injected: check your FXML file 'PagosTDCnoPropias.fxml'.";
        assert tipocta_origen != null : "fx:id=\"tipocta_origen\" was not injected: check your FXML file 'PagosTDCnoPropias.fxml'.";

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

        final InfoDatosTdcPropia datosTdcProp = new InfoDatosTdcPropia();
        datosTdcProp.setTarjetaCredito(num_tarjeta.getText());
        datosTdcProp.setFranquicia(FranquiciasTdc.franquiciasTDC.get(num_tarjeta.getText().substring(0, 1)) == null ? "Franquicia No Soportada" : FranquiciasTdc.franquiciasTDC.get(num_tarjeta.getText().substring(0, 1)));
        datosTdcProp.setTipo_cta_origen(tipocta_origen.getSelectionModel().getSelectedItem());
        datosTdcProp.setCta_origen(cuenta_origen.getSelectionModel().getSelectedItem());
        InfoDatosTdcPropia.setInfoTdcProp(datosTdcProp);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final PagosTDCnoPropiasTrxDatosController controller = new PagosTDCnoPropiasTrxDatosController();
                controller.mostrarTDCNopropiasTrxDatos(InfoDatosTdcPropia.getInfoTdcProp());
            }
        });


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
                        final RestrictiveTDCtextField tarjetastdc = (RestrictiveTDCtextField) root.lookup("#num_tarjeta");
                        final Button buttonCont = (Button) root.lookup("#continuar_op");

                        if (tarjetastdc.getText().length() < 16 || "Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem()) || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem())) {
                            buttonCont.setDisable(true);
                        } else {
                            buttonCont.setDisable(false);
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
    void selTipocta_origen(final ActionEvent event) {
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
                        final RestrictiveTDCtextField tarjetastdc = (RestrictiveTDCtextField) root.lookup("#num_tarjeta");

                        final Button buttonCont = (Button) root.lookup("#continuar_op");
                        cuenta_origen.getSelectionModel().clearSelection();
                        final String tipoC = tipocta_origen.getSelectionModel().getSelectedItem();
                        final Cliente datosCliente = Cliente.getCliente();
                        final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();

                        final List<String> datosctas = new ArrayList<String>();
                        final ArrayList<String> listacuentas = productos.get(tipoC);
                        datosctas.add("Seleccione una cuenta");

                        if (listacuentas != null) {
                            datosctas.addAll(listacuentas);
                        }
                        cuenta_origen.setItems(FXCollections.observableArrayList(datosctas));
                        cuenta_origen.getSelectionModel().select("Seleccione una cuenta");

                        if (tarjetastdc.getText().length() < 16 || "Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem()) || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem())) {
                            buttonCont.setDisable(true);
                        } else {
                            buttonCont.setDisable(false);
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
    void tarjetaskeyTyped(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                    final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                    final RestrictiveTDCtextField tarjetastdc = (RestrictiveTDCtextField) root.lookup("#num_tarjeta");
                    final Button buttonCont = (Button) root.lookup("#continuar_op");

                    if (tarjetastdc.getText().length() < 16 || "Tipo de Cuenta".equalsIgnoreCase(tipocta_origen.getSelectionModel().getSelectedItem()) || "Seleccione una cuenta".equalsIgnoreCase(cuenta_origen.getSelectionModel().getSelectedItem())) {
                        buttonCont.setDisable(true);
                    } else {
                        buttonCont.setDisable(false);
                    }

                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });
    }

    @FXML
    void tarjetaspressed(final KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode())) {


            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                continuar_op.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(num_tarjeta, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                num_tarjeta.clear();
                num_tarjeta.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }
}
