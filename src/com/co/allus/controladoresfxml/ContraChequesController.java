/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.contraCheques.dataContraorden;
import com.co.allus.modelo.contraCheques.infoTablaContraCheques;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ContraChequesController implements Initializable {

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
    private TableColumn<infoTablaContraCheques, String> numcta;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoTablaContraCheques> tablaDatos;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ContraCheques.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'ContraCheques.fxml'.";
        assert numcta != null : "fx:id=\"numcta\" was not injected: check your FXML file 'ContraCheques.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ContraCheques.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ContraCheques.fxml'.";
        this.location = url;
        this.resources = rb;

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        numcta.setCellValueFactory(new PropertyValueFactory<infoTablaContraCheques, String>("cuenta"));

    }

    public void mostrarContraCheques() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ContraCheques.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<infoTablaContraCheques> tablaData = (TableView<infoTablaContraCheques>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
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
                    label_menu.setVisible(false);




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

                    final TreeView<String> arbol_consMov = (TreeView<String>) pane.lookup("#arbol_movimientos");
                    if (arbol_consMov != null) {
                        arbol_consMov.setDisable(true);
                    }

                    final TreeView<String> arbol_conCheques = (TreeView<String>) pane.lookup("#arbol_contrabloqueos");
                    if (arbol_conCheques != null) {
                        arbol_conCheques.setDisable(true);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);





                    final ArrayList<String> DatosCta = datosCliente.getProductos().get(CodigoProductos.CUENTA_CORRIENTE);

                    final ObservableList<infoTablaContraCheques> cuentas = FXCollections.observableArrayList();

                    for (int i = 0; i < DatosCta.size(); i++) {
                        cuentas.add(new infoTablaContraCheques(DatosCta.get(i)));

                    }


                    /**
                     * configuracion de la paginacion
                     */
                    final int numpag = cuentas.size() % rowsPerPage() == 0 ? cuentas.size() / rowsPerPage() : cuentas.size() / rowsPerPage() + 1;

                    pagination = new Pagination(numpag, 0);
                    pagination.setPageFactory(new Callback<Integer, Node>() {
                        @Override
                        public Node call(final Integer pageIndex) {
                            if (pageIndex > numpag) {
                                return null;
                            } else {
                                int lastIndex = 0;
                                int displace = cuentas.size() % rowsPerPage();
                                if (displace >= 0) {
                                    lastIndex = cuentas.size() / rowsPerPage();
                                } else {
                                    lastIndex = cuentas.size() / rowsPerPage() - 1;
                                }
                                int page = pageIndex * itemsPerPage();

                                for (int i = page; i < page + itemsPerPage(); i++) {

                                    if (lastIndex == pageIndex) {
                                        final List<infoTablaContraCheques> subList = cuentas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoTablaContraCheques> subList = cuentas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    }

                                    panel_tabla.getChildren().clear();
                                    tablaData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                    panel_tabla.getChildren().add(tablaData);
                                    panel_tabla.setVisible(true);
                                }
                                return panel_tabla;
                            }
                        }
                    });
//

                    pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                            currentpageindex = newValue.intValue();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tablaData.getItems().setAll(cuentas.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= cuentas.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : cuentas.size())));
                                }
                            });

                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    root.getChildren().add(pagination);
                    root.getChildren().get(3).setLayoutX(172);
                    root.getChildren().get(3).setLayoutY(60);
                    pagination.setVisible(true);
                    Atlas.vista.show();


                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }


            }
        });




    }

    @FXML
    void cancelarOP(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    Logger.getLogger(ContraChequesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void continuarOP(final ActionEvent event) {
        final dataContraorden datosCCheque = dataContraorden.getDatosCCheque();
        datosCCheque.setNumcta(tablaDatos.getSelectionModel().getSelectedItem().getCuenta());
        dataContraorden.setDatosCCheque(datosCCheque);
        final ContraChequesDatosController controller = new ContraChequesDatosController();
        controller.mostrarContraChequesDatos(datosCCheque);

    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
