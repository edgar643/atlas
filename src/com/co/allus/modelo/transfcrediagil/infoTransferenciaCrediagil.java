/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.transfcrediagil;

/**
 *
 * @author stephania.rojas
 */
public class infoTransferenciaCrediagil {

    private static infoTransferenciaCrediagil infoTransfCrediagil = new infoTransferenciaCrediagil();
    private String plazo = "";
    private String tipo_cuenta = "";
    private String cuenta_destino = "";
    private String valor_prestamo_ent = "";
    private String num_prestamo = "";
    private String valor_prestamo_cent = "";
    private String valor = "";

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValor_prestamo_ent() {
        return valor_prestamo_ent;
    }

    public void setValor_prestamo_ent(String valor_prestamo_ent) {
        this.valor_prestamo_ent = valor_prestamo_ent;
    }

    public String getValor_prestamo_cent() {
        return valor_prestamo_cent;
    }

    public void setValor_prestamo_cent(String valor_prestamo_cent) {
        this.valor_prestamo_cent = valor_prestamo_cent;
    }

    public String getplazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    public String getCuenta_destino() {
        return cuenta_destino;
    }

    public void setCuenta_destino(String cuenta_destino) {
        this.cuenta_destino = cuenta_destino;
    }

    public static void setInfoTransfCrediagil(infoTransferenciaCrediagil valor) {
        infoTransfCrediagil = valor;
    }

    public static infoTransferenciaCrediagil getInfoTransfCrediagil() {
        return infoTransfCrediagil;
    }

    public String getNum_prestamo() {
        return num_prestamo;
    }

    public void setNum_prestamo(String num_prestamo) {
        this.num_prestamo = num_prestamo;
    }
}
