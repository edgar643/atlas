/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.transestadocuentas.dataCuentasInscritas;
import com.co.allus.modelo.transestadocuentas.infoFinCuentasinscritas;
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
public class EliminacionCtasAchFinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoFinCuentasinscritas, String> colBanco;
    @FXML
    private TableColumn<infoFinCuentasinscritas, String> colNumCuenta;
    @FXML
    private TableColumn<infoFinCuentasinscritas, String> colTipoCuenta;
    @FXML
    private TableView<infoFinCuentasinscritas> tabla_datos;
    @FXML
    private Button terminar_trx;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert colBanco != null : "fx:id=\"colBanco\" was not injected: check your FXML file 'eliminacionCtasAchFin.fxml'.";
        assert colNumCuenta != null : "fx:id=\"colNumCuenta\" was not injected: check your FXML file 'eliminacionCtasAchFin.fxml'.";
        assert colTipoCuenta != null : "fx:id=\"colTipoCuenta\" was not injected: check your FXML file 'eliminacionCtasAchFin.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'eliminacionCtasAchFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'eliminacionCtasAchFin.fxml'.";
        this.location = url;
        this.resources = rb;
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colBanco.setCellValueFactory(new PropertyValueFactory<infoFinCuentasinscritas, String>("banco"));
        colNumCuenta.setCellValueFactory(new PropertyValueFactory<infoFinCuentasinscritas, String>("numcta"));
        colTipoCuenta.setCellValueFactory(new PropertyValueFactory<infoFinCuentasinscritas, String>("tipocta"));

    }

    public void mostrarCtasACHfin(final dataCuentasInscritas infoCtas) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/eliminacionCtasAchFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final TableView<infoFinCuentasinscritas> tablaData = (TableView<infoFinCuentasinscritas>) root.lookup("#tabla_datos");
                    final ObservableList<infoFinCuentasinscritas> info = FXCollections.observableArrayList();
                    info.add(new infoFinCuentasinscritas(infoCtas.getNumcta(), infoCtas.getTipoCta(), infoCtas.getBanco()));
                    tablaData.setItems(info);

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

                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    try {
                        pane.getChildren().remove(3);

                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }

                } catch (Exception e) {
//                    e.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });

    }

    @FXML
    void terminar_trx(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception ex) {
                    Logger.getLogger(EliminacionCtasAchFinController.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        });
    }
}
