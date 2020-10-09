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
import com.co.allus.modelo.pagosaterceros.infoArchivoDetalle;
import com.co.allus.modelo.pagosaterceros.infoArchivosRecibidos;
import com.co.allus.modelo.pagosaterceros.infoTablaDescInfo;
import com.co.allus.modelo.pagosterceros.DataArchivoRecibidosDet;
import com.co.allus.tareas.BusquedaDocpagArhRec;
import com.co.allus.tareas.BusquedaIdRegArchRec;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaArhRecibDetController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button detalle;
    @FXML
    private TableColumn<infoTablaDescInfo, String> descripcion;
    @FXML
    private TableColumn<infoTablaDescInfo, String> informacion;
    @FXML
    private TableColumn<infoArchivoDetalle, String> docpagador;
    @FXML
    private TableColumn<infoArchivoDetalle, String> idregistro;
    @FXML
    private TableColumn<infoArchivoDetalle, String> ref1;
    @FXML
    private TableColumn<infoArchivoDetalle, String> ref2;
    @FXML
    private TableView<infoArchivoDetalle> tablaDatos;
    @FXML
    private TableView<infoTablaDescInfo> tabla_datosDesc;
    @FXML
    private Button volverMenuRec;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private Button indMasReg;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TextField docpagadorb;
    @FXML
    private TextField idregistrob;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static ObservableList<infoArchivoDetalle> registros = FXCollections.observableArrayList();
    private static infoArchivosRecibidos infoRegArchivos;
    private Service<ObservableList<infoArchivoDetalle>> task = new ConsultaArhRecibDetController.GetRegistrosTask();
    private transient final SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static AtomicInteger numpagina = new AtomicInteger(40);
    public static AtomicInteger totalREg = new AtomicInteger(0);
    public static AtomicInteger acumREg = new AtomicInteger(0);
    public static String indicadorRegistros = "N";
    public static String fechainiAux = "";
    public static String fechafinAux = "";
    public static String codigoConv = "";
    public static String tracePaginacion = "";
    public static String estadoArchivo = "";
    public static String valorTotalArchi = "";
    public static AtomicBoolean clearBusqueda = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert detalle != null : "fx:id=\"detalle\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert docpagador != null : "fx:id=\"docpagador\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert docpagadorb != null : "fx:id=\"docpagadorb\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert idregistro != null : "fx:id=\"idregistro\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert idregistrob != null : "fx:id=\"idregistrob\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert ref1 != null : "fx:id=\"ref1\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert ref2 != null : "fx:id=\"ref2\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert tabla_datosDesc != null : "fx:id=\"tabla_datosDesc\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";
        assert volverMenuRec != null : "fx:id=\"volverMenuRec\" was not injected: check your FXML file 'ConsultaArhRecibDet.fxml'.";



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

        indMasReg.setDisable(true);

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_datosDesc.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaDescInfo, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<infoTablaDescInfo, String>("informacion"));


        idregistro.setCellValueFactory(new PropertyValueFactory<infoArchivoDetalle, String>("idregistro"));
        docpagador.setCellValueFactory(new PropertyValueFactory<infoArchivoDetalle, String>("nitentidadpag"));
        ref1.setCellValueFactory(new PropertyValueFactory<infoArchivoDetalle, String>("ref1"));
        ref2.setCellValueFactory(new PropertyValueFactory<infoArchivoDetalle, String>("ref2"));



    }

    public static String getValorTotalArchi() {
        return valorTotalArchi;
    }

    public static void setValorTotalArchi(String valorTotalArchi) {
        ConsultaArhRecibDetController.valorTotalArchi = valorTotalArchi;
    }

    public static String getEstadoArchivo() {
        return estadoArchivo;
    }

    public static void setEstadoArchivo(String estadoArchivo) {
        ConsultaArhRecibDetController.estadoArchivo = estadoArchivo;
    }

    public static infoArchivosRecibidos getInfoRegArchivos() {
        return infoRegArchivos;
    }

    public static void setInfoRegArchivos(infoArchivosRecibidos infoRegArchivos) {
        ConsultaArhRecibDetController.infoRegArchivos = infoRegArchivos;
    }

    public static String getCodigoConv() {
        return codigoConv;
    }

    public static void setCodigoConv(String codigoConv) {
        ConsultaArhRecibDetController.codigoConv = codigoConv;
    }

    public static String getTracePaginacion() {
        return tracePaginacion;
    }

    public static void setTracePaginacion(String tracePaginacion) {
        ConsultaArhRecibDetController.tracePaginacion = tracePaginacion;
    }

    public String getIndicadorRegistros() {
        return ConsultaArhRecibDetController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        ConsultaArhRecibDetController.indicadorRegistros = indicadorRegistros;
    }

    public void mostrarArchivosDetalle(final ObservableList<infoArchivoDetalle> registross,
            infoArchivosRecibidos selectedItem,
            DataArchivoRecibidosDet data,
            int origen) {

        if (origen == 1) {

            setCodigoConv(data.getCodigoconv());
            setEstadoArchivo(data.getEstadoarchivo());
            setInfoRegArchivos(selectedItem);
            setIndicadorRegistros(data.getIndMasReg());
            setValorTotalArchi(data.getValorTotalArchivo());
            totalREg.set(Integer.parseInt(data.getRegistrosfinalcle()));
            numpagina.set(40);
            acumREg.set(0);
            if ("S".equalsIgnoreCase(data.getIndMasReg())) {
                setTracePaginacion(data.getTracePaginacion());
            } else {
                setTracePaginacion("");
            }


            ConsultaArhRecibDetController.registros.clear();
            ConsultaArhRecibDetController.registros.addAll(registross);
        }


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaArhRecibDet.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button botoncontinuarOp = (Button) root.lookup("#detalle");
                    final Button indMasRegistros = (Button) root.lookup("#indMasReg");
                    final Button volverIni = (Button) root.lookup("#volverMenuRec");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<infoArchivoDetalle> tablaData = (TableView<infoArchivoDetalle>) root.lookup("#tablaDatos");
                    final TableView<infoTablaDescInfo> tablaDatainfoDesc = (TableView<infoTablaDescInfo>) root.lookup("#tabla_datosDesc");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");



                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    // cliente.setTextAlignment(TextAlignment.JUSTIFY);

                    if ("S".equals(getIndicadorRegistros())) {
                        indMasRegistros.setDisable(false);
                    } else {
                        indMasRegistros.setDisable(true);
                    }


                    final ObservableList<infoTablaDescInfo> dataTabla1 = FXCollections.observableArrayList();
                    //                                              + "Trace de paginación,"
                    //                                    + "codigoConvenio,"
                    //                                    + "tipoDeArchivo,"
                    //                                    + "formatoDelArchivo,"
                    //                                    + "nombreDelArchivo,"
                    //                                    + "estadoDelArchivo,"
                    //                                    + "numeroCuentaAbono,"
                    //                                    + "nitEntidadRecaudadora,"
                    //                                    + "nombreEntidadRecaudadora,"
                    //                                    + "tipoRecaudador,"
                    //                                    + "valorTotalTransaccion,"
                    //                                    + "totalRegistrosArchivo,"
                    //                                    + "fechaDeAplicacion,"
                    //                                    + "fechaDeGeneracion,"
                    //                                    + "secuencia,"
                    //                                    + "todosLosRegistros,"
                    //                                    + "listaDetalla,"
                    // se agregan los datos a la tabla
                    DataArchivoRecibidosDet dataarch = DataArchivoRecibidosDet.getDataarch();

                    dataTabla1.add(new infoTablaDescInfo("Codigo Convenio", dataarch.getCodigoconv().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Tipo de Archivo", dataarch.getTipoArch().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Formato de Archivo", dataarch.getFormatoArch().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Nombre del Archivo", dataarch.getNombreArch().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Estado del Archivo", dataarch.getEstadoArch().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Número de Cuenta Abono", dataarch.getNumeroctaabono().trim()));
                    dataTabla1.add(new infoTablaDescInfo("NIT Entidad Recaudadora", dataarch.getNitEntRecaudo().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Nombre Entidad Recaudadora", dataarch.getNombEntRecaudo().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Tipo Recaudador", dataarch.getTipoRecaudador().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Valor Total Tansaccion", dataarch.getValorTotaltrx().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Total Registros Archivo", dataarch.getTotalRegArch().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Fecha Aplicación", dataarch.getFechaAplicacion().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Fecha Generación", dataarch.getFechaGeneracion().trim()));
                    dataTabla1.add(new infoTablaDescInfo("Secuencia Archivo", dataarch.getSecuencia().trim()));




                    tablaDatainfoDesc.setItems(dataTabla1);

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


                    indMasRegistros.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    indMasRegistros.setCursor(Cursor.HAND);
                                    indMasRegistros.setEffect(shadow);
                                }
                            });

                    indMasRegistros.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    indMasRegistros.setCursor(Cursor.DEFAULT);
                                    indMasRegistros.setEffect(null);

                                }
                            });

                    volverIni.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volverIni.setCursor(Cursor.HAND);
                                    volverIni.setEffect(shadow);
                                }
                            });

                    volverIni.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volverIni.setCursor(Cursor.DEFAULT);
                                    volverIni.setEffect(null);

                                }
                            });




                    botoncontinuarOp.setDisable(true);
                    label_menu.setVisible(false);



                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_RIGHT);
                    pane.add(root, 1, 0);


                    /**
                     * configuracion de la paginacion
                     */
                    final int numpag = registros.size() % rowsPerPage() == 0 ? registros.size() / rowsPerPage() : registros.size() / rowsPerPage() + 1;

                    pagination = new Pagination(numpag, 0);
                    pagination.setPageFactory(new Callback<Integer, Node>() {
                        @Override
                        public Node call(final Integer pageIndex) {

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
                                        final List<infoArchivoDetalle> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoArchivoDetalle> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutX(0);
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(241);
                    pagination.setVisible(true);
                    Atlas.vista.show();



                } catch (Exception e) {
                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });





    }

    public class GetRegistrosTask extends Service<ObservableList<infoArchivoDetalle>> {

        @Override
        protected Task<ObservableList<infoArchivoDetalle>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    String Respuesta = new String();


                    final StringBuilder tramadetalleAch = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Cliente cliente = Cliente.getCliente();


                    final AtlasAcceso Atlasacceso = new AtlasAcceso();
                    String rnv = "";
                    if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                        rnv = "E";
                    } else {

                        rnv = "P";
                    }
                    final infoArchivosRecibidos selectedItem = getInfoRegArchivos();



                    /**
                     * consulta detallearchivos
                     */
                    //850,072,connid,codigoTransaccion,tracepaginacion,empresa/persona (E ó P),identificacion,totalreg,numregini,codigoconv,numrastreo,refFija,valorTotalArhicvo,tipoDocPagador,numDocPagador,estadoArchivo,claveHardware,~
                    tramadetalleAch.append("850,072,");
                    tramadetalleAch.append(cliente.getRefid());
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(AtlasConstantes.COD_RECAUDOS_DETALLE_ARCH);
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(""); //tracePagincacion
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(rnv); //regla negocio
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(cliente.getId_cliente());
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(numpagina); // registros cannal
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(acumREg.addAndGet(numpagina.get())); //registrosPaginacion
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(getCodigoConv());
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(selectedItem.getNum_rastreo());
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(" "); //referencia fija?
                    tramadetalleAch.append(",");
                    tramadetalleAch.append("0"); // valor total
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(" "); //doc pagador ?
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(" "); //tipoDocPagador ?                     
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(" "); //registros ini                      
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(cliente.getContraseña());
                    tramadetalleAch.append(",");
                    tramadetalleAch.append(cliente.getTokenOauth());
                    tramadetalleAch.append(",~");


//                    System.out.println("TRAMA detalle archivos :" + tramadetalleAch);



                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  archivosEstado = " + "##" + docs.obtenerHoraActual());


                        // Respuesta = "850,071,000,TRANSACCION EXITOSA,Exitoso,15000000.00,30082018,~";

//                        Respuesta = "850,072,000,"
//                                + "TRANSACCION EXITOSA,"
//                                + "S,"
//                                + "150,"
//                                + "Trace de paginación,"
//                                + "codigoConvenio,"
//                                + "tipoDeArchivo,"
//                                + "formatoDelArchivo,"
//                                + "nombreDelArchivo,"
//                                + "estadoDelArchivo,"
//                                + "numeroCuentaAbono,"
//                                + "nitEntidadRecaudadora,"
//                                + "nombreEntidadRecaudadora,"
//                                + "tipoRecaudador,"
//                                + "valorTotalTransaccion,"
//                                + "totalRegistrosArchivo,"
//                                + "fechaDeAplicacion,"
//                                + "fechaDeGeneracion,"
//                                + "secuencia,"
//                                + "todosLosRegistros,"
//                                + "listaDetalla,"
//                                + "idRegistro%codigoDeRespuesta%descripcion%nitEntidadPagadora%nombreEntidadPagadora%referencia1%referencia2%numeroCuentaPagador%descripcionCuentaPagador%valorDeTransaccion%nombreDelBanco%fechaDeProcesamiento%valorDebitado,~";

                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramadetalleAch.toString());

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES= " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramadetalleAch.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        final String indMasReg = Respuesta.split(",")[4];
                        setIndicadorRegistros(indMasReg);

                        final String tracePaginacion = Respuesta.split(",")[6];
                        setTracePaginacion(tracePaginacion);


                        final String registrosfinalcle = Respuesta.split(",")[5];
                        final String codigoconv = Respuesta.split(",")[11];
                        final String tipoArch = Respuesta.split(",")[12];
                        final String formatoArch = Respuesta.split(",")[13];
                        final String nombreArch = Respuesta.split(",")[14];
                        final String estadoArch = Respuesta.split(",")[15];
                        final String numeroctaabono = Respuesta.split(",")[16];
                        final String nitEntRecaudo = Respuesta.split(",")[17];
                        final String nombEntRecaudo = Respuesta.split(",")[18];
                        final String tipoRecaudador = Respuesta.split(",")[19];
                        final String valorTotaltrx = Respuesta.split(",")[20];
                        final String totalRegArch = Respuesta.split(",")[21];
                        final String fechaAplicacion = Respuesta.split(",")[22];
                        final String fechaGeneracion = Respuesta.split(",")[23];
                        final String secuencia = Respuesta.split(",")[24];
                        final String todoslosReg = Respuesta.split(",")[25];
                        final String listaDetallada = Respuesta.split(",")[26];

                        String[] LMov = Respuesta.split(",")[27].split(";");

                        for (int i = 0; i < LMov.length; i++) {


                            String[] datos = LMov[i].split("%");


//                                private SimpleStringProperty idregistro;
//    private SimpleStringProperty codigorespuesta;
//    private SimpleStringProperty descripcion;
//    private SimpleStringProperty nitentidadpag;
//    private SimpleStringProperty nomentidadpag;
//    private SimpleStringProperty ref1;
//    private SimpleStringProperty ref2;
//    private SimpleStringProperty numctapagador;
//    private SimpleStringProperty descripcionctaPagador;
//    private SimpleStringProperty valordetransaccion;
//    private SimpleStringProperty nombanco;
//    private SimpleStringProperty fechaprocesacion;
//    private SimpleStringProperty valordebitado;




                            String valordetransaccion = "";
                            String valordebitado = "";

                            try {
                                valordetransaccion = "$ " + formatonum.format(Double.parseDouble(datos[9].trim().substring(0, datos[9].length() - 2))).replace(".", ",") + "." + datos[9].trim().substring(datos[9].length() - 2, datos[9].length());
                            } catch (Exception e) {
                                valordetransaccion = "$ " + datos[9];
                            }

                            try {
                                valordebitado = "$ " + formatonum.format(Double.parseDouble(datos[12].trim().substring(0, datos[12].length() - 2))).replace(".", ",") + "." + datos[12].trim().substring(datos[12].length() - 2, datos[12].length());
                            } catch (Exception e) {
                                valordebitado = "$ " + datos[12];
                            }


                            infoArchivoDetalle mov = new infoArchivoDetalle(
                                    datos[0].trim(),
                                    datos[1].trim(),
                                    datos[2].trim(),
                                    datos[3].trim(),
                                    datos[4].trim(),
                                    datos[5].trim(),
                                    datos[6].trim(),
                                    datos[7].trim(),
                                    datos[8].trim(),
                                    valordetransaccion,
                                    datos[10].trim(),
                                    datos[11].trim(),
                                    valordebitado);




                            registros.add(mov);

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

                    }

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
                            final ObservableList<infoArchivoDetalle> TablaID = task.getValue();



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
                                                    final List<infoArchivoDetalle> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoArchivoDetalle> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(241);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println(t.getSource().getException().getMessage());
                            acumREg.addAndGet(-numpagina.get());
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
                            acumREg.addAndGet(-numpagina.get());
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {

                    Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    @FXML
    void detalleOP(ActionEvent event) {
        ConsultaArchRecibDet2Controller controller = new ConsultaArchRecibDet2Controller();
        controller.mostrarArchRecibDetalle2(tablaDatos.getSelectionModel().getSelectedItem());
    }

    @FXML
    void volverMenuRecOP(ActionEvent event) {
        ConsultaArhRecibController controller = new ConsultaArhRecibController();
        controller.mostrarArchRecib(null, null, null, 40, null, null, null, null, 0);

    }

    /**
     * BUSCAR POR doc pagador
     */
    private void BusquedadocPagador(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoArchivoDetalle>> TaskTablaId = new BusquedaDocpagArhRec(id);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(TaskTablaId.progressProperty());
                    veil.visibleProperty().bind(TaskTablaId.runningProperty());
                    p.visibleProperty().bind(TaskTablaId.runningProperty());
                    tablaDatos.itemsProperty().bind(TaskTablaId.valueProperty());
                    TaskTablaId.reset();
                    TaskTablaId.start();



                    TaskTablaId.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<infoArchivoDetalle> TablaID = TaskTablaId.getValue();

//                            System.out.println("TERMINO TAREA");

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
                                                    final List<infoArchivoDetalle> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoArchivoDetalle> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(241);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println(t.getSource().getException().getMessage());
                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    /**
     * BUSCAR POR ID
     */
    private void BusquedaIdReg(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoArchivoDetalle>> TaskTablaId = new BusquedaIdRegArchRec(id);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(TaskTablaId.progressProperty());
                    veil.visibleProperty().bind(TaskTablaId.runningProperty());
                    p.visibleProperty().bind(TaskTablaId.runningProperty());
                    tablaDatos.itemsProperty().bind(TaskTablaId.valueProperty());
                    TaskTablaId.reset();
                    TaskTablaId.start();



                    TaskTablaId.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tablaDatos.itemsProperty().unbind();
                            final ObservableList<infoArchivoDetalle> TablaID = TaskTablaId.getValue();

//                            System.out.println("TERMINO TAREA");

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
                                                    final List<infoArchivoDetalle> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoArchivoDetalle> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(241);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println(t.getSource().getException().getMessage());
                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    @FXML
    void docpagadorkeypress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(docpagadorb, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            docpagadorb.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void docpagadorkeytyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    BusquedadocPagador(docpagadorb.getText() + event.getCharacter());

                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (docpagadorb.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {

                            BusquedadocPagador("");

                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {
                            BusquedadocPagador(docpagadorb.getText());

                        }
                    }


                }

            }

        }
    }

    @FXML
    void idregistrokeypress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(idregistrob, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            idregistrob.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }

    }

    @FXML
    void idregistrokeytyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    BusquedaIdReg(idregistrob.getText() + event.getCharacter());


                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (idregistrob.getText().isEmpty()) {                        
                        synchronized (this) {

                            BusquedaIdReg("");


                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {

                            BusquedaIdReg(idregistrob.getText());


                        }
                    }


                }

            }

        }
    }

    private boolean isnumber(String val) {
        boolean retorno = false;

        try {
            Integer.parseInt(val);
            retorno = true;
        } catch (Exception e) {
            retorno = false;
        }
        return retorno;
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 4;
    }
}
