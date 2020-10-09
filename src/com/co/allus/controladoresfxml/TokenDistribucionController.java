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
import com.co.allus.modelo.tokendistribucion.DatosDisToken;
import com.co.allus.modelo.tokendistribucion.InfoTokenContacto;
import com.co.allus.modelo.tokendistribucion.InfoTokenDistribucion;
import com.co.allus.modelo.tokendistribucion.InfoTokenEntrega;
import com.co.allus.modelo.tokendistribucion.InfoTokenTele;
import com.co.allus.tareas.tokendistribucion.BusqIdTask;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.util.ArrayList;
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
public class TokenDistribucionController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn colContactoEnt;
    @FXML
    private TableColumn colGestionEnt;
    @FXML
    private TableColumn colGestionTele;
    @FXML
    private TableColumn colGuia;
    @FXML
    private TableColumn colID;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colSerial;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TableView<InfoTokenDistribucion> tabla_datos;
    @FXML
    private Button terminar_trx;
    @FXML
    private RestrictiveTextField tfBuscar;
    @FXML
    private TextField tfNit;
    @FXML
    private Button obtMasReg;
    public static ObservableList<InfoTokenDistribucion> tokendistribuidos = FXCollections.observableArrayList();
    private Service<ObservableList<InfoTokenDistribucion>> task = new TokenDistribucionController.GetTokenDistTask();
    private static GestorDocumental docs = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private transient Service<Void> serviceGesTel;
    private transient Service<Void> serviceGesEnt;
    private transient Service<Void> serviceConEnt;
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
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert colContactoEnt != null : "fx:id=\"colContactoEnt\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert colGestionEnt != null : "fx:id=\"colGestionEnt\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert colGestionTele != null : "fx:id=\"colGestionTele\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert colGuia != null : "fx:id=\"colGuia\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert colID != null : "fx:id=\"colID\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert colNombre != null : "fx:id=\"colNombre\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert colSerial != null : "fx:id=\"colSerial\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert tfBuscar != null : "fx:id=\"tfBuscar\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert tfNit != null : "fx:id=\"tfNit\" was not injected: check your FXML file 'TokenDistribucion.fxml'.";
        assert obtMasReg != null : "fx:id=\"obtMasReg\" was not injected: check your FXML file 'TokenEmpresas.fxml'.";
        this.location = url;
        this.resources = rb;

        TokenDistribucionController.cancelartarea.set(false);
        tabla_datos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        progreso.setVisible(false);
        colNombre.setCellValueFactory(new PropertyValueFactory<InfoTokenDistribucion, String>("colNombre"));
        colID.setCellValueFactory(new PropertyValueFactory<InfoTokenDistribucion, String>("colID"));
        colSerial.setCellValueFactory(new PropertyValueFactory<InfoTokenDistribucion, String>("colSerial"));
        colGuia.setCellValueFactory(new PropertyValueFactory<InfoTokenDistribucion, String>("colGuia"));
        colGestionTele.setCellValueFactory(new PropertyValueFactory<InfoTokenDistribucion, String>("colGestionTele"));
        colGestionEnt.setCellValueFactory(new PropertyValueFactory<InfoTokenDistribucion, String>("colGestionEnt"));
        colContactoEnt.setCellValueFactory(new PropertyValueFactory<InfoTokenDistribucion, String>("colContactoEnt"));

        tabla_datos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                if (tabla_datos.getSelectionModel().getSelectedItem() != null) {
                    //System.out.println("Tiene datos");
                } else {

                    tabla_datos.getSelectionModel().clearSelection();
                }
            }
        });

        colGestionTele.setCellFactory(new Callback<TableColumn<InfoTokenDistribucion, String>, TableCell<InfoTokenDistribucion, String>>() {
            @Override
            public TableCell<InfoTokenDistribucion, String> call(TableColumn<InfoTokenDistribucion, String> p) {
                return new ButtonTableCell<InfoTokenDistribucion, String>();

            }
        });

        colGestionEnt.setCellFactory(new Callback<TableColumn<InfoTokenDistribucion, String>, TableCell<InfoTokenDistribucion, String>>() {
            @Override
            public TableCell<InfoTokenDistribucion, String> call(TableColumn<InfoTokenDistribucion, String> p) {
                return new ButtonTableCellEntrega<InfoTokenDistribucion, String>();

            }
        });

        colContactoEnt.setCellFactory(new Callback<TableColumn<InfoTokenDistribucion, String>, TableCell<InfoTokenDistribucion, String>>() {
            @Override
            public TableCell<InfoTokenDistribucion, String> call(TableColumn<InfoTokenDistribucion, String> p) {
                return new ButtonTableCellContacto<InfoTokenDistribucion, String>();

            }
        });

        obtMasReg.setDisable(true);
        setIndicadorRegistros("N");
        numpagina.set(0);

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
                            final ObservableList<InfoTokenDistribucion> TablaID = task.getValue();


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
                                                    final List<InfoTokenDistribucion> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTokenDistribucion> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + rowsPerPageId());
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
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(132);
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

    /**
     * HIPERVINCULO GESTION TELEFONICA
     *
     * @param event
     */
    public class ButtonTableCell<S, T> extends TableCell<S, T> {

        final Button button = new Button();
        private ObservableValue<T> ov;

        public ButtonTableCell() {

            button.setPrefWidth(165);
            button.setPrefHeight(9);
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
                    InfoTokenDistribucion get = (InfoTokenDistribucion) getTableView().getItems().get(getIndex());
                    continuarGesTel(event, get);
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

                Label lbl = new Label();
                lbl.setText(ov.getValue().toString());
                button.setText(lbl.getText());

                if (ov instanceof StringProperty) {
                    button.textProperty().bindBidirectional((StringProperty) ov);

                }

            }
            //cantidad es System.out.println("texto es:" + token.size());

        }

        public void continuarGesTel(final ActionEvent event, InfoTokenDistribucion selectedItem) {

            DatosDisToken datos = DatosDisToken.getDatosdistri();
            datos.setCodigo_guia(selectedItem.getColGuia());
            datos.setCodigo_serial(selectedItem.getColSerial());
            datos.setId_usuario(selectedItem.getColID());
            datos.setNombre_usuario(selectedItem.getColNombre());
            DatosDisToken.setDatosdistri(datos);

            continuarGesTel().setOnSucceeded(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle token" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            continuarGesTel().setOnCancelled(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle token" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            if (continuarGesTel().isRunning()) {
                // button.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(continuarGesTel().progressProperty());
                continuarGesTel().reset();
                continuarGesTel().start();

            } else {
                //button.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(continuarGesTel().progressProperty());
                continuarGesTel().start();
            }
        }

        public Service<Void> continuarGesTel() {
            serviceGesTel = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            String Respuesta = new String();

                            final Cliente datosCliente = Cliente.getCliente();
                            final StringBuilder tramaTokenDisEmpresas = new StringBuilder();
                            final ConectSS servicioSS = new ConectSS();
                            final Cliente cliente = Cliente.getCliente();

                            //850,035,connid,codTransaccion,tipoDoc,identificacion,requiereResp,opcionConsulta,CodigoGuia,CodigoSerial,clavehardware
                            tramaTokenDisEmpresas.append("850,035,");
                            tramaTokenDisEmpresas.append(datosCliente.getRefid());
                            tramaTokenDisEmpresas.append(",");
                            tramaTokenDisEmpresas.append(AtlasConstantes.COD_TOKEN_DIST);
                            tramaTokenDisEmpresas.append(",");
                            tramaTokenDisEmpresas.append("1");
                            tramaTokenDisEmpresas.append(",");
                            tramaTokenDisEmpresas.append(datosCliente.getId_cliente());
                            tramaTokenDisEmpresas.append(",");
                            final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                            tramaTokenDisEmpresas.append(contra);
                            tramaTokenDisEmpresas.append(",");
                            tramaTokenDisEmpresas.append("04");
                            tramaTokenDisEmpresas.append(",");
                            tramaTokenDisEmpresas.append(DatosDisToken.getDatosdistri().getCodigo_guia());//NUM GUIA
                            tramaTokenDisEmpresas.append(",");
                            tramaTokenDisEmpresas.append("");//NUM SERIAL
                            tramaTokenDisEmpresas.append(",");
                            tramaTokenDisEmpresas.append("1");
                            tramaTokenDisEmpresas.append(",");
                            final String chw = datosCliente.getContraseña().isEmpty() ? " " : datosCliente.getContraseña();
                            tramaTokenDisEmpresas.append(chw);
                            tramaTokenDisEmpresas.append(",");
                            tramaTokenDisEmpresas.append(datosCliente.getTokenOauth());
                            tramaTokenDisEmpresas.append(",~");

                            //  System.out.println("TRAMA TOKENDIST TELE :" + tramaTokenDisEmpresas);
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token distribucion = " + "##" + docs.obtenerHoraActual());
                                //EstadoGestion%FechaEnvio%NombreCiudad%Resultado%Observcion%NumeroTelefono

//                            Respuesta = "850,"
//                                    + "035,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "NOMBRE COMPLETO,"
//                                    + "COD SERIAL,"
//                                    + "COD GUIA,"
//                                    + "NOM PROOVEDOR,"
//                                    + "02/10/2017,"
//                                    + "MAÑANA,"
//                                    + "90,"
//                                    + "S,"
//                                    + "20/02/2017%30072016%Bogotá%Bogota%Gestion ok%Gestion Tel%observaciones;"
//                                    + "21/02/2017%30072017%Bogotá%Bogota%Gestion ok2%Gestion Tel2%observaciones2,"
//                                    + "~";
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDisEmpresas.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDisEmpresas.toString());
                                // System.out.println("RESPUESTA TRAMA TOKENDIST TELE:" + Respuesta);
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex) {

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion = " + "##" + docs.obtenerHoraActual());
                                    // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDisEmpresas.toString());
                                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDisEmpresas.toString());
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

                                final String indMasReg = Respuesta.split(",")[11];
                                String data = Respuesta.split(",")[12];

                                final List<InfoTokenTele> lista = new ArrayList<InfoTokenTele>();
                                String[] detalles = data.split(";");
                                for (int i = 0; i < detalles.length; i++) {
                                    final String[] datamov = detalles[i].split("%");
//
//                                   String colEstado,
//                                   String colFecha,
//                                   String colCiudad,
//                                   String colResultado,
//                                   String colObservacion,
//                                   String colTelefono
//                                    String coljornada,
//                                   String colfechavisita

                                    final InfoTokenTele ObjMov = new InfoTokenTele(
                                            "", // ESTADO NO LLEGA EN LA TRAMA
                                            datamov[0].trim(),
                                            datamov[2].trim(),
                                            datamov[4].trim(),
                                            datamov.length == 7 ? datamov[6].trim() : "",
                                            datamov[1].trim(),
                                            Respuesta.split(",")[9],
                                            Respuesta.split(",")[8]);

                                    lista.add(ObjMov);
                                }

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final TokenDistribucionTeleController controller = new TokenDistribucionTeleController();
                                        controller.mostrarTokenDistTele(lista, DatosDisToken.getDatosdistri(), indMasReg, 1);
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

            return serviceGesTel;

        }
    }

    /**
     * FIN
     *
     * @param event
     */
    /**
     * HIPERVINCULO GESTION ENTREGA
     *
     * @param event
     */
    public class ButtonTableCellEntrega<S, T> extends TableCell<S, T> {

        final Button button = new Button();
        private ObservableValue<T> ov;

        public ButtonTableCellEntrega() {

            button.setPrefWidth(140);
            button.setPrefHeight(9);
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
                    // System.out.println(button.getText());
                    InfoTokenDistribucion get = (InfoTokenDistribucion) getTableView().getItems().get(getIndex());
                    // System.out.println(get.getColID());
                    continuarGesEnt(event, get);
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

                Label lbl = new Label();
                lbl.setText(ov.getValue().toString());
                button.setText(lbl.getText());

                if (ov instanceof StringProperty) {
                    button.textProperty().bindBidirectional((StringProperty) ov);

                }

            }
            //cantidad es System.out.println("texto es:" + token.size());

        }

        public void continuarGesEnt(final ActionEvent event, InfoTokenDistribucion selectedItem) {

            DatosDisToken datos = DatosDisToken.getDatosdistri();
            datos.setCodigo_guia(selectedItem.getColGuia());
            datos.setCodigo_serial(selectedItem.getColSerial());
            datos.setId_usuario(selectedItem.getColID());
            datos.setNombre_usuario(selectedItem.getColNombre());
            DatosDisToken.setDatosdistri(datos);

            continuarGesEnt().setOnSucceeded(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle token" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            continuarGesEnt().setOnCancelled(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle token" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            if (continuarGesEnt().isRunning()) {
                //button.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(continuarGesEnt().progressProperty());
                continuarGesEnt().reset();
                continuarGesEnt().start();

            } else {
                // button.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(continuarGesEnt().progressProperty());
                continuarGesEnt().start();
            }
        }

        public Service<Void> continuarGesEnt() {
            serviceGesEnt = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            String Respuesta = new String();

                            final Cliente datosCliente = Cliente.getCliente();
                            final StringBuilder tramaTokenDistEntrega = new StringBuilder();
                            final ConectSS servicioSS = new ConectSS();
                            final Cliente cliente = Cliente.getCliente();

                            //850,035,connid,codTransaccion,tipoDoc,identificacion,requiereResp,opcionConsulta,CodigoGuia,CodigoSerial,clavehardware
                            tramaTokenDistEntrega.append("850,035,");
                            tramaTokenDistEntrega.append(datosCliente.getRefid());
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(AtlasConstantes.COD_TOKEN_DIST);
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append("1");
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(datosCliente.getId_cliente());
                            tramaTokenDistEntrega.append(",");
                            final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                            tramaTokenDistEntrega.append(contra);
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append("05");
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(DatosDisToken.getDatosdistri().getCodigo_guia());//NUM GUIA
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(StringUtilities.eliminarCerosLeft(DatosDisToken.getDatosdistri().getCodigo_serial()));//NUM SERIAL
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append("1");
                            tramaTokenDistEntrega.append(",");
                            final String chw = datosCliente.getContraseña().isEmpty() ? " " : datosCliente.getContraseña();
                            tramaTokenDistEntrega.append(chw);
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(datosCliente.getTokenOauth());
                            tramaTokenDistEntrega.append(",~");

                            // System.out.println("TRAMA TOKENDIST ENT :" + tramaTokenDistEntrega);
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token diustribucion entrega  = " + "##" + docs.obtenerHoraActual());
                                //DescripcionEstado%FechaEnvio%GestionEntrega%Direccion%NombreCiudad%Observacion

//                            Respuesta = "850,"
//                                    + "035,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "NOMBRE,"
//                                    + "COD SERIAL,"
//                                    + "CODIGO GUIA,"
//                                    + "NOM PROOVEDOR,"
//                                    + "150,"
//                                    + "S,"
//                                    + "30072016%CALLE FALSA 123%BOGOTA%BOGOTA%ENTREGA EFECTIVA%ENTREGADO%TOKEN 1 OBS;"
//                                    + "30072017%CALLE FALSA 124%BOGOTA%BOGOTA%ENTREGA EFECTIVA%ENTREGADO%TOKEN 2 OBS,"
//                                    + "~";
                                // System.out.println("RESPUESTA TRAMA TOKENDIST ENT:" + Respuesta);
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDistEntrega.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDistEntrega.toString());

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex) {

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion entrega = " + "##" + docs.obtenerHoraActual());
                                    //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDistEntrega.toString());
                                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDistEntrega.toString());
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

                                try {
                                    String data = Respuesta.split(",")[10];
                                    final String indMasReg = Respuesta.split(",")[9];

                                    final List<InfoTokenEntrega> listaEnt = new ArrayList<InfoTokenEntrega>();
                                    String[] detalles = data.split(";");
                                    for (int i = 0; i < detalles.length; i++) {
                                        final String[] datamov = detalles[i].split("%");

//                                    String colEstado,
//                                    String colFecha,
//                                    String colResultado,
//                                    String colDireccion,
//                                    String colCiudad,
//                                    String colObservacion
                                        final InfoTokenEntrega ObjMov = new InfoTokenEntrega(
                                                datamov[5].trim(),
                                                datamov[0].trim(),
                                                datamov[4].trim(),
                                                datamov[1].trim(),
                                                datamov[2].trim(),
                                                datamov.length == 7 ? datamov[6].trim() : "");

                                        listaEnt.add(ObjMov);
                                    }

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            final TokenDistribucionEntregaController controller = new TokenDistribucionEntregaController();
                                            controller.mostrarTokenDistEnt(listaEnt, DatosDisToken.getDatosdistri(), indMasReg, 1);
                                        }
                                    });

                                } catch (Exception e) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ModalMensajes("NO SE RETORNARON REGISTROS", "999", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            cancel();
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);

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
                                    }
                                });

                            }

                            return null;
                        }
                    };
                }
            };

            return serviceGesEnt;

        }
    }

    /**
     * FIN
     *
     * @return
     */
    /**
     * HIPERVINCULO CONTACTO ENTREGA
     *
     * @param event
     */
    public class ButtonTableCellContacto<S, T> extends TableCell<S, T> {

        final Button button = new Button();
        private ObservableValue<T> ov;

        public ButtonTableCellContacto() {

            button.setPrefWidth(100);
            button.setPrefHeight(9);
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
                    // System.out.println(button.getText());
                    InfoTokenDistribucion get = (InfoTokenDistribucion) getTableView().getItems().get(getIndex());
                    // System.out.println(get.getColID());
                    continuarGesContac(event, get);
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

                Label lbl = new Label();
                lbl.setText(ov.getValue().toString());
                button.setText(lbl.getText());

                if (ov instanceof StringProperty) {
                    button.textProperty().bindBidirectional((StringProperty) ov);

                }

            }

            //cantidad es System.out.println("texto es:" + token.size());
        }

        public void continuarGesContac(final ActionEvent event, InfoTokenDistribucion selectedItem) {

            DatosDisToken datos = DatosDisToken.getDatosdistri();
            datos.setCodigo_guia(selectedItem.getColGuia());
            datos.setCodigo_serial(selectedItem.getColSerial());
            datos.setId_usuario(selectedItem.getColID());
            datos.setNombre_usuario(selectedItem.getColNombre());
            DatosDisToken.setDatosdistri(datos);

            continuarGesContac().setOnSucceeded(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono ver detalle token" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            continuarGesContac().setOnCancelled(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo ver detalle token" + "##" + docs.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            if (continuarGesContac().isRunning()) {
                // button.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(continuarGesContac().progressProperty());
                continuarGesContac().reset();
                continuarGesContac().start();

            } else {
                //button.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(continuarGesContac().progressProperty());
                continuarGesContac().start();
            }
        }

        public Service<Void> continuarGesContac() {
            serviceConEnt = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            String Respuesta = new String();

                            final Cliente datosCliente = Cliente.getCliente();
                            final StringBuilder tramaTokenDistEntrega = new StringBuilder();
                            final ConectSS servicioSS = new ConectSS();
                            final Cliente cliente = Cliente.getCliente();

                            //850,035,connid,codTransaccion,tipoDoc,identificacion,requiereResp,opcionConsulta,CodigoGuia,CodigoSerial,clavehardware
                            tramaTokenDistEntrega.append("850,035,");
                            tramaTokenDistEntrega.append(datosCliente.getRefid());
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(AtlasConstantes.COD_TOKEN_DIST);
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append("1");
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(datosCliente.getId_cliente());
                            tramaTokenDistEntrega.append(",");
                            final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                            tramaTokenDistEntrega.append(contra);
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append("06");
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(DatosDisToken.getDatosdistri().getCodigo_guia());//NUM GUIA
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(StringUtilities.eliminarCerosLeft(DatosDisToken.getDatosdistri().getCodigo_serial()));//NUM SERIAL
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append("1");
                            tramaTokenDistEntrega.append(",");
                            final String chw = datosCliente.getContraseña().isEmpty() ? " " : datosCliente.getContraseña();
                            tramaTokenDistEntrega.append(chw);
                            tramaTokenDistEntrega.append(",");
                            tramaTokenDistEntrega.append(datosCliente.getTokenOauth());
                            tramaTokenDistEntrega.append(",~");

                            // System.out.println("TRAMA TOKEN CONTACTOENT :" + tramaTokenDistEntrega);
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token distribucion entrega = " + "##" + docs.obtenerHoraActual());
                                //DescripcionEstado%FechaEnvio%GestionEntrega%Direccion%NombreCiudad%Observacion

//                            Respuesta = "850,"
//                                    + "035,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "NOMBRE COMPPLETO,"
//                                    + "COD SERIAL,"
//                                    + "COD GUIA,"
//                                    + "NOM PROVEEDOR,"
//                                    + "050,"
//                                    + "S,"
//                                    + "tipo contacto 1%SANDRA CECILLIA%1%71025536%3232568%5102356%3115236859%CALLE FALSA #123%ANTIOQUIA%MEDELLIN%SUC CARABOBO%ALEXANDER LOPEZ%1020416841, "
//                                    //+ "SANDRA CECILIA VELANDIA%2663333%2336666%3113455667%CRA 30 52 14%BOGOTÁ%BOGOTÁ%TipoDireccion%SANTIAGO CHAVERRA%71654789%Nombre Contacto,"
//                                    + "~";
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDistEntrega.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDistEntrega.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                            } catch (Exception ex) {

                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion entrega  = " + "##" + docs.obtenerHoraActual());
                                    //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDistEntrega.toString());
                                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDistEntrega.toString());
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

                                String data = Respuesta.split(",")[10];
                                final String indicadorMasReg = Respuesta.split(",")[9];
                                final List<InfoTokenContacto> listaCont = new ArrayList<InfoTokenContacto>();
                                String[] detalles = data.split(";");
                                for (int i = 0; i < detalles.length; i++) {
                                    final String[] datamov = detalles[i].split("%");

//                                    String colNombreComp,
//                                    String colNumTelefono,
//                                    String colNumTelAlterno,
//                                    String colNumCel,
//                                    String colDireccion,
//                                    String colCodDepartamento,
//                                    String colCodCiudad,
//                                    String colTipoDireccion,
//                                    String colNombreMensajero,
//                                    String colNumCcMensajero,
//                                    String colNombreContacto
//                                    String colsucursal,
//                                    String colTipoContacto
//                                    String tipodoc
//                                    String docummento
                                    final InfoTokenContacto ObjMov = new InfoTokenContacto(
                                            datamov[1].trim(),
                                            datamov[4].trim(),
                                            datamov[5].trim(),
                                            datamov[6].trim(),
                                            datamov[7].trim(),
                                            datamov[8].trim(),
                                            datamov[9].trim(),
                                            "",
                                            datamov[11].trim(),
                                            datamov.length == 13 ? datamov[12].trim() : "",
                                            "",
                                            datamov[10].trim(),
                                            datamov[0].trim(),
                                            datamov[2].trim(),
                                            datamov[3].trim());

                                    listaCont.add(ObjMov);
                                }

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final TokenDistribucionContactoController controller = new TokenDistribucionContactoController();
                                        controller.mostrarTokenContEnt(listaCont, DatosDisToken.getDatosdistri(), indicadorMasReg, 1);
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

            return serviceConEnt;

        }
    }

    @FXML
    void terminar_trx(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();

                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 9;
    }

    public int rowsPerPageId() {
        return 9;
    }

    public void mostrarTokenDistribuidos() {
        tokendistribuidos.clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/TokenDistribucion.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final TextField tfNit = (TextField) root.lookup("#tfNit");
                    final TextField tfBuscar = (TextField) root.lookup("#tfBuscar");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final Button obtMasReg = (Button) root.lookup("#obtMasReg");

                    final TableView<InfoTokenDistribucion> tablaData = (TableView<InfoTokenDistribucion>) root.lookup("#tabla_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    label_menu.setVisible(false);

                    /**
                     * barra progreso
                     */
                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);

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

                            if ("S".equals(getIndicadorRegistros())) {
                                obtMasReg.setDisable(false);
                            } else {
                                obtMasReg.setDisable(true);
                            }

                            tablaData.itemsProperty().unbind();
                            /**
                             * configuracion de la paginacion
                             */
                            final int numpag = tokendistribuidos.size() % rowsPerPage() == 0 ? tokendistribuidos.size() / rowsPerPage() : tokendistribuidos.size() / rowsPerPage() + 1;

                            pagination = new Pagination(numpag, 0);
                            pagination.setPageFactory(new Callback<Integer, Node>() {
                                @Override
                                public Node call(final Integer pageIndex) {

                                    if (pageIndex > numpag) {
                                        return null;
                                    } else {
                                        int lastIndex = 0;
                                        int displace = tokendistribuidos.size() % rowsPerPage();
                                        if (displace >= 0) {
                                            lastIndex = tokendistribuidos.size() / rowsPerPage();
                                        } else {
                                            lastIndex = tokendistribuidos.size() / rowsPerPage() - 1;
                                        }
                                        int page = pageIndex * itemsPerPage();

                                        for (int i = page; i < page + itemsPerPage(); i++) {

                                            if (lastIndex == pageIndex) {
                                                final List<InfoTokenDistribucion> subList = tokendistribuidos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                                tablaData.setItems(FXCollections.observableArrayList(subList));
                                            } else {
                                                final List<InfoTokenDistribucion> subList = tokendistribuidos.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                            tablaData.getItems().setAll(tokendistribuidos.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= tokendistribuidos.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : tokendistribuidos.size())));
                                        }
                                    });

                                }
                            });

                            /**
                             * fin configuracion de la paginacion
                             */
                            root.getChildren().add(pagination);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(1);
                            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(132);
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
                        }
                    });

                } catch (Exception e) {

                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });

    }

    private boolean isAlphanum(String val) {
        boolean retorno = false;
        String pattern = "^[a-zA-Z0-9]*$";
        //String pattern= "[a-zA-Z0-9]";
        if (val.matches(pattern)) {
            retorno = true;
        }
        return retorno;
    }

    private void BusqIdTask(final String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final Service<ObservableList<InfoTokenDistribucion>> TaskTablaDist = new BusqIdTask(id);

                    /**
                     * barra progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    p.progressProperty().bind(TaskTablaDist.progressProperty());
                    veil.visibleProperty().bind(TaskTablaDist.runningProperty());
                    p.visibleProperty().bind(TaskTablaDist.runningProperty());
                    tabla_datos.itemsProperty().bind(TaskTablaDist.valueProperty());
                    TaskTablaDist.reset();
                    TaskTablaDist.start();

                    TaskTablaDist.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                            tabla_datos.itemsProperty().unbind();
                            final ObservableList<InfoTokenDistribucion> TablaID = TaskTablaDist.getValue();


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
                                                    final List<InfoTokenDistribucion> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + displace);
                                                    tabla_datos.setItems(FXCollections.observableArrayList(subList));
                                                } else {
                                                    final List<InfoTokenDistribucion> subList = TablaID.subList(pageIndex * rowsPerPageId(), pageIndex * rowsPerPageId() + rowsPerPageId());
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
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutX(1);
                                AnchorPane.getChildren().get(AnchorPane.getChildren().size() - 1).setLayoutY(132);
                                pagination.setVisible(true);

                            } catch (Exception e) {
                                Logger.getGlobal().log(Level.SEVERE, e.toString());
                            }

                        }
                    });

                    TaskTablaDist.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {

                        }
                    });

                    TaskTablaDist.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(final WorkerStateEvent t) {
                            
                        }
                    });

                } catch (Exception ex) {

                    Logger.getLogger(TokenDistribucionController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    @FXML
    void idbuscarkeytyped(final KeyEvent event) {

        if (event.getEventType().equals(KeyEvent.KEY_TYPED)) {
            if (isAlphanum(event.getCharacter())) {
                event.consume();
                synchronized (this) {
                    BusqIdTask(tfBuscar.getText() + event.getCharacter());
                }
            } else {
                if (event.getCharacter().trim().isEmpty()) {
                    if (tfBuscar.getText().isEmpty()) {
                        synchronized (this) {
                            BusqIdTask("");
                        }
                    } else {
                        synchronized (this) {
                            BusqIdTask(tfBuscar.getText());
                        }
                    }

                }
            }
        }
    }

    @FXML
    void idbuscarkeypress(final KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode()) || KeyCode.BACK_SPACE.equals(event.getCode())) {

            KeyEvent keyEvent = KeyEvent.impl_keyEvent(tfBuscar, "*", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
            tfBuscar.fireEvent(keyEvent);
        } else if (KeyCode.SPACE.equals(event.getCode())) {

            event.consume();
        }
    }

    public class GetTokenDistTask extends Service<ObservableList<InfoTokenDistribucion>> {

        @Override
        protected Task<ObservableList<InfoTokenDistribucion>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();

                    final StringBuilder tramaTokenDistribucion = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    //850,035,connid,codTransaccion,tipoDoc,identificacion,requiereResp,opcionConsulta,CodigoGuia,CodigoSerial,clavehardware

                    tramaTokenDistribucion.append("850,035,");
                    tramaTokenDistribucion.append(datosCliente.getRefid());
                    tramaTokenDistribucion.append(",");
                    tramaTokenDistribucion.append(AtlasConstantes.COD_TOKEN_DIST);
                    tramaTokenDistribucion.append(",");
                    tramaTokenDistribucion.append("1");
                    tramaTokenDistribucion.append(",");
                    tramaTokenDistribucion.append(datosCliente.getId_cliente());
                    tramaTokenDistribucion.append(",");
                    final String contra = datosCliente.getContraseña().isEmpty() ? "T" : "C";
                    tramaTokenDistribucion.append(contra);
                    tramaTokenDistribucion.append(",");
                    tramaTokenDistribucion.append("03");
                    tramaTokenDistribucion.append(",");
                    tramaTokenDistribucion.append("");//NUM GUIA
                    tramaTokenDistribucion.append(",");
                    tramaTokenDistribucion.append("");//NUM SERIAL
                    tramaTokenDistribucion.append(",");
                    tramaTokenDistribucion.append(numpagina.incrementAndGet());
                    tramaTokenDistribucion.append(",");
                    final String chw = datosCliente.getContraseña().isEmpty() ? " " : datosCliente.getContraseña();
                    tramaTokenDistribucion.append(chw);
                    tramaTokenDistribucion.append(",");
                    tramaTokenDistribucion.append(datosCliente.getTokenOauth());
                    tramaTokenDistribucion.append(",~");

                    // System.out.println("trama TokenEmpresas " + tramaTokenDistribucion.toString());
                    if (MarcoPrincipalController.newTokenDistribuidos) {
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ token distribucion = " + "##" + docs.obtenerHoraActual());
                            //Nombre Completo%ID%Cod Serial%Codigo Guia%Nombre Proveedor%Gestion Telefonica%Gestion Entrega%Contacto Entrega
//                            Respuesta = "850,"
//                                    + "035,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "NOMBRE COMPAÑIA,"
//                                    + "150,"
//                                    + "S,"
//                                    + "CLARA DAVILA%TX1111%107108304%GP000003122442%RICARDO RUIZ%1EFECTIVO%ENTREGA EFECTIVA%USUARIO;"
//                                    + "SANDRA CECILIA VELANDIA%TX7AUTO164%107108310%GP000009674568%RICARDO RUIZ%2EFECTIVO%ENTREGA EFECTIVA%USUARIO;"
//                                    + "YEIMY BELTRAN%TX8AUTO164%107109945%GP000009089789%RICARDO RUIZ%3EFECTIVO%ENTREGA NO EFECTIVA%USUARIO;"
//                                    + "YEIMY BELTRAN%TX8AUTO164%107109945%GP000009089789%RICARDO RUIZ%EFECTIVO%ENTREGA EFECTIVA%USUARIO;"
//                                    + "YEIMY BELTRAN%TX8AUTO164%107109945%GP000009089789%RICARDO RUIZ%EFECTIVO%ENTREGA EFECTIVA%USUARIO;"
//                                    + "YEIMY BELTRAN%TX8AUTO164%107109945%GP000009089789%RICARDO RUIZ%EFECTIVO%ENTREGA EFECTIVA%USUARIO;"
//                                    + "YEIMY BELTRAN%TX8AUTO164%107109945%GP000009089789%RICARDO RUIZ%EFECTIVO%ENTREGA EFECTIVA%USUARIO;"
//                                    + "YEIMY BELTRAN%TX8AUTO164%107109945%GP000009089789%RICARDO RUIZ%EFECTIVO%ENTREGA EFECTIVA%USUARIO;"
//                                    + "YEIMY BELTRAN%TX8AUTO164%107109945%GP000009089789%RICARDO RUIZ%EFECTIVO%ENTREGA EFECTIVA%USUARIO;"
//                                    + "YEIMY BELTRAN%TX8AUTO164%107109945%GP000009089789%RICARDO RUIZ%EFECTIVO%ENTREGA EFECTIVA%USUARIO,"
//                                    + "~";
                            // Respuesta="850,035,000,TRANSACCI?N NO EFECTUADA                                              ,,002,N,JOHANNA MARCELA  CASTIBLANCO                                          %               %00000000000123456789%31686083             %DOMINA ENTREGA TOTAL                    %DEV. NO EXISTE                                    %*                                                 %USUARIO                                           ;LAURA VALENTINA PALACIOS FAJAR                                        %               %00000000000123455694%31686084             %DOMINA ENTREGA TOTAL                    %ASIGNADO A CALL CENTER                            %*                                                 %USUARIO                                           ,~";

                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDistribucion.toString());
                            //System.out.println(" RESPUESTA TRAMA TOKEN EMPRESAS:" + Respuesta);
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDistribucion.toString());

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ token distribucion  = " + "##" + docs.obtenerHoraActual());
                                //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaTokenDistribucion.toString());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaTokenDistribucion.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (TokenDistribucionController.cancelartarea.get()) {
                                            cancel();
                                            numpagina.decrementAndGet();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            numpagina.decrementAndGet();
                                            failed();

                                        }
                                    }
                                });

                            }

                        }

                    } else {
                        Respuesta = "850,"
                                + "038,"
                                + "000,"
                                + ",~";

                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (TokenDistribucionController.cancelartarea.get()) {
                            cancel();
                        } else {
                            if (MarcoPrincipalController.newTokenDistribuidos) {
                                if (tokendistribuidos.isEmpty()) {
                                    tokendistribuidos = FXCollections.observableArrayList();
                                }
                                setIndicadorRegistros(Respuesta.split(",")[6]);
                                String[] Ltoken = Respuesta.split(",")[7].split(";");

                                for (int i = 0; i < Ltoken.length; i++) {
                                    String[] datos = Ltoken[i].split("%");

//                                        String colNombre,
//                                        String colID,
//                                        String colSerial,
//                                        String colGuia,
//                                        String colGestionTele,
//                                        String colGestionEnt,
//                                        String colContactoEnt
                                    final InfoTokenDistribucion data = new InfoTokenDistribucion(
                                            datos[0].trim(),
                                            datos[1].trim(),
                                            datos[2].trim(),
                                            datos[3].trim(),
                                            datos[5].trim(),
                                            datos[6].trim(),
                                            datos[7].trim());

                                    tokendistribuidos.add(data);

                                }
                                synchronized (this) {
                                    MarcoPrincipalController.newTokenEmpresas = false;
                                }
                            }

                        }

                    } else {

                        if (TokenDistribucionController.cancelartarea.get()) {
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
                    return tokendistribuidos;

                }
            };
        }
    }
}
