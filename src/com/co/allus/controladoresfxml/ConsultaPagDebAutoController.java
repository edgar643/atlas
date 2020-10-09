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
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaPagDebAutoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button buscar;
    @FXML
    private TextField numConv;
    @FXML
    private ProgressBar progreso;
    private static GestorDocumental docs = new GestorDocumental();
    private transient Service<Void> servicepagdeb = continuar_Op();
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private ObservableList<infoPagDebAuto> listPagdebauto = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert buscar != null : "fx:id=\"buscar\" was not injected: check your FXML file 'ConsultaPagDebAuto.fxml'.";
        assert numConv != null : "fx:id=\"numConv\" was not injected: check your FXML file 'ConsultaPagDebAuto.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ConsultaPagDebAuto.fxml'.";

        progreso.setVisible(false);
        buscar.setDisable(true);
    }

    public void mostrarPagDebAuto(final boolean esNuevaConsulta) {
        // nuevaConsulta.set(esNuevaConsulta);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/ConsultaPagDebAuto.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button botoncontinuarOp = (Button) root.lookup("#buscar");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");





                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);



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

//                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    cancelar.setCursor(Cursor.HAND);
//                                    cancelar.setEffect(shadow);
//                                }
//                            });
//
//                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
//                            new EventHandler<MouseEvent>() {
//                                @Override
//                                public void handle(final MouseEvent event) {
//                                    cancelar.setCursor(Cursor.DEFAULT);
//                                    cancelar.setEffect(null);
//
//                                }
//                            });

                    botoncontinuarOp.setDisable(true);
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

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                        docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());

                }


            }
        });

    }

    @FXML
    void buscarOP(ActionEvent event) {
        if (servicepagdeb.isRunning()) {
            buscar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(servicepagdeb.progressProperty());
            servicepagdeb.reset();
            servicepagdeb.start();

        } else {
            buscar.setDisable(true);
            progreso.setVisible(true);
            progreso.progressProperty().unbind();
            progreso.progressProperty().bind(servicepagdeb.progressProperty());
            servicepagdeb.reset();
            servicepagdeb.start();
        }

        servicepagdeb.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Movimientos PSE" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                ConsultaPagDebAutoController.cancelartarea.set(false);

            }
        });



        servicepagdeb.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta Movimientos PSE" + "##" + docs.obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });

        servicepagdeb.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                Logger.getGlobal().log(Level.SEVERE, t.getSource().getException().toString());
//                System.out.println("fallo");
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
                buscar.setDisable(false);
            }
        });
    }

    public Service<Void> continuar_Op() {
        servicepagdeb = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String Respuesta = new String();

                        final StringBuilder tramaPagDebAuto = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();

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
                        tramaPagDebAuto.append(""); //tracePagincacion
                        tramaPagDebAuto.append(",");
                        tramaPagDebAuto.append(rnv); //regla negocio
                        tramaPagDebAuto.append(",");
                        tramaPagDebAuto.append(cliente.getId_cliente());
                        tramaPagDebAuto.append(",");
                        tramaPagDebAuto.append("40"); // registrosCanal
                        tramaPagDebAuto.append(",");
                        tramaPagDebAuto.append("0"); //registrosPaginacion
                        tramaPagDebAuto.append(",");
                        tramaPagDebAuto.append(numConv.getText());
                        tramaPagDebAuto.append(",");
                        tramaPagDebAuto.append(cliente.getContraseña());
                        tramaPagDebAuto.append(",");
                        tramaPagDebAuto.append(cliente.getTokenOauth());
                        tramaPagDebAuto.append(",~");


//                        System.out.println("TRAMA PAGADORES DEBITOAUTO :" + tramaPagDebAuto);



                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ  movmientos conv = " + "##" + docs.obtenerHoraActual());


//                           Respuesta="850,069,000,TRANSACCION EXITOSA,S,25,40,"
//                                   + "1%1020416841%Modesto Rosado%25252Nada%0258%BANCOLOMBIA%7%25377634449;"
//                                   + "1%1020416843%Modesto Rosado3%Referencia Fija%0258%BANCOLOMBIA%7%25377634449;"
//                                   + "1%1020416842%Modesto Rosado2%154265%0258%BANCOLOMBIA%7%25377634449;"
//                                   + "1%1020416844%Modesto Rosado4%Referencia Fija2%0258%BANCOLOMBIA%7%25377634449,~";
                            // Thread.sleep(2000);

                            //Respuesta="850,069,000,TRANSACCION EXITOSA,S,000042,100458238347,N,1%1037889939     %Pagostren53                                                                     %234234234                          %1007 %BANCOLOMBIA                                                                     %Cuenta de Ahorros             %40679939006         ;5%5037222283     %Pagostren21                                                                     %80239580239                        %1007 %BANCOLOMBIA                                                                     %Cuenta corriente              %40612283001         ;1%1037889913     %Pagostren13                                                                     %1037889913                         %1007 %BANCOLOMBIA                                                                     %Tarjeta de cr?dito Mastercard %************0124    ;1%1037889914     %Pagostren14                                                                     %00439191412                        %1007 %BANCOLOMBIA                                                                     %Tarjeta de cr?dito Mastercard %************9418    ;1%1037889910     %Pagostren10                                                                     %987742509                          %1007 %BANCOLOMBIA                                                                     %Tarjeta de cr?dito Mastercard %************1043    ;1%1037889910     %Pagostren10                                                                     %439194572                          %1007 %BANCOLOMBIA                                                                     %Tarjeta de cr?dito Mastercard %************1043    ;1%1037889910     %Pagostren10                                                                     %714507894                          %1007 %BANCOLOMBIA                                                                     %Tarjeta de cr?dito Mastercard %************1043    ;1%1037889910     %Pagostren10                                                                     %34567890                           %1007 %BANCOLOMBIA                                                                     %Tarjeta de cr?dito Mastercard %************1043    ;1%1037889914     %Pagostren14                                                                     %4391966894                         %1007 %BANCOLOMBIA                                                                     %Cuenta de Ahorros             %40679914007         ;1%1037889915     %Pagostren15                                                                     %2206                               %1007 %BANCOLOMBIA                                                                     %Tarjeta de cr?dito Mastercard %************2857    ;1%1037889920     %Pagostren34                                                                     %122341                             %1007 %BANCOLOMBIA                                                                     %Cuenta de Ahorros             %40679920007         ;1%1037889920     %Pagostren34                                                                     %33324                              %1007 %BANCOLOMBIA                                                                     %Cuenta de Ahorros             %40679920007         ;1%1037889920     %Pagostren34                                                                     %1239                               %1007 %BANCOLOMBIA                                                                     %Tarjeta de cr?dito Mastercard %************8191    ;1%1037889921     %Pagostren35                                                                     %124235                             %1007 %BANCOLOMBIA                                                                     %Cuenta de Ahorros             %40679921007         ;1%1037889921     %Pagostren35                                                                     %6575721                            %1007 %BANCOLOMBIA                                                                     %Cuenta corriente              %40619921001         ;1%1037889921     %Pagostren35                                                                     %657572                             %1007 %BANCOLOMBIA                                                                     %Cuenta corriente              %40619921001         ;1%1037889922     %Pagostren36                                                                     %084896239                          %1007 %BANCOLOMBIA                                                                     %Cuenta corriente              %40619922001         ;1%1037889936     %Pagostren50                                                                     %23124124                           %1007 %BANCOLOMBIA                                                                     %Cuenta de Ahorros             %40679936007         ;1%1037889938     %Pagostren52                                                                     %099                                %1007 %BANCOLOMBIA                                                                     %Cuenta de Ahorros             %40679938006         ,~";
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaPagDebAuto.toString());

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());
                        } catch (Exception ex) {

                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                            //envio a contingencia
                            try {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ MOVIMIENTOS PES= " + "##" + docs.obtenerHoraActual());
                                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AUA, AtlasConstantes.PUERTO_COM_MQ_DMZ_AUA, tramaPagDebAuto.toString());
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + docs.obtenerHoraActual());

                            } catch (Exception ex1) {
                                docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ConsultaPagDebAutoController.cancelartarea.get()) {
//                                            System.out.println("cancelo tarea");
                                            cancel();
                                        } else {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                            buscar.setDisable(false);
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);
                                        }
                                    }
                                });
                            }
                        }



                        if ("000".equals(Respuesta.split(",")[2])) {


                            final String indMasREg = Respuesta.split(",")[4];
                            final String numReg = Respuesta.split(",")[5];
                            final String tracePaginacion = Respuesta.split(",")[6];
                            final String tipoconv = Respuesta.split(",")[7];

                            listPagdebauto.clear();
                            listPagdebauto = FXCollections.observableArrayList();

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


                                listPagdebauto.add(mov);

                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final ConsultaPagDebAutoFinController controller = new ConsultaPagDebAutoFinController();
                                    controller.mostrarPagDebAuto(listPagdebauto,
                                            indMasREg,
                                            numReg,
                                            40,
                                            numConv.getText(),
                                            tracePaginacion,
                                            tipoconv,
                                            1);
                                }
                            });


                        } else {
                            final String coderror = Respuesta.split(",")[2];
                            final String mensaje = Respuesta.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (ConsultaPagDebAutoController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                        buscar.setDisable(false);
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                }
                            });

                        }



                        return null;
                    }
                };
            }
        };



        return servicepagdeb;

    }

    @FXML
    void numConvkeyPress(KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if (!(event.getCode().impl_getChar().trim().equals(""))) {
                buscar.setDisable(false);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(numConv, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                numConv.clear();
                numConv.fireEvent(keyEvent);

            }


        }
    }

    @FXML
    void numConvkeytyped(KeyEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#buscar");
                    final TextField numConv = (TextField) root.lookup("#numConv");

                    if (numConv.getText().trim().isEmpty()) {
                        buttonCont.setDisable(true);

                    } else {
                        buttonCont.setDisable(false);
                    }


                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }

            }
        });
    }
}
