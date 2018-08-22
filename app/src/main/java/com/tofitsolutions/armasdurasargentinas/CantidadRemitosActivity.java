package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CantidadRemitosActivity extends AppCompatActivity {

    private Button b_cantRem;
    private EditText et_cantRem;
    private TextView et_infor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantidad_remitos);

        b_cantRem = (Button) findViewById(R.id.button_CantRem);
        et_cantRem = (EditText) findViewById(R.id.editText_cantidadRemitos);
        et_infor = (TextView) findViewById(R.id.textInfo);

        et_cantRem.setInputType(InputType.TYPE_NULL);

        Intent intentSesioonDescarga = getIntent();
        String gruero = intentSesioonDescarga.getStringExtra("gruero");
        String ayudanteDescarga = intentSesioonDescarga.getStringExtra("ayudanteDescarga");

        b_cantRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CantidadRemitosActivity.this, ReferenciaMPActivity.class);
                String cantRem = et_cantRem.getText().toString();
                if (cantRem.equals("1") || cantRem.equals("2") || cantRem.equals("3")) {
                    int cantRemInt = Integer.parseInt(et_cantRem.getText().toString());
                    i.putExtra("cantRemitos", cantRemInt);
                    i.putExtra("re", "nore");
                    startActivity(i);
                    finish();
                } else {
                    String mensaje = "Error: Seleccione 1, 2 o 3 remitos.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }
            }
        });
    }
}
