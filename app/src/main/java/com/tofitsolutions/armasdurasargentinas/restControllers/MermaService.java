package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.tofitsolutions.armasdurasargentinas.Merma;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface MermaService {
    @POST("/merma/crear")
    void crearMerma(@Body Merma merma, Callback<Merma> cb);

}
