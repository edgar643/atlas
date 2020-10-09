/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo;

import java.util.HashSet;

/**
 *
 * @author alexander.lopez.o
 */
public class AtlasAcceso {

    public static final HashSet<String> mapBarrido = MapBarridoCtas();
    public static final HashSet<String> mapLesBasica = MapLesBasica();
    public static final HashSet<String> mapMillas = MapMillas();
    public static final HashSet<String> mapVirtuales = MapVirtuales();
    public static final HashSet<String> mapEmpresas = MapEmpresas();
    public static final HashSet<String> mapPreferencial = MapPreferencial();
    public static final HashSet<String> mapAlertas = MapAlertas();
    public static final HashSet<String> mapEstablecimientos = MapEstablecimientos();
    public static final HashSet<String> mapCampanasInteractivas = MapCampanasInteractivas();
    public static final HashSet<String> mapFidelizacion = MapFidelizacion();

    public static HashSet<String> MapBarridoCtas() {
        final HashSet<String> result = new HashSet<String>();
        result.add("BE");
        result.add("LE");
        result.add("TC");
        result.add("BM");
        result.add("BP");
        result.add("1V");
        result.add("3D");
        result.add("X3");
        result.add("TN");
        result.add("1Q");
        result.add("3F");
        result.add("X4");
        result.add("TQ");
        result.add("E1");
        result.add("XD");
        result.add("QR");

        return result;
    }

    public static HashSet<String> MapMillas() {
        final HashSet<String> result = new HashSet<String>();
        result.add("ML");
        result.add("AU");
        result.add("1T");
        result.add("3B");
        result.add("X1");
        result.add("RM");

        return result;
    }

    public static HashSet<String> MapVirtuales() {
        final HashSet<String> result = new HashSet<String>();
        result.add("LL");
        result.add("WS");
        result.add("WP");
        result.add("KP");
        result.add("WE");
        result.add("KE");
        result.add("HT");

        return result;
    }

    public static HashSet<String> MapLesBasica() {
        final HashSet<String> result = new HashSet<String>();
        result.add("FS");
        result.add("2F");
        result.add("TC");
        result.add("XL");
        result.add("BR");
        result.add("LP");
        result.add("2I");

        return result;
    }

    public static HashSet<String> MapPreferencial() {
        final HashSet<String> result = new HashSet<String>();
        result.add("E1");
        result.add("XD");
        result.add("KP");
        result.add("WP");
        result.add("P6");
        result.add("P7");
        result.add("P8");
        result.add("P9");
        result.add("P1");
        result.add("P2");
        result.add("P3");
        result.add("P0");
        result.add("AB");
        result.add("XE");
        result.add("PZ");
        result.add("PB");
        result.add("PN");
        result.add("G1");
        result.add("G2");
        result.add("G3");
        result.add("P4");
        result.add("P5");
        result.add("T2");
        result.add("T3");
        result.add("T4");
        result.add("H2");
        result.add("H3");
        result.add("H4");
        result.add("Z1");
        result.add("Z2");
        result.add("Z3");
        result.add("V5");
        result.add("V6");
        result.add("V7");
        result.add("W1");
        result.add("W2");
        result.add("W3");
        result.add("T5");
        result.add("T6");
        result.add("T7");
        result.add("V8");
        result.add("V9");
        result.add("V0");
        result.add("W6");
        result.add("W7");
        result.add("W8");
        result.add("E5");
        result.add("E6");
        result.add("E7");
        result.add("H9");
        result.add("VF");
        result.add("JR");
        result.add("TT");
        result.add("AV");
        result.add("AN");
        result.add("CC");
        result.add("PZ");
        result.add("CL");
        result.add("A1");
        result.add("A2");
        result.add("A3");
        result.add("A4");
        result.add("A5");
        result.add("A6");
        result.add("A7");
        result.add("A8");
        result.add("A9");

        return result;
    }

    public static HashSet<String> MapAlertas() {
        final HashSet<String> result = new HashSet<String>();
        result.add("AN");
        result.add("1R");
        result.add("1Y");
        result.add("3H");
        result.add("X6");
        result.add("RA");

        return result;
    }

    public static HashSet<String> MapEmpresas() {
        final HashSet<String> result = new HashSet<String>();
        result.add("BE");
        result.add("LE");
        result.add("TC");
        result.add("BM");
        result.add("BP");
        result.add("1V");
        result.add("3D");
        result.add("X3");
        result.add("TN");
        result.add("SE");
        return result;
    }

    public static HashSet<String> MapEstablecimientos() {
        final HashSet<String> result = new HashSet<String>();
        result.add("AX");
        result.add("3Q");
        result.add("XA");
        result.add("RX");
        result.add("EC");
        result.add("TW");
        return result;
    }

    public static HashSet<String> MapCampanasInteractivas() {
        final HashSet<String> result = new HashSet<String>();
        result.add("CI");
        result.add("CT");
        result.add("IC");
        return result;
    }

    public static HashSet<String> MapFidelizacion() {
        final HashSet<String> result = new HashSet<String>();
        result.add("CC");
        result.add("2C");
        result.add("3C");
        result.add("2Y");
        result.add("X2");
        result.add("RO");
        result.add("HC");
        result.add("RV");
        return result;
    }

    public HashSet<String> getMapBarrido() {
        return this.mapBarrido;
    }

    public HashSet<String> getMapMillas() {
        return this.mapMillas;
    }

    public HashSet<String> getMapLesBasica() {
        return this.mapLesBasica;
    }

    public HashSet<String> getMapPreferencial() {
        return this.mapPreferencial;
    }

    public HashSet<String> getMapAlertas() {
        return this.mapAlertas;
    }

    public HashSet<String> getMapVirtuales() {
        return this.mapVirtuales;
    }

    public HashSet<String> getMapEmpresas() {
        return this.mapEmpresas;
    }

    public HashSet<String> getMapEstablecimientos() {
        return this.mapEstablecimientos;
    }

    public HashSet<String> getMapCampanasInteractivas() {
        return this.mapCampanasInteractivas;
    }

    public HashSet<String> getMapFidelizacion() {
        return this.mapFidelizacion;
    }
}
