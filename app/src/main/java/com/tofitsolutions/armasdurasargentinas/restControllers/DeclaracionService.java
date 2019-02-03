package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.tofitsolutions.armasdurasargentinas.Declaracion;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface DeclaracionService {
    @POST("/declaracion/crear")
    void crearDeclaracion(@Body Declaracion declaracion, Callback<Declaracion> cb);

}
