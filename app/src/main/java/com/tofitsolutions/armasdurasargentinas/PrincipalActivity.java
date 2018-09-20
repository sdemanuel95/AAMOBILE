package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends AppCompatActivity {

    private Button produccion, inventario, despacho, descarga, loginInventario, stockInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        produccion = (Button) findViewById(R.id.buttonProduccion);
        inventario = (Button) findViewById(R.id.buttonInventario);
        despacho = (Button) findViewById(R.id.buttonDespacho);
        descarga = (Button) findViewById(R.id.buttonDescarga);
        stockInicial = (Button) findViewById(R.id.buttonStockInicial);
        loginInventario = (Button) findViewById(R.id.buttonLoginInventario);
        Intent intentPrePrincipal = getIntent();
        final String usuario = intentPrePrincipal.getStringExtra("usuario");
        final String contraseña = intentPrePrincipal.getStringExtra("contraseña");

        produccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrincipalActivity.this, ProduccionActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                i.putExtra("usuario", usuario);
                i.putExtra("contraseña", contraseña);
                startActivity(i);
            }
        });
        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrincipalActivity.this, DespachoActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                startActivity(i);
            }
        });
        despacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrincipalActivity.this, DespachoActivity.class);
//                    finish();  //Kill the activity from which you will go to next activity
                startActivity(i);
            }
        });
        descarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (PrincipalActivity.this, SesionDescargaActivity.class);
                startActivity(i);
            }
        });
        loginInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (PrincipalActivity.this, LoginInventarioActivity.class);
                startActivity(i);
            }
        });

        stockInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipalActivity.this, StockInicial.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
