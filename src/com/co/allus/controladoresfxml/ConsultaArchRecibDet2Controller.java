/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.atlas.Atlas;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.modelo.consultamovimientos.InfoTablaDetMovPSE;
import com.co.allus.modelo.pagosaterceros.infoArchivoDetalle;
import com.co.allus.modelo.pagosaterceros.infoArchivosRecibidos;
import com.co.allus.modelo.pagosaterceros.infoMovConv;
import com.co.allus.modelo.pagosaterceros.infoTablaDescInfo;
import com.co.allus.modelo.pagosterceros.DataArchivoRecibidosDet;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author alexander.lopez.o
 */
public class ConsultaArchRecibDet2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TableColumn<infoTablaDescInfo, String> descripcion;
    @FXML
    private TableColumn<infoTablaDescInfo, String> informacion;

    @FXML
    private TableView<infoTablaDescInfo> tabla_datos;
    @FXML
    private Button terminar_trx;
    @FXML
    private Button volver;
    
    private GestorDocumental docs = new GestorDocumental();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'consultaArchRecibDet2.fxml'.";
        assert descripcion != null : "fx:id=\"descripcion\" was not injected: check your FXML file 'consultaArchRecibDet2.fxml'.";
        assert informacion != null : "fx:id=\"informacion\" was not injected: check your FXML file 'consultaArchRecibDet2.fxml'.";
        assert tabla_datos != null : "fx:id=\"tabla_datos\" was not injected: check your FXML file 'consultaArchRecibDet2.fxml'.";
        assert terminar_trx != null : "fx:id=\"terminar_trx\" was not injected: check your FXML file 'consultaArchRecibDet2.fxml'.";
        assert volver != null : "fx:id=\"volver\" was not injected: check your FXML file 'consultaArchRecibDet2.fxml'.";


        descripcion.setCellValueFactory(new PropertyValueFactory<infoTablaDescInfo, String>("descripcion"));
        informacion.setCellValueFactory(new PropertyValueFactory<infoTablaDescInfo, String>("informacion"));
    }
    
     public void mostrarArchRecibDetalle2(final infoArchivoDetalle infoArchivoDetalle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    final URL location = getClass().getResource("/com/co/allus/vistas/consultaArchRecibDet2.fxml");
                    final FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                    final Parent frameGnral = Atlas.vista.getScene().getRoot();
                    final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");
                    final Button continuar_op = (Button) root.lookup("#terminar_trx");
                    final Button volver = (Button) root.lookup("#volver");
                    final TableView<infoTablaDescInfo> tablaDatos = (TableView<infoTablaDescInfo>) root.lookup("#tabla_datos");

                    tablaDatos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    final ObservableList<infoTablaDescInfo> dataTabla = FXCollections.observableArrayList();

//                        private SimpleStringProperty idregistro;
//    private SimpleStringProperty codigorespuesta;
//    private SimpleStringProperty descripcion;
//    private SimpleStringProperty nitentidadpag;
//    private SimpleStringProperty nomentidadpag;
//    private SimpleStringProperty ref1;
//    private SimpleStringProperty ref2;
//    private SimpleStringProperty numctapagador;
//    private SimpleStringProperty descripcionctaPagador;
//    private SimpleStringProperty valordetransaccion;
//    private SimpleStringProperty nombanco;
//    private SimpleStringProperty fechaprocesacion;
//    private SimpleStringProperty valordebitado;
                    
                    // se agregan los datos a la tabla
                    dataTabla.add(new infoTablaDescInfo("Id de Registro", infoArchivoDetalle.getIdregistro()));
                    dataTabla.add(new infoTablaDescInfo("Codigo de Respuesta", infoArchivoDetalle.getCodigorespuesta()));
                    dataTabla.add(new infoTablaDescInfo("Descripcion", infoArchivoDetalle.getDescripcion()));
                    dataTabla.add(new infoTablaDescInfo("Nit Entidad Pagadora", infoArchivoDetalle.getNitentidadpag()));
                    dataTabla.add(new infoTablaDescInfo("Nombre Entidad Pagadora", infoArchivoDetalle.getNomentidadpag()));
                    dataTabla.add(new infoTablaDescInfo("Referencia 1", infoArchivoDetalle.getRef1()));
                    dataTabla.add(new infoTablaDescInfo("Referencia 2", infoArchivoDetalle.getRef2()));
                    dataTabla.add(new infoTablaDescInfo("Número Cuenta Pagador", infoArchivoDetalle.getNumctapagador()));
                    dataTabla.add(new infoTablaDescInfo("Descripcion Cuenta Pagador", infoArchivoDetalle.getDescripcionctaPagador()));
                    dataTabla.add(new infoTablaDescInfo("Valor Transacción", infoArchivoDetalle.getValordetransaccion()));
                    dataTabla.add(new infoTablaDescInfo("Nombre Banco", infoArchivoDetalle.getNombanco()));
                    dataTabla.add(new infoTablaDescInfo("Fecha Procesamiento", infoArchivoDetalle.getFechaprocesacion()));
                    dataTabla.add(new infoTablaDescInfo("Valor Debitado", infoArchivoDetalle.getValordebitado()));
                   

                    tablaDatos.setItems(dataTabla);


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

                    volver.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volver.setCursor(Cursor.HAND);
                                    volver.setEffect(shadow);
                                }
                            });

                    volver.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(final MouseEvent event) {
                                    volver.setCursor(Cursor.DEFAULT);
                                    volver.setEffect(null);

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
                    new ModalMensajes("Error en la aplicacion \n , por favor  informar al area tecnica", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                }

            }
        });




    }

    @FXML
    void VolverOP(ActionEvent event) 
    {
        ConsultaArhRecibDetController controller =new ConsultaArhRecibDetController();
        controller.mostrarArchivosDetalle(null, null, DataArchivoRecibidosDet.getDataarch(), 0);
    }

    @FXML
    void aceptar(ActionEvent event) 
    {
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Atlas.getVista().gotoPrincipal();
                } catch (IOException ex) {
                    docs.imprimir("Se presento un error inesperado en la aplicacion --->" + ex.toString());
                }
            }
        });
    }
}
