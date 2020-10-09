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
import com.co.allus.modelo.pagosaterceros.infoPagDebAuto;
import com.co.allus.modelo.pagosterceros.DatosDetallePagos;
import com.co.allus.tareas.BusqIDPagDebAuto;
import com.co.allus.tareas.BusqrefPagDebAuto;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.util.List;
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
public class ConsultaPagDebAutoFinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<infoPagDebAuto, String> ColBanco;
    @FXML
    private TableColumn<infoPagDebAuto, String> ColNomPag;
    @FXML
    private TableColumn<infoPagDebAuto, String> ColTipcta;
    @FXML
    private TableColumn<infoPagDebAuto, String> Colclientepag;
    @FXML
    private TableColumn<infoPagDebAuto, String> ColctaDeb;
    @FXML
    private TableColumn<infoPagDebAuto, String> ColrefFija;
    @FXML
    private RestrictiveTextField clientePag;
    @FXML
    private Button indMasReg;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TextField reffija;
    @FXML
    private TableView<infoPagDebAuto> tablaDatos;
    @FXML
    private Button buscar;
    @FXML
    private Button volverMenupag;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ProgressBar progreso;
    private static GestorDocumental docs = new GestorDocumental();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private transient Service<Void> serviceDetalleFact;
    public static ObservableList<infoPagDebAuto> registros = FXCollections.observableArrayList();
    private Service<ObservableList<infoPagDebAuto>> task = new ConsultaPagDebAutoFinController.GetRegistrosTask();
    public static AtomicInteger numpagina = new AtomicInteger(40);
    public static AtomicInteger totalREg = new AtomicInteger(0);
    public static AtomicInteger acumREg = new AtomicInteger(0);
    public static String indicadorRegistros = "N";
    public static String codigoConv = "";
    public static String tracePaginacion = "";
    public static String tipoConv = "";
    public static AtomicBoolean clearBusqueda = new AtomicBoolean();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert ColBanco != null : "fx:id=\"ColBanco\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert ColNomPag != null : "fx:id=\"ColNomPag\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert ColTipcta != null : "fx:id=\"ColTipcta\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert Colclientepag != null : "fx:id=\"Colclientepag\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert ColctaDeb != null : "fx:id=\"ColctaDeb\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert ColrefFija != null : "fx:id=\"ColrefFija\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert buscar != null : "fx:id=\"buscar\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert clientePag != null : "fx:id=\"clientePag\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert indMasReg != null : "fx:id=\"indMasReg\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert reffija != null : "fx:id=\"reffija\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert tablaDatos != null : "fx:id=\"tablaDatos\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";
        assert volverMenupag != null : "fx:id=\"volverMenupag\" was not injected: check your FXML file 'ConsultaPagDebAutoFin.fxml'.";

        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {

                    buscar.setDisable(false);
                } else {
                    buscar.setDisable(true);
                    tablaDatos.getSelectionModel().clearSelection();
                }
            }
        });

        tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Colclientepag.setCellValueFactory(new PropertyValueFactory<infoPagDebAuto, String>("numdoc"));
        ColNomPag.setCellValueFactory(new PropertyValueFactory<infoPagDebAuto, String>("nompagador"));
        ColctaDeb.setCellValueFactory(new PropertyValueFactory<infoPagDebAuto, String>("numctatarj"));
        ColTipcta.setCellValueFactory(new PropertyValueFactory<infoPagDebAuto, String>("tipoctatarj"));
        ColBanco.setCellValueFactory(new PropertyValueFactory<infoPagDebAuto, String>("nombanco"));
        ColrefFija.setCellValueFactory(new PropertyValueFactory<infoPagDebAuto, String>("reffija"));

        buscar.setDisable(true);
        indMasReg.setDisable(true);
        progreso.setVisible(false);

    }

    public static String getTipoConv() {
        return tipoConv;
    }

    public static void setTipoConv(String tipoConv) {
        ConsultaPagDebAutoFinController.tipoConv = tipoConv;
    }

    public static String getTracePaginacion() {
        return tracePaginacion;
    }

    public static void setTracePaginacion(String tracePaginacion) {
        ConsultaPagDebAutoFinController.tracePaginacion = tracePaginacion;
    }

    public String getCodigoConv() {
        return codigoConv;
    }

    public void setCodigoConv(String codigoConv) {
        ConsultaPagDebAutoFinController.codigoConv = codigoConv;
    }

    public String getIndicadorRegistros() {
        return ConsultaPagDebAutoFinController.indicadorRegistros;
    }

    public void setIndicadorRegistros(String indicadorRegistros) {
        ConsultaPagDebAutoFinController.indicadorRegistros = indicadorRegistros;
    }

    public void mostrarPagDebAuto(final ObservableList<infoPagDebAuto> registross,
            final String indMasReg,
            final String totalReg,
            final int registrosPag,
            final String codigoconv,
            final String tracePaginacion,
            final String tipoConv,
            int origen) {

        if (origen == 1) {
            setCodigoConv(codigoconv);
            setIndicadorRegistros(indMasReg);
            setTipoConv(tipoConv);
            totalREg.set(Integer.parseInt(totalReg));
            numpagina.set(registrosPag);
            acumREg.set(0);
            if ("S".equalsIgnoreCase(indMasReg)) {
                setTracePaginacion(tracePaginacion);
            } else {
                setTracePaginacion("");
            }

            ConsultaPagDebAutoFinController.registros.clear();
            ConsultaPagDebAutoFinController.registros.addAll(registross);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaPagDebAutoFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button botoncontinuarOp = (Button) root.lookup("#buscar");
                    final Button indMasRegistros = (Button) root.lookup("#indMasReg");
                    final Button volverIni = (Button) root.lookup("#volverMenupag");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final TableView<infoPagDebAuto> tablaData = (TableView<infoPagDebAuto>) root.lookup("#tablaDatos");
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
                    pane.setAlignment(Pos.CENTER_LEFT);
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
                                        final List<infoPagDebAuto> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<infoPagDebAuto> subList = registros.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                    root.getChildren().get(root.getChildren().size() - 1).setLayoutY(95);
                    pagination.setVisible(true);
                    Atlas.vista.show();


                } catch (Exception ex) {
                    Logger.getLogger(ConsultaPagDebAutoFinController.class.getName()).log(Level.SEVERE, ex.toString());
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                }

            }
        });

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
                            final ObservableList<infoPagDebAuto> TablaID = task.getValue();

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
                                                    final List<infoPagDebAuto> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoPagDebAuto> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getLogger(ConsultaPagDebAutoFinController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                    Logger.getLogger(ConsultaPagDebAutoFinController.class.getName()).log(Level.SEVERE, null, ex);
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

                    Logger.getLogger(ConsultaPagDebAutoFinController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public class GetRegistrosTask extends Service<ObservableList<infoPagDebAuto>> {

        @Override
        protected Task<ObservableList<infoPagDebAuto>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // setNumpagina(getNumpagina() + 1);
                    final Cliente cliente = Cliente.getCliente();
                    String Respuesta = new String();

                    final StringBuilder tramaPagDebAuto = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    final AtlasAcceso Atlasacceso = new AtlasAcceso();
                    String rnv = "";
                    if (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio())) {
                        rnv = "E";
                    } else {

                        rnv = "P";
                    }

                    //850,069,connid,codigotransaccion,tracepaginacion,empresa/persona( E ó P),identificacion,numRegpag,numRegini,codConv,clavehardware,~
                    tramaPagDebAuto.append("850,069,");
                    tramaPagDebAuto.append(cliente.getRefid());
                    tramaPagDebAuto.append(",");
                    tramaPagDebAuto.append(AtlasConstantes.COD_RECAUDOS_PAG_DEB_AUTO);
                    tramaPagDebAuto.append(",");
                    tramaPagDebAuto.append("S".equalsIgnoreCase(getIndicadorRegistros()) ? getTracePaginacion() : ""); // tracePaginacion
                    tramaPagDebAuto.append(",");
                    tramaPagDebAuto.append(rnv); //regla negocio
                    tramaPagDebAuto.append(",");
                    tramaPagDebAuto.append(cliente.getId_cliente());
                    tramaPagDebAuto.append(",");
                    tramaPagDebAuto.append(numpagina); // registrosCanal
                    tramaPagDebAuto.append(",");
                    tramaPagDebAuto.append(acumREg.addAndGet(numpagina.get())); //registrosPaginacion
                    tramaPagDebAuto.append(",");
                    tramaPagDebAuto.append(getCodigoConv());
                    tramaPagDebAuto.append(",");
                    tramaPagDebAuto.append(cliente.getContraseña());
                    tramaPagDebAuto.append(",");
                    tramaPagDebAuto.append(cliente.getTokenOauth());
                    tramaPagDebAuto.append(",~");

//                    System.out.println("TRAMA insc deb auto :" + tramaPagDebAuto);
                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ movmimientos PES = " + "##" + docs.obtenerHoraActual());

//                        Respuesta = "850,069,000,TRANSACCION EXITOSA,S,25,40,"
//                                + "1%1020416841%Modesto Rosado%25252Nada%0258%BANCOLOMBIA%7%25377634449;"
//                                + "1%1020416843%Modesto Rosado3%Referencia Fija%0258%BANCOLOMBIA%7%25377634449;"
//                                + "1%1020416842%Modesto Rosado2%154265%0258%BANCOLOMBIA%7%25377634449;"
//                                + "1%1020416844%Modesto Rosado4%Referencia Fija2%0258%BANCOLOMBIA%7%25377634449,~";
                        // Respuesta="850,069,000,TRANSACCION EXITOSA,N,000042,105443883789,N,5%5037222286     %                                                                                %561566621                          %1007 %BANCOLOMBIA                                                                     %Cuenta de Ahorros             %00000000040672286002;2%222262         %                                                                                %4587666000                         %1007 %BANCOLOMBIA                                                                     %Cuenta corriente              %00000000040612262000,~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaPagDebAuto.toString());

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES = " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaPagDebAuto.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_TDC);

                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        final String indMasREg = Respuesta.split(",")[4];
                        final String numReg = Respuesta.split(",")[5];
                        final String tracePaginacion = Respuesta.split(",")[6];
                        final String tipoconv = Respuesta.split(",")[7];

                        setTipoConv(tipoconv);
                        setIndicadorRegistros(indMasREg);
                        setTracePaginacion(tracePaginacion);
                        totalREg.set(Integer.parseInt(numReg));

                        String[] LMov = Respuesta.split(",")[8].split(";");

                        for (int i = 0; i < LMov.length; i++) {

                            String[] datos = LMov[i].split("%");

//    private SimpleStringProperty  tipodocpag;
//    private SimpleStringProperty numdoc;
//    private SimpleStringProperty nompagador;
//    private SimpleStringProperty reffija;
//    private SimpleStringProperty codigobanco;
//    private SimpleStringProperty nombanco;
//    private SimpleStringProperty tipoctatarj;
//    private SimpleStringProperty numctatarj; 
                            infoPagDebAuto mov = new infoPagDebAuto(datos[0],
                                    datos[1].trim(),
                                    datos[2].trim(),
                                    datos[3].trim(),
                                    datos[4].trim(),
                                    datos[5].trim(),
                                    datos[6].trim(),
                                    datos[7].trim());

                            registros.add(mov);

                        }

                    } else {
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                acumREg.addAndGet(-numpagina.get());
                            }
                        });

                    }

                    return registros;

                }
            };
        }
    }

    /**
     * BUSCAR POR ID
     */
    private void BusquenaporID(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<infoPagDebAuto>> TaskTablaId = new BusqIDPagDebAuto(id);

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
                            final ObservableList<infoPagDebAuto> TablaID = TaskTablaId.getValue();
                           

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
                                                    final List<infoPagDebAuto> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoPagDebAuto> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(MovRecaudosPSEController.class.getName()).log(Level.SEVERE, null, ex);

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

                    final Service<ObservableList<infoPagDebAuto>> TaskTablaId = new BusqrefPagDebAuto(id);

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
                            final ObservableList<infoPagDebAuto> TablaID = TaskTablaId.getValue();

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
                                                    final List<infoPagDebAuto> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                    tablaDatos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<infoPagDebAuto> subList = TablaID.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    Logger.getLogger(ConsultaPagDebAutoFinController.class.getName()).log(Level.SEVERE, null, ex);

                                }
                                AnchorPane.getChildren().add(pagination);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(0);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(95);
                                pagination.setVisible(true);
                                //Atlas.vista.show(); 
                            } catch (Exception ex) {
                                Logger.getLogger(ConsultaPagDebAutoFinController.class.getName()).log(Level.SEVERE, null, ex);
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
    void clientePagkeyTyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {

            if (isnumber(event.getCharacter())) {
                event.consume();
                //System.out.println("cedula a buscar :" + cedulafindc.getText() + event.getCharacter());
                synchronized (this) {

                    BusquenaporID(clientePag.getText() + event.getCharacter());
                }

            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (clientePag.getText().isEmpty()) {
                        // System.out.println("cargo todos de nuevo");
                        synchronized (this) {

                            BusquenaporID("");

                        }

                    } else {

                        // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                        synchronized (this) {

                            BusquenaporID(clientePag.getText());

                        }
                    }

                }

            }

        }
    }

    @FXML
    void clientePagkeypress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(clientePag, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            clientePag.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void reffijaKeyPress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(reffija, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            reffija.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();

        }
    }

    @FXML
    void reffijakeyTyped(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {
            synchronized (this) {
                BusquedaRef(reffija.getText() + event.getCharacter());
            }
        } else {
            if (event.getCharacter().trim().isEmpty()) {
                if (reffija.getText().isEmpty()) {
                    // System.out.println("cargo todos de nuevo");
                    synchronized (this) {
                        BusquedaRef("");
                    }
                } else {
                    // System.out.println("cedula a buscar 2 :" + cedulafindc.getText());
                    synchronized (this) {
                        BusquedaRef(reffija.getText());
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

    @FXML
    void volverMenupagOP(ActionEvent event) {
        ConsultaPagDebAutoController controller = new ConsultaPagDebAutoController();
        controller.mostrarPagDebAuto(true);
    }

    @FXML
    void detalleOP(ActionEvent event) {
        infoPagDebAuto selectedItem = tablaDatos.getSelectionModel().getSelectedItem();
        DatosDetallePagos datosDetalle = DatosDetallePagos.getDataDetalle();
        datosDetalle.setCodConvenio(getCodigoConv());
        datosDetalle.setNomConvenio("");
        datosDetalle.setRefFija(selectedItem.getReffija());
        datosDetalle.setTipoConv(getTipoConv());
        datosDetalle.setDescripcion("");
        datosDetalle.setFechavencimiento("");
        datosDetalle.setReferencia1("");
        datosDetalle.setReferencia2("");
        datosDetalle.setReferencia3("");
        datosDetalle.setIndicadorReferenciaFija("0");
        // datosDetalle.setSelectedItem(selectedItem);
        DatosDetallePagos.setDataDetalle(datosDetalle);

        continuarDetalle().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                buscar.setDisable(false);
            }
        });

        continuarDetalle().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                buscar.setDisable(false);
            }
        });

        if (continuarDetalle().isRunning()) {
            buscar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuarDetalle().progressProperty());
            continuarDetalle().reset();
            continuarDetalle().start();

        } else {
            buscar.setDisable(true);
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
                        tramaDetalleFactura.append(tablaDatos.getSelectionModel().getSelectedItem().getTipodocpag());
                        tramaDetalleFactura.append(",");
                        tramaDetalleFactura.append(tablaDatos.getSelectionModel().getSelectedItem().getNumdoc());
                        tramaDetalleFactura.append(",");
                        tramaDetalleFactura.append(datosCliente.getContraseña());
                        tramaDetalleFactura.append(",");
                        tramaDetalleFactura.append(datosCliente.getTokenOauth());
                        tramaDetalleFactura.append(",~");

//                        System.out.println("TRAMA DETALLE FACTURA :" + tramaDetalleFactura);
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Trama detalle = " + "##" + docs.obtenerHoraActual());

                            //  Respuesta = "850,067,000,TRANSACCION EXITOSA,Y,0  , , ,0 ,0,20/02/2018,31/12/2099,0000000076788889,18/04/2018,ACTIVA                                                                          ,APP PERSONAS                  ,0007  ,BANCOLOMBIA                                                                     ,5470620251552019    ,Tarjeta                       ,33333                              ,                                   ,2232                               ,1,~";
                            // Respuesta = "850,067,000,TRANSACCION EXITOSA,D,0  , , ,0 ,0,13/04/2018,20/04/2018,0000000000000099,14/04/2018,ACTIVA                                                                          ,SUCURSAL FÍSICA               ,0007  ,BANCOLOMBIA                                                                     ,5470620251552019    ,Tarjeta                       ,999999999999999999999              ,1234567                            ,1234567                            ,1,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaDetalleFactura.toString());
                            //  System.out.println("RESPUESTA DETALLE CONV:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion entrega  = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaDetalleFactura.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

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
                            datosDetalle.setCodConvenio(Respuesta.split(",")[24].trim());
                            datosDetalle.setNomConvenio(Respuesta.split(",")[25].trim());
                            datosDetalle.setCriteriopago(Respuesta.split(",")[26].trim());
                            datosDetalle.setPagoparcial(Respuesta.split(",")[27].trim());

//
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final PagosATercerosDetalleController controller = new PagosATercerosDetalleController();
                                    controller.mostrarDetalleFactura(DatosDetallePagos.getDataDetalle(), 1);
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

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
