/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.corehipotecario;

/**
 *
 * @author alexander.lopez.o
 */
public class DatosSaldoH {

    private static DatosSaldoH datosSaldoh = new DatosSaldoH();
    private String obligacion = "";
    private String saldo = "";
    private String fechacuota = "";
    private String valorcuotasaldo = "";
    private String seguro = "";
    private String estadoCredito = "";
    private String saldoCapital = "";
    private String saldoIntereses = "";
    private String saldomora = "";
    private String otrossaldos = "";
    private String fechavencimiento = "";
    private String valorinicial = "";
    private String tasainteres = "";
    private String fechadesenbolso = "";
    private String numerocuota = "";
    private String numerocuotasmora = "";
    private String plancredito = "";
    private String valorcuotamora = "";
    private String tipoidcliente = "";
    private String numidcliente = "";
    private String nombrecliente = "";
    private String numproxcuota = "";
    private String fechaproxcuota = "";
    private String valorcuotasinseg = "";
    private String numcretidoant = "";
    private String numcretidoorig = "";
    private String ingrerecibxant = "";
    private String segurosrecibxant = "";

    public static DatosSaldoH getDatosSaldoh() {
        return datosSaldoh;
    }

    public static void setDatosSaldoh(DatosSaldoH datosSaldoh) {
        DatosSaldoH.datosSaldoh = datosSaldoh;
    }
    public static final String OBLIGACION = "Número de Crédito";
    public static final String SALDO = "Saldo Total";
    public static final String FECHA_CUOTA = "Fecha de la Cuota";
    public static final String VALOR_CUOTA_SALDO = "Valor a Pagar";
    public static final String SEGURO = "Saldo Seguro";
    public static final String ESTADO_CREDITO = "M/D";
    public static final String SALDO_CAPITAL = "Saldo Capital";
    public static final String SALDO_INTERESES = "Saldo Interès Corriente";
    public static final String SALDO_MORA = "Saldo Interès de Mora";
    public static final String OTROS_SALDOS = "Otros Saldos";
    public static final String FECHA_VENCIMIENTO = "Fecha de Vencimiento";
    public static final String VALOR_INICIAL = "Valor del Desembolso";
    public static final String TASA_INTERES = "Tasa de Interés";
    public static final String FECHA_DESENBOLSO = "Fecha de Desembolso";
    public static final String NUMERO_CUOTA = "Número de Cuota";
    public static final String NUMERO_CUOTAS_MORA = "Número de Cuotas en Mora";
    public static final String PLAN_CREDITO = "Plan";
    public static final String VALOR_CUOTA_MORA = "Valor Cuotas Mora";
    public static final String TIPO_ID_CLIENTE = "Tipo de Identificacion del Cliente";
    public static final String NUM_ID_CLIENTE = "Número de Identificacion del Cliente";
    public static final String NOMBRE_CLIENTE = "Nombre del Cliente";
    public static final String NUMERO_CREDITO_ANT = "Número de crédito Anterior";
    public static final String NUMERO_CREDITO_ORI = "Número de crédito Original";
    public static final String INGRESOS_RECIBIDOS_X_ANT = "Ingresos recibidos por anticipado";
    public static final String SEGUROS_RECIBIDOS_X_ANT = "Seguros recibidos por anticipado";

    public String getValorcuotasinseg() {
        return valorcuotasinseg;
    }

    public void setValorcuotasinseg(String valorcuotasinseg) {
        this.valorcuotasinseg = valorcuotasinseg;
    }

    public String getNumproxcuota() {
        return numproxcuota;
    }

    public void setNumproxcuota(String numproxcuota) {
        this.numproxcuota = numproxcuota;
    }

    public String getFechaproxcuota() {
        return fechaproxcuota;
    }

    public void setFechaproxcuota(String fechaproxcuota) {
        this.fechaproxcuota = fechaproxcuota;
    }

    public String getObligacion() {
        return obligacion;
    }

    public void setObligacion(String obligacion) {
        this.obligacion = obligacion;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getFechacuota() {
        return fechacuota;
    }

    public void setFechacuota(String fechacuota) {
        this.fechacuota = fechacuota;
    }

    public String getValorcuotasaldo() {
        return valorcuotasaldo;
    }

    public void setValorcuotasaldo(String valorcuotasaldo) {
        this.valorcuotasaldo = valorcuotasaldo;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getEstadoCredito() {
        return estadoCredito;
    }

    public void setEstadoCredito(String estadoCredito) {
        this.estadoCredito = estadoCredito;
    }

    public String getSaldoCapital() {
        return saldoCapital;
    }

    public void setSaldoCapital(String saldoCapital) {
        this.saldoCapital = saldoCapital;
    }

    public String getSaldoIntereses() {
        return saldoIntereses;
    }

    public void setSaldoIntereses(String saldoIntereses) {
        this.saldoIntereses = saldoIntereses;
    }

    public String getSaldomora() {
        return saldomora;
    }

    public void setSaldomora(String saldomora) {
        this.saldomora = saldomora;
    }

    public String getOtrossaldos() {
        return otrossaldos;
    }

    public void setOtrossaldos(String otrossaldos) {
        this.otrossaldos = otrossaldos;
    }

    public String getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(String fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getValorinicial() {
        return valorinicial;
    }

    public void setValorinicial(String valorinicial) {
        this.valorinicial = valorinicial;
    }

    public String getTasainteres() {
        return tasainteres;
    }

    public void setTasainteres(String tasainteres) {
        this.tasainteres = tasainteres;
    }

    public String getFechadesenbolso() {
        return fechadesenbolso;
    }

    public void setFechadesenbolso(String fechadesenbolso) {
        this.fechadesenbolso = fechadesenbolso;
    }

    public String getNumerocuota() {
        return numerocuota;
    }

    public void setNumerocuota(String numerocuota) {
        this.numerocuota = numerocuota;
    }

    public String getNumerocuotasmora() {
        return numerocuotasmora;
    }

    public void setNumerocuotasmora(String numerocuotasmora) {
        this.numerocuotasmora = numerocuotasmora;
    }

    public String getPlancredito() {
        return plancredito;
    }

    public void setPlancredito(String plancredito) {
        this.plancredito = plancredito;
    }

    public String getValorcuotamora() {
        return valorcuotamora;
    }

    public void setValorcuotamora(String valorcuotamora) {
        this.valorcuotamora = valorcuotamora;
    }

    public String getTipoidcliente() {
        return tipoidcliente;
    }

    public void setTipoidcliente(String tipoidcliente) {
        this.tipoidcliente = tipoidcliente;
    }

    public String getNumidcliente() {
        return numidcliente;
    }

    public void setNumidcliente(String numidcliente) {
        this.numidcliente = numidcliente;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getNumcretidoant() {
        return numcretidoant;
    }

    public void setNumcretidoant(String numcretidoant) {
        this.numcretidoant = numcretidoant;
    }

    public String getNumcretidoorig() {
        return numcretidoorig;
    }

    public void setNumcretidoorig(String numcretidoorig) {
        this.numcretidoorig = numcretidoorig;
    }

    public String getIngrerecibxant() {
        return ingrerecibxant;
    }

    public void setIngrerecibxant(String ingrerecibxant) {
        this.ingrerecibxant = ingrerecibxant;
    }

    public String getSegurosrecibxant() {
        return segurosrecibxant;
    }

    public void setSegurosrecibxant(String segurosrecibxant) {
        this.segurosrecibxant = segurosrecibxant;
    }
}
