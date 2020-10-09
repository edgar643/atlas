/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.MenuModel;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class GiroNalInitController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private RadioButton benefRadio;
    @FXML
    private Button continuar_op;
    @FXML
    private RadioButton giradorRadio;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    public static AtomicBoolean isGirador = new AtomicBoolean(false);
    public static AtomicBoolean isBeneficiario = new AtomicBoolean(false);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert benefRadio != null : "fx:id=\"benefRadio\" was not injected: check your FXML file 'GiroNalInit.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'GiroNalInit.fxml'.";
        assert giradorRadio != null : "fx:id=\"giradorRadio\" was not injected: check your FXML file 'GiroNalInit.fxml'.";

    }

    public void mostrarGirosInit() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/GiroNalInit.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

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

                    continuar_op.setDisable(true);
                    label_menu.setVisible(false);

                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    Atlas.vista.show();
                } catch (Exception ex) {

                    new ModalMensajes("Error en la aplicacion \n , es posible que la consulta no se haya realizado correctamente , por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }

    @FXML
    void continuar_op(ActionEvent event) {
        if (giradorRadio.isSelected()) {

            isGirador.set(true);
            isBeneficiario.set(false);

            final Parent frameGnral = Atlas.vista.getScene().getRoot();
            final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
            // se limpia el menu de los productos de saldos        
            final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
            //final TitledPane menu_tdcprepago = Menu.getPanes().get(12);
            MarcoPrincipalController.ListMenuGirosNal.clear();
            MarcoPrincipalController.rootNodeGirosNal = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_NAL);

            final TreeItem<String> nodogiros = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_NAL);
            final TreeItem<String> nodogirosinfognral = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_INFO_GENERAL);
            final TreeItem<String> nodogirosCancelacion = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_CANCELACION);

            MarcoPrincipalController.rootNodeGirosNal.getChildren().add(nodogiros);
            MarcoPrincipalController.rootNodeGirosNal.getChildren().add(nodogirosinfognral);
            MarcoPrincipalController.rootNodeGirosNal.getChildren().add(nodogirosCancelacion);

            // se construye el arbol de los productos tipoCuenta-cuentas
            for (MenuModel menus : MarcoPrincipalController.ListMenuGirosNal) {
                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                boolean found = false;
                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeGirosNal.getChildren()) {
                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                        depNode.getChildren().add(empLeaf);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    final TreeItem<String> depNode = new TreeItem<String>(
                            menus.getMenuPadre());
                    MarcoPrincipalController.rootNodeGirosNal.getChildren().add(depNode);
                    if (!empLeaf.getValue().isEmpty()) {
                        depNode.getChildren().add(empLeaf);
                    }

                }
            }
            MarcoPrincipalController.arbolGirosNal.setRoot(MarcoPrincipalController.rootNodeGirosNal);
            MarcoPrincipalController.arbolGirosNal.showRootProperty().setValue(Boolean.FALSE);

            try {
                final TreeView<String> arbolGirosNal = (TreeView<String>) pane.lookup("#arbolGirosNal");
                arbolGirosNal.setDisable(false);
                arbolGirosNal.getSelectionModel().clearSelection();
                pane.getChildren().remove(3);

            } catch (Exception ex) {
                 Logger.getLogger(GiroNalInitController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {

                GirosNalInfoGeneralController.numpagina.set(0);
                GirosNalInfoGeneralController.indicadorRegistros = "N";
                GirosNalInfoGeneralController.registros.clear();
                GirosNalCancelacionController.numpagina.set(0);
                GirosNalCancelacionController.indicadorRegistros = "N";
                GirosNalCancelacionController.registros.clear();

            } catch (Exception ex) {
                Logger.getLogger(GiroNalInitController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (benefRadio.isSelected()) {

            isGirador.set(false);
            isBeneficiario.set(true);

            final Parent frameGnral = Atlas.vista.getScene().getRoot();
            final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
            // se limpia el menu de los productos de saldos        
            final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
            //final TitledPane menu_tdcprepago = Menu.getPanes().get(12);
            MarcoPrincipalController.ListMenuGirosNal.clear();
            MarcoPrincipalController.rootNodeGirosNal = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_NAL);

            final TreeItem<String> nodogiro = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_NAL);
            final TreeItem<String> nodogirosinfognral = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_INFO_GENERAL);
            // final TreeItem<String> nodogirosCancelacion = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_CANCELACION);

            MarcoPrincipalController.rootNodeGirosNal.getChildren().add(nodogiro);
            MarcoPrincipalController.rootNodeGirosNal.getChildren().add(nodogirosinfognral);

            // se construye el arbol de los productos tipoCuenta-cuentas
            for (MenuModel menus : MarcoPrincipalController.ListMenuGirosNal) {
                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                boolean found = false;
                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeGirosNal.getChildren()) {
                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                        depNode.getChildren().add(empLeaf);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    final TreeItem<String> depNode = new TreeItem<String>(
                            menus.getMenuPadre());
                    MarcoPrincipalController.rootNodeGirosNal.getChildren().add(depNode);
                    if (!empLeaf.getValue().isEmpty()) {
                        depNode.getChildren().add(empLeaf);
                    }

                }
            }
            MarcoPrincipalController.arbolGirosNal.setRoot(MarcoPrincipalController.rootNodeGirosNal);
            MarcoPrincipalController.arbolGirosNal.showRootProperty().setValue(Boolean.FALSE);

            try {
                final TreeView<String> arbolGirosNal = (TreeView<String>) pane.lookup("#arbolGirosNal");
                arbolGirosNal.setDisable(false);
                arbolGirosNal.getSelectionModel().clearSelection();
                pane.getChildren().remove(3);

            } catch (Exception ex) {
                Logger.getLogger(GiroNalInitController.class.getName()).log(Level.SEVERE, null, ex);

            }
            try {

                GirosNalInfoGeneralController.numpagina.set(0);
                GirosNalInfoGeneralController.indicadorRegistros = "N";
                GirosNalInfoGeneralController.registros.clear();
                GirosNalCancelacionController.numpagina.set(0);
                GirosNalCancelacionController.indicadorRegistros = "N";
                GirosNalCancelacionController.registros.clear();

            } catch (Exception ex) {
                Logger.getLogger(GiroNalInitController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // menu_tdcprepago.setExpanded(true);          
        }

    }

    @FXML
    void selbenefRadio(ActionEvent event) {
        if (giradorRadio.isSelected()) {
            giradorRadio.setSelected(false);
        }

        if (benefRadio.isSelected()) {
            continuar_op.setDisable(false);
        } else {
            continuar_op.setDisable(true);
        }

    }

    @FXML
    void selgiradorRadio(ActionEvent event) {
        if (benefRadio.isSelected()) {
            benefRadio.setSelected(false);
        }

        if (giradorRadio.isSelected()) {
            continuar_op.setDisable(false);
        } else {
            continuar_op.setDisable(true);
        }
    }
}
