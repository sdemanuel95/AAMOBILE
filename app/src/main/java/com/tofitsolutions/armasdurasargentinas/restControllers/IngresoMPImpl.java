package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.squareup.okhttp.OkHttpClient;
import com.tofitsolutions.armasdurasargentinas.Declaracion;
import com.tofitsolutions.armasdurasargentinas.IngresoMP;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class IngresoMPImpl {
    static boolean ocurrioError = false;
    String API_BASE_URL = "http://" + Util.getHost();

    RestAdapter.Builder builder =
            new RestAdapter.Builder()
                    .setEndpoint(API_BASE_URL)
                    .setClient(
                            new OkClient(new OkHttpClient())
                    );

    RestAdapter adapter = builder.build();

    IngresoMPService client = adapter.create(IngresoMPService.class);

    public boolean  actualizarIngresoMP(IngresoMP ingresoMP){
        try{
            ocurrioError = false;
            client.actualizarIngresoMP(ingresoMP, new Callback<IngresoMP>() {


                @Override
                public void success(IngresoMP declaracion, Response response) {
                    System.out.println("FUNCIONA O QUE ??");
                    ocurrioError = false;
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println("ERROOOOOOOOOOOOOR");
                    System.out.println(error.getBody());
                    ocurrioError = true;
                }

            });

            if(ocurrioError){
                System.out.println("Error, se intenta un llamado recursivo para reintentar");
                return actualizarIngresoMP(ingresoMP);
            }
            else{
                return true;
            }
        }catch(Exception e){
            return false;
        }
    }

}
