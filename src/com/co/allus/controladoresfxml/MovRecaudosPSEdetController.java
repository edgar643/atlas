/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultamovimientos.InfoTablaDetMovPSE;
import com.co.allus.modelo.consultamovimientos.infoMovPSE;
import com.co.allus.modelo.corehipotecario.DatosSaldoH;
import com.co.allus.modelo.corehipotecario.infoTablaSaldoH;
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
public class MovRecaudosPSEdetController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<InfoTablaDetMovPSE, String> descripcion;
    @FXML
    private TableColumn<InfoTablaDetMovPSE, String> informacion;
    @FXML
    private TableView<InfoTablaDetMovPSE> tabla_datos;
    @FXML
    private Button terminar_trx;
    @FXML
    private Button volver;
    private GestorDocumental docs = new GestorDocumental();

    @FXML
    void VolverOP(final ActionEvent event) {
        final MovRecaudosPSEController controller = new MovRecaudosPSEController();
        controller.mostrarMovRecaudosPSE(null, null, null, 0, null, null, null, null, 0);
    }

    @FXML
    void aceptar(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    docs.imprimir("Se presento un error inesperado en la aplicacion --->" + ex.toString());
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'MovRecaudosPSEdet.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'MovRecaudosPSEdet.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'MovRecaudosPSEdet.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'MovRecaudosPSEdet.fxml'.";
        assert volver != null : "fx:id=\"volver\" was not injected: check your FXML file 'MovRecaudosPSEdet.fxml'.";

        descripcion.setCellValueFactory(new PropertyValueFactory<InfoTablaDetMovPSE, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<InfoTablaDetMovPSE, String>("informacion"));

    }

    public void mostrarMovPSEdetalle(final infoMovPSE infoMovpse) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/MovRecaudosPSEdet.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final Button volver = (Button) root.lookup("#volver");
                    final TableView<InfoTablaDetMovPSE> tablaDatos = (TableView<InfoTablaDetMovPSE>) root.lookup("#tabla_datos");

                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<InfoTablaDetMovPSE> dataTabla = FXCollections.observableArrayList();

                    // se agregan los datos a la tabla
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.codigo_conv, infoMovpse.getCodigoconv()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.num_conv, infoMovpse.getNumconv()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.ref_1, infoMovpse.getRef1()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.ref_2, infoMovpse.getRef2()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.ref_3, infoMovpse.getRef3()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.valor_factura, infoMovpse.getValorFactura()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.valor_Pagado, infoMovpse.getValorPagado()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.fecha_Pago, infoMovpse.getFechaPago()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.meotodo_Pagado, infoMovpse.getMeotodoPagado()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.cuenta_Recaudoadora_tipo, infoMovpse.getCuentaRecaudoadora()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.cuenta_Recaudoadora_num, infoMovpse.getCuentaRecaudoadora()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.payment_ID, infoMovpse.getPaymentID()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.canal_Pago, infoMovpse.getCanalPago()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.nombre_Compania, infoMovpse.getNombreCompania()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.nombre_Oficina, infoMovpse.getNombreOficina()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.cod_Oficina, infoMovpse.getCodOficina()));
                    dataTabla.add(new InfoTablaDetMovPSE(infoMovpse.cod_PuntoPago, infoMovpse.getCodPuntoPago()));




                    tablaDatos.setItems(dataTabla);


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

                    volver.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volver.setCursor(Cursor.HAND);
                                    volver.setEffect(shadow);
                                }
                            });

                    volver.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volver.setCursor(Cursor.DEFAULT);
                                    volver.setEffect(null);

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
                    new ModalMensajes("Error en la aplicacion \n , por favor  informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });




    }
}
