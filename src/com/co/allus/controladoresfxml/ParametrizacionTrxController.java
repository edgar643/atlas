/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ParametrizacionTrxController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button aceptar;

    @FXML
    private TableColumn<?, ?> canal;

    @FXML
    private Button cancelar;

    @FXML
    private TableColumn<?, ?> monto;

    @FXML
    private TableColumn<?, ?> montoMax;

    @FXML
    private TableColumn<?, ?> numOperaciones;

    @FXML
    private TableColumn<?, ?> numtrxMax;

    @FXML
    private TableColumn<?, ?> operacion;

    @FXML
    private StackPane panel_tabla;

    @FXML
    private ComboBox<?> selCajero;

    @FXML
    private ComboBox<?> selPAC;

    @FXML
    private TableView<?> tabla_datos;


    @FXML
    void aceptarOP(ActionEvent event) {
    }

    @FXML
    void cancelarOp(ActionEvent event) {
    }

    @FXML
    void selcajeroOp(ActionEvent event) {
    }

    @FXML
    void selpacOp(ActionEvent event) {
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          assert aceptar != null : "fx:id=\"aceptar\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert canal != null : "fx:id=\"canal\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert monto != null : "fx:id=\"monto\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert montoMax != null : "fx:id=\"montoMax\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert numOperaciones != null : "fx:id=\"numOperaciones\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert numtrxMax != null : "fx:id=\"numtrxMax\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert operacion != null : "fx:id=\"operacion\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert selCajero != null : "fx:id=\"selCajero\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert selPAC != null : "fx:id=\"selPAC\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'ParametrizacionTrx.fxml'.";

    }    
}
