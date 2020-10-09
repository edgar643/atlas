/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.pagosaterceros.informacionPagofin1;
import com.co.allus.modelo.pagosaterceros.informacionPagofin2;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class PagosTercerosfinTrxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private transient ResourceBundle resources;
    @FXML
    private transient URL location;
    @FXML
    private transient TableColumn<informacionPagofin1, String> cod_conv;
    @FXML
    private transient TableColumn<informacionPagofin1, String> entidad_pago;
    @FXML
    private transient TableColumn<informacionPagofin2, String> fecha_hora_pago;
    @FXML
    private transient Label mensaje;
    @FXML
    private transient TableColumn<informacionPagofin1, String> refpago2;
    @FXML
    private transient TableColumn<informacionPagofin2, String> num_comprobante;
    @FXML
    private transient TableColumn<informacionPagofin2, String> num_cuenta;
    @FXML
    private transient TableColumn<informacionPagofin1, String> ref_pago;
    @FXML
    private transient TableView<informacionPagofin1> tabla_datos_fin1;
    @FXML
    private transient TableView<informacionPagofin2> tabla_datos_fin2;
    @FXML
    private transient Button terminar_trx;
    @FXML
    private transient TableColumn<informacionPagofin2, String> tipo_cuenta;
    @FXML
    private transient Label titulo_pago;
    @FXML
    private transient TableColumn<informacionPagofin2, String> valor;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // TODO       
        assert cod_conv != null : "fx:id=\"cod_conv\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert entidad_pago != null : "fx:id=\"entidad_pago\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert fecha_hora_pago != null : "fx:id=\"fecha_hora_pago\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert mensaje != null : "fx:id=\"mensaje\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert refpago2 != null : "fx:id=\"refpago2\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert num_comprobante != null : "fx:id=\"num_comprobante\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert num_cuenta != null : "fx:id=\"num_cuenta\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert ref_pago != null : "fx:id=\"ref_pago\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert tabla_datos_fin1 != null : "fx:id=\"tabla_datos_fin1\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert tabla_datos_fin2 != null : "fx:id=\"tabla_datos_fin2\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert titulo_pago != null : "fx:id=\"titulo_pago\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";
        assert valor != null : "fx:id=\"valor\" was not injected: check your FXML file 'PagosTercerosfinTrx.fxml'.";

        cod_conv.setCellValueFactory(new PropertyValueFactory<informacionPagofin1, String>("cod_conv"));
        entidad_pago.setCellValueFactory(new PropertyValueFactory<informacionPagofin1, String>("entidad_pagada"));
        ref_pago.setCellValueFactory(new PropertyValueFactory<informacionPagofin1, String>("ref_pago"));
        refpago2.setCellValueFactory(new PropertyValueFactory<informacionPagofin1, String>("ref_pago2"));

        num_comprobante.setCellValueFactory(new PropertyValueFactory<informacionPagofin2, String>("num_comprobante"));
        valor.setCellValueFactory(new PropertyValueFactory<informacionPagofin2, String>("valor"));
        num_cuenta.setCellValueFactory(new PropertyValueFactory<informacionPagofin2, String>("num_cuenta"));
        tipo_cuenta.setCellValueFactory(new PropertyValueFactory<informacionPagofin2, String>("tipo_cuenta"));
        fecha_hora_pago.setCellValueFactory(new PropertyValueFactory<informacionPagofin2, String>("fecha_pago"));

        this.resources = resources;
        this.location = location;


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
