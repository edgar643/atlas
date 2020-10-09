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
import com.co.allus.modelo.pagosaterceros.infoMovConv;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaMovConvinitController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button buscar;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private TextField numConv;
    @FXML
    private TextField numCtaRec;
    @FXML
    private HBox menu_progreso;
    @FXML
    private ProgressBar progreso;
    @FXML
    private ComboBox<String> tipo_cuenta;
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private static GestorDocumental docs = new GestorDocumental();
    private transient Service<Void> serviceMovPSE = continuar_Op();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static AtomicBoolean nuevaConsulta = new AtomicBoolean();
    private transient final SimpleDateFormat formatoFechatrx = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPagoO = new SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private ObservableList<infoMovConv> movmientos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert buscar != null : "fx:id=\"buscar\" was not injected: check your FXML file 'ConsultaMovConvinit.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'ConsultaMovConvinit.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'ConsultaMovConvinit.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'ConsultaMovConvinit.fxml'.";
        assert numConv != null : "fx:id=\"numConv\" was not injected: check your FXML file 'ConsultaMovConvinit.fxml'.";
        assert numCtaRec != null : "fx:id=\"numCtaRec\" was not injected: check your FXML file 'ConsultaMovConvinit.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaMovConvinit.fxml'.";
        assert tipo_cuenta != null : "fx:id=\"tipo_cuenta\" was not injected: check your FXML file 'ConsultaMovConvinit.fxml'.";

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);

        progreso.setVisible(false);
        buscar.setDisable(true);

    }

    public void mostrarMovConvenio(final boolean esNuevaConsulta) {
        // nuevaConsulta.set(esNuevaConsulta);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaMovConvinit.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button botoncontinuarOp = (Button) root.lookup("#buscar");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final ComboBox<String> tipo_cuenta = (ComboBox<String>) root.lookup("#tipo_cuenta");

                    final DatePicker fechaini = (DatePicker) root.lookup("#fechaini");
                    final DatePicker fechafin = (DatePicker) root.lookup("#fechafin");

                    fechaini.setDateFormat(formatoFechaSalida);
                    fechafin.setDateFormat(formatoFechaSalida);
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    tipo_cuenta.setItems(emptyList);
                    final List<String> datosT = new ArrayList<String>();
                    datosT.add("Tipo de Cuenta");
                    datosT.add(CodigoProductos.CUENTA_AHORROS);
                    datosT.add(CodigoProductos.CUENTA_CORRIENTE);
                    tipo_cuenta.setItems(FXCollections.observableArrayList(datosT));
                    tipo_cuenta.getSelectionModel().select("Tipo de Cuenta");

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

//                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    cancelar.setCursor(Cursor.HAND);
//                                    cancelar.setEffect(shadow);
//                                }
//                            });
//
//                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    cancelar.setCursor(Cursor.DEFAULT);
//                                    cancelar.setEffect(null);
//
//                                }
//                            });
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
    void selTipocta(ActionEvent event) {
    }

    @FXML
    void buscarOP(ActionEvent event) {
        if (serviceMovPSE.isRunning()) {
            buscar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceMovPSE.progressProperty());
            serviceMovPSE.reset();
            serviceMovPSE.start();

        } else {
            buscar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceMovPSE.progressProperty());
            serviceMovPSE.reset();
            serviceMovPSE.start();
        }

        serviceMovPSE.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Movimientos PSE" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                ConsultaMovConvinitController.cancelartarea.set(false);

            }
        });

        serviceMovPSE.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Movimientos PSE" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceMovPSE.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                Logger.getGlobal().log(Level.SEVERE, t.getSource().getException().toString());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                buscar.setDisable(false);
            }
        });

    }

    public Service<Void> continuar_Op() {
        serviceMovPSE = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaMovConv = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        final AtlasAcceso Atlasacceso = new AtlasAcceso();
                        String rnv = "";
                        if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                            rnv = "E";
                        } else {

                            rnv = "P";
                        }

                        //850,068,connid,codigoTransaccion,tracepaginacion,empresa/persona(E o P),identificacion,fechadesde,fechahasta,registrosCanal,registroInicial,codConv,tipoCtaRec,cuentaRec,clavehw,~
                        tramaMovConv.append("850,068,");
                        tramaMovConv.append(cliente.getRefid());
                        tramaMovConv.append(",");
                        tramaMovConv.append(AtlasConstantes.COD_RECAUDOS_MOV_CONV);
                        tramaMovConv.append(",");
                        tramaMovConv.append(""); //tracePagincacion
                        tramaMovConv.append(",");
                        tramaMovConv.append(rnv); //regla negocio
                        tramaMovConv.append(",");
                        tramaMovConv.append(cliente.getId_cliente());
                        tramaMovConv.append(",");
                        tramaMovConv.append(formatoFechatrx.format(fechaini.getSelectedDate()));
                        tramaMovConv.append(",");
                        tramaMovConv.append(formatoFechatrx.format(fechafin.getSelectedDate()));
                        tramaMovConv.append(",");
                        tramaMovConv.append("40"); // registrosCanal
                        tramaMovConv.append(",");
                        tramaMovConv.append("0"); //registrosPaginacion
                        tramaMovConv.append(",");
                        tramaMovConv.append(numConv.getText());
                        tramaMovConv.append(",");
                        tramaMovConv.append(validarTipoCuenta(tipo_cuenta.getSelectionModel().getSelectedItem()));
                        tramaMovConv.append(",");
                        tramaMovConv.append(numCtaRec.getText());
                        tramaMovConv.append(",");
                        tramaMovConv.append(cliente.getContraseña());
                        tramaMovConv.append(",");
                        tramaMovConv.append(cliente.getTokenOauth());
                        tramaMovConv.append(",~");

//                        System.out.println("TRAMA mov Conv :" + tramaMovConv);
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  movmientos conv = " + "##" + docs.obtenerHoraActual());

//                            Respuesta="850,068,000,TRANSACCION EXITOSA,S,10,TracePaginacion,"
//                                    + "20180730%20180731%63815%reference1%reference2%reference3%descripcionCanal%1350000%metodoDePago%modoDeprocesamiento%7%25377634449%228888968%nombreDelPagador%0354%123456789235689%4500000%3500000%1500000%500000%1569%descripcionOficinaRecaudadora%Medellin%10%modoDeCaptura;"
//                                    + "20180730%20180731%21343%reference1%reference2%reference3%descripcionCanal%1350000%metodoDePago%modoDeprocesamiento%7%25377634449%228888968%nombreDelPagador%0354%123456789235689%4500000%3500000%1500000%500000%1569%descripcionOficinaRecaudadora%Medellin%10%modoDeCaptura;"
//                                    + "20180730%20180731%65433%reference1%reference2%reference3%descripcionCanal%1350000%metodoDePago%modoDeprocesamiento%7%25377634449%228888968%nombreDelPagador%0354%123456789235689%4500000%3500000%1500000%500000%1569%descripcionOficinaRecaudadora%Medellin%10%modoDeCaptura;"
//                                    + "20180730%20180731%676443%reference1%reference2%reference3%descripcionCanal%1350000%metodoDePago%modoDeprocesamiento%7%25377634449%228888968%nombreDelPagador%0354%123456789235689%4500000%3500000%1500000%500000%1569%descripcionOficinaRecaudadora%Medellin%10%modoDeCaptura;,~";
//                  
                            //  Respuesta="850,068,000,TRANSACCION EXITOSA,S,000168,tracepaginac,20180629112242%20180629%1206540        %09561111                           %                                   %                                   %Sucursal Virtual Personas     %350000.00       %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889921                         %Pagostren35                                                                     %1007    %40619921000         %35000000%00%00%00%     %                                                                                %                                                  %0  %0;20180629112240%20180629%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180628154315%20180628%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145237%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145236%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145236%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145236%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145235%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180627145206%20180627%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180620093324%20180620%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180619173630%20180619%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180618113912%20180618%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180618113912%20180618%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180616152825%20180616%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180615105513%20180615%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180614100509%20180614%1206540        %1234566                            %                                   %                                   %SUCURSAL F?SICA               %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180614100403%20180614%1206540        %09561111                           %                                   %                                   %Sucursal Virtual Personas     %350000.00       %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889921                         %Pagostren35                                                                     %1007    %40619921000         %35000000%00%00%00%     %                                                                                %                                                  %0  %0;20180613100750%20180613%1206540        %44446                              %                                   %                                   %Sucursal Virtual Personas     %2000000.00      %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920000         %200000000%00%00%00%     %                                                                                %                                                  %0  %0;20180613100330%20180613%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112213%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112213%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112213%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112212%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112212%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112212%20180612%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180612112203%20180612%1206540        %2222222                            %                                   %                                   %Sucursal Virtual Personas     %1000.00         %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889939                         %Pagostren53                                                                     %1007    %40619939000         %100000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154709%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154709%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154708%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154708%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154708%20180606%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180606154706%20180606%1206540        %2222222                            %                                   %                                   %Sucursal Virtual Personas     %1000.00         %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889939                         %Pagostren53                                                                     %1007    %40619939000         %100000%00%00%00%     %                                                                                %                                                  %0  %0;20180601154929%20180601%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111611%20180531%1206540        %1115                               %                                   %                                   %Sucursal Virtual Personas     %131.00          %Cuenta de Ahorros             %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889903                         %Pagostren03                                                                     %1007    %40679903010         %13100%00%00%00%     %                                                                                %                                                  %0  %0;20180531111215%20180531%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111154%20180531%1206540        %09561111                           %                                   %                                   %Sucursal Virtual Personas     %350000.00       %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889921                         %Pagostren35                                                                     %1007    %40619921000         %35000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111152%20180531%1206540        %1109                               %                                   %                                   %Sucursal Virtual Personas     %131000.00       %Cuenta de Ahorros             %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889903                         %Pagostren03                                                                     %1007    %40679903009         %13100000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111116%20180531%1206540        %1234566                            %                                   %                                   %Sucursal Virtual Personas     %10000000.00     %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920001         %1000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531111114%20180531%1206540        %4440                               %                                   %                                   %Sucursal Virtual Personas     %3000000000000.00%Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889920                         %Pagostren34                                                                     %1007    %40619920000         %300000000000000%00%00%00%     %                                                                                %                                                  %0  %0;20180531110856%20180531%1206540        %1111                               %                                   %                                   %Sucursal Virtual Personas     %13100.00        %Cuenta corriente              %Pago programado               %Cuenta de Ahorros             %00099000001         %1037889903                         %Pagostren03                                                                     %1007    %40619903001         %1310000%00%00%00%     %                                                                                %                                                  %0  %0,~";
                            // Thread.sleep(2000);
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMovConv.toString());
                            // Respuesta="850,006,000,TRANSACCIÓN EXITOSA,000000000100000,000000000000000,000000001500000,000000000000000,-35000000,000001500000000,000000000000000,000000001500000,000000000000000,,               ,,000000000000000,11151215,00,0000000000,000000000000000,0,0000000000000000000,,0,,~";
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 2, ",")  + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES= " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMovConv.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 2, ",")  + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ConsultaMovConvinitController.cancelartarea.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            buscar.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);
                                        }
                                    }
                                });
                            }
                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            final String numconv = numConv.getText();

                            final String indMasREg = Respuesta.split(",")[4];
                            final String numReg = Respuesta.split(",")[5];
                            final String tracePaginacion = Respuesta.split(",")[6];
                            final int registrosPag = Respuesta.split(",")[7].split(";").length;
                            movmientos.clear();
                            movmientos = FXCollections.observableArrayList();

                            String[] LMov = Respuesta.split(",")[7].split(";");

                            for (int i = 0; i < LMov.length; i++) {

                                String[] datos = LMov[i].split("%");
                                String fechatran = "";
                                String fechaaplic = "";
                                String montopago = "";
                                String valororiginal = "";
                                String valorcomision = "";
                                String pagoefectivo = "";
                                String pagocheuqe = "";

                                try {
                                    fechatran = formatoFechaPago.format(formatoFechaPagoO.parse(datos[0].trim()));

                                } catch (Exception e) {
                                    fechatran = datos[0].trim();
                                    Logger.getLogger(ConsultaMovConvinitController.class.getName()).log(Level.SEVERE, e.toString());

                                }

                                try {
                                    fechaaplic = formatoFechaSalida.format(formatoFecha.parse(datos[1].trim()));

                                } catch (Exception e) {
                                    fechaaplic = datos[1].trim();
                                    Logger.getLogger(ConsultaMovConvinitController.class.getName()).log(Level.SEVERE, e.toString());

                                }

                                try {
                                    montopago = "$ " + formatonum.format(Double.parseDouble(datos[7].trim().split("\\.")[0])).replace(".", ",") + "." + datos[7].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    montopago = "$ " + datos[7];
                                }

                                try {
                                    valororiginal = "$ " + formatonum.format(Double.parseDouble(datos[16].trim().split("\\.")[0])).replace(".", ",") + "." + datos[16].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    valororiginal = "$ " + datos[16];
                                    Logger.getLogger(ConsultaMovConvinitController.class.getName()).log(Level.SEVERE, e.toString());

                                }

                                try {
                                    valorcomision = "$ " + formatonum.format(Double.parseDouble(datos[19].trim().split("\\.")[0])).replace(".", ",") + "." + datos[19].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    valorcomision = "$ " + datos[19];
                                    Logger.getLogger(ConsultaMovConvinitController.class.getName()).log(Level.SEVERE, e.toString());

                                }

                                try {
                                    pagocheuqe = "$ " + formatonum.format(Double.parseDouble(datos[18].trim().split("\\.")[0])).replace(".", ",") + "." + datos[18].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    pagocheuqe = "$ " + datos[18];
                                    Logger.getLogger(ConsultaMovConvinitController.class.getName()).log(Level.SEVERE, e.toString());

                                }

                                try {
                                    pagoefectivo = "$ " + formatonum.format(Double.parseDouble(datos[17].trim().split("\\.")[0])).replace(".", ",") + "." + datos[17].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    pagoefectivo = "$ " + datos[17];
                                    Logger.getLogger(ConsultaMovConvinitController.class.getName()).log(Level.SEVERE, e.toString());

                                }

//  private SimpleStringProperty fechatran;
//  private SimpleStringProperty fechaaplic;
//  private SimpleStringProperty codigoconv;
//  private SimpleStringProperty ref1;
//  private SimpleStringProperty ref2;
//  private SimpleStringProperty ref3;
//  private SimpleStringProperty descripcioncanal;
//  private SimpleStringProperty montopago;
//  private SimpleStringProperty metodopago;
//  private SimpleStringProperty modoprocess;
//  private SimpleStringProperty tipoctarec;
//  private SimpleStringProperty numctarec;
//  private SimpleStringProperty nitpagador;
//  private SimpleStringProperty nombrepagador;
//  private SimpleStringProperty codigobancpagador;
//  private SimpleStringProperty numctatarjpagador;
//  private SimpleStringProperty valororiginal;
//  private SimpleStringProperty pagoefect;
//  private SimpleStringProperty pagocheque;
//  private SimpleStringProperty valorcomision;
//  private SimpleStringProperty codoficinarec;
//  private SimpleStringProperty descoficinarec;
//  private SimpleStringProperty nomciudad;
//  private SimpleStringProperty diascanje;
//  private SimpleStringProperty modocaptura;
                                infoMovConv mov = new infoMovConv(fechatran,
                                        fechaaplic,
                                        datos[2].trim(),
                                        datos[3].trim(),
                                        datos[4].trim(),
                                        datos[5].trim(),
                                        datos[6].trim(),
                                        montopago.trim(),
                                        datos[8].trim(),
                                        datos[9].trim(),
                                        datos[10].trim(),
                                        datos[11].trim(),
                                        datos[12].trim(),
                                        datos[13].trim(),
                                        datos[14].trim(),
                                        datos[15].trim(),
                                        valororiginal.trim(),
                                        pagoefectivo.trim(),
                                        pagocheuqe.trim(),
                                        valorcomision.trim(),
                                        datos[20].trim(),
                                        datos[21].trim(),
                                        datos[22].trim(),
                                        datos[23].trim(),
                                        datos[24]);

                                movmientos.add(mov);

                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final ConsultaMovConvDetController controller = new ConsultaMovConvDetController();
                                    controller.mostrarMovConv(movmientos,
                                            indMasREg,
                                            numReg,
                                            40,
                                            formatoFechatrx.format(fechaini.getSelectedDate()),
                                            formatoFechatrx.format(fechafin.getSelectedDate()),
                                            numConv.getText(),
                                            validarTipoCuenta(tipo_cuenta.getSelectionModel().getSelectedItem()),
                                            numCtaRec.getText(),
                                            tracePaginacion,
                                            1);
                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ConsultaMovConvinitController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        buscar.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                }
                            });

                        }

                        return null;
                    }
                };
            }
        };

        return serviceMovPSE;

    }

    private void analisisFecha() {
        if (fechaini.getSelectedDate() != null && fechafin.getSelectedDate() != null) {

            rangoFecha(fechaini.getSelectedDate(), fechafin.getSelectedDate());
        }
    }
    private transient final ChangeListener<Date> eventoMenuFechaV = new ChangeListener<Date>() {
        @Override
        public void changed(ObservableValue<? extends Date> ov, Date viejoValor, Date nuevoVal) {
            analisisFecha();

        }
    };
    private transient final ChangeListener<Date> eventoMenuFechaN = new ChangeListener<Date>() {
        @Override
        public void changed(ObservableValue<? extends Date> ov, Date viejoValor, Date nuevoVal) {
            analisisFecha();

        }
    };

    private boolean rangoFecha(final Date calendarFi, final Date calendarFf) {

        Calendar calendarFiaux = Calendar.getInstance();
        Calendar calendarFfaux = Calendar.getInstance();

        calendarFiaux.setTime(calendarFi);
        calendarFfaux.setTime(calendarFf);
        final int diferenciaDias = docs.CalcularDifFechas(calendarFiaux, calendarFfaux);

//        System.out.println("diferencia dias " + diferenciaDias);
        if ((diferenciaDias <= 30) && (diferenciaDias != -1)) {
            if (!numConv.getText().isEmpty()) {
                // numConv.setDisable(false);
                buscar.setDisable(false);
            } else {
                buscar.setDisable(true);
            }
        } else {
            // numConv.setText("");
            // numConv.setDisable(true);
            buscar.setDisable(true);
        }

        return true;

    }

    @FXML
    void numConvkeyPress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                buscar.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(numConv, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                numConv.clear();
                numConv.fireEvent(keyEvent);

            }

        }
    }

    @FXML
    void numConvkeytyped(KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#buscar");
                    final TextField numConv = (TextField) root.lookup("#numConv");

                    if (numConv.getText().isEmpty()) {
                        buttonCont.setDisable(true);
                        try {
                            rangoFecha(fechaini.getSelectedDate(), fechafin.getSelectedDate());
                        } catch (Exception e) {
                            Logger.getLogger(ConsultaMovConvinitController.class.getName()).log(Level.SEVERE, e.toString());
                        }

                    } else {
                        try {
                            rangoFecha(fechaini.getSelectedDate(), fechafin.getSelectedDate());
                        } catch (Exception e) {
                            Logger.getLogger(ConsultaMovConvinitController.class.getName()).log(Level.SEVERE, e.toString());
                        }
                    }

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Logger.getLogger(ConsultaMovConvinitController.class.getName()).log(Level.SEVERE, ex.toString());

                }

            }
        });
    }

    private String validarTipoCuenta(final String Cuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(Cuenta)) {
            retorno.append("7");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(Cuenta)) {
            retorno.append("1");
        } else {
            retorno.append("0");
        }

        return retorno.toString();
    }
}
