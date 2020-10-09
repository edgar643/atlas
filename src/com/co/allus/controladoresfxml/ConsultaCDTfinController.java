/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultas.DatosConsultaCDT;
import com.co.allus.modelo.consultas.infoTablaCDT;
import com.co.allus.modelo.corehipotecario.infoTablaSaldoH;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
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
public class ConsultaCDTfinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoTablaCDT, String> descripcion;
    @FXML
    private TableColumn<infoTablaCDT, String> informacion;
    @FXML
    private TableView<infoTablaCDT> tablaDatosconsultaCDT;
    @FXML
    private Button terminar_trx;
    @FXML
    private Label tituloCDTfin;
    private GestorDocumental docs = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'ConsultaCDTfin.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'ConsultaCDTfin.fxml'.";
        assert tablaDatosconsultaCDT != null : "fx:id=\"tablaDatosconsultaCDT\" was not injected: check your FXML file 'ConsultaCDTfin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ConsultaCDTfin.fxml'.";
        assert tituloCDTfin != null : "fx:id=\"tituloCDTfin\" was not injected: check your FXML file 'ConsultaCDTfin.fxml'.";
        this.resources = rb;
        this.location = url;

        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaCDT, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<infoTablaCDT, String>("informacion"));

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

    public void mostrarSaldoTDCfin(final DatosConsultaCDT infoCreditoCDT) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaCDTfin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final TableView<infoTablaCDT> tablaDatos = (TableView<infoTablaCDT>) root.lookup("#tablaDatosconsultaCDT");

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<infoTablaCDT> dataTabla = FXCollections.observableArrayList();
                    formatonum.setRoundingMode(RoundingMode.DOWN);

                    // se agregan los datos a la tabla
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.NUMERO_CDT, infoCreditoCDT.getNumeroCdt()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.ENTIDAD, infoCreditoCDT.getEntidad().trim()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.ESTADO, infoCreditoCDT.getEstado().trim()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.CODIGO_PLAN, infoCreditoCDT.getCodigoPlan()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.DESCRPCION_CODIGO_PLAN, infoCreditoCDT.getDescrpcionPlan().trim()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.MODALIDAD_PAGO_INT, infoCreditoCDT.getModalidadPagoInt()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.PERIODICIDAD_PAGO_INT, infoCreditoCDT.getPeriodicidadPagoInt()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.OFICINA_APERTURA, infoCreditoCDT.getOficinaAperturaCdt()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.FECHA_APERTURA, infoCreditoCDT.getFechaApertura()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.MONTO_INVERSION, "$ " + formatonum.
                            format(Double.parseDouble(infoCreditoCDT.getMontoInversion())).
                            replace(".", ",") + "." + infoCreditoCDT.getMontoInversion().
                                    substring(infoCreditoCDT.getMontoInversion().
                                            length() - 2, infoCreditoCDT.getMontoInversion().length())));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.PLAZO, infoCreditoCDT.getPlazo()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.NOMBRE_TITULAR, infoCreditoCDT.getNombreTitular().trim()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.NOMBRE_COTITULAR, infoCreditoCDT.getNombreCotitular().trim()));


                    String var1 = infoCreditoCDT.getMontoInversion();
                    String var2 = infoCreditoCDT.getInteresesNetoPagar();
                    String cadena = "";

                    BigDecimal num1 = new BigDecimal(var1);
                    BigDecimal num2 = new BigDecimal(var2);

                    try {
                        BigDecimal suma = num1.add(num2);

                        cadena = String.valueOf(suma);
                        //System.out.println("suma cadena es " + cadena);

                    } catch (Exception e) {
                        Logger.getGlobal().log(Level.SEVERE, e.toString());
                    }


                    //dataTabla.add(new infoTablaCDT(DatosConsultaCDT.MONTO_PAGO_FINAL, "$ " + formatonum.format(Double.parseDouble(cadena)).replace(".", ",")));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.MONTO_PAGO_FINAL, "$ " + formatonum.format(Double.parseDouble(cadena)).replace(".", ",") + "." + cadena.substring(cadena.length() - 2, cadena.length())));
                    //dataTabla.add(new infoTablaCDT(DatosConsultaCDT.MONTO_PAGO_FINAL, "$" + cadena.replace(".", ",")));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.INTERES_NETO_PAGAR, "$ " + formatonum.format(Double.parseDouble(infoCreditoCDT.getInteresesNetoPagar())).replace(".", ",") + "." + infoCreditoCDT.getInteresesNetoPagar().substring(infoCreditoCDT.getInteresesNetoPagar().length() - 2, infoCreditoCDT.getInteresesNetoPagar().length())));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.FECHA_VENCIMIENTO, infoCreditoCDT.getFechaVencimiento()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.FECHA_RENOVACION, infoCreditoCDT.getFechaRenovacion()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.FECHA_CANCELACION, infoCreditoCDT.getFechaCancelacion()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.FECHA_PROX_PAGO, infoCreditoCDT.getFechaProxPagoInt()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.TASA_EFECTIVA, infoCreditoCDT.getTasaEfectiva() + "%"));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.TASA_NOMINAL, infoCreditoCDT.getTasaNominal() + "%"));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.INDEXACION, infoCreditoCDT.getIndexacion()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.TASA_INDEXACION, infoCreditoCDT.getTasaIndexacion() + "%"));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.TASA_PERIOD_GRACIA, infoCreditoCDT.getTasaPeriodoGracia() + "%"));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.PUNTOS_BASICOS, infoCreditoCDT.getPuntoBasicos()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.PERIODO_GRACIA, infoCreditoCDT.getPeriodoGracia()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.RET_FUENTE, infoCreditoCDT.getRetencionFuente()));
                    dataTabla.add(new infoTablaCDT(DatosConsultaCDT.BASE_LIQUIDACION, infoCreditoCDT.getBaseLiquidacion()));


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
                    new ModalMensajes("Error en la aplicacion \n , es posible que el  pago se halla realizado correctamente , por favor no volver a intertalo e informar al area tecnica", "", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }
}
