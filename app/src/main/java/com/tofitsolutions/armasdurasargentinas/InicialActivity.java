package com.tofitsolutions.armasdurasargentinas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tofitsolutions.armasdurasargentinas.controllers.InventarioController;
import com.tofitsolutions.armasdurasargentinas.util.Conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InicialActivity extends AppCompatActivity {

    private EditText codigo, contraseña;
    private String usuario;
    private TextView excepcion;
    private Button ingresar;
    private AlertDialog.Builder builder;
    private boolean codigoCorrecto, contraseñaCorrecta;
    private ArrayList<String> codigos, contraseñas;
    private InventarioController inventarioController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        new MyTask().execute();
        inventarioController = new InventarioController();
        codigoCorrecto = false;
        contraseñaCorrecta = false;

        codigos = new ArrayList<>();
        contraseñas = new ArrayList<>();

        codigo = (EditText) findViewById(R.id.ed_Codigo);
        contraseña = (EditText) findViewById(R.id.ed_Contraseña);
        excepcion = (TextView) findViewById(R.id.excepcion);
        ingresar = (Button) findViewById(R.id.ingresar);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i< codigos.size(); i++) {
                    String u = codigo.getText().toString();
                    if (u.equals(codigos.get(i))) {
                        codigoCorrecto = true;
                    }
                }

                for (int i=0;i<contraseñas.size();i++) {
                    String c = contraseña.getText().toString();
                    if (c.equals(contraseñas.get(i))) {
                        contraseñaCorrecta = true;
                    }
                }

                if(!codigoCorrecto && !contraseñaCorrecta) {
                    codigo.setBackgroundResource(R.drawable.border_error);
                    contraseña.setBackgroundResource(R.drawable.border_error);
                } else if (!contraseñaCorrecta) {
                    codigo.setBackgroundResource(R.drawable.border_correcto);
                    contraseña.setBackgroundResource(R.drawable.border_error);
                    codigoCorrecto = false;
                } else if (!codigoCorrecto){
                    codigo.setBackgroundResource(R.drawable.border_error);
                    //contraseña.setBackgroundResource(R.drawable.border_correcto);
                    contraseñaCorrecta = false;
                } else {
                    // CREAR NUEVO ACTIVITY

                    //PRUEBA
                    //PRUEBA

                    Intent i = new Intent(InicialActivity.this, PrincipalActivity.class);
                    new getUsuario().execute();
//                    finish();  //Kill the activity from which you will go to next activity
                    i.putExtra("usuario", usuario);
                    i.putExtra("contraseña", contraseña.getText().toString());
                    startActivity(i);
                }
            }
        });
    }

    private void setMensaje (String campo) {
        builder.setMessage("El " + campo + " ingresado no existe, por favor verifique los datos e intente nuevamente");
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<String> codigosBD, contraseñasBD;
        private String excepcionBD = "";

        @Override
        protected Void doInBackground(Void... params) {
            codigosBD = new ArrayList<>();
            contraseñasBD = new ArrayList<>();
            Conexion conexion = new Conexion();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/armadurasargentinas","root","root");
                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM usuariosandroid");
                while (rs.next()) {
                    codigosBD.add(rs.getString("codigo"));
                    contraseñasBD.add(rs.getString("contraseña"));
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
            codigos = codigosBD;
            contraseñas = contraseñasBD;
            excepcion.setText(excepcionBD);
            super.onPostExecute(aVoid);
        }
    }

    private class getUsuario extends AsyncTask<Void, Void, Void> {
        private String usuarioBD = "";
        private String excepcionBD = "";

        @Override
        protected Void doInBackground(Void... params) {
            Conexion conexion = new Conexion();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = conexion.crearConexion();
                Statement stmt = con.createStatement();
                final ResultSet rs = stmt.executeQuery("SELECT * FROM usuariosandroid");
                while (rs.next()) {
                    if(codigo.getText().toString().equals(rs.getString("codigo"))){
                        usuarioBD = rs.getString("nombre");
                    }
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
            usuario = usuarioBD;
            excepcion.setText(excepcionBD);
            super.onPostExecute(aVoid);
        }
    }
}
