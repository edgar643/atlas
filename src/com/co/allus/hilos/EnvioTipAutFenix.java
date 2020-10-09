/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.hilos;

import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.utils.AtlasConstantes;

/**
 *
 * @author luis.cuervo
 */
public class EnvioTipAutFenix implements Runnable {

    private String codEmp;
    private String Cod_Automa;
    private GestorDocumental gestorDoc = new GestorDocumental();

    public EnvioTipAutFenix(final String codEmp, final String Cod_Automa) {
        this.codEmp = codEmp;
        this.Cod_Automa = Cod_Automa;
    }

    @Override
    public void run() {
        final ConectSS servicioSS = new ConectSS();
        final String Trama = "749,000,ATLAS," + codEmp + "," + Cod_Automa + ",~";
        String respuesta = "";
        try {


            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + " ENVIO PRINCIPAL  COD AUTO= " + Trama + "##" + gestorDoc.obtenerHoraActual());
            respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_TIP_AUTO, AtlasConstantes.PUERTO_COM_IP_TIP_AUTO, Trama.toString());
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "REPSUESTA  ENVIO PRINCIPAL  COD AUTO= " + Trama + "##" + gestorDoc.obtenerHoraActual());
            if (!"NOK".equals(respuesta)) {
                final String datos[] = respuesta.split(",");
                if ("000".equals(datos[2])) {

                    final String trama = "110," + codEmp + "," + datos[3] + ",~";
                    gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "  ENVIO  FENIX COD_AUTO= " + trama + "##" + gestorDoc.obtenerHoraActual());
                    servicioSS.enviarRecibirServidor(AtlasConstantes.HOST_FENIX, AtlasConstantes.PUERTO_FENIX, trama);
                }
            }
        } catch (Exception ex) {
            try {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "  ENVIO CTG  COD AUTO= " + Trama + "##" + gestorDoc.obtenerHoraActual());
                respuesta = servicioSS.enviarRecibirServidor(AtlasConstantes.IP_CONSULTA_TIP_AUTO_ctg, AtlasConstantes.PUERTO_COM_IP_TIP_AUTO, Trama.toString());
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "  RESPUESTA ENVIO CTG  COD AUTO= " + Trama + "##" + gestorDoc.obtenerHoraActual());
                if (!"NOK".equals(respuesta)) {
                    final String datos[] = respuesta.split(",");
                    if ("000".equals(datos[2])) {
                        final String trama = "110," + codEmp + "," + datos[3] + ",~";
                        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "  ENVIO  FENIX COD_AUTO= " + trama + "##" + gestorDoc.obtenerHoraActual());
                        servicioSS.enviarRecibirServidor(AtlasConstantes.HOST_FENIX, AtlasConstantes.PUERTO_FENIX, trama);
                    }
                }

            } catch (Exception e) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Error ctg ENVIO  COD AUTO= " + e.toString() + "##" + gestorDoc.obtenerHoraActual());
            }

        }
    }
}
