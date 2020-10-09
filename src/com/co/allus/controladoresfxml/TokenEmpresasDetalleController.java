/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.tokenempresas.infoDetalletf;
import com.co.allus.modelo.tokenempresas.infoTablaDetalle;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class TokenEmpresasDetalleController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTablaDetalle, String> canal;
    @FXML
    private Button salir_op;
    @FXML
    private TableColumn<infoTablaDetalle, String> costo_novedad;
    @FXML
    private TableColumn<infoTablaDetalle, String> estado_novedad;
    @FXML
    private TableColumn<infoTablaDetalle, String> fecha_novedad;
    @FXML
    private TableColumn<infoTablaDetalle, String> hora_novedad;
    @FXML
    private Button regresar_op;
    @FXML
    private TableColumn<infoTablaDetalle, String> novedad;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TextField tfEsquema_Seguridad;
    @FXML
    private TextField tfEstado_Empresa;
    @FXML
    private TextField tfId_Usuario;
    @FXML
    private TextField tfNit;
    @FXML
    private TextField tfNombre_Usuario;
    @FXML
    private TableView<infoTablaDetalle> tabla_datos;
    @FXML
    private Label titulo;
    private static List<infoTablaDetalle> dataTabla;
    private static GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        assert canal != null : "fx:id=\"canal\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert salir_op != null : "fx:id=\"salir_op\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert costo_novedad != null : "fx:id=\"costo_novedad\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert estado_novedad != null : "fx:id=\"estado_novedad\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert fecha_novedad != null : "fx:id=\"fecha_novedad\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert hora_novedad != null : "fx:id=\"hora_novedad\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert regresar_op != null : "fx:id=\"regresar_op\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert novedad != null : "fx:id=\"novedad\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert tfEsquema_Seguridad != null : "fx:id=\"tfEsquema_Seguridad\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert tfEstado_Empresa != null : "fx:id=\"tfEstado_Empresa\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert tfId_Usuario != null : "fx:id=\"tfId_Usuario\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert tfNit != null : "fx:id=\"tfNit\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert tfNombre_Usuario != null : "fx:id=\"tfNombre_Usuario\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";
        assert titulo != null : "fx:id=\"titulo\" was not injected: check your FXML file 'TokenEmpresasDetalle.fxml'.";

        this.location = url;
        this.resources = rb;
        progreso.setVisible(false);

        novedad.setCellValueFactory(new PropertyValueFactory<infoTablaDetalle, String>("novedad"));
        estado_novedad.setCellValueFactory(new PropertyValueFactory<infoTablaDetalle, String>("estado_novedad"));
        costo_novedad.setCellValueFactory(new PropertyValueFactory<infoTablaDetalle, String>("costo_novedad"));
        fecha_novedad.setCellValueFactory(new PropertyValueFactory<infoTablaDetalle, String>("fecha_novedad"));
        hora_novedad.setCellValueFactory(new PropertyValueFactory<infoTablaDetalle, String>("hora_novedad"));
        canal.setCellValueFactory(new PropertyValueFactory<infoTablaDetalle, String>("canal"));

    }

    @FXML
    void regresar_op(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dataTabla.clear();

                TokenEmpresasController controller = new TokenEmpresasController();
                controller.mostrarDatosToken();
            }
        });
    }

    @FXML
    void salir_op(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    dataTabla.clear();
                    Atlas.getVista().gotoPrincipal();

                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });
    }

    public void mostrarDetalleToken(final List<infoTablaDetalle> data, final infoDetalletf infodetalletf) {
        this.dataTabla = data;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenEmpresasDetalle.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button regresar_op = (Button) root.lookup("#regresar_op");
                    final Button salir_op = (Button) root.lookup("#salir_op");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<infoTablaDetalle> tablaDatos = (TableView<infoTablaDetalle>) root.lookup("#tabla_datos");
                    final TextField tfNombre = (TextField) root.lookup("#tfNombre_Usuario");
                    final TextField tfId_Usuario = (TextField) root.lookup("#tfId_Usuario");


                    final TextField tfNit = (TextField) root.lookup("#tfNit");
                    final TextField tfEstado_Empresa = (TextField) root.lookup("#tfEstado_Empresa");
                    final TextField tfEsquema_Seguridad = (TextField) root.lookup("#tfEsquema_Seguridad");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    label_menu.setVisible(false);
                    final Cliente datosCliente = Cliente.getCliente();
                    tfNit.setText(datosCliente.getId_cliente());
                    tfId_Usuario.setText(infodetalletf.getId_usuario());
                    tfNombre.setText(data.get(0).nombre_usuarioProperty().getValue());
                    tfEstado_Empresa.setText(infodetalletf.getEstado_empresa());
                    tfEsquema_Seguridad.setText(infodetalletf.getEsquema_seguridad());
                    final ObservableList<infoTablaDetalle> datos = FXCollections.observableArrayList();
                    datos.addAll(data);



                    tablaDatos.setItems(FXCollections.observableArrayList(datos));

                    panel_tabla.getChildren().clear();
                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    panel_tabla.getChildren().add(tablaDatos);
                    panel_tabla.setVisible(true);



//                    final DropShadow shadow = new DropShadow();
//                    regresar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    regresar_op.setCursor(Cursor.HAND);
//                                    regresar_op.setEffect(shadow);
//                                }
//                            });
//
//                    regresar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    regresar_op.setCursor(Cursor.DEFAULT);
//                                    regresar_op.setEffect(null);
//
//                                }
//                            });
//
//                    salir_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    salir_op.setCursor(Cursor.HAND);
//                                    salir_op.setEffect(shadow);
//                                }
//                            });
//
//                    salir_op.addEventHandler(MouseEvent.MOUSE_EXITED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    salir_op.setCursor(Cursor.DEFAULT);
//                                    salir_op.setEffect(null);
//
//                                }
//                            });

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }

                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);


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




                } catch (Exception ex) {
                    
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , informar al area tecnica","Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }
}
