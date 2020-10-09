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
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagosterceros.DatosPagoFactura;
import com.co.allus.modelo.pagosterceros.DatosPagosTerceros;
import com.co.allus.modelo.pagosterceros.InfoTablaPagosTerceros;
import com.co.allus.modelo.pagosterceros.infoTablaMasFacturas;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class PagosATercerosFacturasController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelarPago;
    @FXML
    private Button cancelarRestantes;
    @FXML
    private Button continuarOP;
    @FXML
    private Label lblpago;
    @FXML
    private RadioButton otroVal;
    @FXML
    private RestrictiveTextField otroValor;
    @FXML
    private ComboBox<String> selCuenta;
    @FXML
    private TableView<DatosPagoFactura> tabla_Datos;
    @FXML
    private ComboBox<String> tipo_cuenta;
    @FXML
    private RadioButton valorPag;
    @FXML
    private TableColumn<DatosPagoFactura, String> colNombre;
    @FXML
    private TableColumn<DatosPagoFactura, String> colValor1;
    @FXML
    private TableColumn<DatosPagoFactura, String> colValor2;
    @FXML
    private TableColumn<DatosPagoFactura, String> colValor3;
    @FXML
    private Label lblpagoX;
    @FXML
    private ProgressBar progreso;
    private transient Service<Void> serviceMasFact;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static GestorDocumental docs = new GestorDocumental();
    private transient final SimpleDateFormat formatoFechaon = new SimpleDateFormat("yyyy/MM/dd ", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        assert cancelarPago != null : "fx:id=\"cancelarPago\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert cancelarRestantes != null : "fx:id=\"cancelarRestantes\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert colNombre != null : "fx:id=\"colNombre\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert colValor1 != null : "fx:id=\"colValor1\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert colValor2 != null : "fx:id=\"colValor2\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert colValor3 != null : "fx:id=\"colValor3\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert lblpago != null : "fx:id=\"lblpago\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert lblpagoX != null : "fx:id=\"lblpagoX\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert otroVal != null : "fx:id=\"otroVal\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert otroValor != null : "fx:id=\"otroValor\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert selCuenta != null : "fx:id=\"selCuenta\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert tabla_Datos != null : "fx:id=\"tabla_Datos\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";
        assert valorPag != null : "fx:id=\"valorPag\" was not injected: check your FXML file 'PagosATercerosFacturas.fxml'.";

        this.resources = rb;
        this.location = url;

        colNombre.setCellValueFactory(new PropertyValueFactory<DatosPagoFactura, String>("nombre"));
        colValor1.setCellValueFactory(new PropertyValueFactory<DatosPagoFactura, String>("ref1"));
        colValor2.setCellValueFactory(new PropertyValueFactory<DatosPagoFactura, String>("ref2"));
        colValor3.setCellValueFactory(new PropertyValueFactory<DatosPagoFactura, String>("ref3"));

        progreso.setVisible(false);
    }

    @FXML
    void selTipocta(ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");
                        final ComboBox<String> selCuenta = (ComboBox<String>) root.lookup("#selCuenta");
                        final Button continuarOP = (Button) root.lookup("#continuarOP");

                        final String tipoC = tipo_cuenta.getSelectionModel().getSelectedItem();
                        final String ctadestino = selCuenta.getSelectionModel().getSelectedItem();
                        final Cliente datosCliente = Cliente.getCliente();
                        final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                        final HashMap<String, ArrayList<String>> productostrx = new HashMap<String, ArrayList<String>>();
                        final Set<String> keySet = productos.keySet();

                        for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                            final String tipocuenta = val.next();

                            if (CodigoProductos.CUENTA_AHORROS.equals(tipocuenta) || CodigoProductos.CUENTA_CORRIENTE.equals(tipocuenta)) {
                                final ArrayList<String> cuentas = productos.get(tipocuenta);
                                productostrx.put(tipocuenta, cuentas);
                            }
                        }

                        final ArrayList<String> listacuentas = productostrx.get(tipoC);
                        final List<String> datosC = new ArrayList<String>();
                        datosC.add("Seleccione una cuenta");
                        if (listacuentas != null) {
                            for (int i = 0; i < listacuentas.size(); i++) {
                                if (!ctadestino.equalsIgnoreCase(listacuentas.get(i))) {
                                    datosC.add(listacuentas.get(i));
                                }
                            }
                        }
                        selCuenta.setItems(FXCollections.observableArrayList(datosC));
                        selCuenta.getSelectionModel().select("Seleccione una cuenta");

                        try {
                            pane.getChildren().remove(3);
                        } catch (Exception ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                        }

                        pane.setAlignment(Pos.CENTER_LEFT);
                        pane.add(root, 1, 0);
                        Atlas.vista.show();
                    } catch (Exception ex) {

                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    }
                }
            });
        } catch (Exception ex) {

            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

        }
    }

    @FXML
    void cancelPagoActual(ActionEvent event) {
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
                    mostrarPagosTerceros(DatosPagosTerceros.getDatosPagosTerceros(), AtlasConstantes.PAGO_X_LOTE);
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
                        final String inbd = data.colIndicadorBDProperty().getValue();

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
                        tramaMasFacturas.append(inbd);//ind base datos
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getContraseña());//CLAVE HW
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getTokenOauth());//CLAVE HW
                        tramaMasFacturas.append(",~");

//                        System.out.println("TRAMA MAS  FACTURAS :" + tramaMasFacturas);
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Trama detalle = " + "##" + docs.obtenerHoraActual());

                            // Respuesta="850,079,000,TRANSACCION EXITOSA,N,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~850,079,000,TRANSACCION EXITOSA,N,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~";
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
    void canceltodosPagos(ActionEvent event) {
        final DatosPagosTerceros datosCero = new DatosPagosTerceros();
        DatosPagosTerceros.setDatosPagosTerceros(datosCero);
        MarcoPrincipalController.newConsultaPagosT = true;
        final PagosATercerosInitController controller = new PagosATercerosInitController();
        controller.mostrarDatosPagosTerceros(1);
    }

    @FXML
    void continuarOP(ActionEvent event) {
        DatosPagosTerceros datosTablaDetalle = DatosPagosTerceros.getDatosPagosTerceros();
        if (AtlasConstantes.PAGO_X_DETALLE == datosTablaDetalle.getOrigenPago()) {

            if (valorPag.isSelected()) {
                datosTablaDetalle.setValorPagar(true);
                datosTablaDetalle.setOtroValor(false);
                datosTablaDetalle.setValorPagado(lblpago.getText());
                datosTablaDetalle.setTipoCtaPago(tipo_cuenta.getSelectionModel().getSelectedItem());
                datosTablaDetalle.setNumCtaPago(selCuenta.getSelectionModel().getSelectedItem());
                DatosPagosTerceros.setDatosPagosTerceros(datosTablaDetalle);
                PagosATercerosConfirmController controller = new PagosATercerosConfirmController();
                controller.mostrarPagoPrestamosConfirm(DatosPagosTerceros.getDatosPagosTerceros());

            } else if (otroVal.isSelected()) {

                datosTablaDetalle.setValorPagar(false);
                datosTablaDetalle.setOtroValor(true);

                String valor = otroValor.getText().replace(",", ".");
                if (valor.split("\\.").length == 2) {
                    if (valor.split("\\.")[1].length() != 2) {
                        valor = valor.split("\\.")[0] + "." + valor.split("\\.")[1] + "0";
                    }
                }

                valor = valor.replace(",", "");
                if (valor.contains(".")) {
                    valor = valor.split("\\.")[0] + valor.split("\\.")[1];

                } else if (valor.contains(",")) {
                    valor = valor.split(",")[0] + valor.split(",")[1];

                } else {
                    valor = valor + "00";
                }

                try {
                    valor = "$" + formatonum.format(Double.parseDouble(valor.substring(0, valor.length() - 2))).replace(".", ",") + "." + valor.substring(valor.length() - 2);
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    valor = otroValor.getText();
                }

                datosTablaDetalle.setValorPagado(valor);
                datosTablaDetalle.setTipoCtaPago(tipo_cuenta.getSelectionModel().getSelectedItem());
                datosTablaDetalle.setNumCtaPago(selCuenta.getSelectionModel().getSelectedItem());
                DatosPagosTerceros.setDatosPagosTerceros(datosTablaDetalle);
                PagosATercerosConfirmController controller = new PagosATercerosConfirmController();
                controller.mostrarPagoPrestamosConfirm(DatosPagosTerceros.getDatosPagosTerceros());

            }

        } else if (AtlasConstantes.PAGO_X_LOTE == datosTablaDetalle.getOrigenPago()) {
            List<InfoTablaPagosTerceros> seleccionPagos = datosTablaDetalle.getSeleccionPagos();
            final InfoTablaPagosTerceros data = seleccionPagos.get(datosTablaDetalle.getPagoActual() - 1);
            if (valorPag.isSelected()) {
                datosTablaDetalle.setValorPagar(true);
                datosTablaDetalle.setOtroValor(false);
                datosTablaDetalle.setValorPagado(data.colmontoRestanteProperty().getValue());
                datosTablaDetalle.setTipoCtaPago(tipo_cuenta.getSelectionModel().getSelectedItem());
                datosTablaDetalle.setNumCtaPago(selCuenta.getSelectionModel().getSelectedItem());
                DatosPagosTerceros.setDatosPagosTerceros(datosTablaDetalle);
                PagosATercerosConfirmController controller = new PagosATercerosConfirmController();
                controller.mostrarPagoPrestamosConfirm(DatosPagosTerceros.getDatosPagosTerceros());

            } else if (otroVal.isSelected()) {
                datosTablaDetalle.setValorPagar(false);
                datosTablaDetalle.setOtroValor(true);

                String valor = otroValor.getText().replace(",", ".");
                if (valor.split("\\.").length == 2) {
                    if (valor.split("\\.")[1].length() != 2) {
                        valor = valor.split("\\.")[0] + "." + valor.split("\\.")[1] + "0";
                    }
                }

                valor = valor.replace(",", "");
                if (valor.contains(".")) {
                    valor = valor.split("\\.")[0] + valor.split("\\.")[1];

                } else if (valor.contains(",")) {
                    valor = valor.split(",")[0] + valor.split(",")[1];

                } else {
                    valor = valor + "00";
                }

                try {
                    valor = "$" + formatonum.format(Double.parseDouble(valor.substring(0, valor.length() - 2))).replace(".", ",") + "." + valor.substring(valor.length() - 2);
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    valor = otroValor.getText();
                }


                datosTablaDetalle.setValorPagado(valor);
                datosTablaDetalle.setTipoCtaPago(tipo_cuenta.getSelectionModel().getSelectedItem());
                datosTablaDetalle.setNumCtaPago(selCuenta.getSelectionModel().getSelectedItem());
                DatosPagosTerceros.setDatosPagosTerceros(datosTablaDetalle);
                PagosATercerosConfirmController controller = new PagosATercerosConfirmController();
                controller.mostrarPagoPrestamosConfirm(DatosPagosTerceros.getDatosPagosTerceros());

            }

        }

    }

    @FXML
    void selOtrovalor(ActionEvent event) {

        valorPag.setSelected(false);
        otroValor.setText("");

        if (otroVal.isSelected() && !(otroValor.getText().isEmpty())) {
            continuarOP.setDisable(false);
        } else {
            continuarOP.setDisable(true);
        }

        if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                || "Seleccione una cuenta".equalsIgnoreCase(selCuenta.getSelectionModel().getSelectedItem().toString()))) {
            if (otroVal.isSelected() && !(otroValor.getText().isEmpty())) {
                continuarOP.setDisable(false);
            }
        } else {
            continuarOP.setDisable(true);
        }
    }

    @FXML
    void selvalorPag(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // solo se muestra una seleccion para cada opcion
                otroVal.setSelected(false);
                otroValor.setText("");

                if (valorPag.isSelected()) {
                    if (lblpago.getText().equalsIgnoreCase("Consultar Valor")) {
                        continuarOP.setDisable(true);
                        lblpago.setTooltip(new Tooltip("FACTURA NO DISPONIBLE PARA PAGO"));
                    } else {
                        continuarOP.setDisable(false);
                    }
                } else {
                    continuarOP.setDisable(true);
                }

                if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                        || "Seleccione una cuenta".equalsIgnoreCase(selCuenta.getSelectionModel().getSelectedItem().toString()))) {

                    if (valorPag.isSelected()) {
                        if (lblpago.getText().equalsIgnoreCase("Consultar Valor")) {
                            continuarOP.setDisable(true);
                            lblpago.setTooltip(new Tooltip("FACTURA NO DISPONIBLE PARA PAGO"));
                        } else {
                            continuarOP.setDisable(false);
                        }
                    }

                } else {
                    continuarOP.setDisable(true);
                }

            }
        });
    }

    @FXML
    void selCuenta(final ActionEvent event) {

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");
                        final ComboBox<String> selCuenta = (ComboBox<String>) root.lookup("#selCuenta");
                        final RestrictiveTextField Otroval = (RestrictiveTextField) root.lookup("#otroValor");
                        final RadioButton otroValPesos = (RadioButton) root.lookup("#otroVal");
                        final RadioButton pagoTotal = (RadioButton) root.lookup("#valorPag");
                        final Button continuarOP = (Button) root.lookup("#continuarOP");
                        final Label lblpago = (Label) root.lookup("#lblpago");

                        if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                                || "Seleccione una cuenta".equalsIgnoreCase(selCuenta.getSelectionModel().getSelectedItem().toString()))) {

                            if (pagoTotal.isSelected() || (!(Otroval.getText().isEmpty()) && otroValPesos.isSelected())) {
                                if (lblpago.getText().equalsIgnoreCase("Consultar Valor")) {
                                    continuarOP.setDisable(true);
                                    lblpago.setTooltip(new Tooltip("FACTURA NO DISPONIBLE PARA PAGO"));
                                    final KeyEvent keyEvent = KeyEvent.impl_keyEvent(Otroval, " ", KeyCode.ENTER.name(), KeyCode.ENTER.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                                    Otroval.fireEvent(keyEvent);
                                } else {
                                    continuarOP.setDisable(false);
                                    final KeyEvent keyEvent = KeyEvent.impl_keyEvent(Otroval, " ", KeyCode.ENTER.name(), KeyCode.ENTER.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                                    Otroval.fireEvent(keyEvent);
                                }

                            } else {
                                continuarOP.setDisable(true);
                            }

                        } else {
                            continuarOP.setDisable(true);
                        }

                    } catch (Exception ex) {
                        Logger.getGlobal().log(Level.SEVERE, ex.toString());
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }

                }
            });
        } catch (Exception ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.toString());
            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
        }

    }

    public void mostrarPagosTerceros(final DatosPagosTerceros datosPagosTerceros, final int origen) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosATercerosFacturas.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelarPago = (Button) root.lookup("#cancelarPago");
                    final Button cancelarRestantes = (Button) root.lookup("#cancelarRestantes");
                    final Button continuarOP = (Button) root.lookup("#continuarOP");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<DatosPagoFactura> tablaDatos = (TableView<DatosPagoFactura>) root.lookup("#tabla_Datos");
                    final Label lblpago = (Label) root.lookup("#lblpago");
                    final Label lblCreditoAct = (Label) root.lookup("#lblpagoX");
                    final RadioButton valorPag = (RadioButton) root.lookup("#valorPag");
                    final RadioButton otroValorRadio = (RadioButton) root.lookup("#otroVal");
                    final RestrictiveTextField otroValor = (RestrictiveTextField) root.lookup("#otroValor");
                    final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");
                    final ComboBox<String> selCuenta = (ComboBox<String>) root.lookup("#selCuenta");

                    List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<DatosPagoFactura> dataTabla = FXCollections.observableArrayList();

                    final List<String> datosT = new ArrayList<String>();
                    final List<String> datosC = new ArrayList<String>();

                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    selCuenta.setItems(emptyList);
                    tipo_cuenta.setItems(emptyList);

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    if (AtlasConstantes.PAGO_X_DETALLE == origen) {
                        final String infoCredito = "Pago " + datosPagosTerceros.getPagoActual() + "/" + datosPagosTerceros.getPagoActual();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);
                    } else if (AtlasConstantes.PAGO_X_LOTE == origen) {
                        final String infoCredito = "Pago " + datosPagosTerceros.getPagoActual() + "/" + datosPagosTerceros.getSeleccionPagos().size();
                        lblCreditoAct.setText("");
                        lblCreditoAct.setText(infoCredito);

                    }

                    if (AtlasConstantes.PAGO_X_DETALLE == origen) {

                        InfoTablaPagosTerceros infoTabla = datosPagosTerceros.getSeleccionPagos().get(0);
                        dataTabla.add(new DatosPagoFactura(infoTabla.colNombreConvenioProperty().getValue().trim().toLowerCase(),
                                infoTabla.colNomRef1Property().getValue().trim().isEmpty() ? "NO TIENE" : infoTabla.colNomRef1Property().getValue().trim(),
                                infoTabla.colNomRef2Property().getValue().trim().isEmpty() ? "NO TIENE" : infoTabla.colNomRef2Property().getValue().trim(),
                                infoTabla.colNomRef3Property().getValue().trim().isEmpty() ? "NO TIENE" : infoTabla.colNomRef3Property().getValue().trim()));

                        tablaDatos.getColumns().get(1).setText(infoTabla.colRef1Property().getValue().trim().isEmpty() ? "Referencia1" : infoTabla.colRef1Property().getValue().trim().toLowerCase());
                        tablaDatos.getColumns().get(2).setText(infoTabla.colRef2Property().getValue().trim().isEmpty() ? "Referencia2" : infoTabla.colRef2Property().getValue().trim().toLowerCase());
                        tablaDatos.getColumns().get(3).setText(infoTabla.colRef3Property().getValue().trim().isEmpty() ? "Referencia3" : infoTabla.colRef3Property().getValue().trim().toLowerCase());

                        if (infoTabla.colIndicadorBDProperty().getValue().equalsIgnoreCase("N")) {
                            otroValor.setDisable(false);
                            otroValorRadio.setDisable(false);
                            valorPag.setDisable(false);

                        } else if (infoTabla.colIndicadorBDProperty().getValue().equalsIgnoreCase("I") || infoTabla.colIndicadorBDProperty().getValue().equalsIgnoreCase("E")) {
                            if (infoTabla.colPagoCeroProperty().getValue().equalsIgnoreCase("S")
                                    || infoTabla.colPagoMayorProperty().getValue().equalsIgnoreCase("S")
                                    || infoTabla.colPagoMenorProperty().getValue().equalsIgnoreCase("S")) {
                                otroValor.setDisable(false);
                                otroValorRadio.setDisable(false);
                                valorPag.setDisable(false);
                                if (infoTabla.colPagoCeroProperty().getValue().equalsIgnoreCase("S")) {
                                    otroValor.setRestrict("[0-9][0-9]*+([,\\.][0-9]{0,2})?$");
                                }

                            } else {
                                otroValor.setDisable(true);
                                otroValorRadio.setDisable(true);
                                valorPag.setDisable(false);
                            }

                        } else {
                            otroValor.setDisable(false);
                            otroValorRadio.setDisable(false);
                            valorPag.setDisable(false);
                        }

                        lblpago.setText(infoTabla.colmontoRestanteProperty().getValue());
                        tablaDatos.setItems(dataTabla);

                    } else if (AtlasConstantes.PAGO_X_LOTE == origen) {
                        final InfoTablaPagosTerceros data = seleccionPagos.get(datosPagosTerceros.getPagoActual() - 1);
                        dataTabla.add(new DatosPagoFactura(data.colNombreConvenioProperty().getValue().trim().toLowerCase(),
                                data.colNomRef1Property().getValue().trim().isEmpty() ? "NO TIENE" : data.colNomRef1Property().getValue().trim(),
                                data.colNomRef2Property().getValue().trim().isEmpty() ? "NO TIENE" : data.colNomRef2Property().getValue().trim(),
                                data.colNomRef3Property().getValue().trim().isEmpty() ? "NO TIENE" : data.colNomRef3Property().getValue().trim()));

                        tablaDatos.getColumns().get(1).setText(data.colRef1Property().getValue().trim().isEmpty() ? "Referencia1" : data.colRef1Property().getValue().trim().toLowerCase());
                        tablaDatos.getColumns().get(2).setText(data.colRef2Property().getValue().trim().isEmpty() ? "Referencia2" : data.colRef2Property().getValue().trim().toLowerCase());
                        tablaDatos.getColumns().get(3).setText(data.colRef3Property().getValue().trim().isEmpty() ? "Referencia3" : data.colRef3Property().getValue().trim().toLowerCase());

                        if (data.colIndicadorBDProperty().getValue().equalsIgnoreCase("N")
                                || data.colPagoCeroProperty().getValue().equalsIgnoreCase("S")
                                || data.colPagoMayorProperty().getValue().equalsIgnoreCase("S")
                                || data.colPagoMenorProperty().getValue().equalsIgnoreCase("S")) {
                            otroValor.setDisable(false);
                            otroValorRadio.setDisable(false);
                            valorPag.setDisable(false);
                            if (data.colPagoCeroProperty().getValue().equalsIgnoreCase("S")) {
                                otroValor.setRestrict("[0-9][0-9]*+([,\\.][0-9]{0,2})?$");
                            }

                        } else {
                            otroValor.setDisable(true);
                            otroValorRadio.setDisable(true);
                            valorPag.setDisable(false);
                        }

                        lblpago.setText(data.colmontoRestanteProperty().getValue());
                        tablaDatos.setItems(dataTabla);

                    }

                    final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                    final Set<String> keySet = productos.keySet();

                    datosT.add("Tipo de Cuenta");

                    for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        String cuentatipo = val.next();

                        /* validacion solo se pérmite cuenta ahorros y corriente */
                        if (CodigoProductos.CUENTA_AHORROS.equals(cuentatipo) || CodigoProductos.CUENTA_CORRIENTE.equals(cuentatipo)) {

                            datosT.add(cuentatipo);
                        }
                    }

                    datosC.add("Seleccione una cuenta");

                    tipo_cuenta.setItems(FXCollections.observableArrayList(datosT));
                    tipo_cuenta.getSelectionModel().select("Tipo de Cuenta");
                    selCuenta.setItems(FXCollections.observableArrayList(datosC));
                    selCuenta.getSelectionModel().select("Seleccione una cuenta");

                    final DropShadow shadow = new DropShadow();
                    continuarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {

                            continuarOP.setCursor(Cursor.HAND);
                            continuarOP.setEffect(shadow);
                        }
                    });

                    continuarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            continuarOP.setCursor(Cursor.DEFAULT);
                            continuarOP.setEffect(null);
                        }
                    });

                    cancelarPago.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            cancelarPago.setCursor(Cursor.HAND);
                            cancelarPago.setEffect(shadow);
                        }
                    });

                    cancelarPago.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            cancelarPago.setCursor(Cursor.DEFAULT);
                            cancelarPago.setEffect(null);
                        }
                    });

                    cancelarRestantes.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            cancelarRestantes.setCursor(Cursor.HAND);
                            cancelarRestantes.setEffect(shadow);
                        }
                    });

                    cancelarRestantes.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            cancelarRestantes.setCursor(Cursor.DEFAULT);
                            cancelarRestantes.setEffect(null);
                        }
                    });

                    continuarOP.setDisable(true);
                    label_menu.setVisible(false);

                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                    if (arbol_pagos != null) {
                        arbol_pagos.setDisable(true);
                    }

                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                    if (arbol_saldos != null) {
                        arbol_saldos.setDisable(true);
                    }

                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                    if (arbol_transf != null) {
                        arbol_transf.setDisable(true);
                    }

                    final TreeView<String> arbol_bloqueos = (TreeView<String>) pane.lookup("#arbolbloqueos");
                    if (arbol_bloqueos != null) {
                        arbol_bloqueos.setDisable(true);
                    }

                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servadd != null) {
                        arbol_servadd.setDisable(true);
                    }

                    final TreeView<String> arbol_consMov = (TreeView<String>) pane.lookup("#arbol_movimientos");
                    if (arbol_consMov != null) {
                        arbol_consMov.setDisable(true);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });

    }

    @FXML
    void otroValPressed(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                continuarOP.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(otroValor, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                otroValor.clear();
                otroValor.fireEvent(keyEvent);

            }

        }
    }

    @FXML
    void OtroValKey(KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuarOP");
                    final RestrictiveTextField Otroval = (RestrictiveTextField) root.lookup("#otroValor");
                    final RadioButton otroValPesos = (RadioButton) root.lookup("#otroVal");
                    if (Otroval.getText().isEmpty() && otroValPesos.isSelected()) {
                        buttonCont.setDisable(true);
                    } else if (!("Tipo de Cuenta".equalsIgnoreCase(tipo_cuenta.getSelectionModel().getSelectedItem().toString())
                            || "Seleccione una cuenta".equalsIgnoreCase(selCuenta.getSelectionModel().getSelectedItem().toString()))) {
                        buttonCont.setDisable(false);

                        try {
                            String otroavalor = Otroval.getText();
                            if (".".equals(otroavalor.substring(otroavalor.length() - 1, otroavalor.length())) || ",".equals(otroavalor.substring(otroavalor.length() - 1, otroavalor.length()))) {
                                otroavalor = otroavalor + "00";
                            }
                        } catch (Exception ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                        }
                    }

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });
    }
}
