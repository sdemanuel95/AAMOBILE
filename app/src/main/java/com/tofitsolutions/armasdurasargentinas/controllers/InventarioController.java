package com.tofitsolutions.armasdurasargentinas.controllers;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InventarioController {

    String host = Util.getHost();
    public boolean validarAjuste(){
        String sql = "http://"+host+"/inventario/validarAjuste";
        System.out.println(sql);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conn;
        boolean validado = false;
        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";
            response.toString();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }


            System.out.println("validado es " + validado + " ... Response es " + response.toString());
            validado = Boolean.parseBoolean(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return validado;
    }





}
