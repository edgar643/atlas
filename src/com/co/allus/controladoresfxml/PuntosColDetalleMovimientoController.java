/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.puntoscol.MapeoLogos;
import com.co.allus.modelo.puntoscol.infoTablaDetalleMovimiento;
import com.co.allus.modelo.puntoscol.infoTablaListarMovimientos;
import com.co.allus.modelo.puntoscol.modeloListarTarjeta;
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
 * @author roberto.ceballos
 */
public class PuntosColDetalleMovimientoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTablaDetalleMovimiento, String> descripcion;
    @FXML
    private TableColumn<infoTablaDetalleMovimiento, String> informacion;
    @FXML
    private TableView<infoTablaDetalleMovimiento> tabla_datos;
    @FXML
    private Button terminar_trx;
    @FXML
    private Button volver;

    @FXML
    void aceptar(ActionEvent event) {
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
    void volverOP(ActionEvent event) {
        PuntosColListarMovimientosController controller = new PuntosColListarMovimientosController();
        controller.mostrasMovmientosTDCpco(null, getSelectedtarjeta(), null, null, 0, null, 2);

    }
    private GestorDocumental docs = new GestorDocumental();
    private static modeloListarTarjeta selectedtarjeta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'PuntosColDetalleMovimiento.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'PuntosColDetalleMovimiento.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'PuntosColDetalleMovimiento.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'PuntosColDetalleMovimiento.fxml'.";
        assert volver != null : "fx:id=\"volver\" was not injected: check your FXML file 'PuntosColDetalleMovimiento.fxml'.";

        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaDetalleMovimiento, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<infoTablaDetalleMovimiento, String>("informacion"));

    }

    public static modeloListarTarjeta getSelectedtarjeta() {
        return selectedtarjeta;
    }

    public static void setSelectedtarjeta(modeloListarTarjeta selectedtarjeta) {
        PuntosColDetalleMovimientoController.selectedtarjeta = selectedtarjeta;
    }

    public void mostarDetalleMovTcPCO(final infoTablaListarMovimientos movimientos, final modeloListarTarjeta tarjetaselect) {
        setSelectedtarjeta(tarjetaselect);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PuntosColDetalleMovimiento.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final Button volver = (Button) root.lookup("#volver");
                    final TableView<infoTablaDetalleMovimiento> tablaDatos = (TableView<infoTablaDetalleMovimiento>) root.lookup("#tabla_datos");

                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<infoTablaDetalleMovimiento> dataTabla = FXCollections.observableArrayList();

                    // se agregan los datos a la tabla
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.TIPO_DOC, movimientos.getTipo_doc()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.DOCUMENTO, movimientos.getDocumento()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.FECHA_TRX, movimientos.getFecha_trx()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.HORA_TRX, movimientos.getHora_trx()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.VALOR_DOLAR, movimientos.getValor_trx_dolares()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.TIPO_DOC_BENEF, movimientos.getTipo_doc_benef()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.DOC_BENEF, movimientos.getDoc_benef()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.COD_UNICO, movimientos.getCod_unico()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.DESCOD_UNICO, movimientos.getDescod_unico()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.MCC, movimientos.getMcc()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.DESC_MSCC, movimientos.getDesc_mcc()));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.LOGO, MapeoLogos.mapeoEstados.get(movimientos.getLogo())));
                    dataTabla.add(new infoTablaDetalleMovimiento(movimientos.COD_RESP, movimientos.getRespuesta()));





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
