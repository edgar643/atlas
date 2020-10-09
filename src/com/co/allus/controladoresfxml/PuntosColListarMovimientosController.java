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
import com.co.allus.modelo.puntoscol.MapeoRespPCO;
import com.co.allus.modelo.puntoscol.infoTablaListarMovimientos;
import com.co.allus.modelo.puntoscol.modeloListarTarjeta;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
 * @author roberto.ceballos
 */
public class PuntosColListarMovimientosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn<infoTablaListarMovimientos, String> codigo_trx;
    @FXML
    private TableColumn<infoTablaListarMovimientos, String> descripcion_trx;
    @FXML
    private Button detalle;
    @FXML
    private TableColumn<infoTablaListarMovimientos, String> extra_puntos;
    @FXML
    private TableColumn<infoTablaListarMovimientos, String> fecha_trx;
    @FXML
    private Button indMasReg;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableColumn<infoTablaListarMovimientos, String> puntos;
    @FXML
    private Button regresarOP;
    @FXML
    private TableColumn<infoTablaListarMovimientos, String> respuesta;
    @FXML
    private TableView<infoTablaListarMovimientos> tablaDatos;
    @FXML
    private TableColumn<infoTablaListarMovimientos, String> valor_trx_pesos;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static AtomicInteger numpagina = new AtomicInteger(0);
    public static ObservableList<infoTablaListarMovimientos> registros = FXCollections.observableArrayList();
    public static String indicadorRegistros = "N";
    public static modeloListarTarjeta tarjetaSeleccionada;
    public static String fechaini = "";
    public static String fechafin = "";
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFechamov2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechamov = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private Service<ObservableList<infoTablaListarMovimientos>> task = new PuntosColListarMovimientosController.GetTarjetasTask();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert codigo_trx != null : "fx:id=\"codigo_trx\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert descripcion_trx != null : "fx:id=\"descripcion_trx\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert detalle != null : "fx:id=\"detalle\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert extra_puntos != null : "fx:id=\"extra_puntos\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert fecha_trx != null : "fx:id=\"fecha_trx\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert puntos != null : "fx:id=\"puntos\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert regresarOP != null : "fx:id=\"regresarOP\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert respuesta != null : "fx:id=\"respuesta\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";
        assert valor_trx_pesos != null : "fx:id=\"valor_trx_pesos\" was not injected: check your FXML file 'PuntosColListarMovimientos.fxml'.";



        this.resources = rb;
        this.location = url;

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {

                    detalle.setDisable(false);
                } else {
                    detalle.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });
        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        fecha_trx.setCellValueFactory(new PropertyValueFactory<infoTablaListarMovimientos, String>("fecha_trx"));
        codigo_trx.setCellValueFactory(new PropertyValueFactory<infoTablaListarMovimientos, String>("cod_trx"));
        descripcion_trx.setCellValueFactory(new PropertyValueFactory<infoTablaListarMovimientos, String>("descripcion_trx"));
        valor_trx_pesos.setCellValueFactory(new PropertyValueFactory<infoTablaListarMovimientos, String>("valor_trx_pesos"));
        puntos.setCellValueFactory(new PropertyValueFactory<infoTablaListarMovimientos, String>("puntos"));
        extra_puntos.setCellValueFactory(new PropertyValueFactory<infoTablaListarMovimientos, String>("extra_puntos"));
        respuesta.setCellValueFactory(new PropertyValueFactory<infoTablaListarMovimientos, String>("respuesta"));


    }

    public static String getFechaini() {
        return fechaini;
    }

    public static void setFechaini(String fechaini) {
        PuntosColListarMovimientosController.fechaini = fechaini;
    }

    public static String getFechafin() {
        return fechafin;
    }

    public static void setFechafin(String fechafin) {
        PuntosColListarMovimientosController.fechafin = fechafin;
    }

    public modeloListarTarjeta getTarjetaSeleccionada() {
        return tarjetaSeleccionada;
    }

    public void setTarjetaSeleccionada(modeloListarTarjeta tarjetaSeleccionada) {
        this.tarjetaSeleccionada = tarjetaSeleccionada;
    }

    public String getIndicadorRegistros() {
        return PuntosColListarTarjetasController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        PuntosColListarTarjetasController.indicadorRegistros = indicadorRegistros;
    }

    public void mostrasMovmientosTDCpco(List<infoTablaListarMovimientos> registross, final modeloListarTarjeta selecteditem, String fechaini, String fechafin, int registrosPag, String indMasReg, int origen) {

        if (origen == 1) {
            setIndicadorRegistros(indMasReg);
            setTarjetaSeleccionada(selecteditem);
            numpagina.set(registrosPag);
            setFechaini(fechaini);
            setFechafin(fechafin);
            PuntosColListarMovimientosController.registros.clear();
            PuntosColListarMovimientosController.registros.addAll(registross);
        }

        if (origen == 2) {
            setTarjetaSeleccionada(selecteditem);

        }


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PuntosColListarMovimientos.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelar = (Button) root.lookup("#regresarOP");
                    final Button detalle = (Button) root.lookup("#detalle");
                    final Button botonMasReg = (Button) root.lookup("#indMasReg");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<infoTablaListarMovimientos> tablaData = (TableView<infoTablaListarMovimientos>) root.lookup("#tablaDatos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");

                    /// codigo para inyectar los datos  
                    String tdc = selecteditem.getNumerotarjeta() == null ? getTarjetaSeleccionada().getNumerotarjeta() : selecteditem.getNumerotarjeta();
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                    String tarjeta = tdc.substring(0, 6) + StringUtilities.rellenarDato(" ", tdc.length() - 10, "*") + tdc.substring(tdc.length() - 4, tdc.length());
                    final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente() + "\nTDC: " + tarjeta;
                    cliente.setText("");
                    cliente.setText(info);




                    final DropShadow shadow = new DropShadow();
                    detalle.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    detalle.setCursor(Cursor.HAND);
                                    detalle.setEffect(shadow);
                                }
                            });

                    detalle.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    detalle.setCursor(Cursor.DEFAULT);
                                    detalle.setEffect(null);

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

                    botonMasReg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botonMasReg.setCursor(Cursor.HAND);
                                    botonMasReg.setEffect(shadow);
                                }
                            });

                    botonMasReg.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botonMasReg.setCursor(Cursor.DEFAULT);
                                    botonMasReg.setEffect(null);

                                }
                            });

                    if (getIndicadorRegistros().equals("S")) {
                        botonMasReg.setDisable(false);
                    } else {
                        botonMasReg.setDisable(true);
                    }

                    detalle.setDisable(true);
                    label_menu.setVisible(false);

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }

                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);


//                    System.out.println("TERMINO TAREA");
                    /**
                     * configuracion de la paginacion
                     */
                    // final ObservableList<modeloListarTarjeta> registros = task.getValue();
                    final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;

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
                                int displace = registros.size() % rowsPerPage();
                                if (displace >= 0) {
                                    lastIndex = registros.size() / rowsPerPage();
                                } else {
                                    lastIndex = registros.size() / rowsPerPage() - 1;
                                }
                                int page = pageIndex * itemsPerPage();

                                for (int i = page; i < page + itemsPerPage(); i++) {

                                    if (lastIndex == pageIndex) {
                                        final List<infoTablaListarMovimientos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoTablaListarMovimientos> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
//

                    pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                            currentpageindex = newValue.intValue();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    tablaData.getItems().setAll(registros.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= registros.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : registros.size())));
                                }
                            });

                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    root.getChildren().add(pagination);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutX(3);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(50);
                    pagination.setVisible(true);
                    Atlas.vista.show();



                } catch (Exception e) {
//                    e.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }


            }
        });

    }

    public class GetTarjetasTask extends Service<ObservableList<infoTablaListarMovimientos>> {

        @Override
        protected Task<ObservableList<infoTablaListarMovimientos>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA LISTAR TARJETAS             
                    final Cliente cliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramamovtcpco = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramamovtcpco.append("850,073,");
                    tramamovtcpco.append(cliente.getRefid());
                    tramamovtcpco.append(",");
                    tramamovtcpco.append(AtlasConstantes.COD_LISTAR_MOV_TDC_PCO);
                    tramamovtcpco.append(",");
                    tramamovtcpco.append(cliente.getId_cliente());
                    tramamovtcpco.append(",");
                    tramamovtcpco.append(getTarjetaSeleccionada().getNumtdcori());
                    tramamovtcpco.append(",");
                    tramamovtcpco.append(getFechaini());
                    tramamovtcpco.append(",");
                    tramamovtcpco.append(getFechafin());
                    tramamovtcpco.append(",");
                    tramamovtcpco.append(numpagina.addAndGet(1)); // cantReg
                    tramamovtcpco.append(",");
                    tramamovtcpco.append(cliente.getContraseña());
                    tramamovtcpco.append(",");
                    tramamovtcpco.append(cliente.getTokenOauth());
                    tramamovtcpco.append(",~");

//                    System.out.println("TRAMA listar mov  :" + tramamovtcpco);

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ MOVIMIENTOS tc pco = " + "##" + gestorDoc.obtenerHoraActual());

                        //850,072,000,TRANSACCION EXITOSA,indMasReg,%FECHA%HORA%ID TRX*%TIPO DOCUMENTO%DOCUMENTO%VALOR TRX PESOS%VALOR TRX DÓLARES%COD TRX%DESCRIP TRX%COD RESPUESTA %PUNTOS%EXTRA PUNTOS%TIPO DOC BENEFICI%DOC BENEFICIARIO%COD UNICO%DESCRIPC COD UNICO%MCC%DESCRIPCIÓN MCC%LOGO;.....,~
                        // Thread.sleep(2000);
//                        Respuesta = "850,"
//                                + "072,"
//                                + "000,"
//                                + "TRANSACCION EXITOSA,"
//                                + "S,"
//                                + "31052018%120000%COMPRA*%1%1020416841%5000%0%588%COMPRA ARTURO CALLE%056%699%123%1%10665889654%563%COMPRA UNICA%2442%ARTURO CALLE%673,~";

                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramamovtcpco.toString());
//                        System.out.println(" RESPUESTA mov pco:" + Respuesta);
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS DEB= " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramamovtcpco.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        try {


                            final String indMasREg = Respuesta.split(",")[4];

                            setIndicadorRegistros(indMasREg);
                            // final List<infoTablaListarMovimientos> movtdcpco = new ArrayList<infoTablaListarMovimientos>();

                            String[] LMov = Respuesta.split(",")[5].split(";");



                            for (int i = 0; i < LMov.length; i++) {


                                String[] datos = LMov[i].split("%");
                                String fecha = "";
                                String valordolares = "";
                                String valorpesos = "";


                                try {
                                    fecha = formatoFechamov2.format(formatoFechamov.parse(datos[0].trim()));

                                } catch (Exception e) {
                                    fecha = datos[0].trim();
                                }
//                                
                                try {
                                    valorpesos = "$ " + formatonum.format(Double.parseDouble(datos[5].trim().split("\\.")[0])).replace(".", ",") + "." + datos[5].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    valorpesos = "$ " + datos[5];
                                }
//                                
                                try {
                                    valordolares = "$ " + formatonum.format(Double.parseDouble(datos[6].trim().split("\\.")[0])).replace(".", ",") + "." + datos[6].trim().split("\\.")[1];
                                } catch (Exception e) {
                                    valordolares = "$ " + datos[6];
                                }



                                infoTablaListarMovimientos mov = new infoTablaListarMovimientos(
                                        fecha,
                                        datos[7].trim(),
                                        datos[8].trim(),
                                        valorpesos,
                                        datos[10].trim(),
                                        datos[11].trim(),
                                        MapeoRespPCO.mapeoRespuestas.get(datos[9].trim().toUpperCase()),
                                        datos[1].trim(),
                                        datos[3].trim(),
                                        datos[4].trim(),
                                        valordolares,
                                        datos[12].trim(),
                                        datos[13].trim(),
                                        datos[14].trim(),
                                        datos[15].trim(),
                                        datos[16].trim(),
                                        datos[17].trim(),
                                        datos[18].trim(),
                                        datos[2].trim());


                                registros.add(mov);


                            }




                        } catch (Exception e) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                        }

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

                    // updateProgress(500, 500);

                    return registros;

                }
            };
        }
    }

    @FXML
    void MasRegAction(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaDatos.itemsProperty().bind(task.valueProperty());
                    task.reset();
                    indMasReg.setDisable(true);
                    task.start();



                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<infoTablaListarMovimientos> TablaID = task.getValue();



                            if ("S".equals(getIndicadorRegistros())) {
                                indMasReg.setDisable(false);
                            } else {
                                indMasReg.setDisable(true);
                            }



                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaID.size() % rowsPerPage() == 0 ? TablaID.size() / rowsPerPage() : TablaID.size() / rowsPerPage() + 1;

                                pagination = new Pagination(numpag, 0);
                                //paginationid.setId("paginationid");
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaID.size() % rowsPerPage();
                                            if (displace >= 0) {
                                                lastIndex = TablaID.size() / rowsPerPage();
                                            } else {
                                                lastIndex = TablaID.size() / rowsPerPage() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infoTablaListarMovimientos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoTablaListarMovimientos> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                }

                                                panel_tabla.getChildren().clear();
                                                tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                                panel_tabla.getChildren().add(tablaDatos);
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
                                                tablaDatos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaID.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaID.size())));
                                            }
                                        });

                                    }
                                });

                                /**
                                 * fin configuracion de la paginacion
                                 */
                                try {
                                    AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);

                                } catch (Exception e) {
                                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(3);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(50);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            }

                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println(t.getSource().getException().getMessage());
                            numpagina.addAndGet(-1);
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
                            numpagina.addAndGet(-1);
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {
                    Logger.getLogger(PuntosColListarTarjetasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    @FXML
    void detalleOP(ActionEvent event) {
        infoTablaListarMovimientos selectedItem = tablaDatos.getSelectionModel().getSelectedItem();
        PuntosColDetalleMovimientoController controller = new PuntosColDetalleMovimientoController();
        controller.mostarDetalleMovTcPCO(selectedItem, getTarjetaSeleccionada());
    }

    @FXML
    void regresarOP(ActionEvent event) {
        PuntosColListarTarjetasController controller = new PuntosColListarTarjetasController();
        controller.mostrarListarTarjetaPCO(null, null, 0, 0);

    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
