package com.tofitsolutions.armasdurasargentinas.controllers;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tofitsolutions.armasdurasargentinas.Item;
import com.tofitsolutions.armasdurasargentinas.Maquina;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MaquinaController {
    String host = Util.getHost();
    public List<Maquina> getEstribadoras(){

        String sql = "http://"+host+"/maquina/estribadoras/";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson = new Gson();
        URL url = null;
        HttpURLConnection conn;
        List<Maquina> maquinas = new ArrayList<>();
        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();

            maquinas = gson.fromJson(json, new TypeToken<List<Maquina>>(){}.getType());

            //sal.setText(item.getAcero() + item.getCodigo());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maquinas;
    }


    public List<Maquina> getCortadoras(){

        String sql = "http://" + host + "/maquina/cortadoras/";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson = new Gson();
        URL url = null;
        HttpURLConnection conn;
        List<Maquina> maquinas = new ArrayList<>();
        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();

            maquinas = gson.fromJson(json, new TypeToken<List<Maquina>>(){}.getType());

            //sal.setText(item.getAcero() + item.getCodigo());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maquinas;
    }



    public List<Maquina> getDobladoras(){

        String sql = "http://"+host+"/maquina/dobladoras/";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson = new Gson();
        URL url = null;
        HttpURLConnection conn;
        List<Maquina> maquinas = new ArrayList<>();
        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();

            maquinas = gson.fromJson(json, new TypeToken<List<Maquina>>(){}.getType());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maquinas;
    }


}
