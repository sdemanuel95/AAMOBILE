package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArmadosActivity extends AppCompatActivity {
    Button bt_armados, bt_CancelarDatosUsuario, bt_okDatosUsuario, bt_PrincipalDatosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armados);

        bt_armados = (Button) findViewById(R.id.bt_armados);
        bt_CancelarDatosUsuario = (Button) findViewById(R.id.bt_CancelarDatosUsuario);
        bt_okDatosUsuario = (Button) findViewById(R.id.bt_okDatosUsuario);
        bt_PrincipalDatosUsuario = (Button) findViewById(R.id.bt_PrincipalDatosUsuario);

        bt_armados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ArmadosActivity.this, DatosUsuarioArmaActivity.class);
                finish();
                startActivity(i);
            }
        });
        bt_CancelarDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ArmadosActivity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
        bt_PrincipalDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ArmadosActivity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
        bt_okDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ArmadosActivity.this, DatosUsuarioArmaActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

}
