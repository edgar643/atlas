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
import com.co.allus.modelo.actualizaciondatosseguridad.ModeloDatosDeSeguridad;
import com.co.allus.modelo.cambiomecanismo.ModeloDatosSeguridad;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author luis.cuervo
 */
public class InscripcionServicioClaveDinamicaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField Celular;
    @FXML
    private TextField Email;
    @FXML
    private Button cancelar;
    @FXML
    private TextField cedulaCliente;
    @FXML
    private Button continuar_op;
    @FXML
    private TextField nombreCliente;
    @FXML
    private ComboBox<String> tipoEmail;
    @FXML
    private ComboBox<String> tipoMecanismo;
    @FXML
    private Label titulosActDatSeguridad;
    @FXML
    private ProgressBar progreso;
    @FXML
    private TextField avisos;

    @FXML
    void TipoMailOnAction(ActionEvent event) {

        String tipoEmailSelect = tipoEmail.getSelectionModel().getSelectedItem();
//        System.out.println("Tipo Mail Select:" + tipoEmailSelect);
        StringUtilities.borrarClipBoard();
        final StringUtilities validaciones = new StringUtilities();

        try {
            if ((Email.getText().isEmpty()
                    && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")
                    && Celular.getText().trim().length() == 10)
                    || (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && validaciones.validateEmail(Email.getText())
                    && !tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && Celular.getText().trim().length() == 10)
                    || (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && validaciones.validateEmail(Email.getText())
                    && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")
                    && Celular.getText().trim().length() == 0)) {
                continuar_op.setDisable(false);
                avisos.setEditable(false);
                avisos.setVisible(false);
            } else {

                if (!Email.getText().isEmpty()
                        && !validaciones.validateEmailLuis(Email.getText())) //                    && !validaciones.validateEmail(Email.getText())) 
                {

                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD3); // Email no valido
                    avisos.setEditable(false);
                    avisos.setVisible(true);

                } else if (Celular.getText().trim().length() <= 9 && Celular.getText().trim().length() >= 1) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD2);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 0
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD1);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 10
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 10
                        && !tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 10
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD3);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 0
                        && !tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 0
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!Email.getText().isEmpty()
                        && validaciones.validateEmailLuis(Email.getText())
                        //                        && validaciones.validateEmail(Email.getText())
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione") //                        && Celular.getText().trim().length() == 0
                        ) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6); // tipo mail
//                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8); // tipo mecanismo
                    avisos.setEditable(false);
                    avisos.setVisible(true);

                } /**/ // sin ninguna seleccion
                else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } /**/ else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()
                        && Celular.getText().trim().length() == 0) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()
                        && Celular.getText().trim().length() == 10) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
//                    avisos.setText("Val1");
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
//                    avisos.setText("Val2");
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8); //mecanismo
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && !tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD3);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                }

                continuar_op.setDisable(true);

            }
        } catch (Exception ex) {
            Logger.getLogger(InscripcionServicioClaveDinamicaController.class.getName()).log(Level.SEVERE, ex.toString());
        }
    }

    @FXML
    void cancel_op(ActionEvent event) {
        try {
            task.cancel();
        } catch (Exception ex) {
            Logger.getLogger(InscripcionServicioClaveDinamicaController.class.getName()).log(Level.SEVERE, ex.toString());
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }
        try {
            Atlas.getVista().gotoPrincipal();
        } catch (IOException ex) {
            gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
        }
    }

    @FXML
    void continuar_OP(ActionEvent event) {
        final ModeloDatosDeSeguridad modelo = new ModeloDatosDeSeguridad();

        modelo.setNombre(nombreCliente.getText());
        modelo.setIdentificacion(cedulaCliente.getText());
        modelo.setCelular(Celular.getText());
        modelo.setEmail(Email.getText());

        final String tipoMail = tipoEmail.getSelectionModel().getSelectedItem();
        final String tipoMailE = tipoMail.equals("Personal") ? "P" : tipoMail.equals("Laboral") ? "L" : "S"; // no debe llegar seleccione
        modelo.setTipoEmail(tipoMailE);

        final String tipoMec = tipoMecanismo.getSelectionModel().getSelectedItem();
        final String tipoMecanismoE = tipoMec.equals("E-mail") ? "EML" : tipoMec.equals("SMS") ? "SMS" : "S"; // no debe llegar seleccione
        modelo.setOTP(tipoMecanismoE);

        final InscripcionServicioClaveDinamicaTrxConfirmController mostrarConfirmacion = new InscripcionServicioClaveDinamicaTrxConfirmController();
        mostrarConfirmacion.mostrarInscripcionServicioClaveDinamicaTrxConfirmController(modelo, menu1, flujo1, opcion1);
    }

    @FXML
    void TipoMecanismoOnAction(ActionEvent event) {
        String tipoEmailSelect = tipoEmail.getSelectionModel().getSelectedItem();
//        System.out.println("Tipo Mail Select:" + tipoEmailSelect);
        StringUtilities.borrarClipBoard();
        final StringUtilities validaciones = new StringUtilities();

        try {

            if (!tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                if (tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")
                        && Email.getText().isEmpty()) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4); // Aviso, debe ingresar Mail
                    continuar_op.setDisable(true);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")
                        && Celular.getText().trim().length() == 0) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7); // Aviso, debe ingresar Celular
                    continuar_op.setDisable(true);
                    Email.setDisable(true);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } // START NEW - 23-Jun-2016
                else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !Email.getText().isEmpty()
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6); // Aviso, debe ingresar tipo mail
                    continuar_op.setDisable(true);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !Email.getText().isEmpty()
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6); // Aviso, debe ingresar tipo mail
                    continuar_op.setDisable(true);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()
                        && Celular.getText().trim().length() == 0) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7); // Aviso, debe ingresar mail
                    continuar_op.setDisable(true);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()
                        && Celular.getText().trim().length() == 10) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7); // Aviso, debe ingresar mail
                    continuar_op.setDisable(true);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && !tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD3);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } // END NEW - 23-Jun-2016
                else if ((Email.getText().isEmpty()
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Celular.getText().trim().length() == 10
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS"))
                        || (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && !tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione"))
                        || (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0)
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
                    continuar_op.setDisable(false);
                    avisos.setEditable(false);
                    avisos.setVisible(false);

                }
            } else {

                avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8); // Aviso, debes seleccionar un tipo de mecanismo
                continuar_op.setDisable(true);
                avisos.setEditable(false);
                avisos.setVisible(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(InscripcionServicioClaveDinamicaController.class.getName()).log(Level.SEVERE, ex.toString());

        }

    }
    // Barra de tarea despues de actualizar info
    private static AtomicBoolean cancelartarea = new AtomicBoolean();
    private Service<ModeloDatosSeguridad> task = new InscripcionServicioClaveDinamicaController.GetDatosSeguridadTask();
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private static String menu1 = "";
    private static int flujo1 = 0;  // de donde viene
    private static int opcion1 = 0; // 1 Evidente Ok, 2 Evidente Nok

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert Celular != null : "fx:id=\"Celular\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";
        assert Email != null : "fx:id=\"Email\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";
        assert cedulaCliente != null : "fx:id=\"cedulaCliente\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";
        assert nombreCliente != null : "fx:id=\"nombreCliente\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";
        assert tipoEmail != null : "fx:id=\"tipoEmail\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";
        assert titulosActDatSeguridad != null : "fx:id=\"titulosActDatSeguridad\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'ActualizacionDatosSeguridad.fxml'.";
        assert avisos != null : "fx:id=\"avisos\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";
        assert tipoMecanismo != null : "fx:id=\"tipoMecanismo\" was not injected: check your FXML file 'InscripcionServicioClaveDinamica.fxml'.";

        this.resources = rb;
        this.location = url;
        progreso.setVisible(false);
        avisos.setVisible(false);
    }

    @FXML
    void valkeypressed(KeyEvent event) {
        StringUtilities.borrarClipBoard();
        if (KeyCode.CONTROL.equals(event.getCode())) {

//            System.out.println("Presiono CTRL");
            Runtime rt = Runtime.getRuntime();
            try {
                String comandoEnvio = "cmd.exe /C echo off | clip";
                Process proceso = rt.exec(comandoEnvio);

            } catch (IOException ex) {
                Logger.getLogger(ActualizacionDatosSeguridadController.class.getName()).log(Level.SEVERE, null, ex);
                gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

            }
        }
        final StringUtilities validaciones = new StringUtilities();
        if (!KeyCode.CONTROL.equals(event.getCode())) {
            ModeloDatosDeSeguridad.setCelular(Celular.getText());
            if (KeyCode.DELETE.equals(event.getCode())) {
                if (!(event.getCode().impl_getChar().trim().equals(""))) {
                    continuar_op.setDisable(false);
                } else {
                    KeyEvent keyEvent = KeyEvent.impl_keyEvent(Celular, " ", KeyCode.DELETE.name(), KeyCode.DELETE.impl_getCode(), false, false, false, false, KeyEvent.KEY_TYPED);
                    Celular.clear();
                    Celular.fireEvent(keyEvent);
                    continuar_op.setDisable(true);
                }
            }
            if (Celular.getText().length() == 10) {
                continuar_op.setDisable(false);
            }
            if ((Email.getText().isEmpty()
                    && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && Celular.getText().trim().length() == 10
                    && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS"))
                    || (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && validaciones.validateEmail(Email.getText())
                    && Celular.getText().trim().length() == 10
                    && !tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione"))
                    || (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && validaciones.validateEmail(Email.getText())
                    && Celular.getText().trim().length() == 0)
                    && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
                continuar_op.setDisable(false);
                avisos.setEditable(false);
                avisos.setVisible(false);
            } else {
//                if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
//                        && !validaciones.validateEmail(Email.getText())
//                        && Celular.getText().trim().length() == 0) {
//                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD1);
//                    avisos.setEditable(false);
//                    avisos.setVisible(true);
//                } 
                /**/
                if (Celular.getText().trim().length() <= 9 && Celular.getText().trim().length() >= 1) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD2);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 0
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD1);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 10
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 10
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD3);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 0
                        && !tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 0
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } /**/ else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } /**/ else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()
                        && Celular.getText().trim().length() == 0) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()
                        && Celular.getText().trim().length() == 10) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } /**/ else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && !tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD3);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                }

//                else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
//                        && validaciones.validateEmail(Email.getText())
//                        && Celular.getText().trim().length() == 0
//                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")) {
//                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7);
//                    avisos.setEditable(false);
//                    avisos.setVisible(true);
//                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
//                        && !validaciones.validateEmail(Email.getText())
//                        && Celular.getText().trim().length() == 10
//                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
//                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
//                    avisos.setEditable(false);
//                    avisos.setVisible(true);
//                }

                /**/
                continuar_op.setDisable(true);
            }
        } else {

            if (KeyCode.CONTROL.equals(event.getCode())) {
                StringUtilities.borrarClipBoard();
                Celular.setText(ModeloDatosDeSeguridad.getCelular());
                Celular.end();
            }
        }
    }

    public void mostrarInscripcionServicioClaveDinamicaBotonRegresar(final String menu, final int flujo, final int opcion) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/InscripcionServicioClaveDinamica.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final Label label_menu = (Label) pane.lookup("#label_saldos");
                        final Button cancelar = (Button) root.lookup("#cancelar");
                        final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");

//                        final TextField cedula = (TextField) root.lookup("#nombreCliente");
//                        final TextField nombre = (TextField) root.lookup("#cedulaCliente");
                        /*Ojo, hacer el combobox de tipo de Email*/
                        final Label titulosActDatSeguridad = (Label) root.lookup("#titulosActDatSeguridad");
                        titulosActDatSeguridad.setText(menu);
                        menu1 = menu;
                        flujo1 = flujo;
                        opcion1 = opcion;

                        final Cliente datosCliente = Cliente.getCliente();
                        final Label cliente = (Label) frameGnral.lookup("#cliente");
                        final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                        cliente.setText("");
                        cliente.setText(info);

                        final TextField nombreCliente = (TextField) root.lookup("#nombreCliente");
                        final TextField cedulaCliente = (TextField) root.lookup("#cedulaCliente");

                        nombreCliente.setText(datosCliente.getNombre());
                        nombreCliente.setEditable(false);
                        cedulaCliente.setText(datosCliente.getId_cliente());
                        cedulaCliente.setEditable(false);

                        final TextField Celular = (TextField) root.lookup("#Celular");
                        final TextField Email = (TextField) root.lookup("#Email");

                        final ModeloDatosDeSeguridad datosSeguridad = new ModeloDatosDeSeguridad();
                        Celular.setText(datosSeguridad.getCelular());
                        Email.setText(datosSeguridad.getEmail());
//                        +datosSeguridad.setRegistradoAlertas(data[4].trim());
//                        datosSeguridad.setEmail(data[5].trim());
//                        datosSeguridad.setTipo_email(data[6].trim());
//                        datosSeguridad.setCelular(data[7].trim());

                        if (datosSeguridad.getTipoEmail().equals("P") || datosSeguridad.getTipoEmail().equals("L")) {
                            final ComboBox<String> comboTipoMail = (ComboBox<String>) root.lookup("#tipoEmail");
                            final ObservableList<String> emptyList1 = FXCollections.emptyObservableList();
                            comboTipoMail.setItems(emptyList1);
                            final List<String> listTipoMail = new ArrayList<String>();
                            listTipoMail.add("Seleccione");
                            listTipoMail.add("Personal");
                            listTipoMail.add("Laboral");
                            comboTipoMail.setItems(FXCollections.observableArrayList(listTipoMail));
                            final String datoSelect1 = datosSeguridad.getTipoEmail().equals("P") ? "Personal" : datosSeguridad.getTipoEmail().equals("L") ? "Laboral" : "Seleccione";
                            comboTipoMail.getSelectionModel().select(datoSelect1);

                        } else {
                            final ComboBox<String> comboTipoMail = (ComboBox<String>) root.lookup("#tipoEmail");
                            final ObservableList<String> emptyList1 = FXCollections.emptyObservableList();
                            comboTipoMail.setItems(emptyList1);
                            final List<String> listTipoMail = new ArrayList<String>();
                            listTipoMail.add("Seleccione");
                            listTipoMail.add("Personal");
                            listTipoMail.add("Laboral");
                            comboTipoMail.setItems(FXCollections.observableArrayList(listTipoMail));
                            comboTipoMail.getSelectionModel().select("Seleccione");
                        }

                        if (datosSeguridad.getOTP().equals("SMS") || datosSeguridad.getOTP().equals("EML")) {
                            final ComboBox<String> comboTipoMecanismo = (ComboBox<String>) root.lookup("#tipoMecanismo");
                            final ObservableList<String> emptyList2 = FXCollections.emptyObservableList();
                            comboTipoMecanismo.setItems(emptyList2);
                            final List<String> listTipoMecanismo = new ArrayList<String>();
                            listTipoMecanismo.add("Seleccione");
                            listTipoMecanismo.add("SMS");
                            listTipoMecanismo.add("E-mail");
                            comboTipoMecanismo.setItems(FXCollections.observableArrayList(listTipoMecanismo));
                            final String datoSelect1 = datosSeguridad.getOTP().equals("SMS") ? "SMS" : datosSeguridad.getOTP().equals("EML") ? "E-mail" : "Seleccione";
                            comboTipoMecanismo.getSelectionModel().select(datoSelect1);

                        } else {
                            final ComboBox<String> comboTipoMecanismo = (ComboBox<String>) root.lookup("#tipoMecanismo");
                            final ObservableList<String> emptyList2 = FXCollections.emptyObservableList();
                            comboTipoMecanismo.setItems(emptyList2);
                            final List<String> listTipoMecanismo = new ArrayList<String>();
                            listTipoMecanismo.add("Seleccione");
                            listTipoMecanismo.add("SMS");
                            listTipoMecanismo.add("E-mail");
                            comboTipoMecanismo.setItems(FXCollections.observableArrayList(listTipoMecanismo));
                            comboTipoMecanismo.getSelectionModel().select("Seleccione");
                        }

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

//                        botoncontinuarOp.setDisable(true);
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

                    }

                }
            });

        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//            ex.printStackTrace();
        }

    }

    public void mostrarInscripcionServicioClaveDinamicaController(final String menu, final int flujo, final int opcion) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/InscripcionServicioClaveDinamica.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final Label label_menu = (Label) pane.lookup("#label_saldos");
                        final Button cancelar = (Button) root.lookup("#cancelar");
                        final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");

                        final Label titulosActDatSeguridad = (Label) root.lookup("#titulosActDatSeguridad");
                        titulosActDatSeguridad.setText(menu);
                        menu1 = menu;
                        flujo1 = flujo;
                        opcion1 = opcion;

                        final Cliente datosCliente = Cliente.getCliente();
                        final Label cliente = (Label) frameGnral.lookup("#cliente");
                        final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                        cliente.setText("");
                        cliente.setText(info);

                        final TextField nombreCliente = (TextField) root.lookup("#nombreCliente");
                        final TextField cedulaCliente = (TextField) root.lookup("#cedulaCliente");

                        nombreCliente.setText(datosCliente.getNombre());
                        nombreCliente.setEditable(false);
                        cedulaCliente.setText(datosCliente.getId_cliente());
                        cedulaCliente.setEditable(false);

                        final TextField Celular = (TextField) root.lookup("#Celular");
                        final TextField Email = (TextField) root.lookup("#Email");

                        final Region veil = new Region();

                        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)");
                        final ProgressIndicator p = new ProgressIndicator();
                        p.setPrefSize(150, 150);
                        p.setLayoutX(200);
                        p.setLayoutY(50);

                        root.getChildren().addAll(veil, p);

                        botoncontinuarOp.setDisable(true);
                        label_menu.setVisible(false);

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

                        p.progressProperty().bind(task.progressProperty());
                        veil.visibleProperty().bind(task.runningProperty());
                        p.visibleProperty().bind(task.runningProperty());
                        task.start();

                        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {

//                                System.out.println("TERMINO TAREA");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            final ModeloDatosSeguridad datosSeguridad = task.getValue();

                                            if (!datosSeguridad.getRegistradoAlertas().equalsIgnoreCase("N") && !datosSeguridad.getRegistradoAlertas().isEmpty()) {

                                                final String Cel = datosSeguridad.getCelular().trim();
                                                final String Mail = datosSeguridad.getEmail().trim();

                                                Celular.setText(Cel);
                                                Email.setText(Mail);

                                                final ModeloDatosDeSeguridad modelo = new ModeloDatosDeSeguridad();
                                                modelo.setCelular(Cel);
                                                modelo.setEmail(Mail);
                                                modelo.setTipoEmail(datosSeguridad.getTipo_email());

                                                try {
                                                    if (datosSeguridad.getTipo_email().equals("P") || datosSeguridad.getTipo_email().equals("L")) {
                                                        final ComboBox<String> comboTipoMail = (ComboBox<String>) root.lookup("#tipoEmail");
                                                        final ObservableList<String> emptyList1 = FXCollections.emptyObservableList();
                                                        comboTipoMail.setItems(emptyList1);
                                                        final List<String> listTipoMail = new ArrayList<String>();
                                                        listTipoMail.add("Seleccione");
                                                        listTipoMail.add("Personal");
                                                        listTipoMail.add("Laboral");
                                                        comboTipoMail.setItems(FXCollections.observableArrayList(listTipoMail));
                                                        final String datoSelect1 = datosSeguridad.getTipo_email().equals("P") ? "Personal" : datosSeguridad.getTipo_email().equals("L") ? "Laboral" : "Seleccione";
//                                                        System.out.println("--->" + datoSelect1);
                                                        comboTipoMail.getSelectionModel().select(datoSelect1);
//                                                    comboTipoMail.getSelectionModel().select("Seleccione");

                                                    } else {
                                                        final ComboBox<String> comboTipoMail = (ComboBox<String>) root.lookup("#tipoEmail");
                                                        final ObservableList<String> emptyList1 = FXCollections.emptyObservableList();
                                                        comboTipoMail.setItems(emptyList1);
                                                        final List<String> listTipoMail = new ArrayList<String>();
                                                        listTipoMail.add("Seleccione");
                                                        listTipoMail.add("Personal");
                                                        listTipoMail.add("Laboral");
                                                        comboTipoMail.setItems(FXCollections.observableArrayList(listTipoMail));
                                                        comboTipoMail.getSelectionModel().select("Seleccione");
                                                    }
                                                } catch (Exception ex) {
                                                    Logger.getLogger(InscripcionServicioClaveDinamicaController.class.getName()).log(Level.SEVERE, ex.toString());
                                                    gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                                                }

                                                final ComboBox<String> comboTipoMecanismo = (ComboBox<String>) root.lookup("#tipoMecanismo");
                                                final ObservableList<String> emptyList2 = FXCollections.emptyObservableList();
                                                comboTipoMecanismo.setItems(emptyList2);
                                                final List<String> listTipoMecanismo = new ArrayList<String>();
                                                listTipoMecanismo.add("Seleccione");
                                                listTipoMecanismo.add("SMS");
                                                listTipoMecanismo.add("E-mail");
                                                comboTipoMecanismo.setItems(FXCollections.observableArrayList(listTipoMecanismo));
                                                comboTipoMecanismo.getSelectionModel().select("Seleccione");

                                                if (Cel.isEmpty() && Mail.isEmpty()) {
                                                    final TextField Avisos = (TextField) root.lookup("#avisos");
                                                    Avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD1);
                                                    Avisos.setEditable(false);
                                                    Avisos.setVisible(true);
                                                    botoncontinuarOp.setDisable(true);
                                                }

                                            } else {
                                                final ComboBox<String> comboTipoMail = (ComboBox<String>) root.lookup("#tipoEmail");
                                                final ObservableList<String> emptyList1 = FXCollections.emptyObservableList();
                                                comboTipoMail.setItems(emptyList1);
                                                final List<String> listTipoMail = new ArrayList<String>();
                                                listTipoMail.add("Seleccione");
                                                listTipoMail.add("Personal");
                                                listTipoMail.add("Laboral");
                                                comboTipoMail.setItems(FXCollections.observableArrayList(listTipoMail));
                                                comboTipoMail.getSelectionModel().select("Seleccione");

                                                final ComboBox<String> comboTipoMecanismo = (ComboBox<String>) root.lookup("#tipoMecanismo");
                                                final ObservableList<String> emptyList2 = FXCollections.emptyObservableList();
                                                comboTipoMecanismo.setItems(emptyList2);
                                                final List<String> listTipoMecanismo = new ArrayList<String>();
                                                listTipoMecanismo.add("Seleccione");
                                                listTipoMecanismo.add("SMS");
                                                listTipoMecanismo.add("E-mail");
                                                comboTipoMecanismo.setItems(FXCollections.observableArrayList(listTipoMecanismo));
                                                comboTipoMecanismo.getSelectionModel().select("Seleccione");

                                                final TextField Avisos = (TextField) root.lookup("#avisos");
                                                Avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD1);
                                                Avisos.setEditable(false);
                                                Avisos.setVisible(true);
                                                botoncontinuarOp.setDisable(true);
                                            }
                                            Atlas.vista.show();

                                        } catch (Exception ex) {
                                            Logger.getLogger(InscripcionServicioClaveDinamicaController.class.getName()).log(Level.SEVERE, ex.toString());
                                            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

                                        }
                                    }
                                });
                            }
                        });

                        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
//                                System.out.println("ERROR EN LA CONSULTA");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

                                        final ComboBox<String> comboTipoMail = (ComboBox<String>) root.lookup("#tipoEmail");
                                        final ObservableList<String> emptyList1 = FXCollections.emptyObservableList();
                                        comboTipoMail.setItems(emptyList1);
                                        final List<String> listTipoMail = new ArrayList<String>();
                                        listTipoMail.add("Seleccione");
                                        listTipoMail.add("Personal");
                                        listTipoMail.add("Laboral");
                                        comboTipoMail.setItems(FXCollections.observableArrayList(listTipoMail));
                                        comboTipoMail.getSelectionModel().select("Seleccione");

                                        final ComboBox<String> comboTipoMecanismo = (ComboBox<String>) root.lookup("#tipoMecanismo");
                                        final ObservableList<String> emptyList2 = FXCollections.emptyObservableList();
                                        comboTipoMecanismo.setItems(emptyList2);
                                        final List<String> listTipoMecanismo = new ArrayList<String>();
                                        listTipoMecanismo.add("Seleccione");
                                        listTipoMecanismo.add("SMS");
                                        listTipoMecanismo.add("E-mail");
                                        comboTipoMecanismo.setItems(FXCollections.observableArrayList(listTipoMecanismo));
                                        comboTipoMecanismo.getSelectionModel().select("Seleccione");

                                        Atlas.vista.show();
                                    }
                                });
                            }
                        });

                        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent t) {
//                                System.out.println("cancelo la tarea");
                            }
                        });

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

    @FXML
    void keyReleasedEmail(KeyEvent event) {
        StringUtilities.borrarClipBoard();
        if (KeyCode.CONTROL.equals(event.getCode())) {

//            System.out.println("Presiono CTRL");
            Runtime rt = Runtime.getRuntime();
            try {
                String comandoEnvio = "cmd.exe /C echo off | clip";
                Process proceso = rt.exec(comandoEnvio);

            } catch (IOException ex) {
                Logger.getLogger(ActualizacionDatosSeguridadController.class.getName()).log(Level.SEVERE, null, ex);
                gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

            }
        }

        if (!KeyCode.CONTROL.equals(event.getCode())) {

            String tipiado = event.getText(); // lo que agrega (caracter)
//            System.out.println("TipiadoEmailConf:" + tipiado + "-");

            final StringUtilities validaciones = new StringUtilities();
//            System.out.println("valido:" + Email.getText());
//            System.out.println("--" + validaciones.validateEmailLuis(Email.getText()));

            if ((Email.getText().isEmpty()
                    && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")
                    && Celular.getText().trim().length() == 10)
                    || (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && validaciones.validateEmail(Email.getText())
                    && !tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && Celular.getText().trim().length() == 10)
                    || (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                    && validaciones.validateEmail(Email.getText())
                    && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")
                    && Celular.getText().trim().length() == 0)) {
                continuar_op.setDisable(false);
                avisos.setEditable(false);
                avisos.setVisible(false);

            } else {
                if (!Email.getText().isEmpty()
                        //                        && validaciones.validateEmailLuis(Email.getText()))
                        && !validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD3); // Email no valido
                    avisos.setEditable(false);
                    avisos.setVisible(true);

                } else if (Celular.getText().trim().length() <= 9 && Celular.getText().trim().length() >= 1) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD2);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 0
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD1);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 10
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 10
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD3);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 10
                        && !tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 0
                        && !tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (Celular.getText().trim().length() == 0
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!Email.getText().isEmpty()
                        //                        && validaciones.validateEmailLuis(Email.getText())
                        && validaciones.validateEmail(Email.getText())
                        && tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione") //                        && Celular.getText().trim().length() == 0
                        ) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } // New
                else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD8); // tipo de mecanismo
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()
                        && Celular.getText().trim().length() == 0) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && Email.getText().isEmpty()
                        && Celular.getText().trim().length() == 10) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD6);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 0
                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                } else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
                        && !validaciones.validateEmail(Email.getText())
                        && Celular.getText().trim().length() == 10
                        && !tipoMecanismo.getSelectionModel().getSelectedItem().equals("Seleccione")) {
                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD3);
                    avisos.setEditable(false);
                    avisos.setVisible(true);
                }

//                 else if (!tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
//                        && validaciones.validateEmail(Email.getText())
//                        && Celular.getText().trim().length() == 0
//                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("SMS")) {
//                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD7);
//                    avisos.setEditable(false);
//                    avisos.setVisible(true);
//                } else if (tipoEmail.getSelectionModel().getSelectedItem().equals("Seleccione")
//                        && !validaciones.validateEmail(Email.getText())
//                        && Celular.getText().trim().length() == 10
//                        && tipoMecanismo.getSelectionModel().getSelectedItem().equals("E-mail")) {
//                    avisos.setText(AtlasConstantes.AVISOS_DATOS_SEGURIDAD4);
//                    avisos.setEditable(false);
//                    avisos.setVisible(true);
//                }
                continuar_op.setDisable(true);
            }
        } else {
            if (KeyCode.CONTROL.equals(event.getCode())) {
                String mailtemp = Email.getText();
//                System.out.println("mailTemp:" + mailtemp);
                StringUtilities.borrarClipBoard();
//                System.out.println("mailFin:" + Email.getText());
                if (!Email.getText().endsWith("@")) {
                    Email.setText(ModeloDatosDeSeguridad.getEmail());
                }
                Email.end();
            }
        }
        StringUtilities.borrarClipBoard();
    }

    public class GetDatosSeguridadTask extends Service<ModeloDatosSeguridad> {

        @Override
        protected Task<ModeloDatosSeguridad> createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {

                    // CONSULTA datos de seguridad             
                    final Cliente datosCliente = Cliente.getCliente();
                    String Respuesta = new String();
                    final StringBuilder tramaDatosSeg = new StringBuilder();
                    final ConectSS servicioSS = new ConectSS();

                    tramaDatosSeg.append("850,012,");
                    tramaDatosSeg.append(datosCliente.getRefid());
                    tramaDatosSeg.append(",");
                    tramaDatosSeg.append(datosCliente.getId_cliente());
                    tramaDatosSeg.append(",");
                    tramaDatosSeg.append(datosCliente.getContraseña());
                    tramaDatosSeg.append(",");
                    tramaDatosSeg.append(datosCliente.getTokenOauth());
                    tramaDatosSeg.append(",~");

//                    System.out.println("trama DatosSeguridad" + tramaDatosSeg.toString());
//                    final ModeloDatosDeSeguridad datosSeguridadConfirm = ModeloDatosSeguridad.getDatosSeguridad();
                    try {

                        if (ModeloDatosDeSeguridad.isIsHappy()) {
                            // Respuesta = "850,012,000,CONNID,1234567,felipe.cuervo@gmail.com,P,3017271441,~";
                            // Respuesta = "850,012,000,CONNID,1234567,,,,~";
                        } else {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ datosSeguridad = " + "##" + gestorDoc.obtenerHoraActual());
//                        Respuesta = "850,012,000,Consulta de datos de seguridad exitosa,N,,P,3002588382,~";

                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaDatosSeg.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDatosSeg.toString());
//                            System.out.println("respuesta Lista : " + Respuesta);

                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());
                        }
                    } catch (Exception ex) {
//                        System.out.println("contingencia");
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                        //envio a contingencia
                        try {
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ datosseguridad = " + "##" + gestorDoc.obtenerHoraActual());
                            //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP_CTG, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaDatosSeg.toString());
                            Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaDatosSeg.toString());
                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + StringUtilities.tramaSubString(Respuesta, 0, 3, ",") + "##" + gestorDoc.obtenerHoraActual());

                        } catch (Exception ex1) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (InscripcionServicioClaveDinamicaController.cancelartarea.get()) {
//                                        System.out.println("cancelo tarea");
                                        cancel();
                                    } else {
                                        new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_ACT_DATOS_SEG);
                                        failed();

                                    }
                                }
                            });

                        }

                    }

                    final ModeloDatosSeguridad datosSeguridad = ModeloDatosSeguridad.getDatosSeguridad();
                    if ("000".equals(Respuesta.split(",")[2])) {

                        if (InscripcionServicioClaveDinamicaController.cancelartarea.get()) {
                            cancel();
                        } else {
//                            final ModeloDatosSeguridad datosSeguridad = ModeloDatosSeguridad.getDatosSeguridad();
                            String[] data = Respuesta.split(",");
                            datosSeguridad.setRegistradoAlertas(data[4].trim());
                            datosSeguridad.setEmail(data[5].trim());
                            datosSeguridad.setTipo_email(data[6].trim());
                            datosSeguridad.setCelular(data[7].trim());
                            ModeloDatosSeguridad.setDatosSeguridad(datosSeguridad);
                        }
                    } else {
                        final String coderror = Respuesta.split(",")[2];
                        final String mensaje = Respuesta.split(",")[3].trim();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (InscripcionServicioClaveDinamicaController.cancelartarea.get()) {
//                                    System.out.println("cancelo tarea");
                                    cancel();
                                } else {

                                    new ModalMensajes(mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                    progreso.progressProperty().unbind();
                                    progreso.setProgress(0);
                                    progreso.setVisible(false);

                                }
                            }
                        });
                    }

                    return ModeloDatosSeguridad.getDatosSeguridad();

                }
            };
        }
    }
}
