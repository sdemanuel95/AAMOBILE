package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.tofitsolutions.armasdurasargentinas.Items;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface ItemService {
    @POST("/items/actualizar")
    void actualizarItem(@Body Items item, Callback<Items> cb);

}
