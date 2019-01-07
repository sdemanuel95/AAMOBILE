package com.tofitsolutions.armasdurasargentinas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tofitsolutions.armasdurasargentinas.controllers.CodigoMPController;
import com.tofitsolutions.armasdurasargentinas.controllers.IngresoMPController;

public class LineaCortadoActivity extends AppCompatActivity implements TextWatcher {

    Button bt_datosUsuario, bt_ok, bt_cancel, bt_principal;
    EditText et_precinto, et_kg;
    TextView tv_usuario, tv_ayudante, tv_maquina;
    IngresoMPController ingresoMPController;
    CodigoMPController codigoMPController;
    IngresoMP ingreso = null;
    CodigoMP codigoMP = null;
    public String usuario;
    public String ayudante;
    public Maquina maquina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_cortado);
        bt_datosUsuario = (Button) findViewById(R.id.bt_datosUsuario);
        bt_ok = (Button) findViewById(R.id.bt_ok);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_principal = (Button) findViewById(R.id.bt_principal);
        et_precinto = (EditText) findViewById(R.id.et_precinto);
        et_kg = (EditText) findViewById(R.id.et_kg);
        tv_usuario = (TextView) findViewById(R.id.tv_usuarioEA);
        tv_maquina = (TextView) findViewById(R.id.tv_maquinaEA);
        tv_ayudante = (TextView) findViewById(R.id.tv_ayudanteEA);
        ingresoMPController = new IngresoMPController();
        codigoMPController = new CodigoMPController();
        //SE DESHABILITA EL PRECINTO

        et_precinto.setEnabled(false);
        et_kg.setEnabled(false);
        bt_ok.setEnabled(true);
        //Redirecciona a DatosUsuario
        bt_datosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaCortadoActivity.this, DatosUsuarioCortActivity.class);
                finish();
                startActivity(i);
            }
        });
        //Redirecciona a LINEA CORTADO 2
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!esDouble(et_kg.getText().toString()) || et_precinto.length() != 24){

                    AlertDialog.Builder builder = new AlertDialog.Builder(LineaCortadoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("Validar que el peso sea numérico y que el precinto contenga 24 dígitos");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                    et_precinto.setText("");
                    et_kg.setText("");
                    return;
                }
                else{


                    if(Double.parseDouble(et_kg.getText().toString()) > Double.parseDouble(ingreso.getKgDisponible())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaCortadoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El kg ingresado supera la el disponible del precinto.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                        et_precinto.setText("");
                        et_kg.setText("");
                        return;
                    }
                    Intent i = new Intent(LineaCortadoActivity.this, LineaCortado2Activity.class);
                    i.putExtra("ingresoMP",ingreso);
                    i.putExtra("kgReal",et_kg.getText().toString());
                    i.putExtra("codigoMP",codigoMP);
                    i.putExtra("maquina",maquina);
                    i.putExtra("usuario",usuario);
                    i.putExtra("ayudante",ayudante);
                    i.putExtra("ayudante",ayudante);
                    finish();
                    startActivity(i);
                }

            }
        });

        //Redirecciona a DatosUsuario
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaCortadoActivity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });

        //Redirecciona a Principal
        bt_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaCortadoActivity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });


        //Se agrega el add text changedo al campo precinto
        et_precinto.addTextChangedListener(this);


        // PRUEBA
        TextView usuarioTV = (TextView) findViewById(R.id.et_precintoB);
        TextView ayudanteTV = (TextView) findViewById(R.id.contraseña);
        Intent intentProduccion = getIntent();
        usuario = intentProduccion.getStringExtra("usuario");
        ayudante = intentProduccion.getStringExtra("ayudante");
        maquina = (Maquina) intentProduccion.getSerializableExtra("maquina");
        if (usuario != null) {
            et_precinto.setEnabled(true);
            et_kg.setEnabled(true);
            bt_ok.setEnabled(true);
            if(usuario.length() > 15){
                usuario = usuario.substring(0,15);
            }
            if(ayudante.length() > 15){
                ayudante = ayudante.substring(0,15);
            }
            tv_usuario.setText(usuario);
            tv_ayudante.setText(ayudante);
            tv_maquina.setText(maquina.getMarca() + "-" + maquina.getModelo());
        }
    }

    //AL ESCRIBIR 24 DIGITOS
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
        if (nro.length() == 24) {
            if (et_precinto.length() == 24) {

                //OBTIENE EL INGRESO MP.
                ingreso = ingresoMPController.getMP(et_precinto.getText().toString());

                //SI EL INGRESO NO EXISTE EN BASE DE DATOS.
                if (ingreso == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LineaCortadoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    et_precinto.setText("");
                    et_kg.setText("");
                    return;
                } else {
                    et_kg.setText(ingreso.getKgDisponible());
                }


                //OBTENGO EL CODIGO MP
                codigoMP = codigoMPController.getCodigoMP(ingreso.getDescripcion());

                //Se valida diametro.
                int diametroMinMaquina = Integer.parseInt(maquina.getdiametroMin());
                int diametroMaxMaquina = Integer.parseInt(maquina.getdiametroMax());
                int diametroCodigoMP = Integer.parseInt(codigoMP.getFamilia());

                if(diametroMinMaquina <= diametroCodigoMP && diametroMaxMaquina >= diametroCodigoMP){
                    //EL DIAMETRO ES CORRECTO.
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LineaCortadoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("El diametro de la máquina no corresponde con el precinto ingresado.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                    et_precinto.setText("");
                    et_kg.setText("");
                    return;
                }


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


                if(codigoMP.getDescripcion().toLowerCase().contains(maquinaTipoMP)){
                    //EL TIPO MP COINCIDE

                    et_kg.setText(ingreso.getKgDisponible());
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LineaCortadoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    et_precinto.setText("");
                    et_kg.setText("");
                    return;
                }
            }

        }
    }


    public boolean esDouble(String numero){
        try{
            double d = Double.parseDouble(numero);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
