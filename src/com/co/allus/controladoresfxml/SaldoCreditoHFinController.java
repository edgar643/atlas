/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.corehipotecario.DatosSaldoH;
import com.co.allus.modelo.corehipotecario.infoTablaSaldoH;
import java.io.IOException;
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
public class SaldoCreditoHFinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTablaSaldoH, String> descripcion;
    @FXML
    private TableColumn<infoTablaSaldoH, String> informacion;
    @FXML
    private TableView<infoTablaSaldoH> tablaDatosSaldoH;
    @FXML
    private Button terminar_trx;
    @FXML
    private Label tituloCreditoHfin;
    private transient final DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'SaldoCreditoHFin.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'SaldoCreditoHFin.fxml'.";
        assert tablaDatosSaldoH != null : "fx:id=\"tablaDatosSaldoH\" was not injected: check your FXML file 'SaldoCreditoHFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'SaldoCreditoHFin.fxml'.";
        assert tituloCreditoHfin != null : "fx:id=\"tituloCreditoHfin\" was not injected: check your FXML file 'SaldoCreditoHFin.fxml'.";

        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaSaldoH, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<infoTablaSaldoH, String>("informacion"));

    }

    public void mostrasSaldoCreditoHFin(final DatosSaldoH infoCreditoH) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/SaldoCreditoHFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final TableView<infoTablaSaldoH> tablaDatos = (TableView<infoTablaSaldoH>) root.lookup("#tablaDatosSaldoH");

                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<infoTablaSaldoH> dataTabla = FXCollections.observableArrayList();

                    // se agregan los datos a la tabla
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.OBLIGACION, infoCreditoH.getObligacion()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.SALDO, infoCreditoH.getSaldo()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.FECHA_CUOTA, infoCreditoH.getFechaproxcuota()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.VALOR_CUOTA_SALDO, infoCreditoH.getValorcuotasaldo()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.SEGURO, infoCreditoH.getSeguro()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.ESTADO_CREDITO, infoCreditoH.getEstadoCredito()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.SALDO_CAPITAL, infoCreditoH.getSaldoCapital()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.SALDO_INTERESES, infoCreditoH.getSaldoIntereses()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.SALDO_MORA, infoCreditoH.getSaldomora()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.OTROS_SALDOS, infoCreditoH.getOtrossaldos()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.FECHA_VENCIMIENTO, infoCreditoH.getFechavencimiento()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.VALOR_INICIAL, infoCreditoH.getValorinicial()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.TASA_INTERES, infoCreditoH.getTasainteres()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.FECHA_DESENBOLSO, infoCreditoH.getFechadesenbolso()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.NUMERO_CUOTA, infoCreditoH.getNumproxcuota()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.NUMERO_CUOTAS_MORA, infoCreditoH.getNumerocuotasmora()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.PLAN_CREDITO, infoCreditoH.getPlancredito()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.VALOR_CUOTA_MORA, infoCreditoH.getValorcuotamora()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.TIPO_ID_CLIENTE, infoCreditoH.getTipoidcliente()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.NUM_ID_CLIENTE, infoCreditoH.getNumidcliente()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.NOMBRE_CLIENTE, infoCreditoH.getNombrecliente()));
                    //nuevos campos
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.NUMERO_CREDITO_ANT, infoCreditoH.getNumcretidoant()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.NUMERO_CREDITO_ORI, infoCreditoH.getNumcretidoorig()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.INGRESOS_RECIBIDOS_X_ANT, infoCreditoH.getIngrerecibxant()));
                    dataTabla.add(new infoTablaSaldoH(DatosSaldoH.SEGUROS_RECIBIDOS_X_ANT, infoCreditoH.getSegurosrecibxant()));

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


                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que el  pago se halla realizado correctamente , por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });




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
}
