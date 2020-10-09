/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.alertasnot;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 *
 * @author alexander.lopez.o
 */
public class ParametrizacionAlertas
{
  private StringProperty colCodigoOp;
  private StringProperty colDescodigoOp;
  private StringProperty colTipoOp;
  private StringProperty colTipoid;
  private StringProperty colNumid;
  private StringProperty colCelular;
  private StringProperty colEmail;
  private StringProperty colTipocta;
  private StringProperty colNumCta;
  private StringProperty colNumOperacion;
  private StringProperty colMontoHabilitado;
  private StringProperty colIndEliminacion;
  private StringProperty colFechaHora;
  private ObservableList<String> column1List;
  private StringProperty colvalidacion=new SimpleStringProperty();


    public ParametrizacionAlertas(String colCodigoOp,
            String colDescodigoOp,
            String colTipoOp, 
            String colTipoid,
            String colNumid, 
            String colCelular, 
            String colEmail, 
            String colTipocta, 
            String colNumCta, 
            String colNumOperacion, 
            String colMontoHabilitado, 
            String colIndEliminacion, 
            String colFechaHora,
            ObservableList<String> column1List            
           ) 
    {
        this.colCodigoOp = new SimpleStringProperty(colCodigoOp);
        this.colDescodigoOp = new SimpleStringProperty(colDescodigoOp);
        this.colTipoOp = new SimpleStringProperty(colTipoOp);
        this.colTipoid = new SimpleStringProperty(colTipoid);
        this.colNumid = new SimpleStringProperty(colNumid);
        this.colCelular = new SimpleStringProperty(colCelular);
        this.colEmail = new SimpleStringProperty(colEmail);
        this.colTipocta = new SimpleStringProperty(colTipocta);
        this.colNumCta = new SimpleStringProperty(colNumCta);
        this.colNumOperacion = new SimpleStringProperty(colNumOperacion);
        this.colMontoHabilitado = new SimpleStringProperty(colMontoHabilitado);
        this.colIndEliminacion = new SimpleStringProperty(colIndEliminacion);
        this.colFechaHora = new SimpleStringProperty(colFechaHora);
        this.column1List=column1List;
        this.colvalidacion.bind(this.colIndEliminacion.concat(":").concat(this.colMontoHabilitado).concat(":").concat(this.colNumOperacion));
        
    }

    public StringProperty colvalidacionProperty() {
        return colvalidacion;
    }

    public String getColvalidacion(String validacion) {
        return this.colvalidacion.get();
    }        
    
      public ObservableList<String> getColumn1List() {
        return column1List;
    }

    public void setColumn1List(ObservableList<String> column1List) {
        this.column1List = column1List;
    }
    

    public StringProperty colCodigoOpProperty() {
        return colCodigoOp;
    }

    public void setColCodigoOp(String colCodigoOp) {
        this.colCodigoOp.set(colCodigoOp);
    }

    public StringProperty colDescodigoOpProperty() {
        return colDescodigoOp;
    }

    public void setColDescodigoOp(String colDescodigoOp) {
        this.colDescodigoOp.set(colDescodigoOp);
    }

    public StringProperty colTipoOpProperty() {
        return colTipoOp;
    }

    public void setColTipoOp(String colTipoOp) {
        this.colTipoOp.set(colTipoOp);
    }

    public StringProperty colTipoidProperty() {
        return colTipoid;
    }

    public void setColTipoid(String colTipoid) {
        this.colTipoid.set(colTipoid);
    }

    public StringProperty colNumidProperty() {
        return colNumid;
    }

    public void setColNumid(String colNumid) {
        this.colNumid.set(colNumid);
    }

    public StringProperty colCelularProperty() {
        return colCelular;
    }

    public void setColCelular(String colCelular) {
        this.colCelular.set(colCelular);
    }

    public StringProperty colEmailProperty() {
        return colEmail;
    }

    public void setColEmail(String colEmail) {
        this.colEmail.set(colEmail);
    }

    public StringProperty colTipoctaProperty() {
        return colTipocta;
    }

    public void setColTipocta(String colTipocta) {
        this.colTipocta.set(colTipocta);
    }

    public StringProperty colNumCtaProperty() {
        return colNumCta;
    }

    public void setColNumCta(String colNumCta) {
        this.colNumCta.set(colNumCta);
    }

    public StringProperty colNumOperacionProperty() {
        return colNumOperacion;
    }

    public void setColNumOperacion(String colNumOperacion) {
        this.colNumOperacion.set(colNumOperacion);
    }

    public StringProperty colMontoHabilitadoProperty() {
        return colMontoHabilitado;
    }

    public void setColMontoHabilitado(String colMontoHabilitado) {
        this.colMontoHabilitado.set(colMontoHabilitado);
    }

    public StringProperty colIndEliminacionProperty() {
        return colIndEliminacion;
    }

    public void setColIndEliminacion(String colIndEliminacion) {
        this.colIndEliminacion.set(colIndEliminacion);
    }

    public StringProperty colFechaHoraProperty() {
        return colFechaHora;
    }

    public void setColFechaHora(String colFechaHora) {
        this.colFechaHora.set(colFechaHora);
    }

  

   
    
    
  
  
  
  
  
}
