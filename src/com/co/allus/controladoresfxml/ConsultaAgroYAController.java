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
import com.co.allus.modelo.agroya.DatosAgroYa;
import com.co.allus.modelo.agroya.InfoTablaAgroYa;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.text.DecimalFormat;
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
public class ConsultaAgroYAController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button aceptar_trx;
    @FXML
    private TableColumn<InfoTablaAgroYa, String> descripcion;
    @FXML
    private TableColumn<InfoTablaAgroYa, String> informacion;
    @FXML
    private Label lbltitulo;
    @FXML
    private StackPane panel_tabla;
    @FXML
    private TableView<InfoTablaAgroYa> tablaDatosAgroYa;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ObservableList<InfoTablaAgroYa>> task = new ConsultaAgroYAController.GetinfoAgroYaTask();
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'ConsultaAgroYA.fxml'.";
        assert aceptar_trx != null : "fx:id=\"aceptar_trx\" was not injected: check your FXML file 'ConsultaAgroYA.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'ConsultaAgroYA.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'ConsultaAgroYA.fxml'.";
        assert lbltitulo != null : "fx:id=\"lbltitulo\" was not injected: check your FXML file 'ConsultaAgroYA.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'ConsultaAgroYA.fxml'.";
        assert tablaDatosAgroYa != null : "fx:id=\"tablaDatosAgroYa\" was not injected: check your FXML file 'ConsultaAgroYA.fxml'.";
        descripcion.setCellValueFactory(new PropertyValueFactory<InfoTablaAgroYa, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<InfoTablaAgroYa, String>("informacion"));
        cancelartarea.set(false);
    }

    public void mostrarConsultaAgroYa() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaAgroYA.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button aceptar_op = (Button) root.lookup("#aceptar_trx");

                    final TableView<InfoTablaAgroYa> tablaDatosAgroYa = (TableView<InfoTablaAgroYa>) root.lookup("#tablaDatosAgroYa");
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
                    tablaDatosAgroYa.itemsProperty().bind(task.valueProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            ObservableList<InfoTablaAgroYa> value = task.getValue();
                            tablaDatosAgroYa.itemsProperty().unbind();
                            /**
                             * configuracion de la paginacion
                             */
                            tablaDatosAgroYa.setItems(value);
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

                    aceptar_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    aceptar_op.setCursor(Cursor.HAND);
                                    aceptar_op.setEffect(shadow);
                                }
                            });

                    aceptar_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    aceptar_op.setCursor(Cursor.DEFAULT);
                                    aceptar_op.setEffect(null);

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

    public class GetinfoAgroYaTask extends Service<ObservableList<InfoTablaAgroYa>> {

        @Override
        protected Task<ObservableList<InfoTablaAgroYa>> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    final Cliente datosCliente = Cliente.getCliente();

//                    /*CODIFICACION AUTOMATICA*/
//                    final Runnable aFenix = new EnvioTipAutFenix(datosCliente.getCodemp(), "784");
//                    final Thread aFen = new Thread(aFenix);
//                    aFen.start();

                    String Respuesta = new String();
                    final StringBuilder tramaAgroYa = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final ObservableList<InfoTablaAgroYa> AgroYa = FXCollections.observableArrayList();

                    tramaAgroYa.append("850,071,");
                    tramaAgroYa.append(datosCliente.getRefid());
                    tramaAgroYa.append(",");
                    tramaAgroYa.append(AtlasConstantes.COD_AFC_AGROYA);
                    tramaAgroYa.append(",");
                    tramaAgroYa.append("GDE");
                    tramaAgroYa.append(",");
                    tramaAgroYa.append(datosCliente.getId_cliente());
                    tramaAgroYa.append(",");
                    tramaAgroYa.append(datosCliente.getContraseña());
                    tramaAgroYa.append(",");
                    tramaAgroYa.append(datosCliente.getTokenOauth());
                    tramaAgroYa.append(",~");


                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Consulta AgroYá = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,"
//                                + "071,"
//                                + "000,"
//                                + "TRANSACCION EXITOSA,"                              
//                                + "000000050000000,"
//                                + "00,"
//                                + "000000002669609,"
//                                + "00,"
//                                + "000000047330391,"
//                                + "00,"
//                                + "000000000000000,"
//                                + "00,"
//                                + "DTF,"
//                                + "~";
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU3, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU3, tramaAgroYa.toString());
                        //Thread.sleep(3000);

                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 2, ",") + "##" + gestorDoc.obtenerHoraActual());
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Consulta AgroYá= " + "##" + gestorDoc.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU3, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU3, tramaAgroYa.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 2, ",") + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ConsultaAgroYAController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        failed();
                                    }
                                }
                            });
                        }
                    }

                    if ("000".equals(Respuesta.split(",")[2])) {
                        if (ConsultaAgroYAController.cancelartarea.get()) {
                            cancel();
                        } else {
                            final DatosAgroYa datosAgroYa = DatosAgroYa.getDatosAgroYa();
                            datosAgroYa.setCupo_asignado(Respuesta.split(",")[4].trim());
                            datosAgroYa.setCupo_utilizado(Respuesta.split(",")[5].trim());
                            datosAgroYa.setCupo_disponible(Respuesta.split(",")[6].trim());
                            datosAgroYa.setValor_canje(Respuesta.split(",")[7].trim());
                            DatosAgroYa.setDatosAgroYa(datosAgroYa);

                            String ValorCupoAsignado = "$ " + (formatonum.format(Double.parseDouble(Respuesta.split(",")[4])).replace(".", ",") + "." + Respuesta.split(",")[5]);
                            String ValorCupoUtilizado = "$ " + (formatonum.format(Double.parseDouble(Respuesta.split(",")[6])).replace(".", ",") + "." + Respuesta.split(",")[7]);
                            String ValorDisponible = "$ " + (formatonum.format(Double.parseDouble(Respuesta.split(",")[8])).replace(".", ",") + "." + Respuesta.split(",")[9]);
                            String ValorCanje = "$ " + (formatonum.format(Double.parseDouble(Respuesta.split(",")[10])).replace(".", ",") + "." + Respuesta.split(",")[11]);

                            AgroYa.add(new InfoTablaAgroYa(DatosAgroYa.CUPO_ASIGNADO, ValorCupoAsignado));
                            AgroYa.add(new InfoTablaAgroYa(DatosAgroYa.CUPO_UTILIZADO, ValorCupoUtilizado));
                            AgroYa.add(new InfoTablaAgroYa(DatosAgroYa.CUPO_DISPONIBLE, ValorDisponible));
                            AgroYa.add(new InfoTablaAgroYa(DatosAgroYa.VALOR_CANJE, ValorCanje));
                        }

                    } else {
                        if (ConsultaAgroYAController.cancelartarea.get()) {
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
                    return AgroYa;
                }
            };
        }
    }

    @FXML
    void aceptar_op(ActionEvent event) {
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