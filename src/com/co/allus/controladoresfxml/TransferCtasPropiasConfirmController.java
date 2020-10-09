/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.transfctaprop.confirmacionDatos1;
import com.co.allus.modelo.transfctaprop.confirmacionDatos2;
import com.co.allus.modelo.transfctaprop.infoTranferenciaCtaProp;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class TransferCtasPropiasConfirmController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML //  fx:id="cancelar_op"
    private transient Button cancelar_op; // Value injected by FXMLLoader
    @FXML //  fx:id="continuar_op"
    private transient Button continuar_op; // Value injected by FXMLLoader
    @FXML //  fx:id="cuenta_destino"
    private transient TableColumn<confirmacionDatos1, String> cuenta_destino; // Value injected by FXMLLoader
    @FXML //  fx:id="cuenta_origen"
    private transient TableColumn<confirmacionDatos1, String> cuenta_origen; // Value injected by FXMLLoader
    @FXML //  fx:id="tipo_cta_destino"
    private transient TableColumn<confirmacionDatos1, String> tipo_cta_destino; // Value injected by FXMLLoader
    @FXML //  fx:id="tipo_cta_origen"
    private transient TableColumn<confirmacionDatos1, String> tipo_cta_origen; // Value injected by FXMLLoader
    @FXML //  fx:id="valor_transferido"
    private transient TableColumn<confirmacionDatos2, String> valor_transferido; // Value injected by FXMLLoader
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    @FXML
    private transient TableView<confirmacionDatos1> tabla_Datos1;
    @FXML
    private transient TableView<confirmacionDatos2> tabla_Datos2;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        assert cancelar_op != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'TransferCtasPropiasConfirm.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'TransferCtasPropiasConfirm.fxml'.";
        assert cuenta_destino != null : "fx:id=\"cuenta_destino\" was not injected: check your FXML file 'TransferCtasPropiasConfirm.fxml'.";
        assert cuenta_origen != null : "fx:id=\"cuenta_origen\" was not injected: check your FXML file 'TransferCtasPropiasConfirm.fxml'.";
        assert tipo_cta_destino != null : "fx:id=\"tipo_cta_destino\" was not injected: check your FXML file 'TransferCtasPropiasConfirm.fxml'.";
        assert tipo_cta_origen != null : "fx:id=\"tipo_cta_origen\" was not injected: check your FXML file 'TransferCtasPropiasConfirm.fxml'.";
        assert valor_transferido != null : "fx:id=\"valor_transferido\" was not injected: check your FXML file 'TransferCtasPropiasConfirm.fxml'.";

        cuenta_origen.setCellValueFactory(new PropertyValueFactory<confirmacionDatos1, String>("cuenta_origen"));
        tipo_cta_origen.setCellValueFactory(new PropertyValueFactory<confirmacionDatos1, String>("tipo_cta_origen"));
        cuenta_destino.setCellValueFactory(new PropertyValueFactory<confirmacionDatos1, String>("cuenta_destino"));
        tipo_cta_destino.setCellValueFactory(new PropertyValueFactory<confirmacionDatos1, String>("tipo_cta_destino"));
        valor_transferido.setCellValueFactory(new PropertyValueFactory<confirmacionDatos2, String>("valor"));

        tabla_Datos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_Datos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // Handler for Button[fx:id="cancelar_op"] onAction
    public void cancel_op(final ActionEvent event) {
        Atlas.getVista().mostrarMenuTransfctasprop("Transferencias Cuentas Bancolombia Propias");
    }

    // Handler for Button[fx:id="continuar_op"] onAction
    public void continuar_OP(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final ModalTransferCtaPropiasController controller = new ModalTransferCtaPropiasController();
                controller.mostrarModalTranfctaprop(infoTranferenciaCtaProp.getInfoTranfCtaProp());
            }
        });
    }

    public void mostrarMenuConfDatos(final infoTranferenciaCtaProp dataTeansferencia) {

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/TransferCtasPropiasConfirm.fxml");
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
                        final String val = dataTeansferencia.getValor_transferir().replace(",", "");
                        //dataPago.setValorPagarCompleto(val);

                        if (val.contains(".")) {
                            dataTeansferencia.setValor_transferirEnt(val.split("\\.")[0]);
                            dataTeansferencia.setValor_transferirCent(val.split("\\.")[1]);
                        } else if (val.contains(",")) {
                            dataTeansferencia.setValor_transferirEnt(val.split(",")[0]);
                            dataTeansferencia.setValor_transferirCent(val.split(",")[1]);
                        } else {
                            dataTeansferencia.setValor_transferirEnt(val);
                            dataTeansferencia.setValor_transferirCent("00");
                        }
                        infoTranferenciaCtaProp.setInfoTranfCtaProp(dataTeansferencia);
                        tablaDatos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        tablaDatos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        final ObservableList<confirmacionDatos1> dataTabla1 = FXCollections.observableArrayList();
                        final ObservableList<confirmacionDatos2> dataTabla2 = FXCollections.observableArrayList();

                        dataTabla1.add(new confirmacionDatos1(dataTeansferencia.getCta_origen(), dataTeansferencia.getTipo_cta_origen(), dataTeansferencia.getCta_destino(), dataTeansferencia.getTipo_cta_destino()));
                        dataTabla2.add(new confirmacionDatos2("$ " + formatonum.format(Double.parseDouble(dataTeansferencia.getValor_transferirEnt())).replace(".", ",") + "." + dataTeansferencia.getValor_transferirCent()));

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
}
