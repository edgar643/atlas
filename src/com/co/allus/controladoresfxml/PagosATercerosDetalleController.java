/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.pagosterceros.DatosDetallePagos;
import com.co.allus.modelo.pagosterceros.InfoTablaDetalleFact;
import com.co.allus.modelo.pagosterceros.FrecuenciaPagos;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import java.net.URL;
import com.co.allus.atlas.Atlas;
import com.co.allus.modelo.pagosterceros.DatosPagosTerceros;
import com.co.allus.modelo.pagosterceros.DatosTablaDetalle1;
import com.co.allus.modelo.pagosterceros.InfoTablaPagosTerceros;
import com.co.allus.utils.AtlasConstantes;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
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
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class PagosATercerosDetalleController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn colCodConvenio;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colInformacion;
    @FXML
    private TableColumn colNombreConvenio;
    @FXML
    private Button pagarOP;
    @FXML
    private TableView<DatosTablaDetalle1> tabla_datos;
    @FXML
    private TableView<InfoTablaDetalleFact> tabla_datos2;
    @FXML
    private Label titulo;
    @FXML
    private Button volverOP;
    private static DatosDetallePagos DetallePago;
    private GestorDocumental docs = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static int origen;

    public static int getOrigen() {
        return origen;
    }

    public static void setOrigen(int origen) {
        PagosATercerosDetalleController.origen = origen;
    }

    public static DatosDetallePagos getDetallePago() {
        return DetallePago;
    }

    public static void setDetallePago(DatosDetallePagos DetallePago) {
        PagosATercerosDetalleController.DetallePago = DetallePago;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        assert colCodConvenio != null : "fx:id=\"colCodConvenio\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        assert colDescripcion != null : "fx:id=\"colDescripcion\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        assert colInformacion != null : "fx:id=\"colInformacion\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        assert colNombreConvenio != null : "fx:id=\"colNombreConvenio\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        assert pagarOP != null : "fx:id=\"pagarOP\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        assert tabla_datos2 != null : "fx:id=\"tabla_datos2\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        assert volverOP != null : "fx:id=\"volverOP\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        assert titulo != null : "fx:id=\"titulo\" was not injected: check your FXML file 'PagosATercerosDetalle.fxml'.";
        this.resources = rb;
        this.location = url;
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_datos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colCodConvenio.setCellValueFactory(new PropertyValueFactory<DatosTablaDetalle1, String>("descripcion"));
        colNombreConvenio.setCellValueFactory(new PropertyValueFactory<DatosTablaDetalle1, String>("informacion"));

        colDescripcion.setCellValueFactory(new PropertyValueFactory<InfoTablaDetalleFact, String>("descripcion"));
        colInformacion.setCellValueFactory(new PropertyValueFactory<InfoTablaDetalleFact, String>("informacion"));





    }

    @FXML
    void pagarOP(ActionEvent event) {
        if (getDetallePago().getSelectedItem().colPagoFacturaHabiProperty().getValue().equalsIgnoreCase("N")) {
            PagosATercerosNoDispoController controller = new PagosATercerosNoDispoController();
            controller.mostrarPagoNoDisP(null, getDetallePago().getSelectedItem(), AtlasConstantes.PAGO_X_DETALLE);
        } else {
            final List<InfoTablaPagosTerceros> listapagos = new ArrayList<InfoTablaPagosTerceros>();
            listapagos.add(getDetallePago().getSelectedItem());
            final DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();
            datosPagosTerceros.setPagoActual(datosPagosTerceros.getPagoActual() + 1);
            datosPagosTerceros.setSeleccionPagos(listapagos);
            datosPagosTerceros.setOrigenPago(AtlasConstantes.PAGO_X_DETALLE);
            DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros);

            PagosATercerosFacturasController controller = new PagosATercerosFacturasController();
            controller.mostrarPagosTerceros(datosPagosTerceros.getDatosPagosTerceros(), AtlasConstantes.PAGO_X_DETALLE);

        }

    }

    @FXML
    void volverOP(ActionEvent event) {
        if (getOrigen() == 1) {
            ConsultaPagDebAutoFinController controller = new ConsultaPagDebAutoFinController();
            controller.mostrarPagDebAuto(null, null, null, 40, null, null, null, 0);
        } else {
            final PagosATercerosInitController controller = new PagosATercerosInitController();
            controller.mostrarDatosPagosTerceros(1);
        }


    }

    public void mostrarDetalleFactura(final DatosDetallePagos datosDetallePagos, final int origen) {
        setOrigen(origen);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    setDetallePago(datosDetallePagos);
                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosATercerosDetalle.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button pagarOP = (Button) root.lookup("#pagarOP");
                    final Button volverOP = (Button) root.lookup("#volverOP");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Label titulo = (Label) root.lookup("#titulo");

                    final TableView<InfoTablaDetalleFact> tabla_datos = (TableView<InfoTablaDetalleFact>) root.lookup("#tabla_datos");
                    final TableView<DatosTablaDetalle1> tabla_datos2 = (TableView<DatosTablaDetalle1>) root.lookup("#tabla_datos2");

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    if (getOrigen() == 1) {
                        titulo.setText("CONSULTA PAGADORES DEBITO AUTOMATICO");
                        pagarOP.setVisible(false);
                    }

                    final ObservableList<DatosTablaDetalle1> dataTabla1 = FXCollections.observableArrayList();
                    final ObservableList<InfoTablaDetalleFact> dataTabla = FXCollections.observableArrayList();
                    // se agregan los datos a la tabla
                    dataTabla1.add(new DatosTablaDetalle1(datosDetallePagos.getCodConvenio(), datosDetallePagos.getNomConvenio()));
                    tabla_datos2.setItems(dataTabla1);

                    SortedSet<String> codigos = new TreeSet<String>(FrecuenciaPagos.frecuenciaPagos.keySet());
                    String validator = "";
                    final StringBuilder frecpag = new StringBuilder();

//                     result.put("D", "Diario");
//        result.put("M", "Mensual");
//        result.put("T", "2 veces al mes");
//        result.put("W", "Semanal");
//        result.put("F", "Quincenal");
//        result.put("Q", "Trimestral");
//        result.put("H", "Semestral");
//        result.put("Y", "Anual");
//        result.put("N", "Cada N días");
                    /**
                     * frecuencia Pagos
                     */                   
                    if ("D".equalsIgnoreCase(datosDetallePagos.getFrecuenciaPago())) {
                        frecpag.append("Diario");
                    }
                    if ("M".equalsIgnoreCase(datosDetallePagos.getFrecuenciaPago())) {
                        if (datosDetallePagos.getDiaMes().trim().isEmpty()) {
                            frecpag.append("Mensual");
                        } else {
                            frecpag.append("Mensual ,");
                            frecpag.append("Dia ");
                            frecpag.append(datosDetallePagos.getDiaMes());
                        }

                    } else if ("T".equalsIgnoreCase(datosDetallePagos.getFrecuenciaPago())) {
                        frecpag.append("2 veces al mes");

                    } else if ("W".equalsIgnoreCase(datosDetallePagos.getFrecuenciaPago())) {
                        if (datosDetallePagos.getDiaSemana().trim().isEmpty()) {
                            frecpag.append("Semanal");
                        } else {
                            frecpag.append("Semanal ,");
                            frecpag.append("Dia ");
                            frecpag.append(datosDetallePagos.getDiaSemana());
                        }

                    } else if ("F".equalsIgnoreCase(datosDetallePagos.getFrecuenciaPago())) {
                        frecpag.append("Quincenal");
                    } else if ("Q".equalsIgnoreCase(datosDetallePagos.getFrecuenciaPago())) {
                        frecpag.append("Trimestral");
                    } else if ("H".equalsIgnoreCase(datosDetallePagos.getFrecuenciaPago())) {
                        frecpag.append("Semestral");
                    } else if ("Y".equalsIgnoreCase(datosDetallePagos.getFrecuenciaPago())) {
                        frecpag.append("Anual");
                    } else if ("N".equalsIgnoreCase(datosDetallePagos.getFrecuenciaPago())) {
                        if (datosDetallePagos.getNumeroDias().trim().isEmpty()) {
                            frecpag.append("Cada N días");
                        } else {
                            frecpag.append("Cada ");
                            frecpag.append(datosDetallePagos.getNumeroDias());
                            frecpag.append(" Dias");
                        }

                    }



                    /**
                     *
                     */
//                    for (Iterator<String> it = codigos.iterator(); it.hasNext();) {
//                        String frecuenciaPagos = it.next();
//                        if (frecuenciaPagos.equals(datosDetallePagos.getFrecuenciaPago())) {
//                            frecpag = FrecuenciaPagos.frecuenciaPagos.get(frecuenciaPagos).split(",")[0];
//                            
//
//                        }
//                    }
                    String tarjetaPIN = "";
                    String valorFactura = "";
                    // se agregan los datos a la tabla
//                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.NUMERODIAS, datosDetallePagos.getNumeroDias()));
//                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.NUMEROSEMANA, datosDetallePagos.getNumeroSemana()));
//                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.DIASEMANA, datosDetallePagos.getDiaSemana()));
//                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.DIAMES, datosDetallePagos.getDiaMes()));
//                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.CANAL, datosDetallePagos.getCanal()));
//                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.CODIGOBANCO, datosDetallePagos.getCodigoBanco()));
//                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.TIPOCUENTAPAGADOR, datosDetallePagos.getTipoCuentaPagador()));
//                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA2, datosDetallePagos.getReferencia2()));
//                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA3, datosDetallePagos.getReferencia3()));

                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.NOMCONV, datosDetallePagos.getNomConvenio()));
                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.ESTADO, datosDetallePagos.getEstado()));

                    try {
                        valorFactura = ("$ " + formatonum.format(Double.parseDouble(datosDetallePagos.getValorFactura().substring(0, datosDetallePagos.getValorFactura().length() - 2))).replace(".", ",") + "." + datosDetallePagos.getValorFactura().substring(datosDetallePagos.getValorFactura().length() - 2));
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.VALORFACTURA, valorFactura));
                    } catch (Exception e) {
                        valorFactura = ("$ " + datosDetallePagos.getValorFactura().substring(0, datosDetallePagos.getValorFactura().length() - 2) + "." + datosDetallePagos.getValorFactura().substring(datosDetallePagos.getValorFactura().length() - 2, datosDetallePagos.getValorFactura().length()));
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.VALORFACTURA, valorFactura));
                    }




                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.FECHAVEN, datosDetallePagos.getFechavencimiento()));
                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.DESCRIPCION, datosDetallePagos.getDescripcion()));
                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.FECHAINICIO, datosDetallePagos.getFechaInicio()));
                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.FECHAFIN, datosDetallePagos.getFechaFin()));
                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.FECHASIGUIENTEPAGO, datosDetallePagos.getFechaSiguientePago()));
                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.FRECUENCIAPAGO, frecpag.toString()));

                    if (datosDetallePagos.getIndicadorReferenciaFija().equals("1")) {
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA1, datosDetallePagos.getReferencia1().trim().isEmpty() ? "NO TIENE" : datosDetallePagos.getReferencia1().trim()));
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA2, datosDetallePagos.getReferencia2().trim().isEmpty() ? "NO TIENE" : datosDetallePagos.getReferencia2().trim()));
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA3, datosDetallePagos.getReferencia3().trim().isEmpty() ? "NO TIENE" : datosDetallePagos.getReferencia3().trim()));
                    } else if (datosDetallePagos.getIndicadorReferenciaFija().equals("2")) {
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA1, datosDetallePagos.getReferencia2().trim().isEmpty() ? "NO TIENE" : datosDetallePagos.getReferencia2().trim()));
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA2, datosDetallePagos.getReferencia1().trim().isEmpty() ? "NO TIENE" : datosDetallePagos.getReferencia1().trim()));
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA3, datosDetallePagos.getReferencia3().trim().isEmpty() ? "NO TIENE" : datosDetallePagos.getReferencia3().trim()));
                    } else if (datosDetallePagos.getIndicadorReferenciaFija().equals("3")) {
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA1, datosDetallePagos.getReferencia3().trim().isEmpty() ? "NO TIENE" : datosDetallePagos.getReferencia3().trim()));
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA2, datosDetallePagos.getReferencia1().trim().isEmpty() ? "NO TIENE" : datosDetallePagos.getReferencia1().trim()));
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.REFERENCIA3, datosDetallePagos.getReferencia2().trim().isEmpty() ? "NO TIENE" : datosDetallePagos.getReferencia2().trim()));

                    }

                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.DIASREINTENTO, datosDetallePagos.getDiasReintento()));
                    try {
                        if (datosDetallePagos.getNumeroCuentaPagador().trim().length() >= 15 || datosDetallePagos.getNumeroCuentaPagador().trim().length() >= 13) {
                            tarjetaPIN = datosDetallePagos.getNumeroCuentaPagador().substring(0, 6) + "****" + datosDetallePagos.getNumeroCuentaPagador().substring(datosDetallePagos.getNumeroCuentaPagador().length() - 4, datosDetallePagos.getNumeroCuentaPagador().length());
                            dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.NUMEROCUENTAPAGADOR, tarjetaPIN));
                        } else {
                            dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.NUMEROCUENTAPAGADOR, datosDetallePagos.getNumeroCuentaPagador().trim()));
                        }
                    } catch (Exception e) {
                        tarjetaPIN = datosDetallePagos.getNumeroCuentaPagador().trim().isEmpty() ? "" : "*******" + datosDetallePagos.getNumeroCuentaPagador().substring(datosDetallePagos.getNumeroCuentaPagador().length() - 4, datosDetallePagos.getNumeroCuentaPagador().length());
                        dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.NUMEROCUENTAPAGADOR, tarjetaPIN));

                    }
                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.TIPOCUENTAPAGADOR, datosDetallePagos.getTipoCuentaPagador()));

                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.NOMBREBANCO, datosDetallePagos.getNombreBanco()));

                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.CANAL, datosDetallePagos.getCanal()));

                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.CRITERIO_PAGO, datosDetallePagos.getCriteriopago()));

                    dataTabla.add(new InfoTablaDetalleFact(DatosDetallePagos.PAGO_PARCIAL, datosDetallePagos.getPagoparcial()));

                    tabla_datos.setItems(dataTabla);
                    //tabla_datos.getColumns().get(0).getStyleClass().add("column0");

                    /**
                     *
                     */
                    final DropShadow shadow = new DropShadow();

                    volverOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volverOP.setCursor(Cursor.HAND);
                                    volverOP.setEffect(shadow);
                                }
                            });

                    volverOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volverOP.setCursor(Cursor.DEFAULT);
                                    volverOP.setEffect(null);

                                }
                            });

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

}
