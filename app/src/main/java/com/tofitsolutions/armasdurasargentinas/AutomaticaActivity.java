package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AutomaticaActivity extends AppCompatActivity {

    private Button stemma, tjk, pilotera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatica);

        stemma = (Button) findViewById(R.id.buttonStemma);
        tjk = (Button) findViewById(R.id.buttonTjk);
        pilotera = (Button) findViewById(R.id.buttonPilotera);

        Intent intentProduccion = getIntent();
        final String usuario = intentProduccion.getStringExtra("usuario");
        final String ayudante = intentProduccion.getStringExtra("ayudante");

        stemma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AutomaticaActivity.this, StemmaActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                i.putExtra("usuario", usuario);
                i.putExtra("ayudante", ayudante);
                startActivity(i);
            }
        });
        tjk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AutomaticaActivity.this, TjkActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                i.putExtra("usuario", usuario);
                i.putExtra("ayudante", ayudante);
                startActivity(i);
            }
        });
        pilotera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AutomaticaActivity.this, PiloteraActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                i.putExtra("usuario", usuario);
                i.putExtra("ayudante", ayudante);
                startActivity(i);
            }
        });
    }
}
