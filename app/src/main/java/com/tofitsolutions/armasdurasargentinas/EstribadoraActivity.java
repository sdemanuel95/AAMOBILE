package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tofitsolutions.armasdurasargentinas.controllers.CodigoMPController;
import com.tofitsolutions.armasdurasargentinas.controllers.IngresoMPController;

import java.util.ArrayList;

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
    IngresoMP ingresoMP1 = null;
    IngresoMP ingresoMP2 = null;
    Maquina maquina = null;
    CodigoMPController codigoMPController;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ingresoMPController = new IngresoMPController();
        codigoMPController= new CodigoMPController();
        maquinas = new ArrayList<>();
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
        String user = intentDatosUsuarios.getStringExtra("usuario");
        String ayud = intentDatosUsuarios.getStringExtra("ayudante");

        if(user!=null){
            if(user.length() > 15){
                user = user.substring(0,15);
            }
            if(ayud.length() > 15){
                ayud = ayud.substring(0,15);
            }
        }

        final String usuario = user;
        final String ayudante = ayud;

        maquina = (Maquina)intentDatosUsuarios.getSerializableExtra("maquina");
        final String diametroMin = intentDatosUsuarios.getStringExtra("diametroMin");
        final String diametroMax = intentDatosUsuarios.getStringExtra("diametroMax");
        final String merma = intentDatosUsuarios.getStringExtra("merma");

        //Cambia el valor de los TextView
        if (usuario != null) {

            tv_usuario.setText(usuario);
            tv_ayudante.setText(ayudante);
            tv_maquina.setText(maquina.getMarca() + "-" + maquina.getModelo());
        }

        et_kgA.setInputType(InputType.TYPE_NULL);
        et_kgB.setInputType(InputType.TYPE_NULL);
        et_kgA.setEnabled(false);
        et_kgB.setEnabled(false);

        //Valida nulidad de los EditText
        if (et_invalidos != null) {
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
                if(usuario == null && maquina == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EstribadoraActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("Debe completar los campos 'usuario' y 'máquina' para continuar.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);

                    return;

                }
                if(usuario != null && maquina != null && et_precintoA.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EstribadoraActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("Debe completar al menos un precinto.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                }
                else {
                    //Valida los campos "precintoA" y "precintoB"
                    String precintoA = et_precintoA.getText().toString();
                    String precintoB = "";
                    try {
                        precintoB = et_precintoB.getText().toString();
                    } catch (Exception e) {
                        precintoB = "";
                    }
                    codPreA = precintoA.substring(10, 20);
                    codPreB = "";

                    // Si se ingresan ambos precintos, se valida que sea el mismo codigo de MP
                    if ((precintoB.length() != 24) && precintoB.length() != 0) {
                        String mensaje = "Error: El precinto B , debe contener 24 caracteres.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                        return;
                    }
                    if (!precintoB.isEmpty()) {
                        codPreB = precintoB.substring(10, 20);
                        // Verifica si los codigo de MP son iguales
                        if (codPreA.equals(codPreB)) {
                            validarPrecinto(precintoA, precintoB, usuario, ayudante, maquina);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(EstribadoraActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                            builder.setTitle("Atencion!");
                            builder.setMessage("Los lotes no corresponden al mismo tipo de material. Complete nuevamente los campos.");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                            et_precintoA.setText("");
                            et_precintoB.setText("");
                            et_kgA.setText("");
                            et_kgB.setText("");
                            et_precintoB.setHint("Precinto 2");
                            et_precintoB.setHintTextColor(getResources().getColor(R.color.colorPrimary));
                            et_precintoA.setHint("Por favor lea el codigo");
                            et_precintoA.setHintTextColor(Color.RED);
                            et_precintoA.requestFocus();
                        }
                    }
                    // Si se ingresa solo el precinto "A"
                    else {
                        validarPrecinto(precintoA, precintoB, usuario, ayudante, maquina);
                    }
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
        IngresoMP ingreso;
        if (nro.length()==24) {
            if(et_precintoA.length() == 24){

                ingreso = ingresoMPController.getMP(et_precintoA.getText().toString());
                if(ingreso==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EstribadoraActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("El precinto ingresado no existe.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                    et_precintoA.setText("");
                    et_kgA.setText("");
                    return;
                }
                else{

                    //SE VERIFICA QUE EL TIPO DE MP COINCIDA.
                    String maquinaTipoMP = (maquina.gettipoMP().toLowerCase());
                        if(maquinaTipoMP.equals("rollos")){

                            //CALCULAR PARA ROLLOS
                            maquinaTipoMP = "alambron";
                        }
                        else{
                        //CALCULAR PARA BARRAS
                            maquinaTipoMP = "barra";
                    }


                    if(ingreso.getDescripcion().toLowerCase().contains(maquinaTipoMP)){
                        //EL TIPO MP COINCIDE
                        et_kgA.setText(ingreso.getKgDisponible());

                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(EstribadoraActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El tipo de materia prima no coincide.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                        et_precintoB.setText("");
                        et_kgB.setText("");
                        return;
                    }

                }

                if (et_precintoA.isFocusable()) {
                    et_precintoB.requestFocus();
                    et_precintoB.setHint("Por favor lea el codigo");
                    et_precintoB.setHintTextColor(Color.RED);
                }
                }



            if(et_precintoB.length() == 24){
                IngresoMP ingreso2 = ingresoMPController.getMP(et_precintoB.getText().toString());

                if(ingreso2==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EstribadoraActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("El precinto ingresado no existe.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                    et_precintoB.setText("");
                    et_kgA.setText("");
                    return;
                }

                else{

                    //SE VERIFICA QUE EL TIPO DE MP COINCIDA.
                    String maquinaTipoMP = (maquina.gettipoMP().toLowerCase());
                    if(maquinaTipoMP.equals("rollos")){

                        //CALCULAR PARA ROLLOS
                        maquinaTipoMP = "alambron";
                    }
                    else{
                        //CALCULAR PARA BARRAS
                        maquinaTipoMP = "barra";
                    }

                    if(ingreso2.getDescripcion().toLowerCase().contains(maquinaTipoMP)){
                        //EL TIPO MP COINCIDE
                        et_kgB.setText(ingreso2.getKgDisponible());

                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(EstribadoraActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El tipo de materia prima no coincide.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                        et_precintoB.setText("");
                        et_kgB.setText("");
                        return;
                    }
                }




            }

            /*
            if(ingresoMPController.esRollo(nro)) {
                if (et_precintoA.isFocusable()) {
                    et_precintoB.requestFocus();
                    et_precintoB.setHint("Por favor lea el codigo");
                    et_precintoB.setHintTextColor(Color.RED);
                }
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EstribadoraActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Atencion!");
                builder.setMessage("El lote ingresado no es Rollo. Por favor ingrese uno nuevo.");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_precintoA.setText("");
                        et_precintoA.requestFocus();
                        et_precintoA.setHint("Por favor lea el codigo");
                        et_precintoA.setHintTextColor(Color.RED);
                        et_kgA.setText("");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            //editable.replace(0,editable.length(),"100");
            */
        }
    }

    public void validarPrecinto(String precintoA, String precintoB, String usuario, String ayudante,Maquina maquina) {
        String lotea="";
        String loteb ="";
        if (precintoA.length() == 24) {
            if (precintoB.length() == 24 || precintoB.length() == 0) {
                if (!precintoA.equals(precintoB)) {
                    lotea = precintoA.substring(0,10);
                    if(precintoB.length() != 0){
                        loteb= precintoB.substring(0,10);
                    }
                    if(true){
                        ingresoMP1 = ingresoMPController.getMP(precintoA);
                        if(precintoB.length()!=0) {
                            if(true) {
                                ingresoMP2  = ingresoMPController.getMP(precintoB);

                                Intent i = new Intent(EstribadoraActivity.this, Estribadora2DobleActivity.class);

                                i.putExtra("ingresoMP1",ingresoMP1);
                                i.putExtra("ingresoMP2",ingresoMP2);
                                i.putExtra("kgAUsarMP1",et_kgA.getText().toString());
                                i.putExtra("kgAUsarMP2",et_kgB.getText().toString());
                                i.putExtra("usuario",usuario);
                                i.putExtra("ayudante", ayudante);
                                i.putExtra("maquina", maquina);

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
                            i.putExtra("ingresoMP1",ingresoMP1);
                            i.putExtra("ingresoMP2",ingresoMP2);
                            i.putExtra("kgAUsarMP1",et_kgA.getText().toString());
                            i.putExtra("kgAUsarMP2",et_kgB.getText().toString());
                            i.putExtra("usuario",usuario);
                            i.putExtra("ayudante", ayudante);
                            i.putExtra("maquina", maquina);
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
