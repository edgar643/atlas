/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultas.DatosConsultaCDT;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
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
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaCDTController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar;
    @FXML
    private Button continuar_op;
    @FXML
    private ProgressBar progreso;
    @FXML
    private ComboBox<String> selCreditosCDT;
    @FXML
    private Label tituloCDT;
    private transient Service<Void> serviceSaldoCreditoCDT;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaParse = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ConsultaCDT.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'ConsultaCDT.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaCDT.fxml'.";
        assert selCreditosCDT != null : "fx:id=\"selCreditosCDT\" was not injected: check your FXML file 'ConsultaCDT.fxml'.";
        assert tituloCDT != null : "fx:id=\"tituloCDT\" was not injected: check your FXML file 'ConsultaCDT.fxml'.";
        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);
    }

    public void mostrarSaldosCDT(final String menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaCDT.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final ComboBox<String> creditos_cdt = (ComboBox<String>) root.lookup("#selCreditosCDT");

                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    creditos_cdt.setItems(emptyList);
                    final List<String> datos = new ArrayList<String>();

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);


                    final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                    final Set<String> keySet = productos.keySet();
                    datos.add(AtlasConstantes.MENSAJE_COMBO_CDT);

                    for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        final String cuentatipo = val.next();

                        /* validacion solo se pérmite cuenta ahorros y corriente */

                        if (CodigoProductos.CREDITO_CDT.equals(cuentatipo)) {
                            final ArrayList<String> cuentas = productos.get(cuentatipo);
                            for (int i = 0; i < cuentas.size(); i++) {

                                datos.add(cuentas.get(i));
                            }

                        }
                    }
                    creditos_cdt.setItems(FXCollections.observableArrayList(datos));
                    creditos_cdt.getSelectionModel().select(AtlasConstantes.MENSAJE_COMBO_CDT);

                    final DropShadow shadow = new DropShadow();
                    botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botoncontinuarOp.setCursor(Cursor.HAND);
                                    botoncontinuarOp.setEffect(shadow);
                                }
                            });

                    botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botoncontinuarOp.setCursor(Cursor.DEFAULT);
                                    botoncontinuarOp.setEffect(null);

                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.HAND);
                                    cancelar.setEffect(shadow);
                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.DEFAULT);
                                    cancelar.setEffect(null);

                                }
                            });

                    botoncontinuarOp.setDisable(true);
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

                    final TreeView<String> arbol_servad = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servad != null) {
                        arbol_servad.setDisable(true);
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
//                    e.printStackTrace();
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });



    }

    @FXML
    void cancel_op(ActionEvent event) {

        try {
            continuar_Op().cancel();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Atlas.getVista().gotoPrincipal();
                    } catch (IOException ex) {
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    }
                }
            });


        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }
    }

    @FXML
    void continuar_OP(ActionEvent event) {
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Saldo Credito Hipotecario" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Saldo Credito Hipotecario" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }


    }

    public Service<Void> continuar_Op() {
        serviceSaldoCreditoCDT = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaSaldoCDT = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();



                        tramaSaldoCDT.append("850,028,");
                        tramaSaldoCDT.append(cliente.getRefid());
                        tramaSaldoCDT.append(",");
                        tramaSaldoCDT.append(AtlasConstantes.COD_SALDO_CREDITO_CDT);
                        tramaSaldoCDT.append(",");
                        tramaSaldoCDT.append(cliente.getId_cliente());
                        tramaSaldoCDT.append(",");
                        tramaSaldoCDT.append(selCreditosCDT.getSelectionModel().getSelectedItem());
                        tramaSaldoCDT.append(",");
                        tramaSaldoCDT.append(System.getProperty("user.name"));
                        tramaSaldoCDT.append(",");
                        tramaSaldoCDT.append(cliente.getContraseña());
                         tramaSaldoCDT.append(",");
                        tramaSaldoCDT.append(cliente.getTokenOauth());
                        tramaSaldoCDT.append(",~");

                        // System.out.println("TRAMA SALDO cdt :" + tramaSaldoCDT);



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ SALDO CDT = " + "##" + docs.obtenerHoraActual());


                            //850,028,codRespuesta,desCodRespuesta,NumCdt,Entidad,Estado,CodPlan,DescCodPlan,ModPagoInteres,PeriodicidadPagoInt,Oficina,FechaApertura,MontoInversionEnt,MontoInversionDec,Plazo,NombreTitutar,NombreCotitular,MontoPagoFinalEnt,MontoPagoFinalDEc,InteresNeto,FechaVencimiento,FechaRenovacion,FechaCancelacion,FechaProxPagoInt,TasaEfectiva,TasaNominal,Indexacion,TasaIndexacion,TasaPerGrac,PuntoBasicos,PeriodoGracia,PorcRetFte,RetFte,BaseLiquidacion,~

//                                nombreCompletoTitular
//                                nombreCompletoCoTitular1
//                                nombreCompletoCoTitular2
//                                nombreCompletoCoTitular3
//                                nombreCompletoCoTitular4
//                                nombreCompletoCoTitular5
//                                nombreCompletoCoTitular6
//                                nombreCompletoCoTitular7
//                                fechaApertura
//                                codigoOficina
//                                puntosBase
//                                numeroTitulo
//                                tasaIndex
//                                incrementoIntereses
//                                codigoPlan
//                                codIntAnticipado
//                                incrementoPlazo
//                                fechaPeriodoGracia
//                                fechaCierreCDT
//                                baseLiquidacion
//                                descripcionEstadoTitulo
//                                montoInteresNetoFecha
//                                tasaEfectiva
//                                tasaNominal
//                                valorOriginal
//                                valorRTF
//                                descPlan
//                                diasPeriodoGracias
//                                descripcionTasaIndex
//                                tasaPeriodoGracia
//                                entidad
//                                fechaUltimaRenovacion
//                                fechaVencimiento
//                                fechaProximoInteres


//                            Respuesta = "850,028,000,TRANSACCION EXITOSA,"
//                                    + "CLIENTE P                                                                                                                                             ,"
//                                    + "                                                                                                                                                      ,"
//                                    + "                                                                                                                                                      ,"
//                                    + "                                                                                                                                                      ,"
//                                    + "                                                                                                                                                      ,"
//                                    + "                                                                                                                                                      ,"
//                                    + "                                                                                                                                                      ,"
//                                    + "                                                                                                                                                      ,"
//                                    + "20161018,"
//                                    + "00030,"
//                                    + "+02000,"
//                                    + "00000980013,"
//                                    + "001,"
//                                    + "00000,"
//                                    + "010,"
//                                    + "010V,"
//                                    + "00000,"
//                                    + "20170130,"
//                                    + "00010101,"
//                                    + "00360,"
//                                    + "VIGENTE,"
//                                    + "000000005717900,"
//                                    + "08828,"
//                                    + "09194,"
//                                    + "000000700000000,"
//                                    + "000000000000000,"
//                                    + "FI19                                                                                                ,"
//                                    + "10 ,"
//                                    + "DTF       ,"
//                                    + "01980,"
//                                    + "Bancolombia,"
//                                    + "00010101,"
//                                    + "20170118,"
//                                    + "20161118,"
//                                    + "~";


                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU3, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU3, tramaSaldoCDT.toString());
                            // System.out.println(" RESPUESTA TRAMA SALDO CDT:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ SALDO CDT  = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ SALDO TDC = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU3, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU3, tramaSaldoCDT.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            final DatosConsultaCDT infoSaldoCreditoCDT = DatosConsultaCDT.getDatosConsultaCdt();

                            final StringBuilder cotitulares = new StringBuilder();

                            if (!Respuesta.split(",")[5].trim().isEmpty()) {
                                cotitulares.append(Respuesta.split(",")[5].trim());
                                cotitulares.append("\n");
                            }
                            if (!Respuesta.split(",")[6].trim().isEmpty()) {
                                cotitulares.append(Respuesta.split(",")[6].trim());
                                cotitulares.append("\n");
                            }
                            if (!Respuesta.split(",")[7].trim().isEmpty()) {
                                cotitulares.append(Respuesta.split(",")[7].trim());
                                cotitulares.append("\n");
                            }
                            if (!Respuesta.split(",")[8].trim().isEmpty()) {
                                cotitulares.append(Respuesta.split(",")[8].trim());
                                cotitulares.append("\n");
                            }
                            if (!Respuesta.split(",")[9].trim().isEmpty()) {
                                cotitulares.append(Respuesta.split(",")[9].trim());
                                cotitulares.append("\n");
                            }
                            if (!Respuesta.split(",")[10].trim().isEmpty()) {
                                cotitulares.append(Respuesta.split(",")[10].trim());
                                cotitulares.append("\n");
                            }
                            if (!Respuesta.split(",")[11].trim().isEmpty()) {
                                cotitulares.append(Respuesta.split(",")[11].trim());
                                cotitulares.append("\n");
                            }


                            infoSaldoCreditoCDT.setNombreTitular(Respuesta.split(",")[4].trim());
                            infoSaldoCreditoCDT.setNombreCotitular(cotitulares.toString());
                            try {

                                infoSaldoCreditoCDT.setFechaApertura(formatoFechaParse.format(formatoFecha.parse(Respuesta.split(",")[12])));
                            } catch (Exception e) {
                                infoSaldoCreditoCDT.setFechaApertura(Respuesta.split(",")[12]);
                            }

                            infoSaldoCreditoCDT.setOficinaAperturaCdt(Respuesta.split(",")[13]);


                            final StringBuilder puntosBase = new StringBuilder();
                            if (!Respuesta.split(",")[14].trim().isEmpty()) {
                                if ("+".equals(Respuesta.split(",")[14].trim().substring(0, 1))) {
                                    puntosBase.append(Integer.parseInt(Respuesta.split(",")[14].trim().substring(1, 4)));
                                    puntosBase.append(".");
                                    puntosBase.append(Respuesta.split(",")[14].trim().substring(4, Respuesta.split(",")[14].trim().length()));
                                    infoSaldoCreditoCDT.setPuntoBasicos(puntosBase.toString());
                                } else {
                                    puntosBase.append(Integer.parseInt(Respuesta.split(",")[14].trim().substring(0, 4)));
                                    puntosBase.append(".");
                                    puntosBase.append(Respuesta.split(",")[14].trim().substring(4, Respuesta.split(",")[14].trim().length()));
                                    infoSaldoCreditoCDT.setPuntoBasicos(puntosBase.toString());
                                }


                            } else {
                                infoSaldoCreditoCDT.setPuntoBasicos(Respuesta.split(",")[14].trim());
                            }

                            infoSaldoCreditoCDT.setNumeroCdt(Respuesta.split(",")[15].trim());
                            infoSaldoCreditoCDT.setTasaIndexacion(Respuesta.split(",")[16].trim());


                            infoSaldoCreditoCDT.setPeriodicidadPagoInt(Respuesta.split(",")[17].trim());
                            infoSaldoCreditoCDT.setCodigoPlan(Respuesta.split(",")[18].trim());

                            infoSaldoCreditoCDT.setModalidadPagoInt(Respuesta.split(",")[19].trim());
                            infoSaldoCreditoCDT.setPlazo(Respuesta.split(",")[20].trim());  // se asume como incremento plazo

                            try {

                                infoSaldoCreditoCDT.setFechaPeriodoGracia(formatoFechaParse.format(formatoFecha.parse(Respuesta.split(",")[21])));
                            } catch (Exception e) {
                                infoSaldoCreditoCDT.setFechaPeriodoGracia(Respuesta.split(",")[21]);
                            }

                            infoSaldoCreditoCDT.setFechaCancelacion(Respuesta.split(",")[22]);
                            infoSaldoCreditoCDT.setBaseLiquidacion(Respuesta.split(",")[23]);
                            infoSaldoCreditoCDT.setEstado(Respuesta.split(",")[24]);

                            infoSaldoCreditoCDT.setInteresesNetoPagar(formatonum.format(Double.parseDouble(Respuesta.split(",")[25].substring(0, Respuesta.split(",")[25].length() - 2))).replace(".", "") + "." + Respuesta.split(",")[25].substring(Respuesta.split(",")[25].length() - 2, Respuesta.split(",")[25].length()));


                            final StringBuilder tasaefectiva = new StringBuilder();
                            if (!Respuesta.split(",")[26].trim().isEmpty()) {

                                tasaefectiva.append(Integer.parseInt(Respuesta.split(",")[26].trim().substring(0, 2)));
                                tasaefectiva.append(".");
                                tasaefectiva.append(Respuesta.split(",")[26].trim().substring(2, Respuesta.split(",")[26].trim().length()));
                                infoSaldoCreditoCDT.setTasaEfectiva(tasaefectiva.toString());



                            } else {
                                infoSaldoCreditoCDT.setTasaEfectiva(Respuesta.split(",")[26].trim());
                            }


                            final StringBuilder tasanominal = new StringBuilder();
                            if (!Respuesta.split(",")[27].trim().isEmpty()) {

                                tasanominal.append(Integer.parseInt(Respuesta.split(",")[27].trim().substring(0, 2)));
                                tasanominal.append(".");
                                tasanominal.append(Respuesta.split(",")[27].trim().substring(2, Respuesta.split(",")[27].trim().length()));
                                infoSaldoCreditoCDT.setTasaNominal(tasanominal.toString());



                            } else {
                                infoSaldoCreditoCDT.setTasaNominal(Respuesta.split(",")[27].trim());
                            }

                            infoSaldoCreditoCDT.setMontoInversion(formatonum.format(Double.parseDouble(Respuesta.split(",")[28].substring(0, Respuesta.split(",")[28].length() - 2))).replace(".", "") + "." + Respuesta.split(",")[28].substring(Respuesta.split(",")[28].length() - 2, Respuesta.split(",")[28].length()));

                            infoSaldoCreditoCDT.setRetencionFuente(formatonum.format(Double.parseDouble(Respuesta.split(",")[29].substring(0, Respuesta.split(",")[29].length() - 2))).replace(".", "") + "." + Respuesta.split(",")[29].substring(Respuesta.split(",")[29].length() - 2, Respuesta.split(",")[29].length()));

                            infoSaldoCreditoCDT.setDescrpcionPlan(Respuesta.split(",")[30].trim());

                            infoSaldoCreditoCDT.setPeriodoGracia(Respuesta.split(",")[31].trim());

                            infoSaldoCreditoCDT.setIndexacion(Respuesta.split(",")[32].trim());

                            final StringBuilder tasaPeriodoG = new StringBuilder();
                            if (!Respuesta.split(",")[33].trim().isEmpty()) {

                                tasaPeriodoG.append(Integer.parseInt(Respuesta.split(",")[33].trim().substring(0, 2)));
                                tasaPeriodoG.append(".");
                                tasaPeriodoG.append(Respuesta.split(",")[33].trim().substring(2, Respuesta.split(",")[33].trim().length()));
                                infoSaldoCreditoCDT.setTasaPeriodoGracia(tasaPeriodoG.toString());



                            } else {
                                infoSaldoCreditoCDT.setTasaPeriodoGracia(Respuesta.split(",")[33].trim());
                            }

                            infoSaldoCreditoCDT.setEntidad(Respuesta.split(",")[34].trim());

                            try {

                                infoSaldoCreditoCDT.setFechaRenovacion(formatoFechaParse.format(formatoFecha.parse(Respuesta.split(",")[35])));
                            } catch (Exception e) {
                                infoSaldoCreditoCDT.setFechaRenovacion(Respuesta.split(",")[35]);
                            }

                            try {

                                infoSaldoCreditoCDT.setFechaVencimiento(formatoFechaParse.format(formatoFecha.parse(Respuesta.split(",")[36])));
                            } catch (Exception e) {
                                infoSaldoCreditoCDT.setFechaVencimiento(Respuesta.split(",")[36]);
                            }

                            try {

                                infoSaldoCreditoCDT.setFechaProxPagoInt(formatoFechaParse.format(formatoFecha.parse(Respuesta.split(",")[37])));
                            } catch (Exception e) {
                                infoSaldoCreditoCDT.setFechaProxPagoInt(Respuesta.split(",")[37]);
                            }


                            /**
                             *
                             */
                            DatosConsultaCDT.setDatosConsultaCdt(infoSaldoCreditoCDT);



                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final ConsultaCDTfinController controller = new ConsultaCDTfinController();
                                    controller.mostrarSaldoTDCfin(DatosConsultaCDT.getDatosConsultaCdt());
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
                                    continuar_op.setDisable(false);
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

        return serviceSaldoCreditoCDT;

    }

    @FXML
    void selCreditoscdt(ActionEvent event) {
        if (AtlasConstantes.MENSAJE_COMBO_CDT.equals(selCreditosCDT.getSelectionModel().getSelectedItem())) {
            continuar_op.setDisable(true);
        } else {
            continuar_op.setDisable(false);
        }
    }
}
