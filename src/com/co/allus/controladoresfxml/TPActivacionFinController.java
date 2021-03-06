/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.tpactivacion.DatosActivacionConfirm;
import com.co.allus.modelo.tpactivacion.tablaFinActivacion;
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
 * @author stephania.rojas
 */
public class TPActivacionFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button terminar_trx;
    @FXML
    private TableColumn fechahora;
    @FXML
    private Label lblmensaje;
    @FXML
    private TableView<tablaFinActivacion> tabla_datos;
    private static GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert terminar_trx != null : "fx:id=\"aceptar\" was not injected: check your FXML file 'TPActivacionFin.fxml'.";
        assert fechahora != null : "fx:id=\"fechahora\" was not injected: check your FXML file 'TPActivacionFin.fxml'.";
        assert lblmensaje != null : "fx:id=\"lblmensaje\" was not injected: check your FXML file 'TPActivacionFin.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TPActivacionFin.fxml'.";
        fechahora.setCellValueFactory(new PropertyValueFactory<tablaFinActivacion, String>("valor"));
        this.location = url;
        this.resources = rb;
    }

    @FXML
    void terminar_trx(ActionEvent event) {
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
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Atlas.getVista().gotoPrincipal();
//                } catch (Exception e) {
//                }
//            }
//        });

    }

    public void mostrarActivacionTDCFfin(final DatosActivacionConfirm tarjeta, final String fecha) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TPActivacionFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final TableView<tablaFinActivacion> tablaactivacion = (TableView<tablaFinActivacion>) root.lookup("#tabla_datos");
                    final Label mensaje = (Label) root.lookup("#lblmensaje");


                    final Cliente datosCliente = Cliente.getCliente();


                    if ("N".equalsIgnoreCase(datosCliente.getTipoClientePrepago())) {
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                        final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente() + "\n" + tarjeta.getNumTarj();
                        cliente.setText("");
                        cliente.setText(info);

                    } else if ("E".equalsIgnoreCase(datosCliente.getTipoClientePrepago())) {
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String nombreC = datosCliente.getNombreEmpresa();
                        final String info = nombreC + "\nNIT : " + datosCliente.getNitEmpresa() + "\n" + tarjeta.getNumTarj();
                        cliente.setText("");
                        cliente.setText(info);
                    }


                    mensaje.setText("La Tarjeta " + tarjeta.getTipoTarj() + " ha sido activada con �xito");
                    tablaactivacion.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    /**
                     * se cargan los valores de la informacion del finpago
                     */
                    final ObservableList<tablaFinActivacion> datosInfodesbloqSC = FXCollections.observableArrayList();
                    final tablaFinActivacion datostabla = new tablaFinActivacion(fecha);
                    datosInfodesbloqSC.add(datostabla);
                    tablaactivacion.setItems(datosInfodesbloqSC);

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


                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que se haya realizado correctamente , por favor no volver a intentarlo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });


    }
}
