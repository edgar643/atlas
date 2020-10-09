/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.consultamovimientos.infoTablaConsMov;
import com.co.allus.utils.TableUtils;
import java.net.URL;
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
public class ConsultaMovimientosFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTablaConsMov, String> cod_trx;
    @FXML
    private TableColumn<infoTablaConsMov, String> descripcion;
    @FXML
    private TableColumn<infoTablaConsMov, String> origen_trx;
    @FXML
    private TableColumn<infoTablaConsMov, String> fecha_trx;
    @FXML
    private TableColumn<infoTablaConsMov, String> numero_cheque;
    @FXML
    private TableColumn<infoTablaConsMov, String> ofi_org;
    @FXML
    private TableView<infoTablaConsMov> tablaDatos;
    @FXML
    private TableColumn<infoTablaConsMov, String> valor_transaccion;
    @FXML
    private Button retorno;
    @FXML
    private Button terminar_trx;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private Button copyToClipBoard;
    private static String opcionConsultaMov;
    private static String cuenta;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private static List<infoTablaConsMov> dataTabla;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cod_trx != null : "fx:id=\"cod_trx\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert origen_trx != null : "fx:id=\"origen_trx\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert fecha_trx != null : "fx:id=\"fecha_trx\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert numero_cheque != null : "fx:id=\"numero_cheque\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert ofi_org != null : "fx:id=\"ofi_org\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert retorno != null : "fx:id=\"retorno\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert valor_transaccion != null : "fx:id=\"valor_transaccion\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        assert copyToClipBoard != null : "fx:id=\"copyToClipBoard\" was not injected: check your FXML file 'ConsultaMovimientosFin.fxml'.";
        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cod_trx.setCellValueFactory(new PropertyValueFactory<infoTablaConsMov, String>("cod_trx"));
        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaConsMov, String>("descripcion"));
        origen_trx.setCellValueFactory(new PropertyValueFactory<infoTablaConsMov, String>("origen_trx"));
        fecha_trx.setCellValueFactory(new PropertyValueFactory<infoTablaConsMov, String>("fecha_trx"));
        numero_cheque.setCellValueFactory(new PropertyValueFactory<infoTablaConsMov, String>("num_cheque"));
        ofi_org.setCellValueFactory(new PropertyValueFactory<infoTablaConsMov, String>("ofi_origen"));
        valor_transaccion.setCellValueFactory(new PropertyValueFactory<infoTablaConsMov, String>("valor_transaccion"));




    }

    @FXML
    void copiarTabla(ActionEvent event) {
        TableUtils.copySelectionToClipboard(dataTabla);

    }

    @FXML
    void aceptar(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    dataTabla.clear();
                    Atlas.getVista().gotoPrincipal();

                } catch (Exception ex) {
                    Logger.getLogger(ConsultaMovimientosFinController.class.getName()).log(Level.SEVERE, ex.toString());
                }
            }
        });
    }

    @FXML
    void retornar(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dataTabla.clear();
                ConsultaMovimientosController controller = new ConsultaMovimientosController();
                controller.mostrarConsultaMovimientos(opcionConsultaMov);
            }
        });
    }

    public void mostrarConsultaMovimientosfin(final List<infoTablaConsMov> data, final String opcionConsMov, final String cuenta) {
        this.dataTabla = data;
        this.opcionConsultaMov = opcionConsMov;
        this.cuenta = cuenta;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaMovimientosFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button retorno = (Button) root.lookup("#retorno");
                    final Button CopiarTabla = (Button) root.lookup("#copyToClipBoard");
                    final Button botoncontinuarOp = (Button) root.lookup("#terminar_trx");
                    final TableView<infoTablaConsMov> tablaData = (TableView<infoTablaConsMov>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente() + "\n" + opcionConsMov + " : " + cuenta;
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

                    retorno.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    retorno.setCursor(Cursor.HAND);
                                    retorno.setEffect(shadow);
                                }
                            });

                    retorno.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    retorno.setCursor(Cursor.DEFAULT);
                                    retorno.setEffect(null);

                                }
                            });


                    CopiarTabla.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    CopiarTabla.setCursor(Cursor.HAND);
                                    CopiarTabla.setEffect(shadow);
                                }
                            });

                    CopiarTabla.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    CopiarTabla.setCursor(Cursor.DEFAULT);
                                    CopiarTabla.setEffect(null);

                                }
                            });


                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);


                    final ObservableList<infoTablaConsMov> cuentas = FXCollections.observableArrayList();
                    cuentas.addAll(data);
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
                                        final List<infoTablaConsMov> subList = cuentas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoTablaConsMov> subList = cuentas.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                    root.getChildren().get(4).setLayoutX(0);
                    root.getChildren().get(4).setLayoutY(43);
                    pagination.setVisible(true);
                    Atlas.vista.show();


                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }


            }
        });




    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
