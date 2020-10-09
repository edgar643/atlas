/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosaterceros;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class infoPagDebAuto {

    public static infoPagDebAuto infDebAuto = new infoPagDebAuto();
    private SimpleStringProperty tipodocpag;
    private SimpleStringProperty numdoc;
    private SimpleStringProperty nompagador;
    private SimpleStringProperty reffija;
    private SimpleStringProperty codigobanco;
    private SimpleStringProperty nombanco;
    private SimpleStringProperty tipoctatarj;
    private SimpleStringProperty numctatarj;

    public infoPagDebAuto() {
    }

    public infoPagDebAuto(String tipodocpag,
            String numdoc,
            String nompagador,
            String reffija,
            String codigobanco,
            String nombanco,
            String tipoctatarj,
            String numctatarj) {

        this.tipodocpag = new SimpleStringProperty(tipodocpag);
        this.numdoc = new SimpleStringProperty(numdoc);
        this.nompagador = new SimpleStringProperty(nompagador);
        this.reffija = new SimpleStringProperty(reffija);
        this.codigobanco = new SimpleStringProperty(codigobanco);
        this.nombanco = new SimpleStringProperty(nombanco);
        this.tipoctatarj = new SimpleStringProperty(tipoctatarj);
        this.numctatarj = new SimpleStringProperty(numctatarj);
    }

    public static infoPagDebAuto getInfDebAuto() {
        return infDebAuto;
    }

    public static void setInfDebAuto(infoPagDebAuto infDebAuto) {
        infoPagDebAuto.infDebAuto = infDebAuto;
    }

    public String getTipodocpag() {
        return tipodocpag.get();
    }

    public void setTipodocpag(String tipodocpag) {
        this.tipodocpag.set(tipodocpag);
    }

    public String getNumdoc() {
        return numdoc.get();
    }

    public void setNumdoc(String numdoc) {
        this.numdoc.set(numdoc);
    }

    public String getNompagador() {
        return nompagador.get();
    }

    public void setNompagador(String nompagador) {
        this.nompagador.set(nompagador);
    }

    public String getReffija() {
        return reffija.get();
    }

    public void setReffija(String reffija) {
        this.reffija.set(reffija);
    }

    public String getCodigobanco() {
        return codigobanco.get();
    }

    public void setCodigobanco(String codigobanco) {
        this.codigobanco.set(codigobanco);
    }

    public String getNombanco() {
        return nombanco.get();
    }

    public void setNombanco(String nombanco) {
        this.nombanco.set(nombanco);
    }

    public String getTipoctatarj() {
        return tipoctatarj.get();
    }

    public void setTipoctatarj(String tipoctatarj) {
        this.tipoctatarj.set(tipoctatarj);
    }

    public String getNumctatarj() {
        return numctatarj.get();
    }

    public void setNumctatarj(String numctatarj) {
        this.numctatarj.set(numctatarj);
    }
}
