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
import com.co.allus.modelo.consultadebitosaut.infoDebAut1;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.userComponent.RestrictiveTextField;
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
 * @author stephania.rojas
 */
public class MovDebAutIniController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button ContinuarOP;
    @FXML
    private RestrictiveTextField codConvenio;
    @FXML
    private ProgressBar progreso;
    @FXML
    private DatePicker tffin;
    @FXML
    private DatePicker tfini;
    private static GestorDocumental docs = new GestorDocumental();
    private transient Service<Void> serviceMovDeb = ContinuarOP();
    private static AtomicBoolean cancelartareaMovimientos = new AtomicBoolean();
    private static AtomicBoolean nuevaConsulta = new AtomicBoolean();
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPago = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaPagoO = new SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    // private ObservableList<infoDebAut1> movdebaut = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'MovDebAutIni.fxml'.";
        assert ContinuarOP != null : "fx:id=\"ContinuarOP\" was not injected: check your FXML file 'MovDebAutIni.fxml'.";
        assert codConvenio != null : "fx:id=\"codConvenio\" was not injected: check your FXML file 'MovDebAutIni.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'MovDebAutIni.fxml'.";
        assert tffin != null : "fx:id=\"tffin\" was not injected: check your FXML file 'MovDebAutIni.fxml'.";
        assert tfini != null : "fx:id=\"tfini\" was not injected: check your FXML file 'MovDebAutIni.fxml'.";
        this.resources = rb;
        this.location = url;

        tfini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        tffin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);
        codConvenio.setDisable(true);
        ContinuarOP.setDisable(true);
        progreso.setVisible(false);

    }

    public void mostrarMovDebitoAut(final boolean esNuevaConsulta) {

        nuevaConsulta.set(esNuevaConsulta);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/MovDebAutIni.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button ContinuarOP = (Button) root.lookup("#ContinuarOP");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final DatePicker tfini = (DatePicker) root.lookup("#tfini");
                    final DatePicker tffin = (DatePicker) root.lookup("#tffin");

                    tfini.setDateFormat(formatoFechaSalida);
                    tffin.setDateFormat(formatoFechaSalida);
                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);


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


                    ContinuarOP.setDisable(true);
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

    private boolean rangoFecha(final Date calendarFi, final Date calendarFf) {


        Calendar calendarFiaux = Calendar.getInstance();
        Calendar calendarFfaux = Calendar.getInstance();

        calendarFiaux.setTime(calendarFi);
        calendarFfaux.setTime(calendarFf);
        final int diferenciaDias = docs.CalcularDifFechas(calendarFiaux, calendarFfaux);

//        System.out.println("diferencia dias " + diferenciaDias);
        if ((diferenciaDias <= 31) && (diferenciaDias != -1)) {
            codConvenio.setDisable(false);
            ContinuarOP.setDisable(false);
        } else {
            codConvenio.setText("");
            codConvenio.setDisable(true);
            ContinuarOP.setDisable(true);
        }


        return true;


    }

    private void analisisFecha() {
        if (tfini.getSelectedDate() != null && tffin.getSelectedDate() != null) {

            rangoFecha(tfini.getSelectedDate(), tffin.getSelectedDate());
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

    @FXML
    private void ContinuarOP(ActionEvent event) {

        if (serviceMovDeb.isRunning()) {
            ContinuarOP.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceMovDeb.progressProperty());
            serviceMovDeb.reset();
            serviceMovDeb.start();
        } else {
            ContinuarOP.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceMovDeb.progressProperty());
            serviceMovDeb.reset();
            serviceMovDeb.start();
        }

        serviceMovDeb.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Movimientos PSE" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                MovDebAutIniController.cancelartareaMovimientos.set(false);
            }
        });


        serviceMovDeb.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Movimientos PSE" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceMovDeb.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                Logger.getGlobal().log(Level.SEVERE, t.getSource().getException().toString()); 
//                System.out.println("fallo");
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                ContinuarOP.setDisable(false);
            }
        });

    }

    public Service<Void> ContinuarOP() {
        serviceMovDeb = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaDebAut = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();



                        // 850,063,connid,codtransaccion,tracePaginacion,identificacion,fechaini(DDMMYYYY),fechafin(DDMMYYYY),cantReg,cantREgPaginacion,codConv,INDICADOR(empresas-personas),claveHardware,~
//                        850,063,01N5LN2U38CEP2REF0LH9B5AES03C855,0046,,1036929363,01112017,02112017,,,121213,,77EA68BDA4AFC567,~
                        tramaDebAut.append("850,063,");
                        tramaDebAut.append(cliente.getRefid());
                        tramaDebAut.append(",");
                        tramaDebAut.append(AtlasConstantes.COD_CONS_MOV_DEB);
                        tramaDebAut.append(",");
                        tramaDebAut.append(""); //tracePagincacion
                        tramaDebAut.append(",");
                        tramaDebAut.append(cliente.getId_cliente());
                        tramaDebAut.append(",");
                        tramaDebAut.append(formatoFecha.format(tfini.getSelectedDate()));
                        tramaDebAut.append(",");
                        tramaDebAut.append(formatoFecha.format(tffin.getSelectedDate()));
                        tramaDebAut.append(",");
                        tramaDebAut.append("0"); // cantReg
                        tramaDebAut.append(",");
                        tramaDebAut.append("0"); //cantREgPaginacion
                        tramaDebAut.append(",");
                        tramaDebAut.append(codConvenio.getText());
                        tramaDebAut.append(",");
                        final AtlasAcceso Atlasacceso = new AtlasAcceso();
                        String rnv = "";
                        if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                            rnv = "E";
                        } else {

                            rnv = "P";
                        }
                        tramaDebAut.append(rnv);//INDICADOR(empresas-personas)
                        tramaDebAut.append(",");
                        tramaDebAut.append(cliente.getContraseña());
                        tramaDebAut.append(",");
                        tramaDebAut.append(cliente.getTokenOauth());
                        tramaDebAut.append(",~");



                        //final String chw= (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio());
                        //final String chw = datosCliente.getContraseña().isEmpty() ? " " : datosCliente.getContraseña();
                        //tramaTokenDisEmpresas.append(chw);


//                        System.out.println("TRAMA Debito Aut :" + tramaDebAut);

                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ MOVIMIENTOS DEBAUT = " + "##" + docs.obtenerHoraActual());

                            // Thread.sleep(2000);
                            //Respuesta = "850,063,000,TRANSACCION EXITOSA,S,000063,101611542587,63149          %CRAZY -TÓWN &(COMPAÑÍA)#3.2.                                                    %20171026194319%20171026%78809                              %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%3%78809                              %                                                                                %Pago programado               %4343.00         %434300.00       %Corriente                     %40610115007         %04%04%EXITOSO                       %                                                                                %406396      %10074 %BANCOLOMBIA                             ;63149          %CRAZY -TÓWN &(COMPAÑÍA)#3.2.                                                    %20171026190852%20171026%89898                              %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%3%89898                              %                                                                                %Pago programado               %7878.00         %787800.00       %Corriente                     %40610115007         %00%00%EXITOSO                       %                                                                                %406385      %10074 %BANCOLOMBIA                             ;63036          %RECA-REF FIJA 1(FINACLE 1)                                                      %20171026185447%20171027%AR12345665432123456765432          %DIRECCIÓN#1                                       %                                   %                                                  %                                   %                                                  %1%AR12345665432123456765432          %                                                                                %Pago programado               %32300.00        %3230000.00      %Corriente                     %40610115007         %00%00%RECHAZADO                     %                                                                                %406375      %10074 %BANCOLOMBIA                             ;63149          %CRAZY -TÓWN &(COMPAÑÍA)#3.2.                                                    %20171026184922%20171026%67603                              %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%4                                  %NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR.%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%3%67603                              %                                                                                %Pago programado               %1000.00         %100000.00       %Corriente                     %40610115007         %00%00%EXITOSO                       %                                                                                %406369      %10074 %BANCOLOMBIA                             ,~";

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaDebAut.toString());
//                            System.out.println(" RESPUESTA TRAMA DEBAUT:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS DEB= " + "##" + docs.obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaDebAut.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = "+StringUtilities.tramaSubString(Respuesta, 0, 3, ",")+" ##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (MovDebAutIniController.cancelartareaMovimientos.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            ContinuarOP.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);
                                        }
                                    }
                                });
                            }
                        }

                        //  try {

                        if ("000".equals(Respuesta.split(",")[2])) {

                            try {


                                final String indMasREg = Respuesta.split(",")[4];
                                final String numReg = Respuesta.split(",")[5];
                                final String tracePaginacion = Respuesta.split(",")[6];
                                final int registrosPag = Respuesta.split(",")[7].split(";").length;
                                // movdebaut.clear();
                                // movdebaut = FXCollections.observableArrayList();
                                final List<infoDebAut1> movdebaut = new ArrayList<infoDebAut1>();

                                String[] LMov = Respuesta.split(",")[7].split(";");



                                for (int i = 0; i < LMov.length; i++) {


                                    String[] datos = LMov[i].split("%");
                                    String fechaPago = "";
                                    String valorFactura = "";
                                    String valorPagado = "";


                                    try {
                                        fechaPago = formatoFechaPago.format(formatoFechaPagoO.parse(datos[2].trim()));

                                    } catch (Exception e) {
                                        fechaPago = datos[2].trim();
                                    }

                                    try {
                                        valorPagado = "$ " + formatonum.format(Double.parseDouble(datos[15].trim().split("\\.")[0])).replace(".", ",") + "." + datos[15].trim().split("\\.")[1];
                                    } catch (Exception e) {
                                        valorPagado = "$ " + datos[15];
                                    }

                                    try {
                                        valorFactura = "$ " + formatonum.format(Double.parseDouble(datos[14].trim().split("\\.")[0])).replace(".", ",") + "." + datos[14].trim().split("\\.")[1];
                                    } catch (Exception e) {
                                        valorFactura = "$ " + datos[14];
                                    }
                                    // System.out.println("Datos 25 es" + datos[24].trim());   


                                    infoDebAut1 mov = new infoDebAut1(
                                            datos[0].trim(),
                                            datos[1].trim(),
                                            //datos[2].trim(),
                                            fechaPago,
                                            datos[3].trim(),
                                            datos[4].trim(),
                                            datos[5].trim(),
                                            datos[6].trim(),
                                            datos[7].trim(),
                                            datos[8].trim(),
                                            datos[9].trim(),
                                            datos[10].trim(),
                                            datos[11].trim(),
                                            datos[12].trim(),
                                            datos[13].trim(),
                                            //datos[14].trim(),    
                                            valorFactura,
                                            valorPagado,
                                            //datos[15].trim(),
                                            datos[16].trim(),
                                            datos[17].trim(),
                                            datos[18].trim(),
                                            datos[19].trim(),
                                            datos[20].trim(),
                                            datos[21].trim(),
                                            datos[22].trim(),
                                            datos[23].trim(),
                                            datos[24].trim());


                                    movdebaut.add(mov);


                                }



                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        MovDebAut2Controller controller = new MovDebAut2Controller();
                                        controller.mostrarMovDebAut(movdebaut, indMasREg, numReg, registrosPag, formatoFecha.format(tfini.getSelectedDate()), formatoFecha.format(tffin.getSelectedDate()), codConvenio.getText(), tracePaginacion, 1);

                                    }
                                });

                            } catch (Exception ex) {
                            docs.imprimir("Error  = " + ex.toString()  + docs.obtenerHoraActual());

                            }

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (MovDebAutIniController.cancelartareaMovimientos.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        ContinuarOP.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                }
                            });

                        }



//                        } catch (Exception e) {
//                           System.out.println("Error en la tarea");
//                        }

                        return null;
                    }
                };
            }
        };



        return serviceMovDeb;

    }
}
