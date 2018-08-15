package com.tofitsolutions.armasdurasargentinas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tofitsolutions.armasdurasargentinas.controllers.IngresoMPController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static android.support.v4.view.ViewCompat.setBackground;

//--------------------------------FALTA VALIDAR QUE TOME LA MP DEL HISTORICO------------------------------------------//

public class EstribadoraActivity extends AppCompatActivity implements TextWatcher {

    Button bt_datosUsuario, bt_principal, bt_okEstribadora, bt_cancelEstribadora;
    EditText et_precintoA, et_precintoB, et_kgA, et_kgB;
    TextView tv_usuario, tv_ayudante, tv_maquina;
    private ArrayList<Maquina> maquinas;
    private ProgressDialog progress;
    private String codPreA;
    private String codPreB;
    IngresoMPController ingresoMPController;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ingresoMPController = new IngresoMPController();
        maquinas = new ArrayList<>();
        //new PrecintosQuery().execute();
        //mdi = new MaquinaDAOImpl("Estribadora");
        //mdi.execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estribadora);

        //Inicializo Button

        bt_datosUsuario = (Button)findViewById(R.id.bt_datosUsuario);
        bt_principal = (Button)findViewById(R.id.bt_principal);
        bt_okEstribadora = (Button)findViewById(R.id.bt_okEstribadora);
        bt_cancelEstribadora = (Button)findViewById(R.id.bt_cancelEstribadora);

        //Inicializo EditText
        et_kgA = (EditText) findViewById(R.id.et_kgA);
        et_kgB = (EditText) findViewById(R.id.et_kgB);
        et_precintoA = (EditText)findViewById(R.id.et_precintoA);
        et_precintoA.addTextChangedListener(this);

        et_precintoB = (EditText)findViewById(R.id.et_precintoB);
        et_precintoB.addTextChangedListener(this);
        //Inicializo TextView
        tv_usuario = (TextView)findViewById(R.id.tv_usuarioEA);
        tv_ayudante = (TextView)findViewById(R.id.tv_ayudanteEA);
        tv_maquina = (TextView)findViewById(R.id.tv_maquinaEA);



        //Ingresa info del Activity -> DatosUsuarioActivity
        Intent intentDatosUsuarios = getIntent();
        final String et_invalidos = intentDatosUsuarios.getStringExtra("et_invalidos");
        final String usuario = intentDatosUsuarios.getStringExtra("usuario");
        final String ayudante = intentDatosUsuarios.getStringExtra("ayudante");
        final String maquina = intentDatosUsuarios.getStringExtra("maquina");
        final String diametro_minimo = intentDatosUsuarios.getStringExtra("diametro_minimo");
        final String diametro_maximo = intentDatosUsuarios.getStringExtra("diametro_maximo");
        final String merma = intentDatosUsuarios.getStringExtra("merma");


        //Cambia el valor de los TextView
        if (usuario != null) {
            tv_usuario.setText(usuario);
            tv_ayudante.setText(ayudante);
            tv_maquina.setText(maquina);
        }

        //Valida nulidad de los EditText
        if (et_invalidos != null) {
            //Ejecuto QUERY
            et_precintoA.setEnabled(true);
            et_precintoB.setEnabled(true);
            et_precintoA.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
            et_precintoB.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);

            et_precintoA.setBackground(ContextCompat.getDrawable(EstribadoraActivity.this, R.drawable.border_radius));
            et_precintoB.setBackground(ContextCompat.getDrawable(EstribadoraActivity.this, R.drawable.border_radius));

            if (et_precintoA.isFocusable()) {
                et_precintoA.requestFocus();
                et_precintoA.setHint("Por favor lea el codigo");
                et_precintoA.setHintTextColor(Color.RED);
            }
        }
        // Si es nulo. Devuelve un mensaje
        else {
            et_precintoA.setInputType(InputType.TYPE_NULL);
            et_precintoB.setInputType(InputType.TYPE_NULL);
            et_precintoA.setEnabled(false);
            et_precintoB.setEnabled(false);

            et_precintoA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_precintoA.setEnabled(false);
                    String mensaje = "Ingrese Usuario, Ayudante y Maquina.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }
            });

            et_precintoB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_precintoB.setEnabled(false);
                    String mensaje = "Ingrese Usuario, Ayudante y Maquina.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }
            });
        }

        //Redirecciona a DatosUsuario
        bt_datosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EstribadoraActivity.this, DatosUsuarioActivity.class);
                finish();
                startActivity(i);
            }
        });

        //Redirecciona a Principal
        bt_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EstribadoraActivity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });

        //Acepta
        bt_okEstribadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usuario == null || maquina == null){
                    String mensaje = "Error: Debe completar al menos usuario y máquina para continuar.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                    return;

                }
                //Valida los campos "precintoA" y "precintoB"
                String precintoA = et_precintoA.getText().toString();
                String precintoB = "";
                try {
                    precintoB = et_precintoB.getText().toString();
                }
                catch (Exception e) {
                    precintoB = "";
                }
                codPreA = precintoA.substring(10,20);
                codPreB = "";

                // Si se ingresan ambos precintos, se valida que sea el mismo codigo de MP
                if((precintoB.length() != 24) && precintoB.length() != 0){
                    String mensaje = "Error: El precinto B , debe contener 24 caracteres.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                    return;
                }
                if(!precintoB.isEmpty()) {
                    codPreB = precintoB.substring(10, 20);
                    Log.d("entro","codPreA " + codPreA);
                    Log.d("entro","codPreB " + codPreB);
                    // Verifica si los codigo de MP son iguales
                    if(codPreA.equals(codPreB)) {
                        validarPrecinto(precintoA, precintoB, usuario, ayudante, maquina,diametro_minimo,diametro_maximo,merma);
                    }
                    else {
                        Log.d("entro","al else");
                        String mensaje = "Error: Los lotes no corresponden al mismo código de material.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                        et_precintoA.setText("");
                        et_precintoB.setText("");
                        et_precintoB.setHint("Precinto 2");
                        et_precintoB.setHintTextColor(getResources().getColor(R.color.colorPrimary));
                        et_precintoA.setHint("Por favor lea el codigo");
                        et_precintoA.setHintTextColor(Color.RED);
                        et_precintoA.requestFocus();
                    }
                }
                // Si se ingresa solo el precinto "A"
                else {
                    validarPrecinto(precintoA, precintoB, usuario, ayudante, maquina,diametro_minimo,diametro_maximo,merma);
                }
            }
        });

        //Cancela
        bt_cancelEstribadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EstribadoraActivity.this, ProduccionActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //Toast.makeText(this, "before change", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Toast.makeText(this, "on text change", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String nro = editable.toString();

        if (nro.length()==24) {
            if(et_precintoA.length() == 24){
                et_kgA.setText(et_precintoA.getText().toString().substring((20)));
            }

            if(et_precintoB.length() == 24){
                et_kgB.setText(et_precintoB.getText().toString().substring((20)));
            }


            System.out.println(nro);
            if(ingresoMPController.esRollo(nro)){
                String mensaje = "Es Rollo";
                Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                msjToast.show();
            }
            else{
                String mensaje = "El precinto ingresado no es Rollo";
                Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                msjToast.show();


            }
            //editable.replace(0,editable.length(),"100");
            if (et_precintoA.isFocusable()) {

                et_precintoB.requestFocus();
                et_precintoB.setHint("Por favor lea el codigo");
                et_precintoB.setHintTextColor(Color.RED);
            }
        }
    }

    public void validarPrecinto(String precintoA, String precintoB, String usuario, String ayudante, String maquina, String diametro_minimo, String diametro_maximo, String merma) {
        String lotea="";
        String loteb ="";
        if (precintoA.length() == 24) {
            if (precintoB.length() == 24 || precintoB.length() == 0) {
                if (!precintoA.equals(precintoB)) {
                    lotea = precintoA.substring(0,10);
                    if(precintoB.length() != 0){
                        loteb= precintoB.substring(0,10);
                    }
                    if(ingresoMPController.esRollo(precintoA)){
                        IngresoMP precinto1 = ingresoMPController.getMP(precintoA);
                        if(precintoB.length()!=0) {
                            if(ingresoMPController.esRollo((precintoB))) {
                                IngresoMP precinto2 = ingresoMPController.getMP(precintoB);

                                Intent i = new Intent(EstribadoraActivity.this, Estribadora2Activity.class);
                                String kgPrecintoA = precinto1.getCantidad();
                                String kgPrecintoB = precinto2.getCantidad();
                                i.putExtra("codPreA", codPreA);
                                i.putExtra("codPreB", codPreB);
                                i.putExtra("kgdisponible1" , precinto1.getKgDisponible());
                                i.putExtra("kgdisponible2" , precinto2.getKgDisponible());
                                i.putExtra("kgproducido1",precinto1.getKgProd());
                                i.putExtra("kgproducido2",precinto2.getKgProd());

                                i.putExtra("precintoA", precintoA);
                                i.putExtra("precintoB", precintoB);
                                i.putExtra("usuario",usuario);
                                i.putExtra("ayudante", ayudante);
                                i.putExtra("maquina", maquina);
                                i.putExtra("diametro_minimo", diametro_minimo);
                                i.putExtra("diametro_maximo", diametro_maximo);
                                i.putExtra("merma", merma);
                                i.putExtra("kgPrecintoA", kgPrecintoA);
                                i.putExtra("kgPrecintoB", kgPrecintoB);
                                finish();
                                startActivity(i);
                            } else {
                                String mensaje = "Error: El número de precinto 'B' no es rollo";
                                Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                                msjToast.show();
                                et_precintoB.requestFocus();
                                et_precintoB.setText("");
                                et_precintoB.setHint("Por favor lea el codigo");
                                et_precintoB.setHintTextColor(Color.RED);
                            }
                        }
                        else {
                            Intent i = new Intent(EstribadoraActivity.this, Estribadora2Activity.class);
                            String kgPrecintoA = precinto1.getCantidad();
                            String kgPrecintoB = "0";
                            i.putExtra("codPreA", codPreA);
                            i.putExtra("codPreB", codPreB);
                            i.putExtra("kgdisponible1" , precinto1.getKgDisponible());
                            i.putExtra("kgproducido1",precinto1.getKgProd());
                            i.putExtra("precintoA", precintoA);
                            i.putExtra("precintoB", precintoB);
                            i.putExtra("usuario",usuario);
                            i.putExtra("ayudante", ayudante);
                            i.putExtra("maquina", maquina);
                            i.putExtra("diametro_minimo", diametro_minimo);
                            i.putExtra("diametro_maximo", diametro_maximo);
                            i.putExtra("merma", merma);
                            i.putExtra("kgPrecintoA", kgPrecintoA);
                            i.putExtra("kgPrecintoB", kgPrecintoB);
                            finish();
                            startActivity(i);
                        }
                    }
                    else {
                        String mensaje = "Error: El número de precinto 'A' no es rollo";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                        et_precintoA.requestFocus();
                        et_precintoA.setText("");
                        et_precintoA.setHint("Por favor lea el codigo");
                        et_precintoA.setHintTextColor(Color.RED);
                        et_precintoB.setHint("Precinto 2");
                        et_precintoB.setHintTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                }
                else {
                    String mensaje = "Error: Los números de precinto deben ser distintos";
                    et_precintoB.setText("");
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }
            }
            else {
                String mensaje = "Error: El código del precinto B debe contener 24 caracteres";
                et_precintoB.setText("");
                Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                msjToast.show();
            }
        }
    }




    }


    //NUEVO ASINCSTASK PARA BUSCAR LAS ESTRIBADORAS