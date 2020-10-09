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
import com.co.allus.modelo.consultatrm.Datostrmhistorico;
import com.co.allus.modelo.consultatrm.Datosyearmonth;
import com.co.allus.modelo.consultatrm.infoTablaHistorico;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class ConsultaTRMfinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn colfecha;
    @FXML
    private TableColumn colvalor;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private RadioButton rbhistorico;
    @FXML
    private RadioButton rbhoy;
    @FXML
    private TableView<infoTablaHistorico> tabla_datos;
    @FXML
    private Button terminar_trx;
    @FXML
    private TextField tfmonth;
    @FXML
    private TextField tfyear;
    @FXML
    private Button btn_buscar;
    private static GestorDocumental docs = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private transient Service<Void> serviceConsultaTRM;
    private Pagination pagination = new Pagination();
    String year = "";
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    int currentpageindex = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert colfecha != null : "fx:id=\"colfecha\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert colvalor != null : "fx:id=\"colvalor\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert rbhistorico != null : "fx:id=\"rbhistorico\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert rbhoy != null : "fx:id=\"rbhoy\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert tfmonth != null : "fx:id=\"tfmonth\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert tfyear != null : "fx:id=\"tfyear\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";
        assert btn_buscar != null : "fx:id=\"btn_buscar\" was not injected: check your FXML file 'ConsultaTRMfin.fxml'.";

        this.location = url;
        this.resources = rb;

        colfecha.setCellValueFactory(new PropertyValueFactory<infoTablaHistorico, String>("colfecha"));
        colvalor.setCellValueFactory(new PropertyValueFactory<infoTablaHistorico, String>("colvalor"));
    }

    @FXML
    void regresar_op(final ActionEvent event) {
        rbhistorico.setSelected(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //dataTabla.clear();
                ConsultaTRMController controller = new ConsultaTRMController();
                controller.mostrarConsultaTRM();
            }
        });
    }

    @FXML
    void buscar(final ActionEvent event) {
        //Menu.getPanes().get(0).getContent() 
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final TextField tfyear = (TextField) AnchorPane.lookup("#tfyear");
                final TextField tfmes = (TextField) AnchorPane.lookup("#tfmonth");
//                System.out.println("año es" + tfyear.getText());
                final Datosyearmonth datosyearmonth = Datosyearmonth.getDatosyearmonth();
                datosyearmonth.setValoryear(tfyear.getText() + "/" + tfmes.getText());
                Datosyearmonth.setDatosyearmonth(datosyearmonth);
                year = datosyearmonth.getValoryear();

                continuar_Op().setOnSucceeded(new EventHandler() {
                    @Override
                    public void handle(final Event event) {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ir a Detalle prestamo" + "##" + docs.obtenerHoraActual());
                    }
                });

                continuar_Op().setOnCancelled(new EventHandler() {
                    @Override
                    public void handle(final Event event) {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario canceloir a Detalle prestamo" + "##" + docs.obtenerHoraActual());
                    }
                });

                if (continuar_Op().isRunning()) {
                    continuar_Op().reset();
                    continuar_Op().start();

                } else {
                    continuar_Op().start();
                }

            }
        });

    }

    public Service<Void> continuar_Op() {
        serviceConsultaTRM = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String Respuesta = new String();
                        final StringBuilder tramaconsultatrmdia = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        tramaconsultatrmdia.append("850,042,");
                        tramaconsultatrmdia.append(cliente.getRefid());
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append(AtlasConstantes.COD_CONSULTA_TRM);
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append(cliente.getId_cliente());
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append("C");
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append(Datosyearmonth.getDatosyearmonth().getValoryear().replace("/", "").trim());
                        tramaconsultatrmdia.append(",");
                        tramaconsultatrmdia.append(cliente.getContraseña());
                        tramaconsultatrmdia.append(",~");
//                        System.out.println("TRAMA CONSULTA TRM DIA :" + tramaconsultatrmdia);

                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CONSULTA TRM DIA = " + "##" + docs.obtenerHoraActual());
                            //RespuestaConsMov = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, consultaMovAtlas.toString());
                            //850,042,000,TRANSACCION EXITOSA,trmhoy,trmdia01,....trmdia31,~
                            //,,,,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296912000,0297619000,0296982000,0297619000,0296982000,0296982000,0297619000,0297619000,0297619000,0296982000,0297619000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000,0296982000, ,~

//                            Respuesta = "850,"
//                                    + "042,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "0295982000," //TRM HOY
//                                    + "0296982000,"
//                                    + "0296976000,"
//                                    + "0106924000,"
//                                    + "0116934000,"
//                                    + "0126944000,"
//                                    + "0136954000,"
//                                    + "0146964000,"
//                                    + "0156974000,"
//                                    + "0166984000,"
//                                    + "0176994000,"
//                                    + "0186937000,"
//                                    + "0196947000,"
//                                    + "0206968000,"
//                                    + "0216992000,"
//                                    + "0226955000,"
//                                    + "0236982000,"
//                                    + "0246982000,"
//                                    + "0256982000,"
//                                    + "0266982000,"
//                                    + "0276962000,"
//                                    + "0286982000,"
//                                    + "0123945000,"
//                                    + "0134982000,"
//                                    + "0145982000,"
//                                    + "0156982000,"
//                                    + "0167916000,"
//                                    + "0189915000,"
//                                    + "0234914000,"
//                                    + "0245913000,"
//                                    + "0256912000,"
//                                    + "0267911000,"
//                                    + "~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU7c, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU7c, tramaconsultatrmdia.toString());
//                            System.out.println(" RESPUESTA TRAMA COMEX BUSCARID:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ ConsultaMovimientos = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                            // Thread.sleep(2000);
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ CONSULTA TRM DIA = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU7c, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU7c, tramaconsultatrmdia.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        cancel();
                                        // continuar_Op.cancel();
//                                        progreso.progressProperty().unbind();
//                                        progreso.setProgress(0);
//                                        progreso.setVisible(false);

                                    }
                                });

                            }
                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            final Datostrmhistorico datosTrmHistorico = Datostrmhistorico.getDatostrmhistorico();

                            try {
                                datosTrmHistorico.setValor1(Respuesta.split(",")[5]);
                                datosTrmHistorico.setValor2(Respuesta.split(",")[6]);
                                datosTrmHistorico.setValor3(Respuesta.split(",")[7]);
                                datosTrmHistorico.setValor4(Respuesta.split(",")[8]);
                                datosTrmHistorico.setValor5(Respuesta.split(",")[9]);
                                datosTrmHistorico.setValor6(Respuesta.split(",")[10]);
                                datosTrmHistorico.setValor7(Respuesta.split(",")[11]);
                                datosTrmHistorico.setValor8(Respuesta.split(",")[12]);
                                datosTrmHistorico.setValor9(Respuesta.split(",")[13]);
                                datosTrmHistorico.setValor10(Respuesta.split(",")[14]);
                                datosTrmHistorico.setValor11(Respuesta.split(",")[15]);
                                datosTrmHistorico.setValor12(Respuesta.split(",")[16]);
                                datosTrmHistorico.setValor13(Respuesta.split(",")[17]);
                                datosTrmHistorico.setValor14(Respuesta.split(",")[18]);
                                datosTrmHistorico.setValor15(Respuesta.split(",")[19]);
                                datosTrmHistorico.setValor16(Respuesta.split(",")[20]);
                                datosTrmHistorico.setValor17(Respuesta.split(",")[21]);
                                datosTrmHistorico.setValor18(Respuesta.split(",")[22]);
                                datosTrmHistorico.setValor19(Respuesta.split(",")[23]);
                                datosTrmHistorico.setValor20(Respuesta.split(",")[24]);
                                datosTrmHistorico.setValor21(Respuesta.split(",")[25]);
                                datosTrmHistorico.setValor22(Respuesta.split(",")[26]);
                                datosTrmHistorico.setValor23(Respuesta.split(",")[27]);
                                datosTrmHistorico.setValor24(Respuesta.split(",")[28]);
                                datosTrmHistorico.setValor25(Respuesta.split(",")[29]);
                                datosTrmHistorico.setValor26(Respuesta.split(",")[30]);
                                datosTrmHistorico.setValor27(Respuesta.split(",")[31]);
                                datosTrmHistorico.setValor28(Respuesta.split(",")[32]);
                                datosTrmHistorico.setValor29(Respuesta.split(",")[33]);
                                datosTrmHistorico.setValor30(Respuesta.split(",")[34]);
                                datosTrmHistorico.setValor31(Respuesta.split(",")[35]);

                            } catch (Exception e) {
                                Logger.getLogger(ConsultaTRMfinController.class.getName()).log(Level.SEVERE, e.toString());
                            }

//                            System.out.println("El valor 1 es " + datosTrmHistorico.getValor1());
//                            System.out.println("El valor 31 es " + datosTrmHistorico.getValor31());
//                            System.out.println("El valor 5 es " + datosTrmHistorico.getValor5());
//                            System.out.println("fecha es " + docs.obtenerFechaActualSinDia());
                            Datostrmhistorico.setDatostrmhistorico(datosTrmHistorico);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final ConsultaTRMfinController controller = new ConsultaTRMfinController();
                                    controller.mostrarConsultaTRMfin(Datostrmhistorico.getDatostrmhistorico());
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
                                    //continuar_Op.cancel();
//                                    progreso.progressProperty().unbind();
//                                    progreso.setProgress(0);
//                                    progreso.setVisible(false);
                                }
                            });

                        }

                        return null;
                    }
                };

            }
        };

        return serviceConsultaTRM;
    }

    public void mostrarConsultaTRMfin(final Datostrmhistorico datosTrmHistorico) {
        //this.dataTabla = data;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaTRMfin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    //final TableView<Infodet1> tablaData1 = (TableView<Infodet1>) Menu.getPanes().get(0).getContent().lookup("#tabla_datos1");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");

                    final TextField tfyear = (TextField) root.lookup("#tfyear");
                    final TextField tfmonth = (TextField) root.lookup("#tfmonth");
                    final RadioButton rbhistorico = (RadioButton) root.lookup("#rbhistorico");
                    final RadioButton rbhoy = (RadioButton) root.lookup("#rbhoy");
                    final Calendar capturar_fecha = Calendar.getInstance();

                    final TableView<infoTablaHistorico> tablaData = (TableView<infoTablaHistorico>) root.lookup("#tabla_datos");
                    final ObservableList<infoTablaHistorico> dataTabla = FXCollections.observableArrayList();

                    Datosyearmonth datosyearmonth = Datosyearmonth.getDatosyearmonth();

                    //dataTabla.add(new infoTablaHistorico(year + "/" + tfmonth.getText() + "/1", datosTrmHistorico.getValor1()));
                    //dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/" + tfmonth.getText() + "/1", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor1().substring(0, datosTrmHistorico.getValor1().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor1().substring(5, datosTrmHistorico.getValor1().length())));
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/01", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor1().substring(0, datosTrmHistorico.getValor1().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor1().substring(5, datosTrmHistorico.getValor1().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/01", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/02", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor2().substring(0, datosTrmHistorico.getValor2().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor2().substring(5, datosTrmHistorico.getValor2().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/02", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/03", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor3().substring(0, datosTrmHistorico.getValor3().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor3().substring(5, datosTrmHistorico.getValor3().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/03", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/04", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor4().substring(0, datosTrmHistorico.getValor4().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor4().substring(5, datosTrmHistorico.getValor4().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/04", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/05", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor5().substring(0, datosTrmHistorico.getValor5().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor5().substring(5, datosTrmHistorico.getValor5().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/05", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/06", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor6().substring(0, datosTrmHistorico.getValor6().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor6().substring(5, datosTrmHistorico.getValor6().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/06", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/07", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor7().substring(0, datosTrmHistorico.getValor7().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor7().substring(5, datosTrmHistorico.getValor7().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/07", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/08", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor8().substring(0, datosTrmHistorico.getValor8().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor8().substring(5, datosTrmHistorico.getValor8().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/08", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/09", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor9().substring(0, datosTrmHistorico.getValor9().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor9().substring(5, datosTrmHistorico.getValor9().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/09", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/10", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor10().substring(0, datosTrmHistorico.getValor10().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor10().substring(5, datosTrmHistorico.getValor10().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/10", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/11", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor11().substring(0, datosTrmHistorico.getValor11().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor11().substring(5, datosTrmHistorico.getValor11().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/11", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/12", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor12().substring(0, datosTrmHistorico.getValor12().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor12().substring(5, datosTrmHistorico.getValor12().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/12", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/13", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor13().substring(0, datosTrmHistorico.getValor13().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor13().substring(5, datosTrmHistorico.getValor13().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/13", ""));
                    }

                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/14", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor14().substring(0, datosTrmHistorico.getValor14().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor14().substring(5, datosTrmHistorico.getValor14().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/14", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/15", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor15().substring(0, datosTrmHistorico.getValor15().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor15().substring(5, datosTrmHistorico.getValor15().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/15", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/16", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor16().substring(0, datosTrmHistorico.getValor16().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor16().substring(5, datosTrmHistorico.getValor16().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/16", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/17", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor17().substring(0, datosTrmHistorico.getValor17().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor17().substring(5, datosTrmHistorico.getValor17().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/17", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/18", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor18().substring(0, datosTrmHistorico.getValor18().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor18().substring(5, datosTrmHistorico.getValor18().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/18", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/19", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor19().substring(0, datosTrmHistorico.getValor19().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor19().substring(5, datosTrmHistorico.getValor19().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/19", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/20", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor20().substring(0, datosTrmHistorico.getValor20().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor20().substring(5, datosTrmHistorico.getValor20().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/20", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/21", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor21().substring(0, datosTrmHistorico.getValor21().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor21().substring(5, datosTrmHistorico.getValor21().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/21", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/22", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor22().substring(0, datosTrmHistorico.getValor22().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor22().substring(5, datosTrmHistorico.getValor22().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/22", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/23", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor23().substring(0, datosTrmHistorico.getValor23().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor23().substring(5, datosTrmHistorico.getValor23().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/23", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/24", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor24().substring(0, datosTrmHistorico.getValor24().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor24().substring(5, datosTrmHistorico.getValor24().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/24", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/25", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor25().substring(0, datosTrmHistorico.getValor25().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor25().substring(5, datosTrmHistorico.getValor25().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/25", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/26", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor26().substring(0, datosTrmHistorico.getValor26().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor26().substring(5, datosTrmHistorico.getValor26().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/26", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/27", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor27().substring(0, datosTrmHistorico.getValor27().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor27().substring(5, datosTrmHistorico.getValor27().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/27", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/28", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor28().substring(0, datosTrmHistorico.getValor28().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor28().substring(5, datosTrmHistorico.getValor28().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/28", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/29", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor29().substring(0, datosTrmHistorico.getValor29().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor29().substring(5, datosTrmHistorico.getValor29().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/29", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/30", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor30().substring(0, datosTrmHistorico.getValor30().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor30().substring(5, datosTrmHistorico.getValor30().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/30", ""));
                    }
                    try {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/31", "$" + formatonum.format(Double.parseDouble(datosTrmHistorico.getValor31().substring(0, datosTrmHistorico.getValor31().length() - 5))).replace(",", ".") + "," + datosTrmHistorico.getValor31().substring(5, datosTrmHistorico.getValor31().length())));
                    } catch (Exception e) {
                        dataTabla.add(new infoTablaHistorico(datosyearmonth.getValoryear() + "/31", ""));
                    }

                    tablaData.setItems(dataTabla);

                    final DropShadow shadow = new DropShadow();
                    terminar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            terminar_trx.setCursor(Cursor.HAND);
                            terminar_trx.setEffect(shadow);
                        }
                    });

                    terminar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            terminar_trx.setCursor(Cursor.DEFAULT);
                            terminar_trx.setEffect(null);

                        }
                    });

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    final ObservableList<infoTablaHistorico> datosTabla = FXCollections.observableArrayList();
                    datosTabla.addAll(dataTabla);

                    final int numpag = datosTabla.size() % rowsPerPage() == 0 ? datosTabla.size() / rowsPerPage() : datosTabla.size() / rowsPerPage() + 1;

                    pagination = new Pagination(numpag, 0);
                    pagination.setPageFactory(new Callback<Integer, Node>() {
                        @Override
                        public Node call(final Integer pageIndex) {
                            if (pageIndex > numpag) {
                                return null;
                            } else {
                                int lastIndex = 0;
                                int displace = datosTabla.size() % rowsPerPage();
                                if (displace >= 0) {
                                    lastIndex = datosTabla.size() / rowsPerPage();
                                } else {
                                    lastIndex = datosTabla.size() / rowsPerPage() - 1;
                                }
                                int page = pageIndex * itemsPerPage();

                                for (int i = page; i < page + itemsPerPage(); i++) {

                                    if (lastIndex == pageIndex) {
                                        final List<infoTablaHistorico> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoTablaHistorico> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    }

                                    panel_tabla.getChildren().clear();
                                    tablaData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                    panel_tabla.getChildren().add(tablaData);
                                    panel_tabla.setVisible(true);
                                }
                                return panel_tabla;
                            }
                        }
                    });

                    pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                            currentpageindex = newValue.intValue();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tablaData.getItems().setAll(datosTabla.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= datosTabla.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : datosTabla.size())));
                                }
                            });

                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    root.getChildren().add(pagination);
                    root.getChildren().get(13).setLayoutX(95);
                    root.getChildren().get(13).setLayoutY(203);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que el  pago se haya realizado correctamente , por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }
//    public class GetDatosNovedadiniTask extends Service<ObservableList<infoTablaHistorico>> {
//
//        @Override
//        protected Task<ObservableList<infoTablaHistorico>> createTask() {
//            return new Task() {
//                @Override
//                protected Object call() throws Exception {
//
//                    // CONSULTA datos de seguridad datos iniciales        
//                    ObservableList<infoTablaHistorico> novedades = FXCollections.observableArrayList();
//                    final Cliente datosCliente = Cliente.getCliente();
//                    String Respuesta = new String();
//                    final StringBuilder tramaconsultatrmhisto = new StringBuilder();
//                    final ConectSS servicioSS = new ConectSS();
//                    final Cliente cliente = Cliente.getCliente();
//
//                        tramaconsultatrmhisto.append("850,042,");
//                        tramaconsultatrmhisto.append(cliente.getRefid());
//                        tramaconsultatrmhisto.append(",");
//                        tramaconsultatrmhisto.append(AtlasConstantes.COD_CONSULTA_TRM);
//                        tramaconsultatrmhisto.append(",");
//                        tramaconsultatrmhisto.append(cliente.getId_cliente());
//                        tramaconsultatrmhisto.append(",");
//                        tramaconsultatrmhisto.append("C");
//                        tramaconsultatrmhisto.append(",");
//                        tramaconsultatrmhisto.append(tfyear.getText() + tfmonth.getText());
//                        tramaconsultatrmhisto.append(",");
//                        tramaconsultatrmhisto.append(cliente.getContraseña());
//                        tramaconsultatrmhisto.append(",~");
//                        System.out.println("TRAMA CONSULTA TRM HISTORICO :" + tramaconsultatrmhisto);
//
//
//                    try {
//                        Thread.sleep(1000);
//                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ = " + tramaconsultatrmhisto.toString() + "##" + docs.obtenerHoraActual());
////                           Respuesta = "850,012,000,Consulta de datos de seguridad exitosa,N,oe@hotmail.com,P,3002588382,~";
//
//                        //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramadaNovedadesini.toString());
//                        System.out.println("respuesta Lista : " + Respuesta);
//
//                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
//                    } catch (Exception ex) {
//                        System.out.println("contingencia");
//                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
//                        //envio a contingencia
//                        try {
//                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ = " + tramaconsultatrmhisto.toString() + "##" + docs.obtenerHoraActual());
//                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramadaNovedadesini.toString());
//                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
//
//                        } catch (Exception ex1) {
//
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (ConsultaTRMfinController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
//                                        cancel();
//                                    } else {
//                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
//                                        failed();
//
//                                    }
//                                }
//                            });
//                        }
//                    }
//
//                    if ("000".equals(Respuesta.split(",")[2])) {
//
//                        if (ConsultaTRMfinController.cancelartarea.get()) {
//                            cancel();
//                        } else {
//
//                            try {
////                                setEmailCtle(Respuesta.split(",")[6]);
////                                setCelularClte(Respuesta.split(",")[5]);
//                            } catch (Exception e) {
//                                System.out.println("datos vacios");
//                            }
//
//                            //  tablaDatos.setItems(emptyObservableList);
//                            novedades = FXCollections.observableArrayList();
//
//                            try {
//                                String[] Lnovedades = Respuesta.split(",")[4].split(";");
//
//                                for (int i = 0; i < Lnovedades.length; i++) {
//                                    String[] datos = Lnovedades[i].split("##");
////                                    final String fechaIn = datos[2].substring(0, 2)+"/"+datos[2].substring(2, 4)+"/"+datos[2].substring(4, 8);
////                                    final String HoraIn = datos[3].substring(0, 2)+":"+datos[3].substring(2, 4);
//                                    final String fechaIn = datos[2].substring(6, 8) + "/" + datos[2].substring(4, 6) + "/" + datos[2].substring(0, 4);
//                                    final String HoraIn = datos[3].substring(0, 2) + ":" + datos[3].substring(2, 4);
//
//                                    String dato = "";
//                                    if (i == 0) {
//                                        dato = "Mecanismo Actual";
//                                    }
//                                    if (i == 1) {
//                                        dato = "Mecanismo Anterior";
//                                    }
//
//                                    if (datos[0].equalsIgnoreCase("STK")) {
//                                        datos[1] = datos[0];
//                                    }
//
//
//                                    // transaccion , Descripcion, resultado, fecha, hora - Actual
//                                    final infoTablaNovedades novedad = new infoTablaNovedades(dato, datos[1], fechaIn, HoraIn, datos[4]);
//                                    // transaccion , fecha, hora, Descripcion, resultado - New 
////                                    final infoTablaNovedades novedad = new infoTablaNovedades(dato, fechaIn, HoraIn, datos[1], datos[4]);
//                                    novedades.add(novedad);
//                                }
//                            } catch (Exception ex) {
//                                System.out.println("consulta vacia - " + ex.toString());
//                            }
//                        }
//                    } else {
//
//                        if (ConsultaNovedadesOtpController.cancelartarea.get()) {
//                            cancel();
//                        } else {
//                            final String coderror = Respuesta.split(",")[2];
//                            final String mensaje = Respuesta.split(",")[3].trim();
//
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
//                                }
//                            });
//                            throw new Exception("ERROR");
//                        }
//
//                    }
//
//                    // updateProgress(500, 500);
//
//                    return novedades;
//
//                }
//            };
//        }
//    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 6;
    }
}
