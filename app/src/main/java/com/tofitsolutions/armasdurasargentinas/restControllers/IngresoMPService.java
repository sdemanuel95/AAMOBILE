package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.tofitsolutions.armasdurasargentinas.Declaracion;
import com.tofitsolutions.armasdurasargentinas.IngresoMP;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface IngresoMPService {
    @POST("/ingresomp/actualizar")
    void actualizarIngresoMP(@Body IngresoMP ingresoMP, Callback<IngresoMP> cb);

}
