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

import com.tofitsolutions.armasdurasargentinas.controllers.FormatoController;
import com.tofitsolutions.armasdurasargentinas.controllers.ItemController;
import com.tofitsolutions.armasdurasargentinas.models.Formato;

public class LineaDobladoActivity extends AppCompatActivity {
    Button bt_oklineadoblado2,bt_cancellineadoblado2,bt_principal,bt_datosUsuario;
    TextView tv_usuarioEA2,tv_ayudanteEA2,tv_maquinaEA2,textView_PrecintoA,tv_cantidad1KGEA2,tv_cantPosible,tv_pendiente;
    EditText et_cantidadADeclarar,et_ItemEstribadora2;
    ItemController itemController;

    FormatoController formatoController;
    Item item =null;
    int cantidadPosible = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_doblado2);
        bt_datosUsuario = (Button) findViewById(R.id.bt_datosUsuario);
        bt_oklineadoblado2 = (Button) findViewById(R.id.bt_oklineadoblado2);
        bt_cancellineadoblado2 = (Button) findViewById(R.id.bt_cancellineadoblado2);
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



        itemController = new ItemController();
        formatoController = new FormatoController();
        //OBTIENE DATOS DE VISTA ANTERIOR

        Intent intentProduccion = getIntent();
        final String usuario = intentProduccion.getStringExtra("usuario");
        final String ayudante = intentProduccion.getStringExtra("ayudante");
        final Maquina maquina = (Maquina)intentProduccion.getSerializableExtra("maquina");
        //final IngresoMP ingreso = (IngresoMP)intentProduccion.getSerializableExtra("ingresoMP");
        //final CodigoMP codigoMP = (CodigoMP) intentProduccion.getSerializableExtra("codigoMP");
        //final String kgReal = intentProduccion.getStringExtra("kgReal");

        if(usuario != null){
            tv_usuarioEA2.setText(usuario);
            tv_ayudanteEA2.setText(ayudante);
            tv_maquinaEA2.setText(maquina.getMarca() + "-" + maquina.getModelo());
        }
        //textView_PrecintoA.setText(ingreso.getLote());
        //tv_cantidad1KGEA2.setText(kgReal);



        //Redirecciona a DatosUsuario
        bt_datosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaDobladoActivity.this, DatosUsuarioDoblActivity.class);
                finish();
                startActivity(i);
            }
        });


        //Redirecciona a DatosUsuario
        bt_oklineadoblado2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usuario == null || usuario.equals("") || maquina == null){

                    //DEBE COMPLETAR LOS DATOS DE USUARIO PARA CONTINUAR
                    AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("Debe completar los datos de usuario y maquina para continuar.");
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

                if(et_ItemEstribadora2.getText().toString() == null ||
                        et_ItemEstribadora2.getText().toString().equals("") ||
                        et_cantidadADeclarar.getText().toString() == null ||
                        et_cantidadADeclarar.getText().toString().equals("") ){


                    //DEBE COMPLETAR LOS DATOS DE USUARIO PARA CONTINUAR
                    AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("Debe completar los campos para continuar.");
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
                Intent i = new Intent(LineaDobladoActivity.this, LineaDoblado2Activity.class);
                finish();
                startActivity(i);
            }
        });

        //Redirecciona a DatosUsuario
        bt_cancellineadoblado2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaDobladoActivity.this, ProduccionActivity.class);
                finish();
                startActivity(i);
            }
        });

        //Redirecciona a DatosUsuario
        bt_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LineaDobladoActivity.this, PrincipalActivity.class);
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
                Item itemTemp = null;
                String nro = s.toString();
                String itemPendiente = "none";
                String cantPosible = "none";


                if (nro.length()==11) {

                    itemTemp = itemController.getItem(et_ItemEstribadora2.getText().toString());
                    // --------------ESTA VALIDACION DEBE IR PRIMERO-------------------
                    //Valida que el item exista en la base de datos
                    if(itemTemp==null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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


                    Formato formato = formatoController.getFormato(Long.parseLong(itemTemp.getFormato()));
                    //Valida si el item ya se encuentra declarado


                    if(formato.getCant_doblados() == 0 ){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El formato del item ingresado no contiene doblados.");
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


                    if (itemTemp.getCantidad().equals(itemTemp.getCantidadDec())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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


                    if ( ! (Integer.parseInt(itemTemp.getCantidad()) == Integer.parseInt(itemTemp.getCantidadDec()))) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("Aún no se han declarado todas las piezas en la máquina CORTADORA.");
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
                    //String tipoMat = codigoMP.getTipoMaterial();

                    /*
                    if(!tipoMat.equals("ADNS") && !tipoMat.equals("ADN") ){

                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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

                    }
                }

            }
        });


    }



    public int calcularPosible(double kgPrecinto, double kgItem,int cantItem,int merma){
        double resp= 0;

        double kgTotalItem = kgItem * cantItem;
        kgTotalItem = kgTotalItem + (merma * kgTotalItem / 100);
        if(cantItem == 0){
            return 0;
        }
        if(kgPrecinto >= kgTotalItem){
            return cantItem;
        }
        else{
            return calcularPosible(kgPrecinto, kgItem, cantItem-1,merma);
        }
    }


}
