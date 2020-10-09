/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import java.net.URL;
import java.util.ResourceBundle;
import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.bolsillos.datosCuentasBolsillos;
import com.co.allus.modelo.bolsillos.datosListarBolsillos;
import com.co.allus.modelo.bolsillos.datosDetalleBolsillos;
import com.co.allus.modelo.bolsillos.infoTablaDetalleBolsillos;
import com.co.allus.modelo.bolsillos.mapeoDetalleBolsillos;
import com.co.allus.modelo.bolsillos.mapeoDetalleBolsillosTrans;
import com.co.allus.utils.AtlasConstantes;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author roberto.ceballos
 */
public class BolsillosDetalleController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelar_trx;

    @FXML
    private TableColumn<infoTablaDetalleBolsillos, String> descripcion;

    @FXML
    private TableColumn<infoTablaDetalleBolsillos, String> informacion;

    @FXML
    private Label mensaje_detallebolsillo;

    @FXML
    private Button movimientos_trx;

    @FXML
    private StackPane panel_tabla;

    @FXML
    private Button regresar_trx;

    @FXML
    private TableView<infoTablaDetalleBolsillos> tablaDatos;

    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<infoTablaDetalleBolsillos>> task = new BolsillosDetalleController.GetinfoDetalleBolsillo();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancelar_trx != null : "fx:id=\"cancelar_trx\" was not injected: check your FXML file 'BolsillosDetalle.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'BolsillosDetalle.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'BolsillosDetalle.fxml'.";
        assert mensaje_detallebolsillo != null : "fx:id=\"mensaje_detallebolsillo\" was not injected: check your FXML file 'BolsillosDetalle.fxml'.";
        assert movimientos_trx != null : "fx:id=\"movimientos_trx\" was not injected: check your FXML file 'BolsillosDetalle.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'BolsillosDetalle.fxml'.";
        assert regresar_trx != null : "fx:id=\"regresar_trx\" was not injected: check your FXML file 'BolsillosDetalle.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'BolsillosDetalle.fxml'.";

        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaDetalleBolsillos, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<infoTablaDetalleBolsillos, String>("informacion"));
        cancelartarea.set(false);
    }    

    public void mostrarDetalleBolsillo(final datosListarBolsillos data) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/BolsillosDetalle.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button movimientos_op = (Button) root.lookup("#movimientos_trx");
                    final Button regresar_op = (Button) root.lookup("#regresar_trx");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_trx");

                    final TableView<infoTablaDetalleBolsillos> tablaDatosDesbloqueoAlm = (TableView<infoTablaDetalleBolsillos>) root.lookup("#tablaDatosDetalleBolsillo");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Label mensaje = (Label) root.lookup("#mensaje_detallebolsillo");
                    mensaje.setText(data.getNombreBolsillo());
                    /**
                     * barra de progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    
                    /**
                     * TAREAS
                     */
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaDatosDesbloqueoAlm.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            ObservableList<infoTablaDetalleBolsillos> value = task.getValue();
                            tablaDatosDesbloqueoAlm.itemsProperty().unbind();
                           
                            /**
                             * configuracion de la paginacion
                             */
                            tablaDatosDesbloqueoAlm.setItems(value);
                            Atlas.vista.show();
                        }
                    });
                    
                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                    final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                                    pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));

                                    final TreeView<String> arbolBolsillos = (TreeView<String>) pane.lookup("#arbolBolsillos");
                                    if (arbolBolsillos != null) {
                                        arbolBolsillos.setDisable(false);
                                    }
                                    arbolBolsillos.getSelectionModel().clearSelection();

                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception e) {
                                        gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                    }
                                    Atlas.vista.show();
                                }
                            });
                        }
                    });
                    
                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                        }
                    });
                    
                    final DropShadow shadow = new DropShadow();

                    movimientos_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    movimientos_op.setCursor(Cursor.HAND);
                                    movimientos_op.setEffect(shadow);
                                }
                            });

                    movimientos_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    movimientos_op.setCursor(Cursor.DEFAULT);
                                    movimientos_op.setEffect(null);
                                }
                            });


                    regresar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    regresar_op.setCursor(Cursor.HAND);
                                    regresar_op.setEffect(shadow);
                                }
                            });

                    regresar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    regresar_op.setCursor(Cursor.DEFAULT);
                                    regresar_op.setEffect(null);
                                }
                            });

                    cancelar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_op.setCursor(Cursor.HAND);
                                    cancelar_op.setEffect(shadow);
                                }
                            });

                    cancelar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar_op.setCursor(Cursor.DEFAULT);
                                    cancelar_op.setEffect(null);
                                }
                            });

                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    Atlas.vista.show();
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado correctamente ,"
                            + " por favor no volver a intertalo e informar al area tecnica","Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                }
            }
        });         
    }
        
    public class GetinfoDetalleBolsillo extends Service<ObservableList<infoTablaDetalleBolsillos>> {
        @Override
        protected Task<ObservableList<infoTablaDetalleBolsillos>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
//                    /*CODIFICACION AUTOMATICA*/
                    String Respuesta = new String();
                    final StringBuilder tramaDetalleBolsillo = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final ObservableList<infoTablaDetalleBolsillos> infobolsillos = FXCollections.observableArrayList();
                    final datosListarBolsillos seleccion = datosListarBolsillos.getDataListarBolsillos();

                    tramaDetalleBolsillo.append("850,080,");
                    tramaDetalleBolsillo.append(datosCliente.getRefid());
                    tramaDetalleBolsillo.append(",");
                    tramaDetalleBolsillo.append(AtlasConstantes.COD_BOLSILLOS_DETALLE);
                    tramaDetalleBolsillo.append(",");
                    tramaDetalleBolsillo.append(datosCliente.getId_cliente());
                    tramaDetalleBolsillo.append(",");
                    tramaDetalleBolsillo.append(seleccion.getTipoCuenta());
                    tramaDetalleBolsillo.append(",");
                    tramaDetalleBolsillo.append(seleccion.getNumeroCuenta());
                    tramaDetalleBolsillo.append(",");
                    tramaDetalleBolsillo.append(seleccion.getNumeroBolsillo());
                    tramaDetalleBolsillo.append(",");
                    tramaDetalleBolsillo.append(datosCliente.getContraseña());
                    tramaDetalleBolsillo.append(",");
                    tramaDetalleBolsillo.append(datosCliente.getTokenOauth());
                    tramaDetalleBolsillo.append(",~");

//                    System.out.println("trama detalle bolsillo " + tramaDetalleBolsillo.toString());

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Detalle Bolsillo = " + "##" + gestorDoc.obtenerHoraActual());
                        //850,080,000,0902 - TRANSACCION EXITOSA,00000000003975849700,605517933061,20200422,
                        //00000000,00000000,00000000020000000000,00000000,M,S,31,20200430,20210131,00000000002000000000,~     
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaDetalleBolsillo.toString());


                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Detalle Bolsillo = " + "##" + gestorDoc.obtenerHoraActual());
                             //850,080,000,0902 - TRANSACCION EXITOSA,00000000003975849700,605517933061,20200422,
                        //00000000,00000000,00000000020000000000,00000000,M,S,31,20200430,20210131,00000000002000000000,~ 
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaDetalleBolsillo.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (BolsillosDetalleController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        failed();
                                    }
                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {
                        if (BolsillosDetalleController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final datosDetalleBolsillos dataDetalleBolsillos = datosDetalleBolsillos.getDataDetalleBolsillos();
                            dataDetalleBolsillos.setSaldoBolsillo(Respuesta.split(",")[4].trim());
                            dataDetalleBolsillos.setNumeroBolsillo(Respuesta.split(",")[5].trim());
                            dataDetalleBolsillos.setFechaAperturaBolsillo(Respuesta.split(",")[6].trim());
                            dataDetalleBolsillos.setFechaInicioMeta(Respuesta.split(",")[7].trim());
                            dataDetalleBolsillos.setFechaFinMeta(Respuesta.split(",")[8].trim());
                            dataDetalleBolsillos.setValorMeta(Respuesta.split(",")[9].trim());
                            dataDetalleBolsillos.setFechaCancelacion(Respuesta.split(",")[10].trim());
                            dataDetalleBolsillos.setTransferenciaProgramada(Respuesta.split(",")[11].trim());
                            dataDetalleBolsillos.setPeriodicidadTransferencia(Respuesta.split(",")[12].trim());
                            dataDetalleBolsillos.setDiaTransProgramada(Respuesta.split(",")[13].trim());
                            dataDetalleBolsillos.setFecIniTransProgramada(Respuesta.split(",")[14].trim());
                            dataDetalleBolsillos.setFecFinTransProgramada(Respuesta.split(",")[15].trim());
                            dataDetalleBolsillos.setValorTransferencia(Respuesta.split(",")[16].trim());
                            dataDetalleBolsillos.setDataDetalleBolsillos(dataDetalleBolsillos);
                            
                            //String Saldo_Bolsillo = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[4].substring(0, Respuesta.split(",")[4].length() - 4))).replace(".", ",") + "." + Respuesta.split(",")[4].substring(Respuesta.split(",")[4].length() - 4, Respuesta.split(",")[4].length());
                            String Saldo_Bolsillo = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[4].substring(0, Respuesta.split(",")[4].length() - 4))).replace(".", ",") + "." + Respuesta.split(",")[4].substring(Respuesta.split(",")[4].length() - 4, Respuesta.split(",")[4].length()).substring(0,2);
                            //String Valor_Meta = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[9].substring(0, Respuesta.split(",")[9].length() - 4))).replace(".", ",") + "." + Respuesta.split(",")[9].substring(Respuesta.split(",")[9].length() - 4, Respuesta.split(",")[9].length());
                            String Valor_Meta = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[9].substring(0, Respuesta.split(",")[9].length() - 4))).replace(".", ",") + "." + Respuesta.split(",")[9].substring(Respuesta.split(",")[9].length() - 4, Respuesta.split(",")[9].length()).substring(0,2);
																																			
                            infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.SALDOBOLSILLO, Saldo_Bolsillo));
                            infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.NUMEROBOLSILLO, Respuesta.split(",")[5]));
																																					
                            infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECHAAPERTURABOLSILLO, formatoFechaSalida.format(formatoFecha.parse(Respuesta.split(",")[6].trim()))));
                            if (("00000000".equals(Respuesta.split(",")[7])) || ("".equals(Respuesta.split(",")[7]))) {
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECHAINICIOMETA, "No diligenciado"));
                            } else {    
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECHAINICIOMETA, formatoFechaSalida.format(formatoFecha.parse(Respuesta.split(",")[7].trim()))));
                            }
                            if (("00000000".equals(Respuesta.split(",")[8])) || ("".equals(Respuesta.split(",")[8]))) {
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECHAFINMETA, "No diligenciado"));
                            } else {    
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECHAFINMETA, formatoFechaSalida.format(formatoFecha.parse(Respuesta.split(",")[8].trim()))));
                            }
                            infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.VALORMETA, Valor_Meta));
                            if (("00000000".equals(Respuesta.split(",")[10])) || ("".equals(Respuesta.split(",")[10]))) {
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECHACANCELACION, "No aplica"));
                            } else {    
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECHACANCELACION, formatoFechaSalida.format(formatoFecha.parse(Respuesta.split(",")[10].trim()))));
                            }
                            infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.TRANSFERENCIAPROGRAMADA, mapeoDetalleBolsillosTrans.mapeoDetalleBolsillosTrans.get(Respuesta.split(",")[12].trim())));
                            if (("00000000".equals(Respuesta.split(",")[11])) || ("".equals(Respuesta.split(",")[11]))) {
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.PERIODICIDADTRANSFERENCIA, "No diligenciado"));
                            } else {    
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.PERIODICIDADTRANSFERENCIA, mapeoDetalleBolsillos.mapeoDetalleBolsillos.get(Respuesta.split(",")[11].trim())));
                            }
                            if (("00".equals(Respuesta.split(",")[13])) || ("".equals(Respuesta.split(",")[13]))) {
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.DIATRANSPROGRAMADA, "No diligenciado"));
                            } else {    
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.DIATRANSPROGRAMADA, Respuesta.split(",")[13]));
                            }
                            if (("00000000".equals(Respuesta.split(",")[14])) || ("".equals(Respuesta.split(",")[14]))) {
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECINITRANSPROGRAMADA, "No diligenciado"));
                            } else {    
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECINITRANSPROGRAMADA, formatoFechaSalida.format(formatoFecha.parse(Respuesta.split(",")[14].trim()))));
                            }
                            if (("00000000".equals(Respuesta.split(",")[15])) || ("".equals(Respuesta.split(",")[15]))) {
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECFINTRANSPROGRAMADA, "No diligenciado"));
                            } else {    
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.FECFINTRANSPROGRAMADA, formatoFechaSalida.format(formatoFecha.parse(Respuesta.split(",")[15].trim()))));
                            }
                            if (("00000000000000000000".equals(Respuesta.split(",")[16])) || ("".equals(Respuesta.split(",")[16]))) {
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.VALORTRANSFERENCIA, "No diligenciado"));
                            } else {    
                                //String Valor_Trans = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[16].substring(0, Respuesta.split(",")[16].length() - 4))).replace(".", ",") + "." + Respuesta.split(",")[16].substring(Respuesta.split(",")[16].length() - 4, Respuesta.split(",")[16].length());
                                  String Valor_Trans = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[16].substring(0, Respuesta.split(",")[16].length() - 4))).replace(".", ",") + "." + Respuesta.split(",")[16].substring(Respuesta.split(",")[16].length() - 4, Respuesta.split(",")[16].length()).substring(0,2);
                                infobolsillos.add(new infoTablaDetalleBolsillos(dataDetalleBolsillos.VALORTRANSFERENCIA, Valor_Trans));
                            }
                        }
                    } else {
                        if (BolsillosDetalleController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                }
                            });
                            throw new Exception("ERROR");
                        }
                    }
                    return infobolsillos;
                }
            };
        }
    }
    
    @FXML
    void movimientos_op(ActionEvent event) {
            final datosListarBolsillos dataListarBolsillos = datosListarBolsillos.getDataListarBolsillos();
            datosListarBolsillos.setDataListarBolsillos(dataListarBolsillos);
            final BolsillosMovimientosController controller = new BolsillosMovimientosController();
            controller.mostrarMovimientosBolsillo(datosListarBolsillos.getDataListarBolsillos(), 0);
    }

    @FXML
    void regresar_op(ActionEvent event) {
        try {
            final BolsillosListarController controller = new BolsillosListarController();
            controller.mostrarListarBolsillo(datosCuentasBolsillos.getDataCuentasBolsillos());
        } catch (Exception e) {
           gestorDoc.imprimirExcepcion(e);
        }
    }

    @FXML
    void cancelar_op(ActionEvent event) {
        try {
            BolsillosDetalleController.cancelartarea.set(true);
        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
        } finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Atlas.getVista().gotoPrincipal();
                    } catch (IOException ex) {
                        gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
                    }
                }
            });
        }
    }
}
