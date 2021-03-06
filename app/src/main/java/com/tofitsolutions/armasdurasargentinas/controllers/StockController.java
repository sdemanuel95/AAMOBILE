package com.tofitsolutions.armasdurasargentinas.controllers;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tofitsolutions.armasdurasargentinas.Stock;
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

public class StockController {

    String host = Util.getHost();


    public Stock getStock(String desc){
        String sql = "http://"+host+"/stock/" + desc;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        URL url = null;
        HttpURLConnection conn;
        Stock item = null;

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
            item =  gson.fromJson(json, Stock.class);
            System.out.println("blabla");
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


}
