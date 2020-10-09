/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultadebitosaut.infoDebAut1;
import com.co.allus.modelo.consultadebitosaut.infoTablaDebAutDetalle;
import com.co.allus.modelo.consultadebitosaut.infoTablaDebAutDetalle2;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ScrollBar;
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
 * @author stephania.rojas
 */
public class MovDebAutDetalleController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button VolverOP;
    @FXML
    private TableColumn<infoTablaDebAutDetalle, String> descripcion;
    @FXML
    private TableColumn<infoTablaDebAutDetalle2, String> informacion;
    @FXML
    private TableView<infoTablaDebAutDetalle> tabla_datos;
    @FXML
    private TableView<infoTablaDebAutDetalle2> tabla_datos2;
    @FXML
    private ScrollBar scroll;
    @FXML
    private Button terminar_trx;
    private GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert VolverOP != null : "fx:id=\"VolverOP\" was not injected: check your FXML file 'MovDebAutDetalle.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'MovDebAutDetalle.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'MovDebAutDetalle.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'MovDebAutDetalle.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'MovDebAutDetalle.fxml'.";
        assert scroll != null : "fx:id=\"scroll\" was not injected: check your FXML file 'MovDebAutDetalle.fxml'.";

        this.location = url;
        this.resources = rb;
        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaDebAutDetalle, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<infoTablaDebAutDetalle2, String>("informacion"));


        scroll.setMax(17); //make sure the max is equal to the size of the table row data.
        scroll.setMin(0);
        scroll.unitIncrementProperty().setValue(5);
        scroll.blockIncrementProperty().setValue(5);
        scroll.setVisibleAmount(10);



        scroll.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                if ((Double) newValue < scroll.getMax()) //scrolPane2.setVvalue((Double)newValue);
//                {
////                    System.out.println("T1 ES " + scroll.getValue());
//                }
//                System.out.println("MAX SIZE ES " + tabla_datos.getItems().size());

                int valor = (int) scroll.getValue();
                tabla_datos.scrollTo(valor);
                tabla_datos2.scrollTo(valor);
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
                } catch (IOException ex) {
                    docs.imprimir("Se presento un error inesperado en la aplicacion --->" + ex.toString());
                }
            }
        });
    }

    @FXML
    void VolverOP(final ActionEvent event) {
        final MovDebAut2Controller controller = new MovDebAut2Controller();
        controller.mostrarMovDebAut(null, null, null, 0, null, null, null, null, 0);
    }

    public void mostrarMovDebdetalle(final infoDebAut1 infoMovdeb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/MovDebAutDetalle.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final Button VolverOP = (Button) root.lookup("#VolverOP");
                    // final ScrollBar scroll = (ScrollBar) root.lookup("#scroll");


                    final TableView<infoTablaDebAutDetalle> tablaDatos = (TableView<infoTablaDebAutDetalle>) root.lookup("#tabla_datos");
                    final TableView<infoTablaDebAutDetalle2> tablaDatos2 = (TableView<infoTablaDebAutDetalle2>) root.lookup("#tabla_datos2");

                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    tablaDatos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<infoTablaDebAutDetalle> dataTabla = FXCollections.observableArrayList();
                    final ObservableList<infoTablaDebAutDetalle2> dataTabla2 = FXCollections.observableArrayList();
                    // se agregan los datos a la tabla

                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.descripcion));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.empresa));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.reffija));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.ref2));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.ref3));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.modopro));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.valorfactura));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.valorpagado));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.fechahora));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.resultadopago));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.descrechazo));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.numcompro));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.bancodueño));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.tipoprod));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.numprod));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.numprog));
                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.numejec));

                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getDescfactura()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getNomConvenio() + "/" + infoMovdeb.getCodConvenio()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getReferencia1()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getRef2()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getNomRef3()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getModoProcesamiento()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getValorFactura()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getValorPagado()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getFechaPago()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getEstadoPago()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getDescRechazo()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getNumfactura()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getNumBanco()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getMetodoPago()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getCtarecaudadora()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getNumReintentosPen()));
                    dataTabla2.add(new infoTablaDebAutDetalle2(infoMovdeb.getNumReintentos()));

//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.descripcion, infoMovdeb.getDescfactura()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.empresa, infoMovdeb.getNomConvenio()+"/"+infoMovdeb.getCodConvenio()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.reffija, infoMovdeb.getReferencia1()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.ref2, infoMovdeb.getRef2()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.ref3, infoMovdeb.getNomRef3()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.modopro, infoMovdeb.getModoProcesamiento()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.valorfactura, infoMovdeb.getValorFactura()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.valorpagado, infoMovdeb.getValorPagado()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.fechahora, infoMovdeb.getFechaPago()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.resultadopago, infoMovdeb.getEstadoPago()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.descrechazo, infoMovdeb.getDescRechazo()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.numcompro, infoMovdeb.getNumfactura()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.bancodueño, infoMovdeb.getNumBanco()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.tipoprod, infoMovdeb.getMetodoPago()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.numprod, infoMovdeb.getCtarecaudadora()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.numprog, infoMovdeb.getNumReintentosPen()));
//                    dataTabla.add(new infoTablaDebAutDetalle(infoMovdeb.numejec, infoMovdeb.getNumReintentos()));





                    tablaDatos.setItems(dataTabla);
                    tablaDatos2.setItems(dataTabla2);


                    /**
                     *
                     */
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

                    VolverOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    VolverOP.setCursor(Cursor.HAND);
                                    VolverOP.setEffect(shadow);
                                }
                            });

                    VolverOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    VolverOP.setCursor(Cursor.DEFAULT);
                                    VolverOP.setEffect(null);

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
