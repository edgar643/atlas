/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class RegistroAlertasFinController implements Initializable {
    
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar;
    @FXML
    private TextField cedulaCliente;
    @FXML
    private Label mensaje;
    @FXML
    private TextField mensajefin;
    @FXML
    private TextField nombreCliente;
    @FXML
    private Button parametrizar;
    @FXML
    private Label tituloregAln;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'RegistroAlertasFin.fxml'.";
        assert cedulaCliente != null : "fx:id=\"cedulaCliente\" was not injected: check your FXML file 'RegistroAlertasFin.fxml'.";
        assert mensaje != null : "fx:id=\"mensaje\" was not injected: check your FXML file 'RegistroAlertasFin.fxml'.";
        assert mensajefin != null : "fx:id=\"mensajefin\" was not injected: check your FXML file 'RegistroAlertasFin.fxml'.";
        assert nombreCliente != null : "fx:id=\"nombreCliente\" was not injected: check your FXML file 'RegistroAlertasFin.fxml'.";
        assert parametrizar != null : "fx:id=\"parametrizar\" was not injected: check your FXML file 'RegistroAlertasFin.fxml'.";
        assert tituloregAln != null : "fx:id=\"tituloregAln\" was not injected: check your FXML file 'RegistroAlertasFin.fxml'.";
    }
    
    public void mostrarRegistroAlertasFinController(final String isRegistro) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/RegistroAlertasFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button parametrizar_trx = (Button) root.lookup("#parametrizar");
                    final Button cancelar_trx = (Button) root.lookup("#cancelar");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    
                    final TextField nombreCliente = (TextField) root.lookup("#nombreCliente");
                    final TextField cedulaCliente = (TextField) root.lookup("#cedulaCliente");
                    final Label Titulo = (Label) root.lookup("#mensaje");
                    final Label TituloMain = (Label) root.lookup("#tituloregAln");
                    final TextField Titulo2 = (TextField) root.lookup("#mensajefin");
                    
                    if ("S".equalsIgnoreCase(isRegistro)) {
                        Titulo.setText("La actualización del servicio de Alertas y Notificaciones fue exitoso.\nLe informamos que a partir de este momento se harán\nefectivos los cambios.");
                        Titulo2.setText("Actualización de registros exitosa - para salir presione cancelar");
                        TituloMain.setText("ACTUALIZAR SERVICIO DE ALERTAS Y NOTIFICACIONES");
                    } else {
                        TituloMain.setText("REGISTRO AL  SERVICIO DE ALERTAS Y NOTIFICACIONES");
                        Titulo.setText("El registro al servicio de Alertas y Notificaciones fue exitoso.\nLe informamos que a partir de este momento se harán\nefectivos los cambios.");
                        Titulo2.setText("Registro exitoso - para salir presione cancelar");
                    }
                    
                    if (datosCliente.getClavesn().equalsIgnoreCase("V") || datosCliente.getClavesn().equalsIgnoreCase("K")) {
                        parametrizar_trx.setVisible(true);
                        
                    } else {
                        parametrizar_trx.setVisible(false);
                    }
                    
                    Titulo2.setEditable(false);
                    
                    
                    nombreCliente.setText(datosCliente.getNombre());
                    nombreCliente.setEditable(false);
                    cedulaCliente.setText(datosCliente.getId_cliente());
                    cedulaCliente.setEditable(false);
                    /**
                     *
                     */
                    final DropShadow shadow = new DropShadow();
                    
                    parametrizar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    parametrizar_trx.setCursor(Cursor.HAND);
                                    parametrizar_trx.setEffect(shadow);
                                }
                            });
                    
                    parametrizar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    parametrizar_trx.setCursor(Cursor.DEFAULT);
                                    parametrizar_trx.setEffect(null);
                                    
                                }
                            });
                    
                    cancelar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_trx.setCursor(Cursor.HAND);
                                    cancelar_trx.setEffect(shadow);
                                }
                            });
                    
                    cancelar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_trx.setCursor(Cursor.DEFAULT);
                                    cancelar_trx.setEffect(null);
                                    
                                }
                            });

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
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });
    }
    
    @FXML
    void cancelar_trx(ActionEvent event) {
        try {
            RegistroAlertasFinController.cancelartarea.set(true);
        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        } finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Atlas.getVista().gotoPrincipal();
                    } catch (IOException ex) {
                        gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
                    }
                }
            });
        }
    }
    
    @FXML
    void parametrizar_trx(ActionEvent event) {
        final ParametrizacionAlertasController controller = new ParametrizacionAlertasController();
        ParametrizacionAlertasController.parametrizacion.clear();
        ParametrizacionAlertasController.parametrizacion2.clear();
        ParametrizacionAlertasController.numpagina.set(0);
        controller.mostrarParamAlertas();
    }
}
