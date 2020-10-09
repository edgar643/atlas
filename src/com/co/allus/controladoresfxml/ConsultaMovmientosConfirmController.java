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
import com.co.allus.modelo.consultamovimientos.infoTablaConsMov;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaMovmientosConfirmController implements Initializable {

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
    private Button consultar_costo;
    @FXML
    private Button continuar;
    @FXML
    private Label mensaje_mov;
    @FXML
    private HBox menu_progreso;
    @FXML
    private Pane pane_modalMov;
    @FXML
    private ProgressBar progreso;
    @FXML
    private ImageView icono;
    @FXML
    private Label tituloCta;
    private transient Service<Void> serviceCcosto;
    private transient Service<Void> serviceConsMov;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechatrasnf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static String OpcionConsTipoCtaMov;
    private static String cuenta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.location = url;
        this.resources = rb;
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'ConsultaMovmientosConfirm.fxml'.";
        assert consultar_costo != null : "fx:id=\"consultar_costo\" was not injected: check your FXML file 'ConsultaMovmientosConfirm.fxml'.";
        assert continuar != null : "fx:id=\"continuar\" was not injected: check your FXML file 'ConsultaMovmientosConfirm.fxml'.";
        assert mensaje_mov != null : "fx:id=\"mensaje_mov\" was not injected: check your FXML file 'ConsultaMovmientosConfirm.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'ConsultaMovmientosConfirm.fxml'.";
        assert pane_modalMov != null : "fx:id=\"pane_modalMov\" was not injected: check your FXML file 'ConsultaMovmientosConfirm.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaMovmientosConfirm.fxml'.";
        assert icono != null : "fx:id=\"icono\" was not injected: check your FXML file 'ConsultaMovmientosConfirm.fxml'.";
        assert tituloCta != null : "fx:id=\"tituloCta\" was not injected: check your FXML file 'ConsultaMovmientosConfirm.fxml'.";
        progreso.setVisible(false);

    }

    public void mostrarMovimientosConfirm(final String tipoCtaMov, final String cuenta) {
        this.OpcionConsTipoCtaMov = tipoCtaMov;
        this.cuenta = cuenta;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaMovimientosConfirm.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Label infoCosto = (Label) root.lookup("#mensaje_mov");
                    final Label titulocta = (Label) root.lookup("#tituloCta");
                    infoCosto.setText("RECUERDE QUE ESTA OPERACION PUEDE TENER COSTO");
                    titulocta.setText(tipoCtaMov + " : " + cuenta);
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();;

                } catch (Exception ex) {
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }
            }
        });

    }

    @FXML
    void cancel(final ActionEvent event) {
        try {
            serviceCcosto.cancel();
        } catch (Exception e) {
            gestorDoc.imprimir("Warning servicio CostoOp -->" + e.toString() + "  : " + gestorDoc.obtenerHoraActual());
        }
        try {
            serviceConsMov.cancel();
        } catch (Exception e) {
            gestorDoc.imprimir("Warning Servicio ConsultaMov -->" + e.toString() + "  : " + gestorDoc.obtenerHoraActual());
        }
        final ConsultaMovimientosController controller = new ConsultaMovimientosController();
        controller.mostrarConsultaMovimientos(this.OpcionConsTipoCtaMov);
    }

    @FXML
    void consulta_costo(final ActionEvent event) {
        consultar_Costo().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario presionó ConsultarCosto" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        consultar_Costo().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ConsultarCosto" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (consultar_Costo().isRunning()) {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(consultar_Costo().progressProperty());
            consultar_Costo().reset();
            consultar_Costo().start();

        } else {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(consultar_Costo().progressProperty());
            consultar_Costo().start();
        }
    }

    @FXML
    void continuarOP(final ActionEvent event) {
        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Transferencias ctas propias" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Transferencias ctas propias" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }
    }

    public Service<Void> consultar_Costo() {
        serviceCcosto = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaCosto = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();


                        tramaCosto.append("930,000,");
                        tramaCosto.append(validarTipoCuenta(OpcionConsTipoCtaMov));
                        tramaCosto.append(",");
                        tramaCosto.append(cliente.getId_cliente());
                        tramaCosto.append(",");
                        tramaCosto.append("GDE,");
                        tramaCosto.append(validarTipoTransaccion(OpcionConsTipoCtaMov));
                        tramaCosto.append(",");
                        tramaCosto.append(cuenta);
                        tramaCosto.append(",");
                        tramaCosto.append(validarTipoCuenta(OpcionConsTipoCtaMov));
                        tramaCosto.append(",~");


                        //850,023,CONNID,DOCID,ORIGEN,CODTRANSACCION,TIPOCTA,NUMCTA,CODFONDO,CLAVEHW,~
//                        tramaCosto.append("850,023,");
//                        tramaCosto.append(cliente.getRefid());
//                        tramaCosto.append(",");
//                        tramaCosto.append(cliente.getId_cliente());
//                        tramaCosto.append(",");
//                        tramaCosto.append("GDE,");
//                        tramaCosto.append(validarTipoTransaccion(datatransf.getTipo_cta_origen()));
//                        tramaCosto.append(",");
//                        tramaCosto.append(validarTipoCuenta(datatransf.getTipo_cta_origen()));
//                        tramaCosto.append(",");
//                        tramaCosto.append(datatransf.getCta_origen());
//                        tramaCosto.append(",");
//                        tramaCosto.append("");//codigo fondo 
//                        tramaCosto.append(",");
//                        tramaCosto.append(cliente.getContraseña());
//                        tramaCosto.append(",~");
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo = " + "##" + obtenerHoraActual());
                            //Respuesta = "930,000,000,12,1750,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_COSTO, AtlasConstantes.PUERTO_COM_CONSULTA_COSTO, tramaCosto.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  Consulta Costo = " + Respuesta + "##" + obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal Consulta Costo = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a Consulta Costo ctg = " + "##" + obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_COSTO_CTG, AtlasConstantes.PUERTO_COM_CONSULTA_COSTO, tramaCosto.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  Consulta costo ctg = " + Respuesta + "##" + obtenerHoraActual());
                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctg Consulta Costo = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        icono.setVisible(false);
                                        consultar_costo.setVisible(false);
                                        continuar.setLayoutX(100);
                                        mensaje_mov.setText(" ");
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                        cancel();
                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[1])) {

                            final String costo = Respuesta.split(",")[4];
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    icono.setVisible(false);
                                    consultar_costo.setVisible(false);
                                    continuar.setLayoutX(100);
                                    mensaje_mov.setText("EL COSTO PARA ESTA TRANSACCION ES DE $" + costo + "\nMAS EL 19% DE IVA VALIDAR TABLA DE TASAS Y TARIFAS");
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                }
                            });

                        } else if ("001".equals(Respuesta.split(",")[1])) {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3];

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    icono.setVisible(false);
                                    consultar_costo.setVisible(false);
                                    continuar.setLayoutX(100);
                                    mensaje_mov.setText("");
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                    cancel();

                                }
                            });


                        } else if ("998".equals(Respuesta.split(",")[1])) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes("Error Consultando el Costo", "Error WS", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    icono.setVisible(false);
                                    consultar_costo.setVisible(false);
                                    continuar.setLayoutX(100);
                                    mensaje_mov.setText("");
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                    cancel();

                                }
                            });


                        } else if ("997".equals(Respuesta.split(",")[1])) {

                            final String respuesta = Respuesta.split(",")[2];
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(respuesta, "Time out", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    icono.setVisible(false);
                                    consultar_costo.setVisible(false);
                                    continuar.setLayoutX(100);
                                    mensaje_mov.setText("");
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
                                    cancel();
                                }
                            });


                            // return;
                        }

                        return null;
                    }
                };


            }
        };

        return serviceCcosto;
    }

    ;
    
      public Service<Void> continuar_Op() {
        serviceConsMov = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String RespuestaConsMov = new String();
                        final StringBuilder consultaMovAtlas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        consultaMovAtlas.append("850,030,");
                        consultaMovAtlas.append(cliente.getRefid());
                        consultaMovAtlas.append(",");
                        consultaMovAtlas.append(validarCodigoCuentaMQ(OpcionConsTipoCtaMov));
                        consultaMovAtlas.append(",");
                        consultaMovAtlas.append(cliente.getId_cliente());
                        consultaMovAtlas.append(",");
                        consultaMovAtlas.append(validarTipoCuenta(OpcionConsTipoCtaMov));
                        consultaMovAtlas.append(",");
                        consultaMovAtlas.append(cuenta);
                        consultaMovAtlas.append(",");
                        consultaMovAtlas.append(cliente.getContraseña());
                        consultaMovAtlas.append(",");
                        consultaMovAtlas.append(cliente.getTokenOauth());
                        consultaMovAtlas.append(",~");


                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ ConsultaMovimientos = " + "##" + obtenerHoraActual());
                            RespuestaConsMov = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, consultaMovAtlas.toString());
                            //  RespuestaConsMov="850,030,000,000 :TRANSACCION EXITOSA,C%2016/07/29%0000%000000000%1000000000.00%COBRO SEGURO AUDIOPRESTAMO1234%0253     ;D%2016/07/29%0   %000000000%-49600.00%TRASL CTAS BANCOL PAC E       %          ;C%2016/07/28%0000%000000000%49100.00%PAGO RENDIMIENTOS             %          ;D%2016/07/28%0   %000000000%-49100.00%COBRO SEGURO AUDIOPRESTAMO    %          ;C%2016/07/27%0000%000000000%48600.00%CANCEL ENCARGO FIDUCIARIO     %          ;C%2016/07/27%0   %000000000%48600.00%PAGO RENDIMIENTOS             %          ;C%2016/07/26%0000%000000000%48100.00%RETIRO ENCARGO FIDUCIARIO     %          ;C%2016/07/26%0   %000000000%48100.00%CANCEL ENCARGO FIDUCIARIO     %          ;C%2016/07/25%0000%000000000%47600.00%PAGO RENDIMIENTOS             %          ;C%2016/07/25%0   %000000000%47600.00%RETIRO ENCARGO FIDUCIARIO     %          ;C%2016/07/24%0000%000000000%47100.00%CANCEL ENCARGO FIDUCIARIO     %          ;D%2016/07/24%0   %000000000%-47100.00%PAGO RENDIMIENTOS             %          ,~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ ConsultaMovimientos = " + RespuestaConsMov + "##" + obtenerHoraActual());
                            // Thread.sleep(2000);
                        } catch (Exception ex) {

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ consultaMovimientos = " + "##" + obtenerHoraActual());
                                RespuestaConsMov = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, consultaMovAtlas.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + RespuestaConsMov + "##" + obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        cancel();
                                        // continuar_Op.cancel();
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }
                        }

                        if ("000".equals(RespuestaConsMov.split(",")[2])) {
                            //tipoTran%fecha%grupo%cheque%valor%descripcion%oficina
                            String data = RespuestaConsMov.split(",")[4];
                            final List<infoTablaConsMov> lista = new ArrayList<infoTablaConsMov>();
                            String[] movimientos = data.split(";");
                            for (int i = 0; i < movimientos.length; i++) {
                                final String[] datamov = movimientos[i].split("%");
                                String valor = datamov[4].trim();
                                if (".".equals(valor.substring(0, 1))) {
                                    valor = "0" + valor;
                                }

                                try {
                                    valor = "$ " + formatonum.format(Double.parseDouble(valor.substring(0, valor.length() - 3))).replace(".", ",") + "." + valor.substring(valor.length() - 2, valor.length());

                                } catch (Exception e) {
                                    valor = "$ " + datamov[4];
                                }

                                final infoTablaConsMov ObjMov = new infoTablaConsMov(
                                        datamov[2].trim(),
                                        datamov[5].toLowerCase().trim(),
                                        datamov[1].trim(),
                                        datamov[0].trim(),
                                        valor,
                                        datamov[6].toLowerCase().trim(),
                                        datamov[3].trim());

                                lista.add(ObjMov);
                            }



                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    ConsultaMovimientosFinController controller = new ConsultaMovimientosFinController();
                                    controller.mostrarConsultaMovimientosfin(lista, OpcionConsTipoCtaMov, cuenta);


                                }
                            });


                        } else {
                            final String coderror = RespuestaConsMov.split(",")[2];
                            final String mensaje = RespuestaConsMov.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    cancel();
                                    //continuar_Op.cancel();
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

        return serviceConsMov;
    }

    private String validarTipoCuenta(final String Cuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(Cuenta)) {
            retorno.append("7");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(Cuenta)) {
            retorno.append("1");
        }
        //para mas validaciones de tipo cuenta

        return retorno.toString();
    }

    private String validarTipoTransaccion(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("XMOVAHOGDE");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("XMOVCTEGDE");
        }
//        //para mas validaciones de tipo cuenta
//        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
//            retorno.append("0538");
//
//        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
//            retorno.append("0438");
//        }
        return retorno.toString();
    }

    private String validarCodigoCuentaMQ(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0564");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0464");
        }
        //para mas validaciones
        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
