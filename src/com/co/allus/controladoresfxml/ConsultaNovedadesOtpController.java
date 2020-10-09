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
import com.co.allus.modelo.consultanovedades.CodigosNovedades;
import com.co.allus.modelo.consultanovedades.ConsultaNovTask;
import com.co.allus.modelo.consultanovedades.InfoTablaNovedadesIni;
import com.co.allus.modelo.consultanovedades.infoNovedades;
import com.co.allus.modelo.consultanovedades.infoTablaNovedades;
import com.co.allus.userComponent.DatePicker;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
 * @author alexander.lopez.o
 */
public class ConsultaNovedadesOtpController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button BuscarNov;
    @FXML
    private Label dato_seg;
    @FXML
    private Label estado_serv_otp;
    @FXML
    private DatePicker fecha_final;
    @FXML
    private DatePicker fecha_inicial;
    @FXML
    private TableColumn<infoTablaNovedades, String> canal_nov;
    @FXML
    private TableColumn<infoTablaNovedades, String> estado_serv;
    @FXML
    private TableColumn<infoTablaNovedades, String> fecha_nov;
    @FXML
    private TableColumn<infoTablaNovedades, String> hora_nov;
    @FXML
    private TableColumn<infoTablaNovedades, String> tipo_mecanismo;
    @FXML
    private TableView<infoTablaNovedades> tablaDatos;
    @FXML
    private Label mensajes_val;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ComboBox<String> seleccionCodigos;
    @FXML
    private Button terminar_trx;
    @FXML
    private Button botBorrFF;
    @FXML
    private Button botBorrFi;
    @FXML
    private TableView<InfoTablaNovedadesIni> tablaDatos1;
    @FXML
    private TableColumn<InfoTablaNovedadesIni, String> tipo_mecanismo1;
    @FXML
    private TableColumn<InfoTablaNovedadesIni, String> canal_nov1;
    @FXML
    private TableColumn<InfoTablaNovedadesIni, String> estado_serv1;
    @FXML
    private TableColumn<InfoTablaNovedadesIni, String> fecha_nov1;
    @FXML
    private TableColumn<InfoTablaNovedadesIni, String> hora_nov1;
    @FXML
    private StackPane panel_tabla_1;

    @FXML
    void botBorrFf(final ActionEvent event) {
        fecha_final.setSelectedDate(null);
        final Calendar instance = Calendar.getInstance();
        fecha_final.getCalendarView().setCalendar(instance);
    }

    @FXML
    void botBorrFi(final ActionEvent event) {
        fecha_inicial.setSelectedDate(null);
        final Calendar instance = Calendar.getInstance();
        fecha_inicial.getCalendarView().setCalendar(instance);
    }
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    public static AtomicBoolean cancelartareaNov = new AtomicBoolean();
    private Service<ObservableList<InfoTablaNovedadesIni>> task = new ConsultaNovedadesOtpController.GetDatosNovedadiniTask();
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private Service<ObservableList<infoTablaNovedades>> TaskTablaNovedades = new ConsultaNovTask();
    public static ObservableList<infoTablaNovedades> TablaNovedades = FXCollections.observableArrayList();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private String CelularClte = "";
    private String EmailCtle = "";
    private static int tipoConsulta = 0;

    public String getCelularClte() {
        return CelularClte;
    }

    public void setCelularClte(String CelularClte) {
        this.CelularClte = CelularClte;
    }

    public String getEmailCtle() {
        return EmailCtle;
    }

    public void setEmailCtle(String EmailCtle) {
        this.EmailCtle = EmailCtle;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert BuscarNov != null : "fx:id=\"BuscarNov\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert botBorrFF != null : "fx:id=\"botBorrFF\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert botBorrFi != null : "fx:id=\"botBorrFi\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert canal_nov1 != null : "fx:id=\"canal_nov1\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert dato_seg != null : "fx:id=\"dato_seg\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert estado_serv1 != null : "fx:id=\"estado_serv1\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert estado_serv_otp != null : "fx:id=\"estado_serv_otp\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert fecha_final != null : "fx:id=\"fecha_final\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert fecha_inicial != null : "fx:id=\"fecha_inicial\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert fecha_nov1 != null : "fx:id=\"fecha_nov1\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert hora_nov != null : "fx:id=\"hora_nov\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert mensajes_val != null : "fx:id=\"mensajes_val\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert panel_tabla_1 != null : "fx:id=\"panel_tabla_1\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert seleccionCodigos != null : "fx:id=\"seleccionCodigos\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert tablaDatos1 != null : "fx:id=\"tablaDatos1\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";
        assert tipo_mecanismo1 != null : "fx:id=\"tipo_mecanismo1\" was not injected: check your FXML file 'ConsultaNovedadesOtp.fxml'.";

        this.resources = rb;
        this.location = url;
        ConsultaNovedadesOtpController.cancelartarea.set(false);
        ConsultaNovedadesOtpController.cancelartareaNov.set(false);

        estado_serv.setCellValueFactory(new PropertyValueFactory<infoTablaNovedades, String>("estado_serv"));
        tipo_mecanismo.setCellValueFactory(new PropertyValueFactory<infoTablaNovedades, String>("tipo_mecanismo"));
        fecha_nov.setCellValueFactory(new PropertyValueFactory<infoTablaNovedades, String>("fecha_nov"));
        hora_nov.setCellValueFactory(new PropertyValueFactory<infoTablaNovedades, String>("hora_nov"));
        canal_nov.setCellValueFactory(new PropertyValueFactory<infoTablaNovedades, String>("canal_nov"));
        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        estado_serv1.setCellValueFactory(new PropertyValueFactory<InfoTablaNovedadesIni, String>("estado_serv"));
        tipo_mecanismo1.setCellValueFactory(new PropertyValueFactory<InfoTablaNovedadesIni, String>("tipo_mecanismo"));
        fecha_nov1.setCellValueFactory(new PropertyValueFactory<InfoTablaNovedadesIni, String>("fecha_nov"));
        hora_nov1.setCellValueFactory(new PropertyValueFactory<InfoTablaNovedadesIni, String>("hora_nov"));
        canal_nov1.setCellValueFactory(new PropertyValueFactory<InfoTablaNovedadesIni, String>("canal_nov"));
        tablaDatos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    public void mostraConsultaNovedades() {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaNovedadesOtp.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();

                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                        final Button buscar = (Button) root.lookup("#BuscarNov");
                        // final Button botoncontinuarOp = (Button) root.lookup("#terminar_trx");
                        final Label label_menu = (Label) pane.lookup("#label_saldos");
                        final ComboBox<String> ComboCodigos = (ComboBox<String>) root.lookup("#seleccionCodigos");
                        final DatePicker fecha_ini = (DatePicker) root.lookup("#fecha_inicial");
                        final DatePicker fecha_fin = (DatePicker) root.lookup("#fecha_final");
                        final Label datoSeguridad = (Label) root.lookup("#dato_seg");
                        final Label estadoServ = (Label) root.lookup("#estado_serv_otp");
                        final Label mensajes = (Label) root.lookup("#mensajes_val");
                        final StackPane panel_tabla_1 = (StackPane) root.lookup("#panel_tabla_1");
                        final TableView<infoTablaNovedades> tablaDatos = (TableView<infoTablaNovedades>) root.lookup("#tablaDatos");
                        final TableView<InfoTablaNovedadesIni> tablaDatos1 = (TableView<InfoTablaNovedadesIni>) root.lookup("#tablaDatos1");
                        /// codigo para inyectar los datos 

                        fecha_ini.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
                        fecha_ini.getCalendarView().todayButtonTextProperty().set("Hoy");

                        fecha_fin.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
                        fecha_fin.getCalendarView().todayButtonTextProperty().set("Hoy");

                        final Cliente datosCliente = Cliente.getCliente();
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                        cliente.setText("");
                        cliente.setText(info);

                        final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                        ComboCodigos.setItems(emptyList);
                        final List<String> datos = new ArrayList<String>();
                        datos.addAll(CodigosNovedades.codigosProd.values());
                        Collections.sort(datos);
                        datos.add(0, AtlasConstantes.MENSAJE_COMBO_NOVEDADES);
                        ComboCodigos.setItems(FXCollections.observableArrayList(datos));
                        ComboCodigos.getSelectionModel().select(AtlasConstantes.MENSAJE_COMBO_NOVEDADES);

                        /**
                         * barra progreso
                         */
//                        final Region veil = new Region();
//
//                        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
//                        final ProgressIndicator p = new ProgressIndicator();
//                        p.setPrefSize(150, 150);
//                        p.setLayoutX(200);
//                        p.setLayoutY(50);
//
//                        root.getChildren().addAll(veil, p);
                        final DropShadow shadow = new DropShadow();
//                        botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                                new EventHandler<MouseEvent>() {
//                                    @Override
//                                    public void handle(final MouseEvent event) {
//                                        botoncontinuarOp.setCursor(Cursor.HAND);
//                                        botoncontinuarOp.setEffect(shadow);
//                                    }
//                                });
//
//                        botoncontinuarOp.addEventHandler(MouseEvent.MOUSE_EXITED,
//                                new EventHandler<MouseEvent>() {
//                                    @Override
//                                    public void handle(final MouseEvent event) {
//                                        botoncontinuarOp.setCursor(Cursor.DEFAULT);
//                                        botoncontinuarOp.setEffect(null);
//
//                                    }
//                                });

                        buscar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                                new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(final MouseEvent event) {
                                buscar.setCursor(Cursor.HAND);
                                buscar.setEffect(shadow);
                            }
                        });

                        buscar.addEventHandler(MouseEvent.MOUSE_EXITED,
                                new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(final MouseEvent event) {
                                buscar.setCursor(Cursor.DEFAULT);
                                buscar.setEffect(null);

                            }
                        });

                        label_menu.setVisible(false);

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
                            gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                            Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());

                        }

                        pane.setAlignment(Pos.CENTER_LEFT);
                        pane.add(root, 1, 0);

                        Region veil = new Region();
                        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                        ProgressIndicator p = new ProgressIndicator();
                        p.setMaxSize(150, 150);
                        panel_tabla_1.getChildren().addAll(veil, p);
                        p.progressProperty().bind(task.progressProperty());
                        veil.visibleProperty().bind(task.runningProperty());
                        p.visibleProperty().bind(task.runningProperty());
                        tablaDatos1.itemsProperty().bind(task.valueProperty());
                        task.start();
//                        // Use binding to be notified whenever the data source chagnes
//                        // task = new GetTarjetasTask();
//                        p.progressProperty().bind(task.progressProperty());
//                        veil.visibleProperty().bind(task.runningProperty());
//                        p.visibleProperty().bind(task.runningProperty());

                        tablaDatos.setVisible(false);

                        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {

//                                System.out.println("TERMINO TAREA");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            ObservableList<InfoTablaNovedadesIni> value = task.getValue();

                                            tablaDatos1.itemsProperty().unbind();
                                            tablaDatos1.setItems(value);
                                            String estado_opt = datosCliente.getEstado_opt();

                                            if (estado_opt.equals("01010")) {
                                                estado_opt = "Activo";

                                            } else if (estado_opt.equals("01011")) {
                                                estado_opt = "Inactivo";

                                            } else if (estado_opt.equals("00010")) {
                                                estado_opt = "Bloqueo personalizado";
                                            } else if (estado_opt.equals("00011")) {

                                                estado_opt = "Bloqueo general";
                                            } else if (estado_opt.equals("00013")) {
                                                estado_opt = "Bloqueo voluntario";

                                            } else if (estado_opt.equals("00014")) {

                                                estado_opt = "Bloqueo por seguridad";
                                            } else if (estado_opt.equals("01013")) {

                                                estado_opt = "Bloqueo pot intentos fallidos";
                                            }
//                                            estadoServ.setText(estado_opt);
                                            if (!getCelularClte().isEmpty() && !getEmailCtle().isEmpty()) {
                                                datoSeguridad.setText(getEmailCtle() + " - " + getCelularClte());
                                            } else if (getCelularClte().isEmpty() && !getEmailCtle().isEmpty()) {
                                                datoSeguridad.setText(getEmailCtle());
                                            } else {
                                                if (!getCelularClte().isEmpty() && getEmailCtle().isEmpty()) {
                                                    datoSeguridad.setText(getCelularClte());
                                                }
                                            }

                                            Atlas.vista.show();
                                        } catch (Exception ex) {
                                            Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());

                                        }
                                    }
                                });
                            }
                        });

                        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
//                                System.out.println("ERROR EN LA CONSULTA");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        estadoServ.setText(datosCliente.getEstado_opt());
                                    }
                                });
                            }
                        });

                        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
                            }
                        });

                    } catch (Exception ex) {
                        Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());

                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                }
            });
        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//            ex.printStackTrace();
        }

    }

    @FXML
    void aceptar(ActionEvent event) {
        try {
            // cancelar tarea de buscar si esta corriendo
        } catch (Exception ex) {
            Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());

        }

        try {

            Atlas.getVista().gotoPrincipal();
        } catch (Exception ex) {
            Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());

        }
    }

    private boolean esRangoValido(final Date calendarFi, final Date calendarFf) {
        try {
            final String hoy = gestorDoc.obtenerFechaActualHoy();
            final String dosMant = gestorDoc.obtenerFechaActualMesAnt2();
            final String fini = gestorDoc.convertirFecha2(calendarFi);
            final String ffin = gestorDoc.convertirFecha2(calendarFf);

//            final String fini = gestorDoc.convertToString(calendarFi);
//            final String ffin = gestorDoc.convertToString(calendarFf);
            final long hoy1 = Long.parseLong(hoy); // hoy
            final long dosMant1 = Long.parseLong(dosMant); // hace 2 meses

            final long fini1 = Long.parseLong(fini); //finicial
            final long ffin1 = Long.parseLong(ffin); //ffinal
            Calendar calendarFiaux = Calendar.getInstance();
            Calendar calendarFfaux = Calendar.getInstance();

            calendarFiaux.setTime(calendarFi);
            calendarFfaux.setTime(calendarFf);
            final int diferenciaDias = gestorDoc.CalcularDifFechas(calendarFiaux, calendarFfaux);

            //            if (fini1 <= hoy1 && fini1 >= dosMant1 && ffin1 <= dosMant1 && ffin1 >= dosMant1 && fini1 <= ffin1) {
//                return true;
//            } else {
//                return false;
//            }
//            System.out.println("DIFERENCIA ENTRE FECHAS: " + diferenciaDias);
            if (diferenciaDias > 60) {
                return false;
            }

            if ((fini1 <= hoy1) && (fini1 <= ffin1) && (ffin1 <= hoy1)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());

            return false;
        }

    }

    @FXML
    void buscarNovAction(ActionEvent event) {

        if (((esSeleccionado(fecha_inicial.getCalendarView().getCalendar()) && !esSeleccionado(fecha_final.getCalendarView().getCalendar()))
                || (!esSeleccionado(fecha_inicial.getCalendarView().getCalendar()) && esSeleccionado(fecha_final.getCalendarView().getCalendar())))) {
            // proceso de consulta solo codigo 
            mensajes_val.setText(AtlasConstantes.AVISOS_CONSULTANOVEDADES3);
            mensajes_val.setDisable(false);
            mensajes_val.setVisible(true);
            final ObservableList<infoTablaNovedades> dataempty = FXCollections.emptyObservableList();
            tablaDatos.setItems(dataempty);

        } else {
            if (esSeleccionado(fecha_inicial.getCalendarView().getCalendar()) && esSeleccionado(fecha_final.getCalendarView().getCalendar())
                    && !seleccionCodigos.getSelectionModel().getSelectedItem().equals(AtlasConstantes.MENSAJE_COMBO_NOVEDADES)) {
                mensajes_val.setDisable(false);
                mensajes_val.setVisible(false);

//                System.out.println("con codigo fecha ini y fecha fin");
                consumirDatos(1);
            } else {
                if (esSeleccionado(fecha_inicial.getCalendarView().getCalendar()) && esSeleccionado(fecha_final.getCalendarView().getCalendar())
                        && seleccionCodigos.getSelectionModel().getSelectedItem().equals(AtlasConstantes.MENSAJE_COMBO_NOVEDADES)) {
                    mensajes_val.setDisable(false);
                    mensajes_val.setVisible(false);

//                    System.out.println("con fecha ini y fecha fin");
                    consumirDatos(2);
                } else {
                    if (!esSeleccionado(fecha_inicial.getCalendarView().getCalendar()) && !esSeleccionado(fecha_final.getCalendarView().getCalendar())
                            && !seleccionCodigos.getSelectionModel().getSelectedItem().equals(AtlasConstantes.MENSAJE_COMBO_NOVEDADES)) {
//                        System.out.println("solo codigo");
                        mensajes_val.setDisable(false);
                        mensajes_val.setVisible(false);
                        consumirDatos(3);
                    } else {
                        if (!esSeleccionado(fecha_inicial.getCalendarView().getCalendar()) && !esSeleccionado(fecha_final.getCalendarView().getCalendar())
                                && seleccionCodigos.getSelectionModel().getSelectedItem().equals(AtlasConstantes.MENSAJE_COMBO_NOVEDADES)) {
//                            mensajes_val.setText("Ingrese datos para realizar la consulta");
                            mensajes_val.setText(AtlasConstantes.AVISOS_CONSULTANOVEDADES4);
                            mensajes_val.setDisable(false);
                            mensajes_val.setVisible(true);
                            final ObservableList<infoTablaNovedades> dataempty = FXCollections.emptyObservableList();
                            tablaDatos.setItems(dataempty);
                        }
                    }
                }
            }
        }
    }

    private void consumirDatos(final int tipo) {
//        infoNovedades info = infoNovedades.getInfoNovedades();

        if (tipo == 1) // con codigo fecha ini y fecha fin
        {
            final infoNovedades info = infoNovedades.getInfoNovedades();
            if (esRangoValido(fecha_inicial.getSelectedDate(), fecha_final.getSelectedDate())) {

//                info.setFechainicial(gestorDoc.convertirFechaNovedad(fecha_inicial.getCalendarView().getCalendar()));
//                info.setFechaFinal(gestorDoc.convertirFechaNovedad(fecha_final.getCalendarView().getCalendar()));
                info.setFechainicial(gestorDoc.convertirFecha2(fecha_inicial.getSelectedDate()));
                info.setFechaFinal(gestorDoc.convertirFecha2(fecha_final.getSelectedDate()));

                info.setNovedad(seleccionCodigos.getSelectionModel().getSelectedItem());
                infoNovedades.setNovedades(info);
                ConsultarNovedad(1);
                mensajes_val.setDisable(false);
                mensajes_val.setVisible(false);
            } else {
//                mensajes_val.setText("Ingrese rango de fechas válido");
                mensajes_val.setText(AtlasConstantes.AVISOS_CONSULTANOVEDADES3);
                mensajes_val.setDisable(false);
                mensajes_val.setVisible(true);
                fecha_inicial.setSelectedDate(fecha_inicial.getSelectedDate());
                fecha_final.setSelectedDate(fecha_final.getSelectedDate());
                final ObservableList<infoTablaNovedades> dataempty = FXCollections.emptyObservableList();
                tablaDatos.setItems(dataempty);
            }
        }
        if (tipo == 2) // con fecha ini y fecha fin
        {
            final infoNovedades info = infoNovedades.getInfoNovedades();
            if (esRangoValido(fecha_inicial.getSelectedDate(), fecha_final.getSelectedDate())) {
//                info.setFechainicial(gestorDoc.convertToString(fecha_inicial.getCalendarView().getCalendar()));
//                info.setFechaFinal(gestorDoc.convertToString(fecha_final.getCalendarView().getCalendar()));
                info.setFechainicial(gestorDoc.convertirFecha2(fecha_inicial.getSelectedDate()));
                info.setFechaFinal(gestorDoc.convertirFecha2(fecha_final.getSelectedDate()));
                info.setNovedad("");
                infoNovedades.setNovedades(info);
                ConsultarNovedad(2);
                mensajes_val.setDisable(false);
                mensajes_val.setVisible(false);
            } else {
//                mensajes_val.setText("Ingrese rango de fechas válido");
                mensajes_val.setText(AtlasConstantes.AVISOS_CONSULTANOVEDADES3);
                mensajes_val.setDisable(false);
                mensajes_val.setVisible(true);
                fecha_inicial.setSelectedDate(fecha_inicial.getSelectedDate());
                fecha_final.setSelectedDate(fecha_final.getSelectedDate());
                final ObservableList<infoTablaNovedades> dataempty = FXCollections.emptyObservableList();
                tablaDatos.setItems(dataempty);
            }
        }
        if (tipo == 3) // solo codigo
        {
            final infoNovedades info = infoNovedades.getInfoNovedades();
            final String codigo = seleccionCodigos.getSelectionModel().getSelectedItem();
            info.setFechainicial("");
            info.setFechaFinal("");
            info.setNovedad(codigo);
            infoNovedades.setNovedades(info);
            ConsultarNovedad(3);
            mensajes_val.setDisable(false);
            mensajes_val.setVisible(false);
        }

    }

    private boolean esSeleccionado(final Calendar calendar) {
        try {
            final int hora = calendar.get(Calendar.HOUR_OF_DAY);
            final int minu = calendar.get(Calendar.MINUTE);
            final int segu = calendar.get(Calendar.SECOND);

//            System.out.println(hora + "-" + minu + "-" + segu);
            if (hora == 0 && minu == 0 && segu == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());
            return false;
        }

    }

    private void ConsultarNovedad(final int tipo) {
        // 1 fi,ff y codigo
        // 2 fi y ff
        // 3 Cod
        tipoConsulta = tipo;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(TaskTablaNovedades.progressProperty());
                    veil.visibleProperty().bind(TaskTablaNovedades.runningProperty());
                    p.visibleProperty().bind(TaskTablaNovedades.runningProperty());
                    tablaDatos.itemsProperty().bind(TaskTablaNovedades.valueProperty());
                    TaskTablaNovedades.reset();
                    TaskTablaNovedades.start();
//                    BuscarNov.setDisable(true);

                    TaskTablaNovedades.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("TERMINO TAREA");
                            ObservableList<infoTablaNovedades> value = TaskTablaNovedades.getValue();
                            tablaDatos.itemsProperty().unbind();
                            tablaDatos.setItems(value);
                            tablaDatos.setVisible(true);
//                            BuscarNov.setDisable(false);

                        }
                    });

                    TaskTablaNovedades.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
//                            System.out.println("ERROR EN LA CONSULTA");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    String message = t.getSource().getException().getMessage();
                                    mensajes_val.setText(message);
                                    mensajes_val.setDisable(false);
                                    mensajes_val.setVisible(true);
                                    // tablaDatos.setVisible(false);
                                    ObservableList<infoTablaNovedades> dataempty = FXCollections.emptyObservableList();
                                    tablaDatos.setItems(dataempty);
//                                    BuscarNov.setDisable(false);
                                }
                            });
                        }
                    });

                    TaskTablaNovedades.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
//                            System.out.println("cancelo la tarea");
                            tablaDatos.setVisible(false);
//                            BuscarNov.setDisable(false);
                        }
                    });

//                    System.out.println("llego hasta aca");
                } catch (Exception ex) {
                    Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());

                }

            }
        });

    }

    @FXML
    void selecCodAction(ActionEvent event) {
    }

    public class GetDatosNovedadiniTask extends Service<ObservableList<InfoTablaNovedadesIni>> {

        @Override
        protected Task<ObservableList<InfoTablaNovedadesIni>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA datos de seguridad datos iniciales        
                    ObservableList<infoTablaNovedades> novedades = FXCollections.observableArrayList();
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramadaNovedadesini = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Date fecha = new Date();

                    tramadaNovedadesini.append("850,016,");
                    tramadaNovedadesini.append(datosCliente.getRefid());
                    tramadaNovedadesini.append(",");
                    tramadaNovedadesini.append(AtlasConstantes.COD_CONSULTA_NOV_INI);//
                    tramadaNovedadesini.append(",");
                    tramadaNovedadesini.append(datosCliente.getId_cliente());
                    tramadaNovedadesini.append(",");
                    tramadaNovedadesini.append(formatoFecha.format(fecha));
                    tramadaNovedadesini.append(",");
                    tramadaNovedadesini.append(formatoHora.format(fecha));
                    tramadaNovedadesini.append(",");
                    tramadaNovedadesini.append("CNV");
                    tramadaNovedadesini.append(",");
                    tramadaNovedadesini.append(datosCliente.getContraseña());
                    tramadaNovedadesini.append(",");
                    tramadaNovedadesini.append(datosCliente.getTokenOauth());
                    tramadaNovedadesini.append(",~");

//                    System.out.println("trama NOVEDADES INI 016" + tramadaNovedadesini.toString());
                    try {
                        Thread.sleep(1000);
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ consulta novedades = " + "##" + gestorDoc.obtenerHoraActual());
//                           Respuesta = "850,012,000,Consulta de datos de seguridad exitosa,N,oe@hotmail.com,P,3002588382,~";

                        //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP, AtlasConstantes.PUERTO_COM_MQ_OTP, tramadaNovedadesini.toString());
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramadaNovedadesini.toString());
//                        System.out.println("respuesta Lista : " + Respuesta);

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Consulta Novedades = " + "##" + gestorDoc.obtenerHoraActual());
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP_CTG, AtlasConstantes.PUERTO_COM_MQ_OTP, tramadaNovedadesini.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramadaNovedadesini.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ConsultaNovedadesOtpController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        failed();

                                    }
                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (ConsultaNovedadesOtpController.cancelartarea.get()) {
                            cancel();
                        } else {

                            try {
                                setEmailCtle(Respuesta.split(",")[6]);
                                setCelularClte(Respuesta.split(",")[5]);
                            } catch (Exception ex) {
                                Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());

                            }

                            //  tablaDatos.setItems(emptyObservableList);
                            novedades = FXCollections.observableArrayList();

                            try {
                                String[] Lnovedades = Respuesta.split(",")[4].split(";");

                                for (int i = 0; i < Lnovedades.length; i++) {
                                    String[] datos = Lnovedades[i].split("##");
//                                    final String fechaIn = datos[2].substring(0, 2)+"/"+datos[2].substring(2, 4)+"/"+datos[2].substring(4, 8);
//                                    final String HoraIn = datos[3].substring(0, 2)+":"+datos[3].substring(2, 4);
                                    final String fechaIn = datos[2].substring(6, 8) + "/" + datos[2].substring(4, 6) + "/" + datos[2].substring(0, 4);
                                    final String HoraIn = datos[3].substring(0, 2) + ":" + datos[3].substring(2, 4);

                                    String dato = "";
                                    if (i == 0) {
                                        dato = "Mecanismo Actual";
                                    }
                                    if (i == 1) {
                                        dato = "Mecanismo Anterior";
                                    }

                                    if (datos[0].equalsIgnoreCase("STK")) {
                                        datos[1] = datos[0];
                                    }

                                    // transaccion , Descripcion, resultado, fecha, hora - Actual
                                    final infoTablaNovedades novedad = new infoTablaNovedades(dato, datos[1], fechaIn, HoraIn, datos[4]);
                                    // transaccion , fecha, hora, Descripcion, resultado - New 
//                                    final infoTablaNovedades novedad = new infoTablaNovedades(dato, fechaIn, HoraIn, datos[1], datos[4]);
                                    novedades.add(novedad);
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(ConsultaNovedadesOtpController.class.getName()).log(Level.SEVERE, ex.toString());

                            }
                        }
                    } else {

                        if (ConsultaNovedadesOtpController.cancelartarea.get()) {
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

                    // updateProgress(500, 500);
                    return novedades;

                }
            };
        }
    }
}
