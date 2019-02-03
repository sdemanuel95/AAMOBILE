package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tofitsolutions.armasdurasargentinas.controllers.CodigoMPController;
import com.tofitsolutions.armasdurasargentinas.controllers.DeclaracionController;
import com.tofitsolutions.armasdurasargentinas.controllers.ItemController;
import com.tofitsolutions.armasdurasargentinas.util.Conexion;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Estribadora2Activity extends AppCompatActivity {
    int cantidadPosibleNum;
    Double cantidadPendienteNum ;
    Double kgAProducir;
    //KG, CANT POSIBLE Y CANT PENDIENTE .
    private TextView tv_kgADeclarar,tv_precintoA, tv_precintoB, tv_usuarioEA2, tv_ayudanteEA2, tv_maquinaEA2, tv_cantidad1KGEA2, tv_cantidad2KGEA2, tv_cantPosible, tv_pendiente;
    private EditText et_ItemEstribadora2,et_cantidadADeclarar;
    private Button bt_teclado, bt_okEstribadora2, bt_principalEstribadora2, bt_cancelEstribadora2;
    //private ArrayList<IngresoMP> materiasPrima;

    private ArrayList<CodigoMP> listaCodigosMP;
    private ArrayList<Items> listaDeItems;
    private ArrayList<Declaracion> listaDeclaracion;
    private int pesoItem;
    private double kgTotalItem;
    private  String cantidad;
    private  String cantidadDec;
    private String item;
    private String pesoPrecintoTotal;
    private ProgressDialog progressI;
    private ProgressDialog progresso;
    private String codPreA;
    private String codPreB;
    private String kddisponible1;
    private String kddisponible2;
    private String kdproducido1;
    private String kdproducido2;
    DeclaracionController declaracionController;
    CodigoMPController codigoMPController;
    ItemController itemController;
    Items itemObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estribadora2);
        itemController = new ItemController();
        declaracionController = new DeclaracionController();
        codigoMPController = new CodigoMPController();
        tv_precintoA = (TextView) findViewById(R.id.textView_PrecintoA);
        tv_kgADeclarar =(TextView) findViewById(R.id.textView_kgADeclarar);
        tv_precintoB = (TextView) findViewById(R.id.textView_PrecintoB);
        tv_usuarioEA2 = (TextView) findViewById(R.id.tv_usuarioEA2);
        tv_ayudanteEA2 = (TextView) findViewById(R.id.tv_ayudanteEA2);
        tv_maquinaEA2 = (TextView) findViewById(R.id.tv_maquinaEA2);
        tv_cantidad1KGEA2 = (TextView) findViewById(R.id.tv_cantidad1KGEA2);
        tv_cantidad2KGEA2 = (TextView) findViewById(R.id.tv_cantidad2KGEA2);
        tv_cantPosible = (TextView) findViewById(R.id.tv_cantPosible);
        tv_pendiente= (TextView) findViewById(R.id.tv_pendiente);
        bt_teclado = (Button) findViewById(R.id.bt_teclado);
        bt_okEstribadora2 = (Button) findViewById(R.id.bt_okEstribadora2);
        bt_principalEstribadora2 = (Button) findViewById(R.id.bt_principalEstribadora2);
        bt_cancelEstribadora2 = (Button) findViewById(R.id.bt_cancelEstribadora2);



        listaCodigosMP = new ArrayList<CodigoMP>();

        et_ItemEstribadora2 = (EditText)findViewById(R.id.et_ItemEstribadora2);
        et_ItemEstribadora2.setInputType(InputType.TYPE_NULL);
        et_cantidadADeclarar = (EditText)findViewById(R.id.et_cantidadADeclarar);

        //Ingresa info del Activity -> EstribadoraActivity
        Intent intentPrecintos = getIntent();
        final Maquina maquina = (Maquina) intentPrecintos.getSerializableExtra("maquina");
        final IngresoMP ingresoMP1 = (IngresoMP)intentPrecintos.getSerializableExtra("ingresoMP1");
        final IngresoMP ingresoMP2 = (IngresoMP)intentPrecintos.getSerializableExtra("ingresoMP2");
        final String usuario = intentPrecintos.getStringExtra("usuario");
        final String ayudante = intentPrecintos.getStringExtra("ayudante");


        pesoItem = 0;
        cantidad = null;
        cantidadDec = null;
        item = "";

        if(ingresoMP2 != null ) {
            pesoPrecintoTotal = Integer.toString(Integer.parseInt(ingresoMP1.getCantidad()) +
                    Integer.parseInt(ingresoMP2.getCantidad()));
            tv_precintoB.setText(ingresoMP2.getLote());

            tv_cantidad2KGEA2.setText(ingresoMP2.getKgDisponible());
        }
        else {
            pesoPrecintoTotal = Integer.toString(Integer.parseInt(ingresoMP1.getCantidad()));
        }

        tv_precintoA.setText(ingresoMP1.getLote());

        tv_usuarioEA2.setText(usuario);
        tv_ayudanteEA2.setText(ayudante);
        tv_maquinaEA2.setText(maquina.getMarca() + "-" + maquina.getModelo());
        tv_cantidad1KGEA2.setText(ingresoMP1.getKgDisponible());

        bt_teclado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_ItemEstribadora2.setInputType(InputType.TYPE_CLASS_TEXT);
                et_ItemEstribadora2.requestFocus();
            }
        });




        et_ItemEstribadora2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String nro = s.toString();
                String itemPendiente = "none";
                String cantPosible = "none";
                if (nro.length()==11) {

                    Items itemTemp = itemController.getItem(et_ItemEstribadora2.getText().toString());
                    // --------------ESTA VALIDACION DEBE IR PRIMERO-------------------
                    //Valida que el item exista en la base de datos
                    if(itemTemp==null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El item no existe en la base de datos.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);

                        et_ItemEstribadora2.setText("");
                        return;
                    }

                    //Valida si el item ya se encuentra declarado
                    if (itemTemp.getCantidad().equals(itemTemp.getCantidadDec())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El item ingresado ya encuentra declarado.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                        et_ItemEstribadora2.setText("");
                        return;
                    }

                    //Validacion de codigo de material
                    CodigoMP codigoMP = codigoMPController.getCodigoMP(ingresoMP1.getDescripcion());
                    String tipoMat = codigoMP.getTipoMaterial();
                    if(!tipoMat.equals("ADNS") && !tipoMat.equals("ADN") ){

                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                        et_ItemEstribadora2.setText("");
                        return;
                    }


                    if(codigoMP.getTipoMaterial().equals("ADNS")){

                        /*
                        if(itemTemp.getAcero().equals("ADN420S")){


                            AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                            et_ItemEstribadora2.setText("");
                            return;
                        }
                        */
                    }

                    if(codigoMP.getTipoMaterial().equals("ADN") && !itemTemp.getAcero().equals("ADN420")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                        et_ItemEstribadora2.setText("");
                        return;

                    }

                    if(!codigoMP.getFamilia().equals(itemTemp.getDiametro())){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                            builder.setTitle("Atencion!");
                            builder.setMessage("El diametro del item no corresponde con el lote.");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                            et_ItemEstribadora2.setText("");
                            return;

                    }

                    if(Integer.parseInt(itemTemp.getDiametro()) < Double.parseDouble(maquina.getdiametroMin()) || Integer.parseInt(itemTemp.getDiametro()) > Double.parseDouble(maquina.getdiametroMax())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El diametro del item no corresponde con la máquina.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                        et_ItemEstribadora2.setText("");
                        return;
                    }

                    // Se actualizan los ET de Cantidad Posible e Items Pendientes
                    item = et_ItemEstribadora2.getText().toString();
                    itemObject = itemController.getItem(item);
                    cantidad = (itemObject.getCantidad());
                    cantidadDec = (itemObject.getCantidadDec());
                    double kgUnitario = Double.parseDouble(itemObject.getPeso()) / Double.parseDouble(cantidad);
                    tv_cantidad1KGEA2.setText(ingresoMP1.getKgDisponible());
                    cantidadPosibleNum = Util.calcularPosible((Double.parseDouble(ingresoMP1.getKgDisponible())),kgUnitario,(Integer.parseInt(cantidad) - Integer.parseInt(cantidadDec)),Integer.parseInt(maquina.getMerma()));

                    if(cantidadPosibleNum==0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("No se puede declarar ya que la cantidad posible es 0.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                        et_ItemEstribadora2.setText("");
                        return;
                    }

                    cantidadPendienteNum = (Double.parseDouble(itemObject.getCantidad())) - cantidadPosibleNum - Integer.parseInt(cantidadDec);
                    kgAProducir = cantidadPosibleNum * kgUnitario;
                    System.out.println("Cantidad a producir = " + kgAProducir);
                    tv_cantPosible.setText("CP: " + cantidadPosibleNum);
                    et_cantidadADeclarar.setText(String.valueOf(cantidadPosibleNum));
                    tv_pendiente.setText("P: " + cantidadPendienteNum);
                    tv_kgADeclarar.setText("KG:" + kgAProducir);
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
                if(nro.length() > 0){

                    if(cantidadPosibleNum < Integer.parseInt((et_cantidadADeclarar.getText().toString()))){

                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("La cantidad de piezas a declarar no puede ser mayor a la cantidad posible");
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
                        return;
                    }

                    if(Integer.parseInt(et_cantidadADeclarar.getText().toString()) > cantidadPosibleNum){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("La cantidad de piezas a declarar no puede ser mayor a la cantidad posible");
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
                        return;
                    }
                    else{
                        item = et_ItemEstribadora2.getText().toString();
                        itemObject = itemController.getItem(item);
                        cantidad = (itemObject.getCantidad());
                        cantidadDec = (itemObject.getCantidadDec());
                        double kgUnitario = Double.parseDouble(itemObject.getPeso()) / Double.parseDouble(cantidad);
                        cantidadPendienteNum = (Double.parseDouble(itemObject.getCantidad())) - Integer.parseInt(et_cantidadADeclarar.getText().toString()) - Integer.parseInt(cantidadDec);
                        kgAProducir = Integer.parseInt(et_cantidadADeclarar.getText().toString()) * kgUnitario;
                        tv_pendiente.setText("P: " + cantidadPendienteNum);
                        tv_kgADeclarar.setText("KG:" + kgAProducir);
                    }
                }


                }


            });
        bt_okEstribadora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cantidadPosibleNum < Integer.parseInt((et_cantidadADeclarar.getText().toString()))){

                    AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("La cantidad de piezas a declarar no puede ser mayor a la cantidad posible");
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
                    return;
                }
                if(et_ItemEstribadora2 == null || et_ItemEstribadora2.length() != 11){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("El item insertado debe tener 11 caracteres.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                    dialog.show();
                    return;
                }
                //TODO
                //if(validarItem()){
                    Intent i = new Intent(Estribadora2Activity.this, ConfirmaEstribadora.class);
                    i.putExtra("usuario", usuario);
                    i.putExtra("ayudante", ayudante);
                    i.putExtra("maquina", maquina);
                i.putExtra("ingresoMP1",ingresoMP1);
                i.putExtra("ingresoMP2",ingresoMP2);
                    i.putExtra("item", item);
                    i.putExtra("itemObject",itemObject);
                    i.putExtra("kgAProducir",kgAProducir);
                    i.putExtra("itemObject",itemObject);
                    i.putExtra("cantidad", Integer.parseInt(et_cantidadADeclarar.getText().toString()));
                    i.putExtra("kgTotalItem", String.valueOf(kgTotalItem));
                    finish();
                    startActivity(i);
                //}
                /*else{
                    String mensaje = "Error: El código o el diametro del item no se corresponden.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }*/
            }

        });

        // Cancelar
        bt_cancelEstribadora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Estribadora2Activity.this, EstribadoraActivity.class);
                finish();
                startActivity(i);
            }
        });

        // Menú principal
        bt_principalEstribadora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Estribadora2Activity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    private boolean validarItem(){
        boolean validacion = false;
        // Datos del codigoMP
        String tipoMaterial = ""; // ADN, ATR500, ADNS, AL220, ESPECIAL
        String familia = "";
        boolean val = false;

        // Busca el codigo y trae el tipo de material y la familia
        for(CodigoMP codigo : listaCodigosMP){
            String tM = codigo.getTipoMaterial();
            String f = codigo.getFamilia();
            String cod = codigo.getCodSap();
            if (codPreA.equals(cod)) {
                val = true;
                tipoMaterial = tM;
                familia = f;

            }
        }
        if(!val){
            String mensaje = "Error: El codigo no se corresponde con el precinto";
            Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
            msjToast.show();
        }

        // Datos Items
        String acero = ""; //ADN 420, ADN420S, ANGULOS, AL220, ALAMBRES, CLAVOS
        String diametro = "";
        // Busca el item y trae el acero y el diametro
        for (int i = 0; i<=listaDeItems.size()-1; i++) {
            String c = listaDeItems.get(i).getCodigo();
            String a = listaDeItems.get(i).getAcero();
            String d = listaDeItems.get(i).getDiametro();
            if (c.equals(item)) {
                acero = a;
                diametro = d;
            }
        }

        // Validacion
        // codigomp ADN, ATR500, ADNS, AL220, ESPECIAL
        //item : ADN420, ADN420S, ANGULOS, AL220, ALAMBRES, CLAVOS

        if (diametro.equals(familia)) {
            if (acero.equals("ADN420") && tipoMaterial.equals("ADN") || tipoMaterial.equals("ADNS")){
                validacion = true;
            }
            else if((acero.equals("ADN420S") && tipoMaterial.equals("ADNS"))){
                validacion = true;
            }
            else{
                String mensaje = "Error: El código del item no se corresponde.";
                Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                msjToast.show();
            }
        }
        else {
            String mensaje = "Error: El diametro del item no se corresponde con el diametro del lote.";
            Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
            msjToast.show();
        }
        Log.d("diametro : ", diametro);
        Log.d("familia : ", familia);
        return validacion;
    }





    //NUEVO ASYNTASK PARA VALIDAR SOLO EL ITEM PUESTO.



}