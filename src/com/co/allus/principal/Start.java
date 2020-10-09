package com.co.allus.principal;

import com.co.allus.controladores.socket.ProcesClient;
import com.co.allus.controladores.socket.ProcesLaunch;
import com.co.allus.gestor.documental.GestorDocumental;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {
    
    private static GestorDocumental gestorDoc = new GestorDocumental();
    
    public void subirServicio() {
        
        try (final ServerSocket SOCKET_SERVER = new ServerSocket(9857)){
           
            Socket NewClient;
            iniciarLaunch();
            do {
                NewClient = SOCKET_SERVER.accept();
                NewClient.setSoTimeout(20000);
                iniciarservicio(NewClient);
                
            } while (!SOCKET_SERVER.isClosed());
        } catch (IOException ex) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + " : " + ex.toString() + " : " + obtenerHoraActual());
            System.exit(0);
        }
    }
    
    private void iniciarservicio(final Socket NewClient) {
        
        final Runnable newCliente = new ProcesClient(NewClient);
        final Thread cliente = new Thread(newCliente, "hiloProceso");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//           gestorDoc.imprimir(System.getProperties().getProperty("user.name") + " : " + ex.toString() + " : " + obtenerHoraActual());
//        }
        cliente.start();
        
    }
    
    private void iniciarLaunch() {
        
        final Runnable newCliente = new ProcesLaunch();
        final Thread cliente = new Thread(newCliente);
//        try {
//           // Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//           gestorDoc.imprimir(System.getProperties().getProperty("user.name") + " : " + ex.toString() + " : " + obtenerHoraActual());
//        }
        cliente.start();
        
    }
    
    private static String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
    
    public static void main(final String[] args) {
        
        final String appId = System.getProperty("user.name") + ".atlas";
        boolean alreadyRunning;
        try {
            JUnique.acquireLock(appId);
            alreadyRunning = false;
        } catch (AlreadyLockedException e) {
            alreadyRunning = true;
            Logger.getLogger(appId).log(Level.SEVERE,e.toString());
            
            
        }
        
        if (alreadyRunning) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + " : " + "APLICATIVO EN MEMORIA SE PROCEDE A CERRAR PID" + " : " + obtenerHoraActual());
            // new ModalMensajes("Aplicativo en memmoria", "Exit", ModalMensajes.MENSAJE_INFORMACION, ModalMensajes.DEFAULT);
            //JOptionPane.showMessageDialog(null, MessageConfiguration.getMessage("mensajeCorriendoAplicacion"), "Fenix", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else {
            try {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + " : " + "inicia socket" + " : " + obtenerHoraActual());
                new Start().subirServicio();
                
            } catch (Exception ex) {
                gestorDoc.imprimir(System.getProperties().getProperty("user.name") + " : " + ex.toString() + " : " + obtenerHoraActual());
            }
            
        }
        
        
    }
}
