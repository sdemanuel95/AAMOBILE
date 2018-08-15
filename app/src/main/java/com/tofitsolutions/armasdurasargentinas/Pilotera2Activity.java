package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pilotera2Activity extends AppCompatActivity {
    Button  bt_atras, bt_adelante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilotera2);
        bt_atras = (Button) findViewById(R.id.bt_atras);
        bt_adelante = (Button) findViewById(R.id.bt_adelante);

        bt_adelante.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Pilotera2Activity.this, ConfirmaPilotera.class);
                startActivity(i);
            }
        });
    }
}
