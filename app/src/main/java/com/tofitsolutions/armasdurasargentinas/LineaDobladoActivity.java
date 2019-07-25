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
import com.tofitsolutions.armasdurasargentinas.controllers.OrdenDeProduccionController;
import com.tofitsolutions.armasdurasargentinas.controllers.SubproductoController;
import com.tofitsolutions.armasdurasargentinas.models.Formato;
import com.tofitsolutions.armasdurasargentinas.restControllers.DeclaracionImpl;

public class LineaDobladoActivity extends AppCompatActivity {
    Button bt_oklineadoblado2,bt_cancellineadoblado2,bt_principal,bt_datosUsuario;
    TextView tv_usuarioEA2,tv_ayudanteEA2,tv_maquinaEA2;
    EditText et_ItemEstribadora2;
    ItemController itemController;
    DeclaracionImpl t;
    FormatoController formatoController;
    Items item =null;
    int cantidadPosible = 0;
    SubproductoController subProductoController;
    OrdenDeProduccionController opController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        opController = new OrdenDeProduccionController();
        t = new DeclaracionImpl();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_doblado);
        bt_datosUsuario = (Button) findViewById(R.id.bt_datosUsuario);
        bt_oklineadoblado2 = (Button) findViewById(R.id.bt_oklineadoblado2);
        bt_cancellineadoblado2 = (Button) findViewById(R.id.bt_cancellineadoblado2);
        bt_principal = (Button) findViewById(R.id.bt_principal);
        tv_usuarioEA2 = (TextView) findViewById(R.id.tv_usuarioEA2);
        tv_ayudanteEA2 = (TextView) findViewById(R.id.tv_ayudanteEA2);
        tv_maquinaEA2 = (TextView) findViewById(R.id.tv_maquinaEA2);
        et_ItemEstribadora2 = (EditText) findViewById(R.id.et_ItemEstribadora2) ;

        et_ItemEstribadora2.setEnabled(false);

        itemController = new ItemController();
        formatoController = new FormatoController();
        subProductoController = new SubproductoController();
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

            et_ItemEstribadora2.setEnabled(true);
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
                        et_ItemEstribadora2.getText().toString().equals("") ){


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


                if (item.getCantidad().equals(item.getCantidadDec())){

                        if(subProductoController.isSubproducto(item.getCodigo())){
                            AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);

                            // set title
                            builder.setTitle("Confirmacion");

                            // set dialog message
                            builder.setMessage("¿Está seguro que desea continuar?");
                            builder.setCancelable(false);

                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //ACA TIENE QUE INSERTAR LA DECLARACION! :-)
                                    Declaracion d = new Declaracion(null,null,usuario,
                                            ayudante,(maquina.getMarca() +"-"+ maquina.getModelo()),null,null,item.getCodigo(),"0","0");
                                    boolean isValid = t.crearDeclaracion(d);

                                    if(isValid){
                                        //INSERTÓ CORRECTAMENTE
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                                        builder.setTitle("Declaración exitosa.");
                                        builder.setMessage("La declaracion del item " + item.getCodigo() +" se creó con éxito.");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        AlertDialog dialog2 = builder.create();
                                        dialog2.show();
                                        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.holo_green_light);

                                        et_ItemEstribadora2.setText("");
                                        return;

                                    }
                                    else{
                                        //FALLÓ LA INSERCION
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                                        builder.setTitle("Atencion!");
                                        builder.setMessage("Ocurrió un error al declarar el item.");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        AlertDialog dialog2 = builder.create();
                                        dialog2.show();
                                        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);

                                        et_ItemEstribadora2.setText("");
                                        return;
                                    }
                                }
                            });
                            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            // create alert dialog
                            AlertDialog dialog = builder.create();

                            // show it
                            dialog.show();

                        }
                        else{
                            //DEBE COMPLETAR LOS DATOS DE USUARIO PARA CONTINUAR
                            AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                            builder.setTitle("Atencion!");
                            builder.setMessage("El item no fue declarado como SUBPRODUCTO.");
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


                }
                else{


                    //DEBE COMPLETAR LOS DATOS DE USUARIO PARA CONTINUAR
                    AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("El item no se encuentra declarado al 100%.");
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


                    /*
                    Intent i = new Intent(LineaDobladoActivity.this, LineaDoblado2Activity.class);
                    i.putExtra("item",item);
                    i.putExtra("maquina",maquina);
                    i.putExtra("usuario",usuario);
                    i.putExtra("ayudante",ayudante);
                    finish();
                    startActivity(i);
*/
                }

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
                Items itemTemp = null;
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

                    //VERIFICA QUE EL DIAMETRO DEL ITEM COINCIDA CON LA MAQUINA
                    int diametroMax = Integer.parseInt(maquina.getdiametroMax());
                    int diametroMin = Integer.parseInt(maquina.getdiametroMin());
                    int diametroItem = Integer.parseInt(itemTemp.getDiametro());

                    if(diametroItem > diametroMax || diametroItem <diametroMin){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Atencion!");
                        builder.setMessage("El diametro del item no coincide con el de la máquina.");
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineaDobladoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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



                    item = itemTemp;

                }





            }
        });



    }





}
