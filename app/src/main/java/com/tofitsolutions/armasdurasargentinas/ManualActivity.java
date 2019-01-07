package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManualActivity extends AppCompatActivity {

    private Button lineaCortado, pasadores, lineaDoblado, armados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        lineaCortado = (Button) findViewById(R.id.buttonLineaCortado);
        pasadores = (Button) findViewById(R.id.buttonPasadores);
        lineaDoblado = (Button) findViewById(R.id.buttonLineaDoblado);
        armados = (Button) findViewById(R.id.buttonArmados);

        Intent intentPrincipal = getIntent();
        final String usuario = intentPrincipal.getStringExtra("usuario");
        final String ayudante = intentPrincipal.getStringExtra("ayudante");

        lineaCortado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManualActivity.this, LineaCortadoActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                i.putExtra("usuario", usuario);
                i.putExtra("ayudante", ayudante);
                startActivity(i);
            }
        });
        pasadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManualActivity.this, PasadoresActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                i.putExtra("usuario", usuario);
                i.putExtra("ayudante", ayudante);
                startActivity(i);
            }
        });
        lineaDoblado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManualActivity.this, LineaDoblado2Activity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                i.putExtra("usuario", usuario);
                i.putExtra("ayudante", ayudante);
                startActivity(i);
            }
        });
        armados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManualActivity.this, ArmadosActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                i.putExtra("usuario", usuario);
                i.putExtra("ayudante", ayudante);
                startActivity(i);
            }
        });
    }
}
