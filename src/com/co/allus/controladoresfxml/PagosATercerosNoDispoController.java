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
import com.co.allus.modelo.pagosterceros.infoTablaMasFacturas;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class PagosATercerosNoDispoController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelarOP;
    @FXML
    private Button continuarOP;
    @FXML
    private Label lblconfirmar;
    @FXML
    private ProgressBar progreso;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient Service<Void> serviceMasFact;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaon = new SimpleDateFormat("yyyy/MM/dd ", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaven = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'PagosATercerosNoDispo.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'PagosATercerosNoDispo.fxml'.";
        assert lblconfirmar != null : "fx:id=\"lblconfirmar\" was not injected: check your FXML file 'PagosATercerosNoDispo.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosATercerosNoDispo.fxml'.";
        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);

    }

    @FXML
    void cancelarOP(ActionEvent event) {
        DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();
        datosPagosTerceros.setPagoActual(datosPagosTerceros.getPagoActual() - 1);
        DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros);
        PagosATercerosInitController controller = new PagosATercerosInitController();
        controller.mostrarDatosPagosTerceros(1);
    }

    @FXML
    void continuarOP(ActionEvent event) {
        DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();
        List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
        final List<InfoTablaPagosTerceros> seleccionPagosAux = new ArrayList<InfoTablaPagosTerceros>();
        for (Iterator<InfoTablaPagosTerceros> it = seleccionPagos.iterator(); it.hasNext();) {
            InfoTablaPagosTerceros infoTablaPagosTerceros = it.next();
            if (!(infoTablaPagosTerceros.colPagoFacturaHabiProperty().getValue().equalsIgnoreCase("N"))) {
                seleccionPagosAux.add(infoTablaPagosTerceros);
            }

        }

        datosPagosTerceros.setSeleccionPagos(seleccionPagosAux);
        DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros);
        //PagosATercerosFacturasController controller = new PagosATercerosFacturasController();
        //controller.mostrarPagosTerceros(DatosPagosTerceros.getDatosPagosTerceros(), DatosPagosTerceros.getDatosPagosTerceros().getOrigenPago());

        seleccionPagos = datosPagosTerceros.getSeleccionPagos();
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
            PagosATercerosFacturasController controller = new PagosATercerosFacturasController();
            controller.mostrarPagosTerceros(datosPagosTerceros.getDatosPagosTerceros(), AtlasConstantes.PAGO_X_LOTE);
        }



    }

    public void mostrarPagoNoDisP(final DatosPagosTerceros datosPagosTerceros, final InfoTablaPagosTerceros DatosPagos2, final int tipoPago) {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    int contador = 0;
                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosATercerosNoDispo.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Label lblconfirmar = (Label) root.lookup("#lblconfirmar");
                    final Button continuarOp = (Button) root.lookup("#continuarOP");
                    final Button cancelarOP = (Button) root.lookup("#cancelarOP");

                    final StringBuilder mensaje = new StringBuilder();
                    mensaje.append("Los siguientes convenios no están diponibles para el pago:\n ");

                    if (AtlasConstantes.PAGO_X_LOTE == tipoPago) {

                        List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
                        for (Iterator<InfoTablaPagosTerceros> it = seleccionPagos.iterator(); it.hasNext();) {
                            InfoTablaPagosTerceros infoTablaPagosTerceros = it.next();
                            if (infoTablaPagosTerceros.colPagoFacturaHabiProperty().getValue().equalsIgnoreCase("N")) {
                                mensaje.append("Convenio ");
                                mensaje.append(infoTablaPagosTerceros.colCodConvenioProperty().getValue());
                                mensaje.append(" ");
                                mensaje.append(infoTablaPagosTerceros.colNombreConvenioProperty().getValue());
                                mensaje.append("\n");
                                contador++;
                            }

                        }

                        if (contador == datosPagosTerceros.getSeleccionPagos().size()) {
                            continuarOp.setDisable(true);
                        }
                    } else if (AtlasConstantes.PAGO_X_DETALLE == tipoPago) {

                        if (DatosPagos2.colPagoFacturaHabiProperty().getValue().equalsIgnoreCase("N")) {
                            mensaje.append("Convenio ");
                            mensaje.append(DatosPagos2.colCodConvenioProperty().getValue());
                            mensaje.append(" ");
                            mensaje.append(DatosPagos2.colNombreConvenioProperty().getValue());
                            mensaje.append("\n");

                        }

                        continuarOp.setVisible(false);


                    }


                    lblconfirmar.setText(mensaje.toString());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final DropShadow shadow = new DropShadow();
                    continuarOp.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuarOp.setCursor(Cursor.HAND);
                                    continuarOp.setEffect(shadow);
                                }
                            });

                    continuarOp.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuarOp.setCursor(Cursor.DEFAULT);
                                    continuarOp.setEffect(null);

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


                } catch (Exception e) {
                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }

            }
        });



    }

    public void consultaMasFact(final InfoTablaPagosTerceros selectedItem) {

        consultaMasFacturas().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono mas facturas" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        consultaMasFacturas().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo mas facturas" + "##" + gestorDoc.obtenerHoraActual());
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

                        final StringBuilder rnv = new StringBuilder("");
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
                        final String indBD = data.colIndicadorBDProperty().getValue();


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
                        tramaMasFacturas.append(indBD);//ind base datos
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getContraseña());//CLAVE HW
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getTokenOauth());//CLAVE HW
                        tramaMasFacturas.append(",~");

//                        System.out.println("TRAMA MAS  FACTURAS :" + tramaMasFacturas);

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Trama detalle = " + "##" + gestorDoc.obtenerHoraActual());

                            // Respuesta="850,079,000,TRANSACCION EXITOSA,S,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~850,079,000,TRANSACCION EXITOSA,N,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMasFacturas.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion entrega  = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMasFacturas.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
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

                            try {
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
                                        fechavenc = formatoFechaon.format(formatoFechaven.parse(datos[4].trim()));
                                    } catch (Exception e) {
                                        fechavenc = datos[4].trim().equals("0") ? "" : datos[4].trim();
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
                                        } catch (Exception e2) {
                                            valorRestPago = "";
                                        }

                                    }


                                    infoTablaMasFacturas facturas = new infoTablaMasFacturas(Respuesta.split(",")[7],
                                            Respuesta.split(",")[8].trim(),
                                            Respuesta.split(",")[9].trim(),
                                            Respuesta.split(",")[10].trim(),
                                            Respuesta.split(",")[11].trim(),
                                            Respuesta.split(",")[12].trim(),
                                            Respuesta.split(",")[13].trim(),
                                            Respuesta.split(",")[14].trim(),
                                            Respuesta.split(",")[15].trim(),
                                            Respuesta.split(",")[16].trim(),
                                            Respuesta.split(",")[17].trim(),
                                            Respuesta.split(",")[18].trim(),
                                            Respuesta.split(",")[19].trim(),
                                            Respuesta.split(",")[20].trim(),
                                            Respuesta.split(",")[21].trim(),
                                            datos[0].trim(),
                                            datos[1].trim(),
                                            datos[2].trim(),
                                            datos[3].trim(),
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
                                                indBD,
                                                AtlasConstantes.PAGO_X_LOTE);
                                    }
                                });

                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                                final String mensaje = e.toString();
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes(mensaje, "ERROR DE RESPUESTA", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        cancel();
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                        final DatosPagosTerceros datosPagosTerceros1 = DatosPagosTerceros.getDatosPagosTerceros();
                                        datosPagosTerceros1.setPagoActual(datosPagosTerceros1.getPagoActual() - 1);
                                        DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros1);
                                    }
                                });
                            }


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
                                    final DatosPagosTerceros datosPagosTerceros1 = DatosPagosTerceros.getDatosPagosTerceros();
                                    datosPagosTerceros1.setPagoActual(datosPagosTerceros1.getPagoActual() - 1);
                                    DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros1);
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
}
