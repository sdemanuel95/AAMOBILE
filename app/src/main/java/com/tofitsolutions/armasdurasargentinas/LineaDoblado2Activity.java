package com.tofitsolutions.armasdurasargentinas;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.tofitsolutions.armasdurasargentinas.util.Util;

public class LineaDoblado2Activity extends AppCompatActivity {
    Button bt_datosUsuario,bt_ok,bt_cancel,bt_principal;
    TextView tv_item,tv_kgPrecinto,tv_cantidadPendiente,et_kgADeclarar,tv_cantidadPosible;
    EditText et_precintoA,et_cantidadADeclarar;
    IngresoMPController ingresoMPController;
    CodigoMPController codigoMPController;
    IngresoMP ingresoMP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_doblado2);
        bt_datosUsuario = (Button) findViewById(R.id.bt_datosUsuario);
        bt_ok = (Button) findViewById(R.id.bt_ok);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_principal = (Button) findViewById(R.id.bt_principal);

        tv_item = (TextView) findViewById(R.id.TVItem);
        tv_kgPrecinto = (TextView) findViewById(R.id.TVCantidadKGPrecinto);
        tv_cantidadPendiente = (TextView) findViewById(R.id.TVCantidadPendiente);
        tv_cantidadPosible = (TextView) findViewById(R.id.TVCantidadPosible);
        et_cantidadADeclarar = (EditText) findViewById(R.id.TVCantidadADeclarar);
        et_kgADeclarar = (TextView) findViewById(R.id.TVKGADeclarar);
        et_precintoA = (EditText) findViewById(R.id.et_precintoA);
        Intent intentProduccion = getIntent();
        final String usuario = intentProduccion.getStringExtra("usuario");
        final String ayudante = intentProduccion.getStringExtra("ayudante");
        final Maquina maquina = (Maquina)intentProduccion.getSerializableExtra("maquina");
        final Items item = (Items)intentProduccion.getSerializableExtra("item");


        ingresoMPController = new IngresoMPController();
        codigoMPController = new CodigoMPController();
        // PRUEBA
        TextView usuarioTV = (TextView) findViewById(R.id.tv_usuarioEA);
        TextView ayudanteTV = (TextView) findViewById(R.id.tv_ayudanteEA);
        TextView maquinaTV = (TextView) findViewById(R.id.tv_maquinaEA);

        usuarioTV.setText(usuario);
        ayudanteTV.setText(ayudante);
        maquinaTV.setText(maquina.getMarca()+"-"+maquina.getModelo());
        tv_item.setText(item.getCodigo());
        String cantidadPendiente = String.valueOf(Integer.parseInt(item.getCantidad()) - Integer.parseInt(item.getCantidadDec()));
        tv_cantidadPendiente.setText(cantidadPendiente);
        //Redirecciona a DatosUsuario
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingresoMP == null || et_cantidadADeclarar.getText().toString().equals("")
                        || et_precintoA.getText().toString().length() < 24){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LineaDoblado2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("Complete todos los datos para continuar");
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
                    et_cantidadADeclarar.setText("");
                    et_kgADeclarar.setText("");
                    tv_kgPrecinto.setText("");
                    return;
                }
                else{
                    Intent i = new Intent(LineaDoblado2Activity.this, ConfirmaLineaDoblado.class);
                    i.putExtra("usuario",usuario);
                    i.putExtra("ayudante",ayudante);
                    i.putExtra("maquina",maquina);
                    i.putExtra("ingresoMP",ingresoMP);
                    i.putExtra("item",item);
                    i.putExtra("cantidad",et_cantidadADeclarar.getText().toString());
                    i.putExtra("kgADeclarar",et_kgADeclarar.getText().toString());

                    finish();
                    startActivity(i);
                }

            }
        });

        //Redirecciona a DatosUsuario
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaDoblado2Activity.this, LineaDobladoActivity.class);
                i.putExtra("usuario",usuario);
                i.putExtra("ayudante",ayudante);
                i.putExtra("maquina",maquina);
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


        //EDIT TEXT DEL PRECINTO


        et_precintoA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Items itemTemp = null;
                String nro = s.toString();
                String itemPendiente = "none";
                String cantPosible = "none";


                if (nro.length()==24) {
                    IngresoMP ingresoTemp = ingresoMPController.getMP(et_precintoA.getText().toString());
                    // --------------ESTA VALIDACION DEBE IR PRIMERO-------------------
                    //Valida que el item exista en la base de datos
                    if(ingresoTemp==null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDoblado2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("la materia prima no existe en la base de datos.");
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
                        et_cantidadADeclarar.setText("");
                        et_kgADeclarar.setText("");
                        tv_kgPrecinto.setText("");
                        return;
                    }

                    CodigoMP codigoMP = codigoMPController.getCodigoMP(ingresoTemp.getDescripcion());
                    int diametroMaterial = Integer.parseInt(codigoMP.getFamilia());
                    //Formato formato = formatoController.getFormato(Long.parseLong(itemTemp.getFormato()));
                    //Valida si el item ya se encuentra declarado

                    //VERIFICA QUE EL DIAMETRO DEL ITEM COINCIDA CON LA MAQUINA
                    int diametroMax = Integer.parseInt(maquina.getdiametroMax());
                    int diametroMin = Integer.parseInt(maquina.getdiametroMin());
                    int diametroItem = Integer.parseInt(item.getDiametro());

                    if(diametroItem != diametroMaterial){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDoblado2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El diametro del item no coincide con el de la materia prima.");
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
                        et_cantidadADeclarar.setText("");
                        et_kgADeclarar.setText("");
                        tv_kgPrecinto.setText("");                        return;
                    }

                    if(codigoMP.getTipoMaterial().equals("ADN") && !item.getAcero().equals("ADN420")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDoblado2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El item no corresponde al material del lote.");
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
                        et_cantidadADeclarar.setText("");
                        et_kgADeclarar.setText("");
                        tv_kgPrecinto.setText("");
                        return;

                    }


                    //VERIFICA QUE SEA BARRA O ROLLO


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


                    if(ingresoTemp.getDescripcion().toLowerCase().contains(maquinaTipoMP)){
                        //EL TIPO MP COINCIDE
                        //et_kgA.setText(ingresoTemp.getKgDisponible());

                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDoblado2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                        et_precintoA.setText("");
                        et_cantidadADeclarar.setText("");
                        et_kgADeclarar.setText("");
                        tv_kgPrecinto.setText("");
                        return;
                    }




                    double kgPrecinto = Double.parseDouble(ingresoTemp.getKgDisponible());
                    double kgItem = (Double.parseDouble(item.getPeso()) /Double.parseDouble(item.getCantidad()));
                    int cantidadItem =  (Integer.parseInt(item.getCantidad()) - Integer.parseInt(item.getCantidadDec()));
                    int cantidadPosible = Util.calcularPosible(kgPrecinto,kgItem,cantidadItem,0 );


                    tv_kgPrecinto.setText(ingresoTemp.getKgDisponible());
                    tv_cantidadPendiente.setText(String.valueOf(cantidadItem));
                    tv_cantidadPosible.setText(" " + String.valueOf(cantidadPosible));
                    et_cantidadADeclarar.setText(String.valueOf(cantidadPosible));
                    et_kgADeclarar.setText(String.valueOf(cantidadPosible * kgItem));
                    ingresoMP = ingresoTemp;

                }

            }
        });



        et_cantidadADeclarar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String nro = s.toString();


                if (nro.length()>0) {

                    try{
                        Integer.parseInt(nro);
                    }catch (Exception e){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDoblado2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("La cantidad a declarar debe ser un número.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);

                        et_kgADeclarar.setText("");
                        et_cantidadADeclarar.setText("");
                        return;
                    }

                    if(et_precintoA.getText().toString().length() != 24)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDoblado2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("Complete el precinto para continuar.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);

                        et_kgADeclarar.setText("");
                        et_cantidadADeclarar.setText("");
                        return;
                    }
                    if(Integer.parseInt(nro) > Integer.parseInt((tv_cantidadPosible.getText().toString()).substring(1))){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDoblado2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("La cantidad a declarar no puede superar la cantidad posible.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);

                        et_cantidadADeclarar.setText("");

                        et_kgADeclarar.setText("");
                        return;
                    }
                    String kgADeclarar = String.valueOf(Util.setearDosDecimales(Integer.parseInt(nro) * (Double.parseDouble(item.getPeso()) / Integer.parseInt(item.getCantidad()))));
                    et_kgADeclarar.setText(kgADeclarar);
                    String cantidadPendiente = String.valueOf(Integer.parseInt(item.getCantidad()) - Integer.parseInt(item.getCantidadDec())  - Integer.parseInt(nro)) ;
                    tv_cantidadPendiente.setText(cantidadPendiente);
                }
                else{
                    et_kgADeclarar.setText("");
                }




            }
        });

    }
}
