/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.consultadepositos;

/**
 *
 * @author stephania.rojas
 */
public class InfoTablaDetDeposito {

    private static InfoTablaDetDeposito infoDetDeposito = new InfoTablaDetDeposito();
    //TABLA 1
    private String TipoCuenta = "";
    private String OficinaDue = "";
    private String OficinaApe = "";
    private String Estado = "";
    private String Bloqueos = "";
    private String RazonBloqueo = "";
    private String DiasInactividad = "";
    private String CodigoPlan = "";
    private String NombrePlan = "";
    private String Periodicidad = "";
    private String Impresion = "";
    private String TipoGMF = "";
    private String CodigoOficial = "";
    //TABLA 2
    private String FechaApertura = "";
    private String FechaCancelacion = "";
    private String FechatrxCredito = "";
    private String FechatrxDebito = "";
    //TABLA 3
    private String InterPagadosActual = "";
    private String InterAnterior = "";
    private String TablaInteres = "";
    private String TablaCargos = "";

    public static InfoTablaDetDeposito getInfoTablaDetDeposito() {
        return infoDetDeposito;
    }

    public static void setInfoTablaDetDeposito(InfoTablaDetDeposito infoDetDeposito) {
        InfoTablaDetDeposito.infoDetDeposito = infoDetDeposito;
    }

    public String getTipoCuenta() {
        return TipoCuenta;
    }

    public void setTipoCuenta(String TipoCuenta) {
        this.TipoCuenta = TipoCuenta;
    }

    public String getOficinaDue() {
        return OficinaDue;
    }

    public void setOficinaDue(String OficinaDue) {
        this.OficinaDue = OficinaDue;
    }

    public String getOficinaApe() {
        return OficinaApe;
    }

    public void setOficinaApe(String OficinaApe) {
        this.OficinaApe = OficinaApe;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getBloqueos() {
        return Bloqueos;
    }

    public void setBloqueos(String Bloqueos) {
        this.Bloqueos = Bloqueos;
    }

    public String getRazonBloqueo() {
        return RazonBloqueo;
    }

    public void setRazonBloqueo(String RazonBloqueo) {
        this.RazonBloqueo = RazonBloqueo;
    }

    public String getDiasInactividad() {
        return DiasInactividad;
    }

    public void setDiasInactividad(String DiasInactividad) {
        this.DiasInactividad = DiasInactividad;
    }

    public String getCodigoPlan() {
        return CodigoPlan;
    }

    public void setCodigoPlan(String CodigoPlan) {
        this.CodigoPlan = CodigoPlan;
    }

    public String getNombrePlan() {
        return NombrePlan;
    }

    public void setNombrePlan(String NombrePlan) {
        this.NombrePlan = NombrePlan;
    }

    public String getPeriodicidad() {
        return Periodicidad;
    }

    public void setPeriodicidad(String Periodicidad) {
        this.Periodicidad = Periodicidad;
    }

    public String getImpresion() {
        return Impresion;
    }

    public void setImpresion(String Impresion) {
        this.Impresion = Impresion;
    }

    public String getFechaApertura() {
        return FechaApertura;
    }

    public void setFechaApertura(String FechaApertura) {
        this.FechaApertura = FechaApertura;
    }

    public String getFechaCancelacion() {
        return FechaCancelacion;
    }

    public void setFechaCancelacion(String FechaCancelacion) {
        this.FechaCancelacion = FechaCancelacion;
    }

    public String getFechatrxCredito() {
        return FechatrxCredito;
    }

    public void setFechatrxCredito(String FechatrxCredito) {
        this.FechatrxCredito = FechatrxCredito;
    }

    public String getFechatrxDebito() {
        return FechatrxDebito;
    }

    public void setFechatrxDebito(String FechatrxDebito) {
        this.FechatrxDebito = FechatrxDebito;
    }

    public String getTipoGMF() {
        return TipoGMF;
    }

    public void setTipoGMF(String TipoGMF) {
        this.TipoGMF = TipoGMF;
    }

    public String getCodigoOficial() {
        return CodigoOficial;
    }

    public void setCodigoOficial(String CodigoOficial) {
        this.CodigoOficial = CodigoOficial;
    }

    public String getInterPagadosActual() {
        return InterPagadosActual;
    }

    public void setInterPagadosActual(String InterPagadosActual) {
        this.InterPagadosActual = InterPagadosActual;
    }

    public String getInterAnterior() {
        return InterAnterior;
    }

    public void setInterAnterior(String InterAnterior) {
        this.InterAnterior = InterAnterior;
    }

    public String getTablaInteres() {
        return TablaInteres;
    }

    public void setTablaInteres(String TablaInteres) {
        this.TablaInteres = TablaInteres;
    }

    public String getTablaCargos() {
        return TablaCargos;
    }

    public void setTablaCargos(String TablaCargos) {
        this.TablaCargos = TablaCargos;
    }
    public static final String NumCuenta = "Número de Cuenta";
    public static final String OFICINADUE = "Oficina Dueña";
    public static final String OFICINAAPE = "Oficina Apertura";
    public static final String ESTADO = "Estado";
    public static final String BLOQUEOS = "Bloqueos";
    public static final String RAZONBLOQUEO = "Razón de Bloqueo";
    public static final String DIASINACTIVIDAD = "Días de Inactividad";
    public static final String CODIGOPLAN = "Código del Plan";
    public static final String NOMBREPLAN = "Nombre del Plan";
    public static final String PERIODICIDAD = "Periodicidad del extracto";
    public static final String IMPRESION = "Impresión del extracto";
    public static final String FECHAAPERTURA = "Fecha de Apertura";
    public static final String FECHACANCELACION = "Fecha de Cancelación";
    public static final String FECHATRXCREDITO = "Fecha última TRX Crédito";
    public static final String FECHATRXDEBITO = "Fecha última TRX Débito";
    public static final String TIPOGMF = "Tipo de exención de GMF";
    public static final String CODIGOOFICIAL = "Código del oficial de la cuenta";
    public static final String INTERPAGADOSACTUAL = "Inter. Rendimientos pagados año actual";
    public static final String INTERANTERIOR = "Inter. Rendimientos año anterior";
    public static final String TABLAINTERES = "Tabla de Interés (rendimientos)";
    public static final String TABLACARDOS = "Tabla de cargos o comisiones";
}
