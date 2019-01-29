package com.tofitsolutions.armasdurasargentinas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tofitsolutions.armasdurasargentinas.controllers.CodigoMPController;
import com.tofitsolutions.armasdurasargentinas.controllers.IngresoMPController;

public class StockInicial extends AppCompatActivity {

    EditText et_codigoBarras;
    Button button_finalizar, button_ok;
    IngresoMPController ingresoMPController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_inicial);

        //Inicializo los elementos
        et_codigoBarras=(EditText)findViewById(R.id.et_codigoBarras);
        button_finalizar=(Button)findViewById(R.id.button_finalizar);
        button_ok=(Button)findViewById(R.id.button_ok);
        ingresoMPController = new IngresoMPController();

        //Se le da el foco a et_codigoBarras y se setea la propiedad hint
        et_codigoBarras.requestFocus();
        et_codigoBarras.setHint("Por favor lea el codigo");
        et_codigoBarras.setHintTextColor(Color.RED);

        //Anulo el ingreso por teclado
        et_codigoBarras.setInputType(InputType.TYPE_NULL);

        //Boton OK
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigoBarras = et_codigoBarras.getText().toString();

                //Valida cantidad de digitos
                if(codigoBarras.length()!=24) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StockInicial.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Atencion!");
                    builder.setMessage("El codigo de barras debe contener 24 digitos.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                if(ingresoMPController.getMP(codigoBarras) != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(StockInicial.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Stock Inicial");
                    builder.setMessage("El codigo de barras ingresado ya existe en la base de datos..");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    //Se le da el foco, se elimina el texto y se setea el hint para el elemento et_codigoBarras
                    et_codigoBarras.requestFocus();
                    et_codigoBarras.setText("");
                    et_codigoBarras.setHint("Por favor lea el codigo");
                    et_codigoBarras.setHintTextColor(Color.RED);

                    return;
                }
                if(ingresoMPController.insertStockInicial(codigoBarras)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StockInicial.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Stock Inicial");
                    builder.setMessage("Insertado correctamente.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StockInicial.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Stock Inicial");
                    builder.setMessage("Ha ocurrido un error al insertar el codigo de barras.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                //Se le da el foco, se elimina el texto y se setea el hint para el elemento et_codigoBarras
                et_codigoBarras.requestFocus();
                et_codigoBarras.setText("");
                et_codigoBarras.setHint("Por favor lea el codigo");
                et_codigoBarras.setHintTextColor(Color.RED);
            }
        });

        button_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StockInicial.this, PrincipalActivity.class);
                finish();
                startActivity(i);
            }
        });
    }
}
