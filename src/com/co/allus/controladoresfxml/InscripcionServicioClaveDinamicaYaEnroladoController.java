/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author luis.cuervo
 */
public class InscripcionServicioClaveDinamicaYaEnroladoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button terminar_trx;
    @FXML
    private Label titulosEvidente;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static String menu1 = "";
    private static int flujo1 = 0;
    private static int opcion1 = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaYaEnrolado.fxml'.";
        assert titulosEvidente != null : "fx:id=\"titulosEvidente\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaYaEnrolado.fxml'.";
    }

    @FXML
    void aceptar(ActionEvent event) {
        try {
            Atlas.getVista().gotoPrincipal();
        } catch (IOException ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
    }

    public void mostrarFinInscripcionClaveDinamicaYaEnrolado(final String menu, final int flujo, final int opcion) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/InscripcionServicioClaveDinamicaYaEnrolado.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final Label titulosEvidente = (Label) root.lookup("#titulosEvidente");
                        titulosEvidente.setText(menu);
//                        System.out.println("------ Lo que pasa en menu 2:" + menu);
                        menu1 = menu;
                        flujo1 = flujo;
                        opcion1 = opcion;

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

                        final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                        if (arbol_servadd != null) {
                            arbol_servadd.setDisable(true);
                        }

                        try {
                            pane.getChildren().remove(3);
                        } catch (Exception ex) {
                            gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                        }
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
}
