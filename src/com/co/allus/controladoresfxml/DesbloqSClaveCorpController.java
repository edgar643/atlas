/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class DesbloqSClaveCorpController implements Initializable {

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
    private RadioButton checkValidacionPreg;
    @FXML
    private Button continuar_op;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'DesbloqSClaveCorp.fxml'.";
        assert checkValidacionPreg != null : "fx:id=\"checkValidacionPreg\" was not injected: check your FXML file 'DesbloqSClaveCorp.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'DesbloqSClaveCorp.fxml'.";
    }

    @FXML
    void cancel_op(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception ex) {
                    Logger.getGlobal().log(Level.SEVERE, ex.toString());
                }
            }
        });
    }

    @FXML
    void continuar_OP(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final DesbloqSClaveCorpValDatosController controller = new DesbloqSClaveCorpValDatosController();
                controller.mostrarDesbloqueoSCValDatos();
            }
        });

    }

    @FXML
    void selCheckPreguntas(final ActionEvent event) {

        if (checkValidacionPreg.isSelected()) {
            continuar_op.setDisable(false);
        } else {
            continuar_op.setDisable(true);
        }
    }
}
