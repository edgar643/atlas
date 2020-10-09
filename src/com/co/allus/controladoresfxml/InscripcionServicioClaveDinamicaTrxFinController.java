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
import com.co.allus.modelo.cambiomecanismo.ModeloDatosSeguridad;
import com.co.allus.modelo.enrolamiento.Enrolamiento;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.EstadoSgdaClave;
import com.co.allus.utils.TipoCteGde;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author luis.cuervo
 */
public class InscripcionServicioClaveDinamicaTrxFinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button terminar_trx;
    @FXML
    private Label titulosEvidente;

    @FXML
    void aceptar(ActionEvent event) {
        final ConectSS servicioSS = new ConectSS();
        final TipoCteGde tipoClte = new TipoCteGde();
//        final TipoCteGdeOk tipoClte = new TipoCteGdeOk();
        final Cliente cliente = Cliente.getCliente();
        final ModeloDatosSeguridad datosSeguridad = ModeloDatosSeguridad.getDatosSeguridad();
        final Enrolamiento enrolado = Enrolamiento.getEnrolado();
        final EstadoSgdaClave estado2da = new EstadoSgdaClave();
        String EstadoClave = "ACTA";

        try {


            // getTipo_clinete_gtel() -- Tipo Actual
            // opcion1 = 1 Evidente Ok, 2 Evidente Nok
            // getEstado_opt() // 01010
            // cliente.getOtp_servicio() - 1

            final ArrayList<String> estado = estado2da.codigosProd.get(cliente.getTipo_clinete_gtel());
            if (estado != null) {
                EstadoClave = estado.get(0);
            }

//            final String RetornarTipoClienteGde = tipoClte.RetornarTipoClienteGde("01010",//cliente.getEstado_opt(),
//                                                                            "ODA",//datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("STK")? "STK": (datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("SMS")|| datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("EML")) ? "ODA":"",
//                                                                            "S",//cliente.getTiene_otp(),                                                                           
//                                                                            enrolado.getTipo_mecanismo(),//datosSeguridad.getCambio_mecanismo(), tipoODA // SMS O EML
//                                                                            "1",//cliente.getOtp_servicio(),
//                                                                            "N",//cliente.getOtp_antitramites(), 
//                                                                            EstadoClave);

            final String RetornarTipoClienteGde = tipoClte.RetornarTipoClienteGde("01010",//cliente.getEstado_opt(),
                    "ODA",//datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("STK")? "STK": (datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("SMS")|| datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("EML")) ? "ODA":"",
                    "S",//cliente.getTiene_otp(),                                                                           
                    enrolado.getTipo_mecanismo(),//datosSeguridad.getCambio_mecanismo(), tipoODA // SMS O EML
                    "1",//cliente.getOtp_servicio(),
                    "N",//cliente.getOtp_antitramites(), 
                    EstadoClave,
                    cliente.getbDMigracion() + ",",
                    cliente.getControlDual() + ",",
                    "0");

            final StringBuilder tramaAFenix = new StringBuilder();
            tramaAFenix.append("%300%");
            tramaAFenix.append(RetornarTipoClienteGde + "%");
            tramaAFenix.append(" %"); // siempre vacio
            tramaAFenix.append("S" + "%");
            tramaAFenix.append("01010%"); // EstadoOTP
            tramaAFenix.append(enrolado.getTipo_mecanismo() + "%"); // tipoODA // SMS O EML
            tramaAFenix.append("ODA%"); // tipoOTP
            tramaAFenix.append("N%"); // Otp Antitramite
            tramaAFenix.append("1%"); // OTP Servicio
            tramaAFenix.append("S%"); // Tiene OTP            
            tramaAFenix.append("C%"); // OPCMenu

//            System.out.println("Datos:"+RetornarTipoClienteGde);
//            System.out.println("Datos:");
            gestorDoc.imprimir("Envio Fenix --> " + tramaAFenix.toString());
            servicioSS.enviarRecibirServidorsoftphone(AtlasConstantes.HOST_FENIX, AtlasConstantes.PUERTO_FENIX, tramaAFenix.toString());

        } catch (Exception ex) {

            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new ModalMensajes("Error en la comunicacion con  Softphone\n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                }
            });
        } finally {
            try {
                Atlas.getVista().gotoPrincipal();
            } catch (IOException ex) {
                gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
            }
        }
    }
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static String menu1 = "";
    private static int flujo1 = 0;
    private static int opcion1 = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxFin.fxml'.";
        assert titulosEvidente != null : "fx:id=\"titulosEvidente\" was not injected: check your FXML file 'InscripcionServicioClaveDinamicaTrxFin.fxml'.";
    }

    public void mostrarFinInscripcionClaveDinamicaOk(final String menu, final int flujo, final int opcion) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/InscripcionServicioClaveDinamicaTrxFin.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final Label titulosEvidente = (Label) root.lookup("#titulosEvidente");
                        titulosEvidente.setText(menu);
//                        System.out.println("------ Lo que pasa en menu 2:" + menu);
                        menu1 = menu;
                        flujo1 = flujo;
                        opcion1 = opcion;

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

                        }
                        pane.setAlignment(Pos.CENTER_LEFT);
                        pane.add(root, 1, 0);
                        Atlas.vista.show();

                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//                        ex.printStackTrace();
                    }

                }
            });



        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//            ex.printStackTrace();
        }

    }
}
