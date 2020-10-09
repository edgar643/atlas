package com.co.allus.controladores.socket;

import com.co.allus.gestor.documental.GestorDocumental;
import com.co.allus.utils.AtlasConstantes;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import org.apache.commons.net.telnet.TelnetClient;
import java.util.Enumeration;

/**
 * Clase Encargada de Realizar la comunicacion con el socket
 *
 * ATRIBUTOS PRIVADOS telnet,out,in, ip, puerto
 */
public class ConectSS {

    private transient TelnetClient telnet = null;
    private transient PrintStream salida = null;
    private transient InputStream entrada = null;
    private static GestorDocumental mensaje = new GestorDocumental();
    PrintWriter SalidaDoBot;

    public String enviarRecibirServidor(final String ipCom, final int Puerto, final String aEnviar) throws SocketException, IOException {
        final StringBuilder aRetornar = new StringBuilder();

        telnet = new TelnetClient();
        telnet.connect(ipCom, Puerto);
        entrada = telnet.getInputStream();
        telnet.setSoTimeout(58000);
        salida = new PrintStream(telnet.getOutputStream());
        write(aEnviar);
        aRetornar.append(readUntil());
        disconnected();
        return aRetornar.toString();
    }

    public void enviarRecibirDoBot(final String ipCom, final int Puerto, final String aEnviar) throws SocketException, IOException {

        final SocketAddress sockaddr = new InetSocketAddress(ipCom, Puerto);
        final Socket conexion = new Socket();
        try {
            conexion.setSoTimeout(30000);
            conexion.connect(sockaddr, 2000);

            SalidaDoBot = new PrintWriter(new OutputStreamWriter(conexion.getOutputStream()), true);
            SalidaDoBot.println(aEnviar);



        } finally {
            conexion.close();
            if (SalidaDoBot != null) {
                SalidaDoBot.close();

            }


        }

    }

    public void enviarRecibirServidorsoftphone(final String ipCom, final int Puerto, final String aEnviar) throws SocketException, IOException {

        telnet = new TelnetClient();
        telnet.connect(ipCom, Puerto);
        entrada = telnet.getInputStream();
        telnet.setSoTimeout(10000);
        salida = new PrintStream(telnet.getOutputStream());
        write(aEnviar);
        disconnected();

    }
    
public String soloEnvioSocket(final String trama,  String HOST, final int PUERTO) throws IOException {
       
        String ipSalida = new String();
      try {
          Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
          for (; n.hasMoreElements();) {
              NetworkInterface e = n.nextElement();
               //.out.println("Interface: " + e.getName());
              Enumeration<InetAddress> a = e.getInetAddresses();
              for (; a.hasMoreElements();) {
                  InetAddress addr = a.nextElement();
                  //System.out.println(addr.toString());
                  //System.out.println("  " + addr.getHostAddress());
                  if (addr.getHostAddress().startsWith("172.20")) {
                      ipSalida = addr.getHostAddress();
                      //System.out.println("Interface: " + e.getName());
                      //         System.out.println(addr.toString());
                  //System.out.println("  " + addr.getHostAddress());
                      break;
                  }
              }
              if (ipSalida.startsWith("172.20")) {
                  break;
              }
          }
      } catch (Exception e) {
         // System.out.println(e.toString());
           mensaje.imprimir(" Error--> " + e.getMessage());
      }
       HOST = ipSalida;
       //System.out.println("IP para Kbrowser: "+ipSalida);
       /*    
       try {
           HOST = InetAddress.getLocalHost().getHostAddress();
       } catch (UnknownHostException ex) {
           HOST=AtlasConstantes.IP_KBROWSER;
       }
       * */
       //System.out.println("IP Address:- " +HOST);
       final StringBuilder retorno = new StringBuilder();
//       Socket SockBanc = null;
//       DataOutputStream envioSockBanc = null;
       try(Socket  SockBanc = new Socket();) {
         
               SockBanc.connect(new InetSocketAddress(HOST, PUERTO), 5000);
    
       try ( 
           DataOutputStream envioSockBanc = new DataOutputStream(SockBanc.getOutputStream());) {
           final byte[] datos = trama.getBytes();
           envioSockBanc.write(datos);
           retorno.delete(0, retorno.length());
           final String add = "OK,~";
           retorno.append(add);
       } catch (Exception e) {
           retorno.delete(0, retorno.length());
           final String add = "EXC," + e.getMessage() + ",~";
           retorno.append(add);
       } 
       } catch (Exception e) {
           mensaje.imprimir(" Error --> " + e.getMessage()); 
           
    }

//       finally {
//           try {
//               envioSockBanc.close();
//           } catch (Exception ex) {
//               mensaje.imprimir(" Error--> " + ex.getMessage());
//           }
//           try {
//               SockBanc.close();
//           } catch (Exception ex) {
//               mensaje.imprimir(" Error --> " + ex.getMessage());
//           }
//       }
       return retorno.toString();
    }
    /**
     * Metodo que desconecta la conexion realizada para el envio de la señal de
     * actualizacion al socket
     */
    public void disconnected() throws IOException {

        salida.close();
        entrada.close();
        telnet.disconnect();

    }

    private String readUntil() throws IOException {
        final Charset isolatinChar = Charset.forName("ISO-8859-1");
        final StringBuilder stringb = new StringBuilder();
        int character;

        final BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(entrada, isolatinChar));

        for (;;) {
            character = buffer_reader.read();
            stringb.append((char) character);
            if (character == 126 || character == 13) {
                break;
            } else if (character == -1) {
                break;
            }
        }
        buffer_reader.close();

        return stringb.toString();
    }

    /**
     * metodo que escribe por la conexion generada (ip,puerto)
     *
     * @param value: lo que se va a enviar (Es Estring)
     */
    private void write(final String value) {
        salida.println(value);
        salida.flush();
    }
}
