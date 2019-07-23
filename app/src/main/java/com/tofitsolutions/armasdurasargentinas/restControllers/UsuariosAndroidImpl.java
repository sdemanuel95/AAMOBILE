package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.squareup.okhttp.OkHttpClient;
import com.tofitsolutions.armasdurasargentinas.Declaracion;
import com.tofitsolutions.armasdurasargentinas.UsuarioAndroid;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import java.util.List;


import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuariosAndroidImpl {


    static UsuarioAndroid usuarioAndroid = null;


    public UsuarioAndroid getUsuarioAndroidById(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.15:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsuariosAndroidService postService = retrofit.create(UsuariosAndroidService.class);
        Call<List<UsuarioAndroid>> call = postService.getPost(id);

        call.enqueue(new Callback<List<UsuarioAndroid>>() {

            @Override
            public void onResponse(Call<List<UsuarioAndroid>> call, Response<List<UsuarioAndroid>> response) {

                System.out.println("Error en llamado post 1");

                for(UsuarioAndroid post : response.body()) {
                    usuarioAndroid =  post;
                }
                //arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<UsuarioAndroid>> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Error en llamado post 3");
            }
        });

        return usuarioAndroid;
    }



    public UsuarioAndroid getUsuarioAndroidByUserAndPass(String user,String pass)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.15:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsuariosAndroidService postService = retrofit.create(UsuariosAndroidService.class);
        Call<List<UsuarioAndroid>> call = postService.getByUserAndPass(user,pass);

        call.enqueue(new Callback<List<UsuarioAndroid>>() {

            @Override
            public void onResponse(Call<List<UsuarioAndroid>> call, Response<List<UsuarioAndroid>> response) {

                System.out.println("Error en llamado post 1");

                for(UsuarioAndroid post : response.body()) {
                    usuarioAndroid =  post;
                }

                //arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<UsuarioAndroid>> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Error en llamado post 3");
            }
        });

        return usuarioAndroid;
    }




}
