package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.squareup.okhttp.OkHttpClient;
import com.tofitsolutions.armasdurasargentinas.Merma;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class MermaImpl {

    String API_BASE_URL = "http://" + Util.getHost();

    RestAdapter.Builder builder =
            new RestAdapter.Builder()
                    .setEndpoint(API_BASE_URL)
                    .setClient(
                            new OkClient(new OkHttpClient())
                    );

    RestAdapter adapter = builder.build();

    MermaService client = adapter.create(MermaService.class);

    public boolean  crearMerma(Merma merma){
        try{

            client.crearMerma(merma, new Callback<Merma>() {
                @Override
                public void success(Merma merma, Response response) {
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println(error.getBody());
                }
            });
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
