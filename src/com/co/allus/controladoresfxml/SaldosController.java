/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.saldos.ConsultaSaldoTabla1;
import com.co.allus.modelo.saldos.ConsultaSaldoTabla2;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.TextArea;
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
public class SaldosController implements Initializable {

    @FXML //  fx:id="tabla_saldo1"
    private transient TableView<ConsultaSaldoTabla1> tabla_saldo1; // Value injected by FXMLLoader
    @FXML //  fx:id="tabla_saldo2"
    private transient TableView<?> tabla_saldo2; // Value injected by FXMLLoader
    @FXML //  fx:id="terminar_aceptar"
    private transient Button terminar_aceptar; // Value injected by FXMLLoader
    @FXML //  fx:id="cupoTotal"
    private transient TableColumn<ConsultaSaldoTabla2, String> cupoTotal; // Value injected by FXMLLoader
    @FXML //  fx:id="diaSobregiro"
    private transient TableColumn<ConsultaSaldoTabla2, String> diaSobregiro; // Value injected by FXMLLoader
    @FXML //  fx:id="entidad"
    private transient TableColumn<ConsultaSaldoTabla1, String> entidad; // Value injected by FXMLLoader
    @FXML //  fx:id="numeroCuenta"
    private transient TableColumn<ConsultaSaldoTabla1, String> numeroCuenta; // Value injected by FXMLLoader
    @FXML //  fx:id="saldoCanje"
    private transient TableColumn<ConsultaSaldoTabla2, String> saldoCanje; // Value injected by FXMLLoader
    @FXML //  fx:id="saldodisp"
    private transient TableColumn<ConsultaSaldoTabla2, String> saldodisp; // Value injected by FXMLLoader
    @FXML //  fx:id="tipoCuenta"
    private transient TableColumn<ConsultaSaldoTabla1, String> tipoCuenta; // Value injected by FXMLLoader
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    // Handler for Button[fx:id="terminar_aceptar"] onAction
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    @FXML
    private TextArea textoPopUp;
    @FXML
    private AnchorPane PaneMensajepopup;

    @FXML
    void aceptar(final ActionEvent event) {


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

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {

        assert cupoTotal != null : "fx:id=\"cupoTotal\" was not injected: check your FXML file 'saldos.fxml'.";
        assert diaSobregiro != null : "fx:id=\"diaSobregiro\" was not injected: check your FXML file 'saldos.fxml'.";
        assert entidad != null : "fx:id=\"entidad\" was not injected: check your FXML file 'saldos.fxml'.";
        assert numeroCuenta != null : "fx:id=\"numeroCuenta\" was not injected: check your FXML file 'saldos.fxml'.";
        assert saldoCanje != null : "fx:id=\"saldoCanje\" was not injected: check your FXML file 'saldos.fxml'.";
        assert saldodisp != null : "fx:id=\"saldodisp\" was not injected: check your FXML file 'saldos.fxml'.";
        assert tabla_saldo1 != null : "fx:id=\"tabla_saldo1\" was not injected: check your FXML file 'saldos.fxml'.";
        assert tabla_saldo2 != null : "fx:id=\"tabla_saldo2\" was not injected: check your FXML file 'saldos.fxml'.";
        assert terminar_aceptar != null : "fx:id=\"terminar_aceptar\" was not injected: check your FXML file 'saldos.fxml'.";
        assert tipoCuenta != null : "fx:id=\"tipoCuenta\" was not injected: check your FXML file 'saldos.fxml'.";
        assert PaneMensajepopup != null : "fx:id=\"PaneMensajepopup\" was not injected: check your FXML file 'saldos.fxml'.";
        assert textoPopUp != null : "fx:id=\"textoPopUp\" was not injected: check your FXML file 'saldos.fxml'.";

        entidad.setCellValueFactory(new PropertyValueFactory<ConsultaSaldoTabla1, String>("entidad"));
        numeroCuenta.setCellValueFactory(new PropertyValueFactory<ConsultaSaldoTabla1, String>("numeroCuenta"));
        tipoCuenta.setCellValueFactory(new PropertyValueFactory<ConsultaSaldoTabla1, String>("tipoCuenta"));
        saldodisp.setCellValueFactory(new PropertyValueFactory<ConsultaSaldoTabla2, String>("saldoDisponible"));
        saldoCanje.setCellValueFactory(new PropertyValueFactory<ConsultaSaldoTabla2, String>("saldoCanje"));
        cupoTotal.setCellValueFactory(new PropertyValueFactory<ConsultaSaldoTabla2, String>("saldoTotal"));
        diaSobregiro.setCellValueFactory(new PropertyValueFactory<ConsultaSaldoTabla2, String>("diasSobregiro"));
        final DropShadow shadow = new DropShadow();

        terminar_aceptar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        terminar_aceptar.setCursor(Cursor.HAND);
                        terminar_aceptar.setEffect(shadow);
                    }
                });

        terminar_aceptar.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        terminar_aceptar.setCursor(Cursor.DEFAULT);
                        terminar_aceptar.setEffect(null);

                    }
                });
    }

    public void handleConsultaSaldo(
            final String numerocta,
            final String tipocta,
            final String saldoDisp,
            final String SDcentavos,
            final String SaldoCanje,
            final String SCcentavos,
            final String SaldoTotal,
            final String STcentavos,
            final String DiasSobreg,
            final String Entidad) {

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/saldos.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final TableView tabla1 = (TableView) root.lookup("#tabla_saldo1");
                        final TableView tabla2 = (TableView) root.lookup("#tabla_saldo2");
                        final ObservableList<ConsultaSaldoTabla1> items = tabla1.getItems();
                        // nuevo cambio 
                        final AnchorPane panePopup = (AnchorPane) root.lookup("#PaneMensajepopup");
                        final TextArea textoPopUp = (TextArea) root.lookup("#textoPopUp");
                        items.clear();
                        items.add(new ConsultaSaldoTabla1(Entidad, tipocta, StringUtilities.eliminarCerosLeft(numerocta)));
                        tabla2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        tabla1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


                        //proceso de datos 

                        final String diasSobregiro = String.valueOf(Integer.parseInt(DiasSobreg));

                        final String saldoDisponible = formatonum.format(Double.parseDouble(saldoDisp)).replace(".", ",") + "." + Integer.parseInt(SDcentavos);
                        final String saldocanje = formatonum.format(Double.parseDouble(SaldoCanje)).replace(".", ",") + "." + Integer.parseInt(SCcentavos);
                        final String saldototal = formatonum.format(Double.parseDouble(SaldoTotal)).replace(".", ",") + "." + Integer.parseInt(SDcentavos);
                        //  saldocanje="1200.00";
                        final ObservableList<ConsultaSaldoTabla2> datosTabla2 = tabla2.getItems();
                        datosTabla2.add(new ConsultaSaldoTabla2(saldoDisponible, saldocanje, saldototal, diasSobregiro));
                        if (tipocta.equalsIgnoreCase(CodigoProductos.CUENTA_AHORROS)) {
//                                TableColumn sobregiro=(TableColumn)tabla2.getColumns().get(2);
//                                sobregiro.textProperty().setValue("nuevo texto columna");
                            tabla2.getColumns().remove(3);
                        }


                        if ("0.0".equalsIgnoreCase(saldocanje)) {
                            panePopup.setVisible(false);
                            textoPopUp.setVisible(false);
                        }

                        tabla1.prefWidthProperty().bind(root.widthProperty());
                        tabla2.prefWidthProperty().bind(root.widthProperty());
                        pane.getChildren().remove(3);
                        pane.setAlignment(Pos.CENTER);
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
