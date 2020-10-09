/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexander.lopez.o
 */
public class ReglasNegocioODA {

    public static final Map<String, ArrayList<String>> codigosProd = new HashMap<String, ArrayList<String>>();

    public ReglasNegocioODA() {
        codigosProd.clear();
        createMap();
    }

    private static void createMap() {


        addValues("1B", "STK");
        addValues("2D", "STK");
        addValues("3E", "STK");
        addValues("4F", "STK");
        addValues("2C", "STK");
        addValues("1A", "STK");
        addValues("8L", "SMS");
        addValues("9O", "SMS");
        addValues("2R", "SMS");
        addValues("4T", "SMS");
        addValues("9M", "SMS");
        addValues("7I", "SMS");
        addValues("7K", "EML");
        addValues("1Q", "EML");
        addValues("3S", "EML");
        addValues("5U", "EML");
        addValues("9N", "EML");
        addValues("7J", "EML");
        addValues("2B", "STK");
        addValues("3D", "STK");
        addValues("4E", "STK");
        addValues("5F", "STK");
        addValues("3C", "STK");
        addValues("2A", "STK");
        addValues("9L", "SMS");
        addValues("1P", "SMS");
        addValues("3R", "SMS");
        addValues("5T", "SMS");
        addValues("1N", "SMS");
        addValues("8I", "SMS");
        addValues("8K", "EML");
        addValues("2Q", "EML");
        addValues("4S", "EML");
        addValues("6U", "EML");
        addValues("1O", "EML");
        addValues("8J", "EML");
        addValues("3B", "STK");
        addValues("4D", "STK");
        addValues("5E", "STK");
        addValues("6F", "STK");
        addValues("4C", "STK");
        addValues("3A", "STK");
        addValues("1M", "SMS");
        addValues("2P", "SMS");
        addValues("4R", "SMS");
        addValues("6T", "SMS");
        addValues("2N", "SMS");
        addValues("9I", "SMS");
        addValues("9K", "EML");
        addValues("3Q", "EML");
        addValues("5S", "EML");
        addValues("7U", "EML");
        addValues("2O", "EML");
        addValues("9J", "EML");
        addValues("4B", "STK");
        addValues("5D", "STK");
        addValues("6E", "STK");
        addValues("7F", "STK");
        addValues("5C", "STK");
        addValues("4A", "STK");
        addValues("2M", "SMS");
        addValues("3P", "SMS");
        addValues("5R", "SMS");
        addValues("7T", "SMS");
        addValues("3N", "SMS");
        addValues("1J", "SMS");
        addValues("1L", "EML");
        addValues("4Q", "EML");
        addValues("6S", "EML");
        addValues("8U", "EML");
        addValues("3O", "EML");
        addValues("1K", "EML");
        addValues("5B", "STK");
        addValues("6D", "STK");
        addValues("7E", "STK");
        addValues("8F", "STK");
        addValues("6C", "STK");
        addValues("5A", "STK");
        addValues("3M", "SMS");
        addValues("4P", "SMS");
        addValues("6R", "SMS");
        addValues("8T", "SMS");
        addValues("4N", "SMS");
        addValues("2J", "SMS");
        addValues("2L", "EML");
        addValues("5Q", "EML");
        addValues("7S", "EML");
        addValues("9U", "EML");
        addValues("4O", "EML");
        addValues("2K", "EML");
        addValues("6B", "STK");
        addValues("7D", "STK");
        addValues("8E", "STK");
        addValues("9F", "STK");
        addValues("7C", "STK");
        addValues("6A", "STK");
        addValues("4M", "SMS");
        addValues("5P", "SMS");
        addValues("7R", "SMS");
        addValues("9T", "SMS");
        addValues("5N", "SMS");
        addValues("3J", "SMS");
        addValues("3L", "EML");
        addValues("6Q", "EML");
        addValues("8S", "EML");
        addValues("1V", "EML");
        addValues("5O", "EML");
        addValues("3K", "EML");
        addValues("7B", "STK");
        addValues("8D", "STK");
        addValues("9E", "STK");
        addValues("1G", "STK");
        addValues("8C", "STK");
        addValues("7A", "STK");
        addValues("5M", "SMS");
        addValues("6P", "SMS");
        addValues("8R", "SMS");
        addValues("1U", "SMS");
        addValues("6N", "SMS");
        addValues("4J", "SMS");
        addValues("4L", "EML");
        addValues("7Q", "EML");
        addValues("9S", "EML");
        addValues("2V", "EML");
        addValues("6O", "EML");
        addValues("4K", "EML");
        addValues("1C", "STK");
        addValues("2E", "STK");
        addValues("3F", "STK");
        addValues("4G", "STK");
        addValues("1D", "STK");
        addValues("8M", "SMS");
        addValues("9P", "SMS");
        addValues("2S", "SMS");
        addValues("4U", "SMS");
        addValues("8N", "SMS");
        addValues("6J", "SMS");
        addValues("7L", "EML");
        addValues("1R", "EML");
        addValues("3T", "EML");
        addValues("5V", "EML");
        addValues("8O", "EML");
        addValues("6K", "EML");



    }

    public Map<String, ArrayList<String>> getCodigosProd() {
        return this.codigosProd;
    }

    private static void addValues(String key, String value) {
        ArrayList tempList = null;
        if (codigosProd.containsKey(key)) {
            tempList = codigosProd.get(key);
            if (tempList == null) {
                tempList = new ArrayList();
            }
            tempList.add(value);
        } else {
            tempList = new ArrayList();
            tempList.add(value);
        }
        codigosProd.put(key, tempList);
    }
}
