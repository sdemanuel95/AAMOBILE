package com.tofitsolutions.armasdurasargentinas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by RiverPlate on 19/03/2018.
 */

public class Informes_por_pedido_Activity extends AppCompatActivity{

    private Button porPedido,porLote;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnConsultar;
    private EditText pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes_por_pedido);
        addListenerOnButton();
    }
        public void addListenerOnButton() {

            radioGroup = (RadioGroup) findViewById(R.id.radioSex);
            btnConsultar = (Button) findViewById(R.id.btnConsultar);
            pedido = (EditText) findViewById(R.id.et_Item);
            btnConsultar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // get selected radio button from radioGroup
                    int selectedId = radioGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioButton = (RadioButton) findViewById(selectedId);



                    if(pedido.getText().toString().length() != 8){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Informes_por_pedido_Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Error!");
                        builder.setMessage("El código del pedido debe tener 8 caracteres.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
                        pedido.setText("");
                        return;
                    }


                    Intent i = new Intent(Informes_por_pedido_Activity.this,Informes_por_pedido_lista_Activity.class);
                    i.putExtra("estado",radioButton.getText()).toString();
                    i.putExtra("pedido",pedido.getText().toString());
                    startActivity(i);
                }

            });

        }


}
