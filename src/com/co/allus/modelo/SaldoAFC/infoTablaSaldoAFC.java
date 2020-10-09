/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.SaldoAFC;

import com.co.allus.modelo.consultadepositos.InfoTablaDetDeposito;

/**
 *
 * @author alexander.lopez.o
 */
public class infoTablaSaldoAFC {

    private static infoTablaSaldoAFC infoAFC = new infoTablaSaldoAFC();
    private String razonBloqueo = "";
    private String ahorroDisBenef = "";
    private String ahorrosinBenef = "";
    private String valorBenef = "";
    private String beneficioGanado = "";
    private String beneficioPerdido = "";
    private String rendimientos = "";
    private String saldocanje = "";
    private String valorcongelado2344 = "";
    private String valorRetirosTram = "";
    private String saldoDispVivienda = "";
    private String gmfRetiroVivienda = "";
    private String benefGanarVivienda = "";
    private String benefPerdidogmf = "";
    private String ahorro2344 = "";
    private String gmfVivienda2344 = "";
    private String benefPerdido2344gmf = "";
    private String benefCongelar2344 = "";
    private String saldoDispNoVivienda = "";
    private String benefPerderNoVivienda = "";
    private String ahorro2344NoBenef = "";
    private String benefPerder2344 = "";
    private String gmfRetiroNoVivnda = "";

    public static infoTablaSaldoAFC getInfoAFC() {
        return infoAFC;
    }

    public static void setInfoAFC(infoTablaSaldoAFC infoafc) {
        infoTablaSaldoAFC.infoAFC = infoafc;
    }

    public String getRazonBloqueo() {
        return razonBloqueo;
    }

    public void setRazonBloqueo(String razonBloqueo) {
        this.razonBloqueo = razonBloqueo;
    }

    public String getAhorroDisBenef() {
        return ahorroDisBenef;
    }

    public void setAhorroDisBenef(String ahorroDisBenef) {
        this.ahorroDisBenef = ahorroDisBenef;
    }

    public String getAhorrosinBenef() {
        return ahorrosinBenef;
    }

    public void setAhorrosinBenef(String ahorrosinBenef) {
        this.ahorrosinBenef = ahorrosinBenef;
    }

    public String getValorBenef() {
        return valorBenef;
    }

    public void setValorBenef(String valorBenef) {
        this.valorBenef = valorBenef;
    }

    public String getBeneficioGanado() {
        return beneficioGanado;
    }

    public void setBeneficioGanado(String beneficioGanado) {
        this.beneficioGanado = beneficioGanado;
    }

    public String getBeneficioPerdido() {
        return beneficioPerdido;
    }

    public void setBeneficioPerdido(String beneficioPerdido) {
        this.beneficioPerdido = beneficioPerdido;
    }

    public String getRendimientos() {
        return rendimientos;
    }

    public void setRendimientos(String rendimientos) {
        this.rendimientos = rendimientos;
    }

    public String getSaldocanje() {
        return saldocanje;
    }

    public void setSaldocanje(String saldocanje) {
        this.saldocanje = saldocanje;
    }

    public String getValorcongelado2344() {
        return valorcongelado2344;
    }

    public void setValorcongelado2344(String valorcongelado2344) {
        this.valorcongelado2344 = valorcongelado2344;
    }

    public String getValorRetirosTram() {
        return valorRetirosTram;
    }

    public void setValorRetirosTram(String valorRetirosTram) {
        this.valorRetirosTram = valorRetirosTram;
    }

    public String getSaldoDispVivienda() {
        return saldoDispVivienda;
    }

    public void setSaldoDispVivienda(String saldoDispVivienda) {
        this.saldoDispVivienda = saldoDispVivienda;
    }

    public String getGmfRetiroVivienda() {
        return gmfRetiroVivienda;
    }

    public void setGmfRetiroVivienda(String gmfRetiroVivienda) {
        this.gmfRetiroVivienda = gmfRetiroVivienda;
    }

    public String getBenefGanarVivienda() {
        return benefGanarVivienda;
    }

    public void setBenefGanarVivienda(String benefGanarVivienda) {
        this.benefGanarVivienda = benefGanarVivienda;
    }

    public String getBenefPerdidogmf() {
        return benefPerdidogmf;
    }

    public void setBenefPerdidogmf(String benefPerdidogmf) {
        this.benefPerdidogmf = benefPerdidogmf;
    }

    public String getAhorro2344() {
        return ahorro2344;
    }

    public void setAhorro2344(String ahorro2344) {
        this.ahorro2344 = ahorro2344;
    }

    public String getGmfVivienda2344() {
        return gmfVivienda2344;
    }

    public void setGmfVivienda2344(String gmfVivienda2344) {
        this.gmfVivienda2344 = gmfVivienda2344;
    }

    public String getBenefPerdido2344gmf() {
        return benefPerdido2344gmf;
    }

    public void setBenefPerdido2344gmf(String benefPerdido2344gmf) {
        this.benefPerdido2344gmf = benefPerdido2344gmf;
    }

    public String getBenefCongelar2344() {
        return benefCongelar2344;
    }

    public void setBenefCongelar2344(String benefCongelar2344) {
        this.benefCongelar2344 = benefCongelar2344;
    }

    public String getSaldoDispNoVivienda() {
        return saldoDispNoVivienda;
    }

    public void setSaldoDispNoVivienda(String saldoDispNoVivienda) {
        this.saldoDispNoVivienda = saldoDispNoVivienda;
    }

    public String getBenefPerderNoVivienda() {
        return benefPerderNoVivienda;
    }

    public void setBenefPerderNoVivienda(String benefPerderNoVivienda) {
        this.benefPerderNoVivienda = benefPerderNoVivienda;
    }

    public String getAhorro2344NoBenef() {
        return ahorro2344NoBenef;
    }

    public void setAhorro2344NoBenef(String ahorro2344NoBenef) {
        this.ahorro2344NoBenef = ahorro2344NoBenef;
    }

    public String getBenefPerder2344() {
        return benefPerder2344;
    }

    public void setBenefPerder2344(String benefPerder2344) {
        this.benefPerder2344 = benefPerder2344;
    }

    public String getGmfRetiroNoVivnda() {
        return gmfRetiroNoVivnda;
    }

    public void setGmfRetiroNoVivnda(String gmfRetiroNoVivnda) {
        this.gmfRetiroNoVivnda = gmfRetiroNoVivnda;
    }
    public static final String RAZONBLOQUEO = "Razón de Bloqueo";
    public static final String AHODISPBNF = "Ahorro Disponible con Beneficio";
    public static final String AHOSINBNF = "Ahorro sin Beneficio";
    public static final String VALBNF = "Valor Beneficio";
    public static final String BNFGANADO = "Beneficio Ganado";
    public static final String BNFPERDIDO = "Beneficio Perdido";
    public static final String RENDIMIENTOS = "Rendimientos";
    public static final String SALDOCANJE = "Saldo en Canje";
    public static final String VALORCONG2344 = "Valor Congelado (Decreto 2344)";
    public static final String VALORRETIROTRAM = "Valor Retiros en Trámite";
    public static final String SALDODISPVIVNDA = "Saldo Disponible para Vivienda";
    public static final String GMFRETIROVIVNDA = "GMF por Retiro para Vivienda";
    public static final String BNFGANARVIVNDA = "Beneficio a Ganar para Vivienda";
    public static final String BNFPERDIDOGMF = "Beneficio Perdido GMF";
    public static final String AHO2344 = "Ahorro por Decreto 2344";
    public static final String GMFVIVNDA2344 = "GMF Vivienda Decreto 2344";
    public static final String BNFPERDIDO2344GMF = "Beneficio Perdido por Decreto 2344(GMF)";
    public static final String BNFCONG2344 = "Beneficio a Congelar por Decreto 2344";
    public static final String SALDODISPNOVIVNDA = "Saldo Disponible Diferente de Vivienda";
    public static final String BNFPERDERNOVIVNDA = "Beneficio a Perder Diferente de Vivienda";
    public static final String AHO2344NOBNF = "Ahorro por Decreto 2344 sin Beneficio";
    public static final String BNFPERDER2344 = "Beneficio a Perder por Decreto 2344";
    public static final String GMFRETIRONOVIVNDA = "GMF por Retiro Diferente de Vivienda";
}
