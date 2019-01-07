package com.tofitsolutions.armasdurasargentinas.controllers;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tofitsolutions.armasdurasargentinas.Maquina;
import com.tofitsolutions.armasdurasargentinas.Operario;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OperariosController {
    String host = Util.getHost();
    public List<Operario> getOperarios(){

        String sql = "http://"+host+"/operarios/listado/";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson = new Gson();
        URL url = null;
        HttpURLConnection conn;
        List<Operario> operarios = new ArrayList<>();
        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();

            operarios  = gson.fromJson(json, new TypeToken<List<Operario>>(){}.getType());

            //sal.setText(item.getAcero() + item.getCodigo());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return operarios;
    }



}
