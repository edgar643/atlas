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
import com.co.allus.modelo.evidente.ModelEvidente;
import com.co.allus.utils.AtlasConstantes;
import com.co.allus.utils.StringUtilities;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EvidenteInitController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private RadioButton Otros;
    @FXML
    private Button cancelar;
    @FXML
    private RadioButton cedulaC;
    @FXML
    private RadioButton cedulaE;
    @FXML
    private Button continuar_op;
    @FXML
    private RadioButton nuip;
    @FXML
    private Label titulosEvidente;
    @FXML
    private HBox menu_progreso;
    @FXML
    private ProgressBar progreso;
    private transient GestorDocumental gestorDoc = new GestorDocumental();
    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private transient Service<Void> serviceValidarEvidente = continuar_Op();
    private static AtomicBoolean cancelartareaEvidente = new AtomicBoolean();
    private static String tipoClienteIn = "";
    private static String menu1 = "";
    private static int flujo1 = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert Otros != null : "fx:id=\"Otros\" was not injected: check your FXML file 'EvidenteInit.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'EvidenteInit.fxml'.";
        assert cedulaC != null : "fx:id=\"cedulaC\" was not injected: check your FXML file 'EvidenteInit.fxml'.";
        assert cedulaE != null : "fx:id=\"cedulaE\" was not injected: check your FXML file 'EvidenteInit.fxml'.";
        assert continuar_op != null : "fx:id=\"continuar_op\" was not injected: check your FXML file 'EvidenteInit.fxml'.";
        assert menu_progreso != null : "fx:id=\"menu_progreso\" was not injected: check your FXML file 'EvidenteInit.fxml'.";
        assert nuip != null : "fx:id=\"nuip\" was not injected: check your FXML file 'EvidenteInit.fxml'.";
        assert progreso != null : "fx:id=\"progreso\" was not injected: check your FXML file 'EvidenteInit.fxml'.";
        assert titulosEvidente != null : "fx:id=\"titulosEvidente\" was not injected: check your FXML file 'EvidenteInit.fxml'.";

        EvidenteInitController.cancelartareaEvidente.set(false);
        progreso.setVisible(false);

    }

    @FXML
    void cancel_op(ActionEvent event) {

        try {
            Atlas.getVista().gotoPrincipal();
        } catch (IOException ex) {
            gestorDoc.imprimir("ocurrio un error inesperado en la aplicacion -->" + ex.toString());
        }
    }

    @FXML
    void continuar_OP(ActionEvent event) {
        if (validarTipo()) {

            if (serviceValidarEvidente.isRunning()) {
                continuar_op.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceValidarEvidente.progressProperty());
                serviceValidarEvidente.reset();
                serviceValidarEvidente.start();

            } else {
                continuar_op.setDisable(true);
                progreso.setVisible(true);
                progreso.progressProperty().unbind();
                progreso.progressProperty().bind(serviceValidarEvidente.progressProperty());
                serviceValidarEvidente.reset();
                serviceValidarEvidente.start();
            }

            serviceValidarEvidente.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario Presiono Consulta Evidente" + "##" + gestorDoc.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                    EvidenteInitController.cancelartareaEvidente.set(false);
                }
            });

            serviceValidarEvidente.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Usuario cancelo Cambio Mecanismo" + "##" + gestorDoc.obtenerHoraActual());
                    progreso.progressProperty().unbind();
                    progreso.setProgress(0);
                    progreso.setVisible(false);
                }
            });

            serviceValidarEvidente.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
//                    System.out.println("fallo");
                }
            });

        }
    }

    private boolean validarTipo() {
        final ModelEvidente ingresarTipo = new ModelEvidente();
        if (cedulaE.isSelected()) {

            ingresarTipo.setTipoIdentificacion("4");
            tipoClienteIn = "4";
//            validarConsultar("4");
            return true;
        }
        if (Otros.isSelected()) {
//            validarConsultar("ID");
            ingresarTipo.setTipoIdentificacion("ID");
            tipoClienteIn = "ID";
            return true;
        }
        if (nuip.isSelected()) {
//            validarConsultar("0");
            ingresarTipo.setTipoIdentificacion("0");
            tipoClienteIn = "0";
            return true;
        }
        if (cedulaC.isSelected()) {
//            validarConsultar("1");
            ingresarTipo.setTipoIdentificacion("1");
            tipoClienteIn = "1";
            return true;
        }
        return false;
    }

    public Service<Void> continuar_Op() {
        serviceValidarEvidente = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        final String tipoCliente = tipoClienteIn;

                        if ("ID".equals(tipoCliente)) {
                            if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
                                final EvidenteTrxFalloController controller = new EvidenteTrxFalloController();
                                controller.mostrarEvidenteFallo(menu1);
                            }
                            if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                // Pantalla fallo Enrolamiento
                                final InscripcionServicoClaveDinamicaTrxFalloController controller = new InscripcionServicoClaveDinamicaTrxFalloController();
                                controller.mostrarInscripcionServicoClaveDinamicaTrxFallo(menu1, flujo1);
                            }
                            if (flujo1 == AtlasConstantes.EVIDENTE_DESBLOQUEO_CLAVE_DINAMICA) {
                                final EvidenteTrxFalloController controller = new EvidenteTrxFalloController();
                                controller.mostrarEvidenteFallo(menu1);
                            }
                        } else {

                            if (!"1".equals(tipoCliente) && flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                final InscripcionServicoClaveDinamicaTrxFalloController controller = new InscripcionServicoClaveDinamicaTrxFalloController();
                                controller.mostrarInscripcionServicoClaveDinamicaTrxFallo(menu1, flujo1);
                            } else {

                                final Cliente datosCliente = Cliente.getCliente();

                                final String idCliente = datosCliente.getCliente().getId_cliente();
                                //                        final String tipoCliente = tipo;

                                final String nombreCliente = datosCliente.getCliente().getNombre();
                                final String connid = datosCliente.getCliente().getRefid();
                                final Date fecha = new Date();

                                final StringBuilder tramaValidarClienteEvidente = new StringBuilder();

                                //       922,001,00CDOVIO3SBBPFSFE8LH9B5AES00RJPV,728,007,001,1046666873;1;Apellido1;JUAN FERNANDO BEDOYA ARAQUE;13112013,0227,alexander.lopez.o,71782820,~
                                tramaValidarClienteEvidente.append("922,001,");
                                tramaValidarClienteEvidente.append(connid);
                                if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
                                    tramaValidarClienteEvidente.append(",2732,");
                                }
                                if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                    tramaValidarClienteEvidente.append(",2733,");
                                }
                                tramaValidarClienteEvidente.append("007,001,");
                                tramaValidarClienteEvidente.append(idCliente);
                                tramaValidarClienteEvidente.append(";");
                                tramaValidarClienteEvidente.append(tipoCliente);
                                tramaValidarClienteEvidente.append(";Apellido1;");
                                tramaValidarClienteEvidente.append(nombreCliente);
                                tramaValidarClienteEvidente.append(";");
                                tramaValidarClienteEvidente.append(formatoFecha.format(fecha));
                                tramaValidarClienteEvidente.append(",");
                                if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
                                    tramaValidarClienteEvidente.append("0427,");
                                }
                                if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                    tramaValidarClienteEvidente.append("0327,");
                                }
                                if (flujo1 == AtlasConstantes.EVIDENTE_DESBLOQUEO_CLAVE_DINAMICA) {
                                    tramaValidarClienteEvidente.append("0127,");
                                }
                                tramaValidarClienteEvidente.append(StringUtilities.traerUsRed());
                                tramaValidarClienteEvidente.append(","); // Usuario Red
                                tramaValidarClienteEvidente.append("43869940"); // cc Autentificador        
                                tramaValidarClienteEvidente.append(",~");

                                final StringBuilder respuesta = new StringBuilder();
                                final ModelEvidente modeloVal = new ModelEvidente();
                                try {

                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a WAS = " + tramaValidarClienteEvidente.toString() + "##" + gestorDoc.obtenerHoraActual());

                                    if (modeloVal.isIsHappy()) {
                                        // PRUEBA RESPUESTA NO OK
                                        //                            respuesta.append("922,001,<?xml version=\"1.0\" encoding=\"UTF-8\"?><RespValidacion valApellido=\"true\" valNombre=\"true\" valFechaExp=\"true\" excluirCliente=\"false\" alertas=\"false\" respuestaAlerta=\"03\" codigoAlerta=\"00\" resultado=\"01\" regValidacion=\"5691142\" resultadoProceso=\"true\" consultasDisponibles=\"1\"><Identificacion numero=\"01046666873\"tipo=\"1\" /><Nombre>BEDOYA ARAQUE JUAN FERNANDO</Nombre><FechaExpedicion timestamp=\"1153958400000\"/></RespValidacion>,02,null,Resultado del proceso de validación: true,~");
                                        // PRUEBA RESPUESTA OK

                                        respuesta.append("922,001,<?xml version=\"1.0\" encoding=\"UTF-8\"?><RespValidacion valApellido=\"true\" valNombre=\"true\" valFechaExp=\"true\" excluirCliente=\"false\" alertas=\"false\" respuestaAlerta=\"03\" codigoAlerta=\"00\" resultado=\"01\" regValidacion=\"5691142\" resultadoProceso=\"true\" consultasDisponibles=\"1\"><Identificacion numero=\"01046666873\"tipo=\"1\" /><Nombre>BEDOYA ARAQUE JUAN FERNANDO</Nombre><FechaExpedicion timestamp=\"1153958400000\"/></RespValidacion>,01,null,Resultado del proceso de validación: true,~");
                                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  WAS = " + StringUtilities.tramaSubString(respuesta.toString(), 0, 1, ",") + "##" + gestorDoc.obtenerHoraActual());
                                    } else {
                                        final ConectSS servicioSS = new ConectSS();
                                        respuesta.append(servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_EVIDENTE, AtlasConstantes.PUERTO_COM_EVIDENTE, tramaValidarClienteEvidente.toString()));

                                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  WAS = " + StringUtilities.tramaSubString(respuesta.toString(), 0, 1, ",") + "##" + gestorDoc.obtenerHoraActual());
                                    }
                                } catch (Exception ex) {

                                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal WAS = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                                    //envio a contingencia
                                    try {
                                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg WAS = " + tramaValidarClienteEvidente.toString() + "##" + gestorDoc.obtenerHoraActual());
                                        final ConectSS servicioSS = new ConectSS();
                                        //                String respuesta  = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_EVIDENTE_CTG, AtlasConstantes.PUERTO_COM_EVIDENTE_CTG, tramaValidarClienteEvidente.toString());
                                        respuesta.append(servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_EVIDENTE_CTG, AtlasConstantes.PUERTO_COM_EVIDENTE_CTG, tramaValidarClienteEvidente.toString()));
                                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg WAS = " + StringUtilities.tramaSubString(respuesta.toString(), 0, 1, ",") + "##" + gestorDoc.obtenerHoraActual());

                                    } catch (Exception ex1) {

                                        // Error de conexion
                                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (EvidenteInitController.cancelartareaEvidente.get()) {

                                                    cancel();
                                                } else {
                                                    new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                                    continuar_op.setDisable(false);
                                                    progreso.progressProperty().unbind();
                                                    progreso.setProgress(0);
                                                    progreso.setVisible(false);
                                                }

                                            }
                                        });
                                    }
                                }
                                try {
                                    final String datos[] = respuesta.toString().split(",");

                                    if (datos[1].equals("998") || datos[1].equals("997") || datos[1].equals("996")) {

                                        //                                Aviso Respuesta mala
                                        final String coderror = datos[2];
                                        final String mensaje = "NO SE PUDO REALIZAR LA TRANSACCION";

                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (EvidenteInitController.cancelartareaEvidente.get()) {

                                                    cancel();
                                                } else {
//                                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_EVIDENTE);
                                                    new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                                                    continuar_op.setDisable(false);
                                                    progreso.progressProperty().unbind();
                                                    progreso.setProgress(0);
                                                    progreso.setVisible(false);
                                                }
                                            }
                                        });

                                    } else {
                                        if ("01".equals(datos[3])) {
                                            // Aqui consulta de preguntas
                                            try {

                                                final String temp1 = datos[2].split("<")[2].replaceAll(" ", ",");

                                                final String registroValidacion = temp1.split(",")[9].split("=")[1].replaceAll("\"", "");
                                                final String resultadoProceso = temp1.split(",")[10].split("=")[1].replaceAll("\"", "");

                                                final ModelEvidente modelo = new ModelEvidente();
                                                modelo.setRegValidacion(registroValidacion); // #
                                                modelo.setResPorcesoValidacion(resultadoProceso); // true , false

                                                // CONSTRUYO CONSULTA DE PREGUNTAS
                                                //                                        Thread.sleep(3000);
                                                try {
                                                    final StringBuilder tramaSolicitarPreguntaClienteEvidente = new StringBuilder();
                                                    //                                        Ejemplo
                                                    //                                        922,004,00CDOVIO3SBBPFSFE8LH9B5AES00RJPV,728,007,001,<?xml version="1.0" encoding="UTF-8"?><SolicitudCuestionario tipoId="1" identificacion="1046666873" regValidacion="5691142" />,0228,alexander.lopez.o,71782820,~
                                                    tramaSolicitarPreguntaClienteEvidente.append("922,004,");
                                                    tramaSolicitarPreguntaClienteEvidente.append(connid);

                                                    if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
                                                        tramaSolicitarPreguntaClienteEvidente.append(",2732,");
                                                    }
                                                    if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                                        tramaSolicitarPreguntaClienteEvidente.append(",2733,");
                                                    }
                                                    tramaSolicitarPreguntaClienteEvidente.append("007,001,");

//                                                    tramaSolicitarPreguntaClienteEvidente.append(",728,007,001,");
                                                    //                                            tramaSolicitarPreguntaClienteEvidente.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><SolicitudCuestionario tipoId=\"1\" identificacion=\"1046666873\" regValidacion=\"5691142\" />");
                                                    tramaSolicitarPreguntaClienteEvidente.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                                                    final String add = "<SolicitudCuestionario tipoId=\"" + tipoCliente + "\" identificacion=\"" + idCliente + "\" regValidacion=\"" + registroValidacion + "\" />,";
                                                    tramaSolicitarPreguntaClienteEvidente.append(add);

//                                                    tramaSolicitarPreguntaClienteEvidente.append(",0128,");
                                                    if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
//                                                        tramaSolicitarPreguntaClienteEvidente.append("0128,");
                                                        tramaSolicitarPreguntaClienteEvidente.append("0428,");
                                                    }
                                                    if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                                        tramaSolicitarPreguntaClienteEvidente.append("0328,");
                                                    }
                                                    if (flujo1 == AtlasConstantes.EVIDENTE_DESBLOQUEO_CLAVE_DINAMICA) {
                                                        tramaSolicitarPreguntaClienteEvidente.append("0128,");
                                                    }

                                                    tramaSolicitarPreguntaClienteEvidente.append(StringUtilities.traerUsRed());
                                                    tramaSolicitarPreguntaClienteEvidente.append(","); // Usuario Red
                                                    tramaSolicitarPreguntaClienteEvidente.append("43869940"); // cc Autentificador        
                                                    tramaSolicitarPreguntaClienteEvidente.append(",~");

                                                    final StringBuilder respuestaPreguntas = new StringBuilder();
                                                    try {

                                                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a WAS = " + tramaSolicitarPreguntaClienteEvidente.toString() + "##" + gestorDoc.obtenerHoraActual());

                                                        if (modeloVal.isIsHappy()) {

                                                            // PRUEBA RESPUESTA NO OK
                                                            //respuestaPreguntas.append("922,004,<?xml version=\"1.0\" encoding=\"UTF-8\"?><Cuestionario id=\"00023389\" resultado=\"01\" registro=\"4234304\" excluirCliente=\"false\" alertas=\"false\" respuestaAlerta=\"\" codigoAlerta=\"00\"><Pregunta id=\"005014501\" texto=\"EN EL MES DE ENERO DE 2016 CUANTAS LINEAS ABIERTAS O ACTIVAS EN POSTPAGO TENIA REGISTRADAS A SU NOMBRE CON &quot;CLARO MOVIL (COMCEL) &quot; :\" orden=\"1\" idRespuestaCorrecta=\"00\" peso=\"4\"><Respuesta id=\"001\" texto=\"ENTRE 1 - 5\" /><Respuesta id=\"002\" texto=\"ENTRE 6 - 10\" /><Respuesta id=\"003\" texto=\"MAS DE 10\" /><Respuesta id=\"004\" texto=\"NO TENIA LINEA CON EL OPERADOR\" /></Pregunta><Pregunta id=\"005002005\" texto=\"LOS NUMEROS 8455 CORRESPONDEN A LOS 4 ULTIMOS DIGITOS DE SU TARJETA DE CREDITO ACTIVA/VIGENTE CON :\" orden=\"2\" idRespuestaCorrecta=\"00\" peso=\"4\"><Respuesta id=\"001\" texto=\"BANCO AV VILLAS (AHORRAMAS)\" /><Respuesta id=\"002\" texto=\"BANCO DE OCCIDENTE (BCO UNION, ALIADAS,INTERBANCO)\" /><Respuesta id=\"003\" texto=\"FINANZAUTO S.A FACTORING\" /><Respuesta id=\"004\" texto=\"BANCOLOMBIA (CONAVI, BIC)\" /><Respuesta id=\"005\" texto=\"BCSC (CAJA SOCIAL, COLMENA)\" /><Respuesta id=\"006\" texto=\"NINGUNA DE LAS ANTERIORES\" /></Pregunta><Pregunta id=\"005006001\" texto=\"HACE CUANTO TIEMPO TIENE USTED UNA TARJETA DE CREDITO CON &quot;BCO COLPATRIA&quot;\" orden=\"3\" idRespuestaCorrecta=\"00\" peso=\"4\"><Respuesta id=\"001\" texto=\"0 -  3   AÑOS\" /><Respuesta id=\"002\" texto=\"4 -  7   AÑOS\" /><Respuesta id=\"003\" texto=\"8 - 12   AÑOS\" /><Respuesta id=\"004\" texto=\"13 - 17   AÑOS\" /><Respuesta id=\"005\" texto=\"18 O MAS  AÑOS\" /><Respuesta id=\"006\" texto=\"NO TENGO TARJETA DE CREDITO CON LA ENTIDAD\" /></Pregunta><Pregunta id=\"009001001\" texto=\"DE LAS SIGUIENTES DIRECCIONES INDIQUE CON CUAL USTED HA TENIDO O TIENE ALGUN VINCULO\" orden=\"4\" idRespuestaCorrecta=\"00\" peso=\"3\"><Respuesta id=\"001\" texto=\"CL 52 46 45 APTO 403 ED LOS BUCAROS BR CE\" /><Respuesta id=\"002\" texto=\"CL 52 45 44 APTO 402 ED LOS BUCAROS BR CE\" /><Respuesta id=\"003\" texto=\"CL 51 44 44 APTO 404 ED LOS BUCAROS BR CE\" /><Respuesta id=\"004\" texto=\"NINGUNA DE LAS ANTERIORES\" /></Pregunta></Cuestionario>,02,null,null,~");
                                                            // PRUEBA RESPUESTA OK
                                                            respuestaPreguntas.append("922,004,<?xml version=\"1.0\" encoding=\"UTF-8\"?><Cuestionario id=\"00023389\" resultado=\"01\" registro=\"4234304\" excluirCliente=\"false\" alertas=\"false\" respuestaAlerta=\"\" codigoAlerta=\"00\"><Pregunta id=\"005014501\" texto=\"EN EL MES DE ENERO DE 2016 CUANTAS LINEAS ABIERTAS O ACTIVAS EN POSTPAGO TENIA REGISTRADAS A SU NOMBRE CON &quot;CLARO MOVIL (COMCEL) &quot; :\" orden=\"1\" idRespuestaCorrecta=\"00\" peso=\"4\"><Respuesta id=\"001\" texto=\"ENTRE 1 - 5\" /><Respuesta id=\"002\" texto=\"ENTRE 6 - 10\" /><Respuesta id=\"003\" texto=\"MAS DE 10\" /><Respuesta id=\"004\" texto=\"NO TENIA LINEA CON EL OPERADOR\" /></Pregunta><Pregunta id=\"005002005\" texto=\"LOS NUMEROS 8455 CORRESPONDEN A LOS 4 ULTIMOS DIGITOS DE SU TARJETA DE CREDITO ACTIVA/VIGENTE CON :\" orden=\"2\" idRespuestaCorrecta=\"00\" peso=\"4\"><Respuesta id=\"001\" texto=\"BANCO AV VILLAS (AHORRAMAS)\" /><Respuesta id=\"002\" texto=\"BANCO DE OCCIDENTE (BCO UNION, ALIADAS,INTERBANCO)\" /><Respuesta id=\"003\" texto=\"FINANZAUTO S.A FACTORING\" /><Respuesta id=\"004\" texto=\"BANCOLOMBIA (CONAVI, BIC)\" /><Respuesta id=\"005\" texto=\"BCSC (CAJA SOCIAL, COLMENA)\" /><Respuesta id=\"006\" texto=\"NINGUNA DE LAS ANTERIORES\" /></Pregunta><Pregunta id=\"005006001\" texto=\"HACE CUANTO TIEMPO TIENE USTED UNA TARJETA DE CREDITO CON &quot;BCO COLPATRIA&quot;\" orden=\"3\" idRespuestaCorrecta=\"00\" peso=\"4\"><Respuesta id=\"001\" texto=\"0 -  3   AÑOS\" /><Respuesta id=\"002\" texto=\"4 -  7   AÑOS\" /><Respuesta id=\"003\" texto=\"8 - 12   AÑOS\" /><Respuesta id=\"004\" texto=\"13 - 17   AÑOS\" /><Respuesta id=\"005\" texto=\"18 O MAS  AÑOS\" /><Respuesta id=\"006\" texto=\"NO TENGO TARJETA DE CREDITO CON LA ENTIDAD\" /></Pregunta><Pregunta id=\"009001001\" texto=\"DE LAS SIGUIENTES DIRECCIONES INDIQUE CON CUAL USTED HA TENIDO O TIENE ALGUN VINCULO\" orden=\"4\" idRespuestaCorrecta=\"00\" peso=\"3\"><Respuesta id=\"001\" texto=\"CL 52 46 45 APTO 403 ED LOS BUCAROS BR CE\" /><Respuesta id=\"002\" texto=\"CL 52 45 44 APTO 402 ED LOS BUCAROS BR CE\" /><Respuesta id=\"003\" texto=\"CL 51 44 44 APTO 404 ED LOS BUCAROS BR CE\" /><Respuesta id=\"004\" texto=\"NINGUNA DE LAS ANTERIORES\" /></Pregunta></Cuestionario>,01,null,null,~");
                                                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  WAS = " + respuesta.toString() + "##" + gestorDoc.obtenerHoraActual());
                                                        } else {
                                                            final ConectSS servicioSS = new ConectSS();
                                                            respuestaPreguntas.append(servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_EVIDENTE, AtlasConstantes.PUERTO_COM_EVIDENTE, tramaSolicitarPreguntaClienteEvidente.toString()));

                                                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  WAS = " + StringUtilities.tramaSubString(respuestaPreguntas.toString() , 0, 1, ",")+ "##" + gestorDoc.obtenerHoraActual());
                                                        }
                                                    } catch (Exception ex) {

                                                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal WAS = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                                                        //envio a contingencia
                                                        try {
                                                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg WAS = " + tramaSolicitarPreguntaClienteEvidente.toString() + "##" + gestorDoc.obtenerHoraActual());

                                                            final ConectSS servicioSS = new ConectSS();
                                                            //                String respuesta  = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_EVIDENTE_CTG, AtlasConstantes.PUERTO_COM_EVIDENTE_CTG, tramaValidarClienteEvidente.toString());
                                                            respuestaPreguntas.append(servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_EVIDENTE_CTG, AtlasConstantes.PUERTO_COM_EVIDENTE_CTG, tramaSolicitarPreguntaClienteEvidente.toString()));

                                                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg WAS = " + StringUtilities.tramaSubString(respuestaPreguntas.toString() , 0, 1, ",")+ "##" + gestorDoc.obtenerHoraActual());

                                                        } catch (Exception ex1) {

                                                            // Error de conexion
                                                            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                                                            Platform.runLater(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if (EvidenteInitController.cancelartareaEvidente.get()) {

                                                                        cancel();
                                                                    } else {
                                                                        new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                                                        continuar_op.setDisable(false);
                                                                        progreso.progressProperty().unbind();
                                                                        progreso.setProgress(0);
                                                                        progreso.setVisible(false);
                                                                    }

                                                                }
                                                            });
                                                        }

                                                    }

                                                    try {
                                                        final String datosPreguntas[] = respuestaPreguntas.toString().split(",");

                                                        if (datos[1].equals("998") || datos[1].equals("997") || datos[1].equals("996")) {

                                                            //                                Aviso de conexion mala
                                                            final String coderror = datos[2];
                                                            final String mensaje = "NO SE PUDO REALIZAR LA TRANSACCION";

                                                            Platform.runLater(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if (EvidenteInitController.cancelartareaEvidente.get()) {
                                                                        cancel();
                                                                    } else {

//                                                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.MODAL_EVIDENTE);
                                                                        new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);

                                                                        continuar_op.setDisable(false);
                                                                        progreso.progressProperty().unbind();
                                                                        progreso.setProgress(0);
                                                                        progreso.setVisible(false);
                                                                    }
                                                                }
                                                            });

                                                        } else {

                                                            final int sizeDp = datosPreguntas.length - 1;

                                                            // lleva el replace
                                                            final StringBuilder sincomas = new StringBuilder();

                                                            for (int i = 2; i < sizeDp - 3; i++) {

                                                                final StringBuilder data = new StringBuilder();

                                                                if (i == sizeDp - 4) {
                                                                    data.append(datosPreguntas[i]);
                                                                } else {
                                                                    data.append(datosPreguntas[i] + "###");
                                                                }

                                                                sincomas.append(data);
                                                            }

                                                            final String TramaNew = datosPreguntas[0] + "," + datosPreguntas[1] + "," + sincomas.toString() + "," + datosPreguntas[sizeDp - 3] + "," + datosPreguntas[sizeDp - 2] + "," + datosPreguntas[sizeDp - 1] + "," + datosPreguntas[sizeDp];

                                                            //                                            if ("01".equals(datosPreguntas[3])) {
                                                            if ("01".equals(datosPreguntas[sizeDp - 3])) {
                                                                //                                                   todo OK
                                                                //                                                leer las preguntas y pasar a siguiente ventana

                                                                //                                                final ModelEvidente EvidenteModel = cargarPreguntas(respuestaPreguntas.toString());
                                                                final ModelEvidente EvidenteModel = cargarPreguntas(TramaNew);

                                                                final EvidenteTrxpreguntasController controller = new EvidenteTrxpreguntasController();
                                                                controller.mostrarEvidenteCuestionarioPreguntas(EvidenteModel, menu1, flujo1);

                                                            } else {
                                                                // Que respondo¿?

                                                                //Mostrar proceso de validacion no exitoso
                                                                if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
                                                                    final EvidenteTrxFalloController controller = new EvidenteTrxFalloController();
                                                                    controller.mostrarEvidenteFallo(menu1);
                                                                }
                                                                if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                                                    final InscripcionServicoClaveDinamicaTrxFalloController controller = new InscripcionServicoClaveDinamicaTrxFalloController();
                                                                    controller.mostrarInscripcionServicoClaveDinamicaTrxFallo(menu1, flujo1);
                                                                }
                                                                if (flujo1 == AtlasConstantes.EVIDENTE_DESBLOQUEO_CLAVE_DINAMICA) {
                                                                    final EvidenteTrxFalloController controller = new EvidenteTrxFalloController();
                                                                    controller.mostrarEvidenteFallo(menu1);
                                                                }
                                                            }

                                                        }

                                                    } catch (Exception ex) {
                                                        Logger.getLogger(EvidenteInitController.class.getName()).log(Level.SEVERE, null, ex);

                                                    }
                                                } catch (Exception ex) {
                                                    Logger.getLogger(EvidenteInitController.class.getName()).log(Level.SEVERE, null, ex);

                                                }
                                            } catch (Exception ex) {
                                                Logger.getLogger(EvidenteInitController.class.getName()).log(Level.SEVERE, null, ex);

                                            }

                                        } else {
                                            //Mostrar proceso de validacion no exitoso
//                                            final EvidenteTrxFalloController controller = new EvidenteTrxFalloController();
//                                            controller.mostrarEvidenteFallo(menu1);

                                            //Mostrar proceso de validacion no exitoso
                                            if (flujo1 == AtlasConstantes.EVIDENTE_CAMBIO_DE_MECANISMO) {
                                                final EvidenteTrxFalloController controller = new EvidenteTrxFalloController();
                                                controller.mostrarEvidenteFallo(menu1);
                                            }
                                            if (flujo1 == AtlasConstantes.EVIDENTE_INSCRIPCION_ENROLAMIENTO) {
                                                final InscripcionServicoClaveDinamicaTrxFalloController controller = new InscripcionServicoClaveDinamicaTrxFalloController();
                                                controller.mostrarInscripcionServicoClaveDinamicaTrxFallo(menu1, flujo1);
                                            }
                                            if (flujo1 == AtlasConstantes.EVIDENTE_DESBLOQUEO_CLAVE_DINAMICA) {
                                                final EvidenteTrxFalloController controller = new EvidenteTrxFalloController();
                                                controller.mostrarEvidenteFallo(menu1);
                                            }
                                        }
                                    }
                                } catch (Exception ex) {
                                    Logger.getGlobal().log(Level.SEVERE, ex.toString());
                                }
                            }
                        }

                        return null;
                    }
                };
            }
        };

        return serviceValidarEvidente;

    }


    public ModelEvidente cargarPreguntas(final String trama) {
//        modelo.setRegValidacion(registroValidacion); // #
//        modelo.setResPorcesoValidacion(resultadoProceso); // true , false

        final String datosTrama[] = trama.split(",");

        final String temp1 = datosTrama[2].split("<")[2].replaceAll(" ", ",");

        final String idCuestionario = temp1.split(",")[1].split("=")[1].replaceAll("\"", "");
        final String regCuestionario = temp1.split(",")[3].split("=")[1].replaceAll("\"", "");

        final ModelEvidente EvidenteModel = new ModelEvidente();

        EvidenteModel.setIdCuestionario(idCuestionario);
        EvidenteModel.setRegCuestionario(regCuestionario);

//        HashMap<String,List> PregAndResp = new HashMap<String,List>();
        String PregAndResp;

        final String porPreguntas[] = datosTrama[2].split("<Pregunta");

        final StringBuilder pregRespString = new StringBuilder();

        for (int i = 1; i < porPreguntas.length; i++) {
//            final String temPregunta = porPreguntas[i].replaceAll(" ", ","); 

            String pregunta = porPreguntas[i].split("=")[2].replaceAll("\"", "").replaceAll("&quot;", "\"").replaceAll("orden", "");

            pregRespString.append(pregunta + "%");

            String respuestas[] = porPreguntas[i].split("<Respuesta");

            for (int j = 1; j < respuestas.length; j++) {
                String tempResp[] = respuestas[j].split("=");                // id,texto
                String idRespuesta = tempResp[1].replaceAll("\"", "").split(" ")[0].trim() + "," + tempResp[2].replaceAll("\"", "").replaceAll("/></Pregunta></Cuestionario>", "").replaceAll("/></Pregunta>", "").replaceAll("/>", "").trim() + "&";
                pregRespString.append(idRespuesta);
            }

            if (pregRespString.toString().endsWith("$")) {
                final String temp = pregRespString.toString();
                pregRespString.delete(0, pregRespString.length());
                final String tempO = temp.substring(0, temp.length() - 1);
                pregRespString.append(tempO);
            }

//            PregAndResp.put("Pregunta"+i, temp.toString());
        }

        if (pregRespString.toString().endsWith("$")) {
            final String temp = pregRespString.toString();
            pregRespString.delete(0, pregRespString.length());
            final String tempO = temp.substring(0, temp.length() - 1);
            pregRespString.append(tempO);
        }

        EvidenteModel.setPregAndResp(pregRespString.toString());

        return EvidenteModel;

    }

    @FXML
    void selOtros(ActionEvent event) {
        if (Otros.isSelected()) {
            nuip.setSelected(false);
            cedulaE.setSelected(false);
            cedulaC.setSelected(false);
            continuar_op.setDisable(false);
        } else {
            continuar_op.setDisable(true);
        }
    }

    @FXML
    void selcedulaC(ActionEvent event) {
        if (cedulaC.isSelected()) {
            nuip.setSelected(false);
            cedulaE.setSelected(false);
            Otros.setSelected(false);
            continuar_op.setDisable(false);
        } else {
            continuar_op.setDisable(true);
        }

    }

    @FXML
    void selcedulaE(ActionEvent event) {
        if (cedulaE.isSelected()) {
            nuip.setSelected(false);
            cedulaC.setSelected(false);
            Otros.setSelected(false);
            continuar_op.setDisable(false);
        } else {
            continuar_op.setDisable(true);
        }

    }

    @FXML
    void selnuip(ActionEvent event) {
        if (nuip.isSelected()) {
            cedulaC.setSelected(false);
            cedulaE.setSelected(false);
            Otros.setSelected(false);
            continuar_op.setDisable(false);
        } else {
            continuar_op.setDisable(true);
        }
    }

    public void mostrarEvidenteInit(final String menu, final int flujo) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {

                        final URL location = getClass().getResource("/com/co/allus/vistas/EvidenteInit.fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        final AnchorPane root = (AnchorPane) fxmlLoader.load(location.openStream());
                        final Parent frameGnral = Atlas.vista.getScene().getRoot();
                        final GridPane pane = (GridPane) frameGnral.lookup("#Panel_datos");

                        final Label label_menu = (Label) pane.lookup("#label_saldos");
                        final Button cancelar = (Button) root.lookup("#cancelar");
                        final Button botoncontinuarOp = (Button) root.lookup("#continuar_op");

                        final Label titulosEvidente = (Label) root.lookup("#titulosEvidente");
                        titulosEvidente.setText(menu);

                        menu1 = menu;
                        flujo1 = flujo;

                        /// codigo para inyectar los datos                   
                        final Cliente datosCliente = Cliente.getCliente();
                        final Label cliente = (Label) frameGnral.lookup("#cliente");
                        // final BigDecimal dato = new BigDecimal((datosCliente.getNombre().length() / 2d));
                        // final BigDecimal setScale = dato.setScale(0, BigDecimal.ROUND_UP);
                        // final String info = datosCliente.getNombre() + "\n" + StringUtilities.rellenarDato(" ", setScale.intValue(), " ") + "C.C: " + datosCliente.getId_cliente();
                        final String info = datosCliente.getNombre() + "\nC.C: " + datosCliente.getId_cliente();
                        cliente.setText("");
                        cliente.setText(info);

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
                        Logger.getLogger(EvidenteInitController.class.getName()).log(Level.SEVERE, null, ex);

                    }

                }
            });

        } catch (Exception ex) {
            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

        }

    }
}
