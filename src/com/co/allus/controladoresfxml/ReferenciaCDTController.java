/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultas.DatosRefCDT;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author stephania.rojas
 */
public class ReferenciaCDTController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ProgressBar progreso;
    @FXML
    private ComboBox<String> selReferenciaCdt;
    ;
    
    @FXML
    private Button cancelarOP;
    @FXML
    private Button continuarOP;
    @FXML
    private Label tituloCDT;
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private transient DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static GestorDocumental docs = new GestorDocumental();
    private transient Service<Void> serviceRefCDT;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 

        assert cancelarOP != null : "fx:id=\"cancelarOP\" was not injected: check your FXML file 'ReferenciaCDT.fxml'.";
        assert continuarOP != null : "fx:id=\"continuarOP\" was not injected: check your FXML file 'ReferenciaCDT.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ReferenciaCDT.fxml'.";
        assert tituloCDT != null : "fx:id=\"tituloCDT\" was not injected: check your FXML file 'ReferenciaCDT.fxml'.";
        assert selReferenciaCdt != null : "fx:id=\"selReferenciaCdt\" was not injected: check your FXML file 'ReferenciaCDT.fxml'.";
        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);
    }

    public void mostrarRefCDT(final String menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ReferenciaCDT.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button cancelarOP = (Button) root.lookup("#cancelarOP");
                    final Button continuarOP = (Button) root.lookup("#continuarOP");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final ComboBox<String> referencia_cdt = (ComboBox<String>) root.lookup("#selReferenciaCdt");

                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    referencia_cdt.setItems(emptyList);
                    final List<String> datos = new ArrayList<String>();

                    final Cliente datosCliente = Cliente.getCliente();
                    final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                    final Set<String> keySet = productos.keySet();
                    datos.add(AtlasConstantes.MENSAJE_COMBO_CDT);

                    for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        final String cuentatipo = val.next();

                        /* validacion solo se pérmite cuenta ahorros y corriente */

                        if (CodigoProductos.CREDITO_CDT.equals(cuentatipo)) {
                            final ArrayList<String> cuentas = productos.get(cuentatipo);
                            for (int i = 0; i < cuentas.size(); i++) {

                                datos.add(cuentas.get(i));
                            }
                        }
                    }
                    referencia_cdt.setItems(FXCollections.observableArrayList(datos));
                    referencia_cdt.getSelectionModel().select(AtlasConstantes.MENSAJE_COMBO_CDT);

                    final DropShadow shadow = new DropShadow();
                    continuarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuarOP.setCursor(Cursor.HAND);
                                    continuarOP.setEffect(shadow);
                                }
                            });

                    continuarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    continuarOP.setCursor(Cursor.DEFAULT);
                                    continuarOP.setEffect(null);
                                }
                            });
                    cancelarOP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarOP.setCursor(Cursor.HAND);
                                    cancelarOP.setEffect(shadow);
                                }
                            });
                    cancelarOP.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelarOP.setCursor(Cursor.DEFAULT);
                                    cancelarOP.setEffect(null);
                                }
                            });

                    continuarOP.setDisable(true);
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
                    final TreeView<String> arbol_servad = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                    if (arbol_servad != null) {
                        arbol_servad.setDisable(true);
                    }
                    final TreeView<String> arbol_movimientos = (TreeView<String>) pane.lookup("#arbol_movimientos");
                    if (arbol_movimientos != null) {
                        arbol_movimientos.setDisable(true);
                    }
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception e) {
//                    e.printStackTrace();
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    public Service<Void> continuar_Op() {
        serviceRefCDT = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaRefCDT = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();



                        tramaRefCDT.append("850,027,");
                        tramaRefCDT.append(cliente.getRefid());
                        tramaRefCDT.append(",");
                        tramaRefCDT.append(cliente.getId_cliente());
                        tramaRefCDT.append(",");
                        tramaRefCDT.append(cliente.getContraseña());
                        tramaRefCDT.append(",");
                        tramaRefCDT.append(cliente.getId_cliente());
                        tramaRefCDT.append(",");
                        tramaRefCDT.append("1");
                        tramaRefCDT.append(",");
                        tramaRefCDT.append(selReferenciaCdt.getSelectionModel().getSelectedItem());
                        tramaRefCDT.append(",");
                        tramaRefCDT.append("1");
                        tramaRefCDT.append(",");
                        tramaRefCDT.append(System.getProperties().getProperty("user.name"));
                        tramaRefCDT.append(",");
                        tramaRefCDT.append("Señor(a)");
                        tramaRefCDT.append(",");
                        tramaRefCDT.append(cliente.getNombre());
                        tramaRefCDT.append(",");
                        tramaRefCDT.append("E");
                        tramaRefCDT.append(",~");

//                        System.out.println("TRAMA REFERENCIA CDT :" + tramaRefCDT);



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  redcdt= " + "##" + docs.obtenerHoraActual());

                            //850,027,000,TRANSACCION EXITOSA,COMPROBANTE,MONTO INICIAL,ESTADO,TITULO,FECHA APERTURA,NUMERO CDT,NOMBRE PRODUCTO,TRATO INTERESADO, NOMBRE INTERESADO,FECHA SOLICITUD,TIPO DOCUMENTO,NUM DOCUMENTO,NOMBRE TITULAR,~
//                            Respuesta="850,"
//                                    + "027,"
//                                    + "000,"
//                                    + "0000058312,"
//                                    + "000 -TRANSACCION ACEPTADA                                             ,"
//                                    + "000000200000000,"
//                                    + "2,"
//                                    + "VIGENTE                       ,"
//                                    + "20160823,"
//                                    + "00010779825,"
//                                    + "P                   ,"
//                                    + "      seor,"
//                                    + "         juan pablo gil munera,"
//                                    + "20160901,"
//                                    + "FS001,"
//                                    + "000000010779825,"
//                                    + "CATALINA            ,~";


//                            Respuesta = "850,"
//                                    + "027,"
//                                    + "000,"
//                                    + "TRANSACCION EXITOSA,"
//                                    + "0000047225,"
//                                    + "0000000000,"
//                                    + "2,"
//                                    + "VIGENTE,"
//                                    + "20160612,"
//                                    + "27600100014,"
//                                    + "V,"
//                                    + "Señor(a),"
//                                    + "Juan Pablo Gil Munera,"
//                                    + "20160804,"
//                                    + "IDDOC,"
//                                    + "100404,"
//                                    + "Juan Pablo Gil Munera,"
//                                    + "~";

                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaRefCDT.toString());
                            // Respuesta = servicioSS.enviarRecibirServidor("172.20.5.113", 9850, tramaRefCDT.toString());
//                            System.out.println(" RESPUESTA TRAMA REFERENCIA CDT:" + Respuesta);
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ ref cdt = " + "##" + docs.obtenerHoraActual());
                                // Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ_DMZ, tramaSaldoCDT.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        continuarOP.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);

                                    }
                                });

                            }

                        }

                        if ("000".equals(Respuesta.split(",")[2])) {
                            //850,027,000,TRANSACCION EXITOSA,COMPROBANTE,MONTO INICIAL,ESTADO,TITULO,FECHA APERTURA,NUMERO CDT,NOMBRE PRODUCTO,TRATO INTERESADO, NOMBRE INTERESADO,FECHA SOLICITUD,TIPO DOCUMENTO,NUM DOCUMENTO,NOMBRE TITULAR,~
                            final DatosRefCDT infoRefCDT = DatosRefCDT.getDatosRefCDT();

                            infoRefCDT.setNumeroCdt(Respuesta.split(",")[9].trim());
                            infoRefCDT.setFechaApertura(Respuesta.split(",")[8]);
                            infoRefCDT.setEstado(Respuesta.split(",")[7].trim());
                            infoRefCDT.setSaldo(Respuesta.split(",")[5]);
                            DatosRefCDT.setDatosRefCDT(infoRefCDT);



                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final ReferenciaCDTFinController controller = new ReferenciaCDTFinController();
                                    controller.mostrarRefCDTfin(DatosRefCDT.getDatosRefCDT());
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
                                    continuarOP.setDisable(false);
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

        return serviceRefCDT;

    }

    @FXML
    void cancelarOP(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    docs.imprimir("Se presento un error inesperado en la aplicacion --->" + ex.toString());
                }
            }
        });

    }

    @FXML
    void continuarOP(ActionEvent event) {

        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Referencia CDT" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });
        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Referencia CDT" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        if (continuar_Op().isRunning()) {
            continuarOP.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().reset();
            continuar_Op().start();

        } else {
            continuarOP.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(continuar_Op().progressProperty());
            continuar_Op().start();
        }
    }

    @FXML
    void selReferenciaCdt(ActionEvent event) {
        if (AtlasConstantes.MENSAJE_COMBO_CDT.equals(selReferenciaCdt.getSelectionModel().getSelectedItem())) {
            continuarOP.setDisable(true);
        } else {
            continuarOP.setDisable(false);
        }
    }
}
