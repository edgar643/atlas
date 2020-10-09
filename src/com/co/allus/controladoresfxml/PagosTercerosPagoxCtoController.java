/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagosaterceros.ModelTablaInfoPago;
import com.co.allus.modelo.pagosaterceros.convenio;
import com.co.allus.modelo.pagosaterceros.infoPago;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class PagosTercerosPagoxCtoController implements Initializable {

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
    private transient RestrictiveTextField concepto_3;
    @FXML
    private transient RestrictiveTextField concepto_1;
    @FXML
    private transient RestrictiveTextField concepto_2;
    @FXML
    private transient RestrictiveTextField concepto_4;
    @FXML
    private transient RestrictiveTextField concepto_5;
    @FXML
    private transient RestrictiveTextField concepto_6;
    @FXML
    private transient Button continuar_op;
    @FXML
    private transient Label mensaje;
    @FXML
    private transient HBox menu_progreso;
    @FXML
    private transient TableColumn<ModelTablaInfoPago, String> num_cuenta;
    @FXML
    private transient ProgressBar progreso;
    @FXML
    private transient TableColumn<ModelTablaInfoPago, String> ref_pago;
    @FXML
    private transient TextField suma_valores;
    @FXML
    private transient TableView<ModelTablaInfoPago> tabla_datos;
    @FXML
    private transient TableColumn<ModelTablaInfoPago, String> tipo_cuenta;
    @FXML
    private transient Label titulo_conv;
    @FXML
    private transient RestrictiveTextField valor_1;
    @FXML
    private transient RestrictiveTextField valor_2;
    @FXML
    private transient RestrictiveTextField valor_3;
    @FXML
    private transient RestrictiveTextField valor_4;
    @FXML
    private transient RestrictiveTextField valor_5;
    @FXML
    private transient RestrictiveTextField valor_6;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,##");
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // TODO
        assert cancelar_op != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert concepto_3 != null : "fx:id=\"concepto3\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert concepto_1 != null : "fx:id=\"concepto_1\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert concepto_2 != null : "fx:id=\"concepto_2\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert concepto_4 != null : "fx:id=\"concepto_4\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert concepto_5 != null : "fx:id=\"concepto_5\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert concepto_6 != null : "fx:id=\"concepto_6\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert mensaje != null : "fx:id=\"mensaje\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert num_cuenta != null : "fx:id=\"num_cuenta\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert ref_pago != null : "fx:id=\"ref_pago\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert suma_valores != null : "fx:id=\"suma_valores\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert titulo_conv != null : "fx:id=\"titulo_conv\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert valor_1 != null : "fx:id=\"valor_1\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert valor_2 != null : "fx:id=\"valor_2\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert valor_3 != null : "fx:id=\"valor_3\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert valor_4 != null : "fx:id=\"valor_4\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert valor_5 != null : "fx:id=\"valor_5\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";
        assert valor_6 != null : "fx:id=\"valor_6\" was not injected: check your FXML file 'PagosTercerosPagoxConceptos.fxml'.";

        ref_pago.setCellValueFactory(new PropertyValueFactory<ModelTablaInfoPago, String>("referenciaPago"));
        tipo_cuenta.setCellValueFactory(new PropertyValueFactory<ModelTablaInfoPago, String>("tipoCuenta"));
        num_cuenta.setCellValueFactory(new PropertyValueFactory<ModelTablaInfoPago, String>("cuentaDebitar"));

        this.resources = resources;
        this.location = location;


        valor_1.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {

                if (newValue) {
                } else {

                    double val1, val2, val3, val4, val5, val6;

                    if (!valor_1.getText().isEmpty()) {
                        val1 = Double.parseDouble(valor_1.getText().replace(",", "."));
                    } else {
                        val1 = 0;
                    }
                    if (!valor_2.getText().isEmpty()) {
                        val2 = Double.parseDouble(valor_2.getText().replace(",", "."));
                    } else {
                        val2 = 0;
                    }
                    if (!valor_3.getText().isEmpty()) {
                        val3 = Double.parseDouble(valor_3.getText().replace(",", "."));
                    } else {
                        val3 = 0;
                    }

                    if (!valor_4.getText().isEmpty()) {
                        val4 = Double.parseDouble(valor_4.getText().replace(",", "."));
                    } else {
                        val4 = 0;
                    }
                    if (!valor_5.getText().isEmpty()) {
                        val5 = Double.parseDouble(valor_5.getText().replace(",", "."));
                    } else {
                        val5 = 0;
                    }
                    if (!valor_6.getText().isEmpty()) {
                        val6 = Double.parseDouble(valor_6.getText().replace(",", "."));
                    } else {
                        val6 = 0;
                    }
                    suma_valores.setText(String.format("%.2f", val1 + val2 + val3 + val4 + val5 + val6));

                }
            }
        });

        valor_2.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {

                if (newValue) {
                } else {

                    double val1, val2, val3, val4, val5, val6;

                    if (!valor_1.getText().isEmpty()) {
                        val1 = Double.parseDouble(valor_1.getText().replace(",", "."));
                    } else {
                        val1 = 0;
                    }
                    if (!valor_2.getText().isEmpty()) {
                        val2 = Double.parseDouble(valor_2.getText().replace(",", "."));
                    } else {
                        val2 = 0;
                    }
                    if (!valor_3.getText().isEmpty()) {
                        val3 = Double.parseDouble(valor_3.getText().replace(",", "."));
                    } else {
                        val3 = 0;
                    }

                    if (!valor_4.getText().isEmpty()) {
                        val4 = Double.parseDouble(valor_4.getText().replace(",", "."));
                    } else {
                        val4 = 0;
                    }
                    if (!valor_5.getText().isEmpty()) {
                        val5 = Double.parseDouble(valor_5.getText().replace(",", "."));
                    } else {
                        val5 = 0;
                    }
                    if (!valor_6.getText().isEmpty()) {
                        val6 = Double.parseDouble(valor_6.getText().replace(",", "."));
                    } else {
                        val6 = 0;
                    }
                    suma_valores.setText(String.format("%.2f", val1 + val2 + val3 + val4 + val5 + val6));

                }
            }
        });

        valor_3.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {

                if (newValue) {
                } else {

                    double val1, val2, val3, val4, val5, val6;

                    if (!valor_1.getText().isEmpty()) {
                        val1 = Double.parseDouble(valor_1.getText().replace(",", "."));
                    } else {
                        val1 = 0;
                    }
                    if (!valor_2.getText().isEmpty()) {
                        val2 = Double.parseDouble(valor_2.getText().replace(",", "."));
                    } else {
                        val2 = 0;
                    }
                    if (!valor_3.getText().isEmpty()) {
                        val3 = Double.parseDouble(valor_3.getText().replace(",", "."));
                    } else {
                        val3 = 0;
                    }

                    if (!valor_4.getText().isEmpty()) {
                        val4 = Double.parseDouble(valor_4.getText().replace(",", "."));
                    } else {
                        val4 = 0;
                    }
                    if (!valor_5.getText().isEmpty()) {
                        val5 = Double.parseDouble(valor_5.getText().replace(",", "."));
                    } else {
                        val5 = 0;
                    }
                    if (!valor_6.getText().isEmpty()) {
                        val6 = Double.parseDouble(valor_6.getText().replace(",", "."));
                    } else {
                        val6 = 0;
                    }
                    suma_valores.setText(String.format("%.2f", val1 + val2 + val3 + val4 + val5 + val6));

                }
            }
        });

        valor_4.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {

                if (newValue) {
                } else {

                    double val1, val2, val3, val4, val5, val6;

                    if (!valor_1.getText().isEmpty()) {
                        val1 = Double.parseDouble(valor_1.getText().replace(",", "."));
                    } else {
                        val1 = 0;
                    }
                    if (!valor_2.getText().isEmpty()) {
                        val2 = Double.parseDouble(valor_2.getText().replace(",", "."));
                    } else {
                        val2 = 0;
                    }
                    if (!valor_3.getText().isEmpty()) {
                        val3 = Double.parseDouble(valor_3.getText().replace(",", "."));
                    } else {
                        val3 = 0;
                    }

                    if (!valor_4.getText().isEmpty()) {
                        val4 = Double.parseDouble(valor_4.getText().replace(",", "."));
                    } else {
                        val4 = 0;
                    }
                    if (!valor_5.getText().isEmpty()) {
                        val5 = Double.parseDouble(valor_5.getText().replace(",", "."));
                    } else {
                        val5 = 0;
                    }
                    if (!valor_6.getText().isEmpty()) {
                        val6 = Double.parseDouble(valor_6.getText().replace(",", "."));
                    } else {
                        val6 = 0;
                    }
                    suma_valores.setText(String.format("%.2f", val1 + val2 + val3 + val4 + val5 + val6));

                }
            }
        });

        valor_5.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {

                if (newValue) {
                } else {
                    double val1, val2, val3, val4, val5, val6;

                    if (!valor_1.getText().isEmpty()) {
                        val1 = Double.parseDouble(valor_1.getText().replace(",", "."));
                    } else {
                        val1 = 0;
                    }
                    if (!valor_2.getText().isEmpty()) {
                        val2 = Double.parseDouble(valor_2.getText().replace(",", "."));
                    } else {
                        val2 = 0;
                    }
                    if (!valor_3.getText().isEmpty()) {
                        val3 = Double.parseDouble(valor_3.getText().replace(",", "."));
                    } else {
                        val3 = 0;
                    }

                    if (!valor_4.getText().isEmpty()) {
                        val4 = Double.parseDouble(valor_4.getText().replace(",", "."));
                    } else {
                        val4 = 0;
                    }
                    if (!valor_5.getText().isEmpty()) {
                        val5 = Double.parseDouble(valor_5.getText().replace(",", "."));
                    } else {
                        val5 = 0;
                    }
                    if (!valor_6.getText().isEmpty()) {
                        val6 = Double.parseDouble(valor_6.getText().replace(",", "."));
                    } else {
                        val6 = 0;
                    }
                    suma_valores.setText(String.format("%.2f", val1 + val2 + val3 + val4 + val5 + val6));


                }
            }
        });

        valor_6.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {

                if (newValue) {
                } else {

                    double val1, val2, val3, val4, val5, val6;

                    if (!valor_1.getText().isEmpty()) {
                        val1 = Double.parseDouble(valor_1.getText().replace(",", "."));
                    } else {
                        val1 = 0;
                    }
                    if (!valor_2.getText().isEmpty()) {
                        val2 = Double.parseDouble(valor_2.getText().replace(",", "."));
                    } else {
                        val2 = 0;
                    }
                    if (!valor_3.getText().isEmpty()) {
                        val3 = Double.parseDouble(valor_3.getText().replace(",", "."));
                    } else {
                        val3 = 0;
                    }

                    if (!valor_4.getText().isEmpty()) {
                        val4 = Double.parseDouble(valor_4.getText().replace(",", "."));
                    } else {
                        val4 = 0;
                    }
                    if (!valor_5.getText().isEmpty()) {
                        val5 = Double.parseDouble(valor_5.getText().replace(",", "."));
                    } else {
                        val5 = 0;
                    }
                    if (!valor_6.getText().isEmpty()) {
                        val6 = Double.parseDouble(valor_6.getText().replace(",", "."));
                    } else {
                        val6 = 0;
                    }
                    suma_valores.setText(String.format("%.2f", val1 + val2 + val3 + val4 + val5 + val6));

                }
            }
        });



        continuar_op.setDisable(true);
    }

    @FXML
    void cancel_op(final ActionEvent event) {
        Atlas.getVista().mostrarPanePagos(AtlasConstantes.MENU_PAGOS);
    }

    @FXML
    void continuar_OP(final ActionEvent event) {

        // calculo el valor total
        double val1, val2, val3, val4, val5, val6;

        if (!valor_1.getText().isEmpty()) {
            val1 = Double.parseDouble(valor_1.getText().replace(",", "."));
        } else {
            val1 = 0;
        }
        if (!valor_2.getText().isEmpty()) {
            val2 = Double.parseDouble(valor_2.getText().replace(",", "."));
        } else {
            val2 = 0;
        }
        if (!valor_3.getText().isEmpty()) {
            val3 = Double.parseDouble(valor_3.getText().replace(",", "."));
        } else {
            val3 = 0;
        }

        if (!valor_4.getText().isEmpty()) {
            val4 = Double.parseDouble(valor_4.getText().replace(",", "."));
        } else {
            val4 = 0;
        }
        if (!valor_5.getText().isEmpty()) {
            val5 = Double.parseDouble(valor_5.getText().replace(",", "."));
        } else {
            val5 = 0;
        }
        if (!valor_6.getText().isEmpty()) {
            val6 = Double.parseDouble(valor_6.getText().replace(",", "."));
        } else {
            val6 = 0;
        }
        suma_valores.setText(String.format("%.2f", val1 + val2 + val3 + val4 + val5 + val6));

        long valorTotal = (long) ((long) val1 + val2 + val3 + val4 + val5 + val6);


        if (String.valueOf(valorTotal).length() > 13) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new ModalMensajes("El valor de la suma supera el monto permitido", "Advertencia", ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);
                }
            });

        } else {
            final infoPago dataPago = infoPago.getInfoPago();
            dataPago.setConcepto1(concepto_1.getText());
            dataPago.setConcepto2(concepto_2.getText());
            dataPago.setConcepto3(concepto_3.getText());
            dataPago.setConcepto4(concepto_4.getText());
            dataPago.setConcepto5(concepto_5.getText());
            dataPago.setConcepto6(concepto_6.getText());
//        dataPago.setValor1(valor_1.getText().replace(",", ".").replace(".", ""));
//        dataPago.setValor2(valor_2.getText().replace(",", ".").replace(".", ""));
//        dataPago.setValor3(valor_3.getText().replace(",", ".").replace(".", ""));
//        dataPago.setValor4(valor_4.getText().replace(",", ".").replace(".", ""));
//        dataPago.setValor5(valor_5.getText().replace(",", ".").replace(".", ""));
//        dataPago.setValor6(valor_6.getText().replace(",", ".").replace(".", ""));
            dataPago.setValor1(valor_1.getText().replace(",", ".").split("\\.").length == 2 ? valor_1.getText().replace(",", ".").split("\\.")[0] : valor_1.getText());
            dataPago.setValor2(valor_2.getText().replace(",", ".").split("\\.").length == 2 ? valor_2.getText().replace(",", ".").split("\\.")[0] : valor_2.getText());
            dataPago.setValor3(valor_3.getText().replace(",", ".").split("\\.").length == 2 ? valor_3.getText().replace(",", ".").split("\\.")[0] : valor_3.getText());
            dataPago.setValor4(valor_4.getText().replace(",", ".").split("\\.").length == 2 ? valor_4.getText().replace(",", ".").split("\\.")[0] : valor_4.getText());
            dataPago.setValor5(valor_5.getText().replace(",", ".").split("\\.").length == 2 ? valor_5.getText().replace(",", ".").split("\\.")[0] : valor_5.getText());
            dataPago.setValor6(valor_6.getText().replace(",", ".").split("\\.").length == 2 ? valor_6.getText().replace(",", ".").split("\\.")[0] : valor_6.getText());
            dataPago.setInfoPago(dataPago);



            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    final PagosTercerosConfirmDatosController controller = new PagosTercerosConfirmDatosController();
                    controller.mostrarMenuConfDatos(convenio.getConvenio(), infoPago.getInfoPago(), suma_valores.getText().replace(",", "."));
                }
            });
        }

    }

    @FXML
    void concept1key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");


                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }
                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }


                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicación , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });


    }

    @FXML
    void concept2key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");

                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }

                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });

    }

    @FXML
    void concept3key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");

                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }

                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });
    }

    @FXML
    void concept4key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");


                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }


                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });

    }

    @FXML
    void concept5key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");


                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }

                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });

    }

    @FXML
    void concept6key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");


                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }

                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });

    }

    @FXML
    void val1key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");

                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }


                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });

    }

    @FXML
    void val2key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");

                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }


                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });


    }

    @FXML
    void val3key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");

                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }


                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n " , "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });

    }

    @FXML
    void val4key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");

                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }

                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });

    }

    @FXML
    void val5key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");


                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }

                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });

    }

    @FXML
    void val6key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final RestrictiveTextField concepto_1 = (RestrictiveTextField) root.lookup("#concepto_1");
                    final RestrictiveTextField valor_1 = (RestrictiveTextField) root.lookup("#valor_1");
                    final RestrictiveTextField concepto_2 = (RestrictiveTextField) root.lookup("#concepto_2");
                    final RestrictiveTextField valor_2 = (RestrictiveTextField) root.lookup("#valor_2");
                    final RestrictiveTextField concepto_3 = (RestrictiveTextField) root.lookup("#concepto_3");
                    final RestrictiveTextField valor_3 = (RestrictiveTextField) root.lookup("#valor_3");
                    final RestrictiveTextField concepto_4 = (RestrictiveTextField) root.lookup("#concepto_4");
                    final RestrictiveTextField valor_4 = (RestrictiveTextField) root.lookup("#valor_4");
                    final RestrictiveTextField concepto_5 = (RestrictiveTextField) root.lookup("#concepto_5");
                    final RestrictiveTextField valor_5 = (RestrictiveTextField) root.lookup("#valor_5");
                    final RestrictiveTextField concepto_6 = (RestrictiveTextField) root.lookup("#concepto_6");
                    final RestrictiveTextField valor_6 = (RestrictiveTextField) root.lookup("#valor_6");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");


                    final String datos[][] = new String[6][2];

                    datos[0][0] = concepto_1.getText().trim();
                    datos[1][0] = concepto_2.getText().trim();
                    datos[2][0] = concepto_3.getText().trim();
                    datos[3][0] = concepto_4.getText().trim();
                    datos[4][0] = concepto_5.getText().trim();
                    datos[5][0] = concepto_6.getText().trim();

                    datos[0][1] = valor_1.getText().trim();
                    datos[1][1] = valor_2.getText().trim();
                    datos[2][1] = valor_3.getText().trim();
                    datos[3][1] = valor_4.getText().trim();
                    datos[4][1] = valor_5.getText().trim();
                    datos[5][1] = valor_6.getText().trim();


                    continuar_op.setDisable(false);
                    for (int i = 0; i < datos.length; i++) {
                        if (!datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && !datos[i][1].isEmpty()) {
                            continuar_op.setDisable(true);
                            break;
                        }
                    }

                    int cont = 0;
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i][0].isEmpty() && datos[i][1].isEmpty()) {
                            cont++;
                        }
                    }

                    if (cont == 6) {
                        continuar_op.setDisable(true);
                    }

                } catch (final Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                            //return;
                        }
                    });
                }

            }
        });

    }

    @FXML
    void concept1keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);

            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_1.clear();
                    concepto_1.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_1.clear();
                    concepto_1.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                concepto_1.clear();
                concepto_1.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void concept2keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);

            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_2, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_2.clear();
                    concepto_2.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_2, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_2.clear();
                    concepto_2.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_2, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                concepto_2.clear();
                concepto_2.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void concept3keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);

            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_3, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_3.clear();
                    concepto_3.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_3, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_3.clear();
                    concepto_3.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_3, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                concepto_3.clear();
                concepto_3.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void concept4keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);

            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_4, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_4.clear();
                    concepto_4.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_4, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_4.clear();
                    concepto_4.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_4, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                concepto_4.clear();
                concepto_4.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void concept5keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);

            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_5, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_5.clear();
                    concepto_5.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_5, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_5.clear();
                    concepto_5.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_5, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                concepto_5.clear();
                concepto_5.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void concept6keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);

            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_6, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_6.clear();
                    concepto_6.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_6, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    concepto_6.clear();
                    concepto_6.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(concepto_6, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                concepto_6.clear();
                concepto_6.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void val1keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);
            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_1.clear();
                    valor_1.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_1.clear();
                    valor_1.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                valor_1.clear();
                valor_1.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void val2keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);
            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_2.clear();
                    valor_2.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_2.clear();
                    valor_2.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_2, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                valor_2.clear();
                valor_2.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void val3keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);
            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_3, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_3.clear();
                    valor_3.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_3, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_3.clear();
                    valor_3.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_3, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                valor_3.clear();
                valor_3.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void val4keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);
            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_4, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_4.clear();
                    valor_4.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_4, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_4.clear();
                    valor_4.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_4, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                valor_4.clear();
                valor_4.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void val5keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);
            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_5, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_5.clear();
                    valor_5.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_5, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_5.clear();
                    valor_5.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_5, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                valor_5.clear();
                valor_5.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }

    @FXML
    void val6keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {


            final String datos[][] = new String[6][2];

            datos[0][0] = concepto_1.getText().trim();
            datos[1][0] = concepto_2.getText().trim();
            datos[2][0] = concepto_3.getText().trim();
            datos[3][0] = concepto_4.getText().trim();
            datos[4][0] = concepto_5.getText().trim();
            datos[5][0] = concepto_6.getText().trim();
            datos[0][1] = valor_1.getText().trim();
            datos[1][1] = valor_2.getText().trim();
            datos[2][1] = valor_3.getText().trim();
            datos[3][1] = valor_4.getText().trim();
            datos[4][1] = valor_5.getText().trim();
            datos[5][1] = valor_6.getText().trim();


            continuar_op.setDisable(false);
            for (int i = 0; i < datos.length; i++) {
                if ((!datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_6, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_6.clear();
                    valor_6.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && !datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_6, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    valor_6.clear();
                    valor_6.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                    break;
                }
            }

            int cont = 0;
            for (int i = 0; i < datos.length; i++) {
                if ((datos[i][0].isEmpty() && datos[i][1].isEmpty()) || event.getCode().impl_getChar().trim().equals("")) {
                    cont++;
                }
            }

            if (cont == 6) {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(valor_6, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                valor_6.clear();
                valor_6.fireEvent(keyEvent);
                continuar_op.setDisable(true);
            }


        }
    }
}
