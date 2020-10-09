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
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.EstadoSgdaClave;
import com.co.allus.utils.TipoCteGde;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class CambioMecanismoTrxfinController implements Initializable {

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
    void aceptar(final ActionEvent event) {

        final ConectSS servicioSS = new ConectSS();
        final TipoCteGde tipoClte = new TipoCteGde();
        final Cliente cliente = Cliente.getCliente();
        final ModeloDatosSeguridad datosSeguridad = ModeloDatosSeguridad.getDatosSeguridad();
        final EstadoSgdaClave estado2da = new EstadoSgdaClave();
        /*Aqui debe traer regla de negocio*/
        String EstadoClave = "ACTA";

        try {

            final ArrayList<String> estado = estado2da.codigosProd.get(cliente.getTipo_clinete_gtel());
            if (estado != null) {
                EstadoClave = estado.get(0);
            }
            /*String RetornarTipoClienteGde = tipoClte.RetornarTipoClienteGde(cliente.getEstado_opt(),
             datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("STK")? "STK": (datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("SMS")|| datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("EML")) ? "ODA":"",
             cliente.getTiene_otp(),                                                                           
             datosSeguridad.getCambio_mecanismo(),
             cliente.getOtp_servicio(),
             cliente.getOtp_antitramites(), 
             EstadoClave);*/

            String RetornarTipoClienteGde = tipoClte.RetornarTipoClienteGde(cliente.getEstado_opt(),
                    datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("STK") ? "STK" : (datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("SMS") || datosSeguridad.getCambio_mecanismo().equalsIgnoreCase("EML")) ? "ODA" : "",
                    cliente.getTiene_otp(),
                    datosSeguridad.getCambio_mecanismo(),
                    cliente.getOtp_servicio(),
                    cliente.getOtp_antitramites(),
                    EstadoClave,
                    cliente.getbDMigracion() + ",",
                    cliente.getControlDual() + ",",
                    "1");

            servicioSS.enviarRecibirServidorsoftphone(AtlasConstantes.HOST_FENIX, AtlasConstantes.PUERTO_FENIX, "%300%" + RetornarTipoClienteGde + "% % % % % % % % % %");

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'CambioMecanismoTrxfin.fxml'.";
        this.resources = rb;
        this.location = url;

    }

    public void mostrarCambioMecanismoTrxFin() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/CambioMecanismoTrxfin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                    cliente.setText("");
                    cliente.setText(info);

                    /**
                     *
                     */
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

                    /**
                     * se repinta la vista en particular
                     */
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                            Logger.getLogger(CambioMecanismoTrxfinController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();

                } catch(Exception ex) {
                    Logger.getLogger(CambioMecanismoTrxfinController.class.getName()).log(Level.SEVERE, null, ex);
                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                }

            }
        });


    }
}
