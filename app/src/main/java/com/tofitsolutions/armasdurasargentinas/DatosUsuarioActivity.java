package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DatosUsuarioActivity extends AppCompatActivity {

    Button bt_okDatosUsuario, bt_principalDatosUsuario, bt_cancelarDatosUsuario;
    EditText et_Usuario, et_Ayudante;
    Spinner spinner_Estribadora;
    String maquinaElegida;
    List<Maquina> maquinas;
    List<String> maquinasString = new ArrayList<>();
    MaquinaController mc = new MaquinaController();
    Maquina maquina = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        maquinas =  mc.getEstribadoras();
        for(Maquina m : maquinas){


            maquinasString.add(m.getMarca() + "-" + m.getModelo());
            maquinaElegida = m.getMarca() + "-" + m.getModelo();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_usuario_estr);
        et_Usuario = (EditText)findViewById(R.id.et_Usuario);
        et_Ayudante = (EditText)findViewById(R.id.et_Ayudante);
        spinner_Estribadora = (Spinner)findViewById(R.id.SpinnerEstribadora);
        bt_okDatosUsuario = (Button)findViewById(R.id.bt_okDatosUsuario);
        bt_principalDatosUsuario = (Button)findViewById(R.id.bt_PrincipalDatosUsuario);
        bt_cancelarDatosUsuario = (Button)findViewById(R.id.bt_CancelarDatosUsuario);

        ArrayAdapter adp = new ArrayAdapter(DatosUsuarioActivity.this, android.R.layout.simple_spinner_dropdown_item,maquinasString);
        spinner_Estribadora.setAdapter(adp);
        spinner_Estribadora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maquinaElegida = (String)spinner_Estribadora.getAdapter().getItem(position);
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
                    Intent i = new Intent(DatosUsuarioActivity.this, EstribadoraActivity.class);
                    i.putExtra("usuario", usuario);
                    i.putExtra("ayudante", ayudante);
                    for(Maquina m : maquinas){
                        System.out.println("MAQUINAS = " + m.getdiametroMax());
                        if(maquinaElegida.equals(m.getMarca() + "-" + m.getModelo())){
                            maquina = m ;
                        }

                    }
                    //i.putExtra("maquina", maquina.getMarca() + "-" +maquina.getModelo());
                    i.putExtra("diametroMin" , maquina.getdiametroMin());
                    System.out.println(maquina.getdiametroMax());
                    System.out.println(maquina.getdiametroMin());
                    i.putExtra("diametroMax",maquina.getdiametroMax());
                    i.putExtra("merma", maquina.getMerma());
                    i.putExtra("et_invalidos", "valido");
                    i.putExtra("maquina",maquina);
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
                Intent i = new Intent(DatosUsuarioActivity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
        bt_cancelarDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DatosUsuarioActivity.this, EstribadoraActivity.class);
                finish();
                startActivity(i);
            }
        });
    }


}