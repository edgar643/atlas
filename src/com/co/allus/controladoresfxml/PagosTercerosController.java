/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.pagosaterceros.ModelConvenios;
import com.co.allus.modelo.pagosaterceros.convenio;
import com.co.allus.userComponent.RestrictiveTextField;
import com.co.allus.utils.AtlasConstantes;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class PagosTercerosController {

    @FXML
    private transient ResourceBundle resources;
    @FXML
    private transient URL location;
    @FXML
    private transient Button buscar_conv;
    @FXML
    private transient TableColumn<ModelConvenios, String> cat_conv;
    @FXML
    private transient TableColumn<ModelConvenios, Integer> cod_conv;
    @FXML
    private transient Button continuarIngresoDat;
    @FXML
    private transient Button limpiar;
    @FXML
    public static ComboBox<String> listCategoria;
    @FXML
    private transient TableColumn<ModelConvenios, Integer> nit_conv;
    @FXML
    private transient TableColumn<ModelConvenios, String> nom_conv;
    @FXML
    private transient TableColumn<ModelConvenios, String> valida_banco;
    @FXML
    private transient TableView<ModelConvenios> tabla_convenios;
    @FXML
    private transient RestrictiveTextField textcodConv;
    @FXML
    private transient RestrictiveTextField textnomConv;
    @FXML
    private transient Label titulopagos;
    @FXML
    private transient Label nodataConv;
    @FXML
    private transient StackPane panel_tabla;
    private static final String NO_DATA = "NODATA";
    private static final String ERROR_BD = "BD998";
    private Pagination pagination;
    public boolean order = false;
    int currentpageindex = 0;
    private static GestorDocumental gestorDoc = new GestorDocumental();
    private ObservableList<ModelConvenios> convenios;
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());

    @FXML
    void initialize(final URL url, final ResourceBundle rb) {
        assert buscar_conv != null : "fx:id=\"buscar_conv\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert cat_conv != null : "fx:id=\"cat_conv\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert cod_conv != null : "fx:id=\"cod_conv\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert continuarIngresoDat != null : "fx:id=\"continuarIngresoDat\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert limpiar != null : "fx:id=\"limpiar\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert listCategoria != null : "fx:id=\"listCategoria\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert nit_conv != null : "fx:id=\"nit_conv\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert nom_conv != null : "fx:id=\"nom_conv\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert tabla_convenios != null : "fx:id=\"tabla_convenios\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert textcodConv != null : "fx:id=\"textcodConv\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert textnomConv != null : "fx:id=\"textnomConv\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert titulopagos != null : "fx:id=\"titulopagos\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert nodataConv != null : "fx:id=\"nodataConv\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert panel_tabla != null : "fx:id=\"panel_tabla\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        assert valida_banco != null : "fx:id=\"valida_banco\" was not injected: check your FXML file 'PagosTerceros.fxml'.";
        this.location = url;
        this.resources = rb;
    }

    @FXML
    void buscarConv(final ActionEvent event) {
        final StringBuilder aenviar = new StringBuilder();
        final String seleccionCat = listCategoria.getSelectionModel().getSelectedItem();

        /**
         * manejo de eventos tabla
         */
        tabla_convenios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(final ObservableValue observableValue, final Object oldValue, final Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tabla_convenios.getSelectionModel().getSelectedItem() != null) {

                    continuarIngresoDat.setDisable(false);
                } else {
                    continuarIngresoDat.setDisable(true);
                    tabla_convenios.getSelectionModel().clearSelection();
                }
            }
        });
        tabla_convenios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        /**
         *
         */
        aenviar.append("900##101##");
        aenviar.append(textcodConv.getText().trim());
        aenviar.append("##");
        aenviar.append(textnomConv.getText().trim());
        aenviar.append("##");
        aenviar.append(listCategoria.getSelectionModel().getSelectedItem().equals("SELECCIONE") ? "" : listCategoria.getSelectionModel().getSelectedItem());
        aenviar.append("##~");

        try {
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
            final ConectSS conexion = new ConectSS();
            String datosConsulta = null;
            try {
                try {

                    datosConsulta = conexion.enviarRecibirServidor(AtlasConstantes.IP_MQ_PRINCIPAL, AtlasConstantes.PUERTO_COM_MQ, aenviar.toString());
                } catch (Exception ex) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + obtenerHoraActual());
                    //envio a contingencia
                    try {
                        datosConsulta = conexion.enviarRecibirServidor(AtlasConstantes.IP_MQ_CONTINGENCIA, AtlasConstantes.PUERTO_COM_MQ, aenviar.toString());

                    } catch (final Exception ex1) {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + obtenerHoraActual());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new ModalMensajes("Error Conectándose al Servidor", ex1.getMessage(), ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                            }
                        });
                        return;
                    }

                }

                final URL location = getClass().getResource("/com/co/allus/vistas/PagosTerceros.fxml");
                final FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                final Parent frameGnral = Atlas.vista.getScene().getRoot();
                final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
                //final TableView<ModelConvenios> tabla_convenios=(TableView<ModelConvenios> ) root.lookup("#tabla_convenios");

                /**
                 * configuracion de la tabla
                 */
                cod_conv.setCellValueFactory(new PropertyValueFactory<ModelConvenios, Integer>("codigo_conv"));
                cat_conv.setCellValueFactory(new PropertyValueFactory<ModelConvenios, String>("categoria"));
                nit_conv.setCellValueFactory(new PropertyValueFactory<ModelConvenios, Integer>("nit"));
                nom_conv.setCellValueFactory(new PropertyValueFactory<ModelConvenios, String>("nombre_conv"));
                valida_banco.setCellValueFactory(new PropertyValueFactory<ModelConvenios, String>("valida_banco"));

                // tabla_convenios.prefWidthProperty().bind(root.widthProperty());
                tabla_convenios.setVisible(true);
                /**
                 * iniciar la tabla vacia
                 */
                final ObservableList<ModelConvenios> emptyObservableList = FXCollections.emptyObservableList();
                tabla_convenios.setItems(emptyObservableList);
                /**
                 * fin configuracion de la tabla
                 */
                mensaje_saldos.setVisible(false);
                nodataConv.setVisible(false);
                /**
                 * configuracion de los mensajes a mostrar
                 */
                /**
                 * fin configuracion de los mensajes a mostrar
                 */
                // listCategoria.setItems(conexionBd.consultarDatosCategorias());
                // se ingresan los datos                    
                final String[] data = datosConsulta.split("##");

                if (NO_DATA.equalsIgnoreCase(data[0])) {
                    panel_tabla.getChildren().clear();
                    //tabla_convenios.setVisible(false);
                    nodataConv.setText("No hay convenios que cumplan con el criterio de búsqueda utilizado");
                    nodataConv.setVisible(true);
                    mensaje_saldos.setVisible(false);
                    try {
                        pagination.setVisible(false);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    buscar_conv.setDisable(false);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    Atlas.vista.show();
                    return;

                } else if (ERROR_BD.equalsIgnoreCase(data[0])) {
                    panel_tabla.getChildren().clear();
                    //tabla_convenios.setVisible(false);
                    nodataConv.setText("El sistema no pudo realizar la operacion Cod.998");
                    nodataConv.setVisible(true);
                    mensaje_saldos.setVisible(false);
                    try {
                        pagination.setVisible(false);
                    } catch (Exception ex) {
                        gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                    }
                    buscar_conv.setDisable(false);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    Atlas.vista.show();
                    return;

                } else {

                    convenios = FXCollections.observableArrayList();
                    final List<String> datoskeyconv = new ArrayList<String>();
                    for (int i = 0; i < data.length - 1; i++) {
                        final ModelConvenios modelConvenios = new ModelConvenios(Integer.parseInt(data[i].split("::")[0].trim()), data[i].split("::")[1].trim(), Integer.parseInt(data[i].split("::")[2].trim()), data[i].split("::")[3].trim(), data[i].split("::")[5].trim());
                        convenios.add(modelConvenios);
                        datoskeyconv.add(data[i].split("::")[0] + "," + data[i].split("::")[4]);
                    }
                    ModelConvenios.AddMapdata(datoskeyconv);
                    /**
                     * configuracion de la paginacion
                     */
                    final int numpag = convenios.size() % rowsPerPage() == 0 ? convenios.size() / rowsPerPage() : convenios.size() / rowsPerPage() + 1;
                    pagination = new Pagination(numpag, 0);
                    pagination.setPageFactory(new Callback<Integer, Node>() {
                        @Override
                        public Node call(final Integer pageIndex) {
                            if (pageIndex > numpag) {
                                return null;
                            } else {
                                int lastIndex = 0;
                                int displace = convenios.size() % rowsPerPage();
                                if (displace >= 0) {
                                    lastIndex = convenios.size() / rowsPerPage();
                                } else {
                                    lastIndex = convenios.size() / rowsPerPage() - 1;
                                }
                                int page = pageIndex * itemsPerPage();

                                for (int i = page; i < page + itemsPerPage(); i++) {

                                    if (lastIndex == pageIndex) {
                                        tabla_convenios.setItems(FXCollections.observableArrayList(convenios.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
                                    } else {
                                        tabla_convenios.setItems(FXCollections.observableArrayList(convenios.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
                                    }

                                    panel_tabla.getChildren().clear();
                                    tabla_convenios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                                    panel_tabla.getChildren().add(tabla_convenios);
                                    panel_tabla.setVisible(true);
                                }
                                return panel_tabla;
                            }
                        }
                    });

                    pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                            currentpageindex = newValue.intValue();
                            tabla_convenios.getItems().setAll(convenios.subList(newValue.intValue() * rowsPerPage(), ((newValue.intValue() * rowsPerPage() + rowsPerPage() <= convenios.size()) ? newValue.intValue() * rowsPerPage() + rowsPerPage() : convenios.size())));
                        }
                    });

                    /**
                     * fin configuracion de la paginacion
                     */
                    cod_conv.setPrefWidth(45d);
                    nit_conv.setPrefWidth(35d);
                    valida_banco.setPrefWidth(65d);
                    listCategoria.getSelectionModel().select(seleccionCat);
                    //  tabla_convenios.setItems(convenios);  
                    panel_tabla.getChildren().clear();
                    panel_tabla.getChildren().add(tabla_convenios);
                    panel_tabla.setLayoutX(2.5);
                    panel_tabla.setLayoutY(114);
                    root.getChildren().remove(11);
                    root.getChildren().add(pagination);
                    root.getChildren().get(11).setLayoutX(2.5);
                    root.getChildren().get(11).setLayoutY(114);
                    pagination.setVisible(true);
                    pane.setAlignment(Pos.CENTER_LEFT);
                    buscar_conv.setVisible(true);
                    continuarIngresoDat.setDisable(true);
                    Atlas.vista.show();
                }

            } catch (Exception ex) {
                Logger.getLogger(PagosTercerosController.class.getName()).log(Level.SEVERE, null, ex);
            }

//                }
//            });
        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }

    }
    private ListChangeListener<TableColumn<ModelConvenios, ?>> orderlistener = new ListChangeListener<TableColumn<ModelConvenios, ?>>() {
        @Override
        public void onChanged(final Change<? extends TableColumn<ModelConvenios, ?>> paramChange) {

            if (!paramChange.getList().isEmpty()) {
                final String nomTable = paramChange.getList().get(0).getText();
                Collections.sort(convenios, new Comparator<ModelConvenios>() {
                    @Override
                    public int compare(final ModelConvenios t, final ModelConvenios t1) {
                        if ("Código deconvenio".equalsIgnoreCase(nomTable.replace("\n", ""))) {
                            final Integer codconv = new Integer(t.getCodigo_conv());
                            final Integer codconv1 = new Integer(t1.getCodigo_conv());
                            return codconv.compareTo(codconv1);
                        } else if ("Nombre de convenio".equalsIgnoreCase(nomTable)) {
                            return t.getNombre_conv().compareTo(t1.getNombre_conv());
                        } else if ("NIT".equalsIgnoreCase(nomTable)) {
                            final Integer nit = new Integer(t.getNit());
                            final Integer nit1 = new Integer(t1.getNit());
                            return nit.compareTo(nit1);
                        } else if ("Categoría".equalsIgnoreCase(nomTable)) {
                            return t.getCategoria().compareTo(t1.getCategoria());

                        } else if ("ValidaBanco?".equalsIgnoreCase(nomTable.replace("\n", ""))) {
                            return t.getValida_banco().compareTo(t1.getValida_banco());
                        } else {
                            return -1;
                        }

                    }
                });

                if (isOrder()) {
                    Collections.reverse(convenios);
                }
                setOrder(!isOrder());

                tabla_convenios.getItems().setAll(convenios.subList(currentpageindex * rowsPerPage(), ((currentpageindex * rowsPerPage() + rowsPerPage() <= convenios.size()) ? currentpageindex * rowsPerPage() + rowsPerPage() : convenios.size())));

            }
        }
    };

    @FXML
    void contIngresoDatos(final ActionEvent event) {
        if (tabla_convenios.getSelectionModel().getSelectedItem() != null) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Paso 2 Pagos a Terceros" + "##" + obtenerHoraActual());
            continuarOP.setOnSucceeded(new EventHandler() {
                @Override
                public void handle(Event event) {
                }
            });

            continuarOP.setOnCancelled(new EventHandler() {
                @Override
                public void handle(final Event event) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Paso 2 Pagos a terceros" + "##" + obtenerHoraActual());
                }
            });

        } else {
//            System.out.println("no hay selecion");
            return;

        }
        new Thread(continuarOP).start();
    }

    @FXML
    void limpiarPantalla(final ActionEvent event) {
        final Parent frameGnral = Atlas.vista.getScene().getRoot();
        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
        final Label mensaje_saldos = (Label) pane.lookup("#label_saldos");
        mensaje_saldos.setVisible(false);
        textcodConv.clear();
        textnomConv.clear();
        nodataConv.setVisible(false);
        listCategoria.getSelectionModel().select("SELECCIONE");
        tabla_convenios.getSelectionModel().clearSelection();
        panel_tabla.getChildren().clear();
        buscar_conv.setDisable(true);

        try {
            pagination.setVisible(false);
        } catch (Exception e) {
            gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
        }

    }

    @FXML
    void convkeypress(final KeyEvent event) {

        if (KeyCode.DELETE.equals(event.getCode())) {

            if (event.getCode().impl_getChar().trim().equals("") && textcodConv.getText().trim().isEmpty() && textnomConv.getText().trim().isEmpty() && ("SELECCIONE".equals(listCategoria.getSelectionModel().getSelectedItem()) || listCategoria.getSelectionModel().getSelectedItem() == null)) {
                textcodConv.clear();
                buscar_conv.setDisable(true);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(textcodConv, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                textcodConv.clear();
                textcodConv.fireEvent(keyEvent);
                buscar_conv.setDisable(true);
            }
        } else if (KeyCode.SPACE.equals(event.getCode())) {
            event.consume();
        }

    }

    @FXML
    void convkeyTiped(final KeyEvent event) {

        if (textcodConv.getText().trim().isEmpty() && textnomConv.getText().trim().isEmpty() && ("SELECCIONE".equals(listCategoria.getSelectionModel().getSelectedItem()) || listCategoria.getSelectionModel().getSelectedItem() == null)) {
            buscar_conv.setDisable(true);
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final AnchorPane root = (AnchorPane) pane.getChildren().get(3);
                    Button buscar = (Button) root.lookup("#buscar_conv");
                    buscar.setDisable(false);
                }
            });
        }

    }

    @FXML
    void nomConvKeyPress(final KeyEvent event) {
        if (KeyCode.DELETE.equals(event.getCode())) {

            if (event.getCode().impl_getChar().trim().equals("") && textcodConv.getText().trim().isEmpty() && ("SELECCIONE".equals(listCategoria.getSelectionModel().getSelectedItem()) || listCategoria.getSelectionModel().getSelectedItem() == null)) {
                textnomConv.clear();
                buscar_conv.setDisable(true);
            } else {
                KeyEvent keyEvent = KeyEvent.impl_keyEvent(textnomConv, "", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                textnomConv.clear();
                textnomConv.fireEvent(keyEvent);
                buscar_conv.setDisable(false);
            }
        }

    }

    @FXML
    void nomConvKeyTiped(final KeyEvent event) {

        if (textnomConv.getText().trim().isEmpty() && textcodConv.getText().trim().isEmpty() && ("SELECCIONE".equals(listCategoria.getSelectionModel().getSelectedItem()) || listCategoria.getSelectionModel().getSelectedItem() == null)) {
            buscar_conv.setDisable(true);
        } else {
            buscar_conv.setDisable(false);
        }
    }

    @FXML
    void onselectCate(final ActionEvent event) {

        if (!("SELECCIONE".equals(listCategoria.getSelectionModel().getSelectedItem()) || listCategoria.getSelectionModel().getSelectedItem() == null)) {
            buscar_conv.setDisable(false);
        } else {
            if (textcodConv.getText().trim().isEmpty() && textnomConv.getText().trim().isEmpty()) {
                buscar_conv.setDisable(true);
            }

        }
    }
    Task continuarOP = new Task<Void>() {
        @Override
        protected Void call() throws Exception {

            final convenio dataconv = new convenio();
            final ModelConvenios seleccion = tabla_convenios.getSelectionModel().getSelectedItem();
            dataconv.setCod_conv(seleccion.getCodigo_conv() + "");
            dataconv.setCat_conv(seleccion.getCategoria());
            dataconv.setNom_conv(seleccion.getNombre_conv());
            dataconv.setNit_conv(seleccion.getNit() + "");
            dataconv.setReferencia(ModelConvenios.codigosRef.get(seleccion.getCodigo_conv()));
            convenio.setConvenio(dataconv);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    final PagosTercerosDatosPago1Controller pagos = new PagosTercerosDatosPago1Controller();
                    pagos.MostrarPagosMenu1(dataconv);
                }
            });

            return null;
        }
    };

    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
}
