package com.tofitsolutions.armasdurasargentinas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tofitsolutions.armasdurasargentinas.controllers.IngresoMPController;

/**
 * Created by RiverPlate on 19/03/2018.
 */

public class Informes_por_lote_Activity extends AppCompatActivity{

    private Button btnConsultar;
    private EditText et_Lote;
    private IngresoMPController ingresoMPImpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes_por_lote);
        btnConsultar = (Button)findViewById(R.id.btnConsultar);
        et_Lote = (EditText) findViewById(R.id.et_Lote);
        ingresoMPImpl = new IngresoMPController();

        btnConsultar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(et_Lote.getText().toString().length() != 24){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Informes_por_lote_Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Error!");
                    builder.setMessage("El código del precinto debe tener 24 caracteres.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                    et_Lote.setText("");
                    return;

                }
                else{

                    //ACA COMPLETÓ LOS 24 CARACTERES.
                    IngresoMP ingresoMP = ingresoMPImpl.getMP(et_Lote.getText().toString());
                    if(ingresoMP == null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Informes_por_lote_Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Error!");
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
                        et_Lote.setText("");
                        return;
                    }

                    else{

                        //REDIRECCIONAR A PAGINA DE INFORMACION DEL LOTE
                        Intent i = new Intent(Informes_por_lote_Activity.this, Informes_por_lote_lista_Activity.class);
                        i.putExtra("lote",et_Lote.getText().toString());
                        startActivity(i);
                    }

                }

            }
        });




    }

}
