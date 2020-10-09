/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.conexionDDE.DDE;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.bloqueos.tablaFinBloqueos;
import com.co.allus.utils.AtlasConstantes;
import java.io.IOException;
import java.net.URL;
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
public class BloqueoClavesTarjFinController implements Initializable {

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
    private Label mensajefinbloqclaves;
    @FXML
    private TableView<tablaFinBloqueos> tablabloqclavesfin;
    @FXML
    private Button terminar_trx;
    @FXML
    private Label titulosBloqueosclaves;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @FXML
    void aceptar(final ActionEvent event) {

        final DDE conexionDDe = new DDE();
        final ConectSS servicioSS = new ConectSS();
        try {

            conexionDDe.ejecutarTramaASoftphone("%300%8%BLQA%N%");

        } catch (Exception ex) {

            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
            try {


                servicioSS.enviarRecibirServidorsoftphone(AtlasConstantes.HOST_FENIX, AtlasConstantes.PUERTO_FENIX, "%300%8%BLQA%N% % % % % % % %");

            } catch (Exception ex2) {

                gestorDoc.imprimir("Error en la aplicacion -->" + ex2.toString() + "  :" + gestorDoc.obtenerHoraActual());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new ModalMensajes("Error en la comunicacion con  Softphone\n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                    }
                });
            }

        } finally {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Atlas.getVista().gotoPrincipal();
                    } catch (IOException ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert fechahora != null : "fx:id=\"fechahora\" was not injected: check your FXML file 'BloqueoClavesTarjFin.fxml'.";
        assert mensajefinbloqclaves != null : "fx:id=\"mensajefinbloqclaves\" was not injected: check your FXML file 'BloqueoClavesTarjFin.fxml'.";
        assert tablabloqclavesfin != null : "fx:id=\"tablabloqclavesfin\" was not injected: check your FXML file 'BloqueoClavesTarjFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'BloqueoClavesTarjFin.fxml'.";
        assert titulosBloqueosclaves != null : "fx:id=\"titulosBloqueosclaves\" was not injected: check your FXML file 'BloqueoClavesTarjFin.fxml'.";

        this.location = url;
        this.resources = rb;

        fechahora.setCellValueFactory(new PropertyValueFactory<tablaFinBloqueos, String>("valor"));

    }

    public void mostrarBloqueoClavestarjfin(final String fechaBloqueo) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/BloqueoClavesTarjFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<tablaFinBloqueos> tabladesbloqueoSCfin = (TableView<tablaFinBloqueos>) root.lookup("#tablabloqclavesfin");

                    tabladesbloqueoSCfin.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    /**
                     * se cargan los valores de la informacion del finpago
                     */
                    final ObservableList<tablaFinBloqueos> datosInfodesbloqSC = FXCollections.observableArrayList();
                    final tablaFinBloqueos datosdesbloqueoSC = new tablaFinBloqueos(fechaBloqueo);
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

                    label_menu.setVisible(false);
                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado correctamente , "
                            + "por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });



    }
}
