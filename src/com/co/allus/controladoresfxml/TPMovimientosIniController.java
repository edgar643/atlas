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
import com.co.allus.modelo.tpmovimientos.DatosMovimientosFin;
import com.co.allus.modelo.tpmovimientos.InfoTablaMovFin;
import com.co.allus.modelo.tpmovimientos.InfoTablaMovIni;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
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
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class TPMovimientosIniController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button cancelar_op;
    @FXML
    private TableColumn colEstadoTarjeta;
    @FXML
    private TableColumn colNombreEmpresa;
    @FXML
    private TableColumn colNumtarjeta;
    @FXML
    private TableColumn colTipoTarjeta;
    @FXML
    private Button continuar_op;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<InfoTablaMovIni> tabla_datos;
    @FXML
    private ProgressBar progreso;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static ObservableList<InfoTablaMovIni> movini = FXCollections.observableArrayList();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private static GestorDocumental docs = new GestorDocumental();
    private Service<ObservableList<InfoTablaMovIni>> task = new TPMovimientosIniController.GetTarjetasTask();
    private transient Service<Void> ServiceMovimientosFin;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        assert cancelar_op != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        assert colEstadoTarjeta != null : "fx:id=\"colEstadoTarjeta\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        assert colNombreEmpresa != null : "fx:id=\"colNombreEmpresa\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        assert colNumtarjeta != null : "fx:id=\"colNumtarjeta\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        assert colTipoTarjeta != null : "fx:id=\"colTipoTarjeta\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TPMovimientosIni.fxml'.";
        this.location = url;
        this.resources = rb;
        continuar_op.setDisable(true);
        progreso.setVisible(false);
        colNumtarjeta.setCellValueFactory(new PropertyValueFactory<InfoTablaMovIni, String>("col_Numero"));
        colTipoTarjeta.setCellValueFactory(new PropertyValueFactory<InfoTablaMovIni, String>("col_TipoTarj"));
        colEstadoTarjeta.setCellValueFactory(new PropertyValueFactory<InfoTablaMovIni, String>("col_Bloqueo"));
        colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<InfoTablaMovIni, String>("col_Empresa"));

        tabla_datos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                if (tabla_datos.getSelectionModel().getSelectedItem() != null) {
                    continuar_op.setDisable(false);
                } else {
                    continuar_op.setDisable(true);
                    tabla_datos.getSelectionModel().clearSelection();
                }
            }
        });


    }

    @FXML
    void cancelar_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                final Label label_menu = (Label) pane.lookup("#label_saldos");

                final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                if (arboltdcPrepago != null) {
                    arboltdcPrepago.setDisable(false);
                }

                arboltdcPrepago.getSelectionModel().clearSelection();
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }



            }
        });

    }

    @FXML
    void continuar_op(final ActionEvent event) {
        final InfoTablaMovIni selectedItem = tabla_datos.getSelectionModel().getSelectedItem();

        DatosMovimientosFin datosMF = DatosMovimientosFin.getDataMF();
        datosMF.setNumTarj(selectedItem.valorsinforProperty().getValue());
        datosMF.setNumTarjcf(selectedItem.col_NumeroProperty().getValue());

        DatosMovimientosFin.setDataMF(datosMF);


        continuar_op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle codeudor" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });


        continuar_op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle codeudor" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_op().isRunning()) {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_op().progressProperty());
            continuar_op().reset();
            continuar_op().start();

        } else {
            continuar_op.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_op().progressProperty());
            continuar_op().start();
        }
    }

    public Service<Void> continuar_op() {
        ServiceMovimientosFin = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();
                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramamovimientosfin = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        //850,069,connid,7006,45129087,N,4594270098711362,CH,~
//                        System.out.println("ENTRO SERVICIO MOVIMIRNO");


                        tramamovimientosfin.append("850,069,");
                        tramamovimientosfin.append(datosCliente.getRefid());
                        tramamovimientosfin.append(",");
                        tramamovimientosfin.append(AtlasConstantes.COD_MOVIMIENTO_PREP);
                        tramamovimientosfin.append(",");
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramamovimientosfin.append(cliente.getId_cliente());
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramamovimientosfin.append(cliente.getNitEmpresa());
                        }
                        tramamovimientosfin.append(",");
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramamovimientosfin.append("1"); // tipoID
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            tramamovimientosfin.append("3"); //tipoID
                        }
                        tramamovimientosfin.append(",");
                        tramamovimientosfin.append(Cliente.getCliente().getTipoClientePrepago());
                        tramamovimientosfin.append(",");
                        tramamovimientosfin.append(DatosMovimientosFin.getDataMF().getNumTarj());
                        tramamovimientosfin.append(",");
                        tramamovimientosfin.append(datosCliente.getContraseña());
                        tramamovimientosfin.append(",~");
//                        System.out.println("TRAMA MOVMIENTOS:" + tramamovimientosfin);



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ consulta movmientos = " + "##" + docs.obtenerHoraActual());

                            //Respuesta = "850,069,000,,N,0004745540216776226,039,20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170427##CARGO POR USO CAJERO NO PROPIO##CARGO POR USO CAJERO N        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170427##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170411##CARGA TARJETA PREPAGO AUTOMATI##CARGA TARJETA PREPAGO         ##+000000000500000.00;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170409##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170407##CARGA TARJETA PREPAGO AUTOMATI##CARGA TARJETA PREPAGO         ##+000000000500000.00;20170404##CARGA TARJETA PREPAGO AUTOMATI##CARGA TARJETA PREPAGO         ##+000000000500000.00;20170330##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170330##CARGO CONSULTA SALDO CAJERO NO##CARGO CONSULTA SALDO C        ##-000000000004087.00;20170322##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000016.35;20170322##CARGO POR USO CAJERO NO PROPIO##CARGO POR USO CAJERO N        ##-000000000004087.00;20170322##CARGA TARJETA PREPAGO AUTOMATI##CARGA TARJETA PREPAGO         ##+000000000400000.00;20170312##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000032.46;20170312##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000109.79;20170312##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000040.96;20170312##4X1000 COBRO DE GMF           ##COBRO GMF (4*1000)            ##-000000000000128.27;20170312##COMISION AVANCE INTERNACIONAL ##COMISION AVANCE INTERN        ##-000000000004087.00;20170306##RETIRO CAJERO INTERNACIONAL   ##OF. C. NACIONA/OF. C.         ##-000000000027979.00;20170303##COMPRA INTERNACIONAL          ##MARSHALLS 90372               ##-000000000008114.00;20170303##COMPRA INTERNACIONAL          ##OPTUS PRE PAID                ##-000000000027448.00;20170303##COMPRA INTERNACIONAL          ##Uber BV                       ##-000000000010240.00,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramamovimientosfin.toString());
//                            System.out.println(" RESPUESTA TRAMA CONSULTA MOVIMIENTOS :" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ consulta movmiemtos  = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramamovimientosfin.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }
                        try {
                            if ("000".equals(Respuesta.split(",")[2])) {


                                String[] datosmovfin = Respuesta.split(",")[7].split(";");

                                final List<InfoTablaMovFin> datosMovFin = new ArrayList<InfoTablaMovFin>();
                                datosMovFin.clear();


                                for (int i = 0; i < datosmovfin.length; i++) {
                                    String[] datos = datosmovfin[i].split("##");
                                    final String ValorOr = datos[3].replace(".", "");
                                    //final String ValorOr= Valorsm.replace("-", ""); 

                                    final String Valor = ("$ " + formatonum.format(Double.parseDouble(ValorOr.substring(0, ValorOr.length() - 2))).replace(".", ",") + "." + ValorOr.substring(ValorOr.length() - 2));

                                    String Fecha = (datos[0].substring(0, datos[0].length() - 4)) + "/" + (datos[0].substring(4, datos[0].length() - 2)) + "/" + (datos[0].substring(datos[0].length() - 2));
                                    final InfoTablaMovFin data = new InfoTablaMovFin(
                                            Fecha,
                                            datos[1].trim().toLowerCase(),
                                            datos[2].trim().toLowerCase(),
                                            Valor);

                                    datosMovFin.add(data);

                                }




                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final TPMovimientosFinController controller = new TPMovimientosFinController();
                                        controller.mostrarMovimientoPrepagoFin(datosMovFin, DatosMovimientosFin.getDataMF());
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
                        } catch (Exception ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                        }

                        return null;
                    }
                };
            }
        };

        return ServiceMovimientosFin;

    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    public void mostrarMovimientoPrepago() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TPMovimientosIni.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_op");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<InfoTablaMovIni> tabla_datos = (TableView<InfoTablaMovIni>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    // codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    label_menu.setVisible(false);
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);




                    final DropShadow shadow = new DropShadow();
                    continuar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.HAND);
                                    continuar_op.setEffect(shadow);
                                }
                            });

                    continuar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuar_op.setCursor(Cursor.DEFAULT);
                                    continuar_op.setEffect(null);

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

                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servadd != null) {
                        arbol_servadd.setDisable(true);
                    }

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tabla_datos.itemsProperty().bind(task.valueProperty());
                    task.reset();
                    task.start();

                    /**
                     * barra progreso
                     */
                    // Use binding to be notified whenever the data source chagnes
                    // task = new GetTarjetasTask();
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();

                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = movini.size() % rowsPerPage() == 0 ? movini.size() / rowsPerPage() : movini.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {
                                    //ObservableList<modelSaldoTarjeta> tarjetastemp = FXCollections.observableArrayList();
                                    //tarjetastemp.addAll(tarjetas);
                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = movini.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = movini.size() / rowsPerPage();
                                        } else {
                                            lastIndex = movini.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<InfoTablaMovIni> subList = movini.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<InfoTablaMovIni> subList = movini.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
                                                tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                            }

                                            panel_tabla.getChildren().clear();
                                            tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                            panel_tabla.getChildren().add(tabla_datos);
                                            panel_tabla.setVisible(true);
                                        }
                                        return panel_tabla;
                                    }
                                }
                            });
//

                            pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                @Override
                                public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                    currentpageindex = newValue.intValue();
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            tabla_datos.getItems().setAll(movini.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= movini.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : movini.size())));
                                        }
                                    });
                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(3);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(71);

                            pagination.setVisible(true);
                            Atlas.vista.show();
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("ENTRO FAILED");
//                            System.out.println("ERROR EN LA CONSULTA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                    final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                                    pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
                                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                                    if (arbol_pagos != null) {
                                        arbol_pagos.setDisable(false);
                                    }

                                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                                    if (arbol_saldos != null) {
                                        arbol_saldos.setDisable(false);
                                    }

                                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                                    if (arbol_transf != null) {
                                        arbol_transf.setDisable(false);
                                    }

                                    final TreeView<String> arbol_bloqueos = (TreeView<String>) pane.lookup("#arbolbloqueos");
                                    if (arbol_bloqueos != null) {
                                        arbol_bloqueos.setDisable(false);
                                    }

                                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                                    if (arbol_servadd != null) {
                                        arbol_servadd.setDisable(false);
                                    }

                                    final TreeView<String> arboltdcPrepago = (TreeView<String>) pane.lookup("#arboltdcPrepago");
                                    if (arboltdcPrepago != null) {
                                        arboltdcPrepago.setDisable(false);
                                    }


                                    arboltdcPrepago.getSelectionModel().clearSelection();
                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception e) {
                                        docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                                    }
                                    Atlas.vista.show();
                                }
                            });


                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            docs.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });



                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });
    }

    public class GetTarjetasTask extends Service<ObservableList<InfoTablaMovIni>> {

        @Override
        protected Task<ObservableList<InfoTablaMovIni>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaListarTDC = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramaListarTDC.append("850,065,");
                    tramaListarTDC.append(datosCliente.getRefid());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(AtlasConstantes.COD_COD_CONS_TARJ_PREPAGO);
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getId_cliente());
                    tramaListarTDC.append(",");
                    tramaListarTDC.append("1"); //tipoid
                    tramaListarTDC.append(",");
                    tramaListarTDC.append("N"); // TIPO DE TARJETA
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(AtlasConstantes.COD_MOVIMIENTO_PREP); // dinamico
                    tramaListarTDC.append(",");
                    tramaListarTDC.append(datosCliente.getContraseña());
                    tramaListarTDC.append(",~");

//                    System.out.println("trama listarTarjetas" + tramaListarTDC.toString());

                    if (MarcoPrincipalController.newConsultaBloqTDcf) {
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ listar prepago= " + "##" + docs.obtenerHoraActual());
//                            Respuesta = "850,"
//                                    + "002,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "N,"
//                                    + "354852685895589##"
//                                    + "NOMINA##"
//                                    + "A##+ "EMPRESA 1;"
//                                    + "452685785459825962##"
//                                    + "NOMINA##"
//                                    + "A##"
//                                    + "NOMBRE EMPRESA 2;"
//                                    + "5698521458525932##"
//                                    + "NOMINA##"
//                                    + "B##"
//                                    + "NOMBRE EMPRESA TRES;"
//                                    + ",~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
//                            System.out.println("Respuesta listar prepago: " + Respuesta);

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {
//                            System.out.println("contingencia");
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listar prepago = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaListarTDC.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (TPMovimientosIniController.cancelartarea.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            failed();
                                        }
                                    }
                                });
                            }

                        }

                    } else {
                        Respuesta = "850,"
                                + "002,"
                                + "000,"
                                + ",~";

                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (TPMovimientosIniController.cancelartarea.get()) {
                            cancel();
                        } else {



                            //  tablaDatos.setItems(emptyObservableList);

                            movini.clear();
                            final String[] Ltarjetas = Respuesta.split(",")[5].split(";");



                            for (int i = 0; i < Ltarjetas.length; i++) {

                                String[] datos = Ltarjetas[i].split("##");

                                String tarjetaPIN = datos[0].substring(0, 6) + StringUtilities.rellenarDato(" ", datos[0].length() - 10, "*") + datos[0].substring(datos[0].length() - 4, datos[0].length());

                                final InfoTablaMovIni tarjeta = new InfoTablaMovIni(
                                        tarjetaPIN,
                                        //datos[0].trim(),
                                        datos[1].trim().toLowerCase(),
                                        datos[2].trim(),
                                        datos[3].trim().toLowerCase(),
                                        datos[0].trim());
                                movini.add(tarjeta);
                            }

                        }

                    } else {

                        if (TPMovimientosIniController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCIÓN" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                }
                            });
                            throw new Exception("ERROR");
                        }
                    }

                    return movini;

                }
            };
        }
    }
}
