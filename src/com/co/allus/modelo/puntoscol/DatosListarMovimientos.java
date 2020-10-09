/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.puntoscol;

/**
 *
 * @author roberto.ceballos
 */
public class DatosListarMovimientos {

    private static DatosListarMovimientos datosListarMovimientos = new DatosListarMovimientos();
    private String fecha_trx = "";
    private String hora_trx = "";
    private String id_trx = "";
    private String valor_trx_pesos = "";
    private String valor_trx_dolares = "";
    private String cod_trx = "";
    private String descripcion_trx = "";

    public static DatosListarMovimientos getDatosListarMovimientos() {
        return datosListarMovimientos;
    }

    public static void setDatosListarMovimientos(DatosListarMovimientos datosListarMovimientos) {
        DatosListarMovimientos.datosListarMovimientos = datosListarMovimientos;
    }

    public String getFechaTrx() {
        return fecha_trx;
    }

    public void setFechaTrx(String fecha_trx) {
        this.fecha_trx = fecha_trx;
    }

    public String getHoraTrx() {
        return hora_trx;
    }

    public void setHoraTrx(String hora_trx) {
        this.hora_trx = hora_trx;
    }

    public String getIdTrx() {
        return id_trx;
    }

    public void setIdTrx(String id_trx) {
        this.id_trx = id_trx;
    }

    public String getValorTrxPesos() {
        return valor_trx_pesos;
    }

    public void setValorTrxPesos(String valor_trx_pesos) {
        this.valor_trx_pesos = valor_trx_pesos;
    }

    public String getValorTrxDolares() {
        return valor_trx_dolares;
    }

    public void setFechaPago(String valor_trx_dolares) {
        this.valor_trx_dolares = valor_trx_dolares;
    }

    public String getCodTrx() {
        return cod_trx;
    }

    public void setCodTrx(String cod_trx) {
        this.cod_trx = cod_trx;
    }

    public String getDescripcionTrx() {
        return descripcion_trx;
    }

    public void setDescripcionTrx(String descripcion_trx) {
        this.descripcion_trx = descripcion_trx;
    }
    public static final String FECHA_TRX = "Fecha";
    public static final String HORA_TRX = "Hora";
    public static final String ID_TRX = "Id Transacción";
    public static final String VALOR_TRX_PESOS = "Valor en Pesos";
    public static final String VALOR_TRX_DOLARES = "Valor en Dolares";
    public static final String COD_TRX = "Código Transacción";
    public static final String DESCRIPCION_TRX = "Descripción Transacción";
}
