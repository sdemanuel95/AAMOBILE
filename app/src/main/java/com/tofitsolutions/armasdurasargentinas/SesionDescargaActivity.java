package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tofitsolutions.armasdurasargentinas.util.Conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SesionDescargaActivity extends AppCompatActivity {

    private EditText et_gruero, et_ayudanteDescarga;
    private Button bt_loguearme;
    private TextView excepcion;
    private boolean grueroCorrecto, ayudanteDescargaCorrecto;
    private ArrayList<String> grueros, ayudantesDescarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_descarga);

        et_ayudanteDescarga = (EditText) findViewById(R.id.ayudanteDescarga);
        et_gruero = (EditText) findViewById(R.id.gruero);
        bt_loguearme = (Button) findViewById(R.id.loguearme);
        excepcion = (TextView) findViewById(R.id.excepcionSesionDescarga);
        grueros = new ArrayList<>();
        ayudantesDescarga = new ArrayList<>();

        new MyTask().execute();
        grueroCorrecto = false;
        ayudanteDescargaCorrecto = false;

        bt_loguearme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int index = 0; index < grueros.size(); index++) {
                    String u = et_gruero.getText().toString();
                    String a = et_ayudanteDescarga.getText().toString();
                    if (u.equals(grueros.get(index))) {
                        grueroCorrecto = true;
                        if(a.equals(ayudantesDescarga.get(index))) {
                            ayudanteDescargaCorrecto = true;
                        }
                    }
                }
                if (!grueroCorrecto && !ayudanteDescargaCorrecto) {
                    et_gruero.setBackgroundResource(R.drawable.border_error);
                    et_ayudanteDescarga.setBackgroundResource(R.drawable.border_error);
                } else if (!ayudanteDescargaCorrecto) {
                    et_gruero.setBackgroundResource(R.drawable.border_correcto);
                    et_ayudanteDescarga.setBackgroundResource(R.drawable.border_error);
                    grueroCorrecto = false;
                } else if (!grueroCorrecto){
                    et_gruero.setBackgroundResource(R.drawable.border_error);
                    et_ayudanteDescarga.setBackgroundResource(R.drawable.border_correcto);
                    ayudanteDescargaCorrecto = false;
                } else {
                    // CREAR NUEVO ACTIVITY
                    Intent i = new Intent(SesionDescargaActivity.this, CantidadRemitosActivity.class);
                    i.putExtra("gruero", et_gruero.getText().toString());
                    i.putExtra("ayudanteDescarga", et_ayudanteDescarga.getText().toString());
                    startActivity(i);
                }
            }
        });
    }
    private class MyTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<String> gruerosBD, ayudantesDescargaBD;
        private String excepcionBD = "";

        @Override
        protected Void doInBackground(Void... params) {
            gruerosBD = new ArrayList<>();
            ayudantesDescargaBD = new ArrayList<>();
            Conexion conexion = new Conexion();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/armadurasargentinas","root","root");
                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM usuariosdescarga");
                while (rs.next()) {
                    gruerosBD.add(rs.getString("gruero"));
                    ayudantesDescargaBD.add(rs.getString("ayudanteDescarga"));
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
            grueros = gruerosBD;
            ayudantesDescarga = ayudantesDescargaBD;
            excepcion.setText(excepcionBD);
            super.onPostExecute(aVoid);
        }
    }
}
