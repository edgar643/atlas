/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.controladoresfxml;

import com.co.allus.controladores.socket.ConectSS;
import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.modelo.Cliente;
import com.co.allus.utils.AtlasConstantes;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander.lopez.o
 */
public class MascaraTransaccionalController {
    private String codigoTransaccion;

    GestorDocumental docs = new GestorDocumental();



    public MascaraTransaccionalController(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }
    

    public void EnvioKbrowserMascara() {
            
       

            try {
                
                
                
                StringBuilder tramaKbrowser = new StringBuilder();
                final ConectSS conexion = new ConectSS();
                Cliente cliente = Cliente.getCliente();
                
                //%4448%01TURFMCP0EA73D4F0LH9B5AES0811QC%alejandro ortega leon%1094964008%CC%172.20.5.81%roberto.ceballos%token%~
                //tramaKbrowser.append("%4448%");
                tramaKbrowser.append(codigoTransaccion);
                tramaKbrowser.append(cliente.getRefid());
                tramaKbrowser.append("%");
                tramaKbrowser.append(cliente.getNombre());
                tramaKbrowser.append("%");
                tramaKbrowser.append(cliente.getId_cliente());
                tramaKbrowser.append("%");
                tramaKbrowser.append(cliente.getTipide().trim().isEmpty()? "1": cliente.getTipide()); //CONTINUACON
                tramaKbrowser.append("%");
                try {
                    tramaKbrowser.append(InetAddress.getLocalHost().getCanonicalHostName().toString());
                } catch (Exception e) {
                    tramaKbrowser.append("localhost");
                    
                }
                tramaKbrowser.append("%");
                tramaKbrowser.append(System.getProperties().getProperty("user.name"));
                tramaKbrowser.append("%");
                tramaKbrowser.append(cliente.getTokenOauth());
                tramaKbrowser.append("%~");
                
                docs.imprimir("Envio a KBrowser : " + tramaKbrowser);
                String enviarRecibirServidor = conexion.soloEnvioSocket(tramaKbrowser.toString(),AtlasConstantes.IP_KBROWSER, AtlasConstantes.PUERTO_KBROWSER);
                docs.imprimir("Respuesta de KBrowser : " + enviarRecibirServidor);
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(MascaraTransaccionalController.class.getName()).log(Level.SEVERE, null, ex);

            }
       


    }

    public void MinimizarKbrowserMascara() {
       
        try {
            final ConectSS conexion = new ConectSS();
            StringBuilder tramaKbrowser = new StringBuilder();
            tramaKbrowser.append("%200%~");

            docs.imprimir("Envio a KBrowser : " + tramaKbrowser);
            String enviarRecibirServidor = conexion.soloEnvioSocket(tramaKbrowser.toString(),AtlasConstantes.IP_KBROWSER, AtlasConstantes.PUERTO_KBROWSER);
            docs.imprimir("Respuesta de KBrowser : " + enviarRecibirServidor);
        } catch (IOException ex) {
            Logger.getLogger(MascaraTransaccionalController.class.getName()).log(Level.SEVERE, null, ex);
        }
            }

    public void CerrarKbrowserMascara() {
        
        try {
            StringBuilder tramaKbrowser = new StringBuilder();
            tramaKbrowser.append("%500%~");
            final ConectSS conexion = new ConectSS();
            docs.imprimir("Envio a KBrowser : " + tramaKbrowser);
            String enviarRecibirServidor = conexion.soloEnvioSocket(tramaKbrowser.toString(),AtlasConstantes.IP_KBROWSER, AtlasConstantes.PUERTO_KBROWSER);
            docs.imprimir("Respuesta de KBrowser : " + enviarRecibirServidor);
        } catch (IOException ex) {
            Logger.getLogger(MascaraTransaccionalController.class.getName()).log(Level.SEVERE, null, ex);
        }
       


    }
}
