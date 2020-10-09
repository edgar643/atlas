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
public class EstadoSgdaClave {

    public static final Map<String, ArrayList<String>> codigosProd = new HashMap<String, ArrayList<String>>();

    public EstadoSgdaClave() {
        codigosProd.clear();
        createMap();
    }

    private static void createMap() {

        addValues("5G", "NOEP");
        addValues("1B", "NOEP");
        addValues("2D", "NOEP");
        addValues("3E", "NOEP");
        addValues("4F", "NOEP");
        addValues("2C", "NOEP");
        addValues("1A", "NOEP");
        addValues("6H", "NOEP");
        addValues("8L", "NOEP");
        addValues("9O", "NOEP");
        addValues("2R", "NOEP");
        addValues("4T", "NOEP");
        addValues("9M", "NOEP");
        addValues("7I", "NOEP");
        addValues("8X", "NOEP");
        addValues("7K", "NOEP");
        addValues("1Q", "NOEP");
        addValues("3S", "NOEP");
        addValues("5U", "NOEP");
        addValues("9N", "NOEP");
        addValues("7J", "NOEP");
        addValues("9Y", "NOEP");
        addValues("B", "NOEP");

        addValues("80H", "NOEP");
        addValues("50E", "NOEP");
        addValues("60F", "NOEP");
        addValues("10A", "NOEP");
        addValues("70G", "NOEP");
        addValues("20B", "NOEP");
        addValues("30C", "NOEP");
        addValues("40D", "NOEP");

        addValues("6G", "BLQA");
        addValues("2B", "BLQA");
        addValues("3D", "BLQA");
        addValues("4E", "BLQA");
        addValues("5F", "BLQA");
        addValues("3C", "BLQA");
        addValues("2A", "BLQA");
        addValues("7H", "BLQA");
        addValues("9L", "BLQA");
        addValues("1P", "BLQA");
        addValues("3R", "BLQA");
        addValues("5T", "BLQA");
        addValues("1N", "BLQA");
        addValues("8I", "BLQA");
        addValues("9X", "BLQA");
        addValues("8K", "BLQA");
        addValues("2Q", "BLQA");
        addValues("4S", "BLQA");
        addValues("6U", "BLQA");
        addValues("1O", "BLQA");
        addValues("8J", "BLQA");
        addValues("1Z", "BLQA");
        addValues("C", "BLQA");

        addValues("81H", "BLQA");
        addValues("51E", "BLQA");
        addValues("61F", "BLQA");
        addValues("11A", "BLQA");
        addValues("71G", "BLQA");
        addValues("21B", "BLQA");
        addValues("31C", "BLQA");
        addValues("41D", "BLQA");




        addValues("7G", "BAUO");
        addValues("3B", "BAUO");
        addValues("4D", "BAUO");
        addValues("5E", "BAUO");
        addValues("6F", "BAUO");
        addValues("4C", "BAUO");
        addValues("3A", "BAUO");
        addValues("8H", "BAUO");
        addValues("1M", "BAUO");
        addValues("2P", "BAUO");
        addValues("4R", "BAUO");
        addValues("6T", "BAUO");
        addValues("2N", "BAUO");
        addValues("9I", "BAUO");
        addValues("1Y", "BAUO");
        addValues("9K", "BAUO");
        addValues("3Q", "BAUO");
        addValues("5S", "BAUO");
        addValues("7U", "BAUO");
        addValues("2O", "BAUO");
        addValues("9J", "BAUO");
        addValues("2Z", "BAUO");
        addValues("D", "BAUO");

        addValues("82H", "BAUO");
        addValues("52E", "BAUO");
        addValues("62F", "BAUO");
        addValues("12A", "BAUO");
        addValues("72G", "BAUO");
        addValues("22B", "BAUO");
        addValues("32C", "BAUO");
        addValues("42D", "BAUO");

        addValues("7G", "BLQO");
        addValues("3B", "BLQO");
        addValues("4D", "BLQO");
        addValues("5E", "BLQO");
        addValues("6F", "BLQO");
        addValues("4C", "BLQO");
        addValues("3A", "BLQO");
        addValues("8H", "BLQO");
        addValues("1M", "BLQO");
        addValues("2P", "BLQO");
        addValues("4R", "BLQO");
        addValues("6T", "BLQO");
        addValues("2N", "BLQO");
        addValues("9I", "BLQO");
        addValues("1Y", "BLQO");
        addValues("9K", "BLQO");
        addValues("3Q", "BLQO");
        addValues("5S", "BLQO");
        addValues("7U", "BLQO");
        addValues("2O", "BLQO");
        addValues("9J", "BLQO");
        addValues("2Z", "BLQO");
        addValues("D", "BLQO");

        addValues("82H", "BLQO");
        addValues("52E", "BLQO");
        addValues("62F", "BLQO");
        addValues("12A", "BLQO");
        addValues("72G", "BLQO");
        addValues("22B", "BLQO");
        addValues("32C", "BLQO");
        addValues("42D", "BLQO");


        addValues("7G", "SOLO");
        addValues("3B", "SOLO");
        addValues("4D", "SOLO");
        addValues("5E", "SOLO");
        addValues("6F", "SOLO");
        addValues("4C", "SOLO");
        addValues("3A", "SOLO");
        addValues("8H", "SOLO");
        addValues("1M", "SOLO");
        addValues("2P", "SOLO");
        addValues("4R", "SOLO");
        addValues("6T", "SOLO");
        addValues("2N", "SOLO");
        addValues("9I", "SOLO");
        addValues("1Y", "SOLO");
        addValues("9K", "SOLO");
        addValues("3Q", "SOLO");
        addValues("5S", "SOLO");
        addValues("7U", "SOLO");
        addValues("2O", "SOLO");
        addValues("9J", "SOLO");
        addValues("2Z", "SOLO");
        addValues("D", "SOLO");

        addValues("82H", "SOLO");
        addValues("52E", "SOLO");
        addValues("62F", "SOLO");
        addValues("12A", "SOLO");
        addValues("72G", "SOLO");
        addValues("22B", "SOLO");
        addValues("32C", "SOLO");
        addValues("42D", "SOLO");

        addValues("7G", "ACTO");
        addValues("3B", "ACTO");
        addValues("4D", "ACTO");
        addValues("5E", "ACTO");
        addValues("6F", "ACTO");
        addValues("4C", "ACTO");
        addValues("3A", "ACTO");
        addValues("8H", "ACTO");
        addValues("1M", "ACTO");
        addValues("2P", "ACTO");
        addValues("4R", "ACTO");
        addValues("6T", "ACTO");
        addValues("2N", "ACTO");
        addValues("9I", "ACTO");
        addValues("1Y", "ACTO");
        addValues("9K", "ACTO");
        addValues("3Q", "ACTO");
        addValues("5S", "ACTO");
        addValues("7U", "ACTO");
        addValues("2O", "ACTO");
        addValues("9J", "ACTO");
        addValues("2Z", "ACTO");
        addValues("D", "ACTO");

        addValues("82H", "ACTO");
        addValues("52E", "ACTO");
        addValues("62F", "ACTO");
        addValues("12A", "ACTO");
        addValues("72G", "ACTO");
        addValues("22B", "ACTO");
        addValues("32C", "ACTO");
        addValues("42D", "ACTO");

        addValues("8G", "BAUA");
        addValues("4B", "BAUA");
        addValues("5D", "BAUA");
        addValues("6E", "BAUA");
        addValues("7F", "BAUA");
        addValues("5C", "BAUA");
        addValues("4A", "BAUA");
        addValues("9H", "BAUA");
        addValues("2M", "BAUA");
        addValues("3P", "BAUA");
        addValues("5R", "BAUA");
        addValues("7T", "BAUA");
        addValues("3N", "BAUA");
        addValues("1J", "BAUA");
        addValues("2Y", "BAUA");
        addValues("1L", "BAUA");
        addValues("4Q", "BAUA");
        addValues("6S", "BAUA");
        addValues("8U", "BAUA");
        addValues("3O", "BAUA");
        addValues("1K", "BAUA");
        addValues("3Z", "BAUA");
        addValues("E", "BAUA");

        addValues("83H", "BAUA");
        addValues("53E", "BAUA");
        addValues("63F", "BAUA");
        addValues("13A", "BAUA");
        addValues("73G", "BAUA");
        addValues("23B", "BAUA");
        addValues("33C", "BAUA");
        addValues("43D", "BAUA");


        addValues("8G", "420");
        addValues("4B", "420");
        addValues("5D", "420");
        addValues("6E", "420");
        addValues("7F", "420");
        addValues("5C", "420");
        addValues("4A", "420");
        addValues("9H", "420");
        addValues("2M", "420");
        addValues("3P", "420");
        addValues("5R", "420");
        addValues("7T", "420");
        addValues("3N", "420");
        addValues("1J", "420");
        addValues("2Y", "420");
        addValues("1L", "420");
        addValues("4Q", "420");
        addValues("6S", "420");
        addValues("8U", "420");
        addValues("3O", "420");
        addValues("1K", "420");
        addValues("3Z", "420");
        addValues("E", "420");

        addValues("83H", "420");
        addValues("53E", "420");
        addValues("63F", "420");
        addValues("13A", "420");
        addValues("73G", "420");
        addValues("23B", "420");
        addValues("33C", "420");
        addValues("43D", "420");

        addValues("8G", "90");
        addValues("4B", "90");
        addValues("5D", "90");
        addValues("6E", "90");
        addValues("7F", "90");
        addValues("5C", "90");
        addValues("4A", "90");
        addValues("9H", "90");
        addValues("2M", "90");
        addValues("3P", "90");
        addValues("5R", "90");
        addValues("7T", "90");
        addValues("3N", "90");
        addValues("1J", "90");
        addValues("2Y", "90");
        addValues("1L", "90");
        addValues("4Q", "90");
        addValues("6S", "90");
        addValues("8U", "90");
        addValues("3O", "90");
        addValues("1K", "90");
        addValues("3Z", "90");
        addValues("E", "90");

        addValues("83H", "90");
        addValues("53E", "90");
        addValues("63F", "90");
        addValues("13A", "90");
        addValues("73G", "90");
        addValues("23B", "90");
        addValues("33C", "90");
        addValues("43D", "90");

        addValues("9G", "SOLP");
        addValues("5B", "SOLP");
        addValues("6D", "SOLP");
        addValues("7E", "SOLP");
        addValues("8F", "SOLP");
        addValues("6C", "SOLP");
        addValues("5A", "SOLP");
        addValues("1I", "SOLP");
        addValues("3M", "SOLP");
        addValues("4P", "SOLP");
        addValues("6R", "SOLP");
        addValues("8T", "SOLP");
        addValues("4N", "SOLP");
        addValues("2J", "SOLP");
        addValues("3Y", "SOLP");
        addValues("2L", "SOLP");
        addValues("5Q", "SOLP");
        addValues("7S", "SOLP");
        addValues("9U", "SOLP");
        addValues("4O", "SOLP");
        addValues("2K", "SOLP");
        addValues("4Z", "SOLP");
        addValues("F", "SOLP");

        addValues("84H", "SOLP");
        addValues("54E", "SOLP");
        addValues("64F", "SOLP");
        addValues("14A", "SOLP");
        addValues("74G", "SOLP");
        addValues("24B", "SOLP");
        addValues("34C", "SOLP");
        addValues("44D", "SOLP");

        addValues("9G", "BLQP");
        addValues("5B", "BLQP");
        addValues("6D", "BLQP");
        addValues("7E", "BLQP");
        addValues("8F", "BLQP");
        addValues("6C", "BLQP");
        addValues("5A", "BLQP");
        addValues("1I", "BLQP");
        addValues("3M", "BLQP");
        addValues("4P", "BLQP");
        addValues("6R", "BLQP");
        addValues("8T", "BLQP");
        addValues("4N", "BLQP");
        addValues("2J", "BLQP");
        addValues("3Y", "BLQP");
        addValues("2L", "BLQP");
        addValues("5Q", "BLQP");
        addValues("7S", "BLQP");
        addValues("9U", "BLQP");
        addValues("4O", "BLQP");
        addValues("2K", "BLQP");
        addValues("4Z", "BLQP");
        addValues("F", "BLQP");

        addValues("84H", "BLQP");
        addValues("54E", "BLQP");
        addValues("64F", "BLQP");
        addValues("14A", "BLQP");
        addValues("74G", "BLQP");
        addValues("24B", "BLQP");
        addValues("34C", "BLQP");
        addValues("44D", "BLQP");

        addValues("9G", "BAUP");
        addValues("5B", "BAUP");
        addValues("6D", "BAUP");
        addValues("7E", "BAUP");
        addValues("8F", "BAUP");
        addValues("6C", "BAUP");
        addValues("5A", "BAUP");
        addValues("1I", "BAUP");
        addValues("3M", "BAUP");
        addValues("4P", "BAUP");
        addValues("6R", "BAUP");
        addValues("8T", "BAUP");
        addValues("4N", "BAUP");
        addValues("2J", "BAUP");
        addValues("3Y", "BAUP");
        addValues("2L", "BAUP");
        addValues("5Q", "BAUP");
        addValues("7S", "BAUP");
        addValues("9U", "BAUP");
        addValues("4O", "BAUP");
        addValues("2K", "BAUP");
        addValues("4Z", "BAUP");
        addValues("F", "BAUP");

        addValues("84H", "BAUP");
        addValues("54E", "BAUP");
        addValues("64F", "BAUP");
        addValues("14A", "BAUP");
        addValues("74G", "BAUP");
        addValues("24B", "BAUP");
        addValues("34C", "BAUP");
        addValues("44D", "BAUP");

        addValues("9G", "ACTP");
        addValues("5B", "ACTP");
        addValues("6D", "ACTP");
        addValues("7E", "ACTP");
        addValues("8F", "ACTP");
        addValues("6C", "ACTP");
        addValues("5A", "ACTP");
        addValues("1I", "ACTP");
        addValues("3M", "ACTP");
        addValues("4P", "ACTP");
        addValues("6R", "ACTP");
        addValues("8T", "ACTP");
        addValues("4N", "ACTP");
        addValues("2J", "ACTP");
        addValues("3Y", "ACTP");
        addValues("2L", "ACTP");
        addValues("5Q", "ACTP");
        addValues("7S", "ACTP");
        addValues("9U", "ACTP");
        addValues("4O", "ACTP");
        addValues("2K", "ACTP");
        addValues("4Z", "ACTP");
        addValues("F", "ACTP");

        addValues("84H", "ACTP");
        addValues("54E", "ACTP");
        addValues("64F", "ACTP");
        addValues("14A", "ACTP");
        addValues("74G", "ACTP");
        addValues("24B", "ACTP");
        addValues("34C", "ACTP");
        addValues("44D", "ACTP");

        addValues("1H", "EXPP");
        addValues("6B", "EXPP");
        addValues("7D", "EXPP");
        addValues("8E", "EXPP");
        addValues("9F", "EXPP");
        addValues("7C", "EXPP");
        addValues("6A", "EXPP");
        addValues("2I", "EXPP");
        addValues("4M", "EXPP");
        addValues("5P", "EXPP");
        addValues("7R", "EXPP");
        addValues("9T", "EXPP");
        addValues("5N", "EXPP");
        addValues("3J", "EXPP");
        addValues("4Y", "EXPP");
        addValues("3L", "EXPP");
        addValues("6Q", "EXPP");
        addValues("8S", "EXPP");
        addValues("1V", "EXPP");
        addValues("5O", "EXPP");
        addValues("3K", "EXPP");
        addValues("5Z", "EXPP");
        addValues("G", "EXPP");

        addValues("85H", "EXPP");
        addValues("55E", "EXPP");
        addValues("65F", "EXPP");
        addValues("15A", "EXPP");
        addValues("75G", "EXPP");
        addValues("25B", "EXPP");
        addValues("35C", "EXPP");
        addValues("45D", "EXPP");

        addValues("2H", "ACTA");
        addValues("7B", "ACTA");
        addValues("8D", "ACTA");
        addValues("9E", "ACTA");
        addValues("1G", "ACTA");
        addValues("8C", "ACTA");
        addValues("7A", "ACTA");
        addValues("3I", "ACTA");
        addValues("5M", "ACTA");
        addValues("6P", "ACTA");
        addValues("8R", "ACTA");
        addValues("1U", "ACTA");
        addValues("6N", "ACTA");
        addValues("4J", "ACTA");
        addValues("5Y", "ACTA");
        addValues("4L", "ACTA");
        addValues("7Q", "ACTA");
        addValues("9S", "ACTA");
        addValues("2V", "ACTA");
        addValues("6O", "ACTA");
        addValues("4K", "ACTA");
        addValues("6Z", "ACTA");
        addValues("H", "ACTA");
        addValues("4H", "ACTA");
        addValues("J", "ACTA");

        addValues("86H", "ACTA");
        addValues("56E", "ACTA");
        addValues("66F", "ACTA");
        addValues("16A", "ACTA");
        addValues("76G", "ACTA");
        addValues("26B", "ACTA");
        addValues("36C", "ACTA");
        addValues("46D", "ACTA");

        addValues("4H", "LogueoAmbas");
        addValues("J", "LogueoAmbas");
        addValues("88H", "LogueoAmbas");
        addValues("58E", "LogueoAmbas");
        addValues("68F", "LogueoAmbas");
        addValues("18A", "LogueoAmbas");
        addValues("78G", "LogueoAmbas");
        addValues("28B", "LogueoAmbas");
        addValues("38C", "LogueoAmbas");
        addValues("48D", "LogueoAmbas");


        addValues("3H", "418");
        addValues("I", "418");

        addValues("87H", "418");
        addValues("57E", "418");
        addValues("67F", "418");
        addValues("178A", "418");
        addValues("77G", "418");
        addValues("27B", "418");
        addValues("37C", "418");
        addValues("47D", "418");

        addValues("3H", "419");
        addValues("I", "419");

        addValues("87H", "419");
        addValues("57E", "419");
        addValues("67F", "419");
        addValues("17A", "419");
        addValues("77G", "419");
        addValues("27B", "419");
        addValues("37C", "419");
        addValues("47D", "419");

        addValues("5H", "SGBS");
        addValues("1C", "SGBS");
        addValues("2E", "SGBS");
        addValues("3F", "SGBS");
        addValues("4G", "SGBS");
        addValues("1D", "SGBS");
        addValues("9A", "SGBS");
        addValues("6I", "SGBS");
        addValues("8M", "SGBS");
        addValues("9P", "SGBS");
        addValues("2S", "SGBS");
        addValues("4U", "SGBS");
        addValues("8N", "SGBS");
        addValues("6J", "SGBS");
        addValues("8Y", "SGBS");
        addValues("7L", "SGBS");
        addValues("1R", "SGBS");
        addValues("3T", "SGBS");
        addValues("5V", "SGBS");
        addValues("8O", "SGBS");
        addValues("6K", "SGBS");
        addValues("9Z", "SGBS");
        addValues("S", "SGBS");

        addValues("89H", "SGBS");
        addValues("59E", "SGBS");
        addValues("69F", "SGBS");
        addValues("19A", "SGBS");
        addValues("79G", "SGBS");
        addValues("29B", "SGBS");
        addValues("39C", "SGBS");
        addValues("49D", "SGBS");

        addValues("L", "failVal2da");


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
