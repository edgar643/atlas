/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.utils;

import com.co.allus.gestor.documental.GestorDocumental;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alexander.lopez.o
 */
public class StringUtilities {

    private static GestorDocumental gestorDoc = new GestorDocumental();

    public static String pad(final int fieldWidth, final char padChar, String s) {
        final StringBuilder stringpad = new StringBuilder();
        for (int i = s.length(); i < fieldWidth; i++) {
            stringpad.append(padChar);
        }
        stringpad.append(s);

        return stringpad.toString();
    }

    public static String eliminarCerosLeft(final String cadena) {
        int contador = 0;
        final char[] toCharArray = cadena.toCharArray();
        for (int i = 0; i < toCharArray.length; i++) {
            final char caracter = toCharArray[i];
            if (caracter == '0') {
                contador++;
            } else {
                break;
            }
        }

        return cadena.substring(contador, cadena.length());
    }

    public static double getMaxlista(String[] data, int pos) {
        double mayorini = 10;

        for (int i = 0; i < data.length - 1; i++) {
            double mayoraux = data[i].split(",")[pos].length();
            if (mayoraux > mayorini) {
                mayorini = mayoraux;
            }
        }

        return mayorini;
    }

    public static String rellenarDato(String dato, int longitud, String relleno) {
        if (dato.length() < longitud) {
            for (int i = 0; i < longitud; i++) {
                if (dato.length() < longitud) {
                    dato = relleno + dato;
                } else {
                    break;
                }
            }
        }
        return dato;
    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[_A-Za-z0-9\\+]+([A-Z0-9_%+-]+)*(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);

//            Pattern.compile("^[_A-Za-z\\+]+([A-Za-z0-9]+)*(\\.[_A-Za-z0-9-]+)*@"
//            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    private static final String PATTERN_EMAIL_ALL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; // correo completo
    private static final String VALIDA_CEL = "[0-9]*{10,}"; // empiece por 3 y longitud 10

    public static boolean validateCelLuis(String cel) {
        Pattern pattern = Pattern.compile(VALIDA_CEL);
        Matcher matcher = pattern.matcher(cel);
        return matcher.matches();

    }
    private static final String PATTERN_UNO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"; //"[-\\w\\.]"; // inicia correo
    private static final String PATTERN_DOS = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateEmailLuis(String email) {

        if (email.isEmpty()) {
            return true;
        }

        if (!email.contains("@")) {
            Pattern pattern = Pattern.compile(PATTERN_UNO);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();

        } else {

            final String[] partes = email.split("@");

            for (int i = 0; i < partes.length; i++) {
            }

            if (partes.length == 2 && !email.endsWith("@")) {

                Pattern pattern1 = Pattern.compile(PATTERN_UNO);
                Matcher matcher1 = pattern1.matcher(partes[0]);
                boolean parte1 = matcher1.matches();

                Pattern pattern2 = Pattern.compile(PATTERN_UNO);
                Matcher matcher2 = pattern2.matcher(partes[1]);
                boolean parte2 = matcher2.matches();

                if (parte1 && parte2) {
                    return true;
                } else {
                    return false;
                }

            } else // es mas de 1 @
            {
                return false;
            }

        }

    }

    public static String traerUsRed() {
        final StringBuilder retorno = new StringBuilder();
        try {
            final String usRed = System.getProperty("user.name");
            retorno.append(usRed);
        } catch (Exception ex) {
            retorno.delete(0, retorno.length());
            retorno.append("");
        }
        return retorno.toString();
    }

    public static boolean isNumber(final String numero) {
        try {
            final long dato = Long.parseLong(numero);
            return true;
        } catch (Exception ex) {
//            ex.printStackTrace();
            return false;
        }
    }

    public static void borrarClipBoard() {

        Runtime rt = null;
        Process proceso = null;
        try {
            rt = Runtime.getRuntime();
            String comandoEnvio = "cmd.exe /C echo off | clip";
            proceso = rt.exec(comandoEnvio);

        } catch (Exception ex) {

            Logger.getLogger(StringUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (proceso != null) {
                    proceso.destroy();
                }
            } catch (Exception ex) {
                gestorDoc.imprimir("Error en la aplicacion -->" + ex.toString() + "  :" + gestorDoc.obtenerHoraActual());

            }

        }
    }

    // 100,3,4,5,6,7,8;
    // 0  ,1,2,3,4,5,6; 
    // StringUtilities.tramaSubString(Respuesta, 0, 3, ",")
   public static String tramaSubString(String trama, int posicionInicial, int posicionFinal, String delimitador) {
        String[] datos = trama.split(delimitador);
        StringBuilder nuevaTrama = new StringBuilder();
        String tramaRetorno = "";
        try {
            for (int i = posicionInicial; i <= posicionFinal; i++) {
                nuevaTrama.append(datos[i]);
                if (i != posicionFinal) {
                    nuevaTrama.append(",");
                } else {
                    nuevaTrama.append("~");
                }
            }
            tramaRetorno = nuevaTrama.toString();     

        } catch (Exception e) {
            tramaRetorno = trama;
            Logger.getGlobal().log(Level.SEVERE, e.toString());
        }
        return tramaRetorno;

    }
    /**
     * VERSION VALIDA EMIAIL
     *
     */
//    public static String pad(int fieldWidth, char padChar, String s) {
//        StringBuilder stringpad = new StringBuilder();
//        for (int i = s.length(); i < fieldWidth; i++) {
//            stringpad.append(padChar);
//        }
//        stringpad.append(s);
//
//        return stringpad.toString();
//    }
//
//    public static String eliminarCerosLeft(String cadena) {
//        int contador = 0;
//        char[] toCharArray = cadena.toCharArray();
//        for (int i = 0; i < toCharArray.length; i++) {
//            char caracter = toCharArray[i];
//            if (caracter != '0') {
//                break;
//            }
//            contador++;
//        }
//        return cadena.substring(contador, cadena.length());
//    }
//
//    public static double getMaxlista(String[] data, int pos) {
//        double mayorini = 10.0D;
//        for (int i = 0; i < data.length - 1; i++) {
//            double mayoraux = data[i].split(",")[pos].length();
//            if (mayoraux > mayorini) {
//                mayorini = mayoraux;
//            }
//        }
//        return mayorini;
//    }
//
//    public static String rellenarDato(String dato, int longitud, String relleno) {
//        if (dato.length() < longitud) {
//            for (int i = 0; i < longitud; i++) {
//                if (dato.length() >= longitud) {
//                    break;
//                }
//                dato = relleno + dato;
//            }
//        }
//        return dato;
//    }
//    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[_A-Za-z\\+]+([A-Za-z0-9]+)*(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", 2);
//    private static final String PATTERN_EMAIL_ALL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//    private static final String VALIDA_CEL = "[0-9]*{10,}";
//    private static final String PATTERN_UNO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*";
//    private static final String PATTERN_DOS = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
//
//    public static boolean validateEmail(String emailStr) {
//        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
//        return matcher.find();
//    }
//
//    public static boolean validateCelLuis(String cel) {
//        Pattern pattern = Pattern.compile("[0-9]*{10,}");
//        Matcher matcher = pattern.matcher(cel);
//        System.out.println("Validacion Cel 1" + matcher.matches());
//        return matcher.matches();
//    }
//
//    public static boolean validateEmailLuis(String email) {
//        if (email.isEmpty()) {
//            return true;
//        }
//        if (!email.contains("@")) {
//            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*");
//            Matcher matcher = pattern.matcher(email);
//            return matcher.matches();
//        }
//        String[] partes = email.split("@");
//        for (int i = 0; i < partes.length; i++) {
//            System.out.println("Partes:" + partes[i]);
//        }
//        System.out.println("Tamaño:" + partes.length);
//        if ((partes.length == 2) && (!email.endsWith("@"))) {
//            Pattern pattern1 = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*");
//            Matcher matcher1 = pattern1.matcher(partes[0]);
//            boolean parte1 = matcher1.matches();
//
//            Pattern pattern2 = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*");
//            Matcher matcher2 = pattern2.matcher(partes[1]);
//            boolean parte2 = matcher2.matches();
//            if ((parte1) && (parte2)) {
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }
//
//    public static String traerUsRed() {
//        StringBuilder retorno = new StringBuilder();
//        try {
//            String usRed = System.getProperty("user.name");
//            retorno.append(usRed);
//        } catch (Exception ex) {
//            retorno.delete(0, retorno.length());
//            retorno.append("juan.bedoya");
//        }
//        return retorno.toString();
//    }
//
//    public static boolean isNumber(String numero) {
//        try {
//            long dato = Long.parseLong(numero);
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return false;
//    }
//
//    public static void borrarClipBoard() {
//        System.out.println("Presiono CTRL");
//        Runtime rt = null;
//        Process proceso = null;
//        try {
//            rt = Runtime.getRuntime();
//            String comandoEnvio = "cmd.exe /C echo off | clip";
//            proceso = rt.exec(comandoEnvio);
//            System.out.println("Ok borrado:");
//            return;
//        } catch (Exception ex) {
//            System.out.println("No borrado por:" + ex.getMessage());
//            Logger.getLogger(StringUtilities.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                proceso.destroy();
//            } catch (Exception ex) {
//            }
//        }
//    }
}
