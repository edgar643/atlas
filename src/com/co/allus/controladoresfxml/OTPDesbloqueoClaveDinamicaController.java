/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.utils.AtlasConstantes;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class OTPDesbloqueoClaveDinamicaController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button no_op;
    @FXML
    private ProgressBar progreso;
    @FXML
    private Button si_op;
    private static GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert no_op != null : "fx:id=\"no_op\" was not injected: check your FXML file 'OTPDesbloqueoClaveDinamica.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'OTPDesbloqueoClaveDinamica.fxml'.";
        assert si_op != null : "fx:id=\"si_op\" was not injected: check your FXML file 'OTPDesbloqueoClaveDinamica.fxml'.";
        this.location = url;
        this.resources = rb;

    }

    @FXML
    void no_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    @FXML
    void si_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final OTPDesbloqueoClaveDinamicaFinController controller = new OTPDesbloqueoClaveDinamicaFinController();
                controller.mostrarDesbloqueOTPfin();
            }
        });
    }

    public void mostrarDesbloqueOTP() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/OTPDesbloqueoClaveDinamica.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button no_op = (Button) root.lookup("#no_op");
                    final Button si_op = (Button) root.lookup("#si_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);


                    final DropShadow shadow = new DropShadow();
                    no_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    no_op.setCursor(Cursor.HAND);
                                    no_op.setEffect(shadow);
                                }
                            });

                    no_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    no_op.setCursor(Cursor.DEFAULT);
                                    no_op.setEffect(null);

                                }
                            });

                    si_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    si_op.setCursor(Cursor.HAND);
                                    si_op.setEffect(shadow);
                                }
                            });

                    si_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    si_op.setCursor(Cursor.DEFAULT);
                                    si_op.setEffect(null);

                                }
                            });




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

                    final TreeView<String> arbol_servad = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servad != null) {
                        arbol_servad.setDisable(false);
                    }



                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception e) {
//                    e.printStackTrace();
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }
            }
        });

    }
}
