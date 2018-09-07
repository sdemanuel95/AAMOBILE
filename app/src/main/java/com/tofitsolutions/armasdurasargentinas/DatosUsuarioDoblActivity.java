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

public class DatosUsuarioDoblActivity extends AppCompatActivity {

    Button bt_okDatosUsuario, bt_principalDatosUsuario, bt_cancelarDatosUsuario;
    EditText et_Usuario, et_Ayudante;
    Spinner spinner_Dobladora;
    String maquinaElegida;
    List<Maquina> maquinas;
    List<String> maquinasString = new ArrayList<>();
    MaquinaController mc = new MaquinaController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        maquinas =  mc.getDobladoras();
        for(Maquina m : maquinas){
            maquinasString.add(m.getMarca() + "-" + m.getModelo());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_usuario_dobl);
        et_Usuario = (EditText)findViewById(R.id.et_Usuario);
        et_Ayudante = (EditText)findViewById(R.id.et_Ayudante);
        spinner_Dobladora = (Spinner)findViewById(R.id.spinnerDobladora);
        bt_okDatosUsuario = (Button)findViewById(R.id.bt_okDatosUsuario);
        bt_principalDatosUsuario = (Button)findViewById(R.id.bt_PrincipalDatosUsuario);
        bt_cancelarDatosUsuario = (Button)findViewById(R.id.bt_CancelarDatosUsuario);

        ArrayAdapter adp = new ArrayAdapter(DatosUsuarioDoblActivity.this, android.R.layout.simple_spinner_dropdown_item,maquinasString);
        spinner_Dobladora.setAdapter(adp);
        spinner_Dobladora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maquinaElegida = (String)spinner_Dobladora.getAdapter().getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        bt_okDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = et_Usuario.getText().toString();
                String ayudante = et_Ayudante.getText().toString();
                //String maquina = et_Maquina.getText().toString();
                if (usuario.length()!=0 || ayudante.length()!=0 ) {
                    Intent i = new Intent(DatosUsuarioDoblActivity.this, LineaDobladoActivity.class);
                    i.putExtra("usuario", usuario);
                    i.putExtra("ayudante", ayudante);
                    Maquina maquina = null;
                    for(Maquina m : maquinas){
                        if(m.existe(maquinaElegida)){
                            maquina = m;
                            break;
                        }
                    }
                    i.putExtra("maquina", maquina.getMarca() + "-" +maquina.getModelo());
                    i.putExtra("diametroMin" , maquina.getdiametroMin());
                    i.putExtra("diametroMax",maquina.getdiametroMax());
                    i.putExtra("merma", maquina.getMerma());
                    i.putExtra("et_invalidos", "valido");
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
                Intent i = new Intent(DatosUsuarioDoblActivity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
        bt_cancelarDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DatosUsuarioDoblActivity.this, LineaDobladoActivity.class);
                finish();
                startActivity(i);
            }
        });
    }


}