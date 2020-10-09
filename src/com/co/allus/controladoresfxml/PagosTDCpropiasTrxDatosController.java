/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.pagostdc.FranquiciasTdc;
import com.co.allus.modelo.pagostdc.InfoDatosTdcPropia;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import com.co.allus.userComponent.RestrictiveTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class PagosTDCpropiasTrxDatosController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private RadioButton OtroValorDolar;
    @FXML
    private RadioButton OtroValorPesos;
    @FXML
    private RadioButton PagoMinDolar;
    @FXML
    private RadioButton PagoMinPesos;
    @FXML
    private RadioButton PagoTotalDolar;
    @FXML
    private RadioButton PagoTotalPesos;
    @FXML
    private RestrictiveTextField ValorDolares;
    @FXML
    private RestrictiveTextField ValorPesos;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar_op;
    @FXML
    private VBox Box_pago_dolar;
    private String tarjetaPagar = "";
    private modelSaldoTarjeta tarjetthis;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert OtroValorDolar != null : "fx:id=\"OtroValorDolar\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert OtroValorPesos != null : "fx:id=\"OtroValorPesos\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert PagoMinDolar != null : "fx:id=\"PagoMinDolar\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert PagoMinPesos != null : "fx:id=\"PagoMinPesos\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert PagoTotalDolar != null : "fx:id=\"PagoTotalDolar\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert PagoTotalPesos != null : "fx:id=\"PagoTotalPesos\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert ValorDolares != null : "fx:id=\"ValorDolares\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert ValorPesos != null : "fx:id=\"ValorPesos\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";
        assert Box_pago_dolar != null : "fx:id=\"Box_pago_dolar\" was not injected: check your FXML file 'PagosTDCpropiasTrxDatos.fxml'.";

        this.resources = rb;
        this.location = url;
    }

    public void mostrarTDCpropiasTrxDatos(final InfoDatosTdcPropia infoDatos, final modelSaldoTarjeta TarjetaApagar) {

        this.tarjetthis = TarjetaApagar;
        //  this.tarjetaPagar=TarjetaApagar;
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/PagosTDCpropiasTrxDatos.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final VBox panel_tipo_pago_dolar = (VBox) root.lookup("#Box_pago_dolar");
                        final RestrictiveTextField ValorPesos = (RestrictiveTextField) root.lookup("#ValorPesos");
                        //Restricciones de los campos CodigoConvenio y NombreConvenio
                        // ValorPesos.setMaxLength(15);
                        // solo se permiten numeros sin ceros a la izquerda y hasta dos decimales separados por "." o ","
                        // ValorPesos.setRestrict("[1-9][0-9]*+([,\\.][0-9]{0,2})?$");
                        // ValorPesos.setIsAlphanum(false);

                        //*+++++++++++++++++++++++++++++++
                        //Restricciones de los campos CodigoConvenio y NombreConvenio
                        ValorPesos.setMaxLength(13);
                        // solo se permiten numeros sin ceros a la izquerda y hasta dos decimales separados por "." o ","
                        ValorPesos.setRestrict("[1-9][0-9]");
                        ValorPesos.setIsAlphanum(false);


                        final RestrictiveTextField ValorDolares = (RestrictiveTextField) root.lookup("#ValorDolares");
                        //Restricciones de los campos CodigoConvenio y NombreConvenio
                        //ValorDolares.setMaxLength(15);
                        // solo se permiten numeros sin ceros a la izquerda y hasta dos decimales separados por "." o ","
                        // ValorDolares.setRestrict("[1-9][0-9]*+([,\\.][0-9]{0,2})?$");
                        // ValorDolares.setIsAlphanum(false);
                        //********************************************
                        ValorDolares.setMaxLength(13);
                        // solo se permiten numeros sin ceros a la izquerda y hasta dos decimales separados por "." o ","
                        ValorDolares.setRestrict("[1-9][0-9]");
                        ValorDolares.setIsAlphanum(false);

                        //*******************************************//
                        ValorPesos.setEditable(false);
                        ValorDolares.setEditable(false);

                        if (FranquiciasTdc.VISA.equalsIgnoreCase(infoDatos.getFranquicia().trim())) {
                            panel_tipo_pago_dolar.setDisable(true);
                        }

                        pane.getChildren().remove(3);
                        pane.setAlignment(Pos.CENTER_LEFT);
                        pane.add(root, 1, 0);
                        Atlas.vista.show();
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//                        ex.printStackTrace();
                    }

                }
            });


        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//            ex.printStackTrace();
        }

    }

    @FXML
    void cancel_op(final ActionEvent event) {
        PagosTDCpropiasController controller = new PagosTDCpropiasController();
        controller.mostrarMenuPagosTDCpropia("Pago TDC Propia", this.tarjetthis);
        // Atlas.getVista().mostrarMenuPagosTDCpropia("Pago TDC Propia",this.tarjetaPagar);
    }

    @FXML
    void continuar_OP(final ActionEvent event) {

        final InfoDatosTdcPropia infoTdcProp = InfoDatosTdcPropia.getInfoTdcProp();

        if (Box_pago_dolar.isDisabled()) {
            if (OtroValorPesos.isSelected()) {
                infoTdcProp.setOtro_valor(true);
                infoTdcProp.setPago_pesos(true);
                infoTdcProp.setPago_total(false);
                infoTdcProp.setPago_minnimo(false);
                infoTdcProp.setPago_dolares(false);

                String valor = ValorPesos.getText().replace(",", ".");
                if (valor.split("\\.").length == 2) {
                    if (valor.split("\\.")[1].length() != 2) {
                        valor = valor.split("\\.")[0] + "." + valor.split("\\.")[1] + "0";
                    }
                }
                infoTdcProp.setValor_pago(valor);

            } else if (PagoTotalPesos.isSelected()) {
                infoTdcProp.setOtro_valor(false);
                infoTdcProp.setPago_pesos(true);
                infoTdcProp.setPago_total(true);
                infoTdcProp.setPago_minnimo(false);
                infoTdcProp.setPago_dolares(false);


            } else if (PagoMinPesos.isSelected()) {
                infoTdcProp.setOtro_valor(false);
                infoTdcProp.setPago_pesos(true);
                infoTdcProp.setPago_total(false);
                infoTdcProp.setPago_minnimo(true);
                infoTdcProp.setPago_dolares(false);

            }


        } else {
            if (OtroValorPesos.isSelected()) {
                infoTdcProp.setOtro_valor(true);
                infoTdcProp.setPago_pesos(true);
                infoTdcProp.setPago_total(false);
                infoTdcProp.setPago_minnimo(false);
                infoTdcProp.setPago_dolares(false);

                String valor = ValorPesos.getText().replace(",", ".");
                if (valor.split("\\.").length == 2) {
                    if (valor.split("\\.")[1].length() != 2) {
                        valor = valor.split("\\.")[0] + "." + valor.split("\\.")[1] + "0";
                    }
                }
                infoTdcProp.setValor_pago(valor);

            } else if (PagoTotalPesos.isSelected()) {
                infoTdcProp.setOtro_valor(false);
                infoTdcProp.setPago_pesos(true);
                infoTdcProp.setPago_total(true);
                infoTdcProp.setPago_minnimo(false);
                infoTdcProp.setPago_dolares(false);


            } else if (PagoMinPesos.isSelected()) {
                infoTdcProp.setOtro_valor(false);
                infoTdcProp.setPago_pesos(true);
                infoTdcProp.setPago_total(false);
                infoTdcProp.setPago_minnimo(true);
                infoTdcProp.setPago_dolares(false);

            } else if (OtroValorDolar.isSelected()) {
                infoTdcProp.setOtro_valor(true);
                infoTdcProp.setPago_pesos(false);
                infoTdcProp.setPago_total(false);
                infoTdcProp.setPago_minnimo(false);
                infoTdcProp.setPago_dolares(true);

                String valor = ValorDolares.getText().replace(",", ".");
                if (valor.split("\\.").length == 2) {
                    if (valor.split("\\.")[1].length() != 2) {
                        valor = valor.split("\\.")[0] + "." + valor.split("\\.")[1] + "0";
                    }
                }
                infoTdcProp.setValor_pago(valor);

            } else if (PagoTotalDolar.isSelected()) {
                infoTdcProp.setOtro_valor(false);
                infoTdcProp.setPago_pesos(false);
                infoTdcProp.setPago_total(true);
                infoTdcProp.setPago_minnimo(false);
                infoTdcProp.setPago_dolares(true);


            } else if (PagoMinDolar.isSelected()) {
                infoTdcProp.setOtro_valor(false);
                infoTdcProp.setPago_pesos(false);
                infoTdcProp.setPago_total(false);
                infoTdcProp.setPago_minnimo(true);
                infoTdcProp.setPago_dolares(true);

            }

        }

        InfoDatosTdcPropia.setInfoTdcProp(infoTdcProp);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final PagosTDCpropiasConfirmController controller = new PagosTDCpropiasConfirmController();
                controller.mostrarTDCpropiasConfirmData(InfoDatosTdcPropia.getInfoTdcProp());

            }
        });

    }

    @FXML
    void selOtroValDolar(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion
                PagoTotalPesos.setSelected(false);
                PagoTotalDolar.setSelected(false);
                PagoMinPesos.setSelected(false);
                PagoMinDolar.setSelected(false);
                OtroValorPesos.setSelected(false);
                continuar_op.setDisable(true);
                ValorPesos.setText("");
                ValorPesos.setEditable(false);
                //
                if (OtroValorDolar.isSelected()) {
                    ValorDolares.setEditable(true);

                } else {
                    ValorDolares.setEditable(false);
                }
            }
        });
    }

    @FXML
    void selOtroValPesos(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion
                PagoTotalPesos.setSelected(false);
                PagoTotalDolar.setSelected(false);
                PagoMinPesos.setSelected(false);
                PagoMinDolar.setSelected(false);
                OtroValorDolar.setSelected(false);
                continuar_op.setDisable(true);
                ValorDolares.setText("");
                ValorDolares.setEditable(false);
                //

                if (OtroValorPesos.isSelected()) {
                    ValorPesos.setEditable(true);

                } else {
                    ValorPesos.setEditable(false);
                }
            }
        });
    }

    @FXML
    void selPagoMinDolar(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion
                PagoTotalPesos.setSelected(false);
                PagoTotalDolar.setSelected(false);
                PagoMinPesos.setSelected(false);
                OtroValorDolar.setSelected(false);
                OtroValorPesos.setSelected(false);
                ValorDolares.setText("");
                ValorPesos.setText("");
                ValorDolares.setEditable(false);
                ValorPesos.setEditable(false);

                if (PagoMinDolar.isSelected()) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                }
                //           
            }
        });
    }

    @FXML
    void selPagoMinPesos(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion
                PagoTotalPesos.setSelected(false);
                PagoTotalDolar.setSelected(false);
                PagoMinDolar.setSelected(false);
                OtroValorDolar.setSelected(false);
                OtroValorPesos.setSelected(false);
                ValorDolares.setText("");
                ValorPesos.setText("");
                ValorDolares.setEditable(false);
                ValorPesos.setEditable(false);

                if (PagoMinPesos.isSelected()) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                }
                //           
            }
        });

    }

    @FXML
    void selPagoTotDolar(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion
                PagoTotalPesos.setSelected(false);
                PagoMinPesos.setSelected(false);
                PagoMinDolar.setSelected(false);
                OtroValorDolar.setSelected(false);
                OtroValorPesos.setSelected(false);
                ValorDolares.setText("");
                ValorPesos.setText("");
                ValorDolares.setEditable(false);
                ValorPesos.setEditable(false);

                if (PagoTotalDolar.isSelected()) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                }
                //           
            }
        });
    }

    @FXML
    void selPagoTotPesos(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion               
                PagoTotalDolar.setSelected(false);
                PagoMinDolar.setSelected(false);
                PagoMinPesos.setSelected(false);
                OtroValorDolar.setSelected(false);
                OtroValorPesos.setSelected(false);
                ValorDolares.setText("");
                ValorPesos.setText("");
                ValorDolares.setEditable(false);
                ValorPesos.setEditable(false);

                if (PagoTotalPesos.isSelected()) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                }
                //           
            }
        });
    }

    @FXML
    void valDolarKey(final KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuar_op");
                    final RestrictiveTextField valDolar = (RestrictiveTextField) root.lookup("#ValorDolares");
                    final RestrictiveTextField valpesos = (RestrictiveTextField) root.lookup("#ValorPesos");
                    final RadioButton otroValDolar = (RadioButton) root.lookup("#OtroValorDolar");
                    final RadioButton otroValpesos = (RadioButton) root.lookup("#OtroValorPesos");
                    if (valDolar.getText().isEmpty() && otroValDolar.isSelected()) {
                        if (valpesos.getText().isEmpty()) {
                            buttonCont.setDisable(true);
                        }

                    } else {
                        if (!otroValpesos.isSelected() && otroValDolar.isSelected()) {
                            buttonCont.setDisable(false);
                        }

                    }


                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });


    }

    @FXML
    void valDolarPressed(final KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode())) {

            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                continuar_op.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(ValorDolares, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                ValorDolares.clear();
                ValorDolares.fireEvent(keyEvent);
                if (ValorPesos.getText().isEmpty()) {
                    continuar_op.setDisable(true);
                }

            }

        }

    }

    @FXML
    void valpesosKey(final KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuar_op");
                    final RestrictiveTextField valpesos = (RestrictiveTextField) root.lookup("#ValorPesos");
                    final RestrictiveTextField valDolar = (RestrictiveTextField) root.lookup("#ValorDolares");
                    final RadioButton otroValPesos = (RadioButton) root.lookup("#OtroValorPesos");
                    final RadioButton otroValDolar = (RadioButton) root.lookup("#OtroValorDolar");
                    if (valpesos.getText().isEmpty() && otroValPesos.isSelected()) {
                        if (valDolar.getText().isEmpty()) {
                            buttonCont.setDisable(true);
                        }


                    } else {
                        if (!otroValDolar.isSelected() && otroValPesos.isSelected()) {
                            buttonCont.setDisable(false);
                        }

                    }


                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });

    }

    @FXML
    void valpesosPressed(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                continuar_op.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(ValorPesos, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                ValorPesos.clear();
                ValorPesos.fireEvent(keyEvent);
                if (ValorDolares.getText().isEmpty()) {
                    continuar_op.setDisable(true);
                }

            }


        }
    }
}
