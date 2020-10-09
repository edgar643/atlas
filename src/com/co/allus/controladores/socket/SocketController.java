/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladores.socket;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladoresfxml.BloqueosTDCController;
import com.co.allus.controladoresfxml.ConsultaMovimientosController;
import com.co.allus.controladoresfxml.ConsultaSaldoTDCController;
import com.co.allus.controladoresfxml.MarcoPrincipalController;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.AtlasAcceso;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.MenuModel;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.ReglasNegocioODA;
import com.co.allus.utils.StringUtilities;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author alexander.lopez.o
 */
public class SocketController {

    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
    private static GestorDocumental gestorDoc = new GestorDocumental();
    public static AtomicBoolean isAutenticadoTDCprep = new AtomicBoolean(false);

    /**
     *
     * @param Servicio
     * @param Trama
     * @return
     */
    public String ProcesarServicio(final String Servicio, final String Trama, final int Origen) {

        final StringBuilder retorno = new StringBuilder();

        if ("11".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consultas Saldo" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA-SALDOS", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane menu_saldos = Menu.getPanes().get(0);
                        menu_saldos.setExpanded(true);
                        final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                        ObservableList<TreeItem<String>> children = arbol_saldos.getRoot().getChildren();
                        for (Iterator<TreeItem<String>> it = children.iterator(); it.hasNext();) {
                            TreeItem<String> opciones = it.next();
                            if (opciones.getValue().equalsIgnoreCase("Cuenta Ahorros")) {
                                arbol_saldos.getSelectionModel().select(opciones);
                            }
                        }
//                    final ConsultaSaldoTDCController controller = new ConsultaSaldoTDCController();
//                    controller.mostrarSaldosTDC("", true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("38".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Pagos a terceros" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "PAGOS-TERCEROS", Origen);
        } else if ("48".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  transferencias ctas propias" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "TRANSFERENCIAS-CTAS PROPIAS", Origen);
        } else if ("39".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Pagos TDC" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "PAGOS-TDC", Origen);
        } else if ("40".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Blqueos" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "BLOQUEOS", Origen);
        } else if ("9".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Desbloqueo segunda clave por Seg" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "DESBLOQUEO SEGUNDA CLAVE", Origen);
        }//activacion ahorro a la mano
        else if ("13".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  activacion ahorro a la mano" + "##" + obtenerHoraActual());
            retorno.append(actvAhorroAlaMano(Trama));

        } else if ("565".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Blqueo TDC" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "BLOQUEOS TDC", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane menu_blqueos = Menu.getPanes().get(3);
                        menu_blqueos.setExpanded(true);
                        final TreeView<String> arbolbloqueos = (TreeView<String>) pane.lookup("#arbolbloqueos");
                        ObservableList<TreeItem<String>> children = arbolbloqueos.getRoot().getChildren();
                        for (Iterator<TreeItem<String>> it = children.iterator(); it.hasNext();) {
                            TreeItem<String> opciones = it.next();
                            if (opciones.getValue().equalsIgnoreCase(AtlasConstantes.ITEM_BLOQUEOS_TDC)) {
                                arbolbloqueos.getSelectionModel().select(opciones);
                            }
                        }
                        final BloqueosTDCController controller = new BloqueosTDCController();
                        controller.mostrarBloqueosTDC("");
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });

        } else if ("566".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Bloqueo claves y tarjetas" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "BLOQUEOS CLAVES Y TARJETAS ", Origen);
        } else if ("567".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Bloqueo clave ppal" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "BLOQUEOS CLAVE PPAL", Origen);
        } else if ("92".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  informacion TDC" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "INFORMACIÓN TDC", Origen);

            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane menu_saldos = Menu.getPanes().get(0);
                        menu_saldos.setExpanded(true);
                        final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                        ObservableList<TreeItem<String>> children = arbol_saldos.getRoot().getChildren();
                        for (Iterator<TreeItem<String>> it = children.iterator(); it.hasNext();) {
                            TreeItem<String> opciones = it.next();
                            if (opciones.getValue().equalsIgnoreCase(AtlasConstantes.MENU_TARJETA_CREDITO)) {
                                arbol_saldos.getSelectionModel().select(opciones);
                            }
                        }
                        final ConsultaSaldoTDCController controller = new ConsultaSaldoTDCController();
                        controller.mostrarSaldosTDC("", true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });

        } else if ("93".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Declinaciones TDC" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "DECLINACIONES TDC", Origen);
        } else if ("94".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Avances TDC-SVP" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "AVANCES TDC-SVP", Origen);
        } else if ("715".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Barrido de Cuentas" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "BARRIDO DE CUENTAS", Origen);
//        } else if ("7143".equals(Servicio.trim())) {
//            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Cambio Tipo de Mecanismo" + "##" + obtenerHoraActual());
//            mostrarMenu(Trama, "CAMBIO DE TIPO DE MECANISMO", Origen);
        } else if ("7141".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Actualizacion datos seguridad" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "ACTUALIZACIÓN DATOS DE SEGURIDAD", Origen);
        } else if ("7144".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta de novedades" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA DE NOVEDADES", Origen);
        } else if ("7142".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Inscripcion clave dinamica" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "INSCRIPCIÓN AL SERVICIO DE CLAVE DINÁMICA", Origen);
        } else if ("24".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta Movimientos" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA MOVIMIENTOS", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // System.out.println("entra a mostrar menu credito tarjetas");
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                    final TitledPane menu_movimientos = Menu.getPanes().get(7);
                    menu_movimientos.setExpanded(true);
                    final TreeView<String> arbol_movimientos = (TreeView<String>) pane.lookup("#arbol_movimientos");
                    ObservableList<TreeItem<String>> children = arbol_movimientos.getRoot().getChildren();
                    final HashMap<String, ArrayList<String>> productos = Cliente.getCliente().getProductos();
                    Set<String> keySet = productos.keySet();
                    boolean ahorros = false;
                    boolean corriente = false;
                    for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                        final String tipocuenta = val.next();

                        /* validacion cuentas de deposito*/
                        if (CodigoProductos.CUENTA_AHORROS.equals(tipocuenta)) {
                            ahorros = true;
                        }
                        if (CodigoProductos.CUENTA_CORRIENTE.equals(tipocuenta)) {
                            corriente = true;
                        }
                    }

                    if (ahorros && !corriente) {
                        for (Iterator<TreeItem<String>> it = children.iterator(); it.hasNext();) {
                            TreeItem<String> opciones = it.next();
                            if (opciones.getValue().equalsIgnoreCase(AtlasConstantes.ITEM_MOV_AHORROS)) {
                                arbol_movimientos.getSelectionModel().select(opciones);
                            }
                            final ConsultaMovimientosController controller = new ConsultaMovimientosController();
                            controller.mostrarConsultaMovimientos(CodigoProductos.CUENTA_AHORROS);
                        }
                    } else if (corriente && !ahorros) {
                        for (Iterator<TreeItem<String>> it = children.iterator(); it.hasNext();) {
                            TreeItem<String> opciones = it.next();
                            if (opciones.getValue().equalsIgnoreCase(AtlasConstantes.ITEM_MOV_CORRIENTE)) {
                                arbol_movimientos.getSelectionModel().select(opciones);
                            }
                            final ConsultaMovimientosController controller = new ConsultaMovimientosController();
                            controller.mostrarConsultaMovimientos(CodigoProductos.CUENTA_CORRIENTE);
                        }
                    }
                }
            });

        } else if ("46".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Transferencia Crediagil" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "TRANSFERENCIA CREDIAGIL", Origen);
        } else if ("7144".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta Referencia CDT" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "REFERENCIA CDT", Origen);
        } else if ("310".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Pago Prestamo" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "PAGO PRESTAMOS", Origen);
        } else if ("312".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Recaudos MQ" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "RECAUDOS", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane menu_merchaportal = Menu.getPanes().get(8);
                        menu_merchaportal.setExpanded(true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("006".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio Customer Service" + "##" + obtenerHoraActual());
            retorno.append(CustomerServices(Trama));
        } else if ("036".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Cancelacion ahorro a la mano" + "##" + obtenerHoraActual());
            retorno.append(CancelacionAhorroAlaMano(Trama));
        } else if ("062".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Reexpedicion ahorro a la mano" + "##" + obtenerHoraActual());
            retorno.append(ReexpedicionAhorroAlaMano(Trama));
        } else if ("7145".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta Referencia CDT" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "REFERENCIA CDT", Origen);
        } else if ("42".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta estado cuentas inscritas" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "ESTADO CUENTAS INSCRITAS", Origen);
        } else if ("4613".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta eliminacion cuentas inscritas otros bancos" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "ELIMINACION CUENTAS INSCRITAS OTROS BANCO", Origen);
        } else if ("571".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta eliminacion cuentas inscritas otros bancos" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONTRAORDENES CHEQUES", Origen);
        } else if ("49".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta desembolso crediagil" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "DESEMBOLSO CREDIAGIL", Origen);
        } else if ("611".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta movimiento TDC Dia" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "MOVIMIENTO TDC DIA", Origen);
        } else if ("612".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Consulta TRM" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA TRM", Origen);
        } else if ("568".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio BLOQUEO TDC POR FRAUDE" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "BLOQUEO TDC por  FRAUDE", Origen);
        } else if ("857".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio DESBLOQUEO ALM" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "DESBLOQUEO ALM", Origen);
        } else if ("865".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio INFORMACION GENERAL TOKEN" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "INFORMACION GENERAL TOKEN", Origen);
        } else if ("866".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio DISTRIBUCION TOKEN" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "DISTRIBUCION TOKEN", Origen);
        } else if ("867".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio TOKEN DISPONIBLES" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "TOKEN DISPONIBLES", Origen);
        } else if ("868".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio ACCESOS DE EMERGENCIA" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "ACCESOS DE EMERGENCIA", Origen);
        } else if ("815".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio ESTADO CLAVE" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "ESTADO CLAVE", Origen);
        } else if ("311".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio CONSULTA HISTORICOS" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA MOVIMIENTOS DE PAGOS DEL CLIENTE PAGADOR", Origen);
        } else if ("25".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio MOVIMIENTOS DEBITO AUTOMATICO" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "HISTÓRICO DEBITOS EXITOSOS Y RECHAZADOS", Origen);
        } else if ("FENIX".equals(Servicio.trim())) {  // CAMBIAR A LA REAL
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio FENIX" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "", Origen);

            //PayPro
//            mostrarMenu(Trama, "MERCHAN PORTAL", Origen);
//            try {
//                Thread.currentThread().sleep(300);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        // System.out.println("entra a mostrar menu credito tarjetas");
//                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
//                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
//                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
//                        final TitledPane menu_merchaportal = Menu.getPanes().get(0);
//                        menu_merchaportal.setExpanded(true);
//                    } catch (Exception e) {
//                        //CONTROL ERROR NO AUTOMATIZABLE
//                    }
//
//                }
//            });
        } else if ("613".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta crediagil" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA CREDIAGIL", Origen);
        } else if ("615".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta bolsillos" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CUENTAS DE BOLSILLOS", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane menu_bolsillos = Menu.getPanes().get(13);
                        menu_bolsillos.setExpanded(true);
                        final TreeView<String> arbolBolsillos = (TreeView<String>) pane.lookup("#arbolBolsillos");
                        ObservableList<TreeItem<String>> children = arbolBolsillos.getRoot().getChildren();
                        for (Iterator<TreeItem<String>> it = children.iterator(); it.hasNext();) {
                            TreeItem<String> opciones = it.next();
                            if (opciones.getValue().equalsIgnoreCase(AtlasConstantes.ITEM_BOLSILLOS)) {
                                arbolBolsillos.getSelectionModel().select(opciones);
                            }
                        }
//                        final BolsillosCuentasController controller = new BolsillosCuentasController();
//                        System.out.println("entro por auto bolsillos");
//                        controller.mostrarCuentasBolsillo("", true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("61".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta CDT" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA CDT", Origen);
        } else if ("1011".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta PCO" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA SALDO PUNTOS", Origen);
        } else if ("1013".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta PCO" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA MOVIMIENTOS PUNTOS", Origen);
        } else if ("1012".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta PCO" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA  PUNTOS MIGRADOS", Origen);
        } else if ("1014".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta GIROS NACIONALES" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "GIROS NACIONALES", Origen);
        } else if ("1015".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta PCO" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "BONOS Y AJUSTE PUNTOS", Origen);
        } else if ("7145".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  desbloqueo clave dinamica" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "DESBLOQUEO CLAVE DINAMICA POR SEGURIDAD", Origen);
        } else if ("857".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  desbloqueo clave dinamica" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "DESBLOQUEO ALM", Origen);
        } else if ("1100".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  Merchan Portal" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "ESTABLECIMIENTOS", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane menu_merchaportal = Menu.getPanes().get(13);
                        menu_merchaportal.setExpanded(true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("887".equals(Servicio.trim()) || "1022".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  desbloqueo clave dinamica" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA ALERTAS ENVIADAS", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane consultas = Menu.getPanes().get(6);
                        consultas.setExpanded(true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("1016".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  parametrizacion transaccion" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "PARAMETRIZACION TRANSACCIONES", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane consultas = Menu.getPanes().get(6);
                        consultas.setExpanded(true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("1017".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta transaccion" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA TRANSACCIONES", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane consultas = Menu.getPanes().get(6);
                        consultas.setExpanded(true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }

                }
            });
        } else if ("1018".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  registro/actualizacion servicio " + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "REGISTRO/ACTUALIZACION SERVICIO", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane consultas = Menu.getPanes().get(6);
                        consultas.setExpanded(true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("1019".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  parametrizacion servicio" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "PARAMETRIZACION SERVICIO", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane consultas = Menu.getPanes().get(6);
                        consultas.setExpanded(true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("1020".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta parametrizacion" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA PARAMETRIZACION", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane consultas = Menu.getPanes().get(6);
                        consultas.setExpanded(true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("1021".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  inactivar servicio alertas" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "INACTIVAR SERVICIO", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // System.out.println("entra a mostrar menu credito tarjetas");
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane consultas = Menu.getPanes().get(6);
                        consultas.setExpanded(true);
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else if ("1023".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  consulta log vinculacion" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "CONSULTA LOG VINCULACION", Origen);
        }else if ("289".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  INTEGRACION CON MASCARA GESTIÓN DÍA DÍA" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "GESTION DIA A DIA", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane menu_SERVAD = Menu.getPanes().get(4);
                        menu_SERVAD.setExpanded(true);
                        final TreeView<String> arbolservAd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                        ObservableList<TreeItem<String>> children = arbolservAd.getRoot().getChildren();
                        for (Iterator<TreeItem<String>> it = children.iterator(); it.hasNext();) {
                            TreeItem<String> opciones = it.next();
                            if (opciones.getValue().equalsIgnoreCase(AtlasConstantes.ITEM_MOV_LIFE_COACH)) {
                                arbolservAd.getSelectionModel().select(opciones);
                            }
                        }

                        try {
                            Thread.currentThread().sleep(2000);
                            arbolservAd.getSelectionModel().clearSelection();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        }else if ("290".equals(Servicio.trim())) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio  INTEGRACION CON MASCARA RECARGAS" + "##" + obtenerHoraActual());
            mostrarMenu(Trama, "RECARGAS", Origen);
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {

                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                        final Accordion Menu = (Accordion) frameGnral.lookup("#menu");
                        final TitledPane menu_SERVAD = Menu.getPanes().get(4);
                        menu_SERVAD.setExpanded(true);
                        final TreeView<String> arbolservAd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                        ObservableList<TreeItem<String>> children = arbolservAd.getRoot().getChildren();
                        for (Iterator<TreeItem<String>> it = children.iterator(); it.hasNext();) {
                            TreeItem<String> opciones = it.next();
                            if (opciones.getValue().equalsIgnoreCase(AtlasConstantes.ITEM_MOV_RECARGAS)) {
                                arbolservAd.getSelectionModel().select(opciones);
                            }
                        }

                        try {
                            Thread.currentThread().sleep(2000);
                            arbolservAd.getSelectionModel().clearSelection();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                }
            });
        } else {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio : " + Servicio + "##" + obtenerHoraActual());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new ModalMensajes("No se encontro pantalla de transaccion \n para el servicio. Verifique la Trama", "Advertencia", ModalMensajes.MENSAJE_ADVERTENCIA, ModalMensajes.DEFAULT);
                    //return;
                }
            });
        }
        return retorno.toString();
    }

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
//       /**
//     * version OTP
//     * @param trama
//     * @return
//     */
//    private Cliente populateDatos(final String trama) {
//
//        final String[] datos = trama.split("%");
//        final Cliente cliente = new Cliente();
//        ArrayList<String> cuentas;
//        final HashMap<String, ArrayList<String>> productos = new HashMap<String, ArrayList<String>>();
//        cliente.setNombre(datos[8]);
//        cliente.setAni(datos[7]);
//        cliente.setId_cliente(datos[21]);
//        cliente.setRefid(datos[25]);
//        cliente.setClavesn(datos[20]);          
//        cliente.setContraseña(datos[26]);
//        //OTP
//        cliente.setEstado_opt(datos[1].trim());
//        cliente.setTipo_otp(datos[2].trim());
//        cliente.setTipo_oda(datos[3].trim());
//        cliente.setOtp_antitramites(datos[4].trim());
//        cliente.setOtp_servicio(datos[5].trim());
//        cliente.setTiene_otp(datos[6].trim());
//
//        final Set<String> keySet = CodigoProductos.codigosProd.keySet();
//
//        for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
//            cuentas = new ArrayList<String>();
//            final String codigo = val.next();
//            for (int j = 27; j < datos.length; j++) {
//                if (codigo.equals(datos[j])) {
//                    cuentas.add(datos[j + 1]);
//                    productos.put(CodigoProductos.codigosProd.get(datos[j]), cuentas);
//                }
//            }
//        }
//
//        cliente.setProductos(productos);
//        Cliente.setCliente(cliente);
//        return cliente;
//    }

    /**
     * version OTP - l00 all
     *
     * @param trama
     * @return
     */
    private Cliente populateDatos(final String trama) {
        final ReglasNegocioODA reglasOTP = new ReglasNegocioODA();
        final String[] datos = trama.split("%");
        final Cliente cliente = new Cliente();

        HashMap<String, ArrayList<String>> productos = new HashMap();
//        cliente.setNombre(datos[28]);
//        cliente.setAni(datos[20]);
//        cliente.setId_cliente(datos[26]);
//        cliente.setRefid(datos[22]);
//        cliente.setClavesn(datos[35]);
//        cliente.setTipo_cliente(datos[13]);
//        cliente.setDescripcion(datos[14]);
//        cliente.setTipo_clinete_gtel(datos[15]); //5M,H... Actual
//        cliente.setRegla_negocio(datos[39]);
//        cliente.setDnis(datos[21]);
//        cliente.setIndustria(datos[23]);
//        cliente.setInstitucion(datos[24]);
//        cliente.setCod_apli(datos[25]);
//        cliente.setContraseña(datos[7]);
        cliente.setNombre(datos[30]);
        cliente.setAni(datos[22]);
        cliente.setId_cliente(datos[28]);
        cliente.setRefid(datos[24]);
        cliente.setClavesn(datos[37]);
        cliente.setTipo_cliente(datos[15]);
        cliente.setDescripcion(datos[16]);
        cliente.setTipo_clinete_gtel(datos[17]); //5M,H... Actual
        cliente.setRegla_negocio(datos[41]);
        cliente.setDnis(datos[23]);
        cliente.setIndustria(datos[25]);
        cliente.setInstitucion(datos[26]);
        cliente.setCod_apli(datos[27]);
        cliente.setContraseña(datos[9]);
        cliente.setTipide(datos[10]);
        cliente.setReservado1(datos[38]);
        cliente.setEstado_opt(datos[1].trim()); // Estado OTP // Codigos de 5 digitos
//        cliente.setOpc_final_de_menu(datos[38]);
        cliente.setOpc_final_de_menu(datos[40]);
//        ArrayList<String> OTP = (ArrayList) reglasOTP.getCodigosProd().get(datos[15]);        
        ArrayList<String> OTP = (ArrayList) reglasOTP.getCodigosProd().get(datos[17]);
        if (OTP == null) {
            cliente.setTipo_oda(datos[3].trim());
        } else {
            cliente.setTipo_oda(((String) OTP.get(0)).equalsIgnoreCase(AtlasConstantes.OTP_SOFTOKEN) ? "" : (String) OTP.get(0));
        }
        cliente.setTipo_otp(datos[2].trim());
        cliente.setOtp_antitramites(datos[4].trim()); //N o S
        cliente.setOtp_servicio(datos[5].trim()); // Apagado Easy //N o S
        cliente.setTiene_otp(datos[6].trim());
        /**/
        cliente.setbDMigracion(datos[7].trim()); // Migracion //N o S
        cliente.setControlDual(datos[8].trim()); // Control Dual //N o S
        // BD Migracion [7]
        // Control Dual[8]
        /**/
        Set<String> keySet = CodigoProductos.codigosProd.keySet();
        for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
            ArrayList<String> cuentas = new ArrayList();
            String codigo = (String) val.next();
            //for (int j = 42; j < datos.length - 1; j++) {
            for (int j = 44; j < datos.length - 1; j++) {
                if (codigo.equals(datos[j])) {
                    cuentas.add(datos[(j + 1)]);
                    productos.put(CodigoProductos.codigosProd.get(datos[j]), cuentas);
                }
            }
        }
        cliente.setProductos(productos);
        Cliente.setCliente(cliente);
        return cliente;
    }

    /**
     *
     * @version FENIX 100 COMPLETA CON ATLAS
     * @return
     */
    private Cliente populateDatosFenix(final String trama) {
        final String[] datos = trama.split("%");
        final Cliente cliente = new Cliente();
        HashMap<String, ArrayList<String>> productos = new HashMap();
        /**
         * datos TDC PREPAGO
         */
        cliente.setTipoClientePrepago(datos[2]);
        cliente.setNombre1(datos[3]);
        cliente.setNombre2(datos[4]);
        cliente.setApellido2(datos[5]);
        cliente.setFechaExpedicion(datos[6]);
        cliente.setLugarExpedicion(datos[7]);
        cliente.setNitEmpresa(StringUtilities.eliminarCerosLeft(datos[8]));
        cliente.setNombreEmpresa(datos[9]);
        cliente.setNomRealce1(datos[10]);
        cliente.setNomRealce2(datos[11]);
        cliente.setTipoTarjeta(datos[12]);
        cliente.setApellido1(datos[13]);
        /**
         *
         */
        cliente.setContraseña(datos[14]);
        cliente.setTipoidCliente(datos[15]);
        cliente.setChaTelasociado(datos[16]);
        cliente.setChaFecharegAF(datos[17]);
        cliente.setChaEstadoAF(datos[18]);
        cliente.setChattxtUserId(datos[19]);
        cliente.setTipo_cliente(datos[20]);
        cliente.setDescOpcFinalMenu(datos[21]);
        cliente.setTipo_clinete_gtel(datos[22]); //5M,H... Actual
        cliente.setRegla_negocio(datos[46]);
        cliente.setExtension(datos[24]);
        cliente.setCti_origen(datos[25]);
        cliente.setCodigo_origen(datos[26]);
        cliente.setAni(datos[27]);
        cliente.setDnis(datos[28]);
        cliente.setRefid(datos[29]);
        cliente.setIndustria(datos[30]);
        cliente.setInstitucion(datos[31]);
        cliente.setCod_apli(datos[32]);
        cliente.setId_cliente(datos[33]);
        cliente.setTipide(datos[34]);
        cliente.setNombre(datos[35]);
        cliente.setDireccion(datos[36].isEmpty() ? "SIN DIRECCION" : datos[36].substring(0, datos[24].length() - 3));
        cliente.setTelefono(datos[37]);
        cliente.setFax(datos[38]);
        cliente.setVv_Fechna(datos[39]);
        cliente.setVv_Fechv(datos[40]);
        cliente.setVv_Fechac(datos[41]);
        cliente.setClavesn(datos[42]);
        cliente.setReservado1(datos[43]);
        cliente.setReservado2(datos[44]);
        cliente.setOpc_final_de_menu(datos[45]);
        cliente.setRegla_negocio2(datos[46]);
        cliente.setComprobante(datos[47]);
        cliente.setNum_productos(datos[48]);
        /**/
        Set<String> keySet = CodigoProductos.codigosProd.keySet();
        for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
            ArrayList<String> cuentas = new ArrayList();
            String codigo = (String) val.next();
            //for (int j = 42; j < datos.length - 1; j++) {
            for (int j = 49; j < datos.length - 1; j++) {
                if (codigo.equals(datos[j])) {
                    cuentas.add(datos[(j + 1)]);
                    productos.put(CodigoProductos.codigosProd.get(datos[j]), cuentas);
                }
            }
        }
        cliente.setProductos(productos);
        Cliente.setCliente(cliente);
        return cliente;
    }

    /**
     * codiciacion automatica
     *
     * @param trama
     * @return
     */
    private Cliente populateDatosCodAuto(final String trama) {
        final ReglasNegocioODA reglasOTP = new ReglasNegocioODA();
        final String[] datos = trama.split("%");
        final Cliente cliente = new Cliente();

        HashMap<String, ArrayList<String>> productos = new HashMap();
        cliente.setCodemp(datos[1]);
        cliente.setContraseña(datos[10]);
        cliente.setTipide(datos[11]);
        cliente.setTipo_cliente(datos[16]);
        cliente.setDescripcion(datos[17]);
        cliente.setTipo_clinete_gtel(datos[18]); //5M,H... Actual
        cliente.setAni(datos[23]);
        cliente.setDnis(datos[24]);
        cliente.setRefid(datos[25]);
        cliente.setIndustria(datos[26]);
        cliente.setInstitucion(datos[27]);
        cliente.setCod_apli(datos[28]);
        cliente.setId_cliente(datos[29]);
        cliente.setVv_Tipide(this.parseTipoDocumento(datos[30]));
        cliente.setNombre(datos[31]);
        cliente.setClavesn(datos[38]);
        cliente.setReservado1(datos[39]);
        cliente.setOpc_final_de_menu(datos[41]);
        cliente.setRegla_negocio(datos[42]);
        cliente.setEstado_opt(datos[2].trim()); // Estado OTP // Codigos de 5 digitos
        cliente.setTokenOauth(datos[19].length() <= 2 ? "" : datos[19]);
//        cliente.setOpc_final_de_menu(datos[38]);

//        ArrayList<String> OTP = (ArrayList) reglasOTP.getCodigosProd().get(datos[15]);        
        ArrayList<String> OTP = (ArrayList) reglasOTP.getCodigosProd().get(datos[18]);
        if (OTP == null) {
            cliente.setTipo_oda(datos[4].trim());
        } else {
            cliente.setTipo_oda(((String) OTP.get(0)).equalsIgnoreCase(AtlasConstantes.OTP_SOFTOKEN) ? "" : (String) OTP.get(0));
        }

        cliente.setTipo_otp(datos[3].trim());
        cliente.setOtp_antitramites(datos[5].trim()); //N o S
        cliente.setOtp_servicio(datos[6].trim()); // Apagado Easy //N o S
        cliente.setTiene_otp(datos[7].trim());
        /**/
        cliente.setbDMigracion(datos[8].trim()); // Migracion //N o S
        cliente.setControlDual(datos[9].trim()); // Control Dual //N o S
        // BD Migracion [7]
        // Control Dual[8]
        /**/
        Set<String> keySet = CodigoProductos.codigosProd.keySet();
        for (Iterator<String> val = keySet.iterator(); val.hasNext();) {
            ArrayList<String> cuentas = new ArrayList();
            String codigo = (String) val.next();
            //for (int j = 42; j < datos.length - 1; j++) {
            for (int j = 45; j < datos.length - 1; j++) {
                if (codigo.equals(datos[j])) {
                    cuentas.add(datos[(j + 1)]);
                    productos.put(CodigoProductos.codigosProd.get(datos[j]), cuentas);
                }
            }
        }
        cliente.setProductos(productos);
        Cliente.setCliente(cliente);
        return cliente;
    }

    /**
     *
     * @param datos
     * @return
     */
    private String actvAhorroAlaMano(final String datos) {
        final StringBuilder trama = new StringBuilder();
        String Respuesta = new String();
        final Date fecha = new Date();
        trama.append("900,002,");
        trama.append(datos.split("%")[2]);
        trama.append(",");
        trama.append(AtlasConstantes.COD_AHORRO_MANO_MQ);
        trama.append(",");
        trama.append(datos.split("%")[3]);
        trama.append(",");
        trama.append(formatoFecha.format(fecha));
        trama.append(",");
        trama.append(formatoHora.format(fecha));
        trama.append(",");
        trama.append(datos.split("%")[4]);
        trama.append(",");
        trama.append(datos.split("%")[5]);
        trama.append(",");
        trama.append(datos.split("%")[6]);
        trama.append(",");
        try {
            trama.append(datos.split("%")[7]);
            trama.append(",~");
        } catch (Exception e) {
            trama.append("~");
        }

        final ConectSS servicioSS = new ConectSS();
        try {
            // gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ ACTIVACION AHORRO A LA MANO = " + trama.toString() + "##" + obtenerHoraActual());
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ ACTIVACION AHORRO A LA MANO  " + obtenerHoraActual());
            //900,002,000,0000091982,TRANSACCION EXITOSA                                                   ,~
            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, trama.toString());
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + obtenerHoraActual());
        } catch (Exception ex) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ  ACTIVACION AHORRO A LA MANO = " + ex.toString() + "##" + obtenerHoraActual());
            //envio a contingencia
            try {
                //gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ ACTIVACION AHORRO A LA MANO = " + trama.toString() + "##" + obtenerHoraActual());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ ACTIVACION AHORRO A LA MANO = " + obtenerHoraActual());
                //900,002,000,0000091982,TRANSACCION EXITOSA  
                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, trama.toString());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ ACTIVACION AHORRO A LA MANO = " + Respuesta + "##" + obtenerHoraActual());
            } catch (Exception ex1) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctg MQ ACTIVACION AHORRO A LA MANO = " + ex1.toString() + "##" + obtenerHoraActual());
                Respuesta = "900,002,001,998," + ex1.toString() + ",~";
            }
        }
        //gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ ACTIVACION AHORRO A LA MANO Gestiontel = "+Respuesta + "##" + obtenerHoraActual());
        return Respuesta;
    }

    /**
     *
     * @param trama
     * @return
     */
    private String CancelacionAhorroAlaMano(final String trama) {
        String Respuesta = new String();
        final ConectSS servicioSS = new ConectSS();
        try {
            // gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CANCELACION AHORRO A LA MANO = " + trama.toString() + "##" + obtenerHoraActual());
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ CANCELACION AHORRO A LA MANO  " + obtenerHoraActual());
            //850,036,000,122-   22 CLÁVE INVÁLIDA,~
            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, trama.toString());
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + obtenerHoraActual());
        } catch (Exception ex) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ  CANCELACION AHORRO A LA MANO = " + ex.toString() + "##" + obtenerHoraActual());
            //envio a contingencia
            try {
                // gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ CANCELACION AHORRO A LA MANO = " + trama.toString() + "##" + obtenerHoraActual());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ CANCELACION AHORRO A LA MANO  " + obtenerHoraActual());
                //850,036,000,122-   22 CLÁVE INVÁLIDA,~
                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, trama.toString());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ CANCELACION AHORRO A LA MANO = " + Respuesta + "##" + obtenerHoraActual());

            } catch (Exception ex1) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctg MQ CANCELACION AHORRO A LA MANO = " + ex1.toString() + "##" + obtenerHoraActual());
                Respuesta = "850,036,999,Error Conectándose al Servidor,~";
            }
        }
        return Respuesta;
    }

    /**
     *
     * @param trama
     * @return
     */
    private String ReexpedicionAhorroAlaMano(final String trama) {

        String Respuesta = new String();
        final ConectSS servicioSS = new ConectSS();
        try {
            //gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ REEXPEDICION AHORRO A LA MANO = " + trama.toString() + "##" + obtenerHoraActual());
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ REEXPEDICION AHORRO A LA MANO  " + obtenerHoraActual());
            //850,062,000,TRANSACCION EXITOSA,~ 
            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, trama.toString());
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + obtenerHoraActual());
        } catch (Exception ex) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ  REEXPEDICION AHORRO A LA MANO = " + ex.toString() + "##" + obtenerHoraActual());
            //envio a contingencia
            try {
                // gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ REEXPEDICION AHORRO A LA MANO = " + trama.toString() + "##" + obtenerHoraActual());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ REEXPEDICION AHORRO A LA MANO  " + obtenerHoraActual());
                //850,062,000,TRANSACCION EXITOSA,~ 
                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU5, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU5, trama.toString());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ REEXPEDICION AHORRO A LA MANO = " + Respuesta + "##" + obtenerHoraActual());

            } catch (Exception ex1) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctg MQ REEXPEDICION AHORRO A LA MANO = " + ex1.toString() + "##" + obtenerHoraActual());
                Respuesta = "850,062,999,Error Conectándose al Servidor,~";
            }
        }
        return Respuesta;
    }

    private String CustomerServices(final String tramaGestiontel) {
        //"922,006," + vv_refId + ",101010,MRP,GESTIONTEL," + IdCliente + "," + IpConsumer + ",,," + vv_comprobante + ",gestiontel2010,0955," + UsuarioRedAsesor + ",,~"
        //"922,006" + "," + vv_refId + "," + IdCliente + "," + IpConsumer + "," + vv_comprobante + "," + UsuarioRedAsesor + ",~"
        //922,006,007AESGG4GEIREMVF0LH9B5AES02181T,101010,MRP,GESTIONTEL,18324506,172.20.5.81,,,0000082243,gestiontel2010,0955,roberto.ceballos,,~
        String Respuesta = new String();
        final StringBuilder tramaCustomerServices = new StringBuilder();

        tramaCustomerServices.append(tramaGestiontel.split(",")[0].trim()); //puerto
        tramaCustomerServices.append(",");
        tramaCustomerServices.append(tramaGestiontel.split(",")[1].trim()); //trx
        tramaCustomerServices.append(",");
        tramaCustomerServices.append(tramaGestiontel.split(",")[2].trim()); //refId
        tramaCustomerServices.append(",101010,MRP,GESTIONTEL,");
        tramaCustomerServices.append(tramaGestiontel.split(",")[3].trim()); //IdCliente
        tramaCustomerServices.append(",");
        tramaCustomerServices.append(tramaGestiontel.split(",")[4].trim()); //IpConsumer
        tramaCustomerServices.append(",,,");
        tramaCustomerServices.append(tramaGestiontel.split(",")[5].trim()); //Comprobante
        tramaCustomerServices.append(",gestiontel2010,0955,");
        tramaCustomerServices.append(tramaGestiontel.split(",")[6].trim()); //UsuarioRedAsesor
        tramaCustomerServices.append(",,~");


        final ConectSS servicioSS = new ConectSS();
        try {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a WAS RestEQ2 " + obtenerHoraActual());
            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_WASREST_PRINCIPAL, AtlasConstantes.PUERTO_IP_WASREST, tramaCustomerServices.toString());
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde WAS RestEQ2 = " + Respuesta.split(",")[2].substring(0, 30) + " ##" + obtenerHoraActual());
        } catch (Exception ex) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal RestEQ2 = " + ex.toString() + " ##" + obtenerHoraActual());
            try {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg RestEQ2 " + obtenerHoraActual());
                Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_WASREST_CONTINGENCIA, AtlasConstantes.PUERTO_IP_WASREST, tramaCustomerServices.toString());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg WAS RestEQ2 = " + Respuesta.split(",")[2].substring(0, 30) + " ##" + obtenerHoraActual());
            } catch (Exception ex1) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctg WAS RestEQ2 = " + ex1.toString() + " ##" + obtenerHoraActual());
                Respuesta = "922,006,Error Conectándose al Servidor,~";
            }
        }
        //System.out.println(Respuesta);
        if (("006".equals(Respuesta.split(",")[1])) && (Respuesta.split(",")[2].startsWith("https://www.services.cyota.net"))) {
            //System.out.println(Respuesta.split(",")[2]);
            String UrlCustomerService = Respuesta.split(",")[2].trim();
            URL url = null;
            try {
                url = new URL(UrlCustomerService);
                try {
                    Desktop.getDesktop().browse(url.toURI());
                } catch (IOException e) {
                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                } catch (URISyntaxException e) {
                    Logger.getGlobal().log(Level.SEVERE, e.toString());
                }
            } catch (MalformedURLException e1) {
                Logger.getGlobal().log(Level.SEVERE, e1.toString());
            }
        }
        return Respuesta;
    }

    public String parseTipoDocumento(String tipoDocumento) {
        String tipoDocumentoParse = "";
        switch (tipoDocumento) {
            case "001":
                tipoDocumentoParse = "C.C";
                break;
            case "002":
                tipoDocumentoParse = "C.E";
                break;
            case "003":
                tipoDocumentoParse = "NIT";
                break;
            case "004":
                tipoDocumentoParse = "T.I";
                break;
            default:
                tipoDocumentoParse = "C.C";
                break;
        }

        return tipoDocumentoParse;
    }

    /**
     *
     *
     * @param Trama
     * @param MensajeIni
     */
    private void mostrarMenu(final String Trama, final String Servicio, final int Origen) {
        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Servicio " + Servicio + " ## " + obtenerHoraActual());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    Atlas.getVista().reloadStage();
                    final AtlasAcceso Atlasacceso = new AtlasAcceso();
                    // auxiliar para menu dinamico
                    final Collection<TitledPane> menusAux = new ArrayList<TitledPane>();

                    Cliente DatosCliente = new Cliente();
                    switch (Origen) {
                        case 1:
                            DatosCliente = populateDatos(Trama);
                            break;
                        case 2:
                            DatosCliente = populateDatosFenix(Trama);
                            break;
                        case 3:
                            DatosCliente = populateDatosCodAuto(Trama);
                            break;
                        default:
                            DatosCliente = populateDatos(Trama);
                            break;
                    }
                    // se obtiene la vista principal
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();

                    // se limpia el menu de los productos de saldos
                    MarcoPrincipalController.ListMenuSaldos.clear();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Accordion Menu = (Accordion) frameGnral.lookup("#menu");

                    MarcoPrincipalController.rootNodeSaldos = new TreeItem<String>("Consultas Saldo");
                    // se obtiene la lista de los productos del cliente 
                    final HashMap<String, ArrayList<String>> productos = DatosCliente.getProductos();
                    final String chaClave = DatosCliente.getClavesn();
                    final String tipoCliente2 = DatosCliente.getTipo_cliente();
                    final String reservadoCliente = DatosCliente.getReservado1();

                    final String tipoOTP = DatosCliente.getTipo_otp();
                    final String tieneOTP = DatosCliente.getTiene_otp(); //(S/N) Ley Antitramite (Tiene o No Tiene)
                    final String otpServicio = DatosCliente.getOtp_servicio(); // Apagado Easy 0,1

                    final String otpEstado = DatosCliente.getEstado_opt(); // Codigo 5 digitos
                    final String controlDual = DatosCliente.getControlDual(); // Control Dual
                    final String bdMigracion = DatosCliente.getbDMigracion(); // BD Migracion

//                    DatosCliente.getTiene_otp()
                    final String estadoOTP = DatosCliente.getEstado_opt() == null ? "" : DatosCliente.getEstado_opt();
                    final Set<String> keySet = productos.keySet();

                    // VALIDAR LOS CASOS DONDE SE MUESTRA ESTA OPCION -- 
                    TitledPane menu_saldos = null;
                    try {
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_saldos".equalsIgnoreCase(Menus.getId())) {
                                menu_saldos = Menus;
                                break;
                            }
                        }

                        menu_saldos.setVisible(false);
                        // VALIDACION PARA MOSTRAR EL MENU DE SALDOS
                        if (("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))
                                || Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio())
                                || Atlasacceso.getMapMillas().contains(Cliente.getCliente().getRegla_negocio())) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_saldos.setVisible(true);
                            }

                            MarcoPrincipalController.ListMenuSaldos.clear();

                            for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                                final String tipocuenta = val.next();
                                /* validacion cuentas de deposito*/
                                if (CodigoProductos.CUENTA_AHORROS.equals(tipocuenta)
                                        || CodigoProductos.CUENTA_CORRIENTE.equals(tipocuenta)
                                        || CodigoProductos.CUENTA_AFC.equals(tipocuenta)) {
                                    final ArrayList<String> cuentas = productos.get(tipocuenta);
                                    for (int i = 0; i < cuentas.size(); i++) {

                                        MarcoPrincipalController.ListMenuSaldos.add(new MenuModel(cuentas.get(i), tipocuenta));
                                    }
                                } else if (CodigoProductos.CREDITO_HIPOTECARIO.equals(tipocuenta)) {
                                    MarcoPrincipalController.ListMenuSaldos.add(new MenuModel("", AtlasConstantes.CREDITO_HIPOTECARIO));
                                }
                            }

                            MarcoPrincipalController.ListMenuSaldos.add(new MenuModel("", AtlasConstantes.MENU_TARJETA_CREDITO));

                            MarcoPrincipalController.rootNodeSaldos = new TreeItem<String>(AtlasConstantes.ITEM_SALDO_CREDIAGIL);
                            final TreeItem<String> nodoSaldoCrediagil = new TreeItem<String>(AtlasConstantes.ITEM_SALDO_CREDIAGIL);

                            MarcoPrincipalController.rootNodeSaldos = new TreeItem<String>(AtlasConstantes.ITEM_SALDO_AGROYA);
                            final TreeItem<String> nodoSaldoAgroya = new TreeItem<String>(AtlasConstantes.ITEM_SALDO_AGROYA);

                            MarcoPrincipalController.rootNodeSaldos.getChildren().add(nodoSaldoCrediagil);
                            MarcoPrincipalController.rootNodeSaldos.getChildren().add(nodoSaldoAgroya);

                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuSaldos) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeSaldos.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeSaldos.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbol_saldos.setRoot(MarcoPrincipalController.rootNodeSaldos);
                            MarcoPrincipalController.arbol_saldos.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("Menu Saldos No disponible");
                    }

                    /*  mostrar menu pagos ACA SE DEBE TENER EN CUENTA SI VIENE AUTENTICADO CON SEGUNDA CLAVE
                     */
                    TitledPane menu_pagos = null;
                    try {
                        //menu_pagos = Menu.getPanes().get(1);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_pagos".equalsIgnoreCase(Menus.getId())) {
                                menu_pagos = Menus;
                                break;
                            }
                        }
                        menu_pagos.setVisible(false);
                        //  final String chaClave = DatosCliente.getClavesn();

                        if (("K".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))
                                && !(Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapMillas().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapVirtuales().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_pagos.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuPagos.clear();
                            MarcoPrincipalController.rootNodePagos = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS);
                            final TreeItem<String> nodoPagosTerceros = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS);
                            final TreeItem<String> nodoPagosTDCpropias = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS_TDC_PROPIAS);
                            final TreeItem<String> nodoPagosTDC = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS_TDC_TERCEROS);
                            final TreeItem<String> nodoPagosPrestamos = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS_PRESTAMOS);

                            MarcoPrincipalController.rootNodePagos.getChildren().add(nodoPagosTerceros);
                            MarcoPrincipalController.rootNodePagos.getChildren().add(nodoPagosTDCpropias);
                            MarcoPrincipalController.rootNodePagos.getChildren().add(nodoPagosTDC);
                            MarcoPrincipalController.rootNodePagos.getChildren().add(nodoPagosPrestamos);

                            MarcoPrincipalController.arbol_pagos.setRoot(MarcoPrincipalController.rootNodePagos);
                            MarcoPrincipalController.arbol_pagos.showRootProperty().setValue(Boolean.FALSE);

                        } else if ("S".equalsIgnoreCase(chaClave)
                                && !(Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapMillas().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapVirtuales().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_pagos.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuPagos.clear();
                            MarcoPrincipalController.rootNodePagos = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS);
                            final TreeItem<String> nodoPagosTerceros = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS);
                            final TreeItem<String> nodoPagosTDCpropias = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS_TDC_PROPIAS);
                            final TreeItem<String> nodoPagosPrestamos = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS_PRESTAMOS);

                            MarcoPrincipalController.rootNodePagos.getChildren().add(nodoPagosTDCpropias);
                            MarcoPrincipalController.rootNodePagos.getChildren().add(nodoPagosPrestamos);
                            //  MarcoPrincipalController.rootNodePagos.getChildren().add(nodoPagosTDC);
                            MarcoPrincipalController.arbol_pagos.setRoot(MarcoPrincipalController.rootNodePagos);
                            MarcoPrincipalController.arbol_pagos.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("Menu Pagos No disponible");
                    }

                    TitledPane menu_tranf = null;
                    try {
                        /*  mostrar menu transferencias ACA SE DEBE TENER EN CUENTA SI VIENE AUTENTICADO CON SEGUNDA CLAVE
                         */
                        // menu_tranf = Menu.getPanes().get(2);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_trasnferencias".equalsIgnoreCase(Menus.getId())) {
                                menu_tranf = Menus;
                                break;
                            }
                        }
                        menu_tranf.setVisible(false);
                        if (("K".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))
                                && !(Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapMillas().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_tranf.setVisible(true);
                            }

                            MarcoPrincipalController.ListMenuTransf.clear();
                            MarcoPrincipalController.rootNodeTrasnf = new TreeItem<String>(AtlasConstantes.ITEM_TRANS_CTAS_PROPIAS);
                            final TreeItem<String> nodoctaspropias = new TreeItem<String>(AtlasConstantes.ITEM_TRANS_CTAS_PROPIAS);
                            final TreeItem<String> nodocrediagil = new TreeItem<String>(AtlasConstantes.ITEM_TRANSF_CREDIAGIL);
                            final TreeItem<String> nodocuentasinscritas = new TreeItem<String>(AtlasConstantes.ITEM_CTAS_INSCRITAS);
                            final TreeItem<String> nodoelimictasinscritas = new TreeItem<String>(AtlasConstantes.ITEM_ELIMINACION_CTAS_INSCRITAS);
                            MarcoPrincipalController.rootNodeTrasnf.getChildren().add(nodoctaspropias);
                            MarcoPrincipalController.rootNodeTrasnf.getChildren().add(nodocrediagil);
                            MarcoPrincipalController.rootNodeTrasnf.getChildren().add(nodocuentasinscritas);
                            MarcoPrincipalController.rootNodeTrasnf.getChildren().add(nodoelimictasinscritas);

                            MarcoPrincipalController.arbol_transferencias.setRoot(MarcoPrincipalController.rootNodeTrasnf);
                            MarcoPrincipalController.arbol_transferencias.showRootProperty().setValue(Boolean.FALSE);

                        } else if ("S".equalsIgnoreCase(chaClave)
                                && !(Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapMillas().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_tranf.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuTransf.clear();
                            MarcoPrincipalController.rootNodeTrasnf = new TreeItem<String>(AtlasConstantes.ITEM_TRANSF_CREDIAGIL);
                            final TreeItem<String> nodoPagosTerceros = new TreeItem<String>(AtlasConstantes.ITEM_PAGOS);
                            // final TreeItem<String> nodocrediagil = new TreeItem<String>(AtlasConstantes.ITEM_TRANSF_CREDIAGIL);
                            final TreeItem<String> nodocuentasinscritas = new TreeItem<String>(AtlasConstantes.ITEM_CTAS_INSCRITAS);
                            final TreeItem<String> nodoelimictasinscritas = new TreeItem<String>(AtlasConstantes.ITEM_ELIMINACION_CTAS_INSCRITAS);
                            //MarcoPrincipalController.rootNodeTrasnf.getChildren().add(nodocrediagil);
                            MarcoPrincipalController.rootNodeTrasnf.getChildren().add(nodocuentasinscritas);
                            MarcoPrincipalController.rootNodeTrasnf.getChildren().add(nodoelimictasinscritas);
                            // MarcoPrincipalController.rootNodePagos.getChildren().add(nodoPagosTDC);
                            MarcoPrincipalController.arbol_transferencias.setRoot(MarcoPrincipalController.rootNodeTrasnf);
                            MarcoPrincipalController.arbol_transferencias.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("Menu transferencias No disponible");
                    }

                    TitledPane menu_desploqueoSclave = null;
                    try {
                        /**
                         * MOSTRAR MENU DEBLOQUEO SEGUNDA CLAVE POR SEGURIDAD
                         */
                        //menu_desploqueoSclave = Menu.getPanes().get(3);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_desbloqueo_scps".equalsIgnoreCase(Menus.getId())) {
                                menu_desploqueoSclave = Menus;
                                break;
                            }
                        }
                        menu_desploqueoSclave.setVisible(false);

                        if ("S".equalsIgnoreCase(chaClave) && Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio())) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_desploqueoSclave.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuDSClaveCorp.clear();
                            MarcoPrincipalController.rootNodeDSClaveCorp = new TreeItem<String>(AtlasConstantes.ITEM_DESBLOQUEO_SEGUNDA_CLAVE);
                            final TreeItem<String> nodoDesbloqueoSClave = new TreeItem<String>(AtlasConstantes.ITEM_DESBLOQUEO_SEGUNDA_CLAVE);
                            MarcoPrincipalController.rootNodeDSClaveCorp.getChildren().add(nodoDesbloqueoSClave);

                            MarcoPrincipalController.arbolDesblogSegClave.setRoot(MarcoPrincipalController.rootNodeDSClaveCorp);
                            MarcoPrincipalController.arbolDesblogSegClave.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("Menu desbloqueo No disponible");
                    }

                    TitledPane menu_blqueos = null;
                    try {
                        /**
                         * MOSTRAR MENU BLQUEOS DE ACUERDO AL NIVEL DE
                         * AUTENTICACION
                         *
                         */
                        // menu_blqueos = Menu.getPanes().get(4);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_bloqueos".equalsIgnoreCase(Menus.getId())) {
                                menu_blqueos = Menus;
                                break;
                            }
                        }
                        menu_blqueos.setVisible(false);
                        // VALIDACION PARA MOSTRAR EL MENU 
                        if ((("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "N".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave)
                                || Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio()))
                                || Atlasacceso.getMapPreferencial().contains(Cliente.getCliente().getRegla_negocio())
                                || Atlasacceso.getMapAlertas().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapMillas().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(AtlasConstantes.REGLA_NEGOCIO_TDC_PREPAGO.equals(Cliente.getCliente().getRegla_negocio()))) {
                            menu_blqueos.setVisible(true);
                            MarcoPrincipalController.ListMenuBloqueos.clear();
                            MarcoPrincipalController.rootNodeBlqueos = new TreeItem<String>(AtlasConstantes.ITEM_BLOQUEOS_TDC);
                            /**
                             *
                             */
                            if (("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))) {
                                for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                                    final String tipocuenta = val.next();

                                    if (CodigoProductos.CUENTA_CORRIENTE.equals(tipocuenta)) {

                                        final TreeItem<String> nodocontracheques = new TreeItem<String>(AtlasConstantes.ITEM_CONTRA_CHEQUES);
                                        MarcoPrincipalController.rootNodeBlqueos.getChildren().add(nodocontracheques);
                                        break;
                                    }
                                }
                            }

                            final TreeItem<String> nodoBloqueosTDC = new TreeItem<String>(AtlasConstantes.ITEM_BLOQUEOS_TDC);
                            final TreeItem<String> nodoBloqueosCyTDC = new TreeItem<String>(AtlasConstantes.ITEM_BLQUEOS_CLAVES_Y_TDC);
                            final TreeItem<String> nodoBloqueoClaveppal = new TreeItem<String>(AtlasConstantes.ITEM_BLQUEOS_CLAVE_PRINCIPAL);
//                            final TreeItem<String> nodoBloqueoPreventivo = new TreeItem<String>(AtlasConstantes.ITEM_BLOQUEO_PREVENTIVO);
                            final TreeItem<String> nodoBloqueoPreventivo = new TreeItem<String>("");

                            if (Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio())) {
                                final TreeItem<String> nodoBloqueoTFCFraude = new TreeItem<String>(AtlasConstantes.ITEM_BLOQUEOS_TDC_FRAUDE);
                                MarcoPrincipalController.rootNodeBlqueos.getChildren().add(nodoBloqueoTFCFraude);
                            }

                            MarcoPrincipalController.rootNodeBlqueos.getChildren().add(nodoBloqueosTDC);
                            MarcoPrincipalController.rootNodeBlqueos.getChildren().add(nodoBloqueosCyTDC);
                            MarcoPrincipalController.rootNodeBlqueos.getChildren().add(nodoBloqueoClaveppal);
                            MarcoPrincipalController.rootNodeBlqueos.getChildren().add(nodoBloqueoPreventivo);

                            MarcoPrincipalController.arbolbloqueos.setRoot(MarcoPrincipalController.rootNodeBlqueos);
                            MarcoPrincipalController.arbolbloqueos.showRootProperty().setValue(Boolean.FALSE);

                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuBloqueos) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeBlqueos.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeBlqueos.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbolbloqueos.setRoot(MarcoPrincipalController.rootNodeBlqueos);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("Menu bloqueos No disponible");
                    }

                    TitledPane menu_servadicionales = null;
                    try {
                        /*  mostrar menu SERVICIOS ADICIONALES ACA SE DEBE TENER EN CUENTA SI VIENE AUTENTICADO CON SEGUNDA CLAVE
                         */
                        //menu_servadicionales = Menu.getPanes().get(5);//SEGUN MENU VISUALMENTE ES EL 5 
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_serviciosadicionales".equalsIgnoreCase(Menus.getId())) {
                                menu_servadicionales = Menus;
                                break;
                            }
                        }
                        menu_servadicionales.setVisible(false);

                        if (("S".equalsIgnoreCase(chaClave) || "K".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))
                                && (!"FD".equals(Cliente.getCliente().getRegla_negocio()))
                                && (!Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_servadicionales.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuServAdicional.clear();
                            MarcoPrincipalController.rootNodeServAdicional = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_BARRIDO_CTAS);

                            final TreeItem<String> nodoLifeCoach = new TreeItem<String>(AtlasConstantes.ITEM_MOV_LIFE_COACH);
                            MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodoLifeCoach);
                            
                            final TreeItem<String> nodoRecargas = new TreeItem<String>(AtlasConstantes.ITEM_MOV_RECARGAS);
                            MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodoRecargas);
                            
                            /**
                             * validar visibilidad Barrido de Ctas
                             */
                            if (Atlasacceso.getMapBarrido().contains(Cliente.getCliente().getRegla_negocio())) {
                                final TreeItem<String> nodoBarridoCtas = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_BARRIDO_CTAS);
                                MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodoBarridoCtas);
                            }
                            if (("0".equals(otpServicio) || "1".equals(otpServicio)) && ("NP".equals(controlDual) || "PA".equals(controlDual)) && ("".equals(otpEstado.trim()) || "SIN_ESTADO".equals(otpEstado.trim())) && "S".equals(tieneOTP) && !tipoCliente2.equalsIgnoreCase(AtlasConstantes.CLIENTE_ESCLUSIVO_AHORRO_A_LA_MANO)) {
                                if (!"PA".equals(controlDual)) {
                                    if (!((estadoOTP.equalsIgnoreCase(AtlasConstantes.ESTADO_OTP_BLOQVOL))
                                            || (estadoOTP.equalsIgnoreCase(AtlasConstantes.ESTADO_OTP_BLOQINTFAIL))
                                            || (estadoOTP.equalsIgnoreCase(AtlasConstantes.ESTADO_OTP_BLOQXSEG)))) {
                                    }
                                }
                                if (otpServicio.equals("1") && !"".equals(otpEstado.trim()) && !"SIN_ESTADO".equals(otpEstado.trim())) { // NEW
                                    final TreeItem<String> nodoConsultaNovedades = new TreeItem<String>(AtlasConstantes.ITEM_CONSULTA_NOVEDADES_OTP);
                                    MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodoConsultaNovedades);
                                }
                            } else {
                                if (("0".equals(otpServicio) || "1".equals(otpServicio))
                                        && ("NP".equals(controlDual) || "PA".equals(controlDual) || "PE".equals(controlDual))
                                        && !"".equals(otpEstado.trim()) && !"SIN_ESTADO".equals(otpEstado.trim())
                                        && "S".equals(tieneOTP)
                                        && !tipoCliente2.equalsIgnoreCase(AtlasConstantes.CLIENTE_ESCLUSIVO_AHORRO_A_LA_MANO)) {
                                    if (!"PA".equals(controlDual) && !"PE".equals(controlDual)) {

                                        if (!"PA".equals(controlDual)) {
                                            if (!((estadoOTP.equalsIgnoreCase(AtlasConstantes.ESTADO_OTP_BLOQVOL))
                                                    || (estadoOTP.equalsIgnoreCase(AtlasConstantes.ESTADO_OTP_BLOQINTFAIL))
                                                    || (estadoOTP.equalsIgnoreCase(AtlasConstantes.ESTADO_OTP_BLOQXSEG)))) {
                                            }
                                        }
                                    }
                                    if (otpServicio.equals("1") && !AtlasConstantes.ESTADO_OTP_BLOQXSEG.equals(otpEstado.trim())) { // NEW
                                        final TreeItem<String> nodoConsultaNovedades = new TreeItem<String>(AtlasConstantes.ITEM_CONSULTA_NOVEDADES_OTP);
                                        MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodoConsultaNovedades);
                                    }
                                }
                            }
                            if ("V".equalsIgnoreCase(chaClave) && "1".equals(otpServicio)) { // Clave dinamica valida
                                final TreeItem<String> nodoActualizacionDatosSeguridad = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_ACTUALIZACION_DATOS_SEGURIDAD);
                                MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodoActualizacionDatosSeguridad);
                            }
                            if (!tipoCliente2.equalsIgnoreCase(AtlasConstantes.CLIENTE_ESCLUSIVO_AHORRO_A_LA_MANO) && tieneOTP.equalsIgnoreCase("N")) {
                                if (Cliente.getCliente().getOpc_final_de_menu().equals(AtlasConstantes.ACEPTA_TERMINOS_Y_COND_OTP) || Cliente.getCliente().getOpc_final_de_menu().equals(AtlasConstantes.ACEPTA_TERMINOS_Y_COND_OTP_SIN_ESCUCHA)) {
                                    if (otpServicio.equals("1") && (!"".equals(otpEstado.trim()) || !"SIN_ESTADO".equals(otpEstado.trim()))) {
                                        final TreeItem<String> inscripcionClaveDinamica = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_INSCRIPCION_CLAVE_DINAMICA_MENU);
                                        MarcoPrincipalController.rootNodeServAdicional.getChildren().add(inscripcionClaveDinamica);
                                    }
                                }
                            }
                            //2019-07-12 Ticket#2019070585007028
                            //2019-07-26 Campañas Interactivas
                            if ("1".equals(otpServicio)
                                    && (Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio()) || Atlasacceso.getMapPreferencial().contains(Cliente.getCliente().getRegla_negocio()) || Atlasacceso.getMapCampanasInteractivas().contains(Cliente.getCliente().getRegla_negocio()))
                                    && (AtlasConstantes.ESTADO_OTP_BLOQXSEG.equals(otpEstado.trim()))) {//00014 - Clave din bloq x seg 
                                if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                    menu_servadicionales.setVisible(true);
                                }
                                final TreeItem<String> nodoDesbloqueoClaveDin = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_DESBLOQUEO_CLAVE_DINAMICA_MENU);
                                MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodoDesbloqueoClaveDin);
                            }
                            /**
                             * opciones token , deshabilitados
                             */
                            if (!(Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio()))) {
                                final TreeItem<String> nodotokenemp = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_TOKEN_EMPRESAS);
                                final TreeItem<String> nodotokenae = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_TOKEN_AE);
                                final TreeItem<String> nodotokendist = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_TOKEN_DISTRIBUCION);

                                MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodotokenemp);
                                MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodotokenae);
                                MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodotokendist);
                            }
                            /**
                             * opciones token , deshabilitados
                             */
                            // se construye el arbol de SERVICIOS ADICIONALES
                            for (MenuModel menus : MarcoPrincipalController.ListMenuServAdicional) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeServAdicional.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;// 5306956055945920  346
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeServAdicional.getChildren().add(depNode);
                                    depNode.getChildren().add(empLeaf);
                                }
                            }
                            MarcoPrincipalController.arbol_servadicionales.setRoot(MarcoPrincipalController.rootNodeServAdicional);
                            MarcoPrincipalController.arbol_servadicionales.showRootProperty().setValue(Boolean.FALSE);
                        } else if (("1".equals(otpServicio) && "S".equalsIgnoreCase(chaClave) || "K".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))
                                && (!"FD".equals(Cliente.getCliente().getRegla_negocio()))
                                && (!Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))
                                && (AtlasConstantes.ESTADO_OTP_BLOQXSEG.equals(otpEstado.trim()))) {//00014 - Clave din bloq x seg 
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_servadicionales.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuServAdicional.clear();
                            MarcoPrincipalController.rootNodeServAdicional = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_DESBLOQUEO_CLAVE_DINAMICA_MENU);
                            final TreeItem<String> nodoDesbloqueoClaveDin = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_DESBLOQUEO_CLAVE_DINAMICA_MENU);
                            MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodoDesbloqueoClaveDin);
                            MarcoPrincipalController.arbol_servadicionales.setRoot(MarcoPrincipalController.rootNodeServAdicional);
                            MarcoPrincipalController.arbol_servadicionales.showRootProperty().setValue(Boolean.FALSE);
                            //2019-07-12 Ticket#2019070585007028
                        } else if (("1".equals(otpServicio) && "S".equalsIgnoreCase(chaClave) || "K".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))
                                && (!"FD".equals(Cliente.getCliente().getRegla_negocio()))
                                && (!Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))
                                && (Atlasacceso.getMapPreferencial().contains(Cliente.getCliente().getRegla_negocio())) && AtlasConstantes.ESTADO_OTP_BLOQXSEG.equals(otpEstado.trim())) {//00014 - Clave din bloq x seg 
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_servadicionales.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuServAdicional.clear();
                            MarcoPrincipalController.rootNodeServAdicional = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_DESBLOQUEO_CLAVE_DINAMICA_MENU);
                            final TreeItem<String> nodoDesbloqueoClaveDin = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_DESBLOQUEO_CLAVE_DINAMICA_MENU);
                            MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodoDesbloqueoClaveDin);
                            MarcoPrincipalController.arbol_servadicionales.setRoot(MarcoPrincipalController.rootNodeServAdicional);
                            MarcoPrincipalController.arbol_servadicionales.showRootProperty().setValue(Boolean.FALSE);
                        } else if ("N".equalsIgnoreCase(chaClave) && (!"FD".equals(Cliente.getCliente().getRegla_negocio()))
                                && (!Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_servadicionales.setVisible(true); //menu token deshabilitado
                            }
                            MarcoPrincipalController.ListMenuServAdicional.clear();
                            MarcoPrincipalController.rootNodeServAdicional = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_TOKEN_EMPRESAS);
                            final TreeItem<String> nodotokenemp = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_TOKEN_EMPRESAS);
                            final TreeItem<String> nodotokenae = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_TOKEN_AE);
                            final TreeItem<String> nodotokendist = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_TOKEN_DISTRIBUCION);
                            MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodotokenemp);
                            MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodotokenae);
                            MarcoPrincipalController.arbol_servadicionales.setRoot(MarcoPrincipalController.rootNodeServAdicional);
                            MarcoPrincipalController.arbol_servadicionales.showRootProperty().setValue(Boolean.FALSE);
                            MarcoPrincipalController.rootNodeServAdicional.getChildren().add(nodotokendist);
                        } else if (!tipoCliente2.equalsIgnoreCase(AtlasConstantes.CLIENTE_ESCLUSIVO_AHORRO_A_LA_MANO) && tieneOTP.equalsIgnoreCase("N")
                                && (!Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (Cliente.getCliente().getOpc_final_de_menu().equals(AtlasConstantes.ACEPTA_TERMINOS_Y_COND_OTP) || Cliente.getCliente().getOpc_final_de_menu().equals(AtlasConstantes.ACEPTA_TERMINOS_Y_COND_OTP_SIN_ESCUCHA)) {
                                if (otpServicio.equals("1") && (!"".equals(otpEstado.trim()) || !"SIN_ESTADO".equals(otpEstado.trim()))) {
                                    if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                        menu_servadicionales.setVisible(true); //menu token deshabilitado
                                    }
                                    MarcoPrincipalController.ListMenuServAdicional.clear();
                                    MarcoPrincipalController.rootNodeServAdicional = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_INSCRIPCION_CLAVE_DINAMICA_MENU);
                                    final TreeItem<String> inscripcionClaveDinamica = new TreeItem<String>(AtlasConstantes.ITEM_SERVAD_INSCRIPCION_CLAVE_DINAMICA_MENU);
                                    MarcoPrincipalController.rootNodeServAdicional.getChildren().add(inscripcionClaveDinamica);
                                    MarcoPrincipalController.arbol_servadicionales.setRoot(MarcoPrincipalController.rootNodeServAdicional);
                                    MarcoPrincipalController.arbol_servadicionales.showRootProperty().setValue(Boolean.FALSE);
                                }
                            }
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu servicios adicionales No disponible");
                    }

                    TitledPane menu_infotdc = null;
                    try {
                        //menu_infotdc = Menu.getPanes().get(6);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_infotdc".equalsIgnoreCase(Menus.getId())) {
                                menu_infotdc = Menus;
                                break;
                            }
                        }
                        menu_infotdc.setVisible(false);
                        // VALIDACION PARA MOSTRAR EL MENU DE INFORMACION TDC
                        if (("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_infotdc.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuInfoTdc.clear();
//                            MarcoPrincipalController.rootNodeInfoTdc = new TreeItem<String>(AtlasConstantes.ITEM_INFO_TDC);

//                            final TreeItem<String> nodoMovDia = new TreeItem<String>(AtlasConstantes.ITEM_INFO_TDC);
//                            MarcoPrincipalController.rootNodeInfoTdc.getChildren().add(nodoMovDia);
                            MarcoPrincipalController.rootNodeInfoTdc = new TreeItem<String>("");
                            MarcoPrincipalController.ListMenuInfoTdc.add(new MenuModel("", AtlasConstantes.ITEM_INFO_TDC));
                            MarcoPrincipalController.ListMenuInfoTdc.add(new MenuModel("", AtlasConstantes.ITEM_AVANCESTDC_CTADESTINO));
                            MarcoPrincipalController.ListMenuInfoTdc.add(new MenuModel("", AtlasConstantes.ITEM_AVANCESTDC_CTAORIGEN));
                            // se construye el arbol del menu
                            for (MenuModel menus : MarcoPrincipalController.ListMenuInfoTdc) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeInfoTdc.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeInfoTdc.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbol_infotdc.setRoot(MarcoPrincipalController.rootNodeInfoTdc);
                            MarcoPrincipalController.arbol_infotdc.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu info tdc No disponible");
                    }

                    TitledPane menu_consultas = null;
                    try {
                        // menu_consultas = Menu.getPanes().get(7);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_consultas".equalsIgnoreCase(Menus.getId())) {
                                menu_consultas = Menus;
                                break;
                            }
                        }
                        menu_consultas.setVisible(false);

                        // VALIDACION PARA MOSTRAR EL MENU DE CONSULTAS
                        if (("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))
                                && !(Atlasacceso.getMapLesBasica().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_consultas.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuConsultas.clear();
                            MarcoPrincipalController.rootNodeConsultas = new TreeItem<String>(AtlasConstantes.ITEM_CONSULTAS_CDT);
                            // final TreeItem<String> nodoconsultacdt = new TreeItem<String>(AtlasConstantes.ITEM_CONSULTAS_CDT);
                            final TreeItem<String> nodoconsTRM = new TreeItem<String>(AtlasConstantes.ITEM_CONSULTAS_TRM);
                            final TreeItem<String> nodoinfoSeg = new TreeItem<String>(AtlasConstantes.ITEM_INFO_SEGURIDAD);

                            final TreeItem<String> nodoAlertasEnv = new TreeItem<String>(AtlasConstantes.ITEM_ALERTAS_ENVIADAS);
                            final TreeItem<String> nodoConsultaAlertas = new TreeItem<String>(AtlasConstantes.ITEM_PARAMETRIZACION);

                            final TreeItem<String> nodoParamTopes = new TreeItem<String>(AtlasConstantes.ITEM_CONS_PARAMETRIZACION_TOPES);

                            final TreeItem<String> nodoRegistroAlertas = new TreeItem<String>(AtlasConstantes.ITEM_REGISTRAR_SERVICIO_ALERTAS);
                            final TreeItem<String> nodoInactivarAlertas = new TreeItem<String>(AtlasConstantes.ITEM_INACTIVAR_SERVICIO_ALERTAS);

                            if ("K".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave)) {
                                final TreeItem<String> nodoRegParamTopes = new TreeItem<String>(AtlasConstantes.ITEM_PARAMETRIZACION_TOPES);
                                final TreeItem<String> nodoParamAlertas = new TreeItem<String>(AtlasConstantes.ITEM_PARAMETRIZAR_ALERTAS);
                                MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodoParamAlertas);
                                MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodoRegParamTopes);
                            }

                            MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodoRegistroAlertas);
                            MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodoInactivarAlertas);
                            MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodoAlertasEnv);
                            MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodoConsultaAlertas);
                            MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodoParamTopes);
                            MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodoconsTRM);
                            MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodoinfoSeg);
                            // en validacion
                            for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                                final String tipocuenta = val.next();
                                /* validacion cuentas cdt */
                                if (CodigoProductos.CREDITO_CDT.equals(tipocuenta)) {
                                    MarcoPrincipalController.ListMenuConsultas.add(new MenuModel("", AtlasConstantes.ITEM_CONSULTAS_CDT));
                                    break;
                                }
                            }

                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuConsultas) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeConsultas.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeConsultas.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbol_consultas.setRoot(MarcoPrincipalController.rootNodeConsultas);
                            MarcoPrincipalController.arbol_consultas.showRootProperty().setValue(Boolean.FALSE);
                        } else if (("N".equalsIgnoreCase(chaClave)
                                && (Atlasacceso.getMapEmpresas().contains(Cliente.getCliente().getRegla_negocio())))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_consultas.setVisible(false);
                            }
                            /**
                             * EN CONSTRUCCION
                             */
//                        MarcoPrincipalController.ListMenuConsultas.clear();
//                        MarcoPrincipalController.rootNodeConsultas = new TreeItem<String>(AtlasConstantes.ITEM_REFERENCIAS_CDT);
//                        final TreeItem<String> nodorefcdt = new TreeItem<String>(AtlasConstantes.ITEM_REFERENCIAS_CDT);
//                        MarcoPrincipalController.rootNodeConsultas.getChildren().add(nodorefcdt);
//                        MarcoPrincipalController.arbol_consultas.setRoot(MarcoPrincipalController.rootNodeConsultas);
//                        MarcoPrincipalController.arbol_consultas.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu consultas No disponible");
                    }

                    TitledPane menu_movimientos = null;
                    try {
                        // menu_movimientos = Menu.getPanes().get(8);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_movimientos".equalsIgnoreCase(Menus.getId())) {
                                menu_movimientos = Menus;
                                break;
                            }
                        }
                        menu_movimientos.setVisible(false);
                        // VALIDACION PARA MOSTRAR EL MENU DE MOVIMIENTOS
                        if (("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_movimientos.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuMovmientos.clear();
                            MarcoPrincipalController.rootNodeMovimientos = new TreeItem<String>("");

                            for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                                final String tipocuenta = val.next();
                                /* validacion cuentas cdt */
                                if (CodigoProductos.CUENTA_AHORROS.equals(tipocuenta)) {
                                    MarcoPrincipalController.ListMenuMovmientos.add(new MenuModel("", AtlasConstantes.ITEM_MOV_AHORROS));
                                    break;
                                }
                            }

                            for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                                final String tipocuenta = val.next();
                                /* validacion cuentas cdt */
                                if (CodigoProductos.CUENTA_CORRIENTE.equals(tipocuenta)) {
                                    MarcoPrincipalController.ListMenuMovmientos.add(new MenuModel("", AtlasConstantes.ITEM_MOV_CORRIENTE));
                                    break;
                                }
                            }
                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuMovmientos) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeMovimientos.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeMovimientos.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbol_movimientos.setRoot(MarcoPrincipalController.rootNodeMovimientos);
                            MarcoPrincipalController.arbol_movimientos.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu movimientos No disponible");
                    }

                    TitledPane menu_contraBloqueos = null;
                    try {
                        //menu_contraBloqueos = Menu.getPanes().get(9);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_contra_bloqueos".equalsIgnoreCase(Menus.getId())) {
                                menu_contraBloqueos = Menus;
                                break;
                            }
                        }
                        menu_contraBloqueos.setVisible(false);
                        // VALIDACION PARA MOSTRAR EL MENU CONTRA BLOQUEOS
                        if ((("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave)))
                                && !(Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_contraBloqueos.setVisible(false); // MENU DESHABILITADO
                            }
                            MarcoPrincipalController.ListMenuContraBloqueos.clear();
                            MarcoPrincipalController.rootNodeContraBloqueos = new TreeItem<String>("");

                            for (final Iterator<String> val = keySet.iterator(); val.hasNext();) {
                                final String tipocuenta = val.next();
                                if (CodigoProductos.CUENTA_CORRIENTE.equals(tipocuenta)) {
                                    MarcoPrincipalController.ListMenuContraBloqueos.add(new MenuModel("", AtlasConstantes.ITEM_CONTRA_CHEQUES));
                                    break;
                                }
                            }
                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuContraBloqueos) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeContraBloqueos.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeContraBloqueos.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbol_contrabloqueos.setRoot(MarcoPrincipalController.rootNodeContraBloqueos);
                            MarcoPrincipalController.arbol_contrabloqueos.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("Menu bloqueos No disponible");
                    }

                    TitledPane menu_infoseguridad = null;
                    try {
                        // menu_infoseguridad = Menu.getPanes().get(10);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menu_infoSeguridad".equalsIgnoreCase(Menus.getId())) {
                                menu_infoseguridad = Menus;
                                break;
                            }
                        }
                        menu_infoseguridad.setVisible(false);

                        if (("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_infoseguridad.setVisible(false); // MENU DESHABILITADO                     
                            }
                            MarcoPrincipalController.ListMenuInfoSeguridad.clear();
                            MarcoPrincipalController.rootNodeInfoSeguridad = new TreeItem<String>("");
                            MarcoPrincipalController.ListMenuInfoSeguridad.add(new MenuModel("", AtlasConstantes.ITEM_INFO_SEGURIDAD));

                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuInfoSeguridad) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeInfoSeguridad.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeInfoSeguridad.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbol_infoseguridad.setRoot(MarcoPrincipalController.rootNodeInfoSeguridad);
                            MarcoPrincipalController.arbol_infoseguridad.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu infoseguridad No disponible");
                    }

                    TitledPane menu_recaudos = null;
                    try {
                        //en desarrollo recaudos 
                        //menu_recaudos = Menu.getPanes().get(11);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menurecaudos".equalsIgnoreCase(Menus.getId())) {
                                menu_recaudos = Menus;
                                break;
                            }
                        }

                        menu_recaudos.setVisible(false);
                        if ((("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))) /*&& !(Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio()))*/
                                && !(Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))
                                && !("FD".equals(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_recaudos.setVisible(true); //                      
                            }
                            MarcoPrincipalController.ListMenuRecaudos.clear();
                            MarcoPrincipalController.rootNodeRecaudos = new TreeItem<String>("");
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_MOV_PSE));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_MOV_DEB));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_ARCH_ENV));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_ARCH_REC));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_MOV_CONV));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_PAG_DEB_AUTO));
                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuRecaudos) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeRecaudos.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeRecaudos.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbol_recaudos.setRoot(MarcoPrincipalController.rootNodeRecaudos);
                            MarcoPrincipalController.arbol_recaudos.showRootProperty().setValue(Boolean.FALSE);
                        } else if ((("N".equalsIgnoreCase(chaClave)) && (!"FD".equals(Cliente.getCliente().getRegla_negocio()))) //&& (Atlasacceso.MapEmpresas().contains(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_recaudos.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuRecaudos.clear();
                            MarcoPrincipalController.rootNodeRecaudos = new TreeItem<String>("");
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_MOV_PSE));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_MOV_DEB));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_ARCH_ENV));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_ARCH_REC));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_MOV_CONV));
                            MarcoPrincipalController.ListMenuRecaudos.add(new MenuModel("", AtlasConstantes.ITEM_RECAUDOS_PAG_DEB_AUTO));
                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuRecaudos) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeRecaudos.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeRecaudos.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbol_recaudos.setRoot(MarcoPrincipalController.rootNodeRecaudos);
                            MarcoPrincipalController.arbol_recaudos.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu recaudos No disponible");
                    }

                    TitledPane menu_tdcprepago = null;
                    try {
                        //en desarrollo TDC PREPAGO 
                        // menu_tdcprepago = Menu.getPanes().get(12);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menuTDCprepago".equalsIgnoreCase(Menus.getId())) {
                                menu_tdcprepago = Menus;
                                break;
                            }
                        }
                        menu_tdcprepago.setVisible(false);

                        if (("N".equalsIgnoreCase(chaClave) || "K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave) || chaClave.isEmpty())
                                && (AtlasConstantes.REGLA_NEGOCIO_TDC_PREPAGO.equals(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_tdcprepago.setVisible(true); //                      
                            }
                            MarcoPrincipalController.ListMenuTDCprepago.clear();
                            MarcoPrincipalController.rootNodeTDCprepago = new TreeItem<String>(AtlasConstantes.ITEM_AUTENTICACION_PREPAGO);

                            if (SocketController.isAutenticadoTDCprep.get()) {
                                final TreeItem<String> nodobloqueo = new TreeItem<String>(AtlasConstantes.ITEM_BLOQUEO_PREPAGO);
                                final TreeItem<String> nodoactivacion = new TreeItem<String>(AtlasConstantes.ITEM_ACTIVACION_PREPAGO);
                                final TreeItem<String> nodoconsultas = new TreeItem<String>(AtlasConstantes.ITEM_CONSULTAS_PREPAGO);
                                final TreeItem<String> nodoMovimientos = new TreeItem<String>(AtlasConstantes.ITEM_MOVIMIENTO_PREPAGO);
                                //final TreeItem<String> nodoautorizacion = new TreeItem<String>(AtlasConstantes.ITEM_AUTORIZACIONES_PREPAGO);
                                final TreeItem<String> nodosolclave = new TreeItem<String>(AtlasConstantes.ITEM_SOLCLAVE_PREPAGO);
                                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodobloqueo);
                                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoactivacion);
                                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoconsultas);
                                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoMovimientos);
                                //MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoautorizacion);
                                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodosolclave);
                            } else {
                                final TreeItem<String> nodoautenticacion = new TreeItem<String>(AtlasConstantes.ITEM_AUTENTICACION_PREPAGO);
                                final TreeItem<String> nodobloqueo = new TreeItem<String>(AtlasConstantes.ITEM_BLOQUEO_PREPAGO);
                                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodoautenticacion);
                                MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(nodobloqueo);
                            }
                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuTDCprepago) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeTDCprepago.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeTDCprepago.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arboltdcPrepago.setRoot(MarcoPrincipalController.rootNodeTDCprepago);
                            MarcoPrincipalController.arboltdcPrepago.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu tdc prepago No disponible");
                    }

                    TitledPane menu_puntosCol = null;
                    try {
                        // menu_infoseguridad = Menu.getPanes().get(10);
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menuPuntosCol".equalsIgnoreCase(Menus.getId())) {
                                menu_puntosCol = Menus;
                                break;
                            }
                        }
                        menu_puntosCol.setVisible(false);

                        if ((("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave)))
                                && !("FD".equals(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_puntosCol.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuPuntosCol.clear();
                            MarcoPrincipalController.rootNodePuntosCol = new TreeItem<String>("");
                            MarcoPrincipalController.ListMenuPuntosCol.add(new MenuModel("", AtlasConstantes.ITEM_SALDO_PUNTOS_COL));
                            MarcoPrincipalController.ListMenuPuntosCol.add(new MenuModel("", AtlasConstantes.ITEM_PUNTOS_MIGRACION_COL));
                            MarcoPrincipalController.ListMenuPuntosCol.add(new MenuModel("", AtlasConstantes.ITEM_MOVIMIENTOS_PUNTOS_COL));
                            MarcoPrincipalController.ListMenuPuntosCol.add(new MenuModel("", AtlasConstantes.ITEM_AJUSTE_PUNTOS));

                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuPuntosCol) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodePuntosCol.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodePuntosCol.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbolPuntosCol.setRoot(MarcoPrincipalController.rootNodePuntosCol);
                            MarcoPrincipalController.arbolPuntosCol.showRootProperty().setValue(Boolean.FALSE);

                        } else if (("O".equalsIgnoreCase(chaClave)) || ("N".equalsIgnoreCase(chaClave))
                                && !("FD".equals(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_puntosCol.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuPuntosCol.clear();
                            MarcoPrincipalController.rootNodePuntosCol = new TreeItem<String>("");
                            MarcoPrincipalController.ListMenuPuntosCol.add(new MenuModel("", AtlasConstantes.ITEM_PUNTOS_MIGRACION_COL));
                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuPuntosCol) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodePuntosCol.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodePuntosCol.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbolPuntosCol.setRoot(MarcoPrincipalController.rootNodePuntosCol);
                            MarcoPrincipalController.arbolPuntosCol.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu Puntos Colombia No disponible");
                    }

                    TitledPane menu_girosNal = null;
                    try {
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menuGirosNal".equalsIgnoreCase(Menus.getId())) {
                                menu_girosNal = Menus;
                                break;
                            }
                        }
                        menu_girosNal.setVisible(false);

                        if ((("O".equalsIgnoreCase(chaClave)) || ("N".equalsIgnoreCase(chaClave) || "K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave) || chaClave.isEmpty()))
                                && !("FD".equals(Cliente.getCliente().getRegla_negocio()))
                                && !(Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_girosNal.setVisible(true); //                   salia PCO
                            }
                            MarcoPrincipalController.ListMenuGirosNal.clear();
                            MarcoPrincipalController.rootNodeGirosNal = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_NAL);
                            final TreeItem<String> nodoGirosInit = new TreeItem<String>(AtlasConstantes.ITEM_GIROS_NAL);
                            MarcoPrincipalController.rootNodeGirosNal.getChildren().add(nodoGirosInit);

                            // se construye el arbol de los productos tipoCuenta-cuentas
                            for (MenuModel menus : MarcoPrincipalController.ListMenuGirosNal) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeGirosNal.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeGirosNal.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbolGirosNal.setRoot(MarcoPrincipalController.rootNodeGirosNal);
                            MarcoPrincipalController.arbolGirosNal.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu giros nacionales No disponible");
                    }
                    /*
                     * Add:  Roberto Ceballos:  Desbloqueo ALM
                     */
                    TitledPane menu_DesbloqueoAlm = null;
                    try {
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menuDesbloqueoAlm".equalsIgnoreCase(Menus.getId())) {
                                menu_DesbloqueoAlm = Menus;
                                break;
                            }
                        }
                        menu_DesbloqueoAlm.setVisible(false);
                        if ((("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave)))
                                && !(Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_DesbloqueoAlm.setVisible(true);
                            }
                            MarcoPrincipalController.ListMenuDesbloqueoAlm.clear();
                            MarcoPrincipalController.rootNodeDesbloqueoAlm = new TreeItem<String>(AtlasConstantes.ITEM_DESBLOQUEO_ALM);
                            final TreeItem<String> nodoDesbloqueoInit = new TreeItem<String>(AtlasConstantes.ITEM_DESBLOQUEO_ALM);
                            MarcoPrincipalController.rootNodeDesbloqueoAlm.getChildren().add(nodoDesbloqueoInit);
                            MarcoPrincipalController.ListMenuDesbloqueoAlm.add(new MenuModel("", AtlasConstantes.ITEM_LOG_ALM));

                            // se construye el arbol
                            for (MenuModel menus : MarcoPrincipalController.ListMenuDesbloqueoAlm) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeDesbloqueoAlm.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeDesbloqueoAlm.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbolDesbloqueoAlm.setRoot(MarcoPrincipalController.rootNodeDesbloqueoAlm);
                            MarcoPrincipalController.arbolDesbloqueoAlm.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu Desbloqueo ALM No disponible");
                    }
                    /*
                     * Add:  Roberto Ceballos:  Declinaciones TDC
                     */
                    TitledPane menu_declinacionesTdc = null;
                    try {
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menuDeclinacionesTdc".equalsIgnoreCase(Menus.getId())) {
                                menu_declinacionesTdc = Menus;
                                break;
                            }
                        }
                        menu_declinacionesTdc.setVisible(false);

                        if ((("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave)))
                                && !(Atlasacceso.getMapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio()))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_declinacionesTdc.setVisible(true); // en DLLO
                            }
                            MarcoPrincipalController.ListMenuDeclinacionesTdc.clear();
                            MarcoPrincipalController.rootNodeDeclinacionesTdc = new TreeItem<String>(AtlasConstantes.ITEM_DECLINACIONES_TDC);
                            final TreeItem<String> nodoDeclinacionesInit = new TreeItem<String>(AtlasConstantes.ITEM_DECLINACIONES_TDC);
                            MarcoPrincipalController.rootNodeDeclinacionesTdc.getChildren().add(nodoDeclinacionesInit);

                            // se construye el arbol
                            for (MenuModel menus : MarcoPrincipalController.ListMenuDeclinacionesTdc) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeDeclinacionesTdc.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeDeclinacionesTdc.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbolDeclinacionesTdc.setRoot(MarcoPrincipalController.rootNodeDeclinacionesTdc);
                            MarcoPrincipalController.arbolDeclinacionesTdc.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu declinaciones tdc No disponible");
                    }
                    /*
                     * Add:  Roberto Ceballos:  Bolsillos
                     */
                    TitledPane menu_bolsillos = null;
                    try {
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menubolsillos".equalsIgnoreCase(Menus.getId())) {
                                menu_bolsillos = Menus;
                                break;
                            }
                        }
                        menu_bolsillos.setVisible(false);

                        if (("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave) || "V".equalsIgnoreCase(chaClave))) {
                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_bolsillos.setVisible(true); // en DLLO
                            }
                            MarcoPrincipalController.ListMenuBolsillos.clear();
                            MarcoPrincipalController.rootNodeBolsillos = new TreeItem<String>(AtlasConstantes.ITEM_BOLSILLOS);
                            final TreeItem<String> nodoBolsillosInit = new TreeItem<String>(AtlasConstantes.ITEM_BOLSILLOS);
                            MarcoPrincipalController.rootNodeBolsillos.getChildren().add(nodoBolsillosInit);
                            // se construye el arbol
                            for (MenuModel menus : MarcoPrincipalController.ListMenuBolsillos) {
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeBolsillos.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeBolsillos.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbolBolsillos.setRoot(MarcoPrincipalController.rootNodeBolsillos);
                            MarcoPrincipalController.arbolBolsillos.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu Bolsillos No disponible");
                    }
                    /*
                     * Add:  Roberto Ceballos:  Visor
                     */
                   /* TitledPane menu_visor = null;
                    try {
                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
                            TitledPane Menus = it.next();
                            if ("menuvisor".equalsIgnoreCase(Menus.getId())) {
                                menu_visor = Menus;
                                break;
                            }
                        }
                        menu_visor.setVisible(false);

                        if ((("K".equalsIgnoreCase(chaClave) || "S".equalsIgnoreCase(chaClave)
                                || "V".equalsIgnoreCase(chaClave)))
                                && (Atlasacceso.getMapPreferencial().contains(Cliente.getCliente().getRegla_negocio()))
                                || (Atlasacceso.getMapFidelizacion().contains(Cliente.getCliente().getRegla_negocio()))) {

                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
                                menu_visor.setVisible(true); // en DLLO
                            }
                            MarcoPrincipalController.ListMenuVisor.clear();
                            MarcoPrincipalController.rootNodeVisor = new TreeItem<String>(AtlasConstantes.ITEM_VISOR);
                            final TreeItem<String> nodoVisorInit = new TreeItem<String>(AtlasConstantes.ITEM_VISOR);
                            MarcoPrincipalController.rootNodeVisor.getChildren().add(nodoVisorInit);
                            // se construye el arbol
                            for (MenuModel menus : MarcoPrincipalController.ListMenuVisor) {
                                
                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
                                boolean found = false;
                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeVisor.getChildren()) {
                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {

                                        depNode.getChildren().add(empLeaf);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    final TreeItem<String> depNode = new TreeItem<String>(
                                            menus.getMenuPadre());
                                    MarcoPrincipalController.rootNodeVisor.getChildren().add(depNode);
                                    if (!empLeaf.getValue().isEmpty()) {
                                        depNode.getChildren().add(empLeaf);
                                    }
                                }
                            }
                            MarcoPrincipalController.arbolVisor.setRoot(MarcoPrincipalController.rootNodeVisor);
                            MarcoPrincipalController.arbolVisor.showRootProperty().setValue(Boolean.FALSE);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("menu Visor No disponible");
                    }*/
                    /*
                     * Add:  Roberto Ceballos:  Url WEB
                     */
                    //PayPro
//                    TitledPane menu_urlweb = null;
//                    try {
//                        for (Iterator<TitledPane> it = Menu.getPanes().iterator(); it.hasNext();) {
//                            TitledPane Menus = it.next();
//                            if ("menuUrlWeb".equalsIgnoreCase(Menus.getId())) {
//                                menu_urlweb = Menus;
//                                break;
//                            }
//                        }
//                        menu_urlweb.setVisible(false);
//
//                        if (Atlasacceso.MapEstablecimientos().contains(Cliente.getCliente().getRegla_negocio())) {
//                            if (reservadoCliente.equalsIgnoreCase("CUPT") || reservadoCliente.equalsIgnoreCase("")) {
//                                menu_urlweb.setVisible(true); // en DLLO
//                            }
//                            MarcoPrincipalController.ListMenuUrlWeb.clear();
//                            MarcoPrincipalController.rootNodeUrlWeb = new TreeItem<String>("");
//                            MarcoPrincipalController.ListMenuUrlWeb.add(new MenuModel("", AtlasConstantes.ITEM_PAYPAL_RAM));
//                            MarcoPrincipalController.ListMenuUrlWeb.add(new MenuModel("", AtlasConstantes.ITEM_PAYPAL_MP));
//
//                            // se construye el arbol
//                            for (MenuModel menus : MarcoPrincipalController.ListMenuUrlWeb) {
//                                final TreeItem<String> empLeaf = new TreeItem<String>(menus.getMenuHijo());
//                                boolean found = false;
//                                for (TreeItem<String> depNode : MarcoPrincipalController.rootNodeUrlWeb.getChildren()) {
//                                    if (depNode.getValue().contentEquals(menus.getMenuPadre())) {
//                                        depNode.getChildren().add(empLeaf);
//                                        found = true;
//                                        break;
//                                    }
//                                }
//                                if (!found) {
//                                    final TreeItem<String> depNode = new TreeItem<String>(
//                                            menus.getMenuPadre());
//                                    MarcoPrincipalController.rootNodeUrlWeb.getChildren().add(depNode);
//                                    if (!empLeaf.getValue().isEmpty()) {
//                                        depNode.getChildren().add(empLeaf);
//                                    }
//                                }
//                            }
//                            MarcoPrincipalController.arbolUrlWeb.setRoot(MarcoPrincipalController.rootNodeUrlWeb);
//                            MarcoPrincipalController.arbolUrlWeb.showRootProperty().setValue(Boolean.FALSE);
//                        }
//                    } catch (Exception e) {
//                        gestorDoc.imprimir("menu Url Web No disponible");
//                    }

                    /**
                     * eliminar los menus deacuerdo a su visibilidad
                     */
                    if (menu_saldos != null) {
                        if (!menu_saldos.isVisible()) {
                            menusAux.add(menu_saldos);
                        }
                    }

                    if (menu_pagos != null) {
                        if (!menu_pagos.isVisible()) {
                            menusAux.add(menu_pagos);
                        }
                    }

                    if (menu_tranf != null) {
                        if (!menu_tranf.isVisible()) {
                            menusAux.add(menu_tranf);
                        }
                    }

                    if (menu_blqueos != null) {
                        if (!menu_blqueos.isVisible()) {
                            menusAux.add(menu_blqueos);
                        }
                    }

                    if (menu_servadicionales != null) {
                        if (!menu_servadicionales.isVisible()) {
                            menusAux.add(menu_servadicionales);
                        }
                    }

                    if (menu_desploqueoSclave != null) {
                        if (!menu_desploqueoSclave.isVisible()) {
                            menusAux.add(menu_desploqueoSclave);
                        }
                    }

                    if (menu_consultas != null) {
                        if (!menu_consultas.isVisible()) {
                            menusAux.add(menu_consultas);
                        }
                    }

                    if (menu_infotdc != null) {
                        if (!menu_infotdc.isVisible()) {
                            menusAux.add(menu_infotdc);
                        }
                    }

                    if (menu_movimientos != null) {
                        if (!menu_movimientos.isVisible()) {
                            menusAux.add(menu_movimientos);
                        }
                    }

                    if (menu_contraBloqueos != null) {
                        if (!menu_contraBloqueos.isVisible()) {
                            menusAux.add(menu_contraBloqueos);
                        }
                    }

                    if (menu_infoseguridad != null) {
                        if (!menu_infoseguridad.isVisible()) {
                            menusAux.add(menu_infoseguridad);
                        }
                    }

                    if (menu_recaudos != null) {
                        if (!menu_recaudos.isVisible()) {
                            menusAux.add(menu_recaudos);
                        }
                    }

                    if (menu_tdcprepago != null) {
                        if (!menu_tdcprepago.isVisible()) {
                            menusAux.add(menu_tdcprepago);
                        }
                    }

                    if (menu_puntosCol != null) {
                        if (!menu_puntosCol.isVisible()) {
                            menusAux.add(menu_puntosCol);
                        }
                    }

                    if (menu_girosNal != null) {
                        if (!menu_girosNal.isVisible()) {
                            menusAux.add(menu_girosNal);
                        }
                    }

                    if (menu_DesbloqueoAlm != null) {
                        if (!menu_DesbloqueoAlm.isVisible()) {
                            menusAux.add(menu_DesbloqueoAlm);
                        }
                    }

                    if (menu_declinacionesTdc != null) {
                        if (!menu_declinacionesTdc.isVisible()) {
                            menusAux.add(menu_declinacionesTdc);
                        }
                    }

                    if (menu_bolsillos != null) {
                        if (!menu_bolsillos.isVisible()) {
                            menusAux.add(menu_bolsillos);
                        }
                    }

                    /*if (menu_visor != null) {
                        if (!menu_visor.isVisible()) {
                            menusAux.add(menu_visor);
                        }
                    }*/

                    //PayPro
//                    if (menu_urlweb != null) {
//                        if (!menu_urlweb.isVisible()) {
//                            menusAux.add(menu_urlweb);
//                        }
//                    }
                    Menu.getPanes().removeAll(menusAux);
                    // mostrar cerrado todos los menus 
                    try {
                        Menu.getExpandedPane().setExpanded(false);
                        final ObservableList<TitledPane> panes = Menu.getPanes();
                        for (Iterator<TitledPane> it = panes.iterator(); it.hasNext();) {
                            final TitledPane PanelTitulo = it.next();
                            PanelTitulo.setExpanded(false);
                            Menu.expandedPaneProperty().setValue(PanelTitulo);
                        }
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                    /**
                     * inicializar el arbol todo habilitado
                     */
                    try {

                        MarcoPrincipalController.arbol_pagos.setDisable(false);
                        MarcoPrincipalController.arbol_pagos.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbol_saldos.setDisable(false);
                        MarcoPrincipalController.arbol_saldos.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbol_transferencias.setDisable(false);
                        MarcoPrincipalController.arbol_transferencias.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbolDesblogSegClave.setDisable(false);
                        MarcoPrincipalController.arbolDesblogSegClave.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbolbloqueos.setDisable(false);
                        MarcoPrincipalController.arbolbloqueos.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbol_servadicionales.setDisable(false);
                        MarcoPrincipalController.arbol_servadicionales.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbol_consultas.setDisable(false);
                        MarcoPrincipalController.arbol_consultas.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbol_movimientos.setDisable(false);
                        MarcoPrincipalController.arbol_movimientos.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbol_contrabloqueos.setDisable(false);
                        MarcoPrincipalController.arbol_contrabloqueos.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbol_infoseguridad.setDisable(false);
                        MarcoPrincipalController.arbol_infoseguridad.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arboltdcPrepago.setDisable(false);
                        MarcoPrincipalController.arboltdcPrepago.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbolPuntosCol.setDisable(false);
                        MarcoPrincipalController.arbolPuntosCol.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbolGirosNal.setDisable(false);
                        MarcoPrincipalController.arbolGirosNal.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbolDesbloqueoAlm.setDisable(false);
                        MarcoPrincipalController.arbolDesbloqueoAlm.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbolDeclinacionesTdc.setDisable(false);
                        MarcoPrincipalController.arbolDeclinacionesTdc.getSelectionModel().clearSelection();

                        MarcoPrincipalController.arbolBolsillos.setDisable(false);
                        MarcoPrincipalController.arbolBolsillos.getSelectionModel().clearSelection();

                        //MarcoPrincipalController.arbolVisor.setDisable(false);
                        //MarcoPrincipalController.arbolVisor.getSelectionModel().clearSelection();

                        //PayPro
//                        MarcoPrincipalController.arbolUrlWeb.setDisable(false);
//                        MarcoPrincipalController.arbolUrlWeb.getSelectionModel().clearSelection();
                    } catch (Exception e) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
                    }
                    if ("FD".equals(Cliente.getCliente().getRegla_negocio())) {
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoTarjeta()) || "N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            /// codigo para inyectar los datos                   
                            final Cliente datosCliente = Cliente.getCliente();
                            final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                            final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                            final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
                            cliente.setText("");
                            cliente.setText(info);
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoTarjeta()) || "E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            /// codigo para inyectar los datos                   
                            final Cliente datosCliente = Cliente.getCliente();
                            final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                            final String nombreC = Cliente.getCliente().getNombreEmpresa().trim().isEmpty() ? Cliente.getCliente().getNombre1() : Cliente.getCliente().getNombreEmpresa();
                            String info = "";
                            try {
                                info = nombreC + "\nNIT : " + datosCliente.getNitEmpresa() + "\n Tarjeta : ";
                                String tarjeta = Cliente.getCliente().getId_cliente().substring(0, 6) + StringUtilities.rellenarDato(" ", Cliente.getCliente().getId_cliente().length() - 10, "*") + Cliente.getCliente().getId_cliente().substring(Cliente.getCliente().getId_cliente().length() - 4, Cliente.getCliente().getId_cliente().length());
                                info = info + tarjeta;
                            } catch (Exception e) {
                                info = nombreC + "\nNIT : " + datosCliente.getNitEmpresa() + "\n Tarjeta : ";
                                String tarjeta = Cliente.getCliente().getId_cliente();
                                info = info + tarjeta;
                            }
                            cliente.setText("");
                            cliente.setText(info);
                        }
                    } else {
                        /// codigo para inyectar los datos                   
                        final Cliente datosCliente = Cliente.getCliente();
                        final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                        final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                        final String info = nombreC + "\nC.C: " + datosCliente.getId_cliente();
                        cliente.setText("");
                        cliente.setText(info);
                    }

                    // valores por default 
                    //MarcoPrincipalController.arbol_saldos.setDisable(false);
                    final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                    if ("FD".equals(Cliente.getCliente().getRegla_negocio())) {
                        if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoTarjeta()) || "N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) { //persona natural
                            if (Cliente.getCliente().getFechaExpedicion().trim().isEmpty() || Cliente.getCliente().getLugarExpedicion().trim().isEmpty()) {
                                mensaje_saldos.setText("CLIENTE SIN DATOS \nC.C " + Cliente.getCliente().getId_cliente());
                                MarcoPrincipalController.arboltdcPrepago.setDisable(true);
                            } else {
                                //final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                                mensaje_saldos.setText("");
                            }
                        } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoTarjeta()) || "E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                            if (Cliente.getCliente().getNitEmpresa().trim().isEmpty() || Cliente.getCliente().getNombreEmpresa().trim().isEmpty()) {
                                mensaje_saldos.setText("CLIENTE SIN DATOS \nNIT " + Cliente.getCliente().getNitEmpresa());
                                MarcoPrincipalController.arboltdcPrepago.setDisable(true);
                            } else {
                                //final String nombreC = Cliente.getCliente().getNombre().trim().isEmpty() ? (Cliente.getCliente().getNombre1() + " " + Cliente.getCliente().getNombre2() + " " + Cliente.getCliente().getApellido1() + " " + Cliente.getCliente().getApellido2()) : Cliente.getCliente().getNombre();
                                mensaje_saldos.setText("");
                            }
                        }
                    } else {
                        mensaje_saldos.setText(Servicio);
                    }
//                    /// BLINKING
//                    final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(final ActionEvent actionEvent) {
//                            Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
//                            cliente.setVisible(false);
//                        }
//                    }),
//                            new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent t) {
//                            Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
//                            cliente.setVisible(true);
//                        }
//                    }));
//                    timeline.setCycleCount(Animation.INDEFINITE);
//                    timeline.play();
                    pane.setMargin(mensaje_saldos, new Insets(0, 0, 0, 0));

                    try {
                        pane.getChildren().remove(3);

                    } catch (Exception e) {
                        gestorDoc.imprimir(e.getMessage() + "##" + "No hay vistas para borrar" + "##" + obtenerHoraActual());
                    }

                    Atlas.vista.show();

                } catch (final Exception ex) {
//                    ex.printStackTrace();
                    final ByteArrayOutputStream out = new ByteArrayOutputStream();
                    final PrintStream print = new PrintStream(out);
                    ex.printStackTrace(print);
                    print.flush();
                    final String Salida = new String(out.toByteArray());
                    gestorDoc.imprimir("Error en la aplicacion -->" + Salida + "  :" + gestorDoc.obtenerHoraActual());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            new ModalMensajes("Ocurrio un error en la aplicacion , Consulte con el administrador \n ", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                            try {
                                Atlas.getVista().gotoPrincipal();
                                //return;

                            } catch (IOException ex1) {
                                Logger.getLogger(SocketController.class
                                        .getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    });
                }
            }
        });
    }
}
