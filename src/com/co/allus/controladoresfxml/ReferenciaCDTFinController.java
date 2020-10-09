/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultas.DatosRefCDT;
import com.co.allus.modelo.consultas.infoTablaRefCDT;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
 * @author stephania.rojas
 */
public class ReferenciaCDTFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTablaRefCDT, String> descripcion;
    @FXML
    private TableColumn<infoTablaRefCDT, String> informacion;
    @FXML
    private Label labelTitulo;
    @FXML
    private TableView<infoTablaRefCDT> tablaDatosRefCDT;
    @FXML
    private Button terminar_trx;
    private static GestorDocumental docs = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'ReferenciaCDTFin.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'ReferenciaCDTFin.fxml'.";
        assert labelTitulo != null : "fx:id=\"labelTitulo\" was not injected: check your FXML file 'ReferenciaCDTFin.fxml'.";
        assert tablaDatosRefCDT != null : "fx:id=\"tablaDatosRefCDT\" was not injected: check your FXML file 'ReferenciaCDTFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ReferenciaCDTFin.fxml'.";
        this.resources = rb;
        this.location = url;

        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaRefCDT, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<infoTablaRefCDT, String>("informacion"));

    }

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

    public void mostrarRefCDTfin(final DatosRefCDT infoRefCDT) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ReferenciaCDTFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final TableView<infoTablaRefCDT> tablaDatos = (TableView<infoTablaRefCDT>) root.lookup("#tablaDatosRefCDT");

                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<infoTablaRefCDT> dataTabla = FXCollections.observableArrayList();

                    // se agregan los datos a la tabla
                    dataTabla.add(new infoTablaRefCDT(DatosRefCDT.NUMERO_CDT, infoRefCDT.getNumeroCdt()));
                    //dataTabla.add(new infoTablaRefCDT(DatosRefCDT.FECHA_APERTURA, formatoFecha.format(infoRefCDT.getFechaApertura())));
                    dataTabla.add(new infoTablaRefCDT(DatosRefCDT.FECHA_APERTURA, formatoFecha.format(formatoFecha2.parse(Integer.parseInt(infoRefCDT.getFechaApertura()) + ""))));
                    dataTabla.add(new infoTablaRefCDT(DatosRefCDT.ESTADO, infoRefCDT.getEstado()));
                    dataTabla.add(new infoTablaRefCDT(DatosRefCDT.SALDO, ("$" + formatonum.format(Double.parseDouble(infoRefCDT.getSaldo().replace(",", "."))))));



                    tablaDatos.setItems(dataTabla);


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


                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que el  pago se halla realizado "
                            + "correctamente , por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }
}
