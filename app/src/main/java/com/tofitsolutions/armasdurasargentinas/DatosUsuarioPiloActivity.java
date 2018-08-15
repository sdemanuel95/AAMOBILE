package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tofitsolutions.armasdurasargentinas.controllers.MaquinaController;

import java.util.ArrayList;
import java.util.List;

public class DatosUsuarioPiloActivity extends AppCompatActivity {

    Button bt_okDatosUsuario, bt_principalDatosUsuario, bt_cancelarDatosUsuario;
    EditText et_Usuario, et_Ayudante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_usuario_pilo);
        et_Usuario = (EditText)findViewById(R.id.et_Usuario);
        et_Ayudante = (EditText)findViewById(R.id.et_Ayudante);
        bt_okDatosUsuario = (Button)findViewById(R.id.bt_okDatosUsuario);
        bt_principalDatosUsuario = (Button)findViewById(R.id.bt_PrincipalDatosUsuario);
        bt_cancelarDatosUsuario = (Button)findViewById(R.id.bt_CancelarDatosUsuario);


        bt_okDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = et_Usuario.getText().toString();
                String ayudante = et_Ayudante.getText().toString();
                //String maquina = et_Maquina.getText().toString();
                if (usuario.length()!=0 || ayudante.length()!=0 ) {
                    Intent i = new Intent(DatosUsuarioPiloActivity.this, PiloteraActivity.class);
                    i.putExtra("usuario", usuario);
                    i.putExtra("ayudante", ayudante);

                    finish();
                    startActivity(i);
                }
                else {
                    String mensaje = "Debe completar los campos para continuar.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }
            }
        });
        bt_principalDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DatosUsuarioPiloActivity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
        bt_cancelarDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DatosUsuarioPiloActivity.this, PiloteraActivity.class);
                finish();
                startActivity(i);
            }
        });
    }


}