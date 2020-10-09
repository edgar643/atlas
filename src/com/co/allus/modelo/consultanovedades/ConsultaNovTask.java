/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultanovedades;

import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.controladoresfxml.ConsultaNovedadesOtpController;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.utils.AtlasConstantes;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author alexander.lopez.o
 */
public class ConsultaNovTask extends Service<ObservableList<infoTablaNovedades>> {

    private transient final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private transient final SimpleDateFormat formatoHora = new SimpleDateFormat("kkmmss", Locale.getDefault());
//    private transient final SimpleDateFormat formatoFechaData = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//    private transient final SimpleDateFormat formatoHoraData = new SimpleDateFormat("kk:mm", Locale.getDefault());
    private transient final GestorDocumental gestorDoc = new GestorDocumental();

    @Override
    protected Task<ObservableList<infoTablaNovedades>> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                ObservableList<infoTablaNovedades> novedades = FXCollections.observableArrayList();
                String Respuesta = new String();

                final StringBuilder tramaConsultaNov = new StringBuilder();
                final ConectSS servicioSS = new ConectSS();
                final Cliente cliente = Cliente.getCliente();
                final Date fecha = new Date();
                infoNovedades datosNov = infoNovedades.getInfoNovedades();
                //850,015,connid,codtransaccion,docid,fecha,hora,CNV,clave,fechaIni(8),fechaFin(8)~
                final Set<String> keySet = CodigosNovedades.codigosProd.keySet();
                String CodNovedad = "";

                for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
                    final String codigo = it.next();
                    if (CodigosNovedades.codigosProd.get(codigo).equalsIgnoreCase(datosNov.getNovedad())) {
                        CodNovedad = codigo;
                        break;
                    }
                }

                tramaConsultaNov.append("850,015,");
                tramaConsultaNov.append(cliente.getRefid());
                tramaConsultaNov.append(",");
                tramaConsultaNov.append(AtlasConstantes.COD_CONSULTA_NOV);
                tramaConsultaNov.append(",");
                tramaConsultaNov.append(cliente.getId_cliente());
                tramaConsultaNov.append(",");
                tramaConsultaNov.append(formatoFecha.format(fecha));
                tramaConsultaNov.append(",");
                tramaConsultaNov.append(formatoHora.format(fecha));
                tramaConsultaNov.append(",");
                tramaConsultaNov.append("CNV");
                tramaConsultaNov.append(",");
                tramaConsultaNov.append(cliente.getContraseña());
                tramaConsultaNov.append(",");
                tramaConsultaNov.append(datosNov.getFechainicial());
                tramaConsultaNov.append(",");
                tramaConsultaNov.append(datosNov.getFechaFinal());
                tramaConsultaNov.append(",");
                tramaConsultaNov.append(CodNovedad);
                tramaConsultaNov.append(",");
                tramaConsultaNov.append(cliente.getTokenOauth());
                tramaConsultaNov.append(",~");

                try {
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Envio a MQ Consulta de Novedades = " + "##" + gestorDoc.obtenerHoraActual());

                    //        Respuesta = "850,015,000,0000000000,10##Enrolado##Exitoso##20160406##095411;11##Desenrolado##Exitoso##20160406##095233;12##Retiro Voluntario##Exitoso##20160406##095411;18##Enrolado##Exitoso##20160406##095233;15##Retiro Voluntario##Exitoso##20160406##095411;05##Desenrolado##Exitoso##20160406##095233;16##Enrolado##Exitoso##20160406##095411;06##Enrolado##Exitoso##20160406##095233;11##Desenrolado##Exitoso##20160406##095233;12##Retiro Voluntario##Exitoso##20160406##095411,~";
                    //Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_OTP, AtlasConstantes.PUERTO_COM_MQ_OTP, tramaConsultaNov.toString());
                    Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_PRINCIPAL_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaConsultaNov.toString());

                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde  MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());
                } catch (Exception ex) {

                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error Principal MQ = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
                    //envio a contingencia
                    try {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "envio ctg MQ Consulta de Novedades = " + "##" + gestorDoc.obtenerHoraActual());
                        Respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_MQ_DMZ_CONTINGENCIA_AU4, AtlasConstantes.PUERTO_COM_MQ_DMZ_AU4, tramaConsultaNov.toString());
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Responde ctg MQ = " + Respuesta + "##" + gestorDoc.obtenerHoraActual());

                    } catch (Exception ex1) {
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error cfg MQ = " + ex1.toString() + "##" + gestorDoc.obtenerHoraActual());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (ConsultaNovedadesOtpController.cancelartareaNov.get()) {

                                    cancel();
                                } else {
                                    new ModalMensajes("Error Conectandose al Servidor", "Error", ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                                }
                            }
                        });
                    }
                }

                if ("000".equals(Respuesta.split(",")[2])) {

                    if (ConsultaNovedadesOtpController.cancelartareaNov.get()) {
                        cancel();
                    } else {

                        //  tablaDatos.setItems(emptyObservableList);
                        novedades = FXCollections.observableArrayList();

                        try {

                            String[] Lnovedades = Respuesta.split(",")[4].split(";");

                            for (int i = 0; i < Lnovedades.length; i++) {

                                final String[] datos = Lnovedades[i].split("##");

                                final String fechaIn = datos[3].substring(6, 8) + "/" + datos[3].substring(4, 6) + "/" + datos[3].substring(0, 4);
                                final String HoraIn = datos[4].substring(0, 2) + ":" + datos[4].substring(2, 4);

                                // transaccion , Descripcion, resultado, fecha, hora - Antes
                                //final infoTablaNovedades novedad = new infoTablaNovedades(datos[0], datos[1].toLowerCase().trim(), datos[2].toLowerCase().trim(), fechaIn, HoraIn);
                                // transaccion , fecha, hora, Descripcion, resultado - New 
                                final infoTablaNovedades novedad = new infoTablaNovedades(datos[0], fechaIn, HoraIn, datos[1].toLowerCase().trim(), datos[2].toLowerCase().trim());
                                novedades.add(novedad);

                            }
                        } catch (Exception ex) {
                            gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());
//                            System.out.println("consulta vacia - " + ex.toString());
                        }

                        if (novedades.isEmpty()) {

                            if (datosNov.getNovedad().trim().isEmpty()) {

//                                throw new IOException("No se encontraron registros para las fechas seleccionadas");
                                throw new IOException(AtlasConstantes.AVISOS_CONSULTANOVEDADES1);

                            } else {
                                if (datosNov.getFechaFinal().isEmpty() && datosNov.getFechaFinal().isEmpty()) {

//                                    throw new IOException("No se encontraron registros para el tipo de transacción seleccionado");
                                    throw new IOException(AtlasConstantes.AVISOS_CONSULTANOVEDADES2);

                                } else {
//                                    throw new IOException("No se encontraron registros para los criterios seleccionados");
                                    throw new IOException(AtlasConstantes.AVISOS_CONSULTANOVEDADES5);
                                }
                            }
                        }
                    }

                } else {
                    final String coderror = Respuesta.split(",")[2];
//                    final String coderror = "Error en fechas y/o códigos de consulta";
                    final String mensaje = "Error en fecha y/o códigos de consulta";
                    // final String mensaje = Respuesta.split(",")[3].trim();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (ConsultaNovedadesOtpController.cancelartareaNov.get()) {

                                cancel();
                            } else {
                                new ModalMensajes(mensaje.isEmpty() ? "NO SE PUDO REALIZAR LA TRANSACCION" : mensaje, coderror, ModalMensajes.MENSAJE_ERROR, ModalMensajes.DEFAULT);
                            }
                        }
                    });

                    new Exception(mensaje);

                }

                return novedades;

            }
        };
    }
}
