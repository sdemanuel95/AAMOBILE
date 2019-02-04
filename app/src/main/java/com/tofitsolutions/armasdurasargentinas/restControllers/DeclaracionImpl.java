package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.squareup.okhttp.OkHttpClient;
import com.tofitsolutions.armasdurasargentinas.Declaracion;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class DeclaracionImpl {

    String API_BASE_URL = "http://" + Util.getHost();
    static boolean isValid;
    RestAdapter.Builder builder =
            new RestAdapter.Builder()
                    .setEndpoint(API_BASE_URL)
                    .setClient(
                            new OkClient(new OkHttpClient())
                    );

    RestAdapter adapter = builder.build();

    DeclaracionService client = adapter.create(DeclaracionService.class);

    public boolean  crearDeclaracion(Declaracion d){
        try{

            client.crearDeclaracion(d, new Callback<Declaracion>() {
                @Override
                public void success(Declaracion declaracion, Response response) {
                    isValid = true;
                    System.out.println("FUNCIONA O QUE ??");
                }

                @Override
                public void failure(RetrofitError error) {
                    isValid =  false;
                    System.out.println(error.getBody());
                }
            });
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
