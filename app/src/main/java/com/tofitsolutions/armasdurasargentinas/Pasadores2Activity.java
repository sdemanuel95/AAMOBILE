package com.tofitsolutions.armasdurasargentinas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tofitsolutions.armasdurasargentinas.controllers.ItemController;
import com.tofitsolutions.armasdurasargentinas.controllers.OrdenDeProduccionController;
import com.tofitsolutions.armasdurasargentinas.util.Util;

public class Pasadores2Activity extends AppCompatActivity {
    Button bt_oklineacortado2,bt_cancellineacortado2,bt_principal;
    TextView tv_usuarioEA2,tv_ayudanteEA2,tv_maquinaEA2,textView_PrecintoA,tv_cantidad1KGEA2,tv_cantPosible,tv_pendiente;
    EditText et_cantidadADeclarar,et_ItemEstribadora2;
    ItemController itemController;
    Items item =null;
    boolean declaroTodo;
    private CheckBox chkSubproduco;
    int cantidadPosible = 0;
    OrdenDeProduccionController opController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_cortado2);
        bt_oklineacortado2 = (Button) findViewById(R.id.bt_oklineacortado2);
        bt_cancellineacortado2 = (Button) findViewById(R.id.bt_cancellineacortado2);
        bt_principal = (Button) findViewById(R.id.bt_principal);
        tv_usuarioEA2 = (TextView) findViewById(R.id.tv_usuarioEA2);
        tv_ayudanteEA2 = (TextView) findViewById(R.id.tv_ayudanteEA2);
        tv_maquinaEA2 = (TextView) findViewById(R.id.tv_maquinaEA2);
        textView_PrecintoA  = (TextView) findViewById(R.id.textView_PrecintoA);
        tv_cantidad1KGEA2 = (TextView) findViewById(R.id.tv_cantidad1KGEA2);
        et_cantidadADeclarar = (EditText) findViewById(R.id.et_cantidadADeclarar) ;
        et_ItemEstribadora2 = (EditText) findViewById(R.id.et_ItemEstribadora2) ;
        tv_cantPosible = (TextView) findViewById(R.id.tv_cantPosible);
        tv_pendiente = (TextView) findViewById(R.id.tv_pendiente);

        chkSubproduco = (CheckBox) findViewById(R.id.chkSubproduco);
        chkSubproduco.setVisibility(View.INVISIBLE);
        opController = new OrdenDeProduccionController();
        itemController = new ItemController();
        //OBTIENE DATOS DE VISTA ANTERIOR
        Intent intentProduccion = getIntent();
        final String usuario = intentProduccion.getStringExtra("usuario");
        final String ayudante = intentProduccion.getStringExtra("ayudante");
        final Maquina maquina = (Maquina)intentProduccion.getSerializableExtra("maquina");
        final IngresoMP ingreso = (IngresoMP)intentProduccion.getSerializableExtra("ingresoMP");
        final CodigoMP codigoMP = (CodigoMP) intentProduccion.getSerializableExtra("codigoMP");
        final String kgReal = intentProduccion.getStringExtra("kgReal");
        tv_usuarioEA2.setText(usuario);
        tv_ayudanteEA2.setText(ayudante);
        tv_maquinaEA2.setText(maquina.getMarca() + "-" + maquina.getModelo());
        textView_PrecintoA.setText(ingreso.getLote());
        tv_cantidad1KGEA2.setText(kgReal);

        //Redirecciona a DatosUsuario
        bt_oklineacortado2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_ItemEstribadora2.length() != 11 || et_cantidadADeclarar.length() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Pasadores2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("Complete los datos para continuar");
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
                    et_cantidadADeclarar.setText("");
                    return;
                }
                Intent i = new Intent(Pasadores2Activity.this, ConfirmaPasadores.class);
                i.putExtra("ingreso",ingreso);
                i.putExtra("item",item);
                i.putExtra("codigoMP",codigoMP);
                i.putExtra("cantidad",et_cantidadADeclarar.getText().toString());
                i.putExtra("maquina",maquina);
                i.putExtra("usuario",usuario);
                i.putExtra("ayudante",ayudante);
                i.putExtra("subproducto",chkSubproduco.isChecked());
                i.putExtra("declaroTodo",declaroTodo);
                finish();
                startActivity(i);
            }
        });

        //Redirecciona a DatosUsuario
        bt_cancellineacortado2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pasadores2Activity.this, PasadoresActivity.class);
                finish();
                startActivity(i);
            }
        });

        //Redirecciona a DatosUsuario
        bt_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pasadores2Activity.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });


        //AL ESCRIBIR EL ITEM


        et_ItemEstribadora2.addTextChangedListener(new TextWatcher() {
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


                if (nro.length()==11) {

                    itemTemp = itemController.getItem(et_ItemEstribadora2.getText().toString());
                    // --------------ESTA VALIDACION DEBE IR PRIMERO-------------------
                    //Valida que el item exista en la base de datos
                    if(itemTemp==null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Pasadores2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(Pasadores2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    String tipoMat = codigoMP.getTipoMaterial();

                    /*
                    if(!tipoMat.equals("ADNS") && !tipoMat.equals("ADN") ){

                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaCortado2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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

                    */

                    if(!codigoMP.getFamilia().equals(itemTemp.getDiametro())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Pasadores2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    if(opController.validadoOP(itemTemp.getCodigo(),itemTemp.getDiametro())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Pasadores2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                    if(Integer.parseInt(itemTemp.getDiametro()) < Double.parseDouble(maquina.getdiametroMin()) || Integer.parseInt(itemTemp.getDiametro()) > Double.parseDouble(maquina.getdiametroMax())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Pasadores2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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

                    String cantidad = (itemTemp.getCantidad());
                    String cantidadDec = (itemTemp.getCantidadDec());
                    double kgUnitario = Double.parseDouble(itemTemp.getPeso()) / Double.parseDouble(cantidad);
                    tv_cantidad1KGEA2.setText(ingreso.getKgDisponible());
                    int cantidadPosibleNum = Util.calcularPosible((Double.parseDouble(ingreso.getKgDisponible())),kgUnitario,(Integer.parseInt(cantidad) - Integer.parseInt(cantidadDec)),Integer.parseInt(maquina.getMerma()));
                    cantidadPosible = cantidadPosibleNum;
                    if(cantidadPosibleNum==0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Pasadores2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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

                    int cantidadPendienteNum = (Integer.parseInt(itemTemp.getCantidad())) - cantidadPosibleNum - Integer.parseInt(cantidadDec);
                    double kgAProducir = cantidadPosibleNum * kgUnitario;
                    System.out.println("Cantidad a producir = " + kgAProducir);
                    tv_cantPosible.setText("CP: " + cantidadPosibleNum);
                    et_cantidadADeclarar.setText(String.valueOf(cantidadPosibleNum));
                    tv_pendiente.setText("P: " + String.valueOf(cantidadPendienteNum));
                    et_cantidadADeclarar.setText(String.valueOf(cantidadPosibleNum));

                    item = itemTemp;

                }
                else{
                    et_cantidadADeclarar.setText("");
                    tv_pendiente.setText("");
                    tv_cantPosible.setText("");
                }


                    /*
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

                    */



            }
        });

        et_cantidadADeclarar.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s){
                String nro = s.toString();
                if(nro.length()>=1){
                    if(et_cantidadADeclarar.getText().toString().length() >=1){

                        try{
                            Double.parseDouble(et_cantidadADeclarar.getText().toString());
                        }
                        catch(Exception e){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Pasadores2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                            builder.setTitle("Atencion!");
                            builder.setMessage("Debe ingresar un valor numérico.");
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
                        if (Integer.parseInt(et_cantidadADeclarar.getText().toString()) > cantidadPosible ) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Pasadores2Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                            builder.setTitle("Atencion!");
                            builder.setMessage("No puede declarar más que la cantidad posible.");
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
                    }
                    if(item!=null){
                        tv_pendiente.setText(String.valueOf(Integer.parseInt(item.getCantidad()) - Integer.parseInt(item.getCantidadDec()) - Integer.parseInt(et_cantidadADeclarar.getText().toString())));
                        if(Integer.parseInt(tv_pendiente.getText().toString()) == 0){
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
                    }
                }

            }
        });


    }






}
