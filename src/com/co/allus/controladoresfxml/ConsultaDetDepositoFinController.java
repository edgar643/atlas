/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultadepositos.InfoTablaDetDeposito;
import com.co.allus.modelo.consultadepositos.Infodet1;
import com.co.allus.modelo.consultadepositos.Infodet2;
import com.co.allus.modelo.consultadepositos.Infodet3;
import com.co.allus.modelo.consultadepositos.datosDepositoDetalle;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
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
import javafx.scene.control.Accordion;
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
public class ConsultaDetDepositoFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn<Infodet1, String> descripcion1;
    @FXML
    private TableColumn<Infodet2, String> descripcion2;
    @FXML
    private TableColumn<Infodet3, String> descripcion3;
    @FXML
    private TableColumn<Infodet1, String> informacion1;
    @FXML
    private TableColumn<Infodet2, String> informacion2;
    @FXML
    private TableColumn<Infodet3, String> informacion3;
    @FXML
    private TableView<Infodet1> tabla_datos1;
    @FXML
    private TableView<Infodet2> tabla_datos2;
    @FXML
    private TableView<Infodet3> tabla_datos3;
    @FXML
    private Button terminar_trx;
    @FXML
    private Button volver_op;
    @FXML
    private Accordion menu;
    @FXML
    private AnchorPane ap1;
    @FXML
    private AnchorPane ap2;
    @FXML
    private AnchorPane ap3;
    private static GestorDocumental docs = new GestorDocumental();
    private transient final DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert ap1 != null : "fx:id=\"ap1\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert ap2 != null : "fx:id=\"ap2\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert ap3 != null : "fx:id=\"ap3\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert menu != null : "fx:id=\"menu\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert descripcion1 != null : "fx:id=\"descripcion1\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert descripcion2 != null : "fx:id=\"descripcion2\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert descripcion3 != null : "fx:id=\"descripcion3\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert informacion1 != null : "fx:id=\"informacion1\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert informacion2 != null : "fx:id=\"informacion2\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert informacion3 != null : "fx:id=\"informacion3\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert tabla_datos1 != null : "fx:id=\"tabla_datos1\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert tabla_datos2 != null : "fx:id=\"tabla_datos2\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert tabla_datos3 != null : "fx:id=\"tabla_datos3\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        assert volver_op != null : "fx:id=\"volver_op\" was not injected: check your FXML file 'ConsultaDetDepositoFin.fxml'.";
        this.location = url;
        this.resources = rb;

        tabla_datos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_datos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_datos3.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        descripcion1.setCellValueFactory(new PropertyValueFactory<Infodet1, String>("descripcion"));
        informacion1.setCellValueFactory(new PropertyValueFactory<Infodet1, String>("informacion"));
        descripcion2.setCellValueFactory(new PropertyValueFactory<Infodet2, String>("descripcion2"));
        informacion2.setCellValueFactory(new PropertyValueFactory<Infodet2, String>("informacion2"));
        descripcion3.setCellValueFactory(new PropertyValueFactory<Infodet3, String>("descripcion3"));
        informacion3.setCellValueFactory(new PropertyValueFactory<Infodet3, String>("informacion3"));
    }

    @FXML
    void terminar_trx(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();

                } catch (Exception ex) {
                    Logger.getLogger(ConsultaDetDepositoFinController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void volver_op(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                final ConsultaDepositosController controller = new ConsultaDepositosController();
                controller.mostrarCosultaDepot();
            }
        });

    }

    public void mostrarDetDepositoFin(final InfoTablaDetDeposito infoTablaDetDeposito, final datosDepositoDetalle datosdeposito) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaDetDepositoFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final Button volver_op = (Button) root.lookup("#volver_op");

                    //final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Accordion Menu = (Accordion) root.lookup("#menu");
                    final TableView<Infodet1> tablaData1 = (TableView<Infodet1>) Menu.getPanes().get(0).getContent().lookup("#tabla_datos1");
                    // final TableView<Infodet1> tablaData1 = (TableView<Infodet1>) root.lookup("#tabla_datos1");
                    final TableView<Infodet2> tablaData2 = (TableView<Infodet2>) Menu.getPanes().get(1).getContent().lookup("#tabla_datos2");
                    final TableView<Infodet3> tablaData3 = (TableView<Infodet3>) Menu.getPanes().get(2).getContent().lookup("#tabla_datos3");

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    final ObservableList<Infodet1> dataTabla1 = FXCollections.observableArrayList();

                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.NumCuenta, datosdeposito.getNum_tarjeta()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.OFICINADUE, infoTablaDetDeposito.getOficinaDue()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.OFICINAAPE, infoTablaDetDeposito.getOficinaApe()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.ESTADO, infoTablaDetDeposito.getEstado()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.BLOQUEOS, infoTablaDetDeposito.getBloqueos()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.RAZONBLOQUEO, infoTablaDetDeposito.getRazonBloqueo()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.DIASINACTIVIDAD, infoTablaDetDeposito.getDiasInactividad()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.CODIGOPLAN, infoTablaDetDeposito.getCodigoPlan()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.NOMBREPLAN, infoTablaDetDeposito.getNombrePlan()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.PERIODICIDAD, infoTablaDetDeposito.getPeriodicidad()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.IMPRESION, infoTablaDetDeposito.getImpresion()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.TIPOGMF, infoTablaDetDeposito.getTipoGMF()));
                    dataTabla1.add(new Infodet1(InfoTablaDetDeposito.CODIGOOFICIAL, infoTablaDetDeposito.getCodigoOficial()));

                    tablaData1.setItems(dataTabla1);

                    final ObservableList<Infodet2> dataTabla2 = FXCollections.observableArrayList();

//                    dataTabla2.add(new Infodet2(InfoTablaDetDeposito.FECHAAPERTURA,infoTablaDetDeposito.getFechaApertura()));
//                    dataTabla2.add(new Infodet2(InfoTablaDetDeposito.FECHACANCELACION,infoTablaDetDeposito.getFechaApertura()));
//                    dataTabla2.add(new Infodet2(InfoTablaDetDeposito.FECHATRXCREDITO,infoTablaDetDeposito.getFechatrxCredito()));
//                    dataTabla2.add(new Infodet2(InfoTablaDetDeposito.FECHATRXDEBITO,infoTablaDetDeposito.getFechatrxDebito()));
                    dataTabla2.add(new Infodet2(InfoTablaDetDeposito.FECHAAPERTURA, infoTablaDetDeposito.getFechaApertura().trim().isEmpty() ? "" : infoTablaDetDeposito.getFechaApertura().substring(infoTablaDetDeposito.getFechaApertura().length() - 2, infoTablaDetDeposito.getFechaApertura().length()) + "/" + infoTablaDetDeposito.getFechaApertura().substring(4, infoTablaDetDeposito.getFechaApertura().length() - 2) + "/" + infoTablaDetDeposito.getFechaApertura().substring(0, infoTablaDetDeposito.getFechaApertura().length() - 4)));
                    dataTabla2.add(new Infodet2(InfoTablaDetDeposito.FECHACANCELACION, infoTablaDetDeposito.getFechaCancelacion().trim().isEmpty() ? "" : infoTablaDetDeposito.getFechaCancelacion().substring(infoTablaDetDeposito.getFechaCancelacion().length() - 2, infoTablaDetDeposito.getFechaCancelacion().length()) + "/" + infoTablaDetDeposito.getFechaCancelacion().substring(4, infoTablaDetDeposito.getFechaCancelacion().length() - 2) + "/" + infoTablaDetDeposito.getFechaCancelacion().substring(0, infoTablaDetDeposito.getFechaCancelacion().length() - 4)));
                    dataTabla2.add(new Infodet2(InfoTablaDetDeposito.FECHATRXCREDITO, infoTablaDetDeposito.getFechatrxCredito().trim().isEmpty() ? "" : infoTablaDetDeposito.getFechatrxCredito().substring(infoTablaDetDeposito.getFechatrxCredito().length() - 2, infoTablaDetDeposito.getFechatrxCredito().length()) + "/" + infoTablaDetDeposito.getFechatrxCredito().substring(4, infoTablaDetDeposito.getFechatrxCredito().length() - 2) + "/" + infoTablaDetDeposito.getFechatrxCredito().substring(0, infoTablaDetDeposito.getFechatrxCredito().length() - 4)));
                    dataTabla2.add(new Infodet2(InfoTablaDetDeposito.FECHATRXDEBITO, infoTablaDetDeposito.getFechatrxDebito().trim().isEmpty() ? "" : infoTablaDetDeposito.getFechatrxDebito().substring(infoTablaDetDeposito.getFechatrxDebito().length() - 2, infoTablaDetDeposito.getFechatrxDebito().length()) + "/" + infoTablaDetDeposito.getFechatrxDebito().substring(4, infoTablaDetDeposito.getFechatrxDebito().length() - 2) + "/" + infoTablaDetDeposito.getFechatrxDebito().substring(0, infoTablaDetDeposito.getFechatrxDebito().length() - 4)));

                    tablaData2.setItems(dataTabla2);

                    final ObservableList<Infodet3> dataTabla3 = FXCollections.observableArrayList();

                    dataTabla3.add(new Infodet3(InfoTablaDetDeposito.INTERPAGADOSACTUAL, "$" + formatonum.format(Double.parseDouble(infoTablaDetDeposito.getInterPagadosActual().substring(0, infoTablaDetDeposito.getInterPagadosActual().length() - 2))).replace(",", ".") + "," + infoTablaDetDeposito.getInterPagadosActual().substring(infoTablaDetDeposito.getInterPagadosActual().length() - 2, infoTablaDetDeposito.getInterPagadosActual().length())));
                    dataTabla3.add(new Infodet3(InfoTablaDetDeposito.INTERANTERIOR, "$" + formatonum.format(Double.parseDouble(infoTablaDetDeposito.getInterAnterior().substring(0, infoTablaDetDeposito.getInterAnterior().length() - 2))).replace(",", ".") + "," + infoTablaDetDeposito.getInterAnterior().substring(infoTablaDetDeposito.getInterAnterior().length() - 2, infoTablaDetDeposito.getInterAnterior().length())));
                    dataTabla3.add(new Infodet3(InfoTablaDetDeposito.TABLAINTERES, infoTablaDetDeposito.getTablaInteres()));
                    dataTabla3.add(new Infodet3(InfoTablaDetDeposito.TABLACARDOS, infoTablaDetDeposito.getTablaCargos()));

                    tablaData3.setItems(dataTabla3);

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

                    volver_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            volver_op.setCursor(Cursor.HAND);
                            volver_op.setEffect(shadow);
                        }
                    });

                    volver_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            volver_op.setCursor(Cursor.DEFAULT);
                            volver_op.setEffect(null);

                        }
                    });

                    Menu.getPanes().get(0).setExpanded(true);
                    Menu.getPanes().get(1).setExpanded(false);
                    Menu.getPanes().get(2).setExpanded(false);

                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    // System.out.println("error es " + ex.toString());

                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que el  pago se haya realizado correctamente , por favor no volver a intertalo e informar al area tecnica", "", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }
}
