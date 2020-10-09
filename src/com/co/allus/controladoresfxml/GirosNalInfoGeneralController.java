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
import com.co.allus.modelo.girosnal.mapeoEstadoGiro;
import com.co.allus.modelo.girosnal.tablaInfoGnralGirosnal;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class GirosNalInfoGeneralController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn ColCanalOri;
    @FXML
    private TableColumn ColCanalPago;
    @FXML
    private TableColumn ColCelBenef;
    @FXML
    private TableColumn ColContDiaGiro;
    @FXML
    private TableColumn ColDocBenef;
    @FXML
    private TableColumn ColDocGirador;
    @FXML
    private TableColumn ColEstadoGiro;
    @FXML
    private TableColumn ColFechaCancel;
    @FXML
    private TableColumn ColFechaPago;
    @FXML
    private TableColumn ColFechaPteCancel;
    @FXML
    private TableColumn ColFechaVta;
    @FXML
    private TableColumn ColNomGirador;
    @FXML
    private TableColumn ColSms;
    @FXML
    private TableColumn ColValIva;
    @FXML
    private TableColumn ColValTotal;
    @FXML
    private TableColumn ColValorComision;
    @FXML
    private TableColumn ColValorGiro;
    @FXML
    private TableColumn ColCelGirador;
    @FXML
    private TableColumn colPuntoserPago;
    @FXML
    private TableColumn colPuntoservVenta;
    @FXML
    private TableColumn ColCelCliente;
    @FXML
    private TableColumn ColAcumAnual2;
    @FXML
    private TableColumn ColAcumMensual2;
    @FXML
    private TableColumn ColMotCancel;
    @FXML
    private Button buscarFechas;
    @FXML
    private TableColumn colNomBenef;
    @FXML
    private DatePicker fechafin;
    @FXML
    private DatePicker fechaini;
    @FXML
    private TextField consecutivoRef2;
    @FXML
    private Button indMasReg;
    @FXML
    private Label montoAnio;
    @FXML
    private Label montoMEs;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<tablaInfoGnralGirosnal> tablaDatos;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label tituloGirosnal;
    @FXML
    private Button volverOP;
    @FXML
    private Label labelmontoMes;
    @FXML
    private Label labelmontoanio;
    @FXML
    private Rectangle recmontoanio;
    @FXML
    private Rectangle recmontomes;
    @FXML
    private ProgressBar progreso;
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static final GestorDocumental gestorDoc = new GestorDocumental();
    private transient Service<Void> servicesSms;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    public static AtomicInteger numpagina = new AtomicInteger(0);
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static AtomicBoolean cancelarTareasGirosNal = new AtomicBoolean();
    public static ObservableList<tablaInfoGnralGirosnal> registros = FXCollections.observableArrayList();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaV = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    public static String indicadorRegistros = "N";
    private Service<ObservableList<tablaInfoGnralGirosnal>> task = new GirosNalInfoGeneralController.GetconveniosTask();
    private static tablaInfoGnralGirosnal selectedGiro;
    public static Calendar fechalimite = Calendar.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColCanalOri != null : "fx:id=\"ColCanalOri\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColCanalPago != null : "fx:id=\"ColCanalPago\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColCelBenef != null : "fx:id=\"ColCelBenef\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColCelGirador != null : "fx:id=\"ColCelGirador\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColContDiaGiro != null : "fx:id=\"ColContDiaGiro\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColDocBenef != null : "fx:id=\"ColDocBenef\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColDocGirador != null : "fx:id=\"ColDocGirador\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColEstadoGiro != null : "fx:id=\"ColEstadoGiro\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColFechaCancel != null : "fx:id=\"ColFechaCancel\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColFechaPago != null : "fx:id=\"ColFechaPago\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColFechaPteCancel != null : "fx:id=\"ColFechaPteCancel\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColFechaVta != null : "fx:id=\"ColFechaVta\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColNomGirador != null : "fx:id=\"ColNomGirador\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColSms != null : "fx:id=\"ColSms\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColValIva != null : "fx:id=\"ColValIva\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColValTotal != null : "fx:id=\"ColValTotal\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColValorComision != null : "fx:id=\"ColValorComision\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColValorGiro != null : "fx:id=\"ColValorGiro\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert colPuntoserPago != null : "fx:id=\"colPuntoserPago\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert colPuntoservVenta != null : "fx:id=\"colPuntoservVenta\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert buscarFechas != null : "fx:id=\"buscarFechas\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert colNomBenef != null : "fx:id=\"colNomBenef\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert fechafin != null : "fx:id=\"fechafin\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert fechaini != null : "fx:id=\"fechaini\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert consecutivoRef2 != null : "fx:id=\"consecutivoRef2\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert labelmontoMes != null : "fx:id=\"labelmontoMes\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert labelmontoanio != null : "fx:id=\"labelmontoanio\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert montoAnio != null : "fx:id=\"montoAnio\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert montoMEs != null : "fx:id=\"montoMEs\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert recmontoanio != null : "fx:id=\"recmontoanio\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert recmontomes != null : "fx:id=\"recmontomes\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert tituloGirosnal != null : "fx:id=\"tituloGirosnal\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert volverOP != null : "fx:id=\"volverOP\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColCelCliente != null : "fx:id=\"ColCelCliente\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColAcumAnual2 != null : "fx:id=\"ColAcumAnual2\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColAcumMensual2 != null : "fx:id=\"ColAcumMensual2\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";
        assert ColMotCancel != null : "fx:id=\"ColMotCancel\" was not injected: check your FXML file 'GirosNalInfoGeneral.fxml'.";

        this.resources = rb;
        this.location = url;

        indMasReg.setDisable(true);
//        buscarFechas.setDisable(true);

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaDatos.setEditable(true);
        tablaDatos.setPlaceholder(new Label("No se ha realizado ninguna consulta"));

        colPuntoservVenta.setCellValueFactory(new PropertyValueFactory("colPuntoservVenta"));   //0
        ColCanalOri.setCellValueFactory(new PropertyValueFactory("colCanalVenta"));             //1
        ColFechaVta.setCellValueFactory(new PropertyValueFactory("colFechaVta"));               //2
        ColDocGirador.setCellValueFactory(new PropertyValueFactory("colDocGirador"));           //3
        ColNomGirador.setCellValueFactory(new PropertyValueFactory("colNomGirador"));           //4
        ColCelGirador.setCellValueFactory(new PropertyValueFactory("colCelGirador"));           //5
        ColDocBenef.setCellValueFactory(new PropertyValueFactory("colDocBenef"));               //6
        colNomBenef.setCellValueFactory(new PropertyValueFactory("colNomBenef"));               //7
        ColCelBenef.setCellValueFactory(new PropertyValueFactory("colCelBenef"));               //8
        colPuntoserPago.setCellValueFactory(new PropertyValueFactory("colPuntoserPago"));       //9
        ColCanalPago.setCellValueFactory(new PropertyValueFactory("colCanalPago"));             //10
        ColFechaPago.setCellValueFactory(new PropertyValueFactory("colFechaPago"));             //11
        ColValorGiro.setCellValueFactory(new PropertyValueFactory("colValorGiro"));             //12
        ColValorComision.setCellValueFactory(new PropertyValueFactory("colValorComision"));     //13
        ColValIva.setCellValueFactory(new PropertyValueFactory("colValIva"));                   //14
        ColValTotal.setCellValueFactory(new PropertyValueFactory("colValTotal"));               //15
        ColEstadoGiro.setCellValueFactory(new PropertyValueFactory("colEstadoGiro"));           //16
        ColFechaCancel.setCellValueFactory(new PropertyValueFactory("colFechaCancel"));         //17
        ColFechaPteCancel.setCellValueFactory(new PropertyValueFactory("colFechaPteCancel"));   //18
        ColContDiaGiro.setCellValueFactory(new PropertyValueFactory("colContDiaGiro"));         //19
        ColSms.setCellValueFactory(new PropertyValueFactory("colSms"));                         //20

        ColCelCliente.setCellValueFactory(new PropertyValueFactory("colCelcliente"));           //21
        ColAcumAnual2.setCellValueFactory(new PropertyValueFactory("colAcumAnual2"));           //22
        ColAcumMensual2.setCellValueFactory(new PropertyValueFactory("colAcumMes2"));           //23
        ColMotCancel.setCellValueFactory(new PropertyValueFactory("colMotCancel"));           //24

        ColSms.setCellFactory(new Callback<TableColumn<tablaInfoGnralGirosnal, String>, TableCell<tablaInfoGnralGirosnal, String>>() {
            @Override
            public TableCell<tablaInfoGnralGirosnal, String> call(TableColumn<tablaInfoGnralGirosnal, String> p) {
                return new LinkTableCell<tablaInfoGnralGirosnal, String>();
            }
        });

        fechaini.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaV);
        fechafin.getCalendarView().selectedDateProperty().addListener(eventoMenuFechaN);

        final Calendar instance = Calendar.getInstance();
        fechafin.setSelectedDate(instance.getTime());
        instance.add(Calendar.DAY_OF_MONTH, -90);
        fechaini.setSelectedDate(instance.getTime());
        setFechalimite(instance);

        consecutivoRef2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    buscarFechas.setDisable(true);
                } else if (isnumber(newValue)) {
                    buscarFechas.setDisable(false);
                } else {
                    buscarFechas.setDisable(true);
                }
            }
        });

        numpagina.set(0);

        cancelartarea.set(false);
        setIndicadorRegistros("N");

        setSelectedGiro(null);
        progreso.setVisible(false);

    }

    public static tablaInfoGnralGirosnal getSelectedGiro() {
        return selectedGiro;
    }

    public static void setSelectedGiro(tablaInfoGnralGirosnal selectedGiro) {
        GirosNalInfoGeneralController.selectedGiro = selectedGiro;
    }

    public void mostrarGirosNalInfoGnral(final int origen) {

        if (origen == 0) {
            GirosNalInfoGeneralController.registros.clear();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/GirosNalInfoGeneral.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();

                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button masRegistros = (Button) root.lookup("#indMasReg");
                    final Button btnBuscar = (Button) root.lookup("#buscarFechas");
                    final Button btnvolver = (Button) root.lookup("#volverOP");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<tablaInfoGnralGirosnal> tablaData = (TableView<tablaInfoGnralGirosnal>) root.lookup("#tablaDatos");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final Label label_giros = (Label) root.lookup("#tituloGirosnal");

                    final Label label_montoMes = (Label) root.lookup("#labelmontoMes");
                    final Label label_montoAnio = (Label) root.lookup("#labelmontoanio");
                    final Rectangle rec_montoMes = (Rectangle) root.lookup("#recmontomes");
                    final Rectangle rec_montoAnio = (Rectangle) root.lookup("#recmontoanio");
                    final Label montoMes = (Label) root.lookup("#montoMEs");
                    final Label montoAnio = (Label) root.lookup("#montoAnio");

//                    final RestrictiveTextField consecutivoRef2 = (RestrictiveTextField) root.lookup("#consecutivoRef2");
//                    consecutivoRef2.setMaxLength(20);
//                    consecutivoRef2.setRestrict("[0-9]");
//                    consecutivoRef2.setIsAlphanum(false);
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    if ("]".equals(getIndicadorRegistros())) {
                        masRegistros.setDisable(false);
                    } else {
                        masRegistros.setDisable(true);
                    }

                    final DropShadow shadow = new DropShadow();
                    masRegistros.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            masRegistros.setCursor(Cursor.HAND);
                            masRegistros.setEffect(shadow);
                        }
                    });

                    masRegistros.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            masRegistros.setCursor(Cursor.DEFAULT);
                            masRegistros.setEffect(null);
                        }
                    });

                    btnBuscar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            btnBuscar.setCursor(Cursor.HAND);
                            btnBuscar.setEffect(shadow);
                        }
                    });

                    btnBuscar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            btnBuscar.setCursor(Cursor.DEFAULT);
                            btnBuscar.setEffect(null);
                        }
                    });

                    btnvolver.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            btnvolver.setCursor(Cursor.HAND);
                            btnvolver.setEffect(shadow);
                        }
                    });

                    btnvolver.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            btnvolver.setCursor(Cursor.DEFAULT);
                            btnvolver.setEffect(null);
                        }
                    });

//                    if (GiroNalInitController.isGirador.get()) {
//                        label_giros.setText("Consulta Giros Enviados");
//                        tablaData.getColumns().remove(3, 6);
//                        tablaData.getColumns().get(21).setText("Celular Beneficiario");
//                        rec_montoMes.setVisible(true);
//                        rec_montoAnio.setVisible(true);
//                        label_montoAnio.setVisible(true);
//                        label_montoMes.setVisible(true);
//                        montoMes.setVisible(true);
//                        montoAnio.setVisible(true);
//                    } else if (GiroNalInitController.isBeneficiario.get()) {
//                        label_giros.setText("Consulta Giros Recibidos");
//                        tablaData.getColumns().remove(6, 9);
//                        tablaData.getColumns().get(21).setText("Celular Girador");
//                        rec_montoMes.setVisible(false);
//                        rec_montoAnio.setVisible(false);
//                        label_montoAnio.setVisible(false);
//                        label_montoMes.setVisible(false);
//                        montoMes.setVisible(false);
//                        montoAnio.setVisible(false);
//                    }
                    try {
                        pane.getChildren().remove(3);

                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                        Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    label_menu.setVisible(false);

                    if (origen == 1) {
                        /**
                         * barra progreso
                         */
                        Region veil = new Region();
                        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                        ProgressIndicator p = new ProgressIndicator();
                        p.setMaxSize(150, 150);
                        panel_tabla.getChildren().addAll(veil, p);

                        // Use binding to be notified whenever the data source chagnes
                        // task = new GetTarjetasTask();
                        p.progressProperty().bind(task.progressProperty());
                        veil.visibleProperty().bind(task.runningProperty());
                        p.visibleProperty().bind(task.runningProperty());
                        tablaData.itemsProperty().bind(task.valueProperty());
                        task.start();

                        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {

                                tablaData.itemsProperty().unbind();
//                                System.out.println("TERMINO TAREA");
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
                                                    final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaData.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                root.getChildren().get(root.getChildren().size() - 1).setLayoutX(1);
                                root.getChildren().get(root.getChildren().size() - 1).setLayoutY(90);
                                pagination.setVisible(true);
                                Atlas.vista.show();
                            }
                        });

                        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
//                                System.out.println("ERROR EN LA CONSULTA");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                                        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
                                        final TreeView<String> arbolGirosNal = (TreeView<String>) pane.lookup("#arbolGirosNal");
                                        if (arbolGirosNal != null) {
                                            arbolGirosNal.setDisable(false);
                                        }
                                        arbolGirosNal.getSelectionModel().clearSelection();
                                    }
                                });
                            }
                        });

                        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
//                                System.out.println("cancelo la tarea");
                            }
                        });
                    } else {
                        if (!registros.isEmpty()) {

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
                                                final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<tablaInfoGnralGirosnal> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(1);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(90);
                            pagination.setVisible(true);
                            Atlas.vista.show();
                        }
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                }
            }
        });

    }

    public String getIndicadorRegistros() {
        return GirosNalInfoGeneralController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        GirosNalInfoGeneralController.indicadorRegistros = indicadorRegistros;
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
                            final ObservableList<tablaInfoGnralGirosnal> TablaID = task.getValue();

                            if ("]".equals(getIndicadorRegistros())) {
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
                                                    final List<tablaInfoGnralGirosnal> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<tablaInfoGnralGirosnal> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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

                                } catch (Exception ex) {
                                    Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(90);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);

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
                            numpagina.addAndGet(-1);
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });
                } catch (Exception ex) {
                    Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void buscarFechas(ActionEvent event) {
//        if (!getIndicadorRegistros().equals("]")) {
        GirosNalInfoGeneralController.registros.clear();
        numpagina.set(0);

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
                            final ObservableList<tablaInfoGnralGirosnal> TablaID = task.getValue();

                            if ("]".equals(getIndicadorRegistros())) {
                                indMasReg.setDisable(false);
                            } else {
                                indMasReg.setDisable(true);
                            }
                            tablaInfoGnralGirosnal get = TablaID.get(0);

                            final Cliente datosCliente = Cliente.getCliente();
                            final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                            if (GiroNalInitController.isGirador.get()) {
                                String info = "";
                                info = get.colnombreClienteProperty().getValue() + "\nC.C: " + datosCliente.getId_cliente();
                                cliente.setText("");
                                cliente.setText(info);
                            } else if (GiroNalInitController.isBeneficiario.get()) {
                                String info = "";
                                info = get.colnombreClienteProperty().getValue() + "\nC.C: " + datosCliente.getId_cliente();
                                cliente.setText("");
                                cliente.setText(info);
                            }

                            montoAnio.setText(get.colAcumAnualProperty().getValue());
                            montoMEs.setText(get.colAcumMesProperty().getValue());

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
                                                    final List<tablaInfoGnralGirosnal> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<tablaInfoGnralGirosnal> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
//                                try {
//                                    //AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);
//                                    if (!AnchorPane.getChildren().remove(pagination)) {
                                // AnchorPane.getChildren().remove(AnchorPane.getChildren().size() - 1);
//                                    }
//                                } catch (Exception e) {
//                                    System.out.println("no hay hijos para borrar");
//                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(90);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            numpagina.addAndGet(-1);
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            numpagina.addAndGet(-1);
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);
                        }
                    });
                } catch (Exception ex) {
                    Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
//        } else {
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    new ModalMensajes("Hay registros pendientes de consultar por el canal", "Advertencia", ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.DEFAULT);
//                }
//            });
//        }
    }

    @FXML
    void volverOP(ActionEvent event) {
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

        final TreeView<String> arbol_servad = (TreeView<String>) pane.lookup("#arbol_servadicionales");
        if (arbol_servad != null) {
            arbol_servad.setDisable(false);
        }

        final TreeView<String> arbol_infotdc = (TreeView<String>) pane.lookup("#arbol_infotdc");
        if (arbol_infotdc != null) {
            arbol_infotdc.setDisable(false);
        }

        final TreeView<String> arbol_consultas = (TreeView<String>) pane.lookup("#arbol_consultas");
        if (arbol_consultas != null) {
            arbol_consultas.setDisable(false);
        }

        final TreeView<String> arbol_movmientos = (TreeView<String>) pane.lookup("#arbol_movimientos");
        if (arbol_movmientos != null) {
            arbol_movmientos.setDisable(false);
        }

        final TreeView<String> arbol_contrabloqueos = (TreeView<String>) pane.lookup("#arbol_contrabloqueos");
        if (arbol_contrabloqueos != null) {
            arbol_contrabloqueos.setDisable(false);
        }

        final TreeView<String> arbol_infoseguridad = (TreeView<String>) pane.lookup("#arbol_infoseguridad");
        if (arbol_infoseguridad != null) {
            arbol_infoseguridad.setDisable(false);
        }

        final TreeView<String> arbol_puntosCol = (TreeView<String>) pane.lookup("#arbolPuntosCol");
        if (arbol_puntosCol != null) {
            arbol_puntosCol.setDisable(false);
        }

        final TreeView<String> arbolGirosNal = (TreeView<String>) pane.lookup("#arbolGirosNal");
        if (arbolGirosNal != null) {
            arbolGirosNal.setDisable(false);
        }

        arbolGirosNal.getSelectionModel().clearSelection();
        GirosNalInfoGeneralController.registros.clear();
        numpagina.set(0);
        setIndicadorRegistros("N");
        try {
            pane.getChildren().remove(3);
        } catch (Exception e) {
            gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        Atlas.vista.show();
    }

    public class GetconveniosTask extends Service<ObservableList<tablaInfoGnralGirosnal>> {

        @Override
        protected Task<ObservableList<tablaInfoGnralGirosnal>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaGirosNalGnral = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    String rnv = "";
                    if (GiroNalInitController.isBeneficiario.get()) {
                        rnv = "B";
                    } else if (GiroNalInitController.isGirador.get()) {
                        rnv = "G";
                    }
                    //850,069,connid,codtransaccion,identificacion,tigoConsulta,fechainicial,fechafinal,numeroPaginado,clavehardware,~
                    //Nueva:  850,069,connid,codtransaccion,identificacion,tigoConsulta,fechainicial,fechafinal,numeroPaginado,concecutivoRef2,clavehardware,~

                    //tramaGirosNalGnral.append("850,069,");  //Trx Anterior
                    tramaGirosNalGnral.append("850,082,");
                    tramaGirosNalGnral.append(datosCliente.getRefid()); //CONNID
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(AtlasConstantes.COD_CONSULTA_GIROS_INFOGNRAL);//COD TRX
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(datosCliente.getId_cliente());//ID
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(rnv);
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(formatoFecha.format(fechaini.getSelectedDate()));
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(formatoFecha.format(fechafin.getSelectedDate()));
                    tramaGirosNalGnral.append(",");
                    tramaGirosNalGnral.append(numpagina.addAndGet(1) - 1);
                    tramaGirosNalGnral.append(",");//
                    tramaGirosNalGnral.append(consecutivoRef2.getText());
                    tramaGirosNalGnral.append(",");//
                    tramaGirosNalGnral.append(datosCliente.getContraseña());//CLAVE HW
                    tramaGirosNalGnral.append(",");//
                    tramaGirosNalGnral.append(datosCliente.getTokenOauth());//CLAVE HW
                    tramaGirosNalGnral.append(",~");

//                    System.out.println("Trama Consulta GirosNal: " + tramaGirosNalGnral);
                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Consulta Giros Nacionales = " + "##" + gestorDoc.obtenerHoraActual());

                        //Trama Old
                        //Respuesta="850,069,000,0000:TRANSACCION EXITOSA                                              ,LEIDY AMANDA BEJARANO GAMEZ                                 ,000003054120768,000000000090100,00,000000000106600,00,*,101,0000060952%0917%1%000001121896111%CAMILO ANDRES OSSUNA TORRES%0917%20190507%00040000%PAG%00000000%00000000%0024%000003178729656%00003352%00000638%00043990%032130%CNB%013623%CNB,~";//;0000061017%0917%1%000001010233482%JOHAN ARMANDO NARIÑO MENDOZA%0000%00000000%00030000%ANU%20190528%20190527%0024%000003175767925%00003352%00000638%00033990%032130%CNB%000000%   ;0000061011%0917%1%000001067930978%DARLING WILLIANA JHONSON CERPA%0917%20190507%00040000%PAG%00000000%00000000%0024%000003017731508%00003352%00000638%00043990%032130%CNB%035404%CNB;0000061015%0917%1%000001007463414%BRANDON DAVID ACOSTA TORRES%0661%20190509%00030000%PAG%00000000%00000000%0024%000003115416462%00003352%00000638%00033990%032130%CNB%000004%SUC;0000061018%0917%1%000001001294909%CRISTIAN ANDRES AGUILAR CANACUE%0917%20190509%00030000%PAG%00000000%00000000%0024%000003057371342%00003352%00000638%00033990%032130%CNB%027196%CNB;0000061019%0917%1%000001052407055%JOHANA CAROLINA MARTINEZ SILVA%0661%20190513%00030000%PAG%00000000%00000000%0024%000003212888566%00003352%00000638%00033990%032130%CNB%000004%SUC;0000061020%0917%1%000001020769601%ANDREA TATIANA CONDISA MESA%0917%20190507%00030000%PAG%00000000%00000000%0024%000003043889810%00003352%00000638%00033990%032130%CNB%028768%CNB;0000061024%0917%1%000001106890656%LUIS FERNANDO ARIAS JIMENEZ%0917%20190507%00030000%PAG%00000000%00000000%0024%000003104556794%00003352%00000638%00033990%032130%CNB%017759%CNB;0000061025%0917%1%000000053140923%DIANA CAROLINA TRUJILLO ANGULO%0917%20190509%00030000%PAG%00000000%00000000%0024%000003174066381%00003352%00000638%00033990%032130%CNB%018325%CNB;0000061026%0917%1%000000042799389%PAULA ANDREA GIRALDO SANCHEZ%0917%20190514%00020000%PAG%00000000%00000000%0024%000003502446606%00003352%00000638%00023990%032130%CNB%024598%CNB;0000061029%0917%1%000001026148617%LISETH JOHANA CORREA AREIZA%0917%20190508%00020000%PAG%00000000%00000000%0024%000003016570262%00003352%00000638%00023990%032130%CNB%025889%CNB;0000061032%0917%1%000001128431380%LUISA MARIA ALVAREZ DAVILA%0917%20190508%00020000%PAG%00000000%00000000%0024%000003005793451%00003352%00000638%00023990%032130%CNB%019470%CNB;0000061035%0917%1%000001020487345%ANYI PAOLA CASTRILLON OSORNO%0000%00000000%00020000%VTA%00000000%00000000%0024%000003013983593%00003352%00000638%00023990%032130%CNB%000000%   ;0000061036%0917%1%000001039464526%SERGIO  CORTES AGUIRRE%0917%20190508%00020000%PAG%00000000%00000000%0024%000003136932682%00003352%00000638%00023990%032130%CNB%029228%CNB;0000061043%0917%1%000001020432836%CATALINA  ATEHORTUA GRAJALES%0917%20190508%00020000%PAG%00000000%00000000%0024%000003117553195%00003352%00000638%00023990%032130%CNB%011291%CNB;0000061044%0917%1%000001214718869%WILLIAM ALEXIS HERRERA MAYA%0917%20190510%00020000%PAG%00000000%00000000%0024%000003504001232%00003352%00000638%00023990%032130%CNB%013119%CNB;0000061051%0917%1%000001152706651%ANDRES FELIPE CASTAÑEDA LOAIZA%0000%00000000%00020000%VTA%00000000%00000000%0024%000003148900852%00003352%00000638%00023990%032130%CNB%000000%   ;0000061048%0917%1%000001152190499%CRISTIEL STEFANY MESA ECHEVERRI%0917%20190510%00020000%PAG%00000000%00000000%0024%000003502141376%00003352%00000638%00023990%032130%CNB%035578%CNB;0000061052%0917%1%000001152222733%ANGIE PAOLA TOBON ESCOBAR%0917%20190507%00020000%PAG%00000000%00000000%0024%000003155709047%00003352%00000638%00023990%032130%CNB%018390%CNB;0000061054%0917%1%000001019116186%LINA MARIA AGUIRRE DIAZ%0917%20190509%00050000%PAG%00000000%00000000%0024%000003178346839%00003352%00000638%00053990%032130%CNB%034513%CNB;0000061056%0917%1%000001007829619%DAVID SANTIAGO DIAZ GIL%0917%20190508%00050000%PAG%00000000%00000000%0024%000003197218809%00003352%00000638%00053990%032130%CNB%030991%CNB;0000061058%0917%1%000001233894084%GINA MARCELA CASTAÑEDA PAEZ%0917%20190507%00050000%PAG%00000000%00000000%0024%000003204959686%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061059%0917%1%000001233982880%FORERO CUBIDES JEIMY TATIANA%0000%00000000%00050000%VTA%00000000%00000000%0024%000003136693598%00003352%00000638%00053990%032130%CNB%000000%   ;0000061061%0917%1%000001111198970%JOSE JAVIER SATIZABAL BELTRAN%0917%20190508%00050000%PAG%00000000%00000000%0024%000003219356000%00003352%00000638%00053990%032130%CNB%022764%CNB;0000061063%0917%1%000001019144471%JUAN NICOLAS BERMUDEZ MALDONADO%0917%20190507%00050000%PAG%00000000%00000000%0024%000003174375343%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061065%0917%1%000001030672405%ANGIE LORENA SANCHEZ VARGAS%0917%20190509%00050000%PAG%00000000%00000000%0024%000003124809485%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061066%0917%1%000000052712919%DIANA CAROLINA LUZARDO FORERO%0000%00000000%00050000%VTA%00000000%00000000%0024%000003136440303%00003352%00000638%00053990%032130%CNB%000000%   ;0000061068%0917%1%000001018513101%ANGIE PAOLA RODRIGUEZ CORTES%0917%20190508%00050000%PAG%00000000%00000000%0024%000003115305011%00003352%00000638%00053990%032130%CNB%034513%CNB;0000061069%0917%1%000001062401958%ANA MARIA MORALES ACUÑA%0917%20190508%00050000%PAG%00000000%00000000%0024%000003005243686%00003352%00000638%00053990%032130%CNB%034513%CNB;0000061070%0917%1%000001002517328%BRIYITH TATIANA MONTAÑA CARDOZO%0917%20190507%00040000%PAG%00000000%00000000%0024%000003004420017%00003352%00000638%00043990%032130%CNB%025110%CNB;0000061072%0917%1%000001030672713%LINA MARIA VELANDIA CHAPARRO%0917%20190508%00050000%PAG%00000000%00000000%0024%000003113070358%00003352%00000638%00053990%032130%CNB%034513%CNB;0000061073%0917%1%000001018457451%DAVID LEONARDO ROSERO HERNANDEZ%0917%20190507%00050000%PAG%00000000%00000000%0024%000003219065051%00003352%00000638%00053990%032130%CNB%026778%CNB;0000061074%0917%1%000001192769161%JEISY MICHELT MARTINEZ LOPEZ%0917%20190507%00050000%PAG%00000000%00000000%0024%000003188037664%00003352%00000638%00053990%032130%CNB%015601%CNB;0000061077%0917%1%000001019097318%JENNYFER  CORTES JARAMILLO%0917%20190509%00050000%PAG%00000000%00000000%0024%000003113121909%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061078%0917%1%000001023962937%DIANA MARCELA TORRES SANTIAGO%0917%20190509%00050000%PAG%00000000%00000000%0024%000003118515302%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061086%0917%1%000001007211647%LOPEZ GARZON YEISSON JAVIER%0000%00000000%00050000%VTA%00000000%00000000%0024%000003193290144%00003352%00000638%00053990%032130%CNB%000000%   ;0000061079%0917%1%000001020832783%JHOAN SEBASTIAN VASQUEZ BEJARANO%0917%20190507%00050000%PAG%00000000%00000000%0024%000003508146314%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061081%0917%1%000001030552098%JENNIFFER  MELENJE SANTACRUZ%0917%20190509%00050000%PAG%00000000%00000000%0024%000003114644283%00003352%00000638%00053990%032130%CNB%034513%CNB;0000061083%0917%1%000000080791243%JULIAN ANDRES CARRANZA ACOSTA%0917%20190508%00050000%PAG%00000000%00000000%0024%000003214026469%00003352%00000638%00053990%032130%CNB%034513%CNB;0000061084%0917%1%000001087211764%GUSTAVO ADOLFO VALENCIA MESA%0000%00000000%00050000%VTA%00000000%00000000%0024%000003118645712%00003352%00000638%00053990%032130%CNB%000000%   ;0000061089%0917%1%000001013641024%NANCY JULIETH GAMBOA ROJAS%0917%20190507%00050000%PAG%00000000%00000000%0024%000003142483466%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061090%0917%1%000001026593784%NATALIA YINNET FUENMAYOR CASTILLO%0917%20190508%00050000%PAG%00000000%00000000%0024%000003146685319%00003352%00000638%00053990%032130%CNB%029455%CNB;0000061091%0917%1%000001053340452%JAVIER FELIPE PRADA ALFONSO%0917%20190507%00050000%PAG%00000000%00000000%0024%000003188561124%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061093%0917%1%000001063175891%ANYELO  LOPEZ HERNANDEZ%0917%20190521%00050000%PAG%00000000%00000000%0024%000003206707879%00003352%00000638%00053990%032130%CNB%027194%CNB;0000061095%0917%1%000000074083735%ROLANDO  GAVIRIA SANCHEZ%0917%20190507%00050000%PAG%00000000%00000000%0024%000003124055844%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061096%0917%1%000001018424051%DIANA MARCELA TORRES GARCIA%0917%20190507%00050000%PAG%00000000%00000000%0024%000003114814370%00003352%00000638%00053990%032130%CNB%025107%CNB;0000061097%0917%1%000001035852236%SANDRA MILENA GARCIA TOBON%0917%20190508%00030000%PAG%00000000%00000000%0024%000003108341880%00003352%00000638%00033990%032130%CNB%018372%CNB;0000061098%0917%1%000001000293874%LEIDY TATIANA ROLDAN GONZALEZ%0917%20190507%00030000%PAG%00000000%00000000%0024%000003188506075%00003352%00000638%00033990%032130%CNB%023659%CNB;0000061100%0917%1%000001092359047%FERNEL  ROJAS GUERRERO%0917%20190508%00030000%PAG%00000000%00000000%0024%000003215831127%00003352%00000638%00033990%032130%CNB%013623%CNB;0000061101%0917%1%000001013594972%LUIS FELIPE ANGULO SERRANO%0917%20190507%00030000%PAG%00000000%00000000%0024%000003224378963%00003352%00000638%00033990%032130%CNB%014260%CNB;0000061102%0917%1%000001037576799%LEIDY TATIANA ROJAS CONGOTE%0917%20190507%00020000%PAG%00000000%00000000%0024%000003135960558%00003352%00000638%00023990%032130%CNB%032139%CNB;0000061104%0917%1%000001098798149%CARLOS ALBERTO PUPO PALMEZANO%0917%20190507%00020000%PAG%00000000%00000000%0024%000003162401970%00003352%00000638%00023990%032130%CNB%012597%CNB;0000061105%0917%1%000001067857127%PAOLA PATRICIA PETRO PEREZ%0917%20190507%00040000%PAG%00000000%00000000%0024%000003104203899%00003352%00000638%00043990%032130%CNB%024263%CNB;0000061117%0917%1%000001016106828%STEFANIA  CHAVES PORRAS%0000%00000000%00050000%VTA%00000000%00000000%0024%000003145400704%00003352%00000638%00053990%032130%CNB%000000%   ;0000061108%0917%1%000001027948837%ROSA IRIS SALAS CORDOBA%0917%20190507%00050000%PAG%00000000%00000000%0024%000003107022047%00003352%00000638%00053990%032130%CNB%029918%CNB;0000061118%0917%1%000000079889199%FERNANDO ENRIQUE FLOREZ AGUIRRE%0917%20190507%00050000%PAG%00000000%00000000%0024%000003107668134%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061112%0917%1%000001017200175%SIMON  ECHAVARRIA MAYA%0917%20190507%00075000%PAG%00000000%00000000%0024%000003052381132%00003352%00000638%00078990%032130%CNB%034702%CNB;0000061115%0917%1%000001022968554%DILLY KATHERINE GUERRERO VERANO%0917%20190507%00030000%PAG%00000000%00000000%0024%000003203944009%00003352%00000638%00033990%032130%CNB%019562%CNB;0000061120%0917%1%000001023022916%DIANA CAROLINA CHINGATE GAMBA%0000%00000000%00050000%VTA%00000000%00000000%0024%000003103098244%00003352%00000638%00053990%032130%CNB%000000%   ;0000061121%0917%1%000001104016827%MARTIN JUNIOR MORALES MUÑOZ%0917%20190508%00050000%PAG%00000000%00000000%0024%000003146083250%00003352%00000638%00053990%032130%CNB%025110%CNB;0000061122%0917%1%000001014189827%DEISY LORENA BELTRAN VELEZ%0917%20190507%00050000%PAG%00000000%00000000%0024%000003144672184%00003352%00000638%00053990%032130%CNB%022752%CNB;0000061123%0917%1%000001090411353%FABIANA LORENA BERMUDEZ URREGO%0917%20190508%00020000%PAG%00000000%00000000%0024%000003023631474%00003352%00000638%00023990%032130%CNB%012492%CNB;0000061423%0917%1%000001016090319%JEFERSON NOEL LIS GALEANO%0000%00000000%00020000%VTA%00000000%00000000%0023%000003104970586%00003352%00000638%00023990%032130%CNB%000000%   ;0000061425%0917%1%000001010203642%LEIDY YOLANY ESPINOSA ORJUELA%0917%20190510%00020000%PAG%00000000%00000000%0023%000003138915959%00003352%00000638%00023990%032130%CNB%025110%CNB;0000061427%0917%1%000001042772215%LUISA FERNANDA RESTREPO CEBALLOS%0917%20190511%00050000%PAG%00000000%00000000%0023%000003506149024%00003352%00000638%00053990%032130%CNB%036301%CNB;0000061429%0917%1%000001214722645%SARA  CASTAÑEDA GARZON%0917%20190509%00050000%PAG%00000000%00000000%0023%000003004992423%00003352%00000638%00053990%032130%CNB%023009%CNB;0000061430%0917%1%000001007739577%KEVIN ANDRES MOLINA OSORIO%0917%20190510%00050000%PAG%00000000%00000000%0023%000003046387584%00003352%00000638%00053990%032130%CNB%028478%CNB;0000061433%0917%1%000001152184973%ANDRES FELIPE OSPINA GUERRA%0613%20190509%00060000%PAG%00000000%00000000%0023%000003186546630%00003352%00000638%00063990%032130%CNB%000002%SUC;0000061436%0917%1%000001152455970%ALEJANDRO  FLOREZ AREIZA%0917%20190515%00060000%PAG%00000000%00000000%0023%000003137553052%00003352%00000638%00063990%032130%CNB%015956%CNB;0000061437%0917%1%000001013364230%FERNANDO EMILIO BARRERA HERNANDEZ%0000%00000000%00075000%PEN%00000000%20190531%0023%000003007151133%00003352%00000638%00078990%032130%CNB%000000%   ;0000061443%0917%1%000001017188936%ANDRES JULIAN LOPEZ SEPULVEDA%0917%20190509%00040000%PAG%00000000%00000000%0023%000003005412913%00003352%00000638%00043990%032130%CNB%010228%CNB;0000061439%0917%1%000001216725094%JUAN CAMILO ACOSTA FERNANDEZ%0917%20190513%00075000%PAG%00000000%00000000%0023%000003195776788%00003352%00000638%00078990%032130%CNB%019451%CNB;0000063751%0917%1%000001096233317%YEZMIN ADRIANA RUEDA HERNANDEZ%0917%20190516%00050000%PAG%00000000%00000000%0016%000003012525361%00003352%00000638%00053990%032130%CNB%013119%CNB;0000063754%0917%1%000001015458538%JAVIER MAURICIO CHARRY LUGO%0917%20190519%00030000%PAG%00000000%00000000%0016%000003214203923%00003352%00000638%00033990%032130%CNB%033688%CNB;0000063758%0917%1%000001067930978%DARLING WILLIANA JHONSON CERPA%0917%20190515%00050000%PAG%00000000%00000000%0016%000003017731508%00003352%00000638%00053990%032130%CNB%010772%CNB;0000063760%0917%1%000001017214799%YHOJAN ARLEY BETANCUR GALEANO%0917%20190516%00050000%PAG%00000000%00000000%0016%000003002003402%00003352%00000638%00053990%032130%CNB%028478%CNB;0000063761%0917%1%000001102830317%KAREN ANDREA BERMUDEZ MERCADO%0917%20190516%00050000%PAG%00000000%00000000%0016%000003014761107%00003352%00000638%00053990%032130%CNB%012748%CNB;0000063762%0917%1%000001037665058%JULIAN CAMILO DAVILA GONZALEZ%0917%20190516%00100000%PAG%00000000%00000000%0016%000003015304898%00003352%00000638%00103990%032130%CNB%025173%CNB;0000063763%0917%1%000001093784208%LUIS BERNARDO GARCIA ACOSTA%0917%20190516%00050000%PAG%00000000%00000000%0016%000003012604433%00003352%00000638%00053990%032130%CNB%022604%CNB;0000063764%0917%1%000000014164381%LEONARDO GUZMAN%0000%00000000%00050000%VTA%00000000%00000000%0016%000003133925773%00003352%00000638%00053990%032130%CNB%000000%   ;0000063765%0917%1%000001037599818%JERRY ANDERSON URAN ARANGO%0917%20190515%00050000%PAG%00000000%00000000%0016%000003194841980%00003352%00000638%00053990%032130%CNB%028602%CNB;0000063766%0917%1%000001214717193%PAOLA ANDREA ANDRADE RESTREPO%0000%00000000%00050000%VTA%00000000%00000000%0016%000003024640400%00003352%00000638%00053990%032130%CNB%000000%   ;0000063767%0917%1%000001036942205%DAMARIS  RODRIGUEZ RAMIREZ%0000%00000000%00020000%VTA%00000000%00000000%0016%000003103621762%00003352%00000638%00023990%032130%CNB%000000%   ;0000063768%0917%1%000001152205744%STEFANY  MARTINEZ OSORIO%0917%20190516%00030000%PAG%00000000%00000000%0016%000003044226233%00003352%00000638%00033990%032130%CNB%011801%CNB;0000063769%0917%1%000001042772215%LUISA FERNANDA RESTREPO CEBALLOS%0917%20190520%00060000%PAG%00000000%00000000%0016%000003506149024%00003352%00000638%00063990%032130%CNB%036301%CNB;0000063771%0917%1%000001214722645%SARA  CASTAÑEDA GARZON%0917%20190518%00060000%PAG%00000000%00000000%0016%000003004992423%00003352%00000638%00063990%032130%CNB%036351%CNB;0000063774%0917%1%000001007739577%KEVIN ANDRES MOLINA OSORIO%0917%20190517%00050000%PAG%00000000%00000000%0016%000003046387584%00003352%00000638%00053990%032130%CNB%028478%CNB;0000063790%0917%1%000001152184973%ANDRES FELIPE OSPINA GUERRA%0613%20190517%00090000%PAG%00000000%00000000%0016%000003186546630%00003352%00000638%00093990%032130%CNB%000002%SUC;0000063794%0917%1%000001152455970%ALEJANDRO  FLOREZ AREIZA%0917%20190515%00105000%PAG%00000000%00000000%0016%000003137553052%00005882%00001118%00112000%032130%CNB%023505%CNB;0000063796%0917%1%000001013364230%FERNANDO EMILIO BARRERA HERNANDEZ%0000%00000000%00030000%VTA%00000000%00000000%0016%000003007151133%00003352%00000638%00033990%032130%CNB%000000%   ;0000063798%0917%1%000001216725094%JUAN CAMILO ACOSTA FERNANDEZ%0917%20190522%00075000%PAG%00000000%00000000%0016%000003195776788%00003352%00000638%00078990%032130%CNB%020707%CNB;0000063801%0917%1%000001017188936%ANDRES JULIAN LOPEZ SEPULVEDA%0917%20190515%00030000%PAG%00000000%00000000%0016%000003005412913%00003352%00000638%00033990%032130%CNB%010228%CNB;0000063802%0917%1%000001000293874%LEIDY TATIANA ROLDAN GONZALEZ%0917%20190520%00060000%PAG%00000000%00000000%0016%000003188506075%00003352%00000638%00063990%032130%CNB%016357%CNB;0000063804%0917%1%000001092359047%FERNEL  ROJAS GUERRERO%0917%20190516%00060000%PAG%00000000%00000000%0016%000003215831127%00003352%00000638%00063990%032130%CNB%026676%CNB;0000063816%0917%1%000001053865964%JEIMY LORENA GUTIERREZ BELTRAN%0917%20190517%00060000%PAG%00000000%00000000%0016%000003223544525%00003352%00000638%00063990%032130%CNB%021499%CNB;0000063807%0917%1%000001013594972%LUIS FELIPE ANGULO SERRANO%0917%20190515%00060000%PAG%00000000%00000000%0016%000003224378963%00003352%00000638%00063990%032130%CNB%016831%CNB;0000063808%0917%1%000001037576799%LEIDY TATIANA ROJAS CONGOTE%0917%20190515%00050000%PAG%00000000%00000000%0016%000003135960558%00003352%00000638%00053990%032130%CNB%032139%CNB;0000063810%0917%1%000001098798149%CARLOS ALBERTO PUPO PALMEZANO%0917%20190515%00020000%PAG%00000000%00000000%0016%000003162401970%00003352%00000638%00023990%032130%CNB%024599%CNB;0000063813%0917%1%000001020483743%LISBET  SANCHEZ GARZON%0000%00000000%00050000%VTA%00000000%00000000%0016%000003023955381%00003352%00000638%00053990%032130%CNB%000000%   ;0000063817%0917%1%000001067857127%PAOLA PATRICIA PETRO PEREZ%0917%20190515%00060000%PAG%00000000%00000000%0016%000003104203899%00003352%00000638%00063990%032130%CNB%024263%CNB;0000063818%0917%1%000001027948837%ROSA IRIS SALAS CORDOBA%0917%20190515%00050000%PAG%00000000%00000000%0016%000003107022047%00003352%00000638%00053990%032130%CNB%029918%CNB,~";
                        //Trama Enviados
                        //Respuesta="850,082,000,TRANSACCION EXITOSA                                                   ,1,CIRO MARI?O PERLAZA SANCHEZ                                 ,000000000483200,00,000000000483200,00,1,069,41709%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190708%00000000001540000%1%00000000%3128342076%3103909690%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41667%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000001540000%3%20190705%3002574561%3104587622%00000000000335200%00000000000063800%00000000000013108%CNB%%KON%%%NP%No hay plaza;41666%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000002340000%1%00000000%3002574561%3104587622%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41664%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41663%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41662%00917%01%201901094%RAUL EDWIN SANCHEZ CALLEJAS%00000%20190704%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41370%00917%01%201901047%ANA EDITH OROZCO NINO%00000%20190626%00000000000500000%3%20190704%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%NP%No hay plaza;41001%00917%01%100001154%FELIPE CAMILO ARAQUE CHICA%00000%20190626%00000000000500000%3%20190705%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%RP%Razones personales;40772%00917%01%100001235%FELIPE MARIO LONDO?O ROMERO%00000%20190626%00000000000500000%3%20190704%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%RP%Razones personales;39114%00917%01%100001019%JOHAN CARLOS LOZADA CARDONA%00000%20190626%00000000000500000%3%20190627%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%NP%No hay plaza;37980%00917%01%99100240%CIRO MARI?O PERLAZA SANCHEZ%00917%20190620%00000000001800012%5%20190620%3000000001%3000000002%00000000000360025%00000000000034201%00000000000012036%CNB%00000000000012036%CNB%%%%;37637%00917%01%100001033%YEISON IVAN GARCIA RUEDA%00000%20190606%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;37345%00917%01%100001201%ANDRES ANTONIO OCAMPO NAVARRO%00000%20190606%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;36222%00917%01%99103470%ANA MARGARITA CAICEDO%00000%20190529%00000000000500000%3%20190612%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;36190%00917%01%100001261%YURLEY ALICIA HIGUITA OCHOA%00000%20190529%00000000000500000%3%20190612%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;35882%00917%01%100001043%PABLO OSCAR PACHON PAJARO%00000%20190529%00000000000500000%3%20190604%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;35194%00917%01%100001256%ANGELICA ALEXANDRA ALVAREZ ACEVEDO%00000%20190529%00000000000500000%3%20190612%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;35089%00917%01%100001043%PABLO OSCAR PACHON PAJARO%00000%20190529%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;35068%00917%01%100001326%LEIDY CLAUDIA NU?EZ NAVIA%00000%20190529%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;33690%00917%01%100001229%ALEJANDRO MARIO SANCHEZ OCHOA%00000%20190529%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;33171%00917%01%99104016%FERNANDO RAFAEL SERNA ESCOBAR%00000%20190527%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;32616%00917%01%100001262%KARINA EDITH HIGUITA NORIEGA%00000%20190527%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;32093%00917%01%99104016%FERNANDO RAFAEL SERNA ESCOBAR%00000%20190527%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;28177%00917%01%1673605%GUILLERMO ALFONSO GARCIA CERQUERA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;27597%00917%01%100001347%YURLEY ELIZABETH HENAO ROSAS%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;27277%00917%01%100001208%ANDRES CAMILO ARAQUE RUEDA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;27251%00917%01%100001293%DIANA CATALINA HERNANDEZ MURILLO%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;26626%00917%01%100001361%MARCELA HELENA LOPEZ VANEGAS%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;26502%00917%01%99106858%ADRIANA PILAR GUERRERO CALLE%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;26030%00917%01%100001336%MARIA AMANDA SANABRIA GIRALDO%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25964%00917%01%99107111%CARLOS ARTURO DUQUE GAVIRIA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25777%00917%01%100001062%RAUL FABIAN PEREZ CAMPOS%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25513%00917%01%100001294%DANIELA OLGA OROZCO NI?O%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25352%00917%01%100001041%EDUARDO HUGO RESTREPO CARDONA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;25068%00917%01%201901002%BEATRIZ AMANDA HERRERA NAVIA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;24520%00917%01%100001048%HUMBERTO IVAN GARCIA OCHOA%00000%20190522%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;24200%00917%01%100001200%EDUARDO CARLOS PEREZ VALENCIA%00917%20190521%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;23670%00917%01%100001338%PAULA OLGA HENAO NI?O%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;23613%00917%01%100001032%LUIS HUGO ARAQUE TAMAYO%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;23462%00917%01%100001374%LEIDY CINDY MEJIA NORIEGA%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;22837%00917%01%100001314%PAULA KATERINE MELO NEIRA%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;22416%00917%01%100001052%JUAN EDWIN TOVAR GOMEZ%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;22387%00917%01%1532527%CIFIN CALIDAD IDENTIFICACION PRUEBAS%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;22244%00917%01%100001280%LEIDY LUCIA MELO NAVARRO%00917%20190520%00000000000500000%2%20190522%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%00000000000012036%CNB%%%%;21072%00917%01%99107590%KELLY CRISTINA AVILA CORDOBA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;20648%00917%01%100001171%JUAN IVAN OCAMPO CARDONA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;20597%00917%01%100001277%KARINA HELENA NU?EZ CRUZ%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;19803%00917%01%100001108%RAFAEL ELKIN RINCON ROMERO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;19425%00917%01%100001228%LUIS NICOLAS RUIZ UPEGUI%00000%20190517%00000000000500000%3%20190704%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%RP%Razones personales;19063%00917%01%99106858%ADRIANA PILAR GUERRERO CALLE%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;18216%00917%01%201901098%LUIS MARIO LOZADA MESA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;16933%00917%01%100001215%FELIPE GARCIA MESA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;16806%00917%01%100001143%ALEJANDRO FABIAN FORONDA CANO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;16473%00917%01%99103755%PEDRO MIGUEL SALAZAR HERRERA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15644%00917%01%100001040%TOMAS MARIO GARCIA VALENCIA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15527%00917%01%100001177%HERNAN HUGO LONDO?O ROMERO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15287%00917%01%100001350%LINA FLOR VELEZ ACEVEDO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15202%00917%01%100001297%MARIA ALICIA HERNANDEZ GIRALDO%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;15127%00917%01%201901002%BEATRIZ AMANDA HERRERA NAVIA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;14673%00917%01%99107111%CARLOS ARTURO DUQUE GAVIRIA%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;14291%00917%01%100001279%KARINA ROSA HERNANDEZ MORALES%00000%20190517%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;13607%00917%01%100001305%YULIANA ROSA SALAZAR TRUJILLO%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;13401%00917%01%100001311%BEATRIZ LUCIA ROMERO NAVIA%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;12275%00917%01%100001218%TOMAS EDWIN GAVIRIA MONTOYA%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;11628%00917%01%104685%CIFIN CALIDAD IDENTIFICACION PRUEBAS%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;11312%00917%01%99103470%ANA MARGARITA CAICEDO%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;11015%00917%01%20122745%CIFIN CALIDAD IDENTIFICACION PRUEBAS%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;8982%00917%01%100001252%DANIELA KATERINE SALAZAR NI?O%00000%20190515%00000000000500000%1%00000000%3000000456%3000000456%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%,~";
                        //Respuesta="850,082,000,TRANSACCION EXITOSA                                                   ,1,CIRO MARI?O PERLAZA SANCHEZ                                 ,000000000483200,00,000000000483200,00,1,069,41709%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190708%00000000001540000%1%00000000%3128342076%3103909690%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%,~";
                        //Trama Recibidos
                        //Respuesta="850,082,000,TRANSACCION EXITOSA                                                   ,1,CIRO MARI?O PERLAZA SANCHEZ                                 ,000000000483200,00,000000000483200,00,1,069,41710%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190708%00000000002340000%1%00000000%3006086275%3125856281%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41706%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190708%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41705%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190708%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41669%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190704%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41668%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190704%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41665%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190704%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41661%00917%01%201901088%ANDRES FREDY FORONDA NAVARRO%00000%20190704%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41660%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41659%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190704%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41658%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41657%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41656%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41655%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41654%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41653%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41652%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41651%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41648%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41635%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41634%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190703%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;41540%00917%01%100001340%MARCELA AMANDA VARGAS MURILLO%00000%20190626%00000000000500000%1%00000000%3000000342%3000000342%00000000000005000%00000000000000500%00000000000010201%CNB%%%%%%;39883%00917%01%100001029%ANDRES EDWIN AVILA ARIAS%00000%20190626%00000000000500000%1%00000000%3000000057%3000000057%00000000000005000%00000000000000500%00000000000010201%CNB%%%%%%;39081%00917%01%100001301%KARINA ELI GARCIA VANEGAS%00000%20190626%00000000000500000%3%20190627%3000000303%3000000303%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%NP%No hay plaza;39049%00917%01%100001330%JESSICA OLGA LOPEZ CASTA?EDA%00000%20190626%00000000000500000%3%20190627%3000000332%3000000332%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%NP%No hay plaza;38831%00917%01%99103769%CAMILO ANDRES CORTES RUIZ%00000%20190626%00000000000500000%3%20190627%3000000398%3000000398%00000000000005000%00000000000000500%00000000000010201%CNB%%KON%%%NP%No hay plaza;38015%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190625%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000013108%CNB%%%%%%;37980%00917%01%99100240%CIRO MARI?O PERLAZA SANCHEZ%00917%20190620%00000000001800012%5%20190620%3000000001%3000000002%00000000000360025%00000000000034201%00000000000012036%CNB%00000000000012036%CNB%%%%;37952%00917%01%201901096%JUAN FREDY SANCHEZ NAVARRO%00000%20190620%00000000000500000%1%00000000%3000000034%3000000034%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;37865%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190614%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;37845%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00917%20190613%00000000001800012%5%20190614%3000000001%3000000002%00000000000360025%00000000000034201%00000000000012036%CNB%00000000000012036%CNB%%%%;37835%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00917%20190611%00000000002340000%5%20190611%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%00000000000012036%CNB%%%%;37834%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00917%20190611%00000000001800012%5%20190611%3000000001%3000000002%00000000000360025%00000000000034201%00000000000012036%CNB%00000000000012036%CNB%%%%;37833%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190611%00000000001800012%1%00000000%3000000001%3000000002%00000000000360025%00000000000034201%00000000000012036%CNB%%%%%%;37829%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190610%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;37828%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190610%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;37827%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190610%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;37826%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00917%20190610%00000000002340000%5%20190620%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%00000000000012036%CNB%%%%;37825%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190610%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;37815%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190606%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;37814%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190606%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;37376%00917%01%201901092%JOHAN CAMILO AVILA RUEDA%00000%20190606%00000000000500000%1%00000000%3000000037%3000000037%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;37277%00917%01%100001332%PAULA CINDY HERRERA MORALES%00000%20190606%00000000000500000%1%00000000%3000000334%3000000334%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;36789%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190606%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;36778%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190606%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;36728%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190605%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;36725%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190605%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;36722%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00001%20190605%00000000001230000%2%20190621%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%1234567890CB00001%CNB%%%%;36713%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190605%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;36712%00917%01%99103302%RAMIRO ALBERTO PELAEZ RODRIGUEZ%00000%20190605%00000000001230000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;36708%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190605%00000000002340000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;36309%00917%01%100001189%TOMAS HUGO AVILA RUEDA%00000%20190529%00000000000500000%1%00000000%3000000191%3000000191%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;35955%00917%01%100001047%PEDRO DELIO SANCHEZ VALENCIA%00000%20190529%00000000000500000%3%20190604%3000000075%3000000075%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;35450%00917%01%201901036%ANA KATERINE MELO CUARTAS%00000%20190529%00000000000500000%3%20190612%3000000023%3000000023%00000000000005000%00000000000000500%00000000000010003%CNB%%KON%%%NP%No hay plaza;34917%00917%01%100001316%MARCELA ELI PEREZ NORIEGA%00000%20190529%00000000000500000%1%00000000%3000000318%3000000318%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;34902%00917%01%100001153%JOHAN JAVIER RUIZ PALACIOS%00000%20190529%00000000000500000%1%00000000%3000000155%3000000155%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;34703%00917%01%100001305%YULIANA ROSA SALAZAR TRUJILLO%00000%20190529%00000000000500000%1%00000000%3000000307%3000000307%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;33721%00917%01%1502059%CIFIN CALIDAD IDENTIFICACION PRUEBAS%00000%20190529%00000000000500000%1%00000000%3000000422%3000000422%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;32644%00917%01%99103467%HERNANDO FRANCISCO DIAZ%00000%20190527%00000000000500000%1%00000000%3000000423%3000000423%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;32319%00917%01%100001306%PAULA ALICIA ROMERO NINO%00000%20190527%00000000000500000%1%00000000%3000000308%3000000308%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;32269%00917%01%100001114%DANIEL EDGAR BLANCO TAMAYO%00000%20190527%00000000000500000%1%00000000%3000000116%3000000116%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;31869%00917%01%100001334%BEATRIZ OLGA AREIZA NEIRA%00000%20190527%00000000000500000%1%00000000%3000000336%3000000336%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;31541%00917%01%100001282%ANDREA CATALINA VARGAS MORALES%00000%20190527%00000000000500000%1%00000000%3000000284%3000000284%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;31243%00917%01%201901095%ALEJANDRO HUGO SANCHEZ VALENCIA%00000%20190527%00000000000500000%1%00000000%3000000030%3000000030%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;29731%00917%01%99103760%IRMA JEANET ANDRADE CASTILLO%00000%20190527%00000000000500000%1%00000000%3000000387%3000000387%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%;29177%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190523%00000000005600000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;29174%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190523%00000000005600000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;29172%00917%01%201901091%RAFAEL JEISON BLANCO CALLEJAS%00000%20190523%00000000005600000%1%00000000%3115698566%3125896544%00000000000335200%00000000000063800%00000000000012036%CNB%%%%%%;29157%00917%01%100001192%JUAN CARLOS SANCHEZ VALENCIA%00000%20190522%00000000000500000%1%00000000%3000000194%3000000194%00000000000005000%00000000000000500%00000000000010003%CNB%%%%%%,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaGirosNalGnral.toString());
                        //System.out.println("Respuesta " + Respuesta);
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Consulta Giros Nacionales = " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaGirosNalGnral.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    failed();
                                }
                            });
                            throw new Exception("ERROR");
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        // numpagina.set(Respuesta.split(",")[9].split(";").length);
                        String nombreCliente = Respuesta.split(",")[5].trim();
                        //String celularCliente = Respuesta.split(",")[5].trim();
                        setIndicadorRegistros(Respuesta.split(",")[10]);//Indicador de más registros                        
//                        String acomuladorMes = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[6] + Respuesta.split(",")[7]));
//                        String acomuladorAnual = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[8] + Respuesta.split(",")[9]));
                        String tipoConsulta = Respuesta.split(",")[4];
                        String acomuladorMes = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[6]));
                        String acomuladorAnual = "$ " + formatonum.format(Double.parseDouble(Respuesta.split(",")[8]));

                        String[] Ltarjetas = Respuesta.split(",")[12].split(";");

                        for (int i = 0; i < Ltarjetas.length; i++) {
                            String[] datos = Ltarjetas[i].split("%");
                            String fechaPago = "";
                            String fechacancelado = "";
                            String fechapend = "";
                            String fechaEstado = "";
                            String celularCliente = "";
                            int DiaGiros = 0;
                            String AcumuladoMes2 = "";
                            String AcumuladoAnual2 = "";
                            String canalUltimoEstado = "";
                            String PuntoServicioPago = "";
                            String motivoCancelacion = "";

                            try {
                                fechaPago = formatoFecha2.format(formatoFecha.parse(StringUtilities.eliminarCerosLeft(datos[6].trim())));
                            } catch (Exception e) {
                                fechaPago = datos[6].trim();
                            }

                            try {
                                fechaEstado = formatoFecha2.format(formatoFecha.parse(StringUtilities.eliminarCerosLeft(datos[9].trim())));
                            } catch (Exception e) {
                                fechaEstado = datos[9].trim();
                            }

                            try {
                                celularCliente = datos[10].trim();
                            } catch (Exception e) {
                                celularCliente = "";
                            }

                            try {
                                DiaGiros = Integer.parseInt(datos[11]);
                            } catch (Exception e) {
                                DiaGiros = 0;
                            }

                            Calendar instance = Calendar.getInstance();
                            instance.add(Calendar.DAY_OF_MONTH, -DiaGiros);

                            try {
                                AcumuladoMes2 = "$ " + formatonum.format(Double.parseDouble(datos[18]));
                            } catch (Exception e) {
                                AcumuladoMes2 = "";
                            }

                            try {
                                AcumuladoAnual2 = "$ " + formatonum.format(Double.parseDouble(datos[19]));
                            } catch (Exception e) {
                                AcumuladoAnual2 = "";
                            }

                            try {
                                canalUltimoEstado = datos[17];
                            } catch (Exception ex) {
                                Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                            try {
                                PuntoServicioPago = StringUtilities.eliminarCerosLeft(datos[16]);
                            } catch (Exception ex) {
                                Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                            try {
                                motivoCancelacion = StringUtilities.eliminarCerosLeft(datos[21]);
                            } catch (Exception ex) {
                                Logger.getLogger(GirosNalInfoGeneralController.class.getName()).log(Level.SEVERE, null, ex);

                            }

                            String valorTotal = "$ " + formatonum.format(Double.parseDouble(datos[7].substring(0, datos[7].length() - 2)) + Double.parseDouble(datos[12].substring(0, datos[12].length() - 2)) + Double.parseDouble(datos[13].substring(0, datos[13].length() - 2)));

                            String ColCelGir = "";   //GiroNalInitController.isGirador.get() ? StringUtilities.eliminarCerosLeft(datos[10]) : StringUtilities.eliminarCerosLeft(datos[11]),  //colCelGirador         
                            String ColCelBen = "";   //GiroNalInitController.isGirador.get() ? StringUtilities.eliminarCerosLeft(datos[11]) : StringUtilities.eliminarCerosLeft(datos[10]),  //colCelBenef

//                            System.out.println(Respuesta.split(",")[4]);
                            if (GiroNalInitController.isGirador.get()) {
                                ColCelGir = StringUtilities.eliminarCerosLeft(datos[10]);
                                ColCelBen = StringUtilities.eliminarCerosLeft(datos[11]);
                            } else {
                                if (Respuesta.split(",")[4].equals("1")) {
                                    ColCelGir = StringUtilities.eliminarCerosLeft(datos[11]);
                                    ColCelBen = StringUtilities.eliminarCerosLeft(datos[10]);
                                } else {
                                    ColCelGir = StringUtilities.eliminarCerosLeft(datos[10]);
                                    ColCelBen = StringUtilities.eliminarCerosLeft(datos[11]);
                                }
                            }

                            tablaInfoGnralGirosnal giros = new tablaInfoGnralGirosnal(
                                    datos[15],
                                    fechaPago,
                                    GiroNalInitController.isGirador.get() ? datosCliente.getId_cliente() : StringUtilities.eliminarCerosLeft(datos[3]), //colDocGirador
                                    GiroNalInitController.isGirador.get() ? nombreCliente : datos[4], //colNomGirador
                                    ColCelGir,
                                    GiroNalInitController.isGirador.get() ? StringUtilities.eliminarCerosLeft(datos[3]) : datosCliente.getId_cliente(), //colDocBenef
                                    GiroNalInitController.isGirador.get() ? datos[4] : nombreCliente, //colNomBenef
                                    ColCelBen,
                                    canalUltimoEstado,
                                    fechaPago,
                                    "$ " + formatonum.format(Double.parseDouble(datos[7].substring(0, datos[7].length() - 2))), //colValorGiro
                                    "$ " + formatonum.format(Double.parseDouble(datos[12].substring(0, datos[12].length() - 2))), //colValorComision
                                    valorTotal, //colValTotal
                                    "$ " + formatonum.format(Double.parseDouble(datos[13].substring(0, datos[13].length() - 2))), //colValIva
                                    mapeoEstadoGiro.mapeoEstadoGiro.get(datos[8]), //colEstadoGiro
                                    fechacancelado, //colFechaCancel
                                    fechapend, //colFechaPteCancel
                                    datos[0], //colContDiaGiro   transformada en RefGiro
                                    "Reenvio SMS",
                                    nombreCliente, //colnombreCliente
                                    celularCliente, //colCelcliente
                                    acomuladorAnual, //colAcumAnual
                                    acomuladorMes, //colAcumMes
                                    datos[0], //colConsecutivo
                                    StringUtilities.eliminarCerosLeft(datos[14]), //colPuntoservVenta
                                    datos[15], //colCanalVenta
                                    PuntoServicioPago, //colPuntoserPago
                                    false,
                                    AcumuladoAnual2, //colAcumAnual2
                                    AcumuladoMes2, //colAcumMes2
                                    motivoCancelacion);                                                                             //colMotCancel
                            registros.add(giros);
                        }
                    } else {
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCIÓN" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                failed();
                            }
                        });
                        throw new Exception("ERROR");
                    }
                    return registros;
                }
            };
        }
    }

    /**
     * HIPERVINCULO GESTION reenvio sms
     *
     * @param event
     */
    public class LinkTableCell<S, T> extends TableCell<S, T> {

        final Hyperlink vinculo = new Hyperlink();
        final Label label = new Label();
        private ObservableValue<T> ov;

        public LinkTableCell() {

            vinculo.setPrefWidth(310);
            vinculo.setAlignment(Pos.CENTER);
            vinculo.setStyle("-fx-text-fill: blue;");

            setGraphic(vinculo);

            final Bloom bloom = new Bloom();
            bloom.setThreshold(1.0);
            vinculo.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(final MouseEvent event) {
                    vinculo.setCursor(Cursor.HAND);
                    vinculo.setEffect(bloom);
                }
            });

            vinculo.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(final MouseEvent event) {
                    vinculo.setCursor(Cursor.DEFAULT);
                    vinculo.setEffect(null);
                }
            });

            vinculo.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(final MouseEvent event) {
                    vinculo.setCursor(Cursor.DEFAULT);
                    vinculo.setStyle("-fx-text-fill: purple;");
                }
            });

            vinculo.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    // accion hipervinculo
                    tablaInfoGnralGirosnal selectedItem = tablaDatos.getSelectionModel().getSelectedItem();
                    setSelectedGiro(selectedItem);
                    consultaReenviosms(event, selectedItem);
                }
            });
        }

        @Override
        public void updateItem(T t, boolean empty) {
            super.updateItem(t, empty);
            if (empty) {
                setGraphic(null);
            } else {
                // validaciones mostrar hipervinculo
                tablaInfoGnralGirosnal get = (tablaInfoGnralGirosnal) getTableView().getItems().get(getIndex());
                if ((get.colEstadoGiroProperty().getValue().trim().equals("DIS") || (get.colEstadoGiroProperty().getValue().trim().equals("PEN")))) {
                    if (get.colCanalVentaProperty().getValue().trim().equals("SUC")) {
                        setGraphic(label);
                    } else {
                        setGraphic(vinculo);
                    }
                } else {
                    setGraphic(label);
                }
                if (ov instanceof StringProperty) {
                    vinculo.textProperty().unbindBidirectional((StringProperty) ov);
                    label.textProperty().unbindBidirectional((StringProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof StringProperty) {
                    vinculo.textProperty().bindBidirectional((StringProperty) ov);
                    label.textProperty().bindBidirectional((StringProperty) ov);
                }
            }
        }
    }

    public void consultaReenviosms(final ActionEvent event, final tablaInfoGnralGirosnal selectedItem) {

        continuarDetalle().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        continuarDetalle().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo reenviosms" + "##" + gestorDoc.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuarDetalle().isRunning()) {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuarDetalle().progressProperty());
            continuarDetalle().reset();
            continuarDetalle().start();
        } else {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuarDetalle().progressProperty());
            continuarDetalle().start();
        }
    }

    public Service<Void> continuarDetalle() {
        servicesSms = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramaReenviosms = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final tablaInfoGnralGirosnal selectedGiro1 = getSelectedGiro();

//                        if (selectedGiro1.colEstadoGiroProperty().getValue().equals("PEN")) {
//                            //tramaReenviosms.append("850,070,");
//                            tramaReenviosms.append("850,083,");
//                            tramaReenviosms.append(datosCliente.getRefid()); //CONNID
//                            tramaReenviosms.append(",");
//                            tramaReenviosms.append(AtlasConstantes.COD_CANCELACION_GIRO);//CODTRX
//                            tramaReenviosms.append(",");
//                            tramaReenviosms.append(datosCliente.getId_cliente());//CEDULA
//                            tramaReenviosms.append(",");
//                            tramaReenviosms.append(selectedGiro1.colConsecutivoProperty().getValue());//TIPOPERSONA
//                            tramaReenviosms.append(",");
//                            if (GiroNalInitController.isGirador.get()) {
//                                tramaReenviosms.append(selectedGiro1.colCelclienteProperty().getValue());//CELULAR GIRADOR
//                                tramaReenviosms.append(",");
//                                tramaReenviosms.append(selectedGiro1.colCelBenefProperty().getValue());
//                                tramaReenviosms.append(",");
//                            } else if (GiroNalInitController.isBeneficiario.get()) {
//                                tramaReenviosms.append(selectedGiro1.colCelGiradorProperty().getValue());//CELULAR GIRADOR
//                                tramaReenviosms.append(",");
//                                tramaReenviosms.append(selectedGiro1.colCelclienteProperty().getValue());
//                                tramaReenviosms.append(",");
//                            }
//                            tramaReenviosms.append(datosCliente.getContraseña());
//                            tramaReenviosms.append(",~");
//                        } else {
                        if ((selectedGiro1.colEstadoGiroProperty().getValue().trim().equals("DIS")) || (selectedGiro1.colEstadoGiroProperty().getValue().trim().equals("PEN"))) {
                            //850,070,connid,codTransaccion,identificacion,concecutivoRef2,ceLulaGirador,CelularBeneficiario,claveHardware,~
                            //tramaReenviosms.append("850,071,");
                            tramaReenviosms.append("850,084,");
                            tramaReenviosms.append(datosCliente.getRefid()); //CONNID
                            tramaReenviosms.append(",");
                            tramaReenviosms.append(AtlasConstantes.COD_REENVIO_GIRO_SMS);//CODTRX
                            tramaReenviosms.append(",");
                            tramaReenviosms.append(datosCliente.getId_cliente());//CEDULA
                            tramaReenviosms.append(",");
                            tramaReenviosms.append(selectedGiro1.colConsecutivoProperty().getValue());//TIPOPERSONA
                            tramaReenviosms.append(",");
                            tramaReenviosms.append(datosCliente.getContraseña());
                            tramaReenviosms.append(",");
                            tramaReenviosms.append(datosCliente.getTokenOauth());
                            tramaReenviosms.append(",~");
                        }

//                        System.out.println(selectedGiro1.colEstadoGiroProperty().getValue());
//                        System.out.println("TRAMA reenviosms/cancelacion :" + tramaReenviosms);
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Reenvio SMS Giros Nacionales = " + "##" + gestorDoc.obtenerHoraActual());

                            //  Respuesta = "850,070,000,TRANSACCION EXITOSA,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaReenviosms.toString());
                            //  System.out.println("RESPUESTA DETALLE CONV:" + Respuesta);
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Reenvio SMS Giros Nacionales = " + "##" + gestorDoc.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaReenviosms.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                });
                            }
                        }

                        if ("000".equals(Respuesta.split(",")[2])) {
                            //850,067,000,TRANSACCION EXITOSA,frecuenciaPago,NumeroDias,NumeroSemana,DiaSemana,DiaMes,DiasReintento,FechaInicio,FechaFin,ValorFactura,FechaSiguientePago,Estado,Canal,CodigoBanco,NombreBanco,
                            //NumeroCuentaPagador,TipoCuentaPagador,referencia1,referencia2,referencia3,indicadorReferenciaFija,~
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "TRANSACCION EXITOSA" : mensaje, coderror, ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.DEFAULT);
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);
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
        return servicesSms;
    }

    private void analisisFecha() {
        if (fechaini.getSelectedDate() != null && fechafin.getSelectedDate() != null) {
            rangoFecha(fechaini.getSelectedDate(), fechafin.getSelectedDate());
        } else {
//            System.out.println("Entro aca");
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
        final int diferenciaDias = gestorDoc.CalcularDifFechas(calendarFiaux, calendarFfaux);
//        System.out.println("diferencia dias " + diferenciaDias);
        if ((diferenciaDias <= 90) && (diferenciaDias != -1)) {
            buscarFechas.setDisable(false);
        } else {
            buscarFechas.setDisable(true);
        }
        return true;
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    public static Calendar getFechalimite() {
        return fechalimite;
    }

    public static void setFechalimite(Calendar fechalimite) {
        PuntosColAjustePuntosController.fechalimite = fechalimite;
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
}
