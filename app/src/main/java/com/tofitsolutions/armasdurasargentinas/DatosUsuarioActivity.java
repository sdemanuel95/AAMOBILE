package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.tofitsolutions.armasdurasargentinas.controllers.MaquinaController;
import com.tofitsolutions.armasdurasargentinas.controllers.OperariosController;

import java.util.ArrayList;
import java.util.List;

public class DatosUsuarioActivity extends AppCompatActivity {

    Button bt_okDatosUsuario, bt_principalDatosUsuario, bt_cancelarDatosUsuario;
    Spinner spinner_Estribadora,spinner_Ayudante,spinner_Usuario;
    String maquinaElegida;
    String usuarioElegido;
    String ayudanteElegido;
    List<Maquina> maquinas;
    Maquina maquina;
    List<Operario> operarios;
    List<String> maquinasString = new ArrayList<>();
    List<String> usuariosString =new ArrayList<>();
    List<String> usuariosStringTemp =new ArrayList<>();
    MaquinaController mc = new MaquinaController();
    OperariosController oc = new OperariosController();
    Operario ayudante = null;
    Operario usuario = null;
                            @Override
                            protected void onCreate(Bundle savedInstanceState) {

                                //MAQUINAS
                                maquinas =  mc.getEstribadoras();

                                for(Maquina m : maquinas){
                                    maquinasString.add(m.getMarca() + "-" + m.getModelo());
                                }

                                //OPERARIOS
                                operarios = oc.getOperarios();
                                for(Operario o : operarios){
                                    usuariosString.add(o.getNombre() + " " + o.getApellido());
                                }

                                usuariosString.add("");
                                usuariosStringTemp = usuariosString;

                                super.onCreate(savedInstanceState);
                                setContentView(R.layout.activity_datos_usuario_estr);
                                spinner_Usuario = (Spinner)findViewById(R.id.SpinnerUsuario);
                                spinner_Ayudante = (Spinner)findViewById(R.id.SpinnerAyudante);
                                spinner_Estribadora = (Spinner)findViewById(R.id.SpinnerEstribadora);
                                bt_okDatosUsuario = (Button)findViewById(R.id.bt_okDatosUsuario);
                                bt_principalDatosUsuario = (Button)findViewById(R.id.bt_PrincipalDatosUsuario);
                                bt_cancelarDatosUsuario = (Button)findViewById(R.id.bt_CancelarDatosUsuario);

                                ArrayAdapter adp = new ArrayAdapter(DatosUsuarioActivity.this, android.R.layout.simple_spinner_dropdown_item,maquinasString);
                                spinner_Estribadora.setAdapter(adp);

                                ArrayAdapter adpUsuario = new ArrayAdapter(DatosUsuarioActivity.this, android.R.layout.simple_spinner_dropdown_item,usuariosStringTemp);
                                spinner_Usuario.setAdapter(adpUsuario);
                                spinner_Ayudante.setAdapter(adpUsuario);


                                spinner_Estribadora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        maquinaElegida = (String)spinner_Estribadora.getAdapter().getItem(position);

                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });

                                spinner_Usuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        usuarioElegido = (String)spinner_Usuario.getAdapter().getItem(position);

                                        usuariosStringTemp = getListaOperarios(usuariosString,usuarioElegido);
                                        ArrayAdapter adpUsuario = new ArrayAdapter(DatosUsuarioActivity.this, android.R.layout.simple_spinner_dropdown_item,usuariosStringTemp);
                                        spinner_Ayudante.setAdapter(adpUsuario);
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }

                                });


                                spinner_Ayudante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        ayudanteElegido = (String)spinner_Ayudante.getAdapter().getItem(position);


                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });




                                bt_okDatosUsuario.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        //String maquina = et_Maquina.getText().toString();
                                        if (usuarioElegido != "" || ayudanteElegido != "" ) {
                                            Intent i = new Intent(DatosUsuarioActivity.this, EstribadoraActivity.class);
                                            i.putExtra("usuario", usuarioElegido);
                                            i.putExtra("ayudante", ayudanteElegido);
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


    public List<String> getListaOperarios(List<String> listaOperarios, String operarioSeleccionado){

                                List<String> listaReal = new ArrayList<String>();
                                for(String s : listaOperarios){
                                    if(!s.equals(operarioSeleccionado)){
                                        listaReal.add(s);
                                    }
                                }

                                return listaReal;
    }

}