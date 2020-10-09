/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.transfctaprop.informacionTransfin1;
import com.co.allus.modelo.transfctaprop.informacionTransfin2;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class TransferCtaPropiasFinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private transient ResourceBundle resources;
    @FXML
    private transient URL location;
    @FXML
    private transient TableColumn<informacionTransfin2, String> comprobante;
    @FXML
    private transient TableColumn<informacionTransfin1, String> cuenta_destino;
    @FXML
    private transient TableColumn<informacionTransfin1, String> cuenta_origen;
    @FXML
    private transient TableColumn<informacionTransfin2, String> hora_trans;
    @FXML
    private transient TableView<informacionTransfin1> tabla_datos_fin1;
    @FXML
    private transient TableView<informacionTransfin2> tabla_datos_fin2;
    @FXML
    private transient Button terminar_trx;
    @FXML
    private transient TableColumn<informacionTransfin1, String> tipo_cta_destino;
    @FXML
    private transient TableColumn<informacionTransfin1, String> tipo_cta_origen;
    @FXML
    private transient TableColumn<informacionTransfin2, String> valor_transferido;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        // TODO
        assert comprobante != null : "fx:id=\"comprobante\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";
        assert cuenta_destino != null : "fx:id=\"cuenta_destino\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";
        assert cuenta_origen != null : "fx:id=\"cuenta_origen\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";
        assert hora_trans != null : "fx:id=\"hora_trans\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";
        assert tabla_datos_fin1 != null : "fx:id=\"tabla_datos_fin1\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";
        assert tabla_datos_fin2 != null : "fx:id=\"tabla_datos_fin2\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";
        assert tipo_cta_destino != null : "fx:id=\"tipo_cta_destino\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";
        assert tipo_cta_origen != null : "fx:id=\"tipo_cta_origen\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";
        assert valor_transferido != null : "fx:id=\"valor_transferido\" was not injected: check your FXML file 'TransferCtaPropiasFin.fxml'.";

        cuenta_origen.setCellValueFactory(new PropertyValueFactory<informacionTransfin1, String>("cuenta_origen"));
        tipo_cta_origen.setCellValueFactory(new PropertyValueFactory<informacionTransfin1, String>("tipo_cta_origen"));
        cuenta_destino.setCellValueFactory(new PropertyValueFactory<informacionTransfin1, String>("cuenta_destino"));
        tipo_cta_destino.setCellValueFactory(new PropertyValueFactory<informacionTransfin1, String>("tipo_cta_destino"));

        comprobante.setCellValueFactory(new PropertyValueFactory<informacionTransfin2, String>("num_comprobante"));
        valor_transferido.setCellValueFactory(new PropertyValueFactory<informacionTransfin2, String>("valor_transferido"));
        hora_trans.setCellValueFactory(new PropertyValueFactory<informacionTransfin2, String>("fecha_transf"));

        this.resources = rb;
        this.location = url;

    }

    @FXML
    void aceptar(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    gestorDoc.imprimir("Se presento un error inesperado en la aplicacion --->" + ex.toString());
                }
            }
        });

    }
}
