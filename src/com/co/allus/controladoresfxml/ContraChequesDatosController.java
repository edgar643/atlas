/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.contraCheques.dataContraorden;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import com.co.allus.userComponent.RestrictiveTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ContraChequesDatosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar_op;
    @FXML
    private RestrictiveTextField rangofin;
    @FXML
    private RestrictiveTextField rangoini;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ContraChequesDatos.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'ContraChequesDatos.fxml'.";
        assert rangofin != null : "fx:id=\"rangofin\" was not injected: check your FXML file 'ContraChequesDatos.fxml'.";
        assert rangoini != null : "fx:id=\"rangoini\" was not injected: check your FXML file 'ContraChequesDatos.fxml'.";
    }

    public void mostrarContraChequesDatos(final dataContraorden datoscontraOrden) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ContraChequesDatos.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente()
                            + "\nCuenta Corriente: " + modelSaldoTarjeta.enmascararNumero(datoscontraOrden.getNumcta(),6);
                    cliente.setText("");
                    cliente.setText(info);


                    final DropShadow shadow = new DropShadow();
                    botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botoncontinuarOp.setCursor(Cursor.HAND);
                                    botoncontinuarOp.setEffect(shadow);
                                }
                            });

                    botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botoncontinuarOp.setCursor(Cursor.DEFAULT);
                                    botoncontinuarOp.setEffect(null);

                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.HAND);
                                    cancelar.setEffect(shadow);
                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.DEFAULT);
                                    cancelar.setEffect(null);

                                }
                            });

                    botoncontinuarOp.setDisable(true);

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

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
    void cancelarOP(final ActionEvent event) {

        final ContraChequesController controller = new ContraChequesController();
        controller.mostrarContraCheques();

    }

    @FXML
    void continuarOP(final ActionEvent event) {
        dataContraorden datosCCheque = dataContraorden.getDatosCCheque();
        datosCCheque.setChequeini(rangoini.getText());
        datosCCheque.setChequefin(rangofin.getText());
        dataContraorden.setDatosCCheque(datosCCheque);
        ContraChequesConfirmController controller = new ContraChequesConfirmController();
        controller.mostrarContraChequesConfirm(datosCCheque);

    }

    @FXML
    void rangofinkeytyped(KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuar_op");
                    final RestrictiveTextField rangofin = (RestrictiveTextField) root.lookup("#rangofin");
                    final RestrictiveTextField rangoini = (RestrictiveTextField) root.lookup("#rangoini");

                    if (rangoini.getText().isEmpty()) {
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
    void rangofinkeypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if ((event.getCode().impl_getChar().trim().equals(""))) {
                //continuar_op.setDisable(false);
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(rangofin, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                rangofin.clear();
                rangofin.fireEvent(keyEvent);
            }


        }

    }

    @FXML
    void rangoinikeypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                continuar_op.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(rangoini, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                rangoini.clear();
                rangoini.fireEvent(keyEvent);

            }


        }
    }

    @FXML
    void rangoinikeytyped(final KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuar_op");
                    final RestrictiveTextField rangoini = (RestrictiveTextField) root.lookup("#rangoini");

                    if (rangoini.getText().isEmpty()) {
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
}
