/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.AtlasAcceso;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagosterceros.DatosPagosTerceros;
import com.co.allus.modelo.pagosterceros.InfoTablaPagosTerceros;
import com.co.allus.modelo.pagosterceros.ModelPagofin1;
import com.co.allus.modelo.pagosterceros.ModelPagofin2;
import com.co.allus.modelo.pagosterceros.infoTablaMasFacturas;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.control.ProgressBar;
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
public class PagosATercerosFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button ContinuarPagos;
    @FXML
    private Button cancelarOP;
    @FXML
    private TableColumn<ModelPagofin2, String> comprobante;
    @FXML
    private TableColumn<ModelPagofin1, String> convenio_pagado;
    @FXML
    private TableColumn<ModelPagofin2, String> fecha_pago;
    @FXML
    private Label lblpago;
    @FXML
    private TableColumn<ModelPagofin2, String> num_cuenta;
    @FXML
    private TableView<ModelPagofin1> tabla_Datos1;
    @FXML
    private TableView<ModelPagofin2> tabla_Datos2;
    @FXML
    private TableView<modelValorTotal> tabla_Datos3;
    @FXML
    private Button terminar_trx;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TableColumn<ModelPagofin2, String> tipo_cuenta;
    @FXML
    private TableColumn<ModelPagofin1, String> valor_pagado;
    @FXML
    private TableColumn<modelValorTotal, String> valor_total;
    private GestorDocumental docs = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
    private transient Service<Void> serviceMasFact;
    private transient final SimpleDateFormat formatoFechaon = new SimpleDateFormat("yyyy/MM/dd ", Locale.getDefault());

    @FXML
    void ContinuarPagos(ActionEvent event) {

        DatosPagosTerceros datosTablaDetalle = DatosPagosTerceros.getDatosPagosTerceros();
        if (AtlasConstantes.PAGO_X_DETALLE == datosTablaDetalle.getOrigenPago()) {

            DatosPagosTerceros datosCero = new DatosPagosTerceros();
            DatosPagosTerceros.setDatosPagosTerceros(datosCero);
            synchronized (this) {
                MarcoPrincipalController.newConsultaPagosT = true;
            }

            final PagosATercerosInitController controller = new PagosATercerosInitController();
            controller.mostrarDatosPagosTerceros(1);

        } else if (AtlasConstantes.PAGO_X_LOTE == datosTablaDetalle.getOrigenPago()) {
            if (datosTablaDetalle.getPagoActual() == datosTablaDetalle.getSeleccionPagos().size()) {
                DatosPagosTerceros datosCero = new DatosPagosTerceros();
                DatosPagosTerceros.setDatosPagosTerceros(datosCero);
                synchronized (this) {
                    MarcoPrincipalController.newConsultaPagosT = true;
                }
                final PagosATercerosInitController controller = new PagosATercerosInitController();
                controller.mostrarDatosPagosTerceros(1);

            } else {

                datosTablaDetalle.setPagoActual(datosTablaDetalle.getPagoActual() + 1);
                final DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();

                List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
                final InfoTablaPagosTerceros data = seleccionPagos.get(datosPagosTerceros.getPagoActual() - 1);
                String inBD = data.colIndicadorBDProperty().getValue();
                String inMasFact = data.colindicadorMasFactProperty().getValue();


                if ((inBD.equalsIgnoreCase("I") && inMasFact.equalsIgnoreCase("S"))
                        || (inBD.equalsIgnoreCase("E") && inMasFact.equalsIgnoreCase("S"))
                        || (inBD.equalsIgnoreCase("R"))) {
                    //PagosATerceroMasFactController controller = new PagosATerceroMasFactController();

                    consultaMasFact(data);
                    //  - //controller.mostrarMasFacturas(datosPagosTerceros.getDatosPagosTerceros(), AtlasConstantes.PAGO_X_LOTE);

                } else {
                    DatosPagosTerceros.setDatosPagosTerceros(datosTablaDetalle);
                    PagosATercerosFacturasController controller = new PagosATercerosFacturasController();
                    controller.mostrarPagosTerceros(DatosPagosTerceros.getDatosPagosTerceros(), AtlasConstantes.PAGO_X_LOTE);
                }

            }

        }
    }

    public void consultaMasFact(final InfoTablaPagosTerceros selectedItem) {

        consultaMasFacturas().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono mas facturas" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        consultaMasFacturas().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo mas facturas" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (consultaMasFacturas().isRunning()) {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(consultaMasFacturas().progressProperty());
            consultaMasFacturas().reset();
            consultaMasFacturas().start();

        } else {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(consultaMasFacturas().progressProperty());
            consultaMasFacturas().start();
        }
    }

    public Service<Void> consultaMasFacturas() {
        serviceMasFact = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramaMasFacturas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final AtlasAcceso Atlasacceso = new AtlasAcceso();
                        final DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();
                        List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
                        final InfoTablaPagosTerceros data = seleccionPagos.get(datosPagosTerceros.getPagoActual() - 1);

                        final StringBuilder rnv = new StringBuilder();
                        if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                            rnv.append("E");
                        } else {
                            rnv.append("P");
                        }
                        final StringBuilder refFija = new StringBuilder("");
                        if (data.colIndicadorRefFijaProperty().getValue().equals("1")) {
                            refFija.append(data.colNomRef1Property().getValue());
                        } else if (data.colIndicadorRefFijaProperty().getValue().equals("2")) {
                            refFija.append(data.colNomRef2Property().getValue());
                        } else if (data.colIndicadorRefFijaProperty().getValue().equals("3")) {
                            refFija.append(data.colNomRef3Property().getValue());
                        } else {
                            refFija.append(data.colNomRef1Property().getValue());//REFFIJA
                        }
                        final String convenio = data.colCodConvenioProperty().getValue();
                        final String inbd = "";

                        tramaMasFacturas.append("850,079,");
                        tramaMasFacturas.append(datosCliente.getRefid()); //CONNID
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(AtlasConstantes.COD_PAGOS_MAS_FACTURAS);//COD TRX
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(" ");//TRACE PAGINACION
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getId_cliente());//ID
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(rnv);//INDICADOR(empresas-personas)
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append("0");//CANTIDAD REGISTROS
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append("0");//CANTIDAD REGISTROS TOAL                        
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(convenio);//CONVENIO
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(refFija.toString());//REFERENCIA FIJA
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(data.colIndicadorBDProperty().getValue());//ind base datos
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getContraseña());//CLAVE HW
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getTokenOauth());//CLAVE HW
                        tramaMasFacturas.append(",~");
                        

                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Trama detalle = " + "##" + docs.obtenerHoraActual());

                            //  Respuesta = "850,079,000,TRANSACCION EXITOSA,N,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~850,079,000,TRANSACCION EXITOSA,N,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMasFacturas.toString());
                            //  System.out.println("RESPUESTA DETALLE CONV:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion entrega  = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMasFacturas.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            final String indMAsReg = Respuesta.split(",")[4];//Indicador de más registros
                            final String tracePag = Respuesta.split(",")[5];
                            final int totalRegP = Integer.parseInt(Respuesta.split(",")[6]);
                            //  tablaDatos.setItems(emptyObservableList);
                            ObservableList<infoTablaMasFacturas> list = FXCollections.observableArrayList();

                            String[] Ltarjetas = Respuesta.split(",")[22].split(";");

                            for (int i = 0; i < Ltarjetas.length; i++) {
                                String[] datos = Ltarjetas[i].split("%");

//    private StringProperty codigoConvenio;
//    private StringProperty tipoRecaudo;
//    private StringProperty cuentaRecaudo;
//    private StringProperty cantidadReferencias;
//    private StringProperty nombreConvenio;
//    private StringProperty claseCuenta;
//    private StringProperty indicadorBD;
//    private StringProperty indicadorContingenciaBD;
//    private StringProperty indicadorPagoParcial;
//    private StringProperty indicadorPagoMayor;
//    private StringProperty indicadorPagoEnCero;
//    private StringProperty textoReferencia1;
//    private StringProperty textoReferencia2;
//    private StringProperty textoReferencia3;
//    private StringProperty indicadorReferenciaFija;
//    private StringProperty numeroFactura;
//    private StringProperty referencia1;
//    private StringProperty referencia2;
//    private StringProperty referencia3;
//    private StringProperty fechaDeVencimiento;
//    private StringProperty valorFactura;
//    private StringProperty valorRestanteDelPago;
                                String valorFactura = "";
                                String valorRestPago = "";
                                String fechavenc = "";

                                try {
                                    fechavenc = formatoFechaon.format(datos[4].trim());
                                } catch (Exception e) {
                                    fechavenc = datos[4].trim();
                                }

                                try {
                                    valorFactura = "$" + formatonum.format(Double.parseDouble(datos[5].substring(0, datos[5].length() - 2))).replace(".", ",") + "." + datos[5].substring(datos[5].length() - 2);
                                } catch (Exception e) {
                                    valorFactura = datos[5].trim();
                                }
                                try {
                                    valorRestPago = "$" + formatonum.format(Double.parseDouble(datos[6].substring(0, datos[6].length() - 2))).replace(".", ",") + "." + datos[6].substring(datos[6].length() - 2);
                                } catch (Exception e) {
                                    try {
                                        valorRestPago = datos[6].trim();
                                    } catch (Exception ex) {
                                        valorRestPago = "";
                                    }
                                }

                                infoTablaMasFacturas facturas = new infoTablaMasFacturas(Respuesta.split(",")[7],
                                        Respuesta.split(",")[8],
                                        Respuesta.split(",")[9],
                                        Respuesta.split(",")[10],
                                        Respuesta.split(",")[11],
                                        Respuesta.split(",")[12],
                                        Respuesta.split(",")[13],
                                        Respuesta.split(",")[14],
                                        Respuesta.split(",")[15],
                                        Respuesta.split(",")[16],
                                        Respuesta.split(",")[17],
                                        Respuesta.split(",")[18],
                                        Respuesta.split(",")[19],
                                        Respuesta.split(",")[20],
                                        Respuesta.split(",")[21],
                                        datos[0],
                                        datos[1],
                                        datos[2],
                                        datos[3],
                                        fechavenc,
                                        valorFactura,
                                        valorRestPago);

                                list.add(facturas);

                            }
                            data.setMasFacturas(list);
                            seleccionPagos.set(datosPagosTerceros.getPagoActual() - 1, data);
                            datosPagosTerceros.setSeleccionPagos(seleccionPagos);
                            DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros);

//
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    PagosATerceroMasFactController controller = new PagosATerceroMasFactController();
                                    controller.mostrarMasFacturas(DatosPagosTerceros.getDatosPagosTerceros(),
                                            indMAsReg,
                                            totalRegP,
                                            tracePag,
                                            rnv.toString(),
                                            refFija.toString(),
                                            convenio,
                                            inbd,
                                            AtlasConstantes.PAGO_X_LOTE);
                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    cancel();
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                }
                            });

                        }

                        return null;
                    }
                };
            }
        };

        return serviceMasFact;
    }

    @FXML
    void aceptar(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final DatosPagosTerceros datosCero = new DatosPagosTerceros();
                    DatosPagosTerceros.setDatosPagosTerceros(datosCero);
                    MarcoPrincipalController.newConsultaPagosT = true;
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }
            }
        });
    }

    @FXML
    void cancelarOP(ActionEvent event) {
        final DatosPagosTerceros datosCero = new DatosPagosTerceros();
        DatosPagosTerceros.setDatosPagosTerceros(datosCero);
        MarcoPrincipalController.newConsultaPagosT = true;
        final PagosATercerosInitController controller = new PagosATercerosInitController();
        controller.mostrarDatosPagosTerceros(1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert ContinuarPagos != null : "fx:id=\"ContinuarPagos\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert comprobante != null : "fx:id=\"comprobante\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert convenio_pagado != null : "fx:id=\"convenio_pagado\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert fecha_pago != null : "fx:id=\"fecha_pago\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert lblpago != null : "fx:id=\"lblpago\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert num_cuenta != null : "fx:id=\"num_cuenta\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert tabla_Datos1 != null : "fx:id=\"tabla_Datos1\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert tabla_Datos2 != null : "fx:id=\"tabla_Datos2\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert tabla_Datos3 != null : "fx:id=\"tabla_Datos3\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert valor_pagado != null : "fx:id=\"valor_pagado\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert valor_total != null : "fx:id=\"valor_total\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosATercerosFin.fxml'.";
        this.resources = rb;
        this.location = url;

        convenio_pagado.setCellValueFactory(new PropertyValueFactory<ModelPagofin1, String>("numCredito"));
        valor_pagado.setCellValueFactory(new PropertyValueFactory<ModelPagofin1, String>("valorPagado"));
        comprobante.setCellValueFactory(new PropertyValueFactory<ModelPagofin2, String>("comprobante"));
        num_cuenta.setCellValueFactory(new PropertyValueFactory<ModelPagofin2, String>("numcta"));
        tipo_cuenta.setCellValueFactory(new PropertyValueFactory<ModelPagofin2, String>("tipocta"));
        fecha_pago.setCellValueFactory(new PropertyValueFactory<ModelPagofin2, String>("fechapago"));
        valor_total.setCellValueFactory(new PropertyValueFactory("valorAcumulado"));
        progreso.setVisible(false);

    }

    public void mostrarPagosFin(final DatosPagosTerceros infoTablaDetalle) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosATercerosFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button ContinuarOP = (Button) root.lookup("#ContinuarPagos");
                    final Button cancelarOP = (Button) root.lookup("#cancelarOP");
                    final Button terminarOP = (Button) root.lookup("#terminar_trx");
                    final Label lblCreditoAct = (Label) root.lookup("#lblpago");

                    final TableView<ModelPagofin1> tablaDatos1 = (TableView<ModelPagofin1>) root.lookup("#tabla_Datos1");
                    final TableView<ModelPagofin2> tablaDatos2 = (TableView<ModelPagofin2>) root.lookup("#tabla_Datos2");
                    final TableView<modelValorTotal> tablaValorTotal = (TableView<modelValorTotal>) root.lookup("#tabla_Datos3");

                    tablaDatos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    tablaDatos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    tablaValorTotal.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                    final ObservableList<ModelPagofin1> dataTabla1 = FXCollections.observableArrayList();
                    final ObservableList<ModelPagofin2> dataTabla2 = FXCollections.observableArrayList();
                    final ObservableList<modelValorTotal> dataTablaValorTotal = FXCollections.observableArrayList();
                    final int origenPago = infoTablaDetalle.getOrigenPago();
                    List<InfoTablaPagosTerceros> seleccionPagos = infoTablaDetalle.getSeleccionPagos();

                    if (AtlasConstantes.PAGO_X_DETALLE == origenPago) {
                        terminarOP.setVisible(true);
                        cancelarOP.setVisible(false);
                        ContinuarOP.setVisible(false);
                        InfoTablaPagosTerceros pagoinfo = seleccionPagos.get(0);

                        dataTabla1.add(new ModelPagofin1(pagoinfo.colCodConvenioProperty().getValue(), infoTablaDetalle.getValorPagado()));

                        dataTablaValorTotal.add(new modelValorTotal(infoTablaDetalle.getPagoTotalAcum())); //cambiar!!!

                        dataTabla2.add(new ModelPagofin2(infoTablaDetalle.getComprobante(), infoTablaDetalle.getNumCtaPago(), infoTablaDetalle.getTipoCtaPago(), formatoFechaPago.format(new Date())));

                    } else if (AtlasConstantes.PAGO_X_LOTE == origenPago) {
                        InfoTablaPagosTerceros data = seleccionPagos.get(infoTablaDetalle.getPagoActual() - 1);
                        dataTabla1.add(new ModelPagofin1(data.colCodConvenioProperty().getValue(), infoTablaDetalle.getValorPagado()));
                        dataTabla2.add(new ModelPagofin2(infoTablaDetalle.getComprobante(), infoTablaDetalle.getNumCtaPago(), infoTablaDetalle.getTipoCtaPago(), formatoFechaPago.format(new Date())));

                        if (infoTablaDetalle.getPagoActual() == infoTablaDetalle.getSeleccionPagos().size()) {

                            terminarOP.setVisible(true);
                            cancelarOP.setVisible(false);
                            ContinuarOP.setVisible(false);

                        } else {
                            terminarOP.setVisible(false);

                        }
                        try {
                            String valorTotalFormat = formatonum.format(Double.parseDouble(infoTablaDetalle.getPagoTotalAcum().substring(0, infoTablaDetalle.getPagoTotalAcum().length() - 3))).replace(".", ",") + "." + infoTablaDetalle.getPagoTotalAcum().substring(infoTablaDetalle.getPagoTotalAcum().length() - 2, infoTablaDetalle.getPagoTotalAcum().length());
                            dataTablaValorTotal.add(new modelValorTotal("$ " + valorTotalFormat.replace("$", "")));
                        } catch (Exception e) {
                            dataTablaValorTotal.add(new modelValorTotal("$ " + infoTablaDetalle.getPagoTotalAcum()));
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
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado el pago consulta detalle prestamo correctamente , informar al aera Tecnica","Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

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
