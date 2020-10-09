/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.informaciontdc;

/**
 *
 * @author stephania.rojas
 */
public class dataTablaTdc {

    public static dataTablaTdc dataTablaTdc = new dataTablaTdc();
    private String num_tdc = "";

    public static dataTablaTdc getDataTablaTdc() {
        return dataTablaTdc;
    }

    public static void setDataTablaTdc(dataTablaTdc dataTablaTdc) {
        dataTablaTdc.dataTablaTdc = dataTablaTdc;
    }

    public String getNum_tdc() {
        return num_tdc;
    }

    public void setNum_tdc(String num_tdc) {
        this.num_tdc = num_tdc;
    }
}
