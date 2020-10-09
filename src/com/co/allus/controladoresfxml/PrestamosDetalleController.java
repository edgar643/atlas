/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagoprestamos.DatosPagoPrestamo;
import com.co.allus.modelo.pagoprestamos.infoTablaDetalle;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.DecimalFormat;
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
 * @author stephania.rojas
 */
public class PrestamosDetalleController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button pagarOP;
    @FXML
    private Button cancelarOP;
    @FXML
    private TableColumn<infoTablaDetalle, String> colDescripcion;
    @FXML
    private TableColumn<infoTablaDetalle, String> colInformacion;
    @FXML
    private TableView<infoTablaDetalle> tabla_datos;
    private GestorDocumental docs = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert colDescripcion != null : "fx:id=\"colDescripcion\" was not injected: check your FXML file 'PrestamosDetalle.fxml'.";
        assert colInformacion != null : "fx:id=\"colInformacion\" was not injected: check your FXML file 'PrestamosDetalle.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'PrestamosDetalle.fxml'.";
        assert pagarOP != null : "fx:id=\"pagarOP\" was not injected: check your FXML file 'PrestamosDetalle.fxml'.";
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'PrestamosDetalle.fxml'.";
        this.resources = rb;
        this.location = url;
        colDescripcion.setCellValueFactory(new PropertyValueFactory<infoTablaDetalle, String>("descripcion"));
        colInformacion.setCellValueFactory(new PropertyValueFactory<infoTablaDetalle, String>("informacion"));
    }

    @FXML
    void PagarOP(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final PrestamosPagosController controller = new PrestamosPagosController();
                final DatosPagoPrestamo datosTablaDetalle = DatosPagoPrestamo.getDatosTablaDetalle();
                datosTablaDetalle.setPagoActual(datosTablaDetalle.getPagoActual() + 1);
                datosTablaDetalle.setOrigenPago(AtlasConstantes.PAGO_POR_DETALLE);
                DatosPagoPrestamo.setDatosTablaDetalle(datosTablaDetalle);
                controller.mostrarPagoPrestamos(DatosPagoPrestamo.getDatosTablaDetalle(), AtlasConstantes.PAGO_POR_DETALLE);
            }
        });

    }

    @FXML
    void VolverOP(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                final PrestamosController controller = new PrestamosController();
                controller.mostrarDatosPrestamos();
            }
        });

    }

    public void mostrarTablaDetalle(final DatosPagoPrestamo infoTablaDetalle) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PrestamosDetalle.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button pagarOP = (Button) root.lookup("#pagarOP");
                    final Button cancelarOP = (Button) root.lookup("#cancelarOP");

                    final TableView<infoTablaDetalle> tablaDatos = (TableView<infoTablaDetalle>) root.lookup("#tabla_datos");

                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<infoTablaDetalle> dataTabla = FXCollections.observableArrayList();


                    // se agregan los datos a la tabla
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.NUMERO_CREDITO, infoTablaDetalle.getNumeroCredito()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.TIPO_PRESTAMO, infoTablaDetalle.getTipoPrestamo()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.FECHA_DESEMBOLSO, infoTablaDetalle.getFechaDesembolso()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.FECHA_PROXIMO_PAGO, infoTablaDetalle.getFechaProxPago()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.PAGO_MIN, "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoCuotaMinEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoCuotaMinCent()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.PAGO_TOTAL, "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoTotalEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoTotalCent()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.VALOR_INICIAL, "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getValorInicialEnt())).replace(".", ",") + "." + infoTablaDetalle.getValorInicialCent()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.CAPITAL_VIGENTE, "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getCapitalVigenteEnt())).replace(".", ",") + "." + infoTablaDetalle.getCapitalVigenteCent()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.INTERESES_MORA, "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getInteresesMoraEnt())).replace(".", ",") + "." + infoTablaDetalle.getInteresesMoraCent()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.INTERESES_CORRIENTES, "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getInteresesCorrientesEnt())).replace(".", ",") + "." + infoTablaDetalle.getInteresesCorrientesCent()));
                    dataTabla.add(new infoTablaDetalle(DatosPagoPrestamo.OTROS_CARGOS, "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getOtrosCargosEnt())).replace(".", ",") + "." + infoTablaDetalle.getOtrosCargosCent()));

                    tablaDatos.setItems(dataTabla);

                    /**
                     *
                     */
                    final DropShadow shadow = new DropShadow();

                    pagarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    pagarOP.setCursor(Cursor.HAND);
                                    pagarOP.setEffect(shadow);
                                }
                            });

                    pagarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    pagarOP.setCursor(Cursor.DEFAULT);
                                    pagarOP.setEffect(null);

                                }
                            });

                    cancelarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarOP.setCursor(Cursor.HAND);
                                    cancelarOP.setEffect(shadow);
                                }
                            });

                    cancelarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarOP.setCursor(Cursor.DEFAULT);
                                    cancelarOP.setEffect(null);

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
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado la consulta detalle prestamo correctamente , intentar nuevamente", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });


    }
}
