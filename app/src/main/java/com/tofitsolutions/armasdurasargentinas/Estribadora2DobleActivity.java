package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import com.tofitsolutions.armasdurasargentinas.controllers.CodigoMPController;
import com.tofitsolutions.armasdurasargentinas.controllers.DeclaracionController;
import com.tofitsolutions.armasdurasargentinas.controllers.ItemController;
import com.tofitsolutions.armasdurasargentinas.controllers.OrdenDeProduccionController;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import java.util.ArrayList;

public class Estribadora2DobleActivity extends AppCompatActivity {
    private CheckBox chkSubproduco;

    double kgUnitarioDelItem = 0;
    int cantidadPosibleNumP1;
    int cantidadPosibleNumP2;
    int pesoPosibleP1;
    int pesoPosibleP2;
    Double cantidadPendienteNumP1;
    Double cantidadPendienteNumP2;
    Double kgAProducir;
    boolean declaroTodo;
    //KG, CANT POSIBLE Y CANT PENDIENTE .
    private TextView tv_usuarioEA2, tv_ayudanteEA2, tv_maquinaEA2;
    private TextView textView_precintoA,textView_precintoB,textView_piezasPosiblesA,textView_piezasPosiblesB,
            textView_kgPosibleA,textView_kgPosibleB,textView_piezasPosiblesTotal,textView_kgPosibleTotal,
            textView_piezasPendientes,textView_kgPendientes;

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
    OrdenDeProduccionController opController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estribadora2doble);
        itemController = new ItemController();
        opController = new OrdenDeProduccionController();
        declaracionController = new DeclaracionController();
        codigoMPController = new CodigoMPController();
        tv_usuarioEA2 = (TextView) findViewById(R.id.tv_usuarioEA2);
        tv_ayudanteEA2 = (TextView) findViewById(R.id.tv_ayudanteEA2);
        tv_maquinaEA2 = (TextView) findViewById(R.id.tv_maquinaEA2);
        bt_teclado = (Button) findViewById(R.id.bt_teclado);
        bt_okEstribadora2 = (Button) findViewById(R.id.bt_okEstribadora2);
        bt_principalEstribadora2 = (Button) findViewById(R.id.bt_principalEstribadora2);
        bt_cancelEstribadora2 = (Button) findViewById(R.id.bt_cancelEstribadora2);
        chkSubproduco = (CheckBox) findViewById(R.id.chkSubproduco);
        chkSubproduco.setVisibility(View.INVISIBLE);
        //TEXTVIEWS

        textView_precintoA = (TextView) findViewById(R.id.textView_precintoA);
        textView_precintoB = (TextView) findViewById(R.id.textView_precintoB);
        textView_piezasPosiblesA = (TextView) findViewById(R.id.textView_piezasPosiblesA);
        textView_piezasPosiblesB = (TextView) findViewById(R.id.textView_piezasPosiblesB);
        textView_kgPosibleA = (TextView) findViewById(R.id.textView_kgPosibleA);
        textView_kgPosibleB = (TextView) findViewById(R.id.textView_kgPosibleB);
        textView_piezasPosiblesTotal = (TextView) findViewById(R.id.textView_piezasPosiblesTotal);
        textView_kgPosibleTotal = (TextView) findViewById(R.id.textView_kgPosibleTotal);
        textView_piezasPendientes = (TextView) findViewById(R.id.textView_piezasPendientes);
        textView_kgPendientes = (TextView) findViewById(R.id.textView_kgPendientes);



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

        textView_precintoA.setText(ingresoMP1.getLote());
        textView_precintoB.setText(ingresoMP2.getLote());

        tv_usuarioEA2.setText(usuario);
        tv_ayudanteEA2.setText(ayudante);
        tv_maquinaEA2.setText(maquina.getMarca() + "-" + maquina.getModelo());

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

                    Items item = itemController.getItem(et_ItemEstribadora2.getText().toString());
                    // --------------ESTA VALIDACION DEBE IR PRIMERO-------------------
                    //Valida que el item exista en la base de datos
                    if(item==null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    if (item.getCantidad().equals(item.getCantidadDec())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    CodigoMP codigoMP2 = codigoMPController.getCodigoMP(ingresoMP2.getDescripcion());
                    String tipoMat = codigoMP.getTipoMaterial();
                    String tipoMat2 = codigoMP2.getTipoMaterial();
                    if(!tipoMat.equals("ADNS") && !tipoMat.equals("ADN") || !tipoMat2.equals("ADNS") && !tipoMat2.equals("ADN")){

                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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


                    if( (codigoMP2.getTipoMaterial().equals("ADN") || codigoMP.getTipoMaterial().equals("ADN")) && !item.getAcero().equals("ADN420")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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



                    if(!codigoMP.getFamilia().equals(item.getDiametro()) || !codigoMP2.getFamilia().equals(item.getDiametro())){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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


                    if(opController.validadoOP(item.getCodigo(),item.getDiametro())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("No se puede declarar este item, por que no contiene una orden de producción.");
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


                    if(Integer.parseInt(item.getDiametro()) < Double.parseDouble(maquina.getdiametroMin()) || Integer.parseInt(item.getDiametro()) > Double.parseDouble(maquina.getdiametroMax())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    cantidad = (item.getCantidad());
                    cantidadDec = (item.getCantidadDec());
                    double kgUnitario = Double.parseDouble(item.getPeso()) / Double.parseDouble(cantidad);



                    //CALCULAR POSIBLES

                    List<Integer> cantidadesPosibles = Util.calcularPosibleDosPrecintos((Double.parseDouble(ingresoMP1.getKgDisponible())),(Double.parseDouble(ingresoMP2.getKgDisponible())),kgUnitario,(Integer.parseInt(cantidad) - Integer.parseInt(cantidadDec)),maquina);

                    if(cantidadesPosibles == null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("Ningun precinto puede declarar almenos una pieza.");
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

                    cantidadPosibleNumP1 = cantidadesPosibles.get(0);
                    cantidadPosibleNumP2 = cantidadesPosibles.get(1);


                    //CALCULAR POSIBLES FIN



                    String kgPosibleA = String.valueOf(Util.setearDosDecimales(cantidadPosibleNumP1 * kgUnitario));
                    String kgPosibleB = String.valueOf(Util.setearDosDecimales(cantidadPosibleNumP2 * kgUnitario));
                    textView_kgPosibleA.setText(kgPosibleA);
                    textView_kgPosibleB.setText(kgPosibleB);
                    textView_piezasPosiblesA.setText(String.valueOf(cantidadPosibleNumP1));
                    textView_piezasPosiblesB.setText(String.valueOf(cantidadPosibleNumP2));

                    int piezasTotal = (cantidadPosibleNumP1 +cantidadPosibleNumP2);
                    String kgTotal = String.valueOf(Util.setearDosDecimales(piezasTotal * kgUnitario));

                    textView_piezasPosiblesTotal.setText(String.valueOf(piezasTotal));
                    textView_kgPosibleTotal.setText(kgTotal);

                    et_cantidadADeclarar.setText(String.valueOf(piezasTotal));

                    if(cantidadPosibleNumP1==0 && cantidadPosibleNumP2==0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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


                    if( (cantidadPosibleNumP1 + cantidadPosibleNumP2 < Integer.parseInt((et_cantidadADeclarar.getText().toString())))
                            ){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                        textView_piezasPendientes.setText("");
                        textView_kgPendientes.setText("");
                        et_cantidadADeclarar.setText("");
                        return;
                    }
                    else{


                        item = et_ItemEstribadora2.getText().toString();
                        itemObject = itemController.getItem(item);
                        cantidad = (itemObject.getCantidad());
                        cantidadDec = (itemObject.getCantidadDec());
                        double kgUnitario = Double.parseDouble(itemObject.getPeso()) / Double.parseDouble(cantidad);
                        kgUnitarioDelItem = kgUnitario;
                        //cantidadPendienteNum = (Double.parseDouble(i.getCantidad())) - Integer.parseInt(et_cantidadADeclarar.getText().toString()) - Integer.parseInt(cantidadDec);
                        kgAProducir = Util.setearDosDecimales(Integer.parseInt(et_cantidadADeclarar.getText().toString()) * kgUnitario);

                        int piezasPendientes = Integer.parseInt(cantidad) - Integer.parseInt(cantidadDec) - Integer.parseInt(nro);
                        String kgPendientes = String.valueOf(Util.setearDosDecimales(kgUnitario*piezasPendientes));
                        textView_piezasPendientes.setText(String.valueOf(piezasPendientes));
                        textView_kgPendientes.setText(kgPendientes);



                        if(piezasPendientes == 0){
                            //MOSTRAR CHECKBOX
                            chkSubproduco.setVisibility(View.VISIBLE);
                            declaroTodo = true;
                        }
                        else{
                            chkSubproduco.setChecked(false);
                            chkSubproduco.setVisibility(View.INVISIBLE);
                            declaroTodo = false;
                            //OCULTAR CHECKBOX Y MARCARLO COMO FALSE.
                        }


                        //tv_pendiente.setText("P: " + cantidadPendienteNum);
                        //tv_kgADeclarar.setText("KG:" + kgAProducir);

                    }
                }


                }


            });
        bt_okEstribadora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_cantidadADeclarar.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("Debe ingresar la cantidad de piezas a declarar.");
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
                if(cantidadPosibleNumP1 +  cantidadPosibleNumP2< Integer.parseInt((et_cantidadADeclarar.getText().toString()))){

                    AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("El item insertado no corresponde.");
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
                    double kgPosibleA = Double.parseDouble(textView_kgPosibleA.getText().toString());
                    double kgPosibleB = Double.parseDouble(textView_kgPosibleB.getText().toString());
                    double kgUnitario = kgUnitarioDelItem;
                    int itemsAUsar = Integer.parseInt(et_cantidadADeclarar.getText().toString());
                    List<Integer> kgAProducirPrecrintos = Util.calcularPosibleDosPrecintos((Double.parseDouble(ingresoMP1.getKgDisponible())),(Double.parseDouble(ingresoMP2.getKgDisponible())),kgUnitario,itemsAUsar,maquina);

                    if(kgAProducirPrecrintos ==  null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Estribadora2DobleActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
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

                    int piezasAProducir  =  kgAProducirPrecrintos.get(0);
                    int piezasBProducir  =  kgAProducirPrecrintos.get(1);



                    double kgAProducirLOTEA = Util.setearDosDecimales(piezasAProducir *kgUnitario) ;
                    double kgAProducirLOTEB = Util.setearDosDecimales(piezasBProducir * kgUnitario);




                    Intent i = new Intent(Estribadora2DobleActivity.this, ConfirmaEstribadoraDoble.class);
                    i.putExtra("usuario", usuario);
                    i.putExtra("ayudante", ayudante);
                    i.putExtra("maquina", maquina);
                    i.putExtra("ingresoMP1",ingresoMP1);
                    i.putExtra("ingresoMP2",ingresoMP2);
                    i.putExtra("item", item);
                    i.putExtra("kgAProducirA",kgAProducirLOTEA);
                    i.putExtra("kgAProducirB",kgAProducirLOTEB);
                    i.putExtra("kgAProducir",kgAProducir);
                    i.putExtra("itemObject",itemObject);
                    i.putExtra("cantidad", Integer.parseInt(et_cantidadADeclarar.getText().toString()));
                    i.putExtra("kgTotalItem", String.valueOf(kgTotalItem));
                    i.putExtra("subproducto",chkSubproduco.isChecked());
                    i.putExtra("declaroTodo",declaroTodo);
                    finish();
                    startActivity(i);

                }



                //}
                /*else{
                    String mensaje = "Error: El código o el diametro del item no se corresponden.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }*/


        });

        // Cancelar
        bt_cancelEstribadora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Estribadora2DobleActivity.this, EstribadoraActivity.class);
                finish();
                startActivity(i);
            }
        });

        // Menú principal
        bt_principalEstribadora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Estribadora2DobleActivity.this, PrincipalActivity.class);
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







    public List<Integer>  calcularPiezasAUsar(int posiblesA, int posiblesB, int cantidadTotal){
           List<Integer> resultados = new ArrayList<>(2);
           int piezasA = posiblesA;
           int piezasB = posiblesB;
           boolean esPar = false;
           boolean esParP1 = false;
           boolean esParP2 = false;
        int cantidadProporcional = cantidadTotal / 2;
        if(cantidadTotal % 2 ==0){
            esPar= true;
        }

        if(piezasA % 2 ==0 && piezasA!= 1 ){
            esParP1= true;
        }

        if(piezasB % 2 ==0 && piezasB!= 1){
            esParP2= true;
        }



            if(!esPar){
                cantidadProporcional = (cantidadTotal - 1) /2;
            }

        if(!esParP1){
            piezasA = piezasA - 1;
        }

        if(!esParP2){

            piezasB = piezasB - 1;
        }




           if(piezasA > cantidadProporcional && piezasB > cantidadProporcional ){


               if(!esParP1){
                   piezasA+= 1;
               }else{
                   if(!esParP2){
                       piezasB+=1;
                   }
               }

               resultados.add(piezasA);
               resultados.add(piezasB);

           }
           else{
            int contadorSobrantes = 0;
            if(piezasA < cantidadProporcional){
                while(piezasA < cantidadProporcional){
                    piezasA = piezasA - 1;
                }
            }
            else{
                while(piezasB < cantidadProporcional){
                    piezasB = piezasB - 1;
                }
            }
           }

        resultados.add(piezasA);
        resultados.add(piezasB);

        return resultados;
    }

    public boolean deducirMerma(Double kgAUsarA, Double kgAUsarB, int porcentajeMerma, Double kgDisponibleA, Double kgDisponibleB){

        boolean superaPermitido = false;

        Double merma1 = porcentajeMerma * kgAUsarA / 100;
        Double merma2 = porcentajeMerma * kgAUsarB / 100;

        if((merma1 + kgAUsarA ) > kgDisponibleA || (merma2 + kgAUsarB)> kgDisponibleB){
            superaPermitido = true;
        }

    return superaPermitido;
    }
}