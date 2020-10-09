/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.pagosaterceros.ModelTablaInfoPago;
import com.co.allus.modelo.pagosaterceros.convenio;
import com.co.allus.modelo.pagosaterceros.infoPago;
import com.co.allus.modelo.pagosaterceros.valorApagar;
import com.co.allus.userComponent.RestrictiveTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class PagosTercerosDatosPago2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private transient ResourceBundle resources;
    @FXML
    private transient URL location;
    @FXML
    private transient Button cancelar_op;
    @FXML
    private transient Button continuar_op;
    @FXML
    private transient Label mensaje_valor;
    @FXML
    private transient TableColumn<ModelTablaInfoPago, String> num_cuenta;
    @FXML
    private transient VBox opciones;
    @FXML
    private transient TableColumn<ModelTablaInfoPago, String> ref_pago;
    @FXML
    private transient TableView<ModelTablaInfoPago> tabla_datos;
    @FXML
    private transient TableColumn<ModelTablaInfoPago, String> tipo_cuenta;
    @FXML
    private transient Label titulo_conv;
    @FXML
    private transient Label titulo_pagos;
    @FXML
    private transient TableColumn<valorApagar, String> valor_fijo;
    @FXML
    private transient TextField valor_pagar;
    @FXML
    private transient HBox menu_progreso;
    @FXML
    private transient ProgressBar progreso;
    @FXML
    private transient TableView<valorApagar> tabla_Valor;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        assert cancelar_op != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert mensaje_valor != null : "fx:id=\"mensaje_valor\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert num_cuenta != null : "fx:id=\"num_cuenta\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert opciones != null : "fx:id=\"opciones\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert ref_pago != null : "fx:id=\"ref_pago\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert titulo_conv != null : "fx:id=\"titulo_conv\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert titulo_pagos != null : "fx:id=\"titulo_pagos\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert valor_fijo != null : "fx:id=\"valor_fijo\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert valor_pagar != null : "fx:id=\"valor_pagar\" was not injected: check your FXML file 'PagosTerceroDatospago2.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosTercerosDatospago2.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'PagosTercerosDatospago2.fxml'.";
        assert tabla_Valor != null : "fx:id=\"tablaValor\" was not injected: check your FXML file 'PagosTercerosDatospago2.fxml'.";

        ref_pago.setCellValueFactory(new PropertyValueFactory<ModelTablaInfoPago, String>("referenciaPago"));
        tipo_cuenta.setCellValueFactory(new PropertyValueFactory<ModelTablaInfoPago, String>("tipoCuenta"));
        num_cuenta.setCellValueFactory(new PropertyValueFactory<ModelTablaInfoPago, String>("cuentaDebitar"));
        valor_fijo.setCellValueFactory(new PropertyValueFactory<valorApagar, String>("val_pagar"));

        this.resources = resources;
        this.location = location;
        valor_pagar.alignmentProperty().set(Pos.CENTER);
    }

    @FXML
    void cancel_op(final ActionEvent event) {
        Atlas.getVista().mostrarPanePagos("PAGOS A TERCEROS");
    }

    @FXML
    void continuar_OP(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final TableView<valorApagar> valor_pagar = (TableView<valorApagar>) root.lookup("#tabla_Valor");
                    final RestrictiveTextField textVal_pagar = (RestrictiveTextField) root.lookup("#valor_pagar");

                    if (textVal_pagar.isVisible()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                final PagosTercerosConfirmDatosController controller = new PagosTercerosConfirmDatosController();
                                controller.mostrarMenuConfDatos(convenio.getConvenio(), infoPago.getInfoPago(), textVal_pagar.getText().replace(",", "."));
                            }
                        });


                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                final PagosTercerosConfirmDatosController controller = new PagosTercerosConfirmDatosController();
                                controller.mostrarMenuConfDatos(convenio.getConvenio(), infoPago.getInfoPago(), valor_pagar.getItems().get(0).getVal_pagar().replace("$", ""));
                            }
                        });
                    }

                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });

    }

    @FXML
    void contop_keypress(KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode())) {

            if (valor_pagar.isVisible()) {
                if (!(event.getCode().impl_getChar().trim().equals(""))) {
                    continuar_op.setDisable(false);
                } else {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_pagar, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_pagar.clear();
                    valor_pagar.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                }
            }

        }


    }

    @FXML
    void contop_keyevent(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuar_op");
                    final RestrictiveTextField contop = (RestrictiveTextField) root.lookup("#valor_pagar");

                    if (contop.isVisible()) {
                        if (!(contop.getText().isEmpty())) {
                            buttonCont.setDisable(false);
                        } else {
                            buttonCont.setDisable(true);
                        }
                    }

                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });


    }
}
