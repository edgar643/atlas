/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.alertasnot;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author roberto.ceballos
 */
public class ParametrizacionTopes {
    private StringProperty colCanal;
    private StringProperty colCodigoOp;
    private StringProperty colDescodOp;
    private StringProperty colNummaxOp;
    private StringProperty colMontoMax;
    private StringProperty colIndCtainscB;
    private StringProperty colTipoid;
    private StringProperty colId;
    private StringProperty colNumOp;
    private StringProperty colMontoOp;
    private StringProperty colIndCtainsc;
    private StringProperty colFecha;
    private StringProperty colHora;
   

    public ParametrizacionTopes(String colCanal,
            String colCodigoOp,
            String colDescodOp,
            String colNummaxOp,
            String colMontoMax, 
            String colIndCtainscB,
            String colTipoid,
            String colId,
            String colNumOp,
            String colMontoOp,
            String colIndCtainsc,
            String colFecha,
            String colHora
           ) {
        this.colCanal=new SimpleStringProperty(colCanal);
        this.colCodigoOp = new SimpleStringProperty(colCodigoOp);
        this.colDescodOp = new SimpleStringProperty(colDescodOp);
        this.colNummaxOp = new SimpleStringProperty(colNummaxOp);
        this.colMontoMax = new SimpleStringProperty(colMontoMax);
        this.colIndCtainscB = new SimpleStringProperty(colIndCtainscB);
        this.colTipoid = new SimpleStringProperty(colTipoid);
        this.colId = new SimpleStringProperty(colId);
        this.colNumOp = new SimpleStringProperty(colNumOp);
        this.colMontoOp = new SimpleStringProperty(colMontoOp);
        this.colIndCtainsc = new SimpleStringProperty(colIndCtainsc);
        this.colFecha = new SimpleStringProperty(colFecha);
        this.colHora = new SimpleStringProperty(colHora);
        
    }

    public String getColCanal() {
        return colCanal.get();
    }

    public void setColCanal(String colCanal) {
        this.colCanal.setValue(colCanal);
    }        

    public String getColCodigoOp() {
        return colCodigoOp.get();
    }

    public void setColCodigoOp(String colCodigoOp) {
        this.colCodigoOp.set(colCodigoOp);
    }

    public String getColDescodOp() {
        return colDescodOp.get();
    }

    public void setColDescodOp(String colDescodOp) {
        this.colDescodOp.set(colDescodOp);
    }

    public String getColNummaxOp() {
        return colNummaxOp.get();
    }

    public void setColNummaxOp(String colNummaxOp) {
        this.colNummaxOp.set(colNummaxOp);
    }

    public String getColMontoMax() {
        return colMontoMax.get();
    }

    public void setColMontoMax(String colMontoMax) {
        this.colMontoMax.set(colMontoMax);
    }

    public String getColIndCtainscB() {
        return colIndCtainscB.get();
    }

    public void setColIndCtainscB(String colIndCtainscB) {
        this.colIndCtainscB.set(colIndCtainscB);
    }

    public String getColTipoid() {
        return colTipoid.get();
    }

    public void setColTipoid(String colTipoid) {
        this.colTipoid.set(colTipoid);
    }

    public String getColId() {
        return colId.get();
    }

    public void setColId(String colId) {
        this.colId.set(colId);
    }

    public String getColNumOp() {
        return colNumOp.get();
    }

    public void setColNumOp(String colNumOp) {
        this.colNumOp.set(colNumOp);
    }

    public String getColMontoOp() {
        return colMontoOp.get();
    }

    public void setColMontoOp(String colMontoOp) {
        this.colMontoOp.set(colMontoOp);
    }

    public String getColIndCtainsc() {
        return colIndCtainsc.get();
    }

    public void setColIndCtainsc(String colIndCtainsc) {
        this.colIndCtainsc.set(colIndCtainsc);
    }

    public String getColFecha() {
        return colFecha.get();
    }

    public void setColFecha(String colFecha) {
        this.colFecha.set(colFecha);
    }

    public String getColHora() {
        return colHora.get();
    }

    public void setColHora(String colHora) {
        this.colHora.set(colHora);
    }
    
    
    

   





}
