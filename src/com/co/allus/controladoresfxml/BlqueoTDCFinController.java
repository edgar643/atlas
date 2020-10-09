/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.bloqueos.DatosTDCBloqueos;
import com.co.allus.modelo.bloqueos.tablaFinBloqueos;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class BlqueoTDCFinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<tablaFinBloqueos, String> fechahora;
    @FXML
    private Label mensajefinbloqtdc;
    @FXML
    private TableView<tablaFinBloqueos> tablabloqtdcfin;
    @FXML
    private Button terminar_trx;
    @FXML
    private AnchorPane AnchorPane;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @FXML
    void aceptar(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception e) {
                    Logger.getGlobal().log(Level.SEVERE, e.toString());

                }
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'BlqueoTDCFin.fxml'.";
        assert fechahora != null : "fx:id=\"fechahora\" was not injected: check your FXML file 'BlqueoTDCFin.fxml'.";
        assert mensajefinbloqtdc != null : "fx:id=\"mensajefinbloqtdc\" was not injected: check your FXML file 'BlqueoTDCFin.fxml'.";
        assert tablabloqtdcfin != null : "fx:id=\"tablabloqtdcfin\" was not injected: check your FXML file 'BlqueoTDCFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'BlqueoTDCFin.fxml'.";

        this.location = url;
        this.resources = rb;

        fechahora.setCellValueFactory(new PropertyValueFactory<tablaFinBloqueos, String>("valor"));

    }

    public void mostrarBloqueoTDCfin(final DatosTDCBloqueos tarjetaBloqueada, final String fechabloqueo) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/BlqueoTDCFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final TableView<tablaFinBloqueos> tabladesbloqueoSCfin = (TableView<tablaFinBloqueos>) root.lookup("#tablabloqtdcfin");
                    final Label mensaje = (Label) root.lookup("#mensajefinbloqtdc");

                    mensaje.setText("La TDC " + tarjetaBloqueada.getTipoTarjeta() + " ha sido bloqueada ");
                    tabladesbloqueoSCfin.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    /**
                     * se cargan los valores de la informacion del finpago
                     */
                    final ObservableList<tablaFinBloqueos> datosInfodesbloqSC = FXCollections.observableArrayList();
                    final tablaFinBloqueos datosdesbloqueoSC = new tablaFinBloqueos(fechabloqueo);
                    datosInfodesbloqSC.add(datosdesbloqueoSC);
                    tabladesbloqueoSCfin.setItems(datosInfodesbloqSC);

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


                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado correctamente ,"
                            + " por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });


    }
}
