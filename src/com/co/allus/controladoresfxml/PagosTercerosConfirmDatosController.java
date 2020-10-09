/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.pagosaterceros.confirmacionDatos1;
import com.co.allus.modelo.pagosaterceros.confirmacionDatos2;
import com.co.allus.modelo.pagosaterceros.convenio;
import com.co.allus.modelo.pagosaterceros.infoPago;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class PagosTercerosConfirmDatosController implements Initializable {

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
    private transient TableColumn<confirmacionDatos1, String> cod_conv;
    @FXML
    private transient Button continuar_op;
    @FXML
    private transient HBox menu_progreso;
    @FXML
    private transient TableColumn<confirmacionDatos1, String> ref_pago2;
    @FXML
    private transient TableColumn<confirmacionDatos1, String> nom_conv;
    @FXML
    private transient TableColumn<confirmacionDatos2, String> num_cuenta;
    @FXML
    private transient ProgressBar progreso;
    @FXML
    private transient TableColumn<confirmacionDatos1, String> ref_pago;
    @FXML
    private transient TableColumn<confirmacionDatos2, String> tipo_cuenta;
    @FXML
    private transient TableColumn<confirmacionDatos2, String> valor;
    @FXML
    private transient TableView<confirmacionDatos1> tabla_Datos1;
    @FXML
    private transient TableView<confirmacionDatos2> tabla_Datos2;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert cod_conv != null : "fx:id=\"cod_conv\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert ref_pago2 != null : "fx:id=\"refpago2\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert nom_conv != null : "fx:id=\"nom_conv\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert num_cuenta != null : "fx:id=\"num_cuenta\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert ref_pago != null : "fx:id=\"ref_pago\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert valor != null : "fx:id=\"valor\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert tabla_Datos1 != null : "fx:id=\"tabla_Datos1\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        assert tabla_Datos2 != null : "fx:id=\"tabla_Datos2\" was not injected: check your FXML file 'pagosTercerosConfirmDatos.fxml'.";
        this.location = url;
        this.resources = rb;

        cod_conv.setCellValueFactory(new PropertyValueFactory<confirmacionDatos1, String>("codigo_conv"));
        nom_conv.setCellValueFactory(new PropertyValueFactory<confirmacionDatos1, String>("nombre_conv"));
        ref_pago2.setCellValueFactory(new PropertyValueFactory<confirmacionDatos1, String>("refpago2"));
        ref_pago.setCellValueFactory(new PropertyValueFactory<confirmacionDatos1, String>("ref_pago"));

        valor.setCellValueFactory(new PropertyValueFactory<confirmacionDatos2, String>("valor"));
        num_cuenta.setCellValueFactory(new PropertyValueFactory<confirmacionDatos2, String>("num_cuenta"));
        tipo_cuenta.setCellValueFactory(new PropertyValueFactory<confirmacionDatos2, String>("tipo_cuenta"));
        tabla_Datos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_Datos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void mostrarMenuConfDatos(final convenio dataConvenio, final infoPago dataPago, final String ValorPagar) {

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/pagosTercerosConfirmDatos.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final TableView<confirmacionDatos1> tablaDatos1 = (TableView<confirmacionDatos1>) root.lookup("#tabla_Datos1");
                        final TableView<confirmacionDatos2> tablaDatos2 = (TableView<confirmacionDatos2>) root.lookup("#tabla_Datos2");

                        /**
                         * se organiza el valor a pagar en el formato requerido
                         */
                        String val = ValorPagar.replace(",", "");
                        dataPago.setValorPagarCompleto(val);

                        if (val.split("\\.").length == 2) {
                            if (val.split("\\.")[1].length() != 2) {
                                val = val.split("\\.")[0] + "." + val.split("\\.")[1] + "0";
                            }
                        }

                        if (val.contains(".")) {
                            dataPago.setValorPagarent(val.split("\\.")[0]);
                            dataPago.setValorpagarCent(val.split("\\.")[1]);
                        } else if (val.contains(",")) {
                            dataPago.setValorPagarent(val.split(",")[0]);
                            dataPago.setValorpagarCent(val.split(",")[1]);
                        } else {
                            dataPago.setValorPagarent(val);
                            dataPago.setValorpagarCent("00");
                        }
                        infoPago.setInfoPago(dataPago);

                        tablaDatos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        tablaDatos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        final ObservableList<confirmacionDatos1> dataTabla1 = FXCollections.observableArrayList();
                        final ObservableList<confirmacionDatos2> dataTabla2 = FXCollections.observableArrayList();

                        dataTabla1.add(new confirmacionDatos1(dataConvenio.getCod_conv(), dataConvenio.getNom_conv(), dataPago.getReferenciaPago2(), dataPago.getReferenciaPago1()));
                        dataTabla2.add(new confirmacionDatos2("$ " + formatonum.format(Double.parseDouble(dataPago.getValorPagarent())).replace(".", ",") + "." + dataPago.getValorpagarCent(), dataPago.getCuentaOrigen(), dataPago.getDescripcionCuentaOrigen()));
                        tablaDatos1.getColumns().get(3).setText("Referencia de Pago:\n" + dataConvenio.getReferencia().toLowerCase());
                        tablaDatos1.getColumns().get(2).setText("Referencia de Pago:\n" + dataConvenio.getReferencia2().toLowerCase());

                        /**
                         * validacion dinamica segunda referencia
                         */
                        if (dataPago.getReferenciaPago2().isEmpty()) {
                            tablaDatos1.getColumns().remove(2);
                        }
                        /**
                         * fin validacion
                         */
                        tablaDatos1.setItems(dataTabla1);
                        tablaDatos2.setItems(dataTabla2);


                        /**
                         * se repinta la vista en particular
                         */
                        tablaDatos1.setLayoutX(4);
                        tablaDatos1.setLayoutY(125);
                        pane.getChildren().remove(3);
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
    void cancel_op(final ActionEvent event) {
        Atlas.getVista().mostrarPanePagos("PAGOS A TERCEROS");
    }

    @FXML
    void continuar_OP(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ModalPagosTercerosController controller = new ModalPagosTercerosController();
                controller.mostrarModalPagos(convenio.getConvenio(), infoPago.getInfoPago());
            }
        });

    }
}
