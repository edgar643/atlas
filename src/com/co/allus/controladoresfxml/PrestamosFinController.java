/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagoprestamos.DatosPagoPrestamo;
import com.co.allus.modelo.pagoprestamos.PagofinPrestamos1;
import com.co.allus.modelo.pagoprestamos.PagofinPrestamos2;
import com.co.allus.modelo.pagoprestamos.infoTablaPagosPrest;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
public class PrestamosFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelarPagos;
    @FXML
    private TableColumn<PagofinPrestamos2, String> comprobante;
    @FXML
    private TableColumn<PagofinPrestamos1, String> credito_pagado;
    @FXML
    private TableColumn<PagofinPrestamos2, String> fecha_pago;
    @FXML
    private TableColumn<PagofinPrestamos2, String> num_cuenta;
    @FXML
    private Button sigPago;
    @FXML
    private Button terminar_trx;
    @FXML
    private TableColumn<PagofinPrestamos2, String> tipo_cuenta;
    @FXML
    private TableColumn<PagofinPrestamos1, String> valor_pagado;
    @FXML
    private TableColumn valor_total;
    @FXML
    private Label lblpago;
    @FXML
    private TableView<PagofinPrestamos1> tabla_Datos1;
    @FXML
    private TableView<PagofinPrestamos2> tabla_Datos2;
    @FXML
    private TableView<modelValorTotal> tabla_Datos3;
    private GestorDocumental docs = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelarPagos != null : "fx:id=\"cancelarPagos\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert comprobante != null : "fx:id=\"comprobante\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert credito_pagado != null : "fx:id=\"credito_pagado\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert fecha_pago != null : "fx:id=\"fecha_pago\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert lblpago != null : "fx:id=\"lblpago\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert num_cuenta != null : "fx:id=\"num_cuenta\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert sigPago != null : "fx:id=\"sigPago\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert tabla_Datos1 != null : "fx:id=\"tabla_Datos1\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert tabla_Datos2 != null : "fx:id=\"tabla_Datos2\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert tabla_Datos3 != null : "fx:id=\"tabla_Datos3\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert valor_pagado != null : "fx:id=\"valor_pagado\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        assert valor_total != null : "fx:id=\"valor_total\" was not injected: check your FXML file 'PrestamosFin.fxml'.";
        this.resources = rb;
        this.location = url;

        credito_pagado.setCellValueFactory(new PropertyValueFactory<PagofinPrestamos1, String>("numCredito"));
        valor_pagado.setCellValueFactory(new PropertyValueFactory<PagofinPrestamos1, String>("valorPagado"));
        comprobante.setCellValueFactory(new PropertyValueFactory<PagofinPrestamos2, String>("comprobante"));
        num_cuenta.setCellValueFactory(new PropertyValueFactory<PagofinPrestamos2, String>("numcta"));
        tipo_cuenta.setCellValueFactory(new PropertyValueFactory<PagofinPrestamos2, String>("tipocta"));
        fecha_pago.setCellValueFactory(new PropertyValueFactory<PagofinPrestamos2, String>("fechapago"));
        valor_total.setCellValueFactory(new PropertyValueFactory("valorAcumulado"));

    }

    @FXML
    void ContinuarPagos(final ActionEvent event) {
        final DatosPagoPrestamo datosTablaDetalle = DatosPagoPrestamo.getDatosTablaDetalle();
        if (AtlasConstantes.PAGO_POR_DETALLE == datosTablaDetalle.getOrigenPago()) {

            final DatosPagoPrestamo datosCero = new DatosPagoPrestamo();
            DatosPagoPrestamo.setDatosTablaDetalle(datosCero);
            MarcoPrincipalController.newConsultaPrestamo = true;
            final PrestamosController controller = new PrestamosController();
            controller.mostrarDatosPrestamos();

        } else if (AtlasConstantes.PAGO_POR_LOTE == datosTablaDetalle.getOrigenPago()) {
            if (datosTablaDetalle.getPagoActual() == datosTablaDetalle.getSeleccionPagos().size()) {
                final DatosPagoPrestamo datosCero = new DatosPagoPrestamo();
                DatosPagoPrestamo.setDatosTablaDetalle(datosCero);
                MarcoPrincipalController.newConsultaPrestamo = true;
                final PrestamosController controller = new PrestamosController();
                controller.mostrarDatosPrestamos();

            } else {
                datosTablaDetalle.setPagoActual(datosTablaDetalle.getPagoActual() + 1);
                DatosPagoPrestamo.setDatosTablaDetalle(datosTablaDetalle);
                final PrestamosPagosController controller = new PrestamosPagosController();
                controller.mostrarPagoPrestamos(DatosPagoPrestamo.getDatosTablaDetalle(), AtlasConstantes.PAGO_POR_LOTE);

            }

        }

    }

    @FXML
    void aceptar(final ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });

    }

    @FXML
    void cancelarOP(final ActionEvent event) {
        final DatosPagoPrestamo datosCero = new DatosPagoPrestamo();
        DatosPagoPrestamo.setDatosTablaDetalle(datosCero);
        MarcoPrincipalController.newConsultaPrestamo = true;
        final PrestamosController controller = new PrestamosController();
        controller.mostrarDatosPrestamos();

    }

    public void mostrarPagosFin(final DatosPagoPrestamo infoTablaDetalle) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PrestamosFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button ContinuarOP = (Button) root.lookup("#sigPago");
                    final Button cancelarOP = (Button) root.lookup("#cancelarPagos");
                    final Button terminarOP = (Button) root.lookup("#terminar_trx");
                    final Label lblCreditoAct = (Label) root.lookup("#lblpago");

                    final TableView<PagofinPrestamos1> tablaDatos1 = (TableView<PagofinPrestamos1>) root.lookup("#tabla_Datos1");
                    final TableView<PagofinPrestamos2> tablaDatos2 = (TableView<PagofinPrestamos2>) root.lookup("#tabla_Datos2");
                    final TableView<modelValorTotal> tablaValorTotal = (TableView<modelValorTotal>) root.lookup("#tabla_Datos3");

                    tablaDatos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    tablaDatos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    tablaValorTotal.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                    final ObservableList<PagofinPrestamos1> dataTabla1 = FXCollections.observableArrayList();
                    final ObservableList<PagofinPrestamos2> dataTabla2 = FXCollections.observableArrayList();
                    final ObservableList<modelValorTotal> dataTablaValorTotal = FXCollections.observableArrayList();
                    final int origenPago = infoTablaDetalle.getOrigenPago();
                    final List<infoTablaPagosPrest> seleccionPagos = infoTablaDetalle.getSeleccionPagos();


                    if (AtlasConstantes.PAGO_POR_DETALLE == origenPago) {
                        terminarOP.setVisible(true);
                        cancelarOP.setVisible(false);
                        ContinuarOP.setVisible(false);

                        if (infoTablaDetalle.isPago_min_sel()) {

                            dataTabla1.add(new PagofinPrestamos1(infoTablaDetalle.getNumeroCredito(), "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoCuotaMinEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoCuotaMinCent()));
                            dataTablaValorTotal.add(new modelValorTotal("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoCuotaMinEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoCuotaMinCent()));
                        } else if (infoTablaDetalle.isPago_total_sel()) {
                            dataTabla1.add(new PagofinPrestamos1(infoTablaDetalle.getNumeroCredito(), "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoTotalEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoTotalCent()));
                            dataTablaValorTotal.add(new modelValorTotal("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoTotalEnt())).replace(".", ",") + "." + infoTablaDetalle.getPagoTotalCent()));
                        } else if (infoTablaDetalle.isOtro_valor_sel()) {
                            dataTabla1.add(new PagofinPrestamos1(infoTablaDetalle.getNumeroCredito(), "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getOtroValorEnt())).replace(".", ",") + "." + infoTablaDetalle.getOtroValorCent()));
                            dataTablaValorTotal.add(new modelValorTotal("$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getOtroValorEnt())).replace(".", ",") + "." + infoTablaDetalle.getOtroValorCent()));

                        }



                        dataTabla2.add(new PagofinPrestamos2(infoTablaDetalle.getComprobante(), infoTablaDetalle.getNumctaPago(), infoTablaDetalle.getTipoCtaPago(), formatoFechaPago.format(new Date())));

                    } else if (AtlasConstantes.PAGO_POR_LOTE == origenPago) {
                        final infoTablaPagosPrest data = seleccionPagos.get(DatosPagoPrestamo.getDatosTablaDetalle().getPagoActual() - 1);

                        if (infoTablaDetalle.isPago_min_sel()) {

                            dataTabla1.add(new PagofinPrestamos1(data.colCreditoProperty().getValue(), data.colPagoMinProperty().getValue()));
                        } else if (infoTablaDetalle.isPago_total_sel()) {
                            dataTabla1.add(new PagofinPrestamos1(data.colCreditoProperty().getValue(), data.colSaldoTotalProperty().getValue()));

                        } else if (infoTablaDetalle.isOtro_valor_sel()) {
                            dataTabla1.add(new PagofinPrestamos1(data.colCreditoProperty().getValue(), "$ " + formatonum.format(Double.parseDouble(infoTablaDetalle.getOtroValorEnt())).replace(".", ",") + "." + infoTablaDetalle.getOtroValorCent()));
                        }


                        dataTabla2.add(new PagofinPrestamos2(infoTablaDetalle.getComprobante(), infoTablaDetalle.getNumctaPago(), infoTablaDetalle.getTipoCtaPago(), formatoFechaPago.format(new Date())));
                        dataTablaValorTotal.add(
                                new modelValorTotal("$ "
                                + formatonum.format(Double.parseDouble(infoTablaDetalle.getPago_Total()
                                .replace(".", "").substring(0, infoTablaDetalle.getPago_Total().length() - 3)))
                                .replace(".", ",") + infoTablaDetalle.getPago_Total()
                                .substring(infoTablaDetalle.getPago_Total().length() - 3, infoTablaDetalle.getPago_Total().length())));

                        if (infoTablaDetalle.getPagoActual() == infoTablaDetalle.getSeleccionPagos().size()) {

                            terminarOP.setVisible(true);
                            cancelarOP.setVisible(false);
                            ContinuarOP.setVisible(false);

                        } else {
                            terminarOP.setVisible(false);

                        }

                    }

                    tablaDatos1.setItems(dataTabla1);
                    tablaDatos2.setItems(dataTabla2);
                    tablaValorTotal.setItems(dataTablaValorTotal);

                    if (AtlasConstantes.PAGO_POR_DETALLE == infoTablaDetalle.getOrigenPago()) {
                        final String infoCredito = "Pago " + infoTablaDetalle.getPagoActual() + "/" + infoTablaDetalle.getPagoActual();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);
                    } else if (AtlasConstantes.PAGO_POR_LOTE == infoTablaDetalle.getOrigenPago()) {
                        final String infoCredito = "Pago " + infoTablaDetalle.getPagoActual() + "/" + infoTablaDetalle.getSeleccionPagos().size();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);

                    }


                    /**
                     *
                     */
                    final DropShadow shadow = new DropShadow();

                    ContinuarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    ContinuarOP.setCursor(Cursor.HAND);
                                    ContinuarOP.setEffect(shadow);
                                }
                            });

                    ContinuarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    ContinuarOP.setCursor(Cursor.DEFAULT);
                                    ContinuarOP.setEffect(null);

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
                    terminarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminarOP.setCursor(Cursor.HAND);
                                    terminarOP.setEffect(shadow);
                                }
                            });

                    terminarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminarOP.setCursor(Cursor.DEFAULT);
                                    terminarOP.setEffect(null);

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
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado el pago consulta "
                            + "detalle prestamo correctamente , informar al aera Tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });




    }

    public class modelValorTotal {

        private StringProperty valorAcumulado;

        public modelValorTotal(String valorAcumulado) {
            this.valorAcumulado = new SimpleStringProperty(valorAcumulado);
        }

        public StringProperty valorAcumuladoProperty() {
            return valorAcumulado;
        }

        public void setValorAcumulado(String valorAcumulado) {
            this.valorAcumulado.set(valorAcumulado);
        }
    }
}
