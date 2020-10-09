/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author alexander.lopez.o
 */
public class Cliente {

    private static Cliente cliente = new Cliente();
    private String descripcion = "";
    private String tipo_cliente = "";
    private String tipo_clinete_gtel = "";
    private String regla_negocio = "";
    private String valor_anexo = "";
    private String cti_origen = "";
    private String codigo_origen = "";
    private String ani = "";
    private String dnis = "";
    private String refid = "";
    private String industria = "";
    private String institucion = "";
    private String cod_apli = "";
    private String id_cliente = "";
    private String vv_Tipide = "";
    private String nombre = "";
    private String direccion = "";
    private String telefono = "";
    private String fax = "";
    private String vv_Fechna = "";
    private String vv_Fechv = "";
    private String vv_Fechac = "";
    private String clavesn = "";
    private String reservado1 = "";
    private String reservado2 = "";
    private String opc_final_de_menu = "";
    private String regla_negocio2 = "";
    private String refid2 = "";
    private String num_productos = "";
    private HashMap<String, ArrayList<String>> productos;
    private String num_consultas_Saldo = "";
    private String contraseña = "";
    private String estado_opt = "";
    private String tipo_otp = "";
    private String tipo_oda = "";
    private String otp_antitramites = "";
    private String otp_servicio = "";
    private String tiene_otp = "";
    private String bDMigracion;
    private String controlDual;
    private String tipoidCliente = "";
    private String chaTelasociado = "";
    private String chaFecharegAF = "";
    private String chaEstadoAF = "";
    private String chattxtUserId = "";
    private String extension = "";
    private String descOpcFinalMenu = "";
    private String tipide = "";
    private String comprobante = "";
    private String tipoClientePrepago = "";
    private String nombre1 = "";
    private String nombre2 = "";
    private String apellido2 = "";
    private String fechaExpedicion = "";
    private String lugarExpedicion = "";
    private String nitEmpresa = "";
    private String nombreEmpresa = "";
    private String nomRealce1 = "";
    private String nomRealce2 = "";
    private String tipoTarjeta = "";
    private String apellido1 = "";
    private String codemp = "";
    private String tokenOauth="";

    public String getTokenOauth() {
        return tokenOauth;
    }

    public void setTokenOauth(String tokenOauth) {
        this.tokenOauth = tokenOauth;
    }
       
   
    public String getCodemp() {
        return codemp;
    }

    public void setCodemp(String codemp) {
        this.codemp = codemp;
    }

    public String getTipoClientePrepago() {
        return tipoClientePrepago;
    }

    public void setTipoClientePrepago(String tipoClientePrepago) {
        this.tipoClientePrepago = tipoClientePrepago;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(String fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNomRealce1() {
        return nomRealce1;
    }

    public void setNomRealce1(String nomRealce1) {
        this.nomRealce1 = nomRealce1;
    }

    public String getNomRealce2() {
        return nomRealce2;
    }

    public void setNomRealce2(String nomRealce2) {
        this.nomRealce2 = nomRealce2;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getTipide() {
        return tipide;
    }

    public void setTipide(String tipide) {
        this.tipide = tipide;
    }

    public String getDescOpcFinalMenu() {
        return descOpcFinalMenu;
    }

    public void setDescOpcFinalMenu(String descOpcFinalMenu) {
        this.descOpcFinalMenu = descOpcFinalMenu;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getChaTelasociado() {
        return chaTelasociado;
    }

    public void setChaTelasociado(String chaTelasociado) {
        this.chaTelasociado = chaTelasociado;
    }

    public String getChaFecharegAF() {
        return chaFecharegAF;
    }

    public void setChaFecharegAF(String chaFecharegAF) {
        this.chaFecharegAF = chaFecharegAF;
    }

    public String getChaEstadoAF() {
        return chaEstadoAF;
    }

    public void setChaEstadoAF(String chaEstadoAF) {
        this.chaEstadoAF = chaEstadoAF;
    }

    public String getChattxtUserId() {
        return chattxtUserId;
    }

    public void setChattxtUserId(String chattxtUserId) {
        this.chattxtUserId = chattxtUserId;
    }

    public String getTipoidCliente() {
        return tipoidCliente;
    }

    public void setTipoidCliente(String tipoidCliente) {
        this.tipoidCliente = tipoidCliente;
    }

    public String getControlDual() {
        return controlDual;
    }

    public void setControlDual(String controlDual) {
        this.controlDual = controlDual;
    }

    public String getbDMigracion() {
        return bDMigracion;
    }

    public void setbDMigracion(String bDMigracion) {
        this.bDMigracion = bDMigracion;
    }

    public String getEstado_opt() {
        return estado_opt;
    }

    public void setEstado_opt(String estado_opt) {
        this.estado_opt = estado_opt;
    }

    public String getTipo_otp() {
        return tipo_otp;
    }

    public void setTipo_otp(String tipo_otp) {
        this.tipo_otp = tipo_otp;
    }

    public String getTipo_oda() {
        return tipo_oda;
    }

    public void setTipo_oda(String tipo_oda) {
        this.tipo_oda = tipo_oda;
    }

    public String getOtp_antitramites() {
        return otp_antitramites;
    }

    public void setOtp_antitramites(String otp_antitramites) {
        this.otp_antitramites = otp_antitramites;
    }

    public String getOtp_servicio() {
        return otp_servicio;
    }

    public void setOtp_servicio(String otp_servicio) {
        this.otp_servicio = otp_servicio;
    }

    public String getTiene_otp() {
        return tiene_otp;
    }

    public void setTiene_otp(String tiene_otp) {
        this.tiene_otp = tiene_otp;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public static void setCliente(Cliente valor) {
        cliente = valor;
    }

    public static Cliente getCliente() {
        return cliente;
    }

    public Cliente() {
    }

    public String getTipo_clinete_gtel() {
        return tipo_clinete_gtel;
    }

    public void setTipo_clinete_gtel(String tipo_clinete_gtel) {
        this.tipo_clinete_gtel = tipo_clinete_gtel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo_cliente() {
        return tipo_cliente;
    }

    public void setTipo_cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }

    public String getRegla_negocio() {
        return regla_negocio;
    }

    public void setRegla_negocio(String regla_negocio) {
        this.regla_negocio = regla_negocio;
    }

    public String getValor_anexo() {
        return valor_anexo;
    }

    public void setValor_anexo(String valor_anexo) {
        this.valor_anexo = valor_anexo;
    }

    public String getCti_origen() {
        return cti_origen;
    }

    public void setCti_origen(String cti_origen) {
        this.cti_origen = cti_origen;
    }

    public String getCodigo_origen() {
        return codigo_origen;
    }

    public void setCodigo_origen(String codigo_origen) {
        this.codigo_origen = codigo_origen;
    }

    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    public String getRefid() {
        return refid;
    }

    public void setRefid(String refid) {
        this.refid = refid;
    }

    public String getIndustria() {
        return industria;
    }

    public void setIndustria(String industria) {
        this.industria = industria;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getCod_apli() {
        return cod_apli;
    }

    public void setCod_apli(String cod_apli) {
        this.cod_apli = cod_apli;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getVv_Tipide() {
        return vv_Tipide;
    }

    public void setVv_Tipide(String vv_Tipide) {
        this.vv_Tipide = vv_Tipide;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getVv_Fechna() {
        return vv_Fechna;
    }

    public void setVv_Fechna(String vv_Fechna) {
        this.vv_Fechna = vv_Fechna;
    }

    public String getVv_Fechv() {
        return vv_Fechv;
    }

    public void setVv_Fechv(String vv_Fechv) {
        this.vv_Fechv = vv_Fechv;
    }

    public String getVv_Fechac() {
        return vv_Fechac;
    }

    public void setVv_Fechac(String vv_Fechac) {
        this.vv_Fechac = vv_Fechac;
    }

    public String getClavesn() {
        return clavesn;
    }

    public void setClavesn(String clavesn) {
        this.clavesn = clavesn;
    }

    public String getReservado1() {
        return reservado1;
    }

    public void setReservado1(String reservado1) {
        this.reservado1 = reservado1;
    }

    public String getReservado2() {
        return reservado2;
    }

    public void setReservado2(String reservado2) {
        this.reservado2 = reservado2;
    }

    public String getOpc_final_de_menu() {
        return opc_final_de_menu;
    }

    public void setOpc_final_de_menu(String opc_final_de_menu) {
        this.opc_final_de_menu = opc_final_de_menu;
    }

    public String getRegla_negocio2() {
        return regla_negocio2;
    }

    public void setRegla_negocio2(String regla_negocio2) {
        this.regla_negocio2 = regla_negocio2;
    }

    public String getRefid2() {
        return refid2;
    }

    public void setRefid2(String refid2) {
        this.refid2 = refid2;
    }

    public String getNum_productos() {
        return num_productos;
    }

    public void setNum_productos(String num_productos) {
        this.num_productos = num_productos;
    }

    public HashMap<String, ArrayList<String>> getProductos() {
        return productos;
    }

    public void setProductos(HashMap<String, ArrayList<String>> productos) {
        this.productos = productos;
    }

    public String getNum_consultas_Saldo() {
        return num_consultas_Saldo;
    }

    public void setNum_consultas_Saldo(String num_consultas_Saldo) {
        this.num_consultas_Saldo = num_consultas_Saldo;
    }
}
