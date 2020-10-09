/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultamovimientos.infoMovPSE;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class MovRecaudosPSEinitController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField codConvenio;
    @FXML
    private Button continuarOP;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private HBox menu_progreso;
    @FXML
    private ProgressBar progreso;
    private transient Service<Void> serviceMovPSE = continuar_Op();
    private static AtomicBoolean cancelartareaMovimientos = new AtomicBoolean();
    private static AtomicBoolean nuevaConsulta = new AtomicBoolean();
    private static GestorDocumental docs = new GestorDocumental();
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPagoO = new SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private ObservableList<infoMovPSE> movmientos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert codConvenio != null : "fx:id=\"codConvenio\" was not injected: check your FXML file 'MovRecaudosPSEinit.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'MovRecaudosPSEinit.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'MovRecaudosPSEinit.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'MovRecaudosPSEinit.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'MovRecaudosPSEinit.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'MovRecaudosPSEinit.fxml'.";

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);
        codConvenio.setDisable(true);
        continuarOP.setDisable(true);
        progreso.setVisible(false);


    }

    public void mostrarMovRecaudosPSE(final boolean esNuevaConsulta) {
        nuevaConsulta.set(esNuevaConsulta);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/MovRecaudosPSEinit.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuarOP");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

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
            codConvenio.setDisable(false);
            continuarOP.setDisable(false);
        } else {
            codConvenio.setText("");
            codConvenio.setDisable(true);
            continuarOP.setDisable(true);
        }


        return true;


    }

    @FXML
    private void ContinuarOP(ActionEvent event) {

        if (serviceMovPSE.isRunning()) {
            continuarOP.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceMovPSE.progressProperty());
            serviceMovPSE.reset();
            serviceMovPSE.start();

        } else {
            continuarOP.setDisable(true);
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
                MovRecaudosPSEinitController.cancelartareaMovimientos.set(false);

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
//                System.out.println("fallo");
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                continuarOP.setDisable(false);
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

                        final StringBuilder tramaMOvPES = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();



                        // 850,062,connid,codtransaccion,identificacion,fechaini,fechafin,cantReg,cantREgPaginacion,codConv,Referencia,claveHardware,~

                        tramaMOvPES.append("850,062,");
                        tramaMOvPES.append(cliente.getRefid());
                        tramaMOvPES.append(",");
                        tramaMOvPES.append(AtlasConstantes.COD_CONS_MOV_PSE);
                        tramaMOvPES.append(",");
                        tramaMOvPES.append(""); //tracePagincacion
                        tramaMOvPES.append(",");
                        tramaMOvPES.append(cliente.getId_cliente());
                        tramaMOvPES.append(",");
                        tramaMOvPES.append(formatoFecha.format(fechaini.getSelectedDate()));
                        tramaMOvPES.append(",");
                        tramaMOvPES.append(formatoFecha.format(fechafin.getSelectedDate()));
                        tramaMOvPES.append(",");
                        tramaMOvPES.append(""); // registrosCanal
                        tramaMOvPES.append(",");
                        tramaMOvPES.append(""); //registrosPaginacion
                        tramaMOvPES.append(",");
                        tramaMOvPES.append(codConvenio.getText());
                        tramaMOvPES.append(",");
                        tramaMOvPES.append(cliente.getContraseña());
                         tramaMOvPES.append(",");
                        tramaMOvPES.append(cliente.getTokenOauth());
                        tramaMOvPES.append(",~");


//                        System.out.println("TRAMA mov PSE :" + tramaMOvPES);



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ MOVIMIENTOS PES = " + "##" + docs.obtenerHoraActual());


//                            Respuesta="850,062,000,TRANSACCION EXITOSA,S,130,"
//                                    + "087%C.E.S prepago Medicina%02/04/2017 14:25:18%02/04/2017%123456%nomref1%102087459%nomref2%000023%nomRef3%$115.000%$115.000%Cuenta Ahorro%2537763449%250723489%pse%Allus%carabobo%1033%1023;"
//                                    + "1004%Instituto de Artes%02/04/2017 16:00:00%02/04/2017%321456%nomref1%69854782%nomref2%149955%nomRef3%$120.000%$120.000%Cuenta Ahorro%2537763449%250824569%pse%Konecta%la 33 %1034%1489;"
//                                    + "1953%DIRECTV%04/04/2017 17:30:15%02/04/2017%123652%nomref1%4258746%nomref2%1478500%nomRef3%$87.500%$87.500%Cuenta de Ahorro%2537763449%250736895%telefónico%EPM%Centro Carabobo%1035%2936;"
//                                    + "2014%EPM BOGOTA%03/04/2017 09:26:23%02/04/2017%188455451%nomref1%ref2%nomref2%000052%nomRef3%$95.000%$95.000%Cuenta de Ahorro%2537763449%250736985%virtual%Sempai S.A%Puerta Norte%1533%1026;"
//                                    + "1015%UNE%04/04/2017 13:25:26%02/04/2017%125482%nomref1%1258961%nomref2%1485555%nomRef3%$113.000%$113.000%Cuenta de Ahorro%2537763449%250735896%pse%Directv%Bello Parque%1545%3696;,~";
//                            
                            // Thread.sleep(2000);
                            // Respuesta = "850,062,000,TRANSACCION EXITOSA,S,000045,12454842132168489,26014          %PSE -DIAN                                                                       %20170531152706%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %102.03          %102.03          %Ahorros                       %00113806311         %26354       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170531152146%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %102.03          %102.03          %Ahorros                       %00113806311         %26353       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170531143401%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %102.02          %102.02          %Ahorros                       %00113806311         %26351       %03                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;28058          %PSE - SSS                                                                       %20170531084315%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %102.01          %102.01          %Ahorros                       %00113806311         %26346       %13                            %FirmaPSE                      %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170531083858%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %111                                %CUS                                               %102.00          %102.00          %Ahorros                       %00113806311         %26345       %03                            %FirmaPSE                      %SUCURSAL VIRTUAL                                                                %276  %                                   ;28058          %PSE - SSS                                                                       %20170531083445%20170217%12345678901234567890123456789012345%REF1                                              %                                   %REF2                                              %111                                %CUS                                               %101.99          %101.99          %Ahorros                       %00113806311         %26344       %03                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170530164501%20170217%12345678901234567890123456789012345%REF1                                              %1128405120                         %REF2                                              %111                                %CUS                                               %101.98          %101.98          %Ahorros                       %00113806311         %26222       %03                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170530164239%20170217%12345678901234567890123456789012345%REF1                                              %1128405120                         %REF2                                              %111                                %CUS                                               %101.97          %101.97          %Ahorros                       %00113806311         %26221       %03                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170530163104%20170217%12345678901234567890123456789012345%REF1                                              %1128405120                         %REF2                                              %111                                %CUS                                               %101.96          %101.96          %Ahorros                       %00113806311         %26217       %03                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170530162521%20170217%                                   %REF1                                              %1128405120                         %REF2                                              %111                                %CUS                                               %101.95          %101.95          %Ahorros                       %00113806311         %26216       %03                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170530160606%20170217%511                                %REF1                                              %1128405120                         %REF2                                              %111                                %CUS                                               %101.95          %101.95          %Ahorros                       %00113806311         %26200       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100321%20170218%68459555571                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26794       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100250%20170218%68459555570                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26793       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100227%20170218%68459555569                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26792       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100207%20170218%68459555568                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26791       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100148%20170218%68459555567                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26790       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100128%20170218%68459555566                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26789       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100106%20170218%68459555565                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26788       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100044%20170218%68459555564                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26787       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100024%20170218%68459555563                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26786       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602100005%20170218%68459555562                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26785       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602095944%20170218%68459555561                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26784       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602095925%20170218%68459555560                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26783       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602095904%20170218%68459555559                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26782       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602095843%20170218%68459555558                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26781       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602095820%20170218%68459555557                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26780       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602095755%20170218%68459555556                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26779       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602095725%20170218%68459555555                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26778       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602095212%20170218%68459555554                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26777       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170602095110%20170218%68459555553                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26776       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;26014          %PSE -DIAN                                                                       %20170602085633%20170218%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Ahorros                       %12345678901         %26757       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170601171721%20170215%68459555551                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26693       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;68459          %TESTC3334-FIXED                                                                 %20170601164352%20170218%68459555550                        %REFERENCIA 1                                      %234567898765                       %REFERENCIA 2                                      %2345654                            %REFERENCIA 3                                      %10000.00        %34567.98        %Ahorros                       %1234567890          %26639       %02                            %                              %AVENIDA EL POBLADO                                                              %005  %                                   ;26014          %PSE -DIAN                                                                       %20170601155408%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Ahorros                       %12345678901         %26584       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601154626%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Ahorros                       %00113806311         %26582       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601152112%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Corriente                     %00113806311         %26526       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601151836%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Corriente                     %00113806311         %26518       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601150351%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Corriente                     %00113806311         %26509       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601150135%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Corriente                     %00113806311         %26508       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ;26014          %PSE -DIAN                                                                       %20170601114328%20170217%12345678901234567890123456789012345%REF1                                              %12345678901234567890123456789012345%REF2                                              %12345678901234567890123456789012345%CUS                                               %12345678901234.0%12345678901234.0%Corriente                     %00113806311         %26449       %13                            %111                           %SUCURSAL VIRTUAL                                                                %276  %                                   ,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMOvPES.toString());
                            // Respuesta="850,006,000,TRANSACCIÓN EXITOSA,000000000100000,000000000000000,000000001500000,000000000000000,-35000000,000001500000000,000000000000000,000000001500000,000000000000000,,               ,,000000000000000,11151215,00,0000000000,000000000000000,0,0000000000000000000,,0,,~";
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES= " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMOvPES.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (MovRecaudosPSEinitController.cancelartareaMovimientos.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            continuarOP.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);
                                        }
                                    }
                                });
                            }
                        }



                        if ("000".equals(Respuesta.split(",")[2])) {


                            final String indMasREg = Respuesta.split(",")[4];
                            final String numReg = Respuesta.split(",")[5];
                            final String tracePaginacion = Respuesta.split(",")[6];
                            final int registrosPag = Respuesta.split(",")[7].split(";").length;
                            movmientos.clear();
                            movmientos = FXCollections.observableArrayList();

                            String[] LMov = Respuesta.split(",")[7].split(";");

                            for (int i = 0; i < LMov.length; i++) {


//            String codigoconv, 
//            String numconv, 
//            String fechaPago,
//            String fechaContable                    
//            String ref1, 
//            String nomRef1,
//            String ref2, 
//            String nomRef2, 
//            String ref3, 
//            String nomRef3,
//            String valorFactura                                
//            String valorPagado, 
//            String meotodoPagado, 
//            String cuentaRecaudoadora,
//            String paymentID, 
//            String canalPago,
//            String nombreCompania, 
//            String nombreOficina,
//            String codOficina,
//            String codPuntoPago


                                String[] datos = LMov[i].split("%");
                                String fechaPago = "";
                                String valorPagado = "";
                                String valorFactura = "";

                                try {
                                    fechaPago = formatoFechaPago.format(formatoFechaPagoO.parse(datos[2].trim()));

                                } catch (Exception e) {
                                    fechaPago = datos[2].trim();
                                }

                                try {
                                    valorPagado = "$ " + formatonum.format(Double.parseDouble(datos[10].trim().split("\\.")[0])).replace(".", ",") + "." + datos[10].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    valorPagado = "$ " + datos[10];
                                }

                                try {
                                    valorFactura = "$ " + formatonum.format(Double.parseDouble(datos[11].trim().split("\\.")[0])).replace(".", ",") + "." + datos[11].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    valorFactura = "$ " + datos[11];
                                }

                                infoMovPSE mov = new infoMovPSE(datos[0].trim(),
                                        datos[1].trim(),
                                        fechaPago,
                                        datos[3].trim(),
                                        datos[4].trim(),
                                        datos[5].trim(),
                                        datos[6].trim(),
                                        datos[7].trim(),
                                        datos[8].trim(),
                                        datos[9].trim(),
                                        valorPagado,
                                        valorFactura,
                                        datos[12].trim(),
                                        datos[13].trim(),
                                        datos[14].trim(),
                                        datos[15].trim(),
                                        datos[16].trim(),
                                        datos[17].trim(),
                                        datos[18].trim(),
                                        datos[19].trim());


                                movmientos.add(mov);


                            }


                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    MovRecaudosPSEController controller = new MovRecaudosPSEController();
                                    controller.mostrarMovRecaudosPSE(movmientos, indMasREg, numReg, registrosPag, formatoFecha.format(fechaini.getSelectedDate()), formatoFecha.format(fechafin.getSelectedDate()), codConvenio.getText(), tracePaginacion, 1);

                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (MovRecaudosPSEinitController.cancelartareaMovimientos.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuarOP.setDisable(false);
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
}
