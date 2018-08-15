package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IngresoActivity extends AppCompatActivity {

    /*
    private TextView tvInformativo, tvException;
    private Button btReferencia;
    private EditText etRerencia;
    private ArrayList<String> numerosDeReferencia;
    */

    private EditText usuario, contraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        /*
        numerosDeReferencia = new ArrayList<>();
        new MyTask().execute();

        tvException = (TextView) findViewById(R.id.textView_Exception);
        tvInformativo = (TextView) findViewById(R.id.textViewInfo);
        btReferencia = (Button) findViewById(R.id.button_Referencia);
        etRerencia = (EditText) findViewById(R.id.editText_Referencia);

        btReferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etRerencia.getText().toString().equals("")){
                    if(numerosDeReferencia.contains(etRerencia.getText().toString())){
                        // TODO Crear nueva activity
                        Intent i = new Intent(ReferenciaMPActivity.this, RemitoMPActivity.class);
                        i.putExtra("referencia", etRerencia.getText().toString());
                        startActivity(i);
                    }
                    else {
                        tvInformativo.setText("Numero de referencia invalido.");
                    }
                }
            }
        });*/
    }
    /*
    private class MyTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<String> numerosDeReferenciaBD;
        private String excepcionBD = "";

        @Override
        protected Void doInBackground(Void... params) {
            numerosDeReferenciaBD = new ArrayList<>();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://b4e9xxkxnpu2v96i.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/fq9e54tk8ag0jl2f",
                        "rnahyl78396j7usi", "z4x6xvpkmu82ptrc");
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
    }*/
}
