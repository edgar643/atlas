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
import com.co.allus.modelo.SaldoAFC.Infodet1;
import com.co.allus.modelo.SaldoAFC.Infodet2;
import com.co.allus.modelo.SaldoAFC.Infodet3;
import com.co.allus.modelo.SaldoAFC.Infodet4;
import com.co.allus.modelo.SaldoAFC.infoTablaSaldoAFC;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.ResourceBundle;
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
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
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
 * @author alexander.lopez.o
 */
public class SaldoAFCfinController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<Infodet1, String> descripcion1;
    @FXML
    private TableColumn<Infodet2, String> descripcion2;
    @FXML
    private TableColumn<Infodet3, String> descripcion3;
    @FXML
    private TableColumn<Infodet4, String> descripcion4;
    @FXML
    private TableColumn<Infodet1, String> informacion1;
    @FXML
    private TableColumn<Infodet2, String> informacion2;
    @FXML
    private TableColumn<Infodet3, String> informacion3;
    @FXML
    private TableColumn<Infodet4, String> informacion4;
    @FXML
    private TableView<Infodet1> tabla_datos1;
    @FXML
    private TableView<Infodet2> tabla_datos2;
    @FXML
    private TableView<Infodet3> tabla_datos3;
    @FXML
    private TableView<Infodet4> tabla_datos4;
    @FXML
    private Button terminar_trx;
    @FXML
    private Button volver_op;
    @FXML
    private transient StackPane panel_tabla;
    private static GestorDocumental docs = new GestorDocumental();
    private Service<infoTablaSaldoAFC> task = new SaldoAFCfinController.getSaldoAFCtask();
    private transient final DecimalFormat formatonum = new DecimalFormat("#,###,###,###");
    private static String numCta = "";
    public static AtomicBoolean cancelOP = new AtomicBoolean();

    public static String getNumCta() {
        return numCta;
    }

    public static void setNumCta(String numCta) {
        SaldoAFCfinController.numCta = numCta;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert descripcion1 != null : "fx:id=\"descripcion1\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert descripcion2 != null : "fx:id=\"descripcion2\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert descripcion3 != null : "fx:id=\"descripcion3\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert descripcion4 != null : "fx:id=\"descripcion4\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert informacion1 != null : "fx:id=\"informacion1\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert informacion2 != null : "fx:id=\"informacion2\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert informacion3 != null : "fx:id=\"informacion3\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert informacion4 != null : "fx:id=\"informacion4\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert tabla_datos1 != null : "fx:id=\"tabla_datos1\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert tabla_datos2 != null : "fx:id=\"tabla_datos2\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert tabla_datos3 != null : "fx:id=\"tabla_datos3\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert tabla_datos4 != null : "fx:id=\"tabla_datos4\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert volver_op != null : "fx:id=\"volver_op\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'SaldoAFCfin.fxml'.";

        this.location = url;
        this.resources = rb;

        tabla_datos1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_datos2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_datos3.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla_datos4.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        descripcion1.setCellValueFactory(new PropertyValueFactory<Infodet1, String>("descripcion"));
        informacion1.setCellValueFactory(new PropertyValueFactory<Infodet1, String>("informacion"));
        descripcion2.setCellValueFactory(new PropertyValueFactory<Infodet2, String>("descripcion2"));
        informacion2.setCellValueFactory(new PropertyValueFactory<Infodet2, String>("informacion2"));
        descripcion3.setCellValueFactory(new PropertyValueFactory<Infodet3, String>("descripcion3"));
        informacion3.setCellValueFactory(new PropertyValueFactory<Infodet3, String>("informacion3"));
        descripcion4.setCellValueFactory(new PropertyValueFactory<Infodet4, String>("descripcion4"));
        informacion4.setCellValueFactory(new PropertyValueFactory<Infodet4, String>("informacion4"));

        cancelOP.set(false);


    }

    public void mostrarSaldoAFCfin(final String tituloCTA) {
        this.setNumCta(tituloCTA);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/SaldoAFCfin.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final StackPane panel_tabla = (StackPane) root.lookup("#panel_tabla");
                    final Button terminar_trx = (Button) root.lookup("#terminar_trx");
                    final Button volver_op = (Button) root.lookup("#volver_op");



                    final Accordion Menu = (Accordion) root.lookup("#menu");
                    final TableView<com.co.allus.modelo.SaldoAFC.Infodet1> tablaData1 = (TableView<com.co.allus.modelo.SaldoAFC.Infodet1>) Menu.getPanes().get(0).getContent().lookup("#tabla_datos1");
                    final TableView<com.co.allus.modelo.SaldoAFC.Infodet2> tablaData2 = (TableView<com.co.allus.modelo.SaldoAFC.Infodet2>) Menu.getPanes().get(1).getContent().lookup("#tabla_datos2");
                    final TableView<com.co.allus.modelo.SaldoAFC.Infodet3> tablaData3 = (TableView<com.co.allus.modelo.SaldoAFC.Infodet3>) Menu.getPanes().get(2).getContent().lookup("#tabla_datos3");
                    final TableView<com.co.allus.modelo.SaldoAFC.Infodet4> tablaData4 = (TableView<com.co.allus.modelo.SaldoAFC.Infodet4>) Menu.getPanes().get(3).getContent().lookup("#tabla_datos4");


                    /// codigo para inyectar los datos                   
                    final Cliente datosCliente = Cliente.getCliente();
                    final Label cliente = (Label) Atlas.vista.getScene().getRoot().lookup("#cliente");
                    final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente() + "\nCuenta: " + tituloCTA;
                    cliente.setText("");
                    cliente.setText(info);


                    Region veil = new Region();
                    veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                    ProgressIndicator p = new ProgressIndicator();
                    p.setMaxSize(150, 150);
                    panel_tabla.getChildren().addAll(veil, p);


                    p.progressProperty().bind(task.progressProperty());
                    veil.visibleProperty().bind(task.runningProperty());
                    p.visibleProperty().bind(task.runningProperty());
                    task.start();

                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {

                            final ObservableList<Infodet1> dataTabla1 = FXCollections.observableArrayList();
                            final StringBuilder RazonesBloqueo = new StringBuilder();
                            String[] razonBloqueo = infoTablaSaldoAFC.getInfoAFC().getRazonBloqueo().split("\\|");
                            //int cont=1;
                            for (int i = 0; i < razonBloqueo.length; i++) {

                                // RazonesBloqueo.append(cont++);                     
                                // RazonesBloqueo.append(". ");
                                RazonesBloqueo.append(razonBloqueo[i]);
                                if (!(i == razonBloqueo.length - 1)) {
                                    RazonesBloqueo.append("\n");
                                }

                            }

                            dataTabla1.add(new Infodet1(infoTablaSaldoAFC.RAZONBLOQUEO, RazonesBloqueo.toString()));

                            tablaData1.setItems(dataTabla1);

                            final ObservableList<Infodet2> dataTabla2 = FXCollections.observableArrayList();

                            String ahorroDisBenef = infoTablaSaldoAFC.getInfoAFC().getAhorroDisBenef();
                            if (ahorroDisBenef.isEmpty()) {
                                ahorroDisBenef = "";

                            } else {

                                ahorroDisBenef = "$ " + formatonum.format(Double.parseDouble(ahorroDisBenef.substring(0, ahorroDisBenef.length() - 2))).replace(",", ".") + "," + ahorroDisBenef.substring(ahorroDisBenef.length() - 2, ahorroDisBenef.length());

                            }
                            dataTabla2.add(new Infodet2(infoTablaSaldoAFC.AHODISPBNF, ahorroDisBenef));


                            String ahorrosinBenef = infoTablaSaldoAFC.getInfoAFC().getAhorrosinBenef();
                            if (ahorrosinBenef.isEmpty()) {
                                ahorrosinBenef = "";

                            } else {

                                ahorrosinBenef = "$ " + formatonum.format(Double.parseDouble(ahorrosinBenef.substring(0, ahorrosinBenef.length() - 2))).replace(",", ".") + "," + ahorrosinBenef.substring(ahorrosinBenef.length() - 2, ahorrosinBenef.length());

                            }
                            dataTabla2.add(new Infodet2(infoTablaSaldoAFC.AHOSINBNF, ahorrosinBenef));
                            String valorBenef = infoTablaSaldoAFC.getInfoAFC().getValorBenef();
                            if (valorBenef.isEmpty()) {
                                valorBenef = "";

                            } else {

                                valorBenef = "$ " + formatonum.format(Double.parseDouble(valorBenef.substring(0, valorBenef.length() - 2))).replace(",", ".") + "," + valorBenef.substring(valorBenef.length() - 2, valorBenef.length());

                            }
                            dataTabla2.add(new Infodet2(infoTablaSaldoAFC.VALBNF, valorBenef));

                            String beneficioGanado = infoTablaSaldoAFC.getInfoAFC().getBeneficioGanado();
                            if (beneficioGanado.isEmpty()) {
                                beneficioGanado = "";

                            } else {

                                beneficioGanado = "$ " + formatonum.format(Double.parseDouble(beneficioGanado.substring(0, beneficioGanado.length() - 2))).replace(",", ".") + "," + beneficioGanado.substring(beneficioGanado.length() - 2, beneficioGanado.length());

                            }
                            dataTabla2.add(new Infodet2(infoTablaSaldoAFC.BNFGANADO, beneficioGanado));

                            String beneficioPerdido = infoTablaSaldoAFC.getInfoAFC().getBeneficioPerdido();
                            if (beneficioPerdido.isEmpty()) {
                                beneficioPerdido = "";

                            } else {

                                beneficioPerdido = "$ " + formatonum.format(Double.parseDouble(beneficioPerdido.substring(0, beneficioPerdido.length() - 2))).replace(",", ".") + "," + beneficioPerdido.substring(beneficioPerdido.length() - 2, beneficioPerdido.length());

                            }
                            dataTabla2.add(new Infodet2(infoTablaSaldoAFC.BNFPERDIDO, beneficioPerdido));

                            String rendimientos = infoTablaSaldoAFC.getInfoAFC().getRendimientos();
                            if (rendimientos.isEmpty()) {
                                rendimientos = "";

                            } else {

                                rendimientos = "$ " + formatonum.format(Double.parseDouble(rendimientos.substring(0, rendimientos.length() - 2))).replace(",", ".") + "," + rendimientos.substring(rendimientos.length() - 2, rendimientos.length());

                            }
                            dataTabla2.add(new Infodet2(infoTablaSaldoAFC.RENDIMIENTOS, rendimientos));

                            String saldocanje = infoTablaSaldoAFC.getInfoAFC().getSaldocanje();
                            if (saldocanje.isEmpty()) {
                                saldocanje = "";

                            } else {

                                saldocanje = "$ " + formatonum.format(Double.parseDouble(saldocanje.substring(0, saldocanje.length() - 2))).replace(",", ".") + "," + saldocanje.substring(saldocanje.length() - 2, saldocanje.length());

                            }
                            dataTabla2.add(new Infodet2(infoTablaSaldoAFC.SALDOCANJE, saldocanje));

                            String valorcongelado2344 = infoTablaSaldoAFC.getInfoAFC().getValorcongelado2344();
                            if (valorcongelado2344.isEmpty()) {
                                valorcongelado2344 = "";

                            } else {

                                valorcongelado2344 = "$ " + formatonum.format(Double.parseDouble(valorcongelado2344.substring(0, valorcongelado2344.length() - 2))).replace(",", ".") + "," + valorcongelado2344.substring(valorcongelado2344.length() - 2, valorcongelado2344.length());

                            }
                            dataTabla2.add(new Infodet2(infoTablaSaldoAFC.VALORCONG2344, valorcongelado2344));

                            String valorRetirosTram = infoTablaSaldoAFC.getInfoAFC().getValorRetirosTram();
                            if (valorRetirosTram.isEmpty()) {
                                valorRetirosTram = "";

                            } else {

                                valorRetirosTram = "$ " + formatonum.format(Double.parseDouble(valorRetirosTram.substring(0, valorRetirosTram.length() - 2))).replace(",", ".") + "," + valorRetirosTram.substring(valorRetirosTram.length() - 2, valorRetirosTram.length());

                            }
                            dataTabla2.add(new Infodet2(infoTablaSaldoAFC.VALORRETIROTRAM, valorRetirosTram));


                            tablaData2.setItems(dataTabla2);

                            final ObservableList<Infodet3> dataTabla3 = FXCollections.observableArrayList();

                            String saldoDispVivienda = infoTablaSaldoAFC.getInfoAFC().getSaldoDispVivienda();
                            if (saldoDispVivienda.isEmpty()) {
                                saldoDispVivienda = "";

                            } else {

                                saldoDispVivienda = "$ " + formatonum.format(Double.parseDouble(saldoDispVivienda.substring(0, saldoDispVivienda.length() - 2))).replace(",", ".") + "," + saldoDispVivienda.substring(saldoDispVivienda.length() - 2, saldoDispVivienda.length());

                            }
                            dataTabla3.add(new Infodet3(infoTablaSaldoAFC.SALDODISPVIVNDA, saldoDispVivienda));


                            String gmfRetiroVivienda = infoTablaSaldoAFC.getInfoAFC().getGmfRetiroVivienda();
                            if (gmfRetiroVivienda.isEmpty()) {
                                gmfRetiroVivienda = "";

                            } else {

                                gmfRetiroVivienda = "$ " + formatonum.format(Double.parseDouble(gmfRetiroVivienda.substring(0, gmfRetiroVivienda.length() - 2))).replace(",", ".") + "," + gmfRetiroVivienda.substring(gmfRetiroVivienda.length() - 2, gmfRetiroVivienda.length());

                            }
                            dataTabla3.add(new Infodet3(infoTablaSaldoAFC.GMFRETIROVIVNDA, gmfRetiroVivienda));


                            String benefGanarVivienda = infoTablaSaldoAFC.getInfoAFC().getBenefGanarVivienda();
                            if (benefGanarVivienda.isEmpty()) {
                                benefGanarVivienda = "";

                            } else {

                                benefGanarVivienda = "$ " + formatonum.format(Double.parseDouble(benefGanarVivienda.substring(0, benefGanarVivienda.length() - 2))).replace(",", ".") + "," + benefGanarVivienda.substring(benefGanarVivienda.length() - 2, benefGanarVivienda.length());

                            }
                            dataTabla3.add(new Infodet3(infoTablaSaldoAFC.BNFGANARVIVNDA, benefGanarVivienda));

                            String benefPerdidogmf = infoTablaSaldoAFC.getInfoAFC().getBenefPerdidogmf();
                            if (benefPerdidogmf.isEmpty()) {
                                benefPerdidogmf = "";

                            } else {

                                benefPerdidogmf = "$ " + formatonum.format(Double.parseDouble(benefPerdidogmf.substring(0, benefPerdidogmf.length() - 2))).replace(",", ".") + "," + benefPerdidogmf.substring(benefPerdidogmf.length() - 2, benefPerdidogmf.length());

                            }
                            dataTabla3.add(new Infodet3(infoTablaSaldoAFC.BNFPERDIDOGMF, benefPerdidogmf));

                            String ahorro2344 = infoTablaSaldoAFC.getInfoAFC().getAhorro2344();
                            if (ahorro2344.isEmpty()) {
                                ahorro2344 = "";

                            } else {

                                ahorro2344 = "$ " + formatonum.format(Double.parseDouble(ahorro2344.substring(0, ahorro2344.length() - 2))).replace(",", ".") + "," + ahorro2344.substring(ahorro2344.length() - 2, ahorro2344.length());

                            }
                            dataTabla3.add(new Infodet3(infoTablaSaldoAFC.AHO2344, ahorro2344));

                            String gmfVivienda2344 = infoTablaSaldoAFC.getInfoAFC().getGmfVivienda2344();
                            if (gmfVivienda2344.isEmpty()) {
                                gmfVivienda2344 = "";

                            } else {

                                gmfVivienda2344 = "$ " + formatonum.format(Double.parseDouble(gmfVivienda2344.substring(0, gmfVivienda2344.length() - 2))).replace(",", ".") + "," + gmfVivienda2344.substring(gmfVivienda2344.length() - 2, gmfVivienda2344.length());

                            }
                            dataTabla3.add(new Infodet3(infoTablaSaldoAFC.GMFVIVNDA2344, gmfVivienda2344));

                            String benefPerdido2344gmf = infoTablaSaldoAFC.getInfoAFC().getBenefPerdido2344gmf();
                            if (benefPerdido2344gmf.isEmpty()) {
                                benefPerdido2344gmf = "";

                            } else {

                                benefPerdido2344gmf = "$ " + formatonum.format(Double.parseDouble(benefPerdido2344gmf.substring(0, benefPerdido2344gmf.length() - 2))).replace(",", ".") + "," + benefPerdido2344gmf.substring(benefPerdido2344gmf.length() - 2, benefPerdido2344gmf.length());

                            }
                            dataTabla3.add(new Infodet3(infoTablaSaldoAFC.BNFPERDIDO2344GMF, benefPerdido2344gmf));

                            String benefCongelar2344 = infoTablaSaldoAFC.getInfoAFC().getBenefCongelar2344();
                            if (benefCongelar2344.isEmpty()) {
                                benefCongelar2344 = "";

                            } else {

                                benefCongelar2344 = "$ " + formatonum.format(Double.parseDouble(benefCongelar2344.substring(0, benefCongelar2344.length() - 2))).replace(",", ".") + "," + benefCongelar2344.substring(benefCongelar2344.length() - 2, benefCongelar2344.length());

                            }
                            dataTabla3.add(new Infodet3(infoTablaSaldoAFC.BNFCONG2344, benefCongelar2344));


                            tablaData3.setItems(dataTabla3);


                            final ObservableList<Infodet4> dataTabla4 = FXCollections.observableArrayList();


                            String saldoDispNoVivienda = infoTablaSaldoAFC.getInfoAFC().getSaldoDispNoVivienda();
                            if (saldoDispNoVivienda.isEmpty()) {
                                saldoDispNoVivienda = "";

                            } else {

                                saldoDispNoVivienda = "$ " + formatonum.format(Double.parseDouble(saldoDispNoVivienda.substring(0, saldoDispNoVivienda.length() - 2))).replace(",", ".") + "," + saldoDispNoVivienda.substring(saldoDispNoVivienda.length() - 2, saldoDispNoVivienda.length());

                            }
                            dataTabla4.add(new Infodet4(infoTablaSaldoAFC.SALDODISPNOVIVNDA, saldoDispNoVivienda));

                            String benefPerderNoVivienda = infoTablaSaldoAFC.getInfoAFC().getBenefPerderNoVivienda();
                            if (benefPerderNoVivienda.isEmpty()) {
                                benefPerderNoVivienda = "";

                            } else {

                                benefPerderNoVivienda = "$ " + formatonum.format(Double.parseDouble(benefPerderNoVivienda.substring(0, benefPerderNoVivienda.length() - 2))).replace(",", ".") + "," + benefPerderNoVivienda.substring(benefPerderNoVivienda.length() - 2, benefPerderNoVivienda.length());

                            }
                            dataTabla4.add(new Infodet4(infoTablaSaldoAFC.BNFPERDERNOVIVNDA, benefPerderNoVivienda));

                            String ahorro2344NoBenef = infoTablaSaldoAFC.getInfoAFC().getAhorro2344NoBenef();
                            if (ahorro2344NoBenef.isEmpty()) {
                                ahorro2344NoBenef = "";

                            } else {

                                ahorro2344NoBenef = "$ " + formatonum.format(Double.parseDouble(ahorro2344NoBenef.substring(0, ahorro2344NoBenef.length() - 2))).replace(",", ".") + "," + ahorro2344NoBenef.substring(ahorro2344NoBenef.length() - 2, ahorro2344NoBenef.length());

                            }
                            dataTabla4.add(new Infodet4(infoTablaSaldoAFC.AHO2344NOBNF, ahorro2344NoBenef));

                            String benefPerder2344 = infoTablaSaldoAFC.getInfoAFC().getBenefPerder2344();
                            if (benefPerder2344.isEmpty()) {
                                benefPerder2344 = "";

                            } else {

                                benefPerder2344 = "$ " + formatonum.format(Double.parseDouble(benefPerder2344.substring(0, benefPerder2344.length() - 2))).replace(",", ".") + "," + benefPerder2344.substring(benefPerder2344.length() - 2, benefPerder2344.length());

                            }
                            dataTabla4.add(new Infodet4(infoTablaSaldoAFC.BNFPERDER2344, benefPerder2344));


                            String gmfRetiroNoVivnda = infoTablaSaldoAFC.getInfoAFC().getGmfRetiroNoVivnda();
                            if (gmfRetiroNoVivnda.isEmpty()) {
                                gmfRetiroNoVivnda = "";

                            } else {

                                gmfRetiroNoVivnda = "$ " + formatonum.format(Double.parseDouble(gmfRetiroNoVivnda.substring(0, gmfRetiroNoVivnda.length() - 2))).replace(",", ".") + "," + gmfRetiroNoVivnda.substring(gmfRetiroNoVivnda.length() - 2, gmfRetiroNoVivnda.length());

                            }
                            dataTabla4.add(new Infodet4(infoTablaSaldoAFC.GMFRETIRONOVIVNDA, gmfRetiroNoVivnda));

                            tablaData4.setItems(dataTabla4);

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

                                    final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                                    if (arbol_servadd != null) {
                                        arbol_servadd.setDisable(false);
                                    }



                                    arbol_saldos.getSelectionModel().clearSelection();
                                    try {
                                        pane.getChildren().remove(3);
                                    } catch (Exception e) {
                                        docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                                    }
                                    Atlas.vista.show();
                                }
                            });


                        }
                    });

                    task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            docs.imprimir("Error en la aplicacion -->" + t.getSource().getException().getMessage());
                        }
                    });



                    final DropShadow shadow = new DropShadow();
                    terminar_trx.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminar_trx.setCursor(Cursor.HAND);
                                    terminar_trx.setEffect(shadow);
                                }
                            });

                    terminar_trx.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    terminar_trx.setCursor(Cursor.DEFAULT);
                                    terminar_trx.setEffect(null);

                                }
                            });

                    volver_op.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volver_op.setCursor(Cursor.HAND);
                                    volver_op.setEffect(shadow);
                                }
                            });

                    volver_op.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volver_op.setCursor(Cursor.DEFAULT);
                                    volver_op.setEffect(null);

                                }
                            });

                    try {
                        Menu.getExpandedPane().setExpanded(false);
                        final ObservableList<TitledPane> panes = Menu.getPanes();
                        for (Iterator<TitledPane> it = panes.iterator(); it.hasNext();) {
                            final TitledPane PanelTitulo = it.next();
                            PanelTitulo.setExpanded(false);
                            Menu.expandedPaneProperty().setValue(PanelTitulo);

                        }
                    } catch (Exception e) {
                        docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                    }


                    try {
                        pane.getChildren().remove(3);
                    } catch (Exception e) {
                        docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                    }

                    pane.setAlignment(Pos.CENTER_LEFT);
                    pane.add(root, 1, 0);
                    Atlas.vista.show();
                } catch (Exception ex) {
//                    ex.printStackTrace();

                    docs.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + docs.obtenerHoraActual());
                    new ModalMensajes("Error en la aplicacion \n , es posible que el  pago se haya realizado "
                            + "correctamente , por favor no volver a intertalo e informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }


            }
        });

    }

    public class getSaldoAFCtask extends Service<infoTablaSaldoAFC> {

        @Override
        protected Task<infoTablaSaldoAFC> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    String Respuesta = new String();
                    final StringBuilder tramaSaldoAFC = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();
                    final Cliente cliente = Cliente.getCliente();

                    tramaSaldoAFC.append("850,058,");
                    tramaSaldoAFC.append(cliente.getRefid());
                    tramaSaldoAFC.append(",");
                    tramaSaldoAFC.append(AtlasConstantes.COD_SALDO_AFC);
                    tramaSaldoAFC.append(",");
                    tramaSaldoAFC.append(cliente.getContraseña().trim().isEmpty() ? "T" : "S");
                    tramaSaldoAFC.append(",");
                    tramaSaldoAFC.append(cliente.getId_cliente());
                    tramaSaldoAFC.append(",");
                    tramaSaldoAFC.append(getNumCta());
                    tramaSaldoAFC.append(",");
                    tramaSaldoAFC.append(cliente.getContraseña());
                    tramaSaldoAFC.append(",");
                    tramaSaldoAFC.append(cliente.getTokenOauth());
                    tramaSaldoAFC.append(",~");

//                    System.out.println("TRAMA SALDO AFC :" + tramaSaldoAFC);



                    try {
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ saldo afc = " + "##" + docs.obtenerHoraActual());

                        //850,058,000,TRANSACCION EXITOSA,valorahoBenef,RendimientoBeneficio,valorBeneficio,valorahosinbenef,valorcongelado,valorbeneficioganado,valorbeneficioperdido,valorSaldoCanje,saldodispVivienda,beneficioGanVivienda,GMretiroVivienda,BeneficioTributarioGMF,valorahorDec2344,valorbenefDecrt2344,gmfvivienda2344,beneficiotributario2344,saldoDispDiferentVivi,BeneficioDiferenteVivienda,GmfperdidoDifVivi,valorAhorro2344sinBenef,valorbenefPerdio2344,retirosEntramite,RazonBloqueo,~

//                        Respuesta = "850,058,000,TRANSACCION EXITOSA,"
//                                + "-6400000000,"
//                                + "01200000000,"
//                                + "01500000000,"
//                                + "01500000000,"
//                                + "0800000000,"
//                                + "0100000000,"
//                                + "0200000000,"
//                                + "0100000000,"
//                                + "01200000000,"
//                                + "1300000000,"
//                                + "1700000000,"
//                                + "90000000,"
//                                + "0400000000,"
//                                + "-200000000,"
//                                + "01000000,"
//                                + "0100000000,"
//                                + "04500000000,"
//                                + "01800000000,"
//                                + "06000000,"
//                                + "1500000000,"
//                                + "55000000,"
//                                + "63000000,"
//                                + "Embargo|Investigacion|Fallecido|Otros,~";

                        //                                    Respuesta="850,058,000,TRANSACCION EXITOSA,"
//                                    + "valorahoBenef,"
//                                    + "RendimientoBeneficio,"
                        //+"valorbeneficio"
//                                    + "valorahosinbenef,"
//                                    + "valorcongelado2344,"
//                                    + "valorbeneficioganado,"
//                                    + "valorbeneficioperdido,"
//                                    + "valorSaldoCanje,"
//                                    + "saldodispVivienda,"
//                                    + "beneficioGanVivienda,"
//                                    + "GMretiroVivienda,"
//                                    + "BeneficioTributarioGMF,"
//                                    + "valorahorDec2344,"
//                                    + "valorbenefDecrt2344,"
//                                    + "gmfvivienda2344,"
//                                    + "beneficiotributarioperdido2344,"
//                                    + "saldoDispDiferentVivi,"
//                                    + "BeneficioDiferenteVivienda,"
//                                    + "GmfperdidoDifVivi,"
//                                    + "valorAhorro2344sinBenef,"
//                                    + "valorbenefPerdio2344,"
//                                    + "retirosEntramite,"
//                                    + "RazonBloqueo,~";


                        // Respuesta="850,058,000,,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,00000000000000000,Fallecido|Investigación|Razon00000000000000000000000000000000001|Razon00000000000000000000000000000000002|Razon00000000000000000000000000000000003|Razon00000000000000000000000000000000004|Razon00000000000000000000000000000000005|Razon00000000000000000000000000000000006|Razon00000000000000000000000000000000007|Razon00000000000000000000000000000000008|Razon00000000000000000000000000000000009|Razon00000000000000000000000000000000010|Razon00000000000000000000000000000000011|Razon00000000000000000000,~";
                        // Thread.sleep(1000);
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU3, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU3, tramaSaldoAFC.toString());
//                        System.out.println(" RESPUESTA TRAMA SALDO AFC :" + Respuesta);
                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());
                    } catch (Exception ex) {

                        docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + docs.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ saldo afc= " + "##" + docs.obtenerHoraActual());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU3, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU3, tramaSaldoAFC.toString());
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 1, ",") + "##" + docs.obtenerHoraActual());

                        } catch (Exception ex1) {
                            docs.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + docs.obtenerHoraActual());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes("Error Conectándose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDOS);



                                }
                            });
                            throw new Exception("ERROR");

                        }

                    }

                    if ("000".equals(Respuesta.split(",")[2])) {

                        infoTablaSaldoAFC datosAFC = infoTablaSaldoAFC.getInfoAFC();




                        datosAFC.setAhorroDisBenef(Respuesta.split(",")[4]);
                        datosAFC.setRendimientos(Respuesta.split(",")[5]);
                        datosAFC.setValorBenef(Respuesta.split(",")[6]);
                        datosAFC.setAhorrosinBenef(Respuesta.split(",")[7]);
                        datosAFC.setValorcongelado2344(Respuesta.split(",")[8]);
                        datosAFC.setBeneficioGanado(Respuesta.split(",")[9]);
                        datosAFC.setBeneficioPerdido(Respuesta.split(",")[10]);
                        datosAFC.setSaldocanje(Respuesta.split(",")[11]);
                        datosAFC.setSaldoDispVivienda(Respuesta.split(",")[12]);
                        datosAFC.setBenefGanarVivienda(Respuesta.split(",")[13]);
                        datosAFC.setGmfRetiroVivienda(Respuesta.split(",")[14]);
                        datosAFC.setBenefPerdidogmf(Respuesta.split(",")[15]);
                        datosAFC.setAhorro2344(Respuesta.split(",")[16]);
                        datosAFC.setBenefCongelar2344(Respuesta.split(",")[17]);
                        datosAFC.setGmfVivienda2344(Respuesta.split(",")[18]);
                        datosAFC.setBenefPerdido2344gmf(Respuesta.split(",")[19]);
                        datosAFC.setSaldoDispNoVivienda(Respuesta.split(",")[20]);
                        datosAFC.setBenefPerderNoVivienda(Respuesta.split(",")[21]);
                        datosAFC.setGmfRetiroNoVivnda(Respuesta.split(",")[22]);
                        datosAFC.setAhorro2344NoBenef(Respuesta.split(",")[23]);
                        datosAFC.setBenefPerder2344(Respuesta.split(",")[24]);
                        datosAFC.setValorRetirosTram(Respuesta.split(",")[25]);
                        datosAFC.setRazonBloqueo(Respuesta.split(",")[26]);


                        infoTablaSaldoAFC.setInfoAFC(datosAFC);


                    } else {
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();

                        if (!cancelOP.get()) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_SALDO_HIP);
                                    cancel();

//                                    continuarop.setDisable(false);
//                                    progreso.progressProperty().unbind();
//                                    progreso.setProgress(0);
//                                    progreso.setVisible(false);
                                }
                            });
                        }
                        throw new Exception("ERROR");

                    }

                    return infoTablaSaldoAFC.getInfoAFC();


                }
            };
        }
    }

    @FXML
    void terminar_trx(final ActionEvent event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();

                } catch (Exception e) {
                    docs.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
            }
        });
    }

    @FXML
    void volver_op(final ActionEvent event) {
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

                final TreeView<String> arbol_servadd = (TreeView<String>) pane.lookup("#arbol_servadicionales");
                if (arbol_servadd != null) {
                    arbol_servadd.setDisable(false);
                }



                arbol_saldos.getSelectionModel().clearSelection();
                try {
                    pane.getChildren().remove(3);
                } catch (Exception e) {
                    docs.imprimir("Warning -->" + e.toString() + "  :" + docs.obtenerHoraActual());
                }
                Atlas.vista.show();
            }
        });
    }
}
