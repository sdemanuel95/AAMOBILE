package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProduccionActivity extends AppCompatActivity {

    Button bt_estribadora, bt_lineaCortado, bt_lineaDoblado,bt_pilotera,bt_armados,bt_pasadores, bt_principal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion);

        Intent intentPrincipal = getIntent();
        final String usuario = intentPrincipal.getStringExtra("usuario");
        final String ayudante = intentPrincipal.getStringExtra("ayudante");

        bt_estribadora = (Button)findViewById(R.id.bt_estribadora);
        bt_principal = (Button)findViewById(R.id.bt_principal);
        bt_lineaCortado = (Button) findViewById(R.id.bt_lineaCortado);
        bt_lineaDoblado = (Button) findViewById(R.id.bt_lineaDoblado);
        bt_pilotera = (Button) findViewById(R.id.bt_pilotera);
        bt_armados = (Button) findViewById(R.id.bt_armados);
        bt_pasadores = (Button) findViewById(R.id.bt_pasadores);

        bt_estribadora.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProduccionActivity.this, EstribadoraActivity.class);
                finish();
                startActivity(i);
            }
        });

        bt_lineaCortado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProduccionActivity.this, LineaCortadoActivity.class);
                finish();
                startActivity(i);
            }
        });

        bt_lineaDoblado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProduccionActivity.this, LineaDoblado2Activity.class);
                finish();
                startActivity(i);
            }
        });


        bt_armados.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProduccionActivity.this, ArmadosActivity.class);
                finish();
                startActivity(i);
            }
        });

        bt_pasadores.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProduccionActivity.this, PasadoresActivity.class);
                finish();
                startActivity(i);
            }
        });
        bt_pilotera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProduccionActivity.this, PiloteraActivity.class);
                finish();
                startActivity(i);
            }
        });

        bt_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProduccionActivity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
    }
}
