/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.SocketController;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.MenuModel;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.tdcprepago.DataAutenticacion;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class TPAutenticacionController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar_op;
    @FXML
    private CheckBox cbno1;
    @FXML
    private CheckBox cbno2;
    @FXML
    private CheckBox cbsi1;
    @FXML
    private CheckBox cbsi2;
    @FXML
    private TableColumn colPregunta;
    @FXML
    private TableColumn colRespuesta;
    @FXML
    private Button continuar_op;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<DataAutenticacion> tabla_datos;

    @FXML
    void continuar_op(ActionEvent event) {
        if (cbsi1.isSelected() && cbsi2.isSelected()) {
            SocketController.isAutenticadoTDCprep.set(true);

            final Parent frameGnral = Atlas.vista.getScene().getRoot();
            final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
            // se limpia el menu de los productos de saldos        
            final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
            //final TitledPane menu_tdcprepago = Menu.getPanes().get(12);
            MarcoPrincipalController.ListMenuTDCprepago.clear();
            MarcoPrincipalController.rootNodeTDCprepago = new TreeItem<String>(AtlasConstantes.ITEM_AUTENTICACION_PREPAGO);
            // final TreeItem<String> nodoautenticacion = new TreeItem<String>(AtlasConstantes.ITEM_AUTENTICACION_PREPAGO);
            final TreeItem<String> nodobloqueo = new TreeItem<String>(AtlasConstantes.ITEM_BLOQUEO_PREPAGO);

            // MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoautenticacion);
            MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodobloqueo);

            if (SocketController.isAutenticadoTDCprep.get()) {

                final TreeItem<String> nodoactivacion = new TreeItem<String>(AtlasConstantes.ITEM_ACTIVACION_PREPAGO);
                final TreeItem<String> nodoconsultas = new TreeItem<String>(AtlasConstantes.ITEM_CONSULTAS_PREPAGO);
                final TreeItem<String> nodoMovimientos = new TreeItem<String>(AtlasConstantes.ITEM_MOVIMIENTO_PREPAGO);
                //final TreeItem<String> nodoautorizacion = new TreeItem<String>(AtlasConstantes.ITEM_AUTORIZACIONES_PREPAGO);
                final TreeItem<String> nodosolclave = new TreeItem<String>(AtlasConstantes.ITEM_SOLCLAVE_PREPAGO);

                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoactivacion);
                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoconsultas);
                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoMovimientos);
                // MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoautorizacion);
                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodosolclave);


            }


            // se construye el arbol de los productos tipoCuenta-cuentas
            for (MenuModel menus : MarcoPrincipalController.ListMenuTDCprepago) {
                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                boolean found = false;
                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeTDCprepago.getChildren()) {
                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                        depNode.getChildren().add(empLeaf);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    final TreeItem<String> depNode = new TreeItem<String>(
                            menus.getMenuPadre());
                    MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(depNode);
                    if (!empLeaf.getValue().isEmpty()) {
                        depNode.getChildren().add(empLeaf);
                    }

                }
            }
            MarcoPrincipalController.arboltdcPrepago.setRoot(MarcoPrincipalController.rootNodeTDCprepago);
            MarcoPrincipalController.arboltdcPrepago.showRootProperty().setValue(Boolean.FALSE);

            try {
                final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                arboltdcPrepago.setDisable(false);
                arboltdcPrepago.getSelectionModel().clearSelection();
                pane.getChildren().remove(3);



            } catch (Exception e) {
                docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
            }

            // menu_tdcprepago.setExpanded(true);

        } else {

            new ModalMensajes("Autenticaci�n Fallida\nuna o m�s preguntas est�n incorrectas", "Preguntas Validaci�n", ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);

            SocketController.isAutenticadoTDCprep.set(false);

            final Parent frameGnral = Atlas.vista.getScene().getRoot();
            final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
            // se limpia el menu de los productos de saldos                    
            // final TitledPane menu_tdcprepago = Menu.getPanes().get(12);
            MarcoPrincipalController.ListMenuTDCprepago.clear();
            MarcoPrincipalController.rootNodeTDCprepago = new TreeItem<String>(AtlasConstantes.ITEM_AUTENTICACION_PREPAGO);
            final TreeItem<String> nodoautenticacion = new TreeItem<String>(AtlasConstantes.ITEM_AUTENTICACION_PREPAGO);
            final TreeItem<String> nodobloqueo = new TreeItem<String>(AtlasConstantes.ITEM_BLOQUEO_PREPAGO);

            MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoautenticacion);
            MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodobloqueo);

            if (SocketController.isAutenticadoTDCprep.get()) {

                final TreeItem<String> nodoactivacion = new TreeItem<String>(AtlasConstantes.ITEM_ACTIVACION_PREPAGO);
                final TreeItem<String> nodoconsultas = new TreeItem<String>(AtlasConstantes.ITEM_CONSULTAS_PREPAGO);
                final TreeItem<String> nodoMovimientos = new TreeItem<String>(AtlasConstantes.ITEM_MOVIMIENTO_PREPAGO);
                final TreeItem<String> nodoautorizacion = new TreeItem<String>(AtlasConstantes.ITEM_AUTORIZACIONES_PREPAGO);
                final TreeItem<String> nodosolclave = new TreeItem<String>(AtlasConstantes.ITEM_SOLCLAVE_PREPAGO);

                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoactivacion);
                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoconsultas);
                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoMovimientos);
                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoautorizacion);
                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodosolclave);

            }


            // se construye el arbol de los productos tipoCuenta-cuentas
            for (MenuModel menus : MarcoPrincipalController.ListMenuTDCprepago) {
                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                boolean found = false;
                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeTDCprepago.getChildren()) {
                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                        depNode.getChildren().add(empLeaf);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    final TreeItem<String> depNode = new TreeItem<String>(
                            menus.getMenuPadre());
                    MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(depNode);
                    if (!empLeaf.getValue().isEmpty()) {
                        depNode.getChildren().add(empLeaf);
                    }

                }
            }

            MarcoPrincipalController.arboltdcPrepago.setRoot(MarcoPrincipalController.rootNodeTDCprepago);
            MarcoPrincipalController.arboltdcPrepago.showRootProperty().setValue(Boolean.FALSE);

            try {

                final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                arboltdcPrepago.setDisable(false);
                arboltdcPrepago.getSelectionModel().clearSelection();

                pane.getChildren().remove(3);
            } catch (Exception e) {
                docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
            }
            //menu_tdcprepago.setExpanded(true);
        }
    }
    private SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat formatoOri = new SimpleDateFormat("yyyyMMdd");
    private GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancelar_op != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        assert cbno1 != null : "fx:id=\"cbno1\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        assert cbno2 != null : "fx:id=\"cbno2\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        assert cbsi1 != null : "fx:id=\"cbsi1\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        assert cbsi2 != null : "fx:id=\"cbsi2\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        assert colPregunta != null : "fx:id=\"colPregunta\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        assert colRespuesta != null : "fx:id=\"colRespuesta\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TPAutenticacion.fxml'.";
        this.location = url;
        this.resources = rb;
        continuar_op.setDisable(true);
        colPregunta.setCellValueFactory(new PropertyValueFactory<DataAutenticacion, String>("pregunta"));
        colRespuesta.setCellValueFactory(new PropertyValueFactory<DataAutenticacion, String>("respuesta"));

    }

    @FXML
    void cancelar_op(ActionEvent event) {
        final Parent frameGnral = Atlas.vista.getScene().getRoot();
        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
        final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
        arboltdcPrepago.setDisable(false);
        arboltdcPrepago.getSelectionModel().clearSelection();

        try {
            pane.getChildren().remove(3);

        } catch (Exception e) {
            docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }
    }

    @FXML
    void cbsi1(ActionEvent event) {

        if (cbsi1.isSelected()) {
            // continuar_op.setDisable(false);
            cbno1.setSelected(false);
            if (cbsi2.isSelected() || cbno2.isSelected()) {
                continuar_op.setDisable(false);
            } else {
                continuar_op.setDisable(true);
            }
        } else if (cbno1.isSelected()) {
            if (cbsi2.isSelected() || cbno2.isSelected()) {
                continuar_op.setDisable(false);
            } else {
                continuar_op.setDisable(true);
            }


        } else {
            continuar_op.setDisable(true);
        }

//        } else if (cbsi1.isSelected() && cbno2.isSelected()) {
//            cbsi2.setSelected(false);
//            cbno1.setSelected(false);
//
//        } else {
//           // continuar_op.setDisable(true);
//        }
    }

    @FXML
    void cbsi2(ActionEvent event) {

        if (cbsi2.isSelected()) {
            // continuar_op.setDisable(false);
            cbno2.setSelected(false);
            if (cbsi1.isSelected() || cbno1.isSelected()) {
                continuar_op.setDisable(false);
            } else {
                continuar_op.setDisable(true);
            }
        } else if (cbno2.isSelected()) {
            if (cbsi1.isSelected() || cbno1.isSelected()) {
                continuar_op.setDisable(false);
            } else {
                continuar_op.setDisable(true);
            }


        } else {
            continuar_op.setDisable(true);
        }
    }

    @FXML
    void cbno1(ActionEvent event) {

        if (cbno1.isSelected()) {
            // continuar_op.setDisable(true);
            cbsi1.setSelected(false);
            if (cbsi2.isSelected() || cbno2.isSelected()) {
                continuar_op.setDisable(false);
            } else {
                continuar_op.setDisable(true);
            }

        } else if (cbsi1.isSelected()) {
            if (cbsi2.isSelected() || cbno2.isSelected()) {
                continuar_op.setDisable(false);
            } else {
                continuar_op.setDisable(true);
            }


        } else {
            continuar_op.setDisable(true);
        }
//        else if (cbno1.isSelected() && cbsi2.isSelected()) {
//            cbno1.setSelected(false);
//
//        }
    }

    @FXML
    void cbno2(ActionEvent event) {

        if (cbno2.isSelected()) {
            // continuar_op.setDisable(true);
            cbsi2.setSelected(false);
            if (cbsi1.isSelected() || cbno1.isSelected()) {
                continuar_op.setDisable(false);
            } else {
                continuar_op.setDisable(true);
            }

        } else if (cbsi2.isSelected()) {
            if (cbsi1.isSelected() || cbno1.isSelected()) {
                continuar_op.setDisable(false);
            } else {
                continuar_op.setDisable(true);
            }


        } else {
            continuar_op.setDisable(true);
        }
    }

    public void mostrarAutPrepago() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TPAutenticacion.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_op");

                    final TableView<DataAutenticacion> tabla_datos = (TableView<DataAutenticacion>) root.lookup("#tabla_datos");

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<DataAutenticacion> dataTabla = FXCollections.observableArrayList();

                    String fechaExpedicion = "";
                    try {

                        fechaExpedicion = formato.format(formatoOri.parse(datosCliente.getFechaExpedicion()));

                    } catch (Exception e) {
                        fechaExpedicion = datosCliente.getFechaExpedicion();
                    }
                    // se agregan los datos a la tabla
                    dataTabla.add(new DataAutenticacion("�Cu�l es la fecha de expedici�n de su c�dula?", fechaExpedicion));
                    dataTabla.add(new DataAutenticacion("Lugar de expedici�n de la c�dula", datosCliente.getLugarExpedicion().toLowerCase().trim()));


                    tabla_datos.setItems(dataTabla);


                    /**
                     *
                     */
                    final DropShadow shadow = new DropShadow();

                    continuar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.HAND);
                                    continuar_op.setEffect(shadow);
                                }
                            });

                    continuar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.DEFAULT);
                                    continuar_op.setEffect(null);

                                }
                            });

                    cancelar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_op.setCursor(Cursor.HAND);
                                    cancelar_op.setEffect(shadow);
                                }
                            });

                    cancelar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_op.setCursor(Cursor.DEFAULT);
                                    cancelar_op.setEffect(null);

                                }
                            });


                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);



                    Atlas.vista.show();
                } catch (Exception ex) {

                    new ModalMensajes("Error en la aplicacion \n , es posible que la consulta no se haya realizado "
                            + "correctamente , por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }
}
