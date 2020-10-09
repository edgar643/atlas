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
import com.co.allus.modelo.tokenempresas.InfoTokenToAE;
import com.co.allus.modelo.tokenempresas.InfoTokentToAEgs;
import com.co.allus.modelo.tokenempresas.infoDetalletf;
import com.co.allus.modelo.tokenempresas.infoTablaDetalle;
import com.co.allus.modelo.tokenempresas.infoTablaToken;
import com.co.allus.modelo.tokenempresas.infoTokenDisponibles;
import com.co.allus.tareas.BusqIdEmpresasTask;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
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
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
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
 * @author stephania.rojas
 */
public class TokenEmpresasController implements Initializable {

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
    private TableColumn colae;
    //@FXML
    // private Button buscar_op;
    @FXML
    private Button continuar_op;
    @FXML
    private Button detalle_op;
    @FXML
    private TableColumn colestado1;
    @FXML
    private TableColumn colestado2;
    @FXML
    private TableColumn colfecha_exp;
    @FXML
    private TableColumn colid_usuario;
    @FXML
    private Button limpiar_op;
    @FXML
    private TableColumn colnombre_usuario;
    @FXML
    private TableColumn colperfil;
    @FXML
    private TableColumn colrol;
    @FXML
    private TableColumn colserial;
    @FXML
    private TextField tfBusqueda_ID;
    @FXML
    private TextField tfEsquema_Seguridad;
    @FXML
    private TextField tfEstado_Empresa;
    @FXML
    private TextField tfNit;
    @FXML
    private TextField tfTotal_Tokens;
    @FXML
    private ProgressBar progreso;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<infoTablaToken> tabla_datos;
    @FXML
    private Button obtMasReg;
    private transient Service<Void> serviceDetalleToken;
    private transient Service<Void> serviceDispoToken;
    private Service<ObservableList<infoTablaToken>> task = new TokenEmpresasController.GetTokenTask();
    public static ObservableList<infoTablaToken> token = FXCollections.observableArrayList();
    private static GestorDocumental docs = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Pagination pagination = new Pagination();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    String esquema = "";
    String esquemadis = "";
    String estado_empresa = "";
    String total_token = "";
    int currentpageindex = 0;
    private static AtomicInteger numpagina = new AtomicInteger(0);
    private String indicadorRegistros;

    public String getIndicadorRegistros() {
        return indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        this.indicadorRegistros = indicadorRegistros;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert colae != null : "fx:id=\"colae\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        //assert buscar_op != null : "fx:id=\"buscar_op\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TokenAccesoEmergencia.fxml'.";
        assert detalle_op != null : "fx:id=\"detalle_op\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert colestado1 != null : "fx:id=\"colestado1\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert colestado2 != null : "fx:id=\"colestado2\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert colfecha_exp != null : "fx:id=\"colfecha_exp\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert colid_usuario != null : "fx:id=\"colid_usuario\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert limpiar_op != null : "fx:id=\"limpiar_op\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert colnombre_usuario != null : "fx:id=\"colnombre_usuario\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert colperfil != null : "fx:id=\"colperfil\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert colrol != null : "fx:id=\"colrol\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert colserial != null : "fx:id=\"colserial\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert tfBusqueda_ID != null : "fx:id=\"tfBusqueda_ID\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert tfEsquema_Seguridad != null : "fx:id=\"tfEsquema_Seguridad\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert tfEstado_Empresa != null : "fx:id=\"tfEstado_Empresa\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert tfNit != null : "fx:id=\"tfNit\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert tfTotal_Tokens != null : "fx:id=\"tfTotal_Tokens\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        assert obtMasReg != null : "fx:id=\"obtMasReg\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        this.location = url;
        this.resources = rb;
        tfNit.setEditable(false);
        progreso.setVisible(false);
        continuar_op.setDisable(true);

        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabla_datos.setEditable(false);
        colid_usuario.setCellValueFactory(new PropertyValueFactory("colId_usuario"));
        colnombre_usuario.setCellValueFactory(new PropertyValueFactory("colNombre_usuario"));
        colestado1.setCellValueFactory(new PropertyValueFactory("colEstado1"));
        colrol.setCellValueFactory(new PropertyValueFactory("colRol"));
        colperfil.setCellValueFactory(new PropertyValueFactory("colPerfil"));
        colestado2.setCellValueFactory(new PropertyValueFactory("colEstado2"));
        colserial.setCellValueFactory(new PropertyValueFactory("colSerial"));
        colfecha_exp.setCellValueFactory(new PropertyValueFactory("colFecha_exp"));
        colae.setCellValueFactory(new PropertyValueFactory("colAe"));

        tabla_datos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tabla_datos.getSelectionModel().getSelectedItem() != null) {
                    infoTablaToken selectedItem = tabla_datos.getSelectionModel().getSelectedItem();

                    continuar_op.setDisable(false);

                } else {
                    continuar_op.setDisable(true);
                    tabla_datos.getSelectionModel().clearSelection();
                }
            }
        });

        TokenEmpresasController.cancelartarea.set(false);
        obtMasReg.setDisable(true);
        token.clear();

        colae.setCellFactory(new Callback<TableColumn<infoTablaToken, String>, TableCell<infoTablaToken, String>>() {
            @Override
            public TableCell<infoTablaToken, String> call(TableColumn<infoTablaToken, String> p) {
                return new ButtonTableCell<infoTablaToken, String>();

            }
        });

        setIndicadorRegistros("N");
        numpagina.set(0);
        // isRegresarTkEmp.set(false);

    }

    @FXML
    void obtMasRegOp(ActionEvent event) {
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
                    tabla_datos.itemsProperty().bind(task.valueProperty());
                    task.reset();
                    obtMasReg.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<infoTablaToken> TablaID = task.getValue();

                            if ("S".equals(getIndicadorRegistros())) {
                                obtMasReg.setDisable(false);
                            } else {
                                obtMasReg.setDisable(true);
                            }

                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaID.size() % rowsPerPageId() == 0 ? TablaID.size() / rowsPerPageId() : TablaID.size() / rowsPerPageId() + 1;

                                pagination = new Pagination(numpag, 0);
                                //paginationid.setId("paginationid");
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaID.size() % rowsPerPageId();
                                            if (displace >= 0) {
                                                lastIndex = TablaID.size() / rowsPerPageId();
                                            } else {
                                                lastIndex = TablaID.size() / rowsPerPageId() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infoTablaToken> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoTablaToken> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + rowsPerPageId());
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

                                pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                    @Override
                                    public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                        currentpageindex = newValue.intValue();
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPageId(), ((newValue.intValue() * rowsPerPageId() + rowsPerPageId() <= TablaID.size()) ? newValue.intValue() * rowsPerPageId() + rowsPerPageId() : TablaID.size())));
                                            }
                                        });

                                    }
                                });

                                /**
                                 * fin configuracion de la paginacion
                                 */
                                try {
                                    AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);

                                } catch (Exception ex) {
                                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(4);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(147);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            numpagina.decrementAndGet();
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            numpagina.decrementAndGet();
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public class ButtonTableCell<S, T> extends TableCell<S, T> {

        final Button button = new Button();
        private ObservableValue<T> ov;

        public ButtonTableCell() {

            button.setPrefWidth(78);
            button.setPrefHeight(10);
            setAlignment(Pos.CENTER);
            setGraphic(button);

            final DropShadow shadow = new DropShadow();
            button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(final MouseEvent event) {
                    button.setCursor(Cursor.HAND);
                    button.setEffect(shadow);
                }
            });

            button.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(final MouseEvent event) {
                    button.setCursor(Cursor.DEFAULT);
                    button.setEffect(null);

                }
            });

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {

                    infoTablaToken get = (infoTablaToken) getTableView().getItems().get(getIndex());
                    continuarAE(event, get);
                }
            });
        }

        @Override
        public void updateItem(T t, boolean empty) {
            super.updateItem(t, empty);
            if (empty) {
                setGraphic(null);
            } else {

                ov = getTableColumn().getCellObservableValue(getIndex());
                setGraphic(button);
                if ("NO".equalsIgnoreCase(ov.getValue().toString())) {
                    setTooltip(new Tooltip("El usuario seleccionado no presenta \n ninguna novedad en el servicio token"));
                    Label lblno = new Label();
                    lblno.setText("NO");
                    setGraphic(lblno);

                }
                if (ov instanceof StringProperty) {
                    button.textProperty().bindBidirectional((StringProperty) ov);
                }
            }
            //System.out.println("Cantidad:" + token.size());
        }

        public void continuarAE(final ActionEvent event, infoTablaToken selectedItem) {

            infoDetalletf info = infoDetalletf.getInfoDetalletf();
            info.setId_usuario(selectedItem.getColId_usuario());
            info.setNombre(selectedItem.colNombre_usuarioProperty().getValue());
            infoDetalletf.setInfoDetalletf(info);

            continuarAE().setOnSucceeded(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle token" + "##" + obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            continuarAE().setOnCancelled(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle token" + "##" + obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            if (continuarAE().isRunning()) {
                // button.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(continuar_Op().progressProperty());
                continuarAE().reset();
                continuarAE().start();

            } else {
                // button.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(continuar_Op().progressProperty());
                continuarAE().start();
            }
        }

        public Service<Void> continuarAE() {
            serviceDetalleToken = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            String Respuesta = new String();

                            final Cliente datosCliente = Cliente.getCliente();
                            final StringBuilder tramaTokenAEEmpresas = new StringBuilder();
                            final ConectSS servicioSS = new ConectSS();
                            final Cliente cliente = Cliente.getCliente();

                            tramaTokenAEEmpresas.append("850,043,");
                            tramaTokenAEEmpresas.append(datosCliente.getRefid());
                            tramaTokenAEEmpresas.append(",");
                            tramaTokenAEEmpresas.append(AtlasConstantes.COD_TOKEN_AE);
                            tramaTokenAEEmpresas.append(",");
                            tramaTokenAEEmpresas.append(datosCliente.getId_cliente());
                            tramaTokenAEEmpresas.append(",");
                            final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                            tramaTokenAEEmpresas.append(contra);
                            tramaTokenAEEmpresas.append(",");
                            tramaTokenAEEmpresas.append("21"); //TIPO CONSULTA
                            tramaTokenAEEmpresas.append(",");
                            tramaTokenAEEmpresas.append(infoDetalletf.getInfoDetalletf().getId_usuario()); //ID USUARIO
                            tramaTokenAEEmpresas.append(",");
                            tramaTokenAEEmpresas.append(""); //fini
                            tramaTokenAEEmpresas.append(",");
                            tramaTokenAEEmpresas.append(""); //ffin
                            tramaTokenAEEmpresas.append(",");
                            tramaTokenAEEmpresas.append("1");
                            tramaTokenAEEmpresas.append(",");
                            tramaTokenAEEmpresas.append(datosCliente.getContraseña()); //ID USUARIO
                            tramaTokenAEEmpresas.append(",");
                            tramaTokenAEEmpresas.append(datosCliente.getTokenOauth()); //ID USUARIO
                            tramaTokenAEEmpresas.append(",~");
                            // System.out.println("TRAMA DETALLE AE :" + tramaTokenAEEmpresas);

                            //  System.out.println("TRAMA TRAMA TOKEN TO AE :" + tramaTokenAEEmpresas);
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token acceso emergencia = " + "##" + docs.obtenerHoraActual());

//                                Respuesta = "850,"
//                                        + "043,"
//                                        + "000,"
//                                        + "TRANSACCION EXITOSA,"
//                                        + "BANCOLOMBIA,"
//                                        + "7," //AE PERMITIDOS
//                                        + "4," //AE SOLICITADOS
//                                        + "3," //AE POR SOLICITAR
//                                        + "7," //PERIODO AE
//                                        + "10,"//CANTIDAD REGISTROS
//                                        + "20150721%11:13:05%15%agarzon@bancolombia.com.co%SI;"
//                                        + "20170105%11:14:05%16%agarzon@bancolombia.com.co%NO;"
//                                        + "20150723%11:15:05%17%agarzon@bancolombia.com.co%NO,"
//                                        + "~";
                                // System.out.println(" RESPUESTA TRAMA TOKEN TO AE:" + Respuesta);
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenAEEmpresas.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenAEEmpresas.toString());

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex) {

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token acceso emergencia  = " + "##" + docs.obtenerHoraActual());
                                    //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenAEEmpresas.toString());
                                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenAEEmpresas.toString());
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                                } catch (Exception ex1) {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            continuar_op.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);

                                        }
                                    });

                                }

                            }

                            if ("000".equals(Respuesta.split(",")[2])) {

//                            final infoDetalletf infodetalletf = infoDetalletf.getInfoDetalletf();
//                            infodetalletf.setEsquema_seguridad(Respuesta.split(",")[6].trim());
//                            infodetalletf.setEstado_empresa(Respuesta.split(",")[7].trim());
//                            infoDetalletf.setInfoDetalletf(infodetalletf);
                                final InfoTokentToAEgs infotokentoaegs = InfoTokentToAEgs.getInfotokentoaegs();
                                infotokentoaegs.setNombre_compania(Respuesta.split(",")[4].trim());
                                infotokentoaegs.setAe_permitidos(Respuesta.split(",")[5].trim());
                                infotokentoaegs.setAe_usados(Respuesta.split(",")[6].trim());
                                infotokentoaegs.setAe_disponibles(Respuesta.split(",")[7].trim());
                                infotokentoaegs.setPeriodo_ae(Respuesta.split(",")[8].trim());
                                infotokentoaegs.setCantidad_registros(Respuesta.split(",")[9].trim());

                                String data = Respuesta.split(",")[10];

                                final List<InfoTokenToAE> lista = new ArrayList<InfoTokenToAE>();
                                String[] detalles = data.split(";");
                                for (int i = 0; i < detalles.length; i++) {
                                    final String[] datamov = detalles[i].split("%");
                                    //idUsuario%nombreUsuario%email%telefono%accesosSolicitadosPeriodo;.......,~
                                    final String Fecha = (datamov[0].substring(0, datamov[0].length() - 4)) + "/" + (datamov[0].substring(4, datamov[0].length() - 2)) + "/" + (datamov[0].substring(datamov[0].length() - 2));

                                    final InfoTokenToAE ObjMov = new InfoTokenToAE(
                                            // datamov[0].trim(),
                                            Fecha,
                                            datamov[1].trim(),
                                            datamov[2].trim(),
                                            datamov[3].trim(),
                                            datamov[4].trim());

                                    lista.add(ObjMov);
                                }

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final TokenEmpresasAEController controller = new TokenEmpresasAEController();
                                        controller.mostrarDatosTokenEmpresasAE(lista, infoDetalletf.getInfoDetalletf());
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

                            return null;
                        }
                    };
                }
            };

            return serviceDetalleToken;

        }
    }

    @FXML
    void continuarAE2(ActionEvent event) {
        infoTablaToken selectedItem = tabla_datos.getSelectionModel().getSelectedItem();
        infoDetalletf info = infoDetalletf.getInfoDetalletf();
        info.setId_usuario(selectedItem.getColId_usuario());
        info.setNombre(selectedItem.colNombre_usuarioProperty().getValue());
        infoDetalletf.setInfoDetalletf(info);

        continuarAE2().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle token" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        continuarAE2().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle token" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuarAE2().isRunning()) {
            // button.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuarAE2().reset();
            continuarAE2().start();

        } else {
            // button.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuarAE2().start();
        }
    }

    public Service<Void> continuarAE2() {
        serviceDetalleToken = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramaTokenDetEmpresas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        tramaTokenDetEmpresas.append("850,033,");
                        tramaTokenDetEmpresas.append(datosCliente.getRefid());
                        tramaTokenDetEmpresas.append(",");
                        tramaTokenDetEmpresas.append(AtlasConstantes.COD_TOKEN_EMPRESAS);
                        tramaTokenDetEmpresas.append(",");
                        tramaTokenDetEmpresas.append("1");
                        tramaTokenDetEmpresas.append(",");
                        tramaTokenDetEmpresas.append(datosCliente.getId_cliente());
                        tramaTokenDetEmpresas.append(",");
                        final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                        tramaTokenDetEmpresas.append(contra);
                        tramaTokenDetEmpresas.append(",");
                        tramaTokenDetEmpresas.append("10");
                        tramaTokenDetEmpresas.append(",");
                        tramaTokenDetEmpresas.append(infoDetalletf.getInfoDetalletf().getId_usuario());
                        tramaTokenDetEmpresas.append(",");
                        tramaTokenDetEmpresas.append("1");
                        tramaTokenDetEmpresas.append(",");
                        tramaTokenDetEmpresas.append(datosCliente.getContraseña());
                        tramaTokenDetEmpresas.append(",");
                        tramaTokenDetEmpresas.append(datosCliente.getTokenOauth());
                        tramaTokenDetEmpresas.append(",~");

                        // System.out.println("TRAMA TOKEN EMPRESAS DETALLE :" + tramaTokenDetEmpresas);
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token acceso emergencia  = " + "##" + docs.obtenerHoraActual());
//                            Respuesta = "850,"
//                                    + "033,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "10,"
//                                    + "BANCOLOMBIA,"
//                                    + "Superusuario,"
//                                    + "Activada,"
//                                    + "3,"
//                                    + ","
//                                    + "abc1234%Stepha%Pendiente Aprobacion%Desbloquear%0000%08/09/2016%11:59%AUD,"
//                                    + "~";

                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDetEmpresas.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDetEmpresas.toString());
                            // System.out.println(" RESPUESTA TRAMA TOKEN EMPRESAS DETALLE:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token acceso emergencia  = " + "##" + docs.obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDetEmpresas.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDetEmpresas.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

                            try {
                                final infoDetalletf infodetalletf = infoDetalletf.getInfoDetalletf();
                                infodetalletf.setEsquema_seguridad(Respuesta.split(",")[6].trim());
                                infodetalletf.setEstado_empresa(Respuesta.split(",")[7].trim());
                                infoDetalletf.setInfoDetalletf(infodetalletf);

                                if ("10".equals(Respuesta.split(",")[4])) {

                                    String data = Respuesta.split(",")[12];

                                    final List<infoTablaDetalle> lista = new ArrayList<infoTablaDetalle>();
                                    String[] detalles = data.split(";");
                                    for (int i = 0; i < detalles.length; i++) {
                                        final String[] datamov = detalles[i].split("%");

//                                    String id_usuario,
//                                    String nombre_usuario,
//                                    String estado_novedad,
//                                            String novedad,
//                                            String costo_novedad,
//                                                    String fecha_novedad,
//                                                    String hora_novedad,
//                                                            String canal
                                        final infoTablaDetalle ObjMov = new infoTablaDetalle(
                                                datamov[0].trim(),
                                                datamov[1].trim(),
                                                datamov[2].trim(),
                                                datamov[3].trim(),
                                                datamov[4].trim(),
                                                datamov[5].trim(),
                                                datamov[6].trim(),
                                                datamov.length == 8 ? datamov[7].trim() : "");

                                        lista.add(ObjMov);
                                    }

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            final TokenEmpresasDetalleController controller = new TokenEmpresasDetalleController();
                                            controller.mostrarDetalleToken(lista, infoDetalletf.getInfoDetalletf());
                                        }
                                    });
                                }

                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

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

                        return null;
                    }
                };
            }
        };

        return serviceDetalleToken;

    }

    public void mostrarDatosToken() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenEmpresas.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button detalle_op = (Button) root.lookup("#detalle_op");
                    // final Button buscar_op = (Button) root.lookup("#buscar_op");

                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final TextField tfNit = (TextField) root.lookup("#tfNit");
                    final TextField tfEsquema_Seguridad = (TextField) root.lookup("#tfEsquema_Seguridad");
                    final TextField tfEstado_Empresa = (TextField) root.lookup("#tfEstado_Empresa");
                    final TextField tfTotal_Tokens = (TextField) root.lookup("#tfTotal_Tokens");
                    final TextField tfBusqueda_ID = (TextField) root.lookup("#tfBusqueda_ID");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<infoTablaToken> tablaData = (TableView<infoTablaToken>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    label_menu.setVisible(false);
                    final Button obtMasReg = (Button) root.lookup("#obtMasReg");

                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

                    final DropShadow shadow = new DropShadow();
                    detalle_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            detalle_op.setCursor(Cursor.HAND);
                            detalle_op.setEffect(shadow);
                        }
                    });

                    detalle_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            detalle_op.setCursor(Cursor.DEFAULT);
                            detalle_op.setEffect(null);

                        }
                    });

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

                    obtMasReg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            obtMasReg.setCursor(Cursor.HAND);
                            obtMasReg.setEffect(shadow);
                        }
                    });

                    obtMasReg.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            obtMasReg.setCursor(Cursor.DEFAULT);
                            obtMasReg.setEffect(null);

                        }
                    });

                    try {
                        pane.getChildren().remove(3);

                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

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
                        arbol_servadd.setDisable(false);
                    }

                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaData.itemsProperty().bind(task.valueProperty());
                    task.start();

                    /**
                     * configuracion de la paginacion
                     */
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            final Cliente datosCliente = Cliente.getCliente();
                            tfNit.setText(datosCliente.getId_cliente());

                            tfEsquema_Seguridad.setText(esquema);

                            tfEstado_Empresa.setText(estado_empresa);
                            tfTotal_Tokens.setText(total_token);

                            if ("S".equals(getIndicadorRegistros())) {
                                obtMasReg.setDisable(false);
                            } else {
                                obtMasReg.setDisable(true);
                            }

                            tablaData.itemsProperty().unbind();
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = token.size() % rowsPerPage() == 0 ? token.size() / rowsPerPage() : token.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {

                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = token.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = token.size() / rowsPerPage();
                                        } else {
                                            lastIndex = token.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<infoTablaToken> subList = token.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<infoTablaToken> subList = token.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaData.getItems().setAll(token.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= token.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : token.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(4);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(147);
                            pagination.setVisible(true);
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
                                    numpagina.decrementAndGet();
                                    // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                                    arbol_servadd.getSelectionModel().clearSelection();
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
                            numpagina.decrementAndGet();
                            //setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });

                } catch (Exception e) {

                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });

    }

    /**
     * BUSCAR POR ID
     */
    private void BusqIdEmpresasTask(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoTablaToken>> TaskTablaId = new BusqIdEmpresasTask(id);

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
                    tabla_datos.itemsProperty().bind(TaskTablaId.valueProperty());
                    TaskTablaId.reset();
                    TaskTablaId.start();

                    TaskTablaId.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<infoTablaToken> TablaID = TaskTablaId.getValue();


                            try {
                                /**
                                 * configuracion de la paginacion
                                 */
                                final int numpag = TablaID.size() % rowsPerPageId() == 0 ? TablaID.size() / rowsPerPageId() : TablaID.size() / rowsPerPageId() + 1;

                                pagination = new Pagination(numpag, 0);
                                //paginationid.setId("paginationid");
                                pagination.setPageFactory(new Callback<Integer, Node>() {
                                    @Override
                                    public Node call(final Integer pageIndex) {

                                        if (pageIndex > numpag) {
                                            return null;
                                        } else {
                                            int lastIndex = 0;
                                            int displace = TablaID.size() % rowsPerPageId();
                                            if (displace >= 0) {
                                                lastIndex = TablaID.size() / rowsPerPageId();
                                            } else {
                                                lastIndex = TablaID.size() / rowsPerPageId() - 1;
                                            }
                                            int page = pageIndex * itemsPerPage();

                                            for (int i = page; i < page + itemsPerPage(); i++) {

                                                if (lastIndex == pageIndex) {
                                                    final List<infoTablaToken> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoTablaToken> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + rowsPerPageId());
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

                                pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                                    @Override
                                    public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                                        currentpageindex = newValue.intValue();
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPageId(), ((newValue.intValue() * rowsPerPageId() + rowsPerPageId() <= TablaID.size()) ? newValue.intValue() * rowsPerPageId() + rowsPerPageId() : TablaID.size())));
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
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(4);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(147);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, t.getSource().getException());

                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            //System.out.println("cancelo la tarea");

                        }
                    });

                    
                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    private boolean istext(String val) {
        boolean retorno = false;
        String pattern = "^[a-zA-Z0-9]*$";
        if (val.matches(pattern)) {
            retorno = true;
        }
        return retorno;
    }

    @FXML
    void idbuscarkeytyped(final KeyEvent event) {

        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {
            if (istext(event.getCharacter())) {
                event.consume();
                //System.out.println("ID a buscar :" + tfBusqueda_ID.getText() + event.getCharacter());
                synchronized (this) {
                    BusqIdEmpresasTask(tfBusqueda_ID.getText() + event.getCharacter());
                }
            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (tfBusqueda_ID.getText().isEmpty()) {
                        synchronized (this) {
                            BusqIdEmpresasTask("");
                        }
                    } else {
                        synchronized (this) {
                            BusqIdEmpresasTask(tfBusqueda_ID.getText());
                        }
                    }

                }
            }
        }
    }

    @FXML
    void idbuscarkeypress(final KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(tfBusqueda_ID, "*", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            tfBusqueda_ID.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();
        }
    }

    public class GetTokenTask extends Service<ObservableList<infoTablaToken>> {

        @Override
        protected Task<ObservableList<infoTablaToken>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // setNumpagina(getNumpagina() + 1);
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    String Respuesta2 = new String();
                    final StringBuilder tramaTokenEmpresas = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramaTokenEmpresas.append("850,033,");
                    tramaTokenEmpresas.append(datosCliente.getRefid());
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(AtlasConstantes.COD_TOKEN_EMPRESAS);
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append("1");
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(datosCliente.getId_cliente());
                    tramaTokenEmpresas.append(",");
                    final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                    tramaTokenEmpresas.append(contra);
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append("9");
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append("");
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(numpagina.incrementAndGet());
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(datosCliente.getContraseña());
                    tramaTokenEmpresas.append(",");
                    tramaTokenEmpresas.append(datosCliente.getTokenOauth());
                    tramaTokenEmpresas.append(",~");

                    // System.out.println("trama TokenEmpresas " + tramaTokenEmpresas.toString());
                    if (MarcoPrincipalController.newTokenEmpresas) {
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token empresas = " + "##" + docs.obtenerHoraActual());
                            //idUsurario%estadoUsuario%NombreUsuario%RolUsuario%PerfilUsuario%EstadoToken%DescEstadoToken%serialToken%fechaExp%indicadorAccesoEmergencia;..... 
//                            Respuesta = "850,"
//                                    + "033,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "9,"
//                                    + "BANCOLOMBIA,"
//                                    + "Superusuario,"
//                                    + "Activada,"
//                                    + "3,"
//                                    + "TK,"
//                                    + "20,"
//                                    + (numpagina.get()<=3?"1,":"0,")
//                                    + "angelagar%Pendiente Aprobación%Angela Garzón%Superusuario%MACRO_PROFILE%E%10245864%06/09/2016%NO;"
//                                    + "juancas%Pendiente Aprobación%Juan Castaño%Superusuario%MACRO_PROFILE%E%11445795%08/09/2016%SI;"
//                                    + "andresbui%Aprobada%Andrés Builes%Transaccional%MACRO_PROFILE%P%120354978%10/09/2016%SI;"
//                                    + "1angelagar%Pendiente Aprobación%Angela Garzón%Superusuario%MACRO_PROFILE%E%10245864%06/09/2016%NO;"
//                                    + "2angelagar%Pendiente Aprobación%Angela Garzón%Superusuario%MACRO_PROFILE%E%10245864%06/09/2016%SI;"
//                                    + "3angelagar%Pendiente Aprobación%Angela Garzón%Superusuario%MACRO_PROFILE%E%10245864%06/09/2016%NO;"
//                                    + "3angelagar%Pendiente Aprobación%Angela Garzón%Superusuario%MACRO_PROFILE%E%10245864%06/09/2016%NO;"
//                                    + "4angelagar%Pendiente Aprobación%Angela Garzón%Superusuario%MACRO_PROFILE%E%10245864%06/09/2016%SI;"
//                                    + "5angelagar%Pendiente Aprobación%Angela Garzón%Superusuario%MACRO_PROFILE%E%10245864%06/09/2016%SI;"
//                                    + "julianaper%Pendiente Aprobación%Juliana Perez%Superusuario%MACRO_PROFILE%A%159878865%12/09/2016%NO,"
//                                    + "~";

                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenEmpresas.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenEmpresas.toString());
                            // System.out.println(" RESPUESTA TRAMA TOKEN EMPRESAS:" + Respuesta);

                            //idUsurario%estadoUsuario%NombreUsuario%RolUsuario%PerfilUsuario%SerialCertificado%CodigoEstadoCertificado%DescripcionEstadoCert%fechaExp;.....
//                            Respuesta2 = "850,"
//                                    + "033,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "10,"
//                                    + "BANCOLOMBIA,"
//                                    + "Superusuario,"
//                                    + "Activada,"
//                                    + "3,"
//                                    + "TK,"
//                                    + "abcd123%ESTADO USUARIO%Stepha%superusuario%MACRO_PROFILE%SC123%CEC123%DEC123%06/09/2016;"
//                                    + "abcd456%ESTADO USUARIO%Stepha%superusuario%MACRO_PROFILE%SC123%CEC123%DEC123%06/09/2016,"
//                                    + "~";
//////                                 
//                                    
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenEmpresas.toString());
                            //System.out.println(" RESPUESTA TRAMA TOKEN EMPRESAS:" + Respuesta2);
                            // Thread.sleep(2000);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                            //docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta2 + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token empresas  = " + "##" + docs.obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenEmpresas.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenEmpresas.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                                //docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta2 + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (TokenEmpresasController.cancelartarea.get()) {                                            
                                            cancel();
                                            numpagina.decrementAndGet();
                                            //setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            failed();
                                            numpagina.decrementAndGet();
                                            //setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                                        }
                                    }
                                });

                            }

                        }

                    } else {
                        Respuesta = "850,"
                                + "033,"
                                + "000,"
                                + ",~";

                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        esquema = Respuesta.split(",")[6];
                        estado_empresa = Respuesta.split(",")[7];
                        total_token = Respuesta.split(",")[8];
                        setIndicadorRegistros(Respuesta.split(",")[11]);

                        if ("9".equals(Respuesta.split(",")[4]) && "TK".equals(Respuesta.split(",")[9])) {
                            if (TokenEmpresasController.cancelartarea.get()) {
                                cancel();
                            } else {
                                if (MarcoPrincipalController.newTokenEmpresas) {
                                    if (token.isEmpty()) {
                                        token = FXCollections.observableArrayList();
                                    }

                                    //tabla_datos.getItems();
                                    //tfNit.setText(token.get(0).nit().getValue());
                                    String[] Ltoken = Respuesta.split(",")[12].split(";");

                                    for (int i = 0; i < Ltoken.length; i++) {
                                        String[] datos = Ltoken[i].split("%");
                                        final infoTablaToken data = new infoTablaToken(
                                                //CAMBIAR LA POS 6 X LA 7, LA 7 X LA 8 Y LA 8 X LA 9

                                                //                                                String colId_usuario,
                                                //                                                String colEstado1,
                                                //                                                String colNombre_usuario,
                                                //                                                String colRol,
                                                //String colPerfil,
                                                //                                                String colEstado2, 
                                                //                                                String colSerial,
                                                //                                                String colFecha_exp,
                                                //                                                String colAe,

                                                datos[0].trim(), //colId_usuario
                                                datos[1].trim(), //colEstado1
                                                datos[2].trim(),//colNombre_usuario
                                                datos[3].trim(), //colRol
                                                datos[4].trim(), //colPerfil
                                                datos[5].trim(), //colEstado2
                                                datos[7].trim(), //colSerial
                                                datos[8].trim(), //colFecha_exp
                                                datos[9].trim(), //colAe
                                                "", "", "", "", "", "", "");

                                        token.add(data);

                                    }
//                                    synchronized (this) {
//                                        MarcoPrincipalController.newTokenEmpresas = false;
//                                    }
                                }

                            }

                        } else if ("9".equals(Respuesta.split(",")[4]) && "CD".equals(Respuesta.split(",")[9])) {

                            esquema = Respuesta.split(",")[6];
                            estado_empresa = Respuesta.split(",")[7];
                            total_token = Respuesta.split(",")[8];
                            //"abcd123%ESTADO USUARIO%Stepha%superusuario%MACRO_PROFILE%SC123%CEC123%DEC123%06/09/2016;"

                            if (TokenEmpresasController.cancelartarea.get()) {
                                cancel();
                            } else {
                                if (MarcoPrincipalController.newTokenEmpresas) {
                                    //  tablaDatos.setItems(emptyObservableList);
                                    token = FXCollections.observableArrayList();

                                    String[] Ltoken = Respuesta2.split(",")[12].split(";");

                                    for (int i = 0; i < Ltoken.length; i++) {
                                        String[] datos = Ltoken[i].split("%");
                                        final infoTablaToken data = new infoTablaToken(
                                                datos[0].trim(),
                                                datos[2].trim(),
                                                datos[1].trim(),
                                                datos[3].trim(),
                                                datos[4].trim(),
                                                datos[5].trim(),
                                                datos[8].trim(),
                                                "", "", "", "", "", "", "", "", "");

                                        token.add(data);

                                    }
                                    synchronized (this) {
                                        MarcoPrincipalController.newTokenEmpresas = false;
                                    }
                                }
                            }

                        }

                    } else {

                        if (TokenEmpresasController.cancelartarea.get()) {
                            cancel();
                            numpagina.decrementAndGet();

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

                    return token;

                }
            };
        }
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 8;
    }

    public int rowsPerPageId() {
        return 8;
    }

    /**
     * EVENTO CONTINUAR
     */
    @FXML
    void continuarOP(final ActionEvent event) {
        esquemadis = tfEsquema_Seguridad.getText();

        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle token" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle token" + "##" + obtenerHoraActual());
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

    /**
     * METODO Y SERVICIO VER DETALLES
     */
    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }

    public Service<Void> continuar_Op() {
        serviceDispoToken = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramaTokenEmpresas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        //850,034,connid,codTransaccion,tipoDoc,identificacion,requiereResp,claveHardware,~
                        tramaTokenEmpresas.append("850,034,");
                        tramaTokenEmpresas.append(datosCliente.getRefid());
                        tramaTokenEmpresas.append(",");
                        tramaTokenEmpresas.append(AtlasConstantes.COD_TOKEN_EMPRESAS_DISPO);
                        tramaTokenEmpresas.append(",");
                        tramaTokenEmpresas.append("1");
                        tramaTokenEmpresas.append(",");
                        tramaTokenEmpresas.append(datosCliente.getId_cliente());
                        tramaTokenEmpresas.append(",");
                        final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                        tramaTokenEmpresas.append(contra);
                        tramaTokenEmpresas.append(",");
                        tramaTokenEmpresas.append("1"); // numPagina
                        tramaTokenEmpresas.append(",");
                        tramaTokenEmpresas.append(datosCliente.getContraseña());
                        tramaTokenEmpresas.append(",");
                        tramaTokenEmpresas.append(datosCliente.getTokenOauth());
                        tramaTokenEmpresas.append(",~");

                        // System.out.println("TRAMA TOKEN EMPRESAS DISPONIBLE :" + tramaTokenEmpresas);
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token empresas = " + "##" + docs.obtenerHoraActual());
                            //850,034,000,descripcion,nombreCompañia,tokenSecurity,registrosRecuperados,serial1%fechaExp1;serial2%fechaExp2;.....serialn%fechaExpn,~
//                            Respuesta = "850,"
//                                    + "034,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "BANCOLOMBIA,"
//                                    + "tokenSecurity,"
//                                    + "registrosRecuperados,"
//                                    + "S,"
//                                    + "10245864%06/09/2016;"
//                                    + "11445795%08/09/2016;"
//                                    + "1445795%08/09/2016;"
//                                    + "11445795%08/09/2016,"
//                                    + "~";

                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenEmpresas.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenEmpresas.toString());

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token empresas  = " + "##" + docs.obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenEmpresas.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenEmpresas.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuar_op.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {

//                            final infoDetalletf infodetalletf = infoDetalletf.getInfoDetalletf();
//                            infodetalletf.setEsquema_seguridad(Respuesta.split(",")[6].trim());
//                            infoDetalletf.setInfoDetalletf(infodetalletf);
                            final String indMasReg = Respuesta.split(",")[7];
                            String[] data = Respuesta.split(",")[8].split(";");
                            final List<infoTokenDisponibles> listadis = new ArrayList<infoTokenDisponibles>();

                            for (int i = 0; i < data.length; i++) {
                                final String[] datos = data[i].split("%");

                                final infoTokenDisponibles data2 = new infoTokenDisponibles(
                                        datos[0].trim(),
                                        datos[1].trim());

                                listadis.add(data2);

                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final TokenEmpresasDisponiblesController controller = new TokenEmpresasDisponiblesController();
                                    controller.mostrarDispoToken(listadis, esquemadis, indMasReg, 1);
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

                        return null;
                    }
                };
            }
        };

        return serviceDispoToken;

    }
}
