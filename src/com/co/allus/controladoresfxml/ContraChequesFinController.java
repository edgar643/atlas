/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.contraCheques.dataContraorden;
import com.co.allus.modelo.contraCheques.infoTablaContraChequesFin;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
public class ContraChequesFinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTablaContraChequesFin, String> chequefin;
    @FXML
    private TableColumn<infoTablaContraChequesFin, String> chequeini;
    @FXML
    private TableColumn<infoTablaContraChequesFin, String> fechahora;
    @FXML
    private TableColumn<infoTablaContraChequesFin, String> nuncta;
    @FXML
    private Label respuestalabl;
    @FXML
    private TableView<infoTablaContraChequesFin> tablaDatos;
    @FXML
    private Button terminartrx;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
    private static GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert chequefin != null : "fx:id=\"chequefin\" was not injected: check your FXML file 'ContraChequesFin.fxml'.";
        assert chequeini != null : "fx:id=\"chequeini\" was not injected: check your FXML file 'ContraChequesFin.fxml'.";
        assert fechahora != null : "fx:id=\"fechahora\" was not injected: check your FXML file 'ContraChequesFin.fxml'.";
        assert nuncta != null : "fx:id=\"nuncta\" was not injected: check your FXML file 'ContraChequesFin.fxml'.";
        assert respuestalabl != null : "fx:id=\"respuestalabl\" was not injected: check your FXML file 'ContraChequesFin.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ContraChequesFin.fxml'.";
        assert terminartrx != null : "fx:id=\"terminartrx\" was not injected: check your FXML file 'ContraChequesFin.fxml'.";

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        chequeini.setCellValueFactory(new PropertyValueFactory<infoTablaContraChequesFin, String>("chequeini"));
        chequefin.setCellValueFactory(new PropertyValueFactory<infoTablaContraChequesFin, String>("chequefin"));
        nuncta.setCellValueFactory(new PropertyValueFactory<infoTablaContraChequesFin, String>("cuenta"));
        fechahora.setCellValueFactory(new PropertyValueFactory<infoTablaContraChequesFin, String>("fechahora"));


    }

    @FXML
    void terminar_trx(ActionEvent event) {
        try {
            Atlas.getVista().gotoPrincipal();
        } catch (IOException ex) {
            Logger.getLogger(ContraChequesFinController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarContraordenFin(final dataContraorden datosCC) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ContraChequesFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button botoncontinuarOp = (Button) root.lookup("#terminartrx");
                    final Label respuestaCCheque = (Label) root.lookup("#respuestalabl");
                    final TableView<infoTablaContraChequesFin> tablaData = (TableView<infoTablaContraChequesFin>) root.lookup("#tablaDatos");
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);


                    final DropShadow shadow = new DropShadow();
                    botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botoncontinuarOp.setCursor(Cursor.HAND);
                                    botoncontinuarOp.setEffect(shadow);
                                }
                            });

                    botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botoncontinuarOp.setCursor(Cursor.DEFAULT);
                                    botoncontinuarOp.setEffect(null);

                                }
                            });



                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }

                    final String chequefin = datosCC.getChequefin();
                    final String chequeini = datosCC.getChequeini();

                    final ObservableList<infoTablaContraChequesFin> data = FXCollections.observableArrayList();

                    data.add(new infoTablaContraChequesFin(chequeini, chequefin, datosCC.getNumcta(), formatoFecha.format(new Date())));

                    tablaData.setItems(data);

                    if (chequefin.trim().isEmpty()) {

                        tablaData.getColumns().get(0).setText("Cheque");
                        tablaData.getColumns().remove(1);
                        respuestaCCheque.setText("Contraorden Exitosa.");
                    } else {
                        respuestaCCheque.setText("Contraorden Exitosa.\nSe contraordenó rango de cheques");

                    }


                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();


                } catch (Exception e) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }


            }
        });


    }
}
