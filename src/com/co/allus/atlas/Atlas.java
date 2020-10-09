/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.atlas;

import com.co.allus.controladoresfxml.ConsultaAlertasEnvInitController;
import com.co.allus.controladoresfxml.GirosNalCancelacionController;
import com.co.allus.controladoresfxml.GirosNalInfoGeneralController;
import com.co.allus.controladoresfxml.MarcoPrincipalController;
import com.co.allus.controladoresfxml.MascaraTransaccionalController;
import com.co.allus.controladoresfxml.PagosATerceroMasFactController;
import com.co.allus.controladoresfxml.PagosATercerosInitController;
import com.co.allus.controladoresfxml.PagosTercerosController;
import com.co.allus.controladoresfxml.ParametrizacionAlertasConsController;
import com.co.allus.controladoresfxml.ParametrizacionAlertasController;
import com.co.allus.controladoresfxml.PuntosColListarMovimientosController;
import com.co.allus.controladoresfxml.PuntosColListarTarjetasController;
import com.co.allus.controladoresfxml.SaldoAFCController;
import com.co.allus.controladoresfxml.SaldoAFCfinController;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.evidente.ModelEvidente;
import com.co.allus.modelo.pagoprestamos.DatosPagoPrestamo;
import com.co.allus.modelo.pagosaterceros.ModelTablaInfoPago;
import com.co.allus.modelo.pagosaterceros.convenio;
import com.co.allus.modelo.pagosaterceros.infoPago;
import com.co.allus.modelo.pagosaterceros.informacionPagofin1;
import com.co.allus.modelo.pagosaterceros.informacionPagofin2;
import com.co.allus.modelo.pagosaterceros.valorApagar;
import com.co.allus.modelo.transfctaprop.infoTranferenciaCtaProp;
import com.co.allus.modelo.transfctaprop.informacionTransfin1;
import com.co.allus.modelo.transfctaprop.informacionTransfin2;
import com.co.allus.userComponent.RestrictiveTDCtextField;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.GestionBaseDatos;
import com.co.allus.utils.StringUtilities;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.swing.ImageIcon;

/**
 *
 * @author alexander.lopez.o
 */
public class Atlas extends Application {

    public static Stage vista;
    private boolean firstTime;
    private TrayIcon trayIcon;
    private static Atlas gestionelplus;
    private Scene scene;
    private transient final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private transient final DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private GestorDocumental docs = new GestorDocumental();
    public static final String MENSAJE_CREDITO_H_COMBO = "Seleccione un crédito hipotecario";
    private FXMLLoader fxmlLoader;
    private Parent root;
    private final URL marcoprincipalURL = getClass().getResource("/com/co/allus/vistas/MarcoPrincipal.fxml");

    public Atlas() {
        super();
        gestionelplus = this;
    }

    public static Atlas getVista() {
        return gestionelplus;
    }

    public static void setVista(final Stage vista) {
        Atlas.vista = vista;
    }

    public void reloadStage() throws IOException {
        fxmlLoader = new FXMLLoader(marcoprincipalURL);
        //fxmlLoader = new FXMLLoader(getClass().getResource("/com/co/allus/vistas/MarcoPrincipal.fxml"));
        root = (Parent) fxmlLoader.load();
        final Atlas.DigitalClock hora = new Atlas.DigitalClock();
        final GridPane dato = (GridPane) root.lookup("#pane_arriba");
        final Pane panel_hora = (Pane) dato.getChildren().get(2);
        panel_hora.getChildren().set(0, hora);
        final Group root1 = new Group();
        root1.getChildren().addAll(root);
        scene = new Scene(root1);
        final Image image = new Image(getClass().getResourceAsStream("/com/co/allus/recursos/allusico.PNG"));
        vista.getIcons().add(image);
        vista.setScene(scene);
        vista.centerOnScreen();
        vista.setWidth(757);
        vista.setHeight(645);
        vista.setMaxWidth(757);
        vista.setMaxHeight(645);
        //  vista.setResizable(false);
        Platform.setImplicitExit(false);
    }

    public void mostrarSaldosCreditoH(final String menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/SaldoCreditoH.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final ComboBox<String> creditos_h = (ComboBox<String>) root.lookup("#creditosHipotecarios");

                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    creditos_h.setItems(emptyList);
                    final List<String> datos = new ArrayList<String>();

                    final Cliente datosCliente = Cliente.getCliente();
                    final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                    final Set<String> keySet = productos.keySet();
                    datos.add(Atlas.MENSAJE_CREDITO_H_COMBO);

                    for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        final String cuentatipo = val.next();
                        /* validacion solo se pérmite cuenta ahorros y corriente */
                        if (CodigoProductos.CREDITO_HIPOTECARIO.equals(cuentatipo)) {
                            final ArrayList<String> cuentas = productos.get(cuentatipo);
                            for (int i = 0; i < cuentas.size(); i++) {
                                datos.add(cuentas.get(i));
                            }
                        }
                    }
                    creditos_h.setItems(FXCollections.observableArrayList(datos));
                    creditos_h.getSelectionModel().select(Atlas.MENSAJE_CREDITO_H_COMBO);

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

                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.HAND);
                                    cancelar.setEffect(shadow);
                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.DEFAULT);
                                    cancelar.setEffect(null);
                                }
                            });

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

    public void mostrarDesbloSClaveCorp(final String menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/DesbloqSClaveCorp.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");

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

                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.HAND);
                                    cancelar.setEffect(shadow);
                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.DEFAULT);
                                    cancelar.setEffect(null);
                                }
                            });
                    
                    botoncontinuarOp.setDisable(true);
                    // final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbolDesblogSegClave");
                    // arbol_pagos.setDisable(true);

                    label_menu.setVisible(false);

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

    /**
     * MENE PAGOS TDC NO PROPIA
     *
     * @param menu
     */
    public void mostrarMenuPagosTDCnoPropia(final String menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosTDCnoPropias.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    //
                    label_menu.setVisible(false);
                    // cuenta de origen 
                    final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                    final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");

                    final RestrictiveTDCtextField tarjetas = (RestrictiveTDCtextField) root.lookup("#num_tarjeta");
                    tarjetas.setMaxLength(16);
                    tarjetas.setRestrict("[0-9]");
                    tarjetas.setIsAlphanum(false);

                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    tipocta_origen.setItems(emptyList);
                    cuenta_origen.setItems(emptyList);

                    final List<String> datos = new ArrayList<String>();
                    final List<String> datos2 = new ArrayList<String>();

                    datos.add("Tipo de Cuenta");
                    datos2.add("Seleccione una cuenta");

                    final Cliente datosCliente = Cliente.getCliente();
                    final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                    final Set<String> keySet = productos.keySet();

                    for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        String cuentatipo = val.next();
                        /* validacion solo se pérmite cuenta ahorros y corriente */
                        if (CodigoProductos.CUENTA_AHORROS.equals(cuentatipo) || CodigoProductos.CUENTA_CORRIENTE.equals(cuentatipo)) {
                            datos.add(cuentatipo);
                        }
                    }

                    tipocta_origen.setItems(FXCollections.observableArrayList(datos));
                    tipocta_origen.getSelectionModel().select("Tipo de Cuenta");
                    cuenta_origen.setItems(FXCollections.observableArrayList(datos2));
                    cuenta_origen.getSelectionModel().select("Seleccione una cuenta");

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

                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.HAND);
                                    cancelar.setEffect(shadow);
                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.DEFAULT);
                                    cancelar.setEffect(null);
                                }
                            });
                    // desabilitar arboles y boton conutinuar 
                    botoncontinuarOp.setDisable(true);
                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                    arbol_pagos.setDisable(true);
                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                    arbol_saldos.setDisable(true);
                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                    arbol_transf.setDisable(true);

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception e) {
                        docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
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

    public void mostrarPanePagos(final String menu) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final GestionBaseDatos conexionBd = new GestionBaseDatos();
                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosTerceros.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();
                    PagosTercerosController.listCategoria.setItems(emptyObservableList);
                    PagosTercerosController.listCategoria.setItems(conexionBd.consultarDatosCategorias());
                    PagosTercerosController.listCategoria.getSelectionModel().select("SELECCIONE");
                    final Button botonBuscar = (Button) root.lookup("#buscar_conv");
                    final Button botonlimpiar = (Button) root.lookup("#limpiar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuarIngresoDat");
                    botonBuscar.setDisable(true);
                    final RestrictiveTextField textcodConv = (RestrictiveTextField) root.lookup("#textcodConv");
                    final RestrictiveTextField textnomConv = (RestrictiveTextField) root.lookup("#textnomConv");
                    //Restricciones de los campos CodigoConvenio y NombreConvenio
                    textcodConv.setMaxLength(10);
                    textcodConv.setRestrict("[0-9]");
                    textcodConv.setIsAlphanum(false);
                    textnomConv.setMaxLength(45);
                    textnomConv.setIsAlphanum(true);
                    textnomConv.setRestrict("");
                    // eventos del textfield
                    textcodConv.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (newValue.isEmpty()) {
                                botonBuscar.setDisable(true);
                            } else {
                                botonBuscar.setDisable(false);
                            }
                        }
                    });

                    textnomConv.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (newValue.isEmpty()) {
                                botonBuscar.setDisable(true);
                            } else {
                                botonBuscar.setDisable(false);
                            }
                        }
                    });

                    final DropShadow shadow = new DropShadow();

                    botonBuscar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botonBuscar.setCursor(Cursor.HAND);
                                    botonBuscar.setEffect(shadow);
                                }
                            });

                    botonBuscar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botonBuscar.setCursor(Cursor.DEFAULT);
                                    botonBuscar.setEffect(null);
                                }
                            });

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

                    botonlimpiar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botonlimpiar.setCursor(Cursor.HAND);
                                    botonlimpiar.setEffect(shadow);
                                }
                            });

                    botonlimpiar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    botonlimpiar.setCursor(Cursor.DEFAULT);
                                    botonlimpiar.setEffect(null);
                                }
                            });
                    
                    botoncontinuarOp.setDisable(true);
                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                    arbol_pagos.setDisable(true);
                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                    arbol_saldos.setDisable(true);
                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                    arbol_transf.setDisable(true);
                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception e) {
                        docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                    }
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    public void mostrasmenuTransfctapropfin(final infoTranferenciaCtaProp infotransf) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/TransferCtaPropiasFin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final TableView<informacionTransfin1> tablafintransf1 = (TableView<informacionTransfin1>) root.lookup("#tabla_datos_fin1");
                    final TableView<informacionTransfin2> tablafintransf2 = (TableView<informacionTransfin2>) root.lookup("#tabla_datos_fin2");
                    tablafintransf1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    tablafintransf2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    /**
                     * se cargan los valores de la informacion del finpago
                     */
                    final ObservableList<informacionTransfin1> datosInfotransf = FXCollections.observableArrayList();
                    final informacionTransfin1 datostransfin1 = new informacionTransfin1(infotransf.getCta_origen(), infotransf.getTipo_cta_origen(), infotransf.getCta_destino(), infotransf.getTipo_cta_destino());
                    datosInfotransf.add(datostransfin1);
                    tablafintransf1.setItems(datosInfotransf);
                    /**
                     * ***************************
                     */
                    final ObservableList<informacionTransfin2> datosInfotransf2 = FXCollections.observableArrayList();
                    final informacionTransfin2 datostransfin2 = new informacionTransfin2(infotransf.getComprobante(), "$ " + formatonum.format(Double.parseDouble(infotransf.getValor_transferirEnt())).replace(".", ",") + "." + infotransf.getValor_transferirCent(), infotransf.getFechaTransf());
                    datosInfotransf2.add(datostransfin2);
                    tablafintransf2.setItems(datosInfotransf2);
                    /**
                     * fin validacion
                     */
                    /**
                     * se cargar los valores de la informacion del pago
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
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes( "Error en la aplicacion \n , "
                            + "es posible que el  pago se halla realizado correctamente , "
                            + "por favor no volver a intertalo e informar al area tecnica",
                            "Error",
                            ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                }
            }
        });
    }

    public void mostrasmenuPagosfin(final convenio dataconvenio, final infoPago datainfoPago) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosTercerosfinTrx.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final TableView<informacionPagofin1> tablafinpagos1 = (TableView<informacionPagofin1>) root.lookup("#tabla_datos_fin1");
                    final TableView<informacionPagofin2> tablafinpagos2 = (TableView<informacionPagofin2>) root.lookup("#tabla_datos_fin2");
                    tablafinpagos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    tablafinpagos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    /**
                     * se cargan los valores de la informacion del finpago
                     */
                    final ObservableList<informacionPagofin1> datosInfoPago = FXCollections.observableArrayList();
                    final informacionPagofin1 datoPagofin1 = new informacionPagofin1(dataconvenio.getCod_conv(), dataconvenio.getNom_conv(), datainfoPago.getReferenciaPago1(), datainfoPago.getReferenciaPago2());
                    datosInfoPago.add(datoPagofin1);
                    tablafinpagos1.setItems(datosInfoPago);
                    tablafinpagos1.getColumns().get(2).setText("Referencia de Pago:\n" + dataconvenio.getReferencia().toLowerCase());
                    tablafinpagos1.getColumns().get(3).setText("Referencia de Pago:\n" + dataconvenio.getReferencia2().toLowerCase());
                    /**
                     * ***************************
                     */
                    final ObservableList<informacionPagofin2> datosInfoPago2 = FXCollections.observableArrayList();
                    final informacionPagofin2 datoPagofin2 = new informacionPagofin2(datainfoPago.getComprobantefinPago(), "$ " + formatonum.format(Double.parseDouble(datainfoPago.getValorPagarent())).replace(".", ",") + "." + datainfoPago.getValorpagarCent(), datainfoPago.getCuentaOrigen(), datainfoPago.getDescripcionCuentaOrigen(), datainfoPago.getFechaPago());
                    datosInfoPago2.add(datoPagofin2);
                    tablafinpagos2.setItems(datosInfoPago2);
                    /**
                     * validacion dinamica segunda referencia
                     */
                    if (datainfoPago.getReferenciaPago2().isEmpty()) {
                        tablafinpagos1.getColumns().remove(3);
                    }
                    /**
                     * fin validacion
                     */
                    /**
                     * se cargar los valores de la informacion del pago
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
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n "
                            + ", es posible que el  pago se halla realizado correctamente , "
                            + "por favor no volver a intertalo e informar al area tecnica", "Error", 
                            ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_PAGOS_TERCEROS);
                }
            }
        });
    }

    public void mostrarMenuxconceptos(final convenio dataConvenio, final infoPago informacionPago) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosTercerosPagoxConceptos.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_op");
                    final ProgressBar progreso = (ProgressBar) root.lookup("#progreso");
                    final Label tituloconv = (Label) root.lookup("#titulo_conv");

                    final TableView<ModelTablaInfoPago> tablapagos = (TableView<ModelTablaInfoPago>) root.lookup("#tabla_datos");

                    tablapagos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    /**
                     * se cargar los valores de la informacion del pago
                     */
                    final ObservableList<ModelTablaInfoPago> datosInfoPago = FXCollections.observableArrayList();
                    final ModelTablaInfoPago datoTablaPago = new ModelTablaInfoPago(informacionPago.getReferenciaPago1(), informacionPago.getDescripcionCuentaOrigen(), informacionPago.getCuentaOrigen());
                    datosInfoPago.add(datoTablaPago);
                    tablapagos.setItems(datosInfoPago);
                    tablapagos.getColumns().get(0).setText("Referencia de Pago:\n" + dataConvenio.getReferencia());
                    tituloconv.setText(dataConvenio.getCod_conv() + " - " + dataConvenio.getNom_conv());
                    /**
                     * se cargar los valores de la informacion del pago
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

                    progreso.setVisible(false);

                    /**
                     * se repinta la vista en particular
                     */
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                }
            }
        });
    }

    public void mostrarMenupagos2(final convenio dataConvenio, final infoPago informacionPago, final boolean novalidaPSP) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/PagosTercerosDatospago2.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#continuar_op");
                    final Button cancelar_op = (Button) root.lookup("#cancelar_op");
                    final ProgressBar progreso = (ProgressBar) root.lookup("#progreso");
                    final Label tituloconv = (Label) root.lookup("#titulo_conv");
                    final TextField inputValorPagar = (TextField) root.lookup("#valor_pagar");
                    final TableView<ModelTablaInfoPago> tablapagos = (TableView<ModelTablaInfoPago>) root.lookup("#tabla_datos");
                    final TableView<valorApagar> tablavalor = (TableView<valorApagar>) root.lookup("#tabla_Valor");
                    tablapagos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    tablavalor.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    /**
                     * se cargar los valores de la informacion del pago y el
                     * valor a pagar
                     */
                    final ObservableList<ModelTablaInfoPago> datosInfoPago = FXCollections.observableArrayList();
                    final ModelTablaInfoPago datoTablaPago = new ModelTablaInfoPago(informacionPago.getReferenciaPago1(), informacionPago.getDescripcionCuentaOrigen(), informacionPago.getCuentaOrigen());
                    datosInfoPago.add(datoTablaPago);
                    tablapagos.setItems(datosInfoPago);
                    tablapagos.getColumns().get(0).setText(("Referencia de Pago:\n" + dataConvenio.getReferencia().trim().toLowerCase().trim()));
                    tituloconv.setText(dataConvenio.getCod_conv() + " - " + dataConvenio.getNom_conv());
                    /**
                     * se cargar los valores de la informacion del pago
                     */
                    /**
                     * validacion para mostrar el valor fijo , o valor a
                     * ingresar
                     */
                    if (novalidaPSP) {
                        inputValorPagar.setVisible(true);
                        tablavalor.setVisible(false);
                        final Button buttonCont = (Button) root.lookup("#continuar_op");
                        buttonCont.setDisable(true);
                    } else {
                        final ObservableList<valorApagar> valorpagar = FXCollections.observableArrayList();
                        final valorApagar valor = new valorApagar("$" + formatonum.format(Double.parseDouble(informacionPago.getValorPagarent())).replace(".", ",") + "." + informacionPago.getValorpagarCent());
                        valorpagar.add(valor);
                        tablavalor.setItems(valorpagar);
                        inputValorPagar.setVisible(false);
                        tablavalor.setVisible(true);
                    }
                    /**
                     * restricciones campo de texto
                     */
                    final RestrictiveTextField textpago = (RestrictiveTextField) root.lookup("#valor_pagar");
                    textpago.setMaxLength(15);
                    //textpago.setRestrict("[0-9]+([,\\.][0-9]*)?$");
                    // solo se permiten numeros sin ceros a la izquerda y hasta dos decimales separados por "." o ","
                    textpago.setRestrict("[1-9][0-9]*+([,\\.][0-9]{0,2})?$");
                    textpago.setIsAlphanum(false);
                    /**
                     * restricciones campo de texto
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

                    progreso.setVisible(false);

                    /**
                     * se repinta la vista en particular
                     */
                    final VBox panelOpciones = (VBox) root.lookup("#opciones");
                    panelOpciones.setLayoutX(4);
                    panelOpciones.setLayoutY(229);
                    pane.getChildren().remove(3);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                }
            }
        });
    }

    public void mostrarMenuTransfctasprop(final String menu) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL location = getClass().getResource("/com/co/allus/vistas/TransferCtasPropias.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    //final ObservableList<String> emptyObservableList = FXCollections.emptyObservableList();                   
                    final Button cancelar = (Button) root.lookup("#cancelar");
                    final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");
                    final Label label_menu = (Label) pane.lookup("#label_saldos");
                    final RestrictiveTextField textvaltranf = (RestrictiveTextField) root.lookup("#valor_transferir");
                    //Restricciones de los campos CodigoConvenio y NombreConvenio
                    textvaltranf.setMaxLength(15);
                    // solo se permiten numeros sin ceros a la izquerda y hasta dos decimales separados por "." o ","
                    textvaltranf.setRestrict("[1-9][0-9]*+([,\\.][0-9]{0,2})?$");
                    textvaltranf.setIsAlphanum(false);
                    //
                    label_menu.setVisible(false);
                    // cuenta de origen 
                    final ComboBox<String> tipocta_origen = (ComboBox<String>) root.lookup("#tipocta_origen");
                    final ComboBox<String> cuenta_origen = (ComboBox<String>) root.lookup("#cuenta_origen");
                    final ComboBox<String> tipocta_destino = (ComboBox<String>) root.lookup("#tipocta_destino");
                    final ComboBox<String> cuenta_destino = (ComboBox<String>) root.lookup("#cuenta_destino");
                    final ObservableList<String> emptyList = FXCollections.emptyObservableList();
                    tipocta_origen.setItems(emptyList);
                    cuenta_origen.setItems(emptyList);
                    tipocta_destino.setItems(emptyList);
                    cuenta_destino.setItems(emptyList);
                    final List<String> datos = new ArrayList<String>();
                    final List<String> datos2 = new ArrayList<String>();
                    final List<String> datos3 = new ArrayList<String>();
                    final List<String> datos4 = new ArrayList<String>();

                    final Cliente datosCliente = Cliente.getCliente();
                    final HashMap<String, ArrayList<String>> productos = datosCliente.getProductos();
                    final Set<String> keySet = productos.keySet();
                    datos.add("Tipo de Cuenta");
                    datos2.add("Seleccione una cuenta");
                    datos3.add("Tipo de Cuenta");
                    datos4.add("Seleccione una cuenta");
                    for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        String cuentatipo = val.next();
                        /* validacion solo se pérmite cuenta ahorros y corriente */
                        if (CodigoProductos.CUENTA_AHORROS.equals(cuentatipo) || CodigoProductos.CUENTA_CORRIENTE.equals(cuentatipo)) {
                            datos.add(cuentatipo);
                        }
                    }
                    tipocta_origen.setItems(FXCollections.observableArrayList(datos));
                    tipocta_origen.getSelectionModel().select("Tipo de Cuenta");
                    cuenta_origen.setItems(FXCollections.observableArrayList(datos2));
                    cuenta_origen.getSelectionModel().select("Seleccione una cuenta");
                    tipocta_destino.setItems(FXCollections.observableArrayList(datos3));
                    tipocta_destino.getSelectionModel().select("Tipo de Cuenta");
                    cuenta_destino.setItems(FXCollections.observableArrayList(datos4));
                    cuenta_destino.getSelectionModel().select("Seleccione una cuenta");
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

                    cancelar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.HAND);
                                    cancelar.setEffect(shadow);
                                }
                            });

                    cancelar.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    cancelar.setCursor(Cursor.DEFAULT);
                                    cancelar.setEffect(null);
                                }
                            });
                    
                    botoncontinuarOp.setDisable(true);
                    final TreeView<String> arbol_pagos = (TreeView<String>) pane.lookup("#arbol_pagos");
                    arbol_pagos.setDisable(true);
                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                    arbol_saldos.setDisable(true);
                    final TreeView<String> arbol_transf = (TreeView<String>) pane.lookup("#arbol_transferencias");
                    arbol_transf.setDisable(true);
                    final ArrayList<String> cuentasaho = productos.get(CodigoProductos.CUENTA_AHORROS) == null ? new ArrayList<String>() : productos.get(CodigoProductos.CUENTA_AHORROS);
                    final ArrayList<String> cuentascte = productos.get(CodigoProductos.CUENTA_CORRIENTE) == null ? new ArrayList<String>() : productos.get(CodigoProductos.CUENTA_CORRIENTE);

                    if (cuentasaho.size() == 1 && cuentascte.isEmpty()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes("No se puede realizar la transacción ya que el cliente\n posee una sola cuenta de depósito\nTipo De Cuenta : " + CodigoProductos.CUENTA_AHORROS, "Advertencia", ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);
                            }
                        });
                    } else if (cuentascte.size() == 1 && cuentasaho.isEmpty()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes("No se puede realizar la transacción ya que el cliente\n posee una sola cuenta de depósito\nTipo De Cuenta : " + CodigoProductos.CUENTA_CORRIENTE, "Advertencia", ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);
                            }
                        });
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

    @Override
    public void start(final Stage primaryStage) throws Exception {
        /**
         * performance optimization fxmlloader
         */
        fxmlLoader = new FXMLLoader(marcoprincipalURL);
        //fxmlLoader = new FXMLLoader(getClass().getResource("/com/co/allus/vistas/MarcoPrincipal.fxml"));
        root = (Parent) fxmlLoader.load();
        final Atlas.DigitalClock hora = new Atlas.DigitalClock();
        final GridPane dato = (GridPane) root.lookup("#pane_arriba");
        final Pane panel_hora = (Pane) dato.getChildren().get(2);
        panel_hora.getChildren().set(0, hora);
        setVista(primaryStage);
        //Atlas.vista = primaryStage;
        final Group root1 = new Group();
        root1.getChildren().addAll(root);
        scene = new Scene(root1);
        vista.initStyle(StageStyle.DECORATED);
        vista.setTitle(AtlasConstantes.VERSION_ATLAS);
        vista.setWidth(757);
        vista.setHeight(645);
        vista.setMaxWidth(757);
        vista.setMaxHeight(645);
        final Image image = new Image(getClass().getResourceAsStream("/com/co/allus/recursos/allusico.PNG"));
        vista.getIcons().add(image);
        vista.setScene(scene);
        vista.centerOnScreen();
        //  vista.setResizable(false);
        Platform.setImplicitExit(false);
        createTrayIcon(vista);
        if (vista.isShowing()) {
            hide(vista);
        }

        vista.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Stage stage = ModalMensajes.getStage();
                        if (stage != null) {
                            if (stage.isShowing()) {
                                stage.close();
                            }
                        }
                        new ModalMensajes("¿Está seguro que desea salir de la aplicación?", "Exit", ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.MODAL_SALIRAPP);
                    }
                });
            }
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public void gotoPrincipal() throws IOException {

        limpiarVariablesAtlas();
        final URL location = getClass().getResource("/com/co/allus/vistas/MarcoPrincipal.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        root = (Parent) fxmlLoader.load(location.openStream());
        /**
         * performance
         */
//        fxmlLoader = new FXMLLoader(marcoprincipalURL);
//        root = (Parent) fxmlLoader.load();
        final Atlas.DigitalClock hora = new Atlas.DigitalClock();
        final GridPane dato = (GridPane) root.lookup("#pane_arriba");
        final Pane panel_hora = (Pane) dato.getChildren().get(2);
        panel_hora.getChildren().set(0, hora);
        final Group root1 = new Group();
        root1.getChildren().addAll(root);
        scene = new Scene(root1);
        vista.setTitle(AtlasConstantes.VERSION_ATLAS);
        vista.setWidth(757);
        vista.setHeight(645);
        vista.setMaxWidth(757);
        vista.setMaxHeight(645);
        final Image image = new Image(getClass().getResourceAsStream("/com/co/allus/recursos/allusico.PNG"));
        vista.getIcons().add(image);
        vista.setScene(scene);
        vista.centerOnScreen();
        // vista.setResizable(false);
        if (vista.isShowing()) {
            hide(vista);
        }        
        MascaraTransaccionalController controller=new MascaraTransaccionalController("%4448%");
        controller.MinimizarKbrowserMascara();
    }
    /**
     * en construccion , se deben limpiar todas las variables que usa atlas.
     */
    private void limpiarVariablesAtlas() {
        try {
            /**
             * DATOS DE SEGURIDAD
             */
            final ModelEvidente modelEvidente = new ModelEvidente();
            modelEvidente.setPregAndResp("");
            modelEvidente.setRespSelect(new HashMap<String, String>());
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }

        try {
            SaldoAFCController.cancelOP.set(true);
            SaldoAFCfinController.cancelOP.set(true);
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }

        try {
            MarcoPrincipalController.newConsultaPrestamo = true;
            MarcoPrincipalController.newCuentasACHInscritas = true;
            MarcoPrincipalController.newCuentasInscritas = true;
            MarcoPrincipalController.newConsultaBloqTDc = true;
            MarcoPrincipalController.newConsultaTDc = true;
            MarcoPrincipalController.newTokenEmpresas = true;
            final DatosPagoPrestamo datosCero = new DatosPagoPrestamo();
            DatosPagoPrestamo.setDatosTablaDetalle(datosCero);
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }

        try {
            PuntosColListarTarjetasController.registros.clear();
            PuntosColListarMovimientosController.registros.clear();
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }

        try {
            GirosNalInfoGeneralController.registros.clear();
            GirosNalInfoGeneralController.numpagina.set(0);
            GirosNalInfoGeneralController.indicadorRegistros = "N";
            GirosNalCancelacionController.numpagina.set(0);
            GirosNalCancelacionController.indicadorRegistros = "N";
            GirosNalCancelacionController.registros.clear();
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }

        try {
            PagosATercerosInitController.pagos.clear();
            PagosATercerosInitController.indicadorRegistros = "N";
            PagosATerceroMasFactController.registros.clear();
            PagosATerceroMasFactController.indicadorRegistros = "N";
            MarcoPrincipalController.newConsultaPagosT = true;
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }

        try {
            ConsultaAlertasEnvInitController.alertasEnv.clear();
            ConsultaAlertasEnvInitController.numpagina.set(0);
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }

        try {
            ParametrizacionAlertasConsController.parametrizacion.clear();
            ParametrizacionAlertasConsController.parametrizacion2.clear();
            ParametrizacionAlertasConsController.numpagina.set(0);
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }
 
        try {
            ParametrizacionAlertasController.parametrizacion.clear();
            ParametrizacionAlertasController.parametrizacion2.clear();
            ParametrizacionAlertasController.numpagina.set(0);
        } catch (Exception e) {
            docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();
                    showProgramIsMinimizedMsg();
                } else {
                    docs.imprimir("System tray no esta soportado --- " + docs.obtenerHoraActual());
                }
            }
        });
    }

    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            final SystemTray tray = SystemTray.getSystemTray();

            java.awt.Image image = null;
            image = new ImageIcon(getClass().getResource("/com/co/allus/recursos/allusicotray.png")).getImage();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(final WindowEvent e) {
                    e.consume(); // se consume el evento por defecto            
                }
            });
            // crea el menu pop up
            final PopupMenu popup = new PopupMenu();

            trayIcon = new TrayIcon(image, AtlasConstantes.VERSION_ATLAS, popup);
            MenuItem exitItem = new MenuItem("Salir");
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Stage stage = ModalMensajes.getStage();
                            if (stage != null) {
                                if (stage.isShowing()) {
                                    stage.close();
                                }
                            }
                            new ModalMensajes("¿Está seguro que desea salir de la aplicación?", "Exit", ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.MODAL_SALIRAPP);
                        }
                    });
                }
            });

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                final PrintStream print = new PrintStream(out);
                e.printStackTrace(print);
                print.flush();
                final String Salida = new String(out.toByteArray());
                docs.imprimir("Error en la aplicacion -->" + Salida + "  :" + docs.obtenerHoraActual());
            }
        }
    }

    private void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage(AtlasConstantes.VERSION_ATLAS, "", TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    public class DigitalClock extends Label {

        public DigitalClock() {
            super();
            bindToTime();
        }

        // the digital clock updates once a second.
        private void bindToTime() {

            final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent actionEvent) {
                    Calendar time = Calendar.getInstance();
                    String fecha = formato.format(time.getTime());
                    String hourString = StringUtilities.pad(2, ' ', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
                    String minuteString = StringUtilities.pad(2, '0', time.get(Calendar.MINUTE) + "");
                    String secondString = StringUtilities.pad(2, '0', time.get(Calendar.SECOND) + "");
                    String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
                    setText(fecha + " | " + hourString + ":" + minuteString + ":" + secondString + " " + ampmString);
                }
            }),
            new KeyFrame(Duration.seconds(1)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }
}
