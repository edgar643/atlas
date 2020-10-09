/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.SocketController;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.CodigoProductos;
import com.co.allus.modelo.MenuModel;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.puntoscol.infoTablaAjustePuntos;
import com.co.allus.modelo.puntoscol.modeloListarTarjeta;
import com.co.allus.modelo.saldostdc.modelSaldoTarjeta;
import com.co.allus.modelo.tdcprepago.DatosSelectGral;
import com.co.allus.modelo.tdcprepago.ModelTdcPrepago;
import com.co.allus.modelo.tpactivacion.DatosActivacionConfirm;
import com.co.allus.modelo.tpmovimientos.DatosMovimientosFin;
import com.co.allus.modelo.tpregeneracion.DatosRegeneracionConfirm;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import com.co.allus.visor.VisorSelenium;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class MarcoPrincipalController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private GridPane Panel_datos;
    @FXML
    private Label cliente;
    @FXML
    private Label hora;
    @FXML
    private Label ip;
    @FXML
    private Label label_saldos;
    @FXML
    private Accordion menu;
    @FXML
    private GridPane pane_arriba;
    @FXML
    private Button regresar;
    @FXML
    private Pane time;
    @FXML
    private AnchorPane anchor_pagos;
    @FXML
    private AnchorPane anchor_saldos;
    @FXML
    private AnchorPane anchor_desbloqueoSClave;
    @FXML
    private AnchorPane anchor_bloqueos;
    @FXML
    private AnchorPane anchortrasnf;
    @FXML
    private AnchorPane anchor_serviciosadicionales;
    @FXML
    private AnchorPane anchor_consultas;
    @FXML
    private AnchorPane anchor_movmientos;
    @FXML
    private AnchorPane anchor_contrabloqueos;
    @FXML
    private AnchorPane anchor_infotdc;
    @FXML
    private AnchorPane anchor_infoseguridad;
    @FXML
    private AnchorPane anchor_recaudos;
    @FXML
    private AnchorPane anchor_puntosCol;
    @FXML
    private AnchorPane anchorGirosNal;
    @FXML
    private AnchorPane anchorDesbloqueoAlm;
    @FXML
    private AnchorPane anchorDeclinacionesTdc;
    @FXML
    private AnchorPane anchorBolsillos;
    //@FXML
    //private AnchorPane anchorVisor;
    //PayPro
//    @FXML
//    private AnchorPane anchorUrlWeb;
    @FXML
    public static TreeView<String> arbol_pagos;
    @FXML
    public static TreeView<String> arbol_transferencias;
    @FXML
    public static TreeView<String> arbolDesblogSegClave;
    @FXML
    public static TreeView<String> arbolbloqueos;
    @FXML
    public static TreeView<String> arbol_saldos;
    @FXML
    public static TreeView<String> arbol_servadicionales;
    @FXML
    public static TreeView<String> arbol_consultas;
    @FXML
    public static TreeView<String> arbol_contrabloqueos;
    @FXML
    public static TreeView<String> arbol_infotdc;
    @FXML
    public static TreeView<String> arbol_movimientos;
    @FXML
    public static TreeView<String> arbol_infoseguridad;
    @FXML
    public static TreeView<String> arbol_recaudos;
    @FXML
    public static TreeView<String> arboltdcPrepago;
    @FXML
    public static TreeView<String> arbolPuntosCol;
    @FXML
    public static TreeView<String> arbolGirosNal;
    @FXML
    public static TreeView<String> arbolDesbloqueoAlm;
    @FXML
    public static TreeView<String> arbolDeclinacionesTdc;
    @FXML
    public static TreeView<String> arbolBolsillos;
    //@FXML
    //public static TreeView<String> arbolVisor;

    //PayPro
//    @FXML
//    public static TreeView<String> arbolUrlWeb;
    @FXML
    public static AnchorPane anchor_tdcprepago;
    public static ObservableList<MenuModel> ListMenuSaldos = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeSaldos;
    public static ObservableList<MenuModel> ListMenuPagos = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodePagos;
    public static ObservableList<MenuModel> ListMenuTransf = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeTrasnf;
    public static ObservableList<MenuModel> ListMenuDSClaveCorp = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeDSClaveCorp;
    public static ObservableList<MenuModel> ListMenuBloqueos = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeBlqueos;
    public static ObservableList<MenuModel> ListMenuServAdicional = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeServAdicional;
    public static ObservableList<MenuModel> ListMenuConsultas = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeConsultas;
    public static ObservableList<MenuModel> ListMenuMovmientos = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeMovimientos;
    public static ObservableList<MenuModel> ListMenuContraBloqueos = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeContraBloqueos;
    public static ObservableList<MenuModel> ListMenuInfoTdc = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeInfoTdc;
    public static ObservableList<MenuModel> ListMenuInfoSeguridad = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeInfoSeguridad;
    public static ObservableList<MenuModel> ListMenuRecaudos = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeRecaudos;
    public static ObservableList<MenuModel> ListMenuTDCprepago = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeTDCprepago;
    public static ObservableList<MenuModel> ListMenuPuntosCol = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodePuntosCol;
    public static ObservableList<MenuModel> ListMenuGirosNal = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeGirosNal;
    public static ObservableList<MenuModel> ListMenuDesbloqueoAlm = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeDesbloqueoAlm;
    public static ObservableList<MenuModel> ListMenuDeclinacionesTdc = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeDeclinacionesTdc;
    public static ObservableList<MenuModel> ListMenuBolsillos = FXCollections.observableArrayList();
    public static TreeItem<String> rootNodeBolsillos;
   // public static ObservableList<MenuModel> ListMenuVisor = FXCollections.observableArrayList();
   // public static TreeItem<String> rootNodeVisor;
    //PayPro
//    public static ObservableList<MenuModel> ListMenuUrlWeb = FXCollections.observableArrayList();
//    public static TreeItem<String> rootNodeUrlWeb;
//    public static boolean newConsultaBloqTDcf = true;
//    public static boolean newConsultaBloqTDc = true;
//    public static boolean newConsultaTDc = true;
//    public static boolean newConsultaPrestamo = true;
//    public static boolean newTokenEmpresas = true;
//    public static boolean newTokenDistribuidos = true;
//    public static boolean newTokenAE = true;
//    public static boolean newComex = true;
//    public static boolean newCuentasInscritas = true;
//    public static boolean newCuentasACHInscritas = true;
//    public static boolean newBloqueoPreventivo = true;
//    public static boolean newConsultatdcempresas = true;
//    public static boolean newConsultaInfoTdc = true;
//    public static boolean newConsultaTRM = true;    
    public static boolean newConsultaBloqTDcf = true;
    public static boolean newConsultaBloqTDc = true;
    public static boolean newConsultaTDc = true;
    public static boolean newConsultaPrestamo = true;
    public static boolean newConsultaPagosT = true;
    public static boolean newTokenEmpresas = true;
    public static boolean newTokenDistribuidos = true;
    public static boolean newTokenAE = true;
    public static boolean newComex = true;
    public static boolean newCuentasInscritas = true;
    public static boolean newCuentasACHInscritas = true;
    public static boolean newBloqueoPreventivo = true;
    public static boolean newConsultatdcempresas = true;
    public static boolean newConsultaInfoTdc = true;
    public static boolean newConsultaTRM = true;
    public static boolean newConsultaDeclinacionTDC = true;
    public static boolean newConsultaBolsillosCuentas = true;
    public static boolean newConsultaBolsillosListar = true;
    public static boolean newConsultaBolsillosDetalle = true;
    public static boolean newConsultaBolsillosMovimientos = true;
    public static boolean newConsultaAvanceTdc = true;
    final GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //assert Menu_saldos != null : "fx:id=\"Menu_saldos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert Panel_datos != null : "fx:id=\"Panel_datos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchor_pagos != null : "fx:id=\"anchor_pagos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchor_saldos != null : "fx:id=\"anchor_saldos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbol_pagos != null : "fx:id=\"arbol_pagos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbol_saldos != null : "fx:id=\"arbol_saldos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert cliente != null : "fx:id=\"cliente\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert hora != null : "fx:id=\"hora\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert label_saldos != null : "fx:id=\"label_saldos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert menu != null : "fx:id=\"menu\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        //assert menu_pagos != null : "fx:id=\"menu_pagos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert pane_arriba != null : "fx:id=\"pane_arriba\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert regresar != null : "fx:id=\"regresar\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert time != null : "fx:id=\"time\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchortrasnf != null : "fx:id=\"anchortrasnf\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbol_transferencias != null : "fx:id=\"arbol_transferencias\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchor_desbloqueoSClave != null : "fx:id=\"anchor_desbloqueoSClave\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbolDesblogSegClave != null : "fx:id=\"arbolDesblogSegClave\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbolbloqueos != null : "fx:id=\"arbolbloqueos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchor_bloqueos != null : "fx:id=\"anchor_bloqueos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert ip != null : "fx:id=\"ip\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert anchor_serviciosadicionales != null : "fx:id=\"anchor_serviciosadicionales\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbol_servadicionales != null : "fx:id=\"arbol_servadicionales\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert arbol_consultas != null : "fx:id=\"arbol_consultas\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchor_consultas != null : "fx:id=\"anchor_consultas\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert anchor_movmientos != null : "fx:id=\"anchor_movmientos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbol_movimientos != null : "fx:id=\"arbol_movimientos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert arbol_contrabloqueos != null : "fx:id=\"arbol_contrabloqueos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchor_contrabloqueos != null : "fx:id=\"anchor_contrabloqueos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert anchor_infotdc != null : "fx:id=\"anchor_infotdc\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbol_infotdc != null : "fx:id=\"arbol_infotdc\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert anchor_infoseguridad != null : "fx:id=\"anchor_infoseguridad\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbol_infoseguridad != null : "fx:id=\"arbol_infoseguridad\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert anchor_recaudos != null : "fx:id=\"anchor_recaudos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbol_recaudos != null : "fx:id=\"arbol_recaudos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert anchor_tdcprepago != null : "fx:id=\"anchor_tdcprepago\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arboltdcPrepago != null : "fx:id=\"arboltdcPrepago\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert anchor_puntosCol != null : "fx:id=\"anchor_puntosCol\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert arbolPuntosCol != null : "fx:id=\"arbolPuntosCol\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert arbolGirosNal != null : "fx:id=\"arbolGirosNal\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchorGirosNal != null : "fx:id=\"anchorGirosNal\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert arbolDesbloqueoAlm != null : "fx:id=\"arbolDesbloqueoAlm\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchorDesbloqueoAlm != null : "fx:id=\"anchorDesbloqueoAlm\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert arbolDeclinacionesTdc != null : "fx:id=\"arbolDeclinacionesTdc\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchorDeclinacionesTdc != null : "fx:id=\"anchorDeclinacionesTdc\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        assert arbolBolsillos != null : "fx:id=\"arbolBolsillos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        assert anchorBolsillos != null : "fx:id=\"anchorBolsillos\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        //assert arbolVisor != null : "fx:id=\"arbolVisor\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        //assert anchorVisor != null : "fx:id=\"anchorVisor\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";

        //PayPro
//        assert arbolUrlWeb != null : "fx:id=\"arbolUrlWeb\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
//        assert anchorUrlWeb != null : "fx:id=\"anchorUrlWeb\" was not injected: check your FXML file 'MarcoPrincipal.fxml'.";
        this.location = url;
        this.resources = rb;

        arbol_saldos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbol_saldos.getSelectionModel().selectedItemProperty().addListener(eventoMenuSaldos);

        arbol_pagos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbol_pagos.getSelectionModel().selectedItemProperty().addListener(eventoMenuPagos);

        arbol_transferencias.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbol_transferencias.getSelectionModel().selectedItemProperty().addListener(eventoMenuTransfCtasprop);

//        arbolDesblogSegClave.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        arbolDesblogSegClave.getSelectionModel().selectedItemProperty().addListener(eventoMenuDesbloqSCps);
        arbolbloqueos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbolbloqueos.getSelectionModel().selectedItemProperty().addListener(eventoMenuBloqueos);

        arbol_servadicionales.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbol_servadicionales.getSelectionModel().selectedItemProperty().addListener(eventoMenuServiciosAd);

        arbol_consultas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbol_consultas.getSelectionModel().selectedItemProperty().addListener(eventoMenuConsultas);

        arbol_movimientos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbol_movimientos.getSelectionModel().selectedItemProperty().addListener(eventoMenuMovimientos);

//        arbol_contrabloqueos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        arbol_contrabloqueos.getSelectionModel().selectedItemProperty().addListener(eventoMenuContraBloqueos);
        arbol_infoseguridad.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbol_infoseguridad.getSelectionModel().selectedItemProperty().addListener(eventoMenuInfoSeguridad);

        arbol_infotdc.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbol_infotdc.getSelectionModel().selectedItemProperty().addListener(eventoMenuInfoTdc);

        arbol_recaudos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbol_recaudos.getSelectionModel().selectedItemProperty().addListener(eventoMenuRecaudos);

        arboltdcPrepago.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arboltdcPrepago.getSelectionModel().selectedItemProperty().addListener(eventoMenuTDCprepago);

        arbolPuntosCol.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbolPuntosCol.getSelectionModel().selectedItemProperty().addListener(eventoMenuPuntosCol);

        arbolGirosNal.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbolGirosNal.getSelectionModel().selectedItemProperty().addListener(eventoMenuGirosNal);

        arbolDesbloqueoAlm.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbolDesbloqueoAlm.getSelectionModel().selectedItemProperty().addListener(eventoMenuDesbloqueoAlm);

        arbolDeclinacionesTdc.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbolDeclinacionesTdc.getSelectionModel().selectedItemProperty().addListener(eventoMenuDeclinacionesTdc);

        arbolBolsillos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        arbolBolsillos.getSelectionModel().selectedItemProperty().addListener(eventoMenuBolsillos);

        //arbolVisor.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //arbolVisor.getSelectionModel().selectedItemProperty().addListener(eventoMenuVisor);

        //PayPro
//        arbolUrlWeb.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        arbolUrlWeb.getSelectionModel().selectedItemProperty().addListener(eventoMenuUrlWeb);
        try {
            InetAddress ipmaquina = InetAddress.getLocalHost();
            ip.setText(ipmaquina.toString());
        } catch (UnknownHostException ex) {
            docs.imprimir("Error en la aplicacion " + ex.toString() + "-->" + docs.obtenerHoraActual());

        }
    }

    /**
     *
     * @param event
     */
    @FXML
    void regresar(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                    VisorSelenium visor = new VisorSelenium();
                    visor.finalizarProcesoVisor();
                    if (ModalMensajes.getStage() != null) {
                        ModalMensajes.getStage().close();
                    }   
                } catch (final Exception ex) {
                    docs.imprimir("Error en la aplicacion " + ex.toString() + "-->" + docs.obtenerHoraActual());
                }
            }
        });

    }

    /**
     *
     * @param informacion_cuenta
     */
    private void modalconsultaSaldo(final String informacion_cuenta) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
//                    final URL location = getClass().getResource("/com/co/allus/vistas/ModalSaldos.fxml");
//                    final FXMLLoader fxmlLoader = new FXMLLoader();
//                    fxmlLoader.setLocation(location);
//                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
//                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());

                    /**
                     * performance optimization fxmlloader
                     */
                    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/co/allus/vistas/ModalSaldos.fxml"));
                    final AnchorPane root = (AnchorPane) fxmlLoader.load();
                    final Label infoCuenta = (Label) root.lookup("#informacion_cuenta");
                    final Label infoCosto = (Label) root.lookup("#informacion_costo");
                    infoCosto.setText("RECUERDE QUE ESTA OPERACION PUEDE TENER COSTO");
                    infoCuenta.setText(informacion_cuenta);
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                    pane.setAlignment(Pos.BOTTOM_RIGHT);
                    final TreeView<String> arbol_saldos = (TreeView<String>) pane.lookup("#arbol_saldos");
                    arbol_saldos.setDisable(true);

                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion " + ex.toString() + "-->" + docs.obtenerHoraActual());

                    }

                    pane.add(root, 1, 0);
                    // se organizan los componente en el grid
                    pane.setMargin(mensaje_saldos, new Insets(-350, 0, 0, 0));
                    pane.setMargin(root, new Insets(100, 60, 180, 70));

                    Atlas.vista.show();

                } catch (Exception ex) {
                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

                }
            }
        });

    }
    private transient final ChangeListener<TreeItem<String>> eventoMenuSaldos = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {

            try {
                if (!(nuevoValor.getParent() == null)) {
                    if (!nuevoValor.getParent().isLeaf()) {

                        if (AtlasConstantes.CREDITO_HIPOTECARIO.equalsIgnoreCase(nuevoValor.getValue())) {
                            // System.out.println("entra a mostrar menu credito hipotecario");  
                            Atlas.getVista().mostrarSaldosCreditoH(nuevoValor.getValue());
                        } else if (AtlasConstantes.MENU_TARJETA_CREDITO.equalsIgnoreCase(nuevoValor.getValue())) {

                            final ConsultaSaldoTDCController controller = new ConsultaSaldoTDCController();

                            controller.mostrarSaldosTDC(nuevoValor.getValue(), true);
                        } else if (AtlasConstantes.ITEM_SALDO_CREDIAGIL.equalsIgnoreCase(nuevoValor.getValue())) {

                            ConsultaCrediagilAFCController controller = new ConsultaCrediagilAFCController();
                            controller.mostrarConsultaCrediagilAFC();

                        } else if (AtlasConstantes.ITEM_SALDO_AGROYA.equalsIgnoreCase(nuevoValor.getValue())) {

                            ConsultaAgroYAController controller = new ConsultaAgroYAController();
                            controller.mostrarConsultaAgroYa();

                        } //                        else if (CodigoProductos.CUENTA_AFC.equalsIgnoreCase(nuevoValor.getValue())) {
                        //                            System.out.println("entra a mostrar MENU AFC");                           
                        //                            SaldoAFCfinController controller = new SaldoAFCfinController();
                        //                            controller.mostrarSaldoAFCfin(nuevoValor.getValue());
                        //
                        //                        }
                        else if (nuevoValor.getChildren().isEmpty() || nuevoValor.getChildren() == null) {
                            // System.out.println(nuevoValor.getValue());
                            // System.out.println(nuevoValor.getParent().getValue());
                            if (CodigoProductos.CUENTA_AFC.equalsIgnoreCase(nuevoValor.getParent().getValue())) {

                                SaldoAFCfinController controller = new SaldoAFCfinController();
                                controller.mostrarSaldoAFCfin(nuevoValor.getValue());

                            } else {
                                modalconsultaSaldo(nuevoValor.getParent().getValue() + " : " + nuevoValor.getValue());
                                arbol_pagos.getSelectionModel().clearSelection();
                            }
                        }

                    }

                }
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuInfoTdc = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_INFO_TDC.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    EmpresasTDCController controller = new EmpresasTDCController();
                    synchronized (this) {
                        MarcoPrincipalController.newConsultaInfoTdc = true;
                    }
                    controller.mostrarTDCEmpresas();
                } else if (AtlasConstantes.ITEM_AVANCESTDC_CTADESTINO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final AvanceTDCDestinoController controller = new AvanceTDCDestinoController();
                    controller.mostrarAvanceTDCDestino();
                } else if (AtlasConstantes.ITEM_AVANCESTDC_CTAORIGEN.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final AvanceTDCOrigenFinController controller = new AvanceTDCOrigenFinController();
                    controller.mostrarAvanceTDCOrigenFinController(0);
                }
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuPagos = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {

            try {
                // System.out.println(nuevoValor.getValue());
                if (AtlasConstantes.ITEM_PAGOS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    //Atlas.getVista().mostrarPanePagos(nuevoValor.getValue());
                    // Atlas.getVista().mostrarPanePagos(nuevoValor.getValue());
                    PagosATercerosInitController controller = new PagosATercerosInitController();
                    MarcoPrincipalController.newConsultaPagosT = true;
                    controller.mostrarDatosPagosTerceros(0);

                } else if (AtlasConstantes.ITEM_PAGOS_TDC_PROPIAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final PagosTDCpropiasController controller = new PagosTDCpropiasController();
                    //Atlas.getVista().mostrarMenuPagosTDCpropia(nuevoValor.getValue(),"");
                    controller.mostrarMenuPagosTDCpropia(nuevoValor.getValue(), new modelSaldoTarjeta("", "", ""));
                } else if (AtlasConstantes.ITEM_PAGOS_TDC_TERCEROS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    Atlas.getVista().mostrarMenuPagosTDCnoPropia(nuevoValor.getValue());
                } else if (AtlasConstantes.ITEM_PAGOS_PRESTAMOS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final PrestamosController controller = new PrestamosController();
                    controller.mostrarDatosPrestamos();
                }

                //mostrarConsultaMovimientosfin
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuTransfCtasprop = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_TRANS_CTAS_PROPIAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    Atlas.getVista().mostrarMenuTransfctasprop(nuevoValor.getValue());
                } else if (AtlasConstantes.ITEM_TRANSF_CREDIAGIL.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    //Atlas.getVista().mostrarTransfCrediagil(nuevoValor.getValue());                
                    final CrediagilController controller = new CrediagilController();
                    controller.mostrarTransfCrediagil();
                } else if (AtlasConstantes.ITEM_CTAS_INSCRITAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    //Atlas.getVista().mostrarTransfCrediagil(nuevoValor.getValue());                
                    final TransfEstadoCuentasInscritasController controller = new TransfEstadoCuentasInscritasController();
                    controller.mostrarCuentasInscritas();
                } else if (AtlasConstantes.ITEM_ELIMINACION_CTAS_INSCRITAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    //Atlas.getVista().mostrarTransfCrediagil(nuevoValor.getValue());                
                    final EliminacionCtasACHController controller = new EliminacionCtasACHController();
                    controller.mostrarELiminacionCuentasACH();
                }
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuDesbloqSCps = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                Atlas.getVista().mostrarDesbloSClaveCorp(nuevoValor.getValue());
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuBloqueos = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_BLOQUEOS_TDC.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final BloqueosTDCController controller = new BloqueosTDCController();
                    synchronized (this) {
                        // realiza consulta listar
                        MarcoPrincipalController.newConsultaBloqTDc = true;
                    }
                    controller.mostrarBloqueosTDC(nuevoValor.getValue());

                } else if (AtlasConstantes.ITEM_BLQUEOS_CLAVES_Y_TDC.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final BloqueoClavesTarjConfimController controller = new BloqueoClavesTarjConfimController();
                    controller.mostrarBlqueosClavesTDC();
                } else if (AtlasConstantes.ITEM_BLQUEOS_CLAVE_PRINCIPAL.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final BloqueoClaveppalConfirmController controller = new BloqueoClaveppalConfirmController();
                    controller.mostrarBlqueosClavePpal();
                } else if (AtlasConstantes.ITEM_BLOQUEOS_TDC_FRAUDE.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final BloqueoTDCFraudeController controller = new BloqueoTDCFraudeController();
                    controller.mostrarBloqueoTDCFraude(nuevoValor.getValue());
                } else if (AtlasConstantes.ITEM_BLOQUEO_PREVENTIVO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final AgilityBloqueoPreventivoController controller = new AgilityBloqueoPreventivoController();
                    controller.mostrarBloqueoPreventivo();
                } else if (AtlasConstantes.ITEM_CONTRA_CHEQUES.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ContraChequesController controller = new ContraChequesController();
                    controller.mostrarContraCheques();
                }

            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuServiciosAd = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {

            try {
                if (AtlasConstantes.ITEM_SERVAD_BARRIDO_CTAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ServiciosAdBarridoCtasController controller = new ServiciosAdBarridoCtasController();
                    controller.mostrarBarridoCuentas(nuevoValor.getValue());
//                } else if (AtlasConstantes.ITEM_SERVAD_CAMBIO_MECSMO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
//
//                    final EvidenteInitController controller = new EvidenteInitController();
//
////                    controller.mostrarEvidenteInit(AtlasConstantes.ITEM_SERVAD_CAMBIO_MECSMO_EVIDENTE); // cambio para flujos alternos
//
//                    // DESCOMENTARIAS
//                    controller.mostrarEvidenteInit(AtlasConstantes.ITEM_SERVAD_CAMBIO_MECSMO_EVIDENTE, AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO);
//
////                    final CambioMecanismoController MOSTRAR = new CambioMecanismoController();
////                    MOSTRAR.mostrarCambioMecanismo(AtlasConstantes.ITEM_SERVAD_CAMBIO_MECSMO_EVIDENTE,true);

                } else if (AtlasConstantes.ITEM_SERVAD_ACTUALIZACION_DATOS_SEGURIDAD.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ActualizacionDatosSeguridadController controller = new ActualizacionDatosSeguridadController();
                    controller.mostrarActualizacionDatosSeguridadController(AtlasConstantes.ITEM_SERVAD_ACTUALIZACION_DATOS_SEGURIDAD_V);

                } else if (AtlasConstantes.ITEM_CONSULTA_NOVEDADES_OTP.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ConsultaNovedadesOtpController controller = new ConsultaNovedadesOtpController();
                    controller.mostraConsultaNovedades();

                } else if (AtlasConstantes.ITEM_SERVAD_INSCRIPCION_CLAVE_DINAMICA_MENU.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    //    final EvidenteInitController controller = new EvidenteInitController();
                    //                 controller.mostrarEvidenteInit(AtlasConstantes.ITEM_SERVAD_INSCRIPCION_CLAVE_DINAMICA, AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO);
                    //quitar evidente
                    final InscripcionServicioClaveDinamicaController controller = new InscripcionServicioClaveDinamicaController();
                    controller.mostrarInscripcionServicioClaveDinamicaController(AtlasConstantes.ITEM_SERVAD_INSCRIPCION_CLAVE_DINAMICA, AtlasConstantes.INSCRIPCION_CLAVE_DINAMICA, AtlasConstantes.INSCRIPCION_CLAVE_DINAMICA_AUT_EV_NOK); // cambio para flujos alternos

                } else if (AtlasConstantes.ITEM_SERVAD_DESBLOQUEO_CLAVE_DINAMICA_MENU.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final OTPDesbloqueoClaveDinamicaIniController controller = new OTPDesbloqueoClaveDinamicaIniController();
                    controller.mostrarDesbloqueOTPIni();

                } else if (AtlasConstantes.ITEM_SERVAD_TOKEN_EMPRESAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final TokenEmpresasController controller = new TokenEmpresasController();
                    synchronized (this) {
                        // realiza consulta listar
                        MarcoPrincipalController.newTokenEmpresas = true;
                    }
                    controller.mostrarDatosToken();
                } else if (AtlasConstantes.ITEM_SERVAD_TOKEN_AE.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final TokenAccesoEmergenciaController controller = new TokenAccesoEmergenciaController();
                    synchronized (this) {
                        // realiza consulta listar
                        MarcoPrincipalController.newTokenAE = true;
                    }
                    controller.mostrarDatosTokenAE();
                } else if (AtlasConstantes.ITEM_SERVAD_TOKEN_DISTRIBUCION.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final TokenDistribucionController controller = new TokenDistribucionController();
                    synchronized (this) {
                        // realiza consulta listar
                        MarcoPrincipalController.newTokenAE = true;
                    }
                    controller.mostrarTokenDistribuidos();
               } else if (AtlasConstantes.ITEM_MOV_LIFE_COACH.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final MascaraTransaccionalController controller = new MascaraTransaccionalController("%4448%");
                    Executors.newCachedThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            controller.EnvioKbrowserMascara();
                }
                    });
                }else if (AtlasConstantes.ITEM_MOV_RECARGAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final MascaraTransaccionalController controller = new MascaraTransaccionalController("%4449%");
                    Executors.newCachedThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            controller.EnvioKbrowserMascara();
                }
                    });
                }

            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
//    private transient final ChangeListener<TreeItem<String>> eventoMenuServiciosAd = new ChangeListener<TreeItem<String>>() {
//        @Override
//        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
//
//            try {
//                if (AtlasConstantes.ITEM_SERVAD_BARRIDO_CTAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
//                    //System.out.println("ENTRO A EVENTO servicios adicionales");
//                    final ServiciosAdBarridoCtasController controller = new ServiciosAdBarridoCtasController();
//                    controller.mostrarBarridoCuentas(nuevoValor.getValue());
//                } else if (AtlasConstantes.ITEM_SERVAD_CAMBIO_MECSMO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
//                    // System.out.println("ENTRO A EVENTO cambioMecanismo");
//                    final EvidenteInitController controller = new EvidenteInitController();
//
////                    controller.mostrarEvidenteInit(AtlasConstantes.ITEM_SERVAD_CAMBIO_MECSMO_EVIDENTE); // cambio para flujos alternos
//
//                    // DESCOMENTARIAS
//                    controller.mostrarEvidenteInit(AtlasConstantes.ITEM_SERVAD_CAMBIO_MECSMO_EVIDENTE, AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO);
//
////                    final CambioMecanismoController MOSTRAR = new CambioMecanismoController();
////                    MOSTRAR.mostrarCambioMecanismo(AtlasConstantes.ITEM_SERVAD_CAMBIO_MECSMO_EVIDENTE,true);
//
//
//                } else if (AtlasConstantes.ITEM_SERVAD_ACTUALIZACION_DATOS_SEGURIDAD.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
//                    // System.out.println("ENTRO A EVENTO Actualizacion de datos de seguridad");
//                    final ActualizacionDatosSeguridadController controller = new ActualizacionDatosSeguridadController();
//                    controller.mostrarActualizacionDatosSeguridadController(AtlasConstantes.ITEM_SERVAD_ACTUALIZACION_DATOS_SEGURIDAD_V);
//                } else if (AtlasConstantes.ITEM_CONSULTA_NOVEDADES_OTP.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
//                    // System.out.println("ENTRO A EVENTO CONSULTA NOVEDADES");
//                    final ConsultaNovedadesOtpController controller = new ConsultaNovedadesOtpController();
//                    controller.mostraConsultaNovedades();
//
//                } else if (AtlasConstantes.ITEM_SERVAD_INSCRIPCION_CLAVE_DINAMICA_MENU.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
//                    //  System.out.println("ENTRO A EVENTO INSCRIPCION CLAVE DINAMICA");
//                    //PRUBA
////                    final InscripcionServicioClaveDinamicaController controller=new InscripcionServicioClaveDinamicaController();
////                    controller.mostrarInscripcionServicioClaveDinamicaController(AtlasConstantes.ITEM_SERVAD_INSCRIPCION_CLAVE_DINAMICA,AtlasConstantes.INSCRIPCION_CLAVE_DINAMICA,AtlasConstantes.INSCRIPCION_CLAVE_DINAMICA_AUT_EV_OK); // cambio para flujos alternos
//                    final EvidenteInitController controller = new EvidenteInitController();
//                    controller.mostrarEvidenteInit(AtlasConstantes.ITEM_SERVAD_INSCRIPCION_CLAVE_DINAMICA, AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO);
//                }else if (AtlasConstantes.ITEM_SERVAD_TOKEN_EMPRESAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
//                    System.out.println("ENTRO A EVENTO Token Empresas");
//                    final TokenEmpresasController controller = new TokenEmpresasController();
//                    controller.mostrarDatosToken();
//                }
//
//            } catch (Exception ex) {
//                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
//
//            }
//
//        }
//    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuConsultas = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_CONSULTAS_CDT.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ConsultaCDTController controller = new ConsultaCDTController();
                    controller.mostrarSaldosCDT(nuevoValor.getValue());

                } else if (AtlasConstantes.ITEM_REFERENCIAS_CDT.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ReferenciaCDTController controller = new ReferenciaCDTController();
                    controller.mostrarRefCDT(nuevoValor.getValue());
                } else if (AtlasConstantes.ITEM_CONSULTA_DEPOSITO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ConsultaDepositosController controller = new ConsultaDepositosController();
                    controller.mostrarCosultaDepot();
                } else if (AtlasConstantes.ITEM_CONSULTAS_TRM.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ConsultaTRMController controller = new ConsultaTRMController();
                    controller.mostrarConsultaTRM();

                    //ITEM_REGISTRAR_SERVICIO_ALERTAS
                } else if (AtlasConstantes.ITEM_REGISTRAR_SERVICIO_ALERTAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final RegistroAlertasController controller = new RegistroAlertasController();
                    controller.mostrarRegistroAlertas();

                    //ITEM_INACTIVAR_SERVICIO_ALERTAS                
                } else if (AtlasConstantes.ITEM_INACTIVAR_SERVICIO_ALERTAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final InactivarAlertasController controller = new InactivarAlertasController();
                    controller.mostrarInactivarAlertas();

                } else if (AtlasConstantes.ITEM_ALERTAS_ENVIADAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ConsultaAlertasEnvInitController controller = new ConsultaAlertasEnvInitController();
                    controller.mostrarAlertasEnviadas();
                } else if (AtlasConstantes.ITEM_PARAMETRIZACION.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final ParametrizacionAlertasConsController controller = new ParametrizacionAlertasConsController();
                    ParametrizacionAlertasConsController.parametrizacion.clear();
                    ParametrizacionAlertasConsController.parametrizacion2.clear();
                    ParametrizacionAlertasConsController.numpagina.set(0);
                    controller.mostrarAlertasEnviadas();

                } else if (AtlasConstantes.ITEM_PARAMETRIZAR_ALERTAS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final ParametrizacionAlertasController controller = new ParametrizacionAlertasController();
                    ParametrizacionAlertasController.parametrizacion.clear();
                    ParametrizacionAlertasController.parametrizacion2.clear();
                    ParametrizacionAlertasController.numpagina.set(0);
                    controller.mostrarParamAlertas();
                } else if (AtlasConstantes.ITEM_INFO_SEGURIDAD.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    InformacionSeguridadController controller = new InformacionSeguridadController();
                    controller.mostrarInfoSeguridad();
                } else if (AtlasConstantes.ITEM_CONS_PARAMETRIZACION_TOPES.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final ConsultaAlertasTopesController controller = new ConsultaAlertasTopesController();
                    controller.mostrarConsultaAlertasTopes();
                } else if (AtlasConstantes.ITEM_PARAMETRIZACION_TOPES.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final ParametrizacionAlertasTopesController controller = new ParametrizacionAlertasTopesController();
                    controller.mostrarParametrizarTopes();
                }

            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuMovimientos = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_MOV_AHORROS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ConsultaMovimientosController controller = new ConsultaMovimientosController();
                    controller.mostrarConsultaMovimientos(CodigoProductos.CUENTA_AHORROS);

                } else if (AtlasConstantes.ITEM_MOV_CORRIENTE.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ConsultaMovimientosController controller = new ConsultaMovimientosController();
                    controller.mostrarConsultaMovimientos(CodigoProductos.CUENTA_CORRIENTE);

                }

            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuContraBloqueos = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_CONTRA_CHEQUES.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final ContraChequesController controller = new ContraChequesController();
                    controller.mostrarContraCheques();

                }

            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuInfoSeguridad = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_INFO_SEGURIDAD.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    InformacionSeguridadController controller = new InformacionSeguridadController();
                    controller.mostrarInfoSeguridad();
                }

            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuRecaudos = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_RECAUDOS_MOV_PSE.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final MovRecaudosPSEinitController controller = new MovRecaudosPSEinitController();
                    controller.mostrarMovRecaudosPSE(true);
                } else if (AtlasConstantes.ITEM_RECAUDOS_MOV_DEB.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final MovDebAutIniController controller = new MovDebAutIniController();
                    controller.mostrarMovDebitoAut(true);
                } else if (AtlasConstantes.ITEM_RECAUDOS_MOV_CONV.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    ConsultaMovConvinitController controller = new ConsultaMovConvinitController();
                    controller.mostrarMovConvenio(true);
                } else if (AtlasConstantes.ITEM_RECAUDOS_PAG_DEB_AUTO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    ConsultaPagDebAutoController controller = new ConsultaPagDebAutoController();
                    controller.mostrarPagDebAuto(true);
                } else if (AtlasConstantes.ITEM_RECAUDOS_ARCH_ENV.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final ConsultaArchEnvinitController controller = new ConsultaArchEnvinitController();
                    controller.mostrarConsultaArchEnviadosInit(true);
                } else if (AtlasConstantes.ITEM_RECAUDOS_ARCH_REC.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final ConsultaArchRecinitController controller = new ConsultaArchRecinitController();
                    controller.mostrarConsultaArchRecibidosInit(true);
                }

            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuTDCprepago = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_BLOQUEO_PREPAGO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        BloqueoTDCprepIniController controller = new BloqueoTDCprepIniController();
                        controller.mostrarBloqueoTDCPrepago(null);
                    } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        BloqueoTDCPrepConfirmController controller = new BloqueoTDCPrepConfirmController();
                        ModelTdcPrepago datosTarjeta = new ModelTdcPrepago(Cliente.getCliente().getId_cliente(), "",
                                "", Cliente.getCliente().getNombreEmpresa());
                        datosTarjeta.setTarjetacf(Cliente.getCliente().getId_cliente().substring(0, 6) + StringUtilities.rellenarDato(" ", Cliente.getCliente().getId_cliente().length() - 10, "*") + Cliente.getCliente().getId_cliente().substring(Cliente.getCliente().getId_cliente().length() - 4, Cliente.getCliente().getId_cliente().length()));
                        controller.mostrarBloqueosTDCPconfirm(datosTarjeta);
                    }

                } else if (AtlasConstantes.ITEM_AUTENTICACION_PREPAGO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        final TPAutenticacionController controller = new TPAutenticacionController();
                        controller.mostrarAutPrepago();
                    } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        final TPAutenticacionEmpController controller = new TPAutenticacionEmpController();
                        controller.mostrarAutEmpPrepago();
                    }
                } else if (AtlasConstantes.ITEM_MOVIMIENTO_PREPAGO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        TPMovimientosIniController controller = new TPMovimientosIniController();
                        controller.mostrarMovimientoPrepago();

                    } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        TPMovimientosFinController controller = new TPMovimientosFinController();
                        //controller.mostrarMovimientoPrepago();
                        DatosMovimientosFin dataMF = DatosMovimientosFin.getDataMF();
                        dataMF.setNumTarj(Cliente.getCliente().getId_cliente());
                        dataMF.setNumTarjcf(Cliente.getCliente().getId_cliente().substring(0, 6) + StringUtilities.rellenarDato(" ", Cliente.getCliente().getId_cliente().length() - 10, "*") + Cliente.getCliente().getId_cliente().substring(Cliente.getCliente().getId_cliente().length() - 4, Cliente.getCliente().getId_cliente().length()));

                        DatosMovimientosFin.setDataMF(dataMF);
                        controller.mostrarMovimientosTarjetaEmpresa(DatosMovimientosFin.getDataMF());
                    }

                } else if (AtlasConstantes.ITEM_ACTIVACION_PREPAGO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        TPActivacionIniController controller = new TPActivacionIniController();
                        controller.mostrarActivacionIni();

                    } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        final TPActivacionConfirmController controller = new TPActivacionConfirmController();
                        DatosActivacionConfirm dataAct = DatosActivacionConfirm.getDataAct();
                        dataAct.setNumTarj(Cliente.getCliente().getId_cliente().substring(0, 6) + StringUtilities.rellenarDato(" ", Cliente.getCliente().getId_cliente().length() - 10, "*") + Cliente.getCliente().getId_cliente().substring(Cliente.getCliente().getId_cliente().length() - 4, Cliente.getCliente().getId_cliente().length()));
                        dataAct.setNumTarjsf(Cliente.getCliente().getId_cliente());
                        dataAct.setTipoTarj("");
                        DatosActivacionConfirm.setDataAct(dataAct);
                        controller.mostrarConfirmarActivacion(DatosActivacionConfirm.getDataAct());

                    }

                } else if (AtlasConstantes.ITEM_SOLCLAVE_PREPAGO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        TPRegeneracionController controller = new TPRegeneracionController();
                        controller.mostrarRegeneracionIni();

                    } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        final TPRegeneracionConfirmController controller = new TPRegeneracionConfirmController();
                        DatosRegeneracionConfirm dataAct = DatosRegeneracionConfirm.getDataAct();
                        dataAct.setNumTarj(Cliente.getCliente().getId_cliente().substring(0, 6) + StringUtilities.rellenarDato(" ", Cliente.getCliente().getId_cliente().length() - 10, "*") + Cliente.getCliente().getId_cliente().substring(Cliente.getCliente().getId_cliente().length() - 4, Cliente.getCliente().getId_cliente().length()));
                        dataAct.setNumTarjsf(Cliente.getCliente().getId_cliente());
                        dataAct.setTipoTarj("");
                        DatosRegeneracionConfirm.setDataAct(dataAct);
                        controller.mostrarConfirmarRegeneracion(DatosRegeneracionConfirm.getDataAct());

                    }

                } else if (AtlasConstantes.ITEM_CONSULTAS_PREPAGO.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    if ("N".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {

                        TPConsultaGeneralController controller = new TPConsultaGeneralController();
                        controller.mostrarConsultaGral();

                    } else if ("E".equalsIgnoreCase(Cliente.getCliente().getTipoClientePrepago())) {
                        final TPConsultaGeneralFinController controller = new TPConsultaGeneralFinController();
                        DatosSelectGral datosGral = DatosSelectGral.getDataGral();
                        datosGral.setNumTarj(Cliente.getCliente().getId_cliente().substring(0, 6) + StringUtilities.rellenarDato(" ", Cliente.getCliente().getId_cliente().length() - 10, "*") + Cliente.getCliente().getId_cliente().substring(Cliente.getCliente().getId_cliente().length() - 4, Cliente.getCliente().getId_cliente().length()));
                        datosGral.setNumTarjsf(Cliente.getCliente().getId_cliente());
                        datosGral.setTipoTarj(Cliente.getCliente().getTipoClientePrepago());

                        DatosSelectGral.setDataGral(datosGral);
                        controller.mostrarConsultaGralFinTarjeta(DatosSelectGral.getDataGral());

                    }
                }

            } catch (Exception ex) {
                docs.imprimir("Advertencia la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuPuntosCol = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_SALDO_PUNTOS_COL.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final SaldoPuntosColController controller = new SaldoPuntosColController();
                    controller.mostrarSaldosPuntosCol();
                } else if (AtlasConstantes.ITEM_PUNTOS_MIGRACION_COL.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final PuntosMigradosColController controller = new PuntosMigradosColController();
                    controller.mostrarPuntosColMigrados();
                } else if (AtlasConstantes.ITEM_MOVIMIENTOS_PUNTOS_COL.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final PuntosColListarTarjetasController controller = new PuntosColListarTarjetasController();
                    ObservableList<modeloListarTarjeta> emptyObservableList = FXCollections.emptyObservableList();
                    controller.mostrarListarTarjetaPCO(emptyObservableList, "N", 0, 1);
                } else if (AtlasConstantes.ITEM_AJUSTE_PUNTOS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final PuntosColAjustePuntosController controller = new PuntosColAjustePuntosController();
                    ObservableList<infoTablaAjustePuntos> emptyObservableList = FXCollections.emptyObservableList();
                    controller.mostrarPuntosColAjuste(emptyObservableList, "N", 0, 1);
                }

            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuGirosNal = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_GIROS_NAL.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final GiroNalInitController controller = new GiroNalInitController();
                    controller.mostrarGirosInit();

                } else if (AtlasConstantes.ITEM_GIROS_INFO_GENERAL.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {

                    final GirosNalInfoGeneralController controller = new GirosNalInfoGeneralController();
                    controller.mostrarGirosNalInfoGnral(0);

                } else if (AtlasConstantes.ITEM_GIROS_CANCELACION.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final GirosNalCancelacionController controller = new GirosNalCancelacionController();
                    controller.mostrarGirosNalCancelar(0);
                }

            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());

            }

        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuDesbloqueoAlm = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_DESBLOQUEO_ALM.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final DesbloqueoALMController controller = new DesbloqueoALMController();
                    controller.mostrarDesbloqueoALM();
                } else if (AtlasConstantes.ITEM_LOG_ALM.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final ALMLogVinculacionController controller = new ALMLogVinculacionController();
                    controller.mostrarALMLogVinculacion();
                }
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
            }
        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuDeclinacionesTdc = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_DECLINACIONES_TDC.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final DeclinacionesTDCController controller = new DeclinacionesTDCController();
                    controller.mostrarDeclinacionesTdc(nuevoValor.getValue(), true);
                }
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
            }
        }
    };
    private transient final ChangeListener<TreeItem<String>> eventoMenuBolsillos = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
                if (AtlasConstantes.ITEM_BOLSILLOS.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    final BolsillosCuentasController controller = new BolsillosCuentasController();
                    controller.mostrarCuentasBolsillo(nuevoValor.getValue(), true);
                }
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
            }
        }
    };

  /* private transient final ChangeListener<TreeItem<String>> eventoMenuVisor = new ChangeListener<TreeItem<String>>() {
        @Override
        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
            try {
               
                if (AtlasConstantes.ITEM_VISOR.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
                    //Aqui instancias el metodo para abrir la URL, toma como ejemplo la de PAYPRO que es la que sigue.

                    VisorSelenium visor = new VisorSelenium();
                    visor.visorStart(Cliente.getCliente().getId_cliente(), Cliente.getCliente().getVv_Tipide());

                }
            } catch (Exception ex) {
                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
            }
        }
    };*/
    //PayPro
//    private transient final ChangeListener<TreeItem<String>> eventoMenuUrlWeb = new ChangeListener<TreeItem<String>>() {
//        @Override
//        public void changed(final ObservableValue<? extends TreeItem<String>> obsv, final TreeItem<String> viejovalor, final TreeItem<String> nuevoValor) {
//            try {
//                if (AtlasConstantes.ITEM_PAYPAL_RAM.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
//                    final AutomatizacionRAM_MP controller = new AutomatizacionRAM_MP();
//                    controller.AbrirRAM();
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.currentThread().sleep(3000);
//                            } catch (InterruptedException ex) {
//                                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            arbolUrlWeb.getSelectionModel().clearSelection();
//                        }
//                    });
//                } else if (AtlasConstantes.ITEM_PAYPAL_MP.trim().equalsIgnoreCase(nuevoValor.getValue().trim())) {
//                    final AutomatizacionRAM_MP controller = new AutomatizacionRAM_MP();
//                    controller.AbrirMP();
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.currentThread().sleep(3000);
//                            } catch (InterruptedException ex) {
//                                Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            arbolUrlWeb.getSelectionModel().clearSelection();
//                        }
//                    });
//                }
//            } catch (Exception ex) {
//                docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
//            }
//        }
//    };
}
