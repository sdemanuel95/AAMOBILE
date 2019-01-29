package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tofitsolutions.armasdurasargentinas.util.Conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReferenciaMPActivity extends AppCompatActivity {

    private TextView tvInformativo, tvException;
    private Button btReferencia;
    private EditText etRerencia;
    private ArrayList<String> numerosDeReferencia;
    private ArrayList<String> numerosDeRemitosMP;
    private ArrayList<String> numerosDeReferenciaMP;
    private int cantidadDeRemitos;

    private EditText usuario, contraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referencia_mp);

        numerosDeReferencia = new ArrayList<>();
        new MyTask().execute();

        tvException = (TextView) findViewById(R.id.textView_Exception);
        tvInformativo = (TextView) findViewById(R.id.textViewInfo);
        btReferencia = (Button) findViewById(R.id.button_Referencia);
        etRerencia = (EditText) findViewById(R.id.editText_Referencia);
        etRerencia.setInputType(InputType.TYPE_NULL);

        numerosDeRemitosMP = new ArrayList<>();
        numerosDeReferenciaMP = new ArrayList<>();

        Intent intentCantidadRemitos = getIntent();
        cantidadDeRemitos = intentCantidadRemitos.getIntExtra("cantRemitos", 0);

        if (intentCantidadRemitos.getStringExtra("re").equals("re")) {
            numerosDeRemitosMP = (ArrayList<String>) intentCantidadRemitos.getSerializableExtra("remitos");
            numerosDeReferenciaMP = (ArrayList<String>) intentCantidadRemitos.getSerializableExtra("referencias");
        }

        btReferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etRerencia.getText().toString().equals("")){
                    if(numerosDeReferencia.contains(etRerencia.getText().toString())) {
                        if (!numerosDeReferenciaMP.contains(etRerencia.getText().toString())) {
                            Intent i = new Intent(ReferenciaMPActivity.this, RemitoMPActivity.class);
                            finish();
                            i.putExtra("referencia", etRerencia.getText().toString());
                            i.putExtra("referencias", numerosDeReferenciaMP);
                            i.putExtra("cantRemitos", cantidadDeRemitos);
                            i.putExtra("remitos", numerosDeRemitosMP);
                            startActivity(i);
                        } else {
                            String mensaje = "Error: Número de referencia ya ingresado.";
                            Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                            msjToast.show();
                        }
                    } else {
                        String mensaje = "Error: Número de referencia inválido.";
                        Toast msjToast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                        msjToast.show();
                    }
                }
            }
        });
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<String> numerosDeReferenciaBD;
        private String excepcionBD = "";

        @Override
        protected Void doInBackground(Void... params) {
            numerosDeReferenciaBD = new ArrayList<>();
            Conexion conexion = new Conexion();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/armadurasargentinas","root","root");
                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM ingresoremitos");
                while (rs.next()) {
                    numerosDeReferenciaBD.add(rs.getString("Referencia"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                excepcionBD = e.toString();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            numerosDeReferencia = numerosDeReferenciaBD;
            tvException.setText(excepcionBD);
            super.onPostExecute(aVoid);
        }
    }
}
