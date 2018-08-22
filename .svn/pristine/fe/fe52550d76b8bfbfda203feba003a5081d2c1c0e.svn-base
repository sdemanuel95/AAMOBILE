package com.tofitsolutions.armasdurasargentinas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class pruebaLala extends AppCompatActivity {

    EditText prueba;
    ArrayList<Declaracion> listaDeclaracion;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_lala);

        prueba = (EditText)findViewById(R.id.pruebaPrueba);

        listaDeclaracion = new ArrayList<Declaracion>();

        prueba.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nro = s.toString();
                if (nro.length() == 1) {
                    listaDeclaracion = new ArrayList<Declaracion>();
                    //new Estribadora2Activity.validarItemDeclarado().execute();
                }
            }
        });
    }

    //NUEVO ASINCSTASK PARA VALIDAR SI UN ITEM YA ESTÁ DECLARADO
    private class validarItemDeclarado extends AsyncTask<Void, Integer, Void> {
        private ArrayList<Declaracion> listaDeclaraciones;
        private int progreso = 0;

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(pruebaLala.this);
            //progress.setMax(100);
            progress.setMessage("Validando");
            progress.setTitle("Items");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.incrementProgressBy(1);
            if (progreso == progress.getMax()) {
                progress.dismiss();
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            listaDeclaraciones = new ArrayList<Declaracion>();
            Conexion conexion = new Conexion();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                Statement stmt = con.createStatement();
                final ResultSet rowDeclaracionCompleto = stmt.executeQuery("SELECT * FROM declaracion");

                rowDeclaracionCompleto.last(); //me voy al último
                int sizeRS = rowDeclaracionCompleto.getRow(); //pillo el tamaño
                rowDeclaracionCompleto.beforeFirst(); // lo dejo donde estaba para tratarlo

                progress.setMax(sizeRS);
                while (rowDeclaracionCompleto.next()) {
                    int id = rowDeclaracionCompleto.getInt("ID");
                    String fecha = rowDeclaracionCompleto.getString("Fecha");
                    String usuario = rowDeclaracionCompleto.getString("Usuario");
                    String ayudante = rowDeclaracionCompleto.getString("Ayudante");
                    String equipo = rowDeclaracionCompleto.getString("Equipo");
                    String precintoA = rowDeclaracionCompleto.getString("PrecintoA");
                    String precintoB = rowDeclaracionCompleto.getString("PrecintoB");
                    String item = rowDeclaracionCompleto.getString("Item");
                    String cantidad = rowDeclaracionCompleto.getString("Cantidad");
                    listaDeclaraciones.add(new Declaracion(id, fecha, usuario, ayudante, equipo, precintoA, precintoB, item, cantidad));
                    progreso++;
                    publishProgress(progreso);
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
            Declaracion d;
            listaDeclaracion = listaDeclaraciones;

            super.onPostExecute(aVoid);


        }
    }
}
