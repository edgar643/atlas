/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ModalMensajesController implements Initializable {

    @FXML
    private transient ResourceBundle resources;
    @FXML
    private transient URL location;
    @FXML
    private transient Button aceptar;
    @FXML
    private transient ImageView icono;
    @FXML
    private transient Label mensaje;
    private transient GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        assert aceptar != null : "fx:id=\"aceptar\" was not injected: check your FXML file 'ModalMensajes.fxml'.";
        assert icono != null : "fx:id=\"icono\" was not injected: check your FXML file 'ModalMensajes.fxml'.";
        assert mensaje != null : "fx:id=\"mensaje\" was not injected: check your FXML file 'ModalMensajes.fxml'.";
        this.location = location;
        this.resources = resources;

        ModalMensajes.getStage().focusedProperty().addListener(focus);

        ModalMensajes.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent event) {
                switch (ModalMensajes.getModal()) {
                    case 0:
                        ModalMensajes.cerrarVentana();
                        break;
                    case 1:
                        ModalMensajes.cerrarVentana();
                        break;
                    case 2:
                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {                          
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }
                        Atlas.getVista().mostrarPanePagos("PAGOS TERCEROS");
                        break;
                    case 3:
                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }
                        Atlas.getVista().mostrarMenuTransfctasprop("trasnferencias cuentas Bancolombia propias");
                        break;
                    case 4:
                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }
                        //
                        final PagosTDCpropiasController controller1 = new PagosTDCpropiasController();
                        controller1.mostrarMenuPagosTDCpropia("PAGOS-TDC", new modelSaldoTarjeta("", "", ""));
                        // Atlas.getVista().mostrarMenuPagosTDCpropia("PAGOS-TDC","");
                        break;
                    case 5:
                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }
                        Atlas.getVista().mostrarMenuPagosTDCnoPropia("PAGOS-TDC");
                        break;

                    case 6:
                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }
                        Atlas.getVista().mostrarDesbloSClaveCorp("DESBLOQUEO-SEGUNDA CLAVE");
                        break;

                    case 7:
                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }

                        final ConsultaSaldoTDCController controller = new ConsultaSaldoTDCController();
                        controller.mostrarSaldosTDC("SALDO-TDC", false);

                        break;

                    case 8:
                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }
                        try {
                            final BloqueosTDCController controllerBloq = new BloqueosTDCController();
                            synchronized (this) {
                                // no realiza consulta
                                MarcoPrincipalController.newConsultaBloqTDc = true;
                            }
                            controllerBloq.mostrarBloqueosTDC("BLOQUEOS-TDC");
                        } catch (Exception ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                        }

                        break;

                    case 9:

                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }
                        break;

                    case 10:

                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                        pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
                        final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                        arbol_saldos.setDisable(false);
                        arbol_saldos.getSelectionModel().clearSelection();

                        try {
                            pane.getChildren().remove(3);

                        } catch (Exception e) {
                            docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                        }

                        break;

                    case 11:
                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }

                        try {
                            Atlas.getVista().gotoPrincipal();
                        } catch (Exception ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                        }

                        break;

                    case 12:
                        ModalMensajes.cerrarVentana();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                            Thread.currentThread().interrupt();

                        }

                        final Parent frameGnral2 = Atlas.vista.getScene().getRoot();
                        final GridPane pane2 = (GridPane) frameGnral2.lookup("#Panel_datos");
                        final Label mensaje_saldos2 = (Label) pane2.lookup("#label_saldos");
                        pane2.setMargin(mensaje_saldos2, new Insets(0, 0, 0, 0));
                        final TreeView<String> arbol_servad = (TreeView<String>) pane2.lookup("#arbol_servadicionales");
                        arbol_servad.setDisable(false);
                        arbol_servad.getSelectionModel().clearSelection();

                        try {
                            pane2.getChildren().remove(3);

                        } catch (Exception e) {
                            docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                        }

                        break;

                    default:
                        ModalMensajes.cerrarVentana();
                        break;

                    case 99:
                        ModalMensajes.cerrarVentana();
                        try {
                            Atlas.getVista().gotoPrincipal();
                        } catch (IOException ex) {
                            Logger.getLogger(ModalMensajesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
            }
        });

    }

    @FXML
    void continuarOP(final ActionEvent event) {
        switch (ModalMensajes.getModal()) {
            case 0:
                ModalMensajes.cerrarVentana();
                break;
            case 1:
                ModalMensajes.cerrarVentana();
                break;
            case 2:
                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }
                Atlas.getVista().mostrarPanePagos("PAGOS TERCEROS");
                break;
            case 3:
                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }
                Atlas.getVista().mostrarMenuTransfctasprop("trasnferencias cuentas Bancolombia propias");
                break;
            case 4:
                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }
                final PagosTDCpropiasController controller = new PagosTDCpropiasController();
                controller.mostrarMenuPagosTDCpropia("PAGOS-TDC", new modelSaldoTarjeta("", "", ""));
//                controller.mostrarMenuPagosTDCpropia("PAGOS-TDC", new modelSaldoTarjeta("", "", ""));
                //   Atlas.getVista().mostrarMenuPagosTDCpropia("PAGOS-TDC","");
                break;
            case 5:
                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }
                Atlas.getVista().mostrarMenuPagosTDCnoPropia("PAGOS-TDC");
                break;
            case 6:
                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }
                Atlas.getVista().mostrarDesbloSClaveCorp("DESBLOQUEO-SEGUNDA CLAVE");
                break;
            case 7:
                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }

                final ConsultaSaldoTDCController controller2 = new ConsultaSaldoTDCController();
                controller2.mostrarSaldosTDC("SALDO-TDC", false);

                break;

            case 8:
                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }
                break;

            case 9:

                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }
                break;

            case 10:

                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }

                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));
                final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                arbol_saldos.setDisable(false);
                arbol_saldos.getSelectionModel().clearSelection();

                try {
                    pane.getChildren().remove(3);

                } catch (Exception e) {
                    docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }

                break;

            case 11:

                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }

                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }

                break;
            case 12:

                ModalMensajes.cerrarVentana();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    Thread.currentThread().interrupt();

                }

                final Parent frameGnral2 = Atlas.vista.getScene().getRoot();
                final GridPane pane2 = (GridPane) frameGnral2.lookup("#Panel_datos");
                final Label mensaje_saldos2 = (Label) pane2.lookup("#label_saldos");
                pane2.setMargin(mensaje_saldos2, new Insets(0, 0, 0, 0));
                final TreeView<String> arbol_servad = (TreeView<String>) pane2.lookup("#arbol_servadicionales");
                arbol_servad.setDisable(false);
                arbol_servad.getSelectionModel().clearSelection();

                try {
                    pane2.getChildren().remove(3);

                } catch (Exception e) {
                    docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }

                break;
            default:
                ModalMensajes.cerrarVentana();
                break;

            case 99:
                ModalMensajes.cerrarVentana();
                System.exit(0);
                break;
        }

    }
    ChangeListener<Boolean> focus = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

            if (!newValue) {
                ModalMensajes.getStage().requestFocus();

            }
        }
    };
}
