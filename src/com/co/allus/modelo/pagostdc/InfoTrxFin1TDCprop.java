/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.modelo.pagostdc;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alexander.lopez.o
 */
public class InfoTrxFin1TDCprop {

    private SimpleStringProperty tarjeta_pagar;
    private SimpleStringProperty franquicia;
    private SimpleStringProperty valor_pagado;

    public InfoTrxFin1TDCprop(String tarjeta_pagar, String franquicia, String valor_pagado) {
        this.tarjeta_pagar = new SimpleStringProperty(tarjeta_pagar);
        this.franquicia = new SimpleStringProperty(franquicia);
        this.valor_pagado = new SimpleStringProperty(valor_pagado);

    }

    public String getTarjeta_pagar() {
        return tarjeta_pagar.get();
    }

    public void setTarjeta_pagar(String tarjeta_pagar) {
        this.tarjeta_pagar.set(tarjeta_pagar);
    }

    public String getFranquicia() {
        return franquicia.get();
    }

    public void setFranquicia(String franquicia) {
        this.franquicia.set(franquicia);
    }

    public String getValor_pagado() {
        return valor_pagado.get();
    }

    public void setValor_pagado(String valor_pagado) {
        this.valor_pagado.set(valor_pagado);
    }
}
