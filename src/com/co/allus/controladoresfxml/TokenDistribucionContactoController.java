/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.tokendistribucion.DatosDisToken;
import com.co.allus.modelo.tokendistribucion.InfoTokenContacto;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
public class TokenDistribucionContactoController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TextField tfCelular;
    @FXML
    private TextField tfCiudad;
    @FXML
    private TextField tfDepartamento;
    @FXML
    private TextField tfDireccionEnt;
    @FXML
    private TextField tfDocMens;
    @FXML
    private TextField tfDocumento;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfNit;
    @FXML
    private TextField tfNomMensajero;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNombreU;
    @FXML
    private TextField tfSerial;
    @FXML
    private TextField tfSucursal;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfTelefonoAlt;
    @FXML
    private TextField tfTipoContacto;
    @FXML
    private TextField tfTipoDoc;
    private static GestorDocumental docs = new GestorDocumental();
    public static ObservableList<InfoTokenContacto> datos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfCelular != null : "fx:id=\"tfCelular\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfCiudad != null : "fx:id=\"tfCiudad\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfDepartamento != null : "fx:id=\"tfDepartamento\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfDireccionEnt != null : "fx:id=\"tfDireccionEnt\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfDocMens != null : "fx:id=\"tfDocMens\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfDocumento != null : "fx:id=\"tfDocumento\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfID != null : "fx:id=\"tfID\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfNit != null : "fx:id=\"tfNit\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfNomMensajero != null : "fx:id=\"tfNomMensajero\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfNombre != null : "fx:id=\"tfNombre\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfNombreU != null : "fx:id=\"tfNombreU\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfSerial != null : "fx:id=\"tfSerial\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfSucursal != null : "fx:id=\"tfSucursal\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfTelefono != null : "fx:id=\"tfTelefono\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfTelefonoAlt != null : "fx:id=\"tfTelefonoAlt\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfTipoContacto != null : "fx:id=\"tfTipoContacto\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";
        assert tfTipoDoc != null : "fx:id=\"tfTipoDoc\" was not injected: check your FXML file 'TokenDistribucionContacto.fxml'.";


    }

    @FXML
    void regresar_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                TokenDistribucionController controller = new TokenDistribucionController();
                controller.mostrarTokenDistribuidos();
            }
        });
    }

    public void mostrarTokenContEnt(final List<InfoTokenContacto> data, final DatosDisToken datosditri, final String indMasReg, final int numpag) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenDistribucionContacto.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    label_menu.setVisible(false);

                    final TextField tfID = (TextField) root.lookup("#tfID");
                    final TextField tfNombre = (TextField) root.lookup("#tfNombre");
                    final TextField tfNit = (TextField) root.lookup("#tfNit");
                    final TextField tfSerial = (TextField) root.lookup("#tfSerial");
                    final TextField tfCelular = (TextField) root.lookup("#tfCelular");
                    final TextField tfCiudad = (TextField) root.lookup("#tfCiudad");
                    final TextField tfDepartamento = (TextField) root.lookup("#tfDepartamento");
                    final TextField tfDireccionEnt = (TextField) root.lookup("#tfDireccionEnt");
                    final TextField tfDocMens = (TextField) root.lookup("#tfDocMens");
                    final TextField tfDocumento = (TextField) root.lookup("#tfDocumento");
                    final TextField tfNomMensajero = (TextField) root.lookup("#tfNomMensajero");
                    final TextField tfNombreU = (TextField) root.lookup("#tfNombreU");
                    final TextField tfSucursal = (TextField) root.lookup("#tfSucursal");
                    final TextField tfTelefono = (TextField) root.lookup("#tfTelefono");
                    final TextField tfTelefonoAlt = (TextField) root.lookup("#tfTelefonoAlt");
                    final TextField tfTipoContacto = (TextField) root.lookup("#tfTipoContacto");
                    final TextField tfTipoDoc = (TextField) root.lookup("#tfTipoDoc");


                    final Button regresar_op = (Button) root.lookup("#regresar_op");
                    final Cliente datosCliente = Cliente.getCliente();

                    tfTelefono.setText(data.get(0).getColNumTelefono());
                    tfTelefonoAlt.setText(data.get(0).getColNumTelAlterno());
                    tfCelular.setText(data.get(0).getColNumCel());
                    tfDireccionEnt.setText(data.get(0).getColDireccion());
                    tfDepartamento.setText(data.get(0).getColCodDepartamento());
                    tfCiudad.setText(data.get(0).getColCodCiudad());
                    tfNomMensajero.setText(data.get(0).getColNombreMensajero().toLowerCase());
                    tfDocMens.setText(data.get(0).getColNumCcMensajero());
                    tfNombre.setText(datosditri.getNombre_usuario().toLowerCase());
                    tfNit.setText(datosCliente.getId_cliente());
                    tfID.setText(datosditri.getId_usuario());
                    tfSerial.setText(datosditri.getCodigo_serial());
                    tfSucursal.setText(data.get(0).getColSucursal());
                    tfTipoContacto.setText(data.get(0).getColTipoContacto());
                    tfTipoDoc.setText(data.get(0).getColTipodoc());
                    tfDocumento.setText(data.get(0).getColdocumento());


                    final DropShadow shadow = new DropShadow();
                    regresar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    regresar_op.setCursor(Cursor.HAND);
                                    regresar_op.setEffect(shadow);
                                }
                            });

                    regresar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    regresar_op.setCursor(Cursor.DEFAULT);
                                    regresar_op.setEffect(null);

                                }
                            });


                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }

                    datos.addAll(data);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();

                } catch (Exception ex) {
                    Logger.getGlobal().log(Level.SEVERE, ex.toString());
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n informar al área tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }
            }
        });


    }
}
