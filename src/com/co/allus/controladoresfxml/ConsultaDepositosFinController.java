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
import com.co.allus.modelo.consultadepositos.InfoTablaConsDepo;
import com.co.allus.modelo.consultadepositos.InfoTablaDetDeposito;
import com.co.allus.modelo.consultadepositos.datosDepositoDetalle;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class ConsultaDepositosFinController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button cancelar_op;
    @FXML
    private TableColumn col_estado;
    @FXML
    private TableColumn col_numCuenta;
    @FXML
    private Button continuar_op;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private Label lbltitulo;
    @FXML
    private TableView<InfoTablaConsDepo> tabla_datos;
    @FXML
    private ProgressBar progreso;
    private static GestorDocumental docs = new GestorDocumental();
    private static List<InfoTablaConsDepo> dataTabla;
    private static String Tipocta;
    private Pagination pagination = new Pagination();
    int currentpageindex = 0;
    private transient Service<Void> serviceDetalle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancelar_op != null : "fx:id=\"cancelar_op\" was not injected: check your FXML file 'ConsultaDepositosFin.fxml'.";
        assert col_estado != null : "fx:id=\"col_estado\" was not injected: check your FXML file 'ConsultaDepositosFin.fxml'.";
        assert col_numCuenta != null : "fx:id=\"col_numCuenta\" was not injected: check your FXML file 'ConsultaDepositosFin.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'ConsultaDepositosFin.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaDepositosFin.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'ConsultaDepositosFin.fxml'.";
        assert lbltitulo != null : "fx:id=\"lbltitulo\" was not injected: check your FXML file 'ConsultaDepositosFin.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaDepositosFin.fxml'.";
        this.location = url;
        this.resources = rb;
        progreso.setVisible(false);
        col_numCuenta.setCellValueFactory(new PropertyValueFactory<InfoTablaConsDepo, String>("numCta"));
        col_estado.setCellValueFactory(new PropertyValueFactory<InfoTablaConsDepo, String>("estado"));


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

        continuar_op.setDisable(true);
    }

    @FXML
    void retornar(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dataTabla.clear();
                ConsultaDepositosController controller = new ConsultaDepositosController();
                controller.mostrarCosultaDepot();
            }
        });
    }

    @FXML
    void continuar_OP(ActionEvent event) {

        InfoTablaConsDepo selectedItem = tabla_datos.getSelectionModel().getSelectedItem();
        datosDepositoDetalle dataDetalle = datosDepositoDetalle.getDataDetalle();
        dataDetalle.setNum_tarjeta(selectedItem.getNumCta().toString());
        datosDepositoDetalle.setDataDetalle(dataDetalle);

        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Saldo Credito Hipotecario" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Saldo Credito Hipotecario" + "##" + docs.obtenerHoraActual());
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

    public Service<Void> continuar_Op() {
        serviceDetalle = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();
                        datosDepositoDetalle dataDetalle = datosDepositoDetalle.getDataDetalle();
                        final StringBuilder tramaDetalleDeposito = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

                        //850,055,connid,codTransaccion(0651),identificacion,origen,tipoCta,nuncta,claveHardware,~


                        tramaDetalleDeposito.append("850,055,");
                        tramaDetalleDeposito.append(cliente.getRefid());
                        tramaDetalleDeposito.append(",");
                        tramaDetalleDeposito.append(AtlasConstantes.COD_DETALLE_DEPOSITOS);
                        tramaDetalleDeposito.append(",");
                        tramaDetalleDeposito.append(cliente.getId_cliente());
                        tramaDetalleDeposito.append(",");
                        tramaDetalleDeposito.append("C");
                        tramaDetalleDeposito.append(",");
                        tramaDetalleDeposito.append(dataDetalle.getTipocta().equalsIgnoreCase("Ahorros") ? "S" : "D");
                        tramaDetalleDeposito.append(",");
                        tramaDetalleDeposito.append(dataDetalle.getNum_tarjeta());
                        tramaDetalleDeposito.append(",");
                        tramaDetalleDeposito.append(cliente.getContraseña());
                        tramaDetalleDeposito.append(",");
                        tramaDetalleDeposito.append(cliente.getTokenOauth());
                        tramaDetalleDeposito.append(",~");

                        //  System.out.println("TRAMA DETALLE DEPOSITO :" + tramaDetalleDeposito);


                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ DETALLE DEPOSITOS = " + "##" + docs.obtenerHoraActual());

                            //850,055,000,TRANSACCION EXITOSA,tipocta,oficinadueña,oficinaApertura,estado,bloqueosCta,razonbloqueo1,razonbloqueo2,razonbloqueo3,razonbloqueo4,razonBloqueo5,DiasInactividad,codigoplan,nombrePlan,CodigoOficialCta,TablaCargosComisionm,tablainteresesRedimidos,tipoExcGMF,periodiciadextracto,impresionExtracto,fechaApertura,FechaCancelacion,fechaUltimoAbono,fechaUlitmoRetiro,ValorInteresesRendimientoAñoAct,ValorInteresesRendimientoAñoAnt,~
                            //850,055,000,,SBA,00001,00001,,,,,,,,,,,,,,000,TRIMESTRAL,NO IMPRIME,20160719,,20161027,20161027,+0000000000005616,+0000000000000000,~ 
//                            Respuesta = "850,"
//                                    + "055,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "SBA," //tipocta
//                                    + "150," // oficinadueña
//                                    + "149,"//oficinaApertura
//                                    + "A,"//estado
//                                    + "Débito,"//bloqueosCta
//                                    + "Falle,"//razonbloqueo1
//                                    + "Inves,"//razonbloqueo2
//                                    + "Tiempo,"//razonbloqueo3
//                                    + "razon4,"//razonbloqueo4
//                                    + "razon5,"//razonbloqueo5
//                                    + "0,"//DiasInactividad
//                                    + "21,"//codigoplan
//                                    + "Efectivo,"//nombrePlan
//                                    + "10,"//CodigoOficialCta
//                                    + "0,"//TablaCargosComision
//                                    + "0,"//tablainteresesRedimidos
//                                    + "A,"//tipoExcGMF
//                                    + "Mensual,"//periodiciadextracto
//                                    + "Imprime,"//impresionExtracto
//                                    + "16/05/2016,"//fechaApertura
//                                    + "17/05/2016,"//FechaCancelacion
//                                    + "19/05/2016,"//fechaUltimoAbono
//                                    + "20/05/2016,"//fechaUlitmoRetiro
//                                    + "2035000,"//ValorInteresesRendimientoAñoAct
//                                    + "2165200,"//ValorInteresesRendimientoAñoAnt
//                                    + "~";

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDetalleDeposito.toString());
                            // System.out.println(" RESPUESTA DETALLE DEPOSITO:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ DETALLE DEPOSITOS = " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDetalleDeposito.toString());
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
//                  ,,fechaUltimoAbono,fechaUlitmoRetiro,ValorInteresesRendimientoAñoAct,ValorInteresesRendimientoAñoAnt,~
                            final InfoTablaDetDeposito infoTablaDetDeposito = InfoTablaDetDeposito.getInfoTablaDetDeposito();

                            String[] bloqueos = (Respuesta.split(",")[9].trim() + "," + Respuesta.split(",")[10].trim() + "," + Respuesta.split(",")[11].trim() + "," + Respuesta.split(",")[12].trim() + "," + Respuesta.split(",")[13].trim()).split(",");
                            final StringBuilder razonbloqueo = new StringBuilder();

                            for (int i = 0; i < bloqueos.length; i++) {
                                if (!bloqueos[i].trim().isEmpty()) {
                                    razonbloqueo.append(bloqueos[i]);

                                    if (!(i == bloqueos.length - 1)) {
                                        razonbloqueo.append(",");
                                    }
                                }


                            }

//                            if(!Respuesta.split(",")[9].trim().isEmpty())
//                            {
//                              razonbloqueo.append(Respuesta.split(",")[9].trim());
//                              razonbloqueo.append(",");
//                            }
//                            else if(!Respuesta.split(",")[10].trim().isEmpty())
//                            {
//                              razonbloqueo.append(Respuesta.split(",")[10].trim());
//                              razonbloqueo.append(",");
//                            }
//                            else if(!Respuesta.split(",")[11].trim().isEmpty())
//                            {
//                              razonbloqueo.append(Respuesta.split(",")[11].trim());
//                              razonbloqueo.append(",");
//                              
//                            }
//                            else if(!Respuesta.split(",")[12].trim().isEmpty())
//                            {
//                              razonbloqueo.append(Respuesta.split(",")[12].trim());
//                              razonbloqueo.append(",");
//                            }
//                            
//                            else if(!Respuesta.split(",")[13].trim().isEmpty())
//                            {
//                              razonbloqueo.append(Respuesta.split(",")[13].trim());
//                              
//                            }


                            infoTablaDetDeposito.setTipoCuenta(Respuesta.split(",")[4].trim());
                            infoTablaDetDeposito.setOficinaDue(Respuesta.split(",")[5].trim());
                            infoTablaDetDeposito.setOficinaApe(Respuesta.split(",")[6].trim());
                            infoTablaDetDeposito.setEstado(Respuesta.split(",")[7].trim());
                            infoTablaDetDeposito.setBloqueos(Respuesta.split(",")[8].trim());
                            //infoTablaDetDeposito.setRazonBloqueo(Respuesta.split(",")[9].trim() + "," + Respuesta.split(",")[10].trim() + "," + Respuesta.split(",")[11].trim() + "," + Respuesta.split(",")[12].trim() + "," + Respuesta.split(",")[13].trim());
                            infoTablaDetDeposito.setRazonBloqueo(razonbloqueo.toString());
                            infoTablaDetDeposito.setDiasInactividad(Respuesta.split(",")[14].trim());
                            infoTablaDetDeposito.setCodigoPlan(Respuesta.split(",")[15].trim());
                            infoTablaDetDeposito.setNombrePlan(Respuesta.split(",")[16].trim());
                            infoTablaDetDeposito.setCodigoOficial(Respuesta.split(",")[17].trim());
                            infoTablaDetDeposito.setTablaCargos(Respuesta.split(",")[18].trim());
                            infoTablaDetDeposito.setTablaInteres(Respuesta.split(",")[19].trim());
                            infoTablaDetDeposito.setTipoGMF(Respuesta.split(",")[20].trim());
                            infoTablaDetDeposito.setPeriodicidad(Respuesta.split(",")[21].trim());
                            infoTablaDetDeposito.setImpresion(Respuesta.split(",")[22].trim());
                            infoTablaDetDeposito.setFechaApertura(Respuesta.split(",")[23].trim());
                            infoTablaDetDeposito.setFechaCancelacion(Respuesta.split(",")[24].trim());
                            infoTablaDetDeposito.setFechatrxDebito(Respuesta.split(",")[25].trim());
                            infoTablaDetDeposito.setFechatrxCredito(Respuesta.split(",")[26].trim());
                            infoTablaDetDeposito.setInterPagadosActual(Respuesta.split(",")[27].trim());
                            infoTablaDetDeposito.setInterAnterior(Respuesta.split(",")[28].trim());


                            InfoTablaDetDeposito.setInfoTablaDetDeposito(infoTablaDetDeposito);




                            // System.out.println("ENTRO A SERVICIO DET");

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final ConsultaDetDepositoFinController controller = new ConsultaDetDepositoFinController();
                                    controller.mostrarDetDepositoFin(InfoTablaDetDeposito.getInfoTablaDetDeposito(), datosDepositoDetalle.getDataDetalle());
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

        return serviceDetalle;

    }

    public void mostrarDepositosFin(final List<InfoTablaConsDepo> data, final String tipoCuenta) {
        this.dataTabla = data;
        this.Tipocta = tipoCuenta;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaDepositosFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Label titulo = (Label) root.lookup("#lbltitulo");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_op");

                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final TableView<InfoTablaConsDepo> tablaData = (TableView<InfoTablaConsDepo>) root.lookup("#tabla_datos");

                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    //titulo dinamico
                    if (tipoCuenta.equals(AtlasConstantes.Cuenta_AFC)) {
                        titulo.setText(("CONSULTA DETALLADA " + AtlasConstantes.Cuenta_AFC).toUpperCase());

                    } else if (tipoCuenta.equals(AtlasConstantes.Cuenta_AHORROS)) {
                        titulo.setText(("CONSULTA DETALLADA " + AtlasConstantes.Cuenta_AHORROS).toUpperCase());

                    } else if (tipoCuenta.equals(AtlasConstantes.Cuenta_CORRIENTE)) {
                        titulo.setText(("CONSULTA DETALLADA " + AtlasConstantes.Cuenta_CORRIENTE).toUpperCase());

                    }


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



                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Advertencia -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    final ObservableList<InfoTablaConsDepo> datosTabla = FXCollections.observableArrayList();
                    datosTabla.addAll(data);



                    final int numpag = datosTabla.size() % rowsPerPage() == 0 ? datosTabla.size() / rowsPerPage() : datosTabla.size() / rowsPerPage() + 1;

                    pagination = new Pagination(numpag, 0);
                    pagination.setPageFactory(new Callback<Integer, Node>() {
                        @Override
                        public Node call(final Integer pageIndex) {
                            if (pageIndex > numpag) {
                                return null;
                            } else {
                                int lastIndex = 0;
                                int displace = datosTabla.size() % rowsPerPage();
                                if (displace >= 0) {
                                    lastIndex = datosTabla.size() / rowsPerPage();
                                } else {
                                    lastIndex = datosTabla.size() / rowsPerPage() - 1;
                                }
                                int page = pageIndex * itemsPerPage();

                                for (int i = page; i < page + itemsPerPage(); i++) {

                                    if (lastIndex == pageIndex) {
                                        final List<InfoTablaConsDepo> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace);
                                        tablaData.setItems(FXCollections.observableArrayList(subList));
                                    } else {
                                        final List<InfoTablaConsDepo> subList = datosTabla.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage());
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
                                    tablaData.getItems().setAll(datosTabla.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= datosTabla.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : datosTabla.size())));
                                }
                            });

                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    root.getChildren().add(pagination);
                    root.getChildren().get(4).setLayoutX(136);
                    root.getChildren().get(4).setLayoutY(46);
                    pagination.setVisible(true);
                    Atlas.vista.show();

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , por favor no volver a intertalo e informar al area tecnica", "", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });

    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
