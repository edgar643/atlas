/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author luis.cuervo
 */
public class ActualizacionDatosSeguridadTrxFinController implements Initializable {

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
    private Label titulosActDatSeguridad;

    @FXML
    void aceptar(ActionEvent event) {
        try {
            Atlas.getVista().gotoPrincipal();
        } catch (IOException ex) {
            gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
        }

    }
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static String menu1 = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxFin.fxml'.";
        assert titulosActDatSeguridad != null : "fx:id=\"titulosActDatSeguridad\" was not injected: check your FXML file 'ActualizacionDatosSeguridadTrxFin.fxml'.";

    }

    public void mostrarActualizacionDatosSeguridadTrxFinController(final String menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ActualizacionDatosSeguridadTrxFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");

                    final Cliente datosCliente = Cliente.getCliente();

                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    final Label titulosActDatSeguridad = (Label) root.lookup("#titulosActDatSeguridad");
                    titulosActDatSeguridad.setText(menu);
                    menu1 = menu;


                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();

                } catch (Exception ex) {
                    ex.toString();
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });


    }
}
