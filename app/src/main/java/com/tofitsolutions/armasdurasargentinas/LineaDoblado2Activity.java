package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LineaDoblado2Activity extends AppCompatActivity {
    Button bt_datosUsuario,bt_ok,bt_cancel,bt_principal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_doblado2);
        bt_datosUsuario = (Button) findViewById(R.id.bt_datosUsuario);
        bt_ok = (Button) findViewById(R.id.bt_ok);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_principal = (Button) findViewById(R.id.bt_principal);



        //Redirecciona a DatosUsuario
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaDoblado2Activity.this, ConfirmaLineaDoblado.class);
                finish();
                startActivity(i);
            }
        });

        //Redirecciona a DatosUsuario
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaDoblado2Activity.this, LineaDobladoActivity.class);
                finish();
                startActivity(i);
            }
        });

        //Redirecciona a DatosUsuario
        bt_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaDoblado2Activity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });


        // PRUEBA
        TextView usuarioTV = (TextView) findViewById(R.id.et_precintoB);
        TextView ayudanteTV = (TextView) findViewById(R.id.contraseña);

        Intent intentProduccion = getIntent();
        final String usuario = intentProduccion.getStringExtra("usuario");
        final String ayudante = intentProduccion.getStringExtra("ayudante");

        // usuarioTV.setText(usuario);
        //ayudanteTV.setText(ayudante);

    }
}
