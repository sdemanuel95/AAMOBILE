package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by RiverPlate on 19/03/2018.
 */

public class InformesActivity extends AppCompatActivity{

    private Button porPedido,porLote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);
        porPedido = (Button)findViewById(R.id.buttonPorPedido);
        porLote = (Button)findViewById(R.id.buttonPorLote);



        porPedido.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(InformesActivity.this, Informes_por_pedido_Activity.class);
                startActivity(i);
            }
        });

        porLote.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent i = new Intent(InformesActivity.this, Informes_por_lote_Activity.class);
                startActivity(i);

            }
        });




    }

}
