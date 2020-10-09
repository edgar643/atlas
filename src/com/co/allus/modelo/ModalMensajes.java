/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo;

import com.co.allus.gestor.documental.GestorDocumental;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author alexander.lopez.o
 */
public class ModalMensajes extends Stage {

    private Stage owner;
    public static Stage stage;
    private Parent root;
    private String mensaje;
    private int tipoMensaje;
    private String Titulo;
    public static int MENSAJE_ERROR = 0;
    public static int MENSAJE_ADVERTENCIA = 1;
    public static int MENSAJE_INFORMACION = 2;
    private String MensajeDesc;
    private String tituloVentana;
    private String TipoMensaje;
    public static int MODAL_SALDOS = 1;
    public static int MODAL_PAGOS_TERCEROS = 2;
    public static int MODAL_PAGOS_TRANSF_CTA_PROP = 3;
    public static int MODAL_PAGOS_TDC_PROPIA = 4;
    public static int MODAL_PAGOS_TDC_NO_PROPIA = 5;
    public static int MODAL_DESBLOQUEO_SEGCLAVE = 6;
    public static int MODAL_SALDO_HIP = 10;
    public static int MODAL_SALDO_TDC = 7;
    public static int MODAL_BLOQUEOS_TDC = 8;
    public static int MODAL_BLOQUEOS_CLAVES_TARJETAS = 9;
    public static int DEFAULT = 0;
    public static int MODAL_EVIDENTE = 11;
    public static int MODAL_ACT_DATOS_SEG = 12;
     public static int MODAL_SALIRAPP = 99;
    private static int Modal;
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    public ModalMensajes(final String Mensaje, final String TituloVentana, int TipoMensaje, int tipoModal) {
        final URL location = getClass().getResource("/com/co/allus/vistas/ModalMensajes.fxml");
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            this.mensaje = Mensaje;
            this.Titulo = TituloVentana;
            this.tipoMensaje = TipoMensaje;
            this.Modal = tipoModal;
            stage = this;
            root = (Parent) fxmlLoader.load(location.openStream());
            setTitle(Titulo);
            final Image image = new Image(getClass().getResourceAsStream("/com/co/allus/recursos/allusico.PNG"));
            getIcons().add(image);
            initComponents();


        } catch (IOException ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }
    }

    private void initComponents() {
        final Scene scene = new Scene(root);
        setScene(scene);
        final Label Mensaje_desc = (Label) root.lookup("#mensaje");
        final ImageView icono = (ImageView) root.lookup("#icono");
        Mensaje_desc.setText(mensaje);

        switch (tipoMensaje) {
            case 0:
                icono.setImage(new Image(getClass().getResourceAsStream("/com/co/allus/recursos/error.png")));
                break;
            case 1:
                icono.setImage(new Image(getClass().getResourceAsStream("/com/co/allus/recursos/warning-icon.png")));
                break;
            case 2:
                icono.setImage(new Image(getClass().getResourceAsStream("/com/co/allus/recursos/info.png")));
                break;
            default:
                break;

        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void cerrarVentana() {
        stage.close();
    }

    public static Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public static int getModal() {
        return Modal;
    }

    public static void setModal(int Modal) {
        ModalMensajes.Modal = Modal;
    }
}
