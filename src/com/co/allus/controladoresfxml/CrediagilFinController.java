/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.transfcrediagil.infoTablaTransCredi;
import com.co.allus.modelo.transfcrediagil.infoTransferenciaCrediagil;
import com.co.allus.modelo.transfcrediagil.tablaFinDesembolsos;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
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
import javafx.scene.control.TextField;
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
public class CrediagilFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<tablaFinDesembolsos, String> cuenta_destino;
    @FXML
    private TableColumn<tablaFinDesembolsos, String> valor_prestamo;
    @FXML
    private TableView<tablaFinDesembolsos> tabla_datos;
    @FXML
    private Button terminar_trx;
    @FXML
    private TextField textofin;
    @FXML
    private Label cliente;
    @FXML
    private Label exitoso;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static List<infoTablaTransCredi> dataTabla;
    private static String opcionTransfCredi;
    private static String cuenta;
    private static GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cuenta_destino != null : "fx:id=\"cuenta_destino\" was not injected: check your FXML file 'CrediagilFin.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'CrediagilFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'CrediagilFin.fxml'.";
        assert textofin != null : "fx:id=\"textofin\" was not injected: check your FXML file 'CrediagilFin.fxml'.";
        assert valor_prestamo != null : "fx:id=\"valor_prestamo\" was not injected: check your FXML file 'CrediagilFin.fxml'.";
        assert exitoso != null : "fx:id=\"exitoso\" was not injected: check your FXML file 'CrediagilFin.fxml'.";
        assert cliente != null : "fx:id=\"cliente\" was not injected: check your FXML file 'CrediagilFin.fxml'.";


        cuenta_destino.setCellValueFactory(new PropertyValueFactory<tablaFinDesembolsos, String>("cuenta_destino"));
        valor_prestamo.setCellValueFactory(new PropertyValueFactory<tablaFinDesembolsos, String>("valor_prestamo"));


    }

    @FXML
    void terminar_trx(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    gestorDoc.imprimir("Ocurrio un error inesperado en la aplicación -->" + ex.toString());
                }
            }
        });
    }

    public void mostrarTablaCrediagil(final infoTransferenciaCrediagil dataTrasnfCredi) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/CrediagilFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    //final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final Label mensaje = (Label) root.lookup("#exitoso");
                    mensaje.setText("Transferencia desde Crediágil Exitosa");


                    final TableView<tablaFinDesembolsos> tabla_datos = (TableView<tablaFinDesembolsos>) root.lookup("#tabla_datos");

                    tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<tablaFinDesembolsos> dataTabla = FXCollections.observableArrayList();

                    //infoTransferenciaCrediagil.setInfoTransfCrediagil(dataTrasnfCredi);
                    // dataTabla.add(new tablaFinDesembolsos("oe", "essoo"));
                    dataTabla.add(new tablaFinDesembolsos(dataTrasnfCredi.getCuenta_destino(), "$ " + formatonum.format(Double.parseDouble(dataTrasnfCredi.getValor_prestamo_ent())).replace(".", ",") + "." + dataTrasnfCredi.getValor_prestamo_cent()));
                    tabla_datos.setItems(dataTabla);
                    tabla_datos.getColumns().get(0).setText(dataTrasnfCredi.getTipo_cuenta() + " Destino");

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
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }
            }
        });
    }
}