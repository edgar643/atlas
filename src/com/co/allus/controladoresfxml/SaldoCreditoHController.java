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
import com.co.allus.modelo.corehipotecario.DatosSaldoH;
import com.co.allus.modelo.corehipotecario.TiposIdentificacion;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class SaldoCreditoHController implements Initializable {

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
    private ComboBox<String> creditosHipotecarios;
    @FXML
    private Label tituloCreditoH;
    @FXML
    private ProgressBar progreso;
    private transient Service<Void> serviceSaldoCreditoH;
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'SaldoCreditoH.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'SaldoCreditoH.fxml'.";
        assert creditosHipotecarios != null : "fx:id=\"creditosHipotecarios\" was not injected: check your FXML file 'SaldoCreditoH.fxml'.";
        assert tituloCreditoH != null : "fx:id=\"tituloCreditoH\" was not injected: check your FXML file 'SaldoCreditoH.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'SaldoCreditoH.fxml'.";
        this.location = url;
        this.resources = rb;
        progreso.setVisible(false);
    }

    @FXML
    void cancel_op(final ActionEvent event) {

        try {
            continuar_Op().cancel();
        } catch (Exception e) {
            gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }

        final Parent frameGnral = Atlas.vista.getScene().getRoot();
        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
        final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
        arbol_saldos.setDisable(false);
        arbol_saldos.getSelectionModel().clearSelection();

        try {
            pane.getChildren().remove(3);

        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
    }

    @FXML
    void continuar_OP(final ActionEvent event) {

        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Saldo Credito Hipotecario" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Saldo Credito Hipotecario" + "##" + gestorDoc.obtenerHoraActual());
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

    @FXML
    void selCreditosHipotecarios(final ActionEvent event) {
        if (Atlas.MENSAJE_CREDITO_H_COMBO.equals(creditosHipotecarios.getSelectionModel().getSelectedItem())) {
            continuar_op.setDisable(true);
        } else {
            continuar_op.setDisable(false);
        }
    }

    public Service<Void> continuar_Op() {
        serviceSaldoCreditoH = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaSaldoH = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        final Date fecha = new Date();
                        tramaSaldoH.append("850,001,");
                        tramaSaldoH.append(cliente.getRefid());
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(AtlasConstantes.COD_SALDO_CREDITO_H);
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(cliente.getId_cliente());
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(formatoFecha.format(fecha));
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(formatoHora.format(fecha));
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(creditosHipotecarios.getSelectionModel().getSelectedItem());
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(cliente.getContraseña());
                        tramaSaldoH.append(",");
                        tramaSaldoH.append(cliente.getTokenOauth());
                        tramaSaldoH.append(",~");

//                        System.out.println("TRAMA SALDO HIPOTECARIO :" + tramaSaldoH);



                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ saldo hipotecario = " + "##" + gestorDoc.obtenerHoraActual());

//                            Respuesta= 
//                                   "850,"
//                                   + "001,"
//                                   + "000," // COD RESPUESTA
//                                   + "000 :TRANSACCION EXITOSA," //  DESC RESPUESTA
//                                   + "90000000267     ," // NUM CUENTA
//                                   + "036,"  //NUM CUOTA
//                                   + "                    ," // TIPO PRESTAMO
//                                   + "0000000000000000," // SALDO CAPITA ENT
//                                   + "00," // SALDO CAPITAL CENT
//                                   + "0000000000000000," // SALDO INTERESES ENT
//                                   + "00," // SALDO INTERESES CENT
//                                   + "0000000000182752," // SALDO EN MORA ENT
//                                   + "72," // SALDO EN MORA CENT
//                                   + "0000000000000000," //VALOR SEGUROS ENT
//                                   + "00," // VALOR SEGUROS CENT
//                                   + "0000000000000000," // OTROS SALDO ENT
//                                   + "00," // OTROS SALDO CENT
//                                   + "0000000000000000," //SALDO TOTAL ENT
//                                   + "00," // SALDO TOTAL CENT
//                                   + "02/10/2017," // FECHA VENCIMIENTO - 19
//                                   + "001," //NUM PROXIMA CUOTA
//                                   + "02/02/2015," // FECHA PROX CUOTA
//                                   + "0000000000000000,"  // VALOR CUOTA SIN SEGUROS
//                                   + "00," // VALOR CUOTA SIN SEGUROS CENT
//                                   + "04," //NUM CUOTA MORA
//                                   + "0000000000616086," // VALOR DE LAS CUOTAS EN MORA
//                                   + "04," // VALOR DE LAS CUOTAS MORA CENT  -24
//                                   + "PLAN DE PAGO                                      ," //PLAN
//                                   + "014057900,"  // TASA DE INTERES
//                                   + "0000000000155613," // VALOR CUOTA ENT
//                                   + "51," // VALOR CUOTA CENT
//                                   + "0000000003900000,"  // VALOR INICIAL ENT
//                                   + "00," // VALOR INCIAL CENT
//                                   + "02/10/2014," // FECHA DESMBOLSO
//                                   + "M," // INDICADOR MORA
//                                   + "1," // TIPO ID CLIENTE   
//                                   // NUEVOS CAMPOS
//                                   + "54322232333455," // NUMERO CREDITO ANT
//                                   + "54345545644545," // NUMERO CREDITO ORI
//                                   + "0000000000000000,"  // INGRESOS RECIB X ANT ENT
//                                   + "00," // INGRESOS RECIB X ANT CENT
//                                   + "0000000000000000,"  //SEGUROS  RECIB X ANT ENT
//                                   + "00,"   // SEGUROS RECIB X ANT CENT                                
//                                   + "~";

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaSaldoH.toString());
//                            System.out.println(" RESPUESTA TRAMA SALDO HIPOTECARIO :" + Respuesta);
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ saldo hipotecario = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaSaldoH.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDOS);
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            final DatosSaldoH infoSaldoCreditoH = DatosSaldoH.getDatosSaldoh();

                            infoSaldoCreditoH.setObligacion(Respuesta.split(",")[4].trim());
                            infoSaldoCreditoH.setNumerocuota(Respuesta.split(",")[5]);
                            try {
                                infoSaldoCreditoH.setSaldoCapital("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[7])).replace(".", ",") + "." + Respuesta.split(",")[8]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }
                            try {
                                infoSaldoCreditoH.setSaldoIntereses("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[9])).replace(".", ",") + "." + Respuesta.split(",")[10]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                            try {
                                infoSaldoCreditoH.setSaldomora("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[11])).replace(".", ",") + "." + Respuesta.split(",")[12]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                            try {
                                infoSaldoCreditoH.setSeguro("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[13])).replace(".", ",") + "." + Respuesta.split(",")[14]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                            try {
                                infoSaldoCreditoH.setOtrossaldos("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[15])).replace(".", ",") + "." + Respuesta.split(",")[16]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                            try {
                                infoSaldoCreditoH.setSaldo("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[17])).replace(".", ",") + "." + Respuesta.split(",")[18]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }


                            try {
                                infoSaldoCreditoH.setFechavencimiento(formatoSalida.format(formatoFechaSalida.parse(Respuesta.split(",")[19])));
                            } catch (Exception ex) {
                                infoSaldoCreditoH.setFechavencimiento(Respuesta.split(",")[19].substring(0, 2) + "-" + Respuesta.split(",")[19].substring(2, 4) + "-" + Respuesta.split(",")[19].substring(4, 8));
                            }
                            infoSaldoCreditoH.setNumproxcuota(Respuesta.split(",")[20]);

                            try {
                                infoSaldoCreditoH.setFechaproxcuota(formatoSalida.format(formatoFechaSalida.parse(Respuesta.split(",")[21])));
                            } catch (Exception ex) {
                                infoSaldoCreditoH.setFechaproxcuota(Respuesta.split(",")[21].substring(0, 2) + "-" + Respuesta.split(",")[21].substring(2, 4) + "-" + Respuesta.split(",")[21].substring(4, 8));
                            }

                            try {
                                infoSaldoCreditoH.setValorcuotasinseg("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[22])).replace(".", ",") + "." + Respuesta.split(",")[23]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                            infoSaldoCreditoH.setNumerocuotasmora(Respuesta.split(",")[24]);
                            infoSaldoCreditoH.setPlancredito(Respuesta.split(",")[27].trim());
                            try {
                                infoSaldoCreditoH.setTasainteres(formatonum.format(Double.parseDouble(Respuesta.split(",")[28].substring(0, 3))).replace(".", ",") + "." + Respuesta.split(",")[28].substring(3, Respuesta.split(",")[28].length()) + "%");
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                            try {
                                infoSaldoCreditoH.setValorcuotamora("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[25])).replace(".", ",") + "." + Respuesta.split(",")[26]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                            try {
                                infoSaldoCreditoH.setValorcuotasaldo("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[29])).replace(".", ",") + "." + Respuesta.split(",")[30]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }
                            try {
                                infoSaldoCreditoH.setValorinicial("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[31])).replace(".", ",") + "." + Respuesta.split(",")[32]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }



                            try {
                                infoSaldoCreditoH.setFechadesenbolso(formatoSalida.format(formatoFechaSalida.parse(Respuesta.split(",")[33])));
                            } catch (Exception ex) {
                                infoSaldoCreditoH.setFechadesenbolso(Respuesta.split(",")[33].substring(0, 2) + "-" + Respuesta.split(",")[33].substring(2, 4) + "-" + Respuesta.split(",")[33].substring(4, 8));
                            }

                            infoSaldoCreditoH.setEstadoCredito(Respuesta.split(",")[34]);
                            infoSaldoCreditoH.setNumidcliente(cliente.getId_cliente());
                            infoSaldoCreditoH.setNombrecliente(cliente.getNombre());
                            infoSaldoCreditoH.setTipoidcliente(TiposIdentificacion.TiposIdentificacion.get(Respuesta.split(",")[35]));

                            // NUEVOS CAMPOS 
                            infoSaldoCreditoH.setNumcretidoant(Respuesta.split(",")[36]);
                            infoSaldoCreditoH.setNumcretidoorig(Respuesta.split(",")[37]);
                            try {
                                infoSaldoCreditoH.setIngrerecibxant("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[38])).replace(".", ",") + "." + Respuesta.split(",")[39]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                            try {
                                infoSaldoCreditoH.setSegurosrecibxant("$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[40])).replace(".", ",") + "." + Respuesta.split(",")[41]);
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }


                            DatosSaldoH.setDatosSaldoh(infoSaldoCreditoH);



                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final SaldoCreditoHFinController controller = new SaldoCreditoHFinController();
                                    controller.mostrasSaldoCreditoHFin(DatosSaldoH.getDatosSaldoh());
                                }
                            });

                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_HIP);
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

        return serviceSaldoCreditoH;

    }
}
