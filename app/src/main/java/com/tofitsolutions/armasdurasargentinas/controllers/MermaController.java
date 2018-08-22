package com.tofitsolutions.armasdurasargentinas.controllers;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MermaController {
    String host = Util.getHost();
    public boolean existe(String codItem){
        String sql = "http://" + host+ "/merma/" + codItem;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson = new Gson();
        URL url = null;
        HttpURLConnection conn;
        boolean existe = false;

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
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }


            existe = Boolean.parseBoolean(response.toString());
            System.out.println("Existe es " + existe + " ... Response es " +response.toString() );
            existe = Boolean.parseBoolean(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existe;
    }
}
