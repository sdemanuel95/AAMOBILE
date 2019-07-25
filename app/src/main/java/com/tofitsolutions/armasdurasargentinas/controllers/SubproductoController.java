package com.tofitsolutions.armasdurasargentinas.controllers;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tofitsolutions.armasdurasargentinas.models.ResumenPedidoItem;
import com.tofitsolutions.armasdurasargentinas.models.Subproducto;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SubproductoController {



    String host = Util.getHost();
    public boolean existe(String codItem){
        String sql = "http://" + host+ "/declaracion/nuevoSubproducto";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson = new Gson();
        URL url = null;
        HttpURLConnection conn;
        Boolean existe = false;

        try {

            url = new URL(sql); // here is your URL path

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("subproducto", new Subproducto("brivo001004",true));


            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","json/application");
            conn.setRequestProperty("Accept","json/application");
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                System.out.println(sb.toString());
                return true;
            }
            else {
                System.out.println(responseCode);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }



    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


    public boolean nuevoSubproducto(String codItem, boolean subproducto){
        String sql = "http://" + host+ "/declaracion/nuevoSubproducto/" + codItem+ "/" + String.valueOf(subproducto);
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
            System.out.println("nuevo subproducto es " + existe + " ... Response es " +response.toString() );
            existe = Boolean.parseBoolean(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existe;
    }



    public boolean isSubproducto(String codItem){
        String sql = "http://" + host+ "/declaracion/isSubproducto/" + codItem;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson = new Gson();
        URL url = null;
        HttpURLConnection conn;
        boolean esSubproducto = false;

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


            esSubproducto = Boolean.parseBoolean(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return esSubproducto;
    }



}

