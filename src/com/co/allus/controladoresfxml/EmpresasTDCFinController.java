/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.informaciontdc.infotablatdcbuscar;
import java.net.URL;
import java.util.ResourceBundle;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.informaciontdc.dataTablaTdc;
import com.co.allus.tareas.BusqFechaTask;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class EmpresasTDCFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_entidad;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_fecha;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_hora;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_mensaje;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_origen;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_pais;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_respuesta;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_tpo;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_transaccion;
    @FXML
    private TableColumn<infotablatdcbuscar, String> Col_valor;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private RadioButton rbanteayer;
    @FXML
    private RadioButton rbayer;
    @FXML
    private RadioButton rbhoy;
    @FXML
    private TableView<infotablatdcbuscar> tabla_datos;
    @FXML
    private Button terminar_trx;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TextField tfTdc;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private RadioButton rbtodos;
    public static ObservableList<infotablatdcbuscar> datosTabla = FXCollections.observableArrayList();
    private static GestorDocumental docs = new GestorDocumental();
    private static List<infotablatdcbuscar> dataTabla;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert rbtodos != null : "fx:id=\"rbtodos\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_entidad != null : "fx:id=\"Col_entidad\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_fecha != null : "fx:id=\"Col_fecha\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_hora != null : "fx:id=\"Col_hora\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_mensaje != null : "fx:id=\"Col_mensaje\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_origen != null : "fx:id=\"Col_origen\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_pais != null : "fx:id=\"Col_pais\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_respuesta != null : "fx:id=\"Col_respuesta\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_tpo != null : "fx:id=\"Col_tpo\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_transaccion != null : "fx:id=\"Col_transaccion\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert Col_valor != null : "fx:id=\"Col_valor\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert rbanteayer != null : "fx:id=\"rbanteayer\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert rbayer != null : "fx:id=\"rbayer\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert rbhoy != null : "fx:id=\"rbhoy\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert tfBuscar != null : "fx:id=\"tfBuscar\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        assert tfTdc != null : "fx:id=\"tfTdc\" was not injected: check your FXML file 'EmpresasTDCFin.fxml'.";
        tfBuscar.setDisable(true);
        this.resources = rb;
        this.location = url;

        Col_tpo.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_tpo"));
        Col_hora.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_hora"));
        Col_fecha.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_fecha"));
        Col_valor.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_valor"));
        Col_transaccion.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_transaccion"));
        Col_respuesta.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_respuesta"));
        Col_mensaje.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_mensaje"));
        Col_pais.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_pais"));
        Col_entidad.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_entidad"));
        Col_origen.setCellValueFactory(new PropertyValueFactory<infotablatdcbuscar, String>("Col_origen"));

    }

    @FXML
    void terminar_trx(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    dataTabla.clear();
                    Atlas.getVista().gotoPrincipal();

                } catch (Exception ex) {
                    Logger.getLogger(EmpresasTDCFinController.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        });
    }

    @FXML
    void rbtodos(ActionEvent event) {
        tfBuscar.setText(" ");
        rbhoy.setSelected(false);
        rbayer.setSelected(false);
        rbanteayer.setSelected(false);
        BusqSerialTask(tfBuscar.getText());
    }

    @FXML
    void rbhoy(ActionEvent event) {
        tfBuscar.setText(docs.obtenerFechaActualComp());
        rbanteayer.setSelected(false);
        rbayer.setSelected(false);
        rbtodos.setSelected(false);
        BusqSerialTask(tfBuscar.getText());

    }

    @FXML
    void rbanteayer(ActionEvent event) {

        tfBuscar.setText(docs.obtenerFechaActualDiaAnteayer());
        rbayer.setSelected(false);
        rbhoy.setSelected(false);
        rbtodos.setSelected(false);
        BusqSerialTask(tfBuscar.getText());

    }

    @FXML
    void rbayer(ActionEvent event) {
        tfBuscar.setText(docs.obtenerFechaActualDiaAnt());
        rbhoy.setSelected(false);
        rbanteayer.setSelected(false);
        rbtodos.setSelected(false);
        BusqSerialTask(tfBuscar.getText());
    }

    public void mostrarTDCEmpresasFin(final List<infotablatdcbuscar> data, final dataTablaTdc datatdc) {
        this.dataTabla = data;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/EmpresasTDCFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);
                    final TableView<infotablatdcbuscar> tabla_datos = (TableView<infotablatdcbuscar>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final RadioButton rbhoy = (RadioButton) root.lookup("#rbhoy");
                    final RadioButton rbayer = (RadioButton) root.lookup("#rbayer");
                    final RadioButton rbanteayer = (RadioButton) root.lookup("#rbanteayer");
                    final TextField tfBuscar = (TextField) root.lookup("#tfBuscar");
                    final TextField tfTdc = (TextField) root.lookup("#tfTdc");

                    tfTdc.setText(datatdc.getNum_tdc());

                    final DropShadow shadow = new DropShadow();
                    terminar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            terminar_trx.setCursor(Cursor.HAND);
                            terminar_trx.setEffect(shadow);
                        }
                    });

                    terminar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            terminar_trx.setCursor(Cursor.DEFAULT);
                            terminar_trx.setEffect(null);
                        }
                    });

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    //final ObservableList<infotablatdcbuscar> datosTabla = FXCollections.observableArrayList();
                    datosTabla.clear();
                    datosTabla.addAll(data);

                    final int numpag = datosTabla.size() % rowsPerPage() == 0 ? datosTabla.size() / rowsPerPage() : datosTabla.size() / rowsPerPage() + 1;

                    pagination = new Pagination(numpag, 0);
                    pagination.setPageFactory(new Callback<Integer, Node>() {
                        @Override
                        public Node call(final Integer pageIndex) {
                            if (pageIndex > numpag) {
                                return null;
                            } else {
                                int lastIndex = 0;
                                int displace = datosTabla.size() % rowsPerPage();
                                if (displace >= 0) {
                                    lastIndex = datosTabla.size() / rowsPerPage();
                                } else {
                                    lastIndex = datosTabla.size() / rowsPerPage() - 1;
                                }
                                int page = pageIndex * itemsPerPage();

                                for (int i = page; i < page + itemsPerPage(); i++) {

                                    if (lastIndex == pageIndex) {
                                        final List<infotablatdcbuscar> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infotablatdcbuscar> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
                                        tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                    }

                                    panel_tabla.getChildren().clear();
                                    tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                    panel_tabla.getChildren().add(tabla_datos);
                                    panel_tabla.setVisible(true);
                                }
                                return panel_tabla;
                            }
                        }
                    });

                    pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                            currentpageindex = newValue.intValue();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tabla_datos.getItems().setAll(datosTabla.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= datosTabla.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : datosTabla.size())));
                                }
                            });

                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    root.getChildren().add(pagination);
                    root.getChildren().get(13).setLayoutX(7);
                    root.getChildren().get(13).setLayoutY(197);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que el  pago se haya realizado correctamente , por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }

    private void BusqSerialTask(final String fecha) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infotablatdcbuscar>> TaskTablaTdc = new BusqFechaTask(fecha);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(TaskTablaTdc.progressProperty());
                    veil.visibleProperty().bind(TaskTablaTdc.runningProperty());
                    p.visibleProperty().bind(TaskTablaTdc.runningProperty());
                    tabla_datos.itemsProperty().bind(TaskTablaTdc.valueProperty());
                    TaskTablaTdc.reset();
                    TaskTablaTdc.start();

                    TaskTablaTdc.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<infotablatdcbuscar> TablaTdc = TaskTablaTdc.getValue();

//                            System.out.println("TERMINO TAREA");
                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaTdc.size() % rowsPerPageSr() == 0 ? TablaTdc.size() / rowsPerPageSr() : TablaTdc.size() / rowsPerPageSr() + 1;

                                pagination = new Pagination(numpag, 0);
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaTdc.size() % rowsPerPageSr();
                                            if (displace >= 0) {
                                                lastIndex = TablaTdc.size() / rowsPerPageSr();
                                            } else {
                                                lastIndex = TablaTdc.size() / rowsPerPageSr() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infotablatdcbuscar> subList = TablaTdc.subList(pageIndex * rowsPerPageSr(), pageIndex * rowsPerPageSr() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infotablatdcbuscar> subList = TablaTdc.subList(pageIndex * rowsPerPageSr(), pageIndex * rowsPerPageSr() + rowsPerPageSr());
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                }

                                                panel_tabla.getChildren().clear();
                                                tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                                panel_tabla.getChildren().add(tabla_datos);
                                                panel_tabla.setVisible(true);
                                            }
                                            return panel_tabla;
                                        }
                                    }
                                });

                                pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                    @Override
                                    public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                        currentpageindex = newValue.intValue();
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                tabla_datos.getItems().setAll(TablaTdc.subList(newValue.intValue() * rowsPerPageSr(), ((newValue.intValue() * rowsPerPageSr() + rowsPerPageSr() <= TablaTdc.size()) ? newValue.intValue() * rowsPerPageSr() + rowsPerPageSr() : TablaTdc.size())));
                                            }
                                        });

                                    }
                                });

                                /**
                                 * fin configuracion de la paginacion
                                 */
                                try {
                                    AnchorPane.getChildren().remove(13);
                                } catch (Exception ex) {
                                    Logger.getLogger(EliminacionCtasAchFinController.class.getName()).log(Level.WARNING, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(13).setLayoutX(7);
                                AnchorPane.getChildren().get(13).setLayoutY(197);
                                pagination.setVisible(true);
                            } catch (Exception ex) {
                                    Logger.getLogger(EliminacionCtasAchFinController.class.getName()).log(Level.WARNING, null, ex);
                            }
                        }
                    });

                    TaskTablaTdc.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                        }
                    });

                    TaskTablaTdc.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {
                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 5;
    }

    public int rowsPerPageSr() {
        return 5;
    }
}
