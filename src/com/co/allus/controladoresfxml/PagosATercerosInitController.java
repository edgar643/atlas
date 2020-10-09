/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.modelo.AtlasAcceso;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagosaterceros.BusquedaConvPagosT;
import com.co.allus.modelo.pagosaterceros.BusquedaDescPagosT;
import com.co.allus.modelo.pagosaterceros.BusquedaNConvPagosT;
import com.co.allus.modelo.pagosaterceros.BusquedaRefPagosT;
import com.co.allus.modelo.pagosterceros.DatosDetallePagos;
import com.co.allus.modelo.pagosterceros.DatosPagosTerceros;
import com.co.allus.modelo.pagosterceros.InfoTablaPagosTerceros;
import com.co.allus.modelo.pagosterceros.infoTablaMasFacturas;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
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
import javafx.scene.control.CheckBox;
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
public class PagosATercerosInitController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableColumn colCanal;
    @FXML
    private TableColumn colCodigoConvenio;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colFechaHorainscripcion;
    @FXML
    private TableColumn colFechavencimiento;
    @FXML
    private TableColumn colNombreConvenio;
    @FXML
    private TableColumn<InfoTablaPagosTerceros, Boolean> colPagar;
    @FXML
    private TableColumn colReferencia1;
    @FXML
    private TableColumn colReferencia2;
    @FXML
    private TableColumn colReferencia3;
    @FXML
    private TableColumn colValorPagar;
    @FXML
    private Button masRegistros;
    @FXML
    private Button pagarOP;
    @FXML
    private Button regresarOp;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TableView<InfoTablaPagosTerceros> tabla_datos;
    @FXML
    private RestrictiveTextField txtCodigoConvenio;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtNombreConvenio;
    @FXML
    private RestrictiveTextField txtReferenciaFija;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    public static ObservableList<InfoTablaPagosTerceros> pagos = FXCollections.observableArrayList();
    private transient Service<Void> serviceDetalleFact;
    private transient Service<Void> serviceMasFact;
    private transient Service<Void> serviceFacturas = Buscar_Op();
    int currentpageindex = 0;
    private static AtomicInteger numpagina = new AtomicInteger(0);
    public static AtomicInteger acumREg = new AtomicInteger(0);
    private static AtomicInteger numpagina2 = new AtomicInteger(0);
    public static AtomicInteger acumREg2 = new AtomicInteger(0);
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private static AtomicBoolean cancelarTareasPagosTerc = new AtomicBoolean();
    private Service<ObservableList<InfoTablaPagosTerceros>> task = new PagosATercerosInitController.GetconveniosTask();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFecha2 = new SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaon = new SimpleDateFormat("yyyy/MM/dd ", Locale.getDefault());
    private transient final SimpleDateFormat formatoFechaven = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    public static String indicadorRegistros = "N";
    public static String tracePaginacion = "";
    public static String registrospendientes = "0";
    public static String registrosactivos = "0";
    public static String registrosrechazados = "0";

    /**
     * variables mas facturas
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert btnBuscar != null : "fx:id=\"btnBuscar\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colCanal != null : "fx:id=\"colCanal\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colCodigoConvenio != null : "fx:id=\"colCodigoConvenio\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colDescripcion != null : "fx:id=\"colDescripcion\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colEstado != null : "fx:id=\"colEstado\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colFechaHorainscripcion != null : "fx:id=\"colFechaHorainscripcion\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colFechavencimiento != null : "fx:id=\"colFechavencimiento\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colNombreConvenio != null : "fx:id=\"colNombreConvenio\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colPagar != null : "fx:id=\"colPagar\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colReferencia1 != null : "fx:id=\"colReferencia1\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colReferencia2 != null : "fx:id=\"colReferencia2\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colReferencia3 != null : "fx:id=\"colReferencia3\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert colValorPagar != null : "fx:id=\"colValorPagar\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert masRegistros != null : "fx:id=\"masRegistros\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert pagarOP != null : "fx:id=\"pagarOP\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert txtCodigoConvenio != null : "fx:id=\"txtCodigoConvenio\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert txtDescripcion != null : "fx:id=\"txtDescripcion\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert txtNombreConvenio != null : "fx:id=\"txtNombreConvenio\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert txtReferenciaFija != null : "fx:id=\"txtReferenciaFija\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";
        assert regresarOp != null : "fx:id=\"regresarOp\" was not injected: check your FXML file 'PagosATercerosInit.fxml'.";

        this.resources = rb;
        this.location = url;

        progreso.setVisible(false);
        pagarOP.setDisable(true);
        masRegistros.setDisable(true);
        // btnBuscar.setDisable(true);

        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_datos.setEditable(true);
        tabla_datos.setPlaceholder(new Label("No se ha realizado ninguna consulta"));

        colPagar.setCellValueFactory(new PropertyValueFactory("seleccion"));
        colCodigoConvenio.setCellValueFactory(new PropertyValueFactory("colCodConvenio"));
        colNombreConvenio.setCellValueFactory(new PropertyValueFactory("colNombreConvenio"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("colDescripcion"));
        colReferencia1.setCellValueFactory(new PropertyValueFactory("colNomRef1"));
        colReferencia2.setCellValueFactory(new PropertyValueFactory("colNomRef2"));
        colReferencia3.setCellValueFactory(new PropertyValueFactory("colNomRef3"));
        colEstado.setCellValueFactory(new PropertyValueFactory("colEstado"));
        // colValorPagar.setCellValueFactory(new PropertyValueFactory("colValorPagar"));
        colValorPagar.setCellValueFactory(new PropertyValueFactory("colValorPagOri"));
        colFechaHorainscripcion.setCellValueFactory(new PropertyValueFactory("colFechaInscripcion"));
        colFechavencimiento.setCellValueFactory(new PropertyValueFactory("colFechaVencimiento"));
        colCanal.setCellValueFactory(new PropertyValueFactory("colCanal"));

        colPagar.setCellFactory(new Callback<TableColumn<InfoTablaPagosTerceros, Boolean>, TableCell<InfoTablaPagosTerceros, Boolean>>() {
            @Override
            public TableCell<InfoTablaPagosTerceros, Boolean> call(TableColumn<InfoTablaPagosTerceros, Boolean> p) {
                return new CheckBoxTableCell<InfoTablaPagosTerceros, Boolean>();
            }
        });

        colNombreConvenio.setCellFactory(new Callback<TableColumn<InfoTablaPagosTerceros, String>, TableCell<InfoTablaPagosTerceros, String>>() {
            @Override
            public TableCell<InfoTablaPagosTerceros, String> call(TableColumn<InfoTablaPagosTerceros, String> p) {
                return new LinkTableCell<InfoTablaPagosTerceros, String>();
            }
        });

        //numpagina.set(0);
        //acumREg.set(0);
        cancelartarea.set(false);

    }

    @FXML
    void refkPressed(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(txtReferenciaFija, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            txtReferenciaFija.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void refkTyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    BusquedaRef(txtReferenciaFija.getText() + event.getCharacter());

                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (txtReferenciaFija.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {

                            BusquedaRef("");

                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {

                            BusquedaNomConv(txtReferenciaFija.getText());

                        }
                    }

                }

            }

        }
    }

    @FXML
    void nomconvkpressed(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(txtNombreConvenio, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            txtNombreConvenio.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void nomconvktyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

//            if (isnumber(event.getCharacter())) {
//                event.consume();
//                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
//                synchronized (this) {
//
//                    BusquedaNomConv(txtNombreConvenio.getText() + event.getCharacter());
//
//                }
//
//            } else {
            if (event.getCharacter().trim().isEmpty()) {
                if (txtNombreConvenio.getText().isEmpty()) {
                    // System.out.println("cargo todos de nuevo");
                    synchronized (this) {

                        BusquedaNomConv("");

                    }

                } else {

                    // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                    synchronized (this) {

                        BusquedaNomConv(txtNombreConvenio.getText());

                    }
                }

            } else {
                synchronized (this) {

                    BusquedaNomConv(txtNombreConvenio.getText() + event.getCharacter());

                }
            }

            // }
        }
    }

    @FXML
    void desckPressed(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(txtDescripcion, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            txtDescripcion.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void desckTyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

//            if (isnumber(event.getCharacter())) {
//                event.consume();
//                System.out.println(" a buscar :" + txtDescripcion.getText() + event.getCharacter());
//                synchronized (this) {
//
//                    BusquedaDesc(txtDescripcion.getText() + event.getCharacter());
//
//                }
//
//            } else {
            if (event.getCharacter().trim().isEmpty()) {
                if (txtDescripcion.getText().isEmpty()) {
                    synchronized (this) {

                        BusquedaDesc("");

                    }

                } else {

                    synchronized (this) {

                        BusquedaDesc(txtDescripcion.getText() + event.getCharacter());

                    }
                }

            } else {
                synchronized (this) {

                    BusquedaDesc(txtDescripcion.getText() + event.getCharacter());

                }
            }

            //}
        }
    }

    @FXML
    void codConvkPress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(txtCodigoConvenio, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            txtCodigoConvenio.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void codConvktyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    BusquedaConvenio(txtCodigoConvenio.getText() + event.getCharacter());

                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (txtCodigoConvenio.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {

                            BusquedaConvenio("");

                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {

                            BusquedaConvenio(txtCodigoConvenio.getText());

                        }
                    }

                }

            }

        }
    }

    @FXML
    void regresarOp(ActionEvent event) {
        cancelartarea.set(true);
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

        arbol_pagos.getSelectionModel().clearSelection();
        MarcoPrincipalController.newConsultaPagosT = true;
        try {
            pagos.clear();
        } catch (Exception e) {
            docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());

        }

        try {
            pane.getChildren().remove(3);
        } catch (Exception e) {
            docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }
        Atlas.vista.show();
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 9;
    }

    public static String getRegistrospendientes() {
        return registrospendientes;
    }

    public static void setRegistrospendientes(String registrospendientes) {
        PagosATercerosInitController.registrospendientes = registrospendientes;
    }

    public static String getRegistrosactivos() {
        return registrosactivos;
    }

    public static void setRegistrosactivos(String registrosactivos) {
        PagosATercerosInitController.registrosactivos = registrosactivos;
    }

    public static String getRegistrosrechazados() {
        return registrosrechazados;
    }

    public static void setRegistrosrechazados(String registrosrechazados) {
        PagosATercerosInitController.registrosrechazados = registrosrechazados;
    }

    public String getIndicadorRegistros() {
        return PagosATercerosInitController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        PagosATercerosInitController.indicadorRegistros = indicadorRegistros;
    }

    public static String getTracePaginacion() {
        return tracePaginacion;
    }

    public static void setTracePaginacion(String tracePaginacion) {
        PagosATercerosInitController.tracePaginacion = tracePaginacion;
    }

    /**
     * BUSCAR POR ID
     */
    private void BusquedaConvenio(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<InfoTablaPagosTerceros>> TaskTablaId = new BusquedaConvPagosT(id);

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
                            final ObservableList<InfoTablaPagosTerceros> TablaID = TaskTablaId.getValue();

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
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaID.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaID.size())));
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
                                    docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(111);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                    
                } catch (Exception ex) {

                    Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    /**
     * BUSCAR POR ID
     */
    private void BusquedaNomConv(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<InfoTablaPagosTerceros>> TaskTablaId = new BusquedaNConvPagosT(id);

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
                            final ObservableList<InfoTablaPagosTerceros> TablaID = TaskTablaId.getValue();

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
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaID.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaID.size())));
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
                                    docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(111);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    /**
     * BUSCAR POR ID
     */
    private void BusquedaDesc(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<InfoTablaPagosTerceros>> TaskTablaId = new BusquedaDescPagosT(id);

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
                            final ObservableList<InfoTablaPagosTerceros> TablaID = TaskTablaId.getValue();


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
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaID.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaID.size())));
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
                                    docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(111);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    /**
     * BUSCAR POR ID
     */
    private void BusquedaRef(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<InfoTablaPagosTerceros>> TaskTablaId = new BusquedaRefPagosT(id);

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
                            final ObservableList<InfoTablaPagosTerceros> TablaID = TaskTablaId.getValue();


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
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaID.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaID.size())));
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
                                    docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(111);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                            }

                        }
                    });

                    TaskTablaId.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                    TaskTablaId.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(TokenEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public void mostrarDatosPagosTerceros(final int origen) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosATercerosInit.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();

                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button masRegistros = (Button) root.lookup("#masRegistros");
                    final Button pagarOP = (Button) root.lookup("#pagarOP");
                    final Button btnBuscar = (Button) root.lookup("#btnBuscar");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<InfoTablaPagosTerceros> tabla_datos = (TableView<InfoTablaPagosTerceros>) root.lookup("#tabla_datos");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");

                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    if ("S".equals(getIndicadorRegistros())) {
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

                    pagarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            pagarOP.setCursor(Cursor.HAND);
                            pagarOP.setEffect(shadow);
                        }
                    });

                    pagarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(final MouseEvent event) {
                            pagarOP.setCursor(Cursor.DEFAULT);
                            pagarOP.setEffect(null);

                        }
                    });

                    if (origen == 1) {

                        btnBuscar.fire();
                    }

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
                        arbol_servadd.setDisable(true);
                    }

                    label_menu.setVisible(false);

                } catch (Exception e) {

                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });
    }

    @FXML
    void btnBuscar(ActionEvent event) {

        if (!getIndicadorRegistros().equals("S")) {

            acumREg.set(0);
            numpagina.set(0);
            if (serviceFacturas.isRunning()) {
                btnBuscar.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceFacturas.progressProperty());
                serviceFacturas.reset();
                serviceFacturas.start();

            } else {
                btnBuscar.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceFacturas.progressProperty());
                serviceFacturas.reset();
                serviceFacturas.start();
            }

            serviceFacturas.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Pagos Terceros" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                    PagosATercerosInitController.cancelarTareasPagosTerc.set(false);
                    btnBuscar.setDisable(false);
                    paginacion();

                }
            });

            serviceFacturas.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Pagos Terceros" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                    btnBuscar.setDisable(false);
                }
            });

            serviceFacturas.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    Logger.getGlobal().log(Level.SEVERE, t.getSource().getException().toString()); 
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                    btnBuscar.setDisable(false);
                }
            });
//            }else
//            {
//             return;
//            }
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new ModalMensajes("Hay registros pendientes de consultar por el canal", "Advertencia", ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.DEFAULT);
                }
            });

        }
    }

    public Service<Void> Buscar_Op() {
        serviceFacturas = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        final Cliente datosCliente = Cliente.getCliente();
                        String Respuesta = new String();
                        final StringBuilder tramaPagosTerceros = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();

                        //850,066,connid,codigoTransaccion,identificacion,tracepaginacion,
                        //TipoPersona(P=persona , E=empresa),cantReg,cantidadRegActivos,CantidadRegPendientes,
                        //CantRegRechazados,codigoConveniio,referenciaFija,~
                        //850,066,connid,0255,,5037222286,P,0,0,0,0,63066,302,3DE64DDBDB51A79F,~  --inicial
                        final AtlasAcceso Atlasacceso = new AtlasAcceso();
                        String rnv = "";
                        if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                            rnv = "E";
                        } else {
                            rnv = "P";
                        }

                        tramaPagosTerceros.append("850,066,");
                        tramaPagosTerceros.append(datosCliente.getRefid()); //CONNID
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append(AtlasConstantes.COD_PAGOS_TERCEROS);//COD TRX
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append("S".equalsIgnoreCase(getIndicadorRegistros()) ? getTracePaginacion() : "");//TRACE PAGINACION
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append(datosCliente.getId_cliente());//ID
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append(rnv);//INDICADOR(empresas-personas)
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append(acumREg.addAndGet(numpagina.get()));//CANTIDAD REGISTROS
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append("0".equalsIgnoreCase(getRegistrosactivos()) ? getRegistrosactivos() : "0");//CANTIDAD REGISTROS ACTIVOS
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append("0".equalsIgnoreCase(getRegistrospendientes()) ? getRegistrospendientes() : "0");//CANTIDAD REGISTROS PENDIENTES
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append("0".equalsIgnoreCase(getRegistrosrechazados()) ? getRegistrosrechazados() : "0");//CANTIDAD REGISTROS RECHAZADOS
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append("");//CONVENIO
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append("");//REFERENCIA FIJA
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append(datosCliente.getContraseña());//CLAVE HW
                        tramaPagosTerceros.append(",");
                        tramaPagosTerceros.append(datosCliente.getTokenOauth());//CLAVE HW
                        tramaPagosTerceros.append(",~");

//                        System.out.println("Trama consulta pagos es: " + tramaPagosTerceros);
                        if (MarcoPrincipalController.newConsultaPagosT) {
                            try {
                                pagos.clear();
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ ConsultaPagosTerceros = " + "##" + docs.obtenerHoraActual());

//                                Respuesta = "850,066,000,TRANSACCION EXITOSA,N,084804643335,000026,000000,000000,"
//                                        + "63033          %RECAUDADOR-REF FIJA 1 (FINACLE 1 1)%SUCURSAL VIRTUAL              %6756755                            %*DIRECCIÓN#1                                      %                                   %                                                  %                                   %                                                  %8765400%Alejo 5 inscripción                                                             %A%S%1%6756755                            %0       %S%40618829000     %1%N%N%N%I%20180409144542%120550%S;"
//                                        + "63056          %RECAUDADOR-REF FIJA 1 (FINACLE 1 2)%SUCURSAL VIRTUAL              %876543                             %*DIRECCIÓN#2                                      %                                   %REF2                                              %                                   %REF3                                              %2345600%alejo 21 Inscripción                                                            %I%N%1%876543                             %0       %N%40678829003     %7%N%N%N%N%20180409151530%120550%S;"
//                                        + "63056          %RECAUDADOR-REF FIJA 1 (FINACLE 1 2)%SUCURSAL VIRTUAL              %765445                             %*DIRECCIÓN#2                                      %                                   %REF2                                              %                                   %REF3                                              %3456034%Alejo 18 Inscripción                                                            %I%S%1%765445                             %0       %N%40678829003     %7%N%N%N%E%20180409114750%120550%S;63056          %RECAUDADOR-REF FIJA 1 (FINACLE 1 2)%SUCURSAL VIRTUAL              %6756896                            %*DIRECCIÓN#2                                      %                                   %REF2                                              %                                   %REF3                                              %2345600%Alejo 3 Inscripción                                                             %I%S%1%6756896                            %0       %N%40678829003     %7%N%N%N%N%20180409110341%120550%N;63088          %RECÁUDADOR- FNCLE #2.2-SN.REF3%SUCURSAL VIRTUAL              %                                   %*NÚMERO  APTO.                                    %3457654                            %*NIT                                              %                                   %                                                  %456765400%Alejo 7 inscripción                                                             %I%S%2%3457654                            %0       %N%40678833003     %7%N%N%N%N%20180409144542%120550%S;63088          %RECÁUDADOR- FNCLE #2.2-SN.REF3%SUCURSAL VIRTUAL              %                                   %*NÚMERO  APTO.                                    %0945432                            %*NIT                                              %                                   %                                                  %355600%Alejo 17 Inscripción                                                            %I%S%2%0945432                            %0       %N%40678833003     %7%N%N%N%N%20180409144542%120550%N;63088          %RECÁUDADOR- FNCLE #2.2-SN.REF3%SUCURSAL VIRTUAL              %                                   %*NÚMERO  APTO.                                    %3456765                            %*NIT                                              %                                   %                                                  %6787600%Alejo 12 Inscripción                                                            %I%S%2%3456765                            %0       %N%40678833003     %7%N%N%N%N%20180409144542%120550%S;63092          %CRAZY -TÓWN &(COMPAÑÍA)#1.1.          ADÑ%SUCURSAL VIRTUAL              %                                   %*NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR%456784                             %*NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR%                                   %*NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR%567500%Alejo 22 Inscripción                                                            %I%S%2%456784                             %0       %N%40678830003     %7%N%N%N%N%20180409151809%120550%N;63092          %CRAZY -TÓWN &(COMPAÑÍA)#1.1.          ADÑ%SUCURSAL VIRTUAL              %                                   %*NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR%56543                              %*NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR%                                   %*NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR%3453301%Alejo 19 Inscripción                                                            %I%S%2%56543                              %0       %N%40678830003     %7%N%N%N%N%20180409150552%120550%S;63092          %CRAZY -TÓWN &(COMPAÑÍA)#1.1.          ADÑ%SUCURSAL VIRTUAL              %                                   %*NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR%567554                             %*NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR%                                   %*NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR%7654300%Alejo 16 Inscripción                                                            %I%S%2%567554                             %0       %N%40678830003     %7%N%N%N%N%20180409114303%120550%N;63092          %CRAZY -TÓWN &(COMPAÑÍA)#1.1.          ADÑ%SUCURSAL VIRTUAL              %                                   %*NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR%9456433                            %*NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR%                                   %*NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR%4345400%alejandro 6 inscripción                                                         %I%S%2%9456433                            %0       %N%40678830003     %7%N%N%N%N%20180409111304%120550%S;63099          %CRAZY -TÓWN &(COMPAÑÍA)#1.1.          AÓ%SUCURSAL VIRTUAL              %                                   %*TORRE_1 2                                        %                                   %*TORRE SUR&-OCCIDENTE#                            %456446                             %*NÚMÉRO- APTO                                     %4543200%Alejo 20 Inscripción                                                            %I%S%3%456446                             %0       %N%40618834000     %1%N%N%N%N%20180409150936%120550%S;63099          %CRAZY -TÓWN &(COMPAÑÍA)#1.1.          AÓ%SUCURSAL VIRTUAL              %                                   %*TORRE_1 2                                        %                                   %*TORRE SUR&-OCCIDENTE#                            %6786567                            %*NÚMÉRO- APTO                                     %5644300%Alejo 10 inscripción                                                            %I%S%3%6786567                            %0       %N%40618834000     %1%N%N%N%N%20180409112658%120550%N;63121          %CAR ESP #_&&_ÁÁÉÉÍÍÓÓÚÚÑÑ%SUCURSAL VIRTUAL              %                                   %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%                                   %                                                  %1234545                            %*NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR%7654500%Alejo 9 inscripción                                                             %I%S%3%1234545                            %0       %N%40678835003     %7%N%N%N%N%20180409112415%120550%N;63138          %RECÁUDADOR- FINACLE #2.1%SUCURSAL VIRTUAL              %7656754                            %*NÚMERO  APTO.                                    %                                   %*NIT                                              %                                   %TORRE SUR&-OCCIDENTE#                             %6567800%Alejo 2 inscripción                                                             %I%S%1%7656754                            %0       %N%40678910008     %7%N%N%N%N%20180409105757%120550%N;63143          %RÉCAUDADOR &FINACLE-3.3 SNREF2%SUCURSAL VIRTUAL              %234537                             %*TORRE_1 2                                        %                                   %*REF2                                             %                                   %*NÚMÉRO- APTO                                     %1556500%Alejo 24 inscrip                                                                %I%S%1%234537                             %0       %N%40678835004     %7%N%N%N%N%20180409152204%120550%S;63143          %RÉCAUDADOR &FINACLE-3.3 SNREF2%SUCURSAL VIRTUAL              %6576798                            %*TORRE_1 2                                        %                                   %*REF2                                             %                                   %*NÚMÉRO- APTO                                     %5645400%Alejo 15 Inscrip                                                                %I%S%1%6576798                            %0       %N%40678835004     %7%N%N%N%N%20180409114030%120550%N;63143          %RÉCAUDADOR &FINACLE-3.3 SNREF2%SUCURSAL VIRTUAL              %8238798                            %*TORRE_1 2                                        %                                   %*REF2                                             %                                   %*NÚMÉRO- APTO                                     %5675700%Alejo 1 Inscrita&                                                               %I%S%1%8238798                            %0       %N%40678835004     %7%N%N%N%N%20180409105029%120550%N;63151          %CRAZY -TÓWN &(COMPAÑÍA)#2 1.%SUCURSAL VIRTUAL              %                                   %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%98762                              %*NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%6543300%Alejo 23 Inscripción                                                            %I%S%2%98762                              %0       %N%40618832000     %1%N%N%N%N%20180409152020%120550%N;63151          %CRAZY -TÓWN &(COMPAÑÍA)#2 1.%SUCURSAL VIRTUAL              %                                   %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%4534544                            %*NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%7654300%Alejo 14 Inscripción                                                            %I%S%2%4534544                            %0       %N%40618832000     %1%N%N%N%N%20180409113845%120550%N;63151          %CRAZY -TÓWN &(COMPAÑÍA)#2 1.%SUCURSAL VIRTUAL              %                                   %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%3467865                            %*NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%3466500%Alejo 8 Inscripción                                                             %I%S%2%3467865                            %0       %N%40618832000     %1%N%N%N%N%20180409111717%120550%S;63154          %RÉCAUDADOR &FINACLE-3.4 SNREF2%SUCURSAL VIRTUAL              %234566                             %*TORRE_1 2                                        %                                   %                                                  %                                   %*NÚMÉRO- APTO                                     %456700%alejandro 25 inscripción                                                        %I%S%1%234566                             %0       %N%40618917000     %1%N%N%N%N%20180409154219%120550%S;63154          %RÉCAUDADOR &FINACLE-3.4 SNREF2%SUCURSAL VIRTUAL              %5676580                            %*TORRE_1 2                                        %                                   %                                                  %                                   %*NÚMÉRO- APTO                                     %3456500%Alejo 13 inscripción                                                            %I%S%1%5676580                            %0       %N%40618917000     %1%N%N%N%N%20180409113649%120550%N;63154          %RÉCAUDADOR &FINACLE-3.4 SNREF2%SUCURSAL VIRTUAL              %5676589                            %*TORRE_1 2                                        %                                   %                                                  %                                   %*NÚMÉRO- APTO                                     %345500%Alejo 11 Inscripción&                                                           %I%S%1%5676589                            %0       %N%40618917000     %1%N%N%N%N%20180409112836%120550%S;63154          %RÉCAUDADOR &FINACLE-3.4 SNREF2%SUCURSAL VIRTUAL              %4521789                            %*TORRE_1 2                                        %                                   %                                                  %                                   %*NÚMÉRO- APTO                                     %4675400%Alejo 4 Inscripción                                                             %I%S%1%4521789                            %0       %N%40618917000     %1%N%N%N%N%20180409110556%120550%S;63154          %RÉCAUDADOR &FINACLE-3.4 SNREF2%APP PERSONAS                  %33333                              %*TORRE_1 2                                        %                                   %                                                  %2232                               %*NÚMÉRO- APTO                                     %76788889%Bug 23301                                                                       %A%S%1%33333                              %0       %N%40618917000     %1%N%N%N%N%20180220164517,~";
//
                                // Thread.sleep(3000);
                                // Respuesta = "850,066,000,TRANSACCION EXITOSA,N,102501708055,000065,000005,000002,65442          %CONVENIO ADHOC WMB REF1%APP PERSONAS                  %9439587                            %*FACTURA                                          %                                   %*CEDULA                                           %                                   %NEGOCIO                                           %00%PagosAdhocCero                                                                  %I%S%1%9439587                            %0       %N%40678961008     %7%N%N%S%N%20190424000000%.00             %N;65442          %CONVENIO ADHOC WMB REF1%APP PERSONAS                  %80364386                           %*FACTURA                                          %                                   %*CEDULA                                           %                                   %NEGOCIO                                           %497500%PagosAdhocSuperior                                                              %I%S%1%80364386                           %0       %N%40678961008     %7%N%N%S%N%20190424000000%4975.00         %N;65442          %CONVENIO ADHOC WMB REF1%APP PERSONAS                  %87357355                           %*FACTURA                                          %                                   %*CEDULA                                           %                                   %NEGOCIO                                           %36938600%PagosAdhocParcial                                                               %I%S%1%87357355                           %0       %N%40678961008     %7%N%N%S%N%20190424000000%369386.00       %N;65442          %CONVENIO ADHOC WMB REF1%APP PERSONAS                  %6758493012                         %*FACTURA                                          %                                   %*CEDULA                                           %                                   %NEGOCIO                                           %00%PagosAdhocKonecta                                                               %I%S%1%6758493012                         %0       %N%40678961008     %7%N%N%S%N%20190415000000%.00             %N;65442          %CONVENIO ADHOC WMB REF1%APP PERSONAS                  %2143658709                         %*FACTURA                                          %                                   %*CEDULA                                           %                                   %NEGOCIO                                           %12300000%PagosAdhocKonecta                                                               %I%S%1%2143658709                         %0       %N%40678961008     %7%N%N%S%N%20190415000000%123000.00       %N;65442          %CONVENIO ADHOC WMB REF1%APP PERSONAS                  %2910564738                         %*FACTURA                                          %                                   %*CEDULA                                           %                                   %NEGOCIO                                           %45600000%PagosAdhocKonecta                                                               %I%S%1%2910564738                         %0       %N%40678961008     %7%N%N%S%N%20190415000000%456000.00       %N;65442          %CONVENIO ADHOC WMB REF1%APP PERSONAS                  %6574839201                         %*FACTURA                                          %                                   %*CEDULA                                           %                                   %NEGOCIO                                           %56000000%PagosAdhocKonecta                                                               %I%S%1%6574839201                         %0       %N%40678961008     %7%N%N%S%N%20190415000000%560000.00       %N;65442          %CONVENIO ADHOC WMB REF1%APP PERSONAS                  %1029384756                         %*FACTURA                                          %                                   %*CEDULA                                           %                                   %NEGOCIO                                           %23400000%PagosAdhocKOnecta                                                               %I%S%1%1029384756                         %0       %N%40678961008     %7%N%N%S%N%20190415000000%234000.00       %N;65443          %CONVENIO ADHOC TELEFONICA REF1%APP PERSONAS                  %123456                             %*CEDULA                                           %                                   %*NEGOCIO                                          %                                   %*FACTURA                                          %756756%PruebaDelay1                                                                    %I%S%1%123456                             %0       %N%40678961008     %7%N%N%N%N%20190424000000%7567.56         %N;65443          %CONVENIO ADHOC TELEFONICA REF1%APP PERSONAS                  %4587346                            %*CEDULA                                           %                                   %*NEGOCIO                                          %                                   %*FACTURA                                          %53850300%FacturaAdhocDelayPre                                                            %I%S%1%4587346                            %0       %N%40678961008     %7%N%N%N%N%20190424000000%538503.00       %N;65447          %CONVENIO ADHOC TELEFONICA REF2%APP PERSONAS                  %                                   %*CEDULA                                           %8529406                            %*FACTURA                                          %                                   %NEGOCIO                                           %49827468%FacturaAdhocReF2                                                                %I%S%2%8529406                            %0       %N%40678963014     %7%N%N%N%N%20190422000000%498274.68       %N;65451          %CONVENIO ADHOC TELEFONICA REF3%APP PERSONAS                  %                                   %*CEDULA                                           %                                   %*FACTURA                                          %7487993                            %*NEGOCIO                                          %45734895%FacturaAdhocReF3OK                                                              %I%S%3%7487993                            %0       %N%40618964004     %1%N%N%N%N%20190423000000%457348.95       %N;65451          %CONVENIO ADHOC TELEFONICA REF3%APP PERSONAS                  %                                   %*CEDULA                                           %                                   %*FACTURA                                          %5068045                            %*NEGOCIO                                          %400%FacturaAdhocReF3                                                                %I%S%3%5068045                            %0       %N%40618964004     %1%N%N%N%N%20190422000000%4.00            %N;65480          %REDEBAN_PRUEBA_FACTURANET_APP-WMB%APP PERSONAS                  %4116656428                         %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%FacturaRedeban3                                                                 %I%S%1%4116656428                         %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190423000000%.00             %N;65480          %REDEBAN_PRUEBA_FACTURANET_APP-WMB%APP PERSONAS                  %634779563                          %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%FacturaRedeban2                                                                 %I%S%1%634779563                          %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190423000000%.00             %N;65482          %REDEBAN_PRUEBA_FACTURANET_SVE%APP PERSONAS                  %123456                             %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%RedebanNoExiste                                                                 %I%S%1%123456                             %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190425000000%.00             %N;65482          %REDEBAN_PRUEBA_FACTURANET_SVE%APP PERSONAS                  %5333635656                         %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%FacturaRedeban8                                                                 %I%S%1%5333635656                         %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190423000000%.00             %N;65482          %REDEBAN_PRUEBA_FACTURANET_SVE%APP PERSONAS                  %5333635649                         %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%FacturaRedeban7                                                                 %I%S%1%5333635649                         %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190423000000%.00             %N;65482          %REDEBAN_PRUEBA_FACTURANET_SVE%APP PERSONAS                  %5333637082                         %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%FacturaRedeban6                                                                 %I%S%1%5333637082                         %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190423000000%.00             %N;65482          %REDEBAN_PRUEBA_FACTURANET_SVE%APP PERSONAS                  %5333637075                         %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%FacturaRedeban5                                                                 %I%S%1%5333637075                         %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190423000000%.00             %N;65482          %REDEBAN_PRUEBA_FACTURANET_SVE%APP PERSONAS                  %5333637068                         %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%FacturaRedeban4                                                                 %I%S%1%5333637068                         %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190423000000%.00             %N;65482          %REDEBAN_PRUEBA_FACTURANET_SVE%APP PERSONAS                  %5333479861                         %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%PruebaRedeban1                                                                  %I%S%1%5333479861                         %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190423000000%.00             %N;65483          %REDEBAN_PRUEBA_FACTURANET_ASESOR%APP PERSONAS                  %12051926920                        %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%RedebanPrueba1                                                                  %I%S%1%12051926920                        %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190423000000%.00             %N;65483          %REDEBAN_PRUEBA_FACTURANET_ASESOR%APP PERSONAS                  %12051853576                        %*IDENTIFICACION                                   %                                   %                                                  %                                   %                                                  %00%FacturaRedeban1                                                                 %I%S%1%12051853576                        %0       %S%BC000903COP4PAEM%0%N%N%N%R%20190422000000%.00             %N;65441          %WFACTURANET01%APP PERSONAS                  %40408363                           %NUMERO DE FACTURA                                 %550136320180910                    %NEGOCIO                                           %                                   %                                                  %42777000%PagosKonectaREF2                                                                %A%S%2%40408363                           %20191231%N%40678961008     %7%S%S%N%I%20190423180539%427770.00       %N;65401          %Facturanet-01%SUCURSAL APP                  %2019100410                         %Numero de factura                                 %                                   %                                                  %                                   %                                                  %00%PresentDelayPre2                                                                %P%N%1%                                   %0       % %                % % % % % %20190424101211%.00             %N;65443          %Convenio adhoc telefonica ref1%SUCURSAL APP                  %3782384                            %Cedula                                            %                                   %                                                  %                                   %                                                  %9854800%FacturaAdhocDelayRecha2                                                         %P%N%1%                                   %0       % %                % % % % % %20190424104504%98548.00        %N;65443          %Convenio adhoc telefonica ref1%SUCURSAL APP                  %7975889                            %Cedula                                            %                                   %                                                  %                                   %                                                  %8686000%FacturaAdhocDelayPre3                                                           %P%N%1%                                   %0       % %                % % % % % %20190424104348%86860.00        %N;65443          %Convenio adhoc telefonica ref1%SUCURSAL APP                  %8307746                            %Cedula                                            %                                   %                                                  %                                   %                                                  %8686000%FacturaAdhocDelayPre2                                                           %P%N%1%                                   %0       % %                % % % % % %20190424095739%86860.00        %N;65447          %Convenio adhoc telefonica ref2%SUCURSAL APP                  %53345                              %Factura                                           %                                   %                                                  %                                   %                                                  %34663636%PruebaDelay1                                                                    %P%N%1%                                   %0       % %                % % % % % %20190424110450%346636.36       %N;65401          %Facturanet-01%SUCURSAL APP                  %2019100408                         %Numero de factura                                 %                                   %                                                  %                                   %                                                  %00%PresentDelayRecha                                                               %R%N%1%                                   %0       % %                % % % % % %20190423183401%.00             %N;65443          %Convenio adhoc telefonica ref1%SUCURSAL APP                  %769393                             %Cedula                                            %                                   %                                                  %                                   %                                                  %9854800%FacturaAdhocDelayRecha                                                          %R%N%1%                                   %0       % %                % % % % % %20190423111448%98548.00        %N,~";
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaPagosTerceros.toString());

                                //System.out.println("Respuesta es : " + Respuesta);
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listrarconsultaPagos = " + "##" + docs.obtenerHoraActual());
                                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaPagosTerceros.toString());
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                                } catch (Exception ex1) {

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (PagosATercerosInitController.cancelartarea.get()) {
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
                                    + "066,"
                                    + "000,"
                                    + ",~";
                        }

                        try {

                            if ("000".equals(Respuesta.split(",")[2])) {
                                //850,066,000,TRANSACCION EXITOSA,N,114358953058,000001,000000,000000,63066                                
                                if (PagosATercerosInitController.cancelartarea.get()) {
                                    cancel();
                                } else {

                                    if (MarcoPrincipalController.newConsultaPagosT) {
                                        // numpagina.set(Respuesta.split(",")[9].split(";").length);
                                        numpagina.set(40);
                                        final int totalReg = Integer.parseInt(Respuesta.split(",")[6]);
                                        final int totalRegPen = Integer.parseInt(Respuesta.split(",")[7]);
                                        final int totalRegRec = Integer.parseInt(Respuesta.split(",")[8]);

                                        setIndicadorRegistros(Respuesta.split(",")[4]);//Indicador de más registros
                                        setTracePaginacion(Respuesta.split(",")[5]);//Trace de paginación
                                        setRegistrosactivos(String.valueOf(totalReg));//Total Registros Canal Activas
                                        setRegistrospendientes(String.valueOf(totalRegPen));//Total Registros Canal Pendientes
                                        setRegistrosrechazados(String.valueOf(totalRegRec));//Total Registros Canal Rechazados

                                        //  tablaDatos.setItems(emptyObservableList);
                                        pagos = FXCollections.observableArrayList();

                                        String[] Ltarjetas = Respuesta.split(",")[9].split(";");

                                        for (int i = 0; i < Ltarjetas.length; i++) {
                                            String[] datos = Ltarjetas[i].split("%");
                                            String fechaincs = "";
                                            String fechavenc = "";
                                            String formatopago = "";
                                            String montoRestante = "";

                                            try {
                                                fechaincs = formatoFecha.format(formatoFecha2.parse(datos[23].trim()));
                                            } catch (Exception e) {
                                                fechaincs = datos[23].trim();
                                            }

                                            try {
                                                fechavenc = formatoFechaon.format(formatoFechaven.parse(datos[15].trim()));
                                            } catch (Exception e) {
                                                fechavenc = datos[15].trim().equals("0") ? "" : datos[15].trim();
                                            }

                                            try {
                                                if (datos[9].equals("999999999999999")) {
                                                    formatopago = "Consultar Valor";
                                                } else {
                                                    formatopago = "$" + formatonum.format(Double.parseDouble(datos[9].substring(0, datos[9].length() - 2))).replace(".", ",") + "." + datos[9].substring(datos[9].length() - 2);
                                                }
                                            } catch (Exception e) {
                                                formatopago = datos[9];
                                            }

                                            if (datos[22].equalsIgnoreCase("R")) {
                                                formatopago = "Consultar Valor";
                                            }

                                            try {
                                                if (datos[9].equals("999999999999999")) {
                                                    montoRestante = "Consultar Valor";
                                                } else {
                                                    montoRestante = "$" + formatonum.format(Double.parseDouble(datos[24].substring(0, datos[24].length() - 2))).replace(".", ",") + "." + datos[24].substring(datos[24].length() - 2);
                                                }
                                            } catch (Exception e) {
                                                montoRestante = datos[24];
                                            }

                                            // System.out.println("convenio : " + datos[0].trim() + " --- " + datos[1].trim());
                                            final InfoTablaPagosTerceros convenios = new InfoTablaPagosTerceros(
                                                    false, //checkbox
                                                    datos[0].trim(), //Codigo convenio 
                                                    datos[1].trim(), //Nombre convenio 
                                                    datos[2].trim(), //Canal 
                                                    datos[3].trim().isEmpty() ? "NO TIENE" : datos[3].trim(), //Referencia 1 
                                                    datos[4].trim(), //Nombre referencia 1
                                                    datos[5].trim().isEmpty() ? "NO TIENE" : datos[5].trim(), //Referencia 2 
                                                    datos[6].trim(), //Nombre referencia 2 
                                                    datos[7].trim().isEmpty() ? "NO TIENE" : datos[7].trim(), //Referencia3 
                                                    datos[8].trim(), //Nombre referencia 3 
                                                    formatopago, //Valor factura 9
                                                    datos[10].trim(), //Descripcion factura 
                                                    datos[11].trim(), //Estado factura 
                                                    datos[12].trim(), //Pago factura habilitado
                                                    datos[13].trim(), //Indicador referencia fija 
                                                    datos[14].trim(), //Numero factura 
                                                    fechavenc, //Fecha vencimiento 15
                                                    datos[16].trim(), //Clase cuenta 
                                                    datos[17].trim(), //Cuenta recaudadora
                                                    datos[18].trim(), //Tipo cuenta
                                                    datos[19].trim(), //Pago menor 
                                                    datos[20].trim(), //Pago mayor
                                                    datos[21].trim(), //Pago en cero
                                                    datos[22].trim(),//Indicador BD 
                                                    fechaincs,//Fecha inscripcion 23
                                                    montoRestante,
                                                    datos[25].trim(),
                                                    formatopago);
                                            pagos.add(convenios);
                                        }

                                        if ("N".equalsIgnoreCase(Respuesta.split(",")[4])) {
                                            synchronized (this) {
                                                MarcoPrincipalController.newConsultaPagosT = false;
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (PagosATercerosInitController.cancelartarea.get()) {
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

                                    // throw new Exception("ERROR");
                                }
                            }

                        } catch (Exception e) {
                            docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                        }
                        return null;
                    }
                };
            }
        };
        return serviceFacturas;

    }

    @FXML
    void masRegistros(ActionEvent event) {
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
                    masRegistros.setDisable(true);
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<InfoTablaPagosTerceros> TablaID = task.getValue();

                            if ("S".equals(getIndicadorRegistros())) {
                                masRegistros.setDisable(false);
                            } else {
                                masRegistros.setDisable(true);
                                MarcoPrincipalController.newConsultaPagosT = false;
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
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTablaPagosTerceros> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                                tabla_datos.getItems().setAll(TablaID.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= TablaID.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : TablaID.size())));
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
                                    docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(111);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception e) {
                                docs.imprimir("Error -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                            }

                        }
                    });

                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            acumREg.addAndGet(-numpagina.get());
                            // setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            acumREg.addAndGet(-numpagina.get());
                            //  setNumpagina(getNumpagina() - 1 > 0 ? 0 : getNumpagina() - 1);

                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public class GetconveniosTask extends Service<ObservableList<InfoTablaPagosTerceros>> {

        @Override
        protected Task<ObservableList<InfoTablaPagosTerceros>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaPagosTerceros = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final AtlasAcceso Atlasacceso = new AtlasAcceso();
                    String rnv = "";
                    if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                        rnv = "E";
                    } else {
                        rnv = "P";
                    }

                    tramaPagosTerceros.append("850,066,");
                    tramaPagosTerceros.append(datosCliente.getRefid()); //CONNID
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append(AtlasConstantes.COD_PAGOS_TERCEROS);//COD TRX
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append("S".equalsIgnoreCase(getIndicadorRegistros()) ? getTracePaginacion() : "");//TRACE PAGINACION
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append(datosCliente.getId_cliente());//ID
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append(rnv);//INDICADOR(empresas-personas)
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append(acumREg.addAndGet(numpagina.get()));//CANTIDAD REGISTROS
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append("0".equalsIgnoreCase(getRegistrosactivos()) ? "0" : getRegistrosactivos());//CANTIDAD REGISTROS ACTIVOS
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append("0".equalsIgnoreCase(getRegistrospendientes()) ? "0" : getRegistrospendientes());//CANTIDAD REGISTROS PENDIENTES
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append("0".equalsIgnoreCase(getRegistrosrechazados()) ? "0" : getRegistrosrechazados());//CANTIDAD REGISTROS RECHAZADOS
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append("");//CONVENIO
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append("");//REFERENCIA FIJA
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append(datosCliente.getContraseña());//CLAVE HW
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append(datosCliente.getContraseña());//CLAVE HW
                    tramaPagosTerceros.append(",");
                    tramaPagosTerceros.append(datosCliente.getTokenOauth());//CLAVE HW
                    tramaPagosTerceros.append(",~");

//                    System.out.println("Trama envio pagos es: " + tramaPagosTerceros);
                    if (MarcoPrincipalController.newConsultaPagosT) {
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ ConsultaPagosTerceros = " + "##" + docs.obtenerHoraActual());

//                            Respuesta = "850,066,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "N,"
//                                    + "111547398300,"
//                                    + "000017,"
//                                    + "000000,"
//                                    + "000000,"
//                                    + "63056          %RECAUDADOR-REF FIJA 1 (FINACLE 1 2)                                             %SUCURSAL VIRTUAL              %55555                              %*DIRECCIÓN#2                                      %                                   %*REF2                                             %                                   %*REF3                                             %1000.00         %prueba 1                                                                        %I%S%1%55555                              %0       %N%40678829003     %7%N%N%N%N%20180103095516;"
//                                    + "63066          %RECA-REF FIJA 1(FINACLE 1)                                                      %APP PERSONAS                  %302                                %*DIRECCIÓN#1                                      %                                   %DIRECCIÓN#2                                       %                                   %DIRECCIÓN#3                                       %16000.00        %Pruebas Emilio Telefónica                                                       %I%S%1%302                                %0       %S%BC000099COP4PAEH%0%N%N%N%I%20180205142708;"
//                                    + "63151          %CRAZY -TÓWN &(COMPAÑÍA)#2 1.                                                    %APP PERSONAS                  %                                   %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%123456                             %*NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%458000.00       %Semanalemte                                                                     %I%S%2%123456                             %0       %N%40618832000     %1%N%N%N%N%20180111102659;"
//                                    + "63151          %CRAZY -TÓWN &(COMPAÑÍA)#2 1.                                                    %APP PERSONAS                  %                                   %NÚMERO-& IDENTIFICACIÓN1DEL CLIENTE ÚNICO PAGADOR.%9923796                            %*NÚMERO#  IDENTIFICACIÓN2DEL CLIENTE ÚNICO PAGADOR%                                   %NÚMERO() IDENTIFICACIÓN3DEL CLIENTE ÚNICO PAGADOR.%45789.00        %Prueba Alejov                                                                   %I%S%2%9923796                            %0       %N%40618832000     %1%N%N%N%N%20180110113650;"
//                                    + "63224          %REINTENTOS1                                                                     %SUCURSAL VIRTUAL              %0321                               %*REF1 FIJA OTRAS OPCIONALES                       %                                   %                                                  %                                   %                                                  %345000.01       %programación #1_&&. ñÑ000                                                       %I%S%1%0321                               %0       %N%40610204390     %7%N%N%N%N%20180103092638;"
//                                    + "63224          %REINTENTOS1                                                                     %SUCURSAL VIRTUAL              %22222                              %*REF1 FIJA OTRAS OPCIONALES                       %                                   %                                                  %                                   %                                                  %98000.87        %Prueba tílde_#&                                                                 %I%S%1%22222                              %0       %N%40610204390     %7%N%N%N%N%20180103092507;"
//                                    + "1206540        %PRUEBA TREN 3.1                                                                 %APP PERSONAS                  %45878999                           %*NRO. DE CÉDULA                                   %                                   %NRO DE APTO                                       %                                   %MÁXIMA                                            %1150.00         %preugyyy                                                                        %I%S%1%45878999                           %0       %N%00099000001     %7%N%N%N%N%20180105104529;"
//                                    + "1206540        %PRUEBA TREN 3.1                                                                 %APP PERSONAS                  %432561                             %*NRO. DE CÉDULA                                   %                                   %NRO DE APTO                                       %                                   %MÁXIMA                                            %458.95          %ppp                                                                             %I%S%1%432561                             %0       %N%00099000001     %7%N%N%N%N%20180105103531;"
//                                    + "1206540        %PRUEBA TREN 3.1                                                                 %SUCURSAL VIRTUAL              %002                                %*NRO. DE CÉDULA                                   %                                   %NRO DE APTO                                       %                                   %MÁXIMA                                            %160000.16       %TESTPRE                                                                         %I%S%1%002                                %0       %N%00099000001     %7%N%N%N%N%20171129000001;"
//                                    + "1206540        %PRUEBA TREN 3.1                                                                 %SUCURSAL VIRTUAL              %001                                %*NRO. DE CÉDULA                                   %                                   %NRO DE APTO                                       %                                   %MÁXIMA                                            %160000.16       %TESTPRE                                                                         %I%S%1%001                                %0       %N%00099000001     %7%N%N%N%N%20171129000001;"
//                                    + "1206540        %PRUEBA TREN 3.1                                                                 %SUCURSAL VIRTUAL              %9923796                            %*NRO. DE CÉDULA                                   %                                   %NRO DE APTO                                       %                                   %MÁXIMA                                            %1587900.00      %Prueba pa GDE                                                                   %I%S%1%9923796                            %0       %N%00099000001     %7%N%N%N%N%20171129164952;"
//                                    + "1290717        %PRUEBA TREN 3.2                                                                 %SUCURSAL VIRTUAL              %                                   %NRO. DE CÉDULA                                    %124                                %*NRO. DE APTO                                     %                                   %                                                  %1245800.00      %RecConsPagosProg                                                                %I%S%2%124                                %0       %N%40618955002     %1%N%N%N%N%20171108113417;"
//                                    + "1206540        %PRUEBA TREN 3.1                                                                 %SUCURSAL VIRTUAL              %3216575107                         %*NRO. DE CÉDULA                                   %                                   %NRO DE APTO                                       %                                   %MÁXIMA                                            %548000.00       %EXI CON REINT Y SALDO                                                           %A%S%1%3216575107                         %0       %N%00099000001     %7%N%N%N%N%20171116154559;"
//                                    + "1206540        %PRUEBA TREN 3.1                                                                 %SUCURSAL VIRTUAL              %65986666                           %*NRO. DE CÉDULA                                   %                                   %NRO DE APTO                                       %                                   %MÁXIMA                                            %21500.00        %FACTSUPSALD RECNOREINT                                                          %A%S%1%65986666                           %0       %N%00099000001     %7%N%N%N%N%20171116153851;"
//                                    + "1206540        %PRUEBA TREN 3.1                                                                 %SUCURSAL VIRTUAL              %52228312                           %*NRO. DE CÉDULA                                   %                                   %NRO DE APTO                                       %                                   %MÁXIMA                                            %2100000.00      %factsup sald rec#reint                                                          %A%S%1%52228312                           %0       %N%00099000001     %7%N%N%N%N%20171116153523;"
//                                    + "1206540        %PRUEBA TREN 3.1                                                                 %SUCURSAL VIRTUAL              %561566621                          %*NRO. DE CÉDULA                                   %                                   %NRO DE APTO                                       %                                   %MÁXIMA                                            %12130001.00     %rechconspagprog                                                                 %A%S%1%561566621                          %0       %N%00099000001     %7%N%N%N%N%20171109153756"
//                                    + ",~";
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaPagosTerceros.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaPagosTerceros.toString());

                            // System.out.println("Respuesta es : " + Respuesta);
                            //docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ listrarconsultaprestamos = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaPagosTerceros.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (PagosATercerosInitController.cancelartarea.get()) {

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
                                + "066,"
                                + "000,"
                                + ",~";
                    }
                    if ("000".equals(Respuesta.split(",")[2])) {

                        final int totalReg = Integer.parseInt(Respuesta.split(",")[6]);
                        final int totalRegPen = Integer.parseInt(Respuesta.split(",")[7]);
                        final int totalRegRec = Integer.parseInt(Respuesta.split(",")[8]);

                        setIndicadorRegistros(Respuesta.split(",")[4]);//Indicador de más registros
                        setTracePaginacion(Respuesta.split(",")[5]);//Trace de paginación
                        setRegistrosactivos(String.valueOf(totalReg));//Total Registros Canal Activas
                        setRegistrospendientes(String.valueOf(totalRegPen));//Total Registros Canal Pendientes
                        setRegistrosrechazados(String.valueOf(totalRegRec));//Total Registros Canal Rechazados

                        // numpagina.set(Respuesta.split(",")[9].split(";").length);
                        numpagina.set(40);

                        if (PagosATercerosInitController.cancelartarea.get()) {
                            cancel();
                        } else {

                            String[] Ltarjetas = Respuesta.split(",")[9].split(";");

                            for (int i = 0; i < Ltarjetas.length; i++) {
                                String[] datos = Ltarjetas[i].split("%");
                                String fechaincs = "";
                                String fechavenc = "";
                                String formatopago = "";
                                String montoRestante = "";
//                                    final String Valorinicial = "$ " + formatonum.format(Double.parseDouble(datos[2])).replace(".", ",") + "." + datos[3];
//                                    final String PagoMin = "$ " + formatonum.format(Double.parseDouble(datos[7])).replace(".", ",") + "." + datos[8];
//                                    final String PagoMayor = "$ " + formatonum.format(Double.parseDouble(datos[7])).replace(".", ",") + "." + datos[8];
//                                    final String PagoCero = "$ " + formatonum.format(Double.parseDouble(datos[7])).replace(".", ",") + "." + datos[8];
//                                    final String SaldoActual = "$ " + formatonum.format(Double.parseDouble(datos[4])).replace(".", ",") + "." + datos[5];
                                try {
                                    fechaincs = formatoFecha.format(formatoFecha2.parse(datos[23].trim()));
                                } catch (Exception e) {
                                    fechaincs = datos[23].trim();
                                }

                                try {
                                    fechavenc = formatoFechaon.format(formatoFechaven.parse(datos[15].trim()));
                                } catch (Exception e) {
                                    fechavenc = datos[15].trim().equals("0") ? "" : datos[15].trim();
                                }

                                try {
                                    if (datos[9].equals("999999999999999")) {
                                        formatopago = "Consultar Valor";
                                    } else {
                                        formatopago = "$" + formatonum.format(Double.parseDouble(datos[9].substring(0, datos[9].length() - 2))).replace(".", ",") + "." + datos[9].substring(datos[9].length() - 2);
                                    }
                                } catch (Exception e) {
                                    formatopago = datos[9];
                                }

                                if (datos[22].equalsIgnoreCase("R")) {
                                    formatopago = "Consultar Valor";
                                }

                                try {
                                    if (datos[9].equals("999999999999999")) {
                                        montoRestante = "Consultar Valor";
                                    } else {
                                        montoRestante = "$" + formatonum.format(Double.parseDouble(datos[24].substring(0, datos[24].length() - 2))).replace(".", ",") + "." + datos[24].substring(datos[24].length() - 2);
                                    }
                                } catch (Exception e) {
                                    montoRestante = datos[24];
                                }

                                // System.out.println("convenio : " + datos[0].trim() + " --- " + datos[1].trim());
                                final InfoTablaPagosTerceros convenios = new InfoTablaPagosTerceros(
                                        false, //checkbox
                                        datos[0].trim(), //Codigo convenio 
                                        datos[1].trim(), //Nombre convenio 
                                        datos[2].trim(), //Canal 
                                        datos[3].trim().isEmpty() ? "NO TIENE" : datos[3].trim(), //Referencia 1 
                                        datos[4].trim(), //Nombre referencia 1
                                        datos[5].trim().isEmpty() ? "NO TIENE" : datos[5].trim(), //Referencia 2 
                                        datos[6].trim(), //Nombre referencia 2 
                                        datos[7].trim().isEmpty() ? "NO TIENE" : datos[7].trim(), //Referencia3 
                                        datos[8].trim(), //Nombre referencia 3 
                                        formatopago, //Valor factura 9
                                        datos[10].trim(), //Descripcion factura 
                                        datos[11].trim(), //Estado factura 
                                        datos[12].trim(), //Pago factura habilitado
                                        datos[13].trim(), //Indicador referencia fija 
                                        datos[14].trim(), //Numero factura 
                                        fechavenc, //Fecha vencimiento 15
                                        datos[16].trim(), //Clase cuenta 
                                        datos[17].trim(), //Cuenta recaudadora
                                        datos[18].trim(), //Tipo cuenta
                                        datos[19].trim(), //Pago menor 
                                        datos[20].trim(), //Pago mayor
                                        datos[21].trim(), //Pago en cero
                                        datos[22].trim(),//Indicador BD 
                                        fechaincs,//Fecha inscripcion 23
                                        montoRestante,
                                        datos[25].trim(),
                                        formatopago);
                                pagos.add(convenios);
                            }

                        }
                    } else {
                        if (PagosATercerosInitController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCIÓN" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    acumREg.addAndGet(-numpagina.get());
                                }
                            });

                        }
                    }
                    return pagos;
                }
            };
        }
    }

    @FXML
    private void paginacion() {

        final TableView<InfoTablaPagosTerceros> tablaData = (TableView<InfoTablaPagosTerceros>) AnchorPane.lookup("#tabla_datos");
        //btnBuscar.setDisable(true);
        /**
         * barra progreso
         */
        final Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
        final ProgressIndicator p = new ProgressIndicator();
        p.setMaxSize(150, 150);
        panel_tabla.getChildren().addAll(veil, p);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     * configuracion de la paginacion
                     */
                    tablaData.itemsProperty().unbind();
                    /**
                     * configuracion de la paginacion
                     */
                    if ("S".equals(getIndicadorRegistros())) {
                        masRegistros.setDisable(false);
                    } else {
                        masRegistros.setDisable(true);

                    }

                    if (!pagos.isEmpty()) {
                        final int numpag = pagos.size() % rowsPerPage() == 0 ? pagos.size() / rowsPerPage() : pagos.size() / rowsPerPage() + 1;

                        pagination = new Pagination(numpag, 0);
                        pagination.setPageFactory(new Callback<Integer, Node>() {
                            @Override
                            public Node call(final Integer pageIndex) {

                                if (pageIndex > numpag) {
                                    return null;
                                } else {
                                    int lastIndex = 0;
                                    int displace = pagos.size() % rowsPerPage();
                                    if (displace >= 0) {
                                        lastIndex = pagos.size() / rowsPerPage();
                                    } else {
                                        lastIndex = pagos.size() / rowsPerPage() - 1;
                                    }
                                    int page = pageIndex * itemsPerPage();

                                    for (int i = page; i < page + itemsPerPage(); i++) {

                                        if (lastIndex == pageIndex) {
                                            final List<InfoTablaPagosTerceros> subList = pagos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                            tablaData.setItems(FXCollections.observableArrayList(subList));
                                        } else {
                                            final List<InfoTablaPagosTerceros> subList = pagos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                        tablaData.getItems().setAll(pagos.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= pagos.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : pagos.size())));
                                    }
                                });
                            }
                        });

                        /**
                         * fin configuracion de la paginacion
                         */
                        AnchorPane.getChildren().add(pagination);
                        AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                        AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(111);
                        pagination.setVisible(true);
                        Atlas.vista.show();
                    } else {
                        panel_tabla.getChildren().removeAll(veil, p);
                    }

                    //btnBuscar.setDisable(false);
                } catch (Exception ex) {

                    Logger.getLogger(PagosATercerosInitController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void pagarOP(ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                int indPagosNoHabil = 0;
                final DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();
                final List<InfoTablaPagosTerceros> listapagos = new ArrayList<InfoTablaPagosTerceros>();
                for (Iterator<InfoTablaPagosTerceros> it = pagos.iterator(); it.hasNext();) {
                    InfoTablaPagosTerceros next = it.next();

                    if (next.seleccionProperty().getValue()) {
                        listapagos.add(next);
                        if (next.colPagoFacturaHabiProperty().getValue().equalsIgnoreCase("N")) {
                            indPagosNoHabil++;
                        }

                    }

                }
                datosPagosTerceros.setPagoActual(datosPagosTerceros.getPagoActual() + 1);
                datosPagosTerceros.setSeleccionPagos(listapagos);
                datosPagosTerceros.setOrigenPago(AtlasConstantes.PAGO_X_LOTE);
                DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros);

                if (indPagosNoHabil > 0) {
                    PagosATercerosNoDispoController controller = new PagosATercerosNoDispoController();
                    controller.mostrarPagoNoDisP(datosPagosTerceros.getDatosPagosTerceros(), null, AtlasConstantes.PAGO_X_LOTE);

                } else {
                    List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
                    final InfoTablaPagosTerceros data = seleccionPagos.get(datosPagosTerceros.getPagoActual() - 1);
                    String inBD = data.colIndicadorBDProperty().getValue();
                    String inMasFact = data.colindicadorMasFactProperty().getValue();

                    

                    if ((inBD.equalsIgnoreCase("I") && inMasFact.equalsIgnoreCase("S"))
                            || (inBD.equalsIgnoreCase("E") && inMasFact.equalsIgnoreCase("S"))
                            || (inBD.equalsIgnoreCase("R"))) {
                        //PagosATerceroMasFactController controller = new PagosATerceroMasFactController();

                        consultaMasFact(data);
                        //  - //controller.mostrarMasFacturas(datosPagosTerceros.getDatosPagosTerceros(), AtlasConstantes.PAGO_X_LOTE);

                    } else {

                        PagosATercerosFacturasController controller = new PagosATercerosFacturasController();
                        controller.mostrarPagosTerceros(datosPagosTerceros.getDatosPagosTerceros(), AtlasConstantes.PAGO_X_LOTE);
                    }

                }

            }
        });
    }

    /**
     * ANEXAR CHECKBOX EN COLUMNA
     *
     * @param <InfoTablaPagosTerceros>
     * @param <Boolean>
     */
    public class CheckBoxTableCell<S, T> extends TableCell<S, T> {

        private final CheckBox checkBox;
        private ObservableValue<T> ov;

        public CheckBoxTableCell() {
            this.checkBox = new CheckBox();
            this.checkBox.setAlignment(Pos.CENTER);
            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        }

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(checkBox);
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
            }
            int seleccion = 0;
            //***
            for (Iterator<InfoTablaPagosTerceros> it = pagos.iterator(); it.hasNext();) {
                InfoTablaPagosTerceros next = it.next();

                if (next.seleccionProperty().getValue()) {
                    seleccion++;
                }
            }
            updateBotonera(seleccion);
        }
    }

    public void updateBotonera(final int seleccion) {
        if (seleccion == 1) {

            if (pagarOP != null) {

                pagarOP.setDisable(false);
            }
        } else if (seleccion > 1) {
            if (pagarOP != null) {

                pagarOP.setDisable(false);
            }
        } else if (seleccion == 0) {
            if (pagarOP != null) {

                pagarOP.setDisable(true);
            }
        }
    }

    /**
     * HIPERVINCULO GESTION DETALLE
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

                    InfoTablaPagosTerceros selectedItem = (InfoTablaPagosTerceros) getTableView().getItems().get(getIndex());
                    continuarDetalle(event, selectedItem);
                }
            });
        }

        @Override
        public void updateItem(T t, boolean empty) {
            super.updateItem(t, empty);
            if (empty) {
                setGraphic(null);
            } else {
//                final Label lblaux = new Label();
//                ov = getTableColumn().getCellObservableValue(getIndex());
                InfoTablaPagosTerceros get = (InfoTablaPagosTerceros) getTableView().getItems().get(getIndex());
                if (get.colEstadoProperty().getValue().equals("A")) {
                    setGraphic(vinculo);
                } else {
                    setGraphic(label);
                }
//
//                }
                //setGraphic(vinculo);
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

        public void continuarDetalle(final ActionEvent event, final InfoTablaPagosTerceros selectedItem) {

            DatosDetallePagos datosDetalle = DatosDetallePagos.getDataDetalle();
            datosDetalle.setCodConvenio(selectedItem.colCodConvenioProperty().getValue());
            datosDetalle.setNomConvenio(selectedItem.colNombreConvenioProperty().getValue());
            datosDetalle.setRefFija(selectedItem.colNomRef1Property().getValue());
            datosDetalle.setTipoConv(selectedItem.colIndicadorBDProperty().getValue());
            datosDetalle.setDescripcion(selectedItem.colDescripcionProperty().getValue());
            datosDetalle.setFechavencimiento(selectedItem.colFechaVencimientoProperty().getValue());
            datosDetalle.setReferencia1(selectedItem.colNomRef1Property().getValue());
            datosDetalle.setReferencia2(selectedItem.colNomRef2Property().getValue());
            datosDetalle.setReferencia3(selectedItem.colNomRef3Property().getValue());
            datosDetalle.setIndicadorReferenciaFija(selectedItem.colIndicadorRefFijaProperty().getValue());
            datosDetalle.setSelectedItem(selectedItem);
            DatosDetallePagos.setDataDetalle(datosDetalle);

            continuarDetalle().setOnSucceeded(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            continuarDetalle().setOnCancelled(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle" + "##" + docs.obtenerHoraActual());
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
            serviceDetalleFact = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            String Respuesta = new String();

                            final Cliente datosCliente = Cliente.getCliente();
                            final StringBuilder tramaDetalleFactura = new StringBuilder();
                            final ConectSS servicioSS = new ConectSS();
                            final Cliente cliente = Cliente.getCliente();
                            final AtlasAcceso Atlasacceso = new AtlasAcceso();
                            DatosDetallePagos datosDetalle = DatosDetallePagos.getDataDetalle();

                            String rnv = "";
                            if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                                rnv = "E";
                            } else {
                                rnv = "P";
                            }

                            //850,067,connid,codtransaccion,identificacion,tipoPersona(P o E),codConvenio,referenciaFija,tipoConvenio,clavehardware,~
                            tramaDetalleFactura.append("850,067,");
                            tramaDetalleFactura.append(datosCliente.getRefid()); //CONNID
                            tramaDetalleFactura.append(",");
                            tramaDetalleFactura.append(AtlasConstantes.COD_PAGOS_TERCEROSDET);//CODTRX
                            tramaDetalleFactura.append(",");
                            tramaDetalleFactura.append(datosCliente.getId_cliente());//CEDULA
                            tramaDetalleFactura.append(",");
                            tramaDetalleFactura.append(rnv);//TIPOPERSONA
                            tramaDetalleFactura.append(",");
                            tramaDetalleFactura.append(datosDetalle.getCodConvenio());//CODCONVENIO
                            tramaDetalleFactura.append(",");
                            if (datosDetalle.getIndicadorReferenciaFija().equals("1")) {
                                tramaDetalleFactura.append(datosDetalle.getReferencia1());
                            } else if (datosDetalle.getIndicadorReferenciaFija().equals("2")) {
                                tramaDetalleFactura.append(datosDetalle.getReferencia2());
                            } else if (datosDetalle.getIndicadorReferenciaFija().equals("3")) {
                                tramaDetalleFactura.append(datosDetalle.getReferencia3());
                            } else {
                                tramaDetalleFactura.append(datosDetalle.getRefFija());//REFFIJA
                            }
                            tramaDetalleFactura.append(",");
                            tramaDetalleFactura.append(datosDetalle.getTipoConv());//TIPOCONV
                            tramaDetalleFactura.append(",");
                            tramaDetalleFactura.append(cliente.getTipide());
                            tramaDetalleFactura.append(",");
                            tramaDetalleFactura.append(cliente.getId_cliente());
                            tramaDetalleFactura.append(",");
                            tramaDetalleFactura.append(datosCliente.getContraseña());
                            tramaDetalleFactura.append(",");
                            tramaDetalleFactura.append(datosCliente.getTokenOauth());
                            tramaDetalleFactura.append(",~");

//                            System.out.println("TRAMA DETALLE FACTURA :" + tramaDetalleFactura);
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Trama detalle = " + "##" + docs.obtenerHoraActual());

                                // Respuesta = "850,067,000,TRANSACCION EXITOSA,Y,0  , , ,0 ,0,20/02/2018,31/12/2099,0000000076788889,18/04/2018,ACTIVA                                                                          ,APP PERSONAS                  ,0007  ,BANCOLOMBIA                                                                     ,5470620251552019    ,Tarjeta                       ,33333                              ,                                   ,2232                               ,1,~";
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaDetalleFactura.toString());
                                //  System.out.println("RESPUESTA DETALLE CONV:" + Respuesta);
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex) {

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion entrega  = " + "##" + docs.obtenerHoraActual());
                                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaDetalleFactura.toString());
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                                } catch (Exception ex1) {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
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

                                datosDetalle.setFrecuenciaPago(Respuesta.split(",")[4].trim());
                                datosDetalle.setNumeroDias(Respuesta.split(",")[5].trim());
                                datosDetalle.setNumeroSemana(Respuesta.split(",")[6].trim());
                                datosDetalle.setDiaSemana(Respuesta.split(",")[7].trim());
                                datosDetalle.setDiaMes(Respuesta.split(",")[8].trim());
                                datosDetalle.setDiasReintento(Respuesta.split(",")[9].trim());
                                datosDetalle.setFechaInicio(Respuesta.split(",")[10].trim());
                                datosDetalle.setFechaFin(Respuesta.split(",")[11].trim());
                                datosDetalle.setValorFactura(Respuesta.split(",")[12].trim());
                                datosDetalle.setFechaSiguientePago(Respuesta.split(",")[13].trim());
                                datosDetalle.setEstado(Respuesta.split(",")[14].trim());
                                datosDetalle.setCanal(Respuesta.split(",")[15].trim());
                                datosDetalle.setCodigoBanco(Respuesta.split(",")[16].trim());
                                datosDetalle.setNombreBanco(Respuesta.split(",")[17].trim());
                                datosDetalle.setNumeroCuentaPagador(Respuesta.split(",")[18].trim());
                                datosDetalle.setTipoCuentaPagador(Respuesta.split(",")[19].trim());
                                datosDetalle.setReferencia1(Respuesta.split(",")[20].trim());
                                datosDetalle.setReferencia2(Respuesta.split(",")[21].trim());
                                datosDetalle.setReferencia3(Respuesta.split(",")[22].trim());
                                datosDetalle.setIndicadorReferenciaFija(Respuesta.split(",")[23].trim());

//
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final PagosATercerosDetalleController controller = new PagosATercerosDetalleController();
                                        controller.mostrarDetalleFactura(DatosDetallePagos.getDataDetalle(), 0);
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

            return serviceDetalleFact;
        }
    }

    public void consultaMasFact(final InfoTablaPagosTerceros selectedItem) {

        consultaMasFacturas().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono mas facturas" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        consultaMasFacturas().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo mas facturas" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (consultaMasFacturas().isRunning()) {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(consultaMasFacturas().progressProperty());
            consultaMasFacturas().reset();
            consultaMasFacturas().start();

        } else {
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(consultaMasFacturas().progressProperty());
            consultaMasFacturas().start();
        }
    }

    public Service<Void> consultaMasFacturas() {
        serviceMasFact = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final Cliente datosCliente = Cliente.getCliente();
                        final StringBuilder tramaMasFacturas = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final AtlasAcceso Atlasacceso = new AtlasAcceso();
                        final DatosPagosTerceros datosPagosTerceros = DatosPagosTerceros.getDatosPagosTerceros();
                        List<InfoTablaPagosTerceros> seleccionPagos = datosPagosTerceros.getSeleccionPagos();
                        final InfoTablaPagosTerceros data = seleccionPagos.get(datosPagosTerceros.getPagoActual() - 1);

                        final StringBuilder rnv = new StringBuilder("");
                        if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                            rnv.append("E");
                        } else {
                            rnv.append("P");
                        }
                        final StringBuilder refFija = new StringBuilder("");
                        if (data.colIndicadorRefFijaProperty().getValue().equals("1")) {
                            refFija.append(data.colNomRef1Property().getValue());
                        } else if (data.colIndicadorRefFijaProperty().getValue().equals("2")) {
                            refFija.append(data.colNomRef2Property().getValue());
                        } else if (data.colIndicadorRefFijaProperty().getValue().equals("3")) {
                            refFija.append(data.colNomRef3Property().getValue());
                        } else {
                            refFija.append(data.colNomRef1Property().getValue());//REFFIJA
                        }
                        final String convenio = data.colCodConvenioProperty().getValue();
                        final String indBD = data.colIndicadorBDProperty().getValue();

                        tramaMasFacturas.append("850,079,");
                        tramaMasFacturas.append(datosCliente.getRefid()); //CONNID
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(AtlasConstantes.COD_PAGOS_MAS_FACTURAS);//COD TRX
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(" ");//TRACE PAGINACION
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getId_cliente());//ID
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(rnv);//INDICADOR(empresas-personas)
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append("0");//CANTIDAD REGISTROS
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append("0");//CANTIDAD REGISTROS TOAL                        
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(convenio);//CONVENIO
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(refFija.toString());//REFERENCIA FIJA
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(indBD);//ind base datos
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getContraseña());//CLAVE HW
                        tramaMasFacturas.append(",");
                        tramaMasFacturas.append(datosCliente.getTokenOauth());//CLAVE HW
                        tramaMasFacturas.append(",~");

//                        System.out.println("TRAMA MAS  FACTURAS :" + tramaMasFacturas);
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Trama detalle = " + "##" + docs.obtenerHoraActual());

                            // Respuesta="850,079,000,TRANSACCION EXITOSA,S,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~850,079,000,TRANSACCION EXITOSA,N,Trace de paginación,20,2502,1,12345678902,2,nombreConvenio,claseCuenta,R,indicadorContingenciaBD,1,0,1,textoReferencia1,textoReferencia2,textoReferencia3,indicadorReferenciaFija,12352%referencia1%referencia2%referencia3%08-10-2019%12535030%1500000;12353%referencia1%referencia2%referencia3%30-01-2019%13569823%0,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMasFacturas.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion entrega  = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaMasFacturas.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
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

                            try {
                                final String indMAsReg = Respuesta.split(",")[4];//Indicador de más registros
                                final String tracePag = Respuesta.split(",")[5];
                                final int totalRegP = Integer.parseInt(Respuesta.split(",")[6]);
                                //  tablaDatos.setItems(emptyObservableList);
                                ObservableList<infoTablaMasFacturas> list = FXCollections.observableArrayList();

                                String[] Ltarjetas = Respuesta.split(",")[22].split(";");

                                for (int i = 0; i < Ltarjetas.length; i++) {
                                    String[] datos = Ltarjetas[i].split("%");

//    private StringProperty codigoConvenio;
//    private StringProperty tipoRecaudo;
//    private StringProperty cuentaRecaudo;
//    private StringProperty cantidadReferencias;
//    private StringProperty nombreConvenio;
//    private StringProperty claseCuenta;
//    private StringProperty indicadorBD;
//    private StringProperty indicadorContingenciaBD;
//    private StringProperty indicadorPagoParcial;
//    private StringProperty indicadorPagoMayor;
//    private StringProperty indicadorPagoEnCero;
//    private StringProperty textoReferencia1;
//    private StringProperty textoReferencia2;
//    private StringProperty textoReferencia3;
//    private StringProperty indicadorReferenciaFija;
//    private StringProperty numeroFactura;
//    private StringProperty referencia1;
//    private StringProperty referencia2;
//    private StringProperty referencia3;
//    private StringProperty fechaDeVencimiento;
//    private StringProperty valorFactura;
//    private StringProperty valorRestanteDelPago;
                                    String valorFactura = "";
                                    String valorRestPago = "";
                                    String fechavenc = "";

                                    try {
                                        fechavenc = formatoFechaon.format(formatoFechaven.parse(datos[4].trim()));
                                    } catch (Exception e) {
                                        fechavenc = datos[4].trim().equals("0") ? "" : datos[4].trim();
                                    }

                                    try {
                                        valorFactura = "$" + formatonum.format(Double.parseDouble(datos[5].substring(0, datos[5].length() - 2))).replace(".", ",") + "." + datos[5].substring(datos[5].length() - 2);
                                    } catch (Exception e) {
                                        valorFactura = datos[5].trim();
                                    }
                                    try {
                                        valorRestPago = "$" + formatonum.format(Double.parseDouble(datos[6].substring(0, datos[6].length() - 2))).replace(".", ",") + "." + datos[6].substring(datos[6].length() - 2);
                                    } catch (Exception e) {
                                        try {
                                            valorRestPago = datos[6].trim();
                                        } catch (Exception e2) {
                                            valorRestPago = "";
                                        }

                                    }

                                    infoTablaMasFacturas facturas = new infoTablaMasFacturas(Respuesta.split(",")[7],
                                            Respuesta.split(",")[8].trim(),
                                            Respuesta.split(",")[9].trim(),
                                            Respuesta.split(",")[10].trim(),
                                            Respuesta.split(",")[11].trim(),
                                            Respuesta.split(",")[12].trim(),
                                            Respuesta.split(",")[13].trim(),
                                            Respuesta.split(",")[14].trim(),
                                            Respuesta.split(",")[15].trim(),
                                            Respuesta.split(",")[16].trim(),
                                            Respuesta.split(",")[17].trim(),
                                            Respuesta.split(",")[18].trim(),
                                            Respuesta.split(",")[19].trim(),
                                            Respuesta.split(",")[20].trim(),
                                            Respuesta.split(",")[21].trim(),
                                            datos[0].trim(),
                                            datos[1].trim(),
                                            datos[2].trim(),
                                            datos[3].trim(),
                                            fechavenc,
                                            valorFactura,
                                            valorRestPago);

                                    list.add(facturas);

                                }
                                data.setMasFacturas(list);
                                seleccionPagos.set(datosPagosTerceros.getPagoActual() - 1, data);
                                datosPagosTerceros.setSeleccionPagos(seleccionPagos);
                                DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros);

//
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        PagosATerceroMasFactController controller = new PagosATerceroMasFactController();
                                        controller.mostrarMasFacturas(DatosPagosTerceros.getDatosPagosTerceros(),
                                                indMAsReg,
                                                totalRegP,
                                                tracePag,
                                                rnv.toString(),
                                                refFija.toString(),
                                                convenio,
                                                indBD,
                                                AtlasConstantes.PAGO_X_LOTE);
                                    }
                                });

                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                                final String mensaje = e.toString();
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes(mensaje, "ERROR DE RESPUESTA", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        cancel();
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                        final DatosPagosTerceros datosPagosTerceros1 = DatosPagosTerceros.getDatosPagosTerceros();
                                        datosPagosTerceros1.setPagoActual(datosPagosTerceros1.getPagoActual() - 1);
                                        DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros1);
                                    }
                                });
                            }

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
                                    final DatosPagosTerceros datosPagosTerceros1 = DatosPagosTerceros.getDatosPagosTerceros();
                                    datosPagosTerceros1.setPagoActual(datosPagosTerceros1.getPagoActual() - 1);
                                    DatosPagosTerceros.setDatosPagosTerceros(datosPagosTerceros1);
                                }
                            });

                        }

                        return null;
                    }
                };
            }
        };

        return serviceMasFact;
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
