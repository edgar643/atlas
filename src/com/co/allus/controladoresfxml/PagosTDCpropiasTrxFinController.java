/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagostdc.InfoDatosTdcPropia;
import com.co.allus.modelo.pagostdc.InfoTrxFin1TDCprop;
import com.co.allus.modelo.pagostdc.infoTrxFin2TDCprop;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class PagosTDCpropiasTrxFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTrxFin2TDCprop, String> comprobante;
    @FXML
    private TableColumn<infoTrxFin2TDCprop, String> fecha_pago;
    @FXML
    private TableColumn<InfoTrxFin1TDCprop, String> franquicia;
    @FXML
    private TableColumn<InfoTrxFin1TDCprop, String> tarjeta_pagada;
    @FXML
    private TableColumn<infoTrxFin2TDCprop, String> num_cuenta;
    @FXML
    private TableView<InfoTrxFin1TDCprop> tabla_Datos1;
    @FXML
    private TableView<infoTrxFin2TDCprop> tabla_Datos2;
    @FXML
    private Button terminar_trx;
    @FXML
    private TableColumn<infoTrxFin2TDCprop, String> tipo_cuenta;
    @FXML
    private TableColumn<InfoTrxFin1TDCprop, String> valor_pagado;
    private transient final DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert comprobante != null : "fx:id=\"comprobante\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";
        assert fecha_pago != null : "fx:id=\"fecha_pago\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";
        assert franquicia != null : "fx:id=\"franquicia\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";
        assert num_cuenta != null : "fx:id=\"num_cuenta\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";
        assert tabla_Datos1 != null : "fx:id=\"tabla_Datos1\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";
        assert tabla_Datos2 != null : "fx:id=\"tabla_Datos2\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";
        assert valor_pagado != null : "fx:id=\"valor_pagado\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";
        assert tarjeta_pagada != null : "fx:id=\"tarjeta_pagada\" was not injected: check your FXML file 'PagosTDCpropiasTrxFin.fxml'.";

        this.location = url;
        this.resources = rb;

        tarjeta_pagada.setCellValueFactory(new PropertyValueFactory<InfoTrxFin1TDCprop, String>("tarjeta_pagar"));
        franquicia.setCellValueFactory(new PropertyValueFactory<InfoTrxFin1TDCprop, String>("franquicia"));
        valor_pagado.setCellValueFactory(new PropertyValueFactory<InfoTrxFin1TDCprop, String>("valor_pagado"));


        comprobante.setCellValueFactory(new PropertyValueFactory<infoTrxFin2TDCprop, String>("comprobante"));
        num_cuenta.setCellValueFactory(new PropertyValueFactory<infoTrxFin2TDCprop, String>("num_cuenta"));
        tipo_cuenta.setCellValueFactory(new PropertyValueFactory<infoTrxFin2TDCprop, String>("tipo_cuenta"));
        fecha_pago.setCellValueFactory(new PropertyValueFactory<infoTrxFin2TDCprop, String>("fecha_pago"));
    }

    public void mostrarTrxPagoTDCpropFin(final InfoDatosTdcPropia infoDatos) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosTDCpropiasTrxFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final TableView<InfoTrxFin1TDCprop> tabladatos1 = (TableView<InfoTrxFin1TDCprop>) root.lookup("#tabla_Datos1");
                    final TableView<infoTrxFin2TDCprop> tabladatos2 = (TableView<infoTrxFin2TDCprop>) root.lookup("#tabla_Datos2");
                    tabladatos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    tabladatos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                    /**
                     * se cargan los valores de la informacion del finpago
                     */
                    final ObservableList<InfoTrxFin1TDCprop> datosPagoTDCprop = FXCollections.observableArrayList();
                    final InfoTrxFin1TDCprop datostdcprop = new InfoTrxFin1TDCprop(infoDatos.getTarjetaCredito(), infoDatos.getFranquicia(), "$ " + formatonum.format(Double.parseDouble(InfoDatosTdcPropia.getInfoTdcProp().getValor_pago_ent())).replace(".", ",") + "." + InfoDatosTdcPropia.getInfoTdcProp().getValor_pago_cent());
                    datosPagoTDCprop.add(datostdcprop);
                    tabladatos1.setItems(datosPagoTDCprop);


                    /**
                     * ***************************
                     */
                    final ObservableList<infoTrxFin2TDCprop> datosPagoTDCprop2 = FXCollections.observableArrayList();
                    final infoTrxFin2TDCprop datostdcprop2 = new infoTrxFin2TDCprop(infoDatos.getComprobante(), infoDatos.getCta_origen(), infoDatos.getTipo_cta_origen(), infoDatos.getFechaPago());
                    datosPagoTDCprop2.add(datostdcprop2);
                    tabladatos2.setItems(datosPagoTDCprop2);

                    /**
                     * ***********************************
                     */
                    if (infoDatos.isPago_pesos()) {

                        tabladatos1.getColumns().get(2).setText("Valor Pagado\n   (Pesos)");

                    } else if (infoDatos.isPago_dolares()) {

                        tabladatos1.getColumns().get(2).setText("Valor Pagado\n  (Dólares)");
                    }

                    /**
                     * fin validacion
                     */
                    /**
                     * se cargar los valores de la informacion del pago
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


                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que el  pago se halla realizado "
                            + "correctamente , por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TRANSF_CTA_PROP);

                }

            }
        });

    }

    @FXML
    void aceptar(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    docs.imprimir("Se presento un error inesperado en la aplicacion --->" + ex.toString());
                }
            }
        });

    }
}
