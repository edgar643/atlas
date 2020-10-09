/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.evidente.ModelEvidente;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class EvidenteTrxpreguntasController implements Initializable {

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
    private ComboBox<String> combopreg1;
    @FXML
    private ComboBox<String> combopreg2;
    @FXML
    private ComboBox<String> combopreg3;
    @FXML
    private ComboBox<String> combopreg4;
    @FXML
    private Button continuar_op;
    @FXML
    private Label descpreg1;
    @FXML
    private Label descpreg2;
    @FXML
    private Label descpreg3;
    @FXML
    private Label descpreg4;
    @FXML
    private Label titulosEvidente;
    private static String menu1 = "";
    private static int flujo1 = 0;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert combopreg1 != null : "fx:id=\"combopreg1\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert combopreg2 != null : "fx:id=\"combopreg2\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert combopreg3 != null : "fx:id=\"combopreg3\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert combopreg4 != null : "fx:id=\"combopreg4\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert descpreg1 != null : "fx:id=\"descpreg1\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert descpreg2 != null : "fx:id=\"descpreg2\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert descpreg3 != null : "fx:id=\"descpreg3\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert descpreg4 != null : "fx:id=\"descpreg4\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";
        assert titulosEvidente != null : "fx:id=\"titulosEvidente\" was not injected: check your FXML file 'EvidenteTrxpreguntas.fxml'.";

    }

    @FXML
    void cancel_op(ActionEvent event) {

        try {
            Atlas.getVista().gotoPrincipal();
        } catch (IOException ex) {
            gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
        }

    }

    @FXML
    void continuar_OP(ActionEvent event) {
        ModelEvidente modeloIn = new ModelEvidente();

        HashMap<String, String> respuestas = new HashMap<String, String>();

        respuestas.put("Respuesta1", "00" + combopreg1.getSelectionModel().getSelectedIndex() + "###" + combopreg1.getSelectionModel().getSelectedItem());
        respuestas.put("Respuesta2", "00" + combopreg2.getSelectionModel().getSelectedIndex() + "###" + combopreg2.getSelectionModel().getSelectedItem());
        respuestas.put("Respuesta3", "00" + combopreg3.getSelectionModel().getSelectedIndex() + "###" + combopreg3.getSelectionModel().getSelectedItem());
        respuestas.put("Respuesta4", "00" + combopreg4.getSelectionModel().getSelectedIndex() + "###" + combopreg4.getSelectionModel().getSelectedItem());

        modeloIn.setRespSelect(respuestas);

        final EvidenteTrxConfirmController pintar = new EvidenteTrxConfirmController();
        pintar.mostrarConfirmacionPreguntasEvidente(modeloIn, menu1, flujo1);

    }

    @FXML
    void seleccioncombopreg1(ActionEvent event) {
        combox();
    }

    @FXML
    void seleccioncombopreg2(ActionEvent event) {
        combox();
    }

    @FXML
    void seleccioncombopreg3(ActionEvent event) {
        combox();
    }

    @FXML
    void seleccioncombopreg4(ActionEvent event) {
        combox();
    }

    private void combox() {

        try {
            if ((combopreg1 != null) && (combopreg2 != null) && (combopreg3 != null) && (combopreg4 != null)) {
//            if ((combopreg1.getSelectionModel().getSelectedItem()!= null) && (combopreg2.getSelectionModel().getSelectedItem() != null) && 
//                    (combopreg3.getSelectionModel().getSelectedItem() != null) && (combopreg4.getSelectionModel().getSelectedItem() != null)) {  
                if (combopreg1.getSelectionModel().getSelectedItem().equals("Seleccione")
                        || combopreg2.getSelectionModel().getSelectedItem().equals("Seleccione")
                        || combopreg3.getSelectionModel().getSelectedItem().equals("Seleccione")
                        || combopreg4.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    continuar_op.setDisable(true);
                } else {
                    continuar_op.setDisable(false);
                    // activo boton continuar
                }
//            }
            }
        } catch (Exception ex) {

            Logger.getLogger(EvidenteTrxpreguntasController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void mostrarEvidenteCuestionarioPreguntas(final ModelEvidente modelo, final String menu, final int flujo) {

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/EvidenteTrxpreguntas.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final Button cancelar = (Button) root.lookup("#cancelar");
                        final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");

                        final Label titulosEvidente = (Label) root.lookup("#titulosEvidente");
                        titulosEvidente.setText(menu);
//                        System.out.println("------ Lo que pasa en menu 2:" + menu);
                        menu1 = menu;
                        flujo1 = flujo;

                        ModelEvidente modeloIn = new ModelEvidente();
                        modeloIn = modelo;

                        final Label descpreg1 = (Label) root.lookup("#descpreg1");
                        final Label descpreg2 = (Label) root.lookup("#descpreg2");
                        final Label descpreg3 = (Label) root.lookup("#descpreg3");
                        final Label descpreg4 = (Label) root.lookup("#descpreg4");

                        final String PregResp = modelo.getPregAndResp();
//                        System.out.println("PregResp-->" + PregResp);

//                        Preguntas
                        descpreg1.setText(PregResp.split("%")[0].replaceAll("###", ","));
                        final String respuestas1[] = PregResp.split("%")[1].split("&");
                        int Resp1 = respuestas1.length - 1;

                        descpreg2.setText(respuestas1[Resp1].replaceAll("###", ","));
                        final String respuestas2[] = PregResp.split("%")[2].split("&");
                        int Resp2 = respuestas2.length - 1;

                        descpreg3.setText(respuestas2[Resp2].replaceAll("###", ","));
                        final String respuestas3[] = PregResp.split("%")[3].split("&");
                        int Resp3 = respuestas3.length - 1;

                        descpreg4.setText(respuestas3[Resp3].replaceAll("###", ","));
                        final String respuestas4[] = PregResp.split("%")[4].split("&");

//                        Combos
//                        final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                        final ComboBox<String> combopreg1 = (ComboBox<String>) root.lookup("#combopreg1");
                        final ObservableList<String> emptyList1 = FXCollections.emptyObservableList();
                        combopreg1.setItems(emptyList1);
                        final List<String> listpreg1 = new ArrayList<String>();
                        listpreg1.add("Seleccione");

                        for (int i = 0; i < respuestas1.length - 1; i++) {
                            final String add = respuestas1[i].split(",")[1].replaceAll("###", ",");
//                            System.out.println("ADD-" + add);
                            listpreg1.add(add);
                        }

                        final ComboBox<String> combopreg2 = (ComboBox<String>) root.lookup("#combopreg2");
                        final ObservableList<String> emptyList2 = FXCollections.emptyObservableList();
                        combopreg2.setItems(emptyList2);
                        final List<String> listpreg2 = new ArrayList<String>();
                        listpreg2.add("Seleccione");

                        for (int i = 0; i < respuestas2.length - 1; i++) {
                            listpreg2.add(respuestas2[i].split(",")[1].replaceAll("###", ","));
                        }

                        final ComboBox<String> combopreg3 = (ComboBox<String>) root.lookup("#combopreg3");
                        final ObservableList<String> emptyList3 = FXCollections.emptyObservableList();
                        combopreg3.setItems(emptyList3);
                        final List<String> listpreg3 = new ArrayList<String>();
                        listpreg3.add("Seleccione");
                        for (int i = 0; i < respuestas3.length - 1; i++) {
//                            System.out.println("-" + respuestas3[i]);
                            listpreg3.add(respuestas3[i].split(",")[1].replaceAll("###", ","));
                        }

                        final ComboBox<String> combopreg4 = (ComboBox<String>) root.lookup("#combopreg4");
                        final ObservableList<String> emptyList4 = FXCollections.emptyObservableList();
                        combopreg4.setItems(emptyList4);
                        final List<String> listpreg4 = new ArrayList<String>();
                        listpreg4.add("Seleccione");
                        for (int i = 0; i < respuestas4.length; i++) {
                            listpreg4.add(respuestas4[i].split(",")[1].replaceAll("###", ","));
                        }

                        combopreg1.setItems(FXCollections.observableArrayList(listpreg1));
                        combopreg2.setItems(FXCollections.observableArrayList(listpreg2));
                        combopreg3.setItems(FXCollections.observableArrayList(listpreg3));
                        combopreg4.setItems(FXCollections.observableArrayList(listpreg4));

                        if (modeloIn.getRespSelect().isEmpty()) {
                            combopreg1.getSelectionModel().select("Seleccione");
                        } else {
//                            
                            final String datoSelect1 = modeloIn.getRespSelect().get("Respuesta1").split("###")[1];
//                            System.out.println("datoSelect------------------------>" + datoSelect1 + "<----");
                            combopreg1.getSelectionModel().select(datoSelect1);
                        }

                        if (modeloIn.getRespSelect().isEmpty()) {
                            combopreg2.getSelectionModel().select("Seleccione");
                        } else {
                            final String datoSelect2 = modeloIn.getRespSelect().get("Respuesta2").split("###")[1];
                            combopreg2.getSelectionModel().select(datoSelect2);
                        }

                        if (modeloIn.getRespSelect().isEmpty()) {
                            combopreg3.getSelectionModel().select("Seleccione");
                        } else {

                            final String datoSelect3 = modeloIn.getRespSelect().get("Respuesta3").split("###")[1];
                            combopreg3.getSelectionModel().select(datoSelect3);
                        }

                        if (modeloIn.getRespSelect().isEmpty()) {
                            combopreg4.getSelectionModel().select("Seleccione");
                        } else {
                            final String datoSelect4 = modeloIn.getRespSelect().get("Respuesta4").split("###")[1];
                            combopreg4.getSelectionModel().select(datoSelect4);

                        }

                        final DropShadow shadow = new DropShadow();

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
