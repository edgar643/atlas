/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.hilos.EnvioTipAutFenix;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagosaterceros.convenio;
import com.co.allus.modelo.pagosaterceros.infoPago;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class PagosTercerosDatosPago1Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private transient ResourceBundle resources;
    @FXML
    private transient URL location;
    @FXML
    private transient Button cancelar_op;
    @FXML
    private transient Button continuar_op;
    @FXML
    private static ComboBox<String> cuentas;
    @FXML
    private static ComboBox<String> tipoCuenta;
    @FXML
    private transient Label titulo_nom_conv;
    @FXML
    private transient Label titulosPagos;
    private transient convenio Dataconvenio;
    @FXML
    private transient HBox pen_progreso;
    @FXML
    private transient ProgressBar progreso;
    @FXML
    private transient ProgressBar progreso_conv;
    @FXML
    private transient Button cancelar_consulta;
    @FXML
    private transient Button continuar_consulta;
    @FXML
    private transient Label ref_pago1;
    @FXML
    private transient Label ref_pago2;
    @FXML
    private transient RestrictiveTextField text_refpago1;
    @FXML
    private transient RestrictiveTextField text_refpago2;
    @FXML
    private transient HBox panel_consulta_conv;
    @FXML
    private transient VBox panel_ref;
    @FXML
    private transient Label labelrefPago2;
    @FXML
    private transient Label tooltip_ref2;
    private transient Service<Void> serviceConv;
    private transient Service<Void> serviceConsPagaCuenta;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // TODO
        assert cancelar_op != null : "fx:id=\"cancel\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert cuentas != null : "fx:id=\"cuentas\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert ref_pago1 != null : "fx:id=\"ref_pago\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert text_refpago1 != null : "fx:id=\"text_refpago\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert ref_pago2 != null : "fx:id=\"ref_pago\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert text_refpago2 != null : "fx:id=\"text_refpago\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert tipoCuenta != null : "fx:id=\"tipoCuenta\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert titulo_nom_conv != null : "fx:id=\"titulo_nom_conv\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert titulosPagos != null : "fx:id=\"titulosPagos\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert pen_progreso != null : "fx:id=\"pen_progreso\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert cancelar_consulta != null : "fx:id=\"cancelar_consulta\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert continuar_consulta != null : "fx:id=\"continuar_consulta\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert panel_ref != null : "fx:id=\"panel_ref\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert panel_consulta_conv != null : "fx:id=\"panel_consulta_conv\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert progreso_conv != null : "fx:id=\"progreso\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert tooltip_ref2 != null : "fx:id=\"tooltip_ref2\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";
        assert labelrefPago2 != null : "fx:id=\"labelrefPago2\" was not injected: check your FXML file 'PagosTercerosDatosPago1.fxml'.";

        this.resources = resources;
        this.location = location;

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

        continuar_consulta.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        continuar_consulta.setCursor(Cursor.HAND);
                        continuar_consulta.setEffect(shadow);
                    }
                });

        continuar_consulta.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        continuar_consulta.setCursor(Cursor.DEFAULT);
                        continuar_consulta.setEffect(null);

                    }
                });

        cancelar_consulta.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        cancelar_consulta.setCursor(Cursor.HAND);
                        cancelar_consulta.setEffect(shadow);
                    }
                });

        cancelar_consulta.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        cancelar_consulta.setCursor(Cursor.DEFAULT);
                        cancelar_consulta.setEffect(null);

                    }
                });

        progreso.setVisible(false);
        progreso_conv.setVisible(false);





    }

    public void MostrarPagosMenu1(final convenio dataconvenio) {
        this.Dataconvenio = dataconvenio;

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/PagosTercerosDatosPago1.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        //final Label refpago = (Label) root.lookup("#ref_pago1");
                        final Label tituloconv = (Label) root.lookup("#titulo_nom_conv");
                        final VBox panel_ref = (VBox) root.lookup("#panel_ref");
                        panel_ref.setVisible(false);
                        // refpago.setText(Dataconvenio.getReferencia());
                        tituloconv.setText(Dataconvenio.getCod_conv() + " - " + Dataconvenio.getNom_conv());
                        /**
                         * restricciones campo de texto
                         */
                        final RestrictiveTextField textrefpago1 = (RestrictiveTextField) root.lookup("#text_refpago1");
                        textrefpago1.setMaxLength(40);
                        textrefpago1.setRestrict("[0-9]");
                        textrefpago1.setIsAlphanum(false);
                        final RestrictiveTextField textrefpago2 = (RestrictiveTextField) root.lookup("#text_refpago2");
                        textrefpago2.setMaxLength(40);
                        textrefpago2.setIsAlphanum(true);
                        textrefpago2.setRestrict("");
                        /**
                         * restricciones campo de texto
                         */
                        /**
                         * se llena la informacion de los combobox con la la
                         * informacion del cliente
                         */
                        final ComboBox<String> nrocuentas = (ComboBox<String>) root.lookup("#nro_cuentas");
                        final ComboBox<String> tiposcuentas = (ComboBox<String>) root.lookup("#tipo_Cuenta");
                        final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                        nrocuentas.setItems(emptyList);
                        final Cliente datosCliente = Cliente.getCliente();
                        final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                        final Set<String> keySet = productos.keySet();
                        final List<String> datos = new ArrayList<String>();
                        for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
                            final String cuentatipo = val.next();

                            /* validacion solo se pérmite cuenta ahorros y corriente */

                            if (CodigoProductos.CUENTA_AHORROS.equals(cuentatipo) || CodigoProductos.CUENTA_CORRIENTE.equals(cuentatipo)) {

                                datos.add(cuentatipo);
                            }
                        }
                        tiposcuentas.setItems(FXCollections.observableArrayList(datos));
                        /**
                         * se repinta la vista en particular
                         */
                        pane.getChildren().remove(3);
                        pane.setAlignment(Pos.CENTER_LEFT);
                        pane.add(root, 1, 0);
                        Atlas.vista.show();
                    } catch (Exception ex) {
                        Logger.getLogger(SaldosController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });


        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }



    }

    @FXML
    void cancel_op(final ActionEvent event) {

        try {
            serviceConsPagaCuenta.cancel();
        } catch (Exception ex) {
                               gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }
        Atlas.getVista().mostrarPanePagos("PAGOS A TERCEROS");
    }

    @FXML
    void seleccionTipoCuenta(final ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> nrocuentas = (ComboBox<String>) root.lookup("#nro_cuentas");
                        final ComboBox<String> tiposcuentas = (ComboBox<String>) root.lookup("#tipo_Cuenta");
                        final Button buttonCont = (Button) root.lookup("#continuar_consulta");
                        final String tipoC = tiposcuentas.getSelectionModel().getSelectedItem();
                        final Cliente datosCliente = Cliente.getCliente();
                        final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                        final ArrayList<String> listacuentas = productos.get(tipoC);
                        nrocuentas.setItems(FXCollections.observableArrayList(listacuentas));

                        try {
                            pane.getChildren().remove(3);
                        } catch (Exception e) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                        }
                        if (!(nrocuentas.getSelectionModel().getSelectedItem() == null)) {
                            buttonCont.setDisable(false);
                        } else {
                            buttonCont.setDisable(true);
                        }
                        pane.setAlignment(Pos.CENTER_LEFT);
                        pane.add(root, 1, 0);
                        Atlas.vista.show();
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }

                }
            });


        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }


    }

    @FXML
    void cancel_consultaconv(final ActionEvent event) {
        try {
            serviceConv.cancel();
        } catch (Exception e) {
            gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }
        Atlas.getVista().mostrarPanePagos("PAGOS A TERCEROS");

    }

    @FXML
    void continuar_consultaconv(final ActionEvent event) {

        consultarConv().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                continuar_consulta.setDisable(false);
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Consulta convenio" + "##" + obtenerHoraActual());
                progreso_conv.progressProperty().unbind();
                progreso_conv.setProgress(0);
                progreso_conv.setVisible(false);
            }
        });



        consultarConv().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                continuar_consulta.setDisable(false);
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Consulta convenio" + "##" + obtenerHoraActual());
                progreso_conv.progressProperty().unbind();
                progreso_conv.setProgress(0);
                progreso_conv.setVisible(false);
            }
        });

        if (consultarConv().isRunning()) {
            continuar_consulta.setDisable(true);
            progreso_conv.setVisible(true);
            progreso_conv.progressProperty().unbind();
            progreso_conv.progressProperty().bind(consultarConv().progressProperty());
            consultarConv().reset();
            consultarConv().start();

        } else {
            continuar_consulta.setDisable(true);
            progreso_conv.setVisible(true);
            progreso_conv.progressProperty().unbind();
            progreso_conv.progressProperty().bind(consultarConv().progressProperty());
            consultarConv().start();
        }

    }

    @FXML
    void continuar_OP(final ActionEvent event) {


        continuar_Op().setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                continuar_op.setDisable(false);
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Paso a pantalla 2 pagos a terceros" + "##" + obtenerHoraActual());
                progreso.progressProperty().unbind();
                progreso.setProgress(0);
                progreso.setVisible(false);
            }
        });



        continuar_Op().setOnCancelled(new EventHandler() {
            @Override
            public void handle(final Event event) {
                continuar_op.setDisable(false);
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Paso 2 Pagos a terceros" + "##" + obtenerHoraActual());
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

    @FXML
    void refpago2keypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if (text_refpago2.isVisible()) {
                if (!(text_refpago1.getText().isEmpty() || event.getCode().impl_getChar().trim().equals(""))) {
                    continuar_op.setDisable(false);
                } else {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(text_refpago2, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    text_refpago2.clear();
                    text_refpago2.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                }

            } else {

                if (!(event.getCode().impl_getChar().trim().equals(""))) {
                    continuar_op.setDisable(false);
                } else {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(text_refpago2, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    text_refpago2.clear();
                    text_refpago2.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                }

            }

        }


    }

    @FXML
    void refpagokeypress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if (text_refpago2.isVisible()) {
                if (!(event.getCode().impl_getChar().trim().equals("") || text_refpago2.getText().isEmpty())) {
                    continuar_op.setDisable(false);
                } else {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(text_refpago1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    text_refpago1.clear();
                    text_refpago1.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                }

            } else {

                if (!(event.getCode().impl_getChar().trim().equals(""))) {
                    continuar_op.setDisable(false);
                } else {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(text_refpago1, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    text_refpago1.clear();
                    text_refpago1.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                }

            }

        }

    }

    @FXML
    void refpago2key(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuar_op");
                    final RestrictiveTextField textorefpago1 = (RestrictiveTextField) root.lookup("#text_refpago1");
                    final RestrictiveTextField textorefpago2 = (RestrictiveTextField) root.lookup("#text_refpago2");

                    if (textorefpago2.isVisible()) {
                        if (!(textorefpago1.getText().trim().isEmpty() || textorefpago2.getText().trim().isEmpty())) {
                            buttonCont.setDisable(false);
                        } else {
                            buttonCont.setDisable(true);
                        }

                    } else {

                        if (!(textorefpago1.getText().trim().isEmpty())) {
                            buttonCont.setDisable(false);
                        } else {
                            buttonCont.setDisable(true);
                        }


                    }


                } catch (Exception ex) {
                    Logger.getLogger(SaldosController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });




    }

    @FXML
    void refpagokey(final KeyEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    final Button buttonCont = (Button) root.lookup("#continuar_op");
                    final RestrictiveTextField textorefpago1 = (RestrictiveTextField) root.lookup("#text_refpago1");
                    final RestrictiveTextField textorefpago2 = (RestrictiveTextField) root.lookup("#text_refpago2");

                    if (textorefpago2.isVisible()) {
                        if (!(textorefpago1.getText().trim().isEmpty() || textorefpago2.getText().trim().isEmpty())) {
                            buttonCont.setDisable(false);
                        } else {
                            buttonCont.setDisable(true);
                        }

                    } else {

                        if (!(textorefpago1.getText().trim().isEmpty())) {
                            buttonCont.setDisable(false);
                        } else {
                            buttonCont.setDisable(true);
                        }

                    }


                } catch (Exception ex) {
                    Logger.getLogger(SaldosController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    @FXML
    void numcuentasevento(final ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> nrocuentas = (ComboBox<String>) root.lookup("#nro_cuentas");
                        final ComboBox<String> tiposcuentas = (ComboBox<String>) root.lookup("#tipo_Cuenta");
                        final Button buttonCont = (Button) root.lookup("#continuar_consulta");


                        if (!(tiposcuentas.getSelectionModel().getSelectedItem() == null) && !(nrocuentas.getSelectionModel().getSelectedItem() == null)) {
                            buttonCont.setDisable(false);
                        } else {
                            buttonCont.setDisable(true);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(SaldosController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });


        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }
    }

    private Service<Void> consultarConv() {
        serviceConv = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> nrocuentas = (ComboBox<String>) root.lookup("#nro_cuentas");
                        final ComboBox<String> tiposcuentas = (ComboBox<String>) root.lookup("#tipo_Cuenta");

                        String RespPagoref = new String();
                        final StringBuilder tramaPagoRef = new StringBuilder();
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final convenio dataconvenio = convenio.getConvenio();



                        final Date fecha = new Date();
                        tramaPagoRef.append("900,003,");
                        tramaPagoRef.append(cliente.getRefid());
                        tramaPagoRef.append(",");
                        tramaPagoRef.append(AtlasConstantes.COD_PAGO_REF_MQ);
                        tramaPagoRef.append(",");
                        tramaPagoRef.append(cliente.getId_cliente());
                        tramaPagoRef.append(",");
                        tramaPagoRef.append(formatoFecha.format(fecha));
                        tramaPagoRef.append(",");
                        tramaPagoRef.append(formatoHora.format(fecha));
                        tramaPagoRef.append(",");
                        tramaPagoRef.append(dataconvenio.getCod_conv());
                        tramaPagoRef.append(",");
                        tramaPagoRef.append(seleccionarTipoCuenta(tiposcuentas.getSelectionModel().getSelectedItem()));
                        tramaPagoRef.append(",");
                        tramaPagoRef.append(nrocuentas.getSelectionModel().getSelectedItem());
                        tramaPagoRef.append(",");
                        tramaPagoRef.append(cliente.getContraseña());
                        tramaPagoRef.append(",~");

                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CONSULTA CONVENIO = " + "##" + obtenerHoraActual());
                            RespPagoref = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, tramaPagoRef.toString());
                            // RespPagoref = "900,003,000,0000000000,  ,01,1,                                                                           ,                                                                           ,                                                                           ,                                                                           ,NIT DE  LA EMPRESA                      ,                                        ,~";
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ CONSULTA CONVENIO = " + StringUtilities.tramaSubString(RespPagoref, 0, 1, ",") + "##" + obtenerHoraActual());
                        } catch (Exception ex) {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                            //envio a contingencia
                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ consulta convenio = " + "##" + obtenerHoraActual());
                                RespPagoref = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, tramaPagoRef.toString());
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(RespPagoref, 0, 1, ",") + "##" + obtenerHoraActual());

                            } catch (Exception ex1) {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                                        cancel();
                                        progreso_conv.progressProperty().unbind();
                                        progreso_conv.setProgress(0);
                                        progreso_conv.setVisible(false);

                                    }
                                });

                            }
                        }

                        if ("000".equals(RespPagoref.split(",")[2])) {
                            // COMPROBANTE PARA LOS LOGS
                            final String Comprobante = RespPagoref.split(",")[3];
                            final String lugarValidacion = RespPagoref.split(",")[4];
                            final String tipo_recaudo = RespPagoref.split(",")[5];
                            final String numeroref = RespPagoref.split(",")[6];
                            final String textayuda1 = RespPagoref.split(",")[7];
                            final String textayuda2 = RespPagoref.split(",")[8];
                            final String textayuda3 = RespPagoref.split(",")[9];
                            final String textayuda4 = RespPagoref.split(",")[10];
                            final String referencia1 = RespPagoref.split(",")[11];
                            final String referencia2 = RespPagoref.split(",")[12];

                            dataconvenio.setComprobante(Comprobante);
                            dataconvenio.setLugar_validadacion(lugarValidacion);
                            dataconvenio.setNumero_referencia(numeroref);
                            dataconvenio.setTipo_recaudo(tipo_recaudo);
                            dataconvenio.setTexto_ayuda1(textayuda1);
                            dataconvenio.setTexto_ayuda2(textayuda2);
                            dataconvenio.setTexto_ayuda3(textayuda3);
                            dataconvenio.setTexto_ayuda4(textayuda4);
                            dataconvenio.setReferencia(referencia1.trim());
                            dataconvenio.setReferencia2(referencia2.trim());

                            convenio.setConvenio(dataconvenio);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                                    final ComboBox<String> nrocuentas = (ComboBox<String>) root.lookup("#nro_cuentas");
                                    final ComboBox<String> tiposcuentas = (ComboBox<String>) root.lookup("#tipo_Cuenta");
                                    final VBox panel_ref = (VBox) root.lookup("#panel_ref");
                                    final HBox consulta_conv = (HBox) root.lookup("#panel_consulta_conv");
                                    final RestrictiveTextField text_refpago1 = (RestrictiveTextField) root.lookup("#text_refpago1");
                                    final RestrictiveTextField text_refpago2 = (RestrictiveTextField) root.lookup("#text_refpago2");
                                    final Label tooltip = (Label) root.lookup("#tooltip_ref2");
                                    final Label labelrefPago2 = (Label) root.lookup("#labelrefPago2");
                                    final Label ref_pago = (Label) root.lookup("#ref_pago1");
                                    final Label ref_pago2 = (Label) root.lookup("#ref_pago2");
                                    final ProgressBar progreso_conv = (ProgressBar) root.lookup("#progreso_conv");
                                    consulta_conv.setVisible(false);
                                    panel_ref.setVisible(true);
                                    progreso_conv.setVisible(false);

                                    nrocuentas.setDisable(true);
                                    tiposcuentas.setDisable(true);

                                    if ("1".equals(dataconvenio.getNumero_referencia()) || "0".equals(dataconvenio.getNumero_referencia())) {
                                        text_refpago1.setVisible(true);
                                        text_refpago2.setVisible(false);
                                        tooltip.setVisible(false);
                                        labelrefPago2.setVisible(false);
                                        ref_pago2.setVisible(false);
                                        ref_pago.setText(dataconvenio.getReferencia().trim());
                                    } else {
                                        ref_pago.setText(dataconvenio.getReferencia().trim());
                                        ref_pago2.setText(dataconvenio.getReferencia2().trim());
                                        text_refpago1.setVisible(true);
                                        text_refpago2.setVisible(true);
                                        tooltip.setVisible(true);
                                        labelrefPago2.setVisible(true);
                                        ref_pago2.setVisible(true);
                                    }
                                }
                            });



                        } else {
                            final String coderror = RespPagoref.split(",")[2];
                            final String mensaje = RespPagoref.split(",")[3].trim();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                                    cancel();
                                    //continuar_Op.cancel();
                                    progreso_conv.progressProperty().unbind();
                                    progreso_conv.setProgress(0);
                                    progreso_conv.setVisible(false);
                                }
                            });


                        }

                        return null;
                    }
                };



            }
        };
        return serviceConv;


    }

    ;

    
    private Service<Void> continuar_Op() {
        serviceConsPagaCuenta = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                        final ComboBox<String> nrocuentas = (ComboBox<String>) root.lookup("#nro_cuentas");
                        final ComboBox<String> tiposcuentas = (ComboBox<String>) root.lookup("#tipo_Cuenta");
                        final RestrictiveTextField referencia_Pago1 = (RestrictiveTextField) root.lookup("#text_refpago1");
                        final RestrictiveTextField referencia_Pago2 = (RestrictiveTextField) root.lookup("#text_refpago2");
                        final Label refpago2 = (Label) root.lookup("#ref_pago2");
                        final ConectSS servicioSS = new ConectSS();
                        final Cliente cliente = Cliente.getCliente();
                        final Date fecha = new Date();
                        final convenio dataconvenio = convenio.getConvenio();
                        dataconvenio.setReferencia2(refpago2.getText());
                        convenio.setConvenio(dataconvenio);

                        /**
                         * validaciones para mostrar el tipo de interfaz de pago
                         * lugar de validacion "1" si valida en bancolombia
                         * lugar de validacion "0" si valida en redenban
                         */
                        /*CODIFICACION AUTOMATICA*/
                        final Runnable aFenix = new EnvioTipAutFenix(cliente.getCodemp(), "003");
                        final Thread aFen = new Thread(aFenix);
                        aFen.start();

                        if ("01".equals(dataconvenio.getLugar_validadacion())) {
//                            System.out.println("pago bancolombia 01");
                            //PAGO BANCOLOMBIA                                     
                            /**
                             * consulta paga cuentas
                             */
                            final StringBuilder tramaCpagaCuentas = new StringBuilder();
                            String RespConsultaPago = new String();
                            tramaCpagaCuentas.append("900,004,");
                            tramaCpagaCuentas.append(cliente.getRefid());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(validarCodigoCuentaMQ(tiposcuentas.getSelectionModel().getSelectedItem()));
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(cliente.getId_cliente());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(formatoFecha.format(fecha));
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(formatoHora.format(fecha));
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(seleccionarTipoCuenta(tiposcuentas.getSelectionModel().getSelectedItem()));
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(nrocuentas.getSelectionModel().getSelectedItem());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(dataconvenio.getCod_conv());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(referencia_Pago1.getText());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append("0");
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(cliente.getContraseña());
                            tramaCpagaCuentas.append(",~");

                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CONSULTA PAGA CUENTAS = " + "##" + obtenerHoraActual());
                                RespConsultaPago = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, tramaCpagaCuentas.toString());
                                //RespConsultaPago="900,004,000,0000000000,000000000035500,00,NUMERO FACTURA (5 ESPACIOS NUMERICOS),0000000040616000901, ,55555,FIDUCIARIA BANCOLOMBIA 5837      ,~";
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ CONSULTA PAGA CUENTAS = " + StringUtilities.tramaSubString(RespConsultaPago, 0, 1, ",") + "##" + obtenerHoraActual());
                            } catch (Exception ex) {

                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ consulta paga ctas= " + "##" + obtenerHoraActual());
                                    RespConsultaPago = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, tramaCpagaCuentas.toString());
                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(RespConsultaPago, 0, 1, ",") + "##" + obtenerHoraActual());

                                } catch (Exception ex1) {
                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                                            continuar_Op().cancel();
                                            // continuar_Op.cancel();
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);

                                        }
                                    });

                                }
                            }

                            if ("000".equals(RespConsultaPago.split(",")[2])) {
                                final infoPago dataPago = new infoPago();
                                dataPago.setComprobante(RespConsultaPago.split(",")[3]);
                                dataPago.setValorPagarent(RespConsultaPago.split(",")[4]);
                                dataPago.setValorpagarCent(RespConsultaPago.split(",")[5]);
                                dataPago.setReferenciaConvenio(RespConsultaPago.split(",")[6]);
                                dataPago.setCuentaOrigen(StringUtilities.eliminarCerosLeft(RespConsultaPago.split(",")[7]));
                                dataPago.setDescripcionCuentaOrigen(tiposcuentas.getSelectionModel().getSelectedItem());
                                dataPago.setNumeroFactura(RespConsultaPago.split(",")[9]);
                                dataPago.setNombreConvenio(RespConsultaPago.split(",")[10]);
                                dataPago.setReferenciaPago1(referencia_Pago1.getText());
                                dataPago.setReferenciaPago2(referencia_Pago2.getText());
                                infoPago.setInfoPago(dataPago);

                                /**
                                 * fin consulta paga cuentas
                                 */
                                // PAGO CONVENCIONAL
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Atlas.getVista().mostrarMenupagos2(convenio.getConvenio(), infoPago.getInfoPago(), false);
                                    }
                                });


                            } else {
                                final String coderror = RespConsultaPago.split(",")[2];
                                final String mensaje = RespConsultaPago.split(",")[3].trim();

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                                        continuar_Op().cancel();
                                        //continuar_Op.cancel();
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                });

                            }




                        } else if ("02".equals(convenio.getConvenio().getLugar_validadacion())) {

                            /**
                             * consulta paga cuentas
                             */
                            final StringBuilder tramaCpagaCuentas = new StringBuilder();
                            String RespConsultaPago = new String();
                            tramaCpagaCuentas.append("900,004,");
                            tramaCpagaCuentas.append(cliente.getRefid());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(validarCodigoCuentaMQ(tiposcuentas.getSelectionModel().getSelectedItem()));
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(cliente.getId_cliente());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(formatoFecha.format(fecha));
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(formatoHora.format(fecha));
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(seleccionarTipoCuenta(tiposcuentas.getSelectionModel().getSelectedItem()));
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(nrocuentas.getSelectionModel().getSelectedItem());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(dataconvenio.getCod_conv());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(referencia_Pago1.getText());
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append("0");
                            tramaCpagaCuentas.append(",");
                            tramaCpagaCuentas.append(cliente.getContraseña());
                            tramaCpagaCuentas.append(",~");

                            try {
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CONSULTA PAGA CUENTAS = " + "##" + obtenerHoraActual());
                                RespConsultaPago = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, tramaCpagaCuentas.toString());
                                // RespConsultaPago="900,004,000,0000000000,000000000035500,00,NUMERO FACTURA (5 ESPACIOS NUMERICOS),0000000040616000901, ,55555,FIDUCIARIA BANCOLOMBIA 5837      ,~";
                                //RespConsultaPago = "900,004,000,12345,100000,52,REFCONVENIO,7,ahorros_corriente,nrofact9874512,CONVENIOTEST,~";
                                // Thread.sleep(400);
                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ CONSULTA PAGA CUENTAS = " + StringUtilities.tramaSubString(RespConsultaPago, 0, 1, ",") + "##" + obtenerHoraActual());
                            } catch (Exception ex) {

                                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                                //envio a contingencia
                                try {
                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ consulta paga ctas = " + "##" + obtenerHoraActual());
                                    RespConsultaPago = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, tramaCpagaCuentas.toString());
                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(RespConsultaPago, 0, 1, ",") + "##" + obtenerHoraActual());

                                } catch (Exception ex1) {
                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                                            continuar_Op().cancel();
                                            // continuar_Op.cancel();
                                            progreso.progressProperty().unbind();
                                            progreso.setProgress(0);
                                            progreso.setVisible(false);

                                        }
                                    });

                                }
                            }

                            if ("000".equals(RespConsultaPago.split(",")[2])) {
                                final infoPago dataPago = new infoPago();
                                dataPago.setComprobante(RespConsultaPago.split(",")[3]);
                                dataPago.setValorPagarent(RespConsultaPago.split(",")[4]);
                                dataPago.setValorpagarCent(RespConsultaPago.split(",")[5]);
                                dataPago.setReferenciaConvenio(RespConsultaPago.split(",")[6]);
                                dataPago.setCuentaOrigen(StringUtilities.eliminarCerosLeft(RespConsultaPago.split(",")[7]));
                                dataPago.setDescripcionCuentaOrigen(tiposcuentas.getSelectionModel().getSelectedItem());
                                dataPago.setNumeroFactura(RespConsultaPago.split(",")[9]);
                                dataPago.setNombreConvenio(RespConsultaPago.split(",")[10]);
                                dataPago.setReferenciaPago1(referencia_Pago1.getText());
                                dataPago.setReferenciaPago2(referencia_Pago2.getText());
                                infoPago.setInfoPago(dataPago);

                                /**
                                 * fin consulta paga cuentas
                                 */
                                // PAGO  REDEBAN                                                                                                         
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Atlas.getVista().mostrarMenupagos2(convenio.getConvenio(), infoPago.getInfoPago(), false);
                                    }
                                });


                            } else {
                                final String coderror = RespConsultaPago.split(",")[2];
                                final String mensaje = RespConsultaPago.split(",")[3].trim();

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                                        continuar_Op().cancel();
                                        //continuar_Op.cancel();
                                        progreso.progressProperty().unbind();
                                        progreso.setProgress(0);
                                        progreso.setVisible(false);
                                    }
                                });

                            }

                        } else {
                            if ("01".equals(convenio.getConvenio().getTipo_recaudo())) {
                                // PAGO X CONCEPTOS , NO CONSULTA EL VALOR A PAGAR
                                final infoPago dataPago = new infoPago();
                                dataPago.setCuentaOrigen(nrocuentas.getSelectionModel().getSelectedItem());
                                dataPago.setDescripcionCuentaOrigen(tiposcuentas.getSelectionModel().getSelectedItem());
                                dataPago.setReferenciaPago1(referencia_Pago1.getText());
                                dataPago.setReferenciaPago2(referencia_Pago2.getText() == null ? "" : referencia_Pago2.getText());
                                dataPago.setNumeroFactura(referencia_Pago1.getText());
                                infoPago.setInfoPago(dataPago);

                                // PAGO REFERENCIADO X CONCEPTOS                                                                                                                                           
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Atlas.getVista().mostrarMenuxconceptos(convenio.getConvenio(), infoPago.getInfoPago());
                                    }
                                });



                            } else {
                                // PAGO REFERENCIADO 
                                final infoPago dataPago = new infoPago();
                                dataPago.setCuentaOrigen(nrocuentas.getSelectionModel().getSelectedItem());
                                dataPago.setDescripcionCuentaOrigen(tiposcuentas.getSelectionModel().getSelectedItem());
                                dataPago.setReferenciaPago1(referencia_Pago1.getText());
                                dataPago.setReferenciaPago2(referencia_Pago2.getText() == null ? "" : referencia_Pago2.getText());
                                dataPago.setNumeroFactura(referencia_Pago1.getText());
                                infoPago.setInfoPago(dataPago);
                                // PAGO REFERENCIADO , NO CONSULTA EL VALOR A PAGAR                                                                                                                                          
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Atlas.getVista().mostrarMenupagos2(convenio.getConvenio(), infoPago.getInfoPago(), true);
                                    }
                                });

                            }

                        }

                        /**
                         * fin validacion
                         */
                        return null;
                    }
                };


            }
        };

        return serviceConsPagaCuenta;
    }

    ;
    

    public String seleccionarTipoCuenta(final String NomnreCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(NomnreCuenta.trim())) {
            retorno.append("7");
        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(NomnreCuenta.trim())) {
            retorno.append("1");
        }
        return retorno.toString();
    }

    private String validarCodigoCuentaMQ(final String TipoCuenta) {
        final StringBuilder retorno = new StringBuilder();

        if (CodigoProductos.CUENTA_AHORROS.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0561");

        } else if (CodigoProductos.CUENTA_CORRIENTE.equalsIgnoreCase(TipoCuenta)) {
            retorno.append("0461");
        }
        //para mas validaciones
        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
