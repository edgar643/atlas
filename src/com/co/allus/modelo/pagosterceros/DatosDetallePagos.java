/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagosterceros;

/**
 *
 * @author stephania.rojas
 */
public class DatosDetallePagos {

    private static DatosDetallePagos dataDetalle = new DatosDetallePagos();
    private String codConvenio = "";
    private String nomConvenio = "";
    private String refFija = "";
    private String tipoConv = "";
    private String descripcion = "";
    private String fechavencimiento = "";
    private String FrecuenciaPago = "";
    private String NumeroDias = "";
    private String NumeroSemana = "";
    private String DiaSemana = "";
    private String DiaMes = "";
    private String DiasReintento = "";
    private String FechaInicio = "";
    private String FechaFin = "";
    private String ValorFactura = "";
    private String FechaSiguientePago = "";
    private String Estado = "";
    private String Canal = "";
    private String CodigoBanco = "";
    private String NombreBanco = "";
    private String NumeroCuentaPagador = "";
    private String TipoCuentaPagador = "";
    private String referencia1 = "";
    private String referencia2 = "";
    private String referencia3 = "";
    private String indicadorReferenciaFija = "";
    private String criteriopago="";
    private String pagoparcial="";

    public String getCriteriopago() {
        return criteriopago;
    }

    public void setCriteriopago(String criteriopago) {
        this.criteriopago = criteriopago;
    }

    public String getPagoparcial() {
        return pagoparcial;
    }

    public void setPagoparcial(String pagoparcial) {
        this.pagoparcial = pagoparcial;
    }
    
    
    private InfoTablaPagosTerceros selectedItem;

    public InfoTablaPagosTerceros getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(InfoTablaPagosTerceros selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(String fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getFrecuenciaPago() {
        return FrecuenciaPago;
    }

    public void setFrecuenciaPago(String FrecuenciaPago) {
        this.FrecuenciaPago = FrecuenciaPago;
    }

    public String getNumeroDias() {
        return NumeroDias;
    }

    public void setNumeroDias(String NumeroDias) {
        this.NumeroDias = NumeroDias;
    }

    public String getNumeroSemana() {
        return NumeroSemana;
    }

    public void setNumeroSemana(String NumeroSemana) {
        this.NumeroSemana = NumeroSemana;
    }

    public String getDiaSemana() {
        return DiaSemana;
    }

    public void setDiaSemana(String DiaSemana) {
        this.DiaSemana = DiaSemana;
    }

    public String getDiaMes() {
        return DiaMes;
    }

    public void setDiaMes(String DiaMes) {
        this.DiaMes = DiaMes;
    }

    public String getDiasReintento() {
        return DiasReintento;
    }

    public void setDiasReintento(String DiasReintento) {
        this.DiasReintento = DiasReintento;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String FechaFin) {
        this.FechaFin = FechaFin;
    }

    public String getValorFactura() {
        return ValorFactura;
    }

    public void setValorFactura(String ValorFactura) {
        this.ValorFactura = ValorFactura;
    }

    public String getFechaSiguientePago() {
        return FechaSiguientePago;
    }

    public void setFechaSiguientePago(String FechaSiguientePago) {
        this.FechaSiguientePago = FechaSiguientePago;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getCanal() {
        return Canal;
    }

    public void setCanal(String Canal) {
        this.Canal = Canal;
    }

    public String getCodigoBanco() {
        return CodigoBanco;
    }

    public void setCodigoBanco(String CodigoBanco) {
        this.CodigoBanco = CodigoBanco;
    }

    public String getNombreBanco() {
        return NombreBanco;
    }

    public void setNombreBanco(String NombreBanco) {
        this.NombreBanco = NombreBanco;
    }

    public String getNumeroCuentaPagador() {
        return NumeroCuentaPagador;
    }

    public void setNumeroCuentaPagador(String NumeroCuentaPagador) {
        this.NumeroCuentaPagador = NumeroCuentaPagador;
    }

    public String getTipoCuentaPagador() {
        return TipoCuentaPagador;
    }

    public void setTipoCuentaPagador(String TipoCuentaPagador) {
        this.TipoCuentaPagador = TipoCuentaPagador;
    }

    public String getReferencia1() {
        return referencia1;
    }

    public void setReferencia1(String referencia1) {
        this.referencia1 = referencia1;
    }

    public String getReferencia2() {
        return referencia2;
    }

    public void setReferencia2(String referencia2) {
        this.referencia2 = referencia2;
    }

    public String getReferencia3() {
        return referencia3;
    }

    public void setReferencia3(String referencia3) {
        this.referencia3 = referencia3;
    }

    public String getIndicadorReferenciaFija() {
        return indicadorReferenciaFija;
    }

    public void setIndicadorReferenciaFija(String indicadorReferenciaFija) {
        this.indicadorReferenciaFija = indicadorReferenciaFija;
    }

    public static DatosDetallePagos getDataDetalle() {
        return dataDetalle;
    }

    public static void setDataDetalle(DatosDetallePagos dataDetalle) {
        dataDetalle.dataDetalle = dataDetalle;
    }

    public String getCodConvenio() {
        return codConvenio;
    }

    public void setCodConvenio(String codConvenio) {
        this.codConvenio = codConvenio;
    }

    public String getNomConvenio() {
        return nomConvenio;
    }

    public void setNomConvenio(String nomConvenio) {
        this.nomConvenio = nomConvenio;
    }

    public String getRefFija() {
        return refFija;
    }

    public void setRefFija(String refFija) {
        this.refFija = refFija;
    }

    public String getTipoConv() {
        return tipoConv;
    }

    public void setTipoConv(String tipoConv) {
        this.tipoConv = tipoConv;
    }
    public static final String NOMCONV = "NOMBRE DE CONVENIO - EMPRESA";
    public static final String FECHAVEN = "FECHA VENCIMIENTO";
    public static final String DESCRIPCION = "DESCRIPCIÓN FACTURA";
    public static final String FRECUENCIAPAGO = "FRECUENCIA DEL PAGO";
    public static final String NUMERODIAS = "CANTIDAD DÍAS DÉBITO AUTOMÁTICO";
    public static final String NUMEROSEMANA = "SEMANA DÉBITO AUTOMÁTICO";
    public static final String DIASEMANA = "DÍA SEMANA DÉBITO AUTOMÁTICO";
    public static final String DIAMES = "DÍA MES DÉBITO AUTOMÁTICO";
    public static final String DIASREINTENTO = "NÚMERO DE REINTENTOS";
    public static final String FECHAINICIO = "FECHA INICIO";
    public static final String FECHAFIN = "FECHA FIN DE LA PROGRAMACIÓN";
    public static final String VALORFACTURA = "VALOR";
    public static final String FECHASIGUIENTEPAGO = "FECHA DEL PRÓXIMO DÉBITO";
    public static final String ESTADO = "ESTADO DE LA PROGRAMACIÓN";
    public static final String CANAL = "CANAL DE LA PROGRAMACIÓN";
    public static final String CODIGOBANCO = "CÓDIGO BANCO";
    public static final String NOMBREBANCO = "NOMBRE BANCO";
    public static final String NUMEROCUENTAPAGADOR = "NÚMERO CUENTA PAGADOR";
    public static final String TIPOCUENTAPAGADOR = "TIPO CUENTA PAGADOR";
    public static final String REFERENCIA1 = "REFERENCIA FIJA";
    public static final String REFERENCIA2 = "REFERENCIA VARIABLE 1";
    public static final String REFERENCIA3 = "REFERENCIA VARIABLE 2";
    public static final String CRITERIO_PAGO="CRITERIO PAGO";
    public static final String PAGO_PARCIAL="PAGO PARCIAL";
}
