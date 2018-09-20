package com.tofitsolutions.armasdurasargentinas.controllers;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tofitsolutions.armasdurasargentinas.IngresoMP;
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

public class IngresoMPController {

    String host = Util.getHost();
    public boolean esRollo(String codigoDeBarra) {
        String sql = "http://"+host+"/ingresomp/esRollo/" + codigoDeBarra;
            System.out.println(sql);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        URL url = null;
        HttpURLConnection conn;
        boolean esRollo = true;
        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";
            response.toString();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }


            System.out.println("Existe es " + esRollo + " ... Response es " + response.toString());
            esRollo = Boolean.parseBoolean(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return esRollo;
    }



    public boolean esBarra(String codigoDeBarra) {
        String sql = "http://"+host+"/ingresomp/esBarra/" + codigoDeBarra;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        URL url = null;
        HttpURLConnection conn;
        boolean esBarra = false;
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


            System.out.println("Existe es " + esBarra + " ... Response es " + response.toString());
            esBarra = Boolean.parseBoolean(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return esBarra;
    }

    public IngresoMP getMP(String codBarra){
        String sql = "http://"+host+"/ingresomp/buscar/" + codBarra;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        URL url = null;
        HttpURLConnection conn;
        IngresoMP item = null;

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

            JSONArray jsonArr = null;
            JSONObject objecto = new JSONObject(json);
            /*
            jsonArr = new JSONArray(json);
            String mensaje = "";
            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                Log.d("SLIDA",jsonObject.optString("description"));
                mensaje += "DESCRIPCION "+i+" 11"+jsonObject.optString("description")+"\n";

            }
            */

            String mensaje = objecto.toString();
            item =  gson.fromJson(json, IngresoMP.class);

            //sal.setText(item.getAcero() + item.getCodigo());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return item;
    }

    public void updatekg(String codigoDeBarra) {
        String sql = "http://"+host+"/ingresomp/updatekg/" + codigoDeBarra;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        URL url = null;
        HttpURLConnection conn;
        boolean esBarra = false;
        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            System.out.println("Existe es " + esBarra + " ... Response es " + response.toString());
            //esBarra = Boolean.parseBoolean(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean insertStockInicial(String codigoDeBarra) {
        String sql = "http://"+host+"/ingresomp/stockinicial/insertar/" + codigoDeBarra;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        URL url = null;
        HttpURLConnection conn;
        boolean resultado = false;
        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            System.out.println("Response es " + response.toString());
            resultado = Boolean.parseBoolean(response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            resultado = false;
        } catch (IOException e) {
            e.printStackTrace();
            resultado = false;
        }
        return resultado;
    }

}
