package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.tofitsolutions.armasdurasargentinas.Declaracion;
import com.tofitsolutions.armasdurasargentinas.UsuarioAndroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface UsuariosAndroidService {


    @GET("/usuariosandroid/getById/{id}")
    Call<List<UsuarioAndroid>> getPost(@Path("id") String id);

    @GET("/usuariosandroid/getByUserAndPass/{user}/{pass}")
    Call<List<UsuarioAndroid>> getByUserAndPass(@Path("user") String user, @Path("pass") String pass);





}
