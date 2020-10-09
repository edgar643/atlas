package com.co.allus.conexionDDE;

import com.pretty_tools.dde.ClipboardFormat;
import com.pretty_tools.dde.client.DDEClientConversation;
import java.lang.reflect.Field;
import com.co.allus.gestor.documental.GestorDocumental;


/**
 *
 *
 * @author Alexander Kozlov
 */
public class DDE {

    private transient GestorDocumental gestorDoc = new GestorDocumental();
    
    private static final String SERVICE_gde = "ocxTest";
    private static final String TOPIC_gde = "THOR";
    private static final String SERVICE_softphone = "SoftPhone";
    private static final String TOPIC_softphone = "XIRTAM";

    public void DDE() {
        try {
            System.setProperty("java.library.path", "lib\\");
            Field field = ClassLoader.class.getDeclaredField("sys_paths");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            gestorDoc.imprimir("Error en la aplicacion -->" + e.toString() + "  :" + gestorDoc.obtenerHoraActual());
//            e.printStackTrace();
        }

    }

    public void ejecutarTramaAGestiontel(String trama) throws Exception {
        DDEClientConversation conversation = new DDEClientConversation();
        try {
            // We can use UNICODE format if server prefers it
            conversation.setTextFormat(ClipboardFormat.CF_UNICODETEXT);

            conversation.connect(SERVICE_gde, TOPIC_gde);
            conversation.execute(trama);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conversation.disconnect();
        }

    }

    public void ejecutarTramaASoftphone(String trama) throws Exception {
        DDEClientConversation conversation = new DDEClientConversation();
        try {
            // We can use UNICODE format if server prefers it
            conversation.setTextFormat(ClipboardFormat.CF_UNICODETEXT);
            // System.out.println("Connecting...");
            conversation.connect(SERVICE_softphone, TOPIC_softphone);
            conversation.execute(trama);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            conversation.disconnect();
        }

    }
//       public static void main(String[] args) {
//        try {
//            new DDE().ejecutarTramaASoftphone("100");
//        } catch (Exception ex) {
//            Logger.getLogger(DDE.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    public static void main(String[] args) {
//
//        try {
//            System.setProperty("java.library.path", "lib\\");
//            Field field = ClassLoader.class.getDeclaredField("sys_paths");
//            field.setAccessible(true);
//            field.set(null, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            DDEClientConversation conversation = new DDEClientConversation();
//            // We can use UNICODE format if server prefers it
//            conversation.setTextFormat(ClipboardFormat.CF_UNICODETEXT);
//
//            System.out.println("Connecting...");
//            conversation.connect(SERVICE, TOPIC);
//
//            try {
//                String data = "%100%ACBD0E562A6D740F%1%0%%%37799%FB%No hay descripción para esta opción%O%SB%85501%I%00020%13372193%3430000%00IDL4MSES9I9546E8LH9B5AES02A2TP%01%01%01%1030589730%008%MARTHA HELENA MORALES GUACANEME%CR 78 C 41 A 05 SUR IN 12 AP 502  0MN%%3115640873%1991/04/03%2012/03/09%%S%CUPT%0%41%SB%75436%3%01002%10380772642%01003%5406919586110926%01004%1030083174%";
//                conversation.execute(data);
//
//            } finally {
//                conversation.disconnect();
//            }
//        } catch (DDEMLException e) {
//            System.out.println("DDEMLException: 0x" + Integer.toHexString(e.getErrorCode()) + " " + e.getMessage());
//        } catch (DDEException e) {
//            System.out.println("DDEClientException: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Exception: " + e);
//        }
//    }
}
