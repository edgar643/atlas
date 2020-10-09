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
import com.co.allus.modelo.pagosaterceros.infoArchivosRecibidos;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaArchRecinitController implements Initializable {

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
    private TextField codConv;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private ProgressBar progreso;
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private static GestorDocumental docs = new GestorDocumental();
    private transient Service<Void> serviceConsArchRec = continuar_Op();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static AtomicBoolean nuevaConsulta = new AtomicBoolean();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private ObservableList<infoArchivosRecibidos> archrec = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert buscar != null : "fx:id=\"buscar\" was not injected: check your FXML file 'ConsultaArchRecinit.fxml'.";
        assert codConv != null : "fx:id=\"codConv\" was not injected: check your FXML file 'ConsultaArchRecinit.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'ConsultaArchRecinit.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'ConsultaArchRecinit.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaArchRecinit.fxml'.";

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);

        progreso.setVisible(false);
        buscar.setDisable(true);

    }

    public void mostrarConsultaArchRecibidosInit(final boolean esNuevaConsulta) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaArchRecinit.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button botoncontinuarOp = (Button) root.lookup("#buscar");
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

                    botoncontinuarOp.setDisable(true);
                    label_menu.setVisible(false);

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
    void buscarOP(ActionEvent event) {
        if (serviceConsArchRec.isRunning()) {
            buscar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceConsArchRec.progressProperty());
            serviceConsArchRec.reset();
            serviceConsArchRec.start();

        } else {
            buscar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(serviceConsArchRec.progressProperty());
            serviceConsArchRec.reset();
            serviceConsArchRec.start();
        }

        serviceConsArchRec.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Movimientos PSE" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                ConsultaArchRecinitController.cancelartarea.set(false);

            }
        });

        serviceConsArchRec.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Movimientos PSE" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        serviceConsArchRec.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                Logger.getGlobal().log(Level.SEVERE, t.getSource().getException().toString());

//                System.out.println("fallo");
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                buscar.setDisable(false);
            }
        });
    }

    public Service<Void> continuar_Op() {
        serviceConsArchRec = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaArchEnv = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        final AtlasAcceso Atlasacceso = new AtlasAcceso();
                        String rnv = "";
                        if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                            rnv = "E";
                        } else {

                            rnv = "P";
                        }

                        //850,070,connid,codigotransaccion,tracepaginacion,empresa/persona(E o P),identificacion,codigoconv,fechadesde,fechahasta,regIni,totalRegCanal,clavehardware,~
                        tramaArchEnv.append("850,070,");
                        tramaArchEnv.append(cliente.getRefid());
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(AtlasConstantes.COD_RECAUDOS_ARCH_REC);
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(""); //tracePagincacion
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(rnv); //regla negocio
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(cliente.getId_cliente());
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(codConv.getText());
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(formatoFecha.format(fechaini.getSelectedDate()));
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(formatoFecha.format(fechafin.getSelectedDate()));
                        tramaArchEnv.append(",");
                        tramaArchEnv.append("40"); // registros cannal
                        tramaArchEnv.append(",");
                        tramaArchEnv.append("0"); //registros ini                      
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(cliente.getContraseña());
                        tramaArchEnv.append(",");
                        tramaArchEnv.append(cliente.getTokenOauth());
                        tramaArchEnv.append(",~");

//                        System.out.println("TRAMA archivosRecibidos :" + tramaArchEnv);
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  archivosENv = " + "##" + docs.obtenerHoraActual());

                            // Respuesta="850,070,000,TRANSACCION EXITOSA,S,092704883997,000066,3     %299.97          %20181022%5Id60qyXGhVXdFA2DgMxWudkPx7noG9O   %CargaExitoFacturaci?n20181022.txt                                                                   ;50    %540.00          %20181022%mvjtMjtRv5le5ht9A6yLIZCogZRIiab2   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %500.00          %20181011%lraL7y89QankFx8ewK8IPZ2Ld2OmaKHq   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %540.00          %20181011%NmT1M8JoD9txD0AEzAJMe44PxD8EoHk2   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;48    %480.00          %20181011%ELjSyF4oHnJzsT1l8065KljKkmhlVa19   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %540.00          %20181011%ZHyLzqRmDgc4yC7KzquIjVlCMtfX3viv   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %540.00          %20181011%6vFxxev0nCJ4oUwEvy5fIX5FWVJzNU9o   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %500.00          %20181011%jm8cAQyXeOp8nQg2UKBRatGLVdoY7kSk   %FACTURACION_BANCOLOMBIA.txt                                                                         ;      %.00             %20181011%ffUzHop64wqVaRuDaApwVVqY877AbKtU   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %540.00          %20181011%igR7J0WDZ236LUganNnnDVZB7ffbuIYw   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %540.00          %20181011%EN9pBkpsjetLexypKYlCfHfs2SuYVMzh   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %540.00          %20181011%QII0BCioZdDL5FDAPEobPBJ5jCzVsRLZ   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %540.00          %20181011%aPaikqzmuGYITztMraDQztxC65BZOnmI   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %500.00          %20181011%L3w0buonACyRbmWQMjDWCVDiC2Zudo4Q   %FACTURACION_BANCOLOMBIA.txt                                                                         ;50    %540.00          %20181011%DGoTc3LiQmQBk2CAW81q14OG5T3QxRCL   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;50    %540.00          %20181011%tOiEVdb0MxGi8tmJhOlyfATohgdF6NSM   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;6     %599.93          %20181010%csPXBQyyqquzTfZsCfjhXeSUtbJqpTBQ   %CargaExitoFacturaci?n20181010.txt                                                                   ;100000%1000000.00      %20181010%2W262ejkQEMide50n1XI5ySdyznWQTZP   %FACTURACION_BANCOLOMBIA.txt                                                                         ;4     %400.00          %20181010%H9KCFz65cZZAx4pkTDA5EcT5kQX2XSzL   %ArchivoEnviadoLuisa_23_1.txt                                                                        ;110   %11000.00        %20181010%tM0Bsejn6SoXUh1klNt4B0wUVwPVZ2ow   %ArchivoEnviadoLuisa_24_2.txt                                                                        ;6     %599.92          %20181010%6GVx2j5SgP6aSHw2h9FryElP2MJUg4Ti   %CargaExitoFacturaci?n20181010.txt                                                                   ;100000%1000000.00      %20181010%NNu3QpAICfFR3GSwgmVS4XUfBduV6Wab   %FACTURACION_BANCOLOMBIA.txt                                                                         ;      %.00             %20181010%AJufxS4LNwMLsvO1KrRrm1ylanQTLwM4   %FACTURACION_BANCOLOMBIA_50_REC.txt                                                                  ;      %.00             %20181010%CsYoHFerONFcK6b6TE7yQdmHO5j4Bvw2   %FACTURACION_BANCOLOMBIA.txt                                                                         ;4     %400.00          %20181009%c0Nje5F0kImWJwtMERmgDAo4GbSznbXN   %ArchivoEnviadoLuisa_22.txt                                                                          ;4     %400.00          %20181009%OrFXlcpBxIUIoNw619Hwbt4yFv6HIMud   %ArchivoEnviadoLuisa_22.txt                                                                          ;3     %299.99          %20181008%38dq04hvujVkeND4EEwwqiSjP3iYOLy8   %CargaExitoso2.txt                                                                                   ;3     %299.99          %20181008%yRgBz5LGUdh2BH041YWyCio91XLinh6l   %CargaExitoso2.txt                                                                                   ;3     %299.99          %20181008%P8zKLw48ceJrqhhlmRzyUaAZJMUx5Hru   %CargaExitoso2.txt                                                                                   ;3     %299.99          %20181008%GwEwQhquHRLQSlWFFA9KkDXw4y4UsZ50   %CargaExitoso2.txt                                                                                   ;3     %299.99          %20181008%gQodrWqsfUjLclKYTS7mxYguP0Jn2S3W   %CargaExitoso2.txt                                                                                   ;3     %299.96          %20181006%j9hKurK5AoCcvJMdZGcKjwVqD8Rex0wz   %CargaExitoFacturaci?n20181008.txt                                                                   ;3     %299.99          %20181005%VF9BvKdD62v4Gv6GYJqXLYaA0GaemTsr   %CargaExitoso2.txt                                                                                   ;4     %399.99          %20181005%2yDZAf3CftFSSGddV6LhFJxHfmrqLzEh   %ArchivoEnviadoLuisa_14.txt                                                                          ;4     %399.99          %20181005%sRWTxOGU9S2uyTv33OakOt15MNFzkmQC   %ArchivoEnviadoLuisa_14.txt                                                                          ;4     %400.00          %20181005%ZLRNZt8IUV43UA0Zkvgzi51wjHMAAytg   %ArchivoEnviadoLuisa_21.txt                                                                          ;4     %399.99          %20181005%DbSZQBIxYBhrWQDKa108eAExLAErb5EI   %ArchivoEnviadoLuisa_20.txt                                                                          ;3     %299.99          %20181005%XdQmtUhZvfZNJRuh20OYnpZ909gqsDSn   %CargaExitoso2.txt                                                                                   ;3     %299.99          %20181004%D0We0vbVWRvPWiJuiGib3ZJ0Uf1sgkcQ   %ArchivoEnviadoLuisa_13.txt                                                                          ;3     %299.97          %20181004%S1LEmnj6CFzp7Q7qCU9kwxhQE6ROeFdC   %CargaExitoFacturaci?n20181004.txt                                                                   ,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaArchEnv.toString());

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " +StringUtilities.tramaSubString(Respuesta, 0, 3, ",")  + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES= " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaArchEnv.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",")  + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ConsultaArchRecinitController.cancelartarea.get()) {
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

                            final String indMasREg = Respuesta.split(",")[4];
                            final String numReg = Respuesta.split(",")[6];
                            final String tracePaginacion = Respuesta.split(",")[5];
                            archrec.clear();
                            archrec = FXCollections.observableArrayList();

                            String[] LMov = Respuesta.split(",")[7].split(";");

                            for (int i = 0; i < LMov.length; i++) {

                                String[] datos = LMov[i].split("%");
                                String fechaapli = "";
                                String valorTotalArchivo = "";

                                try {
                                    fechaapli = formatoFechaSalida.format(formatoFecha2.parse(datos[2].trim()));

                                } catch (Exception e) {
                                    fechaapli = datos[2].trim();
                                }

                                try {
                                    valorTotalArchivo = "$ " + formatonum.format(Double.parseDouble(datos[1].trim().split("\\.")[0])).replace(".", ",") + "." + datos[1].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    valorTotalArchivo = "$ " + datos[1];
                                }

                                infoArchivosRecibidos mov = new infoArchivosRecibidos(
                                        datos[0].trim(),
                                        valorTotalArchivo.trim(),
                                        fechaapli.trim(),
                                        datos[3].trim(),
                                        datos[4].trim());

                                archrec.add(mov);

                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final ConsultaArhRecibController controller = new ConsultaArhRecibController();
                                    controller.mostrarArchRecib(archrec,
                                            indMasREg.trim(),
                                            numReg.trim(),
                                            40,
                                            formatoFecha.format(fechaini.getSelectedDate()),
                                            formatoFecha.format(fechafin.getSelectedDate()),
                                            codConv.getText(),
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
                                    if (ConsultaArchRecinitController.cancelartarea.get()) {
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

        return serviceConsArchRec;

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
            if (!codConv.getText().isEmpty()) {
                codConv.setDisable(false);
                buscar.setDisable(false);
            } else {
                buscar.setDisable(true);
            }
        } else {
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
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(codConv, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                codConv.clear();
                codConv.fireEvent(keyEvent);

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
                    final TextField numConv = (TextField) root.lookup("#codConv");

                    if (numConv.getText().isEmpty()) {
                        buttonCont.setDisable(true);
                        try {
                            rangoFecha(fechaini.getSelectedDate(), fechafin.getSelectedDate());
                        } catch (Exception ex) {
                            Logger.getLogger(ConsultaArchRecinitController.class.getName()).log(Level.SEVERE, null, ex);

                        }

                    } else {
                        try {
                            rangoFecha(fechaini.getSelectedDate(), fechafin.getSelectedDate());
                        } catch (Exception ex) {
                            //System.out.println("entro error por fechas en nulo");
                            Logger.getLogger(ConsultaArchRecinitController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });
    }
}
