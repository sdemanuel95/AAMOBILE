package com.tofitsolutions.armasdurasargentinas.controllers;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.tofitsolutions.armasdurasargentinas.models.Formato;
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

public class FormatoController {
    String host = Util.getHost();
    public Formato getFormato(long idFormato){
        String sql = "http://"+host+"/formatos/" + idFormato;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Gson gson = new Gson();
        URL url = null;
        HttpURLConnection conn;
        Formato formato = null;

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

            JSONArray jsonArr = null;
            if(json == null || json.equals("")){
                return null;
            }
            else{
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
                formato =  gson.fromJson(json, Formato.class);

                //sal.setText(item.getAcero() + item.getCodigo());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return formato;
    }

}
