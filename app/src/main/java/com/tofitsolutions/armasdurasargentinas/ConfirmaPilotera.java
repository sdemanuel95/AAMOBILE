package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ConfirmaPilotera extends AppCompatActivity {

    private TextView usuarioConfEst;
    private TextView AyudanteConfEst;
    private TextView equipoConfEst;
    private TextView preAConfEst;
    private TextView preBConfEst;
    private TextView itemConfEst;
    private TextView cantidadConfEst;
    private Button bt_okEstribadoraConf;
    private Button bt_principalConfEst;
    private Button bt_cancelConfEst;



    private ProgressDialog progress;
    private ArrayList<Declaracion> listaDeclaracion;
    private Declaracion d;



    //Ingresa info del Activity -> EstribadoraActivity
    Intent intentPrecintos = getIntent();
    String codPreA;
    String codPreB;
    String precintoA;
    String precintoB;
    String usuario;
    String ayudante;
    String maquina;
    String item;
    String cantidad;
    String kgTotalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_estribadora);

    }
}
