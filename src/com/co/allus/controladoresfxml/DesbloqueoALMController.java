/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import java.net.URL;
import java.util.ResourceBundle;
import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.desbloqueoalm.DatosDesbloqueoAlm;
import com.co.allus.modelo.desbloqueoalm.InfoTablaDesbloqueoAlm;
import com.co.allus.modelo.desbloqueoalm.MapeoEstadosAlm;
import com.co.allus.utils.AtlasConstantes;
import java.util.concurrent.atomic.AtomicBoolean;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
 * @author roberto.ceballos
 */
public class DesbloqueoALMController implements Initializable {
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Button cancelar_trx;

    @FXML
    private Button desbloquear_trx;

    @FXML
    private TableColumn<InfoTablaDesbloqueoAlm, String> descripcion;

    @FXML
    private TableColumn<InfoTablaDesbloqueoAlm, String> informacion;

    @FXML
    private Label lbltitulo;

    @FXML
    private StackPane panel_tabla;

    @FXML
    private TableView<InfoTablaDesbloqueoAlm> tablaDatosDesbloqueoAlm;

    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<InfoTablaDesbloqueoAlm>> task = new DesbloqueoALMController.GetinfoDesbloqueoAlmTask();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'DesbloqueoALM.fxml'.";
        assert cancelar_trx != null : "fx:id=\"cancelar_trx\" was not injected: check your FXML file 'DesbloqueoALM.fxml'.";
        assert desbloquear_trx != null : "fx:id=\"desbloquear_trx\" was not injected: check your FXML file 'DesbloqueoALM.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'DesbloqueoALM.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'DesbloqueoALM.fxml'.";
        assert lbltitulo != null : "fx:id=\"lbltitulo\" was not injected: check your FXML file 'DesbloqueoALM.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'DesbloqueoALM.fxml'.";
        assert tablaDatosDesbloqueoAlm != null : "fx:id=\"tablaDatosDesbloqueoAlm\" was not injected: check your FXML file 'DesbloqueoALM.fxml'.";

//       tablaDatosDesbloqueoAlm.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
//                //Check whether item is selected and set value of selected item to Label
//                if (tablaDatos.getSelectionModel().getSelectedItem() != null) {
//
//                    continuar_op.setDisable(false);
//                } else {
//                    continuar_op.setDisable(true);
//                    tablaDatos.getSelectionModel().clearSelection();
//                }
//            }
//        });
        
        descripcion.setCellValueFactory(new PropertyValueFactory<InfoTablaDesbloqueoAlm, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<InfoTablaDesbloqueoAlm, String>("informacion"));
        desbloquear_trx.setDisable(true);
        cancelartarea.set(false);
    }    

    public void mostrarDesbloqueoALM() {
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/DesbloqueoALM.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button desbloquear_op = (Button) root.lookup("#desbloquear_trx");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_trx");

                    final TableView<InfoTablaDesbloqueoAlm> tablaDatosDesbloqueoAlm = (TableView<InfoTablaDesbloqueoAlm>) root.lookup("#tablaDatosDesbloqueoAlm");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    /**
                     * barra de progreso
                     */
                    final Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    final ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);
                    
                    /**
                     * TAREAS
                     */
                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    tablaDatosDesbloqueoAlm.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            ObservableList<InfoTablaDesbloqueoAlm> value = task.getValue();
                            tablaDatosDesbloqueoAlm.itemsProperty().unbind();
                            //System.out.println("TERMINO TAREA");
                            final DatosDesbloqueoAlm datosDesbloqueoAlm = DatosDesbloqueoAlm.getDatosDesbloqueoAlm();
                            if ("I".equals(datosDesbloqueoAlm.getEstado_canal().trim())) {
                                desbloquear_op.setDisable(false);
                            }
                            /**
                             * configuracion de la paginacion
                             */
                            tablaDatosDesbloqueoAlm.setItems(value);
                            Atlas.vista.show();
                        }
                    });
                    
                    task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
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

                                    arbol_puntosCol.getSelectionModel().clearSelection();


                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception e) {
                                        gestorDoc.imprimir("Warning -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                                    }
                                    Atlas.vista.show();
                                }
                            });


                        }
                    });
                    
                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                        }
                    });
                    
                    final DropShadow shadow = new DropShadow();

                    desbloquear_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    desbloquear_op.setCursor(Cursor.HAND);
                                    desbloquear_op.setEffect(shadow);
                                }
                            });

                    desbloquear_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    desbloquear_op.setCursor(Cursor.DEFAULT);
                                    desbloquear_op.setEffect(null);

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

                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);

                    Atlas.vista.show();
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que se halla realizado correctamente ,"
                            + " por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });         
    }
    
    public class GetinfoDesbloqueoAlmTask extends Service<ObservableList<InfoTablaDesbloqueoAlm>> {
    
        @Override
        protected Task<ObservableList<InfoTablaDesbloqueoAlm>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();

//                    /*CODIFICACION AUTOMATICA*/
//                    final Runnable aFenix = new EnvioTipAutFenix(datosCliente.getCodemp(), "784");
//                    final Thread aFen = new Thread(aFenix);
//                    aFen.start();

                    String Respuesta = new String();
                    final StringBuilder tramaDesbloqueoAlm = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final ObservableList<InfoTablaDesbloqueoAlm> infoseg = FXCollections.observableArrayList();

                    tramaDesbloqueoAlm.append("850,076,");
                    tramaDesbloqueoAlm.append(datosCliente.getRefid());
                    tramaDesbloqueoAlm.append(",");
                    tramaDesbloqueoAlm.append(AtlasConstantes.COD_DESBLOQUEO_CON);
                    tramaDesbloqueoAlm.append(",");
                    tramaDesbloqueoAlm.append(datosCliente.getId_cliente());
                    tramaDesbloqueoAlm.append(",");
                    tramaDesbloqueoAlm.append("ALM");
                    tramaDesbloqueoAlm.append(",");
                    tramaDesbloqueoAlm.append(datosCliente.getContraseña());
                    tramaDesbloqueoAlm.append(",");
                    tramaDesbloqueoAlm.append(datosCliente.getTokenOauth());
                    tramaDesbloqueoAlm.append(",~");

//                    System.out.println("trama desbloqueo alm " + tramaDesbloqueoAlm.toString());

                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ DesbloqueoALM = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,"
//                                + "076,"
//                                + "000,"
//                                + "TRANSACCION EXITOSA,"                              
//                                + "I,"
//                                + "2019-01-02-18.45.02.948000,"
//                                + "359030064111111,"
//                                + "NOKIA 1111,"
//                                + "111111444355555,"
//                                + "Celular Luz,"
//                                + "03012424608,"
//                                + "~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaDesbloqueoAlm.toString());
                        //Thread.sleep(3000);

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ desbloqueo alm= " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, tramaDesbloqueoAlm.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (DesbloqueoALMController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        desbloquear_trx.setDisable(true);
                                        failed();
                                    }
                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {
                        if (DesbloqueoALMController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final DatosDesbloqueoAlm datosDesbloqueoAlm = DatosDesbloqueoAlm.getDatosDesbloqueoAlm();
                            datosDesbloqueoAlm.setEstado_canal(Respuesta.split(",")[4].trim());
                            datosDesbloqueoAlm.setFecha_novedad(Respuesta.split(",")[5].trim());
                            datosDesbloqueoAlm.setMarca_novedad(Respuesta.split(",")[6].trim());
                            datosDesbloqueoAlm.setModelo_novedad(Respuesta.split(",")[7].trim());
                            datosDesbloqueoAlm.setMarca_registrado(Respuesta.split(",")[8].trim());
                            datosDesbloqueoAlm.setModelo_registrado(Respuesta.split(",")[9].trim());
                            datosDesbloqueoAlm.setNumero_cuenta(Respuesta.split(",")[10].trim());
                            DatosDesbloqueoAlm.setDesbloqueoAlm(datosDesbloqueoAlm);
                            
                            infoseg.add(new InfoTablaDesbloqueoAlm(DatosDesbloqueoAlm.NUMERO_CUENTA, Respuesta.split(",")[10]));
                            infoseg.add(new InfoTablaDesbloqueoAlm(DatosDesbloqueoAlm.ESTADO_CANAL, MapeoEstadosAlm.mapeoEstadosAlm.get(Respuesta.split(",")[4].trim())));
                            infoseg.add(new InfoTablaDesbloqueoAlm(DatosDesbloqueoAlm.FECHA_NOVEDAD, Respuesta.split(",")[5]));
                            infoseg.add(new InfoTablaDesbloqueoAlm(DatosDesbloqueoAlm.MARCA_NOVEDAD, Respuesta.split(",")[6]));
                            infoseg.add(new InfoTablaDesbloqueoAlm(DatosDesbloqueoAlm.MODELO_NOVEDAD, Respuesta.split(",")[7]));
                            infoseg.add(new InfoTablaDesbloqueoAlm(DatosDesbloqueoAlm.MARCA_REGISTRADO, Respuesta.split(",")[8]));
                            infoseg.add(new InfoTablaDesbloqueoAlm(DatosDesbloqueoAlm.MODELO_REGISTRADO, Respuesta.split(",")[9]));
                        }
                    } else {
                        if (DesbloqueoALMController.cancelartarea.get()) {
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
                    return infoseg;
                }
            };
        }
    }
    
    
    @FXML
    void desbloquear_op(ActionEvent event) {

        final DatosDesbloqueoAlm datosDesbloqueoAlm = DatosDesbloqueoAlm.getDatosDesbloqueoAlm();
        DatosDesbloqueoAlm.setDesbloqueoAlm(datosDesbloqueoAlm);
        final DesbloqueoALMConfirmController controller = new DesbloqueoALMConfirmController();
        controller.mostrarDesbloqueoALMConfirmController();
        
    }

    @FXML
    void cancelar_op(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                final Label label_menu = (Label) pane.lookup("#label_saldos");

                final TreeView<String> arbol_DesbloqueoAlm = (TreeView<String>) pane.lookup("#arbolDesbloqueoAlm");
                if (arbol_DesbloqueoAlm != null) {
                    arbol_DesbloqueoAlm.setDisable(false);
                }

                arbol_DesbloqueoAlm.getSelectionModel().clearSelection();
                label_menu.setVisible(true);

                try {
                    pane.getChildren().remove(3);
                } catch (Exception ex) {
                    gestorDoc.imprimir("Advertencia -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }
            }
        });
    }

}
