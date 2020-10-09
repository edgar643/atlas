package com.co.allus.gestor.documental;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorDocumental {

    SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");

    public String leerPropertiesSocket(final String proper) {
        final Properties prop = new Properties();
        try {
            InputStream input_stream = new FileInputStream("Configuracion.properties");
            prop.load(input_stream);
            input_stream.close();

        } catch (IOException ex) {
            this.imprimir(ex.toString());
        }

        return prop.getProperty(proper);
    }

    public String obtenerFechaActualSinY() {

        final Calendar capturar_fecha = Calendar.getInstance();
        return Integer.toString((capturar_fecha.get(Calendar.MONTH)) + 1) + Integer.toString(capturar_fecha.get(Calendar.DATE));
    }

    public String obtenerFechaActualSinDia() {

        final Calendar capturar_fecha = Calendar.getInstance();
        return Integer.toString(capturar_fecha.get(Calendar.YEAR)) + "/" + Integer.toString((capturar_fecha.get(Calendar.MONTH)) + 1);
    }

    public String obtenerFechaActualSinDia2() {

        final Calendar capturar_fecha = Calendar.getInstance();
        return Integer.toString(capturar_fecha.get(Calendar.YEAR)) + agregarCerosIzquierda(Integer.toString(capturar_fecha.get(Calendar.MONTH) + 1));
    }

    public String obtenerFechaActualOTP() {

        final Calendar capturar_fecha = Calendar.getInstance();
        return Integer.toString(capturar_fecha.get(Calendar.YEAR)) + "/" + Integer.toString((capturar_fecha.get(Calendar.MONTH)) + 1) + "/" + Integer.toString(capturar_fecha.get(Calendar.DATE));
    }

    public String obtenerFechaActualComp() {//ACTUAL

        final Calendar capturar_fecha = Calendar.getInstance();
        return Integer.toString((capturar_fecha.get(Calendar.MONTH)) + 1) + "/" + agregarCerosIzquierda(Integer.toString(capturar_fecha.get(Calendar.DATE)));
    }

    public String obtenerFechaActualDiaAnt() { //AYER
        final Calendar capturar_fecha = Calendar.getInstance();
        return agregarCerosIzquierda(Integer.toString((capturar_fecha.get(Calendar.MONTH)) + 1)) + "/" + agregarCerosIzquierda(Integer.toString(capturar_fecha.get(Calendar.DATE) - 1));
    }

    public String obtenerFechaActualDiaAnteayer() { //ANTEAYER
        final Calendar capturar_fecha = Calendar.getInstance();
        return agregarCerosIzquierda(Integer.toString((capturar_fecha.get(Calendar.MONTH)) + 1)) + "/" + agregarCerosIzquierda(Integer.toString(capturar_fecha.get(Calendar.DATE) - 2));
    }

    public String obtenerHoraActual() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }

    public String obtenerFechaActual() {

        final Calendar capturar_fecha = Calendar.getInstance();
        return Integer.toString(capturar_fecha.get(Calendar.YEAR)) + Integer.toString((capturar_fecha.get(Calendar.MONTH)) + 1) + Integer.toString(capturar_fecha.get(Calendar.DATE));
    }

    public String obtenerFechaActualMesAnt2() { //yyyyMMdd
        final Calendar capturar_fecha = Calendar.getInstance();
        final int Year = capturar_fecha.get(Calendar.YEAR);
        final int Month = capturar_fecha.get(Calendar.MONTH);
        final int Day = capturar_fecha.get(Calendar.DATE);
        capturar_fecha.set(Year, Month - 2, Day);
        return Integer.toString(capturar_fecha.get(Calendar.YEAR)) + agregarCerosIzquierda(Integer.toString((capturar_fecha.get(Calendar.MONTH)) + 1)) + agregarCerosIzquierda(Integer.toString(capturar_fecha.get(Calendar.DATE)));
    }

    public String convertToString(final Calendar calendar) {

        return Integer.toString(calendar.get(Calendar.YEAR)) + agregarCerosIzquierda(Integer.toString((calendar.get(Calendar.MONTH)) + 1)) + agregarCerosIzquierda(Integer.toString(calendar.get(Calendar.DATE)));
    }

    public String convertirFechaNovedad(final Date calendar) {

        return formato.format(calendar);

    }

    public String convertirFecha2(final Date fecha) {

        return formato.format(fecha);

    }

    public String obtenerFechaActualHoy() // yyyyMMdd
    {
        final Calendar capturar_fecha = Calendar.getInstance();
        return Integer.toString(capturar_fecha.get(Calendar.YEAR)) + agregarCerosIzquierda(Integer.toString((capturar_fecha.get(Calendar.MONTH)) + 1)) + agregarCerosIzquierda(Integer.toString(capturar_fecha.get(Calendar.DATE)));
    }

    private String agregarCerosIzquierda(final String diames) {
        final StringBuffer retorno = new StringBuffer();
        if (Integer.parseInt(diames) < 10) {
            final String add = "0" + diames;
            retorno.append(add);
        } else {
            retorno.append(diames);
        }
        return retorno.toString();
    }

    private String generarNombreDocumento() {
        final File archivo = new File("Logs");
        if (!(archivo.exists())) {
            archivo.mkdir();
        }
        return "Logs" + File.separator + System.getProperties().getProperty("user.name") + "_" + obtenerFechaActual() + ".txt";
    }

    private String generarNombreDocumentoErrores() {
        final File archivo = new File("Logs");
        if (!(archivo.exists())) {
            archivo.mkdir();
        }
        return "Logs" + File.separator + System.getProperties().getProperty("user.name") + "_" + obtenerFechaActual() + "_Erorres.txt";
    }

    public int CalcularDifFechas(Calendar fechaini, Calendar fechafin) {
        try {

            if (fechaini.after(fechafin)) {
                return -1;
            }
            return incrementa(Calendar.DAY_OF_MONTH, fechaini, fechafin);

        } catch (Exception e) {
            return -1;
        }
    }

    private int incrementa(int periodo, Calendar fechaini, Calendar fechafin) {

        int aux = 0;
        int val = 0;
        while (!fechaini.after(fechafin)) {
            fechaini.add(periodo, 1);
            val++;
        }
        fechaini.add(periodo, -1);
        val--;
        switch (periodo) {
            case Calendar.YEAR:
                aux = val;
                break;
            case Calendar.MONTH:
                aux = val;
                break;
            case Calendar.DAY_OF_MONTH:
                aux = val;
                break;
        }

        return aux;
    }

    public void imprimirExcepcion(Exception ex) {
        synchronized (this) {
            PrintWriter print;
            try {

                print = new PrintWriter(new File(generarNombreDocumentoErrores()));
                print.println("-----------");
                ex.printStackTrace(print);
                print.println("-----------");
                print.flush();
                print.close();

            } catch (FileNotFoundException ex1) {
                Logger.getLogger(GestorDocumental.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    // Impresion de logs

    public void imprimir(final String aImprimir) {
        synchronized (this) {
            try (FileWriter escrbibeArchivo = new FileWriter(generarNombreDocumento(), true); 
                    BufferedWriter bufferescribe = new BufferedWriter(escrbibeArchivo)){
               
                bufferescribe.write(aImprimir);
                bufferescribe.newLine();
               
            } catch (Exception ex) {
                Logger.getLogger(GestorDocumental.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
