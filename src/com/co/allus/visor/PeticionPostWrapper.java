/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.visor;

import java.util.Map;

/**
 *
 * @author juan.hernandez.ala
 */
public class PeticionPostWrapper {
    private String url;
    private Map<String, String> parametrosMap;
    
    public PeticionPostWrapper(){
        
    }

    public PeticionPostWrapper(String url, Map<String, String> parametrosMap) {
        this.url = url;
        this.parametrosMap = parametrosMap;
    }

    
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParametrosMap() {
        return parametrosMap;
    }

    public void setParametrosMap(Map<String, String> parametrosMap) {
        this.parametrosMap = parametrosMap;
    }
    
}
