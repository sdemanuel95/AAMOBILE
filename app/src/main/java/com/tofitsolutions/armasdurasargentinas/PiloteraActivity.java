package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tofitsolutions.armasdurasargentinas.controllers.ItemController;

public class PiloteraActivity extends AppCompatActivity {

    private Button ok, cancel, buttonPedido,bt_circulo;
    private TextView usuarioTV, ayudanteTV;
    private EditText pedido;
    private ItemController itemController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilotera);
        itemController = new ItemController();
        ok = (Button) findViewById(R.id.buttonOk);
        cancel = (Button) findViewById(R.id.buttonCancel);
        //buttonPedido = (Button) findViewById(R.id.buttonPedido);
        bt_circulo = (Button) findViewById(R.id.imageViewPilotera);
        usuarioTV = (TextView) findViewById(R.id.textViewUsuario);
        ayudanteTV = (TextView) findViewById(R.id.textViewAyudante);
        pedido = (EditText) findViewById(R.id.editTextPedido);

        Intent intentAutomatica = getIntent();
        final String usuario = intentAutomatica.getStringExtra("usuario");
        final String ayudante = intentAutomatica.getStringExtra("ayudante");

        usuarioTV.setText(usuario);
        ayudanteTV.setText(ayudante);


        bt_circulo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(PiloteraActivity.this, DatosUsuarioPiloActivity.class);
                i.putExtra("usuario", usuario);
                i.putExtra("ayudante", ayudante);
                startActivity(i);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PiloteraActivity.this, Pilotera2Activity.class);
                i.putExtra("usuario", usuario);
                i.putExtra("ayudante", ayudante);
                startActivity(i);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        pedido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String nro = s.toString();
                if(nro.length() == 11){
                    Items itemTemp = itemController.getItem(pedido.getText().toString());
                    if(itemTemp==null){
                        System.out.println("No existe.");
                        String mensaje = "Error: El item no existe en la base de datos.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                        pedido.setText("");
                        return;
                    }
                    else{
                        String mensaje = "Existe genio!!!";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                    }
                }

            }});
        /*buttonPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
    }
}
