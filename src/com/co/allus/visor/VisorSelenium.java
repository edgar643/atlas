/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.visor;

import com.co.allus.gestor.documental.GestorDocumental;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author juan.hernandez.ala
 */
public class VisorSelenium {

    
    private final String driverIexplorer = "webdriver.ie.driver";
    private final String URL = "https://panoramicadelcliente.bancolombia.corp/ReportServer?/Panoramica+del+Cliente/Reportes/ReportePersonasBPO";
    //"https://visionclientescert.ambientesbc.lab/ReportServer?/Panoramica+del+Cliente/Reportes/ReportePersonasBPO";
    private transient GestorDocumental gestorDoc = new GestorDocumental();

    private PeticionPostWrapper peticionPostWrapper;
//    private WebDriver driver;
    private String contenidoArchivoDisparador;

    private static final List<String> PROCESOS_ANTES = new ArrayList<>();
    private static final List<String> PROCESOS_DESPUES = new ArrayList<>();
//    private static DesiredCapabilities capacidades = DesiredCapabilities.internetExplorer();


    public void visorStart(String documento, String tipoDocumento) {
        gestorDoc.imprimir(System.getProperties().getProperty("user.name") 
                + "##" + "Iniciando visor ##" + gestorDoc.obtenerHoraActual());
        asignarCapacidadesNavegador();
        this.limpiarListaProcesos();
        capturarProcesosSistema(PROCESOS_ANTES);
        for (String string : PROCESOS_ANTES) {
            System.out.println("proceso: "+string);
        }

        buildPeticionPostWrapper(documento, tipoDocumento);
        buildContenidoJs();

        System.setProperty(driverIexplorer, "C:/gestiontel/ie-driver/IEDriverServer.exe");
       
//        driver = new InternetExplorerDriver(capacidades);
        gestorDoc.imprimir(System.getProperties().getProperty("user.name") 
                + "##" + "Visor: Levantando ie-driver... ##" + gestorDoc.obtenerHoraActual());
//        driver.manage().window().maximize();
//        JavascriptExecutor js = (JavascriptExecutor) driver;
        gestorDoc.imprimir(System.getProperties().getProperty("user.name") 
                + "##" + "Visor: ejecutando script... ##" + gestorDoc.obtenerHoraActual());
//        js.executeScript(this.contenidoArchivoDisparador);
        capturarProcesosSistema(PROCESOS_DESPUES);
                for (String string : PROCESOS_DESPUES) {
            System.out.println("proceso despues: "+string);
        }

    }
    
    public void asignarCapacidadesNavegador(){
//        capacidades.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//        capacidades.setBrowserName("VISOR");
//        capacidades.setJavascriptEnabled(true);
//        capacidades.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
//        capacidades.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
    }
    

    public void finalizarProcesoVisor() {
        
        if (PROCESOS_DESPUES.removeAll(PROCESOS_ANTES) || "000".equals(PROCESOS_ANTES.get(0))) {
            for (String item : PROCESOS_DESPUES) {
                matarProceso(item);
            }
        } else {
            Logger.getGlobal().log(Level.WARNING, "Ningun procesos para finalizar: No hay coincidencias");

        }
        try {
            this.send();
        } catch (MalformedURLException ex) {
            Logger.getLogger(VisorSelenium.class.getName()).log(Level.SEVERE, ex.toString());
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + 
                    "##" + "Visor: Error inesperado mientras se ejecutaba el modulo visor"
                            + " = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
        }
        this.limpiarListaProcesos();
    }

    public void limpiarListaProcesos() {
        gestorDoc.imprimir(System.getProperties().getProperty("user.name") + "##" + "Visor: Limpiando algun proceso activo anterior... ##" + gestorDoc.obtenerHoraActual());

        PROCESOS_DESPUES.clear();
        PROCESOS_ANTES.clear();
        gestorDoc.imprimir(System.getProperties()
                .getProperty("user.name") + "##" + "Visor: Finalizando limpieza de procesos ##" 
                + gestorDoc.obtenerHoraActual());

    }

    public void buildContenidoJs() {

        String formularioInicio = "var url = \"" + peticionPostWrapper.getUrl() + "\";\n\t document.body.innerHTML +='<form id=\"visor\" action=\"'+url+'\" method=\"post\">";

        String formularioFin = "  </form>';"
                + "\n"
                + "        document.getElementById(\"visor\").submit();\n";

        Map<String, String> map = peticionPostWrapper.getParametrosMap();
        List<String> nombres = new ArrayList<>();
        for (String key : map.keySet()) {
            nombres.add(key);
        }
        List<String> valores = new ArrayList<>();
        for (String value : map.values()) {
            valores.add(value);
        }
        String inputs = "";

        for (int i = 2; i >= 0; i--) {
            inputs += buildInputs(nombres.get(i), valores.get(i));
        }

        contenidoArchivoDisparador = formularioInicio + inputs
                + formularioFin;
        

    }

    public String buildInputs(String nombre, String valor) {
        String formato = "<input type=\"hidden\" name=\"".concat(nombre).
                concat("\" value=\"").concat(valor).concat("\">");
        return formato;
    }

    private void buildPeticionPostWrapper(String documento, String tipoDocumento) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("rc:Toolbar", "False");
        parametrosMap.put("PrmCedulaCliente", "[Cliente].[Id].&[" + documento + "]");
        parametrosMap.put("PrmTipoDocumento", "[Tipo Documento].[Tipo Documento].&[" + tipoDocumento + "]");

        parametrosMap.put("rc:Toolbar", "False");
        peticionPostWrapper = new PeticionPostWrapper();

        peticionPostWrapper.setUrl(URL);
        peticionPostWrapper.setParametrosMap(parametrosMap);

    }

    public void matarProceso(String pid) {
        try {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") 
                    + "##" + "Visor: matando procesos... ##" + gestorDoc.obtenerHoraActual());
            Logger.getGlobal().log(Level.INFO, "Matando proceso {0}", pid);
            String comando = "cmd.exe /C taskkill /f /PID " + pid;
            Runtime.getRuntime().exec(comando);
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") 
                    + "##" + "Visor: procesos finalizados con exito ##" + gestorDoc.obtenerHoraActual());

        } catch (IOException ex) {
            Logger.getLogger(VisorSelenium.class.getName()).
                    log(Level.SEVERE, "No se pudo ejecutar comando en cmd", ex);
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + 
                    "##" + "Visor: Error inesperado mientras se ejecutaba el modulo visor"
                            + " = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());

        }
    }

    public void capturarProcesosSistema(List<String> procesos) {
        Process process;
        try {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") 
                    + "##" + "Visor: Iniciando captura de procesos... ##" + gestorDoc.obtenerHoraActual());
            Logger.getGlobal().log(Level.INFO, "capturando procesos sistema");
            String comando = "cmd.exe /C tasklist /SVC /fi \"IMAGENAME eq iexplore.exe\"  ";
            process = Runtime.getRuntime().exec(comando);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String linea;
            String pid;
            int numeroLinea = 0;
            boolean hayTareas = true;
            while (((linea = br.readLine()) != null) && (hayTareas)) {
                if (numeroLinea > 2) {
                    pid = linea.substring(27, 34).trim();
                    procesos.add(pid);
                }
                if (!linea.isEmpty() && linea.contains("no hay tareas")) {
                    procesos.add("000");
                    hayTareas = false;

                }
                numeroLinea++;
            }
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") 
                    + "##" + "Visor: procesos capturados con exito ##" + gestorDoc.obtenerHoraActual());

        } catch (IOException ex) {            
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + 
                    "##" + "Visor: Error inesperado mientras se ejecutaba el modulo visor"
                            + " = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());

            Logger.getLogger(VisorSelenium.class.getName()).
                    log(Level.SEVERE, "No se pudo ejecutar comando en cmd", ex);

        }
    }

    public void send() throws MalformedURLException {
        URL url = new URL(URL);
        Map<String, Object> params = new LinkedHashMap<>();

        params.put("rc:Toolbar", "False");
        params.put("PrmCedulaCliente", "11111111111111111");
        params.put("PrmTipoDocumento", "C.C");

        StringBuilder postData = new StringBuilder();
        try {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") 
                    + "##" + "Visor: enviando mail falso ##" + gestorDoc.obtenerHoraActual());
            for (Map.Entry<String, Object> param : params.entrySet()) {

                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
                        "UTF-8"));

            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length",
                    String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") 
                    + "##" + "Visor: finaliza envio de mail con exito ##" + gestorDoc.obtenerHoraActual());

        } catch (IOException  ex) {
            gestorDoc.imprimir(System.getProperties().getProperty("user.name") + 
                    "##" + "Visor: Error inesperado mientras se ejecutaba el modulo visor"
                            + " = " + ex.toString() + "##" + gestorDoc.obtenerHoraActual());
            Logger.getLogger(VisorSelenium.class.getName()).log(Level.SEVERE, ex.toString());
        }
    }
}
