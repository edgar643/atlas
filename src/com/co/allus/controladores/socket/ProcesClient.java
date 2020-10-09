// Las acciones especificas del socket se encuentran en la
// documentacion del mismo
package com.co.allus.controladores.socket;

import com.co.allus.atlas.Atlas;
import com.co.allus.controladoresfxml.MascaraTransaccionalController;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.ModalMensajes;
import com.co.allus.visor.VisorSelenium;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.sql.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

public class ProcesClient implements Runnable {

    public static int idHilo = 0;
    private transient int indicador = 0;
    private final transient Socket NewClient;
    private static final GestorDocumental DOCUMENTOS = new GestorDocumental();
    private transient long tiempo;

    public ProcesClient(final Socket NewClient) {
        this.NewClient = NewClient;
    }

    @Override
    public void run() {

        BufferedReader buffer_reader = null;
        try {
            revisarID();
            String trama;
            int character;

            buffer_reader = new BufferedReader(new InputStreamReader(NewClient.getInputStream(), Charset.forName("ISO-8859-1")));
            final StringBuilder string_builder = new StringBuilder();
            character = buffer_reader.read();
            while (character != -1) {

                string_builder.append((char) character);
                if (character == 126 || character == 13) {
                    break;
                }
                character = buffer_reader.read();
            }

            trama = string_builder.toString();
            //System.out.println(trama);
            procesoPrincipal(trama.trim());
            // buffer_reader.close();
            // NewClient.close();

        } catch (Exception ex) {
            DOCUMENTOS.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + DOCUMENTOS.obtenerHoraActual());
        } finally {

            if (buffer_reader != null) {
                try {
                    buffer_reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ProcesClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                NewClient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProcesClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void procesoPrincipal(final String trama) {
  
        //   final StringBuilder connid = new StringBuilder();
        final StringBuffer respuestaACliente = new StringBuffer();
        try {
            DOCUMENTOS.imprimir(indicador + "   " + obtenerHoraActual() + "   Recibo: " + trama);
            tiempo = System.currentTimeMillis();
            //consulta de saldos
            if (trama.startsWith("100%")) {
//                final String Servicio = trama.split("%")[12]; //sin OTP

                final String Servicio = trama.split("%")[trama.split("%").length - 1].replace("~", "").trim(); // 100 completa otp
                final SocketController proceso = new SocketController();
                if (trama.split("%")[1].length() == 3) {
                    proceso.ProcesarServicio(Servicio, trama, 3);
                } else if ((trama.split("%")[1].length() >= 3) || (trama.split("%")[1].trim().isEmpty())) {
                    if ((trama.split("%")[1].trim().isEmpty()) && trama.split("%")[19].equals("HT")) {
                        proceso.ProcesarServicio(Servicio, trama, 3);
                    } else {
                        proceso.ProcesarServicio(Servicio, trama, 1);
                    }

                } else {
                    proceso.ProcesarServicio(Servicio, trama, 3);
                }

            } else if (trama.startsWith("101%")) {
                final String Servicio = trama.split("%")[1];
                final SocketController proceso = new SocketController();
                respuestaACliente.append(proceso.ProcesarServicio(Servicio, trama, 1));
            } else if (trama.startsWith("850,036,")) {
                final SocketController proceso = new SocketController();
                respuestaACliente.append(proceso.ProcesarServicio("036", trama, 1));
            } else if (trama.startsWith("922,006,")) {
                final SocketController proceso = new SocketController();
                respuestaACliente.append(proceso.ProcesarServicio("006", trama, 1));
            } else if (trama.startsWith("850,062,")) {
                final SocketController proceso = new SocketController();
                respuestaACliente.append(proceso.ProcesarServicio("062", trama, 1));
            } else if (trama.startsWith("%4444")) {  // fenix con atlas                
                final SocketController proceso = new SocketController();
                respuestaACliente.append(proceso.ProcesarServicio("FENIX", trama, 2));
            } else if (trama.startsWith("200%") || trama.startsWith("%200%")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Atlas.getVista().gotoPrincipal();
                            VisorSelenium visor = new VisorSelenium();
                            visor.finalizarProcesoVisor();
                            
                            MascaraTransaccionalController controller=new MascaraTransaccionalController("%4448%");
                            controller.MinimizarKbrowserMascara();
                            
                            SocketController.isAutenticadoTDCprep.set(false);
                            if (ModalMensajes.getStage() != null) {
                                ModalMensajes.getStage().close();
                            }
                        } catch (final Exception ex) {
                            SocketController.isAutenticadoTDCprep.set(false);
                            DOCUMENTOS.imprimir(idHilo + "Error en la aplicacion " + ex.toString() + "-->" + obtenerHoraActual());
                        }
                    }
                });

            } else if (trama.startsWith("500%")) {
                // termina la ejecucion
                DOCUMENTOS.imprimir(idHilo + "Se ha ejecutado el comando de cerrar aplicacion -->" + obtenerHoraActual());
                try {
                    NewClient.close();

                } catch (Exception ex) {
                    DOCUMENTOS.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + DOCUMENTOS.obtenerHoraActual());
                }
                VisorSelenium visor = new VisorSelenium();
                visor.finalizarProcesoVisor();
                
                MascaraTransaccionalController controller=new MascaraTransaccionalController("%4448%");
                controller.CerrarKbrowserMascara();
                
                System.exit(0);
            } else {
                respuestaACliente.delete(0, respuestaACliente.length());
                respuestaACliente.append("100%999%~"); // el socket no entendio lo que recibio
            }
            setTiempo(System.currentTimeMillis() - this.tiempo);
            imprimirAlCliente(respuestaACliente.toString());
            // System.out.println("Respondo: " + respuestaACliente);
            // DOCUMENTOS.imprimir(indicador + "   " + obtenerHoraActual() + "   Respondo: " + respuestaACliente + " Connid:" + (connid.toString().isEmpty() ? "operacion sin Connid" : connid.toString()) + "   Me demore: " + tiempo);
            // NewClient.close();
            respuestaACliente.delete(0, respuestaACliente.length());

        } catch (Exception ex) {
            DOCUMENTOS.imprimir(System.getProperties().getProperty("user.name") + " : " + ex.toString() + " : " + obtenerHoraActual());
        }
    }

    private void revisarID() {
        synchronized (this) {
            if (idHilo == 9999) {
                idHilo = 0;
            }
            idHilo++;
            indicador = idHilo;
        }
    }

    private String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }

    public void setTiempo(final long Time) {
        this.tiempo = Time;
    }

    public long getTiempo() {

        return tiempo;
    }

    private void imprimirAlCliente(final String aImprimir) {
        try {
            final PrintWriter out = new PrintWriter(NewClient.getOutputStream(), true);
            out.println(aImprimir);
            out.close();
        } catch (Exception ex) {
            DOCUMENTOS.imprimir("Error en: " + indicador + " ImprimirCliente: " + ex.toString());
        }
    }
}
