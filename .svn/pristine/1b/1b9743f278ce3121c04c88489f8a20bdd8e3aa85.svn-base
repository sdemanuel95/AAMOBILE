package com.tofitsolutions.armasdurasargentinas;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RemitoMPActivity extends AppCompatActivity {

    private TextView tvInformativo, tvException;
    private Button btRemito;
    private EditText etRemito;
    private ArrayList<String> numerosDeRemito;
    private ArrayList<String> numerosDeReferencia;
    private int cantidadDeRemitos;
    private ArrayList<String> numerosDeRemitosMP;
    private ArrayList<String> numerosDeReferenciaMP;
    private String referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remito_mp);

        new TraerRemitos().execute();

        tvInformativo = (TextView) findViewById(R.id.textView_Informativo);
        tvException = (TextView) findViewById(R.id.textView_Exception);
        btRemito = (Button) findViewById(R.id.button_Remito);
        etRemito = (EditText) findViewById(R.id.editText_Remito);
        etRemito.setInputType(InputType.TYPE_NULL);
        numerosDeRemito = new ArrayList<>();
        numerosDeReferencia = new ArrayList<>();
        numerosDeRemitosMP = new ArrayList<>();
        numerosDeReferenciaMP = new ArrayList<>();

        Intent intentReferenciaMPActivity = getIntent();
        cantidadDeRemitos = intentReferenciaMPActivity.getIntExtra("cantRemitos", 1);
        numerosDeRemitosMP = (ArrayList<String>) intentReferenciaMPActivity.getSerializableExtra("remitos");
        numerosDeReferenciaMP = (ArrayList<String>) intentReferenciaMPActivity.getSerializableExtra("referencias");
        referencia = intentReferenciaMPActivity.getStringExtra("referencia");

        btRemito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msj = "Remito: " + etRemito.getText().toString();
                Toast mToast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
                //mToast.show();
                if(!etRemito.getText().toString().equals("") && Validar.numeroDeRemito(etRemito.getText().toString())
                        && !numerosDeRemito.contains(etRemito.getText().toString())
                        && !numerosDeReferencia.contains(etRemito.getText().toString())){

                    String antes = etRemito.getText().toString().substring(0,4);
                    String despues = etRemito.getText().toString().substring(4,12);
                    String nroRemito = antes + despues;
                    if (!numerosDeRemitosMP.contains(nroRemito)){
                        numerosDeReferenciaMP.add(referencia);
                        numerosDeRemitosMP.add(nroRemito);
                        cantidadDeRemitos--;
                        if (cantidadDeRemitos != 0) {
                            Intent i = new Intent(RemitoMPActivity.this, ReferenciaMPActivity.class);
                            i.putExtra("cantRemitos",cantidadDeRemitos);
                            i.putExtra("remitos",numerosDeRemitosMP);
                            i.putExtra("referencias",numerosDeReferenciaMP);
                            i.putExtra("re", "re");
                            finish();
                            startActivity(i);
                        } else {
                            Intent i = new Intent(RemitoMPActivity.this, ValidarChapaActivity.class);
                            i.putExtra("remitos",numerosDeRemitosMP);
                            i.putExtra("referencias",numerosDeReferenciaMP);
                            finish();
                            startActivity(i);
                        }
                    } else {
                        String mensaje = "Error: Numero de remito ya ingreasdo.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                    }
                } else {
                    String mensaje = "Error: Numero de remito invalido.";
                    Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    msjToast.show();
                }
            }
        });

    }

    private class TraerRemitos extends AsyncTask<Void, Void, Void> {

        private ArrayList<String> numerosDeRemitoBD, numerosDeReferenciaBD;

        @Override
        protected Void doInBackground(Void... params) {
            numerosDeRemitoBD = new ArrayList<>();
            numerosDeReferenciaBD = new ArrayList<>();
            Conexion conexion = new Conexion();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM ingresoremitos");
                while (rs.next()) {
                    numerosDeRemitoBD.add(rs.getString("NroRemito"));
                    numerosDeReferenciaBD.add(rs.getString("Referencia"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            numerosDeRemito = numerosDeRemitoBD;
            numerosDeReferencia = numerosDeReferenciaBD;
            super.onPostExecute(aVoid);
        }
    }
}
