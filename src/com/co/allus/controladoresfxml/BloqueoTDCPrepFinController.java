/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.bloqueos.tablaFinBloqueos;
import com.co.allus.modelo.tdcprepago.ModelTdcPrepago;
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
import javafx.scene.control.TreeView;
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
public class BloqueoTDCPrepFinController implements Initializable {

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
    private Label mensajefinbloqtdcP;
    @FXML
    private TableView<tablaFinBloqueos> tablabloqtdcfin;
    @FXML
    private Button terminar_trx;

    @FXML
    void aceptar(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                final Label label_menu = (Label) pane.lookup("#label_saldos");

                final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                if (arboltdcPrepago != null) {
                    arboltdcPrepago.setDisable(false);
                    arboltdcPrepago.getSelectionModel().clearSelection();
                }

               
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }



            }
        });
    }
    private transient GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert fechahora != null : "fx:id=\"fechahora\" was not injected: check your FXML file 'BloqueoTDCPrepFin.fxml'.";
        assert mensajefinbloqtdcP != null : "fx:id=\"mensajefinbloqtdcP\" was not injected: check your FXML file 'BloqueoTDCPrepFin.fxml'.";
        assert tablabloqtdcfin != null : "fx:id=\"tablabloqtdcfin\" was not injected: check your FXML file 'BloqueoTDCPrepFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'BloqueoTDCPrepFin.fxml'.";
        fechahora.setCellValueFactory(new PropertyValueFactory<tablaFinBloqueos, String>("valor"));

    }

    public void mostrarBloqueoTDCFfin(final ModelTdcPrepago tarjetaBloqueada, final String fechabloqueo) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/BloqueoTDCPrepFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final TableView<tablaFinBloqueos> tabladesbloqueoSCfin = (TableView<tablaFinBloqueos>) root.lookup("#tablabloqtdcfin");
                    final Label mensaje = (Label) root.lookup("#mensajefinbloqtdcP");


                    final Cliente datosCliente = Cliente.getCliente();

                    if ("N".equalsIgnoreCase(datosCliente.getTipoClientePrepago())) {
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                        final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
                        cliente.setText("");
                        cliente.setText(info);
                        mensaje.setText("La tarjeta " + tarjetaBloqueada.getTipoTarjeta() + " ha sido bloqueada ");

                    } else if ("E".equalsIgnoreCase(datosCliente.getTipoClientePrepago())) {
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String nombreC = datosCliente.getNombreEmpresa();
                        final String info = nombreC + "\nNIT : " + datosCliente.getNitEmpresa() + "\n" + tarjetaBloqueada.getTarjetacf();
                        cliente.setText("");
                        cliente.setText(info);
                        mensaje.setText("La tarjeta " + tarjetaBloqueada.getTipoTarjeta() + " ha sido bloqueada ");

                    }



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
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado correctamente ,"
                            + " por favor no volver a intertalo e informar al area tecnica","Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });


    }
}
