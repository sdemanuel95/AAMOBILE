package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.squareup.okhttp.OkHttpClient;
import com.tofitsolutions.armasdurasargentinas.Items;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class ItemImpl {

    String API_BASE_URL = "http://" + Util.getHost();

    RestAdapter.Builder builder =
            new RestAdapter.Builder()
                    .setEndpoint(API_BASE_URL)
                    .setClient(
                            new OkClient(new OkHttpClient())
                    );

    RestAdapter adapter = builder.build();

    ItemService client = adapter.create(ItemService.class);

    public boolean  actualizarItem(Items item){
        try{

            client.actualizarItem(item, new Callback<Items>() {
                @Override
                public void success(Items item, Response response) {
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
